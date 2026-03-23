# Minecraft 1.21.4 -> 1.21.5 Mod Migration Primer

This is a high level, non-exhaustive overview on how to migrate your mod from 1.21.4 to 1.21.5. This does not look at any specific mod loader, just the changes to the vanilla classes. All provided names use the official mojang mappings.

This primer is licensed under the [Creative Commons Attribution 4.0 International](http://creativecommons.org/licenses/by/4.0/), so feel free to use it as a reference and leave a link so that other readers can consume the primer.

If there's any incorrect or missing information, please file an issue on this repository or ping @ChampionAsh5357 in the Neoforged Discord server.

Thank you to:

- @TelepathicGrunt for the information within the 'Very Technical Changes' section
- @RogueLogix for their review and comments on the 'Render Pipeline Rework' section
- @Tslat for catching an error about `equipOnInteract`

## Pack Changes

There are a number of user-facing changes that are part of vanilla which are not discussed below that may be relevant to modders. You can find a list of them on [Misode's version changelog](https://misode.github.io/versions/?id=1.21.5&tab=changelog).

## Handling the Removal of Block Entities Properly

Previously, `BlockEntity` would handle all of their removal logic within `BlockBehaviour#onRemove`, including both dropping any stored items and removing the block entity itself. However, depending on how the method is used, it can cause some strange behaviors due to the mutable state of the block entity. For this reason, the logic that makes up the removal process has been split between two methods: `BlockEntity#preRemoveSideEffects` and `BlockBehaviour#affectNeighborsAfterRemoval`.

`BlockEntity#preRemoveSideEffects` is now responsible for removing anything from the block entity before it is removed from the level. By default, if the `BlockEntity` is a `Container` instance, it will drop the contents of the container into the level. Other logic can be handled within here, but it should generally avoid removing the `BlockEntity` itself, unless the position of the block entity tends to change dynamically, like for a piston.

From there, the `LevelChunk` logic will call `removeBlockEntity` before calling `BlockBehaviour#affectNeighborsAfterRemoval`. This should only send the updates to other blocks indicating that this block has been removed from the level. For `BlockEntity` holders, this can be done easily by calling `Containers#updateNeighboursAfterDestroy`. Otherwise may want to call `Level#updateNeighborsAt` themselves, depending on the situation.

- `net.minecraft.world.Containers`
    - `updateNeighboursAfterDestroy` - Updates the neighbor state aftering destroying the block at the specified position.
    - `dropContentsOnDestroy` is removed, handled within `BlockEntity#preRemoveSideEffects` for `Container` instances
- `net.minecraft.world.level.block.entity.BlockEntity#preRemoveSideEffects` - Handles logic on the block entity that should happen before being removed from the level.
- `net.minecraft.world.level.block.state.BlockBehaviour#onRemove`, `$BlockStateBase#onRemove` -> `affectNeighborsAfterRemoval`, should only handle logic to update the surrounding neighbors rather than dropping container data

## Voxel Shape Helpers

`VoxelShape`s have received numerous helpers for more common transformations of its base state. There are the `Block` methods for creating a centered (if desired) box and the `Shapes` methods for rotating a `VoxelShape` to its appropriate axis or direction. There is also a `Shapes#rotateAttachFace` method for rotating some `VoxelShape` that is attached to a face of a different block. The results are either stored in a `Map` of some key to a `VoxelShape`, or when using `Block#getShapeForEachState`, a `Function<BlockState, VoxelShape>`.

Most of the `Block` subclasses that had previous public or protected `VoxelShape`s are now private, renamed to a field typically called `SHAPE` or `SHAPES`. Stored `VoxelShape`s may also be in a `Function` instead of directly storing the map itself.

- `com.mojang.math.OctahedralGroup`
    - `permute` - Returns the axis that the given axis is permuted to within the specified group.
    - `fromAngles` - Creates a group with the provided X and Y rotations.
- `net.minecraft.core.Direction$Axis#choose` now has an overload that takes in three booleans
- `net.minecraft.world.level.block.Block`
    - `boxes` - Creates one more than the specified number of boxes, using the index as part of the function to create the `VoxelShape`s.
    - `cube` - Creates a centered cube of the specified size.
    - `column` - Creates a horizontally centered column of the specified size.
    - `boxZ` - Creates a vertically centered (around the X axis) cube/column of the specified size.
    - `getShapeForEachState` now returns a `Function` which wraps the `ImmutableMap`, there is also a method that only considers the specified properties instead of all possible states.
- `net.minecraft.world.phys.shapes`
    - `DiscreteVoxelShape#rotate` - Rotates a voxel shape according to the permutation of the `OctahedralGroup`.
    - `Shapes`
        - `blockOccudes` -> `blockOccludes`
        - `rotate` - Rotates a given voxel shape according to the permutation of the `OctahedralGroup` around the provided vector, or block center if not specified.
        - `equal` - Checks if two voxel shapes are equivalent.
        - `rotateHorizontalAxis` - Creates a map of axes to `VoxelShape`s of a block being rotated around the y axis.
        - `rotateAllAxis` - Creates a map of axes to `VoxelShape`s of a block being rotated around any axis.
        - `rotateHorizontal` - Creates a map of directions to `VoxelShape`s of a block being rotated around the y axis.
        - `rotateAll` - Creates a map of directions to `VoxelShape`s of a block being rotated around the any axis.
        - `rotateAttachFace` - Creates a map of faces to a map of directions to `VoxelShape`s of a block being rotated around the y axis when attaching to the faces of other blocks.
    - `VoxelShape#move` now has an overload to take in a `Vec3i`

## Weapons, Tools, and Armor: Removing the Redundancies

There have been a lot of updates to weapons, tools, and armor that removes the reliance on the hardcoded base classes of `SwordItem`, `DiggerItem`, and `ArmorItem`, respectively. These have been replaced with their associated data components `WEAPON` for damage, `TOOL` for mining, `ARMOR` for protection, and `BLOCKS_ATTACKS` for shields. Additionally, the missing attributes are usually specified by setting the `ATTRIBUTE_MODIFIERS`, `MAX_DAMAGE`, `MAX_STACK_SIZE`, `DAMAGE`, `REPAIRABLE`, and `ENCHANTABLE`. Given that pretty much all of the non-specific logic has moved to a data component, these classes have now been completely removed. Use one of the available item property methods or call `Item$Properties#component` directly to set up each item as a weapon, tool, armor, or some combination of the three.

Constructing a `BlockAttacks` component for a shield-like item:

```java
var blocker = new BlocksAttacks(
    // The number of seconds to wait when the item is being used
    // before the blocking effect is applied.
    1.2f,
    // A scalar to change how many ticks the blocker is disabled
    // for. If negative, the blocker cannot normally be disabled.
    0.5f,
    // A list of reductions for what type and how much of a damage type
    // is blocked by this blocker.
    List.of(
        new DamageReduction(
            // The horizontal blocking angle of the shield required to apply
            // the reduction
            90f,
            // A set of damage types this reduction should apply for.
            // When empty, it applies for all damage types.
            Optional.empty(),
            // The base damage to reduce the attack by.
            1f,
            // A scalar representing the fraction of the damage blocked.
            0.5f
        )
    ),
    // A function that determines how much durability to remove to the blocker.
    new ItemDamageFunction(
        // A threshold that specifies the minimum amount of damage required
        // to remove durability from the blocker.
        4f,
        // The base durability to remove from the blocker.
        1f,
        // A scalar representing the fraction of the damage to convert into
        // removed durability.
        0.5f
    ),
    // A tag key containing the items that can bypass the blocker and deal
    // damage directly to the wielding entity. If empty, no item can bypass
    // the blocker.
    Optional.of(DamageTypeTags.BYPASSES_SHIELD),
    // The sound to play when the blocker successfully mitigates some damage.
    Optional.of(SoundEvents.SHIELD_BLOCK),
    // The sound to play when the blocker is disabled by a weapon.
    Optional.of(SoundEvents.SHIELD_BREAK)
);
```

Constructing a `Weapon` component for a sword-like item:

```java
var weapon = new Weapon(
    // The amount of durability to remove from the item.
    3,
    // The number of seconds a `BlocksAttack`s component item should
    // be disabled for when hit with this weapon.
    5f
);
```

- `net.minecraft.core.component.DataComponents`
    - `UNBREAKABLE` is now a `Unit` instance
    - `HIDE_ADDITIONAL_TOOLTIP`, `HIDE_TOOLTIP` have been bundled in `TOOLTIP_DISPLAY`, taking in a `TooltipDisplay`
    - `BLOCKS_ATTACKS` - A component that determines whether a held item can block an attack from some damage source
    - `INSTRUMENT` now takes in an `InstrumentComponent`
    - `PROVIDES_TRIM_MATERIAL`, `PROVIDES_BANNER_PATTERNS` handles a provider for their associated types.
    - `BEES` now takes in a `Bees` component
    - `BREAK_SOUND` - The sound to play when the item breaks.
- `net.minecraft.data.recipes`
    - `RecipeProvider#trimSmithing` now takes in the key for the `TrimPattern`
    - `SmithingTrimRecipeBuilder` now takes in a holder for the `TrimPattern`
- `net.minecraft.world.entity.LivingEntity`
    - `blockUsingShield` -> `blockUsingItem`
    - `blockedByShield` -> `blockedByItem`
    - `hurtCurrentlyUsedShield` is removed
    - `canDisableBlocking` -> `getSecondsToDisableBlocking`, not one-to-one
    - `applyItemBlocking` - Applies the damage reduction done when blocking an attack with an item.
    - `isDamageSourceBlocked` is removed
- `net.minecraft.world.entity.player.Player#disableShield` -> `net.minecraft.world.item.component.BlocksAttacks#disable`
- `net.minecraft.world.item`
    - `AnimalArmorItem` class is removed
    - `ArmorItem` class is removed
    - `AxeItem` now extends `Item`
    - `BannerPatternItem` class is removed
    - `DiggerItem` class is removed
    - `FireworkStarItem` class is removed
    - `HoeItem` now extends `Item`
    - `InstrumentItem` no longer takes in the tag key
    - `Item`
        - `getBreakingSound` is removed
        - `$Properties`
            - `tool` - Sets the item as a tool.
            - `pickaxe` - Sets the item as a pickaxe.
            - `sword` - Sets the item as a sword.
            - `axe` - Sets the item as an axe.
            - `hoe` - Sets the item as a hoe.
            - `shovel` - Sets the item as a shovel.
            - `trimMaterial` - Sets the item as providing a trim material.
    - `ItemStack#getBreakingSound` is removed
    - `PickaxeItem` class is removed
    - `ShovelItem` now extends `Item`
    - `SwordItem` class is removed
    - `ToolMaterial#applyToolProperties` now takes in a boolean of whether the weapon can disable a blocker (e.g., shield)
- `net.minecraft.world.item.component`
    - `Bees` - A component that holds the occupants of a beehive.
    - `BlocksAttacks` - A component for blocking an attack with a held item.
    - `InstrumentComponent` - A component that holds the sound an instrument plays.
    - `ProvidesTrimMaterial` - A component that provides a trim material to use on some armor.
    - `Tool` now takes in a boolean representing if the tool can destroy blocks in creative
    - `Unbreakable` class is removed
    - `Weapon` - A data component that holds how much damage the item can do and for how long it disables blockers (e.g., shield).
- `net.minecraft.world.item.equipment`
    - `AllowedEntitiesProvider` - A functional interface for getting the entities that are allowed to handle the associated logic.
    - `ArmorMaterial`
        - `humanoidProperties` -> `Item$Properties#humanoidArmor`
        - `animalProperties` -> `Item$Properties#wolfArmor`, `horseArmor`
        - `createAttributes` is now public
    - `Equippable`
        - `equipOnInteract` - When true, the item can be equipped to another entity when interacting with them.
        - `saddle` - Creates an equippable for a saddle.
        - `equipOnTarget` - Equips the item onto the target entity.


### Extrapolating the Saddles: Equipment Changes

A new `EquipmentSlot` has been added for saddles, which brings with it new changes for genercizing slot logic.

First, rendering an equipment slot for an entity can now be handled as an additional `RenderLayer` called `SimpleEquipmentLayer`. This takes in the entity renderer, the `EquipmentLayerRenderer`, the layer type to render, a function to get the `ItemStack` from the entity state, and the adult and baby models. The renderer will attempt to look up the client info from the associated equippable data component and use that to render the laters as necessary.

Next, instead of having individual lists for each equipment slot on the entity, there is now a general `EntityEquipment` object that holds a delegate to a map of slots to `ItemStack`s. This simplifies the storage logic greatly.

Finally, equippables can now specify whether an item should be equipped to a mob on interact (usually right-click) by setting `equipOnInteract`.

- `net.minecraft.client.model`
    - `CamelModel`
        - `head` is now public
        - `createBodyMesh` - Creates the mesh definition for a camel.
    - `CamelSaddleModel` - A model for a camel with a saddle.
    - `DonkeyModel#createSaddleLayer` - Creates the layer definition for a donkey with a saddle.
    - `EquineSaddleModel` - A model for an equine animal with a saddle.
    - `PolarBearModel#createBodyLayer` now takes in a boolean for if the entity is a baby
- `net.minecraft.client.renderer.entity.layers.HorseArmorLayer`, `SaddleLayer` -> `SimpleEquipmentLayer`
- `net.minecraft.client.renderer.entity.state`
    - `CamelRenderState#isSaddled` -> `saddle`, not one-to-one
    - `EquineRenderState#isSaddled` -> `saddle`, not one-to-one
    - `PigRenderState#isSaddled` -> `saddle`, not one-to-one
    - `SaddleableRenderState` class is removed
    - `StriderRenderState#isSaddled` -> `saddle`, not one-to-one
    - `CamelRenderState#isSaddled` -> `saddle`, not one-to-one
- `net.minecraft.client.resources.model.EquipmentClientInfo$LayerType` now has:
    - `PIG_SADDLE`
    - `STRIDER_SADDLE`
    - `CAMEL_SADDLE`
    - `HORSE_SADDLE`
    - `DONKEY_SADDLE`
    - `MULE_SADDLE`
    - `ZOMBIE_HORSE_SADDLE`
    - `SKELETON_HORSE_SADDLE`
    - `trimAssetPrefix` - Returns the prefix applied to the texture containing the armor trims for the associated type.
- `net.minecraft.world.entity`
    - `EntityEquipment` - A map of slots to item stacks representing the equipment of the entity.
    - `EquipmentSlot`
        - `SADDLE`, `$Type#SADDLE`
        - `canIncreaseExperience` - Whether the slot can increase the amount of experience earned when killing a mob.
    - `EquipmentSlotGroup` is now an iterable
        - `SADDLE`
        - `slots` - Returns the slots within the group.
    - `LivingEntity`
        - `getEquipSound` - Gets the sound to play when equipping an item into a slot.
        - `getArmorSlots`, `getHandSlots`, `getArmorAndBodyArmorSlots`, `getAllSlots` are removed
        - `equipment` - The equipment worn by the entity.
        - `createEquipment` - Sets the default equipment worn by the entity.
        - `drop` - Drops the specified stack.
        - `getItemBySlot`, `setItemBySlot` are no longer abstract.
        - `verfiyEquippedItem` is removed
    - `Mob`
        - `isSaddled` - Checks if an item is in the saddle slot.
        - `createEquipmentSlotContainer` - Creates a single item container for the equipment slot.
    - `OwnableEntity#getRootOwner` - Gets the highest level owner of the entity.
    - `Saddleable` interface is removed
- `net.minecraft.world.entity.animal.horse.AbstractHorse`
    - `syncSaddletoClients` is removed
    - `getBodyArmorAccess` is removed
- `net.minecraft.world.entity.player`
    - `Inventory`
        - `armor`, `offhand` -> `EQUIPMENT_SLOT_MAPPING`, not one-to-one
        - `selected` is now private
        - `setSelectedHotbarSlot` -> `setSelectedSlot`
            - Getter also exists `getSelectedSlot`
        - `getSelected` -> `getSelectedItem`
            - Setter also exists `setSelectedItem`
        - `getNonEquipmentItems` - Returns the list of non-equipment items in the inventory.
        - `getDestroySpeed` is removed
        - `getArmor` is removed
    - `PlayerEquipment` - Equipment that is worn by the player.
- `net.minecraft.world.item`
    - `Item#inventoryTick(ItemStack, Level, Entity, int, boolean)` -> `inventoryTick(ItemStack, ServerLevel, Entity, EquipmentSlot)`
    - `SaddleItem` class is removed

## Weighted List Rework

The weighted random lists have been redesigned into a basic class that hold weighted entries, and a helper class that can obtain weights from the objects themselves.

First there is `WeightedList`. It is effectively the replacement for `SimpleWeightedRandomList`, working the exact same way by storing `Weighted` (replacement for `WeightedEntry`) entries in the list itself. Internally, the list is either stored as a flat array of object entries, or the compact weighted list if the total weight is greater than 64. Then, to get a random element, either `getRandom` or `getRandomOrThrow` can be called to obtain an entry. Both of these methods will either return some form of an empty object or exception if there are no elements in the list.

Then there are the static helpers within `WeightedRandom`. These take in raw lists and some `ToIntFunction` that gets the weight from the list's object. Some methods also take in an integer either representing the largest index to choose from or the entry associated with the weighted index.

- `net.minecraft.client.resources.model.WeightedBakedModel` now takes in a `WeightedList` instead of a `SimpleWeightedRandomList`
- `net.minecraft.util.random`
    - `SimpleWeightedRandomList`, `WeightedRandomList` -> `WeightedList`, now final and not one-to-one
        - `contains` - Checks if the list contains this element.
    - `Weight` class is removed
    - `WeightedEntry` -> `Weighted`
    - All `WeightedRandom` static methods now take in a `ToIntFunction` to get the weight of some entry within the provided list
- `net.minecraft.util.valueproviders.WeightedListInt` now takes in a `WeightedList`
- `net.minecraft.world.level.SpawnData#LIST_CODEC` is now a `WeightedList` of `SpawnData`
- `net.minecraft.world.level.biome`
    - `Biome#getBackgroundMusic` is now a `WeightedList` of `Music`
    - `BiomeSpecialEffects#getBackgroundMusic`, `$Builder#backgroundMusic` is now a `WeightedList` of `Music`
    - `MobSpawnSettings#EMPTY_MOB_LIST`, `getMobs` is now a `WeightedList`
- `net.minecraft.world.level.block.entity.trialspawner.TrialSpawnerConfig#spawnPotentialsDefinition`, `lootTablesToEject` now takes in a `WeightedList`
- `net.minecraft.world.level.chunk.ChunkGenerator#getMobsAt` now returns a `WeightedList`
- `net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider` now works with `WeightedList`s
- `net.minecraft.world.level.levelgen.heightproviders.WeightedListHeight` now works with `WeightedList`s
- `net.minecraft.world.level.levelgen.structure.StructureSpawnOverride` now takes in a `WeightedList`
- `net.minecraft.world.level.levelgen.structure.pools.alias`
    - `PoolAliasBinding#random`, `randomGroup` now takes in a `WeightedList`
    - `Random` now takes in a `WeightedList`
    - `RandomGroup` now takes in a `WeightedList`
- `net.minecraft.world.level.levelgen.structure.structures.NetherFortressStructure#FORTRESS_ENEMIES` is now a `WeightedList`

## Tickets

Tickets have been reimplemented into a half type registry-like, half hardcoded system. The underlying logic of keeping a chunk loaded or simulated for a certain period of time still exists; however, the logic associated with each ticket is hardcoded into their appropriate locations, such as the forced or player loading tickets.

Tickets begin with their registered `TicketType`, which contains information about how many ticks should the ticket should last for (or `0` when permanent), whether the ticket should be saved to disk, and what the ticket is used for. A ticket has two potential uses: one for loading the chunk and keeping it loaded, and one for simulating the chunk based on the expected movement of the ticket creator. Most ticks specify that they are for both loading and simulation.

There are two special types that have additional behavior associated with them. `TicketType#FORCED` has some logic for immediately loading the chunk and keeping it loaded. `TicketType#UNKNOWN` cannot be automatically timed out, meaning they are never removed unless explicitly specified.

```java
// You need to register the ticket type to `BuiltInRegistries#TICKET_TYPE`
public static final TicketType EXAMPLE = new TicketType(
    // The amount of ticks before the ticket is removed
    // Set to 0 if it should not be removed
    0L,
    // Whether the ticket should be saved to disk
    true,
    // What the ticket will be used for
    TicketType.TicketUse.LOADING_AND_SIMULATION
);
```

Then there is the `Ticket` class, which are actually stored and handled within the `TicketStorage`. The `Ticket` class takes in the type of the ticket and uses it to automatically populate how long until it expires. It also takes in the ticket level, which is a generally a value of 31 (for entity ticking and block ticking), 32 (for block ticking), or 33 (only can access static or modify, not naturally update) minus the radius of chunks that can be loaded. A ticket is then added to the process by calling `TicketStorage#addTicketWithRadius` or its delegate `ServerChunkCache#addTicketWithRadius`. There is also `addTicket` if you wish to specify the ticket manually rather than having it computed based on its radius.

- `net.minecraft.server.level`
    - `ChunkMap` now takes in a `TicketStorage`
        - `$TrackedEntity#broadcastIgnorePlayers` - Broadcasts the packet to all player but those within the UUID list.
    - `DistanceManager`
        - `chunksToUpdateFutures` is now protected and takes in a `TicketStorage`
        - `purgeStaleTickets` -> `net.minecraft.world.level.TicketStorage#purgeStaleTickets`
        - `getTicketDebugString` -> `net.minecraft.world.level.TicketStorage#getTicketDebugString`
        - `getChunkLevel` - Returns the current chunk level or the simulated level when the provided boolean is true.
        - `getTickingChunks` is removed
        - `removeTicketsOnClosing` is removed
        - `$ChunkTicketTracker` -> `LoadingChunkTracker`, or `SimulationChunkTracker`
    - `ServerChunkCache`
        - `addRegionTicket` -> `addTicketWithRadius`, or `addTicket`
        - `removeRegionTicket` -> `removeTicketWithRadius`
        - `removeTicketsOnClosing` -> `deactivateTicketsOnClosing`
    - `Ticket` is no longer final or implements `Comparable`
        - The constructor no longer takes in a key
        - `CODEC`
        - `setCreatedTick`, `timedOut` -> `resetTicksLeft`, `decreaseTicksLeft`, `isTimedOut`; not one-to-one
    - `TicketType` is now a record and no longer has a generic
        - `getComparator` is removed
        - `doesLoad`, `doesSimulate` - Checks whether the ticket use is for their particular instance.
        - `$TicketUse` - What a ticket can be used for.
    - `TickingTracker` -> `SimulationChunkTracker`
- `net.minecraft.world.level.ForcedChunksSavedData` -> `TicketStorage`
- `net.minecraft.world.level.chunk.ChunkSource`
    - `updateChunkForced` now returns a boolean indicating if the chunk has been forcefully loaded
    - `getForceLoadedChunks` - Returns all chunks that have been forcefully loaded.

## The Game Test Overhaul

Game tests have been completely overhauled into a registry based system, completely revamped from the previous automatic annotation-driven system. However, most implementations required to use the system must be implemented yourself rather than provided by vanilla. As such, this explanation will go over the entire system, including which parts need substantial work to use it similarly to the annotation-driven system of the previous version.

### The Environment

All game tests happen within some environment. Most of the time, a test can occur independent of the area, but sometimes, the environment needs to be curated in some fashion, such as checking whether an entity or block does something at a given time or whether. To facilitate the setup and teardown of the environment for a given test instance, a `TestEnvironmentDefinition` is created.

A `TestEnvironmentDefinition` works similarly to the `BeforeBatch` and `AfterBatch` annotations. The environment contains two methods `setup` and `teardown` that manage the `ServerLevel` for the test. The environments are structured in a type-based registry system, meaning that every environment registers a `MapCodec` to built-in registry `minecraft:test_environment_definition_type` that is then consumed via the `TestEnvironmentDefinition` in a datapack registry `minecraft:test_environment`.

Vanilla, by default, provides the `minecraft:default` test environment which does not do anything. However, additional test environments can be created using the available test definition types.

#### Game Rules

This environment type sets the game rules to use for the test. During teardown, the game rules are set back to their default value.

```json5
// examplemod:example_environment
// In 'data/examplemod/test_environment/example_environment.json'
{
    "type": "minecraft:game_rules",

    // A list of game rules with boolean values to set
    "bool_rules": [
        {
            // The name of the rule
            "rule": "doFireTick",
            "value": false
        }
        // ...
    ],

    // A list of game rules with integer values to set
    "int_rules": [
        {
            "rule": "playersSleepingPercentage",
            "value": 50
        }
        // ...
    ]
}
```

#### Time of Day

This environment type sets the time to some non-negative integer, similar to how the `/time set <number>` command is used.

```json5
// examplemod:example_environment
// In 'data/examplemod/test_environment/example_environment.json'
{
    "type": "minecraft:time_of_day",

    // Sets the time of day in the world
    // Common values:
    // - Day      -> 1000
    // - Noon     -> 6000
    // - Night    -> 13000
    // - Midnight -> 18000
    "time": 13000
}
```

#### Weather

This environment type sets the weather, similar to how the `/weather` command is used.

```json5
// examplemod:example_environment
// In 'data/examplemod/test_environment/example_environment.json'
{
    "type": "minecraft:weather",

    // Can be one of three values:
    // - clear   (No weather)
    // - rain    (Rain)
    // - thunder (Rain and thunder)
    "weather": "thunder"
}
```

#### Function

This environment type provides two `ResourceLocation`s to mcfunctions to setup and teardown the level, respectively.

```json5
// examplemod:example_environment
// In 'data/examplemod/test_environment/example_environment.json'
{
    "type": "minecraft:function",

    // The setup mcfunction to use
    // If not specified, nothing will be ran
    // Points to 'data/examplemod/function/example/setup.mcfunction'
    "setup": "examplemod:example/setup",

    // The teardown mcfunction to use
    // If not specified, nothing will be ran
    // Points to 'data/examplemod/function/example/teardown.mcfunction'
    "teardown": "examplemod:example/teardown"
}
```

#### Composite

If multiple combinations are required, then the composite environment type (aptly named `all_of`) can be used to string multiple of the above environment types together.

```json5
// examplemod:example_environment
// In 'data/examplemod/test_environment/example_environment.json'
{
    "type": "minecraft:all_of",

    // A list of test environments to use
    // Can either specified the registry name or the environment itself
    "definitions": [
        // Points to 'data/minecraft/test_environment/default.json'
        "minecraft:default",
        {
            // A raw environment definition
            "type": "..."
        }
        // ...
    ]
}
```

### Custom Types

If none of the types above work, then a custom definition can be created by implementing `TestEnvironmentDefinition` and creating an associated `MapCodec`:

```java
public record ExampleEnvironmentType(int value1, boolean value2) implements TestEnvironmentDefinition {

    // Construct the map codec to register
    public static final MapCodec<ExampleEnvironmentType> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("value1").forGetter(ExampleEnvironmentType::value1),
            Codec.BOOL.fieldOf("value2").forGetter(ExampleEnvironmentType::value2)
        ).apply(instance, ExampleEnvironmentType::new)
    );

    @Override
    public void setup(ServerLevel level) {
        // Setup whatever is necessary here
    }

    @Override
    public void teardown(ServerLevel level) {
        // Undo whatever was changed within the setup method
        // This should either return to default or the previous value
    }

    @Override
    public MapCodec<ExampleEnvironmentType> codec() {
        return CODEC;
    }
}
```

Then register the `MapCodec` using whatever registry method is required by your mod loader:

```java
Registry.register(
    BuiltInRegistries.TEST_ENVIRONMENT_DEFINITION_TYPE,
    ResourceLocation.fromNamespaceAndPath("examplemod", "example_environment_type"),
    ExampleEnvironmentType.CODEC
);
```

Finally, you can use it in your environment definition:

```json5
// examplemod:example_environment
// In 'data/examplemod/test_environment/example_environment.json'
{
    "type": "examplemod:example_environment_type",

    "value1": 0,
    "value2": true
}
```

### Test Functions

The initial concept of game tests were structured around running functions from `GameTestHelper` determining whether the test succeeds or fails. Test functions are the registry-driven representation of those. Essentially, every test function is a method that takes in a `GameTestHelper`.

At the moment, vanilla only provides `minecraft:always_pass`, which just calls `GameTestHelper#succeed`. Test functions are also not generated, meaning it simply runs the value with whatever is provided. As such, a test function should generally represent a single old game test:

```java
Registry.register(
    BuiltInRegistries.TEST_FUNCTION,
    ResourceLocation.fromNamespaceAndPath("examplemod", "example_function"),
    (GameTestHelper helper) -> {
        // Run whatever game test commands you want
        helper.assertBlockPresent(...);

        // Make sure you have some way to succeed
        helper.succeedIf(() -> ...);
    }
);
```

### Test Data

Now that we have environments and test functions, we can get into defining our game test. This is done through `TestData`, which is the equivalent of the `GameTest` annotation. The only things changed are that structures are now referenced by their `ResourceLocation` via `structure`, `GameTest#timeoutTicks` is now renamed to `TestData#maxTicks`, and instead of specifying `GameTest#rotationSteps`, you simply provide the `Rotation` via `TestData#rotation`. Everything else remains the same, just represented in a different format.

### The Game Test Instance

With the `TestData` in hand, we can now link everything together through the `GameTestInstance`. This instance is what actually represents a single test. Once again, vanilla only provides the default `minecraft:always_pass`, so we will need to construct the instance ourselves.

#### The Original Instance

The previous game tests are implemented using `minecraft:function`, which links a test function to the test data.

```json
// examplemod:example_test
// In 'data/examplemod/test_instance/example_test.json'
{
    "type": "minecraft:function",

    // Points to a 'Consumer<GameTestHelper>' in the test function registry
    "function": "examplemod:example_function",

    // The 'TestData' information

    // The environment to run the test in
    // Points to 'data/examplemod/test_environment/example_environment.json'
    "environment": "examplemod:example_environment",
    // The structure used for the game test
    // Points to 'data/examplemod/structure/example_structure.nbt'
    "structure": "examplemod:example_structure",
    // The number of ticks that the game test will run until it automatically fails
    "max_ticks": 400,
    // The number of ticks that are used to setup everying required for the game test
    // This is not counted towards the maximum number of ticks the test can take
    // If not specified, defaults to 0
    "setup_ticks": 50,
    // Whether the test is required to succeed to mark the batch run as successful
    // If not specified, defaults to true
    "required": true,
    // Specifies how the structure and all subsequent helper methods should be rotated for the test
    // If not specified, nothing is rotated
    "rotation": "clockwise_90",
    // When true, the test can only be ran through the `/test` command
    // If not specified, defaults to false
    "manual_only": true,
    // Specifies the maximum number of times that the test can be reran
    // If not specified, defaults to 1
    "max_attempts": 3,
    // Specifies the minimum number of successes that must occur for a test to be marked as successful
    // This must be less than or equal to the maximum number of attempts allowed
    // If not specified, defaults to 1
    "required_successes": 1,
    // Returns whether the structure boundary should keep the top empty
    // This is currently only used in block-based test instances
    // If not specified, defaults to false 
    "sky_access": false
}
```

#### Block-Based Instances

Vanilla also provides a block-based test instance via `minecraft:block_based`. This is handled through via structures with test blocks receiving signals via `Level#hasNeighborSignal`. To start, a structure must have one test block which is set to its start mode. This block is then triggered, sending a fifteen signal pulse for one tick. Structures may then have as many test blocks with either a log, accept, or fail mode set. Log test blocks also send a fifteen signal pulse when activated. Accept and fail test blocks either succeed or fail the game test if any of them are activated (success takes precedent over failure).

As this test relies on test blocks in the structure, no additional information is required other than the test data:

```json
// examplemod:example_test
// In 'data/examplemod/test_instance/example_test.json'
{
    "type": "minecraft:block_based",

    // The 'TestData' information

    // Points to 'data/examplemod/test_environment/example_environment.json'
    "environment": "examplemod:example_environment",
    // Points to 'data/examplemod/structure/example_structure.nbt'
    "structure": "examplemod:example_structure",
    "max_ticks": 400,
    "setup_ticks": 50,
    "required": true,
    "rotation": "clockwise_90",
    "manual_only": true,
    "max_attempts": 3,
    "required_successes": 1,
    "sky_access": false
}
```

#### Custom Tests

If you need to implement your own test-based logic, whether using a more dynamic feature ~~or because you can't be bothered to migrated all of your data logic to the new systems~~, you can create your own custom test instance by extending `GameTestInstance` and creating an associated `MapCodec`:

```java
public class ExampleTestInstance extends GameTestInstance {

    // Construct the map codec to register
    public static final MapCodec<ExampleTestInstance> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("value1").forGetter(test -> test.value1),
            Codec.BOOL.fieldOf("value2").forGetter(test -> test.value2),
            TestData.CODEC.forGetter(ExampleTestInstance::info)
        ).apply(instance, ExampleTestInstance::new)
    );

    public ExampleTestInstance(int value1, boolean value2, TestData<Holder<TestEnvironmentDefinition>> info) {
        super(info);
    }

    @Override
    public void run(GameTestHelper helper) {
        // Run whatever game test commands you want
        helper.assertBlockPresent(...);

        // Make sure you have some way to succeed
        helper.succeedIf(() -> ...);
    }

    @Override
    public MapCodec<ExampleTestInstance> codec() {
        return CODEC;
    }

    @Override
    protected MutableComponent typeDescription() {
        // Provides a description about what this test is supposed to be
        // Should use a translatable component
        return Component.literal("Example Test Instance");
    }
}
```

Then register the `MapCodec` using whatever registry method is required by your mod loader:

```java
Registry.register(
    BuiltInRegistries.TEST_INSTANCE_TYPE,
    ResourceLocation.fromNamespaceAndPath("examplemod", "example_test_instance"),
    ExampleTestInstance.CODEC
);
```

Finally, you can use it in your test instance:

```json
// examplemod:example_test
// In 'data/examplemod/test_instance/example_test.json'
{
    "type": "examplemod:example_test_instance",

    "value1": 0,
    "value2": true,

    // The 'TestData' information

    // Points to 'data/examplemod/test_environment/example_environment.json'
    "environment": "examplemod:example_environment",
    // Points to 'data/examplemod/structure/example_structure.nbt'
    "structure": "examplemod:example_structure",
    "max_ticks": 400,
    "setup_ticks": 50,
    "required": true,
    "rotation": "clockwise_90",
    "manual_only": true,
    "max_attempts": 3,
    "required_successes": 1,
    "sky_access": false
}
```

- `net.minecraft.client.renderer.blockentity`
    - `BeaconRenderer` now has a generic that takes in a subtype of `BlockEntity` and `BeaconBeamOwner`
    - `StructureBlockRenderer` -> `BlockEntityWithBoundingBoxRenderer`, not one-to-one
- `net.minecraft.core.registries.Registries#TEST_FUNCTION`, `TEST_ENVIRONMENT_DEFINITION_TYPE`, `TEST_INSTANCE_TYPE`
- `net.minecraft.gametest.Main` - The entrypoint for the game test server.
- `net.minecraft.gametest.framework`
    - `AfterBatch`, `BeforeBatch` annotations are removed
    - `BlockBasedTestInstance` - A test instance for testing the test block.
    - `BuiltinTestFunctions` - Contains all registered test functions.
    - `FailedTestTracker` - An object for holding all game tests that failed.
    - `FunctionGameTestInstance` - A test instance for running a test function.
    - `GameTest` annotation is removed
    - `GameTestAssertException` now extends `GameTestException`
    - `GameTestException` - An exception thrown during the execution of a game test.
    - `GameTestBatch` now takes in an index and environment definition instead of a name and batch setups
    - `GameTestBatchFactory`
        - `fromTestFunction` -> `divideIntoBatches`, not one-to-one
        - `toGameTestInfo` is removed
        - `toGameTestBatch` now takes in an environment definition and an index
        - `$TestDecorator` - Creates a list of test infos from a test instance and level.
    - `GameTestEnvironments` - Contains all environments used for batching game test instances.
    - `GameTestGenerator` annotation is removed
    - `GameTestHelper`
        - `tickBlock` - Ticks the block at the specific position.
        - `assertionException` - Returns a new exception to throw on error.
        - `getBlockEntity` now takes in a `Class` to cast the block entity to
        - `assertBlockTag` - Checks whether the block at the position is within the provided tag.
        - `assertBlock` now takes in a block -> component function for the error message.
        - `assertBlockProperty` now takes in a `Component` instead of a string
        - `assertBlockState` now takes in either nothing, a blockstate -> component function, or a supplied component
        - `assertRedstoneSignal` now takes in a supplied component
        - `assertContainerSingle` - Asserts that a container contains exactly one of the item specified.
        - `assertEntityPosition`, `assertEntityProperty` now takes in a component
        - `fail` now takes in a `Component` for the error message
        - `assertTrue`, `assertValueEqual`, `assertFalse` now takes in a component
    - `GameTestInfo` now takes in a holder-wrapped `GameTestInstance` instead of a `TestFunction`
        - `setStructureBlockPos` -> `setTestBlockPos`
        - `placeStructure` now returns nothing
        - `getTestName` - `id`, not one-to-one
        - `getStructureBlockPos` -> `getTestBlockPos`
        - `getStructureBlockEntity` -> `getTestInstanceBlockEntity`
        - `getStructureName` -> `getStructure`
        - `getTestFunction` -> `getTest`, `getTestHolder`, not one-to-one
        - `getOrCalculateNorthwestCorner`, `setNorthwestCorner` are removed
        - `fail` now takes in a `Component` or `GameTestException` instead of a `Throwable`
        - `getError` now returns a `GameTestException` instead of a `Throwable`
    - `GameTestInstance` - Defines a test to run.
    - `GameTestInstances` - Contains all registered tests.
    - `GameTestMainUtil` - A utility for running the game test server.
    - `GameTestRegistry` class is removed
    - `GameTestSequence`
        - `tickAndContinue`, `tickAndFailIfNotComplete` now take in an integer for the tick instead of a long
        - `thenFail` now takes in a supplied `GameTestException` instead of a `Throwable`
    - `GameTestServer#create` now takes in an optional string and boolean instead of the collection of test functions and the starting position
    - `GeneratedTest` - A object holding the test to run for the given environment and the function to apply
    - `GameTestTicker$State` - An enum containing what state the game test ticker is currently executing.
    - `GameTestTimeoutException` now extends `GameTestException`
    - `ReportGameListener#spawnBeacon` is removed
    - `StructureBlockPosFinder` -> `TestPosFinder`
    - `StructureUtils`
        - `testStructuresDir` is now a path
        - `getStructureBounds`, `getStructureBoundingBox`, `getStructureOrigin`, `addCommandBlockAndButtonToStartTest` are removed
        - `createNewEmptyStructureBlock` -> `createNewEmptyTest`, not one-to-one
        - `getStartCorner`, `prepareTestStructure`, `encaseStructure`, `removeBarriers` are removed
        - `findStructureBlockContainingPos` -> `findTestContainingPos`
        - `findNearestStructureBlock` -> `findNearestTest`
        - `findStructureByTestFunction`, `createStructureBlock` are removed
        - `findStructureBlocks` -> `findTestBlocks`
        - `lookedAtStructureBlockPos` -> `lookedAtTestPos`
    - `TestClassNameArgument` is removed
    - `TestEnvironmentDefinition` - Defines the environment that the test is run on by setting the data on the level appropriately.
    - `TestFinder` no longer contains a generic for the context
        - `$Builder#allTests`, `allTestsInClass`, `locateByName` are removed
        - `$Builder#byArgument` -> `byResourceSelection`
    - `TestFunction` -> `TestData`, not one-to-one
    - `TestFunctionArgument` -> `net.minecraft.commands.arguments.ResourceSelectorArgument`
    - `TestFunctionFinder` -> `TestInstanceFinder`
    - `TestFunctionLoader` - Holds the list of test functions to load and run.
    - `UnknownGameTestException` - An exception that is thrown when the error of the game test is unknown.
- `net.minecraft.network.protocol.game`
    - `ClientboundTestInstanceBlockState` - A packet sent to the client containing the status of a test along with its size.
    - `ServerboundSetTestBlockPacket` - A packet sent to the server to set the information within the test block to run.
    - `ServerboundTestInstanceBlockActionPacket` - A packet sent to the server to set up the test instance within the test block.
- `net.minecraft.world.entity.player.Player`
    - `openTestBlock` - Opens a test block.
    - `openTestInstanceBlock` - Opens a test block for a game test instance.
- `net.minecraft.world.level.block`
    - `TestBlock` - A block used for running game tests.
    - `TestInstanceBlock` - A block used for managing a single game test.
- `net.minecraft.world.level.block.entity`
    - `BeaconBeamOwner` - An interface that represents a block entity with a beacon beam.
    - `BeaconBlockEntity` now implements `BeaconBeamOwner`
        - `BeaconBeamSection` -> `BeaconBeamOwner$Section`
    - `BoundingBoxRenderable` - An interface that represents a block entity that can render an arbitrarily-sized bounding box.
    - `StructureBlockEntity` now implements `BoundingBoxRenderable`
    - `TestBlockEntity` - A block entity used for running game tests.
    - `TestInstanceBlockEntity` - A block entity used for managing a single game test.
- `net.minecraft.world.level.block.state.properties.TestBlockMode` - A property for representing the current state of the game tests associated with a test block.

## Data Component Getters

The data component system can now be represented on arbitrary objects through the use of the `DataComponentGetter`. As the name implies, the getter is responsible for getting the component from the associated type key. Both block entities and entities use the `DataComponentGetter` to allow querying the internal data, such as variant information or custom names. They both also have methods for collecting the data components from another holder (via `applyImplicitComponents` or `applyImplicitComponent`). Block entities also contain a method for collection to another holder via `collectImplicitComponents`.

### Items

`ItemSubPredicate`s have been completely replaced with `DataComponentPredicate`s. Each sub predicate has its appropriate analog within the system.

- `net.minecraft.advancements.critereon.*` -> `net.minecraft.core.component.predicates.*`
    - `ItemAttributeModifiersPredicate` -> `AttributeModifiersPredicate`
    - `ItemBundlePredicate`  -> `BundlePredicate`
    - `ItemContainerPredicate` -> `ContainerPredicate`
    - `ItemCustomDataPredicate` -> `CustomDataPredicate`
    - `ItemDamagePredicate` -> `DamagePredicate`
    - `ItemEnchantmentsPredicate` -> `EnchantmentsPredicate`
    - `ItemFireworkExplosionPredicate` -> `FireworkExplosionPredicate`
    - `ItemFireworksPredicate` -> `FireworksPredicate`
    - `ItemJukeboxPlayablePredicate` -> `JukeboxPlayablePredicate`
    - `ItemPotionsPredicate` -> `PotionsPredicate`
    - `ItemSubPredicate` -> `DataComponentPredicate`, not one-to-one
        - `SINGLE_STREAM_CODEC`
    - `ItemSubPredicates` -> `DataComponentPredicates`, not one-to-one
    - `ItemTrimPredicate` -> `TrimPredicate`
    - `ItemWritableBookPredicate` -> `WritableBookPredicate`
    - `ItemWrittenBookPredicate` -> `WrittenBookPredicate`
- `net.minecraft.advancements.critereon`
    - `BlockPredicate` now takes in a `DataComponentMatchers` for matching any delegated component data
    - `DataComponentMatchers` - A predicate that operates on a `DataComponentGetter`, matching any exact and partial component data on the provider.
    - `EntityPredicate` now takes in a `DataComponentMatchers` instead of a `Optional<DataComponentExactPredicate>`
    - `ItemPredicate` now takes in a `DataComponentMatchers` for matching any delegated component data
    - `NbtPredicate#matches` now takes in a `DataComponentGetter` instead of an `ItemStack`
    - `SingleComponentItemPredicate` now implements `DataComponentPredicate` instead of `ItemSubPredicate`
        - `matches(ItemStack, T)` -> `matches(T)`
- `net.minecraft.core.component`
    - `DataComponentPatch`
        - `DELIMITED_STREAM_CODEC`
        - `$CodecGetter` - Gets the codec for a given component type.
    - `DataComponentPredicate` -> `DataComponentExactPredicate`
        - `isEmpty` - Checks if the expected components list within the predicate is empty.
- `net.minecraft.core.registries.Registries#ITEM_SUB_PREDICATE_TYPE` -> `DATA_COMPONENT_PREDICATE_TYPE`, not one-to-one
- `net.minecraft.world.item.AdventureModePredicate` no longer takes in a boolean to show in tooltip
- `net.minecraft.world.item`
    - `BannerItem#appendHoverTextFromBannerBlockEntityTag` is removed
    - `Item#appendHoverText(ItemStack, Item.TooltipContext, List<Component>, TooltipFlag)` -> `appendHoverText(ItemStack, Item.TooltipContext, TooltipDisplay, Consumer<Component>, TooltipFlag)`, now deprecated
    - `ItemStack`
        - `addToTooltip` is now public
        - `addDetailsToTooltip` - Appends the component details of an item to the tooltip.
    - `JukeboxPlayable#showInTooltip` is removed
- `net.minecraft.world.item.component`
    - `BlockItemStateProperties` now implements `TooltipProvider`
    - `ChargedProjectiles` now implements `TooltipProvider`
    - `CustomData#itemMatcher` is removed
    - `DyedItemColor#showInTooltip` is removed
    - `FireworkExplosion#addShapeNameTooltip` is removed
    - `ItemAttributeModifiers#showInTooltip` is removed
    - `ItemContainerContents` now implements `TooltipProvider`
    - `SeededContainerLoot` now implements `TooltipProvider`
    - `TooltipDisplay` - A component that handles what should be hidden within an item's tooltip.
    - `TooltipProvider#addToTooltip` now takes in a `DataComponentGetter`
- `net.minecraft.world.item.enchantment.ItemEnchantments#showInTooltip` is removed
- `net.minecraft.world.item.equipment.trim.ArmorTrim#showInTooltip` is removed
- `net.minecraft.world.item.trading.ItemCost` now takes in a `DataComponentExactPredicate` instead of a `DataComponentPredicate`
- `net.minecraft.world.level.block.Block#appendHoverText` is removed
- `net.minecraft.world.level.block.entity`
    - `BannerPatternLayers` now implements `TooltipProvider`
    - `PotDecorations` now implements `TooltipProvider`
- `net.minecraft.world.level.saveddata.maps.MapId` now implements `TooltipProvider`

### Entities

Some `EntitySubPredicate`s for entity variants have been transformed into data components stored on the held item due to a recent change on `EntityPredicate` now taking in a `DataComponentExactPredicate` for matching the slots on an entity.

- `net.minecraft.advancements.critereon`
    - `EntityPredicate` now takes in a `DataComponentExactPredicate` to match the equipment slots checked
    - `EntitySubPredicate`
        - `AXOLTOL` -> `DataComponents#AXOLOTL_VARIANT`
        - `FOX` -> `DataComponents#FOX_VARIANT`
        - `MOOSHROOM` -> `DataComponents#MOOSHROOM_VARIANT`
        - `RABBIT` -> `DataComponents#RABBIT_VARIANT`
        - `HORSE` -> `DataComponents#HORSE_VARIANT`
        - `LLAMA` -> `DataComponents#LLAMA_VARIANT`
        - `VILLAGER` -> `DataComponents#VILLAGER_VARIANT`
        - `PARROT` -> `DataComponents#PARROT_VARIANT`
        - `SALMON` -> `DataComponents#SALMON_SIZE`
        - `TROPICAL_FISH` -> `DataComponents#TROPICAL_FISH_PATTERN`, `TROPICAL_FISH_BASE_COLOR`, `TROPICAL_FISH_PATTERN_COLOR`
        - `PAINTING` -> `DataComponents#PAINTING_VARIANT`
        - `CAT` -> `DataComponents#CAT_VARIANT`, `CAT_COLLAR`
        - `FROG` -> `DataComponents#FROG_VARIANT`
        - `WOLF` -> `DataComponents#WOLF_VARIANT`, `WOLF_COLLAR`
        - `PIG` -> `DataComponents#PIG_VARIANT`
        - `register` with variant subpredicates have been removed
        - `catVariant`, `frogVariant`, `wolfVariant` are removed
        - `$EntityHolderVariantPredicateType`, `$EntityVariantPredicateType` are removed
    - `SheepPredicate` no longer takes in the `DyeColor`
- `net.minecraft.client.renderer.entity.state.TropicalFishRenderState#variant` -> `pattern`
- `net.minecraft.core.component`
    - `DataComponentGetter` - A getter that obtains data components from some object.
    - `DataComponentHolder`, `DataComponentMap` now extends `DataComponentGetter`
    - `DataComponentExactPredicate` is now a predicate of a `DataComponentGetter`
        - `expect` - A predicate that expects a certain value for a data component.
        - `test(DataComponentHolder)` is removed
    - `DataComponents`
        - `SHEEP_COLOR` - The dye color of a sheep.
        - `SHULKER_COLOR` - The dye color of a shulker (box).
        - `COW_VARIANT` - The variant of a cow.
        - `CHICKEN_VARIANT` - The variant of a chicken.
        - `WOLF_SOUND_VARIANT` - The sounds played by a wolf. 
- `net.minecraft.world.entity`
    - `Entity` now implements `DataComponentGetter`
        - `applyImplicitComponents` - Applies the components from the getter onto the entity. This should be overriden by the modder.
        - `applyComponentsFromItemStack` - Applies the components from the stack onto the entity.
        - `castComponentValue` - Casts the type of the object to the component type.
        - `setComponent` - Sets the component data onto the entity.
        - `applyImplicitComponent` - Applies the component data to the entity. This should be overriden by the modder.
        - `applyImplicitComponentIfPresent` - Applies the component if it is present on the getter.
    - `EntityType#appendCustomNameConfig` -> `appendComponentsConfig`
    - `VariantHolder` interface is removed
        - As such, all `setVariant` methods on relevant entities are private while the associated data can also be obtained from the `DataComponentGetter`
- `net.minecraft.world.entity.animal`
    - `CatVariant#CODEC`
    - `Fox$Variant#STREAM_CODEC`
    - `FrogVariant#CODEC`
    - `MushroomCow$Variant#STREAM_CODEC`
    - `Parrot$Variant#STREAM_CODEC`
    - `Rabbit$Variant#STREAM_CODEC`
    - `Salmon$Variant#STREAM_CODEC`
    - `TropicalFish`
        - `getVariant` -> `getPattern`
        - `$Pattern` now implements `TooltipProvider`
    - `Wolf` -> `.wolf.Wolf`
    - `WolfVariant` -> `.wolf.WolfVariant`, now a record, taking in an `$AssetInfo` and a `SpawnPrioritySelectors`
    - `WolfVariants` -> `.wolf.WolfVariants`
- `net.minecraft.world.entity.animal.axolotl.Axolotl$Variant#STREAM_CODEC`
- `net.minecraft.world.entity.animal.horse`
    - `Llama$Variant#STREAM_CODEC`
    - `Variant#STREAM_CODEC`
- `net.minecraft.world.entity.animal.wolf`
    - `WolfSoundVariant` - The sounds played by a wolf.
    - `WolfSoundVariants` - All vanilla wolf sound variants.
- `net.minecraft.world.entity.decoration.Painting`
    - `VARIANT_MAP_CODEC` is removed
    - `VARIANT_CODEC` is now private
- `net.minecraft.world.entity.npc.VillagerDataHolder#getVariant`, `setVariant` are removed
- `net.minecraft.world.entity.variant.VariantUtils`- A utility for getting the variant info of an entity.
- `net.minecraft.world.item`
    - `ItemStack#copyFrom` - Copies the component from the getter.
    - `MobBucketItem#VARIANT_FIELD_CODEC` -> `TropicalFish$Pattern#STREAM_CODEC`
- `net.minecraft.world.level.block.entity.BlockEntity#applyImplicitComponents` now takes in a `DataComponentGetter`
    - `$DataComponentInput` -> `DataComponentGetter`
- `net.minecraft.world.level.Spawner` methods now takes in a `CustomData` instead of the `ItemStack` itself

#### Spawn Conditions

To allow entities to spawn variants randomly but within given conditions, a new registry called `SPAWN_CONDITION_TYPE` was added. These take in `SpawnCondition`s: a selector that acts like a predicate to take in the context to see whether the given variant can spawn there. All of the variants are thrown into a list and then ordered based on the selected priorty stored in the `SpawnProritySelectors`. Those with a higher priority will be checked first, with multiple of the same priority selected in the order they are provided. Then, all variants on the same priority level where a condition has been met is selected at random.

```json5
// For some object where there are spawn conditions
[
    {
        // The spawn condition being checked
        "condition": {
            "type": "minecraft:biome",
            // Will check that the biome the variant is attempting to spawn in is in the forest
            "biomes": "#minecraft:is_forest"
        },
        // Will check this condition first
        "priority": 1
    },
    {
        // States that the condition will always be true
        "priority": 0
    }
]
```

- `net.minecraft.core.registries.Registries#SPAWN_CONDITION_TYPE`
- `net.minecraft.world.entity.variant`
    - `BiomeCheck` - A spawn condition that checks whether the entity is in one of the given biomes.
    - `MoonBrightnessCheck` - A spawn condition that checks the brightness of the moon.
    - `PriorityProvider` - An interface which orders the condition selectors based on some priority integer.
    - `SpawnCondition` - Checks whether an entity can spawn at this location.
    - `SpawnConditions` - The available spawn conditions to choose from.
    - `SpawnContext` - An object holding the current position, level, and biome the entity is being spawned within.
    - `SpawnPrioritySelectors` - A list of spawn conditions to check against the entity. Used to select a random variant to spawn in a given location.
    - `StructureCheck` - A spawn condition that checks whether the entity is within a structure.

#### Variant Datapack Registries

Frog, cat, cow, chicken, pig, and wolf, and wolf sound variants are datapack registry objects, meaning that most references now need to be referred to through the `RegistryAccess` or `HolderLookup$Provider` instance.

For a frog, cat, or wolf:

```json5
// A file located at:
// - `data/examplemod/frog_variant/example_frog.json`
// - `data/examplemod/cat_variant/example_cat.json`
// - `data/examplemod/wolf_variant/example_wolf.json`
{
    // Points to a texture at `assets/examplemod/textures/entity/cat/example_cat.png`
    "asset_id": "examplemod:entity/cat/example_cat",
    "spawn_conditions": [
        // The conditions for this variant to spawn
        {
            "priority": 0
        }
    ]
}
```

For a pig, cow, or chicken:
```json5
// A file located at:
// - `data/examplemod/pig_variant/example_pig.json`
// - `data/examplemod/cow_variant/example_cow.json`
// - `data/examplemod/chicken_variant/example_chicken.json`
{
    // Points to a texture at `assets/examplemod/textures/entity/pig/example_pig.png`
    "asset_id": "examplemod:entity/pig/example_pig",
    // Defines the `PigVariant$ModelType` that's used to select what entity model to render the pig variant with
    "model": "cold",
    "spawn_conditions": [
        // The conditions for this variant to spawn
        {
            "priority": 0
        }
    ]
}
```

For a wolf sound variant:
```json5
// A file located at:
// - `data/examplemod/wolf_sound_variant/example_wolf_sound.json``
{
    // The registry name of the sound event to play randomly on idle
    "ambient_sound": "minecraft:entity.wolf.ambient",
    // The registry name of the sound event to play when killed
    "death_sound": "minecraft:entity.wolf.death",
    // The registry name of the sound event to play randomly when angry on idle
    "growl_sound": "minecraft:entity.wolf.growl",
    // The registry name of the sound event to play when hurt
    "hurt_sound": "minecraft:entity.wolf.hurt",
    // The registry name of the sound event to play randomly
    // 1/3 of the time on idle when health is max
    "pant_sound": "minecraft:entity.wolf.pant",
    // The registry name of the sound event to play randomly
    // 1/3 of the time on idle when health is below max
    "whine_sound": "minecraft:entity.wolf.whine"
}
```

#### Client Assets

Raw `ResourceLocation`s within client-facing files for identifiers or textures are being replaced with objects defining an idenfitier along with a potential texture path. There are three main objects to be aware of: `ClientAsset`, `ModelAndTexture`, and `MaterialAssetGroup`.

`ClientAsset` is an id/texture pair used to point to a texture location. By default, the texture path is contructed from the id, with the path prefixed with `textures` and suffixed with the PNG extension.

`ModelAndTexture` is a object/client asset pair used when a renderer should select between multiple models. Usually, the renderer creates a map of the object type to the model, and the object provided to the `ModelAndTexture` is used as a lookup into the map.

`MaterialAssetGroup` is a handler for rendering an equipment asset with some trim material. It takes in the base texture used to overlay onto the armor along with any overrides for a given equipment asset.

- `net.minecraft.advancements.DisplayInfo` now takes in a `ClientAsset` instead of only a `ResourceLocation` for the background texture
- `net.minecraft.client.model`
    - `AdultAndBabyModelPair` - Holds two `Model` instances that represents the adult and baby of some entity.
    - `ChickenModel#createBaseChickenModel` - Creates the default chicken model.
    - `ColdChickenModel` - A variant model for a chicken in cold temperatures.
    - `ColdCowModel` - A variant model for a cow in cold temperatures.
    - `ColdPigModel` - A variant model for a big in cold temperatures.
    - `CowModel#createBaseCowModel` - Creates the base model for a cow.
    - `PigModel#createBasePigModel` - Creates the default pig model.
    - `WarmCowModel` - A variant model for a cow in warm temperatures.
- `net.minecraft.client.renderer.entity`
    - `ChickenRenderer` now extends `MobRenderer` instead of `AgeableMobRenderer`
    - `CowRenderer` now extends `MobRenderer` instead of `AgeableMobRenderer`
    - `PigRenderer` now extends `MobRenderer` instead of `AgeableMobRenderer`
- `net.minecraft.client.renderer.entity.layers.SheepWoolUndercoatLayer` - A layer that renders the wool undercoat of a sheep.
- `net.minecraft.client.renderer.entity.state`
    - `CowRenderState` - A render state for a cow entity.
    - `SheepRenderState`
        - `getWoolColor` - Returns the integer color of the sheep wool.
        - `isJebSheep` - Returns whether the sheep's name contains the `jeb_` prefix.
- `net.minecraft.core.ClientAsset` - An object that holds an identifier and a path to some texture.
- `net.minecraft.data.loot.EntityLootSubProvider#killedByFrogVariant` now takes in a `HolderGetter` for the `FrogVariant`
- `net.minecraft.data.tags.CatVariantTagsProvider` class is removed
- `net.minecraft.tags.CatVariantTags` class is removed
- `net.minecraft.world.entity.animal`
    - `AbstractCow` - An abstract animal that represents a cow.
    - `Chicken#setVariant`, `getVariant` - Handles the variant information of the chicken.
    - `ChickenVariant` - A class which defines the common-sideable rendering information and biome spawns of a given chicken.
    - `ChickenVariants` - Holds the keys for all vanilla chicken variants.
    - `Cow` now extends `AbstractCow`.
    - `CowVariant` - A class which defines the common-sideable rendering information and biome spawns of a given cow.
    - `CowVariants` - Holds the keys for all vanilla cow variants.
    - `CatVariant(ResourceLocation)` -> `CatVariant(ClientAsset, SpawnPrioritySelectors)`
    - `CatVariants` - Holds the keys for all vanilla cat variants.
    - `FrogVariant` -> `.frog.FrogVariant`
        - `FrogVariant(ResourceLocation)` -> `FrogVariant(ClientAsset, SpawnPrioritySelectors)`
    - `MushroomCow` now extends `AbstractCow`
    - `PigVariant` - A class which defines the common-sideable rendering information and biome spawns of a given pig.
    - `TemperatureVariants` - An interface which holds the `ResourceLocation`s that indicate an entity within a different temperature.
- `net.minecraft.world.entity.variant.ModelAndTexture` - Defines a model with its associated texture.
- `net.minecraft.world.item.equipment.trim`
    - `MaterialAssetGroup` - An asset defines some base and the permutations based on the equipment worn.
    - `TrimMaterial` now takes in a `MaterialAssetGroup` instead of the raw base and overrides

## Tags and Parsing

Tags have received a rewrite, removing any direct references to types while also sealing and finalizing related classes. Getting a value from the tag now returns an `Optional`-wrapped entry, unless you call one of the `get*Or`, where you specify the default value. Objects, on the other hand, do not take in a default, instead returning an empty variant of the desired tag.

```java
// For some `CompoundTag` tag

// Read a value
Optional<Integer> value1 = tag.getInt("value1");
int value1Raw = tag.getIntOr("value1", 0);

// Reading another object
Optional<CompoundTag> childTag = tag.getCompound("childTag");
CompoundTag childTagRaw = tag.getCopmoundOrEmpty("childTag");
```

### Writing with Codecs

`CompoundTag`s now have methods to write and read using a `Codec` or `MapCodec`. For a `Codec`, it will store the serialized data inside the key specified. For a `MapCodec`, it will merge the fields onto the top level tag.

```java
// For some Codec<ExampleObject> CODEC and MapCodec<ExampleObject> MAP_CODEC
// We will also have ExampleObject example
CompoundTag tag = new CompoundTag();

// For a codec
tag.store("example_key", CODEC, example);
Optional<ExampleObject> fromCodec = tag.read("example_key", CODEC);

// For a map codec
tag.store(MAP_CODEC, example);
Optional<ExampleObject> fromMapCodec = tag.read(MAP_CODEC);
```

### Command Parsers

The packrat parser has been updated with new rules and systems, allowing commands to have parser-based arguments. This comes from the `CommandArgumentParser`, which parses some grammar to return the desired object. The parser is then consumed by the `ParserBasedArgument`, where it attempts to parse a string and build any suggestions based on what you're currently typing. These are both handled through the `Grammar` class, which implements `CommandArgumentParser`, constructed using a combination of atoms, dictionaries, rules, and terms.

- `net.minecraft.commands.ParserUtils#parseJson`
- `net.minecraft.commands.arguments`
    - `ComponentArgument` now extends `ParserBasedArgument`
    - `NbtTagArgument` now extends `ParserBasedArgument`
    - `StyleArgument` now extends `ParserBasedArgument`
- `net.minecraft.commands.arguments.item.ItemPredicateArgument` now extends `ParserBasedArgument`
- `net.minecraft.nbt`
    - `ByteArrayTag`, now final, no longer takes in a list object
    - `ByteTag` is now a record
    - `CollectionTag` is now a sealed interface, no longer extending `AbstractList` or has a generic
        - `set`, `add` is removed
        - `remove` now returns a `Tag`
        - `get` - Returns the tag at the specified index.
        - `getElementType` is removed
        - `size` - Returns the size of the collection.
        - `isEmpty` - Returns whether the collection has no elements.
        - `stream` - Streams the elements of the collection.
    - `CompoundTag` is now final
        - `store` - Writes a codec or map codec to the tag.
        - `read` - Reads the codec or map codec-encoded value from the tag.
        - `getFloatOrDefault`, `getIntOrDefault`, `getLongOrDefault` - Gets the value with the associated key, or the default if not present or an exception is thrown.
        - `storeNullable` - When not null, uses the codec to write the value to the tag.
        - `putUUID`, `getUUID`, `hasUUID` is removed
        - `getAllKeys` -> `keySet`
        - `values`, `forEach` - Implements the standard map operations.
        - `putByteArray`, `putIntArray` with list objects are removed
        - `getTagType` is removed
        - `contains` is removed
        - `get*`, `get*Or` - Returns an optional wrapped object for the key, or the default value specified is using the `Or` methods.
    - `DoubleTag` is now a record
    - `EndTag` is now a record
    - `FloatTag` is now a record
    - `IntArrayTag`, now final, no longer takes in a list object
    - `IntTag` is now a record
    - `ListTag`, now final, extends `AbstractList`
        - `addAndUnwrap` - Adds the tag to the list where, if a compound with one element, adds the inner tag instead.
        - `get*`, `get*Or` - Returns an optional wrapped object for the key, or the default value specified is using the `Or` methods.
        - `compoundStream` - Returns a flat map of all `CompoundTag`s within the list.
    - `LongArrayTag`, now final, no longer takes in a list object
    - `LongTag` is now a record
    - `NbtIo#readUnnamedTag` is now public, visible for testing
    - `NbtOps` now has a private constructor
    - `NbtUtils`
        - `getDataVersion` now has an overload that takes in a `Dynamic`
        - `createUUID`, `loadUUID` is removed
        - `readBlockPos`, `writeBlockPos` is removed
    - `NumericTag` is now a sealed interface that implements `PrimitiveTag`
        - `getAsLong` -> `longValue`
        - `getAsInt` -> `intValue`
        - `getAsShort` -> `shortValue`
        - `getAsByte` -> `byteValue`
        - `getAsDouble` -> `doubleValue`
        - `getAsFloat` -> `floatValue`
        - `getAsNumber` -> `box`
        - `as*` - Returns an optional wrapped of the numeric value.
    - `PrimitiveTag` - A sealed interface that represents the tag data as being a primitive object.
    - `ShortTag` is now a record
    - `SnbtGrammar` - A parser creater for stringified NBTs.
    - `SnbtOperations` - A helper that contains the built in operations for parsing some value.
    - `StringTag` is now a record
    - `StringTagVisitor`
        - `visit` -> `build`, not one-to-one
        - `handleEscape` -> `handleKeyEscape`, now private
    - `Tag` is now a sealed interface
        - `as*` -> Attempts to cast the tag as one of its subtypes, returning an empty optional on failure.
        - `getAsString` -> `asString`, not one-to-one
    - `TagParser` now holds a generic referncing the type of the intermediate object to parse to
        - The constructor now takes in a grammar, or `create` constructs the grammar from a `DynamicOps`
        - `AS_CODEC` -> `FLATTENED_CODEC`
        - `parseTag` -> `parseCompoundFully` or `parseCompoundAsArgument`
            - Additional methods such as `parseFully`, `parseAsArgument` parse to some intermediary object
            - These are all instance methods
        - `readKey`, `readTypedValue` is removed
    - `TagType#isValue` is removed
- `net.minecraft.util.parsing.packrat`
    - `CachedParseState` - A parse state that caches the parsed positions and controls when reading.
    - `Control#hasCut` - Returns whether the control flow for the grammar has a cut for the reading object.
    - `DelayedException` - An interface that creates an exception to throw.
    - `Dictionary`
        - `put` now returns a `NamedRule`
        - `put(Atom<T>, Term<S>, Rule.RuleAction<S, T>)` -> `putComplex`
        - `get` -> `getOtThrow`, not one-to-one
        - `forward` - Gets or writes the term to the dictionary.
        - `namedWithAlias` - Creates a new reference to the named atom or its alias.
    - `ErrorCollector$Nop` - A error collector that does nothing.
    - `NamedRule` - A rule that has an associated name.
    - `ParseState` is now an interface
        - Caching logic has moved to `CachedParseState`
        - `scope` - Returns the current scope being analyzed within the parsing object.
        - `parse` now takes in a `NamedRule` instead of an `Atom`
        - `acquireControl`, `releaseControl` - Handles obtaining the `Control` used during parsing.
        - `silent` - Returns a `ParseState` that does not collect any errors.
    - `Rule`
        - `parse`, `$RuleAction#run` now returns a nullable value rather than an optional
        - `SimpleRuleAction` now implements `$RuleAction`
    - `Scope#pushFrame`, `popFrame`, `splitFrame`, `clearFrameValues`, `mergeFrame` - Handles the management of parsing terms into sections called frames.
    - `Term`
        - `named` -> `Dictionary#named`, not one-to-one
        - `repeated`, `repeatedWithTrailingSeparator`, `repeatedWithoutTrailingSeparator` - Handles terms similar to varargs that are repeated and sticks them into a list.
        - `positiveLookahead`, `negativeLookahead` - Handles a term that matches information based on what is following.
        - `fail` - Mark a term as having failed during parsing.
- `net.minecraft.util.parsing.packrat.commands`
    - `CommandArgumentParser` - Parses a string into an argument for use with a command.
    - `Grammar` now takes in a `NamedRule` for the top rather than an `Atom`
    - `GreedyPatternParseRule` - A rule that attempts to match the provided pattern greedily, assuming that if a region matches, that the matched group can be obtained.
    - `GreedyPredicateParseRule` - A rule that attempts to match the accepted characters greedily, making sure that the string reaches a minimum size.
    - `NumberRunParseRule` - A rule that attempts to parse a number from the string.
    - `ParserBasedArgument` - A command argument that uses a parser to extract the value.
    - `ResourceLookupRule` now takes in a `NamedRule` for the id parser rather than an `Atom`
    - `StringReaderParserState` now extends `CachedParsedState`
        - The `Dictoionary` is no longer taken in
    - `StringReaderTerms#characters` - Matches multiple characters in a string, usually for catching both the lowercase and uppercase variant.
    - `UnquotedStringParseRule` - A rule that reads part of the sequence as an unquoted string, making sure it reaches a minimum size.

## Saved Data, now with Types

`SavedData` has been reworked to abstract most of its save and loading logic into a separate `SavedDataType`. This means that the `save` override and additional `load` and `factory` methods are now handled within the `SavedDataType` itself.

To construct a `SavedDataType`, you need to pass in four paramters. First is the string identifier, used to resolve the `.dat` file holding your information. This must be a vaild path. Then there is the constructor, which takes in the `SavedData$Context` to return an instance of your data object when no information is present. Following that is the codec, which takes in the `SavedData$Context` and returns a `Codec` to read and write your saved data. Finally, there is the `DataFixTypes` used for data fixers. As this is a static enum, you will either need to inject into the enum itself, if you plan on using vanilla data fixers, or patch out the `update` call within `DimensionDataStorage#readTagFromDisk` to pass in a null value.

```java
// Our saved data instance
public class ExampleSavedData extends SavedData {

    // The saved data type
    public static final SavedDataType<ExampleSavedData> TYPE = new SavedDataType<>(
        // Best to preface the identifier with your mod id followed by an underscore
        // Slashes will throw an error as the folders are not present
        // Will resolve to `saves/<world_name>/data/examplemod_example.dat`
        "examplemod_example",
        // Constructor for the new instance
        ExampleSavedData::new,
        // Codec factory to encode and decode the data
        ctx -> RecordCodecBuilder.create(instance -> instance.group(
            RecordCodecBuilder.point(ctx.levelOrThrow()),
            Codec.INT.fieldOf("value1").forGetter(data -> data.value1),
            Codec.BOOL.fieldOf("value2").forGetter(data -> data.value2)
        ).apply(instance, ExampleSavedData::new));
    );

    private final ServerLevel level;
    private final int value1;
    private final boolean value2;


    // For the new instance
    private ExampleSavedData(ServerLevel.Context ctx) {
        this(ctx.levelOrThrow(), 0, false);
    }

    // For the codec
    // The constructors don't need to be public if not using `DimensionDataStorage#set`
    private ExampleSavedData(ServerLevel level, int value1, boolean value2) {
        this.level = level;
        this.value1 = value1;
        this.value2 = value2;
    }

    // Other methods here
}

// With access to the DimensionDataStorage storage
ExampleSavedData data = storage.computeIfAbsent(ExampleSavedData.TYPE);
```

- `net.minecraft.server.ServerScoreboard`
    - `dataFactory` is removed
    - `createData` now takes in a `$Packed` instance
- `net.minecraft.world.RandomSequences`
    - `factory`, `load` is removed
    - `codec` - Constructs a codec for the random sequence given the current world seed.
- `net.minecraft.world.entity.raid.Raids` no longer takes in anything
    - `getType` - Returns the saved data type based on the current dimension.
    - `factory` is removed
    - `tick` now takes in the `ServerLevel`
    - `getId` - Gets the identifier for the raid instance.
    - `canJoinRaid` no longer takes in the raid instance
    - `load` no longer takes in the `ServerLevel`
- `net.minecraft.world.level.levelgen.structure.structures.StructureFeatureIndexSavedData`
    - `factory`, `load` is removed
    - `type` - Returns the feature saved data type with its specified id.
- `net.minecraft.world.level.saveddata`
    - `SavedData`
        - `save` is removed
        - `$Factory` record is removed
        - `$Context` - Holds the current context that the saved data is being written to.
    - `SavedDataType` - A record that represents the type of the saved data, including information on how to construct, save, and load the data.
- `net.minecraft.world.level.saveddata.maps`
    - `MapIndex` now has a constructor to take in the last map id
        - `factory`, `load` is removed
        - `getFreeAuxValueForMap` -> `getNextMapId`
    - `MapItemSavedData`
        - `factory`, `load` is removed
        - `type` - Returns the saved data type using the map id's key.
- `net.minecraft.world.level.storage.DimensionDataStorage` now takes in a `SavedData$Context`
    - `computeIfAbsent`, `get` now take in only the `SavedDataType`
    - `set` now takes in the `SavedDataType` along with the data instance
- `net.minecraft.world.scores.ScoreboardSaveData`
    - `load` -> `loadFrom`
    - `pack` - Packs the data into its saved data format.
    - `$Packed` - Represents the serializable packed data.

## Render Pipeline Rework

Rendering an object to the screen, whether through a shader or a `RenderType`, has been fully or partially reworked, depending on what systems you were using previously. As such, a lot of things need to be reexplained, which a more in-depth look will be below. However, for the people who don't care, here's the TL;DR.

First, shader JSONs no longer exist. This is replaced by a `RenderPipeline`, which is effectively an in-code substitute. Second, the `RenderPipeline`s forcibly make most abtrarily values into objects. For example, instead of storing the blend function mode id, you store a `BlendFunction` object. Similarly, you no longer store or setup the direct texture objects, but instead manage it through a `GpuTexture`. Finally, the `VertexBuffer` can either draw to the framebuffer by directly passing in the `RenderPipeline`, updating any necessary uniforms in the consumer, or by passing in the `RenderType`.

Now, for those who need the details, let's jump into them.

### Abstracting Open GL

As many are aware, Minecraft has been abstracting away their OpenGL calls and constants, and this release is no different. All of the calls to GL codes, except `BufferUsage`, have been moved out of object references, to be obtained typically by calling `GlConst$toGl`. However, with all of the other rendering reworks, there are numerous changes and complexities that require learning an entirely new system, assuming you're not using `RenderType`s.

Starting from the top, all calls to the underlying render system goes through `GpuDevice`, an interface that acts like a general implementation of a render library like OpenGL or Vulkan. The device is responsible for creating buffers and textures, executing whatever commands are desired. Getting the current `GpuDevice` can be accessed through the `RenderSystem` via `getDevice` like so:

```java
GpuDevice device = RenderSystem.getDevice();
```

The `GpuDevice` can the create either buffers with the desired data or a texture containing information on what to render using `createBuffer` and `createTexture`, respectively. Just for redundancy, buffers hold the vertex data while textures hold the texture (color and depth) data. You should generally cache the buffer or texture object for later use with any additional data updated as needed. For reference, buffers are typically created by using a `BufferBuilder` with a `ByteBufferBuilder` to build the `MeshData` first, before passing that into `createBuffer`.

With the desired buffers and textures set up, how do we actually modify render them to the screen? Well, this is handled through the `CommandEncoder`, which can also be optained from the device via `GpuDevice#createCommandEncoder`. The encoder contain the familiar read and write methods along with a few extra to clear texture to a given color or simply blit the the texture immediately to the screen (`presentTexture`). However, the most important method here is `createRenderPass`. This takes in the `GpuTexture` to draw to the screen along with a default ARGB color for the background. Additionally, it can take in a depth texture as well. This should be created using a try with resources block like so:

```java
// We will assume you have constructed a `GpuTexture` texture for the color data
try (RenderPass pass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(this.texture, OptionalInt.of(0xFFFFFFFF))) {
    // Setup things here

}
```

Within the `RenderPass`, you can set the `RenderPipeline` to use, which defines the associated shaders, bind any samplers from other targets or set uniforms, scissor a part of a screen to render, and set the vertex and index buffers used to define the vertices to render. Finally, everything can be drawn to the screen using one of the `draw` methods, providing the starting index and the vertex count.

```java
// If the buffers/textures are not created or cached, create them here
// Any methods ran from `CommandEncoder` cannot be run while a render pass is open
RenderSystem.AutoStorageIndexBuffer indices = RenderSystem.getSequentialBuffer(VertexFormat.Mode.QUADS);
GpuBuffer vertexBuffer = RenderSystem.getQuadVertexBuffer();
GpuBuffer indexBuffer = indices.getBuffer(6);

// We will assume you have constructed a `GpuTexture` texture for the color data
try (RenderPass pass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(this.texture, OptionalInt.of(0xFFFFFFFF))) {

    // Set pipeline information along with any samplers and uniforms
    pass.setPipeline(EXAMPLE_PIPELINE);
    pass.setVertexBuffer(0, vertexBuffer);
    pass.setIndexBuffer(indexBuffer, indices.type());
    pass.bindSampler("Sampler0", RenderSystem.getShaderTexture(0));

    // Then, draw everything to the screen
    // In this example, the buffer just contains a single quad
    // For those unaware, the vertex count is 6 as a quad is made up of 2 triangles, so 2 vertices overlap
    pass.drawIndexed(0, 6);
}
```

However, unless you need such fine control, it is recommended to use a `RenderType` with the `MultiBufferSource` when necessary as that sets up most things for you.

### Object References

Most raw references to GL codes used for determining the mode and handling the texture have been replaced with objects. As the TL;DR previously mentioned, these are stored typically as some sort of enum or object that can then be resolved to their GL counterparts. Some objects contain their reference identifier directly, like `BlendFunction`. Others are simply placeholders that are resolved in their appropriate location, like `DepthTestFunction` whose enum values are converted via `RenderPipeline#toGl`.

However, the biggest change is the addition of the `GpuTexture`. This is responsible for managing anything to do with creating, writing, and releasing a texture written to some buffer. At initialization, the texture is created and bound, with any necessary parameters set for mipmaps and texture formats. These `GpuTexture`s are stored and referenced everywhere, from the depth and color targets for a `RenderTarget` to the texture backing a `TextureAtlas`. Then, once no longer need, the texture is released by calling `#close`. Note that although technically `#bind` can be called again, the texture is already considered deleted and should not be used.

If, for some reason, you need to use a `GpuTexture`, it's actually quite simple to use. First, you just construct the `GpuTexture` via `GpuDevice#createTexture`. Then, if you need to change any of the addressing or texture mipmap filters, you can apply them whenever before writing.

```java
public class MyTextureManager {
    
    private final GpuTexture texture;

    public MyTextureManager() {
        this.texture = RenderSystem.getDevice().createTexture(
            // The texture name, used for logging and debugging
            "Example Texture",
            // The format of the texture pixels, can be one of three values that
            // Values:   (texture internal format, texel data format,  texel data type,  pixel size)
            // - RGBA8   (GL_RGBA8,                GL_RGBA,            GL_UNSIGNED_BYTE, 4)
            // - RED8    (GL_R8,                   GL_RED,             GL_UNSIGNED_BYTE, 1)
            // - DEPTH32 (GL_DEPTH_COMPONENT32,    GL_DEPTH_COMPONENT, GL_FLOAT,         4)
            TextureFormat.RGBA8,
            // Width of the texture
            16,
            // Height of the texture
            16,
            // The mipmap level and maximum level-of-detail (minimum of 1)
            1
        );

        // Set the texture mode for the UV component
        // Values:
        // - REPEAT        (GL_REPEAT)
        // - CLAMP_TO_EDGE (GL_CLAMP_TO_EDGE)
        this.texture.setAddressMode(
            // The mode to use for the U component (GL_TEXTURE_WRAP_S)
            AddressMode.CLAMP_TO_EDGE,
            // The mode to use for the V component (GL_TEXTURE_WRAP_R)
            AddressMode.REPEAT
        );

        // Sets the filter functions used for scaling the texture on the screen
        // Values    (default,    for mipmaps):
        // - NEAREST (GL_NEAREST, GL_NEAREST_MIPMAP_LINEAR)
        // - LINEAR  (GL_LINEAR,  GL_LINEAR_MIPMAP_LINEAR)
        this.texture.setTextureFilter(
            // The mode to use for the texture minifying function (GL_TEXTURE_MIN_FILTER)
            FilterMode.LINEAR,
            // The mode to use for the texture magnifying function (GL_TEXTURE_MAG_FILTER)
            FilterMode.NEAREST,
            // Whether mipmaps should be used for the minifying function (should have a higher mipmap level than 1 when true)
            false
        );
    }
}
```

Then, whenever you want to upload something to the texture, you call `CommandEncoder#writeToTexture` or `CommandEncoder#copyTextureToTexture`. This either takes in the `NativeImage` to write from or an `IntBuffer` with the texture data and a `NativeImage$Format` to use.

```java
// Like other buffer/texture modification methods, this must be done outside of a render pass
// We will assume you have some `NativeImage` image to load into the texture
RenderSystem.getDevice().createCommandEncoder().writeToTexture(
    // The texture (destination) being written to
    this.texture,
    // The image (source) being read from
    image,
    // The mipmap level
    0,
    // The starting destination x offset
    0,
    // The starting destination y offset
    0,
    // The destination width (x size)
    16,
    // The desintation height (y size)
    16,
    // The starting source x offset
    0,
    // The starting source y offset
    0
)
```

Finally, once you're done with the texture, don't forget to release it via `#close` if it's not already handled for you.

### Render Pipelines

Previously, a pipeline was constructed using a JSON that contained all metadata from the vertex and fragement shader to their defined values, samplers, and uniforms. However, this has all been replaced with an in-code solution that more localizes some parts of the JSON and some parts that were relegated to the `RenderType`. This is known as a `RenderPipeline`.

A `RenderPipeline` can be constructed using its builder via `RenderPipeline#builder`. A pipeline can then be built by calling `build`. If you want the shader to be pre-compiled without any additional work, the final pipeline can be passed to `RenderPipeline#register`. However, you can also handle the compilation yourself if more graceful fail states are desired. If you have snippets that are used across multiple pipelines, then a partial pipeline can be built via `$Builder#buildSnippet` and then passed to the constructing pipelines in the `builder` method.

> The following enums described in the examples have their GL codes provided with them as they have been abstracted away.

```java
// This assumes that RenderPipeline#register has been made public through some form
public static final RenderPipeline EXAMPLE_PIPELINE = RenderPipelines.register(
    RenderPipeline.builder()
    // The name of the pipeline (required)
    .withLocation(ResourceLocation.fromNamespaceAndPath("examplemod", "pipeline/example"))
    // The location of the vertex shader, relative to 'shaders' (required)
    // Points to 'assets/examplemod/shaders/example.vsh'
    .withVertexShader(ResourceLocation.fromNamespaceAndPath("examplemod", "example"))
    // The location of the fragment shader, relative to 'shaders' (required)
    // Points to 'assets/examplemod/shaders/example.fsh'
    .withFragmentShader(ResourceLocation.fromNamespaceAndPath("examplemod", "example"))
    // The format of the vertices within the shader (required)
    .withVertexFormat(
        // The vertex format
        DefaultVertexFormat.POSITION_TEX_COLOR,
        // The mode of the format
        VertexFormat.Mode.QUADS
    )
    // Adds constants that can be referenced within the shaders
    // Can specify a name in addition to an int / float to represent its value
    // If no value is specified, then it should be gated with a #ifdef / #endif block
    .withShaderDefines("ALPHA_CUTOUT", 0.5)
    // Adds the texture sampler2Ds that can be referenced within the shaders
    // Typically, the shader textures stored in the `RenderSystem` is referenced via `Sampler0` - `Sampler11`
    // - `Sampler0` is usually always present, but these should be set up beforehand
    // Additionally, for render targets, `InSampler` is typically present, along with any defined in a postpass
    .withSampler("Sampler0")
    // Adds uniforms that can be referenced within the shaders
    // These are just definitions which are then populated by default or by the caller depending on the scenario
    // Defaults can be found in `CompiledShaderProgram#setupUniforms`
    .withUniform("ModelOffset", UniformType.VEC3)
    // Custom uniforms must be set manually as the vanilla batching system does not support such an operation
    .withUniform("CustomUniform", UniformType.INT)
    // Sets the depth test functions used rendering objects at varying distances from the camera
    // Values:
    // - NO_DEPTH_TEST      (GL_ALWAYS)
    // - EQUAL_DEPTH_TEST   (GL_EQUAL)
    // - LEQUAL_DEPTH_TEST  (GL_LEQUAL)
    // - LESS_DEPTH_TEST    (GL_LESS)
    // - GREATER_DEPTH_TEST (GL_GREATER)
    .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
    // Sets how the polygons should render
    // Values:
    // - FILL      (GL_FILL)
    // - WIREFRAME (GL_LINE)
    .withPolygonMode(PolygonMode.FILL)
    // When true, can cull front or back-facing polygons
    .withCull(false)
    // Specifies the functions to use when blending two colors with alphas together
    // Made up of the `GlStateManager$SourceFactor` and `GlStateManager$DestFactor`
    // First two are for RGB, the last two are for alphas
    // If nothing is specified, then blending is disabled
    .withBlend(BlendFunction.TRANSLUCENT)
    // Determines whether to mask writing colors and alpha to the draw buffer
    .withColorWrite(
        // Mask RGB
        false,
        // Mask alpha
        false
    )
    // Determines whether to mask writing values to the depth buffer
    .withDepthWrite(false)
    // Determines the logical operation to apply when applying an RGBA color to the framebuffer
    .withColorLogic(LogicOp.NONE)
    // Sets the scale and units used to calculate the depth values for the polygon.
    // This takes the place of the polygon offset.
    .withDepthBias(0f, 0f)
    .build()
);
```

From there, the pipeline can either be used directly or through some `RenderType`:

```java
// This will assume that RenderType#create is made public
public static final RenderType EXAMPLE_RENDER_TYPE = RenderType.create(
    // The name of the render type
    "examplemod:example",
    // The size of the buffer
    // Or 4MB
    4194304,
    // Whether it effects crumbling that is applied to block entities
    false,
    // Whether the vertices should be sorted before upload
    true,
    // The pipeline to use
    EXAMPLE_PIPIELINE,
    // Any additional composite state settings to apply
    RenderType.CompositeState.builder().createCompositeState(RenderType.OutlineProperty.NONE)
);
```

The pipeline can then be drawn by creating the `RenderPass` and setting the `RenderPipeline` to use your pipeline. As for the `RenderType`, the associated buffer can be obtained using  `MultiBufferSource#getBuffer`. Note that custom uniforms should not be used within `RenderType`s as they cannot be set easily.

