# Minecraft 1.21.2/3 -> 1.21.4 Mod Migration Primer

This is a high level, non-exhaustive overview on how to migrate your mod from 1.21.2/3 to 1.21.4. This does not look at any specific mod loader, just the changes to the vanilla classes. All provided names use the official mojang mappings.

This primer is licensed under the [Creative Commons Attribution 4.0 International](http://creativecommons.org/licenses/by/4.0/), so feel free to use it as a reference and leave a link so that other readers can consume the primer.

If there's any incorrect or missing information, please file an issue on this repository or ping @ChampionAsh5357 in the Neoforged Discord server.

## Pack Changes

There are a number of user-facing changes that are part of vanilla which are not discussed below that may be relevant to modders. You can find a list of them on [Misode's version changelog](https://misode.github.io/versions/?id=1.21.4&tab=changelog).

## Client Items

Minecraft has moved the lookup and definition of how an item should be rendered to its own data generated system, which will be referred to as **Client Items**, located at `assets/<namespace>/items/<path>.json`. Client Items is similar to the block state model definition, but has the potential to have more information enscribed in the future. Currently, it functions as simply a linker to the models used for rendering.

All client items contain some `ItemModel$Unbaked` using the `model` field. Each unbaked model has an associated type, which defines how the item should be set up for rendering, or rendered in one specific case. These `type`s can be found within `ItemModels`. This primer will review all but one type, as that unbaked model type is specifically for bundles when selecting an item.

The item also contains a `properties` field which holds some metadata-related parameters. Currently, it only specifies a boolean that, when false, make the hand swap the currently held item instantly rather than animate the hand coming up.

```json5
// For some item 'examplemod:example_item'
// JSON at 'assets/examplemod/items/example_item.json'
{
    "model": {
        "type": "" // Set type here
        // Add additional parameters
    },
    "properties": {
        // When false, disables animation when swapping this item into the hand
        "hand_animation_on_swap": false
    }
}
```

### A Basic Model

The basic model definition is handled by the `minecraft:model` type. This contains two fields: `model`, to define the relative location of the model JSON, and an optional list of `tints`, to define how to tint each index.

`model` points to the model JSON, relative to `assets/<namespace>/models/<path>.json`. In most instances, a client item defintion will look something like this:

```json5
// For some item 'examplemod:example_item'
// JSON at 'assets/examplemod/items/example_item.json'
{
    "model": {
        "type": "minecraft:model",
        // Points to 'assets/examplemod/models/item/example_item.json'
        "model": "examplemod:item/example_item"
    }
}
```

#### Tint Sources

In model JSONs, some element faces will have a `tintindex` field which references some index into the `tints` list in the `minecraft:model` unbaked model type. The list of tints are `ItemTintSource`s, which are all defined in `net.minecraft.client.color.item.*`. All defined tint sources can be found within `ItemTintSources`, like `minecraft:constant` for a constant color, or `minecraft:dye`, to use the color of the `DataComponents#DYED_COLOR` or default if not present. All tint sources must return an opaque color, though all sources typically apply this by calling `ARGB#opaque`.

```json5
// For some item 'examplemod:example_item'
// JSON at 'assets/examplemod/items/example_item.json'
{
    "model": {
        "type": "minecraft:model",
        // Points to 'assets/examplemod/models/item/example_item.json'
        "model": "examplemod:item/example_item",
        // A list of tints to apply
        "tints": [
            {
                // For when tintindex: 0
                "type": "minecraft:constant",
                // 0x00FF00 (or pure green)
                "value": 65280
            },
            {
                // For when tintindex: 1
                "type": "minecraft:dye",
                // 0x0000FF (or pure blue)
                // Only is called if `DataComponents#DYED_COLOR` is not set
                "default": 255
            }
        ]
    }
}
```

To create your own `ItemTintSource`, you need to implement the `calculate` method register the `MapCodec` associated for the `type` field. `calculate` takes in the current `ItemStack`, level, and holding entity and returns an RGB integer with an opaque alpha, defining how the layer should be tinted.

Then, the `MapCodec` needs to be registered to `ItemTintSources#ID_MAPPER`, though this field is private by default, so some access changes or reflection needs to be applied.

```java
// The item source class
public record FromDamage(int defaultColor) implements ItemTintSource {

    public static final MapCodec<FromDamage> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            ExtraCodecs.RGB_COLOR_CODEC.fieldOf("default").forGetter(FromDamage::defaultColor)
        ).apply(instance, FromDamage::new)
    );

    public FromDamage(int defaultColor) {
        this.defaultColor = ARGB.opaque(defaultColor);
    }

    @Override
    public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity) {
        return stack.isDamaged() ? ARGB.opaque(stack.getBarColor()) : defaultColor;
    }

    @Override
    public MapCodec<FromDamage> type() {
        return MAP_CODEC;
    }
}

// Then, in some initialization location where ItemTintSources#ID_MAPPER is exposed
ItemTintSources.ID_MAPPER.put(
    // The registry name
    ResourceLocation.fromNamespaceAndPath("examplemod", "from_damage"),
    // The map codec
    FromDamage.MAP_CODEC
);
```

```json5
// For some object in the 'tints' array
{
    "type": "examplemod:from_damage",
    // 0x0000FF (or pure blue)
    // Only is called if the item has not been damaged yet
    "default": 255
}
```

### Ranged Property Model

Ranged property models, as defined by the `minecraft:range_dispatch` unbaked model type, are the most similar to the previous item override system. Essentially, the type defines some item property that can be scaled along with a list of thresholds and associated models. The model chosen is the one with the closest threshold value that is not over the property (e.g. if the property value is `4` and we have thresholds `3` and `5`, `3` would be chosen as it is the cloest without going over). The item property is defined via a `RangeSelectItemModelProperty`, which takes in the stack, level, entity, and some seeded value to get a float, usually scaled betwen 0 and 1 depending on the implementation. All properties can be found within `net.minecraft.client.renderer.item.properties.numeric.*` and are registered in `RangeSelectItemModelProperties`, such as `minecraft:cooldown`, for the cooldown percentage, or `minecraft:count`, for the current number of items in the stack or percentage of the max stack size when normalized.

```json5
// For some item 'examplemod:example_item'
// JSON at 'assets/examplemod/items/example_item.json'
{
    "model": {
        "type": "minecraft:range_dispatch",

        // The `RangeSelectItemModelProperty` to use
        "property": "minecraft:count",
        // A scalar to multiply to the computed property value
        // If count was 0.3 and scale was 0.2, then the threshold checked would be 0.3*0.2=0.06
        "scale": 1,
        "fallback": {
            // The fallback model to use if no threshold matches
            // Can be any unbaked model type
            "type": "minecraft:model",
            "model": "examplemod:item/example_item"
        },

        // ~~ Properties defined by `Count` ~~
        // When true, normalizes the count using its max stack size
        "normalize": true,

        // ~~ Entries with threshold information ~~
        "entries": [
            {
                // When the count is a third of its current max stack size
                "threshold": 0.33,
                "model": {
                    // Can be any unbaked model type
                }
            },
            {
                // When the count is two thirds of its current max stack size
                "threshold": 0.66,
                "model": {
                    // Can be any unbaked model type
                }
            }
        ]
    }
}
```

To create your own `RangeSelectItemModelProperty`, you need to implement the `get` method register the `MapCodec` associated for the `type` field. `get` takes in the stack, level, entity, and seeded value and returns an arbitrary float to be interpreted by the ranged dispatch model.

Then, the `MapCodec` needs to be registered to `RangeSelectItemModelProperties#ID_MAPPER`, though this field is private by default, so some access changes or reflection needs to be applied.

```java
// The ranged property class
public record AppliedEnchantments() implements RangeSelectItemModelProperty {

    public static final MapCodec<AppliedEnchantments> MAP_CODEC = MapCodec.unit(new AppliedEnchantments());

    @Override
    public float get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
        return (float) stack.getEnchantments().size();
    }

    @Override
    public MapCodec<AppliedEnchantments> type() {
        return MAP_CODEC;
    }
}

// Then, in some initialization location where RangeSelectItemModelProperties#ID_MAPPER is exposed
RangeSelectItemModelProperties.ID_MAPPER.put(
    // The registry name
    ResourceLocation.fromNamespaceAndPath("examplemod", "applied_enchantments"),
    // The map codec
    AppliedEnchantments.MAP_CODEC
);
```

```json5
// For some client item in 'model'
{
    "type": "minecraft:range_dispatch",

    // The `RangeSelectItemModelProperty` to use
    "property": "examplemod:applied_enchantments",
    // A scalar to multiply to the computed property value
    "scale": 0.5,
    "fallback": {
        // The fallback model to use if no threshold matches
        // Can be any unbaked model type
        "type": "minecraft:model",
        "model": "examplemod:item/example_item"
    },

    // ~~ Properties defined by `AppliedEnchantments` ~~
    // N/A (no arguments to constructor)

    // ~~ Entries with threshold information ~~
    "entries": [
        {
            // When there is one enchantment present
            // Since 1 * the scale 0.5 = 0.5
            "threshold": 0.5,
            "model": {
                // Can be any unbaked model type
            }
        },
        {
            // When there are two enchantments present
            "threshold": 1,
            "model": {
                // Can be any unbaked model type
            }
        }
    ]
}
```

### Select Property Model

Select property models, as defined by the `minecraft:select` unbaked model type, are functionally similar to ranged property models, except now it switches on some property, typically an enum. The item property is defined via a `SelectItemModelProperty`, which takes in the stack, level, entity, some seeded value, and the current display context to get one of the property values. All properties can be found within `net.minecraft.client.renderer.item.properties.select.*` and are registered in `SelectItemModelProperties`, such as `minecraft:block_state`, for the stringified value of a specified block state property, or `minecraft:display_context`, for the current `ItemDisplayContext`.

