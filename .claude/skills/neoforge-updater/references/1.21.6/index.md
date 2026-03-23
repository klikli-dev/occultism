# Minecraft 1.21.5 -> 1.21.6 Mod Migration Primer

This is a high level, non-exhaustive overview on how to migrate your mod from 1.21.5 to 1.21.6. This does not look at any specific mod loader, just the changes to the vanilla classes. All provided names use the official mojang mappings.

This primer is licensed under the [Creative Commons Attribution 4.0 International](http://creativecommons.org/licenses/by/4.0/), so feel free to use it as a reference and leave a link so that other readers can consume the primer.

If there's any incorrect or missing information, please file an issue on this repository or ping @ChampionAsh5357 in the Neoforged Discord server.

Thank you to:

- @Soaryn for some `ItemStack` best practices and typo fixes
- @earthcomputer for color changes when drawing strings

## Pack Changes

There are a number of user-facing changes that are part of vanilla which are not discussed below that may be relevant to modders. You can find a list of them on [Misode's version changelog](https://misode.github.io/versions/?id=1.21.6&tab=changelog).

## GUI Changes

The GUI rendering system has significantly changed from the previous version. While it may seem somewhat similar on the surface, the actual implementation details are much more complex and roundabout. This will explain a high-level overview of the new system with some basic examples.

### Prepare and Render

Rendering a GUI has been broken into two phases: 'prepare' and 'render'.

The prepare phase is what we typically consider the GUI render methods (e.g., `Gui#render`, `AbstractContainerScreen#renderBg`, etc.). These methods, instead of actually rendering the components, now submit them to the `GuiRenderState` to be stored for use during the 'render' phase. The `GuiRenderState` is stored on the `GameRenderer` and passed to the `GuiGraphics` which uses the previous methods to add the desired objects to the render state.

The render phase is what actually handles the rendering of all the objects. This is handled through the `GuiRenderer`, which reads the data from the `GuiRenderState` and, after a bit of delegated preparation and sorting, renders the objects to the screen. The `GuiRenderer` is also stored on the `GameRenderer`.

### Element Ordering

Now, how are elements rendered onto screen? In the previous versions, this happened mainly based on the order of the render calls. However, the new system using two different methods of sorting. The first method handles the depth using strata and a linear tree. The second method uses a comparator based on three things (in order of priorty): the `ScreenRectangle` scissor, the `RenderPipeline` order, and the `TextureSetup`'s hash code. These are all stored on the `GuiElementRenderState` passed to the `GuiRenderState` via `GuiRenderState#submitGuiElement`.

Once the elements have been ordered, they are rendered starting at a Z of `0`, with each element rendered `0.01` ahead of the previous.

As a warning, due to the element sort order, certain custom configurations may result in incorrect screen renders. As such, it is imperative that you understand how the methods and their assigned values work when sorting elements.

#### Strata and Trees

The first method of sorting handles the z-depth an object is rendered at.

First let's start with the linear tree. As the name implies, it is basically a doubly-linked `GuiRenderState$Node` list. Each node contains its own list of elements to render. Navigating the node list is handled using `GuiRenderState#up` or `down`, where each either gets the next node, or creates a new one if it doesn't exist. Nodes within a given tree are rendered from bottom to top, meaning that `down` will render any elements submitted before the current node, while `up` will render any elements submitted after the current node.

Determining what node an object is added to is computed automatically when submitting an element using its `ScreenArea`. The `ScreenArea` defines the `bounds` of your element to be rendered. Essentially, a node will be added in the `up` direction if the bounds of the element intersect with any other element on the current node.

Then there are strata. A stratum is essentially a linear tree. Strata are rendered in the order they are created, which means calling `nextStratum` will render all elements after the previous stratum. This can be used if you want to group elements into a specific layer. Note: you cannot navigate to a prior stratum.

#### The Comparator

The comparator handles sorting the elements within a given node in a linear tree in a strata.

##### Screen Rectangle Scissor

The `ScreenRectangle` is simply the area that the element is allowed to draw to, stored via `GuiElementRenderState#scissorArea`. Elements with no specified `ScreenRectangle` will be ordered first, followed by the minimum Y, the maximum Y, the minimum X, and the maximum X.

##### Render Pipeline

`RenderPipeline`s define the pipeline used to render some object to the screen, including its shaders, format, uniforms, etc. This is stored via `GuiElementRenderState#pipeline`. Pipelines are sorted in the order they are built. This means that `RenderPipelines#ENTITY_TRANSLUCENT` will be rendered before `RenderPipelines#GUI` if on the same layer and scissor rectangle. As this is a system that relies on classloading constants, if you want to add new elements, make sure your mod loader supports some kind of dynamic pipeline ordering.

##### Texture Hash Code

`TextureSetup` defines `Sampler0`, `Sampler1`, and `Sampler2` for use in the render pipelines, stored via `GuiElementRenderState#textureSetup`. Elements with no textures will be ordered first, followed by `getSortKey` of the record object. Note that, at the moment, this returns the `hashCode` of the `TextureSetup`, which may be non-deterministic as `GpuTextureView` does not implement `hashCode`, relying instead on the identity object hash.

### GuiElementRenderState

Now that we understand ordering, what exactly is the `GuiElementRenderState` that we've been using? Well essentially, every object rendered to the screen is represented by a `GuiElementRenderState`, from the player seen in the inventory menu to each individual item. A `GuiElementRenderState` defines four methods. First, there are the common ones used for ordering and how to render to the screen (`pipeline`, `scissorArea`, `textureSetup`, `bounds`). Then there is `buildVertices`, which takes in the `VertexConsumer` to write the vertices to and the z-depth. For GUIs, this typically calls `VertexConsumer#addVertexWith2DPose`.

There are three types of `GuiElementRenderState`s provided by vanilla: `BlitRenderState`, `ColoredRectangleRenderState`, `GlyphEffectRenderState`, and `GlyphRenderState`. `ColoredRectangleRenderState` and `GlyphRenderState`/`GlyphEffectRenderState` are simple cases for handling a basic color rectangle and text character, respectively. `BlitRenderState` covers every other case as almost every method eventually writes to a `GpuTexture` which is then consumed by this.

`GuiElementRenderState`s are added to the `GuiRenderState` via `GuiRenderState$Node#submitGuiElement`. The `GuiRenderState` makes `submitBlitToCurrentLayer` and `submitGlyphToCurrentLayer` available to add textures and glyphs. For example, these are called by `GuiGraphics#*line`, `fill`, and `blit*` methods.

#### GuiItemRenderState

`GuiItemRenderState` is a special case used to render an item to the screen. It takes in the stringified name of the item, the current pose, its XY coordinates, and its scissor area. The `ItemStackRenderState` it holds is what defines how the item is rendered. The rendering bounds are computed based on the item model bounding box when `ClientItem$Properties#oversizedInGui` is true; otherwise, using a 16x16 square when false.

Just before the 'render' phase, the `GuiRenderer` effectively turns the `GuiItemRenderState` into a `GuiElementRenderState`, more specifically a `BlitRenderState`. This is done by constructing an item atlas `GpuTexture` which the item is drawn to, and then that texture is submitted as a `BlitRenderState`. All `GuiItemRenderState`s use `RenderPipelines#GUI_TEXTURED_PREMULTIPLIED_ALPHA`.

`GuiElementRenderState`s are added to the `GuiRenderState` via `GuiRenderState#submitItem`. This is called by `GuiGraphics#render*Item*` methods.

#### GuiTextRenderState

`GuiTextRenderState` is a special case used to render text to the screen. It takes in the `Font`, `FormattedCharSequence`, current pose, its XY coordinates, color, background color, whether it has a drop shadow, and its rendering bounds.

Just before the 'render' phase, the `GuiRenderer` turns the `GuiTextRenderState` into a `GuiElementRenderState`, more specifically a `GlyphRenderState` or `GlyphEffectRenderState` depending on what should be rendered via `GuiTextRenderState#ensurePrepared`. This performs a similar process as the item render state where the text is written to a `GpuTexture` to be consumed. Any backgrounds are rendered first, followed by the background effects, then the character, then finally the foreground effects.

`GuiElementRenderState`s are added to the `GuiRenderState` via `GuiRenderState#submitText`. This is called by `GuiGraphics#draw*String` and `render*Tooltip` methods.

#### Picture-in-Picture

Picture-in-Picture is a special case used to render arbitrary objects to a `GpuTexture` to be passed into a `BlitRenderState`. A Picture-in-Picture is made up of two components the `PictureInPictureRenderState`, and the `PictureInPictureRenderer`.

`PictureInPictureRenderState` is an interface which can store some data used to render the object to the texture. By default, it must supply the minimum and maximum XY coordinates, the texture scale, its scissor area, and its rendering bounds. You can also choose to specify the transformation matrix via `pose`. Any other data can be added by the implementor.

```java
public record ExamplePIPRenderState(boolean data, int x0, int x1, int y0, int y1, float scale, @Nullable ScreenRectangle scissorArea, @Nullable ScreenRectangle bounds)
    implements PictureInPictureRenderState {}
```

`PictureInPictureRenderer` is the renderer which writes the data to the `GpuTexture`. It does this by taking advantage of the fact that the `RenderSystem#output*Override` textures are written to in a `BufferSource` if they are not null. A `PictureInPictureRenderer` method must implement three methods. `getRenderStateClass` returns the class of the `PictureInPictureRenderState` implementation. `getTextureLabel` returns the texture label for debugging purposes. `renderToTexture` is where the render logic is called to write the data to the texture. 

```java
public class ExamplePIPRenderer extends PictureInPictureRenderer<ExamplePIPRenderState> {

    // Takes in the buffer source from `RenderBuffers`
    public ExamplePIPRenderer(MultiBufferSource.BufferSource bufferSource) {
        super(bufferSource);
    }

    @Override
    public Class<ExamplePIPRenderState> getRenderStateClass() {
        // The class of the render state
        return ExamplePIPRenderState.class;
    }

    @Override
    protected void renderToTexture(ExamplePIPRenderState renderState, PoseStack pose) {
        // Render whatever you want here
        // You can make use of the buffer source via `this.bufferSource`
    }

    @Override
    protected void blitTexture(ExamplePIPRenderState renderState, GuiRenderState guiState) {
        // You can override this if you want to change how your layer is submitted to the render state
        // By default, this uses the `BlitRenderState`

        // Remove this if you want to handle submission yourself
        super.blitTexture(renderState, guiState);
    }

    @Override
    protected boolean textureIsReadyToBlit(ExamplePIPRenderState renderState) {
        // When true, this will skip setting up the textures and projection matrices and use whatever is currently available
        return false;
    }

    @Override
    protected String getTextureLabel() {
        // This can be anything, but it should be unique
        return "examplemod: example pip";
    }
}
```

To be able to use the renderer, is must be added to `GuiRenderer#pictureInPictureRenderers`. As the constructor takes in an immutable list while the renderer stores an immutable map, use whatever method is provided by your mod loader.

```java
// We will assume:
// - `GuiRenderer#bufferSource` is accessible
// - The map is made mutable
// For some GuiRenderer renderer

var examplePIP = new ExamplePIPRenderer(renderer.bufferSource);

renderer.pictureInPictureRenderers.put(
    examplePIP.getRenderStateClass(),
    examplePIP
);
```

`PictureInPictureRenderState`s are added to the `GuiRenderState` via `GuiRenderState#submitPicturesInPictureState`. This is called by `GuiGraphics#submit*RenderState` methods.

### Logic Changes

`GuiGraphics#drawString` now joins the rest of the `GuiGraphics` methods by allowing the `int` color argument to accept an alpha channel (upper eight bits). This means that any color string without an alpha specified will not be rendered. Previous behavior can be replicated by ORing your `int` color with `0xFF000000` or by passing your color into `ARGB#opaque`.  

### Contextual Bars

Many HUD elements on the screen take over where the experience bar is rendered. However, how these are rendered and which one is prioritized is handled through the contextual bar system. A contextual bar is made up of two parts, the `ContextualBarRenderer`, responsible for rendering the bar to the screen, and the `Gui$ContextualInfo`, which is used as the bar's identifier.

A `ContextualBarRenderer` requires two methods to be implemented: `renderBackground`, which renders the bar itself, and `render`, which renders elements that appear on top of the bar.

```java
public class ExampleContextualBarRenderer implements ContextualBarRenderer {

    @Override
    public void renderBackground(GuiGraphics graphics, DeltaTracker delta) {
        // Render background bar sprites here
    }

    @Override
    public void render(GuiGraphics graphics, DeltaTracker delta) {
        // Render anything that might sit on top of the bar
    }
}
```

`Gui$ContextInfo` is an enum, so your mod loader will need to either allow extensions or some other method of identifying a bar renderer. Then, the renderer needs to be added to map of renderers stored in `Gui#contextualInfoBarRenderers`. As this is an immutable map, use whatever method is provided by your mod loader.

```java
// We will assume:
// - Created a new enum value `EXAMPLEMOD_EXAMPLE`
// - The map is made mutable
// For some Gui gui
gui.contextualInfoBarRenderers.put(
    EXAMPLEMOD_EXAMPLE,
    ExampleContextualBarRenderer::new
);
```

Finally, to get your bar to be called and prioritized, you need to modify `Gui#nextContextualInfoState` to return your enum value, or whatever method is provided by your mod loader.

### Dialogs

To more generally handle screens which provide some basic functionalities -- such as confirmation screens, button selection, or user input, Vanilla has provided a generic dialog system. These dialogs can be constructed in a datapack and delivered on the client by calling `Player#openDialog`. The basic JSON description is documented in  [Minecraft Snapshot 25w20a](https://www.minecraft.net/en-us/article/minecraft-snapshot-25w20a).

For a quick overview, a basic `Dialog` contains the following components: a title, an optional external title for navigation, whether the screen can be closed by pressing 'esc', and its `DialogBody` contents. Everything else is determined by the dialog itself, but it has functionality for user inputs via `InputControl`s. Buttons are typically added through `ClickAction`s which hold the common button data and an event to execute on click. If the dialog is canceled (e.g., closed), then `onCancel` is run.

`DialogBody` and `InputControl` are simply generic interfaces that have no defined structure. Implementing them, or any dialog for that matter, requires some registration to the available types on both the client and server.

#### Custom Bodies

A `DialogBody` is simply the contents of a dialog screen. This is generally immutable information that should be displayed at all times. Every dialog holds a list of these `DialogBody`s as every screen has some text to explain what it is for. It only contains one method `mapCodec`, which is used as the registry key and encoder for the body information.

```java
// Obviously, a body would have actual contents, but this is simply an example implementation
public record ExampleDialogBody(boolean val1, int val2) implements DialogBody {
    public static final MapCodec<ExampleDialogBody> BODY_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        Codec.BOOL.fieldOf("val1").forGetter(ExampleDialogBody::val1),
        Codec.INT.fieldOf("val2").forGetter(ExampleDialogBody::val2)
    ).apply(ExampleDialogBody::new));

    @Override
    public MapCodec<? extends DialogBody> mapCodec() {
        return BODY_CODEC;
    }
}

// Register the codec to the registry
Registry.register(BuiltInRegistries.DIALOG_BODY_TYPE, "examplemod:example_body", ExampleDialogBody.BODY_CODEC);
```

```json5
// For some dialog
{
    // ...
    "body": [
        {
            // Our body
            "type": "examplemod:example_body",
            "val1": true,
            "val2": 0
        },
        // ...
    ]
}
```

How does this body actually render then? Well, that is done via a `DialogBodyHandler`. This contains a single method `createControls` that generates the `LayoutElement` to render given the `DialogScreen` and the dialog data. This body handler can be linked to the `MapCodec` via `DialogBodyHandlers#register`.

```java
public class ExampleDialogBodyHandler implements DialogBodyHandler<ExampleDialogBody> {

    @Override
    public LayoutElement createControls(DialogScreen<?> screen, ExampleDialogBody body) {
        // Create the element (widgets, layouts, etc.)
        return new StringWidget(...);
    }
}

// Note that `register` is not public, so this needs to be access widened
DialogBodyHandlers.register(BODY_CODEC, new ExampleDialogBodyHandler());
```

#### Custom Inputs

An `InputControl` represents some input a user can provide, whether that would be some text or a button submission click. This is generally made up of components that can accept or provide some string value or tag based on a state. All dialogs can provide these inputs; through the `DialogControlSet`. It only contains one method `mapCodec`, which is used as the registry key and encoder for the input control.

```java
public record ExampleInputControl(int value) implements InputControl {
    public static final MapCodec<ExampleInputControl> INPUT_CODEC = Codec.INT.fieldOf("value").xmap(
        ExampleInputControl::new, ExampleInputControl::value
    );

    @Override
    public MapCodec<? extends InputControl> mapCodec() {
        return INPUT_CODEC;
    }
}

// Register the codec to the registry
Registry.register(BuiltInRegistries.INPUT_CONTROL_TYPE, "examplemod:example_input", ExampleInputControl.INPUT_CODEC);
```

```json5
// For some dialog (assume `minecraft:simple_input_form`)
{
    "inputs": [
        {
            // Our input
            "type": "examplemod:example_input",
            
            // The identifier for the data of this input
            "key": "example_input",

            "value": 0
        },
        // ...
    ]
}
```

Like above, the input is rendered via a `InputControlHandler`. This contains a single method `addControl`, which provides the `Screen` and input control and creates the `LayoutElement` and its associated `Action$ValueGetter` via `$Output`. This input handler can be linked to the `MapCodec` via `InputControlHandlers#register`.

```java
public class ExampleInputControlHandler implements InputControlHandler<ExampleInputControl> {

    @Override
    public void addControl(ExampleInputControl control, Screen screen, InputControlHandler.Output output) {
        EditBox box = new EditBox(...);
        box.setValue(String.valueOf(control.value()));

        // Add the elements to the output
        output.accept(
            // The element to render
            box,
            // The value output of the input
            Action.ValueGetter.of(box::getValue)
        );
    }
}

// Note that `register` is not public, so this needs to be access widened
InputControlHandlers.register(INPUT_CODEC, new ExampleInputControlHandler());
```

### Custom Actions

As shown above, an input can provide some value to pass to an `Action`. The former is known as a `$ValueGetter`, which essentially gets a stringified or tagged input to use. The latter, meanwhile, ends up creating the `ClickEvent` that is sent to the server. These are typically created through `ActionButton`s which defines some common button data along with the `Action` to perform.

A custom action contains two methods: one which returns the `MapCodec` (`codec`) to use during encoding, while the other creates the `ClickEvent` based on the map of input strings to their `$ValueGetter`s.

```java
public record ExampleAction() implements Action {
    public static final MapCodec<ExampleAction> ACTION_CODEC = MapCodec.unit(new ExampleAction());

    @Override
    public MapCodec<? extends Action> codec() {
        return ACTION_CODEC;
    }

    @Override
    public Optional<ClickEvent> createAction(Map<String, Action.ValueGetter> keysToGetters) {
        // Handle how you want to map the key input map to some click event
        return Optional.empty();
    }
}

// Register the codec to the registry
Registry.register(BuiltInRegistries.DIALOG_ACTION_TYPE, "examplemod:example_action", ExampleAction.ACTION_CODEC);
```

```json5
// For some dialog (assume `minecraft:notice`)
{
    "action": {
        // Button data
        "label": "Example!",
        "tooltip": "This is an example!",
        "width": 80,

        // The action to perform
        "action": {
            // Out action type
            "type": "examplemod:example_action"
        }
    }
}
```

Depending on how you implement your `Dialog` below, the action button will automatically be added to the screen, or you will need to add it in one of the methods via `DialogControlSet#createActionButton`:

```java
// For some DialogScreen implementation, we will assume some `SimpleDialog`
@Override
protected void updateHeaderAndFooter(HeaderAndFooterLayout layout, DialogControlSet controls, SimpleDialog dialog, DialogConnectionAccess access) {
    dialog.mainActions().forEach(actionButton -> layout.addToFooter(controls.createActionButton(actionButton).build()));
}
```

#### Custom Dialogs

A `Dialog` is pretty much all of the above components put together as desired. It is up to the user to choose how to implement them. Every `Dialog` must provide its `CommonDialogData`, which defines the basic title, body contents, and functionality. In additional, a `Dialog` may choose to execute an `Action` on close via `onCancel`.

```java
// `common` is already implemented by the record
public record ExampleDialog(CommonDialogData common, boolean val1, int val2) implements Dialog {
    public static final MapCodec<ExampleDialog> DIALOG_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        CommonDialogData.MAP_CODEC.forGetter(ExampleDialog::common),
        Codec.BOOL.fieldOf("val1").forGetter(ExampleDialog::val1),
        Codec.INT.fieldOf("val2").forGetter(ExampleDialog::val2)
    ).apply(ExampleDialog::new));

    @Override
    public MapCodec<? extends Dialog> codec() {
        return DIALOG_CODEC;
    }

    @Override
    public Optional<Action> onCancel() {
        // You can choose to return something here, or empty if nothing should be done
        return Optional.empty();
    }
}

// Register the codec to the registry
Registry.register(BuiltInRegistries.DIALOG_TYPE, "examplemod:example_dialog", ExampleDialog.DIALOG_CODEC);
```

```json5
// For some dialog in `data/examplemod/dialog/example.json`
{
    // Our dialog type
    "type": "examplemod:example_dialog",
    
    // Common button data
    "title": "Example dialog!",
    "can_close_with_escape": true,
    "after_action": "wait_for_response",

    // Our custom params
    "val1": true,
    "val2": 0
}
```

Like the others, a dialog type can only be rendered through a `DialogScreen$Factory`. This interface constructs a `DialogScreen` given the parent `Screen`, `Dialog` instance, and `DialogConnectionAccess` used to communicate to the server. This dialog screen can then be linked to the `MapCodec` via `DialogScreens#register`.

```java
public class ExampleDialogScreen extends DialogScreen<ExampleDialog> {

    public ExampleDialogScreen(@Nullable Screen previousScreen, ExampleDialog dialog, DialogConnectionAccess connectionAccess) {
        super(previousScreen, dialog, connectionAccess);
    }

    // You can choose to implement the other methods as you wish
    // See existing dialog screens for an example

    @Override
    protected void populateBodyElements(LinearLayout layout, DialogControlSet controls, ExampleDialog dialog, DialogConnectionAccess access) {
        // Add elements and actions to the body of the screen (usually the center)
    }

    @Override
    protected void updateHeaderAndFooter(HeaderAndFooterLayout layout, DialogControlSet controls, ExampleDialog dialog, DialogConnectionAccess access) {
        // Add elements and actions to the header and footer of the screen (top and bottom)
    }
}

// Note that `register` is not public, so this needs to be access widened
DialogScreens.register(DIALOG_CODEC, ExampleDialogScreen::new);
```

- `com.mojang.blaze3d.vertex.VertexConsumer#addVertexWith2DPose` - Adds a vertex to be rendered in 2d space.
- `net.minecraft.client.gui`
    - `Font`
        - `drawInBatch` no longer returns anything
        - `prepareText` - Prepares the text to be rendered to the screen.
        - `splitIgnoringLanguage` - Splits the text in the order provided without being processed by the language handler.
        - `ALPHA_CUTOFF` is removed
        - `$GlyphVisitor` - An interface that handles a glyph or effect to be rendered.
        - `$PreparedText` - An interface that defines how a piece of text should be rendered by visiting each glyph.
        - `$StringRenderOutput` -> `$PreparedTextBuilder`, no longer takes in the `MultiBufferSource`, `Matrix4f`, `$DisplayMode`, packed light coords, and the inverse depth boolean
    - `Gui`
        - `shouldRenderDebugCrosshair` - Returns true if the debug crosshair should be rendered.
        - `$RenderFunction` - An interface that defines how a certain section or element is rendered on the gui.
    - `GuiGraphics` now takes in a `GuiRenderState` instead of the `MultiBufferSource$BufferSource`
        - `MAX_GUI_Z`, `MIN_GUI_Z` are removed
        - `pose` now returns a `Matrix3x2fStack`
        - `flush` is removed
        - `nextStratum` - Adds another layer to traverse through that renders after all nodes in the previous tree.
        - All methods no longer take in a `RenderType`, `VertexConsumer`, or `Function<ResourceLocation, RenderType>`, instead specifying a `RenderPipeline` and a `TextureSetup` depending on the call
        - `drawString`, `drawStringWithBackdrop` no longer returns anything
        - `draw*String` methods must now pass in an ARGB value, where A cannot be 0.
        - `renderItem(ItemStack, int, int, int, int)` is removed
        - `drawSpecial` is removed, replaced by individual `submit*RenderState` depending on the special case
        - `blurBeforeThisStratum` - Notifies the render state that a blur effect should render between this strata and all previously rendered ones. This can only be applied between once per frame.
        - `render*Tooltip` -> `set*TooltipForNextFrame`, does not directly add to the render state, instead waiting for `renderDeferredTooltip` to be called when not present or overridden
        - `renderDeferredTooltip` - Adds the tooltip information to be rendered on a new stratum.
        - `blitSprite` now has an overload that takes in a float R tint color
        - `$ScissorStack#peek` - Gets the last rectangle on the stack.
    - `LayeredDraw` class is removed
- `net.minecraft.client.gui.components`
    - `AbstractTextAreaWidget` now has an overload which takes in two additional booleans of whether to show the background or decorations
    - `AbstractWidget#getTooltip` is removed
    - `CycleButton$Builder#displayOnlyValue` now has an overload that takes in the `boolean` to set
    - `EditBox`
        - `setCentered` - Sets whether the text position should be centered.
        - `setTextShadow` - Sets whether the text should have a drop shadow effect.
    - `FocusableTextWidget` can now take in a boolean indicating whether to fill the background
        - `DEFAULT_PADDING` is now public
    - `ImageWidget#updateResource` - Updates the sprite of the image on the component.
    - `ItemDisplayWidget` - A widget that displays an `ItemStack`.
    - `LogoRenderer#keepLogoThroughFade` - When true, keeps the logo visible even when the title screen is fading.
    - `MultiLineEditBox` is now package private and should be constructed via `builder`, calling the `$Builder#set*` methods
        - `setLineLimit` - Sets the line limit of the text field.
        - `setValue` now has an overload that takes in the `boolean` to determine whether it should force pass the max line limit
    - `MultiLineLabel`
        - `getStyleAtCentered` - Computes the component style for centered text.
        - `getStyleAtLeftAligned` - Computes the component style for left aligned text.
    - `MultilineTextField#NO_CHARACTER_LIMIT` -> `NO_LIMIT`
        - `setLineLimit` - Sets the maximum number of lines that can be written on the text field.
        - `hasLineLimit` - Returns whether the text field has some line limit.
        - `setValue` now has an overload that takes in the `boolean` to determine whether it should force pass the max line limit
    - `MultiLineTextWidget#configureStyleHandling` - Sets whether something is displayed when hovering over components and what to do on click.
    - `ScrollableLayout` - A layout with some defined bounds that can be scrolled through.
    - `SplashRenderer#render` now takes in a float for the R color instead of an int
    - `WidgetTooltipHolder#refreshTooltipForNextRenderPass` now takes in the `GuiGraphics` and the XY position
- `net.minecraft.client.gui.components.spectator.SpectatorGui#renderTooltip` -> `renderAction`
- `net.minecraft.client.gui.components.tabs`
    - `LoadingTab` - A tab that indicates information is currently being loaded.
    - `Tab#getTabExtraNarration` - Returns the hint narration of the tab.
    - `TabManager` can now take in two `Consumer`s on what to do when a tab is selected or deselected
    - `TabNavigationBar`
        - `getTabs` - Returns the list of tabs on the navigation bar.
        - `setTabActiveState` - Sets the active state of the given tab index.
        - `setTabTooltip` - Sets the tooltip information of the given tab index.
- `net.minecraft.client.gui.components.toasts`
    - `NowPlayingToast` - A toast that displays the currently playing background music.
    - `Toast`
        - `xPos`, `yPos` - Gets the x and y position in relation to the current toast index.
        - `onFinishedRendering` - A method called once the toast has finished rendered on screen.
    - `ToastManager` now takes in the `Options`
        - `showNowPlayingToast`, `hideNowPlayingToast`, `createNowPlayingToast`, `removeNowPlayingToast` - Handles the 'Now Playing' music toast.
        - `$ToastInstance#resetToast` - Resets the toast.
- `net.minecraft.client.gui.contextualbar`
    - `ContextualBarRenderer` - An interface which defines an object with some background to render.
    - `ExperienceBarRenderer` - Draws the experience bar.
    - `JumpableVehicleBarRenderer` - Draws the jump power bar (e.g. horse jumping).
    - `LocatorBarRenderer` - Draws the bar that points to given waypoints.
- `net.minecraft.client.gui.font.GlyphRenderTypes` now takes in a `RenderPipeline` for the gui rendering
- `net.minecraft.client.gui.font.glyphs.BakedGlyph` now takes in a `GpuTextureView`
    - `extractEffect` - Extracts a glyph effect (e.g. drop shadow) submits the element to be rendered.
    - `extractBackground` - Extracts a glyph effect and submits the element to be rendered on the background Z.
    - `left`, `top`, `right`, `bottom` - Computes the bounds of the glyph.
    - `textureView` - Returns the view of the texture making up the glyph.
    - `guiPipeline` - Returns the pipeline to render the glyph.
    - `renderChar`, `renderEffect` now takes in an additional boolean that sets the Z offset to `0` when true and `0.001` when false
    - `$Effect#left`, `top`, `right`, `bottom` - Computes the bounds of the glyph effect.
    - `$GlyphInstance#left`, `top`, `right`, `bottom` - Computes the bounds of the glyph instance.
- `net.minecraft.client.gui.navigation.ScreenRectangle`
    - `transformAxisAligned` now takes in a `Matrix3x2f` instead of a `Matrix4f`
    - `intersects` - Returns whether this rectangle overlaps with another rectangle.
    - `encompasses` - Returns whether this rectangle contains the entirety of another rectangle.
    - `transformMaxBounds` - Returns a new rectangle that is moved into a given position by the provided matrix.
- `net.minecraft.client.gui.render`
    - `GuiRenderer` - A class that renders all submitted elements to the screen.
    - `TextureSetup` - A record that specifies samplers 0-2 for use in a render pipeline. The first two textures views are arbitrary with the third being for the current lightmap texture.
- `net.minecraft.client.gui.render.pip`
    - `GuiBannerResultRenderer` - A renderer for the banner result preview.
    - `GuiBookModelRenderer` - A renderer for the book model in the enchantment screen.
    - `GuiEntityRenderer` - A renderer for a given entity.
    - `GuiProfilerChartRenderer` - A renderer for the profiler chart.
    - `GuiSignRenderer` - A renderer for the sign background in the edit screen.
    - `GuiSkinRenderer` - A renderer for a player with a given skin.
    - `OversizedItemRenderer` - A rendered for when an item model should be rendered larger than its item slot.
    - `PictureInPictureRenderer` - An abstract class meant for rendering dynamic elements that are not standard 2d elements, items, or text.
- `net.minecraft.client.gui.render.state`
    - `BlitRenderState` - An element state for a basic 2d texture blit.
    - `ColoredRectangleRenderState` - An element state for a simple rectangle with a tint.
    - `GlyphEffectRenderState` - An element state for a glyph effect (e.g., font text drop shadow).
    - `GlyphRenderState` - An element state for a glyph (font text).
    - `GuiElementRenderState` - An interface representing the state of a element to render.
    - `GuiItemRenderState` - A record representing the state of an item to render.
    - `GuiRenderState` - The state of the GUI to render to the screen.
    - `GuiTextRenderState` - A record representing the state of the text and its location to render.
    - `ScreenArea` - An interface which defines the render area of an element. This does not affect scissoring, instead layer orders.
- `net.minecraft.client.gui.render.state.pip`
    - `GuiBannerResultRenderState` - The state of the banner result preview.
    - `GuiBookModelRenderState` - The state of the book model in the enchantment screen.
    - `GuiEntityRenderState` - The state of a given entity.
    - `GuiProfilerChartRenderState` - The state of the profiler chart.
    - `GuiSignRenderState` - The state of the sign background in the edit screen.
    - `GuiSkinRenderState` - The state of a player with a given skin.
    - `OversizedItemRenderState` - The state of an item model that can be rendered at an arbitrary size.
    - `PictureInPictureRenderState` - An interfaces that defines the basic state necessary to render the picture-in-picture to the screen.
- `net.minecraft.client.gui.screens`
    - `ConfirmScreen`
        - `layout` - Defines a vertical list of elements spaced by eight units.
        - `yesButton`, `noButton` -> `yesButtonComponent`, `noButtonComponent`
            - `yesButton`, `noButton` now represent the actual buttons
        - `addAdditionalText` - Adds any additional text before the buttons in the layout.
        - `addButtons` - Adds the buttons in the layout.
    - `PauseScreen`
        - `rendersNowPlayingToast` - Returns whether the 'Now Playing' toast should be rendered.
        - `onDisconnect` -> `disconnectFromWorld`, now public and static; not one-to-one
    - `Screen`
        - `CUBE_MAP` -> `net.minecraft.client.renderer.GameRenderer#cubeMap`
        - `PANORAMA` -> `net.minecraft.client.renderer.GameRenderer#panorama`
        - `renderBlurredBackground` now takes in the `GuiGraphics`
        - `*TooltipForNextRenderPass` methods are either removed or moved to `GuiGraphics`
        - `FADE_IN_TIME` - Represents how much time in milliseconds does it take for some element to fade in.
        - `fadeWidgets` - Sets the alpha of the `AbstractWidget`s added as children to the screen.
        - `handleClickEvent` - Handles the event to play when the component is clicked.
        - `defaultHandleClickEvent` - The default logic to execute when the component is clicked.
        - `clickUrlAction` - The logic to perform when a URL is clicked (has the Open URL style).
        - `clickCommandAction` - The logic to perform when a command should be executed (has the \* Command style).
- `net.minecraft.client.gui.screens.dialog`
    - `ButtonListDialogScreen` - A dialog screen that contains some list of buttons.
    - `DialogConnectionAccess` - A client side interface that processes general interaction information from the dialog.
    - `DialogControlSet` - An input handler for a dialog screen.
    - `DialogListDialogScreen` - A button list of `DialogListDialog`s.
    - `DialogScreen` - A screen for some dialog modal.
    - `DialogScreens` - A factory registry of dialog modals to their associated screen.
    - `MultiButtonDialogScreen` - A button list of `MultiActionDialog`s.
    - `ServerLinksDialogScreen` - A button list of `ServerLinksDialog`s.
    - `SimpleDialogScreen` - A dialog screen for some simple dialog.
    - `WaitingForResponseScreen` - A screen that handles the downtime between client/server communication of dialog submissions.
- `net.minecraft.client.gui.screens.dialog.body`
    - `DialogBodyHandler` - A list of body elements describing the contents between the title and actions/inputs.
    - `DialogBodyHandlers` - A registry of `DialogBody`s to their `DialogBodyHandler`s.
- `net.minecraft.client.gui.screens.dialog.input`
    - `InputControlHandler` - A user input handler.
    - `InputControlHandlers`- A registry of `InputControl`s to their `InputControlHandler`s.
- `net.minecraft.client.gui.screens.inventory`
    - `AbstractContainerScreen`
        - `SLOT_ITEM_BLIT_OFFSET` is removed
        - `renderContents` - Renders the slot and labels of an inventory.
        - `renderCarriedItem` - Renders the held item.
        - `renderSnapbackItem` - Renders the swapped item with the held item.
        - `$SnapbackData` - Holds the information about the dragging or swapped item as it moves from the held location to its slot location.
    - `AbstractSignEditScreen#offsetSign` -> `getSignYOffset`, not one-to-one
    - `BookEditScreen`
        - `TEXT_*`, `IMAGE_*`, `BACKGROUND_TEXTURE_*` are now public
    - `BookSignScreen` - A screen for signing a book.
    - `BookViewScreen`
        - `closeScreen` -> `closeContainerOnServer`, not one-to-one
        - `$BookAccess#getPage` now returns a `Component`
    - `EffectsInInventory`
        - `renderEffects` is now public
        - `renderTooltip` has been overloaded to a public method
    - `InventoryScreen#renderEntityInInventory` no longer takes in the XY offset, instead taking in 4 `int`s to represent the region to render to
    - `ItemCombinerScreen#renderFg` is removed
- `net.minecraft.client.gui.screens.inventory.tooltip`
    - `ClientTooltipComponent#renderText` no longer takes in the pose and buffer, instead the `GuiGraphics` to submit the text for rendering
    - `TooltipRenderUtil#renderTooltipBackground` no longer takes in a Z offset
- `net.minecraft.client.gui.screens.multiplayer.ServerLinksScreen` class is removed, replaced by dialog modal
- `net.minecraft.client.gui.screens.social`
    - `PlayerEntry#refreshHasDraftReport` - Sets whether the current context as a report for this player.
    - `SocialInteractionsPlayerList#refreshHasDraftReport` - Refreshes whether the current context as a report for all players.
- `net.minecraft.client.gui.screens.worldselection.ExperimentsScreen$ScrollArea` class is removed, replaced by the `ScrollableLayout`
- `net.minecraft.client.model.geom.ModelPart#getExtentsForGui` - Gets the set of vectors representing the part transformed into their appropriate space.
- `net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl`
    - `showDialog` - Shows the current dialog, creating the screen dynamically.
    - `serverLinks` - Returns the entries of server links the client can access.
    - `createDialogAccess` - Creates the dialog handler on the client used for communication.
    - `clearDialog` - Closes the current dialog screen.
- `net.minecraft.client.player.LocalPlayer#experienceDisplayStartTick` - Represents the start tick of when the experience display should be prioritized.
- `net.minecraft.client.renderer`
    - `GameRenderer` no longer takes in a `ResourceManager`
        - `ITEM_ACTIVATION_ANIMATION_LENGTH` is removed
        - `setRenderHand` is removed
        - `renderZoomed` is removed
        - `getPanorama` - Returns the panorama renderer.
    - `LightTexture#getTexture` -> `getTextureView`, not one-to-one
    - `MapRenderer#WIDTH`, `HEIGHT` are now public
    - `PanoramaRenderer#registerTextures` - Registers the texture for use by the cube map.
    - `PostPass$Input#texture` now returns a `GpuTextureView` instead of a `GpuTexture`
    - `RenderPipelines`
        - `GUI_OVERLAY`, `GUI_GHOST_RECIPE_OVERLAY`, `GUI_TEXTURED_OVERLAY` are removed
        - `GUI_TEXTURED_PREMULTIPLIED_ALPHA` - A pipeline that assumes the textures have already had their transparency premultiplied during the composite stage.
    - `RenderStateShard`
        - `$Builder#add` no longer takes in whether the shard should be blurred
        - `$TextureStateShard` no longer takes in a `TriState` to set the blur mode
    - `RenderType`
        - `debugLine` is removed
        - `gui`, `guiOverlay`, `guiTexturedOverlay`, `guiOpaqueTexturedBackground`, `guiNauseaOverlay`, `guiTextHighlight`, `guiGhostRecipeOverlay`, `guiTextured` are removed
        - `vignette` is removed
        - `crosshair` is removed
        - `mojangLogo` is removed
    - `ScreenEffectRenderer` is now an instance implementation instead of simply a static method
        - The constructor takes in the current `Minecraft` instance and a `MultiBufferSource`
        - `renderScreenEffect` is now an instance method, taking in whether the entity is sleeping and the partial tick
        - `resetItemActivation`, `displayItemActivation` - Handles when an item is automatically activated (e.g., totem).
- `net.minecraft.client.renderer.blockentity`
    - `*Renderer#getExtents` - Adds the transformed vectors representing all models to a set.
    - `HangingSignRenderer`
        - `MODEL_RENDER_SCALE` is now public
        - `translateBase` is now public
    - `SignRenderer`
        - `RENDER_SCALE` is now public
        - `applyInHandTransforms` - Transforms the stack to properly represent the held sign's position.
    - `SkullBlockRenderer#getRenderType(SkullBlock$Type, ResolvableProfile, ResourceLocation)` -> `getSkullRenderType`, `getPlayerSkinRenderType`; not one-to-one
- `net.minecraft.client.renderer.entity.ItemRenderer`
    - `GUI_SLOT_CENTER_X`, `GUI_SLOT_CENTER_Y`, `ITEM_DECORATION_BLIT_OFFSET` are removed
    - `COMPASS_*` -> `SPECIAL_*`
- `net.minecraft.client.renderer.item`
    - `ClientItem$Properties#oversizedInGui` - When true, allows an item to render outside the 16x16 box in a GUI; otherwise, clips the size to the box.
    - `ItemStackRenderState`
        - `setAnimated`, `isAnimated` - Returns whether the item is animated (e.g., foil).
        - `appendModelIdentityElement`, `getModelIdentity`, `clearModelIdentity` - Handles the identity component being rendered.
        - `getModelBoundingBox` - Computes the bounding box of the model.
        - `setOversizedInGui`, `isOversizedInGui` - Handles when the item can be oversized in the GUI based on its property.
- `net.minecraft.client.renderer.special`
    - `PlayerHeadSpecialRenderer` - Renders an player head based on its render info.
    - `SkullSpecialRenderer` now implements `NoDataSpecialModelRenderer`, no longer taking in the model or texture override, instead just the `RenderType` to use
    - `SpecialModelRenderer#getExtents` - Adds the transformed vectors representing all models used by this renderer to a set.
- `net.minecraft.client.renderer.texture.TextureAtlasSprite#isAnimated` - Returns whether the sprite has any animation.
- `net.minecraft.commands.arguments.ResourceOrIdArgument#dialog`, `getDialog`, `$DialogArgument` - Handles command arguments for dialog screens.
- `net.minecraft.core.registries`
    - `BuiltInRegistries#DIALOG_TYPE`, `DIALOG_ACTION_TYPE`, `INPUT_CONTROL_TYPE`, `DIALOG_BODY_TYPE`
    - `Registries#DIALOG_TYPE`, `DIALOG_ACTION_TYPE`, `INPUT_CONTROL_TYPE`, `DIALOG_BODY_TYPE`, `DIALOG`
- `net.minecraft.data.tags.DialogTagsProvider` - A tags provider for dialogs.
- `net.minecraft.network.chat`
    - `ClientEvent`
        - `$Action#SHOW_DIALOG`, `CUSTOM`
            - `valueCodec` - Returns the map codec used to encode this action.
        - `$Custom` - An event that contains some nbt payload to send to the server, currently does nothing.
        - `$ShowDialog` - An event that shows the specified dialog.
    - `CommonComponents`
        - `GUI_RETURN_TO_MENU` - The component that displays the 'Return to Menu' text.
        - `disconnectButtonLabel` - Returns the component based on whether the server is local.
- `net.minecraft.network.protocol.common`
    - `ClientboundClearDialogPacket` - Closes the current dialog screen.
    - `ClientboundShowDialogPacket` - Opens a new dialog screen.
    - `ServerboundCustomClickActionPacket` - Sends a custom action to the server.
- `net.minecraft.server.dialog`
    - `ActionButton` - A button that can perform some `Action` on click.
    - `ButtonListDialog` - A dialog modal that has some number of columns to click buttons of.
    - `CommonButtonData` - The data that is associated with every button within a dialog modal.
    - `CommonDialogData` - The data that is associated with every dialog modal.
    - `ConfirmationDialog` - A dialog modal whether you can either click yes or no.
    - `Dialog` - The base interface that defines some dialog modal.
    - `DialogAction` - The action to perform typically after some action button is clicked.
    - `DialogListDialog` - A scrollable list of buttons that lead to other dialogs.
    - `Dialogs` - A datapack bootstrap registering `Dialog`s.
    - `DialogTypes` - A registry of map codecs to encode some dialog model.
    - `Input` - A handler that maps some key to an `InputControl`.
    - `MultiActionDialog` - A scrollable list of actions arrange in columns.
    - `NoticeDialog` - A simple screen with one action in the footer.
    - `ServerLinksDialog` - A scrollable list of links received from the server, arrange in columns.
    - `SimpleDialog` - A dialog that defines the main actions that can be taken.
- `net.minecraft.server.dialog.action`
    - `Action` - A general operation to perform on some input, usually a button click.
    - `ActionTypes` - A registry of map codecs to encode some action.
    - `CustomTemplate` - Builds a command and requests the server to run it.
    - `CustomAll` - Builds a custom server click action from all inputs and requests the server to run it.
    - `ParsedTemplate` - A template that encodes some string similar to how function macros work.
    - `StaticAction` - An action that fires a `ClickEvent` on activation.
- `net.minecraft.server.dialog.body`
    - `DialogBody` - A body element describing the content between the title and actions/inputs.
    - `DialogBodyTypes` - A registry of map codecs to encode some body.
    - `ItemBody` - An item with an optional description.
    - `PlainMessage` - A multiline label.
- `net.minecraft.server.dialog.input`
    - `BooleanInput` - A plain checkbox with a label.
    - `InputControl` - A control mechanism to accept user input.
    - `InputControlTypes` - A registry of map codecs to encode some input control.
    - `NumberRangeInput` - A slider for picking a numeric value from some range.
    - `SingleOptionInput` - A button that cycles between a set of options.
    - `TextInput` - Simple text input.
- `net.minecraft.world.entity.player.Player#openDialog` - Opens the specified dialog screen.

## Waypoints

Waypoints are simply a method to track the position of some object in the game. The underlying system is handled by the `WaypointManager`, responsible for holding the waypoints it is tracking while also allowing updates and removals as necessary. The server handles waypoints through the `ServerWaypointManager` (obtained via `ServerLevel#getWaypointManager`), which holds an active connect to the transmitter to receive live updates. The client receives these waypoints via the `ClientWaypointManager` (obtained via `ClientPacketListener#getWaypointManager`), which simply holds some identifier along with an exact position, a chunk if the position is not within the view distance, or an angle if the distance is further than the stored `Waypoint$Fade#farDist`, which is over 332 blocks away by default.

Entities track waypoints by default.

### Styles and Icons

Every waypoint is represents by an icon of some kind, which is defined by its `WaypointStyle` and the color of the icon. A `WaypointStyle` is similar to a client item or a equipment model, where it has some key pointed to by a `WaypointStyleAsset` that's located in `assets/<modid>/waypoint_style/<path>.json`. This contains a list of sprites located within `assets/<modid>/textures/gui/sprites/hud/locator_bar_dot/<path>.png` which is chosen based upon the distance from the tracker. The sprites change between the range specified by the near and far distance.

```json5
// For some style examplemod:example_style
// It will be found in `assets/examplemod/waypoint_style/example_style.json`
{
    // Represents that any value closer will use the first sprite when rendering the bar
    // Defaults to 128 when not specified
    "near_distance": 100,
    // Represents that any value further will use the last sprite when rendering the bar
    // Defaults to 332 when not specified
    // Must be greater than near distance
    "far_distance": 400,
    // A non-empty list of textures relative to `assets/<modid>/textures/gui/sprites/hud/locator_bar_dot/<path>.png`
    // This is what's used to lerp between the two distances
    "sprites": [
        // Points to `assets/examplemod/textures/gui/sprites/hud/locator_bar_dot/example_style_0.png`
        "examplemod:example_style_0",
        "examplemod:example_style_1",
        "examplemod:example_style_2"
    ]
}
```

The icon can then be constructed using its constructor and referenced via `WaypointTransmitter#waypointIcon` or `TrackedWaypoint#icon` on the server or client, respectively.

```java
// We will assume that this constructor is made public for a more dynamic usage
// Currently, it can only be set on a `LivingEntity` through its `CompoundTag` via `locator_bar_icon`
public static Waypoint.Icon EXAMPLE_ICON = new Waypoint.Icon(
    // The registry key of the waypoint style
    // Points to `assets/examplemod/waypoint_style/example_style.json`
    ResourceKey.create(WaypointStyleAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath("examplemod", "example_style")),
    // The color of the waypoint
    // When not present, uses the hashcode of the waypoint identifier
    Optional.of(0xFFFFFF)
);
```

### Connections

Waypoints, when tracked, are managed through their connections via `WaypointTransmitter$Connection`. A connection is responsible for syncing information to the client, whether connecting, disconnecting, or updating.

```java
public class ExampleBlockConnection implements WaypointTransmitter.Connection {

    private final BlockState state;
    private final BlockPos pos;
    private final Waypoint.Icon icon;
    private final ServerPlayer receiver;

    public ExampleBlockConnection(BlockState state, BlockPos pos, Waypoint.Icon icon, ServerPlayer receiver) {
        this.state = state;
        this.pos = pos;
        this.icon = icon;
        this.recevier = receiver;
    }

    public static boolean doesSourceIgnoreReceiver(BlockPos pos, ServerPlayer player) {
        double receiveRange = player.getAttributeValue(Attributes.WAYPOINT_RECEIVE_RANGE);
        return pos.distSqr(player.blockPosition()) >= receiveRange * receiveRange;
    }

    @Override
    public boolean isBroken() {
        // When true, it will attempt to remake the connection to this transmitter
        // This is only called when updating the position
        return ExampleBlockConnection.doesSourceIgnoreReceiver(this.pos, this.receiver);
    }

    @Override
    public void connect() {
        // This sends the tracking packet to the client
        this.receiver.connection.send(ClientboundTrackedWaypointPacket.addWaypointPosition(this.state.toString() + ": " + this.pos.toString(), this.icon, this.pos));
    }

    @Override
    public void disconnect() {
        // This sends the removal packet to the client
        this.receiver.connection.send(ClientboundTrackedWaypointPacket.removeWaypoint(this.state.toString() + ": " + this.pos.toString()));
    }

    @Override
    public void update() {
        // This updates the tracked value on the client assuming that the connect has not broken
        // In our case, we can assume this never changes as the position of a block should remain consistent
    }
}
```

### Transmitters

A `WaypointTransmitter` is responsible for constructing the connection between it and the server. For your object to transmit a location, it must implement `WaypointTransmitter` and its three methods. `waypointIcon` simply returns the icon to display. `isTransmittingWaypoint` will determine whether the waypoint can be transmitted from this object. `makeWaypointConnectionWith` actually handles constructing the connection used to track the position or angle of the connection.

```java
public class ExampleWaypointBlock extends BlockEntity implements WaypointTransmitter {

    // ...

    @Override
    public boolean isTransmittingWaypoint() {
        // This should return false if no connection should be made
        return true;
    }

    @Override
    public Optional<WaypointTransmitter.Connection> makeWaypointConnectionWith(ServerPlayer player) {
        // Make a connection if nothing is ignored
        return ExampleBlockConnection.doesSourceIgnoreReceiver(this.getBlockPos(), player)
            ? Optional.empty()
            : Optional.of(new ExampleBlockConnection(this.getBlockState(), this.getBlockPos(), this.waypointIcon(), player));
    }

    @Override
    public Waypoint.Icon waypointIcon() {
        return EXAMPLE_ICON;
    }
}
```

Then, all you need to do is track, update, or untrack the transmitter as necessary. This can be done using the methods provided by `ServerWaypointManager`:

```java
// We will assume we have access to:
// - ServerLevel serverLevel
// - ExampleWaypointBlock be

// Tracking the waypoint, such as on some initialization
serverLevel.getWaypointManager().trackWaypoint(be);

// Update the waypoint if the position changes
serverLevel.getWaypointManager().updateWaypoint(be);

// Remove the waypoint once it no longer exists
serverLevel.getWaypointManager().untrackWaypoint(be);
```

- `net.minecraft.client`
    - `Camera` now implements `TrackedWaypoint$Camera`
    - `Minecraft#getWaypointStyles` - Returns a manager of keys to the style of a waypoint.
- `net.minecraft.client.data.models.WaypointStyleProvider` - A data provider that generates waypoint styles.
- `net.minecraft.client.multiplayer.ClientPacketListener#getWaypointManager` - Gets the client manager used for tracking waypoints.
- `net.minecraft.client.renderer.GameRenderer` now implements `TrackedWaypoint$Projector`
- `net.minecraft.client.resources`
    - `WaypointStyle` - Defines the style used to render a waypoint.
    - `WaypointStyleManager` -  The manager that maps some key to the style of a waypoint.
- `net.minecraft.client.waypoints.ClientWaypointManager` - A client-side manager for tracked waypoints.
- `net.minecraft.commands.arguments.WaypointArgument` - A static method holder that gets some waypoint transmitter from an entity.
- `net.minecraft.network.protocol.game`
    - `ClientboundTrackedWaypointPacket` - A packet that sends some operation for a waypoint to the client.
    - `ClientGamePacketListener#handleWaypoint` - Handles the waypoint packet on the client.
- `net.minecraft.server.ServerScoreboard$Method` enum is removed
- `net.minecraft.server.level.ServerLevel#getWaypointManager` - Gets the server manager used for tracking waypoints.
- `net.minecraft.server.level.ServerPlayer#isReceivingWaypoints` - Whether the player is receiving waypoints from other locations or entities.
- `net.minecraft.server.waypoints.ServerWaypointManager` - A server-side manager for tracked waypoints (e.g., players and transmitters).
- `net.minecraft.world.entity`
    - `Entity#getRequiresPrecisePosition`, `setRequiresPrecisePosition` - Handles when a more precise position is needed for tracking.
    - `LivingEntity` now implements `WaypointTransmitter`
- `net.minecraft.world.waypoints`
    - `TrackedWaypoint` - A waypoint that is identified by some UUID or string, displayed via a `Waypoint$Icon`, and specified via `$Type`.
    - `TrackedWaypointManager` - A waypoint manager for `TrackedWaypoint`s.
    - `Waypoint` - An interface that represents some positional location. This holds no information, and is typically used in the context of `TrackedWaypoint`.
    - `WaypointManager` - An interface that tracks and updates waypoints.
    - `WaypointStyleAsset` - A class that indicates a style asset.
    - `WaypointStyleAssets` - A class that defines all vanilla style assets.
    - `WaypointTransmitter` - An object that transmits some position that can be connected to and tracked.

## Blaze3d Changes

Just like the previous update Blaze3d has new redesigns that change how rendering is handled.

### Buffer Slices

The most important change within the rendering system is the use of `GpuBufferSlice`s. As the name implies, it simply takes some slice of a `GpuBuffer`, using an `offset` to indicate the starting index, and the `length` to indicate its size. When dealing with anything rendering related, even when passing to a shader, you will almost always be dealing with a `GpuBufferSlice`. A way to think about it is that the buffer is just some arbitrary list of object where a slice represents a specific object.

To create a slice, simply call `GpuBuffer#slice`, optionally providing the starting index and length. In most instances, you will not be calling this method directly, instead dealing with other methods that call it for you. Note that as you are generally writing data to these slices, they should be constructed before a try-with-resources `RenderPass` is created.

### Uniform Rework

The uniform system has been completely overhauled such that, unless you are familiar with specific features, it might seem completely foreign. In a nutshell, uniforms are now stored as either interface objects, a texel buffer, or a sampler. These are populated either by `GpuBuffer`s or `GpuBufferSlice`s.

#### Uniform Types

A uniform is currently represented as one of two `UniformType`s: either as an `UNIFORM_BUFFER`/interface block, or a `TEXEL_BUFFER`. When setting up a pipeline and calling `withUniform`, you must specify your uniform with one of the above types. If you choose to use a texel buffer, you must also specify the format of the texture data. Samplers have not changed.

```java
public static final RenderPipeline EXAMPLE_PIPELINE = RenderPipeline.builder(...)
    // Uses a uniform interface block called 'ExampleUniform'
    .withUniform("ExampleUniform", UniformType.UNIFORM_BUFFER)
    // Uses a buffer called 'ExampleTexel' that stores texture data as a 8-bit R value
    .withUniform("ExampleTexel", UniformType.TEXEL_BUFFER, TextureFormat.RED8I)
    // Perform other setups
    .build();
```

#### Interface Blocks

A `UNIFORM_BUFFER` is stored as a `std140` interface block. In GLSL, this is represented like so:

```glsl
// In assets/examplemod/shaders/core/example_shader.json

// ExampleUniform is the name of the block
layout(std140) uniform ExampleUniform {
    // The data within the block
    // Post Effects can only use int, float, vec2, vec3, ivec3, vec4, or mat4
    vec4 Param1;
    float Param2;
};
```

The values can be used freely inside the main block like so:

```glsl
// In assets/examplemod/shaders/core/example_shader.json

out vec4 fragColor;

void main() {
    fragColor = Param1;
}
```

The uniform block can either be inside the same file as the main function or, if it will be reused, as a `moj_import`, where the uniform file is within `assets/<modid>/shaders/include/<path>`. Note that any shaders used before resource packs are loaded cannot use `moj_import`.

Post Effects define their interface blocks in the `uniform` input:

```json5
// In assets/examplemod/post_effect/example_post_effect.json
// Inside a 'passes' object
{
    "vertex_shader": "...",
    // ...
    "uniforms": {
        // The name of the interface block
        "ExampleUniform": [
            // A parameter within the uniform block
            // See `UniformValue` for codec formats
            {
                // The name of the parameter
                "name": "Param1",
                // The parameter type, one of:
                // - int
                // - ivec3
                // - float
                // - vec2
                // - vec3
                // - vec4
                // - matrix4x4 (mat4)
                "type": "vec4",
                // The value stored in the uniform
                // Dynamic values from the codebase are no longer supported
                "value": [ 0.0, 0.0, 0.0, 0.0 ]
            },
            {
                "name": "Param2",
                "type": "float",
                "value": 0
            }
        ]
    }
}
```

#### Texel Buffers

Texel buffers are typically represented as a `isamplerBuffer` to be queried from, typically using `texelFetch`:

```glsl
// In assets/examplemod/shaders/core/example_shader.json

uniform isamplerBuffer ExampleTexel;

void main() {
    // Read the documentation to understand how texel fetch works
    texelFetch(ExampleTexel, gl_InstanceID);
}
```

#### Writing Custom Uniforms

Writing custom uniforms can only happen when you are the one responsible for creating the `RenderPass`. Like before, you create and cache the object before opening the `RenderPass`, then you set the uniform by calling `RenderPass#withUniform`. The only difference is that, instead of providing arbitrary objects, you must now either provide a `GpuBuffer` or `GpuBufferSlice` with the uniform data written to it. For a texel buffer, this is usually some encoded data in the desired texture format (such as a mesh). For an interface block, the easiest method is to use the `Std140Builder` to populate the buffer with the correct values.

There are many different methods of writing to a `GpuBuffer` or `GpuBufferSlice`, such as using the newly created `MappableRingBuffer`. It is up to you to figure out what method works best in your scenario. The following is simply one solution out of many.

```java
// Buffer for 'ExampleUniform'
// Takes in the name of the buffer, its usage, and the size
private final MappableRingBuffer ubo = new MappableRingBuffer(
    // Buffer name
    () -> "Example UBO",
    // Buffer Usage
    // We set 128 as its used for a uniform and 2 since we are writing to it
    // Other bits can be found as constants in `GpuBuffer`
    GpuBuffer.USAGE_UNIFORM | GpuBuffer.USAGE_MAP_WRITE,
    // The size of the buffer
    // Easiest method is to use Std140SizeCalculator to properly calculate this
    new Std140SizeCalculator()
        // Param1
        .putVec4()
        // Param2
        .putFloat()
        .get()
);

// Buffer for 'ExampleTexel'
private final MappableRingBuffer utb = new MappableRingBuffer(
    // Buffer name
    () -> "Example UTB",
    // We set 256 as its used for a texel buffer and 2 since we are writing to it
    GpuBuffer.USAGE_UNIFORM_TEXEL_BUFFER | GpuBuffer.USAGE_MAP_WRITE,
    // The size of the buffer
    // It will likely be larger than this for you
    3
);

// In some render method

// Populate the buffers as required

// As we are using a ring buffer, this simply uses the next available buffer in the list
this.ubo.rotate();
// Write the data to the buffer
try (GpuBuffer.MappedView view = RenderSystem.getDevice().createCommandEncoder().mapBuffer(this.ubo.currentBuffer(), false, true)) {
    Std140Builder.intoBuffer(view.data())
        .putVec4(0f, 0f, 0f, 0f)
        .putFloat(0f);
}

// Similar thing here
this.utb.rotate();
try (GpuBuffer.MappedView view = RenderSystem.getDevice().createCommandEncoder().mapBuffer(this.utb.currentBuffer(), false, true)) {
    view.data()
        .put((byte) 0)
        .put((byte) 0)
        .put((byte) 0);
}

// Create the render pass and pass in the buffers as part of the uniform
try (RenderPass pass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(...)) {
    // ..
    pass.setUniform("ExampleUniform", this.ubo.currentBuffer());
    pass.setUniform("ExampleTexel", this.utb.currentBuffer());
    // ...
}
```

### Fog Environments

The fog system has been reworked into an environment/render state-like handler. Each environment mostly handles setting up the fog data and color. Some additional processing is provided for the individual `FogType`s and special mob effects.

All environments are handled through the `FogEnvironment`. A environment is setup via the `setupFog` method, which is responsible for mutating the passed in `FogData` to the values associated with that environment. If the fog colors its surroundings, then `providesColor` should return true, with the base color tint applied in `getBaseColor`. Likewise, if the fog darkens its surroundings, then `modifiesDarkness` should return true, with the modified darkness returned via `getModifiedDarkness`. To determine whether an environment should apply, `isApplicable` is checked. If not applicable, then `onNotApplicable` is called.

All environments are registered to `FogRenderer#FOG_ENVIRONMENTS`; however, what environment to use is based on `isApplicable` and the list order. For the color and darkness, the fog environment chosen is the last one in the list where one of the `provides*` methods return true. For the actual fog setup, the fog environment chosen is the first one in the list.

### Render Pass Scissoring now only OpenGL

The scissoring state has been removed from the generic pipeline code, now only accessible through the OpenGL implementation. Generic region handling has been delegated to `CommandEncoder#clearColorAndDepthTextures`. Note that this does not affect the existing `ScreenRectangle` system that handles the blit facing scissoring.

- `com.mojang.blaze3d.buffers`
    - `BufferType` enum is removed
    - `BufferUsage` enum is removed
    - `GpuBuffer` now takes two ints represent the size and usage, the type is no longer specified
        - `type` is removed
        - `usage` now returns an int
        - `slice` - Returns a record representing a slice of some buffer. The actual buffer is not modified in any way.
        - `$ReadView` -> `$MappedView`
    - `GpuBufferSlice` - A record that represents a slice of some buffer by holding a reference to the entire buffer, an offset index of the starting point, and the length of the slice.
    - `GpuFence` is now an interface, with its OpenGL implementation moved to `blaze3d.opengl.GlFence`
    - `Std140Builder` - A buffer inserter for an interface block laid out in the `std140` format. This is used for the uniform blocks within shaders.
    - `Std140SizeCalculator` - An object used for keeping track of the size of some interface block.
- `com.mojang.blaze3d.opengl`
    - `AbstractUniform` is removed, replaced by the `Std140*` classes
    - `BufferStorage` - A class that create buffers given its types. Its implementations define its general usage.
    - `DirectStateAccess`
        - `createBuffer` - Creates a buffer.
        - `bufferData` - Initializes a buffer's data store.
        - `bufferSubData` - Updates a subset of a buffer's data store.
        - `bufferStorage` - Creates the data store of a buffer.
        - `mapBufferRange` - Maps a section of a buffer's data store.
        - `unmapBuffer` - Relinguishes the mapping of a buffer and invalidates the pointer to its data store.
    - `GlBuffer` now takes in a `DirectStateAccess`, two ints, and a `ByteBuffer` instead of a `GlDebugLabel` and its `Buffer*` enums
        - `initialized` is removed
        - `persistentBuffer` - Holds a reference to an immutable section of some buffer.
        - `$ReadView` -> `$GlMappedView`
    - `GlCommandEncoder`
        - `executeDrawMultiple` now takes in a collection of strings that defines the list of required uniforms and a generic indicating the object of the draw call
        - `executeDraw` now takes in two `int`, the number of instances of the specified range of indices to be rendered and the base vertex
    - `GlConst#toGl` for `BufferType` and `BufferUsage` -> `bufferUsageToGlFlag`, `bufferUsageToGlEnum`
    - `GlDebugLabel#pushDebugGroup`, `popDebugGroup` - Profiler commands for grouping similar calls.
    - `GlDevice`
        - `USE_GL_ARB_buffer_storage` - Sets the extension flag for `GL_ARB_buffer_storage`.
        - `getBufferStorage` - Returns the storage responsible for creating buffers.
    - `GlProgram`
        - `Uniform` fields have been removed
        - `safeGetUniform`, `setDefaultUniforms` are removed
        - `bindSampler` is removed
        - `getSamplerLocations`, `getSamplers`, `getUniforms` -> `getUniforms` which returns a map of names to their `Uniform` objects
    - `GlRenderPass`
        - `uniforms` now is a `HashMap<String, GpuBufferSlice>`
        - `dirtSamplers` is removed
        - `samplers` map now holds a `GpuTextureView` value
        - `pushedDebugGroups` - Returns the number of groups pushed onto the stack. No debug groups must be open for the render pass to be closed.
        - `isScissorEnabled` - Returns whether the scissoring state is enabled which crops the area that is rendered to the screen.
        - `getScissorX`, `getScissorY`, `getScissorWidth`, `getScissorHeight` - Returns the values representing the scissored rectangle.
    - `GlTexture` now takes in an additional integer for the usage and depth/layers
        - `flushModeChanges` now takes in an `int` which represents the texture target
        - `addViews`, `removeViews` - Manages the views looking at a texture for some mip levels.
    - `GlTextureView` - A view implementation of a texture for some mip levels.
    - `Uniform` is now a sealed interface which is implemented as either a buffer object, texel buffer, or a sampler
- `com.mojang.blaze3d.pipeline`
    - `BlendFunction#PANORAMA` is removed
    - `CompiledRenderPipeline#containsUniform` is removed
    - `RenderPipeline`
        - `$Builder#withUniform` now has an overload that can take in a `TextureFormat`
        - `$UniformDescription` now takes in a nullable `TextureFormat`
    - `RenderTarget`
        - `colorTextureView`, `depthTextureView`, `getColorTextureView`, `getDepthTextureView` - A view of the current color and depth textures.
        - `blitAndBlendToTexture` now takes in a `GpuTextureView` instead of a `GpuTexture`
- `com.mojang.blaze3d.platform.Lightning` is now `AutoCloseable`
    - `setup*` -> `setupFor`, an instance method that takes in an `$Entry`
    - `updateLevel` - Updates the lighting buffer, taking in whether to use the nether diffused lighting.
- `com.mojang.blaze3d.shaders`
    - `FogShape` -> `net.minecraft.client.renderer.fog.FogData`, not one-to-one
    - `UniformType` now only holds two types: `UNIFORM_BUFFER`, or `TEXEL_BUFFER`, and no longer takes in a count
- `com.mojang.blaze3d.systems`
    - `CommandEncoder`
        - `clearColorAndDepthTextures` now has an overload that takes in four `int`s representing the region to clear the texture information within
        - `writeToBuffer`, `mapBuffer(GpuBuffer, int, int)` now take in a `GpuBufferSlice` instead of a `GpuBuffer`
        - `createFence` - Creates a new sync fence.
        - `createRenderPass` now takes in a `Supplier<String>`, used for determining the name of the pass to use as a debug group, and `GpuTextureView`s instead of `GpuTexture`s
        - `writeToTexture` now takes in an additional `int` representing the depth or layer to write to
        - `presentTexture` now takes in a `GpuTextureView` instead of a `GpuTexture`
    - `GpuDevice`
        - `createBuffer` no longer takes in a `BufferUsage`, with `BufferType` replaced by an `int`
        - `getUniformOffsetAlignment` - Returns the uniform buffer offset alignment.
        - `createTexture` now takes in additionals `int` for the usage and the number of depth/layers, see `GpuTexture` constants
        - `createTextureView` - Creates a view of a texture for a certain range of mip levels.
    - `RenderPass`
        - `enableScissor` is removed
        - `bindSampler` can now take in a nullable `GpuTextureView`
        - `setUniform` can either take in a `GpuBuffer` or `GpuBufferSlice` instead of the raw inputs
        - `drawIndexed` now takes the following `int`s: the base vertex, the start index, the number of elements, and the prim count
        - `drawMultipleIndexed` now takes in a collection of strings that defines the list of required uniforms and a generic indicating the object of the draw call
        - `pushDebugGroup`, `popDebugGroup` - Profiler commands for grouping similar calls.
        - `$UniformUploader#upload` now takes in a `GpuBufferSlice` instead of an array of `float`s
        - `$Draw` now has a generic that passed in to upload any uniforms to the buffer
    - `RenderSystem`
        - `SCISSOR_STATE` -> `scissorStateForRenderTypeDraws`, now private
            - Accessible through `getScissorStateForRenderTypeDraws`
        - `enableScissor`, `disableScissor` -> `enableScissorForRenderTypeDraws`, `disableScissorForRenderTypeDraws`, not one to one
        - `PROJECTION_MATRIX_UBO_SIZE` - Returns the size of the projection matrix uniform
        - `setShaderFog`, `getShaderFog` now deals with a `GpuBufferSlice`
        - `setShaderGlintAlpha`, `getShaderGlintAlpha` is removed
        - `setShaderLights`, `getShaderLights` now deals with a `GpuBufferSlice`
        - `setShaderColor`, `getShaderColor`
        - `setup*Lighting` methods are removed
        - `setProjectionMatrix`, `getProjectionMatrix` (now `getProjectionMatrixBuffer`) now deals with a `GpuBufferSlice`
        - `setShaderGameTime`, `getShaderGameTime` -> `setGlobalSettingsUniform`, `getGlobalSettingsUniform`; not one-to-one
        - `getDynamicUniforms` - Returns a list of uniforms to write for the shader.
        - `bindDefaultUniforms` - Binds the default uniforms to be used within a `RenderPass`
        - `outputColorTextureOverride`, `outputDepthTextureOverride` are now `GpuTextureView`s
        - `setupOverlayColor`, `setShaderTexture`, `getShaderTexture` now operate on `GpuTextureView`s instead of `GpuTexture`s
- `com.mojang.blaze3d.textures`
    - `GpuTexture` now takes in an int representing the usage flags and number of depth/layers
        - `usage` - The flags that define how the texture can be used.
        - `depthOrLayers`, `getDepthOrLayers` - Defines how many layers or depths are available for a given texture. This is meant as a generic count for available texture encodings. Only cubemap support is currently available (meaning the layer count must be a multiple of 6).
    - `GpuTextureView` - A view of some texture for a range of mip levels.
    - `TextureFormat#RED8I` - An 8-bit signed integer handling the red color channel.
- `com.mojang.blaze3d.vertex`
    - `ByteBufferBuilder` now takes in a long for the maximum capacity
        - `exactlySized` - Returns a buffer that is its maximum capacity. This should be called over the public constructor.
    - `DefaultVertexFormat#EMPTY` - A vertex format with no elements.
- `net.minecraft.client.renderer`
    - `CachedOrthoProjectionMatrixBuffer` - An object that caches the orthographic projection matrix, rebuilding if the width or height of the screen changes.
    - `CachedPerspectiveProjectionMatrixBuffer` - An object that caches the perspective projection matrix, rebuilding if the width, height, or field of view changes.
    - `CloudRenderer`
        - `FLAG_INSIDE_FACE`, `FLAG_USE_TOP_COLOR` are now private
        - `RADIUS_BLOCKS` is removed
        - `endFrame` - Ends the current frame being rendered to by constructing a fence.
    - `CubeMap` is now `AutoCloseable`
        - `render` no longer takes in the float for the partial tick.
    - `DynamicUniforms` - A class that writes the uniform interface blocks to the buffer for use in shaders.
    - `DynamicUniformStorage` - A class that holds the uniforms within a slice of a mappable ring buffer.
    - `FogParameters` record is removed, now stored in a general `GpuBufferSlice`
    - `FogRenderer` now implements `AutoCloseable` -> `.fog.FogRenderer`
        - `endFrame` - Ends the current frame being rendered to by constructing a fence.
        - `getBuffer` - Gets the buffer slice that holds the uniform for the current fog mode.
        - `setupFog` no longer takes in the `FogMode`, returning nothing
        - `$FogData#mode` is removed, replaced by `skyEnd`, `cloudEnd`
        - `$FogMode` enums have been replaced with `NONE` and `WORLD`, not one-to-one
    - `GameRenderer`
        - `getGlobalSettingsUniform` - Returns the uniform for the game settings.
        - `getLighting` - Gets the lighting renderer.
        - `setLevel` - Sets the current level the renderer is rendering.
    - `GlobalSettingsUniform` - An object fod handling the uniform holding the current game settings.
    - `LevelRenderer`
        - `endFrame` - Ends the current frame being rendered to by constructing a fence.
        - `renderLevel` now takes in a `GpuBufferSlice`, the fog vector, and whether the current position is foggy instead of the `GameRenderer`
    - `MappableRingBuffer` - An object that contains three buffers that are written to as required, then rotates to the next buffer to use on end. These are synced using fences.
    - `PanoramaRenderer#render` now takes in a boolean for whether to change the spin up the spin rather than two floats.
    - `PerspectiveProjectionMatrixBuffer` - An object that holds the projection matrix uniform.
    - `PostChain`
        - `load` now takes in the `CachedOrthoProjectionMatrixBuffer`
        - `addToFrame` no longer takes in the `Consumer<RenderPass>`
        - `process` no longer takes in the `Consumer<RenderPass>`
    - `PostChainConfig`
        - `$FixedSizedTarget` record is removed
        - `$FullScreenTarget` record is removed
        - `$InternalTarget` is now a record which can take in an optional width, height, whether the target is persistent, and the clear color to use
        - `$Pass#uniforms` is now a `Map<String, List<UniformValue>>`
        - `$Uniform` record is removed
    - `PostPass` is now `AutoCloseable`, taking in a `Map<String, List<UniformValue>>` for the uniforms along with a list of `PostPass$Input`s
        - `addToFrame` no longer takes in the `Consumer<RenderPass>`, with the `Matrix4f` passed in as a `GpuBufferSlice`
        - `$Input`
            - `bindTo` is removed
            - `texture` - Constructs a `GpuTexture` for the input given the map of resources.
            - `samplerName` - Returns the name of the sampler.
    - `SkyRenderer#renderSunMoonAndStars` no longer takes in the `FogParameters`
    - `UniformValue` - An interface that represents a uniform stored within an interface block.
- `net.minecraft.client.renderer.chunk.SectionRendererDispatcher$RenderSection#setDynamicTransformIndex`, `getDynamicTransformIndex` - Handles the index used to query the correct dynamic transforms for a given section.
- `net.minecraft.client.renderer.fog.environment`
    - `AirBasedFogEnvironment` - An environment whose color is derived from the air of the biome
    - `AtmosphericFogEnvironment` - The default fog environment if no other special cases match.
    - `BlindessFogEnvironment` - An environment that activates if the entity has the blindness effect.
    - `DarknessFogEnvironment` - An environment that activates if the entity has the darkness effect.
    - `DimensionOrBossFogEnvrionment` - An environment that activates based on if there are any bosses or the dimension special effects.
    - `FogEnvironment` - An abstract class that determines how the fog should render at a given location for an entity.
    - `LavaFogEnvironment` - An environment that activates if the entity is within lava.
    - `MobEffectFogEnvironment` - An environment that activates based on if the entity has a given mob effect.
    - `PowderedSnowFogEnvironment` - An environment that activates if the entity is within powdered snow.
    - `WaterFogEnvironment` - An environment that activates if the entity is within water.
- `net.minecraft.client.renderer.texture`
    - `AbstractTexture`
        - `setUseMipmaps` - Sets whether the texture should use mipmapping.
        - `textureView`, `getTextureView` - Represents the current texture view.
    - `CubeMapTexture` - A cubemap compatible texture, textures are expected to have suffixes `_0` to `_5`.
    - `ReloadableTexture#doLoad` is now protected
- `net.minecraft.world.level.material.FogType`
    - `DIMENSION_OR_BOSS` - Fog for dimension special effects or bosses.
    - `ATMOSPHERIC` - Default fog type.

## Tag Providers: Appender Rewrite

The `TagAppender` has been rewritten to a degree, changing the basic implementations of `TagsProvider`s.

By default, the `TagsProvider` no longer provides any useful methods for adding content to a `TagBuilder`. The most you can do is construct the builder using `TagsProvider#getOrCreateRawBuilder` and add elements or tags via their `ResourceLocation` using one of the available `add*` methods.

```java
// We will assume there is some TagKey<Item> EXAMPLE_TAG
public class ExampleTagsProvider extends TagsProvider<Item> {

    public ExampleTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, CompletableFuture<TagsProvider.TagLookup<Item>> parentProvider) {
        super(
            // The output location of the pack
            output,
            // The registry key to generate tags for
            Registries.ITEM,
            // The registry of registries
            registries,
            // Optional: A parent provider to use the generate tags of
            // Typically obtained from TagsProvider#contentsGetter
            parentProvider
        );
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        // Add tags here
        TagBuilder builder = this.getOrCreateRawBuilder(EXAMPLE_TAG);
        builder
            // Add single element
            .addElement(ResourceLocation.fromNamespaceAndPath("minecraft", "apple"))
            // Add tag to builder
            .addTag(ItemTags.WOOL.location());
    }
}
```

But what if we want to reference tags by their `ResourceKey`? Or registry object? This is where the rewritten `TagAppender` comes in. `TagAppender` is an interface with two generics, `E` which represents the type of the entry to add, and `T` which represents the type of the objects within the tag. A `TagAppender` has five common methods: three that add entries (`add`, `addAll`, `addOptional`), and two that add tags  (`addTag`, `addOptionalTag`). A `TagAppender` can be created via `forBuilder`, which accepts `ResourceKey`s for entries.

`KeyTagProvider` provides this for you by adding a `tag` method, creating the appender from a `TagKey`. Generally, datapack registry objects have their tags generated using this provider:

```java
// We will assume there is some TagKey<Item> EXAMPLE_TAG
public class ExampleTagsProvider extends KeyTagProvider<Item> {

    public ExampleTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(
            // The output location of the pack
            output,
            // The registry key to generate tags for
            Registries.ITEM,
            // The registry of registries
            registries
        );
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        // Add tags here
        TagAppender<ResourceKey<Item>, Item> builder = this.tag(EXAMPLE_TAG);
        builder
            // Add single element
            .add(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("minecraft", "apple")))
            // Add tag to builder
            .addTag(ItemTags.WOOL);
    }
}
```

`TagAppender`s can also be mapped to change their entry type using the `map` method. This takes in a function which maps the new entry type to the original entry type. `IntrinsicHolderTagsProvider` provides this for you via a `tag` method, creating the appender from a `TagKey` and mapping it using a key extractor. Generally, built-in registry objects have their tags generated using this provider:

```java
// We will assume there is some TagKey<Item> EXAMPLE_TAG
public class ExampleTagsProvider extends IntrinsicHolderTagsProvider<Item> {

    public ExampleTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(
            // The output location of the pack
            output,
            // The registry key to generate tags for
            Registries.ITEM,
            // The registry of registries
            registries,
            // Maps the registry object to its resource key
            item -> item.builtInRegistryHolder().key()
        );
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        // Add tags here
        TagAppender<Item, Item> builder = this.tag(EXAMPLE_TAG);
        builder
            // Add single element
            .add(Items.APPLE)
            // Add tag to builder
            .addTag(ItemTags.WOOL);
    }
}
```

### Copying Tags: Block and Item

Copying tags no longer exists in the traditional sense, where it loops through the elements of an existing tag. Instead, the implementation has been narrowed down to a general `BlockItemTagsProvider`. This method provides a `TagAppender<Block, Block>` by taking in both a block and an item tag, and then converting them appropriately within the `Vanilla*TagsProvider`. As such, it is less copying and more mapping blocks to their associated items.

The one drawback is that tags that are used within other tags (calling `addTag`) must have the same name. For example, adding `BlockTags#LOGS` only works because there is a `minecraft:logs` for both block and item tags. Meanwhile, adding `BlockTags#CEILING_HANGING_SIGNS` would fail as the associated item tag is `minecraft:hanging_signs` instead of `minecraft:ceiling_hanging_signs`.

```java
// We will assume there is some TagKey<Block> BLOCK_EXAMPLE_TAG
// We will assume there is some TagKey<Item> ITEM_EXAMPLE_TAG
public class ExampleBlockItemTagsProvider extends BlockItemTagsProvider {

    @Override
    public void run() {
        // Add block item tags here
        // Will add entries to block or item tag depending on the provider
        TagAppender<Block, Block> builder = this.tag(BLOCK_EXAMPLE_TAG, ITEM_EXAMPLE_TAG);
        builder
            // Add single element
            .add(Blocks.TERRACOTTA)
            // Add tag to builder
            // There exists both `minecraft:logs` in block and item tags
            .addTag(BlockTags.LOGS);
    }
}

// For some IntrinsicHolderTagsProvider<Item> or IntrinsicHolderTagsProvider<Block>
@Override
protected void addTags(HolderLookup.Provider registries) {
    // Add tags here
    new ExampleBlockItemTagsProvider() {
        // Or <Item, Item> depending on the situation
        protected TagAppender<Block, Block> tag(TagKey<Block> blockTag, TagKey<Item> itemTag) {
            // Return a TagAppender
            // See VanillaItemTagsProvider$BlockToItemConverter for an item example
            // See VanillaBlockTagsProvider for a block example
        }
    }.run();
}
```

- `net.minecraft.data.tags`
    - `BlockItemTagsProvider` - A provider that generates tags for block items, using the block and item tag equivalents as a starting point.
    - `IntrinsicHolderTagsProvider`
        - `tag` now returns the raw `TagAppender`
        - `$IntrinsicTagAppender` class is removed
    - `ItemTagsProvider` class is removed
    - `KeyTagProvider` - A provider which appends elements via their `ResourceKey`.
    - `TagsProvider`
        - `tag` is removed
        - `$TagAppender` -> `TagAppender`, not one-to-one
    - `VanillaItemTagsProvider` now implements `IntrinsicHolderTagsProvider`
        - `BlockToItemConverter` - A tag appender that adds a block to an item tag.

## Generic Encoding and Decoding: Replacing Direct NBT Access

Direct access to get data out of some NBT has been removed completely from higher level objects like entities and block entities. This means that, generally, you cannot directly touch the `CompoundTag` during the serialization process. Instead, indirect access is provided to nbt tags via `ValueInput` and `ValueOutput`. As their name implies, these read values from and write values to the data object, respectively. The methods available are similar to those on `CompoundTag`s. For `ValueInput`, there's the `get*` methods by providing the associated key, and `get*Or` for a get-or-default if not present. There is also `read` for handling `Codec`s. For `ValueOutput`, there's the `put*` methods by providing the associated key and value, and also a `store` for `Codec`s. List variants exists separately on the input/output access.

As such, most methods that take in a `CompoundTag` now instead take in a `ValueInput` for `read`/`load` or `ValueOutput` for `write`/`save`.

```java
// For some Entity with an ItemStack stack
@Override
protected void readAdditionalSaveData(ValueInput in) {
    super.readAdditionalSaveData(in);
    // By default, the input uses a registry ops
    this.stack = in.read("example_stack", ItemStack.CODEC).orElse(ItemStack.EMPTY);
}

@Override
protected void addAdditionalSaveData(ValueOutput out) {
    super.addAdditionalSaveData(out);
    // By default, the output uses a registry ops
    in.storeNullable("example_stack", ItemStack.CODEC, this.stack);
}

// For some BlockEntity with an int value
@Override
    protected void loadAdditional(ValueInput in) {
        super.loadAdditional(in);
        this.value = in.getIntOr("value", 0);
    }

    @Override
    protected void saveAdditional(ValueOutput out) {
        super.saveAdditional(out);
        out.putInt("value", this.value);
    }
```

### NBT Implementations

To provide indirect access to `CompoundTag`s, there are implementations of `ValueInput` and `ValueOutput` called `TagValueInput` and `TagValueOutput` respectively.

A `TagValueOutput` can be created using either `createWithContext` or `createWithoutContext`. 'With context' means that the output has access to the `HolderLookup$Provider` for registry objects while 'without context' does not. Currently, no location uses `createWithoutContext`. Internally, this creates a new `CompoundTag`. Then, this output is passed around to be populated before finally returning the NBT for writing via `buildResult`.

A `TagValueInput`, on the other hand, can be created via `create`, taking in the `HolderLookup$Provider` and the `CompoundTag` to read from. If the data is stored as a list of objects rather than a single object, you can pass in a `List<CompoundTag>` and get back a `ValueInputList`. Note this does not handle indicies, only providing an iterable or stream associated implementation.

### Problem Reporter

In addition to what's mentioned above, the `create*` methods also take in a `ProblemReporter`, used for collecting all problems encountered when attempting to serialize/deserialize the data. It is up to the implementation to determine whether this report causes a crash or gives a warning. A `ProblemReporter` contains two methods: `report`, which reports a `$Problem`; and `forChild`, which further groups `$Problem`s using a `$PathElement`. Both problems and path elements are simply interface objects that return a string indicating what the problem is or the grouping, respectively.

There are two common `ProblemReporter`s that are used: `$Collector`, which is typically used with data providers, and `$ScopedCollector`, which is used with disk objects (e.g., entities, block entities, chunks, etc.). A `$Collector` typiccally uses either `forEach` to report each problem one by one, `getReport`/`getTreeReport` to return a stringified problems, or `isEmpty` to list if there are any problems. A `$ScopedCollector` does the same, except this is `AutoCloseable`, in case nothing is done with the output. In these scenarios, the reports are written to a logger.

Each collector takes in an initial `$PathElement`. This typically comes from the object itself containing some method called `problemPath`, which implements the interface. The `HolderLookup$Provider` is provided in the same fashion.

```java
// For some object with HolderLookup.Provider registries
// There is also a Logger LOGGER

// Let's assume our root path element is implemented like so
public record ExamplePathElement(String name) implements ProblemReporter.PathElement {

    @Override
    public String get() {
        return "Example: " + this.name();
    }
}

// For a data provider
ProblemReporter.Collector problems = new ProblemReporter.Collector(
    // Can be empty for a non-specified root
    new ExamplePathElement("data_provider")
);
// Pass around the provider

// For a disk-based object
try (ProblemReporter.ScopedCollector problems = new ProblemReporter.ScopedCollector(new ExamplePathElement("EXAMPLE TEST"), LOGGER)) {
    TagValueOutput out = TagValueOutput.createWithContext(problems, this.registries);
    // Pass around the output to write data

    // For the input
    // The last parameter can be whatever CompoundTag, using the output as an example
    TagValueInput in = TagValueInput.create(problems, this.registries, out.buildResult());
    // Pass around the input to read data
}
```
- `net.minecraft.nbt.StringTag#escapeWithoutQuotes` - Creates a string that escapes control characters, quotations, apostrophes, and backslashes.
- `net.minecraft.server.level.ServerPlayer`
    - `loadAndSpawnParentVehicle` now takes in a `ValueInput`
    - `loadAndSpawnEnderPearls` now takes in a `ValueInput`
    - `loadGameTypes` now takes in a `ValueInput`
- `net.minecraft.server.players.PlayerList#load` takes in a `ProblemReporter`, returning an optional `ValueInput`
- `net.minecraft.util.ProblemReporter`
    - `DISCARDING` - A reporter which discards all reporters.
    - `forChild` now takes in a `$PathElement`
    - `report` now takes in a `$Problem`
    - `$Collector` now has a constructor that takes in the root `$PathElement`
        - `isEmpty` - Returns whether there are no reports.
        - `forEach` - Loops through all available problems.
        - `getReport` now returns a regular `String`
        - `getTreeReport` - Gets the report and all its children using DFS.
    - `$ElementReferencePathElement` - A path element that references some `ResourceKey`.
    - `$FieldPathElement` - A path element that references some string.
    - `$IndexedFieldPathElement` - A path element that references some string at an index.
    - `$IndexedPathElement` - A path element that references some index.
    - `$PathElement` - An interface that defines the grouping or element.
    - `$Problem` - An interface that defines a problem with an element.
    - `$RootElementPathElement` - A path element that references some `ResourceKey` as the root.
    - `$RootFieldPathElement` - A path element that references some string as the root.
    - `$ScopedCollector` - A collector that logs warnings when problems arise.
- `net.minecraft.world` 
    - `ContainerHelper`
        - `saveAllItems` now takes in a `ValueOutput` instead of a `CompoundTag`, and no longer takes in a `HolderLookup$Provider` while returning nothing
        - `loadAllItems` now takes in a `ValueInput` instead of a `CompoundTag`, and no longer takes in a `HolderLookup$Provider`
    - `LockCode#addToTag`, `fromTag` now takes in a `ValueOutput`/`ValueInput` instead of a `CompoundTag`, and no longer takes in a `HolderLookup$Provider`
    - `RandomziableContainer#tryLoadLootTable`, `trySaveLootTable` now takes in a `ValueOutput`/`ValueInput` instead of a `CompoundTag`
    - `SimpleContainer`
        - `fromTag` -> `fromItemList`, not one-to-one
        - `createTag` -> `storeAsItemList`, not one-to-one
- `net.minecraft.world.entity`  
    - `Entity`
        - `saveAsPassenger` now takes in a `ValueOutput` instead of a `CompoundTag`
        - `save` now takes in a `ValueOutput` instead of a `CompoundTag`
        - `saveWithoutId` now takes in a `ValueOutput` instead of a `CompoundTag`, returning nothing
        - `load` now takes in a `ValueInput` instead of a `CompoundTag`
        - `readAdditionalSaveData` now takes in a `ValueInput` instead of a `CompoundTag`
        - `addAdditionalSaveData` now takes in a `ValueOutput` instead of a `CompoundTag`
        - `problemPath` - Returns the path element to report problems from.
    - `EntityRenference`
        - `store` now takes in a `ValueOutput` instead of a `CompoundTag`
        - `read`, `readWithOldOwnerConversion` now take in a `ValueInput` instead of a `CompoundTag`
    - `Entity`
        - `UUID_TAG` -> `TAG_UUID`
        - `create`, `by` now take in a `ValueInput` instead of a `CompoundTag`
        - `loadEntityRecursive` now takes in a `ValueInput` instead of a `CompoundTag`
        - `loadEntitiesRecursive` now takes in a `ValueInput$ValueInputList` instead of a list of nbt tags
        - `loadStaticEntity` now takes in a `ValueInput` instead of a `CompoundTag`
    - `EntityReference#store` - Writes the reference data to the `ValueOutput`.
    - `Leashable#readLeashData`, `writeLeashData` now take in a `ValueInput`/`ValueOutput` instead of a `CompoundTag`
    - `LivingEntity#ATTRIBUTES_FIELD` -> `TAG_ATTRIBUTES`
    - `NeutralMob#addPersistentAngerSaveData`, `readPersistentAngerSaveData` now take in a `ValueOutput`/`ValueInput` instead of a `CompoundTag`
- `net.minecraft.world.entity.npc.InventoryCarrier#readInventoryFromTag`, `writeInventoryToTag` now take in a `ValueInput`/`ValueOutput` instead of a `CompoundTag`
- `net.minecraft.world.entity.player.Inventory`
    - `save` now takes in a `ValueOutput$TypedOutputList`, returning nothing
    - `load` now takes in a `ValueOutput$TypedInputList`
- `net.minecraft.world.entity.variant.VariantUtils`
    - `writeVariant` now takes in a `ValueOutput` instead of a `CompoundTag`
    - `readVariant` now takes in a `ValueInput` instead of a `CompoundTag`, and no longer takes in a `RegistryAccess`
- `net.minecraft.world.entity.vehicle.ContainerEntity#addChestVehicleSaveData`, `readChestVehicleSaveData` now take in a `ValueOutput`/`ValueInput` instead of a `CompoundTag`, and no longer takes in a `HolderLookup$Provider`
- `net.minecraft.world.inventory.PlayerEnderChestContainer`
    - `fromTag` -> `fromSlots`, not one-to-one
    - `createTag` -> `storeAsSlots`, not one-to-one
- `net.minecraft.world.item`
    - `BlockItem#setBlockEntityData` now takes in a `TagValueOutput` instead of a `CompoundTag`
    - `ItemStack#parse`, `save` are removed
- `net.minecraft.world.level`
    - `BaseCommandBlock#save`, `load` now take in a `ValueOutput`/`ValueInput` instead of a `CompoundTag`, and no longer takes in a `HolderLookup$Provider`, returning nothing
    - `BaseSpawner#save`, `load` now take in a `ValueOutput`/`ValueInput` instead of a `CompoundTag`, returning nothing
- `net.minecraft.world.level.block.SculkSpreader#save`, `load` now take in a `ValueOutput`/`ValueInput` instead of a `CompoundTag`
- `net.minecraft.world.level.block.entity.BlockEntity`
    - `load*` methods now take in a `ValueInput` instead of a `CompoundTag`, and no longer takes in a `HolderLookup$Provider`
    - `save*` methods now take in a `ValueOutput` instead of a `CompoundTag`, and no longer takes in a `HolderLookup$Provider`
    - `removeComponentsFromTag` now takes in a `ValueOutput` instead of a `CompoundTag`
    - `parseCustomNameSafe` now takes in a `ValueInput` and key instead of a tag and `HolderLookup$Provider`
    - `problemPath` - Returns the path element to report problems from.
- `net.minecraft.world.level.block.entity.trialspawner.TrialSpawner#load`, `store` - Handles writing the spawner data.
- `net.minecraft.world.level.chunk.ChunkAccess#problemPath` - Returns the path element to report problems from.
- `net.minecraft.world.level.storage`
    - `PlayerDataStorage#load` now returns a `ValueInput` instead of a `CompoundTag`, taking in a `ProblemReporter`
    - `TagValueInput` - A compound tag input.
    - `TagValueOutput` - A compound tag output.
    - `ValueInput` - An interface that defines how to read data from some object.
    - `ValueInputContextHelper` - A class that contains the context used to read object data.
    - `ValueOutput` - An interface that defines how to write data to some object.
- `net.minecraft.world.level.storage.loot.ValidationContext`
    - `forChild`, `enterElement` now takes in a `ProblemReporter$PathElement` instead of a `String`
    - `reportProblem` now takes in a `ProblemReporter$Problem` instead of a `String`
    - `$MissingReferenceProblem` - A problem where the referenced object is missing.
    - `$ParametersNotProvidedProblem` - A problem where the loot context params are missing.
    - `$RecursiveReferenceProblem` - A problem where the referenced object is referencing itself.
    - `$ReferenceNotAllowedProblem` - A problem where the referenced object is not allowed to be referenced.
- `net.minecraft.world.level.storage.loot.entries`
    - `AlternativesEntry#UNREACHABLE_PROBLEM` - A problem where the altnerative entry can never be executed.
    - `CompositeEntryBase#NO_CHILDREN_PROBLEM` - A problem where the composite has no entries.
    - `NestedLootTable#INLINE_LOOT_TABLE_PATH_ELEMENT` - An element which indicates that a table is inlined.

## Server Player Changes

`MinecraftServer` is no longer publicly exposed on the `ServerPlayer`. Additionally, `serverLevel` has been removed, now replaced with overloading `level` to return the `ServerLevel`.

- `net.minecraft.server.level.ServerPlayer`
    - `server` field is now private
    - `serverLevel` -> `level`, still returns `ServerLevel`

## Minor Migrations

The following is a list of useful or interesting additions, changes, and removals that do not deserve their own section in the primer.

### Leashes

The leash system has been updated to support up to four on enabled entities. Additionally, the physics have been updated to more properly handle real-world elasicity of some stretchable object.

- `net.minecraft.client.renderer.entity.state.EntityRenderState`
    - `leashState` now returns a list of `$LeashState`s
    - `$LeashState#slack` - Whether the leash has any slack.
- `net.minecraft.world.entity`
    - `Entity`
        - `shearOffAllLeashConnections` - Handles when shears are used to removed a leash.
        - `dropAllLeashConnections` - Handles when all leashes should be removed from an entity.
        - `getQuadLeashHolderOffsets` - Gets the offsets when four leashes attached to an entity.
        - `supportQuadLeashAsHolder` - Returns whether the entity can be leashed up to four times.
        - `notifyLeashHolder`, `notifyLeasheeRemoved` - Handles when the leash is ticked on an entity and when its removed.
        - `setLeashOffset` is removed
        - `getLeashOffset` -> `Leashable#getLeashOffset`
    - `Leashable`
        - `MAXIMUM_ALLOWED_LEASHED_DIST` - The maximum distance a leash can be attached to an entity.
        - `AXIS_SPECIFIC_ELASTICITY` - The resistance of the leash along each axis.
        - `SPRING_DAMPENING` - The loss of energy when the leash is oscillating on stretch.
        - `TORSIONAL_ELASTICITY` - The resistance of the leash under torque.
        - `STIFFNESS` - The stiffness of the leash.
        - `ENTITY_ATTACHMENT_POINT` - Specifies the attachment point of the leash on the entity.
        - `LEASHER_ATTACHMENT_POINT` - Specifies the attachment point of the leash on the holder.
        - `SHARED_QUAD_ATTACHMENT_POINTS` - Specifies the attachment points of the leashes on an entity.
        - `canHaveALeashAttachedToIt` -> `canHaveALeashAttachedTo`, not one-to-one
        - `leashDistanceTo` - Returns the distance of the leash traveled between the holder and leashee.
        - `onElasticLeashPull` - Handles when the leash is being pulled upon.
        - `leashSnapDistance` - Returns when the leash will attempt to remove itself from the entity.
        - `leashElasticDistance` - Returns the max distance before the elasticity of the leash becomes a physics factor.
        - `handleLeashAtDistance` is removed
        - `angularFriction` - Returns the angular friction of the entity on a block or within a liquid.
        - `whenLeashedTo` - Notifies the entity that the leash is attached.
        - `elasticRangeLeashBehaviour`, `legacyElasticRangeLeashBehaviour` -> `checkElasticInteractions`, not one-to-one
        - `supportQuadLeash` - Whether the entity can be leashed up to four times.
        - `getQuadLeashOffsets` - Returns the offsets of each leash attached to the entity.
        - `createQuadLeashOffsets` - Creates the offsets for each of the four possible leash positions.
        - `leashableLeashedTo` - Gets all entities within a 32-block radius that are leashed to this holder.
        - `$LeashData#angularMomentum` - Returns the angular momentum of the entity when leashed.
        - `$Wrench` - A record which handles the force and torque applied to the leash.
- `net.minecraft.world.item.LeadItem#leashableInArea` -> `Leashable#leashableInArea`

### Removal of Mob Effects Atlas

The mob effect atlas has been removed and merged with the gui altas.

- `net.minecraft.client.Minecraft#getMobEffectTextures` is removed
- `net.minecraft.client.gui.Gui#getMobEffectSprite` - Gets the location of the mob effect sprite.
- `net.minecraft.client.resources.MobEffectTextureManage` class is removed
- `AtlasIds#MOB_EFFECTS` is removed

### Permission Sources

The permission checks for commands have been abstracted into its own `PermissionSource` interface. This provides the previously provided `hasPermission` method, in addition to a new method `allowsSelectors`, which returns whether the source has the necessary permission to select other entities (defaults to level 2 perms). You can incoporate the permission check into your commands by calling `Commands#hasPermission` with the desired level in `ArgumentBuilder#requires`.

- `net.minecraft.client.multiplayer`
    - `ClientPacketListener`
        - `getCommands` now returns a `ClientSuggestionProvider` generic
        - `sendUnattendedCommand` now takes in a `Screen` instead of a `boolean`
    - `ClientSuggestionListener` now implements `PermissionSource`, taking in a boolean of whether it allows restricted commands
        - `allowRestrictedCommands` - Returns whether restricted commands can be suggested.
- `net.minecraft.commands`
    - `Commands#hasPermission` - Returns a permission check for the given level.
    - `CommandSourceStack` now implements `PermissionSource`
    - `ExecutionCommandSource` now implements `PermissionSource`
    - `PermissionSource` - Returns the source of the permission to run a command.
    - `SharedSuggestionProvider`
        - `suggestResgitryElements` now takes in a `HolderLookup` instead of a `Registry`
        - `listSuggestions` - Lists the suggestion for some registry elements.
        - `hasPermission` is removed
- `net.minecraft.commands.synchronization.SuggestionProviders`
    - `AVAILABLE_SOUNDS`, `SUMMONABLE_ENTITIES` now take in a `SharedSuggestionProvider` for their generic
    - `cast` - Casts the suggestion provider to the correct type.
    - `safelySwap` is removed
    - `$Wrapper` -> `$RegisteredSuggestion`
- `net.minecraft.world.entity.Entity#getCommandSenderWorld` is removed

### Animation Baking

Animations are now baked into `KeyframeAnimation`. Each `KeyframeAnimation` is made up of entries that apply the keyframes to a given `ModelPart`. To create an animation, the definition should be baked via `AnimationDefinition#bake` in the model constructor, then calling `#apply` or `#applyWalk` as required during `EntityModel#setupAnim`.

```java
// For some entity model
// Assume some AnimationDefinition EXAMPLE_DEFN
// Assume our ExampleEntityState has some AnimationState exampleAnimState
public class ExampleModel extends EntityModel<ExampleEntityState> {

    private final KeyframeAnimation exampleAnim;

    public ExampleModel(ModelPart root) {
        // We pass in whatever 'root' that can apply all animations
        this.exampleAnim = EXAMPLE_DEFN.bake(root);
    }

    @Override
    public void setupAnim(ExampleEntityState state) {
        super.setupAnim(state);
        this.exampleAnim.apply(state.exampleAnimState, state.ageInTicks);
    }
}
```

- `net.minecraft.client.animation`
    - `AnimationDefinition#bake` - Bakes a defined animation to be used on a `Model`.
    - `KeyframeAnimation` - A baked animation used to move `ModelPart`s on a given `Model`.
    - `KeyframeAnimations#animate` -> `KeyframeAnimation$Entry#apply`
- `net.minecraft.client.model.Model`
    - `getAnyDescendantWithName` is removed
    - `animate` -> `KeyframeAnimation#apply`
    - `animateWalk` - `KeyframeAnimation#applyWalk`
    - `applyStatic` -> `KeyframeAnimation#applyStatic`
- `net.minecraft.client.model.geom.ModelPart`
    - `getAllParts` now returns a `List`
    - `createPartLookup` - Creates a lookup of part names to their `ModelPart`, any duplicate names are ignored.

### ChunkSectionLayers

`RenderType`s used for defining how a block or fluid should render are now replaced with `ChunkSectionLayer`s. These functionally do the same thing as the `RenderType`; however, they only specify the `RenderPipeline` to use along with the buffer information. This also means that certain `RenderType`s are removed, like `TRANSLUCENT`, as they were only used for the block chunk rendering.

This also means that adding to `ItemBlockRenderTypes#TYPE_BY_BLOCK` must specify the `ChunkSectionLayer` instead of the associated `RenderType`.

- `net.minecraft.client.renderer`
    - `ItemBlockRenderTypes`
        - `getChunkRenderType` now returns a `ChunkSectionLayer`
        - `getRenderLayer(FluidState)` now returns a `ChunkSectionLayer`
    - `RenderType`
        - `translucent` is removed
        - `getRenderTarget`, `getRenderPipeline` is removed
        - `chunkBufferLayers` is removed
- `net.minecraft.client.renderer.chunk`
    - `ChunkSectionLayer` - An enum that defines how an individual chunk layer (e.g., solid blocks, translucent blocks) is rendered.
    - `ChunkSectionLayerGroup` - An enum that groups the layers for rendering.
    - `ChunkSectionsToRender` - A record that contains the draws of a given chunk, allowing them to be rendered per layer group.
    - `RenderChunk` -> `SectionCopy`
    - `RenderChunkRegion` -> `RenderSectionRegion`
    - `RenderRegionCache#createRegion` now takes in a `long` instead of a `SectionPos`
    - `SectionCompiler$Results`
        - `globalBlockEntities` -> - `net.minecraft.client.multiplayer.ClientLevel#getGloballyRenderedBlockEntities`
        - `renderedLayers` now takes in a `ChunkSectionLayer` for the key
    - `SectionMesh` - An interface that defines the mesh of a given section within a chunk
    - `SectionRenderDispatcher`
        - `getBatchToCount` -> `getCompileQueueSize`
        - `setCamera`, `getCameraPosition` are removed
        - `blockUntilClear` is removed
        - `clearBatchQueue` -> `clearCompileQueue`, now public
        - `$CompiledSection` -> `CompiledSectionMesh`
        - `$RenderSection`
            - `getBuffers` is removed
            - `uploadSectionLayer(RenderType, MeshData)` -> `upload(Map, CompiledSectionMesh)`, not one-to-one
            - `uploadSectionIndexBuffer` now takes in a `CompiledSectionMesh` and a `ChunkSectionLayer` instead of a `RenderType`
            - `getDistToPlayerSqr` is removed
            - `getCompiled` -> `getSectionMesh`, not one-to-one
            - `rebuildSectionAsync` no longer takes in the `SectionRenderDispatcher`
            - `setDynamicTransformIndex`, `getDynamicTransformIndex` are removed
        - `$SectionBuffers` -> `SectionBuffers`
        - `$TranslucencyPointOfView` -> `TranslucencyPointOfView`
- `net.minecraft.server.level.ChunkMap#getUpdatingChunkIfPresent` is now public
- `net.minecraft.world.level.TicketStorage`
    - `purgeStaleTickets` now takes in the `ChunkMap`
    - `removeTicketIf` now takes in a `BiPredicate` instead of a `Predicate`, taking in the chunk position and the ticket
- `net.minecraft.world.level.chunk.ChunkAccess#isSectionEmpty` is removed

### Tag Changes

- `minecraft:block`
    - `plays_ambient_desert_block_sounds` is split into `triggers_ambient_desert_sand_block_sounds`, `triggers_ambient_desert_dry_vegetation_block_sounds`
    - `happy_ghast_avoids`
    - `triggers_ambient_dried_ghast_block_sounds`
- `minecraft:dialog`
    - `pause_screen_additions`
    - `quick_actions`
- `minecraft:entity_type`
    - `can_equip_harness`
    - `followable_friendly_mobs`
- `minecraft:item`
    - `harnesses`
    - `happy_ghast_food`
    - `happy_ghast_tempt_items`

### List of Additions

- `com.mojang.blaze3d.pipeline`
    - `BlendFunction#TRANSLUCENT_PREMULTIPLIED_ALPHA` - A blend function that assumes the target has a premultiplied alpha from the composite step.
    - `RenderPipeline`
        - `getSortKey` - Returns a value representing how the element should be sorted for rendering. Used for layer sorting.
        - `updateSortKeySeed` - Updates the seed of the sort key, currently unused.
- `com.mojang.blaze3d.systems.RenderSystem`
    - `outputColorTextureOverride` - Holds a texture containing the override color used instead of whatever is specified in the `RenderType` target.
    - `outputDepthTextureOverride` - Holds a texture containing the override depth used instead of whatever is specified in the `RenderType` target.
- `com.mojang.blaze3d.textures.GpuTexture#setUseMipmaps` - Sets whether the texture should use mipmaps at different distances.
- `net.minecraft`
    - `FileUtil#isPathPartPortable` - Returns whether the provided string does not match any of the windows reserved filenames.
    - `WorldVersion$Simple` - A simple implementation of the current world version.
- `net.minecraft.advancements.critereon`
    - `ItemUsedOnLocationTrigger$TriggerInstance#placedBlockWithProperties` - Creates a trigger where a block was placed with the specified property.
    - `PlayerInteractTrigger$TriggerInstance#equipmentSheared` - Creates a criterion trigger that actus upon a player taking off an item on some entity.
- `net.minecraft.client`
    - `GameNarrator#saySystemChatQueued` - Narrates a component if either system or chat message narration is enabled.
    - `Minecraft#disconnectWithSavingScreen` - Disconnects the current client instance and shows the 'Saving Level' screen.
    - `Options`
        - `keyQuickActions` - A key mapping for showing the quick actions dialog.
        - `cloudRange` - Returns the maximum distance clouds can render at.
        - `musicFrequency` - Returns how frequency the background music handled by the `MusicManager` should play.
        - `showNowPlayingToast` - Returns whether the 'Now Playing' toast is shown.
        - `getFinalSoundSourceVolume` - Computes the volume for the given sound source, with non-master sources being scaled by the master source.
    - `NarratorStatus#shouldNarrateSystemOrChat` - Returns whether the current narration status is anything but `OFF`.
- `net.minecraft.client.color.ColorLerper` - A utility class for lerping between color types based on some partial tick.
- `net.minecraft.client.data.models.BlockModelGenerators#createDriedGhastBlock` - Creates the dired ghast block model definition.
- `net.minecraft.client.data.models.model`
    - `ModelTemplates#DRIED_GHAST` - A template that uses the `minecraft:block/dried_ghast` parent.
    - `TextureMapping#driedGhast` - Creates the default texture mapping for the dried ghast model.
    - `TextureSlot#TENTACLES` - Provides a texture key `tentacles`.
- `net.minecraft.client.model`
    - `GhastModel#animateTentacles` - Animates the tentacles of a ghast.
    - `HappyGhastHarnessModel` - A model representing the a ghast harness.
    - `HappyGhastModel` - A model representing a 'tamed' ghast.
    - `QuadrupedModel#createBodyMesh` now takes in two booleans for handling if the left and right hind leg textures are mirrored, respectively.
- `net.minecraft.client.multiplayer.ClientLevel`
    - `DEFAULT_QUIT_MESSAGE` - The component holding the quit game text.
    - `disconnect(Copmonent)` - Disconnects from the current level instance, showing the associated component.
- `net.minecraft.client.renderer.entity.HappyGhastRenderer` - The renderer for a 'tamed' ghast.
- `net.minecraft.client.renderer.entity.layers.RopesLayer` - The render layer for the ropes used on a 'tamed' ghast.
- `net.mienecraft.client.renderer.entity.state.HappyGhastRenderState` - The state of a 'tamed' ghast.
- `net.minecraft.client.resources.model.EquipmentclientInfo$LayerType#HAPPY_GHAST_BODY` - A layer representing the body of a happy ghast.
- `net.minecraft.client.resources.sounds.RidingHappyGhastSoundInstance` - A tickable sound instance that plays when riding a happy ghast.
- `net.minecraft.client.sounds`
    - `MusicManager`
        - `getCurrentMusicTranslationKey` - Returns the translation key of the currently playing music.
        - `setMinutesBetweenSongs` - Sets the frequency between the background tracks.
        - `showNowPlayingToastIfNeeded` - Shows the now playing toast if it needs to be seen.
        - `$MusicFrequency` - The frequency of the background tracks being played.
    - `SoundEngine$PlayResult` - The starting state of the sound trying to be played.
- `net.minecraft.commands.arguments`
    - `HexColorArgument` - An integer argument that takes in a hexadecimal color.
    - `ResourceOrIdArgument`
        - `createGrammar` - Creates the grammar used to parse the argument.
        - `$InlineResult` - A result that returns a direct holder.
        - `$ReferenceResult` - A result that returns a reference holder.
        - `$Result` - An interface that represents the result of some argument parsing.
- `net.minecraft.data.loot.LootTableProvider$MissingTableProblem` - A record that holds the key of some missing built-in table generator.
- `net.minecraft.data.recipes.RecipeProvider`
    - `dryGhast` - The recipe for a dried ghast.
    - `harness` - The recipe for a colored harness.
- `net.minecraft.gametest.framework.GameTestTicker#startTicking` - Starts ticking the runner for the game tests.
- `net.minecraft.nbt.NbtUtils`
    - `addCurrentDataVersion`, `addDataVersion`, adds the data version to some nbt tag.
- `net.minecraft.network.FriendlyByteBuf#writeEither`, `readEither` - Handles an `Either` with the given stream encoders/decoders.
- `net.minecraft.network.codec.ByteBufCodecs`
    - `RGB_COLOR` - A stream codec that writes the RGB using three bytes.
    - `lenientJson` - Creates a stream codec that parses a json in lenient mode.
    - `optionalTagCodec` - Creates a stream codec that parses an `Optional`-wrapped `Tag` using the supplied `NbtAccounter`.
- `net.minecraft.network.protocol.game`
    - `ServerboundChangeGameModePacket` - Changes the current gamemode.
    - `ServerboundCustomClickActionPacket` - Executes a custom action on the server, currently does nothing.
- `net.minecraft.server.MinecraftServer#handleCustomClickAction` - Handles a custom action sent from a click event.
- `net.minecraft.server.level.ServerLevel`
    - `updateNeighboursOnBlockSet` - Updates the neighbors of the current position. If the blocks are not the same (not including their properties), then `BlockState#affectNeighborsAfterRemoval` is called.
    - `waitForChunkAndEntities` - Adds a task that causes the server to wait until entities are loaded in the provided chunk range.
- `net.minecraft.sources.SoundSource#UI` - Sounds that come from some user interface.
- `net.minecraft.stats`
    - `RecipeBookSettings#MAP_CODEC`
    - `ServerRecipeBook#pack`, `loadUntrusted`, `$Packed` - Handles encoding and decoding the data of the recipe book.
- `net.minecraft.util`
    - `ARGB`
        - `setBrightness` - Returns the brightness of some color using a float between 0 and 1.
        - `color` - Returns a ARGB color from a float red and integer alpha.
    - `ExtraCodecs`
        - `VECTOR2F`
        - `VECTOR3I`
        - `NBT`
    - `LenientJsonParser` - A json parser using lenient rules.
    - `Mth#smallestSquareSide` - Takes the ceiled square root of a number.
    - `StrictJsonParser` - A json parser using strict rules.
- `net.minecraft.world`
    - `Difficulty#STREAM_CODEC`
    - `ItemStackWithSlot` - A record which holds a stack along with its slot index.
- `net.minecraft.world.entity`
    - `Entity`
        - `MAX_MOVEMENTS_HANDELED_PER_TICK` - The maximum number of movements that can be applied to an entity in a given tick.
        - `isInClouds` - Returns whether the entity's Y position is between the cloud height and four units above.
        - `teleportSpectators` - Teleports the spectators currently viewing from the player's perspective.
        - `isFlyingVehicle` - Returns whether the vehicle can fly.
        - `clearMovementThisTick` - Clears all movement the entity will make this tick.
    - `EntityAttachments#getAverage` - Returns the average location of all attachment points.
    - `ExperienceOrb`
        - `awardWithDirection` - Adds an experience orb that moves via the specified vector.
        - `unstuckIfPossible` - Attempts to find and move the orb to a free position.
    - `Mob` 
        - `isWithinHome` - Returns whether the position is within the entity's restriction radius.
        - `canShearEquipment` - Returns whether the current player can shear the equipment off of this mob.
- `net.minecraft.world.entity.ai.control.MoveControl#setWait` - Sets the operation to `WAIT`.
- `net.minecraft.world.entity.ai.goal.TemptGoal`
    - `stopNavigation`, `navigateTowards` - Handles navigation towards the player.
    - `$ForNonPathfinders` - A tempt goal that navigates towards a wanted position rather than immediately pathfinding.
- `net.minecraft.world.entity.ai.navigation.PathNavigation#canNavigateGround` - Returns whether the entity can pathfind while on the ground.
- `net.minecraft.world.entity.ai.sensing.AdultSensorAnyType` - An adult sensor that ignores whether the entity is the same type as the child.
- `net.minecraft.world.entity.animal`
    - `HappyGhast` - An entity representing a happy ghast.
    - `HappyGhastAi` - The brain of the happy ghast.
- `net.minecraft.world.entity.decoration.ArmorStand`
    - `setArmorStandPose`, `getArmorStandPose`, `$ArmorStandPose` - Handles the pose of the armor stand.
- `net.minecraft.world.entity.monster.Ghast`
    - `faceMovementDirection` - Rotates the entity to face its current movement direction.
    - `$RandomFloatAroundGoal#getSuitableFlyToPosition` - Gets a position that the ghast should fly to.
- `net.minecraft.world.entity.player.Inventory#SLOT_BODY_ARMOR`, `SLOT_SADDLE` - The indicies for the corresponding slot.
- `net.minecraft.world.entity.projectile.ProjectileUtil#computeMargin` - Computes the bounding box margin to check for a given entity based on its tick count.
- `net.minecraft.world.item.component`
    - `ItemAttributeModifiers`
        - `forEach` - Applies the consumer to all attributes within the slot group.
        - `$Builder#add` - Adds an attribute to apply for a given slot group with a display.
        - `$Display` - Defines how an attribute modifier should be displayed within its tooltip.
        - `$Default` - Shows the default attribute display.
        - `$Hidden` - Does not show any attribute info.
        - `$OverrideText` - Overrides the attribute text with the component provided.
    - `Equippable$Builder`
        - `setCanBeSheared` - Sets whether the equipment can be sheared off the entity.
        - `setShearingSound` - Sets the sound to play when a piece of equipment is sheared off an entity.
    - `ResolvableProfile#pollResolve` - Returns the profile of the stored id or name.
- `net.minecraft.world.item.equipment.Equippable#harness` - Represents a harness to equip.
- `net.minecraft.world.level`
    - `CollisionGetter`
        - `getPreMoveCollisions` - Returns an iterable of shapes containing the entity and block collisions at the given bounding box and futue movement direction.
        - `getBlockCollisionsFromContext` - Gets the block shapes from the given collision context.
    - `GameType#STREAM_CODEC`
    - `Level`
        - `precipitationAt` - Returns the precipitation at a given position.
        - `onBlockEntityAdded` - Logic to run when a block entity is added to the level.
- `net.minecraft.world.level.block`
    - `BaseRailBlock#rotate` - Rotates the current rail shape in the associated direction.
    - `DriedGhastBlock` - A block that represents a dried ghast.
- `net.minecraft.world.level.block.entity.trialspawner.TrialSpawner$FullConfig` - Represents the entire configuration of a trial.
- `net.minecraft.world.level.dimension.DimensionDefaults`
    - `CLOUD_THICKNESS` - The block thickness of the clouds.
    - `OVERWORLD_CLOUD_HEIGHT` - The cloud height level in the overworld.
- `net.minecraft.world.level.levelgen.flat.FlatLayerInfo#heightLimited` - Returns a new layer info whether the current height is limited to the specified value, as long as that value is not within the maximum range already.
- `net.minecraft.world.phys`
    - `AABB`
        - `intersects` - Returns whether the `BlockPos` intersects with this box.
        - `distanceToSqr` - Returns the squared distance of the bounding boxes from their furthest point.
    - `Vec3#rotateClockwise90` - Rotates the vector 90 degrees clockwise (flip x and z and invert new x value).
- `net.minecraft.world.phys.shapes.CollisionContext`
    - `withPosition` - Returns the collision context of an entity with its bottom y position.

### List of Changes

- `com.mojang.blaze3d.platform.Window#setGuiScale`, `getGuiScale` now deals with an `int` instead of a `double`
- `net.mineraft`
    - `DetectedVersion` no longer implements `WorldVersion`
    - `WorldVersion` methods now use record naming schema due to `WorldVersion$Simple` usage
        - `getDataVersion` -> `dataVersion`
        - `getId` -> `id`
        - `getName` -> `name`
        - `getProtocolVersion` -> `protocolVersion`
        - `getPackVersion` -> `packVersion`
        - `getBuildTime` -> `buildTime`
        - `isStable` -> `stable`
- `net.minecraft.client`
    - `GameNarrator`
        - `sayChat` -> `sayChatQueued`
        - `say` -> `saySystemQueued`
        - `sayNow` -> `saySystemNow`
    - `Minecraft`
        - `grabPanoramixScreenshot` no longer takes in the window width and height to set
        - `disconnect()` -> `disconnectWithProgressScreen`
    - `Screenshot#grab`, `takeScreenshot` now takes in an `int` representing the downscale factor
- `net.minecraft.client.main.GameConfig$QuickPlayData` now takes in a `$QuickPlayVariant`
    - `path` -> `logPath`
    - `singleplayer` -> `variant` with `$QuickPlaySinglePlayerData`
    - `multiplayer` -> `variant` with `$QuickPlayMultiplayerData`
    - `realms` -> `variant` with `$QuickPlayRealmsData`
    - null for `singleplayer`, `multiplayer`, `realms` is represented by `variant` with `$QuickPlayDisabled`
- `net.minecraft.client.data.models.ItemModelGenerators#generateWolfArmor` -> `generateTwoLayerDyedItem`
- `net.minecraft.client.gui.components.DebugScreenOverlay#render3dCrosshair` now takes in the current `Camera`
- `net.minecraft.client.gui.components.debugchart.ProfilerPieChart`
    - `RADIUS` is now public
    - `CHART_Z_OFFSET` -> `PIE_CHART_THICKNESS`, now public
- `net.minecraft.client.multiplayer`
    - `ClientLevel$ClientLevelData#getClearColorScale` -> `voidDarknessOnsetRange`, not one-to-one
    - `MultiPlayerGameMode#createPlayer` now takes in an `Input` instead of a `boolean`
- `net.minecraft.client.player.LocalPlayer` now takes in the last sent `Input` instead of a `boolean` for the shift key
    - `getLastSentInput` - Gets the last sent input from the server.
- `net.minecraft.client.quickplay.QuickPlay#connect` now takes in a `GameConfig$QuickPlayVariant` instead of a `GameConfig$QuickPlayData`
- `net.minecraft.client.renderer`
    - `DimensionSpecialEffects` no longer takes in the current cloud level and whether there is a ground
    - `LightTexture#getTarget` -> `getTexture`
- `net.minecraft.client.renderer.blockentity.BlockEntityRenderer#shouldRenderOffscreen` no longer takes in the `BlockEntity`
- `net.minecraft.client.resources`
    - `AbstractSoundInstance#sound` is now `Nullable`
    - `SoundInstance#getSound` is now `Nullable`
- `net.minecraft.client.sounds`
    - `SimpleSoundInstance#forMusic` now also takes in the `float` volume
    - `SoundEngine` now takes in the `MusicManager`
        - `pause` -> `pauseAllExcept`, not one-to-one
        - `play` now returns a `$PlayResult`
    - `SoundManager` now takes in the `MusicManager`
        - `pause` -> `pauseAllExcept`, not one-to-one
        - `play` now returns a `SoundEngine$PlayResult`
- `net.minecraft.commands.arguments`
    - `ResourceOrIdArgument` now takes in an arbitrary codec rather than a `Holder`-wrapped value
        - `ERROR_INVALID` -> `ERROR_NO_SUCH_ELEMENT`, now public, not one-to-one
        - `VALUE_PARSER` -> `OPS`, now public, not one-toe
    - `ResourceSelectorArgument#getSelectedResources` no longer takes in the `ResourceKey`
- `net.minecraft.commands.functions.StringTemplate`
    - `fromString` no longer takes in the line number
    - `isValidVariableName` is now public
- `net.minecraft.data.recipes.RecipeProvider#colorBlockWithDye` -> `colorItemWithDye`, now takes in the `RecipeCategory`
- `net.minecraft.gametest.framework.GameTestInfo#prepareTestStructure` is now nullable
- `net.minecraft.network`
    - `Connection#send` now takes in a `ChannelFutureListener` instead of a `PacketSendListener`
    - `FriendlyByteBuf#readJsonWithCodec` -> `readLenientJsonWithCodec`
    - `PacketSendListener` is now a class whose methods return `ChannelFutureListener`s instead of `PacketSendListener`s
        - `onSuccess`, `onFailure` are removed
- `net.minecraft.network.codec`
    - `ByteBufCodecs#fromCodec` now has an overload that takes in some ops and a codec
    - `StreamCodec#composite` now has an overload that takes in ten parameters
- `net.minecraft.network.protocol.login.ClientboundLoginDisconnectPacket` is now a record
- `net.minecraft.network.protocol.game`
    - `ClientboundChangeDifficultyPacket` is now a record
    - `ClientboundCommandsPacket` now takes in a `$NodeInspector`
        - `getRoot` is now generic, taking in a `$NodeBuilder`
        - `$NodeBuilder` - A builder for a given command.
        - `$NodeInspector` - An agent that checks the information of a given command node.
    - `ServerboundChangeDifficultyPacket` is now a record
- `net.minecraft.server.ReloadableServerRegistries$Holder#lookup` returns a `HolderLookup$Provider`
- `net.minecraft.server.network.ServerCommonPacketListenerImpl#send` now takes in a `ChannelFutureListener` instead of a `PacketSendListener`
- `net.minecraft.sounds.Music` is now a record
- `net.minecraft.stats.RecipeBookSettings`
    - `getSettings` is now public
    - `$TypeSettings` is now public
- `net.minecraft.world.entity`
    - `AreaEffectCloud#setParticle` -> `setCustomParticle`
    - `Entity`
        - `checkSlowFallDistance` -> `checkFallDistanceAccumulation`
        - `collidedWithFluid`, `collidedWithShapeMovingFrom` are now public
        - `canBeCollidedWith` now takes the entity its colliding with
        - `spawnAtLocation` now has an overload that takes in a `Vec3` for the offset position
        - `removeLatestMovementRecordingBatch` -> `removeLatestMovementRecording`
    - `EntityReference` is now final
    - `ExperienceOrb` now has an overload that takes in two vectors for the position and movement
    - `FlyingMob` is replaced by calling `LivingEntity#travelFlying`
    - `LivingEntity#canBreatheUnderwater` is no longer `final`
    - `Mob`
        - `restrictTo` -> `setHomeTo`
        - `getRestrictCenter` -> `getHomePosition`
        - `getRestrictRadius` -> `getHomeRadius`
        - `clearRestriction` -> `clearHome`
        - `hasRestriction` -> `hasHome`
- `net.minecraft.world.entity.ai.attributes`
    - `AttributeInstance`
        - `save` -> `pack`, `$Packed`; not one-to-one
        - `load` -> `apply`, not one-to-one
    - `AttributeMap`
        - `save` -> `pack`; not one-to-one
        - `load` -> `apply`, not one-to-one
- `net.minecraft.world.entity.ai.behavior`
    - `AnimalPanic` now has overloads that take in a radius or a position getter
    - `BabyFollowAdult#create` now returns a `OneShot<LivingEntity>` and can take in a `boolean` of whether to target the eye position
    - `EntityTracker` can now take in a `boolean` of whether to target the eye position
    - `FollowTemptation` now has an overload that checks whether the entity needs to track the entity's eye height.
- `net.minecraft.world.entity.ai.goal.TemptGoal` now has an overload that takes in the stop distance
    - `mob` is now a `Mob`
    - `speedModifier` is now `protected`
- `net.minecraft.world.entity.ai.memory.MemoryModuleType#NEAREST_VISIBLE_ADULT` now holds a `LivingEntity`
- `net.minecraft.world.entity.ai.navigation`
    - `FlyingPathNavigation`, `GroundPathNavigation#setCanOpenDoors` -> `PathNavigation#setCanOpenDoors`
- `net.minecraft.world.entity.ai.sensing.AdultSensor` now looks for a `LivingEntity`
    - `setNearestVisibleAdult` is now `protected`
- `net.minecraft.world.entity.animal.*Variants#selectVariantToSpawn` -> `entity.variant.VariantUtils#selectVariantToSpawn`, not one-to-one
- `net.minecraft.world.entity.animal.Fox#isJumping` -> `LivingEntity#isJumping`
- `net.minecraft.world.entity.animal.horse.AbstractHorse`
    - `isJumping` -> `LivingEntity#isJumping`
    - `setStanding` now takes in an `int` instead of a `boolean` for the stand counter
        - the `false` logic is moved to `clearStanding`
- `net.minecraft.world.entity.monster`
    - `Ghast` now implements `Mob`
        - `$GhastLookGoal` is now public, taking in a `Mob`
        - `$GhastMoveControl` is now public, taking in whether it should be careful when moving and a supplied boolean of whether the ghast should stop moving
        - `$RandomFloatAroundGoal` is now `public`, taking in a `Mob` and a block distance
    - `Phantom` now implements `Mob`
- `net.minecraft.world.entity.player`
    - `Abilities`
        - `addSaveData` -> `pack`, `$Packed`; not one-to-one
        - `loadSaveData` -> `apply`, not one-to-one
    - `Player` no longer takes in the `BlockPos` and y rotation
- `net.minecraft.world.entity.projectile`
    - `AbstractThrownPotion#onHitAsPostion` now takes in a `HitResult` instead of a nullable `Entity`
    - `EyeOfEnder#signalTo` now takes in a `Vec3` instead of a `BlockPos`
    - `Projectile`
        - `ownerUUID`. `cachedOwner` -> `owner`, now protected; not one-to-one
        - `setOwner` now has an overload to take in an `EntityReference`
    - `ProjectileUtil`
        - `DEFAULT_ENTITY_HIT_RESULT_MARGIN` is now public
        - `getEntityHitResult` now takes in a `Projectile` instead of an `Entity`
- `net.minecraft.world.item.ItemStack`
    - `forEachModifier` now takes in a `TriConsumer` that provides the modifier display
    - `hurtAndBreak` now has an overload which gets the `EquipmentSlot` from the `InteractionHand`
- `net.minecraft.world.item.equipment.Equippable` now takes in whether the equipment can be sheared off an entity and the sound to play when doing so
- `net.minecraft.world.level.BlockGetter`
    - `forEachBlockIntersectedBetween` now returns a boolean indicating that each block visited in the intersected area can be successfully visited
    - `$BlockStepVisitor#visit` now returns whether the location can be successfully moved to
- `net.minecraft.world.level.block.AbstractCauldronBlock#SHAPE` is now protected
- `net.minecraft.world.level.block.entity`
    - `BlockEntity#getNameForReporting` is now public
    - `SignBlockEntity#executeClickCommandsIfPresent` now takes in a `ServerLevel` instead of the `Level`, parameters are reordered
    - `StructureBlockEntity#saveStructure` now takes in a list of blocks to ignore
- `net.minecraft.world.level.block.entity.trialspawner`
    - `TrialSpawner` now takes in a `$FullConfig`
        - `getConfig` -> `activeConfig`
        - `get*Config` -> `*config`
        - `getData` -> `getStateData`
    - `TrialSpawnerData` -> `TrialSpawnerStateData`, serialized form as `TrialSpawnerStateData$Packed`, not one-to-one
- `net.minecraft.world.level.block.sounds.AmbientDesertBlockSoundsPlayer#playAmbientBlockSounds` has been split into `playAmbientSandSounds`, `playAmbientDryGrassSounds`, `playAmbientDeadBushSounds`, `shouldPlayDesertDryVegetationBlockSounds`; not one-to-one
- `net.minecraft.world.level.dimension.DimensionType` now takes in an optional integer representing the cloud height level
- `net.minecraft.world.level.entity`
    - `PersistentEntitySectionManager#processPendingLoads` is now public
    - `UUIDLookup#getEntity` can now return null
- `net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate#fillFromWorld` now takes in a list of blocks to ignore rather than a single `Block`
- `net.minecraft.world.level.storage.DataVersion` is now a record
- `net.minecraft.world.phys.shapes.CollisionContext#placementContext` now takes in a `Player` instead of an `Entity`

### List of Removals

- `net.minecraft.client.Minecraft#disconnect(Screen)`
- `net.minecraft.client.renderer`
    - `DimensionSpecialEffects#getCloudHeight`, `hasGround`
    - `LevelRenderer#updateGlobalBlockEntities`
- `net.minecraft.client.renderer.texture.AbstractTexture`
    - `defaultBlur`
    - `setFilter`
- `net.minecraft.network.chat.Component$Serializer`, `$SerializerAdapter`
- `net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket$Action#*_SHIFT_KEY`
- `net.minecraft.server.ReloadableServerRegistries$Holder#getKeys`
- `net.minecraft.server.players.PlayerList#getPlayerForLogin`
- `net.minecraft.stats`
    - `RecipeBookSettings#read`, `write`
    - `ServerRecipeBook#toNbt`, `fromNbt`
- `net.minecraft.util`
    - `GsonHelper#fromNullableJson(..., boolean)`, `fromJson(..., boolean)`
    - `LowerCaseEnumTypeAdapterFactory`
- `net.minecraft.world.entity.ai.attributes.AttributeInstance#ID_FIELD`, `TYPE_CODEC`
- `net.minecraft.world.entity.animal.horse.AbstractHorse#setIsJumping`
- `net.minecraft.world.entity.animal.sheep.Sheep#getColor`
- `net.minecraft.world.entity.monster.Drowned#waterNavigation`, `groundNavigation`
- `net.minecraft.world.entity.projectile.Projectile#findOwner`, `setOwnerThroughUUID`
- `net.minecraft.world.level.Level#disconnect()`
- `net.minecraft.world.level.block`
    - `AbstractCauldronBlock#isEntityInsideContent`
    - `TerracottaBlock`
- `net.minecraft.world.level.block.entity.trialspawner.TrialSpawner`
    - `*_CONFIG_TAG_NAME`
    - `codec`
- `net.minecraft.world.level.dimension.DimensionType#parseLegacy`