```java
// Since we are using a custom uniform, we must handle it ourselves
// We will assume we have some `GpuTexture` texture to write to

// Create the render pass to use
try (RenderPass pass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(
        // The GPU color texture to write to
        this.texture,
        // The clear color in ARGB format
        OptionalInt.of(0xFFFFFFFF),
        // The depth texture and the clear depth value can also be constructed here
    )
) {
    // Add the pipeline and our uniform
    pass.setPipeline(EXAMPLE_PIPELINE);
    pass.setUniform("CustomUniform", 1);
    
    // Set any additional sampler and the vertex/index buffers to use

    // Finally, call one of the draw functions
    // Takes in the first index and the index count to draw for the vertices
    pass.draw(...);
}
```

### Post Effects

Given that the pipeline JSONs have been stripped, this also effects the post effects. The `program` is replaced with directly specifying the `vertex_shader` and the `fragment_shader`. Additionally, uniforms must specify their `type`.

```json5
// Before 1.21.5 (for some pass in 'passes')
{
    // Same as before
    "inputs": [ /*...*/ ],
    "output": "swap",

    // Replaced by 'vertex_shader', 'fragement_shader'
    "program": "minecraft:post/box_blur",

    "uniforms": [
        {
            "name": "BlurDir",
            // Required
            "values": [ 1.0, 0.0 ]
        },
        {
            "name": "Radius",
            // Required
            "values": [ 0.0 ]
        }
    ]
}

// 1.21.1 (for some pass in 'passes')
{
    // Same as before
    "inputs": [ /*...*/ ],
    "output": "swap",

    // Relative to 'shaders'
    // Points to 'assets/minecraft/shaders/post/blur.vsh'
    "vertex_shader": "minecraft:post/blur",
    // Points to 'assets/minecraft/shaders/post/box_blur.fsh'
    "fragment_shader": "minecraft:post/box_blur",


    "uniforms": [
        {
            "name": "BlurDir",
            // Specifies the type to use for this uniform
            // One of `Uniform$Type`:
            // - int
            // - ivec3
            // - float
            // - vec2
            // - vec3
            // - vec4
            // - matrix4x4
            "type": "vec2",
            "values": [ 1.0, 0.0 ]
        },
        {
            "name": "Radius",
            "type": "float"
            // Values are no longer required
        }
    ]
}
```