```json5
// For some item 'examplemod:example_item'
// JSON at 'assets/examplemod/items/example_item.json'
{
    "model": {
        "type": "minecraft:select",

        // The `SelectItemModelProperty` to use
        "property": "minecraft:display_context",
        "fallback": {
            // The fallback model to use if no threshold matches
            // Can be any unbaked model type
            "type": "minecraft:model",
            "model": "examplemod:item/example_item"
        },

        // ~~ Properties defined by `DisplayContext` ~~
        // N/A (no arguments to constructor)

        // ~~ Switch cases based on Selectable Property ~~
        "cases": [
            {
                // When the display context is `ItemDisplayContext#GUI`
                "when": "gui",
                "model": {
                    // Can be any unbaked model type
                }
            },
            {
                // When the display context is `ItemDisplayContext#FIRST_PERSON_RIGHT_HAND`
                "when": "firstperson_righthand",
                "model": {
                    // Can be any unbaked model type
                }
            }
        ]
    }
}
```

To create your own `SelectItemModelProperty`, you need to implement the `get` method register the `SelectItemModelProperty$Type` associated for the `type` field. `get` takes in the stack, level, entity, seeded value, and display context and returns an encodable object to be interpreted by the select model.

Then, the `MapCodec` needs to be registered to `SelectItemModelProperties#ID_MAPPER`, though this field is private by default, so some access changes or reflection needs to be applied.

```java
// The select property class
public record StackRarity() implements SelectItemModelProperty<Rarity> {

    public static final SelectItemModelProperty.Type<StackRarity, Rarity> TYPE = SelectItemModelProperty.Type.create(
        // The map codec for this property
        MapCodec.unit(new StackRarity()),
        // The codec for the object being selected
        Rarity.CODEC
    );

    @Nullable
    @Override
    public Rarity get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed, ItemDisplayContext displayContext) {
        // When null, uses the fallback model
        return stack.get(DataComponents.RARITY);
    }

    @Override
    public SelectItemModelProperty.Type<StackRarity, Rarity> type() {
        return TYPE;
    }
}

// Then, in some initialization location where SelectItemModelProperties#ID_MAPPER is exposed
SelectItemModelProperties.ID_MAPPER.put(
    // The registry name
    ResourceLocation.fromNamespaceAndPath("examplemod", "rarity"),
    // The property type
    StackRarity.TYPE
);
```

```json5
// For some item 'examplemod:example_item'
// JSON at 'assets/examplemod/items/example_item.json'
{
    "model": {
        "type": "minecraft:select",

        // The `SelectItemModelProperty` to use
        "property": "examplemod:rarity",
        "fallback": {
            // The fallback model to use if no threshold matches
            // Can be any unbaked model type
            "type": "minecraft:model",
            "model": "examplemod:item/example_item"
        },

        // ~~ Properties defined by `StackRarity` ~~
        // N/A (no arguments to constructor)

        // ~~ Switch cases based on Selectable Property ~~
        "cases": [
            {
                // When rarity is `Rarity#UNCOMMON`
                "when": "uncommon",
                "model": {
                    // Can be any unbaked model type
                }
            },
            {
                // When rarity is `Rarity#RARE`
                "when": "rare",
                "model": {
                    // Can be any unbaked model type
                }
            }
        ]
    }
}
```

### Conditional Property Model

Conditional property models, as defined by the `minecraft:condition` unbaked model type, are functionally similar to ranged property models, except now it switches on boolean. These are usually combined with range dispatch, such as when pulling the bow. The item property is defined via a `ConditionalItemModelProperty`, which takes in the stack, level, entity,  some seeded value, and the current display context to get a true of false statement. All properties can be found within `net.minecraft.client.renderer.item.properties.conditional.*` and are registered in `ConditionalItemModelProperties`, such as `minecraft:damaged`, for if the item is damaged, or `minecraft:has_component`, if it has a given data component.

```json5
// For some item 'examplemod:example_item'
// JSON at 'assets/examplemod/items/example_item.json'
{
    "model": {
        "type": "minecraft:condition",

        // The `SelectItemModelProperty` to use
        "property": "minecraft:damaged",

        // ~~ Properties defined by `Damaged` ~~
        // N/A (no arguments to constructor)

        // ~~ What the boolean outcome is ~~
        "on_true": {
            // Can be any unbaked model type
        },
        "on_false": {
            // Can be any unbaked model type
        }
    }
}
```

To create your own `ConditionalItemModelProperty`, you need to implement the `get` method register the `MapCodec` associated for the `type` field. `get` takes in the stack, level, entity, seeded value, and display context and returns a boolean to be interpreted by `on_true` and `on_false`, respectively.

Then, the `MapCodec` needs to be registered to `ConditionalItemModelProperties#ID_MAPPER`, though this field is private by default, so some access changes or reflection needs to be applied.

```java
// The predicate property class
public record TimePeriod(int month, MinMaxBounds.Ints dates, boolean enabled) implements ConditionalItemModelProperty {

    public static final MapCodec<TimePeriod> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            Codec.intRange(1, 12).fieldOf("month").forGetter(TimePeriod::month),
            MinMaxBounds.Ints.CODEC.fieldOf("dates").forGetter(TimePeriod::dates)
        ).apply(instance, TimePeriod::new)
    );

    public TimePeriod(int month, MinMaxBounds.Ints dates) {
        this.month = month;
        this.dates = dates;

        Calendar cal = Calendar.getInstance();
        this.enabled = cal.get(Calendar.MONTH) + 1 == this.month
            && this.dates.matches(cal.get(Calendar.DATE));
    }

    @Override
    public boolean get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed, ItemDisplayContext context) {
        return this.enabled;
    }

    @Override
    public MapCodec<TimePeriod> type() {
        return MAP_CODEC;
    }
}

// Then, in some initialization location where ConditionalItemModelProperties#ID_MAPPER is exposed
ConditionalItemModelProperties.ID_MAPPER.put(
    // The registry name
    ResourceLocation.fromNamespaceAndPath("examplemod", "time_period"),
    // The map codec
    TimePeriod.MAP_CODEC
);
```

```json5
// For some item 'examplemod:example_item'
// JSON at 'assets/examplemod/items/example_item.json'
{
    "model": {
        "type": "minecraft:condition",

        // The `SelectItemModelProperty` to use
        "property": "examplemod:time_period",

        // ~~ Properties defined by `TimePeriod` ~~
        // Month of July
        "month": 7,
        "dates": {
            // Between July 1st - 14th
            "min": 1,
            "max": 14
        },

        // ~~ What the boolean outcome is ~~
        "on_true": {
            // Can be any unbaked model type
        },
        "on_false": {
            // Can be any unbaked model type
        }
    }
}
```

### Composite Model

Composite models, defined by `minecraft:composite`, are essentially a combination of other model types to render. Specifically, this sets up multiple players to overlay models on top of one another when rendering.

```json5
// For some item 'examplemod:example_item'
// JSON at 'assets/examplemod/items/example_item.json'
{
    "model": {
        "type": "minecraft:composite",

        // Will render in the order they appear in the list
        "models": [
            {
                // Can be any unbaked model type
            },
            {
                // Can be any unbaked model type
            }
        ]
    }
}
```

### Special Dynamic Models

Special dynamic models, as defined by the `minecraft:special` unbaked model type, are the new system for block entity without level renderers (e.g., chests, banners, and the like). Instead of storing a baked model, these provide a render method to call. The special model wrapper takes in a base model used for grabbing the basic model settings (not the elements) and a `SpecialModelRenderer`. All special model renderers can be found within `net.minecraft.client.renderer.special.*` and are registered in `SpecialModelRenderers`.

```json5
// For some item 'examplemod:example_item'
// JSON at 'assets/examplemod/items/example_item.json'
{
    "model": {
        "type": "minecraft:special",

        // The model to read the particle texture and display transformation from
        "base": "minecraft:item/template_skull",
        "model": {
            // The special model renderer to use
            "type": "minecraft:head",

            // ~~ Properties defined by `SkullSpecialRenderer.Unbaked` ~~
            // The type of the skull block
            "kind": "wither_skeleton"
        }
    }
}
```

To create your own `SpecialModelRenderer`, you need to implement both the renderer and the `$Unbaked` model to read the data from the JSON. The `$Unbaked` model creates the `SpecialModelRenderer` via `bake` and is registered using a `MapCodec` for its `type`. The `SpecialModelRenderer` then extracts the necessary data from the stack needed to render via `extractArgument` and passes that to the `render` method. If you do not need any information from the stack, you can implement `NoDataSpecialModelRenderer` instead.

Then, the `MapCodec` needs to be registered to `SpecialModelRenderers#ID_MAPPER`, though this field is private by default, so some access changes or reflection needs to be applied.

If your item is a held block, it also needs to be added to the `SpecialModelRenderers#STATIC_BLOCK_MAPPING` for specific rendering scenarios via `BlockRenderDispatcher#renderSingleBLock` (e.g. in minecart, or picked up by endermen). Both the default model renderer and the special model renderer are called in this method; allowing for both the static block model and the dynamic special model to be rendered at the same time. As this map is immutable, you will either need to replace it or hook into `SpecialBlockModelRenderer` and somehow add to the stored map there.

