# Minecraft 1.21.1 -> 1.21.2 Mod Migration Primer

This is a high level, non-exhaustive overview on how to migrate your mod from 1.21.1 to 1.21.2. This does not look at any specific mod loader, just the changes to the vanilla classes. All provided names use the official mojang mappings.

This primer is licensed under the [Creative Commons Attribution 4.0 International](http://creativecommons.org/licenses/by/4.0/), so feel free to use it as a reference and leave a link so that other readers can consume the primer.

If there's any incorrect or missing information, please file an issue on this repository or ping @ChampionAsh5357 in the Neoforged Discord server.

## Pack Changes

There are a number of user-facing changes that are part of vanilla which are not discussed below that may be relevant to modders. You can find a list of them on [Misode's version changelog](https://misode.github.io/versions/?id=1.21.2&tab=changelog).

## The Holder Set Transition

Many of the methods that used `TagKey`s or raw registry objects have been replaced with the direct `HolderSet` object. A `HolderSet` is essentially a list of registry object references that can be dynamically updated and managed as needed by the game. There are effectively two kinds of `HolderSet`s: direct and named. Named `HolderSet`s are the object representation of tags in game. It's called a named set as the `HolderSet` is referenced by the tag's name. Direct `HolderSet`s, on the other hand, are created by `HolderSet#direct`, which functions as an inlined list of values. These are useful when a separate object doesn't need to be defined to construct some value.

For a JSON example:
```json5
// HolderSet#direct with one element
{
    "holder_set": "minecraft:apple"
}

// HolderSet#direct with multiple elements
{
    "holder_set": [
        "minecraft:apple",
        "minecraft:stick"
    ]
}

// HolderSet reference (tags)
{
    "holder_set": "#minecraft:planks"
}
```

Generally, you should never be constructing holder sets incode except during provider generation. Each set type has a different method of construction.

First, to even deal with `Holder`s or `HolderSet`s, you will need access to the static registry instance via `Registry` or the datapack registry via `HolderGetter`. The `HolderGetter` is either obtained from a `BootstrapContext#lookup` during datapack registry generation or `HolderLookup$Provider#lookupOrThrow` either as part of generation or `MinecraftServer#registryAccess` during gameplay.

Once that is available, for direct `HolderSet`s, you will need to get the `Holder` form of a registry object. For static registries, this is done through `Registry#wrapAsHolder`. For datapack registries, this is done through `HolderGetter#getOrThrow`.

```java
// Direct holder set for Items
HolderSet<Item> items = HolderSet.direct(BuiltInRegistries.ITEM.wrapAsHolder(Items.APPLE));

// Direct holder set for configured features
// Assume we have access to the HolderGetter<ConfiguredFeature<?, ?>> registry
Holderset<ConfiguredFeature<?, ?>> features = HolderSet.direct(registry.getOrThrow(OreFeatures.ORE_IRON));
```

For named `HolderSet`s, the process is similar. For both static and dynamic registries, you call `HolderGetter#getOrThrow`.

```java
// Named holder set for Items
HolderSet<Item> items = BuiltInRegistries.ITEM.getOrThrow(ItemTags.PLANKS);

// Named holder set for biomes
// Assume we have access to the HolderGetter<Biome> registry
Holderset<Biome> biomes = registry.getOrThrow(BiomeTags.IS_OCEAN);
```

As these changes are permeated throughout the entire codebase, they will be listed in more relevant subsections.

## Gui Render Types

Gui rendering methods within `GuiGraphics` now take in a `Function<ResourceLocation, RenderType>` to determine how to render the image. Also, `blit` methods now require the size of the PNG to be specified.

```java
// For some GuiGraphics graphics
graphics.blit(
    // How to render the texture
    RenderType::guiTextured,
    // The previous texture parameters
    ...,
    // The size of the PNG to use
    256, 256);
```

This means methods that provided helpers towards setting the texture or other properties that could be specified within a shader have been removed.

- `com.mojang.blaze3d.pipeline.RenderTarget#blitToScreen(int, int, boolean)` -> `blitAndBlendToScreen`
- `net.minecraft.client.gui.GuiGraphics`
    - `drawManaged` is removed
    - `setColor` is removed - Now a parameter within the `blit` and `blitSprite` methods
    - `blit(int, int, int, int, int, TextureAtlasSprite, *)` is removed
    - `bufferSource` -> `drawSpecial`, not one-to-one as this takes in a consumer of the `MultiBufferSource` and ends the current batch instead of just returning the `MultiBufferSource`
- `net.minecraft.client.gui.components.PlayerFaceRenderer`
    - All `draw` methods except `draw(GuiGraphics, PlayerSkin, int, int, int)` takes in an additional `int` that defines the color
- `net.minecraft.client.renderer.RenderType`
        - `guiTexturedOverlay` - Gets the render type for an image overlayed onto the game screen.
        - `guiOpaqueTexturedBackground` - Gets the render type for a GUI texture applied to the background of a menu.
        - `guiNauseaOverlay` - Gets the render type for the nausea overlay.
        - `guiTextured` - Gets the render type for an image within a GUI menu.
- `net.minecraft.client.resources.metadata.gui.GuiSpriteScaling$NineSlice` now takes in a boolean representing whether the center portion of the texture should be streched to fit the size

## Shader Rewrites

The internals of shaders have been rewritten quite a bit.

### Shaders Files

The main changes are the defined samplers and post shaders.

The `DiffuseSampler` and `DiffuseDepthSampler` have been given new names depending on the target to apply: `InSampler`, `MainSampler`, and `MainDepthSampler`. `InSampler` is used in everything but the `transparency` program shader.

```json5
// In some shader JSON
{
    "samplers": [
        { "name": "MainSampler" },
        // ...
    ]
}
```

Within post effect shaders, they have been changed completely. For a full breakdown of the changes, see `PostChainConfig`, but in general, all targets are now keys to objects, all pass inputs and filters are now lists of sampler inputs. As for how this looks:

```json5
// Old post effect shader JSON
// In assets/<namespace>/shaders/post
{
    "targets": [
        "swap"
    ],
    "passes": [
        {
            "name": "invert",
            "intarget": "minecraft:main",
            "outtarget": "swap",
            "use_linear_filter": true,
            "uniforms": [
                {
                    "name": "InverseAmount",
                    "values": [ 0.8 ]
                }
            ]
        },
        {
            "name": "blit",
            "intarget": "swap",
            "outtarget": "minecraft:main"
        }
    ]
}

// New post effect JSON
// In assets/<namespace>/post_effect
{
    "targets": {
        "swap": {} // Swap is now a target object (fullscreen unless otherwise specified)
    },
    "passes": [
        {
            // Name of the program to apply (previously 'name')
            // assets/minecraft/shaders/post/invert.json
            "program": "minecraft:post/invert",
            // Inputs is now a list
            "inputs": [
                {
                    // Targets the InSampler
                    // Sampler must be available in the program shader JSON
                    "sampler_name": "In",
                    // Reading from the main screen (previously 'intarget')
                    "target": "minecraft:main",
                    // Use GL_LINEAR (previously 'use_linear_filter')
                    "bilinear": true
                }
            ],
            // Writes to the swap target (previously 'outtarget')
            "output": "swap",
            "uniforms": [
                {
                    "name": "InverseAmount",
                    "values": [ 0.8 ]
                }
            ]
        },
        {
            "program": "minecraft:post/blit",
            "inputs": [
                {
                    "sampler_name": "In",
                    "target": "swap"
                }
            ],
            "output": "minecraft:main"
        }
    ]
}
```

### Shader Programs

All shaders, regardless of where they are used (as part of a program or post effect), have a JSON within `assets/<namespace>/shaders`. This JSON defines everything the shader will use, as defined by `ShaderProgramConfig`. The main addition is the change to `ResourceLocation` relative references, and adding the `defines` header dynamically during load time.

```json5
// For some assets/my_mod/shaders/my_shader.json
{
    // Points to assets/my_mod/shaders/my_shader.vsh (previously 'my_shader', without id specification)
    "vertex": "my_mod:my_shader",
    // Points to assets/my_mod/shaders/my_shader.fsh (previously 'my_shader', without id specification)
    "fragment": "my_mod:my_shader",
    // Adds '#define' headers to the shaders
    "defines": {
        // #define <key> <value>
        "values": {
            "ALPHA_CUTOUT": "0.1"
        },
        // #define flag
        "flags": [
            "NO_OVERLAY"
        ]
    },
    // A list of sampler uniforms to use in the shader
    // There are 12 texture sampler uniforms Sampler0-Sampler11, though usually only Sampler0 is supplied
    // Additionally, there are dynamic '*Sampler' for post effect shaders which are bound to read the specified targets or 'minecraft:main'
    "samplers": [
        { "name": "Sampler0" }
    ],
    // A list of uniforms that can be accessed within the shader
    // A list of available uniforms can be found in CompiledShaderProgram#setUniforms
    "uniforms": [
        { "name": "ModelViewMat", "type": "matrix4x4", "count": 16, "values": [ 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0 ] },
        { "name": "ProjMat", "type": "matrix4x4", "count": 16, "values": [ 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0 ] },
        { "name": "ModelOffset", "type": "float", "count": 3, "values": [ 0.0, 0.0, 0.0 ] },
        { "name": "ColorModulator", "type": "float", "count": 4, "values": [ 1.0, 1.0, 1.0, 1.0 ] }
    ]
}
```

```glsl
// For some assets/my_mod/shaders/my_shader.vsh (Vertex Shader)

// GLSL Version
#version 150

// Imports Mojang GLSL files
// Located in assets/<namespace>/shaders/include/<path>
#moj_import <minecraft:light.glsl>

// Defines are injected (can use 'ALPHA_CUTOUT' and 'NO_OVERLAY')

// Defined by the VertexFormat passed into the ShaderProgram below
in vec3 Position; // vec3 float
in vec4 Color; // vec4 unsigned byte (0-255)

// Samplers defined by the JSON
uniform sampler2D Sampler0;

// Uniforms provided by the JSON
uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform vec3 ModelOffset;

// Values to output to the fragment shader
out float vertexDistance;
out vec4 vertexColor;
out vec2 texCoord0;

void main() {
    // Out values should be set here
}
```

```glsl
// For some assets/my_mod/shaders/my_shader.fsh (Fragment Shader)

// GLSL Version
#version 150

// Imports Mojang GLSL files
// Located in assets/<namespace>/shaders/include/<path>
#moj_import <minecraft:fog.glsl>

// Defines are injected (can use 'ALPHA_CUTOUT' and 'NO_OVERLAY')

// Defined by the output of the vertex shader above 
in float vertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;

// Samplers defined by the JSON
uniform sampler2D Sampler0;

// Uniforms provided by the JSON
uniform vec4 ColorModulator;

// Values to output to the framebuffer (the color of the pixel)
out vec4 fragColor;

void main() {
    // Out values should be set here
}
```

On the code side, shaders are stored internally as either a `ShaderProgram` or a `CompiledShaderProgram`. `ShaderProgram` represents the identifier, while the `CompiledShaderProgram` represents the shader itself to run. Both are linked together through the `ShaderManager`.

Shader programs are compiled dynamically unless specified as a core shader. This is done by registering the `ShaderProgram` to `CoreShaders#PROGRAMS`.

```java
// List<ShaderProgram> PROGRAMS access
ShaderProgram MY_SHADER = new ShaderProgram(
    // Points to assets/my_mod/shaders/my_shader.json
    ResourceLocation.fromNamespaceAndPath('my_mod', 'my_shader'),
    // Passed in vertex format used by the shader
    DefaultVertexFormat.POSITION_COLOR,
    // Lists the '#define' values and flags
    // Value: '#define <key> <value>'
    // Flag: '#define <flag>'
    ShaderDefines.EMPTY
)
```

The shader programs are then set by calling `RenderSystem#setShader` with the `ShaderProgram` in question. In fact, all references to `GameRenderer#get*Shader` should be replaced with a `ShaderProgram` reference.

```java
// In some rendering method
RenderSystem.setShader(MY_SHADER);

// Creating a new ShaderStateShard for a RenderType
ShaderStateShard MY_SHARD = new ShaderStateShard(MY_SHADER);
```

- `com.mojang.blaze3d.ProjectionType` - An enum which holds the logic for how a projection matrix should be rendered.
- `com.mojang.blaze3d.buffers`
    - `BufferType` - An enum that specifies the GL target buffer type.
    - `GpuBuffer` - A wrapper around the GL buffer calls for handling the rendering of the screen.
    - `GpuFence` - A handle for managing the sync status of the GPU fence.
- `com.mojang.blaze3d.platform.GlStateManager`
    - `glShaderSource` now takes in a `String` rather than a `List<String>`
    - `_glMapBufferRange` - Delegates to `GL30#glMapBufferRange`.
    - `_glFenceSync` - Delegates to `GL32#glFenceSync`.
    - `_glClientWaitSync` - Delegates to `GL32#glClientWaitSync`.
    - `_glDeleteSync` - Delegates to `GL32#glDeleteSync`.
    - `_glBuffserSubData` - Delegates to `GL15#glBufferSubData`.
- `com.mojang.blaze3d.preprocessor.GlslPreprocessor#injectDefines` - Injects any defined sources to the top of a loaded `.*sh` file.
- `com.mojang.blaze3d.shaders`
    - `BlendMode`, `Effect`, `EffectProgram`, `Program`, `ProgramManager`, `Shader` has been bundled into `CompiledShader`
    - `Unform` no longer takes in a `Shader`
        - `glGetAttribLocation` is removed
        - `glBindAttribLocation` -> `VertexFormat#bindAttributes`
        - `setFromConfig` - Sets the uniform parameters given the values and count of another uniform configuration.
- `com.mojang.blaze3d.systems.RenderSystem`
    - `setShader` now takes in the `CompiledShaderProgram`, or `ShaderProgram`
    - `clearShader` - Clears the current system shader.
    - `runAsFancy` is removed, handled internally by `LevelRenderer#getTransparencyChain`
    - `setProjectionMatrix` now takes in a `ProjectionType` than just the `VertexSorting`
    - `getVertexSorting` -> `getProjectionType`; not one-to-one, but the `VertexSorting` is accessible on the `ProjectionType`
- `com.mojang.blaze3d.vertex.VertexBuffer`
    - `drawWithShader` will now noop when passing in a null `CompiledShaderProgram`
    - `$Usage` -> `com.mojang.blaze3d.buffers.BufferUsage`
- `net.minecraft.client.Minecraft#getShaderManager` - Returns the manager that loads all the shaders and post effects.
- `net.minecract.client.renderer`
    - `EffectInstance` class is removed, replaced by `CompiledShaderProgram` in most cases
    - `GameRenderer`
        - `get*Shader` -> `CoreShaders#*`
        - `shutdownEffect` -> `clearPostEffect`
        - `createReloadListener` -> `ShaderManager`
        - `currentEffect` -> `currentPostEffect`
    - `ItemBlockRenderTypes#getRenderType` no longer takes in a boolean indicating whether to use the translucent render type
    - `ShaderInstance` -> `CompiledShaderProgram`
        - `CHUNK_OFFSET` -> `MODEL_OFFSET`
            - JSON shaders: `ChunkOffset` -> `ModelOffset`
        - `getUniformConfig` - Returns the configuration of a uniform given its name.
    - `LevelRenderer#graphicsChanged` is removed, handled internally by `LevelRenderer#getTransparencyChain`
    - `PostChainConfig` - A configuration that represents how a post effect shader JSON is constructed.
    - `PostPass` now takes in the `ResourceLocation` representing the output target instead of the in and out `RenderTarget`s or the `boolean` filter mode, the `CompiledShaderProgram` to use instead of the `ResourceProvider`, and a list of uniforms for the shader to consume
        - No longer `AutoCloseable`
        - `addToFrame` no longer takes in the `float` time
        - `getEffect` -> `getShader`
        - `addAuxAsset` -> `addInput`
        - `process` -> `addToFrame`
        - `$Input` - Represents an input of the post effect shader.
        - `$TargetInput` - An input from a `RenderTarget`.
        - `$TextureInput` - An input from a texture.
    - `PostChain` constructor is now created via `load`
        - No longer `AutoCloseable`
        - `MAIN_RENDER_TARGET` is now public
        - `getName` is removed, replaced with `ShaderProgram#configId`
        - `process` no longer takes in the `DeltaTracker`
        - `$TargetBundle` - Handles the getting and replacement of resource handles within the chain.
    - `RenderType`
        - `entityTranslucentCull`, `entityGlintDirect` is removed
        - `armorTranslucent` - A render type which renders armor that can have a translucent texture.
    - `ShaderDefines` - The defined values and flags used by the shader as constants.
    - `ShaderManager` - The resource listener that loads the shaders.
    - `ShaderProgram` - An identifier for a shader.
    - `ShaderProgramConfig` - The definition of the program shader JSON.
    - `Sheets#translucentCullBlockSheet` is removed
    - `SkyRenderer` now implements `AutoCloseable`
- `net.minecraft.client.renderer.entity.ItemRenderer`
    - `getFoilBufferDirect` is removed, replaced by `getFoilBuffer`
    - `ITEM_COUNT_BLIT_OFFSET` -> `ITEM_DECORATION_BLIT_OFFSET`

## Entity Render States

Entity models and renderers have been more or less completely reworked due to the addition of `EntityRenderState`s. `EntityRenderState`s are essentially data object classes that only expose the computed information necessary to render the `Entity`. For example, a `Llama` does not need to know what is has in its inventory, just that it has a chest to render in the layer.

To start, you need to choose an `EntityRenderState` to use, or create one using a subclass if you need additional information passed to the renderer. The most common states to subclass is either `EntityRenderState` or `LivingEntityRenderState` for living entities. These fields should be mutable, as the state class is created only once for a renderer.

```java
// Assuming MyEntity extends LivingEntity
public class MyEntityRenderState extends LivingEntityRenderState {
    // An example of a field
    boolean hasExampleData;
}
```

From there, you create the `EntityModel` that will render your `Entity`. The `EntityModel` has a generic that takes in the `EntityRenderState` along with taking in the `ModelPart` root, and optionally the `RenderType` factory as part of its super. The are no methods to implement by default; however, if you need to setup any kind of model movement, you will need to overrride `setupAnim` which modifies the `ModelPart`'s mutable fields using the render state. If your model does not have any animation, then a `Model$Simple` implementation can be used instead. This does not need anything implemented.

```java
public class MyEntityModel extends EntityModel<MyEntityRenderState> {

    public MyEntityModel(ModelPart root) {
        super(root);
        // ...
    }

    @Override
    public void setupAnim(MyEntityRenderState state) {
        // Calls resetPose and whatever other transformations done by superclasses
        super.setupAnim(state); 

        // Perform transformations to model parts here
    }
}
```

`EntityModel` also has three `final` methods from the `Model` subclass: `root`, which grabs the root `ModelPart`; `allParts`, which returns a list of all `ModelPart`s flattened; and `resetPose`, which returns the `ModelPart` to their default state.

`LayerDefinition`s, `MeshDefinition`s, `PartDefinition`s, and `CubeDeformation`s remain unchanged in their implementation and construction for the `ModelLayerLocation` -> `LayerDefinition` map in `LayerDefinitions`.

What about model transformations? For example, having a baby version of the entity, or where the model switches out altogether? In those cases, a separate layer definition is registered for each. For example, a Llama would have a model layer for the main Llama model, the baby model, the decor for both the adult and baby, and finally one for the spit. Since models are generally similar to one another with only a slight transformation, a new method was added to `LayerDefinition` to take in a `MeshTransformer`. `MeshTransformer`s are basically unary operators on the `MeshDefinition`. For baby models, a `BabyModelTransform` mesh transformer is provided, which can be applied via `LayerDefinition#apply`.

```java
public class MyEntityModel extends EntityModel<MyEntityRenderState> {
    public static final MeshTransformer BABY_TRANSFORMS = ...;

    public static LayerDefinition create() {
        // ...
    }
}

// Wherever the model layers are registered
ModelLayerLocation MY_ENTITY = layers.register("examplemod:my_entity");
ModelLayerLocation MY_ENTITY_BABY = layers.register("examplemod:my_entity_baby");

// Wherever the layer definitions are registered
defns.register(MY_ENTITY, MyEntityModel.create());
defns.register(MY_ENTITY_BABY, MyEntityModel.create().apply(MyEntityModel.BABY_TRANSFORMS));
```

But how does the model know what render state to use? That's where the `EntityRenderer` comes in. The `EntityRenderer` has two generics: the type of the `Entity`, and the type of the `EntityRenderState`. The `EntityRenderer` takes in a `Context` object, similar to before. Additionally, `getTextureLocation` needs to be implemented, though this time it takes in the render state instead of the entity. The new methods to implement/override are `createRenderState` and `extractRenderState`. `createRenderState` constructs the default render state object. `extractRenderState`, meanwhile, populates the render state for the current entity being rendered. `extractRenderState` will need to be overridden if you are not using an existing render state class.

Of course, there are also subclasses of the `EntityRenderer`. First, there is `LivingEntityRenderer`. This has an additional generic of the `EntityModel` being rendered, and takes that value in the constructor along with the shadow radius. This renderer also accepts `RenderLayer`s, which largely remain unchanged if you access the previous arguments through the render state. Then, there is the `MobRenderer`, which is what all entities extend. Finally, there is `AgeableMobRenderer`, which takes in two models - the adult and the baby - and decides which to render dependent on `LivingEntityRenderState#isBaby`. `AgeableMobRenderer` should be used with `BabyModelTransform` if the entity has a baby form. Otherwise, you will most likely use `MobRenderer` or `EntityRenderer`.

```java
public class MyEntityRenderer extends AgeableMobRenderer<MyEntity, MyEntityRenderState, MyEntityModel> {

    public MyEntityRenderer(EntityRendererProvider.Context ctx) {
        super(
            ctx,
            new MyEntityModel(ctx.bakeLayer(MY_ENTITY)), // Adult model
            new MyEntityModel(ctx.bakeLayer(MY_ENTITY_BABY)), // Baby model
            0.7f // Shadow radius
        );

        // ...
    }

    @Override
    public ResourceLocation getTextureLocation(MyEntityRenderState state) {
        // Return entity texture here
    }

    @Override
    public MyEntityRenderState createRenderState() {
        // Constructs the reusable state
        return new MyEntityRenderState();
    }

    @Override
    public void extractRenderState(MyEntity entity, MyEntityRenderState state, float partialTick) {
        // Sets the living entity and entity render state info
        super.extractRenderState(entity, state, partialTick);
        // Set our own variables
        state.hasExampleData = entity.hasExampleData();
    }
}

// Wherever the entity renderers are registered
renderers.register(MyEntityTypes.MY_ENTITY, MyEntityRenderer::new);
```

- `net.minecraft.client.model`
    - `AbstractBoatModel` - A model that assumes there is a `left_paddle` and `right_paddle` that is animated according to the boat's rowing time.
    - `AgeableHierarchicalModel`, `ColorableAgeableListModel`, `AgeableListModel` -> `BabyModelTransform`
    - `AnimationUtils`
        - `animateCrossbowCharge` now takes in a `float` representing the charge duration and `int` representing the use ticks instead of a `LivingEntity`
        - `swingWeaponDown` now takes in a `HumanoidArm` instead of a `Mob`
    - `BabyModelTransform` - A mesh transformer that applies a baby scaled form of the model.
    - `BoatModel`
        - `createPartsBuilder` is removed
        - `createChildren` -> `addCommonParts`, now private
        - `createBodyModel` -> `createBoatModel`, `createChestBoatModel`
        - `waterPatch` -> `createWaterPatch`
        - `parts` is removed
    - `ChestBoatModel` -> `BoatModel#createChestBoatModel`
    - `ChestedHorseModel` class is removed and now purely lives in `LlamaModel` and `DonkeyModel`
    - `ChestRaftModel` -> `RaftModel#createChestRaftModel`
    - `ColorableHierarchicalModel` is now stored in the individual `EntityRenderState`
    - `EntityModel`
        - The generic now takes in a `EntityRenderState`
        - `setupAnim` only takes in the `EntityRenderState` generic
        - `prepareMobModel` is removed
        - `copyPropertiesTo` is removed, still exists in `HumanoidModel`
    - `HierarchicalModel` class is removed
    - `HumanoidModel#rotLerpRad` -> `Mth#rotLerpRad`
    - `ListModel` class is removed
    - `Model`
        - `renderToBuffer` is now final
        - `root` - Returns the root `ModelPart`.
        - `getAnyDescendantWithName` - Returns the first descendant of the root that has the specified name.
        - `animate` - Give the current state and definition of the aninmation, transforms the model between the current time and the maximum time to play the animation for.
        - `animateWalk` - Animates the walking cycle of the model.
        - `applyStatic` - Applies an immediate animation to the specified state.
        - `$Simple` - Constructs a simple model that has no additional animation.
    - `ModelUtils` class is removed
    - `ParrotModel#getState` -> `getPose`, now public
    - `PlayerModel` no longer has a generic
        - `renderEars` -> `PlayerEarsModel`
        - `renderCape` -> `PlayerCapeModel`
        - `getRandomModelPart` -> `getRandomBodyPart`
        - `getArmPose` - Returns the arm pose of the player given its render state.
    - `RaftModel#createBodyModel` -> `createRaftModel`
    - `WardenModel#getTendrilsLayerModelParts`, `getHeartLayerModelParts`, `getBioluminescentLayerModelParts`, `getPulsatingSpotsLayerModelParts` now take in the `WardenRenderState`
    - `WaterPatchModel` -> `BoatModel#createWaterPatch` and `Model$Simple`
- `net.minecraft.client.model.geom`
    - `ModelLayerLocation` is now a record
    - `ModelLayers`
        - `createRaftModelName`, `createChestRaftModelName` is removed
        - `createSignModelName` -> `createStandingSignModelName`, `createWallSignModelName`
        - `createBoatModelName`, `createChestBoatModelName` is removed
    - `ModelPart`
        - `rotateBy` - Rotates the part using the given `Quaternionf`.
        - `$Cube#polygons`, `$Polygon`, `$Vertex` is now public
    - `PartPose` is now a record
        - `translated` - Translates a pose.
        - `withScale`, `scaled` - Scales a pose.
- `net.minecraft.client.model.geom.builders`
    - `LayerDefinition#apply` - Applies a mesh transformer to the definition and returns a new one. 
    - `MeshDefinition#transformed` - Applies a transformation to the root pose and returns a new one.
    - `MeshTransformer` - Transforms an existing `MeshDefinition` into a given form.
    - `PartDefinition`
        - `addOrReplaceChild` now has an overload that takes in a `PartDefinition`
        - `clearChild` - Removes the child from the part definition.
        - `getChildren` - Gets all the children of the current part.
        - `transformed` - Applies a transformation to the current pose and returns a new one.
- `net.minecraft.client.renderer.entity`
    - `AbstractBoatRenderer` - A boat renderer that contains methods for the boat model and any additions to the boat itself.
    - `AgeableMobRenderer` - A mob renderer that takes in the baby and adult model.
    - `BoatRenderer` now takes in a `ModelLayerLocation` instead of a `boolean`
    - `EntityRenderDispatcher` now takes in a `MapRenderer`
        - `render` no longer takes in the entity Y rotation
    - `EntityRenderer` now takes in a generic for the `EntityRenderState`
        - `getRenderOffset` only takes in the `EntityRenderState`
        - `getBoundingBoxForCulling` - Returns the bounding box of the entity to determine whether to cull or not.
        - `affectedByCulling` - Returns whether the entity can be culled.
        - `render` only takes in the render state, along with the stack, buffer source, and packet light
        - `shouldShowName` now takes in a `double` for the camera squared distance from the entity
        - `getTextureLocation` is removed, being moved to the classes where it is used, like `LivingEntityRenderer`
            - Subsequent implementations of `getTextureLocation` may be protected or private
        - `renderNameTag` now takes in the render state instead of the entity and removes the partial tick `float`
        - `getNameTag` - Gets the name tag from the entity.
        - `getShadowRadius` now takes in the render state instead of the entity
        - `createRenderState` - Creates the render state object.
        - `extractRenderState` - Reads any data from the entity to the render state.
    - `EntityRendererProvider$Context` takes in the `MapRenderer` instead of the `ItemInHandRenderer`
    - `LivingRenderer`
        - `isShaking` now takes in the render state instead of the entity
        - `setupRotations` now takes in the render state instead of the entity
        - `getAttackAnim`, `getBob` are now within the render state
        - `getFlipDegrees` no longer takes in the entity
        - `getWhiteOverlayProgress` now takes in the render state instead of the entity and no longer takes in the entity Y rotation
        - `scale` now takes in the render state instead of the entity and no longer takes in the entity Y rotation
        - `shouldShowName` now takes in a `double` representing the squared distance to the camera
        - `getShadowRadius` now takes in the render state instead of the entity
    - `RaftRenderer` - A raft renderer that implements the `AbstractBoatRenderer`.
    - `RenderLayerParent#getTextureLocation` is removed
- `net.minecraft.client.renderer.entity.layers`
    - `EnergySwirlLayer#isPowered` - Returns whether the energy is powered.
    - `CustomHeadLayer` and `#translateToHead` takes in a `CustomHeadLayer$Transforms` instead of a scaling information hardcoding the transform
    - `PlayerItemInHandRenderer` takes in an `ItemRenderer` instead of a `ItemInHandRenderer`
    - `RenderLayer` takes in an `EntityRenderState` generic instead of an `Entity` generic
        - `coloredCutoutModelCopyLayerRender` takes in a single `EntityModel` with the state info bundled into the render state
        - `renderColoredCutoutModel` takes in non-generic forms of the rendering information, assuming a `LivingEntityRenderState`
        - `getTextureLocation` is removed, instead being passed directly into the appropriate location
        - `render` now takes in the render state instead of the entity and parameter information
    - `SaddleLayer` has a constructor to take in a baby model.
    - `SheepFurLayer` -> `SheepWoolLayer`
    - `StuckInBodyLayer` now takes in the model to apply the stuck objects to, the texture of the stuck objects, and the placement style of the objects
        - `numStuck` now takes in the render state instead of the entity
        - `renderStuckItem` is now private
    - `WardenEmissiveLayer` -> `LivingEntityEmissiveLayer`, a more generalized implementation
- `net.minecraft.client.renderer.entity.player.PlayerRenderer`
    - `renderRightHand`, `renderLeftHand` now take in a `ResourceLocation` instead of the `AbstractClientPlayer` and a `boolean` whether to render the left and/or right sleeve
    - `setupRotations` now takes in the render state instead of the entity and parameter information
- `net.minecraft.world.entity`
    - `AnimationState#copyFrom` - Copies the animation state from another state.
    - `Entity`
        - `noCulling` -> `EntityRenderer#affectedByCulling`
        - `getBoundingBoxForCulling` -> `EntityRenderer#getBoundingBoxForCulling`
    - `LerpingModel` class is removed
    - `PowerableMob` class is removed

### Model Baking

`UnbakedModel`s now have a different method to resolve any dependencies. Instead of getting the dependencies and resolving the parents, this is now done through a single method called `resolveDependencies`. This method takes in the `Resolver`. The `Resolver` is responsible for getting the `UnbakedModel` for the `ResourceLocation`.

```java
// For some UnbakedModel instance
public class MyUnbakedModel implements UnbakedModel {

    @Nullable
    protected ResourceLocation parentLocation;
    @Nullable
    protected UnbakedModel parent;
    private final List<ItemOverride> overrides;

    // ...

    @Override
    public void resolveDependencies(UnbakedModel.Resolver resolver) {
        // Get parent model for delegate resolution
        if (this.parentLocation != null) {
            this.parent = resolver.resolve(this.parentLocation);
        }
    }
}
```

- `net.minecraft.client.renderer.block`
    - `BlockModel#getDependencies`, `resolveParents` -> `resolveDependencies`
    - `BlockModelDefintion` now takes in a `MultiPart$Definition`, no `List<BlockModelDefinition>` constructor exists
        - `fromStream`, `fromJsonElement` no longer take in a `$Context`
        - `getVariants` is removed
        - `isMultiPart` is removed
        - `instantiate` -> `MultiPart$Definition#instantiate`
    - `MultiVariant` is now a record
    - `UnbakedBlockStateModel` - An interface that represents a block state model, contains a single method to group states together with the same model.
    - `VariantSelector` - A utility for constructing the state definitions from the model descriptor.
- `net.minecraft.client.renderer.block.model`
    - `BlockModel`
        - `MISSING_MATERIAL` - The material of the missing block texture.
        - `bake` no longer takes in the `ModelBaker` and `BlockModel`
        - `$LoopException` class is removed
- `net.minecraft.client.renderer.block.model.multipart.MultiPart` now implements `UnbakedBlockStateModel`
    - `getSelectors` -> `$Definition#selectors`
    - `getMultiVariants` ->` $Definition#getMultiVariants`
- `net.minecraft.client.resources.model`
    - `BakedModel#getOverrides` -> `overrides`, method is defaulted to an empty override
    - `BlockStateModelLoader` only takes in the missing unbaked model
        - `loadAllBlockStates` is removed
        - `definitionLocationToBlockMapper` - Gets the state definition from a given resource location
        - `loadBlockStateDefinitions` -> `loadBlockStateDefinitionStack`
        - `getModelGroups` -> `ModelGroupCollector`
        - `$LoadedJson` -> `$LoadedBlockModelDefinition`
        - `$LoadedModel` is now public
        - `$LoadedModels` - A record which maps a model location to a loaded model.
    - `BuiltInModel` no longer takes in the `ItemOverrides`
    - `DelegateBakedModel` - A utility implementation that delegates all logic to the supplied `BakedModel`
    - `Material#buffer` takes in another `boolean` that handles whether to apply the glint
    - `MissingBlockModel` - The missing model for a block.
    - `ModelBaker#getModel` is removed, implementation in `ModelBakery$ModelBakerImpl` is private
    - `ModelBakery` only takes in the top models, all unbacked models, and the missing model
        - `BUILTIN_SLASH` -> `SpecialModels#builtinModelId`
        - `BUILTIN_SLASH_GENERATED` -> `SpecialModels#BUILTIN_GENERATED`
        - `BUILTIN_BLOCK_ENTITY` -> `SpecialModels#BUILTIN_BLOCK_ENTITY`
        - `MISSING_MODEL_LOCATION` -> `MissingBlockModel#LOCATION`
        - `MISSING_MODEL_VARIANT` -> `MissingBlockModel#VARIANT`
        - `GENERATION_MARKER` -> `SpecialModels#GENERATED_MARKER`
        - `BLOCK_ENTITY_MARKER` -> `SpecialModels#BLOCK_ENTITY_MARKER`
        - `getModelGroups` -> `ModelGroupCollector`
    - `ModelDiscovery` - A loader for block and item models, such as how to resolve them when reading.
    - `ModelGroupCollector` - A blockstate collector meant to map states to their associated block models.
    - `ModelResourceLocation#vanilla` is removed
    - `MultiPartBakedModel` fields are now obtained from the first model in the selector and are private
        - `$Builder` class is removed, replaced with `$Selector`
    - `SimpleBakedModel`, `SimpleBakedModel$Builder` no longer takes in the `ItemOverrides`
    - `SpecialModels` - A utility for builtin models.
    - `UnbakedModel`
        - `getDependencies`, `resolveParents` -> `resolveDependencies`
        - `bake` is no longer nullable
        - `$Resolver` - Determines how the unbaked model should be loaded when on top or on override.
    - `WeightedBakedModel` now takes in a `SimpleWeightedRandomList` rather than a list of `WeightedEntry`s

## Equipments and Items, Models and All

Equipments and Items have had a major overhaul, most of which is spread throughout this documentation. This is some of the core change which, while they are important, do not deserve more than a cursory explanation due to their ease of change.

### Item Names and Models

The item name and model is now set directly within the properties using the `ITEM_NAME` and `ITEM_MODEL` data components, respectively. By default, this will use the same name and model location as previously, but these can be set via `Item$Properties#overrideDescription` and `#overrideModel`. `overrideDescription` takes in the translation key to use. There is also `useBlockDescriptionPrefix` and `useItemDescriptionPrefix` to change it to the default block and item translation keys, respectively. `overrideModel` takes in the relative `ResourceLocation` of the model JSON. For example, a value of `examplemod:example_item` will map to a `ModelResourceLocation` of `examplemod:example_item#inventory`. This is intended to link to the model JSON at `assets/examplemod/models/item/example_item.json`.

> There is a slight quirk to item models. The same key can also point to `assets/examplemod/models/example_item.json` if the modder decides to for a special model to load at that location under the `inventory` variant. So, it is recommended to avoid having model names with the same name in the root `models` and `models/item` subdirectory.

### Enchantable, Repairable Items

The enchantment value and repair item checks are being replaced with data components: `DataComponents#ENCHANTABLE` and `DataComponents#REPAIRABLE`, respectively. These can be set via the `Item$Properties#enchantable` and `#repairable`. As a result, `Item#getEnchantmentValue` and `isValidRepairItem` are removed.

### Elytras -> Gliders

Any item can act similarly to an elytra if they have the `DataComponents#GLIDER` value equipped. This essentially functions as a flag to indicate that the item can be used to glide. This works only if the item also has a `DataComponents#EQUIPPABLE` entry.

```java
new Item(
    new Item.Properties()
        .component(DataComponents.GLIDER, Unit.INSTANCE) // Sets as a glider
        .component(DataComponents.EQUIPPABLE, /*...*/) // Determines the slot to check whether it can be used
);
```

### Tools, via Tool Materials

`Tier`s within items have been replaced with `ToolMaterial`, which better handles the creation of tools and swords without having to implement each method manually. `ToolMaterial` takes in the same arguments as `Tier`, just as parameters to a single constructor rather than as implementable methods. From there, the `ToolMaterial` is passed to the `DiggerItem` subtypes, along with two floats representing the attack damage and attack speed. Interally, `ToolMaterial#apply*Properties` is called, which applies the `ToolMaterial` info to the `DataComponents#TOOL` and the attributes from the given `float`s.

```java
// Some tool material
public static final ToolMaterial WOOD = new ToolMaterial(
    BlockTags.INCORRECT_FOR_WOODEN_TOOL, // Tier#getIncorrectBlocksForDrops
    59, // Tier#getUses
    2.0F, // Tier#getSpeed
    0.0F, // Tier#getAttackDamageBonus
    15, // Tier#getEnchantmentValue
    ItemTags.WOODEN_TOOL_MATERIALS // Tier#getRepairIngredient
);

// When constructing the digger item subtype
new PickaxeItem(
    WOOD, // Tool material
    1.0f, // Attack damage
    -2.8f, // Attack speed
    new Item.Properties()
)
```

## Armor Materials, Equipment, and Model (Textures)

This is, by far, the largest change outside of consumables to items. `ArmorMaterial`s have effectively been made obsolete, as almost all of the logic is handled within data components, and attached to some resource pack JSON to load the associated textures. It is annoyingly complicated to understand at first glance, but is rather intuitive once you are familiar with the process.

### `ArmorMaterial`

`ArmorMaterial` is essentially a record that converts a list of properties to their proper location on the data components, NOT a registry object. This is done by passing in the item properties and an additional setting to either `#humanoidProperties` or `#animalProperties`. These settings should be familiar, as they remained unchanged from the previous version, the only difference is that they now specify a 'model id', which we will go into below. The armor material is used in conjunction with the `ArmorType`: an enum which defines the equipment slot the armor is placed into, the unit durability of each armor type, and the name (which is only used to construcct the attribute modifier id).

```java
ArmorMaterial exampleArmorMaterial = new ArmorMaterial(
    15, // The scalar durability to multiply the armor type against
    // A map of ArmorType -> half armor bars to apply to the entity ARMOR attribute
    // Should be set for all ArmorTypes
    Util.make(new EnumMap<>(ArmorType.class), map -> {
        map.put(ArmorType.BOOTS, 2);
        map.put(ArmorType.LEGGINGS, 5);
        map.put(ArmorType.CHESTPLATE, 6);
        map.put(ArmorType.HELMET, 2);
        map.put(ArmorType.BODY, 5);
    },
    25, // The enchantment value of the armor
    SoundEvents.ARMOR_EQUIP_IRON, // The holder wrapped sound event on what sound to make when the item is equipped
    0f, // The ARMOR_TOUGHNESS attribute value
    2f, // The KNOCKBACK_RESISTANCE attribute value,
    ItemTags.REPAIRS_DIAMOND_ARMOR, // An item tag representing the items that can repair this armor
    // The relative location of the EquipmentModel JSON
    // Points to assets/examplemod/models/equipment/example_armor_material.json
    ResourceLocation.fromNamespaceAndPath("examplemod", "example_armor_material")
)
```

With the `ArmorMaterial`, this is either applied to the item properties by calling `humanoidProperties`, to apply the armor to a specific `ArmorType`; or `animalProperties` to apply the armor to the `BODY` and only allow specific entities to wear them.

Does this mean that `ArmorItem` and `AnimalArmorItem` are effectively pointless? For `AnimalArmorItem`, this can be argued. The only thing that `AnimalArmorItem` does is have a `$BodyType` parameter, which means that the armor can only be applied to a horse or a wolf, and specifies the item breaking sound. `ArmorItem`, on the other hand, only has one specific usecase: determining whether the item can be taken off or swapped. This implicity checks the currently wearing armor item to see whether it can't be taken off (via `PREVENT_ARMOR_CHANGE` enchantment) and calculating the properties on the replacing armor material so that any hotswaps will only improve their wearer's armor attribute values.

Let's go one level deeper.

### The Data Components

`ArmorMaterial` specifies eight data components to apply to item:

- `MAX_DAMAGE` - Set to the maximum durability of the item multiplied by the `ArmorType` unit durability
- `MAX_STACK_SIZE` - Set to 1
- `DAMAGE` - Set to 0
- `ATTRIBUTE_MODIFIERS` - Sets `ARMOR` and `ARMOR_TOUGHNESS` attributes, and `KNOCKBACK_RESISTANCE` when greater than 0
- `ENCHANTABLE` - Set to the enchantment value (not set when calling `animalProperties`)
- `REPAIRABLE` - Set to the `HolderSet` of the tag key representing the repairing ingredients
- `EQUIPPABLE` - Sets the slot, equip sound, model id, what entities can wear the item, and whether it is dispensible

Everything but `EQUIPPABLE` has already been explained above or has been around from a prior version, so this primer will only focus on `EQUIPPABLE` from now on.

### Equippable

`Equippable`, which used to be an interface, is now a data component that contains how an entity can equip this item and if the equipment should be rendered. Because of this, an item can only be equipped to a single slot. This can be done using the `Equippable` constructor or through the builder via `Equippable#builder`.

```java
new Item(
    new Item.Properties()
    .component(DataComponents.EQUIPPABLE, new Equippable(
        EquipmentSlot.HEAD, // The slot the item can be equipped to
        SoundEvents.ARMOR_EQUIP_IRON, // The sound to play when equipping the item
        // The relative location of the EquipmentModel JSON
        // Points to assets/examplemod/models/equipment/example_armor_material.json
        // When set to an empty optional, the item does not attempt to render as equipment
        Optional.of(ResourceLocation.fromNamespaceAndPath("examplemod", "example_armor_material")),
        // The relative location over the texture to overlay on the player screen when wearing
        // Points to assets/examplemod/textures/example_overlay.png
        // When set to an empty optional, does not render on the player screen
        Optional.of(ResourceLocation.withDefaultNamespace("examplemod", "example_overlay")),
        // A HolderSet of entities (direct or tag) that can equip this item
        // When set to an empty optional, any entity can equip this item
        Optional.of(HolderSet.direct(EntityType::builtInRegistryHolder, EntityType.ZOMBIE)),
        // Whether the item can be equipped when dispensed from a dispenser 
        true,
        // Whether the item can be swapped off the player during a quick equip
        false,
        // Whether the item should be damaged when attacked (for equipment typically)
        // Must also be a damageable item
        false
    ))
);
```

### Equipment Models?

So, as mentioned previously, `Equippable` items, and by extension the `ArmorMaterial` delegate, can specify a model id to determine how the equipment should render. However, what does this id link to? Well, it points to an `EquipmentModel` serialized as a JSON within `models/equipment` of the resource pack. This JSON defines the layers and textures of the equippable item to render. This does NOT specify the model, making the record a misnomer. It is better to think of this as the equipment textures applied to the passed in model.

`EquipmentModel` functions as a more feature generic version of the previous `ArmorMaterial$Layer`, which has been removed. Each `EquipmentModel` is functionally a map of `$LayerType`s to a list of `$Layer` to render.

A `$LayerType` is an enum representing the layer to render the equipment model as. While these are non-specific, they are implemented and read by specific entity renderer through the layer renderers. For example, `HUMANOID` is used by the `HumanoidArmorLayer` to render the head, chest, and feet; so any usage of `HUMANOID` will be rendered using that system. Another example is `WOLF_BODY` is used by `WolfArmorLayer` to render the body armor. As such, if using existing layer types (which is the only scenario unless your mod loader supports enum extensions), make sure that they are compatible with the existing renderers in place.

The `$Layer` list specifies the texture and dyeable options to use when rendering over the passed in model. The first parameter specifes the texture location, relative to `textures/entity/equipment`. The second parameter specifies an optional indicating whether the texture can be tinted (stored via the `ItemTags#DYEABLE` in conjunction with the `DYED_COLOR` data component). When specified, an optional color can be specified for when the item is not dyed. If empty, the armor will be invisible when not dyed. The final parameter indicates whether it should use the texture provided to the renderer instead, such as when rendering a custom elytra texture for the player.

```json5
// In assets/examplemod/models/equipment/example_armor_material.json
{
    // The layer map
    "layers": {
        // The serialized name of the EquipmentModel$LayerType to apply to
        "humanoid": [
            // A list of layers to render in the order provided in the list
            {
                // The relative texture of the layer
                // Points to assets/examplemod/textures/entity/equipment/example.png
                "texture": "examplemod:example",
                // When specified, allows the texture to be tinted the color in DYED_COLOR data component
                // Otherwise, cannot be tinted
                "dyeable": {
                    // An RGB value (always opaque color)
                    // 0x7683DE as decimal
                    // When not specified, set to 0 (meaning transparent or invisible)
                    "color_when_undyed": 7767006
                },
                // When true, uses the texture passed into the layer renderer instead
                "use_player_texture": true
            }
            // ...
        ]
        // ...
    }
}
```

```java
EquipmentModel.builder()
    .addLayers(EquipmentModel.LayerType.HUMANOID, new EquipmentModel.Layer(
        // The relative texture of the layer
        // Points to assets/examplemod/textures/entity/equipment/example.png
        ResourceLocation.fromNamespaceAndPath("examplemod", "example"),
        // When specified, allows the texture to be tinted the color in DYED_COLOR data component
        // Otherwise, cannot be tinted
        Optional.of(new EquipmentModel.Dyeable(
            // An RGB value (always opaque color)
            // When not specified, set to 0 (meaning transparent or invisible)
            Optional.of(0x7683DE)
        )),
        // When true, uses the texture passed into the layer renderer instead
        true
    )/*, ... */)
    .build();
```

The equipment model is then rendered by calling `EquipmentLayerRenderer#renderLayers` in the render function of an `EntityRenderer` or `RenderLayer`. `EquipementLayerRenderer` is passed in as part of the render context via `EntityRendererProvider$Context#getEquipmentRenderer`.

```java
// In some render method where EquipmentLayerRenderer equipmentLayerRenderer is a field
this.equipmentLayerRenderer.renderLayers(
    // The layer type to render
    EquipmentModel.LayerType.HUMANOID,
    // The model id representing the EquipmentModel JSON
    // This would be set in the `EQUIPPABLE` data component via `model`
    ResourceLocation.fromNamespaceAndPath("examplemod", "example_armor_material"),
    // The model to apply the textures to
    // These are usually separate models from the entity model
    // and are separate ModelLayers linking to a LayerDefinition
    model,
    // The item stack representing the item being rendered as a model
    // This is only used to get the dyeable, foil, and armor trim information
    stack,
    // The stack used to render the model in the correct location
    poseStack,
    // The source of the buffers to get the vertex consumer of the render type
    bufferSource,
    // The packed light texture
    lighting,
    // An absolute path of the texture to render when use_player_texture is true for one of the layer if not null
    ResourceLocation.fromNamespaceAndPath("examplemod", "textures/other_texture.png");
)
```

### Technical Changes to Items

- `net.minecraft.client.Minecraft#getEquipmentModels` - Gets the `EquipmentModelSet` that contains the current equipment model textures.
- `net.minecraft.client.gui.GuiGraphics#renderTooltip`, `renderComponentTooltip` now has a parameter to take in the relative directory of the background and frame textures of the tooltip, or the default if `null`
- `net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil#renderTooltipBackground` now has a parameter to take in the relative directory of the background and frame textures of the tooltip, or the default if `null`
- `net.minecraft.client.renderer.block.model`
    - `ItemOverrides` -> `BakedOverrides`
        - The construct no longer takes in the parent `BlockModel`
        - `resolve` -> `findOverride`, does not take in the fallback model
    - `ItemOverride`, `ItemOverride$Predicate` is now a record
        - `getPredicates` is removed, use `predicates`
        - `getModel` -> `model`
- `net.minecraft.client.renderer.entity`
    - `EntityRenderDispatcher` now takes in the `EquipmentModelSet`
    - `EntityRendererProvider$Context`
        - `getEquipmentModels` - Gets the current equipment textures.
        - `getEquipmentRenderer` - Gets the renderer for the equipment.
    - `ItemRenderer` no longer takes in the `Minecraft` instance and `TextureManager`
        - `TRIDENT_MODEL`, `SPYGLASS_MODEL` is now public
        - `TRIDENT_IN_HAND_MODEL`, `SPYGLASS_IN_HAND_MODEL` is removed
        - `getItemModelShaper` is removed
        - `renderBundleWithSelectedItem` -> `renderBundleItem`, not one-to-one
- `net.minecraft.client.renderer.entity.layers`
    - `CapeLayer` now takes in the `EquipmentModelSet`
    - `ElytraLayer` -> `WingsLayer`
        - The constructor now takes in the `EquipmentLayerRenderer`
    - `EquipmentLayerRenderer` - A renderer for equipment layers on the provided model.
    - `HorseArmorLayer` now takes in the `EquipmentLayerRenderer`
    - `HumanoidArmorLayer` now teaks in the `EquipmentLayerRenderer` instead of the `ModelManager`
        - `shouldRender` - Returns whether the equippable item should be rendered in the given slot.
    - `LlamaDecorLayer` now takes in the `EquipmentLayerRenderer`
    - `WolfArmorLayer` now takes in the `EquipmentLayerRenderer`
- `net.minecraft.client.renderer.entity.player.PlayerRenderer#getArmPose` is now private, replaced publically with a method that only takes in the `HumanoidArm` and `PlayerRenderState`
- `net.minecraft.client.resources.model`
    - `EquipmentModelSet` - A resource listener that loads the `EquipmentModel`s from `models/equipment`.
    - `ItemModel` - A model for an item.
- `net.minecraft.core.component.DataComponents`
    - `ITEM_MODEL` - Returns the model of the item. The `item/` is stripped, meaning that `minecraft:apple` points to `minecraft/textures/models/item/apple.json`.
    - `EQUIPPABLE` - Indicates that an item is equippable in the given slot. Also contains the model to render for the equipment.
    - `GLIDER` - Indicates that an item can be used to glide across the air. Must also be used in conjunction with `EQUIPPABLE`.
    - `TOOLTIP_STYLE` - Determines the relative location representing how the tooltip should render
- `net.minecraft.core.dispenser.EquipmentDispenseItemBehavior` - Handles how equipment is dispensed from a dispenser.
- `net.minecraft.core.registries.BuiltInRegistries#`, `Registries#ARMOR_MATERIAL` is no longer a registry, handled purely through data components
- `net.minecraft.world.entity`
    - `EquipmentSlot#getFilterFlag` -> `getId`
        - Also a method `getFilterBit` for converting the ID to a bit mask
    - `LivingEntity`
        - `canContinueToGlide` -> `canGlide`, no longer takes in the `ItemStack`
        - `canTakeItem` replaced by `DataComponents#EQUIPPABLE`
        - `canEquipWithDispenser` - Returns whether the stack can be equipped when spat from a dispenser.
        - `canDispenserEquipIntoSlot` - An entity override that specifies whether a dispenser can put eequipment into a given slot.
        - `isEquippableInSlot` - Returns whether the stack can be equipped in the given slot.
        - `canGlideUsing` - Whether the entity can glide with the stack in the provided slot.
    - `Mob`
        - `canReplaceCurrentItem` now takes in the `EquipmentSlot`
        - `isBodyArmorItem` replaced by `DataComponents#EQUIPPABLE`
- `net.minecraft.world.entity.animal.horse`
    - `Horse#isBodyArmorItem` replaced by `DataComponents#EQUIPPABLE`
    - `Llama#isBodyArmorItem`, `getSwag` replaced by `DataComponents#EQUIPPABLE`
- `net.minecraft.world.item`
    - `AnimalArmorItem` no longer extends `ArmorItem`
        - The constructor no longer takes in a boolean indicating the overlay texture, as that is now part of the `EquipmentModel`
        - The constructor can take in an optional `Holder<SoundEvent>` of the equip sound
        - The constructor can take in a `boolean` representing whether the armor should be damaged if the entity is hurt
        - `$BodyType` no takes in the allowed entities to wear the armor rather than the path factory to the texture
    - `ArmorItem` is no longer equipable
        - Basically functions as an item class where its only remaining usage is to prevent armor change when enchanted and get the associated attributes
        - `$Type` -> `ArmorType`
    - `ArmorMaterial` -> `.equipment.ArmorMaterial`
        - Bascially a dummy record to easily handle applying the associated data components (`MAX_DAMAGE`, `ATTRIBUTE_MODIFIERS`, `ENCHANTABLE`, `EQUIPPABLE`, `REPAIRABLE`)
    - `ArmorMaterials` -> `.equipment.ArmorMaterials`
    - `BookItem`, `EnchantedBookItem` -> `DataComponents#WRITTEN_BOOK_CONTENT`
    - `BundleItem` now takes in a `ResourceLocation` for the model rather than just strings
        - `$Mutable#setSelectedItem` -> `toggleSelectedItem`
    - `ComplexItem` class is removed
    - `ElytraItem` class is removed, now just and item with `DataComponents#GLIDER`
    - `Equippable` -> `.equipment.Equippable`, now a record which defines how an item can be equipped
    - `FoodOnAStackItem` parameter order has been switched
    - `InstrumentItem` parameter order has been switched
    - `Item`
        - `descriptionId` is now protected
        - `getDescription` -> `getName`
        - `getOrCreateDescriptionId` is removed
        - `getDescriptionId(ItemStack)` -> `DataComponents#ITEM_NAME`
        - `isEnchantable`, `getEnchantmentValue` is removed
        - `isValidRepairItem` is removed
        - `getDefaultAttributeModifiers` is removed
        - `getDamageSource` - Returns the damage source this item makes against the `LivingEntity`
        - `isComplex` is removed
        - `$Properties`
            - `equippable` - Sets an equippable component, defining how an item can be equipped
            - `equippableUnswappable` - Sets an equippable commponent that cannot be swapped via a key shortcut.
            - `overrideDescription` - Sets the translation key of the item.
            - `overrideModel` - Sets the model resource location.
        - `getCraftingRemainingItem`, `hasCraftingRemainingItem` -> `getCraftingRemainder`
    - `ItemNameBlockItem` class is removed, just a normal `Item` `useItemDescriptionPrefix` as a property
    - `ItemStack`
        - `ITEM_NON_AIR_CODEC` -> `Item#CODEC`
        - `isValidRepairItem` - Returns whether the stack can be repaired by this stack.
        - `nextDamageWillBreak` - Checks if the next damage taken with break the item.
        - `getDescriptionId` -> `getItemName`, not one-to-one, as now it returns the full component
    - `ShieldItem` no longer implements `Equippable`, passed in through `DataComponents#EQUIPPABLE`
    - `SignItem` parameter order has been switched
    - `SmithingTemplateItem` parameter order has been swtiched, removes `FeatureFlag`s
    - `StandingAndWallBlockItem` paramter order has been switched
    - `AxeItem` now takes in two floats representing the attack damage and attack speed
    - `DiggerItem` now takes in two floats representing the attack damage and attack speed
        - `createAttributes` -> `ToolMaterial#applyToolProperties`
    - `HoeItem` now takes in two floats representing the attack damage and attack speed
    - `PickaxeItem` now takes in two floats representing the attack damage and attack speed
    - `ShovelItem` now takes in two floats representing the attack damage and attack speed
    - `SwordItem` now takes in two floats representing the attack damage and attack speed
        - `createAttributes` -> `ToolMaterial#applySwordProperties`
    - `Tier` -> `ToolMaterial`
    - `TieredItem` class is removed
    - `Tiers` constants are stored on `ToolMaterial`
- `net.minecraft.world.item.alchemy.Potion` name is now required
        - `getName` -> `name`, not one-to-one as this is stored directly on the potion without any other processing
- `net.minecraft.world.item.armortrim.*` -> `.equipment.trim.*`
- `net.minecraft.world.item.component`
    - `Tool` methods that return `Tool$Rule` now only take the `HolderSet` of blocks and not a list or tag key
    - `DamageResistant` - A component that holds a tag of damage types the item is resistant to as an entity or being worn
- `net.minecraft.world.item.enchantment`
    - `Enchantable` - The data component object for the item's enchantment value.
    - `Repairable` - The data component object for the items that can repair this item.
- `net.minecraft.world.level.block`
    - `AbstractSkullBlock` no longer implements `Equippable`
    - `EquipableCarvedPumpkinBlock` class is removed, as replaced by `DataComponents#EQUIPPABLE`
    - `WoolCarpetBlock` no longer implements `Equippable`

## Interaction Results

`InteracitonResult`s have been completely modified to encompass everything to one series of sealed implementations. The new implementation of `InteractionResult` combines both `InteractionResultHolder` and `ItemInteractionResult`, meaning that all uses have also been replcaed.

`InteractionResult` is now an interface with four implementations depending on the result type. First there is `$Pass`, which indicates that the interaction check should be passed to the next object in the call stack. `$Fail`, when used for items and blocks, prevents anything further in the call stack for executing. For entities, this is ignored. Finally, `$TryEmptyHandInteraction` tells the call stack to try to apply the click with no item in the hand, specifically for item-block interactions.

There is also `$Success`, which indicates that the interaction was successful and can be consumed. A success specifies two pieces of information: the `$SwingSource`, which indicates where the source of the swing originated from (`CLIENT` or `SERVER`) or `NONE` if not specified, and `$ItemContext` that handles whether there was an iteraction with the item in the hand, and what the item was transformed to.

None of the objects should be directly initialized. The implementations are handled through six constants on the `InteractionResult` interface:

- `SUCCESS` - A `$Success` object that swings the hand on the client.
- `SUCCESS_SERVER` - A `$Success` object that swings the hand on the server.
- `CONSUME` - A `$Success` object that does not swing the hand.
- `FAIL` - A `$Fail` object.
- `PASS` - A `$Pass` object.
- `TRY_WITH_EMPTY_HAND` - A `$TryEmptyHandInteraction` object.

```java
// For some method that returns an InteractionResult
return InteractionResult.PASS;
```

For success objects, if the item interaction should transform the held stack, then you call `$Success#heldItemTransformedTo`, or `$Success#withoutItem` if no item was used for the interaction.

```java
// For some method that returns an InteractionResult
return InteractionResult.SUCCESS.heldItemTransformedTo(new ItemStack(Items.APPLE));

// Or
return InteractionResult.SUCCESS.withoutItem();
```

- `net.minecraft.core.cauldron.CauldronInteraction`
    - `interact` now returns an `InteractionResult`
    - `fillBucket`, `emptyBucket` now returns an `InteractionResult`
- `net.minecraft.world`
    - `InteractionResultHolder`, `ItemInteractionResult` -> `InteractionResult`
- `net.minecraft.world.item`
    - `Equipable#swapWithEquipmentSlot` now returns an `InteractionResult`
    - `Item#use`, `ItemStack#use` now returns an `InteractionResult`
    - `ItemUtils#startUsingInstantly` now returns an `InteractionResult`
    - `JukeboxPlayable#tryInsertIntoJukebox` now returns an `InteractionResult`
- `net.minecraft.world.level.block.state.BlockBehaviour#useItemOn`, `$BlockStateBase#useItemOn` now returns an `InteractionResult`

## Instruments, the Datapack Edition

`Instrument`s (not `NoteBlockInstrument`s) are now a datapack registry, meaning they must be defined in JSON or datagenned.

```json5
// In data/examplemod/instrument/example_instrument.json
{
    // The registry name of the sound event
    "sound_event": "minecraft:entity.arrow.hit",
    // How many seconds the instrument is used for
    "use_duration": 7.0,
    // The block range, where each block is 16 units
    "range": 256.0,
    // The description of the instrument
    "description": {
        "translate": "instrument.examplemod.example_instrument"
    },
}
```

```java
// For some RegistrySetBuilder builder
builder.add(Registries.INSTRUMENT, bootstrap -> {
    bootstrap.register(
        ResourceKey.create(Registries.INSTRUMENT, ResourceLocation.fromNamespaceAndPath("examplemod", "example_instrument")),
        new Instrument(
            BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.ARROW_HIT),
            7f,
            256f,
            Component.translatable(Util.makeDescriptionId("instrument", ResourceLocation.fromNamespaceAndPath("examplemod", "example_instrument")))
        )
    )
});
```

- `net.minecraft.world.item`
    - `Instrument` takes in a `float` for the use duration and a `Component` description.
    - `InstrumentItem#setRandom` is removed

## Trial Spawner Configurations, now in Datapack Form

`TrialSpawnConfig` are now a datapack registry, meaning they must be defined in JSON or datagenned.

```json5
// In data/examplemod/trial_spawner/example_config.json
{
    // The range the entities can spawn from the trial spawner block
    "spawn_range": 2,
    // The total number of mobs that can be spawned
    "total_mobs": 10.0,
    // The number of mobs that can be spawned at one time
    "simultaneous_mobs": 4.0,
    // The number of mobs that are added for each player in the trial
    "total_mobs_added_per_player": 3.0,
    // The number of mobs that can be spawned at one time that are added for each player in the trial
    "simultaneous_mobs_added_per_player": 2.0,
    // The ticks between each spawn
    "ticks_between_spawn": 100,
    // A weighted list of entities to select from when spawning
    "spawn_potentials": [
        {
            // The SpawnData
            "data": {
                // Entity to spawn
                "entity": {
                    "id": "minecraft:zombie"
                }
            },
            // Weighted value
            "weight": 1
        }
    ],
    // A weight list of loot tables to select from when the reward is given
    "loot_tables_to_eject": [
        {
            // The loot key
            "data": "minecraft:spawners/ominous/trial_chamber/key",
            // Weight value
            "weight": 1
        }
    ],
    // Returns the loot table to use when the the trial spawner is ominous
    "items_to_drop_when_ominous": "minecraft:shearing/bogged"
}
```

```java
// For some RegistrySetBuilder builder
builder.add(Registries.TRIAL_SPAWNER_CONFIG, bootstrap -> {
    var entityTag = new CompoundTag();
    entityTag.putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.ZOMBIE).toString());

    bootstrap.register(
        ResourceKey.create(Registries.INSTRUMENT, ResourceLocation.fromNamespaceAndPath("examplemod", "example_config")),
        TrialSpawnerConfig.builder()
            .spawnRange(2)
            .totalMobs(10.0)
            .simultaneousMobs(4.0)
            .totalMobsAddedPerPlayer(3.0)
            .simultaneousMobsAddedPerPlayer(2.0)
            .ticksBetweenSpawn(100)
            .spawnPotentialsDefinition(
                SimpleWeightedRandomList.single(new SpawnData(entityTag, Optional.empty(), Optional.empty()))
            )
            .lootTablesToEject(
                SimpleWeightedRandomList.single(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_KEY)
            )
            .itemsToDropWhenOminous(
                BuiltInLootTables.BOGGED_SHEAR
            )
            .build()
    )
});
```

- `net.minecraft.world.level.block.entity.trialspawner`
    - `TrialSpawner` now takes in a `Holder` of the `TrialSpawnerConfig`
        - `canSpawnInLevel` now takes in a `ServerLevel`
    - `TrialSpawnerConfig`
        - `CODEC` -> `DIRECT_CODEC`
        - `$Builder`, `builder` - A builder for a trial spawner configuration

## Recipe Providers, the 'not actually' of Data Providers

`RecipeProvider` is no longer a `DataProvider`. Instead, a `RecipeProvider` is constructed via a `RecipeProvider$Runner` by implementing `createRecipeProvider`. The name of the provider must also be specified.

```java
public class MyRecipeProvider extends RecipeProvider {

    // The parameters are stored in protected fields
    public MyRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        // Register recipes here
    }

    // The runner class, this should be added to the DataGenerator as a DataProvider
    public static class Runner extends RecipeProvider.Runner {

        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super(output, registries)
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new VanillaRecipeProvider(registries, output);
        }

        @Override
        public String getName() {
            return "My Recipes";
        }
    }
}
```

- `net.minecraft.data.recipes`
    - `RecipeOutput#includeRootAdvancement` - Generates the root advancement for recipes.
    - `RecipeProvider` no longer extends `DataProvider`
        - The constructor takes in the lookup provider and a `RecipeOutput`, which are protected fields
        - `buildRecipes` does not take in any parameters
        - All generation methods do not take in a `RecipeOutput` and are instance methods
        - `$FamilyRecipeProvider` - Creates a recipe for a `BlockFamily` by passing in the `Block` the resulting block and the base block.
        - `$Runner` - A `DataProvider` that constructs the `RecipeProvider` via `createRecipeProvider`
    - `ShapedRecipeBuilder`, `ShapelessRecipeBuilder` now have private constructors and take in a holder getter for the items

## The Ingredient Shift

`Ingredient`s have be reimplemented to use a `HolderSet` as its base rather that it own internal `Ingredient$Value`. This most changes the call to `Ingredient#of` as you either need to supply it with `Item` objects or the `HolderSet` representing the tag. For more information on how to do this, see the [holder set section](#the-holder-set-transition).

- `net.minecraft.world.item.crafting.Ingredient`
    - `EMPTY` -> `Ingredient#of`, though the default usecases do not allow empty ingredients
    - `CODEC` is removed
    - `CODEC_NONEMPTY` -> `CODEC`
    - `testOptionalIngredient` - Tests whether the stack is within the ingredient if present, or default to an empty check if not.
    - `getItems` -> `items`
    - `getStackingIds` is removed
    - `of(ItemStack...)`, `of(Stream<ItemStack>)` is removed
    - `of(TagKey)` -> `of(HolderSet)`, need to resolve tag key

## BlockEntityTypes Privatized!

`BlockEntityType`s have been completely privatized and the builder being removed! This means that if a mod loader or mod does not provide some access widening to the constructor, you will not be able to create new block entities. The only other change is that the `Type` for data fixers was removed, meaning that all that needs to be supplied is the client constructor and the set of valid blocks the block entity can be on.

```java
// If the BlockEntityType constructor is made public
// MyBlockEntity(BlockPos, BlockState) constructor
BlockEntityType<MyBlockEntity> type = new BlockEntityType(MyBlockEntity::new, MyBlocks.EXAMPLE_BLOCK);
```

## Consumables

Consuming an item has been further expanded upon, with most being transitioned into separate data component entries.

### The `Consumable` Data Component

The `Consumable` data component defines how an item is used when an item is finished being used. This effectively functions as `FoodProperties` used to previously, except all consumable logic is consolidated in this one component. A consumable has five properties: the number of seconds it takes to consume or use the item, the animation to play while consuming, the sound to play while consuming, whether particles should appear during consumption, and the [effects to apply once the consumption is complete](#consumeeffect).

A `Consumable` can be applied using the `food` item property. If only the `Consumable` should be added, then `component` should be called. A list of vanilla consumables and builders can be found in `Consumables`.

```java
// For some item
Item exampleItem = new Item(new Item.Properties().component(DataComponents.CONSUMABLE,
    Consumable.builder()
    .consumeSeconds(1.6f) // Will use the item in 1.6 seconds, or 32 ticks
    .animation(ItemUseAnimation.EAT) // The animation to play while using
    .sound(SoundEvents.GENERIC_EAT) // The sound to play while using the consumable
    .soundAfterConsume(SoundEvents.GENERIC_DRINK) // The sound to play after consumption (delegates to 'onConsume')
    .hasConsumeParticles(true) // Sets whether to display particles
    .onConsume(
        // When finished consuming, applies the effects with a 30% chance
        new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F)
    )
    // Can have multiple
    .onConsume(
        // Teleports the entity randomly in a 50 block radius
        new TeleportRandomlyConsumeEffect(100f)
    )
    .build()
));
```

#### `OnOverrideSound`

Sometimes, an entity may want to play a different sound when consuming an item. In that case, the entity can implement `Consumable$OverrideConsumeSound` and return the sound event that should be played.

```java
// On your own entity
public class MyEntity extends Mob implements Consumable.OverrideCustomSound {
    // ...

    @Override
    public SoundEvent getConsumeSound(ItemStack stack) {
        // Return the sound event to play
    }
}
```

### `ConsumableListener`

`ConsumableListener`s are data components that indicate an action to apply once the stack has been 'consumed'. This means whenever `Consumable#consumeTicks` has passed since the player started using the consumable. An example of this would be `FoodProperties`. `ConsumableListener` only has one method `#onConsume` that takes in the level, entity, stack doing the consumption, and the `Consumable` that has finished being consumed.

```java
// On your own data component
public record MyDataComponent() implements ConsumableListener {

    // ...

    @Override
    public void onConsume(Level level, LivingEntity entity, ItemStack stack, Consumable consumable) {
        // Perform stuff once the item has been consumed.
    }
}
```

### `ConsumeEffect`

There is now a data component that handles what happens when an item is consumed by an entity, aptly called a `ConsumeEffect`. The current effects range from adding/removing mob effects, teleporting the player randomly, or simply playing a sound. These are applied by passing in the effect to the `Consumable` or `onConsume` in the builder.

```java
// When constructing a consumable
Consumable exampleConsumable = Consumable.builder()
    .onConsume(
        // When finished consuming, applies the effects with a 30% chance
        new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F)
    )
    // Can have multiple
    .onConsume(
        // Teleports the entity randomly in a 50 block radius
        // NOTE: CURRENTLY BUGGED, only allows for 8 block raidus
        new TeleportRandomlyConsumeEffect(100f)
    )
    .build();
```

### On Use Conversion

Converting an item into another stack on consumption is now handled through `DataComponents#USE_REMAINDER`. The remainder will only be converted if the stack is empty after this use. Otherwise, it will return the current stack, just with one item used.

```java
// For some item
Item exampleItem = new Item(new Item.Properties().usingConvertsTo(
    Items.APPLE // Coverts this into an apple on consumption
)); 
Item exampleItem2 = new Item(new Item.Properties().component(DataComponents.USE_REMAINDER,
    new UseCooldown(
        new ItemStack(Items.APPLE, 3) // Converts into three apples on consumption
    )
));
```

### Cooldowns

Item cooldowns are now handled through `DataComponents#USE_COOLDOWN`; however, they have been expanded to apply cooldowns to stacks based on their defined group. A cooldown group either refers to the `Item` registry name if not specified, or a custom resource location. When applying the cooldown, it will store the cooldown instance on anything that matches the defined group. This means that, if a stack has some defined cooldown group, it will not be affected when a normal item is used.

```java
// For some item
Item exampleItem = new Item(new Item.Properties().useCooldown(
    60 // Wait 60 seconds
    // Will apply cooldown to items in the 'my_mod:example_item' group (assuming that's the registry name)
)); 
Item exampleItem2 = new Item(new Item.Properties().component(DataComponents.USE_COOLDOWN,
    new UseCooldown(
        60, // Wait 60 seconds
        // Will apply cooldown to items in the 'my_mod:custom_group' group
        Optional.of(ResourceLocation.fromNamespaceAndPath("my_mod", "custom_group"))
    )
));
```

- `net.minecraft.core.component.DataComponents#FOOD` -> `CONSUMABLE`
- `net.minecraft.world.entity.LivingEntity`
    - `getDrinkingSound`, `getEatingSound` is removed, handled via `ConsumeEffect`
    - `triggerItemUseEffects` is removed
    - `eat` is removed
- `net.minecraft.world.entity.npc.WanderingTrader` now implements `Consumable$OverrideConsumeSound`
- `net.minecraft.world.food`
    - `net.minecraft.world.food.FoodData`
        - `tick` now takes in a `ServerPlayer`
        - `getLastFoodLevel`, `getExhaustionLevel`, `setExhaustion` is removed
    - `FoodProperties` is now a `ConsumableListener`
        - `eatDurationTicks`, `eatSeconds` -> `Consumable#consumeSeconds`
        - `usingConvertsTo` -> `DataComponents#USE_REMAINDER`,
        - `effects` -> `ConsumeEffect`
- `net.minecraft.world.item`
    - `ChorusFruitItem` class is removed
    - `HoneyBottleItem` class is removed
    - `Item`
        - `getDrinkingSound`, `#getEatingSound` is removed, handled via `ConsumeEffect`
        - `releaseUsing` now returns a `boolean` whether it was successfully released
        - `$Properties#food` can now take in a `Consumable` for custom logic
        - `$Properties#usingConvertsTo` - The item to convert to after use.
        - `$Properties#useCooldown` - The amount of seconds to wait before the item can be used again.
    - `ItemCooldowns` now take in `ItemStack`s or `ResourceLocation`s to their methods rather than just an `Item`
        - `getCooldownGroup` - Returns the key representing the group the cooldown is applied to
    - `ItemStack#getDrinkingSound`, `getEatingSound` is removed
    - `MilkBucketItem` class is removed
    - `OminousBottleItem` class is removed
    - `SuspiciousStewItem` class is removed
- `net.minecraft.world.item.alchemy.PotionContents` now implements `ConsumableListener`
    - The constructor takes in an optional string representing the translation key suffix of the custom name
    - `applyToLivingEntity` - Applies all effects to the provided entity.
    - `getName` - Gets the name component by appending the custom name to the end of the provided contents string.
- `net.minecraft.world.item.component`
    - `Consumable` - A data component that defines when an item can be consumed.
    - `ConsumableListener` - An interface applied to data components that can be consumed, executes once consumption is finished.
    - `SuspiciousStewEffects` now implements `ConsumableListener`
    - `UseCooldown` - A data component that defines how the cooldown for a stack should be applied.
    - `UseRemainder` - A data component that defines how the item should be replaced once used up.
    - `DeathProtection` - A data component that contains a list of `ConsumeEffect`s on what to do when using the item to survive death.
- `net.minecraft.world.item.consume_effects.ConsumeEffect` - An effect to apply after the item has finished being consumed.

## Registry Objcet Id, in the Properties?

When providing the `BlockBehaviour$Properties` to the `Block` or the `Item$Properties` to the `Item`, it must set the `ResourceKey` in the block directly by calling `#setId`. An error will be thrown if this is not set before passing in.

```java
new Block(BlockBehaviour.Properties.of()
    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("examplemod", "example_block"))));

new BlockItem(exampleBlock, new Item.Properties()
    .useBlockDescriptionPrefix() // Makes the description id for a block item
    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("examplemod", "example_item"))));

new Item(new Item.Properties()
    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("examplemod", "example_item"))));
```

- `net.minecraft.world.item.Item$Properties`
    - `setId` - Sets the resource key of the item to get the default description and model from. This property must be set.
    - `useBlockDescriptionPrefix` - Creates the description id using the `block.` prefix.
    - `useItemDescriptionPrefix` - Creates the description id using the `item.` prefix.
- `net.minecraft.world.level.block.state.BlockBehaviour$Properties#setId` - Sets the resource key of the block to get the default drops and description from. This property must be set.

## Properties Changes

`DirectionProperty` has been removed, and must now be called and referenced via `EnumProperty#create` with a `Direction` generic. Additionally, all property classes have been made final and must be constructed through one of the exposed `create` methods.

- `net.minecraft.world.level.block.state.properties`
    - `BooleanProperty` is now final
    - `DirectionProperty` class is removed
    - `EnumProperty` is now final
        - `create` now takes in a `List` instead of a `Collection`
    - `IntegerProperty` is now final
    - `Property#getPossibleValues` now returns a `List` instead of a `Collection`

## Recipes, now in Registry format

Recipes have been upgraded to a data pack registry, similar to how loot tables are handled. They are still queried in the same fashion, it just simply using a pseudo-registry-backed instance. Some of the more common changes is that `RecipeHolder` may be replaced by `RecipeDisplayId`, `RecipeDisplay`, or `RecipeDisplayEntry` if the holder itself is not needed. With this, there are a few changes to how recipe books are handled.

### Recipe Books

`RecipeBookComponent`s have been modified somewhat to hold a generic instance of the menu to render. As such, the component no longer implements `PlacedRecipe` and instead takes in a generic representing the `RecipeBookMenu`. The menu is passed into the component via its constructor instead of through the `init` method. This also menas that `RecipeBookMenu` does not have any associated generics. To create a component, the class needs to be extended.

```java
// Assume some MyRecipeMenu extends AbstractContainerMenu
public class MyRecipeBookComponent extends RecipeBookComponent<MyRecipeMenu> {

    public MyRecipeBookComponent(MyRecipeMenu menu, List<RecipeBookComponent.TabInfo> tabInfos) {
        super(menu, tabInfos);
        // ...
    }

    @Override
    protected void initFilterButtonTextures() {
        // ...
    }

    @Override
    protected boolean isCraftingSlot(Slot slot) {
        // ...
    }

    @Override
    protected void selectMatchingRecipes(RecipeCollection collection, StackedItemContents contents) {
        // ...
    }

    @Override
    protected Component getRecipeFilterName() {
        // ...
    }

    @Override
    protected void fillGhostRecipe(GhostSlots slots, RecipeDisplay display, ContextMap ctx) {

    }
}

public class MyContainerScreen extends AbstractContainerScreen<MyRecipeMenu> implements RecipeUpdateListener {

    public MyContainerScreen(MyRecipeMenu menu, List<RecipeBookComponent.TabInfo> tabInfos, ...) {
        super(menu, ...);
        this.recipeBookComponent = new MyRecipeBookComponent(menu, tabInfos);
    }
    

    // See AbstractFurnaceScreen for a full implementation
}
```

### Recipe Displays

However, how does a recipe understand what should be displayed in a recipe book? This falls under two new static registries: the `RecipeDisplay` and the `SlotDisplay`.

The `SlotDisplay` represents what displays in a single slot within a recipe. The display only has one method (ignoring types): `resolve`. `resolve` takes in the `ContextMap` holding the data and the `DisplayContentsFactory` which accepts the stacks and remainders that will be displayed in this slot. `SlotDisplay` also has a lot of helper implementations, such as `$Composite` that takes in a list of displays or `$ItemStackSlotDisplay` that takes in the stack to display. The display is registered by its `$Type`, which takes in the map codec and stream codec.

The slot also has methods to get for the associated stacks that can be displayed via `resolveForStacks` and `resolveForFirstStack`.

```java
public static record MySlotDisplay() implements SlotDisplay {

    @Override
    public <T> Stream<T> resolve(ContextMap ctx, DisplayContentsFactory<T> output) {
        // Call output.forStack(...) or addRemainder(..., ...) using instanceof to display items
        if (output instanceof ForStacks<T> stacks) {
            stacks.forStack(...);
        } else if (output instanceof ForRemainders<T> remainders) {
            remainders.addRemainder(..., ...);
        }
    }

    @Override
    public SlotDisplay.Type<? extends SlotDisplay> type() {
        // Return the registered object here registered to Registries#SLOT_DISPLAY
    }
}
```

`RecipeDisplay` represents how a recipe is displayed. As an implementation detail, the `RecipeDisplay` only needs to be aware of the result (via `result` slot display) and the place the recipe is being used (via `craftingStation` slot display) as those are the only two details the recipe book cares about. However, it is recommended to also have slot displays for the ingredients and then have those consumed by your `RecipeBookComponent`. The display is registered by its `$Type`, which takes in the map codec and stream codec.


```java
public record MyRecipeDisplay(SlotDisplay result, SlotDisplay craftingStation, ...) implements RecipeDisplay {

    @Override
    public RecipeDisplay.Type<? extends RecipeDisplay> type() {
        // Return the registered object here registered to Registries#RECIPE_DISPLAY
    }
}
```

### Recipe Placements

Recipe ingredients and placements within the recipe book are now handled through `Recipe#placementInfo`. A `PlacementInfo` is basically a definition of items the recipe contains and where they should be placed within the menu if supported. If the recipe cannot be placed, such as if it is not an `Item` or uses stack information, then it should return `PlacementInfo#NOT_PLACEABLE`.

A `PlacementInfo` can be created either from an `Ingredient`, a `List<Ingredient>`, or a `List<Optional<Ingredient>>` using `create` or `createFromOptionals`, respectively.

```java
public class MyRecipe implements Recipe<RecipeInput> {

    private PlacementInfo info;

    public MyRecipe(Ingredient input) {
        // ...
    }

    // ...

    @Override
    public PlacementInfo placementInfo() {
        // This delegate is done as the HolderSet backing the ingredient may not be fully populated in the constructor
        if (this.info == null) {
            this.info = PlacementInfo.create(input);
        }

        return this.info;
    }
}
```

If an `Optional<Ingredient>` is used, they can be tested via `Ingredient#testOptionalIngredient`.

- `net.minecraft.world.item.crafting`
    - `Ingredient#display` - Returns the `SlotDisplay` that shows this ingredient.
    - `PlacementInfo` - Defines all ingredients necessary to construct the result of a recipe.
    - `Recipe`
        - `getToastSymbol` -> `getCategoryIconItem`
        - `getIngredients`, `isIncomplete` -> `placementInfo`
            - `getIngredients` -> `PlacementInfo#stackedRecipeContents`,
            - `isIncomplete` -> `PlacementInfo#isImpossibleToPlace`
    - `RecipeManager#getSynchronizedRecipes` - Returns all recipes that can be placed and sends them to the client. No other recipes are synced.
    - `ShapedRecipePattern` now takes in a `List<Optional<Ingredient>>` instead of a `NonNullList<Ingredient>`
    - `ShapelessRecipe` now takes in a `List<Ingredient>` instead of a `NonNullList<Ingredient>`
    - `SmithingTransformRecipe`, `SmithingTrimRecipe` now takes in `Optional<Ingredient>`s instead of `Ingredient`s
    - `SuspiciousStewRecipe` class is removed

### Recipe Changes

There have been a few changes within the recipe class itself, which mirror all of the above changes. First, `canCraftInDimensions` is removed and now hardcoded into the match function. `getResultItem` and `getCategoryIconItem` has been replaced by `RecipeDisplay` via `display`. `getRemainingItems` has moved to `CraftingRecipe`. Finally, all recipes now return their `RecipeBookCategory` via `recipeBookCategory`.

```java
public class MyRecipe implements Recipe<RecipeInput> {

    @Override
    public String group() {
        // Return here what `getGroup` was
    }

    @Override
    public List<RecipeDisplay> display() {
        return List.of(
            // Some recipe display instance
            // RecipeDisplay#result should return `getResultItem`
            // RecipeDisplay#craftingStation should return `getCategoryIconItem`
        )
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        // Functions similar to the book category passed into the recipe builders during data generation
        return RecipeBookCategories.CRAFTING_MISC;
    }
}
```

### Creating Recipe Book Categories

Recipe book categories are unified by `ExtendedRecipeBookCategory` and split into two sections: `RecipeBookCategory` for actual categories, and `SearchRecipeBookCategory` for aggregate categories. While `SearchRecipeBookCategory`s are enums, `RecipeBookCategory` is like any other static registry object. This is done by creating a new `RecipeBookCategory`.

```java
// Using the standard vanilla registry method
public static final RecipeBookCategory EXAMPLE_CATEGORY = Registry.register(
    BuiltInRegistries.RECIPE_BOOK_CATEGORY,
    // The registry object name
    ResourceLocation.fromNamespaceAndPath("examplemod", "example_category"),
    // This creates a new recipe book category. It functions as a marker object.
    new RecipeBookCategory()
);
```

### Technical Changes

- `net.minecraft.advancements.AdvancementRewards` now takes in a list of `ResourceKey`s instead of `ResourceLocation`s for the recipe
    - `$Builder#recipe`, `addRecipe` now takes in a `ResourceKey`
- `net.minecraft.advancements.critereon`
    - `PlayerPredicate` now takes in a `ResourceKey` for the recipe map
        - `$Builder#addRecipe` now takes in a `ResourceKey`
    - `RecipeCraftedTrigger`
        - `trigger` now takes in a `ResourceKey`
        - `$TriggerInstance` now takes in a `ResourceKey`
        - `$TriggerInstance#craftedItem`, `crafterCraftedItem` now takes in a `ResourceKey`
    - `RecipeUnlockedTrigger`
        - `unlocked` now takes in a `ResourceKey`
        - `$TriggerInstance` now takes in a `ResourceKey`
- `net.minecraft.client`
    - `ClientRecipeBook`
        - `setupCollections` -> `rebuildCollections`, not one-to-one
        - `getCollection(RecipeBookCategories)` -> `getCollection(ExtendedRecipeBookCategory)`
        - `add`, `remove` - Handles adding/removing a recipe entry to display within the recipe book.
        - `addHighLight`, `removeHighlight`, `hasHighlight` - Handles if the entry is highlighted when filtered or selected by the player.
        - `clear` - Clears the known and highlighted recipes.
    - `RecipeBookCategories#*_MISC` -> `SearchRecipeBookCategory#*`
        - This can also be replaced within methods by `RecipeBookComponent$TabInfo`, `ExtendedRecipeBookCategory`, or `RecipeBookCategory`
- `net.minecraft.client.gui.components.toasts`
    - `RecipeToast(RecipeHolder)` -> `RecipeToast()`, now private
    - `addOrUpdate` now takes in a `RecipeDisplay` instead of a `RecipeHolder`
- `net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen`
    - `recipeBookComponent` is now private
    - `AbstractFurnaceScreen(T, AbstractFurnaceRecipeBookComponent, Inventory, Component, ResourceLocation, ResourceLocation, ResourceLocation)` - `AbstractFurnaceRecipeBookComponent` has been replaced with a `Component` as the recipe book is not constructed internally and now takes in a list of `RecipeBookComponent$TabInfo`
- `net.minecraft.client.gui.screens.recipebook`
    - `AbstractFurnaceReipceBookComponent`, `BlastingFurnaceReipceBookComponent`, `SmeltingFurnaceReipceBookComponent`, `SmokingFurnaceReipceBookComponent` -> `FurnaceReipceBookComponent`
    - `GhostRecipe` -> `GhostSlots` not one-to-one, as the recipe itself is stored as a private field in `RecipeBookComponent` as a `RecipeHolder`
        - `addResult` -> `setResult`, not one-to-one
        - `addIngredient` -> `setIngredient`, not one-to-one
        - `setSlot`, `setInput`, `setResult` now take in a `ContextMap`
    - `OverlayRecipeComponent()` -> `OverlayRecipeComponent(SlotSelectTime, boolean)`
        - `init` takes in a `ContextMap` containing registry data to display within the components and a `boolean` representing whether the recipe book is filtering instead of computing it from the `Minecraft` instance
        - `getLastRecipeClicked` now returns a `RecipeDisplayId`
        - `$OverlayRecipeButton` is now an abstract package-private class, taking in the `ContextMap`
        - `$Pos` is now a record
    - `RecipeBookComponent` no longer implements `RecipeShownListener`
        - The constructor takes in a list of `$TabInfo`s containing the tabs shown in the book
        - `init` no longer takes in a `RecipeBookMenu`
        - `initVisuals` is now private
        - `initFilterButtonTextures` is now abstract
        - `updateCollections` now takes in another boolean representing if the book is filtering
        - `renderTooltip` now takes in a nullable `Slot` instead of an `int` representing the slot index
        - `renderGhostRecipe` no longer takes in a float representing the delay time
        - `setupGhostRecipe` -> `fillGhostRecipe`, no longer takes in the `List<Slot>` to place, that is stored within the component itself
        - `selectMatchingRecipes` no longer takes in the `RecipeBook`
        - `recipesShown` now takes in a `RecipeDisplayId`
        - `setupGhostRecipeSlots` -> `fillGhostRecipe`, taking in the `ContextMap`
        - `$TabInfo` - A record that denotes the icons and categories of recipe to display within a recipe book page.
    - `RecipeBookPage()` -> `RecipeBookPage(RecipeBookComponent, SlotSelectTime, boolean)`
        - `updateCollections` now takes in a boolean representing if the book is filtering
        - `getMinecraft` is removed
        - `addListener` is removed
        - `getLastRecipeClicked` now returns a `RecipeDisplayId`
        - `recipesShown` now takes in a `RecipeDisplayId`
        - `getRecipeBook` now returns a `ClientRecipeBook`
    - `RecipeBookTabButton` now takes in a `RecipeBookComponent$TabInfo`
        - `startAnimation(Minecraft)` -> `startAnimation(ClientRecipeBook, boolean)`
        - `getCategory` now returns a `ExtendedRecipeBookCategory`
    - `RecipeButton()` -> `RecipeButton(SlotSelectTime)`
        - `init` now takes in a `boolean` representing if the book is filtering and a `ContextMap` holding the registry data
        - `getRecipe` -> `getCurrentRecipe`, not one-to-one
        - `getDisplayStack` - Returns the result stack of the recipe.
        - `getTooltipText` now takes in the `ItemStack`
    - `RecipeCollection(RegistryAccess, List<RecipeHolder>)` -> `RecipeCollection(List<RecipeDisplayEntry>)`
        - `canCraft` ->  `selectRecipes`
        - `getRecipes`, `getDisplayRecipes` -> `getSelectedRecipes`
        - `registryAccess`, `hasKnownRecipes`, `updateKnownRecipes` is removed
        - `isCraftable` now takes in a `RecipeDisplayId`
        - `hasFitting` -> `hasAnySelected`
        - `getRecipes` now returns a list of `RecipeDisplayEntry`s
    - `RecipeShownListener` class is removed
    - `RecipeUpdateListener`
        - `getRecipeBookComponent` is removed
        - `fillGhostRecipe` -> Fills the ghost recipe given the `RecipeDisplay`
    - `SearchRecipeBookCategory` - An enum which holds the recipe book categories for aggregate types.
    - `SlotSelectTime` - Represents the current index of the slot selected by the player.
- `net.minecraft.client.multiplayer`
    - `ClientPacketListener#getRecipeManager` -> `recipes`, returns `RecipeAccess`
    - `ClientRecipeContainer` - A client side implementation of the `RecipeAccess` when synced from the server.
    - `MultiPlayerGameMode#handlePlaceRecipe` now takes in a `RecipeDisplayId`
    - `SessionSearchTrees#updateRecipes` now takes in a `Level` instead of the `RegistryAccess$Frozen`
- `net.minecraft.client.player.LocalPlayer#removeRecipeHightlight` now takes in a `RecipeDisplayId`
- `net.minecraft.commands.SharedSuggestionProvider#getRecipeNames` is removed as it can be queried from the registry access
- `net.minecraft.commands.arguments.ResourceLocationArgument`
    - `getRecipe` -> `ResourceKeyArgument#getRecipe`
    - `getAdvancement` -> `ResourceKeyArgument#getAdvancement`
- `net.minecraft.commands.synchronization.SuggestionProviders#ALL_RECIPES` is removed
- `net.minecraft.core.component.DataComponents#RECIPES` now takes in a list of `ResourceKey`s
- `net.minecraft.data.recipes`
    - `RecipeBuilder#save` now takes in a `ResourceKey` instead of a `ResourceLocation`
    - `RecipeOutput#accept` now takes in a `ResourceKey` instead of a `ResourceLocation`
    - `RecipeProvider#trimSmithing` now takes in a `ResourceKey` instead of a `ResourceLocation`
- `net.minecraft.network.protocol.game`
    - `ClientboundPlaceGhostRecipePacket` - A packet that contains the container id and the `RecipeDisplay`
    - `ClientboundRecipeBookAddPacket` - A packet that adds entries to the recipe book
    - `ClientboundRecipeBookRemovePacket` - A packet that removes entries to the recipe book
    - `ClientboundRecipeBookSettingsPacket` - A packet that specifies the settings of the recipe book
    - `ClientboundRecipePacket` class is removed
    - `ClientboundUpdateRecipesPacket` is now a record, taking in the property sets of the recipes and the stonecutter recipes
        - `getRecipes` is removed
    - `ServerboundPlaceRecipePacket` is now a record
    - `ServerboundRecipeBookSeenRecipePacket` is now a record
- `net.minecraft.recipebook`
    - `PlaceRecipe` -> `PlaceRecipeHelper`
        - `addItemToSlot` -> `$Output#addItemToSlot`
        - `placeRecipe` now takes in a `Recipe` instead of the `RecipeHolder`
            - There is an overload that takes in two more ints that represent the pattern height and width for a `ShapedRecipe`, or just the first two integers repeated
    - `RecipeBook`
        - `add`, `contains`, `remove` -> `ServerRecipeBook#add`, `contains`, `remove`
        - `addHighlight`, `removeHighlight`, `willHighlight` -> `ServerRecipeBook#addHighlight`, `removeHighlight`, `ClientRecipeBook#hasHighlight`
        - `bookSettings` is now protected
    - `RecipeBookSettings#read`, `write` is now private
    - `ServerPlaceRecipe` is not directly accessible anymore, instead it is accessed and returned as a `RecipeBookMenu$PostPlaceAction` via `#placeRecipe`
        - `$CraftingMenuAccess` - Defines how the placable recipe menu can be interacted with.
    - `ServerRecipeBook`
        - `fromNbt` now takes in a predicate of a `ResourceKey` instead of the `RecipeManager`
        - `copyOverData` - Reads the data from another recipe book.
        - `$DisplayResolver` - Resoluves the recipes to display by passing in a `RecipeDisplayEntry`
- `net.minecraft.stats.RecipeBook#isFiltering(RecipeBookMenu)` is removed
- `net.minecraft.world.entity.player`
    - `Player#awardRecipesByKey` now takes in a list of `ResourceKey`s
    - `StackedItemContents#canCraft` overloads that take in a list of ingredient infos
- `net.minecraft.world.inventory`
    - `AbstractCraftingMenu` - A menu for a crafting interface.
    - `AbstractFurnaceMenu` now takes in the `RecipePropertySet` key
    - `CraftingMenu#slotChangedCraftingGrid` now takes in a `ServerLevel` instead of a `Level`
    - `ItemCombinerMenu` now takes in an `ItemCombinerMenuSlotDefinition`
        - `mayPickup` now defaults to `true`
    - `ItemCombinerMenuSlotDefinition#hasSlot`, `getInputSlotIndexes` is removed
    - `RecipeBookMenu` no longer takes in any generics
        - `handlePlacement` is now abstract and returns a `$PostPlaceAction`, taking in an additional `ServerLevel`
            - This remove all basic placement recipes calls, as that would be handled internally by the `ServerPlaceRecipe`
    - `RecipeCraftingHolder#setRecipeUser` no longer takes in a `Level`
    - `SmithingMenu#hasRecipeError` - Returns whether the recipe had an error when placing items in the inventory.
- `net.minecraft.world.item.crafting`
    - `AbstractCookingRecipe` now implements `SingleItemRecipe`
        - The constructor no longer takes in the `RecipeType`, making the user override the `getType` method
        - `getExperience` -> `experience`
        - `getCookingTime` -> `cookingTime`
        - `furnaceIcon` - Returns the icon of the furnace.
        - `$Serializer` - A convenience implementation for the cooking recipe serializer instance.
    - `CookingBookCategory` now has an integer id
    - `CraftingRecipe#defaultCrafingRemainder` - Gets the stacks that should remain behind in the crafting recipe.
    - `CustomRecipe$Serializer` - A convenience implementation for the custom recipe serializer instance.
    - `ExtendedRecipeBookCategory` - A unifying interface that denotes a category within the recipe book.
    - `Ingredient#optionalIngredientToDisplay` - Converts an optional ingredient to a `SlotDisplay`.
    - `Recipe#getRemainingItems` -> `CraftingRecipe#getRemainingItems`
    - `RecipeAccess` - An accessor that returns the property sets that contain the inputs of available recipes.
    - `RecipeBookCategory` - An object that represents a single category within the recipe book.
    - `RecipeCache#get` now takes in a `ServerLevel` instead of a `Level`
    - `RecipeHolder` now takes in a `ResourceKey`
    - `RecipeManager` now extends `SimplePreparableReloadLsitener<RecipeMap>` and implements `RecipeAccess`
        - `prepare` - Creates the recipe map from the recipe registry
        - `logImpossibleRecipes`, `hasErrorsLoading` is removed
        - `getRecipeFor` now takes in a `ResourceKey` where there was a `ResourceLocation` repviously
        - `getRecipesFor`, `getAllRecipesFor` -> `RecipeMap#getRecipesFor`
        - `byType` is removed
        - `getRemainingItemsFor` is Removed
        - `byKey`.`byKeyTyped` now takes in a `ResourceKey`
        - `getOrderedRecipes` is revmoed
        - `getSynchronizedRecipes` -> `getSynchronizedItemProperties`, `getSynchronizedStonecutterRecipes`; not one-to-one
        - `getRecipeIds` is removed
        - `getRecipeFromDisplay` - Gets the recipe display info given its id.
        - `listDisplaysForRecipe` - Accepts a list of display entries of the recipes to display.
        - `replaceRecipes` is removed
        - `$CachedCheck#getRecipeFor` now takes in a `ServerLevel` instead of a `Level`
        - `$IngredientCollector` - A recipe consumer that extracts the ingredient from a recipe and adds it to a `RecipePropertySet`
        - `$IngredientExtractor` - A method that gets the ingredients of a recipe when present.
        - `$ServerDisplayInfo` - A record that links a display entry to its recipe holder.
    - `RecipeMap` - A class which maps recipe holders by their recipe type and resource key.
    - `RecipePropertySet` - A set of ingredients that can be used as input to a given recipe slot. Used to only allow specific inputs to slots on screens.
    - `SelectableRecipe` - A record that holds the slot display and its associated recipe. Currently only used for the stonecutting menu.
    - `SimpleCookingSerializer` -> `AbstractCookingRecipe$Serializer`
    - `SingleItemRecipe` no longer takes in the `RecipeType` or `RecipeSerializer`
        - `ingredient`, `result`, `group` is now private
        - `input`, `result` - The slots of the recipe.
- `net.minecraft.world.item.crafting.display`
    - `DisplayContentsFactory` - A factory for accepting contents of a recipe. Its subtypes accepts the stacks of the recipe and the remainder.
    - `RecipeDisplay` - A display handler to show the contents of a recipe.
    - `RecipeDisplayEntry` - A record that links the recipe display to its identifier, category, and crafting requirements.
    - `RecipeDisplayId` - An identifier for the recipe display.
    - `SlotDisplay` - A display handler to show the contents of a slot within a recipe.
    - `SlotDisplayContext` - Context keys used by slot displays.
- `net.minecraft.world.level.Level#getRecipeManager` -> `recipeAccess`, returns `RecipeAccess` on level but `RecipeManager` on `ServerLevel`
- `net.minecraft.world.level.block.CrafterBlock#getPotentialResults` now takes in a `ServerLevel` instead of a `Level`
- `net.minecraft.world.level.block.entity.CampfireBlockEntity`
    - `getCookableRecipe` is removed
    - `placeFood` now takes in a `ServerLevel` instead of a `Level`

## Minor Migrations

The following is a list of useful or interesting additions, changes, and removals that do not deserve their own section in the primer.

### Language File Removals and Renames

All removals and renames to translations keys within `assets/minecraft/lang` are now shown in a `deprecated.json`.

### Critereons, Supplied with HolderGetters

All critereon builders during construction now take in a `HolderGetter`. While this may not be used, this is used instead of a direct call to the static registry to grab associated `Holder`s and `HolderSet`s.

- `net.minecraft.advancement.critereon`
    - `BlockPredicate$Builder#of`
    - `ConsumeItemTrigger$TriggerInstance#usedItem`
    - `EntityEquipmentPredicate#captainPredicate`
    - `EntityPredicate$Builder#of`
    - `EntityTypePredicate#of`
    - `ItemPredicate$Builder#of`
    - `PlayerTrigger$TriggerInstance#walkOnBlockWithEquipment`
    - `ShotCrossbowTrigger$TriggerInstance#shotCrossbow`
    - `UsedTotemTrigger$TriggerInstance#usedToItem`

### MacosUtil#IS_MACOS

`com.mojang.blaze3d.platform.MacosUtil#IS_MACOS` has been added to replace specifying a boolean during the render process.

- `com.mojang.blaze3d.pipeline`
    - `RenderTarget#clear(boolean)` -> `clear()`
    - `TextureTarget(int, int, boolean, boolean)` -> `TextureTarget(int, int, boolean)`
- `com.mojang.blaze3d.platform.GlStateManager#_clear(boolean)` -> `_clear()`
- `com.mojang.blaze3d.systems.RenderSystem#clear(int, boolean)` -> `clear(int)`

### Fog Parameters

Fog methods for individual values have been replaced with a `FogParameters` data object.

- `com.mojang.blaze3d.systems.RenderSystem`
    - `setShaderFogStart`, `setShaderFogEnd`, `setShaderFogColor`, `setShaderFogShape` -> `setShaderFog`
    - `getShaderFogStart`, `getShaderFogEnd`, `getShaderFogColor`, `getShaderFogShape` -> `getShaderFog`
- `net.minecraft.client.renderer.FogRenderer`
    - `setupColor` -> `computeFogColor`, returns a `Vector4f`
    - `setupNoFog` -> `FogParameters#NO_FOG`
    - `setupFog` now takes in a `Vector4f` for the color and returns the `FogParameters`
    - `levelFogColor` is removed

### New Tags

- `minecraft:banner_pattern`
    - `bordure_indented`
    - `field_masoned`
- `minecraft:block`
    - `bats_spawnable_on`
    - `pale_oak_logs`
- `minecraft:damage_type`
    - `mace_smash`
- `minecraft:item`
    - `diamond_tool_materials`
    - `furnace_minecart_fuel`
    - `gold_tool_materials`
    - `iron_tool_materials`
    - `netherite_tool_materials`
    - `villager_picks_up`
    - `wooden_tool_materials`
    - `piglin_safe_armor`
    - `repairs_leather_armor`
    - `repairs_chain_armor`
    - `repairs_iron_armor`
    - `repairs_gold_armor`
    - `repairs_diamond_armor`
    - `repairs_netherite_armor`
    - `repairs_turtle_helmet`
    - `repairs_wolf_armor`
    - `duplicates_allays`
    - `brewing_fuel`
    - `panda_eats_from_ground`
    - `shulker_boxes`
    - `bundles`
    - `map_invisibility_equipment`
    - `pale_oak_logs`
    - `gaze_disguise_equipment`
- `minecraft:entity_type`
    - `boat`

### Smarter Framerate Limiting

Instead of simply limiting the framerate when the player is not in a level or when in a screen or overlay, there is different behavior depending on different actions. This is done using the `InactivityFpsLimit` via the `FramerateLimitTracker`. This adds two additional checks. If the window is minimized, the game runs at 10 fps. If the user provides no input for a minute, then the game runs at 30 fps. 10 fps after ten minutes of no input.

- `com.mojang.blaze3d.platform.FramerateLimitTracker` - A tracker that limits the framerate based on the set value.
- `com.mojang.blaze3d.platform#Window#setFramerateLimit`, `getFramerateLimit` is removed
- `net.minecraft.client`
    - `InactivityFpsLimit` - An enum that defines how the FPS should be limited when the window is minimzed or the player is away from keyboard.
    - `Minecraft#getFramerateLimitTracker` - Returns the framerate limiter.

### Fuel Values

`FuelValues` has replaced the static map within `AbstractFurnaceBlockEntity`. This functions the same as that map, except the fuel values are stored on the `MinecraftServer` itself and made available to individual `Level` instances. The map can be obtained with access to the `MinecraftServer` or `Level` and calling the `fuelValues` method.

- `net.minecraft.client.multiplayer.ClientPacketListener#fuelValues` - Returns the burn times for fuel.
- `net.minecraft.server.MinecraftServer#fuelValues` - Returns the burn times for fuel.
- `net.minecraft.server.level.Level#fuelValues` - Returns the burn times for fuel.
- `net.minecraft.world.level.block.entity`
    - `AbstractFurnaceBlockEntity`
        - `invalidateCache`, `getFuel` -> `Level#fuelValues`
        - `getBurnDuration` now takes in the `FuelValues`
        - `isFuel` -> `FuelValues#isFuel`
    - `FuelValues` - A class which holds the list of fuel items and their associated burn times

### Light Emissions

Light emission data is now baked into the quad and can be added to a face using the `light_emission tag`.

- `net.minecraft.client.renderer.block.model`
    - `BakedQuad` now takes in an `int` representing the light emission
        - `getLightEmission` - Returns the light emission of a quad.
    - `BlockElement` now takes in an `int` representing the light emission
    - `FaceBakery#bakeQuad` now takes in an `int` representing the light emission

### Map Textures

Map textures are now handled through the `MapTextureManager`, which handles the dynamic texture, and the `MapRenderer`, which handles the map rendering. Map decorations are still loaded through the `map_decorations` sprite folder.

- `net.minecraft.client`
    - `Minecraft`
        - `getMapRenderer` - Gets the renderer for maps.
        - `getMapTextureManager` - Gets the texture manager for maps.
- `net.minecraft.client.resources#MapTextureManager` - Handles creating the dynamic texture for the map.
- `net.minecraft.client.gui.MapRenderer` -> `net.minecraft.client.renderer.MapRenderer`
- `net.minecraft.client.renderer#GameRenderer#getMapRenderer` -> `Minecraft#getMapRenderer`

### Orientations

With the edition of the redstone wire experiments comes a new class provided by the neighbor changes: `Orientation`. `Orientation` is effectively a combination of two directions and a side bias. `Orientation` is used as a way to propogate updates relative to the connected directions and biases of the context. Currently, this means nothing for people not using the new redstone wire system as all other calls to neighbor methods set this to `null`. However, it does provide a simple way to propogate behavior in a stepwise manner.


- `net.minecraft.client.renderer.debug.RedstoneWireOrientationsRenderer` - A debug renderer for redstone wires being oriented.
- `net.minecraft.world.level.Level`
    - `updateNeighborsAt` - Updates the neighbor at the given position with the specified `Orientation`.
    - `updateNeighborsAtExceptFromFacing`, `neighborChanged` now takes in an `Orientation`
- `net.minecraft.world.level.block.RedStoneWireBlock`
    - `getBlockSignal` - Returns the strength of the block signal.
- `net.minecraft.world.level.block.state.BlockBehaviour`
    - `neighborChanged`, `$BlockStateBase#handleNeighborChanged` now takes in an `Orientation` instead of the neighbor `BlockPos`
    - `updateShape` now takes in the `LevelReader`, `ScheduledTickAccess`, and a `RandomSource` instead of the `LevelAccessor`; the `Direction` and `BlockState` parameters are reordered
    - `$BlockStateBase#updateShape` now takes in the `LevelReader`, `ScheduledTickAccess`, and a `RandomSource` instead of the `LevelAccessor`; the `Direction` and `BlockState` parameters are reordered
- `net.minecraft.world.level.redstone`
    - `CollectingNeighborUpdater$ShapeUpdate#state` -> `neighborState`
    - `NeighborUpdater`
        - `neighborChanged`, `updateNeighborsAtExceptFromFacing`, `executeUpdate` now takes in an `Orientation` instead of the neighbor `BlockPos`
        - `executeShapeUpdate` switches the order of the `BlockState` and neighbor `BlockPos`
    - `Orientation` - A group of connected `Directions` on a block along with a bias towards either the front or the up side.
    - `RedstoneWireEvaluator` - A strength evaluator for incoming and outgoing signals.

### Minecart Behavior

Minecarts now have a `MinecartBehavior` class that handles how the entity should be moved and rendered.

- `net.minecraft.core.dispenser.MinecartDispenseItemBehavior` - Defines how a minecart should behave when dispensed from a dispenser.
- `net.minecraft.world.entity.vehicle`
    - `AbstractMinecart`
        - `getMinecartBehavior` - Returns the behavior of the minecart.
        - `exits` is now public
        - `isFirstTick` - Returns whether this is the first tick the entity is alive.
        - `getCurrentBlockPosOrRailBelow` - Gets the current position of the minecart or the rail beneath.
        - `moveAlongTrack` -> `makeStepAlongTrack`
        - `setOnRails` - Sets whether the minecart is on rails.
        - `isFlipped`, `setFlipped` - Returns whetherh the minecart is upside down.
        - `getRedstoneDirection` - Returns the direction the redstone is powering to.
        - `isRedstoneConductor` is now public
        - `applyNaturalSlowdown` now returns the vector to slowdown by.
        - `getPosOffs` -> `MinecartBehavior#getPos`
        - `setInitialPos` - Sets the initial position of the minecart.
        - `createMinecart` is now abstract in its creation, meaning it can be used to create any minecart given the provided parameters
        - `getMinecartType` is removed
        - `getPickResult` is now abstract
        - `$Type` and `getMinecartType` is replaced by `isRideable` and `isFurnace`, which is not one-to-one.
    - `AbstractMinecartContainer(EntityType, double, double, double, Level)` is removed
    - `MinecartBehavior` - holds how the entity should be rendered and positions during movement.
    - `MinecartFurnace#xPush`, `zPush` -> `push`
- `net.minecraft.world.level.block.state.properties.RailShape#isAscending` -> `isSlope`
- `net.minecraft.world.phys.shapes.MinecartCollisionContext` - An entity collision context that handles the collision of a minecart with some other collision object.

### EXPLOOOOSSSION!

`Explosion` is now an interface that defines the metadata of the explosion. It does not contain any method to actually explode itself. However, `ServerExplosion` is still used internally to handle level explosions and the like.

- `net.minecraft.world.level`
    - `Explosion` -> `ServerExplosion`
    - `Explosion` - An interface that defines how an explosion should occur.
        - `getDefaultDamageSource` - Returns the default damage source of the explosion instance.
        - `shouldAffectBlocklikeEntities` - Returns whether block entites should be affected by the explosion.
        - `level` - Gets the `ServerLevel`
    - `ExplosionDamageCalculator#getEntityDamageAmount` now takes in an additional `float` representing the seen percent
    - `Level#explode` no longer returns anything
- `net.minecraft.world.level.block.Block#wasExploded` now takes in a `ServerLevel` instead of a `Level`
- `net.minecraft.world.level.block.state.BlockBehaviour#onExplosionHit`, `$BlockStateBase#onExplosionHit` now takes in a `ServerLevel` instead of a `Level`


### The Removal of the Carving Generation Step

`GenerationStep$Carving` has been removed, meaning that all `ConfiguredWorldCarver`s are provided as part of a single `HolderSet`.

```json
// In some BiomeGenerationSettings JSON
{
    "carvers": [
        // Carvers here
    ]
}
```

- `net.minecraft.world.level.biome.BiomeGenerationSettings`
    - `getCarvers` no longer takes in a `GenerationStep$Carving`
    - `$Builder#addCarver` no longer takes in a `GenerationStep$Carving`
    - `$PlainBuilder#addCarver` no longer takes in a `GenerationStep$Carving`
- `net.minecraft.world.level.chunk`
    - `ChunkGenerator#applyCarvers` no longer takes in a `GenerationStep$Carving`
    - `ProtoChunk#getCarvingMask`, `getOrCreateCarvingMask`, `setCarvingMask` no longer takes in a `GenerationStep$Carving`
- `net.minecraft.world.level.levelgen.placement`
    - `CarvingMaskPlacement` class is removed
    - `PlacementContext#getCarvingMask` no longer takes in a `GenerationStep$Carving`

### Codecable Json Reload Listener

The `SimpleJsonResourceReloadListener` has been rewritten to use codecs instead of pure `Gson`.

```java
public class MyJsonListener extends SimpleJsonResourceReloadListener<MyJsonObject> {

    // If you do not need registry access, the HolderLookup$Provider parameter can be removed
    public MyJsonListener(HolderLookup.Provider registries, Codec<T> codec, String directory) {
        super(registries, codec, directory);
    }
}
```

- `net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener` now takes in a generic representing the data object of the JSON
    - The constructor is now protected, taking in the codec of the data object, the string of the directory, and an optional `HolderLookup$Provider` to construct the `RegistryOps` serialization context as necessary
    - `prepare` now returns a map of names to objects
    - `scanDirectory` now takes in the `DynamicOps` and `Codec`

### Consecutive Executors

`ProcessorMailbox` and `ProcessorHandle` have been replaced with `AbstractConsecutiveExecutor` and `TaskScheduler`, respectively. These are effectively the same in their usage, just with potentially different method names.

- `net.minecraft.util.thread`
    - `ProcessorMailbox` -> `AbstractConsecutiveExecutor`, not one-to-one
        - `ConsecutiveExecutor` would be the equivalent implementation
    - `PriorityConsecutiveExecutor` - An executor that specifies the priority of the task to run when scheduling.
    - `BlockableEventLoop#wrapRunnable` -> `AbstractConsecutiveExecutor#wrapRunnable`
    - `ProcessorHandle` -> `TaskScheduler`, where the generic is a subtype of `Runnable`
        - `tell` -> `schedule`
        - `ask`, `askEither` -> `scheduleWithResult`, not one-to-one
        - `of` -> `wrapExecutor`
    - `StrictQueue` no longer takes in an `F` generic and makes `T` a subtype of `Runnable`
        - `pop` now returns a `Runnable`
        - `$IntRunnable` -> `$RunnableWithPriority`


### Mob Conversions

Mobs, converted via `#convertTo`, have their logic handled by `ConversionType`, `ConversionParams`. `ConversionType` is an enum that dictates the logic to apply when copying the information from one mob to another via `#convert`. The common properties are handled via `#convertCommon`, which is called within the `#convert` method. There are currently two types: `SINGLE`, where the entity is converted one-to-one to another entity; and `SPLIT_ON_DEATH`, where the `Mob#convertTo` method is called mutiple times such as when a slime dies. `ConversionParams` contains the metadata about the conversion process: the type, whether the entity can keep its equipment or pick up loot, and what team the entity is on. `Mob#convertTo` also takes in a mob consumer to apply any finalization settings to the entity itself.

```java
// For some Mob exampleMob
exampleMob.convertTo(
    EntityType.SHEEP, // The entity to convert to
    new ConversionParams(
        ConversionType.SINGLE, // One-to-one
        true, // Keep equipment
        false // Do not preserve pick up loot
    ),
    EntitySpawnReason.CONVERSION, // Reason entity spawned
    sheep -> {
        // Perform any other settings to set on the newly converted entity
    },
)
```

- `net.minecraft.world.entity`
    - `ConversionParams` - A record containing the settings of what happens when a mob is converted to another entity
    - `ConversionType` - An enum that defines how one mob is transformed to another. Currently either `SINGLE` for one-to-one, or `SPLIT_ON_DEATH` for one-to-many (only used for slimes)
    - `Mob#convertTo` now takes in the `ConversionParams`, an optional `EntitySpawnReason` of the entity (default `CONVERSION`), and a mob consumer to set any other information after conversion

### Ender Pearl Chunk Loading

Ender pearls now load the chunks they cross through by adding a ticket to the chunk source and storing the entity on the player.

- `net.minecraft.server.level.ServerPlayer`
    - `registerEnderPearl`, `deregisterEnderPearl`, `getEnderPearls` - Handles the ender pearls thrown by the player.
    - `registerAndUpdateEnderPearlTicket`, `placeEnderPearlTicket` - Handles the region tickets for the thrown ender pearls.

### Profilers and the Tracy Client

Profilers have been separated from the minecraft instance, now obtained through `Profiler#get`. A new profiler instance can be added via a try-resource block on `Profiler#use`. In addition, the profiler addds a new library called Tracy, made to track the current stack frame along with capturing images on the screen, if the associated `--tracy` argument is passed in. These sections can be split into 'zones' to more granularly distinguish what is happening.

```java
Profiler.get().push("section");
// Do code here
Profiler.get().pop();
```

- `com.mojang.blaze3d.systems.RenderSystem#flipFrame` now takes in a `TracyFrameCapture`, or `null`
- `net.minecraft.client.Minecraft#getProfiler` -> `Profiler#get`
- `net.minecraft.client.main.GameConfig$GameData` now takes in a boolean on whether to capture the screen via the tracy client.
- `net.minecraft.client.multiplayer.ClientLevel` no longer takes in the `ProfilerFiller`
- `net.minecraft.server.MinecraftServer#getProfiler` -> `Profiler#get`
- `net.minecraft.server.packs.resources.PreparableReloadListener#reload` no longer takes in the `ProfilerFiller`s
- `net.minecraft.util.profiling`
    - `Profiler` - A static handler for managing the currently active `ProfilerFiller`.
    - `ProfilerFiller`
        - `addZoneText` - Adds text to label when profiling the current frame.
        - `addZoneValue` - Adds the value of the zone when profiling the current frame.
        - `setZoneColor` - Sets the color of the zone when profiling the current frame.
        - `zone` - Adds a profiler section while creating a new zone to call the above methods for.
        - `tee` -> `combine`
        - `$CombinedProfileFiller` - A profiler that writes to multiple profilers.
    - `TracyZoneFiller` - A profiler used by the tracy client to keep track of the currently profiling zones.
    - `Zone` - A section that is current being profiled and interpreted by Tracy.
- `net.minecraft.world.entity.ai.goal.GoalSelector` no longer takes in the supplied `ProfilerFiller`
- `net.minecraft.world.level`
    - `Level` no longer takes in the `ProfilerFiller`
        - `getProfiler`, `getProfilerSupplier` -> `Profiler#get`
    - `PathNavigationRegion#getProfiler` -> `Profiler#get`
- `net.minecraft.world.ticks.LevelTicks` no longer takes in the `ProfilerFiller`

### Tick Throttler

To prevent the player from spamming certain actions, `TickThrottler` was added. The throttler takes in the threshold and the increment to add to the count. If the count is less than the threshold, the action can occur. The count is reduced every tick.

- `net.minecraft.util.TickThrottler` - A utility for throttling certain actions from happening too often.

### Context Keys

Loot context parameters have been replaced with Context keys, which is simply a more general naming scheme for the previous classes. This also caused the context keys to be used in other contexts that may have arbitrary data.

For a brief description, the context key system is effectively a general typed dictionary, where each `ContextKey` holds the value type, which is then stored in a backed-map within a `ContextMap`. To enforce required and optional parameters, a `ContextMap` is built with a `ContextKeySet`, which defines the keys of the dictionary map.

- `net.minecraft.advancements.critereon.CriterionValidator#validate` now takes in a `ContextKeySet` instead of a `LootContextParamSet`
- `net.minecraft.data.loot.LootTableProvider$SubProviderEntry#paramSet` now takes in a `ContextKeySet` instead of a `LootContextParamSet`
- `net.minecraft.util.context`
    - `ContextKey` - A key that represents an object. It can be thought of a dictionary key that specifies the value type.
    - `ContextKeySet` - A key set which indicates what keys the backing dictionary must have, along with optional keys that can be specified.
    - `ContextMap` - A map of context keys to their typed objects.
- `net.minecraft.world.item.enchantment`
    - `ConditionalEffect#codec` now takes in a `ContextKeySet` instead of a`LootContextParamSet`
    - `TargetedConditionalEffect#codec` now takes in a `ContextKeySet` instead of a`LootContextParamSet`
- `net.minecraft.world.level.storage.loot`
    - `LootContext`
        - `hasParam` -> `hasParameter`
        - `getParam` -> `getParameter`
        - `getParamOrNull` - `getOptionalParameter`
        - `$EntityTraget#getParam` now returns a `ContextKey` instead of a `LootContextParam`
    - `LootContextUser#getReferencedContextParams` now takes in a set of `ContextKey`s rather than a set of `LootContextParam`s
    - `LootParams` now takes in a `ContextMap` instead of a map of params to objects
        - `hasParam`, `getParameter`, `getOptionalParameter`, `getParamOrNull` are accessible through the `ContextMap` under different names
        - `$Builder#withParameter`, `withOptionalParameter`, `getParameter`, `getOptionalParameter` now takes in a `ContextKey` instead of a `LootContextParam`
        - `$Builder#create` now takes in a `ContextKeySet` instead of a `LootContextParamSet`
    - `LootTable`
        - `getParameSet` now returns a `ContextKeySet` instead of a `LootContextParamSet`
        - `$Builder#setParamSet` now takes in a `ContextKeySet` instead of a `LootContextParamSet`
    - `ValidationContext` now takes in a `ContextKeySet` instead of a `LootContextParamSet`
        - `validateUser` -> `validateContextUsage`
        - `setParams` - `setContextKeySet`
- `net.minecraft.world.level.storage.loot.functions`
    - `CopyComponentsFunction$Source#getReferencedContextParams` now takes in a set of `ContextKey`s rather than a set of `LootContextParam`s
- `net.minecraft.world.level.storage.loot.parameters`
    - `LootContextParam` -> `net.minecraft.util.context.ContextKey`
    - `LootContextParamSet` -> `net.minecraft.util.context.ContextKeySet`
- `net.minecraft.world.level.storage.loot.providers.nbt`
    - `ContextNbtProvider$Getter#getReferencedContextParams` now takes in a set of `ContextKey`s rather than a set of `LootContextParam`s
    - `NbtProvider#getReferencedContextParams` now takes in a set of `ContextKey`s rather than a set of `LootContextParam`s
- `net.minecraft.world.level.storage.loot.providers.score.ScoreboardNameProvider#getReferencedContextParams` now takes in a set of `ContextKey`s rather than a set of `LootContextParam`s

### List of Additions

- `com.mojang.blaze3d.framegraph`
    - `FrameGraphBuilder` - A builder that constructs the frame graph that define the resources used and the frame passes to render.
    - `FramePass` - An interface that defines how to read/write resources and execute them for rendering within the frame graph.
- `com.mojang.blaze3d.platform`
    - `ClientShutdownWatchdog` - A watchdog created for what happens when the client is shutdown.
    - `NativeImage#getPixelsABGR` - Gets the pixels of the image in ABGR format.
    - `Window`
        - `isIconified` - Returns whether the window is currently iconified (usually minimized onto the taskbar).
        - `setWindowCloseCallback` - Sets the callback to run when the window is closed.
- `com.mojang.blaze3d.resource`
    - `CrossFrameResourcePool` - Handles resources that should be rendered across multiple frames
    - `GraphicsResourceAllocator` - Handles resources to be rendered and removed.
    - `RenderTargetDescriptor` - Defines a render target to be allocated and freed.
    - `ResourceDescriptor` - Defines a resource and how it is allocated and freed.
    - `ResourceHandle` - Defines a pointer to an individual resource.
- `com.mojang.blaze3d.systems.RenderSystem#overlayBlendFunc` - Sets the default overlay blend function between layers with transparency.
- `com.mojang.blaze3d.vertex`
    - `PoseStack#translate(Vec3)` - Translates the top pose using a vector
    - `VertexConsumer#setNormal(PoseStack$Pose, Vec3)` - Sets the normal of a vertex using a vector
- `net.minecraft`
    - `Optionull#orElse` - If the first object is null, return the second object.
    - `TracingExecutor` - An executor that traces the stack frames of the class references executing.
    - `Util`
        - `allOf` - ANDs all predicates or a list of predicates provided. If there are no supplied predicates, the method will default to `true`.
        - `anyOf` - ORs all predicates or a list of predicates provided. If there are no supplied predicates, the method will default to `false`.
        - `makeEnumMap` - Creates an enum map given the enum class and a function to convert the enum to a value.
- `net.minecraft.advancements.critereon`
    - `InputPredicate` - A predicate that matches the input the player is making.
    - `SheepPredicate` - A predicate for when the entity is a sheep.
- `net.minecraft.client`
    - `Minecraft`
        - `saveReport` - Saves a crash report to the given file.
        - `triggerResourcePackRecovery` - A function that attempts to save the game when a compilation exception occurs, currently used by shaders when loading.
    - `Options#highContrastBlockOutline` - When enabled, provides a greater contrast when hovering over a block in range.
    - `ScrollWheelHandler` - A handler for storing information when a mouse wheel is scrolled.
- `ItemSlotMouseAction` - An interface that defines how the mouse interacts with a slot when hovering over.
- `net.minecraft.client.gui.components`
    - `AbstractSelectionList#setSelectedIndex` - Sets the selected entry based on its index.
    - `AbstractWidget#playButtonClickSound` - Plays the button click sound.
    - `DebugScreenOverlay#getProfilerPieChart` - Gets the pie chart profiler renderer.
- `net.minecraft.client.gui.components.debugchart.AbstractDebugChart#getFullHeight` - Returns the height of the rendered chart.
- `net.minecraft.client.gui.components.toasts`
    - `Toast`
        - `getWantedVisbility` - Returns the visbility of the toast to render.
        - `update` - Updates the data within the toast.
    - `TutorialToast` has a constructor that takes in an `int` to represent the time to display in milliseconds.
- `net.minecraft.client.gui.font.glyphs.BakedGlyph`
    - `renderChar` - Renders a character in the specified color.
    - `$GlyphInstance` - An instance of a glyph with the metadata of its screen location.
- `net.minecraft.client.gui.screens`
    - `BackupConfirmScreen` has a constructor that takes in another `Component` that represents the prompt for erasing the cache.
    - `Screen`
        - `getFont` - Returns the current font used for rendering the screen.
        - `showsActiveEffects` - When true, shows the mob effects currently applied to the player, assuming that such functionality is added to the screen in question.
- `net.minecraft.client.gui.screens.inventory`
    - `AbstractContainerScreen`
        - `BACKGROUND_TEXTURE_WIDTH`, `BACKGROUND_TEXTURE_HEIGHT` - Both set to 256.
        - `addItemSlotMouseAction` - Adds a mouse action when hovering over a slot.
        - `renderSlots` - Renders all active slots within the menu.
    - `AbstractRecipeBookScreen` - A screen that has a renderable and interactable `RecipeBookComponent` supplied from the constructor.
- `net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent#showTooltipWithItemInHand`- Returns whether the tooltip should be rendered when the item is in the player's hand.
- `net.minecraft.client.gui.screens.worldselection`
    - `CreateWorldCallback` - An interface that creates the world given the current screen, registries, level data, and path directory.
    - `CreateWorldScreen#testWorld` - Tries to open the world create screen with the provided generation settings context.
    - `InitialWorldCreationOptions` - Contains the options set when creating the world to generate.
    - `WorldCreationContextMapper` - An interface that creates the world context from the available resource reloaders and registries.
- `net.minecraft.client.multiplayer`
    - `ClientChunkCache`
        - `getLoadedEmptySections` - Returns the sections that have been loaded by the game, but has no data.
    - `ClientLevel`
        - `isTickingEntity` - Returns whether the entity is ticking in the level.
        - `setSectionRangeDirty`- Marks an area as dirty to update during persistence and network calls.
        - `onSectionBecomingNonEmpty` - Updates the section when it has data.
    - `PlayerInfo#setTabListOrder`, `getTabListOrder` - Handles the order of players to cycle through in the player tab.
- `net.minecraft.client.multiplayer.chat.report.ReportReason#getIncompatibleCategories` - Gets all reasons that cannot be reported for the given type.
- `net.minecraft.client.particle.TrailParticle` - A particle to trail from its current position to the target position.
- `net.minecraft.client.player.LocalPlayer#getDropSpamThrottler` - Returns a throttler that determines when the player can drop the next item.
- `net.minecract.client.renderer`
    - `CloudRenderer` - Handles the rendering and loading of the cloud texture data.
    - `DimensionSpecialEffects#isSunriseOrSunset` - Returns whether the dimension time represents sunrise or sunset in game.
    - `LevelEventHandler` - Handles the events sent by the `Level#levelEvent` method.
    - `LevelRenderer`
        - `getCapturedFrustrum` - Returns the frustrum box of the renderer.
        - `getCloudRenderer` - Returns the renderer for the clouds in the skybox.
        - `onSectionBecomingNonEmpty` - Updates the section when it has data.
    - `LevelTargetBundle` - Holds the resource handles and render targets for the rendering stages.
    - `LightTexture`
        - `getBrightness` - Returns the brightness of the given ambient and sky light.
        - `lightCoordsWithEmission` - Returns the packed light coordinates.
    - `RenderType`
        - `entitySolidZOffsetForward` - Gets a solid entity render type where the z is offset from the individual render objects.
        - `flatClouds` - Gets the render type for flat clouds.
        - `debugTriangleFan` - Gets the render type for debugging triangles.
        - `vignette` - Gets the vignette type.
        - `crosshair` - Gets the render type for the player crosshair.
        - `mojangLogo` - Gets the render type for the mojang logo
    - `Octree` - A traversal implementation for defining the order sections should render in the frustum.
    - `ShapeRenderer` - Utility for rendering basic shapes in the Minecraft level.
    - `SkyRenderer` - Renders the sky.
    - `WeatherEffectRenderer` - Renders weather effects.
    - `WorldBorderRenderer` - Renders the world border.
- `net.minecraft.client.renderer`
    - `SectionOcclusionGraph#getOctree` - Returns the octree to handle traversal of the render sections.
    - `ViewArea#getCameraSectionPos` - Gets the section position of the camera.
- `net.minecraft.client.renderer.culling.Frustum`
    - `getFrustumPoints` - Returns the frustum matrix as an array of `Vector4f`s.
    - `getCamX`, `getCamY`, `getCamZ` - Returns the frustum camera coordinates.
- `net.minecraft.client.renderer.chunk.CompileTaskDynamicQueue` - A syncrhonized queue dealing with the compile task of a chunk render section.
- `net.minecraft.client.renderer.debug`
    - `ChunkCullingDebugRenderer` - A debug renderer for when a chunk is culled.
    - `DebugRenderer`
        - `renderAfterTranslucents` - Renders the chunk culling renderer after translucents have been rendered.
        - `renderVoxelShape` - Renders the outline of a voxel shape.
        - `toggleRenderOctree` - Toggles whether `OctreeDebugRenderer` is rendered.
    - `OctreeDebugRenderer` - Renders the order of the section nodes.
- `net.minecraft.client.renderer.texture.AbstractTexture#defaultBlur`, `getDefaultBlur` - Returns whether the blur being applied is the default blur.
- `net.minecraft.client.resources.DefaultPlayerSkin#getDefaultSkin` - Returns the default `PlayerSkin`.
- `net.minecraft.commands.CommandBuildContext#enabledFeatures` - Returns the feature flags 
- `net.minecraft.commands.arguments.selector.SelectorPattern` - A record that defines an `EntitySelector` resolved from some pattern.
- `net.minecraft.core`
    - `BlockPos#betweenClosed` - Returns an iterable of all positions within the bounding box.
    - `Direction`
        - `getYRot` - Returns the Y rotation of a given direction.
        - `getNearest` - Returns the nearest direction given some XYZ coordinate, or the fallback direction if no direction is nearer.
        - `getUnitVec3` - Returns the normal unit vector.
        - `$Axis#getPositive`, `getNegative`, `getDirections` - Gets the directions along the axis.
    - `GlobalPos#isCloseEnough` - Returns whether the distance from this position to another block position in a dimension is within the given radius.
    - `HolderLookup$Provider`
        - `listRegistries` - Returns the registry lookups for every registry.
        - `allRegistriesLifecycle` - Returns the lifecycle of all registries combined.
    - `HolderSet#isBound` - Returns whether the set is bound to some value.
    - `Registry$PendingTags#size` - Gets the number of tags to load.
    - `Vec3i#distChessboard` - Gets the maximum absolute distance between the vector components.
- `net.minecraft.core.component`
    - `DataComponentHolder#getAllOfType` - Returns all data components that are of the specific class type.
    - `DataComponentPredicate`
        - `someOf` - Constructs a data component predicate where the provided map contains the provided component types.
        - `$Builder#expect` - Adds that we should expect the data component has some value.
    - `PatchedDataComponentMap#clearPatch` - Clears all patches to the data components on the object.
- `net.minecraft.core.particles.TargetColorParticleOption` - A particle option that specifies a target location and a color of the particle.
- `net.minecraft.data.DataProvider`
    - `saveAll` - Writes all values in a resource location to value map to the `PathProvider` using the provided codec.
    - `saveStable` - Writes a value to the provided path given the codec.
- `net.minecraft.data.loot#BlockLootSubProvider`
    - `createMossyCarpetBlockDrops` - Creates a loot table for a mossy carpet block.
    - `createShearsOrSlikTouchOnlyDrop` - Creates a loot table that can only drop its item when mined with shears or an item with the silk touch enchantment.
- `net.minecraft.data.worldgen.Pools#createKey` - Creates a `ResourceKey` for a template pool.
- `net.minecraft.data.models.EquipmentModelProvider` - A model provider for equipment models, only includes vanilla bootstrap.
- `net.minecraft.data.info.DatapackStructureReport` - A provider that returns the structure of the datapack.
- `net.minecraft.gametest.framework`
    - `GameTestHelper`
        - `absoluteAABB`, `relativeAABB` - Moves the bounding box between absolute coordinates and relative coordinates to the test location
        - `assertEntityData` - Asserts that the entity at the provided block position matches the predicate.
        - `hurt` - Hurts the entity the specified amount from a source.
        - `kill` - Kills the entity.
    - `GameTestInfo#getTestOrigin` - Gets the origin of the spawn structure for the test.
    - `StructureUtils#getStartCorner` - Gets the starting position of the test to run.
- `net.minecraft.network`
    - `FriendlyByteBuf`
        - `readVec3`, `writeVec3` - Static methods to read and write vectors.
        - `readContainerId`, `writeContainerId` - Methods to read and write menu identifiers.
        - `readChunkPos`, `writeChunkPos` - Methods to read and write the chunk position.
    - `StreamCodec#composite` - A composite method that takes in seven/eight parameters.
- `net.minecraft.network.codec.ByteBufCodecs`
    - `CONTAINER_ID` - A stream codec to handle menu identifiers.
    - `ROTATION_BYTE` - A packed rotation into a byte.
    - `LONG` - A stream codec for a long, or 64 bytes.
    - `OPTIONAL_VAR_INT` - A stream codec for an optional integer, serializing `0` when not present, or one above the stored value.
        - `-1` cannot be sent properly using this stream codec.
- `net.minecraft.network.protocol.game`
    - `ClientboundEntityPositionSyncPacket` - A packet that syncs the entity's position.
    - `ClientboundPlayerRotationPacket` - A packet that contains the player's rotation.
- `net.minecraft.server`
    - `MinecraftServer`
        - `tickConnection` - Ticks the connection for handling packets.
        - `reportPacketHandlingException` - Reports a thrown exception when attempting to handle a packet
        - `pauseWhileEmptySeconds` - Determines how many ticks the server should be paused for when no players are on.
    - `SuppressedExceptionCollector` - A handler for exceptions that were supressed by the server.
- `net.minecraft.server.commands.LookAt` - An interface that defines what should happen to an entity when the command is run, typically moving it to look at another.
- `net.minecraft.server.level`
    - `ChunkHolder#hasChangesToBroadcast` - Returns whether there is any updates within the chunk to send to the clients.
    - `ChunkTaskDispatcher` - A task scheduler for chunks.
    - `DistanceManager`
        - `getSpawnCandidateChunks` - Returns all chunks that the player can spawn within.
        - `getTickingChunks` - Returns all chunks that are currently ticking.
    - `ServerChunkCache#onChunkReadyToSend` - Adds a chunk holder to broadcast to a queue.
    - `ServerEntityGetter` - An entity getter interface implementation that operates upon the `ServerLevel`.
        - Replcaes the missing methods from `EntityGetter`
    - `ServerPlayer`
        - `getTabListOrder` - Handles the order of players to cycle through in the player tab.
        - `getLastClientInput`, `setLastClientInput`, `getLastClientMoveIntent` - Handles how the server player interprets the client impulse.
        - `commandSource` - Returns the player's source of commands.
        - `createCommandSourceStack` - Creates the source stack of the player issuing the command.
    - `ThrottlingChunkTaskDispatcher` - A chunk task dispatcher that sets a maximum number of chunks that can be executing at once.
    - `TickingTracker#getTickingChunks` - Returns all chunks that are currently ticking.
- `net.minecraft.server.packs.repository.PackRepository#isAbleToClearAnyPack` - Rebuilds the selected packs and returns whether it is different from the currently selected packs.
- `net.minecraft.resources.DependantName` - A reference object that maps some registry object `ResourceKey` to a value. Acts similarly to `Holder` except as a functional interface.
- `net.minecraft.tags.TagKey#streamCodec` - Constructs a stream codec for the tag key.
- `net.minecraft.util`
    - `ARGB#vector3fFromRGB24` - Creates a `Vector3f` containing the RGB components using the low 24 bits of an integer.
    - `BinaryAnimator` - A basic animator that animates between two states using an easing function.
    - `ExtraCodecs`
        - `NON_NEGATIVE_FLOAT` - A float codec that validates the value cannot be negative.
        - `RGB_COLOR_CODEC` - An integer, float, or three vector float codec representing the RGB color.
        - `nonEmptyMap` - A map codec that validates the map is not empty.
    - `Mth`
        - `wrapDegrees` - Sets the degrees to a value within (-180, 180].
        - `lerp` - Linear interpolation between two vectors using their components.
        - `length` - Gets the length of a 2D point in space.
        - `easeInOutSine` - A cosine function that starts at (0,0) and alternates between 1 and 0 every pi.
        - `packDegrees`, `unpackDegrees` - Stores and reads a degree in `float` form to a `byte`.
    - `RandomSource#triangle` - Returns a random `float` between the two `floats` (inclusive, exclusive) using a trangle distribution.
    - `StringRepresentable$EnumCodec#byName` - Gets the enum by its string name or the provided supplier value if null.
    - `TriState` - An enum that represents three possible states: true, false, or default.
- `net.minecraft.util.datafix.ExtraDataFixUtils`
    - `patchSubType` - Rewrites the second type to the third type within the first type.
    - `blockState` - Returns a dynamic instance of the block state
    - `fixStringField` - Modifies the string field within a dynamic.
- `net.minecraft.util.thread.BlockableEventLookup`
    - `BLOCK_TIME_NANOS` - Returns the amount of time in nanoseconds that an event will block the thread.
    - `isNonRecoverable` - Returns whether the exception can be recovered from.
- `net.minecraft.world.damagesource.DamageSources`
    - `enderPearl` - Returns a damage source from when an ender pearl is hit.
    - `mace` - Returns a damage source where a direct entity hits another with a mace.
- `net.minecraft.world.entity`
    - `Entity`
        - `applyEffectsFromBlocks` - Applies any effects from blocks via `Block#entityInside` or hardcoded checks like snow or rain.
        - `isAffectedByBlocks` - Returns whether the entity is affect by the blocks when inside.
        - `checkInsideBlocks` - Gets all blocks that teh player has traversed and checks whether the entity is inside one and adds them to a set when present.
        - `oldPosition`, `setOldPosAndrot`, `setOldPos`, `setOldRot` - Helpers for updating the last position and rotation of the entity.
        - `getXRot`, `getYRot` - Returns the linearly interpolated rotation of the entity given the partial tick.
        - `isAlliedTo(Entity)` - Returns whether the entity is allied to this entity.
        - `teleportSetPosition` - Sets the position and rotation data of the entity being teleported via a `DimensionTransition`
        - `getLootTable` - Returns the `ResourceKey` of the loot table the entity should use, if present.
        - `isControlledByOrIsLocalPlayer` - Return whether the entity is the local player or is controlled by a local player.
        - `shouldPlayLavaHurtSound` - When `true`, plays the lava hurt sound when the entity is hurt by lava.
        - `onRemoval` - A method that gets called when the entity is removed.
        - `cancelLerp` - Stops any lerped movement.
        - `forceSetRotation` - Sets the rotation of the entity.
        - `isControlledByClient` - Returns whether the entity is controlled by client inputs.
    - `EntityType`
        - `getDefaultLootTable` now returns an `Optional` in case the loot table is not present
        - `$Builder#noLootTable` - Sets the entity type to have no loot spawn on death.
        - `$Builder#build` now takes in the resouce key of the entity type
    - `EntitySelector#CAN_BE_PICKED` - Returns a selector that gets all pickable entities not in spectator.
    - `LivingEntity`
        - `dropFromShearingLootTable` - Resolves a loot table with a shearing context.
        - `getItemHeldByArm` - Returns the stack held by the specific arm.
        - `getEffectiveGravity` - Returns the gravity applied to the entity.
        - `canContinueToGlide` - Returns whether the entity can stil glide in the sky.
        - `getItemBlockingWith` - Returns the stack the player is currently blocking with.
        - `canPickUpLoot` - Returns whether the entity can pick up items.
        - `dropFromGiftLootTable` - Resolves a loot table with a gift context.
        - `handleExtraItemsCreatedOnUse` - Handles when a living entity gets a new item as a result of using another item.
        - `isLookingAtMe` - Checks whether the provided entity is looking at this entity.
    - `PositionMoveRotation` - A helper for handling the position and rotation of the entity in context.
    - `WalkAnimationState#stop` - Stops the walking animation of the entity.
- `net.minecraft.world.entity.ai.attributes`
    - `AttributeInstance`
        - `getPermanentModifiers` - Returns all permanent modifiers applied to the entity.
        - `addPermanentModifiers` - Adds a collection of permanent modifiers to apply.
    - `AttributeMap#assignPermanentModifiers` - Copies the permanent modifiers from another map.
- `net.minecraft.world.entity.ai.control.Control#rotateTowards` - Returns a float that rotates to some final rotation by the provided difference within a clamped value.
- `net.minecraft.world.entity.ai.goal.Goal#getServerLevel` - Gets the server level given the entity or a level.
- `net.minecraft.world.entity.ai.navigation.PathNavigation`
    - `updatePathfinderMaxVisitedNodes` - Updates the maximum number of nodes the entity can visit.
    - `setRequiredPathLength` - Sets the minimum length of the path the entity must take.
    - `getMaxPathLength` - Returns the maximum length of the path the entity can take.
- `net.minecraft.world.entity.ai.sensing`
    - `PlayerSensor#getFollowDistance` - Returns the following distance of this entity.
    - `Sensor#wasEntityAttackableLastNTicks` - Returns a predicate that checks whether the entity is attackable within the specified number of ticks.
- `net.minecraft.world.entity.ai.village.poi.PoiRecord#pack`, `PoiSection#pack` - Packs the necessary point of interest information. This only removes the dirty runnable.
- `net.minecraft.world.entity.animal`
    - `AgeableWaterCreature` - A water creature that has an age state.
    - `Animal`
        - `createAnimalAttributes` - Creates the attribute supplier for animals.
        - `playEatingSound` - Plays the sound an animal makes while eating.
    - `Bee#isNightOrRaining` - Returns whether the current level has sky light and is either at night or raining.
    - `Cat#isLyingOnTopOfSleepingPlayer` - Returns whether the cat is on top of a sleeping player.
    - `Salmon#getSalmonScale` - Returns the scale factor to apply to the entity's bounding box.
    - `Wolf#DEFAULT_TAIL_ANGLE` - Returns the default tail angle of the wolf.
- `net.minecraft.world.entity.boss.enderdragon.DragonFlightHistory` - Holds the y and rotation of the dragon when flying through the sky. Used for animating better motion of the dragon's parts.
- `net.minecraft.world.entity.monster.Zombie#canSpawnInLiquids` - When true, the zombie can spawn in a liquid.
- `net.minecraft.world.entity.player`
    - `Inventory`
        - `isUsableForCrafting` - Returns whether the state can be used in a crafting recipe.
        - `createInventoryUpdatePacket` - Creates the packet to update an item in the inventory.
    - `Player`
        - `handleCreativeModeItemDrop` - Handles what to do when a player drops an item from creative mode.
        - `shouldRotateWithMinecart` - Returns whether the player should also rotate with the minecart.
        - `canDropItems` - When `true`, the player can drop items from the menu.
        - `getPermissionLevel`, `hasPermissions` - Returns the permissions of the player.
    - `StackedContents` - Holds a list of contents along with their associated size.
        - `$Output` - An interface that defines how the contents are accepted when picked.
- `net.minecraft.world.entity.projectile.Projectile`
    - `spawnProjectileFromRotation` - Spawns a projectile and shoots from the given rotation.
    - `spawnProjectileUsingShoot` - Spawns a projectile and sets the initial impulse via `#shoot`.
    - `spawnProjectile` - Spawns a projectile.
    - `applyOnProjectileSpawned` - Applies any additional configurations from the given level and `ItemStack`.
    - `onItemBreak` - Handles what happens when the item that shot the projectile breaks.
    - `shouldBounceOnWorldBorder` - Returns whether the projectile should bounce off the world border.
    - `setOwnerThroughUUID` - Set the owner of the projectile by querying it through its UUID.
    - `$ProjectileFactory` - Defines how a projectile is spawned from some `ItemStack` by an entity.
- `net.minecraft.world.entity.vehicle`
    - `AbstractBoat` - An entity that represents a boat.
    - `AbstractChestBoat` - An entity that represent a boat with some sort of inventory.
    - `ChestRaft` - An entity that represents a raft with some sort of inventory.
    - `Raft` - An entity that represents a raft.
- `net.minecraft.world.inventory.AbstractContainerMenu`
    - `addInventoryHotbarSlots` - Adds the hotbar slots for the given container at the x and y positions.
    - `addInventoryExtendedSlots` - Adds the player inventory slots for the given container at the x and y positions.
    - `addStandardInventorySlots` - Adds the hotbar and player inventory slots at their normal location for the given container at the x and y positions.
    - `setSelectedBundleItemIndex` - Toggles the selected bundle in a slot.
- `net.minecraft.world.item`
    - `BundleItem`
        - `getOpenBundleModelFrontLocation`, `getOpenBundleModelBackLocation` - Returns the model locations of the bundle.
        - `toggleSelectedItem`, `hasSelectedItem`, `getSelectedItem`, `getSelectedItemStack` - Handles item selection within a bundle.
        - `getNumberOfItemsToShow` - Determines the number of items in the bundle to show at once.
        - `getByColor` - Handles the available links from bundle to dyed bundles.
        - `getAllBundleItemColors` - Returns a stream of all dyed bundles.
    - `ItemStack`
        - `clearComponents` - Clears the patches made to the stack, not the item components.
        - `isBroken` - Returns wheter the stack has been broken.
        - `hurtWithoutBreaking` - Damages the stack without breaking the stack.
        - `getStyledHoverName` - Gets the stylized name component of the stack.
- `net.minecraft.world.item.component.BundleContents`
    - `canItemBeInBundle` - Whether the item can be put into the bundle.
    - `getNumberOfItemsToShow` - Determines the number of items in the bundle to show at once.
    - `hasSelectedItem`, `getSelectedItem` - Handles item selection within a bundle.
- `net.minecraft.world.item.enchantment.EnchantmentHelper`
    - `createBook` - Creates an enchanted book stack.
    - `doPostAttackEffectsWithItemSourceOnBreak` - Applies the enchantments after attack when the item breaks.
- `net.minecraft.world.level`
    - `BlockCollisions` has a constructor to take in a `CollisionContext`
    - `BlockGetter#boxTraverseBlocks` - Returns an iterable of the positions traversed along the vector in a given bounding box.
    - `CollisionGetter`
        - `noCollision` - Returns whether there is no collision between the entity and blocks, entities, and liquids if the `boolean` provided is `true`.
        - `getBlockAndLiquidCollisions` - Returns the block and liquid collisions of the entity within the bounding box.
        - `clipIncludingBorder` - Gets the block hit result for the specified clip context, clamped by the world border if necessary.
    - `EmptyBlockAndTintGetter` - A dummy `BlockAndTintGetter` instance.
    - `GameType#isValidId` - Checks whether the id matches an existing game type.
    - `LevelHeightAccessor#isInsideBuildHeight` - Returns whether the specified Y coordinate is within the bounds of the level.
- `net.minecraft.world.level.block`
    - `Block#UPDATE_SKIP_SHAPE_UPDATE_ON_WIRE` - A block flag that, when enabled, does not update the shape of a redstone wire.
    - `BonemealableFeaturePlacerBlock` - A block that places a configured feature and can be bonemealed.
- `net.minecraft.world.level.block.entity.trialspawner.TrialSpawnerData#resetStatistics` - Resets the data of the spawn to an empty setting, but does not clear the current mobs or the next spawning entity.
- `net.minecraft.world.level.block.piston.PistonMovingBlockEntity#getPushDirection` - Returns the push direction of the moving piston.
- `net.minecraft.world.level.block.state`
    - `BlockBehaviour`
        - `getEntityInsideCollisionShape`, `$BlockStateBase#getEntityInsideCollisionShape` - Determines the voxel shape of the block when the entity is within it.
        - `$Properties#overrideDescription` - Sets the translation key of the block name.
    - `StateHolder`
        - `getValueOrElse` - Returns the value of the property, else the provided default.
        - `getNullableValue` - Returns the value of the property, or null if it does not exist.
- `net.minecraft.world.level.block.state.properties.Property#getInternalIndex` - Converts the provided boolean to a 0 when true, or 1 otherwise.
- `net.minecraft.world.level.border.WorldBorder#clampVec3ToBound` - Clamps the vector to within the world border.
- `net.minecraft.world.level.chunk`
    - `ChunkAccess#canBeSerialized` - Returns true, allows the chunk to be written to disk.
    - `ChunkSource#onSectionEmptinessChanged` - Updates the section when it has data.
    - `LevelChunkSection`
        - `copy` - Makes a shallow copy of the chunk section.
        - `setUnsavedListener` - Adds a listener which takes in the chunk position whenever the chunk is marked dirty.
        - `$UnsavedListener` - A consumer of a chunk position called when the chunk is marked dirty.
    - `PalettedContainerRO#copy` - Creates a shallow copy of the `PalettedContainer`.
    - `UpgradeData#copy` - Creates a deep copy of `UpgradeData`.
- `net.minecraft.world.level.chunk.storage.IOWorker#store` - Stores the writes of the chunk to the worker.
- `net.minecraft.world.level.levelgen`
    - `SurfaceRules$Context#getSeaLevel`, `SurfaceSystem#getSeaLevel` - Gets the sea level of the generator settings.
    - `WorldOptions#testWorldWithRandomSeed` - Creates a test world with a randomly generated seed.
- `net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator$Context#checkBlock` - Checks if the block at the given position matches the predicate.
- `net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate`
    - `getJigsaws` - Returns the jigsaw blocks that are at the provided position with the given rotation.
    - `getJointType` - Returns the joint type of the jigsaw block.
    - `$JigsawBlockInfo` - A record which contains the block info for a jigsaw block.
        - Most methods that involve jigsaws have replaced the `$StructureBlockInfo` with a `$JigsawBlockInfo`.
- `net.minecraft.world.level.lighting.LayerLightSectionStorage#lightOnInColumn` - Returns whether there is light in the zero node section position.
- `net.minecraft.world.level.pathfinder.PathFinder#setMaxVisitedNodes` - Sets the maximum number of nodes that can be visited.
- `net.minecraft.world.level.portal`
    - `DimensionTransition#withRotation` - Updates the entity's spawn rotation.
    - `PortalShape#findAnyShape` - Finds a `PortalShape` that can be located at the given block position facing the specific direction.
- `net.minecraft.world.phys`
    - `AABB`
        - `clip` - Clips the vector inside the given bounding box, or returns an empty optional if there is no intersection.
        - `collidedAlongVector` - Returns whether this box collided with one of the bounding boxes provided in the list along the provided movement vector.
        - `getBottomCenter` - Gets the bottom center of the bounding box as a vector.
    - `Vec3`
        - `add`, `subtract` - Translates the vector and returns a new object.
        - `horizontal` - Returns the horizontal components of the vector.
        - `projectedOn` - Gets the unit vector representing this vector projected onto another vector.
- `net.minecraft.world.phys.shapes`
    - `CollisionContext`
        - `of(Entity, boolean)` - Creates a new entity collision context, where the `boolean` determines whether the entity can always stand on the provided fluid state.
        - `getCollisionShape` - Returns the collision shape collided with.
    - `VoxelShape#move(Vec3)` - Offsets the voxel shape by the provided vector.
- `net.minecraft.world.ticks.ScheduledTick#toSavedTick` - Converts a scheduled tick to a saved tick.

### List of Changes

- `F3 + F` now toggles fog rendering
- `com.mojang.blaze3d.platform`
    - `NativeImage`
        - `getPixelRGBA`, `setPixelRGBA` are now private. These are replaced by `getPixel` and `setPixel`, respectively
        - `getPixelsRGBA` -> `getPixels`
    - `Window#updateDisplay` now takes in a `TraceyFrrameCapture`, or `null`
- `net.minecraft.Util`
    - `backgroundExecutor`, `ioPool`, and `nonCriticalIoPool` now return a `TracingExecutor` instead of an `ExecutorService`
    - `wrapThreadWithTaskName` -> `runNamed` with its parameters flipped and no return value
- `net.minecraft.advancements.critereon`
    - `KilledByCrossbowTrigger` -> `KilledByArrowTrigger`, not one-to-one, takes in the stack in question
    - `PlayerPredicate` can now match the player's input
- `net.minecraft.client`
    - `Minecraft`
        - `debugFpsMeterKeyPress` -> `ProfilerPieChart#profilerPieChartKeyPress` obtained via `Minecraft#getDebugOverlay` and then `DebugScreenOverlay#getProfilerPieChart`
        - `getTimer` -> `getDeltaTracker`
        - `getToasts` -> `getToastManager`
    - `Options#setModelPart` is now public, replaces `toggleModelPart` but without broadcasting the change
    - `ParticleStatus` -> `net.minecraft.server.level.ParticleStatus`
- `net.minecraft.client.animation.KeyframeAnimations#animate` now takes in a `Model` instead of a `HierarchicalModel`
- `net.minecraft.client.gui.Font`
    - `drawInBatch(String, float, float, int, boolean, Matrix4f, MultiBufferSource, Font.DisplayMode, int, int, boolean)` is removed and should use the `Component` replacement
        - There is also a delegate that sets the inverse depth boolean to true by default for the `Component` `drawInBatch` method
    - `$StringRenderOutput` now takes in the `Font`, an optional background color, and a boolean representing if inverse depth should be use when drawing the text
    - `$StringRenderOutput#finish` is now package private
- `net.minecraft.client.gui.components`
    - `AbstractSelectionList`
        - `replaceEntries` is now public
        - `getRowTop`, `getRowBottom` is now public
    - `PlayerFaceRenderer#draw(GuiGraphics, ResourceLocation, int, int, int, int)` takes in a `PlayerSkin` instead of a `ResourceLocation`
- `net.minecraft.client.gui.components.toasts`
    - `Toast`
        - `Toast$Visibility render(GuiGraphics, ToastComponent, long)` -> `void render(GuiGraphics, Font, long)`
        - `slotCount` - `occupiedSlotCount`
    - `ToastComponent` -> `ToastManager`
- `net.minecraft.client.gui.font.glyphs.BakedGlyph`
    - `render` now takes in a single integer representing the color instead of four floats and is private
        - `renderChar` is the public replacement, taking in the `$GlyphInstance`, the `Matrix4f`, `VertexConsumer`, and color integer
    - `$Effect` is a record, now taking in a single integer representing the color instead of four floats
- `net.minecraft.client.gui.screens`
    - `LoadingOverlay#MOJANG_STUDIOS_LOGO_LOCATION` is now public
    - `Screen`
        - `renderBlurredBackground(float)` -> `renderBlurredBackground()`
        - `wrapScreenError` -> `fillCrashDetails`, not one to one as it only adds the relevant crash information and not actually throw the error
- `net.minecraft.client.gui.screens.inventory`
    - `AbstractContainerScreen#renderSlotHighlight` -> `renderSlotHighlightBack`, `renderSlotHighlightFront`, now private
    - `BookEditScreen` now takes in the `WritableBookContent`
    - `AbstractSignEditScreen`
        - `sign` is now protected
        - `renderSignBackground` no longer takes in the `BlockState`
    - `EffectRenderingInventoryScreen` -> `Screen#hasActiveEffects`, `EffectsInInventory`. Not one-to-one as `EffectsInInventory` now acts as a helper class to a screen to render its effects at the specified location.
- `net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent`
    - `getHeight()` -> `getHeight(Font)`
    - `renderImage` now takes in the `int` width and height of the rendering tooltip
- `net.minecraft.client.gui.screens.recipebook`
    - `GhostSlots#render` no longer takes in an x and y offset.
    - `RecipeBookComponent` no longer takes in an x and y offset.
- `net.minecraft.client.gui.screens.reporting.ReportReasonSelectionScreen` now takes in a `ReportType`
- `net.minecraft.client.gui.screens.worldselection`
    - `CreateWorldScreen`
        - `$DataPackReloadCookie` -> `DataPackReloadCookie`
        - `openFresh` now has an overload that takes in the `CreateWorldCallback`
    - `WorldCreationContext` now takes in the `InitialWorldCreationOptions`
    - `WorldOpenFlows#createFreshLevel` takes in a `Function<HolderLookup.Provider, WorldDimensions>` instead of `Function<RegistryAccess, WorldDimensions>`
- `net.minecraft.client.gui.spectator.SpectatorMenuItem#renderIcon` now takes in a `float` instead of an `int` to represent the alpha value
- `net.minecraft.client.multiplayer`
    - `ClientLevel` now takes in an `int` representing the sea level
        - `getSkyColor` now returns a single `int` instead of a `Vec3`
        - `getCloudColor` now returns a single `int` instead of a `Vec3`
        - `setGameTime`, `setDayTime` -> `setTimeFromServer`
    - `TagCollector` -> `RegistryDataCollector$TagCollector`, now package-private
- `net.minecraft.client.player`
    - `AbstractClientPlayer#getFieldOfViewModifier` now takes in a boolean representing whether the camera is in first person and a float representing the partial tick
    - `Input` -> `ClientInput` and `net.minecraft.world.entity.player.Input`
    - `KeyboardInput` now extends `ClientInput`
    - `LocalPlayer#input` is now `ClientInput`
- `net.minecraft.client.renderer`
    - `DimensionSpecialEffects#getSunriseColor` -> `getSunriseOrSunsetColor`
    - `GameRenderer`
        - `processBlurEffect` no longer takes in the partial tick `float`
        - `getFov` returns a `float` instead of a `double`
        - `getProjectionMatrix` now takes in a `float` instead of a `double`
    - `ItemModelShaper`
        - `shapes` is now private
        - `getItemModel(Item)` is removed
        - `getItemModel(ResourceLocation)` - Gets the baked model associated with the provided `ResourceLocation`.
        - `register` is removed
        - `getModelManager` is removed
        - `invalidateCache` - Clears the model map.
    - `LevelRenderer`
        - `renderSnowAndRain` -> `WeatherEffectRenderer`
        - `tickRain` -> `tickParticles`
        - `renderLevel` now takes in a `GraphicsResourceAllocator`
        - `renderClouds` -> `CloudRenderer`
        - `addParticle` is now public
        - `globalLevelEvent` -> `LevelEventHandler`
        - `entityTarget` -> `entityOutlineTarget`
        - `$TransparencyShaderException` no longer takes in the throwable cause
    - `SectionOcclusionGraph`
        - `onSectionCompiled` -> `schedulePropagationFrom`
        - `update` now takes in a `LongOpenHashSet` that holds the currently loaded section nodes
        - `$GraphState` is now package-private
        - `addSectionsInFrustum` now takes in a list to add the render sections to
    - `ShapeRenderer#renderShape` now takes in a single integer for the color instead of four floats
    - `ViewArea`
        - `repositionCamera` now takes in the `SectionPos` instead of two `double`s
        - `getRenderSectionAt` -> `getRenderSection`
- `net.minecraft.client.renderer.blockentity`
    - `BannerRenderer#renderPatterns` now takes in a `boolean` determining the glint render type to use
    - `*Renderer` classes that constructed `LayerDefinition`s have now been moved to their associated `*Model` class
    - `SignRenderer$SignModel` -> `SignModel`
- `net.minecraft.client.renderer.chunk.SectionRenderDispatcher` now takes in a `TracingExecutor` rather than just a `Executor`
    - `$CompiledSection#hasNoRenderableLayers` -> `hasRenderableLayers`
    - `$RenderSection` now takes in a compiled `long` of the section node
        - `setOrigin` -> `setSectionNode`
        - `getRelativeOrigin` -> `getNeighborSectionNode`
        - `cancelTasks` now returns nothing
        - `pointOfView` - A reference to the location of where the translucent render type is rendered from.
        - `resortTransparency` no longer takes in the `RenderType` and returns nothing
        - `hasTranslucentGeometry` - Returns whether the compiled blocks have a translucent render type.
        - `transparencyResortingScheduled` - Returns whether the last task was scheduled but not completed.
        - `isAxisAlignedWith` -> `$TranslucencyPointOfView#isAxisAligned`
    - `$CompileTask` is now public
        - No longer `Comparable`
        - The constructor no longer takes in the distance at creation
        - `isHighPriority` -> `isRecompile`
    - `$TranslucencyPointOfView` - Returns the coordinate representing the view point of the tranlucent render type in this section.
- `net.minecraft.client.renderer.culling.Frustum#cubeInFrustum` now returns an `int` representing the index of the first plane that culled the box
- `net.minecraft.client.renderer.DebugRenderer#render` now takes in the `Frustum`
- `net.minecraft.client.renderer.texture.atlas.sources.PalettedPermutations#loadPaletteEntryFromImage` is now private
- `net.minecraft.client.tutorial`
    - `Tutorial`
        - `addTimedToast`, `#removeTimedToast`, `$TimedToast` -> `TutorialToast` parameter
        - `onInput` takes in a `ClientInput` instead of an `Input`
    - `TutorialStepInstance`
        - `onInput` takes in a `ClientInput` instead of an `Input`
- `net.minecraft.core`
    - `Direction`
        - `getNearest` -> `getApproximateNearest`
        - `getNormal` -> `getUnitVec3i`
    - `HolderGetter$Provider#get` no longer takes in the registry key, instead reading it from the `ResourceKey`
    - `HolderLookup$Provider` now implements `HolderGetter$Provider`
        - `asGetterLookup` is removed as the interface is a `HolderGetter$Provider`
        - `listRegistries` -> `listRegistryKeys`
    - `Registry` now implements `HolderLookup$RegistryLookup`
        - `getTags` only returns a stream of named holder sets
        - `asTagAddingLookup` -> `prepareTagReload`
        - `bindTags` -> `WritabelRegistry#bindTag`
        - `get` -> `getValue`
        - `getOrThrow` -> `getValueOrThrow`
        - `getHolder` -> `get`
        - `getHolderOrThrow` -> `getOrThrow`
        - `holders` -> `listElements`
        - `getTag` -> `get`
        - `holderOwner`, `asLookup` is removed as `Registry` is an instance of them
    - `RegistryAccess`
        - `registry` -> `lookup`
        - `registryOrThrow` -> `lookupOrThrow`
    - `RegistrySynchronization#NETWORKABLE_REGISTRIES` -> `isNetworkable`
- `net.minecraft.core.cauldron.CauldronInteraction`
    - `FILL_WATER` -> `fillWaterInteraction`, now private
    - `FILL_LAVA` -> `fillLavaInteraction`, now private
    - `FILL_POWDER_SNOW` -> `fillPowderSnowInteraction`, now private
    - `SHULKER_BOX` -> `shulkerBoxInteraction`, now private
    - `BANNER` -> `bannerInteraction`, now private
    - `DYED_ITEM` -> `dyedItemIteration`, now private
- `net.minecraft.core.dispenser.BoatDispenseItemBehavior` now takes in the `EntityType` to spawn rather that the variant and chest boat boolean
- `net.minecraft.core.particles.DustColorTransitionOptions`, `DustParticleOptions` now takes in integers representing an RGB value instead of `Vector3f`s.
- `net.minecraft.data.loot`
    - `BlockLootSubProvider`
        - `HAS_SHEARS` -> `hasShears`
        - `createShearsOnlyDrop` is now an instance method
    - `EntityLootSubProvider`
        - `killedByFrog`, `killedByFrogVariant` now take in the getter for the `EntityType` registry
        - `createSheepTable` -> `createSheepDispatchPool`, not one-to-one as the table was replaced with a pool builder given a map of dye colors to loot tables
- `net.minecraft.gametest.framework`
    - `GameTestHelper#assertEntityPresent`, `assertEntityNotPresent` takes in a bounding box instead of two vectors
    - `GameTestInfo#getOrCalculateNorthwestCorner` is now public
- `net.minecraft.network.chat.Component#score` now takes in a `SelectorPattern`
- `net.minecraft.network.chat.contents.ScoreContents`, `SelectorContents` is now a record
- `net.minecraft.network.protocol.login.ClientboundGameProfilePacket` -> `ClientboundLoginFinishedPacket`
- `net.minecraft.network.protocol.game`
    - `ClientboundMoveEntityPacket#getyRot`, `getxRot` now returns a `float` of the degrees
    - `ClientboundPlayerPositionPacket` is now a record, taking in a `PositionMoverotation` representing the change
        - `relativeArguments` -> `relatives`
        - `yRot`, `xRot` -> `ClientboundPalyerRotationPacket`
    - `ClientboundSetTimePacket` is now a record
    - `ClientboundRotateHeadPacket#getYHeadRot` now returns a `float` of the degrees
    - `ClientboundTeleportEntityPacket` is now a record, where the necessary parameters are passed into the packet instead of the entity
    - `ServerboundPlayerInputPacket` is now a record, taking in an `Input`
- `net.minecraft.resources.RegistryDataLoader$Loader#loadFromNetwork` now takes in a `$NetworkedRegistryData`, which contains the packed registry entries
- `net.minecraft.server`
    - `MinecraftServer` no longer implements `AutoCloseable`
        - `tickChildren` is now protected
        - `wrapRunnable` is now public
    - `ReloadableServerRegistries#reload` now takes in a list of pending tags and returns a `$LoadResult` instead of a layered registry access
    - `ReloadableServerResources`
        - `loadResources` now takes in a list of pending tags and the server `Executor`
        - `updateRegistryTags` -> `updateStaticRegistryTags`
    - `ServerFunctionLibrary#getTag`, `ServerFunctionManager#getTag` returns a list of command functions
- `net.minecraft.server.level`
    - `ChunkHolder`
        - `blockChanged`, `sectionLightChanged` now returns `boolean` if the information has changed
        - `addSaveDependency` is now protected, a method within `GenerationChunkHolder`
    - `ChunkTaskPriorityQueue` no longer takes in a generic
        - The constructor no longer takes in the maximum number of tasks to do
        - `submit` now takes in a `Runnable` rather than an `Optional`
        - `pop` returns a `$TasksForChunk` instead of a raw `Stream`
    - `ChunkTaskPriorityQueueSorter` -> `ChunkTaskDispatcher`
    - `ServerPlayer`
        - `teleportTo` takes in a `boolean` that determines whether the camera should be set
        - `INTERACTION_DISTANCE_VERIFICATION_BUFFER` -> `BLOCK_INTERACTION_DISTANCE_VERIFICATION_BUFFER`
            - Also splits into `ENTITY_INTERACTION_DISTANCE_VERIFICATION_BUFFER` set to 3.0
        - `findRespawnPositionAndUseSpawnBlock` now deals with `TeleportTransition`
    - `TextFilterClient` -> `ServerTextFilter`
    - `ThreadedLevelLightEngine` now takes in a `ConsecutiveExecutor` and `ChunkTaskDispatcher` instead of a `ProcessorMailbox` and a `ProcessorHandle`, respectively
- `net.minecraft.server.packs.resources.ProfiledReloadInstance$State` is now a record
- `net.minecraft.sounds.SoundEvent` is now a record
- `net.minecraft.tags`
    - `TagEntry$Lookup#element` now takes in a `boolean` representing if the element is required
    - `TagLoader` now takes in an `$ElementLookup`, which functions the same as its previous function parameter
        - `build` now returns a value of lists
        - `loadAndBuild` -> `loadTagsFromNetwork`, `loadTagsForExistingRegistries`, `loadTagsForRegistry`, `buildUpdatedLookups`
    - `TagNetworkSerialization$NetworkPayload`
        - `size` -> `isEmpty`
        - `applyToRegistry` -> `resolve`
- `net.minecraft.util`
    - `FastColor` -> `ARGB`
        - `scaleRGB` overload with an alpha integer and three floats.
    - `Mth#color` -> `ARGB#color`
- `net.minecraft.util.profiling.metrics.MetricCategory#MAIL_BOXES` -> `CONSECUTIVE_EXECUTORS`
- `net.minecraft.util.thread`
    - `BlockableEventLoop#waitForTasks` is now protected
    - `ProcessorMailbox` no longer implements `AutoCloseable`
- `net.minecraft.util.worldupdate.WorldUpgrader` implements `AutoCloseable`
- `net.minecraft.world.LockCode` now takes in an `ItemPredicate` instead of a `String` representing the item name
    - `addToTag`, `fromTag` now takes in a `HolderLookup$Provider`
- `net.minecraft.world.effect`
    - `MobEffect#applyEffectTick`, `applyInstantenousEffect`, `onMobRemoved`, `onMobHurt` now takes in the `ServerLevel`
    - `MobEffectInstance#onMobRemoved`, `onMobHurt` now takes in the `ServerLevel`
- `net.minecraft.world.entity`
    - `AgeableMob$AgeableMobGroupData` now has a public constructor
    - `AnimationState#getAccumulatedTime` -> `getTimeInMillis`
    - `Entity` no longer implements `CommandSource`
        - `setOnGroundWithMovement` now takes in an additional `boolean` representing whether there is any horizontal collision.
        - `getInputVector` is now protected
        - `isAlliedTo(Entity)` -> `considersEntityAsAlly`
        - `teleportTo` now takes in an additional `boolean` that determines whether the camera should be set
        - `checkInsideBlocks()` -> `recordMovementThroughBlocks`, not one-to-one as it takes in the movement vectors
        - `checkInsideBlocks(Set<BlockState>)` -> `collectBlockCollidedWith`, now private
        - `kill` now takes in the `ServerLevel`
        - `hurt` has been marked as deprecated, to be replaced by `hurtServer` and `hurtClient`
            - `hurtOrSimulate` acts as a helper to determine which to call, also marked as deprecated
        - `spawnAtLocation` now takes in a `ServerLevel`
        - `isInvulnerableTo` -> `isInvulnerableToBase`, now protected and final
            - `isInvulnerableTo` is moved to `LivingEntity#isInvulnerableTo`
        - `teleportSetPosition` now public and takes in a `PositionMoveRotation` and `Relative` set instead of the `DimensionTransition`
        - `createCommandSourceStack` -> `createCommandSourceStackForNameResolution`, not one to one as it takes in the `ServerLevel`
        - `mayInteract` now takes in the `ServerLevel` instead of just the `Level`
        - `setOldRot` is now public
        - `changeDimension` -> `teleport`, returns `ServerPlayer` given `TeleportTransition`
        - `canChangeDimensions` -> `canTeleport`
    - `EntitySpawnReason#SPAWN_EGG` -> `SPAWN_ITEM_USE`, not one-to-one as this indicates the entity can be spawned from any item
    - `EntityType`
        - `create`, `loadEntityRecursive`, `loadEntitiesRecursive`, `loadStaticEntity` now takes in an `EntitySpawnReason`
        - `*StackConfig` now takes in a `Level` instead of a `ServerLevel`
    - `EquipmentTable` now has a constructor that takes in a single float representing the slot drop chance for all equipment slots
    - `MobSpawnType` -> `EntitySpawnReason`
    - `Leashable#tickLeash` now takes in the `ServerLevel`
    - `LivingEntity`
        - `getScale` is now final
        - `onAttributeUpdated` is now protected
        - `activeLocationDependentEnchantments` now takes in an `EquipmentSlot`
        - `handleRelativeFrictionAndCalculateMovement` is now private
        - `updateFallFlying` is now protected
        - `onEffectRemoved` -> `onEffectsRemoved`
        - `spawnItemParticles` is now public
        - `getLootTable` -> `Entity#getLootTable`, wrapped in optional
        - `getBaseExperienceReward` now takes in the `ServerLevel`
        - `triggerOnDeathMobEffects` now takes in the `ServerLevel`
        - `canAttack` is removed
        - `dropEquipment` now takes in the `ServerLevel`
        - `dropExperience` now takes in the `ServerLevel`
        - `dropFromLootTable` now takes in the `ServerLevel`
        - `actuallyHurt`, `doHurtTarget` now takes in the `ServerLevel`
        - `hasLineOfSight` overload with clip contexts and a eye y supplier
        - `makePoofParticles` is now public
    - `Mob`
        - `pickUpItem`, `wantsToPickUp` now takes in the `ServerLevel`
        - `equipItemIfPossible` now takes in the `ServerLevel`
        - `customServerAiStep` now takes in the `ServerLevel`
        - `dropPreservedEquipment` now takes in the `ServerLevel`
    - `NeutralMob`
        - `isAngryAt`, `isAngryAtAllPlayers` now takes in the `ServerLevel`
        - `playerDied` now takes in the `ServerLevel`
    - `PortalProcessor#getPortalDestination` now returns a `TeleportTransition`
    - `PositionMoveRotation`
        - `of(ClientboundPlayerPositionPacket)` -> `ofEntityUsingLerpTarget(Entity)`
        - `of(DimensionTransition)` -> `of(TeleportTransition)`
    - `Shearable#shear` now takes in the `ServerLevel` and `ItemStack` that is shearing the entity
    - `RelativeMovement` -> `Relative`, expanded to contain delta movement
    - `WalkAnimationState#update` now takes in an additional `float` representing the position scale when moving.
- `net.minecraft.world.entity.ai.behavior`
    - `StartAttacking` now takes in a `$TargetFinder` and additionally a `$StartAttackingCondition`
        - Both are functional interfaces that replace the previous functions/predicates, though with an extra `ServerLevel` parameter
    - `StopAttackingIfTargetInvalid` now takes in a `$TargetErasedCallback` and/or a `$StopAttackCondition`
        - Both are functional interfaces that replace the previous consumers/predicates, though with an extra `ServerLevel` parameter
    - `MeleeAttack#create` can now take in a predicate to test the mob for
    - `Swim` now takes in a generic representing the mob
- `net.minecraft.world.entity.ai.control.LookControl#rotateTowards` -> `Control#rotateTowards`
- `net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal` now takes in a `$Selector`
     - It is a functional interface that replaces the previous predicate, though with an extra `ServerLevel` parameter
- `net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities` now takes in a `ServerLevel`
- `net.minecraft.world.entity.ai.sensing`
    - `NearestLivingEntitySensor`
        - `radiusXZ`, `radiusY` -> `Attributes#FOLLOW_RANGE`
        - `isMatchingEntity` now takes in a `ServerLevel`
    - `Sensor`
        - `TARGETING_RANGE` is now private
        - `isEntityTargetable`, `isEntityAttackable`, `isEntityAttackableIgnoringLineOfSight` now take in a `ServerLevel`
        - `wasEntityAttackableLastNTicks`, `rememberPositives` now delas with `BiPredicate`s instead of `Predicate`s
- `net.minecraft.world.entity.ai.targeting.TargetingConditions`
    - `selector` now takes in a `$Selector`
        - It is a functional interface that replaces the previous predicate, though with an extra `ServerLevel` parameter
    - `test` now takes in a `ServerLevel`
- `net.minecraft.world.entity.ai.village.poi.PoiRecord#codec`, `PoiSection#codec` -> `$Packed#CODEC`
- `net.minecraft.world.entity.animal`
    - `Fox$Type` -> `$Variant`
    - `MushroomCow$MushroomType` -> `$Variant`
        - `$Variant` no longer takes in the loot table
    - `Salmon` now has a variant for its size
    - `Wolf`
        - `getBodyRollAngle` -> `#getShakeAnim`, not one-to-one as the angle is calculated within the render state
        - `hasArmor` is removed
- `net.minecraft.world.entity.animal.horse.AbstractHorse#followMommy` now takes in a `ServerLevel`
- `net.minecraft.world.entity.boss.enderdragon.EnderDragon#onCrystalDestroyed` now takes in a `ServerLevel`
- `net.minecraft.world.entity.boss.enderdragon.phases.DragonPhaseInstance#doServerTick` now takes in a `ServerLevel`
- `net.minecraft.world.entity.boss.wither.WitherBoss#getHead*Rot` -> `getHead*Rots`, returns all rotations rather than just the provided index
- `net.minecraft.world.entity.decoration`
    - `ArmorStand` default rotations are now public
        - `isShowArms` -> `showArms`
        - `isNoBasePlate` -> `showBasePlate`
    - `PaintingVariant` now takes in a title and author `Component`
- `net.minecraft.world.entity.item.ItemEntity#getSpin` is now static
- `net.minecraft.world.entity.monster.Monster#isPreventingPlayerRest` now takes in a `ServerLevel`
- `net.minecraft.world.entity.monster.breeze.Breeze#getSnoutYPosition` -> `getFiringYPosition`
- `net.minecraft.world.entity.monster.hoglin.HoglinBase#hurtAndThrowTarget` now takes in a `ServerLevel`
- `net.minecraft.world.entity.monster.piglin.PiglinAi#isWearingGold` -> `#isWearingSafeArmor`
- `net.minecraft.world.entity.npc.InventoryCarrier#pickUpItem` now takes in a `ServerLevel`
- `net.minecraft.world.entity.player`
    - `Player#disableShield` now takes in the stack to apply the cooldown to
    - `Inventory`
        - `findSlotMatchingUnusedItem` -> `findSlotMatchingCraftingIngredient`
        - `swapPaint` -> `setSelectedHotbarSlot`
        - `StackedContents` -> `StackedItemContents`
- `net.minecraft.world.entity.projectile`
    - `AbstractArrow#inGround` -> `IN_GROUND`, now an `EntityDataAccessor`
        - Protected accessible via `isInGround` and `setInGround`
    - `ThrowableItemProjectile` can now take in an `ItemStack` of the item thrown
- `net.minecraft.world.entity.raid.Raid#getLeaderBannerInstance` -> `getOminousBannerInstance`
- `net.minecraft.world.entity.vehicle`
    - `Boat$Type` now takes in the supplied boat item and the translation key for the item, but no longer take in the planks they are made from
    - `ContainerEntity`
        - `*LootTable*` -> `ContainerLootTable`
        - `chestVehicleDestroyed` now takes in a `ServerLevel`
    - `VehicleEntity`
        - `destroy` now takes in a `seerverLevel`
        - `getDropItem` is now protected
- `net.minecraft.world.item`
    - `BoatItem` now takes in an `EntityType` instead of the variant and chest boolean
    - `ItemStack#hurtEnemy`, `postHurtEnemy` now take in a `LivingEntity` instead of a `Player`
    - `SmithingTemplateItem` now takes in the `Item.Properties` instead of hardcoding it, also true for static initializers
    - `UseAnim` -> `ItemUseAnimation`
- `net.minecraft.world.item.crafting.ShulkerBoxColoring` -> `TransmuteRecipe`, expanded to copy any data stored on the item to the result item
- `net.minecraft.world.item.enchantment.EnchantmentHelper`
    - `onProjectileSpawned` now takes in a `Projectile` instead of an `AbstractArrow`
- `net.minecraft.world.item.enchantment.effects.DamageItem` -> `ChangeItemDamage`
- `net.minecraft.world.level`
    - `GameRules` takes in a `FeatureFlagSet` during any kind of construction
        - `$IntegerValue#create` takes in a `FeatureFlagSet`
        - `$Type` takes in a `FeatureFlagSet`
    - `Level`
        - `setSpawnSettings` no longer takes in a `boolean` to determine whether to spawn friendlies
        - `getGameRules` -> `ServerLevel#getGameRules`
    - `LevelAccessor` now implements `ScheduledTickAccess`, an interface that now contains the tick scheduling methods that were originally on `LevelAccessor`
        - `neighborShapeChanged` switches the order of the `BlockState` and neighbor `BlockPos` parameters
    - `LevelHeightAccessor`
        - `getMinBuildHeight` -> `getMinY`
        - `getMaxBuildHeight` -> `getMaxY`, this value is one less than the previous version
        - `getMinSection` -> `getMinSectionY`
        - `getMaxSection` -> `getMaxSectionY`, this value is one less than the previous version
    - `NaturalSpawner#spawnForChunk` has been split into two methods: `getFilteredSpawningCategories`, and `spawnForChunk`
- `net.minecraft.world.level.biome#Biome#getPrecipitationAt`, `coldEnoughToSnow`, `warmEnoughToRain`, `shouldMeltFrozenOceanIcebergSlightly` now takes in an `int` representing the the base height of the biome
- `net.minecraft.world.level.block`
    - `Block`
        - `shouldRenderFace` takes in the relative state for the face being checked, no longer passing in the `BlockGetter` or `BlockPos`s.
        - `updateEntityAfterFallOn` -> `updateEntityMovementAfterFallOn`
        - `$BlockStatePairKey` -> `FlowingFluid$BlockStatePairKey`, now package private
        - `getDescriptionId` -> `BlockBehaviour#getDescriptionId`, also a protected field `descriptionId`
    - `ChestBlock` constructor switched its parameter order
    - `Portal#getPortalDestination` now returns `TeleportTransition`
- `net.minecraft.world.level.block.entity`
    - `AbstractFurnaceBlockEntity#serverTick` now takes in a `ServerLevel` instead of a `Level`
    - `BrushableBlockEntity`
        - `brush` now takes in the level and stack performing the brushing behavior
        - `unpackLootTable` is now private
        - `checkReset` now takes in the server level
- `net.minecraft.world.level.block.state`
    - `BlockBehaviour`
        - `getOcclusionShape`, `getLightBlock`, `propagatesSkylightDown` only takes in the `BlockState`, not the `BlockGetter` or `BlockPos`
        - `getLootTable` now returns an `Optional`, also a protected field `drops`
        - `$BlockStateBase#getOcclusionShape`, `getLightBlock`, `getFaceOcclusionShape`, `propagatesSkylightDown`, `isSolidRender` no longer takes in the `BlockGetter` or `BlockPos`
        - `$BlockStateBase#getOffset` no longer takes in the `BlockGetter`
        - `$OffsetFunction#evaluate` no longer takes in the `BlockGetter`
        - `$Properties#dropsLike` -> `overrideLootTable`
    - `StateHolder#findNextInCollection` now takes in a `List` instead of a `Collection`
- `net.minecraft.world.level.chunk`
    - `ChunkAccess`
        - `addPackedPostProcess` now takes in a `ShortList` instead of a single `short`
        - `getTicksForSerialization` now takes in a `long` of the game time
        - `unsaved` is now private
        - `setUnsaved` -> `markUnsaved`, `tryMarkSaved`
        - `$TicksToSave` -> `$PackedTicks`
    - `ChunkSource#setSpawnSettings` no longer takes in a `boolean` to determine whether to spawn friendlies
    - `LevelChunk#postProcessGeneration` now takes in a `ServerLevel`
    - `Palette#copy` now takes in a `PaletteResize`
- `net.minecraft.world.level.chunk.status.WorldGenContext` now takes in an `Executor` or the main thread rather than a processor handle mail box
    - The construtor also takes in a `LevelChunk$UnsavedListener` for when a chunk is marked as dirty
- `net.minecraft.world.level.chunk.storage`
    - `ChunkSerializer` -> `SerializableChunkData`
    - `ChunkStorage#write` now takes in a supplied `CompoundTag` instead of the instance itself
    - `SectionStorage` now takes in a second generic representing the packed form of the storage data
        - The constructor now takes in the packed codec, a function to convert the storage to a packed format, and a function to convert the packed and dirty runnable back into the storage.
- `net.minecraft.world.level.levelgen`
    - `Aquifer$FluidStatus` is now a record
    - `WorldDimensions#withOverworld` now takes in a `HolderLookup` instead of the `Registry` itself
    - `BlendingData` now has a packed and unpacked state for serializing the interal data as a simple object
- `net.minecraft.world.level.levelgen.material.MaterialRuleList` now takes in an array instead of a list
- `net.minecraft.world.level.levelgen.placement.PlacementContext#getMinBuildHeight` -> `getMinY`
- `net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement#getShuffledJigsawBlocks` now returns a `StructureTemplate$JigsawBlockInfo`
- `net.minecraft.world.level.lighting`
    - `LevelLightEngine#lightOnInSection` -> `lightOnInColumn`
    - `LightEngine`
        - `hasDifferentLightProperties`, `getOcclusionShape` no longer takes in the `BlockGetter` or `BlockPos`
        - `getOpacity` no longer takes in the `BlockPos`
        - `shapeOccludes` no longer takes in the two `longs` representing the packed positions
- `net.minecraft.world.level.material`
    - `FlowingFluid`
        - `spread` now takes in the `BlockState` at the current position
        - `getSlopeDistance` previous parameters have been merged into a `$SpreadContext` object
        - `spread`, `getNewLiquid`, `canConvertToSource`, `getSpread` now takes in a `ServerLevel`
    - `Fluid`
        - `tick` now takes in the `BlockState` at the current position
        - `tick` and `randomTick` now take in the `ServerLevel`
    - `FluidState`
        - `tick` now takes in the `BlockState` at the current position
        - `tick` and `randomTick` now take in the `ServerLevel`
    - `MapColor#calculateRGBColor` -> `calculateARGBColor`
- `net.minecraft.world.level.portal`
    - `DimensionTransition` -> `TeleportTransition`
        - `pos` -> `position`
        - `speed` -> `deltaMovement`
        - The constructor can now take in a set of `Relatives` to indicate in what motions should the positions be moved relative to another
    - `PortalShape#createPortalBlocks` now takes in a `LevelAccessor`
- `net.minecraft.world.level.saveddata.SavedData#save(File, HolderLookup$Provider)` now returns `CompoundTag`, not writing the data to file in the method
- `net.minecraft.world.level.storage`
    - `DimensionDataStorage` now implements `AutoCloseable`
        - The constructor takes in a `Path` instead of a `File`
        - `save` -> `scheduleSave` and `saveAndJoin`
    - `LevelData#getGameRules` -> `ServerLevelData#getGameRules`
- `net.minecraft.world.phys.BlockHitResult` now takes in a boolean representing if the world border was hit
    - Adds in two helpers `hitBorder`, `isWorldBorderHit`
- `net.minecraft.world.ticks`
    - `ProtoChunkTicks#load` now takes in a list of saved ticks
    - `SavedTick#loadTickList` now returns a list of saved ticks, rather than consuming them
    - `SerializableTickContainer#save` -> `pack`

### List of Removals

- `com.mojang.blaze3d.Blaze3D`
    - `process`
    - `render`
- `com.mojang.blaze3d.pipeline.RenderPipeline`
    - Replaced by `com.mojang.blaze3d.framegraph.*` and `com.mojang.blaze3d.resources.*`
- `com.mojang.blaze3d.platform.NativeImage`
    - `setPixelLuminance`
    - `getRedOrLuminance`, `getGreenOrLuminance`, `getBlueOrLuminance`
    - `blendPixel`
    - `asByteArray`
- `com.mojang.blaze3d.systems.RenderSystem`
    - `glGenBuffers`
    - `glGenVertexArrays`
    - `_setShaderTexture`
    - `applyModelViewMatrix`
- `net.minecraft.Util#wrapThreadWithTaskName(String, Supplier)`
- `net.minecraft.advancements.critereon.EntitySubPredicates#BOAT`
- `net.minecraft.client.Options#setKey`
- `net.minecraft.client.gui.screens.inventory.EnchantmentScreen#time`
- `net.minecraft.client.multiplayer`
    - `ClientCommonPacketListenerImpl#strictErrorHandling`
    - `ClientLevel#isLightUpdateQueueEmpty`
    - `CommonListenerCookie#strictErrorHandling`
- `net.minecraft.client.particle.ParticleRenderType#PARTICLE_SHEET_LIT`
- `net.minecraft.client.renderer`
    - `GameRenderer#resetProjectionMatrix`
    - `LevelRenderer`
        - `playJukeboxSong`
        - `clear`
    - `PostChain`
        - `getTempTarget`, `addTempTarget`
    - `PostPass`
        - `setOrthoMatrix`
        - `getFilterMode`
- `net.minecraft.client.renderer.block.model.BlockModel#fromString`
- `net.minecraft.client.renderer.texture`
    - `AbstractTexture#blur`, `mipmap`
    - `TextureManager#bindForSetup`
- `net.minecraft.commands.arguments.coordinates.WorldCoordinates#current`
- `net.minecraft.core`
    - `Direction#fromDelta`
    - `Registry#getOrCreateTag`, `getTagNames`, `resetTags`
- `net.minecraft.server.MinecraftServer`
    - `isSpawningAnimals`
    - `areNpcsEnabled`
- `net.minecraft.server.level`
    - `GenerationChunkHolder#getGenerationRefCount`
    - `ServerPlayer`
        - `setPlayerInput`
        - `teleportTo(ServerLevel, double, double, double, float, float, boolean)`
- `net.minecraft.tags`
    - `TagManager`
    - `TagManagerSerialization$TagOutput`
- `net.minecraft.world.entity`
    - `AnimationState#updateTime`
    - `Entity`
        - `walkDist0`, `walkDist`
        - `wasOnFire`
        - `tryCheckInsideBlocks`
    - `EntitySelector$MobCanWearArmorEntitySelector`
- `net.minecraft.world.entity.ai.sensing`
    - `BreezeAttackEntitySensor#BREEZE_SENSOR_RADIUS`
    - `TemptingSensor#TEMPTATION_RANGE`
- `net.minecraft.world.entity.animal`
    - `Cat#getTextureId`
    - `Squid#setMovementVector`
    - `Wolf#isWet`
- `net.minecraft.world.entity.boss.dragon.EnderDragon`
    - `getLatencyPos`
    - `getHeadPartYOffset`
- `net.minecraft.world.entity.monster.Zombie#supportsBreakDoorGoal`
- `net.minecraft.world.entity.npc.Villager#setChasing`, `isChasing`
- `net.minecraft.world.entity.projectile`
    - `AbstractArrow#shotFromCrossbow`
    - `ThrowableProjectile(EntityType, LivingEntity, Level)`
- `net.minecraft.world.item`
    - `BannerPatternItem#getDisplayName`
    - `ItemStack#LIST_STREAM_CODEC`
- `net.minecraft.world.level.BlockGetter#getMaxLightLevel`
- `net.minecraft.world.level.block.entity.JigsawBlockEntity$JointType#byName`
- `net.minecraft.world.level.block.state.BlockBehaviour#isOcclusionShapeFullBlock`
- `net.minecraft.world.level.chunk.ChunkAccess#setBlendingData`
- `net.minecraft.world.level.storage.loot.LootDataType#deserialize`
- `net.minecraft.world.phys.AABB#getBottomCenter`
- `net.minecraft.world.phys.shapes.Shapes#getFaceShape`
- `net.minecraft.world.ticks.SavedTick#saveTick`