Note that if you do not define a value for a uniform, they still must be specified before processing the `PostChain` by calling `#setUniform` within the `RenderPass` consumer of `PostChain#process`.

```java
// Assume we already got the `PostChain` post
post.process(Minecraft.getInstance().getMainRenderTarget(), GraphicsResourceAllocator.UNPOOLED, pass -> {
    pass.setUniform("Radius", 0.4f);
});
```

- `com.mojang.blaze3d.GpuOutOfMemoryException` - An exception thrown when a texture could not be allocated on the GPU.
- `com.mojang.blaze3d.buffers`
    - `BufferType` no longer stores the GL codes, now in `GlConst#toGl`
    - `BufferUsage` no longer stores the GL codes, now in `GlConst#toGl`
        - `isReadable`, `isWritable` - Returns whether the buffer can be read from or written to.
    - `GpuBuffer` is now abstract
        - Constructor with `ByteBuffer` is removed
        - `size` - Returns the size of the buffer.
        - `type` -  Returns the type of the buffer.
        - `resize`, `write`, `read`, `bind` is removed
        - `usage` - Returns the usage of the buffer.
        - `close` is now abstract
        - `isClosed` - Returns whether the buffer has been closed.
        - `$ReadView` is now an interface that defines the buffer data and how to close the view