```java
// The special renderer
public record SignSpecialRenderer(WoodType defaultType, Model model) implements SpecialModelRenderer<WoodType> {

    // Render the model
    @Override
    public void render(@Nullable WoodType type, ItemDisplayContext displayContext, PoseStack pose, MultiBufferSource bufferSource, int light, int overlay, boolean hasFoil) {
        VertexConsumer consumer = Sheets.getSignMaterial(type).buffer(bufferSource, this.model::renderType);
        this.model.renderToBuffer(pose, consumer, light, overlay);
    }

    // Get the wood type from the stack
    @Nullable
    @Override
    public WoodType extractArgument(ItemStack stack) {
        return (stack.getItem() instanceof BlockItem item && item.getBlock() instanceof SignBlock sign)
            ? sign.type() : this.defaultType;
    }

    // The model to read the json from
    public static record Unbaked(WoodType defaultType) implements SpecialModelRenderer.Unbaked {

        public static final MapCodec<SignSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                WoodType.CODEC.fieldOf("default").forGetter(SignSpecialRenderer.Unbaked::defaultType)
            ).apply(instance, SignSpecialRenderer.Unbaked::new)
        );

        // Create the special model renderer, or null if it fails
        @Nullable
        @Override
        public SpecialModelRenderer<?> bake(EntityModelSet modelSet) {
            return new SignSpecialRenderer(
                this.defaultType,
                SignRenderer.createSignModel(modelSet, defaultType, true)
            )
        }

        @Overrides
        public MapCodec<SignSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }
    }
}

// Then, in some initialization location where SpecialModelRenderers#ID_MAPPER is exposed
SpecialModelRenderers.ID_MAPPER.put(
    // The registry name
    ResourceLocation.fromNamespaceAndPath("examplemod", "sign"),
    // The map codec
    SignSpecialRenderer.Unbaked.MAP_CODEC
);
// Let assume we can add directly to SpecialModelRenderers#STATIC_BLOCK_MAPPING as well
// We'll have a Block EXAMPLE_SIGN
SpecialModelRenderers.STATIC_BLOCK_MAPPING.put(
    // The block with a special rendering as an item
    EXAMPLE_SIGN,
    // The unbaked renderer to use
    new SignSpecialRenderer.Unbaked(WoodType.BAMBOO)
);
```

```json5
// For some item 'examplemod:example_item'
// JSON at 'assets/examplemod/items/example_item.json'
{
    "model": {
        "type": "minecraft:special",

        // The model to read the particle texture and display transformation from
        "base": "minecraft:item/bamboo_sign",
        "model": {
            // The special model renderer to use
            "type": "examplemod:sign",

            // ~~ Properties defined by `SignSpecialRenderer.Unbaked` ~~
            // The default wood type if none can be found
            "default": "bamboo"
        }
    }
}
```

### Rendering an Item

Rendering an item is now done through the `ItemModelResolver` and `ItemStackRenderState`. This is similar to how the `EntityRenderState` works: first, the `ItemModelResolver` sets up the `ItemStackRenderState`, then the state is rendered via `ItemStackRenderState#render`.

Let's start with the `ItemStackRenderState`. For rendering, the only one we care about the following methods: `isEmpty`, `isGui3d`, `usesBlockLight`, `transform`, and `render`. `isEmpty` is used to determine whether the stack should render at all. Then `isGui3d`, `usesBlockLight`, and `transform` are used in their associated contexts to properly position the stack to render. Finally, `render` takes in the pose stack, buffer source, packed light, and overlay texture to render the item in its appropriate location.

`ItemModelResolver` is responsible for setting the information on the `ItemStackRenderState` needed to render. This is done through `updateForLiving` for items held by living entities, `updateForNonLiving` for items held by other kinds of entities, and `updateforTopItem`, for all other scenarios. `updateForItem`, which the previous two methods delegate to, take in the render state, the stack, the display context, if the stack is in the left hand, the level, the entity, and some seeded value. This will effectively clear the previous state via `ItemStackRenderState#clear` and then set up the new state via a delegate to `ItemModel#update`. The `ItemModelResolver` can always be obtained via `Minecraft#getItemModelResolver`, if you are not within a renderer context (e.g., block entity, entity).

```java
// In its most simple form, assuming you are not doing any transformations (which you should as necessary)
public class ExampleRenderer {
    private final ItemStackRenderState state = new ItemStackRenderState();

    public void render(ItemStack stack, Level level, PoseStack pose, MultiBufferSource bufferSource) {
        // First update the render state
        Minecraft.getInstance().getItemModelResolver().updateForTopItem(
            // The render state
            this.state,
            // The stack to update the state with
            stack,
            // The display context to render within
            ItemDisplayContext.NONE,
            // Whether it is in the left hand of the entity (use false when unknown)
            false,
            // The current level (can be null)
            level,
            // The holding entity (can be null)
            null,
            // An arbitrary seed value
            0
        );

        // Perform any desired transformations here

        // Then render the state
        this.state.render(
            // The pose stack with the required transformations
            pose,
            // The buffer sources
            bufferSource,
            // The packed light value
            LightTexture.FULL_BRIGHT,
            // The overlay texture value
            OverlayTexture.NO_OVERLAY
        );
    }
}
```

### Custom Item Model Defintions

To make a custom item model definition, we need to look at a few more methods in `ItemStackRenderState` which, although you won't typically use, is useful to understand: `ensureCapacity` and `newLayer`. Both of these are responsible for ensuring there are enough `ItemStackRenderState$LayerRenderState`s if you happen to be overlaying multiple models at once. Effectively, every time you are planning to render something in an unbaked model, `newLayer` should be called. If you plan on rendering multiple things on top of one another, then `ensureCapacity` should be set with the number of layers that you plan to render before calling `newLayer`.

An item model definition is made up of the `ItemModel`, which functionally defines a 'baked model' and its `ItemModel$Unbaked`, used for serialization.

The unbaked variant has two methods: `bake`, which is used to create the `ItemModel`, and `type`, which references the `MapCodec` to be registered to `ItemModels#ID_MAPPER`, though this field is private by default, so some access changes or reflection needs to be applied. `bake` takes in the `$BakingContext`, which contains the `ModelBaker` to get `BakedModel`s, the `EntityModelSet` for entity models, and the missing `ItemModel`.

The baked variant only has one method `update`, which is responsible for setting up everything necessary on the `ItemStackRenderState`. The model does no rendering itself.

```java
public record RenderTypeModelWrapper(BakedModel model, RenderType type) implements ItemModel {

    // Update the render state
    @Override
    public void update(ItemStackRenderState state, ItemStack stack, ItemModelResolver resolver, ItemDisplayContext displayContext, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
        ItemStackRenderState.LayerRenderState layerState = state.newLayer();
        if (stack.hasFoil()) {
            layerState.setFoilType(ItemStackRenderState.FoilType.STANDARD);
        }
        layerState.setupBlockModel(this.model, this.type);
    }

    public static record Unbaked(ResourceLocation model, RenderType type) implements ItemModel.Unbaked {
        // Create a render type map for the codec
        private static final BiMap<String, RenderType> RENDER_TYPES = Util.make(HashBiMap.create(), map -> {
            map.put("translucent_item", Sheets.translucentItemSheet());
            map.put("cutout_block", Sheets.cutoutBlockSheet());
        });
        private static final Codec<RenderType> RENDER_TYPE_CODEC = ExtraCodecs.idResolverCodec(Codec.STRING, RENDER_TYPES::get, RENDER_TYPES.inverse()::get);

        // The map codec to register
        public static final MapCodec<RenderTypeModelWrapper.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                ResourceLocation.CODEC.fieldOf("model").forGetter(RenderTypeModelWrapper.Unbaked::model),
                RENDER_TYPE_CODEC.fieldOf("render_type").forGetter(RenderTypeModelWrapper.Unbaked::type)
            )
            .apply(instance, RenderTypeModelWrapper.Unbaked::new)
        );

        @Override
        public void resolveDependencies(ResolvableModel.Resolver resolver) {
            // Resolve model dependencies, so pass in all known resource locations
            resolver.resolve(this.model);
        }

        @Override
        public ItemModel bake(ItemModel.BakingContext context) {
            // Get the baked model and return
            BakedModel baked = context.bake(this.model);
            return new RenderTypeModelWrapper(baked, this.type);
        }

        @Override
        public MapCodec<RenderTypeModelWrapper.Unbaked> type() {
            return MAP_CODEC;
        }
    }
}

// Then, in some initialization location where ItemModels#ID_MAPPER is exposed
ItemModels.ID_MAPPER.put(
    // The registry name
    ResourceLocation.fromNamespaceAndPath("examplemod", "render_type"),
    // The map codec
    RenderTypeModelWrapper.Unbaked.MAP_CODEC
);
```

```json5
// For some item 'examplemod:example_item'
// JSON at 'assets/examplemod/items/example_item.json'
{
    "model": {
        "type": "examplemod:render_type",
        // Points to 'assets/examplemod/models/item/example_item.json'
        "model": "examplemod:item/example_item",
        // Set the render type to use when rendering
        "render_type": "cutout_block"
    }
}
```

- `net.minecraft.client`
    - `ClientBootstrap` - Registers the maps backing the client; currently used for item model definitions.
    - `Minecraft`
        - `getEquipmentModels` is removed, only directly accessible in the `EntityRendererProvider$Context#getEquipmentAssets`
        - `getItemModelResolver` - Returns the updater for resolving the current model to be rendered in the `ItemStackRenderState$LayerRenderState`.
    - `KeyMapping#get` - Gets a key mapping based on its translation key.
- `net.minecraft.client.color.item`
    - `Constant` - A constant to tint the item texture.
    - `CustomModelDataSource` - Gets the color to tint based on an index in the `DataComponent#CUSTOM_MODEL_DATA` data component. If no index is found or is out of bounds, then the default color is used.
    - `Dye` - Gets the color to tint using the `DataComponent#DYED_COLOR` data component.
    - `Firework` - Gets the color to tint using the `DataComponent#FIRE_EXPLOSION` data component.
    - `GrassColorSource` - Gets the color to tint based on the provided temperature and downfall values.
    - `ItemColor` -> `ItemTintSource`, not one-to-one as indexing is setup by providing multiple `ItemTintSource`s in the model list.
    - `ItemColors` class is removed, now data generated as `ItemTintSource`s
    - `ItemTintSources` - A registry of sources for tinting an item texture in a model.
    - `MapColor` - Gets the color to tint using the `DataComponent#MAP_COLOR` data component.
    - `Potion` - Gets the color to tint using the `DataComponent#POTION_CONTENTS` data component.
    - `TeamColor` - Gets the color based on the holding entity's team color.
- `net.minecraft.client.data.Main` - The entrypoint for client data generation.
- `net.minecraft.client.particle.BreakingItemParticle` now takes in an `ItemStackRenderState` instead of an `ItemStack`
    - `$ItemParticleProvider` - An abstract particle provide that provides a simple method to calculate the `ItemStackRenderState`.
- `net.minecraft.client.renderer`
    - `BlockEntityWithoutLevelRenderer` class is removed, replaced by the `NoDataSpecialModelRenderer` datagen system
    - `ItemInHandRenderer` now takes in an `ItemModelResolver`
    - `ItemModelShaper` is removed, as the methods are available within the `ModelManager`
    - `Sheets`
        - `getBedMaterial` - Gets the bed material from the dye color.
        - `colorToResourceMaterial` - Gets the resource location of the dye color.
        - `createBedMaterial` - Creates the bed material from the dye color or resource location.
        - `getShulkerBoxMaterial` - Gets the shulker box material from the dye color.
        - `colorToShulkerMaterial` - Gets the resource location of the dye color for the shulker box.
        - `createShulkerMaterial` - Creates the shulker box material from the dye color or resource location.
        - `chestMaterial` - Creates a new material for a chest with the given resource location.
    - `SpecialBlockModelRenderer` - A map of blocks to special renderers for item variants.
- `net.minecraft.client.renderer.block.BlockRenderDispatcher` now takes in a supplied `SpecialBlockModelRenderer` instead of a `BlockEntityWithoutLevelRenderer`
- `net.minecraft.client.renderer.block.model`
    - `BakedOverrides` class is removed, replaced by the `RangeSelectItemModelProperty` datagen system
    - `BlockModel` now takes in a `TextureSlots$Data` instead of just a material map, and no longer takes in a list of `ItemOverride`s
        - `MISSING_MATERIAL` is removed, replaced by `minecraft:missingno`
        - `textureMap` -> `textureSlots`, now private, not one-to-one
        - `parent` is now private, not one-to-one
        - `parentLocation` is now private
        - `hasAmbientOcclusion` -> `getAmbientOcclusion`
        - `isResolved` is removed
        - `getOverrides` is removed
        - `getParent` - Returns the unbaked parent model.
        - `getTextureSlots` - Returns the texture data for the model.
        - `getElements` is now package-private
        - `$GuiLight` -> `UnbakedModel$GuiLight`
    - `FaceBakery`
        - `bakeQuad` is now static
        - `calculateFacing` is now private
    - `ItemModelGenerator` now implements `UnbakedModel`
    - `ItemOverride` class is removed, replaced by the `RangeSelectItemModelProperty` datagen system
    - `ItemTransforms` is now a record
        - `hasTransform` is removed
    - `TextureSlots` - A class which handles the texture mapping within a model. The data is read from `$Data` and stored as `$SlotContents` until it is resolved during the baking process into `Material`s.
    - `UnbakedBlockStateModel` now extends `ResolvableModel` instead of `UnbakedModel`
        - `bake` - Bakes the block state into its selectable models.
    - `Variant` is now a record
- `net.minecraft.client.renderer.blockentity`
    - `BannerRenderer` now has an overload constructor which takes in the `EntityModelSet`
        - `renderInHand` - Renders the item model of the banner.
    - `BedRenderer` now has an overload constructor which takes in the `EntityModelSet`
        - `renderInHand` - Renders the item model of the bed.
    - `BlockEntityRenderDispatcher(Font, EntityModelSet, Supplier<BlockRenderDispatcher>, Supplier<ItemRenderer>, Supplier<EntityRenderDispatcher>)` -> `BlockEntityRenderDispatcher(Font, Supplier<EntityModelSet>, BlockRenderDispatcher, ItemModelResolver, ItemRenderer, EntityRenderDispatcher)`
        - `renderItem` is removed, implemented in their specific classes
    - `BlockEntityRendererProvider` now takes in an `ItemModelResolver`
        - `getItemModelResolver` - Gets the resolver which returns the item models.
    - `ChestRenderer#xmasTextures` - Returns whether christmas textures should render on a chest.
    - `DecoratedPotRenderer` now has an overload constructor which takes in the `EntityModelSet`
        - `renderInHand` - Renders the item model of the pot.
    - `ShulkerBoxRenderer` now has an overload constructor which takes in the `EntityModelSet`
        - `render` - Renders the shulker box.
        - `$ShulkerBoxModel#animate` no longer takes in the `ShulkerBoxBlockEntity`
    - `SkullblockRenderer#createSkullRenderers` -> `createModel`, not one-to-one
- `net.minecraft.client.renderer.entity`
    - `EntityRenderDispatcher` now takes in an `IteModelResolver`, a supplied `EntityModelSet` instead of the instance, and an `EquipmentAssetManager` instead of a `EquipmentModelSet`
    - `EntityRendererProvider$Context` now takes in an `ItemModelResolver` instead of an `ItemRenderer`, and an `EquipmentAssetManager` instead of a `EquipmentModelSet`
        - `getItemRenderer` -> `getItemModelResolver`, not one-to-one
        - `getEquipmentModels` -> `getEquipmentAssets`
    - `FishingHookRenderer` - Returns the holding arm of the fishing hook.
    - `HumanoidMobRenderer`
        - `getArmPose` - Returns the arm pose of the entity.
        - `extractHumanoidRenderState` now takes in an `ItemModelResolver`
    - `ItemEntityRenderer`
        - `getSeedForItemStack` is removed
        - `renderMultipleFromCount` now takes in the `ItemClusterRenderState`, and removes the `ItemRenderer`, `ItemStack`, `BakedModel`, and 3d boolean
    - `ItemRenderer` no longer implements `ResourceManagerReloadListener`
        - The constructor now only takes in the `ItemModelResolver`
        - `render` -> `renderItem`, not one-to-one
        - `renderBundleItem` is removed
        - `getModel`, `resolveItemModel` is removed
    - `LivingEntityRenderer#itemRenderer` -> `itemModelResolver`, not one-to-one
    - `OminousItemSpawnerRenderer` now uses the `ItemClusterRenderState`
    - `SkeletonRenderer#getArmPose` -> `AbstractSkeletonRenderer#getArmPose`
    - `SnowGolemRenderer` now uses the `SnowGolemRenderState`
- `net.minecraft.client.renderer.entity.layers`
    - `CrossArmsItemLayer` now uses the `HoldingEntityRenderState`
    - `CustomHeadLayer` no longer takes in the `ItemRenderer`
    - `DolphinCarryingItemLayer` no longer takes in the `ItemRenderer`
    - `EquipmentLayerRenderer$TrimSpriteKey` now takes in a `ResourceKey<EquipmentAsset>`
        - `textureId` - Gets the texture id for the trim.
    - `FoxHeldItemLayer` no longer takes in the `ItemRenderer`
    - `ItemInHandLayer` now uses the `ArmedEntityRenderState`
        - The constructor no longer takes in the `ItemRenderer`
        - `renderArmWithItem` no longer takes in the `BakedModel`, `ItemStack`, or `ItemDisplayContext` and instead the `ItemStackRenderState`
    - `LivingEntityEmissiveLayer` now takes in a boolean which determines whether the layer is always visible
    - `PandaHoldsItemLayer` no longer takes in the `ItemRenderer`
    - `PlayerItemInHandLayer` no longer takes in the `ItemRenderer`
        - `renderArmWithItem` no longer takes in the `BakedModel`, `ItemStack`, or `ItemDisplayContext` and instead the `ItemStackRenderState`
    - `SnowGolemHeadLayer` now uses the `SnowGolemRenderState`
    - `WitchItemLayer` no longer takes in the `ItemRenderer`
- `net.minecraft.client.renderer.entity.player.PlayerRenderer#getArmPose` is now private
- `net.minecraft.client.renderer.entity.state`
    - `ArmedEntityRenderState` - A render state for an entity that holds items in their right and left hands.
    - `HoldingEntityRenderState` - A render state for an entity that holds a single item.
    - `ItemClusterRenderState` - A render state for an item that should be rendered multiple times.
    - `ItemDisplayEntityRenderState#itemRenderState`, `itemModel` -> `item`, not one-to-one
    - `ItemEntityRenderState#itemModel`, `item` -> `ItemClusterRenderState#item`, not one-to-one
    - `ItemFrameRenderState#itemStack`, `itemModel` -> `item`, not one-to-one
    - `LivingEntityRenderState`
        - `headItemModel`, `headItem` -> `headItem`, not one-to-one
        - Arm and Hand methods moved to `ArmedEntityRenderState`
    - `OminousItemSpawnerRenderState` -> `ItemClusterRenderState`
    - `PlayerRenderState`
        - `mainHandState`, `offHandState` -> `ArmedEntityRenderState` methods
        - `heldOnHead` - Represents the item stack on the head of the player.
    - `SkeletonRenderState#isHoldingBow` - Represents if the skeleton is holding a bow.
    - `SnowGolemRenderState` - The render state for the snow golem.
    - `ThrownItemRenderState#item`, `itemModel` -> `item`, not one-to-one
    - `WitchRenderState#isHoldingPotion` - Whether the witch is holding a potion or not.
- `net.minecraft.client.renderer.item`
    - `BlockModelWrapper` - The basic model definition that contains the model and its associated tints.
    - `BundleSelectedItemSpecialRenderer`- A special renderer for a stack selected by a bundle.
    - `ClampedItemPropertyFunction`, `ItemPropertyFunction` -> `.properties.numeric.*` classes depending on the situation and property
    - `ClientItem` - The base item that represents the model definition in `assets/<modid>/items`.
    - `CompositeModel` - Overlays multiple models together.
    - `ConditionalItemModel` - A model that shows a different model based on a boolean.
    - `EmptyModel` - A model that renders nothing.
    - `ItemModel` - The base item model that updates the stack render state as necessary.
    - `ItemModelResolver` - The resolver that updates the stack render state.
    - `ItemModels` - Contains all potential item models for a `ClientItem`.
    - `ItemProperties` class is removed
    - `ItemStackRenderState` - The render state representing the stack to render.
    - `MissingItemModel` - A model that represents the missing model.
    - `RangeSelectItemModel` - A model that contains some range of values that applies the associated model that meets the threshold.
    - `SelectItemModel` - An item model that switches based on the provided property.
    - `SpecialModelWrapper` - An item model for models that are rendered dynamically, such as chests.