- `com.mojang.blaze3d.font.SheetGlyphInfo#upload` now takes in a `GpuTexture`
- `com.mojang.blaze3d.opengl`
    - `DirectStateAccess` - An interface that creates and binds data to some framebuffer.
        - `$Core` - An implementation of DSA that modifies the framebuffer without binding them to the context.
        - `$Emulated` - An abstraction over DSA that still binds the context.
    - `GlBuffer` - An implementation of the `GpuBuffer` for OpenGL.
    - `GlCommandEncoder` - An implementation of the `CommandEncoder` for OpenGL.
    - `GlDebugLabel` - A labeler for handling debug references to GL-specified data structures.
    - `GlDevice` - An implementation of `GpuDevice` for OpenGL.
    - `GlRenderPass` - An implementation of `RenderPass` for OpenGL.
    - `GlRenderPipeline` - An implementation of `CompiledRenderPipeline` for OpenGL.
    - `GlTexture` - An implementation of `GpuTexture` for OpenGL.
    - `VertexArrayCache` - A cache for binding and uploading a vertex array to the OpenGL pipeline.
- `com.mojang.blaze3d.pipeline`
    - `BlendFunction` - A class that holds the source and destination colors and alphas to apply when overlaying pixels in a target. This also holds all vanilla blend functions.
    - `CompiledRenderPipeline` - An interface that holds the pipeline with all necessary information to render to the screen.
    - `RenderPipeline` - A class that contains everything required to render some object to the screen. It acts similarly to a render state before being applied.
    - `RenderTarget` now takes in a string representing the name of the target
        - `colorTextureId` -> `colorTexture`, now a `GpuTexture`
            - Same with `getColorTextureId` -> `getColorTexture`
        - `depthBufferId` -> `depthTexture`, now a `GpuTexture`
            - Same with `getDepthTextureId` -> `getDepthTexture`
        - `filterMode` is now a `FilterMode`
            - Same with `setFilterMode` for the int parameter
        - `blitAndBlendToScreen` no longer takes in the viewport size parameters
        - `framebufferId` is removed
        - `checkStatus` is removed
        - `bindWrite`, `unbindWrite`, `setClearColor` is removed
        - `blitToScreen` no longer takes in any parameters
        - `blitAndBlendToScreen` -> `blitAndBlendToTexture`, not one-to-one
        - `clear` is removed
        - `unbindRead` is removed
- `com.mojang.blaze3d.platform`
    - `DepthTestFunction` - An enum representing the supported depth tests to apply when rendering a sample to the framebuffer.
    - `DisplayData` is now a record
        - `withSize` - Creates a new instance with the specified width/height.
        - `withFullscreen` - Creates a new instance with the specified fullscreen flag.
    - `FramerateLimitTracker`
        - `getThrottleReason` - Returns the reason that the framerate of the game was throttled.
        - `isHeavilyThrottled` - Returns whether the current throttle significantly impacts the game speed.
        - `$FramerateThrottleReason` - The reason the framerate is throttled.
    - `GlConst` -> `com.mojang.blaze3d.opengl.GlConst`
        - `#toGl` - Maps some reference object to its associated OpenGL code.
    - `GlDebug` -> `com.mojang.blaze3d.opengl.GlDebug`
        - `enableDebugCallback` now takes in a set of the enabled extensions.
    - `GlStateManager` -> `com.mojang.blaze3d.opengl.GlStateManager`
        - `_blendFunc`, `_blendEquation` is removed
        - `_glUniform2(int, IntBuffer)`, `_glUniform4(int, IntBuffer)` is removed
        - `_glUniformMatrix2(int, boolean, FloatBuffer)`, `_glUniformMatrix3(int, boolean, FloatBuffer)` is removed
        - `_glUniformMatrix4(int, boolean, FloatBuffer)` -> `_glUniformMatrix4(int, FloatBuffer)`, transpose is now always false
        - `_glGetAttribLocation` is removed
        - `_glMapBuffer` is removed
        - `_glCopyTexSubImage2D` is removed
        - `_glBindRenderbuffer`, `_glDeleteRenderbuffers` is removed
        - `glGenRenderbuffers`, `_glRenderbufferStorage`, `_glFramebufferRenderbuffer` is removed
        - `_texParameter(int, int, float)` is removed
        - `_genTextures`, `_deleteTextures` is removed
        - `_texSubImage2D` now has an overload that takes in an `IntBuffer` instead of a `long` for the pixel data
        - `upload` is removed
        - `_stencilFunc`, `_stencilMask`, `_stencilOp`, `_clearStencil` is removed
        - `_getTexImage` is removed
        - `_glDrawPixels`, `_readPixels` is removed
        - `$CullState#mode` is removed
        - `$DestFactor` -> `DestFactor`, codes are removed to be called through `GlConst#toGl`
        - `$FramebufferState` enum is removed
        - `$LogicOp` -> `LogicOp`, codes are removed to be called through `GlConst#toGl`
            - All but `OR_REVERSE` is removed
            - `NONE` - Performs no logic operation.
        - `$PolygonOffsetState#line` is removed
        - `$SourceFactor` -> `SourceFactor`, codes are removed to be called through `GlConst#toGl`
        - `$StencilFunc`, `$StencilState` class is removed
        - `$Viewport` enum is removed
    - `GlUtil` class is removed
        - `getVendor`, `getRenderer`, `getOpenGlVersion` (now `getVersion`) have been moved to instance abstract methods on `GpuDevice`
        - `getCpuInfo` -> `GLX#_getCpuInfo`
    - `GLX`
        - `getOpenGLVersionString` is removed
        - `_init` -> `_getCpuInfo`, not one-to-one
        - `_renderCrosshair`, `com.mojang.blaze3d.systems.RenderSystem#renderCrosshair` -> `net.minecraft.client.gui.components.DebugScreenOverlay#render3dCrosshair`, not one-to-one
    - `PolygonMode` - A enum that defines how the polygons will render in the buffer.
    - `NativeImage` constructor is now public
        - `upload` is removed
        - `getPointer` - Returns the pointer to the image data.
        - `setPixelABGR` is now public
        - `applyToAllPixels` is removed
        - `downloadTexture`, `downloadDepthBuffer` is removed
        - `flipY` is removed
        - `setPackPixelStoreState`, `setUnpackPixelStoreState` is removed
        - `$InternalGlFormat` enum is removed
        - `$Format` no longer contains the GL codes, now in `GlConst#toGl`
    - `TextureUtil`
        - `generateTextureId`, `releaseTextureId` is removed
        - `prepareImage` is removed
        - `writeAsPNG` now takes in a `GpuTexture` instead of the direct three integers
            - The overload without the `IntUnaryOperator` is removed
- `com.mojang.blaze3d.resource`
    - `RenderTargetDescriptor` now takes in an integer representing the color to clear to
    - `ResourceDescriptor`
        - `prepare` - Prepares the resource for use after allocation.
        - `canUsePhysicalResource` - Typically returns whether a descriptor is already allocated with the same information.
- `com.mojang.blaze3d.shaders`
    - `AbstractUniform` -> `com.mojang.blaze3d.opengl.AbstractUniform`
        - `setSafe` methods are removed
        - `setMat*` methods are removed
        - `set(Matrix3f)` is removed
    - `CompiledShader` -> `com.mojang.blaze3d.opengl.GlShaderModule`
        - `$Type` -> `com.mojang.blaze3d.shaders.ShaderType`
    - `Uniform` -> `com.mojang.blaze3d.opengl.Uniform`
        - Constructor now takes in a `$Type` instead of the count and an integer representing the type
        - `UT_*` fields are removed
        - `setFromConfig(ShaderProgramConfig.Uniform)` is removed
        - `getTypeFromString` is removed
        - `getType` now returns a `$Type`
        - `set(int, float)` is removed
        - `setSafe` is now private
        - `$Type` - Holds the type name as well as how many values it holds.
        - `getLocation` is removed
        - `getCount` -> `$Type#count`
        - `getIntBuffer`, `getFloatBuffer` is removed
        - `$Type` -> `com.mojang.blaze3d.shaders.UniformType`
- `com.mojang.blaze3d.systems`
    - `CommandEncoder` - An interface that defines how to encode various commands to the underlying render system, such as creating a pass, clearing and writing textures, or reading from the buffer.
    - `GpuDevice` - An interface that defines the device or underlying render system used to draw to the screen. This is responsible for creating the buffers and textures while compiling any pipelines.
    - `RenderPass` - An interface that defines how a given pass is rendered to some buffer using the underlying render system. This allows binding any samplers and setting the required uniforms.
    - `RenderSystem`
        - `isOnRenderThreadOrInit`, `assertOnRenderThreadOrInit` is removed
        - `recordRenderCall`, `replayQueue` is removed
        - `blendFunc`, `blendFuncSeparate`, `blendEquation` is removed
        - `texParameter`, `deleteTexture`, `bindTextureForSetup` is removed
        - `stencilFunc`, `stencilMask`, `stencilOp` is removed
        - `clearDepth` is removed
        - `glBindBuffer`, `glBindVertexArray`, `glBufferData`, `glDeleteBuffers` is removed
        - `glUniform1i` is removed
        - `glUniform1`, `glUniform2`, `glUniform3`, `glUniform4` is removed
        - `glUniformMatrix2`, `glUniformMatrix3`, `glUniformMatrix4` is removed
        - `setupOverlayColor` now takes in a `GpuTexture` instead of two ints
        - `beginInitialization`, `finishInitialization` is removed
        - `renderThreadTesselator` is removed
        - `setShader`, `clearShader`, `getShader` is removed
        - `setShaderTexture` now takes in a `GpuTexture` instead of a bind address
        - `getShaderTexture` now returns a `GpuTexture` or null if not present
        - `pixelStore`, `readPixels` is removed
        - `queueFencedTask`, `executePendingTasks` - Handles sending tasks that run on the GPU asyncronously.
        - `SCISSOR_STATE` - Holds the main scissor state.
        - `disableDepthTest`, `enableDepthTest` is removed
        - `depthFunc`, `depthMask` is removed
        - `enableBlend`, `disableBlend` is removed
        - `neableCull`, `disableCull` is removed
        - `polygonMode`, `enablePolygonOffset`, `disablePolygonOffset`, `polygonOffset` is removed
        - `enableColorLogicOp`, `disableColorLogicOp`, `logicOp` is removed
        - `bindTexture`, `viewport` is removed
        - `colorMask`, `clearColor`, `clear` is removed
        - `setupShaderLights(CompiledShaderProgram)` is removed
        - `getShaderLights` - Returns the vectors representing the block and sky lights.
        - `drawElements`, `getString` is removed
        - `initRenderer` now takes in the window pointer, the default shader source, and a boolean of whether to use debug labels
        - `setupDefaultState` no longer takes in any parameters
        - `maxSupportTextureSize` is removed
        - `glDeleteVertexArrays` is removed
        - `defaultBlendFunc` is removed
        - `setShaderTexture` is removed
        - `getQuadVertexBuffer` - Returns a vertex buffer with a quad bound to it.
        - `getDevice`, `tryGetDevice` - Returns the `GpuDevice` representing the underlying render system to use.
        - `getCapsString` is removed
        - `activeTexture` is removed
        - `setModelOffset`, `resetModelOffset`, `getModelOffset` - Handles the offset to apply to a model when rendering for the uniform `ModelOffset`. Typically for clouds and world borders.
        - `$AutoStorageIndexBuffer#bind` -> `getBuffer`, not one-to-one
        - `$GpuAsyncTask` - A record that holds the callback and fence object used to sync information to the GPU.
    - `ScissorState` - A class which holds the part of the screen to render.
- `com.mojang.blaze3d.textures`
    - `AddressMode` - The mode set for how to render a texture to a specific location.
    - `FilterMode` - The mode set for how to render a texture whenever the level-of-detail function determines how the texture should be maximized or minimized.
    - `GpuTexture` - A texture that is bound and written to the GPU as required.
    - `TextureFormat` - Specifies the format that the texture should be allocated with.
- `com.mojang.blaze3d.vertex`
    - `PoseStack`
        - `mulPose(Quaternionf)`, `rotateAround` now takes in a `Quaternionfc` instead of a `Quaternionf`
        - `clear` -> `isEmpty`
        - `mulPose(Matrix4f)` -> `mulPose(Matrix4fc)`
        - `$Pose`
            - `computeNormalMatrix` is now private
            - `transformNormal` now takes in a `Vector3fc` as its first parameter
            - `translate`, `scale`, `rotate`, `rotateAround`, `setIdentity`, `mulPose` are now available on the pose itself in addition to the stack
    - `VertexBuffer` -> `com.mojang.blaze3d.buffers.GpuBuffer`, not one-to-one
        - Some logic is also moved to `VertexFormat`
    - `VertexFormat`
        - `bindAttributes` is removed
        - `setupBufferState`, `clearBufferState`, `getImmediateDrawVertexBuffer` -> `uploadImmediateVertexBuffer`, `uploadImmediateIndexBuffer`; not one-to-one
        - `$IndexType` no longer stores the GL codes, now in `GlConst#toGl`
        - `$Mode` no longer stores the GL codes, now in `GlConst#toGl`
    - `VertexFormatElement`
        - `setupBufferState` is removed
        - `$Type` no longer stores the GL codes, now in `GlConst#toGl`
        - `$Usage` no longer stores the GL function calls, now in `VertexArrayCache#setupCombinedAttributes`
- `com.mojang.math`
    - `MatrixUtil`
        - `isIdentity`, `isPureTranslation`, `isOrthonormal` now take in a `Matrix4fc`
        - `checkProperty` - Checks if the provided property is represented within the matrix.
    - `OctahedralGroup`
        - `transformation` now returns a `Matrix3fc`
        - `fromAnges` -> `fromXYAngles`, not one-to-one
    - `Quadrant` - An enum that contains rotations in 90 degree increments.
    - `SymmetricGroup3#transformation` now returns a `Matrix3fc`
    - `Transformation` now takes in a `Matrix4fc`
        - `getMatrix` now returns a `Matrix4fc`
        - `getMatrixCopy` - Returns a deep copy of the current matrix.
- `net.minecraft.client.gui.font.FontTexture` now takes in a supplied label string
- `net.minecraft.client.main.GameConfig` now takes in a boolean representing whether to render debug labels
- `net.minecraft.client.renderer`
    - `CloudRenderer#render` no longer takes in the `Matrix4f`s used for projection or posing
    - `CompiledShaderProgram` -> `com.mojang.blaze3d.opengl.GlProgram`
        - `link` now takes in a string for the shader name
        - `setupUniforms` now take in the list of `$UniformDescription`s along with a list of names used by the samplers
        - `getUniformConfig` is removed
        - `bindSampler` now takes in a `GpuTexture` instead of the integer bind identifier
        - `parseUniformNode` is removed
    - `CoreShaders` -> `RenderPipelines`, not one-to-one
    - `LightTexture#getTarget` - Returns the `GpuTexture` that contains the light texture for the current level based on the player.
    - `PostChain`
        - `load` no longer takes in the `ShaderManager`, now taking in a `ResourceLocation` representing the name of the chain
        - `addToFrame`, `process` now takes in a `RenderPass` consumer to apply any additional settings to the pass to render
        - `setUniform` is removed
        - `setOnRenderPass` - Sets the uniform within the post chain on the `RenderPass` for use in the shaders.
    - `PostChainConfig`
        - `$Pass` now takes in the ids of the vertex and fragment shader instead of the program id
            - `referencedTargets` - Returns the targets referenced in the pass to apply.
            - `program` is removed
        - `$Uniform` now takes in the type of the uniform along with an optional list of floats if the value does not need to be overridden
    - `PostPass` no longer takes in the `CompiledShaderProgram`, now taking in the `RenderPipeline` instead of a string representing the name of the pass
         - `addToFrame` now takes in a `RenderPass` consumer to apply any additional settings to the pass to render
         - `getShader` is removed
         - `$Input#bindTo` now takes in a `RenderPass` instead of the `CompiledShaderProgram`
    - `RenderStateShard`
        - `$LayerStateShard`s using polygon offsets have been removed
        - `getName` - Returns the name of the shard.
        - `$TransparencyStateShard` class is removed
            - Now handled through `BlendFunction`
        - `$ShaderStateShard` class is removed
            - Directly referred to by the `VertexBuffer`
        - `$CullStateShard` class is removed
            - Now handled as a setting on the `RenderPipeline`
        - `$DepthTestStateShard` class is removed
            - Now handled through `DepthTestFunction`
        - `$WriteMaskStateShard` class is removed
            - Now handled as a setting on the `RenderPipeline`
        - `$ColorLogicStateShard` class is removed
            - Now handled as a setting on the `RenderPipeline`
        - `$OutputStateShard` now takes in a supplied `RenderTarget` instead of the runnables for the startup and teardown states
    - `RenderType` no longer takes in the `VertexFormat` or `VertexFormat$Mode`
        - `SKY`, `END_SKY`, `sky`, `endSky`, `stars` is removed
        - `ENTITY_OUTLINE_BLIT`, `entityOutlineBlit` is removed
        - `PANORAMA`, `panorama` is removed
        - `CREATE_LIGHTMAP`, `createLightmap` is removed
        - `createClouds`, `flatClouds`, `clouds`, `cloudsDepthOnly` is removed
        - `worldBorder` is removed
        - `debugLine` - Returns the `RenderType` associated with the debug line.
        - `entityOutlineBlit` - Returns the `RenderType` used for rendering an entity outline.
        - `panorama` - Returns the `RenderType` used for rendering panorama mode.
        - `createLightmap` - Returns the `RenderType` used for rendering the lightmap texture.
        - `create` no longer takes in the `VertexFormat` or `VertexFormat$Mode`, instead the `RenderPipeline`
        - `getRenderTarget`, `getRenderPipeline` - Returns the target and pipeline used for rendering.
        - `format`, `mode`, `draw` are now abstract
        - `$CompositeStateBuilder` methods are now protected
        - `$OutlineProperty` is now protected
    - `ShaderDefines$Builder#define` now has an overload that takes in an integer
    - `ShaderManager`
        - `SHADER_INCLUDE_PATH` is now private
        - `MAX_LOG_LENGTH` is removed
        - `preloadForStartup` is removed, replaced by `GpuDevice#precompilePipeline`
        - `getProgram`, `getProgramForLoading` -> `getShader`, not one-to-one
        - `linkProgram` now takes in a `RenderPipeline` instead of a `ShaderProgram` and `ShaderProgramConfig`
        - `$CompilationCache#getOrCompileProgram`, `getOrCompileShader` -> `getShaderSource`, not one-to-one
        - `$Configs` no longer takes in the map of programs
        - `$ShaderCompilationKey` record is removed
    - `ShaderProgram`, `ShaderProgramConfig` -> `RenderPipeline`, not one-to-one
    - `SkyRenderer#renderDarkDisc` no longer takes in the `PoseStack`
- `net.minecraft.client.renderer.chunk.SectionRenderDispatcher`
    - `uploadSectionLayer`, `uploadSectionIndexBuffer` -> `$RenderSection#uploadSectionLayer`, `uploadSectionIndexBuffer`
    - `$SectionBuffers` - A class that holds the buffers used to render the sections.
- `net.minecraft.client.renderer.texture`
    - `AbstractTexture`
        - `NOT_ASSIGNED` is removed
        - `texture`, `getTexture` - Holds the reference to the texture to render.
        - `getId`, `releaseId` is removed
        - `bind` is removed
    - `DynamicTexture` now takes in the label of the texture
    - `SpriteContents#uploadFirstFrame`, `$AnimatedTexture#uploadFirstFrame` now takes in a `GpuTexture`
    - `SpriteTicker#tickAndUpload` now takes in the `GpuTexture`
    - `TextureAtlasSprite#uploadFirstFrame`, `$Ticker#tickAndUpload` now takes in the `GpuTexture`

## Model Rework

The model system has been further separated into models for block states, blocks, and items. As such, the unifying `BakedModel` has been completely removed and separated into their own  sections, loaded in three steps: from JSON, resolving dependencies, and then baking for use with the associated block state model or item model. For reference, everything discussed below is what's happenening within `ModelManager#reload` in parallel.

First, let's start from the base model JSON used between blocks and items. These are loaded into an `UnbakedModel` (specifically `BlockModel`) which contains the familiar properties such as gui light and texture slots. However, one change is the splitting of the elements from their render settings. These elements that hold the render quads are stored in an `UnbakedGeometry`. The `UnbakedGeometry` is responsible for baking the model into a `QuadCollection`, which effectively holds the list of `BakedQuad`s to render. Currently, vanilla only has the `SimpleUnbakedGeometry`, which holds the familiar list of `BlockElement`s. These `UnbakedModel`, once loaded, are then passed to the `ModelDiscovery` for resolving the block state and item models.

Next we have the `ResolvableModel`s, which is the base of both block state and item models. These models essentially function as markers requesting the `UnbakedModel`s that they will be using. From there, we have their subtypes `BlockStateModel$UnbakedRoot` for the block state JSON and `ItemModel$Unbaked` for the model referenced in the client item JSON. Each of these implement `resolveDependencies` in some way to call `ResolvableModel$Resolver#markDependency` with the model location they would like to use.

> Technically, `BlockStateModel`s are a bit more complex as variants use `BlockStateModel$Unbaked` during loading which are then transformed into an `$UnbakedRoot` during initialization.

Now that we know what models that should be loaded, they now have to be put into a usable state for baking. This is the job of the `ModelDiscovery`, which takes in a `ResolvableModel` and loads the `UnbakedModel`s into a `ResolvedModel` on first reference. `ResolvedModel`s are functionally wrappers around `UnbakedModel`s used to resolve all dependency chains, as the name implies.

From there, model groups are built for `BlockState`s and the textures are loaded, leading to the final step of actually baking the `BlockStateModel` and the `ItemModel`. This is handled through the `bake` methods provided on the `$UnbakedRoot` (or `$Unbaked`) and the `ModelBakery`. In a nutshell, `bake` constructs the list of `BakedQuad`s stored with whatever additional information is desired by the block state or item model itself. The `ResolvedModel`s are obtained from the baker, from which the instance methods are called. For `BlockStateModel`s, this is resolved via `SimpleModelWrapper#bake`, from which the `ModelState` is obtained from the `Variant` data. They stored the baked quads in a `BlockModelPart`. For `ItemModel`s, it just consumes the `BakedQuad`s list directly along with information provided by `ModelRenderProperties#fromResolvedModel`. This does mean that each `BlockStateModel` and `ItemModel` may contain duplicated (but unique) `BakedQuad`s if the same model is referenced in multiple locations.

### Block Generators: The Variant Mutator

Given all the changes that separated out the block state JSON loading, there have also been a number of changes to the `BlockModelGenerators`. While most of them are simply renames (e.g., `BlockStateGenerator` -> `BlockModelDefinitionGenerator`), the major change is the addition of the `VariantMutator`. The `VariantMutator` is functionally a `UnaryOperator` on a `Variant` used to set some setting. This addition has simplified (or more like codec construction) the use of the `PropertyDispatch` for more quickly dispatching blocks with variants based on their properties.

```java
// Creates a property dispatch on the horizontal facing property
// Applies the associated variants, though if desired, a functional interface can be provided instead
public static final PropertyDispatch<VariantMutator> ROTATION_HORIZONTAL_FACING = PropertyDispatch.modify(BlockStateProperties.HORIZONTAL_FACING)
    .select(Direction.EAST, BlockModelGenerators.Y_ROT_90)
    .select(Direction.SOUTH, BlockModelGenerators.Y_ROT_180)
    .select(Direction.WEST, BlockModelGenerators.Y_ROT_270)
    .select(Direction.NORTH, BlockModelGenerators.NOP);

// Then, with access to the `Consumer<BlockModelDefinitionGenerator>` blockStateOutput
this.blockStateOutput.accept(
    MultiVariantGenerator.dispatch(EXAMPLE_BLOCK).with(ROTATION_HORIZONTAL_FACING)
);
```

- `net.minecraft.client.data.models`
    - `BlockModelGenerators`
        - `nonOrientableTrapdoor` -> `NON_ORIENTABLE_TRAPDOOR`, now static
        - Constants are now available for common `VariantMutator`s, like rotating the block model some number of degrees
        - `texturedModels` -> `TEXTURED_MODELS`, now static
        - `MULTIFACE_GENERATOR` is now private
        - `plainModel` - Creates a variant from the model location.
        - `variant`, `variants` - Creates a regular `MultiVariant` from some number of `Variant`s
        - `plainVariant` - Creates a `MultiVariant` with only one model from its location.
        - `condition` - Creates a new condition builder for multipart models
        - `or` - ORs multiple conditions together.
        - Most generator methods now return the `BlockModelDefinitionGenerator`, `Variant`, or `MultiVariant` and take in a `Variant` or `MultiVariant` instead of a `ResourceLocation` pointing to the desired model
            - `VariantProperties` have been replaced with `VariantMutator`s
            - `Condition$TerminalCondition` is replaced with `Condition`
        - `createHorizontalFacingDispatch`, `createHorizontalFacingDispatchAlt`, `createTorchHorizontalDispatch` is removed
        - `createFacingDispatch` is removed
        - `createRotatedVariant(Block, ResourceLocation)` is removed
        - `selectMultifaceProperties` - Creates a map of properties to `VariantMutator`s based on the provided `BlockState` and direction to property function.
        - `applyRotation` no longer takes in the `Variant` and returns a `VariantMutator`
    - `ItemModelGenerators#generateSpawnEgg` is removed
    - `ModelProvider#saveAll` is removed
- `net.minecraft.client.data.models.blockstates`
    - `BlockStateGenerator` -> `BlockModelDefinitionGenerator`, not one-to-one
    - `Condition` -> `net.minecraft.client.renderer.block.model.multipart.Condition`, not one-to-one
        - `validate` -> `instantiate`, not one-to-one
    - `ConditionBuilder` - Builds a condition using property values
    - `MultiPartGenerator` now implements `BlockModelDefinitionGenerator`
        - `with(List<Variant>)` -> `with(MultiVariant)`
        - `with(Variant)` is removed
        - `with(Condition, ...)` -> `with(Condition, MultiVariant)`
            - Overload taking in `ConditionBuilder`
        - `$ConditionalEntry`, `$Entry` is removed
    - `MultiVariantGenerator` now implements `BlockModelDefinitionGenerator`
        - `multiVariant` -> `dispatch`
        - `multiVariant(Block, ...)` -> `dispatch(Block, MultiVariant)`
        - `$Empty` - A multi variant entry that matches every block state.
    - `PropertyDispatch` has a generic containing the value of the dispatch
        - The generic `V` replaces all values of `List<Variant>`
        - `property`, `properties` -> `initial` or `modify`
        - `$C*#generateList` methods are removed
        - `$*Function` are removed
    - `Selector` -> `PropertyValueList`, not one-to-one
    - `Variant` -> `net.minecraft.client.renderer.block.model.Variant`, not one-to-one
    - `VariantProperties` -> `net.minecraft.client.renderer.block.model.VariantMutator`, not one-to-one
    - `VariantProperty` -> `net.minecraft.client.renderer.block.model.VariantMutator$VariantProperty`, not one-to-one