- `net.minecraft.client.renderer.item.properties.conditional`
    - `Broken` - If the item only has one durability left.
    - `BundleHasSelectedItem` - If the bundle is holding the selected item.
    - `ConditionalItemModelProperties` - Contains all potential conditional property types.
    - `ConditionalItemModelProperty` - Represents a property that returns some boolean.
    - `CustomModelDataProperty` - If the current index is set to true within `DataComponents#CUSTOM_MODEL_DATA`.
    - `Damaged` - If the item is damaged.
    - `ExtendedView` - If the display context is a gui and the shift key is down.
    - `FishingRodCast` - If the fishing rod is being used.
    - `HasComponent` - Whether it has the associated data component.
    - `IsCarried` - If the item is being carried in the current menu.
    - `IsKeybindDown` - If the key mapping is being pressed.
    - `IsSelected` - If the item is selected in the hotbar.
    - `IsUsingItem` - If the item is being used.
    - `IsViewEntity` - Whether the holding entity is the current camera entity.
- `net.minecraft.client.renderer.item.properties.numeric`
    - `BundleFullness` - A threshold based on the bundle contents.
    - `CompassAngle` - A threshold on the currrent angle state.
    - `CompassAngleState` - A threshold based on the current compass angle towards its target.
    - `Cooldown` - A threshold based on the current cooldown percentage.
    - `Count` - A threshold based on the stack count.
    - `CrossbowPull` - A threshold based on the crossbow being pulled.
    - `CustomModelDataProperty` - If the current index has set theshold value within `DataComponents#CUSTOM_MODEL_DATA`.
    - `Damage` - A threshold based on the durability percentage remaining.
    - `NeedleDirectionHelper` - An abstract class which help point the position needle in the correct direction.
    - `RangeSelectItemModelProperties` - Contains all potential ranged property types.
    - `RangeSelectItemModelProperty` - Represents a property that returns some threshold of a float.
    - `Time` - A threshold based on the current time of day.
    - `UseCycle` - A threshold based on the remaining time left normalized to some period modulo in the stack being used.
    - `UseDuration` - A threshold based on the remaining time left in the stack being used.
- `net.minecraft.client.renderer.item.properties.select`
    - `Charge` - A case based on the charge type of a crowssbow.
    - `ContextDimension` - A case based on the dimension the item is currently within.
    - `ContextEntityType` - A case based on the holding entity's type.
    - `CustomModelDataProperty` - If the current index is set to the string within `DataComponents#CUSTOM_MODEL_DATA`.
    - `DisplayContext` - A case based on the display context.
    - `ItemBlockState` - A case based on getting a property value from an item holding the block state properties.
    - `LocalTime` - A case based on a simple date format pattern.
    - `MainHand` - A case based on the arm holding the item.
    - `SelectItemModelProperties` - Contains all potential select-cased property types.
    - `SelectItemModelProperty` - Represents a property that returns some cased selection.
    - `TrimMaterialProperty` - A case based on the trim material on the item.
- `net.minecraft.client.renderer.special`
    - `BannerSpecialRenderer` - An item renderer for a banner.
    - `BedSpecialRenderer` - An item renderer for a bed.
    - `ChestSpecialRenderer` - An item renderer for a chest.
    - `ConduitSpecialRenderer` - An item renderer for a conduit.
    - `DecoratedPotSpecialRenderer` - An item renderer for a decorated pot.
    - `HangingSignSpecialRenderer` - An item renderer for a hanging sign.
    - `NoDataSpecialModelRenderer` - An item renderer that does not need to read any data from the stack.
    - `ShieldSpecialRenderer` - An item renderer for a shield.
    - `ShulkerBoxSpecialRenderer` - An item renderer for a shulker box.
    - `SkullSpecialRenderer` - An item renderer for a skull.
    - `SpecialModelRenderer` - Represents a model that reads data from the stack and renders the object without needing the render state.
    - `SpecialModelRenderers` - Contains all potential special renderers.
    - `StandingSignSpecialRenderer` - An item renderer for a standing sign.
    - `TridentSpecialRenderer` - An item renderer for a trident.
- `net.minecraft.client.resources.model`
    - `BakedModel`
        - `isCustomRenderer` is removed, replaced by the special renderer system
        - `overrides` is removed, replaced by the properties renderer system
    - `BlockStateModelLoader` no longer takes in the missing model
        - `definitionLocationToBlockMapper` is now private
        - `loadBlockStateDefinitionStack` is now private
        - `loadBlockStates` - Gets the loaded models for the block state.
        - `$LoadedBlockModelDefinition` is now package-private
        - `$LoadedModel` now takes in an `UnbakedBlockStateModel` instead of a `UnbakedModel`
        - `$LoadedModels`
            - `forResolving` - Returns all models hat need to be resolved.
            - `plainModels` - Returns a map from the model location to the unbaked model.
    - `BuiltInModel` class is removed
    - `ClientItemInfoLoader` - Loads all models for all item stacks.
    - `EquipmentModelSet` -> `EquipmentAssetManager`
    - `ItemModel` -> `net.minecraft.client.renderer.item.ItemModel`
    - `MissingBlockModel#MISSING` is now private
    - `ModelBaker`
        - `sprites` - Returns the getter to get sprites.
        - `rootName` - Gets the name of the model for debugging.
    - `ModelBakery(Map<ModelResourceLocation, UnbakedModel>, Map<ResourceLocation, UnbakedModel>, UnbakedModel)` -> `ModelBakery(EntityModelSet, Map<ModelResourceLocation, UnbakedBlockStateModel>, Map<ResourceLocation, ClientItem>, Map<ResourceLocation, UnbakedModel>, UnbakedModel)`
        - `bakeModels` now returns a `$BakingResult`
        - `getBakedTopLevelModels` is removed
        - `$BakingResult` - Holds all models that have been lodaded.
        - `$TextureGetter`
            - `get` now takes in the `ModelDebugName` instead of the `ModelResourceLocation`
            - `reportingMissingReference` - Handles how a texture is reported when not set.
            - `bind` - Creates a spriate getter bound to the current model.
    - `ModelDebugName` - Returns the name of the model for debugging.
    - `ModelDiscovery`
        - `registerStandardModels` is removed
        - `registerSpecialModels` - Adds the internal models loaded by the system.
        - `addRoot` - Adds a new model that can be resolved.
        - `getUnreferencedModels` - Returns the difference between the models loaded vs the models used.
        - `getTopModels` is removed
    - `ModelGroupCollector$GroupKey#create` now takes in an `UnbakedBlockStateModel` instead of a `UnbakedModel`
    - `ModelManager`
        - `specialBlockModelRenderer` - Returns the renderer for special block models.
        - `entityModels` - Returns the model set for the entities.
        - `getItemProeprties` - Returns the properties for the client item based on its resource location.
    - `ModelResourceLocation#inventory` is removed
    - `ResolvableModel` - The base model, usually unbaked, that have references to resolve.
    - `SimpleBakedModel` fields are now all private
        - `bakeElements` - Bakes a model given the block elements.
        - `$Builder` no longer has an overload that takes in the `BlockModel`
    - `SpecialModels` class is removed
    - `SpriteGetter` - A getter for atlas sprites for the associated materials.
    - `UnbakedModel` is now a `ResolvableModel`
        - `bake(ModelBaker, Function<Material, TextureAtlasSprite>, ModelState)` -> `bake(TextureSlots, ModelBaker, ModelState, boolean, boolean, ItemTransforms)`
        - `getAmbientOcclusion`, `getTopAmbientOcclusion` - Returns whether ambient occlusion should be enabled on the item.
        - `getGuiLight`, `getTopGuiLight` - Returns the lighting side within a gui.
        - `getTransforms`, `getTopTransform`, `getTopTransforms` - Returns the transformations to apply based on the display context.
        - `getTextureSlots`, `getTopTextureSlots` - Returns the texture data for the model.
        - `getParent` - Returns the parent of this model.
        - `bakeWithTopModelValues` - Bakes the model.
- `net.minecraft.data.models.*` -> `net.minecraft.client.data.models.*`
- `net.minecraft.world.item`
    - `BundleItem` no longer takes in any `ResourceLocation`s
        - `openFrontModel`, `openBackModel` is removed
    - `CrossbowItem$ChargeType` - The item being charged by the crossbow.
    - `DyeColor#getMixedColor` - Returns the dye most closely representing the mixed color.
    - `Item$Properties#overrideModel` is removed
    - `SpawnEggItem` no longer takes in its tint colors
        - `getColor` is removed
- `net.minecraft.world.item.alchemy.PotionContents`
    - `getColor(*)` is removed
    - `getColorOr` - Gets a custom color fro the potion or the default if not present.
- `net.minecraft.world.item.component.CustomModelData` now takes in a list of floats, flags, strings, and colors to use in the custom model properties based on the provided index
- `net.minecraft.world.item.equipment`
    - `ArmorMaterial` now takes in a `ResourceKey<EquipmentAsset>` instead of just the model id
    - `EquipmentAsset` - A marker to represent the equipment client info key
    - `EquipmentAssets` - All vanilla equipment assets.
    - `EquipmentModel` -> `net.minecraft.client.resources.model.EquipmentClientInfo`
    - `EquipmentModels` -> `net.minecraft.client.data.models.EquipmentAssetProvider`, not one-to-one
    - `Equippable` now takes in a `ResourceKey<EquipmentAsset>` instead of just the model id
        - `$Builder#setModel` -> `setAsset`
- `net.minecraft.world.item.equipment.trim`
    - `ArmorTrim#getTexture` is removed
    - `TrimMaterial` no longer takes in an item model index, and the key over the override armor materials points to `ResourceKey<EquipmentAsset>`
- `net.minecraft.world.level.FoliageColor`
    - `getEvergreenColor` -> `FOLIAGE_EVERGREEN`
    - `getBirchColor` -> `FOLIAGE_BIRCH`
    - `getDefaultColor` -> `FOLIAGE_DEFAULT`
    - `getMangroveColor` -> `FOLIAGE_MANGROVE`
- `net.minecraft.world.level.block.RenderShape#ENTITYBLOCK_ANIMATED` is removed
- `net.minecraft.world.level.block.entity`
    - `BannerBlockEntity#fromItem` is removed
    - `BedBlockEntitty#setColor` is removed
    - `BlockEntity#saveToItem` is removed
    - `DecoratedPotBlockEntity#setFromItem`, `getPotAsItem` is removed
- `net.minecraft.world.level.storage.loot.functions.SetCustomModelDataFunction` now takes in a list of floats, flags, strings, and colors to use in the custom model properties based on the provided index

## Mob Replacing Current Items

One of the last hardcoded instances relating to tools and armor being subtypes of `DiggerItem` and `ArmorItem`, respectively, have been reworked: `Mob#canReplaceCurrentItem`. Now, it reads the `EquipmentSlot` of the stack from the `DataComponents#EQUIPPABLE` data component. Then, using that, different logic occurs depending on the situation.

For armor slots, it cannot be changed if the armor is enchanted with a `EnchantmentEffectComponents#PREVENT_ARMOR_CHANGE` effect component. Otherwise, it will attempt to compare the armor attributes first, then armor toughness if equal.

For weapons (via hand slots), it will first check if the mob has a preferred weapon type tag. If so, it will switch the item to the weapon in the tag, provided one item is in the tag and the other is not. Otherwise, it will attempt to compare attack damage attributes.

If all attributes are equal, then they will both default to the following logic. First, it will try to pick the item with the most enchantments. Then, it will attempt to pick the item with the most durability remaining (the raw value, not the percentage). Finally, it will check whether one of the items hsa a custom name via the `DataComponents#CUSTOM_NAME`.

> As a small caveat, `BambooSaplingBlock` and `BambooStalkBLock` still hardcode a check for check if the mainhand item is a `SwordItem`, though this could probably be replaced with a change to `ToolMaterial#applySwordProperties` in the future.

## Particles, rendered through Render Types

Particles are now rendered using a `RenderType`, rather than setting a buffer builder themselves. The only special cases are `ParticleRenderType#CUSTOM`, which allows the modder to implement their own rendering via `Particle#renderCustom`; and `ParticleRenderType#NO_RENDER`, which renders nothing.

To create a new `ParticleRenderType`, it can be created by passing in its name for logging and the `RenderType` to use. Then, the type is returned in `Particle#getRenderType`.

```java
public static final ParticleRenderType TERRAIN_SHEET_OPAQUE = new ParticleRenderType(
    "TERRAIN_SHEET_OPAQUE", // Typically something recognizable, like the name of the field
    RenderType.opaqueParticle(TextureAtlas.LOCATION_BLOCKS) // The Render Type to use
);
```

- `net.minecraft.client.particle`
    - `CherryParticle` -> `FallingLeavesParticle`, not one-to-one as the new class has greater configuration for its generalization
    - `ItemPickupParticle` no longer takes in the `RenderBuffers`
    - `Particle#renderCustom` - Renders particles with the `ParticleRenderType#CUSTOM` render type.
    - `ParticleEngine#render(LightTexture, Camera, float)` -> `render(Camera, float, MutliBufferSource$BufferSource)`
    - `ParticleRenderType` is now a record which takes in the name and the `RenderType` it uses.

## Minor Migrations

The following is a list of useful or interesting additions, changes, and removals that do not deserve their own section in the primer.

### `SimpleJsonResourceReloadListener`

`SimpleJsonResourceReloadListener`s now take in a converter to map some key to a resource location. An abstract has been provided for registry keys. This is done through a `FileToIdConverter`, which essentially holds a prefix and extension to apply to some `ResourceLocation`.

```java
// We will assume this is a server reload listener (meaning in the 'data' folder)
public class MyLoader extends SimpleJsonResourceReloadListener<ExampleObject> {

    public MyLoader() {
        super(
            // The codec to encode/decode the object
            ExampleObject.CODEC,
            // The file converter
            // Will place files in data/<namespace>/example/object/<path>.json
            FileToIdConverter.json(
                // The prefix
                "example/object"
            )
        );
    }

    // Below is the same
}
```

- `net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener` now takes in a resource key for a registry, or a file to id converter instead of just a string
    - `scanDirectory` now takes in a resource key for a registry, or a file to id converter instead of just a string

### MetadataSectionSerializer, replaced by Codecs

`MetadataSectionSerializer` has been removed in favor of using `Codec`s to serialize the metadata sections. As such all `MetadataSectionSerializer`s have been replaced by its `MetadataSectionType` instead, which holds the name of the section and the codec for the metadata section.

- `net.minecraft.client.renderer.texture`
    - `HttpTexture` -> `SkinTextureDownloader`, not one-to-one as the new class is just a utility that returns the content to store
    - `MissingTextureAtlasSprite`
        - `getTexture` -> `generateMissingImage`, not one-to-one
        - `getMissingImage(int, int)` is now public
    - `SpriteLoader#loadAndStitch` now takes in a collection of `MetadataSectionType`s rather than `MetadataSectionSerializer`s
- `net.minecraft.client.resources.SkinManager` no longer takes in the `TextureManager`
    - `getOrLoad` now returns an `Optional<PlayerSkin>` future instead of just the `PlayerSkin`
- `net.minecraft.client.resources.metadata.animation`
    - `AnimationFrame` is now a record
    - `AnimationMetadataSection` is now a record
    - `AnimationMetadataSectionSerializer` class is removed
    - `VillagerMetaDataSection` -> `VillagerMetadataSection`
    - `VillagerMetadataSectionSerializer` class is removed
- `net.minecraft.client.resources.metadata.texture`
    - `TextureMetadataSection` is now a record
    - `TextureMetadataSectionSerializer` class is removed
- `net.minecraft.server.packs.PackResources#getMetadataSection` now takes in a `MetadataSectionType` instead of a `MetadataSectionSerializer`
- `net.minecraft.server.packs.metadata`
    - `MetadataSectionSerializer` is removed in favor of section codecs
    - `MetadataSectionType` is a now a record instead of a `MetadataSectionSerializer` extension
- `net.minecraft.server.packs.resources.ResourceMetadata`
    - `getSection` now takes in a `MetadataSectionType` instead of a `MetadataSectionSerializer`
    - `copySections` now takes in a collection of `MetadataSectionType`s instead of a `MetadataSectionSerializer`s

### Music, now with Volume Controls

The background music is now handled through a `MusicInfo` class, which also stores the volume along with the associated `Music`.

- `net.minecraft.client.Minecraft#getSituationalMusic` now returns a `MusicInfo` instead of a `Music`
- `net.minecraft.client.sounds`
    - `MusicInfo` - A record that holds the currently playing `Music` and the volume its at.
    - `MusicManager#startPlaying` now takes in a `MusicInfo` instead of a `Music`
    - `SoundEngine#setVolume`, `SoundManager#setVolume` - Sets the volume of the associated sound instance.
- `net.minecraft.world.level.biome`
    - `Biome`
        - `getBackgroundMusic` now returns a optional `SimpleWeightedRandomList` of music.
        - `getBackgroundMusicVolume` - Gets the volume of the background music.
    - `BiomeSpecialEffects$Builder#silenceAllBackgroundMusic`, `backgroundMusic(SimpleWeightedRandomList<Music>)` - Handles setting the background music for the biome.

### Tag Changes

- `minecraft:block`
    - `tall_flowers` -> `bee_attractive`
- `minecraft:item`
    - `tall_flowers`, `flowers` is removed
    - `trim_templates` is removed
    - `skeleton_preferred_weapons`
    - `drowned_preferred_weapons`
    - `piglin_preferred_weapons`
    - `pillager_preferred_weapons`
    - `wither_skeleton_disliked_weapons`

### List of Additions

- `com.mojang.blaze3d.platform.Window#isMinimized` - Returns whether the application window is minimized.
- `com.mojang.blaze3d.vertex.VertexBuffer`
    - `uploadStatic` - Immediately uploads the provided vertex data via the `Consumer<VertexConsumer>` using the `Tesselator` with a `STATIC_WRITE` `VertexBuffer`.
    - `drawWithRenderType` - Draws the current buffer to the screen with the given `RenderType`.
- `com.mojang.math.MatrixUtil#isIdentity` - Checks whether the current `Matrix4f` is an identity matrix.
- `net.minecraft`
    - `SuppressForbidden` - An annotation that holds some reason, usually related to needing the sysout stream.
    - `Util#maxAllowedExecutorThreads` - Returns the number of available processors clamped between one and the maximum number of threads.
- `net.minecraft.client.gui.components.events.GuiEventListener#getBorderForArrowNavigation` - Returns the `ScreenRectangle` bound to the current direction.
- `net.minecraft.client.gui.navigation.ScreenRectangle#transformAxisAligned` - Creates a new `ScreenRectangle` by transforming the position using the provided `Matrix4f`.
- `net.minecraft.client.gui.narration.NarratableEntry#getNarratables` - Returns the list of narratable objects within the current object.
- `net.minecraft.client.gui.screens.recipebook.RecipeCollection#EMPTY` - An empty collection of recipes.
- `net.minecraft.client.gui.screens.worldselection`
    - `ExperimentsScreen$ScrollArea` - Represents a narratable scroll area of the currently available experiments.
    - `SwitchGrid#layout` - Returns the layout of the grid to visit.
- `net.minecraft.client.model`
    - `BannerFlagModel`, `BannerModel` - Models for the banner and hanging banner.
    - `VillagerLikeModel#translateToArms` - Translates the pose stack such that the current relative position is at the entity's arms.
- `net.minecraft.client.model.geom.EntityModelSet#vanilla` - Creates a new model set with all vanilla models. 
- `net.minecraft.client.multiplayer.PlayerInfo#setShowHat`, `showHat` - Handles showing the hat layer of the player in the tab overlay.
- `net.minecraft.client.renderer.blockentity`
    - `AbstractSignRenderer` - How a sign should render as a block entity.
    - `HangingSignRenderer`
        - `createSignModel` - Creates a sign model given the wood and the attachment location.
        - `renderInHand` - Renders the model in the entity's hand.
        - `$AttachmentType` - An enum which represents where the model is attached to, given its properies.
        - `$ModelKey` - A key for the model that combines the `WoodType` with its `$AttachmentType`.
    - `SignRenderer`
        - `renderInHand` - Renders the model in the entity's hand.