- `net.minecraft.client.renderer.ItemInHandRenderer#renderItem` no longer takes in the boolean representing if the item is held in the left hand
- `net.minecraft.client.renderer.block`
    - `BlockModelShaper#stateToModelLocation`, `statePropertiesToString` is removed
    - `BlockRenderDispatcher#renderBatched` now takes in a list of `BlockModelPart`s instead of a `RandomSource`
    - `ModelBlockRenderer`
        - `tesselateBlock`, `tesselateWithAO`, `tesselateWithoutAO` no longer takes in a `RandomSource` and replaces `BlockStateModel` with a list of `BlockModelPart`s
        - `renderModel` is now static and no longer takes in the `BlockState`
        - `$AmbientOcclusionFace` -> `$AmbientOcclusionRenderStorage`
        - `$CommonRenderStorage` - A class that holds some metadata used to render a block at its given position.
        - `$SizeInfo` now takes in the direct index rather than computing the info from its direction and a flipped boolean
- `net.minecraft.client.renderer.block.model`
    - `BakedQuad` is now a record
    - `BlockElement` is now a record
        - `from`, `to` are now `Vector3fc`
    - `BlockElementFace` now takes in a `Quadrant` for the face rotation
        - `getU`, `getV` - Returns the texture coordinate after rotation.
        - `$Deserializer#getTintIndex` is now private and static
    - `BlockFaceUV` -> `BlockElementFace$UVs`, not one-to-one
    - `BlockModel` is now a record, taking in an `UnbakedGeometry` instead of the direct list of `BlockElement`s
        - `$Deserializer#getElements` now returns an `UnbakedGeometry`
    - `BlockModelDefinition` is now a record, taking in `$SimpleModelSelectors` and `$MultiPartDefinition`s
        - `GSON`, `fromStream`, `fromJsonElement` -> `CODEC`, not one-to-one
        - `instantiate` now takes in a supplied string instead of the string directly
        - `$Deserializer` is removed
        - `$MultiPartDefinition` - A record that holds a list of selectors to get for the multi part model.
        - `$SimpleModelSelectors` - A record that holds a map of variants to their unbaked model instances.
    - `BlockModelPart` - A baked model representation of a block.
    - `BlockStateModel` - A baked representation of a block state.
        - `collectParts` - Obtains the list of baked models used to render this state.
        - `$SimpleCachedUnbakedRoot` - A class that represents a delegate of some `$Unbaked` model.
        - `$Unbaked` - An extension over `$UnbakedRoot` that can create a `$SimpleCachedUnbakedRoot`
    - `FaceBakery`
        - `bakeQuad` now takes in `Vector3fc`s instead of `Vector3f`s
        - `recomputeUVs` is removed
        - `extractPositions` - Extracts the face positions and passes them to a consumer for use.
    - `ItemTransform` is now a record, vectors are `Vector3fc`s
    - `MultiVariant` -> `net.minecraft.client.data.models.MultiVariant`
        - `CODEC`
        - `with` - Creates a `MultiVariant` with the specified mutators.
        - `$Deserializer` class is removed
    - `SimpleModelWrapper` now implements `BlockModelPart`
    - `SimpleUnbakedGeometry` - An unbaked geometry that holds a list of `BlockElement`s to bake.
    - `SingleVariant` - A `BlockStateModel` implementation with only one model for its state.
    - `UnbakedBlockStateModel` -> `BlockStateModel$UnbakedRoot`
    - `Variant` no longer implements `ModelState`, now taking in a `$SimpleModelState` instead of the direct rotation and uv lock
        - The constructor now has an overload for only providing the `ResourceLocation` and no longer takes in the weight, leaving that to the `MultiVariant`
        - `CODEC`
        - `withXRot`, `withYRot`, `withUvLock`, `withModel`, `withState`, `with` - Mutates the variant into a new object with the given setting applied.
        - `$Deserializer` class is removed
        - `$SimpleModelState` - A record that holds the x/y rotations and uv lock.
    - `VariantMutator` - A unary operator on a variant that applies the specified setting to the variant. Used during state generation.
- `net.minecraft.client.renderer.block.model.multipart`
    - `AndCondition`, `OrCondition` -> `CombinedCondition`, not one-to-one
    - `KeyValueCondition` is now a record that takes in a map of keys to terms to test
    - `MultiPart` -> `MultiPartModel$Unbaked`
        - `$Definition`
            - `CODEC`
            - `getMultiVariants` is removed
        - `$Deserializer` class is removed
    - `Selector` is now a record, taking in a `BlockStateModel$Unbaked` instead of a `MultiVariant`
        - `$Deserializer` class is removed
- `net.minecraft.client.renderer.entity.ItemRenderer`
    - `renderItem` now takes in a `List<BakedQuad>` instead of a `BakedModel`
    - `renderStatic` no longer takes in a boolean indicating what hand the item was held in
- `net.minecraft.client.renderer.item`
    - `BlockModelWrapper` now has a public constructor that takes in the list of tint sources, the list of quads, and the `ModelRenderProperties`
        - The list of quads and `ModelRenderProperties` replaces the direct `BakedModel`, or now `BlockStateModel`
        - `computeExtents` - Extracts the vertices of the baked quads into an array.
    - `ItemModel$BakingContext#bake` is removed
    - `ItemModelResolver#updateForLiving`, `updateForTopItem` no longer takes in a boolean representing if the item is in the left hand
    - `ItemStackReenderState`
        - `isGui3d` is removed
        - `transform` is removed
        - `visitExtents` - Visits all vertices of the model to render and passes them into the provided consumer.
        - `$LayerRenderState`
            - `NO_EXTENTS_SUPPLIER` - An empty list of vertices.
            - `setupBlockModel` has been broken into `prepareQuadList`, `setRenderType`, `setUsesBlockLight`, `setExtents`, `setParticleIcon`, `setTransform`
            - `setupSpecialModel` no longer takes in the base `BakedModel`
    - `MissingItemModel` now takes in a list of `BakedQuad`s and `ModelRenderProperties` instead of the direct `BakedModel`
    - `ModelRenderProperties` - The properties used to render a model, typically retrieved from the `ResolvedModel`.
    - `SpecialModelRenderer` now takes in the `ModelRenderProperties` insetad of the base `BakedModel`
- `net.minecraft.client.resources.model`
    - `BakedModel` -> `net.minecraft.client.resources.model.QuadCollection`, not one-to-one
    - `BlockModelRotation`
        - `by` now takes in `Quadrant`s instead of integers
        - `withUvLock` - Returns the model state with the rotation and a mention that it locks the UV for the rotation.
    - `BlockStateDefinitions` - A manager for creating the mapper of block names to their state defintions.
    - `BlockStateModelLoader`
        - `ModelResourceLocation` fields are removed
        - `loadBlockState` no longer takes in the missing model
        - `$LoadedModel` class is removed
        - `$LoadedModels` now takes in a `BlockStateModel$UnbakedRoot` instead of an `$Unbaked`
            - `forResolving`, `plainModels` is removed
    - `DelegateBakedModel` -> `net.minecraft.client.renderer.block.model.SimpleModelWrapper`, not one-to-one
    - `MissingBlockModel#VARIANT` is removed
    - `ModelBaker`
        - `bake` -> `getModel`, not one-to-one
            - The baker is simply retrieving the `ResolvedModel`
        - `rootName` is removed
        - `compute` - Computes the provided key that contains the `ModelBaker`. Typically used for baking `BlockStateModel`s
        - `$SharedOperationKey` - An interface which typically computes some baking process for an unbaked model.
    - `ModelBakery` now takes in a `Map<BlockState, BlockStateModel$UnbakedRoot>` for the unbaked block state models, a `Map<ResourceLocation, ResolvedModel>` for the loaded models, and a `ResolvedModel` for the missing model
        - `bakeModels` now takes in a `SpriteGetter` and an `Executor` while returning a `CompletableFuture` for parallel loading and baking
        - `$BakingResult` now takes in a `$MissingModels` for the missing block state and item model and a `Map<BlockState, BlockStateModel>` for the baked block state models; the missing item model is stored within `$MissingModels`
        - `$MissingModels` - Holds the missing models for a block state and item.
        - `$TextureGetter` interface is removed
    - `ModelDebugName` no longer extends `Supplier<String>`, instead using `debugName`
    - `ModelDiscovery`
        - `registerSpecialModels` is removed
        - `discoverDependencies` is now private
        - `getReferencedModels`, `getUnreferencedModels` is removed
        - `addSpecialModel` - Adds a root model to the list of arbitrarily loaded models.
        - `missingModel` - Returns the missing model
        - `resolve` - Resolves all model dependencies, returning a map of model names to their models.
    - `ModelGroupCollector$GroupKey#create` now takes in a `BlockStateModel$UnbakedRoot` instead of an `$Unbaked`
    - `ModelManager`
        - `getModel` is removed
        - `getMissingModel` -> `getMissingBlockStateModel`
        - `$ResolvedModels` - A map of models with their dependencies resolved.
    - `ModelResourceLocation` record is removed
    - `ModelState`
        - `getRotation` -> `transformation`
        - `isUvLocked` is removed
        - `faceTransfomration`, `inverseFaceTransformation` - Handles returning the transformed `Matrix4fc` for baking the face vertices.
    - `MultiPartBakedModel` -> `net.minecraft.client.renderer.block.model.multipart.MultiPartModel`
        - Now implements `BlockStateModel` instead of extending `DelegateBakedModel`
        - `$SharedBlockState` - A holder that contains the `BlockStateModel`s mapped to their `$Selector`s.
    - `QuadCollection` - A data object containing the list of quads to render based on the associated direction and culling.
    - `ResolvableModel$Resolver#resolve` -> `markDependency`, not one-to-one
        - Instead of directly resolving, the dependency is marked for a later post processing step
    - `ResolvedModel` - An `UnbakedModel` whose model and texture dependencies have been completely resolved.
    - `SimpleBakedModel` -> `net.minecraft.client.renderer.block.model.SimpleModelWrapper` or `net.minecraft.client.renderer.block.model.SimpleUnbakedGeometry`, not one-to-one
    - `SpriteGetter`
        - `get`, `reportMissingReference` now take in the `ModelDebugName`
        - `resolveSlot` - Resolves the key from the `TextureSlot`s into its `TextureAtlasSprite`.
    - `UnbakedGeometry` - An interface that constructs a collection of quads the render when baked.
    - `UnbakedModel` no longer implements `ResolvableModel`
        - `DEFAULT_AMBIENT_OCCLUSION`, `DEFAULT_GUI_LIGHT` is removed
        - `PARTICLE_TEXTURE_REFERENCE` - Holds the key representing the particle texture.
        - `bake` is removed
        - `getAmbientOcclusion` -> `ambientOcclusion`
        - `getGuiLight` -> `guiLight`
        - `getTransforms` - `transforms`
        - `getTextureSlots` - `textureSlots`
        - `geometry` - Holds the unbaked geometry representing the model elements.
        - `getParent` -> `parent`, not one-to-one
        - `bakeWithTopModelValues` is removed
        - `getTopTextureSlots`, `getTopAmbientOcclusion`, `getTopGuiLight`, `getTopTransform`, `getTopTransforms` is removed
    - `WeightedBakedModel` -> `WeightedVariants`
        - Now implements `BlockStateModel` instead of extending `DelegateBakedModel`
- `net.minecraft.world.item.ItemDisplayContext#leftHand` - Returns whether the display context is rendering with the entity's left hand.

## Minor Migrations

The following is a list of useful or interesting additions, changes, and removals that do not deserve their own section in the primer.

### Entity References

Generally, the point of storing the UUID of another entity was to later grab that entity to perform some logic. However, storing the raw entity could lead to issues if the entity was removed at some point in time. As such, the `EntityReference` was added to handle resolving the entity from its UUID while also making sure it still existed at the time of query.

An `EntityReference` is simply a wrapped `Either` which either holds the entity instance or the UUID. When resolving via `getEntity`, it will attempt to verify that the stored entity, when present, isn't removed. If it is, it grabs the UUID to perform another lookup for the entity itself. If that entity does exist, it will be return, or otherwise null.

Most references to a UUID within an entity have been replaced with an `EntityReference` to facilitate this change.

- `net.minecraft.network.syncher.EntityDataSerializers#OPTIONAL_UUID` -> `OPTIONAL_LIVING_ENTITY_REFERENCE`, not one to one as it can hold the entity reference
- `net.minecraft.server.level.ServerLevel#getEntity(UUID)` -> `Level#getEntity(UUID)`
- `net.minecraft.world.entity`
    - `EntityReference` - A reference to an entity either by its entity instance when present in the world, or a UUID.
    - `LivingEntity#lastHurtByPlayer`, `lastHurtByMob` are now `EntityReference`s
    - `OwnableEntity`
        - `getOwnerUUID` -> `getOwnerReference`, not one-to-one
        - `level` now returns a `Level` instead of an `EntityGetter`
    - `TamableAnimal#setOwnerUUID` -> `setOwner`, or `setOwnerReference`; not one-to-one
- `net.minecraft.world.entity.animal.horse.AbstractHorse#setOwnerUUID` -> `setOwner`, not one-to-one
- `net.minecraft.world.level.Level` now implements `UUIDLookup<Entity>`
- `net.minecraft.world.level.entity`
    - `EntityAccess` now implements `UniquelyIdentifyable`
    - `UniquelyIdentifyable` - An interface that claims the object as a UUID and keeps tracks of whether the object is removed or not.
    - `UUIDLookup` - An interface that looks up a type by its UUID.

### Descoping Player Arguments

Many methods that take in the `Player` has been descoped to take in a `LivingEntity` or `Entity` depending on the usecase. The following methods below are a non-exhaustive list of this.

- `net.minecraft.world.entity.EntityType`
    - `spawn`
    - `createDefaultStackConfig`, `appendDefaultStackConfig`
    - `appendCustomEntityStackConfig`, `updateCustomEntityTag`
- `net.minecraft.world.item`
    - `BucketItem#playEmptySound`
    - `DispensibleContainerItem#checkExtraContent`, `emptyContents`
- `net.minecraft.world.level`
    - `Level`
        - `playSeededSound`
        - `mayInteract`
    - `LevelAccessor`
        - `playSound`
        - `levelEvent`
- `net.minecraft.world.level.block`
    - `BucketPickup#pickupBlock`
    - `LiquidBlockContainer#canPlaceLiquid`
- `net.minecraft.world.level.block.entity.BrushableBlockEntity#brush`

### Component Interaction Events

Click and hover events on a `MutableComponent` have been reworked into `MapCodec` registry-like system. They are both now interfaces that register their codecs to an `$Action` enum. The implementation then creates a codec that references the `$Action` type and stores any necessary information that is needed for the logic to apply. However, there is no direct 'action' logic associated with the component interactions. Instead, they are hardcoded into their use locations. For click events, this is within `Screen#handleComponentClicked`. For hover events, this is in `GuiGraphics#renderComponentHoverEffect`. As such, any additional events added will need to inject into both the enum and one or both of these locations.

- `net.minecraft.network.chat`
    - `ClickEvent` is now an interface
        - `getAction` -> `action`
        - `getValue` is now on the subclasses as necessary for their individual types
    - `HoverEvent` is now an interface
        - `getAction` -> `action`
        - `$EntityTooltipInfo`
            - `CODEC` is now a `MapCodec`
            - `legacyCreate` is removed
        - `$ItemStackInfo` is removed, replaced by `$ShowItem`
        - `$LegacyConverter` interface is removed

### Texture Atlas Reworks

The texture atlas logic has been finalized into a registry codec system; however, the querying of the atlas data has changed. First, all atlas identifiers are stored within `AtlasIds` while the corresponding texture location is stored within `Sheets`. To get a material from an atlas, a `MaterialMapper` is used as a wrapper around the texture location and the associated prefix to append to the material. The `Material` can then be obtained using `apply` by passing in the id of the material you would like to use.

For example:

```java
// Found in sheets
public static final MaterialMapper ITEMS_MAPPER = new MaterialMapper(TextureAtlas.LOCATION_BLOCKS, "item");
public static final MaterialMapper BLOCKS_MAPPER = new MaterialMapper(TextureAtlas.LOCATION_BLOCKS, "block");

// Finds the texture for the material at `assets/examplemod/textures/item/example_item.png`
public static final Material EXAMPLE_ITEM = ITEMS_MAPPER.apply(ResourceLocation.fromNamespaceAndPath("examplemod", "example_item"));

// Finds the texture for the material at `assets/examplemod/textures/block/example/block.png`
public static final Material EXAMPLE_BLOCK = ITEMS_MAPPER.apply(ResourceLocation.fromNamespaceAndPath("examplemod", "example/block"));
```

- `net.minecraft.client.data.AtlasProvider` - A data provider for generating the providers of a texture atlas.
- `net.minecraft.client.data.models.ItemModelGenerators`
    - `SLOT_*` -> `TRIM_PREFIX_*`, now public and `ResourceLocation`s
    - `TRIM_MATERIAL_MODELS` is now public
    - `generateTrimmableItem` now takes in a `ResourceLocation` instead of a `String`
    - `$TrimMaterialData` is now public, taking in a `MaterialAssetGroup` instead of the name and override materials
- `net.minecraft.client.renderer`
    - `MaterialMapper` - An object that stores the location of the atlas texture and the prefix applied to the ids within the texture.
    - `Sheets`
        - `*_MAPPER` - `MaterialMapper`s for each texture atlas texture.
        - `createBedMaterial(ResourceLocation)` is removed
        - `createShulkerMaterial(ResourceLocation)` is removed
        - `createSignMaterial(ResourceLocation)` is removed
        - `chestMaterial(String)`, `chestMaterial(ResourceLocation)` are removed
        - `createDecoratedPotMaterial(ResourceLocation)` is removed
- `net.minecraft.client.renderer.blockentity.ConduitRenderer#MAPPER` - A mapper to get the conduit textures from the block atlas.
- `net.minecraft.client.renderer.texture.atlas`
    - `SpriteSource#type` -> `codec`, not one-to-one
    - `SpriteSources` now contains logic similar to client registries via their id mapper
    - `SpriteSourceType` record is removed
- `net.minecraft.client.renderer.texture.atlas.sources`
    - `DirectoryLister` is now a record
    - `PalettedPermutations` is now a record
    - `SingleFile` is now a record
    - `SourceFilter` is now a record
    - `Unstitcher` is now a record
        - `$Region` is now public
- `net.minecraft.client.resources.model.AtlasIds` - A class which holds the `ResourceLocation`s of all vanilla texture atlases.

### Registry Context Swapper

Client items now store a `RegistryContextSwapper`, which is used to properly check client item information that accesses registry objects. Before level load, this is provided a placeholder to avoid crashing and populated with the correct value during rendering.

- `net.minecraft.client.multiplayer`
    - `CacheSlot` - An object that contains a value computed from some context. When updated, the previous value is overwritten and the context registers the slot to be cleaned.
    - `ClientLevel` now implements `CacheSlot$Cleaner`
- `net.minecraft.client.renderer.item`
    - `ClientItem` can now take in a nullable `RegistryContextSwapper`
        - `withRegistrySwapper` - Sets the `RegistryContextSwapper` within a `ClientItem`
    - `ItemModel$BakingContext` now takes in a `RegistryContextSwapper`
- `net.minecraft.util`
    - `PlaceholderLookupProvider` - A provider that contains placeholders for referenced objects. Used within client items as they will be loaded before the `RegistyAccess` is populated.
    - `RegistryContextSwapper` - An interface used to swap out some object for a different one. Used by client items to swap the placeholders for the loaded `RegistryAccess`.

### Reload Instance Creation

Reload instances have been slightly rearranged. The `SimpleReloadInstance` base now only takes in the `List<PreparableReloadListener>`, where the other fields are passed into the `of` function such that `#startTasks` can be called immediately.

- `net.minecraft.server.packs.resources`
    - `ProfiledReloadInstance` construct is now private, accessed through `of`
    - `SimpleReloadInstance` only takes in the `List<PreparableReloadListener>`
        - `of` now returns a `ReloadInstance`, not one-to-one
        - `allPreparations` is now package private
        - `allDone` is now private
        - `startTasks` - Begins the reload of the listener.
        - `prepareTasks` - Runs the executor and sets up the futures needed to read and load all desired data.
        - `StateFactory$SIMPLE` - A factory that calls `PreparableReloadListener#reload`

### Block Effect Appliers

Effects that are applied to entities when inside a block are now handled through the `InsideBlockEffectApplier` and `InsideBlockEffectType`. The `InsideBlockEffectType` is an enum that contains a consumer on what to apply to an entity when called. `InsideBlockEffectApplier`, on the other hand, is stored on the entity has a way to apply an effect in a ordered manner based on the enum ordinals.

To call one of the effect types, you must override `BlockBehaviour#entityInside` or `Fluid#entityInside` and call `InsideBlockEffectApplier#apply`. If something should apply before the effect type, like entinguishing fire before freezing in powder snow, then `InsideBlockEffectApplier#runBefore` should be called before `apply`. Similarly, if something should run afterward, like hurting an enemy after being placed in lava, then `runAfter` should be called.

```java
// In some block or fluid subclass
@Override
protected void entityInside(Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier applier) {
    applier.runBefore(InsideBlockEffectType.EXTINGUISH, entity -> {
        // Modify entity here.
    });

    // Do the base application logic stored on the type
    applier.apply(InsideBlockEffectType.FIRE_IGNITE);

    applier.runAfter(InsideBlockEffectType.FIRE_IGNITE, entity -> {
        // Perform any final checks that are as a result of the effect being applied
        entity.hurt(...);
    });
}
```

- `net.minecraft.world.entity`
    - `InsideBlockEffectApplier` - An interface that defines how an entity should interact when within a given block.
    - `InsideBlockEffectType` - An enum that defines what behavior to perform when side the specific block that references the type.
- `net.minecraft.world.level.block.state.BlockBehaviour#entityInside`, `$BlockStateBase#entityInside` now takes in an `InsideBlockEffectApplier`
- `net.minecraft.world.level.material.Fluid#entityInside`, `FluidState#entityInside` - A method called whenever the entity is considered inside the bounding box of the fluid.

### Timer Callbacks, joining the codec club!

`TimerCallback`s, used in the server schedule for executing events, typically mcfunctions in datapacks, have now been reworked into a codec form. This means that a callback can be registered to the list of available callbacks by passing in the `MapCodec` to `TimerCallbacks#register` (via `TimerCallbacks#SERVER_CALLBACKS`) instead of the serializer.

- `net.minecraft.world.level.timers`
    - `FunctionCallback` is now a record
    - `FunctionTagCallback` is now a record
    - `TimerCallback`
        - `codec` - Returns the codec used for serialization.
        - `$Serializer` class is removed
    - `TimerCallbacks`
        - `serialize`, `deserialize` -> `codec`, not one-to-one

### The JOML Backing Interfaces