- `net.minecraft.client.renderer.entity.EntityRenderer#getShadowStrength` - Returns the raw opacity of the display's shadow.
- `net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer#applyTranslation` - Applies the translation to render the item in the model's arms.
- `net.minecraft.client.renderer.texture`
    - `ReloadableTexture` - A texture that can be reloaded from its associated contents.
    - `TextureContents` - Holds the image and metadata associated with a given texture.
    - `TextureManager`
        - `registerAndLoad` - Registers a reloadable texture with the given name.
        - `registerForNextReload` - Registers a texture by its resource location to be loaded on next reload.
- `net.minecraft.commands.SharedSuggestionProvider#MATCH_SPLITTER` - Defines a matcher that matches a period, underscore, or forward slash.
- `net.minecraft.core.BlockPos$TraversalNodeStatus` - A marker indicating whether the `BlockPos` should be used, skipped, or stopped from any further traversal.
- `net.minecraft.core.component.PatchedDataComponentMap`
    - `toImmutableMap` - Returns either the immutable patch or a copy of the current map.
    - `hasNonDefault` - Returns whether there is a custom value for the data component instead of just the default.
- `net.minecraft.data.PackOutput$PathProvider#json` - Gets the JSON path from a resource key.
- `net.minecraft.data.loot.BlockLootSubProvider#createMultifaceBlockDrops` - Drops a block depending on the block face mined.
- `net.minecraft.data.worldgen.placement.PlacementUtils#HEIGHTMAP_NO_LEAVES` - Creates a y placement using the `Heightmap$Types#MOTION_BLOCKING_NO_LEAVES` heightmap.
- `net.minecraft.network.chat.Style#getShadowColor`, `withShadowColor` - Methods for handling the shadow color of a component.
- `net.minecraft.network.protocol.game.ServerboundPlayerLoadedPacket` - A packet for when the client player loads into a client world.
- `net.minecraft.resources.FileToIdConverter#registry` - Gets the file converter from a registry key.
- `net.minecraft.util.ExtraCodecs`
    - `idResolverCodec` - Creates a codec that maps some key to some value.
    - `compactListCodec` - Creates a codec that can either be an element of a list of elements.
    - `floatRange` - Creates a codec that must be between two float values.
    - `$LateBoundIdMapper` - A mapper that functionally acts like a registry with an associated codec. 
- `net.minecraft.util.profiling.jfr.JvmProfiler#onStructureGenerate` - Returns the profiled duration on when a structure attempts to generate in the world.
- `net.minecraft.util.profiling.jfr.event.StructureGenerationEvent` - A profiler event when a structure is being generated.
- `net.minecraft.util.profiling.jfr.stats.StructureGenStat` - A result of a profiled structure generation.
- `net.minecraft.world.entity`
    - `LivingEntity`
        - `resolvePlayerResponsibleForDamage` - Gets the player responsible for hurting the current entity.
        - `canBeNameTagged` - When true, the entity's can be set with a name tag.
    - `Mob#getPreferredWeaponType` - Gets the tag that represents the weapons the entity wants to pick up.
- `net.minecraft.world.entity.ai.attributes.AttributeMap#resetBaseValue` - Resets the attribute instance to its default value.
- `net.minecraft.world.entity.monster.creaking`
    - `Creaking`
        - `activate`, `deactivate` - Handles the activateion of the brain logic for the creaking.
        - `setTransient`, `isHeartBound`, `setHomePos`, `getHomePos` - Handles the home position.
        - `blameSourceForDamage` - Finds the player responsible for the damage.
        - `tearDown` - Handles when the creaking is destroyed.
        - `creakingDeathEffects` - Handles the death of a creaking.
        - `playerIsStuckInYou` - Checks whether there are at least four players stuck in a creaking.
        - `setTearingDown`, `isTearingDown` - Handles the tearing down state.
        - `hasGlowingEyes`, `checkEyeBlink` - Handles the eye state.
- `net.minecraft.world.entity.player.Player`
    - `hasClientLoaded`, `setClientLoaded` - Whether the client player has been loaded.
    - `tickClientLoadTimeout` - Ticks the timer on how long to wait before kicking out the client player if not loaded.
- `net.minecraft.world.item`
    - `Item#shouldPrintOpWarning` - Whether a warning should be printed to the player based on stored block entity data and adminstrator permissions.
    - `ItemStack`
        - `getCustomName` - Returns the custom name of the item, or `null` if no component exists.
        - `immutableComponents` - Returns either the immutable patch or a copy of the stack component map.
        - `hasNonDefault` - Returns whether there is a custom value for the data component instead of just the default.
- `net.minecraft.world.item.component.CustomData`
    - `parseEntityId` - Reads the entity id off of the component.
    - `parseEntityType` - Reads the entity type from the id and maps it to its registry object.
- `net.minecraft.world.item.crafting.Ingredient#isEmpty` - Returns whether the ingredient has no values.
- `net.minecraft.world.item.trading.Merchant#stillValid` - Checks whether the merchant can still be accessed by the player.
- `net.minecraft.world.level`
    - `Level#dragonParts` - Returns the list of entities that are the parts of the ender dragon.
    - `ServerExplosion#getDamageSource` - Returns the damage source of the explosion.
- `net.minecraft.world.level.block`
    - `EyeblossomBlock$Type`
        - `block` - Gets the block for the current type.
        - `state` - Gets the block state for the current type.
        - `transform` - Returns the opposiate state of this type.
    - `FlowerBlock#getBeeInteractionEffect` - Returns the effect that bees obtain when interacting with the flower.
    - `FlowerPotBlock#opposite` - Returns the opposite state of the block, only for potted eyeblossoms.
    - `MultifaceBlock#canAttachTo` - Returns whether this block can attach to another block.
    - `MultifaceSpreadeableBlock` - A multiface block that can naturally spread.
- `net.minecraft.world.level.block.entity.trialspawner`
    - `TrialSpawner#overrideEntityToSpawn` - Changes the entity to spawn in the trial.
    - `TrialSpawnerConfig#withSpawning` - Sets the entity to spawn within the trial.

### List of Changes

- `com.mojang.blaze3d.platform.NativeImage#upload` no longer takes in three booleans that set the filter mode or texture wrap clamping for `TEXTURE_2D`
    - This has been moved to `AbstractTexture#setClamp` and `#setFilter`
- `net.minecraft.client.gui`
    - `Gui#clear` -> `clearTitles`
    - `GuiGraphics#drawWordWrap` has a new overload that takes in whether a drop shadow should be applied to the text
        - The default version enables drop shadows instead of disabling it
- `net.minecraft.client.gui.components`
    - `AbstractContainerWidget` now implements `AbstractScrollArea`
    - `AbstractScrollWidget` -> `AbstractScrollArea` or `AbstractTextAreaWidget` depending on use-case, not one-to-one
    - `AbstractSelectionList`
        - `setRenderHeader` is now bundled into a new constructor with an extra integer
        - `getMaxScroll` -> `AbstractScrollArea#maxScrollAmount`
        - `getScrollAmount` -> `AbstractScrollArea#scrollAmount`
        - `scrollbarVisible` -> `AbstractScrollArea#scrollbarVisible`
        - `setClampedScrollAmount`, `setScrollAmount` -> `AbstractScrollArea#setScrollAmount`
        - `clampScrollAmount` -> `refreshScrollAmount`
        - `updateScrollingState` -> `AbstractScrollArea#updateScrolling`
        - `getScrollbarPosition`, `getDefaultScrollbarPosition` -> `scrollBarY`, not one-to-one
    - `AbstractWidget#clicked` -> `isMouseOver`, already exists
- `net.minecraft.client.gui.components.toasts.TutorialToast` now requires a `Font` as the first argument in its constructor
- `net.minecraft.client.gui.font.glyphs.BakedGlyph$Effect` and `$GlyphInstance` now take in the color and offset of the text shadow
- `net.minecraft.client.gui.screens`
    - `LoadingOverlay#registerTextures` now takes in a `TextureManager` instead of the `Minecraft` instance
    - `TitleScreen#preloadResources` -> `registerTextures`, not one-to-one
- `net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen$GameModeSlot` is now a static inner class
- `net.minecraft.client.gui.screens.reporting.ChatSelectionScreen$Entry`, `$PaddingEntry` are now static inner classes
- `net.minecraft.client.gui.screens.worldselection.SwitchGrid$Builder#build` no longer takes in a `Consumer<LayoutElement>`
- `net.minecraft.client.model`
    - `DonkeyModel#createBodyLayer`, `createBabyLayer` now take in a scaling factor
    - `VillagerHeadModel` -> `VillagerLikeModel`
- `net.minecraft.client.model.geom.EntityModelSet` is no longer a `ResourceManagerReloadListener`
- `net.minecraft.client.multiplayer.MultiPlayerGameMode#handlePickItem` -> `handlePickItemFromBlock` or `handlePickItemFromEntity`, providing both the actual object data to sync and a `boolean` about whether to include the data of the object being picked
- `net.minecraft.client.particle.CherryParticle` -> `FallingLeavesParticle`, not one-to-one as the new class has greater configuration for its generalization
- `net.minecraft.client.player.ClientInput#tick` no longer takes in any parameters
- `net.minecraft.client.renderer`
    - `CubeMap#preload` -> `registerTextures`, not one-to-one
    - `LevelRenderer`
        - `renderLevel` no longer takes in the `LightTexture`
        - `onChunkLoaded` -> `onChunkReadyToRender`
    - `PostChainConfig$Pass#program` -> `programId`
        - `program` now returns the `ShaderProgram` with the given `programId`
    - `ScreenEffectRenderer#renderScreenEffect` now takes in a `MultiBufferSource`
    - `SectionOcclusionGraph#onChunkLoaded` -> `onChunkReadyToRender`
    - `Sheets#createSignMaterial`, `createHangingSignMaterial` now has an overload that takes in a `ResourceLocation`
    - `SkyRenderer`
        - `renderSunMoonAndStars`, `renderSunriseAndSunset` now takes in a `MultiBufferSource$BufferSource` instead of a `Tesselator`
        - `renderEndSky` no longer takes in the `PoseStack`
    - `WeatherEffectRenderer#render` now takes in a `MultiBufferSource$BufferSource` instead of a `LightTexture`
- `net.minecraft.client.renderer.blockentity`
    - `BannerRenderer#createBodyLayer` -> `BannerModel#createBodyLayer`, not one-to-one
    - `HangingSignRenderer`
        - `createHangingSignLayer` now takes in a `HangingSignRenderer$AttachmentType`
        - `$HangingSignModel` is now replaced with a `Model$Simple`, though its fields can be obtained from the root
    - `SkullBlockRenderer#getRenderType` now has an overload that takes in a `ResourceLocation` to override representing the player's texture
- `net.minecraft.client.renderer.entity.AbstractHorseRenderer`, `DonkeyRenderer` no longer takes in a float scale
- `net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer` now requires the generic `M` to be a `VillagerLikeModel`
- `net.minecraft.client.renderer.entity.state.CreakingRenderState#isActive` -> `eyesGlowing`
    - The original parameter still exists on the `Creaking`, but is not necessary for rendering
- `net.minecraft.core.BlockPos#breadthFirstTraversal` now takes in a function that returns a `$TraversalNodeStatus` instead of a simple predicate to allow certain positions to be skipped
- `net.minecraft.core.particles.TargetColorParticleOption` -> `TrailParticleOption`, not one-to-one
- `net.minecraft.data.DataProvider#savelAll` now has overloads for maps with a key function to get the associated path
- `net.minecraft.network`
    - `NoOpFrameEncoder` replaced by `LocalFrameEncoder`, not one-to-one
    - `NoOpFrameDecoder` replaced by `LocalFrameDecoder`, not one-to-one
    - `MonitorFrameDecoder` replaced by `MonitoredLocalFrameDecoder`, not one-to-one
- `net.minecraft.network.protocol.game`
    - `ClientboundLevelParticlesPacket` now takes in a boolean that determines whether the particle should always render
    - `ClientboundMoveVehiclePacket` is now a record
    - `ClientboundPlayerInfoUpdatePacket$Entry` now takes in a boolean representing whether the hat should be shown
    - `ClientboundSetHeldSlotPacket` is now a record
    - `ServerboundMoveVehiclePacket` is now a record
    - `ServerboundPickItemPacket` -> `ServerboundPickItemFromBlockPacket`, `ServerboundPickItemFromEntityPacket`; not one-to-one
- `net.minecraft.server.level
    - `ServerLevel#sendParticles` now has an overload that takes in the override limiter distance and whether the particle should always be shown
        - Other overloads that take in the override limiter now also take in the boolean for if the particle should always be shown
    - `ServerPlayer#doCheckFallDamage` -> `Entity#doCheckFallDamage`, now final
- `net.minecraft.util`
    - `ARGB#from8BitChannel` is now private, with individual float components obtained from `alphaFloat`, `redFloat`, `greenFloat`, and `blueFloat`
    - `SpawnUtil#trySpawnMob` now takes in a boolean that, when false, allows the entity to spawn regardless of collision status with the surrounding area
- `net.minecraft.util.profiling.jfr.callback.ProfiledDuration#finish` now takes in a boolean that indicates whether the profiled event was successful
- `net.minecraft.util.profiling.jfr.parse.JfrStatsResults` now takes in a list of structure generation statistics
- `net.minecraft.world.effect.PoisonMobEffect`, `WitherMobEffect` is now public
- `net.minecraft.world.entity`
    - `Entity`
        - `setOnGroundWithMovement` has an overload that sets the horizontal collision to whatever the entity's current state is.
        - `awardKillScore` no longer takes in an integer
        - `makeBoundingBox()` is now final
            - `makeBoundingBox(Vec3)` is now
        - `onlyOpCanSetNbt` -> `EntityType#onlyOpCanSetNbt`
    - `Leashable`
        - `readLeashData` is now private, replaced by a method that returns nothing
        - `dropLeash(boolean, boolean)` -> `dropLeash()`, `removeLeash`, `onLeashRemoved`; not one-to-one, as they all internally call the private `dropLeash`
    - `LivingEntity`
        - `isLookingAtMe` no longer takes in a `Predicate<LivingEntity>`, and array of `DoubleSupplier`s is now an array of `double`s
        - `hasLineOfSight` takes in a double instead of a `DoubleSupplier`
- `net.minecraft.world.entity.ai.behavior.AcquirePoi#create` now has overloads which take in a `BiPredicate<ServerLevel, BlockPos>` for filtering POI locations
- `net.minecraft.world.entity.animal.Bee#attractsBees` is now public
- `net.minecraft.world.entity.monster.Shulker#getProgressAabb`, `getProgressDeltaAabb` now take in a movement `Vec3`
- `net.minecraft.world.entity.player`
    - `Inventory`
        - `setPickedItem` -> `addAndPickItem`
        - `findSlotMatchingCraftingIngredient` now takes in an `ItemStack` to compare against
    - `Player#getPermissionLevel` is now public
    - `StackedContents$IngredientInfo` is now an interface that acts like a predicate for accepting some item
- `net.minecraft.world.entity.projectile.FishingHook` no longer takes in the `ItemStack`
- `net.minecraft.world.inventory.Slot#getNoItemIcon` now returns a single `ResourceLocation` rather than a pair of them
- `net.minecraft.world.item`
    - `Item$TooltipContext#of` now takes in the `Player` viewing the item
    - `MobBucketItem` now requires a `Mob` entity type
    -` SpawnEggItem#spawnsEntity`, `getType` now takes in a `HolderLookup$Provider`
- `net.minecraft.world.item.crafting`
    - `Ingredient` now implements `StackedContents$IngredientInfo<Holder<Item>>`
        - `items` now returns a stream instead of a list
    - `PlacementInfo#slotInfo` -> `slotsToIngredientIndex`, not one-to-one
- `net.minecraft.world.level.Level#addParticle` now takes in a boolean representing if the particle should always be shown
- `net.minecraft.world.level.block`
    - `Block#getCloneItemStack` -> `state.BlockBehaviour#getCloneItemStack`, now protected
    - `CherryLeavesBlock` -> `ParticleLeavesBlock`
    - `CreakingHeartBlock#canSummonCreaking` -> `isNaturalNight`
    - `MultifaceBlock` is no longer abstract and implements `SimpleWaterloggedBlock`
        - `getSpreader` -> `MultifaceSpreadeableBlock#getSpreader`
    - `SculkVeinBlock` is now an instance of `MultifaceSpreadeableBlock`
    - `SnowyDirtBlock#isSnowySetting` is now protected
- `net.minecraft.world.level.block.entity`
    - `AbstractFurnaceBlockEntity`
        - `litTime` -> `litTimeRemaining`
        - `litDuration` -> `litTotalTime`
        - `cookingProgress` -> `cookingTimer`
    - `BeehiveBlockEntity#addOccupant` now takes in a `Bee` rather than an `Entity`
    - `CreakingHeartBlockEntity#setCreakingInfo` - Sets the creaking the block entity is attached to.
- `net.minecraft.world.level.block.state.BlockBehaviour#getCloneItemStack`, `$BlockStateBase#getCloneItemStack` now takes in a boolean representing if there is infinite materials and whether the current block data should be saved.
- `net.minecraft.world.level.chunk.ChunkGenerator#createStructures` now takes in the `Level` resource key, only used for profiling
- `net.minecraft.world.level.levelgen.feature.configurations`
    - `MultifaceGrowthConfiguration` now takes in a `MultifaceSpreadableBlock` instead of a `MultifaceBlock`
    - `SimpleBlockConfiguration` now takes in a boolean on whether to schedule a tick update
- `net.minecraft.world.level.levelgen.structure.Structure#generate` now takes in the `Structure` holder and a `Level` resource key, only used for profiling

### List of Removals

- `com.mojang.blaze3d.systems.RenderSystem#overlayBlendFunc`
- `net.minecraft.client.gui.components.AbstractSelectionList`
    - `clickedHeader`
    - `isValidMouseClick`
- `net.minecraft.client.gui.screens.recipebook.RecipeCollection#hasSingleResultItem`
- `net.minecraft.client.model`
    - `DrownedModel#getArmPose`, now part of the `ArmedEntityRenderState`
    - `FelineModel#CAT_TRANSFORMER`
    - `HumanoidModel#getArmPose`, now part of the `ArmedEntityRenderState`
    - `PlayerModel#getArmPose`, now part of the `ArmedEntityRenderState`
    - `SkeletonModel#getArmPose`, now part of the `ArmedEntityRenderState`
    - `VillagerModel#BABY_TRANSFORMER`
- `net.minecraft.client.renderer.texture`
    - `AbstractTexture`
        - `load`
        - `reset`
        - `getDefaultBlur`
    - `PreloadedTexture`
    - `TextureManager`
        - `getTexture(ResourceLocation, AbstractTexture)`
        - `register(String, DynamicTexture)`
        - `preload`
- `net.minecraft.server.level.TicketType#POST_TELEPORT`
- `net.minecraft.world.entity.LivingEntity#deathScore`
- `net.minecraft.world.entity.ai.navigation.FlyingPathNavigation`, `GroundPathNavigation`
    - `canPassDoors`, `setCanPassDoors`
    - `canOpenDoors`
- `net.minecraft.world.entity.monster.creaking.CreakingTransient`
- `net.minecraft.world.entity.player.StackedItemContents#convertIngredientContents`
- `net.minecraft.world.item`
    - `CompassItem#getSpawnPosition`
    - `ItemStack#clearComponents`
- `net.minecraft.world.item.crafting.PlacementInfo`
    - `ingredientToContents`
    - `unpackedIngredients`
    - `$SlotInfo`
- `net.minecraft.world.level.block.CreakingHeartBlock$CreakingHeartState`
- `net.minecraft.world.level.block.entity.BlockEntity#onlyOpCanSetNbt`
- `net.minecraft.world.level.block.entity.trialspawner.TrialSpawnerData#setEntityId`