Mojang has opted to lessen the restriction on JOML objects by passing around the implementing interface of their logic objects (usually implemented with a tacked on `c`). For example, `Vector3f` becomes `Vector3fc` or `Matrix4f` becomes `Matrix4fc`. This does not change any logic itself as the `c` interfaces are implemented by the class components.

### Tag Changes

- `minecraft:worldgen/biome`
    - `spawns_cold_variant_farm_animals`
    - `spawns_warm_variant_farm_animals`
- `minecraft:block`
    - `sword_instantly_mines`
    - `replaceable_by_mushrooms`
    - `plays_ambient_desert_block_sounds`
    - `edible_for_sheep`
    - `dead_bush_may_place_on` -> `dry_vegetation_may_place_on`
    - `camels_spawnable_on`
- `minecraft:cat_variant` are removed
- `minecraft:entity_type`
    - `can_equip_saddle`
    - `can_wear_horse_armor`
- `minecraft:item`
    - `book_cloning_target`
    - `eggs`
    - `flowers`

### Mob Effects Field Renames

Some mob effects have been renamed to their in-game name, rather than some internal descriptor.

- `MOVEMENT_SPEED` -> `SPEED`
- `MOVEMENT_SLOWDOWN` -> `SLOWNESS`
- `DIG_SPEED` -> `HASTE`
- `DIG_SLOWDOWN` -> `MINING_FATIGUE`
- `DAMAGE_BOOST` -> `STRENGTH`
- `HEAL` -> `INSTANT_HEALTH`
- `HARM` -> `INSTANT_DAMAGE`
- `JUMP` -> `JUMP_BOOST`
- `CONFUSION` -> `NAUSEA`
- `DAMAGE_RESISTANCE` -> `RESISTANCE`

### Very Technical Changes

This is a list of technical changes that could cause highly specific errors depending on your specific setup.

- The order of the `minecraft:patch_sugar_cane` feature and `minecraft:patch_pumpkin` feature have swapped orders (first pumpkin, then sugar cane), meaning modded biomes that generate both of these features will need to update their JSONs to the new ordering.

- Serveral vanilla oak tree and tree selector features now have `_leaf_litter` appended at the end.
    - For example: `trees_birch_and_oak` -> `trees_birch_and_oak_leaf_litter`

### List of Additions

- `net.minecraft`
    - `ChatFormatting#COLOR_CODEC`
    - `CrashReportCategory#populateBlockLocationDetails` - Adds the block location details to a crash report.
- `net.minecraft.advancements.critereon.MinMaxBounds#createStreamCodec` - Constructs a stream codec for a `MinMaxBounds` implementation.
- `net.minecraft.client.Options#startedCleanly` - Sets whether the game started cleanly on last startup.
- `net.minecraft.client.data.models`
    - `BlockModelGenerators#createSegmentedBlock` - Generates a multipart blockstate definition with horizontal rotation that displays up to four models based on some integer property.
    - `ItemModelGenerators#prefixForSlotTrim` - Generates a vanilla `ResourceLocation` for a trim in some slot.
- `net.minecraft.client.MouseHandler`
    - `fillMousePositionDetails` - Adds details about the current mouse location and screen size to a crash report.
    - `getScaledXPos` - Gets the current x position scaled by the gui scaling option.
    - `getScaledYPos` - Gets the current y position scaled by the gui scaling option.
    - `drawDebugMouseInfo` - Draws information about the scaled position of the mouse to the screen.
- `net.minecraft.client.gui.components.toasts.Toast#getSoundEvent` - Returns the sound to play when the toast is displayed.
- `net.minecraft.client.gui.screens.options.VideoSettingsScreen#updateFullscreenButton` - Sets the fullscreen option to the specified boolean.
- `net.minecraft.client.model.geom.builders`
    - `MeshDefinition#apply` - Applies the given transformer to the mesh before returning a new instance.
    - `MeshTransformer#IDENTITY`- Performs the identity transformation.
- `net.minecraft.client.multiplayer.ClientPacketListener#decoratedHashOpsGenenerator` - Returns the generator used to create a hash of a data component and its value.
- `net.minecraft.client.particle`
    - `FallingLeavesParticle$TintedLeavesProvider` - A provider for a `FallingLeavesParticle` that uses the color specified by the block above the particle the spawn location.
    - `FireflyParticle` - A particle that spawns fireflies around a given non-air block position.
- `net.minecraft.client.renderer`
    - `BiomeColors#getAverageDryFoliageColor` - Returns the average foliage color for dry biomes.
    - `LevelRenderer$BrightnessGetter` - An interfaces which obtains the packed brightness at a given block position.
    - `WorldBorderRenderer#invalidate` - Invalidates the current render of the world border to be rerendered.
- `net.minecraft.client.renderer.entity`
    - `EntityRenderDispatcher#getRenderer` - Gets the renderer to use from the data stored on the render state.
    - `EntityRenderer#extractAdditionalHitboxes` - Gets any additional hitboxes to render when the 'show hitboxes' debug state is enabled.
- `net.minecraft.client.renderer.entity.state`
    - `EntityRenderState`
        - `entityType` - The type of the entity.
        - `hitboxesRenderState` - The hitbox information of the entity relative to the entity's position.
        - `serverHitboxesRenderState` - The hitbox information of the entity synced from the server.
        - `fillCrashReportCategory` - Sets the details for any crashes related to the render state.
    - `HitboxesRenderState` - The render state of the hitboxes for the entity relative to the entity's position.
    - `HitboxRenderState` - The render state of a single hitbox to render along with its color, such as the eye height of an entity.
    - `ServerHitboxesRenderState` - The render state containing the last synced information from the related server entity.
    - `PigRenderState#variant` - The variant of the pig.
- `net.minecraft.client.renderer.item.SelectItemModel$ModelSelector` - A functional interface that selects the item model based on the switch case and level.
- `net.minecraft.client.renderer.item.properties.conditional.ComponentMatches` - A conditional property that checks whether the given predicate matches the component data.
- `net.minecraft.client.renderer.item.properties.select`
    - `ComponentContents` - A switch case property that operates on the contents within a data component.
    - `SelectItemModelProperty#valueCodec` - Returns the `Codec` for the property type.
- `net.minecraft.client.resources.DryFoliageColorReloadListener` - A reload listener that loads the colormap for dry foliage.
- `net.minecraft.commands.arguments.ComponentArgument#getResolvedComponent` - Constructs a component with the resolved information of its contents.
- `net.minecraft.core`
    - `Direction#getUnitVec3f` - Returns the float unit vector of the direction.
    - `HolderGetter$Provider#getOrThrow` - Gets a holder reference from a resource key.
    - `SectionPos#sectionToChunk` - Converts a compressed section position to a compressed chunk position.
    - `Vec3i#STREAM_CODEC`
- `net.minecraft.network`
    - `HashedPatchMap` - A record containing a map of components to their hashed type/value along with a set of removed components.
    - `HashedStack` - An `ItemStack` representation that hashes the stored components.
    - `ProtocolInfo$DetailsProvider` - Provides the details for a given protocol.
    - `SkipPacketDecoderException` - An exception thrown when an error occurs during decoding before having its data ignored.
    - `SkipPacketEncoderException` - An exception thrown when an error occurs during encoding before having its data ignored.
- `net.minecraft.network.chat`
    - `LastSeenMessages`
        - `computeChecksum` - Computes a byte representing the merged checksums of all message signatures.
        - `$Update#verifyChecksum` - Verifies that the update checksum matches those within the last seen messages.
    - `LastSeenMessagesValidator$ValidationException` - An exception thrown if the messages can not be validated.
    - `MessageSignature`
        - `describe` - Returns a stringified version of the message signature.
        - `checksum` - Hashes the bytes within the signature into a single integer.
    - `PlayerChatMessage#describeSigned` - Returns a stringified version of the chat message.
- `net.minecraft.network.codec`
    - `ByteBufCodecs`
        - `LONG_ARRAY`
        - `lengthPrefixed` - Returns an operation that limits the size of the buffer to the given size.
    - `IdDispatchCodec$DontDecorateException` - An interface that tells the exception handler to rethrow the raw exception rather than wrap it within an `EncoderException`.
- `net.minecraft.network.protocol`
    - `CodecModifier` - A function that modifies some codec using a given object.
    - `ProtocolInfoBuilder#context*Protocol` - Builds an `UnboundProtocol` with the given context used to modify the codecs to send.
- `net.minecraft.network.protocol.game.GameProtocols`
    - `HAS_INFINITE_MATERIALS` - A modifier that checks the `ServerboundSetCreativeModeSlotPacket` for if the player has the necessary settings. If not, the packet is discarded.
    - `$Context` - Returns the context used by the packet to modify the incoming codec.
- `net.minecraft.resources.DelegatingOps`
    - `$DelegateListBuilder` - A list builder that can be subclassed if needed.
    - `$DelegateRecordBuilder` - A record builder that can be subclassed if needed.
- `net.minecraft.server.bossevents.CustomBossEvent$Packed` - A record that backs the event information for serialization.
- `net.minecraft.server.commands.InCommandFunction` - A command function that takes in some input and returns a result.
- `net.minecraft.server.level`
    - `DistanceManager#forEachBlockTickingChucnks` - Applies the provided consumer for each chunk with block ticking enabled.
    - `ServerLevel`
        - `areEntitiesActuallyLoadedAndTicking` - Returns whether the entity manager is actually ticking and loading entities in the given chunk.
        - `tickThunder` - Ticks the thunger logic within a given level.
        - `anyPlayerCloseEnoughForSpawning` - Returns if a player is close enough to spawn the entity at the given location.
    - `ServerPlayer$RespawnConfig` - A record containing the respawn information for the player.
- `net.minecraft.util`
    - `AbstractListBuilder` - A ops list builder which boils the implementation down to three methods which initializes, appends, and builds the final list.
    - `Brightness`
        - `block` - Returns the block light from a packed value.
        - `sky` - Returns the sky light from a packed value.
    - `HashOps` - A dynamic ops that generates a hashcode for the data.
    - `ExtraCodecs`
        - `UNTRUSTED_URI` - A codec for a URI that is not trusted by the game.
        - `CHAT_STRING` - A codec for a string in a chat message.
        - `legacyEnum` - A codec that maps an enum to its output in `Enum#toString`.
    - `FileSystemUtil` - A utility for interacting with the file system.
    - `GsonHelper#encodesLongerThan` - Returns whether the provided element can be written in the specified number of characters.
    - `Unit#STREAM_CODEC` - A stream codec for a unit instance.
    - `Util`
        - `mapValues` - Updates the values of a map with the given function.
        - `mapValuesLazy` - Updates the values of a map with the given function, but each value is resolved when first accessed.
        - `growByHalf` - Returns an integer multiplied by 1.5, rounding down, clamping the value to some minimum and the max integer size.
- `net.minecraft.util.random.Weighted#map`, `WeightedList#map` - Transforms the stored object(s) to a new type.
- `net.minecraft.util.thread.ParallelMapTransform` - A helper that handles scheduling and batching tasks in parallel.
- `net.minecraft.world.effect.MobEffectInstance#withScaledDuration` - Constructs a new instance with the duration scaled by some float value.
- `net.minecraft.world.entity`
    - `AreaEffectCloud#setPotionDurationScale` - Sets the scale of how long the potion should apply for.
    - `DropChances` - A map of slots to probabilities indicating how likely it is for an entity to drop that piece of equipment.
    - `Entity`
        - `isInterpolating` - Returns whether the entity is interpolating between two steps.
        - `sendBubbleColumnParticles` - Spawns bubble column particles from the server.
        - `canSimulateMovement` - Whether the entity's movement can be simulated, usually from being the player.
        - `propagateFallToPassengers` - Propogates the fall damage of a vehicle to its passengers.
        - `lavaIgnite` - Ignites the entity for 15 seconds if not immune.
        - `clearFreeze` - Sets the number of ticks the entity is frozen for to 0.
        - `removeLatestMovementRecordingBatch` - Removes the last element from all movements performed this tick.
    - `InterpolationHandler` - A class meant to easily handle the interpolation of the position and rotation of the given entity as necessary.
    - `LivingEntity`
        - `getLuck` - Returns the luck of the entity for random events.
        - `getLastHurtByPlayer`, `setLastHurtByPlayer` - Handles the last player to hurt this entity.
        - `getEffectBlendFactor` - Gets the blend factor of an applied mob effect.
        - `applyInput` - Applies the entity's input as its AI, typically for local players.
        - `INPUT_FRICTION` - The scalar to apply to the movements of the entity.
- `net.minecraft.world.entity.animal.camel.Camel#checkCamelSpawnRules` - Checks if a camel can spawn at a particular position.
- `net.minecraft.world.entity.animal.sheep.SheepColorSpawnRules` - A class that contains the color spawn configurations for a sheep's wool when spawning within a given climate.
- `net.minecraft.world.entity.npc.Villager#createDefaultVillagerData` - Returns the default type and profession of the villager to use when no data is set.
- `net.minecraft.world.entity.player.Player`
    - `preventsBlockDrops` - Whether the player cannot drop any blocks on destruction.
    - `gameMode` - Returns the current game mode of the player.
    - `debugInfo` - Returns the common information about the player as a single string.
- `net.minecraft.world.inventory`
    - `ContainerSynchronizer#createSlot` - Creates a `RemoteSlot` that represents a slot on the opposite side.
    - `RemoteSlot` - A slot that represents the data on the opposing side, syncing when the data is not consistent. 
- `net.minecraft.world.item`
    - `EitherHolder#key` - Returns the resource key of the held registry object.
    - `Item#STREAM_CODEC`
    - `ItemStack`
        - `OPTIONAL_UNTRUSTED_STREAM_CODEC`
        - `MAP_CODEC`
        - `canDestroyBlock` - Returns whether this item can destroy the provided block state.
- `net.minecraft.world.item.alchemy.PotionContents#getPotionDescription` - Returns the description of the mob effect with some amplifier.
- `net.minecraft.world.item.crafting`
    - `Recipe#KEY_CODEC`
    - `TransmuteResult` - A recipe result object that represents an item, count, and the applied components.
- `net.minecraft.world.item.equipment.trim.ArmorTrim#layerAssetId` - Returns the location of the the trim asset.
- `net.minecraft.world.level`
    - `BlockGetter$BlockStepVisitor` - A consumer that takes in the current position and how many collisions within the desired path of travel.
    - `ColorMapColorUtil` - A helper for getting the color from a map given the biome's temperature, downfall, colormap, and default color.
    - `DryFoliageColor` - A color resolver for biomes with dry foliage.
    - `GameRules`
        - `getType` - Gets the game rule type from its key.
        - `keyCodec` - Creates the codec for the key of a game rule type.
    - `Level`
        - `isMoonVisible` - Returns wehther the moon is currently visible in the sky.
        - `getPushableEntities` - Gets all entities except the specified target within the provided bounding box.
        - `getClientLeafTintColor` - Returns the color of the leaf tint at the specified location.
        - `playPlayerSound` - Plays a sound to the current player on the client.
    - `LevelReader#getHeight` - Returns the height of the map at the given position.
    - `NaturalSpawner#INSCRIBED_SQUARE_SPAWN_DISTANCE_CHUNK` - Provides the minimum distance that the player is close enough for spawning to occur.
- `net.minecraft.world.level.biome`
    - `Biome`   
        - `getDryFoliageColor`, `getDryFoliageColorFromTexture` - Gets the dry foliage color of the biome, either from the effects or from the climate settings.
    - `BiomeSpecialEffects#getDryFoliageColorOverride`, `$Builder#dryFoliageColorOverride` - Returns the default dry foliage color when not pulling from a colormap texture.
- `net.minecraft.world.level.block`
    - `BaseFireBlock#fireIgnite` - Lights an entity on fire.
    - `Block`
        - `UPDATE_SKIP_BLOCK_ENTITY_SIDEEFFECTS` - A flag that skips all potential sideeffects when updating a block entity.
        - `UPDATE_SKIP_ALL_SIDEEFFECTS` - A flag that skips all sideeffects by skipping certain block entity logic, supressing drops, and updating the known shape.
        - `UPDATE_SKIP_ON_PLACE` - A flag that skips calling `BlockState#onPlace` when set.
    - `BonemealableBlock#hasSpreadableNeighbourPos`, `findSpreadableNeighbourPos` - Handles finding other positions that the vegetation can spread to on bonemeal.
    - `CactusFlowerBlock` - A flower that grows on a cactus.
    - `FireflyBushBlock` - A bush that spawns firefly particles around it.
    - `SandBlock` - A colored sand block that can play ambient sounds.
    - `SegmentableBlock` - A block that can typically be broken up into segments with unique sizes and placements.
    - `ShortDryGrassBlock` - A single grass block that has been dried out.
    - `TallDryGrassBlock` - A double grass block that has been dried out.
    - `TerracottaBlock` - A terracotta block that can play ambient sounds.
    - `TintParticleLeavesBlock` - A leaves block whose particles are tinted.
    - `UntintedParticleLeavesBlock` - A leaves block whose particles are not tinted.
    - `VegetationBlock` - A block that represents some sort of vegetation that can propogate light and need some sort of farmland or dirt to survive.
- `net.minecraft.world.level.block.entity.StructureBlockEntity#isStrict`, `setStrict` - Sets strict mode when generating structures.
- `net.minecraft.world.level.block.sounds.AmbientDesertBlockSoundsPlayer` - A helper to play sounds for a given block, typically during `animateTick`.
- `net.minecraft.world.level.block.state.BlockBehaviour#getEntityInsideCollisionShape` - Gets the collision shape of the block when the entity is within it.
- `net.minecraft.world.level.border.WorldBorder`
    - `closestBorder` - Returns a list of the closest borders to the player based on their horizontal direction.
    - `$DistancePerDirection` - A record containing the distance from the entity of the world border in a given direction.
- `net.minecraft.world.level.chunk.status.ChunkStatus#CODEC`
- `net.minecraft.world.level.entity.PersistentEntitySectionManager#isTicking` - Returns whether the specified chunk is currently ticking.
- `net.minecraft.world.level.levelgen.Heightmap$Types#STREAM_CODEC`
- `net.minecraft.world.level.levelgen.feature`
    - `AbstractHugeMushroomFeature#placeMushroomBlock` - Places a mushroom block that specified location, replacing a block if it can.
    - `FallenTreeFeature` - A feature that generates flane trees with a stump of given lengths.
    - `TreeFeature#getLowestTrunkOrRootOfTree` - Retruns the lowest block positions of the tree decorator.
- `net.minecraft.world.level.levelgen.feature.configurations.FallenTreeConfiguration` - A configuration for fallen trees with stumps.
- `net.minecraft.world.level.levelgen.feature.treedecorators`
    - `AttachedToLogsDecorator` - A decorator that attaches a random block to a given direction on a log with a set probability.
    - `PlaceOnGroundDecorator` - A decorator that places the tree on a valid block position.
- `net.minecraft.world.level.levelgen.structure.pools`
    - `ListPoolElement#getElements` - Returns the elements of the structure pool.
    - `SinglePoolElement#getTemplateLocation` - Returns the location of the template used by the element.
    - `StructureTemplatePool#getTemplates` - Returns a list of elements with their weights.
- `net.minecraft.world.level.levelgen.structure.structures.JigsawStructure`
    - `getStartPool` - Returns the starting pool of the jigsaw to generate.
    - `getPoolAliases` - Returns all pools used by the jigsaw.
- `net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate#getDefaultJointType` - Returns the default join type between two jigsaw pieces if none is specified or an error occurs during loading.
- `net.minecraft.world.level.material.Fluid#getAABB`, `FluidState#getAABB` - Returns the bounding box of the fluid.
- `net.minecraft.world.scores`
    - `Objective#pack`, `$Packed` - Handles the serializable form of the objective data.
    - `PlayerTeam#pack`, `$Packed` - Handles the serializable form of the player team data.
    - `Scoreboard`
        - `loadPlayerTeam`, `loadObjective` - Loads the data from the packed object.
        - `$PackedScore` - Handles the serializable form of the scoreboard data.
- `net.minecraft.world.level.storage.loot.LootTable#KEY_CODEC`
- `net.minecraft.world.phys`
    - `AABB$Builder` - A builder for constructing a bounding box by providing the vectors within.
    - `Vec2#CODEC`
- `net.minecraft.world.phys.shapes.CollisionContext`
    - `placementContext` - Constructs the context when placing a block from its item.
    - `isPlacement` - Returns whether the context is being used for placing a block.
- `net.minecraft.world.ticks.TickPriority#CODEC`

### List of Changes

- `net.minecraft.client.Screenshot` is now a utility instead of an instance class, meaning all instance methods are removed
    - `takeScreenshot(RenderTarget)` -> `takeScreenshot(RenderTarget, Consumer<NativeImage>)`, not returning anything
- `net.minecraft.client.multiplayer`
    - `ClientChunkCache#replaceWithPacketData` now takes in a `Map<Heightmap$Types, long[]>` instead of a `CompoundTag`
    - `MultiPlayerGameMode#hasInfiniteItems` -> `net.minecraft.world.entity.LivingEntity#hasInfiniteMaterials`
    - `ClientPacketListener#markMessageAsProcessed` now takes in a `MessageSignature` instead of a `PlayerChatMessage`
- `net.minecraft.client.multiplayer.chat.ChatListener#handleChatMessageError` now takes in a nullable `MessageSignature`
- `net.minecraft.client.player`
    - `ClientInput#leftImpulse`, `forwardImpulse` -> `moveVector`, now protected
    - `LocalPlayer#spinningEffectIntensity`, `oSpinningEffectIntensity` -> `portalEffectIntensity`, `oPortalEffectIntensity`
- `net.minecraft.client.renderer.LevelRenderer#getLightColor(BlockAndTintGetter, BlockState, BlockPos)` -> `getLightColor(LevelRenderer$BrightnessGetter, BlockAndTintGetter, BlockState, BlockPos)`
- `net.minecraft.client.renderer.blockentity.BlockEntityRenderer#render` now takes in a `Vec3` representing the camera's position
- `net.minecraft.client.renderer.chunk.SectionRenderDispatcher`
    - `$RenderSection`
        - `getOrigin` -> `getRenderOrigin`
        - `reset` is now public
        - `releaseBuffers` is removed
    - `$CompileTask#getOrigin` -> `getRenderOrigin`
- `net.minecraft.client.renderer.entity`
    - `DonkeyRenderer` now takes in a `DonekyRenderer$Type` containing the textures, model layers, and equipment information
    - `ItemEntityRenderer#renderMultipleFromCount` now has an overload that takes in the model bounding box
    - `UndeadHorseRenderer` now takes in a `UndeadHorseRenderer$Type` containing the textures, model layers, and equipment information
- `net.minecraft.client.renderer.entity.layers`
    - `EquipmentLayerRenderer$TrimSpriteKey#textureId` -> `spriteId`
    - `VillagerProfessionLayer#getHatData` now takes in a map of resource keys to metadata sections and swaps the registry and value for a holder instance
- `net.minecraft.client.renderer.item`
    - `ConditionalItemModel` now takes in a `ItemModelPropertyTest` instead of a `ConditionalItemModelProperty`
    - `SelectItemModel` now takes in a `$ModelSelector` instead of an object map
- `net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty` now implements `ItemModelPropertyTest`
    - `ItemModelPropertyTest` holds the `get` method previously within `ConditionalItemModelProperty`
- `net.minecraft.commands.arguments`
    - `ComponentArgument`
        - `ERROR_INVALID_JSON` -> `ERROR_INVALID_COMPONENT`
        - `getComponent` -> `getRawComponent`
    - `ResourceKeyArgument#getRegistryKey` is now public
    - `StyleArgument#ERROR_INVALID_JSON` -> `ERROR_INVALID_STYLE`
- `net.minecraft.commands.arguments.item`
    - `ComponentPredicateParser$Context#createComponentTest`, `createPredicateTest` now takes in a `Dynamic` instead of a `Tag`
    - `ItemPredicateArgument`
        - `$ComponentWrapper#decode` now takes in a `Dynamic` instead of a `RegistryOps`, `Tag` pair
        - `$PredicateWrapper#decode` now takes in a `Dynamic` instead of a `RegistryOps`, `Tag` pair
- `net.minecraft.core`
    - `BlockMath`
        - `VANILLA_UV_TRANSFORM_LOCAL_TO_GLOBAL`, `VANILLA_UV_TRANSFORM_GLOBAL_TO_LOCAL` is now private
        - `getUVLockTransform` -> `getFaceTransformation`
    - `Direction#rotate` now takes in a `Matrix4fc` instead of a `Matrix4f`
    - `Rotations` is now a record
- `net.minecraft.data.loot.BlockLootSubProvider#createPetalDrops` -> `createSegmentedBlockDrops`
- `net.minecraft.network`
    - `FriendlyByteBuf`
        - `writeLongArray`, `readLongArray` now have static delegates which take in the `ByteBuf` and `*Fixed*` versions for fixed size arrays
    - `ProtocolInfo$Unbound` -> `$Details`, `net.minecraft.network.protocol.SimpleUnboundProtocol`, `net.minecraft.network.protocol.UnboundProtocol`; not one-to-one
        - `#bind` -> `net.minecraft.network.protocol.SimpleUnboundProtocol#bind`, `UnboundProtocol#bind`; not one-to-one
    - `SkipPacketException` is now an interface instead of a subclass of `EncoderException`
- `net.minecraft.network.chat`
    - `ComponentSerialization#flatCodec` -> `flatRestrictedCodec`
    - `LastSeenMessages$Update` now takes in a byte representing the checksum value
    - `LastSeenMessagesValidator`
        - `applyOffset` now returns nothing and can throw a `$ValidationException`
        - `applyUpdate` now returns the raw messages and can throw a `$ValidationException`
- `net.minecraft.network.codec.StreamCodec#composite` now has an overload for nine parameters
- `net.minecraft.network.protocol.ProtocolInfoBuilder` now takes in a third generic representing how to modify the provided codec.
    - `addPacket` now has an overload that takes in a `CodecModifier`
    - `build` -> `buildUnbound`, not one-to-one
    - `protocol`, `serverboundProtocol`, `clientboundProtocol` now returns a `SimpleUnboundProtocol`
- `net.minecraft.network.protocol.ConfigurationProtocols` now contain `SimpleUnboundProtocol` constants
- `net.minecraft.network.protocol.game`
    - `ClientboundContainerSetContentPacket` is now a record
    - `ClientboundMoveEntityPacket#getyRot`, `getxRot` -> `getYRot`, `getXRot`
    - `ClientboundPlayerChatPacket` now takes in a global index for the chat message
    - `ClientboundLevelChunkPacketdata#getHeightmaps` now returns a `Map<Heightmap.Types, long[]>`
    - `ClientboundUpdateAdvancementsPacket` now takes in a boolean representing whether to show the adavncements as a toast
    - `GameProtocols` constants are now either `SimpleUnboundProtocol`s or `UnboundProtocol`s
    - `ServerboundContainerClickPacket` is now a record
    - `ServerboundMovePlayerPacket$Pos`, `$PosRot` now has an overload that takes in a `Vec3` for the position
    - `ServerboundSetStructureBlockPacket` now takes in an additional boolean representing whether the structure should be generated in strict mode
- `net.minecraft.network.protocol.handshake.HandshakeProtocols#SERVERBOUND_TEMPLATE` is now a `SimpleUnboundProtocol`
- `net.minecraft.network.protocol.login.LoginProtocols#SERVERBOUND_TEMPLATE` constants are now `SimpleUnboundProtocol`s
- `net.minecraft.network.protocol.status.StatusProtocols#SERVERBOUND_TEMPLATE` constants are now `SimpleUnboundProtocol`s
- `net.minecraft.server.PlayerAdvancements#flushDirty` now takes in a boolean that represents whether the advancements show display as a toast
- `net.minecraft.server.bossevents.CustomBossEvent`
    - `save` -> `pack`, not one-to-one
    - `load` now takes in the id and the packed variant to unpack
- `net.minecraft.server.level`
    - `DistanceManager`
        - `hasPlayersNearby` now returns a `TriState`
        - `forEachBlockTickingChunks` -> `forEachEntityTickingChunk`, not one-to-one
    - `ServerEntity` now takes in a consumer for broadcasting a packet to all players but those in the ignore list
    - `ServerLevel`
        - `getForcedChunks` -> `getForceLoadedChunks`
        - `isPositionTickingWithEntitiesLoaded` is now public
        - `isNaturalSpawningAllowed` -> `canSpawnEntitiesInChunk`, `BlockPos` variant is removed
    - `ServerPlayer`
        - `getRespawnPosition`, `getRespawnAngle`, `getRespawnDimension`, `isRespawnForced` -> `getRespawnConfig`, not one-to-one
        - `setRespawnPosition` now takes in a `$RespawnConfig` instead of the individual respawn information
        - `loadAndSpawnParentVehicle`, `loadAndSpawnEnderpearls` now takes in a `CompoundTag` without the optional wrapping\
- `net.minecraft.server.network.ServerGamePacketListenerImpl` now implements `GameProtocols$Context`
- `net.minecraft.sounds.SoundEvents` have the following sounds now `Holder` wrapped:
    - `ITEM_BREAK`
    - `SHIELD_BLOCK`, `SHIELD_BREAK`,
    - `WOLF_ARMOR_BREAK`
- `net.minecraft.util`
    - `Brightness`
        - `FULL_BRIGHT` is now final
        - `pack` now has a static overload that takes in the block and sky light.
    - `ExtraCodecs#MATRIX4f` now is a `Codec<Matrix4fc>`
    - `Util#makeEnumMap` returns the `Map` superinstance rather than the specific `EnumMap`
- `net.minecraft.util.parsing.packrat.commands.TagParseRule` now takes in a generic for the tag type
    - The construct is now public, taking in a `DynamicOps`
- `net.minecraft.util.profiling`
    - `ActiveProfiler` now takes in a `BooleanSupplier` instead of a boolean
    - `ContinuousProfiler` now takes in a `BooleanSupplier` instead of a boolean
- `net.minecraft.util.worldupdate.WorldUpgrader` now takes in the current `WorldData`
- `net.minecraft.world`
    - `BossEvent$BossBarColor`, `$BossBarOverlay` now implements `StringRepresentable`
    - `Container` now implements `Iterable<ItemStack>`
- `net.minecraft.world.effect`
    - `MobEffect`
        - `getBlendDurationTicks` -> `getBlendInDurationTicks`, `getBlendOutDurationTicks`, `getBlendOutAdvanceTicks`; not one-to-one
        - `setBlendDuration` now has an overload that takes in three integers to set the blend in, blend out, and blend out advance ticks
    - `MobEffectInstance#tick` -> `tickServer`, `tickClient`; not one-to-one
- `net.minecraft.world.entity`
    - `Entity`
        - `cancelLerp` -> `InterpolationHandler#cancel`
        - `lerpTo` -> `moveOrInterpolateTo`
        - `lerpTargetX`, `lerpTargetY`, `lerpTargetZ`, `lerpTargetXRot`, `lerpTargetYRot` -> `getInterpolation`
        - `onAboveBubbleCol` -> `onAboveBubbleColumn` now takes in a `BlockPos` for the bubble column particles spawn location
            - Logic delegates to the protected static `handleOnAboveBubbleColumn`
        - `isControlledByOrIsLocalPlayer` -> `isLocalInstanceAuthoritative`, now final
        - `isControlledByLocalInstance` -> `isLocalClientAuthoritative`, now protected
        - `isControlledByClient` -> `isClientAuthoritative`
        - `fallDistance`, `causeFallDamage` is now a double
        - `absMoveto` -> `absSnapTo`
        - `absRotateTo` -> `asbSnapRotationTo`
        - `moveTo` -> `snapTo`
        - `sendBubbleColumnParticles` is now static, taking in the `Level`
        - `onInsideBubbleColumn` logic delegates to the protected static `handleOnInsideBubbleColumn`
    - `EntityType`
        - `POTION` -> `SPLASH_POTION`, `LINGERING_POTION`, not one-to-one
        - `$EntityFactory#create` can now return a null instance
    - `ExperienceOrb#value` -> `DATA_VALUE`
    - `ItemBasedSteering` no longer takes in the accessor for having a saddle
    - `LivingEntity`
        - `lastHurtByPlayerTime` -> `lastHurtByPlayerMemoryTime`
        - `lerpSteps`, `lerpX`, `lerpY`, `lerpZ`, `lerpYRot`, `lerpXRot` -> `interpolation`, not one-to-one
        - `isAffectedByFluids` is now public
        - `removeEffectNoUpdate` is now final
        - `tickHeadTurn` now returns nothing
        - `canDisableShield` -> `canDisableBlocking`, now set via the `WEAPON` data component
        - `calculateFallDamage` now takes in a double instead of a float
    - `Mob`
        - `handDropChances`, `armorDropChances`, `bodyArmorDropChance` -> `dropChances`, not one-to-one
        - `getEquipmentDropChance` -> `getDropChances`, not one-to-one
- `net.minecraft.world.entity.ai.Brain#addActivityWithConditions` now has an overload that takes in an integer indiciating the starting priority
- `net.minecraft.world.entity.ai.behavior`
    - `LongJumpToRandomPos$PossibleJump` is now a record
    - `VillagerGoalPackages#get*Package` now takes in a holder-wrapped profession
- `net.minecraft.world.entity.ai.gossip.GossipContainer#store`, `update` -> `clear`, `putAll`, `copy`; not one-to-one
- `net.minecraft.world.entity.animal`
    - `Pig` is now a `VariantHolder`
    - `Sheep` -> `.sheep.Sheep`
    - `WaterAnimal#handleAirSupply` now takes in a `ServerLevel`
- `net.minecraft.world.entity.animal.axolotl.Axolotl#handleAirSupply` now takes in a `ServerLevel`
- `net.minecraft.world.entity.monster.ZombieVillager#setGossips` now takes in a `GossipContainer`
- `net.minecraft.world.entity.monster.warden.WardenSpawnTracker` now has an overload which sets the initial parameters to zero
- `net.minecraft.world.entity.npc`
    - `Villager` now takes in either a key or a holder of the `VillagerType`
        - `setGossips` now takes in a `GossipContainer`
    - `VillagerData` is now a record
        - `set*` -> `with*`
    - `VillagerProfession` now takes in a `Component` for the name
    - `VillagerTrades`
        - `TRADES` now takes in a resource key as the key of the map
            - This is similar for all other type specific trades
        - `$FailureItemListing` is now private
- `net.minecraft.world.entity.player.Player`
    - `stopFallFlying` -> `LivingEntity#stopFallFlying`
    - `isSpectator`, `isCreative` no longer abstract in the `Player` class
- `net.minecraft.world.entity.projectile.ThrownPotion` -> `AbstractThrownPotion`, implemented in `ThrownLingeringPotion` and `ThrownSplashPotion`
- `net.minecraft.world.entity.raid.Raid(int, ServerLevel, BlockPos)` -> `Raid(BlockPos, Difficulty)`
    - `tick`, `addWaveMob` now takes in the `ServerLevel`
- `net.minecraft.world.entity.vehicle`
    - `AbstractMinecart#setDisplayBlockState` -> `setCustomDisplayBlockState`
    - `MinecartBehavior` 
        - `cancelLerp` -> `InterpolationHandler#cancel`
        - `lerpTargetX`, `lerpTargetY`, `lerpTargetZ`, `lerpTargetXRot`, `lerpTargetYRot` -> `getInterpolation`
    - `MinecartTNT#primeFuse` now takes in the `DamageSource` cause
- `net.minecraft.world.inventory`
    - `AbstractContainerMenu`
        - `setRemoteSlotNoCopy` -> `setRemoteSlotUnsafe`, not one-to-one
        - `setRemoteCarried` now takes in a `HashedStack`
    - `ClickType` now takes in an id for its representations
    - `ContainerSynchronizer#sendInitialData` now takes in a list of stacks rather than a `NonNullList`
- `net.minecraft.world.item`
    - `EitherHolder` now takes in an `Either` instance rather than just an `Optional` holder and `ResourceKey`
    - `Item`
        - `canAttackBlock` -> `canDestroyBlock`
        - `hurtEnemy` no longer returns anything
        - `onCraftedBy` no longer takes in a separate `Level` instance, now relying on the one provided by the `Player`
    - `ItemStack`
        - `validateStrict` is now public
        - `onCraftedBy` no longer takes in a separate `Level` instance, now relying on the one provided by the `Player`
    - `MapItem`
        - `create` now takes in a `ServerLevel` instead of a `Level`
        - `lockMap` is now private
    - `ThrowablePotionItem` is now abstract, containing two methods to create the `AbstractThrownPotion` entity
    - `WrittenBookItem#resolveBookComponents` -> `WrittenBookContent#resolveForItem`
- `net.minecraft.world.item.alchemy.PotionContents` now implements `TooltipProvider`
    - `forEachEffect`, `applyToLivingEntity` now takes in a float representing a scalar for the duration
- `net.minecraft.world.item.component.WrittenBookContent` now implements `TooltipProvider`
- `net.minecraft.world.item.crafting`
    - `SmithingRecipe#baseIngredient` now returns an `Ingredient`
    - `SmithingTransformRecipe` now takes in a `TransmuteResult` instead of an `ItemStack` and an `Ingredient` for the base
    - `SmithingTrimRecipe` now takes in `Ingredient`s instead of `Optional` wrapped entries along with a `TrimPattern` holder
    - `TransmuteRecipe` now takes in a `TransmuteResult` instead of an `Item` holder
- `net.minecraft.world.item.crafting.display.SlotDisplay$SmithingTrimDemoSlotDisplay` now takes in a `TrimPattern` holder
- `net.minecraft.world.item.enchantment.EnchantmentInstance` is now a record
- `net.minecraft.world.level`
    - `BlockGetter#boxTraverseBlocks` -> `forEachBlockIntersectedBetween`, not one-to-one
    - `CustomSpawner#tick` no longer returns anything
    - `GameRules$Type` now takes in a value class
    - `Level`
        - `onBlockStateChange` -> `updatePOIOnBlockStateChange`
        - `isDay` -> `isBrightOutside`
        - `isNight` -> `isDarkOutside`
        - `setMapData` -> `net.minecraft.server.level.ServerLevel#setMapData`
        - `getFreeMapId` -> `net.minecraft.server.level.ServerLevel#getFreeMapId`
    - `LevelAccessor#blockUpdated` -> `updateNeighborsAt`
- `net.minecraft.world.level.biome.MobSpawnSettings$SpawnerData` is now a record
- `net.minecraft.world.level.block`
    - `AttachedStemBlock` now extends `VegetationBlock`
    - `AzaleaBlock` now extends `VegetationBlock`
    - `Block#fallOn` now takes a double for the fall damage instead of a float
    - `BushBlock` now extends `VegetationBlock` and implements `BonemealableBlock`
    - `ColoredFallingBlock#dustColor` is now protected
    - `CropBlock` now extends `VegetationBlock`
    - `DeadBushBlock` -> `DryVegetationBlock`
    - `DoublePlantBlock` now extends `VegetationBlock`
    - `FallingBlock#getDustColor` is now abstract
    - `FlowerBedBlock` now extends `VegetationBlock`
    - `FlowerBlock` now extends `VegetationBlock`
    - `FungusBlock` now extends `VegetationBlock`
    - `LeafLitterBlock` now extends `VegetationBlock`
    - `LeavesBlock` is now abstract, taking in the chance for a particle to spawn
        - Particles are spawned via `spawnFallingLeavesParticle`
    - `MangroveLeavesBlock` now extends `TintedParticleLeavesBlock`
    - `MushroomBlock` now extends `VegetationBlock`
    - `NetherSproutsBlock` now extends `VegetationBlock`
    - `NetherWartBlock` now extends `VegetationBlock`
    - `ParticleLeavesBlock` -> `LeafLitterBlock`
    - `PinkPetalsBlock` -> `FlowerBedBlock`
    - `RootsBlock` now extends `VegetationBlock`
    - `Rotation` now has an index used for syncing across the network
    - `SaplingBlock` now extends `VegetationBlock`
    - `SeagrassBlock` now extends `VegetationBlock`
    - `SeaPickleBlock` now extends `VegetationBlock`
    - `StemBlock` now extends `VegetationBlock`
    - `SweetBerryBushBlock` now extends `VegetationBlock`
    - `TallGrassBlock` now extends `VegetationBlock`
    - `TntBlock#prime` now returns whether the primed tnt was spawned.
    - `WaterlilyBlock` now extends `VegetationBlock`
- `net.minecraft.world.level.block.entity`
    - `BlockEntity`
        - `parseCustomNameSafe` now takes in a nullable `Tag` instead of a string
        - `getPosFromTag` now takes in the `ChunkPos`
        - `$ComponentHolder#COMPONENTS_CODEC` is now a `MapCodec`
    - `BLockEntityType#create` is no longer nullable
- `net.minecraft.world.level.block.entity.trialspawner.TrialSpawner#codec` now returns a `MapCodec`
- `net.minecraft.world.level.block.state.StateHolder`
    - `getNullableValue` is now private
    - `hasProperty` no longer contains a generic
- `net.minecraft.world.level.chunk`
    - `ChunkAccess#setBlockState` now takes in the block flags instead of a boolean, and has an overload to update all set
    - `LevelChunk#replaceWithPacketData` now takes in a `Map<Heightmap$Types, long[]>` instead of a `CompoundTag`
- `net.minecraft.world.level.chunk.storage.SerializableChunkData#getChunkTypeFromTag` -> `getChunkStatusFromTag`, not one-to-one
- `net.minecraft.world.level.gameevent.vibrations.VibrationSystem#DEFAULT_VIBRATION_FREQUENCY` -> `NO_VIBRATION_FREQUENCY`
- `net.minecraft.world.level.levelgen.feature.TreeFeature#isVine` is now public
- `net.minecraft.world.level.levelgen.structure.pools.alias`
    - `Direct` -> `DirectPoolAlias`
    - `Random` -> `RandomPoolAlias`
    - `RandomGroup` -> `RandomGroupPoolAlias`
- `net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate$JigsawBlockInfo` now takes in a `ResourceKey` to the `StructureTemplatePool` instead of a raw `ResourceLocation`
- `net.minecraft.world.level.saveddata.maps.MapFrame` is now a record
    - `save`, `load` -> `CODEC`, not one-to-one
- `net.minecraft.world.level.storage.loot.functions.SetWrittenBookPagesFunction#PAGE_CODEC` -> `WrittenBookContent#PAGES_CODEC`
- `net.minecraft.world.scores`
    - `Score#write` -> `CODEC`, not one-to-one
    - `Scoreboard`
        - `savePlayerScores` -> `packPlayerScores`, not one-to-one
        - `loadPlayerScores` -> `loadPlayerScore`, not one-to-one
    - `Team$CollisionRule`, `$Visibility` are now `StringRepresentable`
- `net.minecraft.world.phys.shapes.EntityCollisionContext` now takes in a boolean representing if it is used for placing a block
- `net.minecraft.world.ticks.SavedTick`
    - `loadTick`, `saveTick`, `save` -> `codec`, not one-to-one
    - `loadTickList` -> `filterTickListForChunk`, not one-to-one

### List of Removals

- `com.mojang.blaze3d.vertex.BufferUploader`
- `net.minecraft.core.Rotations#getWrapped*`
- `net.minecraft.network.chat.ComponentSerialization#FLAT_CODEC`
- `net.minecraft.network.protocol.game`
    - `ClientboundAddExperimentOrbPacket`
    - `ClientGamePacketListener#handleAddExperienceOrb`
- `net.minecraft.resources.ResourceLocation$Serializer`
- `net.minecraft.server.network.ServerGamePacketListenerImpl#addPendingMessage`
- `net.minecraft.world`
    - `BossEvent$BossBarColor#byName`, `$BossBarOverlay#byName`
    - `Clearable#tryClear`
- `net.minecraft.world.effect.MobEffectInstance#save`, `load`
- `net.minecraft.world.entity`
    - `Entity`
        - `isInBubbleColumn`
        - `isInWaterRainOrBubble`, `isInWaterOrBubble`
        - `newDoubleList`, `newFloatList`
        - `recordMovementThroughBlocks`
    - `EntityEvent#ATTACK_BLOCKED`, `SHIELD_DISABLED`
    - `ItemBasedSteering`
        - `addAdditionalSaveData`, `readAdditionalSaveData`
        - `setSaddle`, `hasSadddle`
    - `LivingEntity`
        - `timeOffs`, `rotOffs`
        - `rotA`
        - `oRun`, `run`
        - `animStep`, `animStep0`
        - `appliedScale`
        - `canBeNameTagged`
    - `Mob`
        - `DEFAULT_EQUIPMENT_DROP_CHANCE`
        - `PRESERVE_ITEM_DROP_CHANCE_THRESHOLD`, `PRESERVE_ITEM_DROP_CHANCE`
    - `NeutralMob#setLastHurtByPlayer`
    - `PositionMoveRotation#ofEntityUsingLerpTarget`
- `net.minecraft.world.entity.ai.attributes.AttributeModifier#save`, `load`
- `net.minecraft.world.entity.animal`
    - `Dolphin#setTreasurePos`, `getTreasurePos`
    - `Fox$Variant#byName`
    - `MushroomCow$Variant#byName`
    - `Panda$Gene#byName`
    - `Salmon$Variant#byName`
    - `Turtle`
        - `getHomePos`
        - `setTravelPos`, `getTravelPos`
        - `isGoingHome`, `setGoingHome`
        - `isTravelling`, `setTravelling`
- `net.minecraft.world.entity.animal.armadillo.Armadillo$ArmadilloState#fromName`
- `net.minecraft.world.entity.npc.VillagerTrades#EXPERIMENTAL_WANDERING_TRADER_TRADES`
- `net.minecraft.world.entity.projectile.AbstractArrow#getBaseDamage`
- `net.minecraft.world.entity.raid.Raid`
    - `getLevel`, `getId`
    - `save`
- `net.minecraft.world.entity.vehicle.AbstractMinecart#hasCustomDisplay`, `setCustomDisplay`
- `net.minecraft.world.item.ItemStack#parseOptional`, `saveOptional`
- `net.minecraft.world.item.equipment.trim.TrimPattern#templateItem`
- `net.minecraft.world.level.Level#updateNeighborsAt(BlockPos, Block)`
- `net.minecraft.world.level.block.entity`
    - `CampfireBlockEntity#dowse`
    - `PotDecorations#save`, `load`
- `net.minecraft.world.level.levelgen.BelowZeroRetrogen#read`
- `net.minecraft.world.level.levelgen.structure.structures.RuinedPortalPiece$VerticalPlacement#byName`
- `net.minecraft.world.level.saveddata.maps.MapBanner#LIST_CODEC`
- `net.minecraft.world.scores.Team`
    - `$CollisionRule#byName`
    - `$Visibility#getAllNames`, `byName`
- `net.minecraft.world.ticks.LevelChunkTicks#save`, `load`
