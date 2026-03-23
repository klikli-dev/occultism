# Minecraft 1.21.8 -> 1.21.9 Mod Migration Primer

This is a high level, non-exhaustive overview on how to migrate your mod from 1.21.8 to 1.21.9. This does not look at any specific mod loader, just the changes to the vanilla classes. All provided names use the official mojang mappings.

This primer is licensed under the [Creative Commons Attribution 4.0 International](http://creativecommons.org/licenses/by/4.0/), so feel free to use it as a reference and leave a link so that other readers can consume the primer.

If there's any incorrect or missing information, please file an issue on this repository or ping @ChampionAsh5357 in the Neoforged Discord server.

Thank you to:

- @Soaryn for pointing out some non-recommended rendering usages
- @Deximus-Maximus for some typos

## Pack Changes

There are a number of user-facing changes that are part of vanilla which are not discussed below that may be relevant to modders. You can find a list of them on [Misode's version changelog](https://misode.github.io/versions/?id=1.21.9&tab=changelog).

## The Debugging Overhaul

The entirety of the debug system has been completely overhauled, from exposing the internal debugging tools to the debug screen. This documentation aims to provide a high level overview for handling your own debug screen and renderer additions.

### Debug Renderers

Vanilla now allows users to see the debug renderers provided by the internal API by enabling them through the JVM properties via `-DMC_DEBUG_ENABLED` with whatever other desired flags. Users can take advantage of these exposed features to handle their own renderers through the vanilla provided pipeline. This overview will explain via patching the existing renderers and subscribers as needed, but these can generally be set up wherever needed. The benefit vanilla provides is its integration into existing objects (e.g. entities, block entities) and the general synchronization across the network. Of course, you can always use a simple `boolean` instead. After all, although the flags in `SharedConstants` are final, they are still checked every tick.

#### Subscribe to Debuggers

In most cases, information that you want to render and debug are stored on the server side. Sometimes, the information in question will be synced on the client, but in most cases it is usually some kind of partial state only necessary for rendering.

```java
// An example object on the server
public record ExampleObject(Block held, int count) {}

// An example on the client
// Count is not used for rendering, only for server logic
public class ExampleRenderState {
    Block held;
}
```

Therefore, if we want to see the additional data from the server, we need some method to not only synchronize it to the client, but also update it whenever the value changes. To do so, vanilla provides `DebugSubscription`s: a class that stores the information required to sync an object if it has changed. The constructor contains two fields: the `StreamCodec` to sync the object across the network, and an optional `int` that, when greater than zero, will purge the synced value from the client if there were no more updates within the specified time.

To handle the logic associated with synchronization, the server makes use of `TrackingDebugSynchronizer`s to handle player listeners and sync the object when necessary, and `LevelDebugSynchronizers` to handle the general tracking and ticking of the synchronizers. This data is then sent to the `ClientDebugSubscriber` for storage and the `DebugRenderer` for rendering. Note that clients can only see the debug information if they are either the owner of a singleplayer world or is an operator of the server. Additionally, the client can only request subscriptions that are added to the set provided by `ClientDebugSubscriber#requestedSubscriptions`.

`DebugSubscription`s must be registered to `BuiltInRegistries#DEBUG_SUBSCRIPTION`:

```java
public static final DebugSubscription<ExampleObject> EXAMPLE_OBJECt = Registry.register(
    BuiltInRegistries.DEBUG_SUBSCRIPTION
    ResourceLocation.withNamespaceAndPath("examplemod", "example_object"),
    new DebugSubscription<>(
        // The stream codec to sync the example object
        StreamCodec.composite(
            ByteBufCodecs.registry(Registries.BLOCK), ExampleObject::block,
            ByteBufCodecs.VAR_INT, ExampleObject::count,
            ExampleObject::new
        ),
        // The maximum number of ticks between updates
        //   before the data is purged from the client
        // Set to zero if it should never expire
        0
    )
);
```

To be able to check for updates properly, the object used must correctly implement `hashCode` and `equals`, not relying on the object identity.

#### Debug Sources

So, how do we tell the synchronizer to track and update our `DebugSubscription`? Well, you can extend `TrackingDebugSynchronizer` or use its subclasses and implement the tracking and syncing logic yourself, either by patching `LevelDebugSynchronizers` or creating your own. However, if the data you would like to track is directly attached to a `LevelChunk`, `Entity`, or `BlockEntity` and can be updated from their associated server object, you can make use of the `DebugValueSource`.

`DebugValueSource` is a way to register `DebugSubscription`s as a `TrackingDebugSynchronizer$SourceSynchronizer`. This will poll and send updates to every player tracking the source with the subscription enabled every tick. Registering a `DebugSubscription` is done via `DebugValueSource#registerDebugValues`, taking in the server level and the `$Registration` interface. The registration is then handled via `$Registration#register` by passing in the subscription and a supplier to construct the subscription value.

```java
// Assume we have some ExampleObject exampleObject in the below classes

// For some BlockEntity, Entity, or LevelChunk subclass
@Override
public void registerDebugValues(ServerLevel level, DebugValueSource.Registration registrar) {
    super.registerDebugValues(level, registrar);
    // Register our subscription
    registrar.register(
        // The subscription
        EXAMPLE_OBJECT,
        // The supplied subscription object
        () -> this.exampleObject
    );
}
```

#### Rendering the Debug Information

Once the information has been synced to the client and stored within `ClientDebugSubscriber` (assuming you are using the above method), we now need to render that information to the screen. This is typically handled through `DebugRenderer#render`, which checks the enabled debug renderers via `refreshRendererList` before running the associated renderers. Technically, it doesn't particularly matter where as the data can be obtained at any point in the render process, but this will assume you are patching `refreshRendererList` to add your own renderer to either the opaque or translucent renderers.

All renderers implement `DebugRenderer$SimpleDebugRenderer` to call `render`, which provides the current `PoseStack`, buffer source, camera XYZ, and `Frustum`. In addition, vanilla passes in a `DebugValueAccess` via `Connection#createDebugValueAccess` to get the synched debug information from the `ClientDebugSubscriber`. `DebugRenderer` provides simple methods to render text or boxes in specific locations using `render*`.

The `DebugValueAccess` contains two types of methods: `get*Value` to obtain the debug object for that specific source (e.g. position, entity); and `forEach*`, which loops through all sources sending out the debug object. Which you use depends on which source you registered your `DebugSubscription` to.

```java
// We will assume that our example object was registered to an entity
public class ExampleObjectRenderer implements DebugRenderer.SimpleDebugRenderer {

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, double x, double y, double z, DebugValueAccess access, Frustum frustum) {
        // Loop through all blocks with our example object
        access.forEachEntity(EXAMPLE_OBJECT, (entity, exampleObject) -> {
            // Render the debug info
            DebugRenderer.renderTextOverMob(
                poseStack, bufferSource, entity,
                // Text Y offset (entities display a lot of information)
                100,
                // Text to render
                "Held Count: " + exampleObject.count(),
                // Text color
                0xFFFFFFFF,
                // The scale of the text
                1f
            );
        });
    }
}
```

## Debug Screens

The debug screens allows for users to to enable, disable, or only show in F3 specific components. This modular system allows for modders to add their own debug entries to the screen. Not all parts of this explanation is accessible without a bit more modding work, so those areas will be specifically pointed out.

#### `DebugScreenEntry`

Every debug option has its own entry that either defines what is being displayed (e.g., fps, memory), or no-ops to be handled by a separate implementation (e.g., entity hitboxes, chunk borders). This is known as a `DebugScreenEntry`, which defines three methods.

First is the `category`. In vanilla, this is almost always `DebugEntryCategory#SCREEN_TEXT`, as all a debug entry does is draw text to the screen. The other available option `RENDERER` is only for no-op as the rendering options always rendered independent from the debug screen. `DebugEntryCategory` is simply a record with a label and some sort of sort key value, so more can be added by calling the constructor. All the category is used for is searching in the debug options screen.

Next is `isAllowed`. This method determines whether the debug option should render on the screen independent of the entry status. By default, this is true only when the `Minecraft#showOnlyReducedInfo` accessibility option is false. Some debug entries override this method to always return true, or if some other check passes.

Finally, there is the `display` method. This is responsible for drawing the text to the screen using the `DebugScreenDisplayer`. It also takes in the current `Level`, client chunk, and server chunk. The `DebugScreenDisplayer` has four methods that each draw text to the screen. First, there is the standard `addLine` class, which just adds the string to either the left or right side depending on what element its rendered as. These will always come one right after the other. Then, there is `addPriorityLine`, which will always be added to the top of either the left or the right side. Finally, there is `addToGroup`, which takes an additional key to render the lines as one separate group with an extra new line added at the end.

```java
public class ExampleDebugEntry implements DebugScreenEntry {

    public static final ResourceLocation GROUP_ONE = ResourceLocation.fromNamespaceAndPath("examplemod", "group_one");
    public static final ResourceLocation GROUP_TWO = ResourceLocation.fromNamespaceAndPath("examplemod", "group_two");


    @Override
    public void display(DebugScreenDisplayer displayer, @Nullable Level level, @Nullable LevelChunk clientChunk, @Nullable LevelChunk serverChunk) {
        // The following will display like so if it is the only entry on the screen:
        // First left!                                                First Right!
        // 
        // Hello world!                                               Random text!
        // Lorem ipsum.
        //                                                            I am another group!
        // I am one group                                             This will appear after with no line breaks!
        // All in a row
        // Provided in a list.
        //

        displayer.addLine("Hello world!");
        displayer.addLine("Lorem ipsum.");
        displayer.addLine("Random text!");

        // These will be displayed first
        displayer.addPriorityLine("First left!");
        displayer.addPriorityLine("First right!");

        // These will be grouped separately based on the key
        displayer.addToGroup(GROUP_ONE, List.of(
            "I am one group",
            "All in a row",
            "Provided in a list."
        ));

        displayer.addToGroup(GROUP_TWO, "I am another group!");
        displayer.addToGroup(GROUP_TWO, "This will appear after with no line breaks!");
    }

    @Override
    public boolean isAllowed(boolean reducedDebugInfo) {
        // Always show regardless of accessibility option
        return true;
    }
}
```

Then, simply register your entry to `DebugScreenEntries` to have it display. It can be toggled on through the debug menu using the provided key

```java
// This method is private, so its access will need to be widened
DebugScreenEntries.register(
    // The id, this will be displayed on the options screen
    ResourceLocation.fromNamespaceAndPath("examplemod", "example_entry"),
    // The screen entry
    new ExampleScreenEntry();
);
```

#### External Toggles and Checks

What if you want to toggle the active status separately from the options menu? What if you want to check is an entry is enabled to display debug data in-game? This can be done by accessing the `DebugScreenEntryList` through the `Minecraft` instance.

Toggling the current status can be done via `DebugScreenEntryList#toggleStatus`. How this behaves changes depending on what screen is active. Basically, calling toggle will always flip the debug entry on or off: if it's not currently on screen, it will render on screen and vice versa. If in F3, then toggling on will only render that debug entry when F3 is on.

The status of the entry can then be checked using `DebugScreenEntryList#isCurrentlyEnabled`. This will only check if the debug screen is in the currently on list and not check `DebugScreenEntry#isAllowed`.

```java
// Lets create another entry
public static final ResourceLocation EXAMPLE_TOGGLE = DebugScreenEntries.register(
    ResourceLocation.fromNamespaceAndPath("examplemod", "example_toggle"),
    // We're using noop as nothing is being displayed as text
    new DebugEntryNoop();
);

// To toggle:
Minecraft.getInstance().debugEntries.toggleStatus(EXAMPLE_TOGGLE);

// To check if enabled:
if (Minecraft.getInstance().debugEntries.isCurrentlyEnabled(EXAMPLE_TOGGLE)) {
    // ...
}
```

#### Profiles

Profiles are defined presets that can be configured to the user's desire. Currently, profiles are hardcoded to either default or performance. To extend the system, you need to be able to dynamically add an entry to the `DebugScreenProfile` enum, make the `DebugScreenEntries#PROFILES` map mutable to add your profile and preset, and modify the debug option screen with your profile button.

- `net.minecraft.SharedConstants`
    - `DEBUG_SHUFFLE_MODELS` - A flag that likely shuffles the model loading order.
    - `DEBUG_FLAG_PREFIX` - A prefix put in front of every debug flag.
    - `USE_DEBUG_FEATURES` -> `DEBUG_ENABLED`
    - `DEBUG_RENDER` is removed
    - `DEBUG_WORLDGENATTEMPT` is removed
    - `debugGenerateStripedTerrainWithoutNoise` is removed
    - `DEBUG_RESOURCE_GENERATION_OVERRIDE` is removed
    - `DEBUG_POI` - Enables the POI debug renderer.
    - `DEBUG_PANORAMA_SCREENSHOT` - When enabled, allows the user to take a panorama screenshot.
    - `DEBUG_CHASE_COMMAND` - When enabled, adds the chase command.
    - `FAKE_MS_LATENCY` -> `DEBUG_FAKE_LATENCY_MS`
    - `FAKE_MS_JITTER` -> `DEBUG_FAKE_JITTER_MS`
    - `DEBUG_VERBOSE_COMMAND_ERRORS` - When enabled, outputs verbose errors through the chat box.
    - `DEBUG_DEV_COMMANDS` - When enabled, adds the commands used for debugging the game.
- `net.minecraft.client.Minecraft`
    - `debugEntries` - Returns a list of debug features and what should be shown on screen.
    - `fpsString`, `sectionPath`, `sectionVisibility` are removed
    - `debugRenderer` -> `LevelRenderer#debugRenderer`
- `net.minecraft.client.gui.Gui`
    - `renderDebugOverlay` is now public
    - `shouldRenderDebugCrosshair` is removed
- `net.minecraft.client.gui.components.DebugScreenOverlay`
    - `drawGameInformation`, `drawSystemInformation` are removed
    - `getGameInformation`, `getSystemInformation` are removed
    - `toggleOverlay` is removed
- `net.minecraft.client.gui.components.debug`
    - `DebugEntryBiome` - A debug entry displaying the biome the camera entity is within.
    - `DebugEntryCategory` - A category that describes how a debug entry is displayed.
    - `DebugEntryChunkGeneration` - A debug entry displaying the current chunk generation info.
    - `DebugEntryChunkRenderStats` - A debug entry displaying the current section statistics.
    - `DebugEntryChunkSourceStats` - A debug entry displaying the general metadata of the chunk sources.
    - `DebugEntryEntityRenderStats` - A debug entry displaying the general metadata of the entity storage.
    - `DebugEntryFps` - A debug entry displaying the frames per second and vsync info.
    - `DebugEntryGpuUtilization` - A debug entry displaying the GPU utilization.
    - `DebugEntryHeightmap` - A debug entry displaying the height maps at the current position.
    - `DebugEntryLight` - A debug entry displaying the client light information.
    - `DebugEntryLocalDifficulty` - A debug entry displaying the current world difficulty and time.
    - `DebugEntryLookingAtBlock` - A debug entry displaying the block the camera is currently looking at.
    - `DebugEntryLookingAtEntity` - A debug entry displaying the entity the camera is currently looking at.
    - `DebugEntryLookingAtFluid` - A debug entry displaying the fluid the camera is currently looking at.
    - `DebugEntryMemory` - A debug entry displaying the memory allocated and used by the game.
    - `DebugEntryNoop` - A debug entry displaying nothing.
    - `DebugEntryParticleRenderState` - A debug entry displaying the number of particles being rendered.
    - `DebugEntryPosition` - A debug entry displaying the current position and rotation of the camera entity.
    - `DebugEntryPostEffect` - A debug entry displaying the currently applied post effect.
    - `DebugEntrySectionPosition` - A debug entry displaying the current section position.
    - `DebugEntrySimplePerformanceImpactors` - A debug entry displaying the graphics mod, cloud status, and biome blend radius.
    - `DebugEntrySoundMood` - A debug entry displaying the current sound played and player mood.
    - `DebugEntrySpawnCounts` - A debug entry displaying the entity spawn counts per mob category.
    - `DebugEntrySystemSpecs` - A debug entry displaying the specs of the running machine.
    - `DebugEntryTps` - A debug entry displaying the general ticks per second.
    - `DebugEntryVersion` - A debug entry displaying the current Minecraft version.
    - `DebugScreenDisplayer` - An interface that the debug entries use to display elements to the screen.
    - `DebugScreenEntries` - The debug entries registered by Minecraft.
    - `DebugScreenEntry` - An element that shows up, if enabled, when the debug overlay is enabled.
    - `DebugScreenEntryList` - The options information for what debug elements are displayed on screen.
    - `DebugScreenEntryStatus` - The status of when a debug entry should be displayed.
    - `DebugScreenProfile` - An enum denoting the profiles that the debug screen can set when deciding what entries to show.
- `net.minecraft.client.gui.screen.debug.DebugOptionsScreen` - A screen that allows the user to change the displayed debug entries for a profile.
- `net.minecraft.client.renderer.LevelRenderer`
    - `getSectionStatistics` is now nullable
    - `getEntityStatistics` is now nullable
    - `gameTestBlockHighlightRenderer` - The renderer for the block highlight within a game test.
- `net.minecraft.client.renderer.debug.DebugRenderer`
    - `switchRenderChunkborder` -> `DebugScreenEntries#CHUNK_BORDERS`, not one-to-one
    - `toggleRenderOctree` -> `DebugScreenEntries#CHUNK_SECTION_OCTREE`, not one-to-one
- `net.minecraft.client.multiplayer`
    - `DebugSampleSubscriber` -> `ClientDebugSubscriber`, not one-to-one
    - `ClientPacketListener#createDebugValueAccess` - Creates the access to get the current debug values.
- `net.minecraft.client.renderer.debug`
    - `BeeDebugRenderer#addOrUpdateHiveInfo`, `addOrUpdateBeeInfo`, `removeBeeInfo` are removed
    - `BrainDebugRenderer`
        - `addPoi`, `removePoi`, `$PoiInfo` are removed
        - `setFreeTicketCount` is removed
        - `addOrUpdateBrainDump`, `removeBrainDump` are removed
    - `BreezeDebugRenderer` now implements `DebugRenderer$SimpleDebugRenderer`
        - `render` now takes in a `DebugValueAccess`
        - `clear`, `add` are removed
    - `DebugRenderer` no longer takes in the `Minecraft` instance
        - All field renderers have been removed from public access, instead being store in on of the `*Renderers` lists
        - `worldGenAttemptRenderer` is removed
        - `renderTextOverBlock` - Renders the given stream above the provided block position.
        - `renderTextOverMob` - Renders the given string over the provided entity.
        - `refreshRendererList` - Populates the renderer lists with the enabled debug renderers.
        - `render`, `renderAfterTranslucents` have been merged into `render`, where a `boolean` determines whether to render the translucent or opaque renderers
        - `$SimpleDebugRenderer`
            - `render` now takes in a `DebugValueAccess` and `Frustum`
            - `clear` is removed
    - `EntityBlockIntersectionDebugRenderer` - A debug renderer for displaying the blocks the entity is intersecting with.
    - `GameEventListenerRenderer` no longer takes in the `Minecraft` instance
        - `trackGameEvent`, `trackListener` are removed
    - `GameTestDebugRenderer` -> `GameTestBlockHighlightRenderer`, not one-to-one
        - `addMarker` -> `highlightPos`, not one-to-one
    - `GoalSelectorDebugRenderer#addGoalSelector`, `removeGoalSelector` are removed
    - `NeighborsUpdateRenderer` no longer takes in the `Minecraft` instance
        - `addUpdate` is removed
    - `OctreeDebugRenderer` now implements `DebugRenderer$SimpleDebugRenderer`
    - `PathfindingRenderer#addPath` is removed
    - `PoiDebugRenderer` - A debug renderer for displaying the point of interests.
    - `RaidDebugRenderer#setRaidCenters` is removed
    - `RedstoneWireOrientationsRenderer` no longer takes in the `Minecraft` instance
        - `addWireOrientation` is removed
    - `StructureRenderer` no longer takes in the `Minecraft` instance
        - `addBoundingBox` is removed
    - `VillagerSectionsDebugRenderer#setVillageSection`, `setNotVillageSection` are removed
    - `WorldGenAttemptRenderer` class is removed
- `net.minecraft.core.registries.BuiltInRegistries`, `Registries#DEBUG_SUBSCRIPTION` - A registry for subscriptions to debug handlers.
- `net.minecraft.gametest.framework`
    - `GameTestAssertPosException#getMessageToShowAtBlock` now returns a `Component`
    - `GameTestRunner#clearMarkers` is removed
- `net.minecraft.network.protocol.common.custom`
    - All classes have been moved to `net.minecraft.util.debug`
    - They are no longer payloads, instead just records containing the object info and an associated stream codec
    - If the payload class had an associated object inner class, then that class was moved and the payload class removed
    - Otherwise the payload class was added without the `*Payload` suffix, most of the time with an `*Info` suffix
- `net.minecraft.network.protocol.game`
    - `ClientboundDebugBlockValuePacket` - A packet sent to the client about a debug value change on a block position.
    - `ClientboundDebugChunkValuePacket` - A packet sent to the client about a debug value change on a chunk position.
    - `ClientboundDebugEntityValuePacket` - A packet sent to the client about a debug value change on an entity.
    - `ClientboundDebugEventPacket` - A packet sent to the client about the debug event fired.
    - `ClientboundGameTestHighlightPosPacket` - A packet sent to the client about the game test position to highlight.
    - `ClientGamePacketListener`
        - `handleDebugChunkValue` - Handles the debug chunk position packet.
        - `handleDebugBlockValue` - Handles the debug block position packet.
        - `handleDebugEntityValue` - Handles the debug entity packet.
        - `handleDebugEvent` - Handles the firing debug event.
        - `handleGameTestHighlightPos` - Handles the provided position being highlighted.
    - `DebugPackets` class is removed
    - `ServerboundDebugSampleSubscriptionPacket` -> `ServerboundDebugSubscriptionRequestPacket`, not one-to-one
    - `ServerGamePacketListener#handleDebugSampleSubscription` -> `handleDebugSubscriptionRequest`, not one-to-one
- `net.minecraft.server.MinecraftServer`
    - `subscribeToDebugSample` is removed
    - `debugSubscribers` - Returns a map of the tracked subscriptions to the list of players that have it enabled.
- `net.minecraft.server.level`
    - `ChunkMap`
        - `isChunkTracked` is now public
        - `getChunks` is removed
    - `ServerLevel#debugSynchronizers` - Returns the debugger handler and synchronizer for the level.
    - `ServerPlayer`
        - `requestDebugSubscriptions` - Sets the debuggers that the player is listening for.
        - `debugSubscriptions` - Returns the debuggers that the player is listening for.
- `net.minecraft.util.debug`
    - `DebugSubscription` - A tracked data point that can be listened or subscribed to.
    - `DebugSubscriptions` - Vanilla debug subscriptions.
    - `DebugValueAccess` - Accesses the values tracked by the debug subscription, used on the client for the debug renderers.
    - `DebugValueSource` - Defines a source object that provides debug values to track, such as an entity.
    - `LevelDebugSynchronizers` - Handles sending the subscription data across the network to the tracking clients.
    - `ServerDebugSubscribers` - Handles the global state of players subscribed to the currently enabled subscriptions.
    - `TrackingDebugSynchronizer` - Handles the list of players subscribed to a subscription.
- `net.minecraft.util.debugchart`
    - `DebugSampleSubscriptionTracker` class is removed
    - `RemoteDebugSampleType` now takes in a `DebugSubscription`
        - `subscription` - Returns the subscription reported by the sample type.
    - `RemoteSampleLogger` now takes in `ServerDebugSubscribers` instead of `DebugSampleSubscriptionTracker`
- `net.minecraft.world.entity`
    - `Entity` now implements `DebugValueSource`
    - `Mob#sendDebugPackets` is removed
- `net.minecraft.world.entity.ai.village.poi`
    - `PoiManager#getFreeTickets` -> `getDebugPoiInfo`, not one-to-one
    - `PoiSection#getDebugPoiInfo` - Returns the debug poi info for the given position.
- `net.minecraft.world.level.block.entity`
    - `BlockEntity` now implements `DebugValueSource`
    - `TestInstanceBlockEntity#markError`, `clearErrorMarkers`, `getErrorMarkers`, `$ErrorMarker` - Handles the error markers set by the test instance.
- `net.minecraft.world.level.chunk.LevelChunk` now implements `DebugValueSource`
- `net.minecraft.world.level.pathfinder.PathFinder#setCaptureDebug` - Sets whether the path should be captured for debugging.
- `net.minecraft.world.level.redstone.CollectingNeighborUpdater#setDebugListener` - Sets the listener for block location changes for debugging.

## Feature Submissions: The Movie

The entirety of the rendering pipeline, from entities to block entities to particles, has been reworked into a submission / render phase system known as features. This guide will go over the basics of the feature system itself followed by how each major type are implemented using it.

### Submission and Rendering

The feature system, like GUIs, is broken into two phases: submission and rendering. The submission phase is handled by the `SubmitNodeCollector`, which basically collects the data required to abstractly render an object to the screen. This is all done through the `submit*` methods, which generally take a `PoseStack` to place the object in 3D space, and any other required data such as the model, state, render type, etc.

Here is a quick overview of what each method takes in:

Method                 | Parameters
:---------------------:|:----------
`submitHitbox`         | A pose stack, render state of the entity, and the hitboxes render state
`submitShadow`         | A pose stack, the shadow radius, and the shadow pieces
`submitNameTag`        | A pose stack, an optional position, the Y offset, the text component, if the text should be seethrough (like when sneaking), light coordinates, and the camera render state
`submitText`           | A pose stack, the XY offset, the text sequence, whether to add a drop shadow, the font display mode, light coordinates, color,  background color, and outline color
`submitFlame`          | A pose stack, render state of the entity, and a rotation quaternion
`submitLeash`          | A pose stack and the leash state
`submitModel`          | A pose stack, entity model, render state, render type, light coordinates, overlay coordinates, tint color, an optional texture, outline color, and an optional crumbling overlay
`submitModelPart`      | A pose stack, model part, render type, light coordinates, overlay coordinates, an optional texture, whether to use item glint over entity glint if render type is not transparent, whether to render the glint overlay, tint color, an optional crumbling overlay, and an outline color
`submitBlock`          | A pose stack, block state, light coordinates, overlay coordinates, and outline color
`submitMovingBlock`    | A pose stack and the moving block render state
`submitBlockModel`     | A pose stack, the render type, block state model, RGB floats, light coordinates, overlay coordinates, and outline color
`submitItem`           | A pose stack, item display context, light coordinates, overlay coordinates, outline color, tint layers, quads, render type, and foil type
`submitCustomGeometry` | A pose stack, render type, and a function that takes in the current pose and `VertexConsumer` to create the mesh
`submitParticleGroup`  | A `SubmitNodeCollector$ParticleGroupRenderer`

Technically, the `submit*` methods are provided by the `OrderedSubmitNodeCollector`, of which the `SubmitNodeCollector` extends. This is because features can be submitted to different orders, which function similarly to strata in GUIs. By default, all submit calls are pushed onto order 0. Using `SubmitNodeCollector#order` with some integer and then calling the `submit*` method, you can have an object render before or after all features on a given order. This is stored as an AVL tree, where each order's data is stored in a `SubmitNodeCollection`. With the current default feature rendering order, this is only used in very specific circumstances, such as rendering a slime's outer body or equipment layers.

```java
// Assume we have access to the `SubmitNodeCollector` collector

// This will render in order 0
collector.submitModel(...);

// This will render before the `submitModel` call
collector.order(-1).submitShadow(...);

// This will render after the `submitModel` call
collector.order(1).submitNameTag(...);
```

The render phase is handled through the `FeatureRenderDispatcher`, which renders the object using their submitted feature renderers. What are feature renderers? Quite literally an arbitrary method that loops through the node contents its going to push to the buffer. Currently, for a given order, the features push their vertices like so: shadows, models, model parts, flame animations, entity name tags, arbitrary text, hitboxes, leashes, items, blocks, custom render pipelines, and finally particles. Each order, starting from the smallest number to the largest, will rerun all of the feature renders until it reaches the end of the tree. All submissions are then cleared for next use.

Most of the feature dispatchers are simply run a loop over its collection. Those that store the render type batch the render calls into one buffer. `ModelFeatureRenderer`, meanwhile, goes one step further, sorting its translucent models by distance from the camera and sends them to the buffer after all opaque models.

### Entity Models

So, how does this affect entities? Let's start with the root `Model` that makes up all entity models. `Model` now has a generic which is used to pass in the state of the backing object to `setupAnim`, which has also been moved to `Model`. This means that the base model class is rarely passed around, instead opting for some subtype, like `Model$Simple` for signs. Given that most `EntityModel`s already require a generic of `EntityRenderState`, this does not affect anything.

The main change comes from how model part visibility work, such as armors and capes. Every single individual part (e.g. helmet, chestplate) now has their own separate model, meaning that the general part visibility system has been completely removed. You can still provide visibility through the mutable model part in `setupAnim`, but the general movement is to simply have parts that should be separate models as separate models.

To facilitate this, `PartDefinition`s now has methods to selectively keep certain parts of models and remove the others. This is done through the `clear*` and `retain*` methods. Basically, all of these methods are doing are keeping the part pose while removing any cubes associated with the children queries. `retain*` allows for certain parts and potentially subparts to keep their cubes. This addition provides a twofold benefit: model layers can deterministically use `setupAnim` to setup the parts similarly to the base model, and the model texture will only need to contain the retained elements.

Here is an example for creating the armor models for a creeper:

```java
// Deformations for humanoid armor
private static final CubeDeformation OUTER_ARMOR_DEFORMATION = new CubeDeformation(1.0F);
private static final CubeDeformation INNER_ARMOR_DEFORMATION = new CubeDeformation(0.5F);

// Creeper has the following parts:
// - head
// - body
// - right_hind_leg, left_hind_leg
// - right_front_leg, left_front_leg

// We use separate meshes as we are basically isolating the parts we want to keep in each

public static ArmorModelSet<MeshDefinition> createCreeperArmor() {
    // Helmet

    // Create mesh
    MeshDefinition helmetMesh = CreeperModel.createBodyLayer(OUTER_ARMOR_DEFORMATION);
    // Only retain the desired parts
    // Note that body and the legs still exist, they simply have no cubes
    helmetMesh.getRoot().retainExactParts(Set.of("head"));

    // Chestplate

    // Create mesh
    var chestplateMesh = CreeperModel.createBodyLayer(OUTER_ARMOR_DEFORMATION);
    // Only retain the desired parts
    chestplateMesh.getRoot().retainExactParts(Set.of("body"));

    // Leggings

    // Create mesh
    var leggingsMesh = CreeperModel.createBodyLayer(INNER_ARMOR_DEFORMATION);
    // Only retain the desired parts
    leggingsMesh.getRoot().retainExactParts(Set.of("right_hind_leg", "left_hind_leg", "right_front_leg", "left_front_leg"));

    // Boots

    // Create mesh
    var bootsMesh = CreeperModel.createBodyLayer(OUTER_ARMOR_DEFORMATION);
    // Only retain the desired parts
    bootsMesh.getRoot().retainExactParts(Set.of("right_hind_leg", "left_hind_leg", "right_front_leg", "left_front_leg"));

    // Store this all in an ArmorModelSet, which is basically just object holder and mapper
    return new ArmorModelSet<>(
        helmetMesh,
        chestplateMesh,
        leggingsMesh,
        bootsMesh
    );
}

// To register the layer definitions, basically the same process of using the ArmorModelSet
public static final ArmorModelSet<ModelLayerLocation> CREEPER_ARMOR = new ArmorModelSet<>(
    new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("examplemod", "creeper"), "helmet"),
    new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("examplemod", "creeper"), "chestplate"),
    new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("examplemod", "creeper"), "leggings"),
    new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("examplemod", "creeper"), "boots")
);

// In some method where you have access to the Map<ModelLayerLocation, LayerDefinition> builder
ArmorModelSet<LayerDefinition> creeperArmorLayers = createCreeperArmor().map(mesh -> LayerDefinition.create(mesh, 64, 32));
CREEPER_ARMOR.putFrom(creeperArmorLayers, builder);
```

### Entity Renderer

With the change to submission, the `EntityRenderer` and its associated `RenderLayer`s have also changed. Basically, you can assume almost every method that has the word `render` has been changed to `submit`, and `MultiBufferSource` and light coordinates integer have generally been replaced by `SubmitNodeCollector` and the associated entity render state.

The new `submit` method that replaces `render` in `EntityRenderer` now takes in the render state of the entity, the `PoseStack`, the `SubmitNodeCollector`, and the `CameraRenderState`. When submitting any element, the location in 3D space is taken by getting the last pose on the `PoseStack` and storing that for future use.

```java
// A basic entity renderer

// We will assume all the classes listed exist
public class ExampleEntityRenderer extends MobRenderer<ExampleEntity, ExampleEntityRenderState, ExampleModel> {

    public ExampleEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, ctx.bakeLayer(EXAMPLE_MODEL_LAYER), 0.5f);
    }

    @Override
    public void submit(ExampleEntityRenderState renderState, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState) {
        super.submit(renderState, poseStack, collector, cameraState);

        // An example of submitting something
        collector.submitCustomGeometry(
            poseStack, // The current pose
            RenderType.solid(), // The render type to use
            ExampleEntityRenderer::addVertices // The method to write the geometry data
        );
    }

    private static void addVertices(PoseStack.Pose pose, VertexConsumer consumer) {
        // Add custom geometry
    }
}

// A render layer

public class CreeperArmorLayer extends RenderLayer<CreeperRenderState, CreeperModel> {

    private final ArmorModelSet<CreeperModel> modelSet;
    private final EquipmentLayerRenderer equipment;

    public CreeperArmorLayer(RenderLayerParent<CreeperRenderState, CreeperModel> parent, ArmorModelSet<CreeperModel> modelSet, EquipmentLayerRenderer equipment) {
        super(parent);
        this.modelSet = modelSet;
        this.equipment = equipment;
    }

    // We will assume we added headEquipment, chestEquipment, legsEquipment, feetEquipment to the CreeperRenderState somehow
    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords, CreeperRenderState renderState, float yRot, float xRot) {
        this.renderArmorPiece(poseStack, collector, renderState.chestEquipment, EquipmentSlot.CHEST, lightCoords, renderState);
        this.renderArmorPiece(poseStack, collector, renderState.legsEquipment,  EquipmentSlot.LEGS,  lightCoords, renderState);
        this.renderArmorPiece(poseStack, collector, renderState.feetEquipment,  EquipmentSlot.FEET,  lightCoords, renderState);
        this.renderArmorPiece(poseStack, collector, renderState.headEquipment,  EquipmentSlot.HEAD,  lightCoords, renderState);
    }

    // Taken from humanoid armor layer
    private void renderArmorPiece(PoseStack poseStack, SubmitNodeCollector collector, ItemStack stack, EquipmentSlot slot, int lightCoords, CreeperRenderState renderState) {
        Equippable equippable = stack.get(DataComponents.EQUIPPABLE);
        if (equippable != null && equippable.assetId().isPresent() && equippable.slot() == slot) {
            CreeperModel model = this.modelSet.get(slot);
            EquipmentClientInfo.LayerType layer = slot == EquipmentSlot.LEGS
                ? EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS
                : EquipmentClientInfo.LayerType.HUMANOID;
            this.equipmentRenderer.renderLayers(
                layer, // The equipment layer to use
                equippable.assetId().orElseThrow(), // The equipment asset to pull
                model, // The armor model
                renderState, // The entity render state
                stack, // The armor stack
                poseStack, // The pose stack
                collector, // The collector to add the model data to
                lightCoords, // The light coordinates
                renderState.outlineColor // The outline color of the entity
            );
        }
    }
}

// Then, to add it to the creeper renderer constructor
public CreeperRenderer(EntityRendererProvider.Context ctx) {
    // ...
    this.addLayer(new CreeperArmorLayer(
        this, // The parent is the renderer itself
        ArmorModelSet.bake( // Baking the model set
            CREEPER_ARMOR, // The model layer locations
            ctx.getModelSet(), // The model set to map the models from the layer locations
            CreeperModel::new // The mapper for the root part to the model
        ),
        ctx.getEquipmentRenderer() // The renderer for the equipment
    ));
}
```

### Block Entity Renderer

`BlockEntityRenderer`s also use the new submission method, replacing almost every `render*` with `submit`. They have taken a play from entities, now having their own `BlockEntityRenderState` which is extracted from the `BlockEntity`. As such, `BlockEntityRenderer` now has a new generic `S` for the `BlockEntityRenderState`.

The `BlockEntityRenderState`, by default, contains information about its position, block state, type, light coordinates, and the current break progress as a `ModelFeatureRenderer$CrumblingOverlay`. These are all populated through `BlockEntityRenderState#extractBase`, which is called in `BlockEntityRenderer#extractRenderState`. Like entities, the render state is first constructed via `BlockEntityRenderer#createRenderState` before the values are extracted from the block entity. `extractRenderState` does contain the partial tick and camera position, but this is not passed to the `BlockEntityRenderState` by default.

As such, the `submit` method that takes over the `render` method takes in the render state, a `PoseStack` for the location in 3D space, the `SubmitNodeCollector` for pushing the elements to render, and the `CameraRenderState`.

```java
// We will assume all the classes not specified here exist

// A simple render state
public class ExampleRenderState extends BlockEntityRenderState {
    public float partialTick;
}

// A basic block entity renderer
public class ExampleBlockEntityRenderer implements BlockEntityRenderer<ExampleBlockEntity, ExampleRenderState> {

    public ExampleBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        // Get anything you need from the context
    }

    @Override
    public ExampleRenderState createRenderState() {
        // Create the render state used to submit the block entity to the feature renderer
        return new ExampleRenderState();
    }

    @Override
    public void extractRenderState(ExampleBlockEntity blockEntity, ExampleRenderState renderState, float partialTick, Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
        // Extract the necessary rendering values from the block entity to the render state
        // Always call super or BlockEntityRenderState#extractBase
        super.extractRenderState(blockEntity, renderState, partialTick, cameraPos, crumblingOverlay);

        // Populate any desired values
        renderState.partialTick = partialTick;
    }


    @Override
    public void submit(ExampleRenderState renderState, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState) {
        // An example of submitting something
        collector.submitModel(..., renderState.breakProgress);
    }
}
```

### Special Item Models

Since special item models also make use of custom rendering, they have been updated to with the `submit` change, only replacing `MultiBufferSource` with the `SubmitNodeCollector`.

```java
// A basic special item model

// We will assume all the classes listed exist
public class ExampleSpecialModelRenderer implements NoDataSpecialModelRenderer {

    public ExampleSpecialModelRenderer() {}

    @Override
    public void submit(ItemDisplayContext displayContext, PoseStack poseStack, SubmitNodeCollector collector, int lightCoords, int overlayCoords, boolean hasFoil, int outlineColor) {
        // An example of submitting something
        collector.submitModelPart(...);
    }

    @Override
    public void getExtents(Set<Vector3f> extents) {}
}
```

### Particles

Particles have been added to the submission process; however, there are multiple paths depending on how complicated your particle is. A few classes and general names have been reused for an additional purpose as well, sometimes making it difficult to understand how each part works. As such, this document will go over two methods of creating a particle: one more familiar with the old system, and one that explains the underlying nuances from the ground up.

#### The Separation of Engines and Resources

Before we discuss the two methods, first, let's go with the overarching changes. `ParticleEngine` has been functionally split up into two classes: `ParticleEngine`, which handle the actual ticking and extraction, not submission, of the render state; and `ParticleResources`, which is the reload listener that registers the `ParticleProvider` and optionally `ParticleResources$SpriteParticleRegistration` and reloads the `SpriteSet` from its `ParticleDescription`. This underlying behavior hasn't change (besides the whole extract and submission process), the methods have simply been moved.

`ParticleProvider#createParticle` also now provides a `RandomSource`, which can be used as needed.

As for the actual submission and rendering process, this is handled outside of `ParticleEngine`. More specifically, the `LevelRenderer` extracts all the particles to submit in `ParticlesRenderState` via `ParticleEngine#extract`. Then, in `LevelRenderer#addParticlesPass`, the resource handles are set to the particle `FramePass`, to which on execution the particles are submitted via `ParticlesRenderState#submit` and then rendered through the feature dispatcher via `ParticleFeatureRenderer`.

#### A Single Quad

Many particles in the old system were simply made up of a single quad with a texture(s) slapped on it. These particles are `SingleQuadParticle`s, which merges both the previous `SingleQuadParticle` and `TextureSheetParticle` into one. The `SingleQuadParticle` now takes in an initial `TextureAtlasSprite` to set the first texture, which can then be updated by overriding `Particle#tick` and calling `SingleQuadParticle#setSpriteFromAge` for a `SpriteSet` or directly with `setSprite`. The tint can also be modified in the tick using `setColor` and `setAlpha`. Some also set these directly in `SingleQuadParticle#extract`, but which to use depends on if you need to override the entire tick or not.

To determine the `RenderType` that is used to render the quad, `SingleQuadParticle#getLayer` must be set to the desired `$Layer`. A `$Layer` is basically a record defining whether the quad can have translucency, what texture atlas it pulls from, and the render pipeline to use. Vanilla provides `TERRAIN`, `OPAQUE`, and `TRANSLUCENT` similar to the old `Particle#getRenderType` which it replaces. `TERRAIN` and `TRANSLUCENT` both allow transparency, and `OPAQUE` and `TRANSLUCENT` pull from the particle atlas while `TERRAIN` uses the block atlas. A custom `$Layer` can be created using the constructor.

```java
public static final SingleQuadParticle.Layer EXAMPLE_LAYER = new SingleQuadParticle.Layer(true, TextureAtlas.LOCATION_PARTICLES, RenderPipelines.WEATHER_DEPTH_WRITE);
```

In addition to all this, you can also set how the particle is rotated by overriding `SingleQuadParticle#getFacingCameraMode`. `$FacingCameraMode` is a functional interface that sets the rotation of the particle whenever it is extracted. By default, this means that the texture will always face the camera. Any other method changes and additions are in the list below.

From there, everything else is the same to create the `ParticleProvider` and register it.

```java
// We will assume we have some SimpleParticleType EXAMPLE_QUAD for our particle
// Additionally, we will assume there is some particle description with our textures to use
public class ExampleQuadParticle extends SingleQuadParticle {

    private final SpriteSet spriteSet;

    // This can be package-private, protected, or public
    // public should be used if the particle will be constructed outside of the provider
    ExampleQuadParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
        // We use first to set the initial particle texture
        super(level, x, y, z, spriteSet.first());
    }

    @Override
    public void tick() {
        super.tick();
        // Update the particle image
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public SingleQuadParticle.Layer getLayer() {
        return EXAMPLE_LAYER;
    }

    // Create the provider
    public static class Provider implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType options, ClientLevel level, double x, double y, double z, double xd, double yd, double zd, RandomSource random) {
            // Create the particle
            return new ExampleQuadParticle(level, x, y, z, this.spriteSet);
        }
    }
}

// Register the provider to `ParticleResources#register`
// Assume access to ParticleResources resources and register has been made public
resources.register(EXAMPLE_QUAD, ExampleQuadParticle.Provider::new);
```

#### From the Ground Up

What about rendering some more complex or custom? Well, in those instances, we need to take a deeper dive into how particles are extracted by the `ParticleEngine`. The `Particle` class, by itself actually does no extraction, submission, or rendering itself. It simply handles the physics update every tick. The actual extraction logic is handled through a `ParticleGroup`, while the submission is handled by a `ParticleGroupRenderState`.

So, what is a `ParticleGroup`? Well, as the name implies, a particle group holds a group of particles and is responsible for keeping track of, ticking, and extracting the render state of its particles. The generic represents the type of `Particle` it can keep track of up to the maximum of 16,384 per group (though individual particles can set their own subgroup limit via `Particle#getParticleLimit`). All `SingleQuadParticle`s are part of the `QuadParticleGroup`. To extract the render state, the `ParticleEngine` calls `ParticleGroup#extractRenderState`, which takes in the current frustum, camera, and partial tick to return a `ParticleGroupRenderState`.

`ParticleGroupRenderState` is sort of a mix between a render state, submission handler, and cache. It contains two methods: `submit`, which takes in the `SubmitNodeCollector` and submits the group; and `clear`, which clears all previous cached particle states. Technically, anything can be submitted using the collector, but particles have `SubmitNodeCollector$ParticleGroupRenderer`: an additional utility to help with caching and rendering. The group renderer contains two methods: `prepare`, to write the mesh data to a ring buffer; and `render`, which typically uses the cached buffer to write the data to the shared sequential buffer using the provided `RenderPass` and draw it to the screen.  Only `QuadParticleRenderState` makes use of the cache and `ParticleGroupRenderer` as the render states are currently cleared immediately after rendering.

To link the `ParticleGroup` to a `Particle` for use, we must set the `ParticleRenderType` using `Particle#getGroup`. `ParticleRenderType`, unlike the previous version, is simply a key for a `ParticleGroup`. This key is mapped to the group via `ParticleEngine#createParticleGroup`, and the submission/render order is determined by `ParticleEngine#RENDER_ORDER`. Both the method and the list must be patched for the particle to be properly managed by your group and extracted for submission.

```java
// We will assume we have some SimpleParticleType EXAMPLE_ONE, EXAMPLE_TWO for our particles
// This example with construct two particles with the same base type to show how the group works

// Create the particle type
public static final ParticleRenderType EXAMPLE_TYPE = new ParticleRenderType("examplemod:example_type");

// Create our particles
public abstract class ExampleParticle extends Particle {

    // You can handle passing to the particle group however you want
    // Making the fields accessible or having a dedicated method
    public final Model<Unit> model;

    protected ExampleParticle(ClientLevel level, double x, double y, double z, Function<EntityModelSet, Model<Unit>> modelFactory) {
        super(level, x, y, z);
        this.model = modelFactory.apply(Minecraft.getInstance().getEntityModels());
    }

    @Override
    public ParticleRenderType getGroup() {
        // Set the particle type to our group
        return EXAMPLE_TYPE;
    }

    @FunctionalInterface
    public interface ExampleParticleFactory<P extends ExampleParticle> {
        
        P create(ClientLevel level, double x, double y, double z);
    }

    protected static <P extends ExampleParticle> ParticleProvider<SimpleParticleType> createProvider(ExampleParticleFactory<P> factory) {
        return (options, level, x, y, z, xd, yd, zd, random) -> factory.create(level, x, y, z);
    }
}

public class ExampleOneParticle extends ExampleParticle {

    ExampleOneParticle(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z, modelSet -> new Model.Simple(new ModelPart(Collections.emptyList(), Collections.emptyMap()), RenderType::entityCutoutNoCull));
    }

    public static ParticleProvider<SimpleParticleType> provider() {
        return ExampleParticle.createProvider(ExampleOneParticle::new);
    }
}

public class ExampleTwoParticle extends ExampleParticle {

    private static final ParticleLimit LIMIT = new ParticleLimit(5);

    ExampleTwoParticle(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z, modelSet -> new Model.Simple(new ModelPart(Collections.emptyList(), Collections.emptyMap()), RenderType::entityCutoutNoCull));
    }

    @Override
    public Optional<ParticleLimit> getParticleLimit() {
        // Limits the number of particles using the LIMIT subgroup to 5
        // Note that since ParticleLimit is a record, any with the same limit will be considered as the same key
        return Optional.of(LIMIT);
    }

    public static ParticleProvider<SimpleParticleType> provider() {
        return ExampleParticle.createProvider(ExampleTwoParticle::new);
    }
}

// Register the providers to `ParticleResources#register`
// Assume access to ParticleResources resources and register has been made public
resources.register(EXAMPLE_ONE, ExampleOneParticle.provider());
resources.register(EXAMPLE_TWO, ExampleTwoParticle.provider());

// Create the render state to submit all particles in the group
// Store whatever you need to submit to the node collector
public record ExampleGroupRenderState(List<Model<Unit>> models) implements ParticleGroupRenderState {

    @Override
    public void submit(SubmitNodeCollector collector) {
        // Submit every particle 
        for (var model : this.models) {
            collector.submitModel(model, ...);
        }
    }
}

// Create the particle group to keep track of the particles and create the render state
// Both EXAMPLE_ONE and EXAMPLE_TWO will be added to this group
public class ExampleParticleGroup extends ParticleGroup<ExampleParticle> {

    public ExampleParticleGroup(ParticleEngine engine) {
        super(engine);
    }

    @Override
    public ParticleGroupRenderState extractRenderState(Frustum frustum, Camera camera, float partialTick) {
        // Create the particle group to submit the particles
        return new ExampleGroupRenderState(
            this.particles.stream().map(particle -> particle.model).toList()
        );
    }
}

// Link the ParticleRenderType to its ParticleGroup
// Assume we have access to ParticleEngine engine
// Assume that ParticleEngine#RENDER_ORDER is made mutable and public
// Assume we can patch ParticleEngine#createParticleGroup
engine.RENDER_ORDER.add(EXAMPLE_TYPE);

// In ParticleEngine
private ParticleGroup<?> createParticleGroup(ParticleRenderType renderType) {
    if (renderType == EXAMPLE_TYPE) {
        // this is referring to the ParticleEngine
        return new ExampleParticleGroup(this);
    }
    // ...
}
```

### Atlas Handler Consolidation

The atlas handler has had some of its logic modified to consolidate other sprites and change how to obtain a `TextureAtlasSprite`.

First, map decorations, paintings, and GUI sprites are now proper atlases with their own sheets: `Sheets#MAP_DECORATIONS_SHEET`, `PAINTINGS_SHEET`, and `GUI_SHEET` respectively.

Obtaining the `TextureAtlasSprite` from said sheets are now completely routed through the `MaterialSet`: a functional interface that takes in a `Material` (basically a sheet location and texture location), and returns the associated `TextureAtlasSprite`. The `MaterialSet` handles texture grabs for item models, block entity renderers, and entity renderers:

```java
// Here is an example material to grab the apple texture from the appropriate sheet
public static final Material APPLE = new Material(
    TextureAtlas.LOCATION_BLOCKS, // The sheet where the item textures are stored
    ResourceLocation.fromNamespaceAndPath("minecraft", "item/apple") // The texture name according to the sprite contents
);
// You can also do the same using Sheets.ITEMS_MAPPER.defaultNamespaceApply("apple")

// For some item model
public class ExampleUnbakedItemModel implements ItemModel.Unbaked {
    
    // ...

    @Override
    public ItemModel bake(ItemModel.BakingContext ctx) {
        TextureAtlasSprite appleTexture = ctx.materials().get(APPLE);
        // ...
    }
}

// For some special item model
public class ExampleUnbakedSpecialModel implements SpecialModelRenderer.Unbaked {
    
    // ...

    @Override
    @Nullable
    public SpecialModelRenderer<?> bake(SpecialModelRenderer.BakingContext ctx) {
        TextureAtlasSprite appleTexture = ctx.materials().get(APPLE);
        // ...
    }
}

// For some block entity renderer
public class ExampleBlockEntityRenderer implements BlockEntityRenderer<ExampleBlockEntity> {

    public ExampleBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        TextureAtlasSprite appleTexture = ctx.materials().get(APPLE);
        // ...
    }

    // ...
}


// For some entity renderer
public class ExampleEntityRenderer implements EntityRenderer<ExampleEntity, ExampleEntityState> {

    public ExampleEntityRenderer(EntityRendererProvider.Context ctx) {
        TextureAtlasSprite appleTexture = ctx.getMaterials().get(APPLE);
        // ...
    }

    // ...
}
```

- `assets/minecraft/shaders/core`
    - `blit_screen.json` -> `screenquad.json`, using no-format triangles instead of positioned quads
    - `position_color_lightmap.*` are removed
    - `position_color_tex_lightmap.*` are removed
- `com.mojang.blaze3d.vertex`
    - `CompactVectorArray` - An holder that smashes a list of float vectors into a single sequential array.
    - `MeshData$SortState#centroids` now returns a `CompactVectorArray`
    - `VertexSorting`
        - `byDistance` now takes in a `Vector3fc` instead of a `Vector3f`
        - `sort` now takes in a `CompactVectorArray` instead of a `Vector3f[]`
- `net.minecraft.client.Minecraft`
    - `getTextureAtlas` -> `AtlasManager#getAtlasOrThrow`, not one-to-one
    - `getPaintingTextures`, `getMapDecorationTextures`, `getGuiSprites` -> `getAtlasManager`, not one-to-one
- `net.minecraft.client.animation.Keyframe` now has an overload that takes in the `preTarget` and `postTarget` instead of one simple `target`, taking in a `Vector3fc` instead of a `Vector3f`
- `net.minecraft.client.entity`
    - `ClientAvatarEntity` - The client data of the avatar.
    - `ClientAvatarState` - The movement state of the avatar.
    - `ClientMannequin` - The client version of the `Mannequin` entity.
- `net.minecraft.client.gui`
    - `GuiGraphics`
        - `renderOutline` -> `submitOutline`
        - `renderDeferredTooltip` -> `renderDeferredElements`, not one-to-one
        - `submitBannerPatternRenderState` now takes in a `BannerFlagModel` instead of a `ModelPart`
    `GuiSpriteManager` class is removed
- `net.minecraft.client.gui.render.GuiRenderer` now takes in the `SubmitNodeCollector` and `FeatureRenderDispatcher`
    - `MIN_GUI_Z` is now public
- `net.minecraft.client.gui.render.pip`
    - `GuiBannerResultRenderer` now takes in a `MaterialSet`
    - `GuiSignRenderer` now takes in a `MaterialSet`
- `net.minecraft.client.gui.render.state.TiledBlitRenderState` - A render state for building a sprite using tiling, usually for tile or nine slice textures.
- `net.minecraft.client.gui.render.state.pip.GuiBannerResultRenderState` now takes in a `BannerFlagModel` instead of a `ModelPart`
- `net.minecraft.client.model`
    - `AbstractPiglinModel#createArmorMeshSet` - Creates the model meshes for each of the humanoid armor slots.
    - `ArmedModel` now has a generic of the `EntityRenderState`
        - `translateToHand` now takes in the entity render state
    - `ArmorStandArmorModel#createBodyLayer` -> `createArmorLayerSet`, not one-to-one
    - `BellModel$State` - Represents the state of the backing object.
    - `BookModel$State` - Represents the state of the backing object.
    - `BreezeModel`
        - `createBodyLayer` -> `createBaseMesh`, now private
            - Replaced by `createBodyLayer`, `createWindLayer`, `createEyesLayer`
    - `CopperGolemModel` - A model for the copper golem entity.
    - `CopperGolemStatueModel` - A model for the coper golem statue.
    - `CreakingModel`
        - `NO_PARTS`, `getHeadModelParts` are removed
        - `createEyesLayer` - Creates the eyes of the model.
    - `EntityModel#setupAnim` -> `Model#setupAnim`
    - `GuardianParticleModel` - The particle spawned from a guardian.
    - `HeadedModel#translateToHead` - Transforms the pose stack to the head's position and rotation.
    - `HumanoidArmorModel` -> `HumanoidModel#createArmorMeshSet`, not one-to-one
    - `HumanoidModel#copyPropertiesTo` is removed
    - `Model` now takes in a generic representing the render state
    - `PlayerCapeModel` now extends `PlayerModel`
    - `PlayerEarsModel` now extends `PlayerModel`
    - `PlayerModel` render state has been broadened to `AvatarRenderState`
        - Static fields are now `protected`
        - `createArmorMeshSet` - Creates the model meshes for each of the humanoid armor slots.
    - `SkullModelBase$State` - Represents the state of the backing object.
    - `SpinAttackEffectModel` generic has been broadened to `AvatarRenderState`
    - `VillagerLikeModel` now takes in a generic for the render state
        - `hatVisible` is removed
            - Replaced by `VillagerModel#createNoHatModel`
        - `translateToArms` now takes in the render state
    - `WardenModel`
        - `createTendrilsLayer`, `createHeartLayer`, `createBioluminescentLayer`, `createPulsatingSpotsLayer` - Creates the layers used by the warden's `RenderLayer`s.
        - `getTendrilsLayerModelParts`, `getHeartLayerModelParts`, `getBioluminescentLayerModelParts`, `getPulsatingSpotsLayerModelParts` are removed
    - `ZombieVillagerModel`
        - `createArmorLayer` -> `createArmorLayerSet`, not one-to-one
        - `createNoHatLayer` - Creates the model without the hat layer.
- `net.minecraft.client.model.geom.ModelPart`
    - `copyFrom` is removed
    - `$Polygon#normal` is now a `Vector3fc` instead of a `Vector3f`
    - `$Vertex`
        - `pos` -> `x`, `y`, `z`
        - `worldX`, `worldY`, `worldZ` - Returns the coordinates scaled down by a factor of 16.
- `net.minecraft.client.model.geom.builders.PartDefinition`
    - `clearRecursively` - Clears all children parts and its sub-children.
    - `retainPartsAndChildren` - Retains the specified parts from its root and any sub-children.
    - `retainExactParts` - Retains only the top level part, clearing out all others and sub-children.
- `net.minecraft.client.particle`
    - `AttackSweepParticle` now extends `SingleQuadParticle`
    - `BaseAshSmokeParticle` now extends `SingleQuadParticle` and is `abstract`
    - `BlockMarker` now extends `SingleQuadParticle`
    - `BreakingItemParticle` now extends `SingleQuadParticle`
        - The constructor takes in a `TextureAtlasSprite` instead of the `ItemStackRenderState`
        - `$ItemParticleProvider#calculateState` -> `getSprite`, not one-to-one
    - `BubbleColumnUpParticle` now extends `SingleQuadParticle`
    - `BubbleParticle` now extends `SingleQuadParticle`
    - `BubblePopParticle` now extends `SingleQuadParticle`
    - `CampfireSmokeParticle` now extends `SingleQuadParticle`
    - `CritParticle` now extends `SingleQuadParticle`
    - `DragonBreathParticle` now extends `SingleQuadParticle`
        - `$Provider` generic now uses a `PowerParticleOption`
    - `DripParticle` now extends `SingleQuadParticle` and takes in a `TextureAtlasSprite`
        - `create*Particle` methods -> `$*Provider` classes
    - `DustParticleBase` now extends `SingleQuadParticle`
    - `ElderGuardianParticleGroup` - The particle group responsible for setting up and submitting the elder guardian particle.
    - `ExplodeParticle` now extends `SingleQuadParticle`
    - `FallingDustParticle` now extends `SingleQuadParticle`
    - `FallingLeavesParticle` now extends `SingleQuadParticle` and takes in a `TextureAtlasSprite` instead of a `SpriteSet`
    - `FireflyParticle` now extends `SingleQuadParticle` and takes in a `TextureAtlasSprite`
    - `FireworkParticles`
        - `$FlashProvider` generic now uses `ColorParticleOption`
        - `$OverlayParticle` now extends `SingleQuadParticle` and takes in a `TextureAtlasSprite`
    - `FlameParticle` now takes in a `TextureAtlasSprite`
    - `FlyStraightTowardsParticle` now extends `SingleQuadParticle` and takes in a `TextureAtlasSprite`
    - `FlyTowardsPositionParticle` now extends `SingleQuadParticle` and takes in a `TextureAtlasSprite`
    - `GlowParticle` now extends `SingleQuadParticle`
    - `GustParticle` now extends `SingleQuadParticle`
    - `HeartParticle` now extends `SingleQuadParticle` and takes in a `TextureAtlasSprite`
    - `HugeExplosionParticle` now extends `SingleQuadParticle`
    - `ItemPickupParticle` now takes in the `EntityRenderState` instead of the `EntityRenderDispatcher`
        - Fields are now all `protected` aside from the target entity
    - `ItemPickupParticleGroup` - The particle group responsible for setting up and submitting the item pickup particle.
    - `LavaParticle` now extends `SingleQuadParticle`
    - `MobAppearanceParticle` -> `ElderGuardianParticle`
    - `NoRenderParticleGroup` - The particle group that does nothing.
    - `NoteParticle` now extends `SingleQuadParticle` and takes in a `TextureAtlasSprite`
    - `Particle`
        - `rCol`, `gCol`, `bCol`, `alpha` -> `SingleQuadParticle#rCol`, `gCol`, `bCol`, `alpha`
        - `roll`, `oRoll` -> `SingleQuadParticle#roll`, `oRoll`
        - `setColor`, `setAlpha` -> `SingleQuadParticle#setColor`, `setAlpha`
        - `render`, `renderCustom` -> `ParticleGroupRenderState#submit`, not one-to-one
        - `getRenderType` -> `getGroup`
            - The original purpose of this method has been moved to `SingleQuadParticle#getLayer`
        - `getParticleGroup` -> `getParticleLimit`, not one-to-one
    - `ParticleEngine` no longer implements `PreparableReloadListener`
        - The constructor now takes in the `ParticleResources` instead of the `TextureManager`
        - `close` is removed
        - `updateCount` is now `protected`
        - `render` -> `extract`, not one-to-one
        - `destroy` -> `ClientLevel#addDestroyBlockEffect`
        - `crack` -> `ClientLevel#addBreakingBlockEffect`
        - `clearParticles` is now `public`
        - `$MutableSpriteSet` -> `ParticleResources$MutableSpriteSet`
        - `$SpriteParticleRegistration` -> `ParticleResources$SpriteParticleRegistration`
    - `ParticleGroup` - A holder of particles for a specific `ParticleRenderType`, responsible for ticking and extracting the general render state.
    - `ParticleProvider`
        - `createParticle` now takes in the `RandomSource`
        - `$Sprite#createParticle` now takes in the `RandomSource` and returns a `SingleQuadParticle` instead of a `TextureSheetParticle`
    - `ParticleRenderType` no longer takes in the `RenderType`
        - This record has been repurposed to represent a key for the particle groups
        - `TERRAIN_SHEET` -> `SingleQuadParticle$Layer#TERRAIN`
        - `PARTICLE_SHEET_OPAQUE` -> `SingleQuadParticle$Layer#OPAQUE`
        - `PARTICLE_SHEET_TRANSLUCENT` -> `SingleQuadParticle$Layer#TRANSLUCENT`
        - `CUSTOM` is replaced by a particle group that is not for `ParticleRenderType#SINGLE_QUADS`
    - `ParticleResources` - Loads the particle providers, any necessary descriptions, and computes them into their desired sprite set.
    - `PlayerCloudParticle` now extends `SingleQuadParticle`
    - `PortalParticle` now extends `SingleQuadParticle` and takes in a `TextureAtlasSprite`
    - `QuadParticleGroup` - The particle group responsible for setting up and submitting single quad particles.
    - `ReversePortalParticle` now takes in a `TextureAtlasSprite`
    - `RisingParticle` now extends `SingleQuadParticle`
    - `SculkChargeParticle` now extends `SingleQuadParticle`
    - `SculkChargePopParticle` now extends `SingleQuadParticle`
    - `SculkChargePopParticle` now extends `SingleQuadParticle`
    - `ShriekParticle` now extends `SingleQuadParticle` and takes in a `TextureAtlasSprite`
    - `SimpleAnimatedParticle` now extends `SingleQuadParticle` and is `abstract`
    - `SingleQuadParticle` now takes in a `TextureAtlasSprite`
        - `sprite` - The texture of the particle.
        - `render` -> `extract`, not one-to-one, now taking in the `QuadParticleRenderState` instead of a `VertexConsumer`
        - `renderRotatedQuad` -> `extractRotatedQuad`, not one-to-one, now taking in the `QuadParticleRenderState` instead of a `VertexConsumer`
        - `getU0`, `getU1`, `getV0`, `getV1` are no longer abstract
        - `getLayer` - Sets the render layer of the single quad.
        - `$Layer` - The layer the single quad should render in.
    - `SnowflakeParticle` now extends `SingleQuadParticle`
    - `SpellParticle` now extends `SingleQuadParticle`
        - `InstantProvider` generic now uses `SpellParticleOption`
    - `SplashParticle` now takes in a `TextureAtlasSprite`
    - `SpriteSet#first` - Returns the first texture in the sprite set.
    - `SuspendedParticle` now extends `SingleQuadParticle` and takes in a `TextureAtlasSprite`
    - `SuspendedTownParticle` now extends `SingleQuadParticle` and takes in a `TextureAtlasSprite`
    - `TerrainParticle` now extends `SingleQuadParticle`
    - `TextureSheetParticle` class is removed, use `SingleQuadParticle` instead
    - `TrailParticle` now extends `SingleQuadParticle` and takes in a `TextureAtlasSprite`
    - `TrialSpawnerDetectionParticle` now extends `SingleQuadParticle`
    - `VibrationSignalParticle` now extends `SingleQuadParticle` and takes in a `TextureAtlasSprite`
    - `WakeParticle` now extends `SingleQuadParticle`
    - `WaterCurrentDownParticle` now extends `SingleQuadParticle` and takes in a `TextureAtlasSprite`
    - `WaterDropParticle` now extends `SingleQuadParticle` and takes in a `TextureAtlasSprite`
- `net.minecraft.client.player.AbstractClientPlayer` fields are now stored within `ClientAvatarState`
    - `elytraRot*` -> `*Cloak`
    - `clientLevel` is removed
    - `getDeltaMovementLerped` -> `addWalkedDistance`, not one-to-one
    - `updateBob` - Updates the bobbing motion of the camera.
- `net.minecraft.client.renderer`
    - `EndFlashState` - The render state of the end flashes.
    - `GameRenderer` now takes in the `BlockRenderDispatcher`
        - `getSubmitNodeStorage` - Gets the node submission for feature-like objects.
        - `getFeatureRenderDispatcher` - Gets the dispatcher for rendering feature-like objects.
        - `getLevelRenderState` - Gets the render state of dynamic features in a level.
    - `ItemInHandRenderer`
        - `renderItem` now takes in a `SubmitNodeCollector` instead of a `MultiBufferSource`
        - `renderHandsWithItems` now takes in a `SubmitNodeCollector` instead of a `MultiBufferSource$BufferSource`
    - `LevelRenderer` now takes in the `LevelRenderState` and `FeatureRenderDispatcher`
        - `getSectionRenderDispatcher` is now nullable
        - `tickParticles` is removed
        - `addParticle` is removed
    - `MapRenderer` now takes in an `AtlasManager` instead of a `MapDecorationTextureManager`
        - `render` now takes in a `SubmitNodeCollector` instead of a `MultiBufferSource`
    - `OrderedSubmitNodeCollector` - A submission handler for holding elements to be drawn in a given order to the screen whenever the features are dispatched.
    - `OutlineBufferSource` no longer takes in any parameters
        - `setColor` now takes in a single integer
    - `ParticleGroupRenderState` - The render state for a group of particles.
    - `ParticlesRenderState` - The render state for all particles.
    - `QuadParticleRenderState` - The render group state for all single quad particles.
    - `RenderPipelines`
        - `GUI_TEXT` - The pipeline for text in a gui.
        - `GUI_TEXT_INTENSITY` - The pipeline for text intensity when not colored in a gui.
    - `RenderStateShard#TRANSLUCENT_TARGET`, `PARTICLES_TARGET` are removed
    - `RenderType`
        - `pipeline` - The `RenderPipeline` the type uses.
        - `opaqueParticle`, `translucentParticle` are removed
        - `sunriseSunset`, `celestial` are removed
    - `ScreenEffectRenderer` now takes in a `MaterialSet`
        - `renderScreenEffect` now takes in a `SubmitNodeCollector`
    - `ShapeRenderer`
        - `renderLineBox` now takes in a `PoseStack$Pose` instead of a `PoseStack`
        - `renderFace` now takes in a `Matrix4f` instead of a `PoseStack`
    - `Sheets`
        - `GUI_SHEET`, `MAP_DECORATIONS_SHEET`, `PAINTINGS_SHEET` - Atlas textures.
        - `BLOCK_ENTITIES_MAPPER` - A mapper for block textures onto block entities.
        - `*COPPER*` - Materials for copper chests.
        - `chooseMaterial` now takes in a `ChestRenderState$ChestMaterialType` instead of a `BlockEntity` and `boolean`
    - `SkyRenderer`
        - `END_SKY_LOCATION` is now private
        - `renderSunMoonAndStars` no longer takes in the buffer source
        - `renderEndFlash` no longer takes in the buffer source
        - `renderSunriseAndSunset` no longer takes in the buffer source
    - `SpecialBlockModelRenderer`
        - `vanilla` now takes in a `SpecialModelRenderer$BakingContext` instead of an `EntityModelSet`
        - `renderByBlock` now takes in a `SubmitNodeCollector` instead of a `MultiBufferSource`, and an outline color
    - `SubmitNodeCollection` - An implementation of `OrderedSubmitNodeCollector` that holds the submitted features in separate lists.
    - `SubmitNodeCollector` - An `OrderedSubmitNodeCollector` that provides a method to change the current order that an element will be rendered in.
    - `SubmitNodeStorage` - A storage of collections held by some order.
    - `SkyRenderer`
        - `renderEndFlash` - Renders the end flashes.
        - `initTextures` - Gets the texture for the used elements.
        - `extractRenderState` - Extracts the `SkyRenderState` from the current level.
    - `WeatherEffectRenderer`
        - `render` now takes in the `WeatherRenderState` instead of an `int`, `float`, and `Level`
            - Those fields have moved to `extractRenderState`
        - `extractRenderState` - Extracts the `WeatherRenderState` from the current level.
        - `$ColumnInstance` is now public
    - `WorldBorderRenderer`
        - `render` now takes in the `WorldBorderRenderState` instead of the `WorldBorder`
        - `extract` - Extracts the `WorldBorderRenderState` from the current world border.
- `net.minecraft.client.renderer.block`
    - `BlockRenderDispatcher` now takes in a `MaterialSet`
    - `MovingBlockRenderState` - A render state for a moving block that implements `BlockAndTintGetter`.
    - `LiquidBlockRenderer#setupSprites` now take in the `BlockModelShaper` and `MaterialSet`
- `net.minecraft.client.renderer.blockentity`
    - Most methods here that take in the `MultiBufferSource` have been replaced by a `SubmitNodeCollector`, and a `ModelFeatureRenderer$CrumblingOverlay` if the method is not used for item rendering
    - Most methods change their name from `render*` to `submit*`, with the main submit method now using a `BlockEntityRenderState`
    - All `BlockEntityRenderer`s now have a `BlockEntityRenderState` generic
    - `AbstractEndPortalRenderer` - A block entity renderer for the end portal.
    - `AbstractSignRenderer`
        - `getSignModel` now returns a `Model$Simple`
        - `renderSign` -> `submitSign`, now takes in a `Model$Simple` and no longer takes in a tint color
    - `BannerRenderer` has an overload that takes in a `SpecialModelRenderer$BakingContext`
        - The `EntityModelSet` constructor now takes in the `MaterialSet`
        - `renderPatterns` -> `submitPatterns` now takes in the `MaterialSet` and `ModelFeatureRenderer$CrumblingOverlay`, the `ModelPart` has been replaced with the `Model` and its render state, a `boolean` for whether to use the entity glint, and an outline color
            - The overload with two additional `boolean`s has been removed
        - `renderSpecial` -> `submitSpecial`, now takes in the outline color
    - `BeaconRenderer#renderBeaconBeam` -> `submitBeaconBeam`, no longer takes in the game time `long`
    - `BedRenderer` has an overload that takes in a `SpecialModelRenderer$BakingContext`
        - The `EntityModelSet` constructor now takes in the `MaterialSet`
        - `renderSpecial` -> `submitSpecial`, now takes in the outline color
    - `BlockEntityRenderDispatcher` now takes in the `MaterialSet` and `PlayerSkinRenderCache`
        - `render` -> `submit`, now takes in the `BlockEntityRenderState` instead of a `BlockEntity`, no longer takes in the partial tick `float`, and takes in the `CameraRenderState`
        - `getRenderer` now has an overload that can get the renderer from its `BlockEntityRenderState`
        - `tryExtractRenderState` - Gets the `BlockEntityRenderState` from its `BlockEntity`
        - `level`, `camera`, `cameraHitResult` is removed
        - `prepare` now only takes in the `Camera`
        - `setLevel` is removed
    - `BlockEntityRenderer` now has another generic `S` representing the `BlockEntityRenderState`
        - `render` -> `submit`, taking in the `BlockEntityRenderState`, the `PoseStack`, the `SubmitNodeCollector`, and the `CameraRenderState`
        - `createRenderState` - Creates the render state object.
        - `extractRenderState` - Extracts the render state from the block entity.
    - `BlockEntityRendererProvider$Context` is now a record, taking in a `MaterialSet` and `PlayerSkinRenderCache`
        - It now has another generic `S` representing the `BlockEntityRenderState`
    - `CopperGolemStatueBlockRenderer` - A block entity renderer for the copper golem statue.
    - `DecoratedPotRenderer` has an overload that takes in a `SpecialModelRenderer$BakingContext`
        - The `EntityModelSet` constructor now takes in the `MaterialSet`
        - `render` overload -> `submit`, now takes in the outline color
    - `HangingSignRenderer`
        - `createSignModel` now returns a `Model$Simple`
        - `renderInHand` now takes in a `MaterialSet`
    - `ShelfRenderer` - A block entity renderer for a shelf.
    - `ShulkerBoxRenderer` has an overload that takes in a `SpecialModelRenderer$BakingContext`
        - The `EntityModelSet` constructor now takes in the `MaterialSet`
        - `render` overload -> `submit`, now takes in the outline color
    - `SignRenderer`
        - `createSignModel` now returns a `Model$Simple`
        - `renderInHand` now takes in a `MaterialSet`
    - `SkullBlockRenderer#submitSkull` - Submits the skull model to the collector.
    - `SpawnerRenderer#renderEntityInSpawner` -> `submitEntityInSpawner`, now takes in the `CameraRenderState`
    - `TestInstanceREnderer` now takes in the `BlockEntityRendererProvider$Context`
- `net.minecraft.client.renderer.blockentity.state`
    - `BannerRenderState` - The render state for the banner block entity.
    - `BeaconRenderState` - The render state for the beacon block entity.
    - `BedRenderState` - The render state for the bed block entity.
    - `BellRenderState` - The render state for the bell block entity.
    - `BlockEntityRenderState` - The base render state for all block entities.
    - `BlockEntityWithBoundingBoxRenderState` - The render state for a block entity with a custom bounding box.
    - `BrushableBlockRenderState` - The render state for a brushable block entity.
    - `CampfireRenderState` - The render state for the campfire block entity.
    - `ChestRenderState` - The render state for the chest block entity.
    - `CondiutRenderState` - The render state for the conduit block entity.
    - `CopperGolemStatueRenderState` - The render state for the copper golem block entity.
    - `DecoratedPotRenderState` - The render state for the decorated pot block entity.
    - `EnchantTableRenderState` - The render state for the enchantment table block entity.
    - `EndGatewayRenderState` - The render state for the end gateway block entity.
    - `EndPortalRenderState` - The render state for the end portal block entity.
    - `LecternRenderState` - The render state for the lectern block entity.
    - `PistonHeadRenderState` - The render state for the piston head block entity.
    - `ShelfRenderState` - The render state for the shelf block entity.
    - `ShulkerBoxRenderState` - The render state for the shulker box block entity.
    - `SignRenderState` - The render state for the sign block entity.
    - `SkullBlockRenderState` - The render state for the skull block entity.
    - `SpawnerRenderState` - The render state for the spawner block entity.
    - `TestInstanceRenderState` - The render state for the test instance block entity.
    - `VaultRenderState` - The render state for the vault block entity.
- `net.minecraft.client.renderer.culling.Frustum`
    - `offset` - Offsets the position.
    - `pointInFrustum` - Checks whether the provided coordinate is within the frustum.
- `net.minecraft.client.renderer.entity`
    - Most methods here that take in the `MultiBufferSource` and light coordinates integer have been replaced by `SubmitNodeCollector` and a render state param
    - Most methods change their name from `render*` to `submit*`
    - `AbstractBoatRenderer#renderTypeAdditions` -> `submitTypeAdditions`
    - `AbstractMinecartRenderer#renderMinecartContents` -> `submitMinecartContents`
    - `AbstractSkeletonRenderer` takes in a `ArmorModelSet` instead of a `ModelLayerLocation`
    - `AbstractZombieRenderer` takes in a `ArmorModelSet` instead of a model
    - `ArmorModelSet` - A holder that maps some object to each humanoid armor slot. Typically holds the layer definitions, which are then baked into their associated models.
    - `BreezeRenderer#enable` is removed
    - `CopperGolemRenderer` - The renderer for the copper golem entity.
    - `DisplayRenderer#renderInner` -> `submitInner`
    - `EnderDragonRenderer#renderCrystalBeams` -> `submitCrystalBeams`
    - `EntityRenderDispatcher` now takes in the `AtlasManager`
        - `prepare` no longer takes in the `Level`
        - `setRenderShadow`, `setRenderHitBoxes`, `shouldRenderHitBoxes` are removed
        - `extractEntity` - Creates the render state from the entity and the partial tick.
        - `render` -> `submit`, now takes in the `CameraRenderState`
        - `setLevel` -> `resetCamera`, not one-to-one
        - `getPlayerRenderer` - Gets the `AvatarRenderer` from the given client player.
        - `overrideCameraOrientation`, `distanceToSqr`, `cameraOrientation` are removed
    - `EntityRenderer`
        - `NAMETAG_SCALE` is now public
        - `render(S, PoseStack, MultiBufferSource, int)` -> `submit(S, PoseStack, SubmitNodeCollector, CameraRenderState)`
        - `renderNameTag` -> `submitNameTag`, now takes in a `CameraRenderState`
        - `finalizeRenderState` - Extracts the information of the render state as a last step after `extractRenderState`, such as shadows.
    - `EntityRendererProvider$Context` now takes in the `PlayerSkinRenderCache` and `AtlasManager`
        - `getModelManager` is removed
        - `getMaterials` - Returns a mapper of material to atlas sprite.
        - `getPlayerSkinRenderCache` - Gets the render cache of player skins.
        - `getAtlas` - Returns the atlas for that location.
    - `EntityRenderers#createPlayerRenderers` now returns a map of `PlayerModelType`s to `AvatarRenderer`s
    - `ItemEntityRenderer`
        - `renderMultipleFromCount` -> `submitMultipleFromCount`
        - `renderMultipleFromCount(PoseStack, MultiBufferSource, int, ItemClusterRenderState, RandomSource)` -> `renderMultipleFromCount(PoseStack, SubmitNodeCollector, int, ItemClusterRenderState, RandomSource)`
    - `ItemRenderer` no longer takes in the `ItemModelResolver`
        - `getArmorFoilBuffer` -> `getFoilRenderTypes`, not one-to-one
        - `renderStatic` methods are removed
    - `MobRenderer#checkMagicName` - Returns whether the custom name matches the given string.
    - `PiglinRenderer` takes in a `ArmorModelSet` instead of a `ModelLayerLocation`
    - `TntMinecartRenderer#renderWhiteSolidBlock` -> `submitWhiteSolidBlock`, now takes in an outline color
    - `ZombieRenderer` takes in a `ArmorModelSet` instead of a `ModelLayerLocation`
    - `ZombifiedPiglinPiglinRenderer` takes in a `ArmorModelSet` instead of a `ModelLayerLocation`
- `net.minecraft.client.renderer.entity.layers`
    - `ArrowLayer` now deals with `AvatarRenderState` instead of `PlayerRenderState`
    - `BeeStingerLayer` now deals with `AvatarRenderState` instead of `PlayerRenderState`
    - `BlockDecorationLayer` - A layer that handles a block model transformed by an entity.
    - `BreezeWindLayer` now takes in the `EntityModelSet` instead of the `EntityRendererProvider$Context`
    - `CapeLayer` now deals with `AvatarRenderState` instead of `PlayerRenderState`
    - `CustomHeadLayer` now takes in the `PlayerSkinRenderCache`
    - `Deadmau5EarsLayer` now deals with `AvatarRenderState` instead of `PlayerRenderState`
    - `EquipmentLayerRenderer#renderLayers` now takes in the render state, `SubmitNodeCollector`, outline color, and an initial order instead of a `MultiBufferSource`
    - `HumanoidArmorLayer` now takes in `ArmorModelSet`s instead of models
        - `setPartVisibility` is removed
    - `ItemInHandLayer#renderArmWithItem` -> `submitArmWithItem`
    - `LivingEntityEmissiveLayer` now takes in a function for the texture instead of a `ResourceLocation` and a model instead of the `$DrawSelector`
        - `$DrawSelector` is removed
    - `ParrotOnShoulderLayer` now deals with `AvatarRenderState` instead of `PlayerRenderState`
    - `PlayerItemInHandLayer` now deals with `AvatarRenderState` instead of `PlayerRenderState`
    - `RenderLayer`
        - `renderColoredCutoutModel`, `coloredCutoutModelCopyLayerRender` now takes in a `Model` instead of an `EntityModel`, a `SubmitNodeCollector` instead of a `MultiBufferSource`, and an integer representing the order layer for rendering
        - `render` -> `submit`, taking in a `SubmitNodeCollector` instead of a `MultiBufferSource`
    - `SimpleEquipmentLayer` now takes in an order integer
    - `SpinAttackEffectLayer` now deals with `AvatarRenderState` instead of `PlayerRenderState`
    - `StuckInBodyLayer` now has an additional generic for the render state, also taking in the render state in the constructor
        - `numStuck` now takes in an `AvatarRenderState` instead of the `PlayerRenderState`
    - `VillagerProfessionLayer` now takes in two models
- `net.minecraft.client.renderer.entity.player.PlayerRenderer` -> `AvatarRenderer`
    - `render*Hand` now takes in a `SubmitNodeCollector` instead of a `MultiBufferSource`
- `net.minecraft.client.renderer.entity.state`
    - `CopperGolemRenderState` - The render state for the copper golem entity.
    - `DisplayEntityRenderState#cameraYRot`, `cameraXRot` - The rotation of the camera.
    - `EntityRenderState`
        - `NO_OUTLINE` - A constant that represents the color for no outline.
        - `outlineColor` - The outline color of the entity.
        - `lightCoords` - The packed light coordinates used to light the entity.
        - `shadowPieces`, `$ShadowPiece` - Represents the relative coordinates of the shadow(s) the entity is casting.
    - `FallingBlockRenderState` fields and implementations have all been moved to `MovingBlockRenderState`
    - `LivingEntityRenderState`
        - `appearsGlowing` -> `EntityRenderState#appearsGlowing`, now a method
        - `customName` is removed
    - `PaintingRenderState#lightCoords` -> `lightCoordsPerBlock`
    - `PlayerRenderState` -> `AvatarRenderState`
        - `useItemRemainingTicks`, `swinging` are removed
        - `showDeadMouseEars` -> `showExtraEars`
    - `SheepRenderState`
        - `id` is removed
        - `isJebSheep` is now a field instead of a method
    - `WitherSkullRenderState#xRot`, `yRot` -> `modeState`, not one-to-one
- `net.minecraft.client.renderer.feature`
    - `BlockFeatureRenderer` - Renders the submitted blocks, block models, or falling blocks.
    - `CustomFeatureRenderer` - Renders the submitted geometry via a passed function.
    - `FeatureRenderDispatcher` - Dispatches all features to render from the submitted node collector objects.
    - `FlameFeatureRenderer` - Renders the submitted entity on fire animation.
    - `HitboxFeatureRenderer` - Renders the submitted entity hitbox.
    - `ItemFeatureRenderer` - Renders the submitted items.
    - `LeashFeatureRenderer` - Renders the submitted leash attached to entities.
    - `ModelFeatureRenderer` - Renders the submitted `Model`s.
    - `ModelPartFeatureRenderer` - Renders the submitted `ModelPart`s.
    - `NameTagFeatureRenderer` - Renders the submitted name tags.
    - `ParticleFeatureRenderer` - Renders the submitted particles.
    - `ShadowFeatureRenderer` - Render the submitted entity shadow.
    - `TextFeatureRenderer` - Renders the submitted text.
- `net.minecraft.client.renderer.item`
    - `ItemModel$BakingContext` now takes in a `MaterialSet` and `PlayerSkinRenderCache`, and implements `SpecialModelRenderer$BakingContext`
    - `ItemStackRenderState#render` -> `submit`, taking in a `SubmitNodeCollector` instead of a `MultiBufferSource` and an outline color
- `net.minecraft.client.renderer.special`
    - Most methods here that take in the `MultiBufferSource` have been replaced by `SubmitNodeCollector`
    - `ChestSpecialRenderer` now takes in a `MaterialSet`
        - `*COPPER*` - Textures for the copper chest.
    - `ConduitSpecialRenderer` now takes in a `MaterialSet`
    - `CopperGolemStatueSpecialRenderer` - A special renderer for the copper golem statue as an item.
    - `HangingSignSpecialRenderer` now takes in a `MaterialSet` and a `Model$Simple` instead of a `Model`
    - `NoDataSpecialModelRenderer#render` -> `submit`, now takes in an outline color
    - `PlayerHeadSpecialRenderer` now takes in the `PlayerSkinRenderCache`
    - `ShieldSpecialRenderer` now takes in a `MaterialSet`
    - `SpecialModelRenderer`
        - `render` -> `submit`, now takes in an outline color
        - `$BakingContext` - The context used to bake a special item model.
        - `$Unbaked#bake` now takes in a `SpecialModelRenderer$BakingContext` instead of an `EntityModelSet`
    - `SpecialModelRenderers#createBlockRenderers` now takes in a `SpecialModelRenderer$BakingContext` instead of an `EntityModelSet`
    - `StandingSignSpecialRenderer` now takes in a `MaterialSet` and a `Model$Simple` instead of a `Model`
- `net.minecraft.client.renderer.state`
    - `BlockBreakingRenderState` - The render state for how far the current block has been broken.
    - `BlockOutlineRenderState` - The render state for the outline of a block via its `VoxelShape`s.
    - `CameraRenderState` - The render state of the camera.
    - `LevelRenderState` - The render state of the dynamic features in a level.
    - `ParticleGroupRenderState` - The render state for a group of particles.
    - `ParticlesRenderState` - The render state for all particles.
    - `QuadParticleRenderState` - The render group state for all single quad particles.
    - `SkyRenderState` - The render state of the sky, including the moon and stars.
    - `WeatherRenderState` - The render state of the current weather.
    - `WorldBorderRenderState` - The render state of the world border.
- `net.minecraft.client.renderer.texture`
    - `SkinTextureDownloader` is now an instance class rather than a static method holder, taking in a `Proxy`, `TextureManager`, and the main thread `Executor`
        - Most methods that were previously static are now instance methods
    - `SpriteContents` now takes in an optional `AnimationMetadataSection` and `MetadataSectionType$WithValue` list instead of a `ResourceMetadata`
        - `metadata` -> `getAdditionalMetadata`, not one-to-one
    - `SpriteLoader`
        - `DEFAULT_METADATA_SECTIONS` is removed
        - `stitch` is now private
        - `runSpriteSuppliers` is now private
        - `loadAndStitch(ResourceManager, ResourceLocation, int, Executor)` isn removed
        - `loadAndStitch` now takes a set of `MetadataSectionType`s instead of a collection
        - `$Preparations`
            - `waitForUpload` is removed
            - `getSprite` - Returns the atlas sprite for a given resource location.
    - `TextureAtlasSprite#isAnimated` -> `SpriteContents#isAnimated`
- `net.minecraft.client.renderer.texture.atlas.SpriteResourceLoader#create` now takes a set of `MetadataSectionType`s instead of a collection
- `net.minecraft.client.resources`
    - `MapDecorationTextureManager` class is removed
    - `PaintingTextureManager` class is removed
    - `PlayerSkin$Model` -> `PlayerModelType`, not one-to-one
        - The constructor not takes in the legacy service name
        - `byName` -> `byLegacyServicesName`
    - `SkinManager` now takes in `Services` instead of a `MinecraftSessionService`, and a `SkinTextureDownloader`
        - `lookupInsecure` -> `createLookup`, now taking in a boolean of whether to check for insecure skins
        - `getOrLoad` -> `get`
    - `TextureAtlasHolder` class is removed
- `net.minecraft.client.resources.model`
    - `AtlasIds` -> `net.minecraft.data.AtlasIds`
    - `AtlasSet` -> `AtlasManager`, not one-to-one
        - `forEach` - Iterates through each of the atlas sheets.
    - `Material`
        - `sprite` -> `MaterialSet#get`
        - `buffer` now takes in a `MaterialSet`
    - `MaterialSet` - A map of material to its atlas sprite.
    - `ModelBakery` now takes in a `MaterialSet` and `PlayerSkinRenderCache`
    - `ModelManager` is no longer `AutoCloseable`
        - The constructor takes in the `PlayerSkinRenderCache`, `AtlasManager` instead of the `TextureManager`, and the max mipmap levels integer
        - `getAtlas` -> `MaterialSet#get`
        - `updateMaxMipLevel` -> `AtlasManager#updateMaxMipLevel`
- `net.minecraft.core.particles`
    - `ParticleGroup` -> `ParticleLimit`
    - `ParticleTypes`
        - `DRAGON_BREATH` now uses a `PowerParticleOption`
        - `EFFECT` now uses a `SpellParticleOption`
        - `FLASH` now uses a `ColorParticleOption`
        - `INSTANT_EFFECT` now uses a `SpellParticleOption`
    - `PowerParticleOption` - A particle option for the dragon's breath.
    - `SpellParticleOption` - A particle option for potion effects.

## The Font Glyph Pipeline

Glyphs have been partially reworked in the backend to both unify both characters and their effects to a single renderable and add a baking flow, albeit delayed. This will provide a brief overview of this new flow.

Let's start from the top when the assets are reloaded, causing the `FontManager` to run. The font definitions are loaded and passed to their appropriate `GlyphProviderDefinition`, which for this example with immediately unpack into a `GlyphProvider$Loader`. The loader reads whatever information it needs (most likely a texture) and maps it to an associated codepoint as an `UnbakedGlyph`. An `UnbakedGlyph` represents a single character containing its `GlyphInfo` with the position metadata and a `bake` method to write it to a texture. `bake` takes in a `UnbakedGlyph$Stitcher`, which itself takes in a `GlyphBitmap` to correctly position and upload the glyph along with the `GlyphInfo` for any offset adjustments. Then, after and resolving and finalizing, the `FontSet`s are created using `FontSet#reload`. This resets the textures and cache and calls `FontSet#selectProviders` to store the active list of `GlyphProvider`s and populates the `glyphsByWidth` by mapping the character advance to a list of matching codepoints. At this point, all of the loading has been done. While the glyphs are stored in their unbaked format, they are not baked and cached until that specific character is rendered.

Now, let's move onto the `FontDescription`. A `FontDescription` provides a one-to-one map to a `GlyphSource`, which is responsible for obtaining the glyphs. Vanilla provides three `FontDescription`s: `$Resource` for mapping to the `FontSet` (the font JSON name) as mentioned above, `$AtlasSprite` to interpret an atlas as a bunch of glyphs, and `$PlayerSprite` for rendering the player head and hat. For a given `Component`, the `FontDescription` used can be set as its `Style` via `Style#withFont`.

So, let's assume you are drawing text to the screen using `FontDescription#DEFAULT`, which uses the defined `assets/minecraft/font/default.json`. Eventually, this will either call `Font#drawInBatch` or `drawInBatch8xOutline`. Then, whether through the `StringSplitter` or directly, the `GlyphSource` is obtained from the `FontDescription`. Then, `GlyphSource#getGlyph` is called. For the `FontDescription$AtlasSprite` and `$PlayerSprite`, this just returns a wrapper around the texture in question. For the `$Resource` though, this internally calls `FontSet#getGlyph`, which stores the `UnbakedGlyph` as part of a `FontSet$DelayedBake`, which it then immediately resolves to the `BakedGlyph` by calling `UnbakedGlyph#bake` to write the glyph to some texture.

A `BakedGlyph` contains two methods: the `GlyphInfo` for the position metadata like before, and a method called `createGlyph`, which returns a `TextRenderable`: a renderer to draw the glyph to the screen. `TextRenderable` also has a subinterface called `PlainTextRenderable` for help with texture sprites. Internally, all resource `BakedGlyph`s are `BakedSheetGlyph`s, as the `UnbakedGlyph$Stitcher#stitch` called just wraps around `GlyphStitcher#stitch`. You can think of the `BakedSheetGlyph` as a view onto a 256x256 `FontTexture`s, where if there is not enough space on one `FontTexture`, then a new `FontTexture` is created.

`BakedSheetGlyph` also implements `EffectGlyph`, which is likely supposed to render some sort of effect on the text. This functionality, while it is currently implemented as just another object, is only used for the white glyph, which goes unused.

### Object Info

`ObjectInfo`s are the server side implementations to construct the `FontDescription` used to render an arbitrary object through the glyph pipeline. Each info takes in the `FontDescription` and a regular `String` to display a simple description. Additionally, it takes in a `MapCodec` to encode and decode from the object contents.

Note that for custom `FontDescription`s to map to a source properly, you will need to somehow modify `FontManager$CachedFontProvider#getGlyphSource` or implement a custom `Font$Provider`.

```java
// A basic font description
public static record BlockDescription(Block block) implements FontDescription {}

// A simple object info
public record BlockInfo(Block block) implements ObjectInfo {
    // The codec to send the information over the network
    public static final MapCodec<BlockInfo> MAP_CODEC = BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block");

    @Override
    public FontDescription fontDescription() {
        // The font description to render
        return new BlockDescription(this.block);
    }

    @Override
    public String description() {
        // Just a text description of the object
        return Objects.toString(BuiltInRegistries.BLOCK.getKey(this.block));
    }

    @Override
    public MapCodec<? extends ObjectInfo> codec() {
        return MAP_CODEC;
    }
}

// Create the `GlyphSource` and `PlainTextRenderable` used to render the object
public record BlockRenderable(...) implements PlainTextRenderable {
    // ...
}

public static GlyphSource create(Block block) {
    return new SingleSpriteSource(...);
}
```

The `ObjectInfo` also needs to be rendered to `ObjectInfos#ID_MAPPER`:

```java
// Assumes the the mapper has been made public
ObjectInfos.ID_MAPPER.put("examplemod:block", BlockInfo.MAP_CODEC);
```

### Component Contents

Component contents also now use an id mapper instead of holding the id on the type. This means that creating a custom component is done by somehow hooking into `ComponentSerialization#bootstrap` and registering the map codec of your `ComponentContents` implementation to the mapper.

### Data Sources

Data sources has received a similar treatment to component contents, now using an id mapper instead of holding the id on the type. The data source is then registered by registering to `DataSources#ID_MAPPER` the map codec of your `DataSource`. Note that the mapper field is private, so you will probably need to use an available workaround.

- `com.mojang.blaze3d.font`
    - `GlyphInfo`
        - `bake` -> `UnbakedGlyph#bake`, now takes in a `UnbakedGlyph$Stitcher` instead of a function
            - The function behavior is replaced by `UnbakedGlyph$Stitcher#stitch`
        - `$SpaceGlyphInfo` -> `GlyphInfo#simple` and `EmptyGlyph`, not one-to-one
        - `$Stitched` -> `UnbakedGlyph`, not one-to-one
    - `GlyphProvider#getGlyph` now returns an `UnbakedGlyph`
    - `SheetGlyphInfo` -> `GlyphBitmap`
- `net.minecraft.client.gui`
    - `Font` no longer takes in a function and boolean, instead a `Font$Provider`
        - `random` is now private
        - `$GlyphVisitor`
            - `acceptGlyph` now takes in a `TextRenderable` instead of a `BakedGlyph$GlyphInstance`
            - `acceptEffect` now only takes in a `TextRenderable`
        - `$PreparedTextBuilder#accept` now has an override that takes in a `BakedGlyph` instead of its codepoint
        - `$Provider` - A simple interface for providing the glyph source based on its description, along with the empty glyph.
    - `GlyphSource` - An interface that holds the baked glyphs based on its codepoint.
- `net.minecraft.client.gui.font`
    - `AtlasGlyphProvider` - A glyph provider based off a texture atlas.
    - `FontManager` now takes in the `AtlasManager` and `PlayerSkinRenderCache`
    - `FontSet` now takes in a `GlyphStitcher` instead of a `TextureManager` and no longer takes in the name
        - `name` is removed
        - `source` - Returns the glyph source depending given whether only non-fishy glyphs should be seen.
        - `getGlyphInfo`, `getGlyph` -> `getGlyph`, now package-private, not one-to-one
        - `getRandomGlyph` now takes in a `RandomSource` and a codepoint instead of the `GlyphInfo`, returning a `BakedGlyph`
        - `whiteGlyph` now returns an `EffectGlyph`
        - `$GlyphSource` - A source that can get the glyphs to bake.
    - `FontTexture#add` now takes in a `GlyphInfo` and `GlyphBitmap`, returning a `BakedSheetGlyph`
    - `GlyphStitcher` - A class that creates `BakedGlyph`s from its glyph information.
    - `PlainTextRenderable` - An implementation of `TextRenderable` that determines how a sprite is rendered.
    - `PlayerGlyphProvider` - A glyph provider for rendering the player head and hat.
    - `SingleSpriteSource` - A glyph source that only contains one glyph, such as a texture.
    - `TextRenderable` - Represents how a text representation, like a glyph in a font, is rendered.
- `net.minecraft.client.gui.font.glyphs`
    - `BakedGlyph` is now an interface that creates the renderable object
        - It original purpose has been moved to `BakedSheetGlyph`
    - `EffectGlyph` - An interface that creates the renderable effect of some glyph.
    - `EmptyGlyph` now implements `UnbakedGlyph` instead of extending `BakedGlyph`
        - The constructor takes in the text advance of the glyph.
    - `SpecialGlyphs#bake` now returns a `BakedSheetGlyph`
        - This method is technically new.
- `net.minecraft.client.gui.font.providers`
    - `BitmapProvider$Glyph` now implements `UnbakedGlyph` instead of `GlyphInfo$Stitched`
    - `UnihexProvider$Glyph` now implements `UnbakedGlyph` instead of `GlyphInfo$Stitched`
- `net.minecraft.client.gui.render.state`
    - `GlyphEffectRenderState` is removed
        - Use `GlyphRenderState`
    - `GlyphRenderState` now takes in a `TextRenderable` instead of a `BakedGlyph$Instance`
- `net.minecraft.network.chat`
    - `Component#object` - Creates a mutable component with object contents.
    - `ComponentContents#type` -> `codec`, dropping the string id
        - `$Type` is removed
    - `ComponentSerialization`
        - `createLegacyComponentMatcher`  no longer requires a `StringRepresentable` generic, instead taking in a id mapper using `String` keys
        - `$FuzzyCodec` now takes in a collection of map codecs instead of a list
    - `FontDescription` - An identifier that describes a font, typically as a location or sprite.
    - `Style` is now final
        - `getFont` now returns a `FontDescription`
        - `withFont` now takes in a `FontDescription` instead of a `ResourceLocation`
        - `DEFAULT_FONT` is removed
- `net.minecraft.network.chat.contents`
    - `BlockDataSource` -> `.data.BlockDataSource`
    - `DataSource` -> `.data.DataSource`
        - `type` -> `codec`, dropping the string id
    - `EntityDataSource` -> `.data.EntityDataSource`
    - `*Contents`
        - `CODEC` -> `MAP_CODEC`
        - `TYPE` is removed
    - `ObjectContents` - An arbitrary piece of content that can be written as part of a component, like a sprite.
    - `StorageDataSource` -> `.data.StorageDataSource`
- `net.minecraft.network.chat.contents.data.DataSources` - All vanilla-registered data sources.
- `net.minecraft.network.chat.contents.objects`
    - `AtlasSprite` - An object info for a sprite in a texture atlas.
    - `ObjectInfo` - Information relating the references for an arbitrary object through the glyph pipeline.
    - `ObjectInfos` - All vanilla object infos.
    - `PlayerSprite` - An object info for a player head and hat texture.

## The JSON-RPC Management Servers

Minecraft has introduced support for remotely managing dedicated servers through a JSON-RPC websocket. This can be enabled through the `management-server-enabled` property, which listens for a server on `localhost:25585` by default. The entire system handles not only constructing the network requests sent via `JsonElement`s, but also the schemas each request uses. These schemas can then be generated through the `JsonRpcApiSchema` data provider.

There are two types of RPC methods supported by the system: `IncomingRpcMethod`s for requests from the management server, and `OutgoingRpcMethod`s to either notify or request information from the management server. Both of these are static registry objects, but rather than implementing the interfaces, they are typically constructed and registered through their associated builders.

### Schemas

`Schema`s, as the name implies, are the specification of the JSON objects. They are constructed in a similar fashion `JsonElement`s. Most are constructed as `Schema#record`s, with their field and types being populated via `withField`, before being stored in a `SchemaComponent`, which maps a reference name to the schema. Then the `Schema`s can be obtained via `asRef` or `asArray` to get the object or array implementation, respectively. The `SchemaComponent`s themselves and registered to the list `Schema#SCHEMA_REGISTRY` for reference resolving.

```json5
// An example json for changing the weather:
{
    "weather": "clear|rain|thunder",
    "duration": -1,
}
```

Here is what the associated schema component would look like:

```java
public static final SchemaComponent WEATHER_SCHEMA = new SchemaComponent(
    // The reference name
    // While we can use colon, it would need to be percent encoded as defined by RFC 3986
    "examplemod_weather",
    // The schema to use
    Schema.record()
        .withField("weather", Schema.ofEnum(List.of("clear", "rain", "thunder")))
        .withField("duration", Schema.INT_SCHEMA)
);

// Register the schema to the registry
Schema.getSchemaRegistry().add(WEATHER_SCHEMA);
```

Note that schemas currently cannot specify whether an individual property is optional or not.

### `IncomingRpcMethod`

`IncomingRpcMethod`s are constructed and registered through the `method` builder. These methods take in the handler function to update the minecraft server, a codec for the result, and an optional codec for the parameters sent from the management server. From there, the builder contains methods towards the discovery service and how to execute the method. `$IncomingRpcMethodBuilder#response` and `param` define the parameters and response sent by the management server. `description` is used by the discovery service for the schemas. `undiscoverable` hides the route by default. Finally, `notOnMainThread` tells the method that it can run off the main thread. Once the desired methods are called, then either `build` or `register` can be used to construct the object, taking in the route endpoint as a namespace and path.

The methods passed in take in the `MinecraftApi` for communicating with the dedicated server through specified 'services', and the current `ClientInfo` connection. It also takes in the parameter object if it exists.

```java
// Construct the dto object
public record WeatherDto(String weather, int duration) {
    public static final Codec<WeatherDto> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            Codec.STRING.fieldOf("weather").forGetter(WeatherDto::weather),
            Codec.INT.fieldOf("duration").forGetter(WeatherDto::duration),
        )
        .apply(instance, WeatherDto::new)
    );

    // Create the incoming method handler
    public static WeatherDto handleWeather(MinecraftApi api, WeatherDto params, ClientInfo info) {
        // Note that the api makes the server private since it expects the
        // logic to be handled through one of the middle layer services.
        // We will assume the `server` field was made public somehow.
        if (params.weather().equals("clear")) {
            api.server.overworld().setWeatherParameters(params.duration(), 0, false, false);
        } else if (params.weather().equals("rain")) {
            api.server.overworld().setWeatherParameters(0, params.duration(), true, false);
        } else if (params.weather().equals("thunder")) {
            api.server.overworld().setWeatherParameters(0, params.duration(), true, true);
        }

        return params;
    }
}

// Construct and register the method endpoint
IncomingRpcMethod.method(
    // The handler method
    WeatherDto::handleWeather,
    // The codec for the parameters
    WeatherDto.CODEC,
    // The codec for the result
    WeatherDto.CODEC
)
    // Sets the description of the route
    .description("Sets the weather")
    // The parameters
    .param(new ParamInfo(
        // The name of the object
        "weather",
        // The schema
        WEATHER_SCHEMA.asRef()
    ))
    // The response
    .response(new ResultInfo(
        // The name of the object
        "weather",
        // The schema
        WEATHER_SCHEMA.asRef()
    ))
    // Build and register the route
    .register(
        BuiltInRegistries.INCOMING_RPC_METHOD,
        // The endpoint namespace
        "examplemod",
        // The endpoint path
        "weather/update"
    );
```

### `OutgoingRpcMethod`

`OutgoingRpcMethod`s are constructed and registered through the `notification` or `request` builder. Notifications typically only send parameters with no returned result, while requests provide a result as well. In either case, they just take in codecs for the params or result when present. `$OutgoingRpcMethod#response` and `param` define the parameters and response sent by the minecraft server. `description` is used by the discovery service for the schemas. Once the desired methods are called, then `register` can be used to construct the object, taking in the route endpoint as a namespace and path and wrapping it in a `Holder$Reference`.

Currently, vanilla only broadcasts notifications through the `NotificationService`: a remote logger for specific method actions, like player join or changing game rule. While there are results, they must be read via `Connection#sendRequest` and handled asynchronously.

```java
// Construct the outgoing method
// Since the minecraft server will be sending these, we need to hold the object
public static final Holder.Reference<OutgoingRpcMethod.ParmeterlessNotification> SOMETHING_HAPPENED = OutgoingRpcMethod.notification()
        .description("Something happened!")
        .register("examplemod", "something");

// Send notification, assume we have access to the `ManagementServer` managementServer
managementServer.forEachConnection(connection -> connection.sendNotification(SOMETHING_HAPPENED));
```

- `net.minecraft.SharedConstants#RPC_MANAGEMENT_SERVER_API_VERSION` - The API version for JSON RPC server management.
- `net.minecraft.core.registries`
    - `BuiltInRegistries`, `Registries`
        - `INCOMING_RPC_METHOD` - Registry for queries to the RPC service.
        - `OUTGOING_RPC_METHOD` - Registry for broadcasts from the RPC service.
- `net.minecraft.server`
    - `MinecraftServer`
        - `notificationManager` - Returns the current notification manager.
        - `onGameRuleChanged` - Handles what happens when the game rule of the current server changes.
        - `getOperatorUserPermissionLevel` -> `operatorUserPermissionLevel`
        - `isLevelEnabled` -> `isAllowedToEnterPortal`, not one-to-one
        - `setPvpAllowed` replaced by `GameRules#RULE_PVP`
        - `setFlightAllowed` replaced by `DedicatedServerProperties#allowFlight`
        - `isCommandBlockEnabled` is no longer abstract
        - `isSpawnerBlockEnabled` - Returns whether spawner blocks can spawn entities.
        - `getPlayerIdleTimeout` -> `playerIdleTimeout`
        - `kickUnlistedPlayers` no longer takes in the `CommandSourceStack`
        - `pauseWhileEmptySeconds` -> `pauseWhenEmptySeconds`
        - `getSpawnProtectionRadius` -> `DedicatedServer#spawnProtectionRadius`
        - `isUsingWhiteList` - Handles enabling whitelist users for the server.
        - `setAutoSave`, `isAutoSave` - Handles whether the server should auto save every so often.
- `net.minecraft.server.dedicated`
    - `DedicatedServer` now takes in a `LevelLoadListener` instead of a `ChunkProgressListenerFactory`
        - `setAllowFlight` - Sets whether the players can fly in the server.
        - `setDifficulty` - Sets the game difficulty of the server.
        - `viewDistance`, `setViewDistance` - Handles the view distance of the server (renderable chunks).
        - `simulationDistance`, `setSimulationDistance` - Handles the simulation distance of the server (logic running chunks).
        - `setMaxPlayers` - Sets the maximum number of players that can join the server.
        - `setSpawnProtectionRadius` - Sets the radius of chunks that should be under spawn protection.
        - `setRepliesToStatus` - Sets whether the game uses the legacy query handler.
        - `setHidesOnlinePlayers` - Sets whether online players should be hidden from other players.
        - `setOperatorUserPermissionLevel` - Sets the permission level of operator users.
        - `statusHeartbeatInterval`, `setStatusHeartbeatInterval` - Handles the interval that the management server will check the minecraft server is still alive.
        - `entityBroadcastRangePercentage`, `setEntityBroadcastRangePercentage` - Handles the broadcast range scalar for entity tracking.
        - `forceGameMode`, `setForceGameMode` - Handles forcing all game modes for the users.
        - `gameMode`, `setGameMode` - Sets the default game mode of the server.
        - `setAcceptsTransfers` - Sets whether the server accepts client transfers from other servers.
        - `setPauseWhenEmptySeconds` - Sets the number of seconds that the server should wait until it stops ticking when there are no players.
    - `DedicatedServerProperties`
        - `pvp` replaced by `GameRules#RULE_PVP`
        - `allowFlight`, `motd`, `forceGameMode`, `enforceWhitelist`, `difficulty`, `gameMode`, `spawnProtection`, `opPermissionLevel`,  `viewDistance`, `simulationDistance`, `enableStatus`, `hideOnlinePlayers`, `entityBroadcastRangePercentage`, `pauseWhenEmptySeconds`, `acceptsTransfers` are now mutable properties
        - `allowNether` replaced by `GameRules#RULE_ALLOW_NETHER`
        - `spawnMonsters` replaced by `GameRules#RULE_SPAWN_MONSTERS`
        - `enabledCommandBlock` replaced by `GameRules#RULE_COMMAND_BLOCKS_ENABLED`
        - `managementServerEnabled` - Returns whether a management server is enabled.
        - `managementServerHost` - Returns the host the management server is communicating on.
        - `managementServerPort` - Returns the port the management server is communicating on.
        - `statusHeartbeatInterval` - Returns the heartbeat interval the management server sends to make sure the minecraft server is still alive.
        - `MANAGEMENT_SERVER_TLS_ENABLED_KEY`, `MANAGEMENT_SERVER_TLS_KEYSTORE_KEY`, `MANAGEMENT_SERVER_TLS_KEYSTORE_PASSWORD_KEY` - TLS keystore setup for communication with the management server.
        - `managementServerSecret` - The authorization token secret.
        - `managementServerTlsEnabled` - If TLS should be used as the communication protocol.
        - `managementServerTlsKeystore` - Specifies the filepath used for the TLS keystore.
        - `managementServerTlsKeystorePassword` - Specifies the password for the keystore file.
    - `Settings#getMutable` - Gets a mutable property for the settings key and default.
- `net.minecraft.server.jsonrpc`
    - `Connection` - The inbound handler for json elements sent by the management server.
    - `IncomingRpcMethod` - A definition and handler for the incoming message sent by the management server.
    - `IncomingRpcMethods` - All vanilla incoming RPC handlers.
    - `JsonRPCErrors` - An enum of errors that can be thrown when attempting to read, parse, or execute the method.
    - `JsonRpcLogger` - A general logger for things performed by the minecraft server, whether communication or logic.
    - `JsonRpcNotificationService` - A notification service that broadcasts to all connected minecraft servers.
    - `JsonRPCUtils` - A utility for request objects and errors.
    - `ManagementServer` - A basic websocket comms for the minecraft server to communicate with the management server.
    - `OutgoingRpcMethod` - A definition and handler for outgoing requests to the management server.
    - `OutgoingRpcMethods` - All vanilla outgoing RPC handlers.
    - `PendingRpcRequest` - A pending request made by the minecraft server to the management server.
- `net.minecraft.server.jsonrpc.api`
    - `MethodInfo` - Defines a method that is handled by the management server, either inbound or outbound.
    - `ParamInfo` - Defines the parameters a specific method takes in.
    - `PlayerDto` - A data transfer object representing the player.
    - `ReferenceUtil` - A utility for creating a local reference URI.
    - `ResultInfo` - Defines the response a specific method returns.
    - `Schema` - An implementation of the json schema used the discovery service, parameters, and results.
    - `SchemaComponent` - A reference definition of some component within a schema.
- `net.minecraft.server.jsonrpc.dataprovider.JsonRpcApiSchema` - A data provider that generates the json schema from the discovery service.
- `net.minecraft.server.jsonrpc.internalapi`
    - `GameRules` - An api that gets the current game rule value.
    - `MinecraftAllowListService` - A minecraft middle layer that handles communication from the management server about the allow list.
    - `MinecraftAllowListServiceImpl` - The allow list implementation.
    - `MinecraftApi` - A minecraft api that handles all the services that communicate with the management server.
    - `MinecraftBanListService` - A minecraft middle layer that handles communication from the management server about the ban list.
    - `MinecraftBanListServiceImpl` - The ban list implementation.
    - `MinecraftExecutorService` - A minecraft middle layer that submits an arbitrary runnable or supplier to execute on the server.
    - `MinecraftExecutorServiceImpl` - The executor implementation.
    - `MinecraftGameRuleService` - A minecraft middle layer that handles communication from the management server about the game rules.
    - `MinecraftExecutorServiceImpl` - The executor implementation.
    - `MinecraftOperatorListService` - A minecraft middle layer that handles communication from the management server about the operator commands.
    - `MinecraftOperatorListServiceImpl` - The operator list implementation.
    - `MinecraftPlayerListService` - A minecraft middle layer that handles communication from the management server about the player list.
    - `MinecraftPlayerListServiceImpl` - The player list implementation.
    - `MinecraftServerSettingsService` - A minecraft middle layer that handles communication from the management server about the server settings.
    - `MinecraftServerSettingsServiceImpl` - The server settings implementation.
    - `MinecraftServerStateService` - A minecraft middle layer that handles communication from the management server about the current server state and send messages.
    - `MinecraftServerStateServiceImpl` - The server state implementation.
- `net.minecraft.server.jsonrpc.methods`
    - `AllowlistService` - A service that handles communication from the management server about the allow list.
    - `BanlistService` - A service that handles communication from the management server about the player ban list.
    - `ClientInfo` - An identifier for the current minecraft server connection to the management server.
    - `DiscoveryService` - A service that displays the schemas of all services supported by the management server.
    - `EncodeJsonRpcException` - An exception thrown when attempting to encode the json packet.
    - `GameRulesService` - A service that handles communication from the management server about the game rules.
    - `IllegalMethodDefinitionException` - An exception thrown when the method definition provided is illegal.
    - `InvalidParameterJsonRpcException` - An exception thrown when the parameters to the method are invalid.
    - `InvalidRequestJsonRpcException` - An exception thrown when the request is invalid.
    - `IpBanlistService` - A service that handles communication from the management server about the ip ban list.
    - `Message` - A data transfer object representing a literal or translatable component.
    - `MethodNotFoundJsonRpcException` - An exception thrown when a 404 error occurs, indicating that the method doesn't exist.
    - `OperatorService` - A service that handles communication from the management server about the operator commands.
    - `PlayerService` - A service that handles communication from the management server about the player list.
    - `RemoteRpcErrorException` - An exception thrown when something goes wrong on the management server.
    - `ServerSettingsService` - A service that handles communication from the management server about the server settings.
    - `ServerStateService` - A service that handles communication from the management server about the current server state and send messages.
- `net.minecraft.server.jsonrpc.security`
    - `AuthenticationHandler` - Handles the validation of the authentication token bearer in the request.
    - `JsonRpcSslContextProvider` - Provides the keystore context for the TLS communication.
    - `SecurityConfig` - Handles the secret key, from checking basic validaty to generating a new one.
- `net.minecraft.server.jsonrpc.websocket`
    - `JsonToWebSocketEncoder` - A message to message encoder for a json.
    - `WebSocketToJsonCodec` - A message to message decoder for a json.
- `net.minecraft.server.notifications`
    - `EmptyNotificationService` - A notification service that does nothing.
    - `NotificationManager` - A manager for handling multiple notification services.
    - `NotificationService` - A service that defines prospective actions taken by a listener, like a management server.
- `net.minecraft.server.players`
    - `BanListEntry`
        - `getReason` can now be `null`
        - `getReasonMessage` - Returns the translatable component of the ban reason.
    - `IpBanList` now takes in the `NotificationService`
        - `add`, `remove` - Handles entries on the list.
    - `PlayerList` now takes in the `NotificationService` instead of the max players
        - `maxPlayers` is removed
        - `op` now has an override for the permission level and bypass limit boolean
        - `setUsingWhiteList` -> `MinecraftServer#setUsingWhiteList`
        - `getPlayer` - Gets a player by their name.
    - `ServerOpList` now takes in the `NotificationService`
        - `add`, `remove` - Handles entries on the list.
    - `StoredUserEntry#getUser` is now public
    - `StoredUserList` now takes in the `NotificationService`
        - `add`, `remove` now return a boolean if successful
        - `remove(StoredUserEntry<K>)` is removed
        - `clear` - Clears all stored users.
    - `UserBanList` now takes in the `NotificationService`
        - `add`, `remove` - Handles entries on the list.
    - `UserWhiteList` now takes in the `NotificationService`
        - `add`, `remove` - Handles entries on the list.

## Input Handling Consolidation

Input handling has previously passed around the raw values, each in their own separate argument, provided by GLFW. However, a good bit of the handling logic is redundant, as specific keys are commonly checked, or all values are passed around in a game of hot potato from one method to the next. With this in mind, the input handlers in `GuiEventListener` and other calls, are now handled through event objects. These objects still contain the info GLFW passed through; however, now they have a lot of common checks through the super `InputWithModifiers` interface.

There are two types of events: `KeyEvent`s for key presses, and `MouseButtonEvent`s for mouse presses. Key, scancode, and modifiers are wrapped into the `KeyEvent`. Button, modifiers, and the XY screen position are wrapped into the `MouseButtonEvent`. As such, it is generally a drag-and-drop replacement of just removing any of the parameters mentioned above and replacing them with their appropriate event.

### Key Mapping Categories

Key mappings have changed slightly, no longer taking in a raw string for its category, and instead using a `KeyMapping$Category` record, which is essentially a namspaced string. Categories can be created using `KeyMapping$Category#register`; otherwise, an error will be thrown whenever the mapping is used as part of a comparator.

```java
public static final KeyMapping.Category EXAMPLE = KeyMapping.Category.register(ResourceLocation.withNamespaceAndPath("examplemod", "example"));
```

### Double-Click Expansion

`GuiEventListener#mouseClicked` now takes in whether the click was in fact a double-click as a boolean.

```java
// For some Screen subclass (or AbstractWidget or any GUI object rendering)

@Override
public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
    // ...
    return false;
}
```

- `com.mojang.blaze3d.platform.InputConstants`
    - `MOD_SHIFT`, `MOD_ALT`, `MOD_SUPER`, `MOD_CAPS_LOCK`, `MOD_NUM_LOCK` - Modifier keys to transform a key input.
    - `KEY_LWIN`, `KEY_RWIN` -> `KEY_LSUPER`, `KEY_RSUPER`
    - `getKey` now takes in a `KeyEvent` instead of the key and scancode `int`s
    - `isKeyDown`, `setupKeyboardCallbacks`, `setupMouseCallbacks`, `grabOrReleaseMouse`, `updateRawMouseInput` now take in a `Window` instead of the `long` handle
- `net.minecraft.client`
    - `KeyboardHandler`
        - `keyPress` is now private
        - `setup` now takes in a `Window` instead of the `long` handle
    - `KeyMapping` now takes in a `$Category` instead of a `String`
        - `shouldSetOnIngameFocus` - Returns whether the key is mapped to some value on the keyboard.
        - `restoreToggleStatesOnScreenClosed` - Restores the toggled keys to its state during in-game actions.
        - `key` is now protected
        - `release` is now protected
        - `CATEGORY_*` -> `$Category#*`
            - Key can be obtained via `$Category#id`
            - Component is available through `$Category#label`
        - `getCategory` now returns a `$Category` instead of a `String`
        - `matches` now takes in a `KeyEvent` instead of the key and scancode `int`s
        - `matchesMouse` now takes in a `MouseButtonEvent` instead of the button `int`
    - `Minecraft#ON_OSX` -> `InputQuirks#ON_OSX`
    - `MouseHandler#setup` now takes in a `Window` instead of the `long` handle
    - `ToggleKeyMapping` now has an overload that takes in an input type
        - The constructor now takes in a `KeyMapping$Category` instead of a `String`, and a `boolean` that represents if the previous state of the key binding should be restored
- `net.minecraft.client.gui.components`
    - `AbstractButton#onPress` now takes in an `InputWithModifiers`
    - `AbstractScrollArea#updateScrolling` now takes in a `MouseButtonEvent` instead of the button info and XY positions
    - `AbstractWidget`
        - `onClick` now takes in a `MouseButtonEvent` instead of the XY positions and whether the button was double-clicked
        - `onRelease` now takes in a `MouseButtonEvent` instead of the XY positions
        - `onDrag` now takes in a `MouseButtonEvent` instead of the XY positions
        - `isValidClickButton` now takes in the `MouseButtonInfo` instead of the button `int`
    - `CommandSuggestions`
        - `keyPressed` now takes in a `KeyEvent` instead of the key, scancode, modifiers `int`
        - `mouseClicked` now takes in a `MouseButtonEvent` instead of the button info and XY positions
        - `$SuggestionsList`
            - `mouseClicked` no longer takes in the button `int`
            - `keyPressed` now takes in a `KeyEvent` instead of the key, scancode, modifiers `int`
    - `MultilineTextField#keyPressed` now takes in a `KeyEvent` instead of the key `int`
- `net.minecraft.client.gui.components.events.GuiEventListener`
    - `DOUBLE_CLICK_THRESHOLD_MS` -> `MouseHandler#DOUBLE_CLICK_THRESHOLD_MS`
    - `mouseClicked` now takes in a `MouseButtonEvent` instead of the button info and XY positions, and whether the button was double-clicked
    - `mouseReleased` now takes in a `MouseButtonEvent` instead of the button info and XY positions
    - `mouseDragged` now takes in a `MouseButtonEvent` instead of the button info and XY positions
    - `keyPressed` now takes in a `KeyEvent` instead of the key, scancode, modifiers `int`
    - `keyReleased` now takes in a `KeyEvent` instead of the key, scancode, modifiers `int`
    - `charTyped` now takes in a `CharacterEvent` instead of the codepoint `char` and modifiers `int`
- `net.minecraft.client.gui.font.TextFieldHelper`
    - `charTyped` now takes in a `CharacterEvent` instead of the codepoint `char`
    - `keyPressed` now takes in a `KeyEvent` instead of the key `int`
- `net.minecraft.client.gui.navigation.CommonInputs` class is removed
    - `selected` -> `InputWithModifiers#isSelection`
    - `confirm` -> `InputWithModifiers#isConfirmation`
- `net.minecraft.client.gui.screens.Screen`
    - `hasControlDown` -> `Minecraft#hasControlDown`, `InputWithModifiers#hasControlDown`
    - `hasShiftDown` -> `Minecraft#hasShiftDown`, `InputWithModifiers#hasShiftDown`
    - `hasAltDown` -> `Minecraft#hasAltDown`, `InputWithModifiers#hasAltDown`
    - `isCut` -> `InputWithModifiers#isCut`
    - `isPaste` -> `InputWithModifiers#isPaste`
    - `isCopy` -> `InputWithModifiers#isCopy`
    - `isSelectAll` -> `InputWithModifiers#isSelectAll`
- `net.minecraft.client.gui.screens.inventory.AbstractContainerScreen`
    - `hasClickedOutside` no longer takes in the button `int`
    - `checkHotbarKeyPressed` now takes in a `KeyEvent` instead of the key and modifiers `int` 
- `net.minecraft.client.gui.screens.options.controls.KeyBindsList$CategoryEntry` now takes in a `KeyMapping$Category` instead of the `Component`
- `net.minecraft.client.gui.screens.recipebook`
    - `hasClickedOutside` no longer takes in the button `int`
    - `RecipeBookPage#mouseClicked` now takes in a `MouseButtonEvent` instead of the button info and XY positions, and whether the button was double-clicked
- `net.minecraft.client.input`
    - `CharacterEvent` - Some kind of input interaction generates a codepoint with the currently active modifiers.
    - `InputQuirks` - A utility for quirks between operating systems when handling inputs.
    - `InputWithModifiers` - Defines an input with the currently active modifiers, along with some common input checks.
    - `KeyEvent` - Some kind of input interaction generates a key by the scancode with the currently active modifiers.
    - `MouseButtonEvent` - Some kind of input interaction generates a button at the XY position.
    - `MouseButtonInfo` - Some kind of input interaction generates a button with the currently active modifiers.

## `Level#isClientSide` now private

The `Level#isClientSide` field is now private, so all queries must be made to the method version:

```java
// For some Level level
level.isClientSide();
```

- `net.minecraft.world.level.Level#isClientSide` field is now private

## Minor Migrations

The following is a list of useful or interesting additions, changes, and removals that do not deserve their own section in the primer.

### Item Owner

Minecraft has further abstracted the holder of an item in item models to its own interface: `ItemOwner`. An `ItemOwner` is defined by three things: the `level` the owner is in, the `position` of the owner, and the Y rotation (in degrees) of the owner to indicate the facing direction. `ItemOwner`s are implemented on every `Entity`; however, their position can be offset by using `ItemOwner#offsetFromOwner` or creating a `ItemOwner$OffsetFromOwner`.

Currently, only the new shelf block uses the offset owner so that compasses are not randomly spinning when placed.

- `net.minecraft.client.renderer.entity.ItemRenderer#renderStatic` now takes in an optional `ItemOwner` instead of a `LivingEntity`
- `net.minecraft.client.renderer.item`
    - `ItemModel#update` now takes in an `ItemOwner` instead of a `LivingEntity`
    - `ItemModelResolver#updateForTopItem`, `appendItemLayers` now takes in an `ItemOwner` instead of a `LivingEntity`
- `net.minecraft.client.renderer.item.properties.numeric`
    - `NeedleDirectionHelper#get`, `calculate` now takes in an `ItemOwner` instead of a `LivingEntity`
    - `RangeSelectItemModelProperty#get` now takes in an `ItemOwner` instead of a `LivingEntity`
    - `Time$TimeSource#get` now takes in an `ItemOwner` instead of a `Entity`
- `net.minecraft.world.entity`
    - `Entity` now implements `ItemOwner`
    - `ItemOwner` - Represents the owner of the item being rendered.

### Container User

As this new version of Minecraft introduces the copper golem (a vanilla mob that can interact with inventories), the concept of an entity that can use a container has been abstracted into its own interface: `ContainerUser`. `ContainerUser`s are expected to be implemented on some `LivingEntity` subclass. Since the actual inventory logic is handled elsewhere, `ContainerUser` only has two methods: `hasContainerOpen`, which checks whether the living entity is currently opening some container (e.g., barrel, chest); and `getContainerInteractionRange`, which determines the radius in blocks that the entity can interact with some container.

- `net.minecraft.world.Container`
    - `startOpen`, `stopOpen` now take in a `ContainerUser` instead of a `Player`
    - `getEntitiesWithContainerOpen` - Returns the list of `ContainerUser`s that are interacting with this container.
- `net.minecraft.world.entity.ContainerUser` - Typically an entity that can interact with and open a container.
- `net.minecraft.world.entity.player.Player` now implements `ContainerUser`
- `net.minecraft.world.level.block.entity.EnderChestBlockEntity#startOpen`, `stopOpen` now take in a `ContainerUser` instead of a `Player`

### Name And Id

Unless a `GameProfile` is needed, Minecraft now passes around a `NameAndId` for every player instead. As the name implies, `NameAndId` contains the UUID and name of the player, as that is usually all that's necessary for most logic involving a player entity.

- `net.minecraft.commands.arguments.GameProfileArgument`
    - `getGameProfiles` now return a collections of `NameAndId`s
    - `$Result#getNames` now return a collections of `NameAndId`s
- `net.minecraft.network.codec.ByteBufCodecs#PLAYER_NAME` - A 16 byte string stream codec of the player name.
- `net.minecraft.network.protocol.status.ServerStatus$Players#Sample` now is a list of `NameAndId`s rather than `GameProfile`s
- `net.minecraft.server`
    - `MinecraftServer`
        - `getProfilePermissions` now takes in a `NameAndId` instead of a `GameProfile`
        - `isSingleplayerOwner` now takes in a `NameAndId` instead of a `GameProfile`
        - `ANONYMOUS_PLAYER_PROFILE` is now a `NameAndId`
    - `Services` now takes in a `UserNameToIdResolver` instead of a `GameProfileCache`, and a `ProfileResolver`
- `net.minecraft.server.players`
    - `GameProfileCache` -> `UserNameToIdResolver`, `CachedUserNameToIdResolver`; not one-to-one
        - `getAsync` is removed
        - `add(GameProfile)` is removed
    - `NameAndId` - An object that holds the UUID and name of a profile.
    - `PlayerList`
        - `load` -> `loadPlayerData`, not one-to-one
        - `canPlayerLogin` now takes in a `NameAndId` instead of a `GameProfile`
        - `disconnectAllPlayersWithProfile` now takes in a `UUID` instead of a `GameProfile`
        - `op`, `deop` now take in a `NameAndId` instead of a `GameProfile`
        - `isWhiteListed`, `isOp` now take in a `NameAndId` instead of a `GameProfile`
        - `canBypassPlayerLimit` now takes in a `NameAndId` instead of a `GameProfile`
    - `ServerOpList` now takes in `NameAndId` as its first generic
        - `canBypassPlayerLimit` now takes in a `NameAndId` instead of a `GameProfile`
    - `ServerOpListEntry` now takes in `NameAndId` as its generic and for the constructor
    - `UserBanList` now takes in `NameAndId` as its first generic
        - `isBanned` now takes in a `NameAndId` instead of a `GameProfile`
    - `UserBanListEntry` now takes in `NameAndId` as its generic and for the constructor
    - `UserWhiteList` now takes in `NameAndId` as its first generic
        - `isWhiteListed` now takes in a `NameAndId` instead of a `GameProfile`
    - `UserWhiteListEntry` now takes in `NameAndId` as its generic and for the constructor
- `net.minecraft.world.entity.player.Player#nameAndId` - Returns the name and id of the player.
- `net.minecraft.world.level.storage.PlayerDataStorage#load(Player, ProblemReporter)` -> `load(NameAndId)`, returns an `Optional<CompoundTag>`

### Typed Entity Data

Data components for storing block and entity data have been changed from using `CustomData` to the type safe variant `TypedEntityData`. `TypedEntityData` is pretty much the same as `CustomData`, storing a `CompoundTag`, except that it is represented by some `IdType` generic like `EntityType` or `BlockEntityType`. All of the methods within `CustomData` relating to block entities and entities have moved to `TypedEntityData`.

Spawn eggs use the `TypedEntityData` to store the `EntityType` that it will spawn. The logic of spawning is still tied to the `SpawnEggItem` subclass.

- `net.minecraft.core.component.DataComponents#ENTITY_DATA`, `BLOCK_ENTITY_DATA` are now `TypeEntityData` with `EntityType` and `BlockEntityType` ids, respectively
- `net.minecraft.world.entity.EntityType#updateCustomEntityTag` now takes in a `TypedEntityData` instead of `CustomData`
- `net.minecraft.world.item.Item$Properties#spawnEgg` - Adds the `ENTITY_DATA` component with the entity type.
- `net.minecraft.world.item.component`
    - `CustomData`
        - `CODEC_WITH_ID` is removed
        - `COMPOUND_TAG_CODEC` - A codec for a flattened compound tag.
        - `parseEntityId`, `parseEntityType` are removed
        - `loadInto` -> `TypedEntityData#loadInto`
        - `update`, `read`, `size` are removed
        - `contains` -> `TypedEntityData#contains`
        - `getUnsafe` -> `TypedEntityData#getUnsafe`
    - `TypedEntityData` - A custom data that provides a type-safe identifier.
- `net.minecraft.world.level.Spawner#appendHoverText`, `getSpawnEntityDisplayName` now take in a `TypedEntityData` instead of a `CustomData`
- `net.minecraft.world.level.block.entity.BeehiveBlockEntity$Occupant#entityData` is now a `TypedEntityData`

### Reload Listener Shared State

`PreparableReloadListener` now have an option to share their data between other reload listeners through the use of the `PreparableReloadListener#prepareSharedState`. `prepareSharedState` is ran on the main thread before `reload` is called, which can store arbitrary values to some `$SharedKey` as basically a dictionary lookup. Then, once `reload` is called, the stored data can be obtained and used via the key. Normally, the shared values used are some kind of `CompletableFuture` and joined within.

```java
public class FirstExampleListener implements PreparableReloadListener {

    // Create the key to write the shared data to
    public static final PreparableReloadListener.StateKey<CompletableFuture<Void>> EXAMPLE = new PreparableReloadListener.StateKey<>();

    @Override
    public void prepareSharedState(PreparableReloadListener.SharedState state) {
        // Add the data to the shared state
        state.set(EXAMPLE, CompletableFuture.allOf());
    }

    @Override
    public CompletableFuture<Void> reload(PreparableReloadListener.SharedState state, Executor tasksExecutor, PreparableReloadListener.PreparationBarrier barrier, Executor reloadExecutor) {
        // Use the shared state
        var example = state.get(EXAMPLE);
    }
}


public class SecondExampleListener implements PreparableReloadListener {

    @Override
    public CompletableFuture<Void> reload(PreparableReloadListener.SharedState state, Executor tasksExecutor, PreparableReloadListener.PreparationBarrier barrier, Executor reloadExecutor) {
        // Use the shared state in a different listener
        var example = state.get(FirstExampleListener.EXAMPLE);
    }
}
```

- `net.minecraft.server.packs.resources`
    - `PreparableReloadListener`
        - `reload` now takes in a `PreparableReloadListener$SharedState` instead of a `ResourceManager`
        - `prepareSharedState` - A method called before the reload listener is reloaded to share data between reloading listeners.
        - `$SharedState` - A general dictionary that can provide resources between reload listeners, though they should typically be in the form of `CompletableFuture`s.
        - `$StateKey` - A key into the shared state.
    - `SimpleReloadInstance$StateFactory#create` now takes in a `PreparableReloadListener$SharedState` instead of a `ResourceManager`

### Ticket Flags

`TicketType` has been updated to take a bit flag representing the flag's properties rather than a boolean and enum.

```java
// This needs to be registered to BuiltInRegistries#TICKET_TYPE
public static final TicketType EXAMPLE = new TicketType(
    20L, // The number of ticks before the ticket is timed out and removed, set to 0 if the ticket should never time out
    // The bit flags to set, see below for replacements and explanations of the new flags
    TicketType.FLAG_SIMULATION | TicketType.FLAG_LOADING
);
```

- `net.minecraft.server.level.TicketType` now takes in a bitmask of flags rather than a boolean and ticket use
    - `FLAG_PERSIST`, `persist` replace the boolean
    - `FLAG_LOADING` replaces `$TicketUse#LOADING`
    - `FLAG_SIMULATION` replaces `$TicketUse#SIMULATION`
    - `FLAG_KEEP_DIMENSION_ACTIVE`, `shouldKeepDimensionActive` - When set, prevents the level from unloading.
    - `FLAG_CAN_EXPIRE_IF_UNLOADED`, `canExpireIfUnloaded` - When set, the ticket expires if the chunk is unloaded.
    - `START` -> `PLAYER_SPAWN`, `SPAWN_SEARCH`
    - `$TicketUse` class is removed
- `net.minecraft.world.level.TicketStorage`
    - `shouldKeepDimensionActive` - Checks if the dimension active flag is set on any tickets in the storage.
    - `removeTicketIf` now takes a `$TicketPredicate` instead of a `BiPredicate`
    - `$TicketPredicate` - Tests the ticket at a given chunk position.

### Respawn Data

The respawn logic has been consolidated into a single object known as `LevelData$RespawnData`. The respawn data holds the global position (a level key and block position) and the rotation of the entity. All calls to the original angle and position methods have been replaced by `Level#setRespawnData` or `getRespawnData`, which delegates to the `MinecraftServer` and eventually the level data.

- `net.minecraft.client.multiplayer.ClientLevel#setDefaultSpawnPos` -> `Level#setRespawnData`
- `net.minecraft.network.protocol.game.ClientboundSetDefaultSpawnPositionPacket` is now a record
    - The parameters have been replaced with a `LevelData$RespawnData` object
- `net.minecraft.server.MinecraftServer`
    - `findRespawnDimension` - Gets the default `ServerLevel` that the player should respawn in.
    - `setRespawnData` - Sets the default spawn position.
    - `getRespawnData` - Gets the default spawn position.
- `net.minecraft.server.level`
    - `ServerLevel#setDefaultSpawnPos` -> `Level#setRespawnData`
    - `ServerPlayer$RespawnConfig` now takes in a `LevelData$RespawnData` instead of the dimension, position, and angle
        - Fields are still accessible from the respawn data
- `net.minecraft.world.level.Level#getSharedSpawnPos`, `getSharedSpawnAngle` -> `getRespawnData`, `getWorldBorderAdjustedRespawnData`, not one-to-one
- `net.minecraft.world.level.storage`
    - `LevelData`
        - `getSpawnPos`, `getSpawnAngle` -> `getRespawnData`, not one-to-one
        - `$RespawnData` - Defines the global position, yaw, and pitch of where to respawn the players by default.
    - `WritableLevelData#setSpawn` now only takes in the `LevelData$RespawnData`

### The 'On Shelf' Transform

Models now have a new transform for determining how an item sits on a shelf called `on_shelf`.

- `net.minecraft.client.renderer.block.model.ItemTransforms#fixedFromBottom` - A transform that expects the item to be sitting on some fixed bottom, like a shelf.
- `net.minecraft.world.item.ItemDisplayContext#ON_SHELF` - When an item is placed on a shelf.

### Client Asset Split

`ClientAsset` has been reworked to better specify what the asset is referencing. Currently, vanilla adds `ClientAsset$Texture` to specify that the asset is a texture, which further subclasses to `$DownloadedTexture` for textures downloaded from the internet, and `$ResourceTexture` for textures accessible from some existing resource on the disk.

- `net.minecraft.advancements.DisplayInfo` now takes in an optional `ClientAsset$ResourceTexture` for the background instead of a `ClientAsset`
- `net.miencraft.client.renderer.texture.SkinTextureDownloader#downloadAndRegisterSkin` now returns a future for the `ClientAsset$Texture` instead of a `ResourceLocation`
- `net.minecraft.client.resources`
    - `PlayerSkin` -> `net.minecraft.world.entity.player.PlayerSkin`
        - The constructor now takes in `ClientAsset$Texture`s instead of `ResourceLocation` and `String`s
        - `texture`, `textureUrl` -> `body`
        - `capeTexture` -> `cape`
        - `elytraTexture` -> `elytra`
        - `insecure` - Constructs a `PlayerSkin` with `insecure` set to false.
        - `with` - Builds a `PlayerSkin` from its `$Patch`.
        - `$Patch` - A packed object representing the skin in other files or when sending across the network.
    - `SkinManager$TextureCache#getOrLoad` now returns a future for the `ClientAsset$Texture` instead of a `ResourceLocation`
- `net.minecraft.core.ClientAsset` is now an interface instead of a record
    - Original implementation moved to `$ResourceTexture`
    - `id` still exists as a method
    - `texturePath` -> `$Texture#texturePath`
    - `CODEC`, `DEFAULT_FIELD_CODEC`, `STREAM_CODEC` -> `$ResourceTexture#*`
    - `$DownloadedTexture` - A texture downloaded from the internet.
    - `$Texture` - A client asset which defines a texture.
- `net.minecraft.world.entity.animal.CatVariant#assetInfo` now takes in a `ClientAsset$ResourceTexture` instead of a `ClientAsset`
- `net.minecraft.world.entity.animal.frog.FrogVariant#assetInfo` now takes in a `ClientAsset$ResourceTexture` instead of a `ClientAsset`
- `net.minecraft.world.entity.animal.wolf.WolfVariant$AssetInfo#*` now take in a `ClientAsset$ResourceTexture` instead of a `ClientAsset`
- `net.minecraft.world.entity.variant.ModelAndTexture#asset` now takes in a `ClientAsset$ResourceTexture` instead of a `ClientAsset`

### Cursor Types

The current cursor on screen can now change to a native `CursorType`, via `GLFW#glfwCreateStandardCursor` in a GUI via `GuiGraphics#requestCursor`, or `Window#selectCursor` anywhere else. Note that `Window#setAllowCursorChanges` must be true, which can be set through the options menu.

- `com.mojang.blaze3d.platform.Window`
    - `setAllowCursorChanges` - Sets whether the cursor can be changed to a different standard shape.
    - `selectCursor` - Sets the current cursor to the specified type.
- `com.mojang.blaze3d.platform.cursor`
    - `CursorType` - A definition of a GLFW standard cursor shape.
        - `createStandardCursor` - Creates a standard cursor from its shape, name, and fallback.
    - `CursorTypes` - Blaze3d's cursor types.
- `net.minecraft.client.Options#allowCursorChanges` - Sets whether the cursor can be changed to a different standard shape.
- `net.minecraft.client.gui.GuiGraphics`
    - `requestCursor` - Requests the cursor to submit to be rendered.
    - `applyCursor` - Applies the cursor change for rendering.

### New Tags

- `minecraft:block`
    - `wooden_shelves`
    - `copper_chests`
    - `lightning_rods`
    - `copper`
    - `copper_golem_statues`
    - `incorrect_for_copper_tool`
    - `chains`
    - `lanterns`
    - `bars`
- `minecraft:entity_types`
    - `cannot_be_pushed_onto_boats`
    - `accepts_iron_golem_gift`
    - `candidate_for_iron_golem_gift`
- `minecraft:item`
    - `wooden_shelves`
    - `copper_chests`
    - `lightning_rods`
    - `copper`
    - `copper_golem_statues`
    - `copper_tool_materials`
    - `repairs_copper_armor`
    - `chains`
    - `lanterns`
    - `bars`
    - `shearable_from_copper_golem`

### List of Additions

- `com.mojang.blaze3d.GraphicsWorkarounds#isGlOnDx12` - Returns whether the renderer is using DirectX 12.
- `com.mojang.blaze3d.opengl.GlStateManager#incrementTrackedBuffers` - Increments the number of buffers used by the game.
- `com.mojang.blaze3d.systems.TimerQuery#isRecording` - Returns whether there is a query that's currently active. 
- `net.minecraft.SharedConstants#RESOURCE_PACK_FORMAT_MINOR`, `DATA_PACK_FORMAT_MINOR` - The minor component of the pack version.
- `net.minecraft.advancements.critereon.MinMaxBounds`
    - `bounds` - Returns the bounds of the value.
    - `$FloatDegrees` - A bounds for a float representing the degree of some angle.
- `net.minecraft.client`
    - `Minecraft`
        - `isOfflineDevelopedMode` - Returns whether the game is in offline developer mode.
        - `canSwitchGameMode` - Whether the player entity exists and has a game mode.
        - `playerSkinRenderCache` - Returns the cache holding the player's render texture.
        - `canInterruptScreen` - Whether the current screen can be closed by another screen.
        - `packetProcessor` - Returns the processor that schedules and handles packets.
    - `Options`
        - `invertMouseX` - When true, inverts the X mouse movement.
        - `toggleAttack` - When true, sets the attack key to be toggleable.
        - `toggleUse` - When true, sets the use key to be toggleable.
        - `sprintWindow` - Time window in ticks where double-tapping the forward key activates sprint.
        - `saveChatDrafts` - Returns whether the message being typed in chat should be retained if the box is closed.
        - `keySpectatorHotbar` - A key that pulls up the spectator hotbar menu.
    - `ToggleKeyMapping#shouldRestoreStateOnScreenClosed` - Whether the previous toggle state should be restored after screen close.
- `net.minecraft.client.animation.definitions.CopperGolemAnimation` - The animations for the copper golem.
- `net.minecraft.client.data.models.BlockModelGenerators`
    - `and` - ANDs the given conditions together.
    - `createShelf` - Creates a shelf block state from a base and a particle texture.
    - `addShelfPart` - Adds a variant for a model for all the shelf's states.
    - `forEachHorizontalDirection` - Performs an operation for a pair of directions and rotations.
    - `shelfCondition` - Constructs a condition based on the state of the shelf.
    - `createCopperGolemStatues` - Creates all copper golem statues.
    - `createCopperGolemStatue` - Creates a copper golem statue for the provided block, copper block, and weathered state.
    - `createCopperChests` - Creates all copper chests.
    - `createCopperLantern` - Creates a lantern and hanging lantern.
    - `createCopperChain` - Creates a chain.
    - `createCopperChainItem` - Creates a chain item model.
- `net.minecraft.client.data.models.model`
    - `ModelTemplate`
        - `SHELF_*` - Model templates for constructing a shelf into a multipart.
        - `LIGHTNING_ROD` - Model template for a lightning rod.
        - `CHAIN` - Model template for a chain.
        - `BARS_*` - Model templates for constructing a bars-like block.
    - `TexturedModel#CHAIN` - A model provider for a chain.
    - `TextureMapping#bars` - A texture mapping for a bars-like block.
    - `TextureSlot#BARS` - A reference to the `#bars` texture.
- `net.minecraft.client.gui`
    - `Gui#renderDeferredSubtitles` - Renders the subtitles on screen.
    - `GuiGraphics#getSprite` - Gets a `TextureAtlasSprite` from its material.
- `net.minecraft.client.gui.components`
    - `AbstractScrollArea#isOverScrollbar` - Returns whether the current cursor position is over the scroll bar.
    - `AbstractSelectionList`
        - `sort` - Sorts the entries within the list.
        - `swap` - Swaps the position of two entries in the list
        - `clearEntriesExcept` - Removes all entries that don't match the provided element.
        - `getNextY` - Returns the Y component to render the scrolled component.
        - `entriesCanBeSelected` - Whether the entries in the list can be selected.
        - `scrollToEntry` - Scrolls the bar such that the selected entry is on screen.
        - `removeEntries` - Removes all entries that are in the provided list.
        - `$Entry`
            - `set*` - Sets the component size.
            - `getContent*` - Get the content size and positions.
    - `ChatComponent`
        - `saveAsDraft`, `discardDraft` - Handles the draft message in the chat box.
        - `createScreen`, `openScreen`, `preserveCurrentChatScreen`, `restoreChatScreen` - Handles screen behavior depending on the draft chat option.
        - `$ChatMethod` - Defines what type of message is the chat.
        - `$Draft` - Defines a draft message in the chat box.
    - `EditBox`
        - `DEFAULT_HINT_STYLE` - The style of the default hints.
        - `SEARCH_HINT_STYLE` - The style of the searchable hints, such as in a recipe book.
        - `$TextFormatter` - An interface used to format the string in the box.
    - `FittingMultiLineTextWidget#minimizeHeight` - Sets the height of the widget to the inner height and padding.
    - `FocusableTextWidget$BackgroundFill` - An enum that represents when the background should be filled.
    - `ItemDisplayWidget#renderTooltip` - Submits the tooltip to render.
    - `MultiLineLabel$Align` - Handles the alignment of the label.
    - `MultilineTextField#selectWordAtCursor` - Selects the word whether the cursor is currently located.
    - `SpriteIconButton$Builder#withTooltip` - Sets the tooltip to display the message.
    - `StringWidget`
        - `setMaxWidth` - Sets the max width of the string and how to handle the extra width.
        - `$TextOverflow` - What to do if the text is longer than the max width.
- `net.minecraft.client.gui.components.events.GuiEventListener#shouldTakeFocusAfterInteraction` - When true, sets the element as focused when clicked.
- `net.minecraft.client.gui.screens`
    - `ChatScreen`
        - `isDraft` - Whether there is a message that hasn't been sent in the box.
        - `exitReason` - The reason the chat screen was closed.
        - `shouldDiscardDraft` - Whether the current draft message should be discarded.
        - `$ChatConstructor` - A constructor for the chat screen based on the draft state.
        - `$ExitReason` - The reason the chat screen was closed.
    - `FaviconTexture#isClosed` - Returns whether the texture has been closed.
    - `LevelLoadingScreen`
        - `update` - Updates the current load tracker and reason.
        - `$Reason` - The reason for changing the level loading screen.
    - `Overlay#tick` - Ticks the overlay.
    - `Screen`
        - `panoramaShouldSpin` - Returns whether the rendered panorama should spin its camera.
        - `setNarrationSuppressTime` - Sets until when the narration should be suppressed for.
        - `isInGameUi` - Returns whether the screen is opened while playing the game, not the pause menu.
        - `isAllowedInPortal` - Whether this screen can be open during the portal transition effect.
        - `canInterruptWithAnotherScreen` - Whether this screen can be closed by another screen, defaults to 'close on escape' screens.
- `net.minecraft.client.gui.screens.inventory.AbstractCommandBlockScreen#addExtraControls` - Adds any extra widgets to the command block screen.
- `net.minecraft.client.gui.screens.multiplayer`
    - `CodeOfConductScreen` - A screens that displays the code of conduct text for a server.
    - `ServerSelectionList$Entry`
        - `matches` - Returns whether the provided entry matches this one.
        - `join` - Handles how the client joins to the server entry.
- `net.minecraft.client.gui.screens.reporting.ChatSelectionScreen$ChatSelectionList#ITEM_HEIGHT` - The height of each entry in the list.
- `net.minecraft.client.gui.screens.worldselection.WorldSelectionList`
    - `returnToScreen` - Returns to the previous screen after reloading the world list.
    - `$Builder` - Creates a new `WorldSelectionList`.
    - `$Entry#getLevelSummary` - Returns the summary of the selected world.
    - `$EntryType` - A enum representing what type of world the entry is.
    - `$NoWorldsEntry` - An entry where there are no worlds to select from.
- `net.minecraft.client.main.GameConfig$GameData#offlineDeveloperMode` - Whether the game is offline and is ran for development.
- `net.minecraft.client.multiplayer`
    - `ClientCommonPacketListenerImpl`
        - `seenPlayers` - All players that have been seen, regardless of if they are currently online, by this player.
        - `seenInsecureChatWarning` - If the player has seen the insecure chat warning.
        - `$CommonDialogAccess` - A dialog access used by the client network.
    - `ClientConfigurationPacketListenerImpl#DISCONNECTED_MESSAGE` - The message to display on disconnect because of not accepting the code of conduct.
    - `ClientExplosionTracker` - Tracks the explosions made on the client, specifically to handle particles.
    - `ClientLevel`
        - `endFlashState` - Handles the state of the flashes of light that appear in the end.
        - `trackExplosionEffects` - Tracks an explosion to handle its particles.
    - `ClientPacketListener`
        - `getSeenPlayers` - Returns all players seen by this player.
        - `getPlayerInfoIgnoreCase` - Gets the player info for the provided profile name.
- `net.minecraft.client.player.LocalPlayerResolver` - A profile resolver for the local player.
- `net.minecraft.client.resources.WaypointStyle#ICON_LOCATION_PREFIX` - The prefix for waypoint icons.
- `net.minecraft.client.server.IntegratedServer#MAX_PLAYERS` - The maximum number of players allowed in an locally hosted server.
- `net.minecraft.client.sounds`
    - `DirectionalSoundInstance` - A sound that changes position based on the direction of the camera.
    - `SoundEngineExecutor#startUp` - Creates the thread to run the engine on.
    - `SoundPreviewHandler` - A utility for previewing how some event would sound like with the given settings.
- `net.minecraft.core`
    - `BlockPos#betweenCornersInDirection` - An iterable that iterates through the provided bounds in the direction provided by the vector.
    - `Direction#axisStepOrder` - Returns a list of directions that the given vector should be checked in.
- `net.minecraft.core.particles.ExplosionParticleInfo` - The particle that used as part of an explosion.
- `net.minecraft.data.loot.BlockLootSubProvider#createCopperGolemStatueBlock` - The loot table for a copper golem statue.
- `net.minecraft.data.loot.packs`
    - `VanillaBlockInteractLoot` - A sub provider for vanilla block loot from entity interaction.
    - `VanillaChargedCreeperExplosionLoot` - A sub provider for vanilla entity loot from charged creeper explosions.
    - `VanillaEntityInteractLoot` - A sub provider for vanilla entity loot from entity interaction.
- `net.minecraft.data.recipes.RecipeProvider#shelf` - Constructs a shelf recipe from one item.
- `net.minecraft.data.worldgen.BiomeDefaultFeatures#addNearWaterVegetation` - Adds the vegetation when near a body of water.
- `net.minecraft.gametest.framework.GameTestHelper#getTestDirection` - Gets the direction that the test is facing.
- `net.minecraft.network`
    - `FriendlyByteBuf#readLpVec3`, `writeLpVec3` - Handles syncing a compressed `Vec3`.
    - `LpVec3` - A vec3 network handler that compresses and decompresses a vector into at most two bytes and two integers.
    - `PacketProcessor` - A processor for packets to be scheduled and handled.
- `net.minecraft.network.chat.CommonComponents#GUI_COPY_TO_CLIPBOARD` - The message to display when copying a chat to the clipboard.
- `net.minecraft.network.protocol.configuration`
    - `ClientboundCodeOfConductPacket` - A packet the sends the code of conduct of a server to the joining client.
    - `ClientConfigurationPacketListener#handleCodeOfConduct` - Handles the code of conduct sent from the server.
    - `ServerboundAcceptCodeOfConductPacket` - A packet that sends the acceptance response of a client reading the code of conduct from a server.
    - `ServerConfigurationPacketListener#handleAcceptCodeOfConduct` - Handles the acceptance of the code of conduct from the client.
- `net.minecraft.network.syncher.EntityDataSerializers`
    - `WEATHERING_COPPER_STATE` - The weather state of the copper on the golem.
    - `COPPER_GOLEM_STATE` - The logic state of the copper golem.
    - `RESOLVABLE_PROFILE` - The resovlable profile of an entity.
- `net.minecraft.server.MinecraftServer`
    - `selectLevelLoadFocusPos` - Returns the loading center position of the server, usually the shared spawn position.
    - `getLevelLoadListener` - Returns the listener used to track level loading.
    - `getCodeOfConducts` - Returns a map of file names to their code of conduct text.
    - `enforceGameTypeForPlayers` - Sets the game type of all players.
    - `packetProcessor` - Returns the packet processor for the server.
- `net.minecraft.server.commands.FetchProfileCommand` - Fetches the profile of a given user.
- `net.minecraft.server.dedicated.DedicatedServerProperties#codeOfConduct` - Whether the server has a code of conduct.
- `net.minecraft.server.level`
    - `ChunkLoadCounter` - Keeps track of chunk loading when a level is loading or the player spawns.
    - `ChunkMap`
        - `getLatestStatus` - Returns the latest status for the given chunk position.
        - `isTrackedByAnyPlayer` - Checks whether the entity is tracked by any player.
        - `forEachEntityTrackedBy` - Loops through each entity tracked by the given player.
        - `forEachReadyToSendChunk` - Loops through each chunk that is ready to be sent to the client.
    - `ServerChunkCache`
        - `hasActiveTickets` - Checks whether the current level has any active tickets keeping it loaded.
        - `addTicketAndLoadWithRadius` - Adds a ticket to some location and loads the chunk and radius around that location.
    - `ServerEntity$Synchronizer` - Handles sending packets to tracking entities.
    - `ServerLevel#isSpawningMonsters` - Returns whether the server can spawn monsters.
    - `ServerPlayer$SavedPosition` - Holds the current position of the player on disk.
- `net.minecraft.server.level.progress.ChunkLoadStatusView` - A status view for the loading chunks.
- `net.minecraft.server.network.ConfigurationTask#tick` - Calls the task every tick until it returns true, then finishes the task.
- `net.minecraft.server.network.config`
    - `PrepareSpawnTask` - A configuration task that finds the spawn of the player.
    - `ServerCodeOfConductConfigurationTask` - A configuration task that sends the code of conduct to the client.
- `net.minecraft.server.packs.OverlayMetadataSection`
    - `codecForPackType` - Constructs a codec for the section given the pack type.
    - `forPackType` - Gets the metadata section type from the pack type.
- `net.minecraft.server.packs.metadata.MetadataSectionType#withValue`, `$WithValue` - Holds a metadata section along with its provided value.
- `net.minecraft.server.packs.metadata.pack`
    - `PackFormat` - Represents the major and minor revision of a pack.
    - `PackMetadataSection#forPackType` - Gets the metadata section type from the pack type.
- `net.minecraft.server.packs.repository.PackCompatibility#UNKNOWN` - The compatibility of the pack with the game is unknown as the major version is set to the max integer value.
- `net.minecraft.server.packs.resources.ResourceMetadata#getTypedSection`, `getTypedSections` - Gets the metadata sections for the provided types.
- `net.minecraft.server.players.ProfileResolver` - Resolves a game profile from its name or UUID.
- `net.minecraft.util`
    - `ExtraCodecs`
        - `gameProfileCodec` - Creates a codec for a `GameProfile` given the UUID codec.
        - `$LateBoundIdMapper#values` - Returns a set of values within the mapper.
    - `InclusiveRange#map` - Maps a range from one generic to another.
    - `Mth#ceilLong` - Returns the double rounded up to the nearest long.
- `net.minecraft.util.random`
    - `Weighted#streamCodec` - Constructs a stream codec for the weighted entry.
    - `WeightedList#streamCodec` - Constructs a stream codec for the weighted list.
- `net.minecraft.util.thread.BlockableEventLoop#shouldRunAllTasks` - Returns if there are any blocked tasks.
- `net.minecraft.world.Nameable#getPlainTextName` - Returns the string of the name component.
- `net.minecraft.world.entity`
    - `Avatar` - An entity that makes up the base of a player.
    - `EntityReference`
        - `getEntity` - Returns the entity given the type class and the level.
        - Static `getEntity`, `getLivingEntity`, `getPlayer` - Returns the entity given its `EntityReference` and level.
    - `EntityType`
        - `MANNEQUIN` - The mannequin entity type.
        - `STREAM_CODEC` - The stream codec for an entity type.
        - `$Builder#notInPeaceful` - Sets the entity to not spawn in peaceful mode.
    - `Entity`
        - `canInteractWithLevel` - Whether the entity can interact with the current level.
        - `isInShallowWater` - Returns whether the player is in water but not underwater.
        - `getAvailableSpaceBelow` - Returns the amount of space in the Y direction between the entity and the collider, offset by the given value.
        - `collectAllColliders` - Returns all entity collisions.
    - `InsideBlockEffectType#CLEAR_FREEZE` - A in-block effect that clears the frozen ticks.
    - `LivingEntity`
        - `dropFromEntityInteractLootTable` - Drops loot from a table from an entity interaction.
        - `shouldTakeDrowningDamage` - Whether the entity should take damage from drowning.
    - `Mob#WEARING_ARMOR_UPGRADE_MATERIAL_CHANCE`, `WEARING_ARMOR_UPGRADE_MATERIAL_ATTEMPTS` - Constants for the armor material upgrade.
    - `PositionMoveRotation#withRotation` - Creates a new object with the provided XY rotation.
    - `Relative`
        - `rotation` - Gets the set of relative rotations from the XY booleans.
        - `position` - Gets the set of relative positions from the XYZ booleans.
        - `direction` - Gets the set of relative deltas from the XYZ booleans.
- `net.minecraft.world.entity.ai.Brain#isBrainDead` - Returns whether the brain does not have any memories, snesors, or behaviors.
- `net.minecraft.world.entity.ai.behavior.TransportItemsBetweenContainers` - A behavior where an entity will move items between visited containers.
- `net.minecraft.world.entity.ai.memory.MemoryModuleType`
    - `VISITED_BLOCK_POSITIONS` - Important block positions visited.
    - `TRANSPORT_ITEMS_COOLDOWN_TICKS` - How many ticks to wait before transporting items.
    - `UNREACHABLE_TRANSPORT_BLOCK_POSITIONS` - Holds a list of positions that are impossible to get to.
- `net.minecraft.world.entity.ai.navigation.GroundPathNavigation#setCanPathToTargetsBelowSurface` - Sets whether the entity can target other entities that are underneath a non-air block below the starting position.
- `net.minecraft.world.entity.animal.coppergolem`
    - `CopperGolem` - The copper golem entity.
    - `CopperGolemAi` - The brain logic for the copper golem.
    - `CopperGolemOxidationLevel` - A record holding the source events and textures for a given oxidation level.
    - `CopperGolemOxidationLevels` - All vanilla oxidation levels.
    - `CopperGolemState` - The current logic state the copper golem is in.
- `net.minecraft.world.entity.decoration`
    - `HangingEntity#canCoexist` - Whether any other hanging entities are in the same position as this one. By default, makes sure that the entities are not the same entity type and not facing the same direction.
    - `Mannequin` - An avatar that does not have a connected player.
- `net.minecraft.world.entity.player`
    - `Player`
        - `isMobilityRestricted` - Returns whether the player has the blindness effect.
        - `handleShoulderEntities` - Handles the entities on the player's shoulders.
        - `extractParrotVariant`, `convertParrotVariant`, `*ShoulderParrot*` - Handles the parrot on the player's shoulders.
    - `PlayerModelPart#CODEC` - The codec for the model part.
- `net.minecraft.world.entity.raid.Raids#getRaidCentersInChunk` - Returns the number of raid centers in the given chunk.
- `net.minecraft.world.entity.vehicle.MinecartFurnace#addFuel` - Adds fuel to the furnace to push the entity.
- `net.minecraft.world.item`
    - `BucketItem#getContent` - Returns the fluid held in the bucket.
    - `Item$TooltipContext#isPeaceful` - Returns true if the difficulty is set to peaceful.
    - `ToolMaterial#COPPER` - The copper tool material.
    - `WeatheringCopperItems` - A record of items that represent each of the weathering copper states.
- `net.minecraft.world.item.equipment`
    - `ArmorMaterials#COPPER` - The copper armor material.
    - `EquipmentAssets#COPPER` - The key reference to the copper equipment asset.
    - `ResolvableProfile`
        - `skinPatch` - Returns the player skin reference.
        - `$Static` - Uses the already resolved game profile.
        - `$Dynamic` - Dynamically resolves the game profile on use.
        - `$Partial` - Represents part of the game profile depending on whatever information is provided to the component.
- `net.minecraft.world.level`
    - `BaseCommandBlock$CloseableCommandBlockSource` - A command source typically for a command block.
    - `ChunkPos#contains` - Whether the given block position is in the chunk.
    - `GameRules#RULE_SPAWNER_BLOCKS_ENABLED` - Whether spawner blocks should spawn entities.
    - `Level`
        - `getEntityInAnyDimension` - Gets the entity by UUID.
        - `getPlayerInAnyDimension` - Gets the player by UUID.
        - `hasEntities` - Returns whether the provided bounds has entities matching the type and predicate.
        - `palettedContainerFactory` - Returns the factory used to create paletted containers.
- `net.minecraft.world.level.border.WorldBorder$Settings#toWorldBorder` - Constructs a world border from the settings.
- `net.minecraft.world.level.block`
    - `Block#dropFromBlockInteractLootTable` - Drops the loot when interacting with a block.
    - `ChestBlock`
        - `chestCanConnectTo` - Returns whether the chest can merge with another block.
        - `getConnectedBlockPos` - Gets the connected block position of the chest.
        - `getOpenChestSound`, `getCloseChestSound` - Returns the sounds played on chest open / close.
        - `getChestType` - Returns the type of the chest based on the surrounding chests.
    - `ChiseledBookShelfBlock#FACING`, `SLOT_*_OCCUPIED` - The block state properties of the chiseled bookshelf.
    - `CopperChestBlock` - The block for the copper chest.
    - `CopperGolemStatueBlock` - The block for the copper golem statue.
    - `SelectableSlotContainer` - A container whose slot can be selected based on in-game interaction.
    - `ShelfBlock` - A block for the shelf.
    - `SideChainPartBlock` - A helper for providing how the chain of a shelf should be displayed.
    - `WeatheringCopper$WeatherState`
        - `BY_ID` - The mapper of enum ordinal to state.
        - `STREAM_CODEC` - The stream codec for the state.
        - `next` - Gets the next state in the ordinal.
        - `previous` - Gets the previous state in the ordinal.
    - `WeatheringCopperBarsBlock` - The block for weathering copper bars.
    - `WeatheringCopperBlocks` - A record of blocks that represent each of the weathering copper states.
    - `WeatheringCopperChainBlock` - The block for weathering copper chains.
    - `WeatheringCopperChestBlock` - The block for the weathering copper chest.
    - `WeatheringCopperGolemStatueBlock` - The block for the weathering copper golem statue.
    - `WeatheringLanternBlock` - The block for weathering lanterns.
    - `WeatheringLightningRodBlock` - The block for the weathering lightning rod.
- `net.minecraft.world.level.block.entity`
    - `BaseContainerBlockEntity#isLocked` - Returns whether the block entity has a lock.
    - `CopperGolemStatueBlockEntity` - The block entity for the copper golem statue.
    - `ListBackedContainer` - A container that is baked by a list of items.
    - `ShelfBlockEntity` - The block entity for the shelf.
- `net.minecraft.world.level.block.entity.vault.VaultConfig#playerDetector` - Returns the detector used to detect specific entities.
- `net.minecraft.world.level.block.state.BlockBehaviour#shouldChangedStateKeepBlockEntity`, `$BlockStateBase#shouldChangedStateKeepBlockEntity` - Returns whether the block entity should be kept if the block is changed to a different block.
- `net.minecraft.world.level.block.state.properties.SideChainPart` - The location of where the chain of an object is connected to.
- `net.minecraft.world.level.chunk`
    - `Configuration` - Configures what type of pallete should be created. 
    - `PalettedContainerFactory` - A factory for constructing paletted containers using the provided strategies.
    - `PalettedContainerRO#bitsPerEntry` - Returns the number of bits needed to represent an entry.
    - `PaletteResize#noResizeExpected` - Returns a resizer that throws an exception.
- `net.minecraft.world.level.levelgen`
    - `Beardifier#EMPTY` - An instance with no pieces or junctions.
    - `DensityFunction#invert` - Inverts the density output by putting the result one over the value.
    - `DensityFunctions#findTopSurface` - Finds the top surface between the two bounds.
    - `NoiseChunk#maxPreliminarySurfaceLevel` - Returns the largest preliminary surface level.
    - `NoiseRouterData#NOISE_ZERO` - A constant containing the base noise level for layer zero in the overworld.
- `net.minecraft.world.level.levelgen.blending.Blender#isEmpty` - Returns whether there is no blending data present.
- `net.minecraft.world.level.levelgen.structure.BoundingBox`
    - `encapsulating` - Returns the smallest box that includes the given boxes.
    - `STREAM_CODEC` - The stream codec for the bounding box.
- `net.minecraft.world.level.levelgen.structure.structures.JigsawStructure$MaxDistance` - The maximum horizontal and vertical distance the jigsaw can expand to.
- `net.minecraft.world.level.pathfinder.Path#STREAM_CODEC` - The stream codec of the path.
- `net.minecraft.world.level.portal.TeleportTransition#createDefault` - Creates the default teleport transition.
- `net.minecraft.world.level.storage.loot.LootContext`
    - `$BlockEntityTarget` - Specifies targets when interacting with a block entity.
    - `$EntityTarget`
        - `TARGET_ENTITY` - The entity being targeted by another, typically the object dropping the loot.
        - `INTERACTING_ENTITY` - The entity interacting with the object dropping the loot.
    - `$ItemStackTarget` - Specifies targets when interacting or using an item stack.
- `net.minecraft.world.level.storage.loot.parameters`
    - `LootContextParams`
        - `TARGET_ENTITY` - The entity being targeted by another, typically the object dropping the loot.
        - `INTERACTING_ENTITY` - The entity interacting with the object dropping the loot.
    - `LootContextParamSets`
        - `ENTITY_INTERACT` - An entity being interacted with.
        - `BLOCK_INTERACT` - A block being interacted with.
- `net.minecraft.world.phys.Vec3#X_AXIS`, `Y_AXIS`, `Z_AXIS` - The unit vector in the positive direction of each axis.
- `net.minecraft.world.phys.shapes.CollisionContext`
    - `alwaysCollideWithFluid` - Whether the collision detector should always collide with a fluid in its path.
    - `emptyWithFluidCollisions` - Returns an empty collision context while checking for any fluid collisions. 
- `net.minecraft.world.waypoints.PartialTickSupplier` - Gets the partial tick given the provided entity.

### List of Changes

- `com.mojang.blaze3d.buffers.GpuBuffer#size` is now private
- `com.mojang.blaze3d.opengl`
    - `DirectStateAccess`
        - `bufferSubData`, `mapBufferRange`, `unmapBuffer`, `flushMappedBufferRange` now take in the buffer usage bit mask
        - `create` now takes in the `GraphicsWorkarounds`
    - `GlStateManager#_texImage2D`, `_texSubImage2D` now takes in a `ByteBuffer` instead of an `IntBuffer`
    - `VertexArrayCache#bindVertexArray` can now take in a nullable `GlBuffer`
- `com.mojang.blaze3d.platform`
    - `ClipboardManager#getClipboard`, `setClipboard` now take in a `Window` instead of the `long` handle
    - `MacosUtil#exitNativeFullscreen`, `clearResizableBit`, `getNsWindow` now take in a `Window` instead of the `long` handle
    - `Window#getWindow` -> `handle`
- `com.mojang.blaze3d.systems`
    - `CommandEncoder#writeToTexture` now takes in a `ByteBuffer` instead of an `IntBuffer`
    - `RenderSystem#flipFrame` now takes in a `Window` instead of the `long` handle
    - `TimerQuery#getInstance` now returns the raw instance rather than an optional-wrapped instance
- `com.mojang.blaze3d.vertex`
    - `PoseStack$Pose#set` is now public
    - `VertexConsumer#addVertexWith2DPose` no longer takes in the z component
- `net.minecraft`
    - `CrashReportCategory#formatLocation` now has an overload that doesn't take in the `LevelHeightAccessor`
    - `DetectedVersion#createFromConstants` -> `createBuiltIn`, now public, taking in the id, name, and optional stable `boolean`
    - `SharedConstants#*_PACK_FORMAT` -> `*_PACK_FORMAT_MAJOR`
    - `WorldVersion`
        - `packVersion` now returns a `PackFormat`
        - `$Simple` now takes in a `PackFormat` for the `resourcePackVersion` and `datapackVersion`
- `net.minecraft.advancements.critereon`
    - `MinMaxBounds` now requires its generic to be `Comparable`
        - `min`, `max` are now default
        - `unwrapPoint` -> `$Bounds#asPoint`
        - `fromReader` is now public
        - `$Doubles`, `$Ints` now take in `$Bounds` for its values
    - `WrappedMinMaxBounds` -> `MinMaxBounds$Bounds`, not one-to-one
- `net.minecraft.client`
    - `Minecraft`
        - `setLevel` no longer takes in the `ReceivingLevelScreen$Reason`
        - `cameraEntity` is now private
        - `openChatScreen` is now public, taking in the `ChatComponent$ChatMethod`
        - `setCamerEntity` can now take in a null entity
        - `forceSetScreen` -> `setScreenAndShow`
        - `getMinecraftSessionService` -> `services`, now returning a `Service` instance
            - The `MinecraftSessionService` can be obtained via `Service#sessionService`
    - `Options#invertYMouse` -> `invertMouseY`
    - `User` no longer takes in the user type
- `net.minecraft.client.data.models.BlockModelGenerators`
    - `condition` now has an overload that takes in an enum or boolean property
    - `createLightningRod` now takes in the regular and waxed blocks
    - `createIronBars` -> `createBarsAndItem`, `createBars`; not one-to-one
- `net.minecraft.client.gui.GuiGraphics#submitSignRenderState` now takes in a `Model$Simple` instead of a `Model`
- `net.minecraft.client.gui.components`
    - `AbstractScrollArea#renderScrollbar` now takes in the current XY position of the cursor.
    - `AbstractSelectionList` no longer takes in the header height
        - `itemHeight` -> `defaultEntryHeight`
        - `addEntry`, `addEntryToTop` now takes in the height of the element
        - `removeEntryFromTop` no longer returns anything
        - `updateSizeAndPosition` can now take in an X component
        - `renderItem` now takes in the entry instead of five integers
        - `renderSelection` now takes in the entry instead of four integers
        - `removeEntry` no longer returns anything
        - `$Entry` now implements `LayoutElement`
        - `render`, `renderBack` -> `renderContent`
    - `ContainerObjectSelectionList` no longer takes in the headerh height
    - `EditBox#setFormatter` -> `addFormatter`, not one-to-one
    - `FocusableTextWidget` now takes in a `$BackgroundFill` instead of a boolean
    - `MultiLineLabel`
        - `renderCentered`, `renderLeftAligned`, `renderLeftAlignedNoShadow` -> `render`, not one-to-one
        - `getStyleAtCentered`, `getStyleAtLeftAligned` -> `getStyle`, not one-to-one
    - `ObjectSelectionList` no longer takes in the headerh height
    - `SpriteIconButton` now takes in a `WidgetSprites` instead of a `ResourceLocation`, and toolip `Component`
        - `sprite` is now a `WidgetSprites`
        - `$Builder#sprite` now has an overload that takes in a `WidgetSprites`
        - `$CenteredIcon` now takes in a `WidgetSprites` instead of a `ResourceLocation`, and toolip `Component`
        - `$TextAndIcon` now takes in a `WidgetSprites` instead of a `ResourceLocation`, and toolip `Component`
    - `WidgetSprites` now has an overload with a single `ResourceLocation`
- `net.minecraft.client.gui.components.spectator.SpectatorGui#onMouseMiddleClick` -> `onHotbarActionKeyPressed`
- `net.minecraft.client.gui.render.state`
    - `GuiElementRenderState#buildVertices` no longer takes in the `z` component
    - `GuiRenderState#forEachElement` now takes in a `Consumer<GuiElementRenderState>` instead of a `GuiRenderState$LayeredElementConsumer`
- `net.minecraft.client.gui.render.state.pip.GuiSignRenderState` now takes in a `Model$Simple` instead of a `Model`
- `net.minecraft.client.gui.screens`
    - `ChatScreen` now takes in a boolean representing whether the message is a draft
        - `initial` is now protected
    - `EditServerScreen` -> `ManageServerScreen`
    - `InBedChatScreen` now takes in the initial text and whether there is a draft mesage in the box
    - `LevelLoadingScreen` now takes in a `LevelLoadTracker` and `LevelLoadingScreen$Reason`
    - `PauseScreen#disconnectFromWorld` -> `Minecraft#disconnectFromWorld`
    - `ReceivingLevelScreen` has been merged into `LevelLoadingScreen`
    - `Screen`
        - `renderWithTooltip` -> `renderWithTooltipAndSubtitles`
        - `$NarratableSearchResult` is now a record
        - `isValidCharacterForName` now takes in a codepoint `int` instead of a `char`
- `net.minecraft.client.gui.screens.achievement.StatsScreen`
    - `initLists`, `initButtons` merged into `onStatsUpdated`
    - `$ItemRow#getItem` is now protected
- `net.minecraft.client.gui.screens.dialog.DialogScreen$WarningScreen#create` now takes in a `DialogConnectionAccess`
- `net.minecraft.client.gui.screens.inventory.tooltip.ClientActivePlayersTooltip$ActivePlayersTooltip#profiles` now is a list of `PlayerSkinRenderCache$RenderInfo`s instead of `ProfileResult`s
- `net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen`
    - `*_BUTTON_WIDTH` are now private
    - `join` is now public
- `net.minecraft.client.gui.screens.options.OptionsScreen#CONTROLS` is now public
- `net.minecraft.client.gui.screens.packs`
    - `PackSelectionModel` now takes in a `Consumer<PackSelectionModel$EntryBase>` instead of a `Runnable`
        - `$EntryBase` is now public
    - `TransferableSelectionList` now is a list of `$Entry`s instead of `$PackEntry`s
        - `$PackEntry` is no longer static and extends `$Entry`
- `net.minecraft.client.gui.screens.worldselection`
    - `CreateWorldScreen#openFresh`, `testWorld`, `createFromExisting` now take in a `Runnable` instead of a `Screen`
    - `WorldSelectionList` now has a package-private constructor
        - `getScreen` now returns a basic `Screen`
        - `$WorldListEntry` is now static
            - `canJoin` -> `canInteract`, not one-to-one
- `net.minecraft.client.gui.spectator.PlayerMenuItem` now only takes in the `PlayerInfo`
- `net.minecraft.client.main.GameConfig$UserData` no longer takes in the `PropertyMap`s
- `net.minecraft.client.multiplayer`
    - `ClientHandshakePacketListenerImpl` now takes in a `LevelLoadTracker`
    - `CommonListenerCookie` now takes in a `LevelLoadTracker`, map of seen players, and whether the insecure chat warning has been shown
    - `LevelLoadStatusManager` -> `LevelLoadTracker`, not one-to-one
        - `CLOSE_DELAY_MS` -> `LEVEL_LOAD_CLOSE_DELAY_MS`, now public
    - `TransferState` now takes in a map of seen players and whether the insecure chat warning has been shown
- `net.minecraft.client.multiplayer.chat.ChatListener#clearQueue` -> `flushQueue`
- `net.minecraft.client.renderer`
    - `DimensionSpecialEffects#forceBrightLightmap` -> `hasEndFlashes`, not one-to-one
    - `LevelEventHandler` now takes in a `ClientLevel` instead of the `Level` and `LevelRenderer`
    - `LevelRenderer`
        - `prepareCullFrustum` is now private
        - `renderLevel` now takes in an additional `Matrix4f` for the frustum
- `net.minecraft.client.resources.WaypointStyle#validate` is now public
- `net.minecraft.client.server.IntegratedServer` now takes in a `LevelLoadListener` instead of a `ChunkProgressListenerFactory`
- `net.minecraft.client.sounds`
    - `SoundEngine#updateCategoryVolume` no longer takes in the gain
    - `SoundEngineExecutor#flush` -> `shutDown`, not one-to-one
    - `SoundManager#updateSourceVolume` no longer takes in the gain
- `net.minecraft.commands.arguments.coordinates`
    - `LocalCoordinates` is now a record
    - `WorldCoordinate` is now a record
    - `WorldCoordinates` is now a record
- `net.minecraft.commands.arguments.selector.EntitySelectorParser`
    - `getDistance`, `getLevel` can now be `null`
    - `*Rot*` methods now return or use `MinMaxBounds$FloatDegrees`
        - Return values can be `null`
- `net.minecraft.core.Registry`
    - `getRandomElementOf` -> `HolderGetter#getRandomElementOf`
    - `registerForHolder` now has an additional generic, making the `Holder$Reference` returned hold the actual object type instead of the registry type
- `net.minecraft.core.component.PatchedDataComponentMap#set` now has an overload that takes in a `TypedDataComponent`
- `net.minecraft.data.worldgen.TerrainProvider` methods now use a `BoundedFloatFunction` generic instead of `ToFloatFunction`
- `net.minecraft.gametest.framework`
    - `GameTestHelper`
        - `spawn` now has overloads that take in some number of entities to spawn
        - `setBlock` now has overloads to take in a direction for block facing
        - `fail` now has an overload that takes in a string
    - `GameTestRunner` now takes in whether the level should be cleared for more space to spawn between batches
        - `$Builder#haltOnError` no longer takes in any parameters
- `net.minecraft.nbt.NbtUtils#addDataVersion`, `addCurrentDataVersion` now has an overload that takes in a `Dynamic` instead of a `CompoundTag` or `ValueOutput`
- `net.minecraft.network`
    - `FriendlyByteBuf#readSectionPos`, `writeSectionPos` -> `SectionPos#STREAM_CODEC`
    - `VarInt#MAX_VARINT_SIZE` is now public
- `net.minecraft.network.protocol.PacketUtils#ensureRunningOnSameThread` now takes in a `PacketProcessor` instead of a `BlockableEventLoop`
- `net.minecraft.network.protocol.game`
    - `ClientboundAddEntityPacket#getXa`, `getYa`, `getZa` -> `getMovement`
    - `ClientboundExplodePacket` now takes in a radius, block count, and the block particles to display
    - `ClientboundPlayerRotationPacket` now takes in whether the XY rotation is relative
    - `ClientboundSetEntityMotionPacket#getXa`, `getYa`, `getZa` -> `getMovement`
- `net.minecraft.server`
    - `SPAWN_POSITION_SEARCH_RADIUS` is now public
    - `MinecraftServer` now takes in a `LevelLoadListener` instead of a `ChunkProgressListenerFactory`
        - `createLevels` no longer takes in a `ChunkProgressListener`
        - `getSessionService`, `getProfileKeySignatureValidator`, `getProfileRepository`, `getProfileCache` -> `services`, not one-to-one
            - `getProfileCache` is now `nameToIdCache`, not one-to-one
        - `updateMobSpawningFlags` is now public
- `net.minecraft.server.level`
    - `ChunkMap` no longer takes in the `ChunkProgressListener`
        - `getTickingGenerated` -> `allChunksWithAtLeastStatus`, not one-to-one
        - `broadcast`, `broadcastAndSend` -> `sendToTrackingPlayers`, `sendToTrackingPlayersFiltered`, `sendToTrackingPlayersAndSelf`; not one-to-one
    - `PlayerRespawnLogic` -> `PlayerSpawnFinder`, not one-to-one
    - `ServerChunkCache` no longer takes in the `ChunkProgressListener`
        - `broadcastAndSend` -> `sendToTrackingPlayersAndSelf`, not one-to-one
        - `broadcast` -> `sendToTrackingPlayers`, not one-to-one
    - `ServerEntity` now takes in a `$Synchronizer` instead of the brodcast method references
    - `ServerEntityGetter#getNearestEntity` now has an overload that takes in a `TagKey` of entities instead of a class
    - `ServerLevel` no longer takes in the `ChunkProgressListener`
        - `waitForChunkAndEntities` -> `waitForEntities`, not one-to-one
        - `tickCustomSpawners` no longer takes in the tick friendlies `boplean`
- `net.minecraft.server.level.chunk`
    - `ChunkAccess` now takes in a `PalettedContainerFactory` instead of a `Registry<Biome>`
- `net.minecraft.server.level.progress`
    - `ChunkProgressListener` -> `LevelLoadListener`, not one-to-one
    - `ChunkProgressListenerFactory` -> `MinecraftServer#createChunkLoadStatusView`, not one-to-one
    - `LoggerChunkProgressListener` -> `LoggingLevelLoadListener`, not one-to-one
    - `ProcessorChunkProgressListener`, `StoringChunkProgressListener` -> `LevelLoadProgressListener`, not one-to-one
- `net.minecraft.server.packs`
    - `AbstractPackResources#getMetadataFromStream` now takes in a `PackLocationInfo`
    - `OverlayMetadataSection`
        - `TYPE` -> `CLIENT_TYPE`, `SERVER_TYPE`
        - `overlaysForVersion` now takes in a `PackFormat` instead of an integer
        - `$OverlayEntry` now takes in an `InclusiveRange<PackFormat>` instead of an `InclusiveRange<Integer>`
            - `isApplicable` now takes in a `PackFormat` instead of an integer
- `net.minecraft.server.packs.metadata.pack.PackMetadataSection` now takes in an `InclusiveRange<PackFormat>` instead of the raw pack format and integer pack.
    - `CODEC` -> `FALLBACK_CODEC`, now private
    - `TYPE` -> `CLIENT_TYPE`, `SERVER_TYPE`, `FALLBACK_TYPE`
- `net.minecraft.server.packs.repository`
    - `Pack#readPackMetadata` now takes in a `PackFormat` and `PackType` instead of an integer
    - `PackCompatibility#forVersion` now takes in `PackFormat`s instead of integers
- `net.minecraft.util`
    - `CubicSpline` methods and inner classes now use `BoundedFloatFunction` instead of `ToFloatFunction`
    - `ExtraCodecs`
        - `GAME_PROFILE_WITHOUT_PROPERTIES` -> `AUTHLIB_GAME_PROFILE`, now a `Codec` and public
        - `GAME_PROFILE` -> `STORED_GAME_PROFILE`, now a `MapCodec`
    - `StringRepresentable#createNameLookup` now can take in an arbitrary object and return a string
        - The base overload that takes in the object array uses `getSerializedName`
    - `StringUtil#isAllowedChatCharacter` now takes in an `int` codepoint instead of a `char`
    - `ToFloatFunction` -> `BoundedFloatFunction`
        - This still exists as a standard interface to convert some object to a `float`
- `net.minecraft.world.entity`
    - `AgeableMob#finalizeSpawn` is now nullable
    - `Entity`
        - `startRiding(Entity)` is now final
        - `startRiding(Entity, boolean)` now takes in an additional boolean of whether to trigger the game event and criteria triggers
        - `killedEntity` now takes in the `DamageSource`
        - `moveOrInterpolateTo` now has overloads that take in an XY rotation, a `Vec3` position, or all three as optionals
        - `lerpMotion` now takes in a `Vec3` instead of three doubles
        - `forceSetRotation` now takes in whether the XY rotation is relative
        - `teleportSetPosition` now has an overload that takes in both the starting and end position
    - `EntityReference` is now private, constructed using the `of` static constructors
        - `getEntity` now takes in a `UUIDLookup<? extends UniquelyIdentifyable>` instead of a `UUIDLookup<? super StoredEntityType>`
        - `get` now takes in a `Level` instead of a `UUIDLookup`
    - `EntityType` now takes in whether the entity is allowed in peaceful mode
        - `create`, `loadEntityRecursive` now has an overload that takes in an `EntityType`
    - `LivingEntity`
        - `shouldDropLoot` now takes in the `ServerLevel`
        - `dropFromLootTable` now has overloads to take in a specific loot table key and how the items should be dispensed
        - `getSlotForHand` -> `InteractionHand#asEquipmentSlot`
    - `Mob#shouldDespawnInPeaceful` -> `EntityType#isAllowedInPeaceful`, not one-to-one
    - `Pose` now implements `StringRepresentable`, has an associated `Codec`
- `net.minecraft.world.entity.ai.village.poi`
    - `PoiManager#add` now returns a `PoiRecord`
    - `PoiSection#add` now returns a `PoiRecord`
- `net.minecraft.world.entity.animal.Animal#usePlayerItem` -> `Mob#usePlayerItem`
- `net.minecraft.world.entity.animal.armadillo.Armadillo#brushOffScute` now takes in an `Entity` and `ItemStack`
- `net.minecraft.world.entity.player`
    - `Player` now extends `Avatar` instead of `LivingEntity`
        - `DATA_SHOULDER_*` -> `DATA_SHOULDER_PARROT_*`, now private
        - `oBob`, `bob` -> `ClientAvatarState#bob0`, `bob`
        - `*Cloak*` -> `ClientAvatarState#*Cloak*`
        - `setEntityOnShoulder` -> `ServerPlayer#setEntityOnShoulder`
        - `*ShoulderEntity*` -> `ServerPlayer#*ShoulderEntity*`
        - `setMainArm` -> `Avatar#setMainArm`
        - `CROUCH_BB_HEIGHT`, `SWIMMING_BB_WIDTH`, `SWIMMING_BB_HEIGHT`, `STANDING_DIMENSIONS` have been moved to `Avatar`
        - `POSES` -> `Avatar#POSES`, now protected
    - `PlayerModelPart` now implements `StringRepresentable`
- `net.minecraft.world.entity.projectile.Projectile`
    - `deflect` now takes in an `EntityReference` of the owner instead of the `Entity` itself
    - `onDeflection` no longer takes in the direct `Entity`
    - `checkLeftOwner` now checks if the projectile is outside the collision range only when `leftOwner` and `leftOwnerChecked` are both false
        - The original logic pertaining to this method has been moved to `isOutsideOwnerCollisionRange`, which is still private
- `net.minecraft.world.entity.vehicle.MinecartBehavior#lerpMotion` now takes in a `Vec3` instead of three doubles
- `net.minecraft.world.item`
    - `ItemStack#set` now has an overload that takes in a `TypedDataComponent`
    - `SpawnEggItem` no longer takes in the `EntityType`
        - `spawnsEntity` no longer takes in the `HolderLookup$Provider`
        - `getType` no longer takes in the `HolderLookup$Provider`
- `net.minecraft.world.item.component`
    - `Bees#STREAM_CODEC` now requires a `RegistryFriendlyByteBuf`
    - `ResolvableProfile` is now a sealed class with a protected constructor, created through the static `createResolved`, `createUnresolved`
        - `pollResolve`, `resolve`, `isResolved` -> `resolveProfile`, not one-to-one
- `net.minecraft.world.item.enchantment.effects.ExplodeEffect` now takes in a list of block particles to display
- `net.minecraft.world.level`
    - `BaseCommandBlock` no longer implements `CommandSource`
        - `createCommandSourceStack` now takes in a `CommandSource`
    - `BaseSpawner#getoSpin` -> `getOSpin`
    - `CustomSpawner#tick` no longer takes in the tick friendlies `boolean`
    - `GameRules`
        - `availableRules` is now public
        - `$BooleanValue#create` is now public
        - `$IntegerValue#create` is now public
    - `Level` no longer implements `UUIDLookup`
        - `explode` now takes in a weighter list of explosion particles to display
        - `neighborUpdater` is now a `CollectingNeighborUpdater`
        - `tickBlockEntities` is now public
    - `ServerExplosion#explode` now returns the number of blocks exploded
- `net.minecraft.world.level.border`
    - `BorderChangeListener`
        - `onBorderSizeSet` -> `onSetSize`
        - `onBorderSizeLerping` -> `onLerpSize`
        - `onBorderCenterSet` -> `onSetCenter`
        - `onBorderSetWarningTime` -> `onSetWarningTime`
        - `onBorderSetWarningBlocks` -> `onSetWarningBlocks`
        - `onBorderSetDamagePerBlock` -> `onSetDamagePerBlock`
        - `onBorderSetDamageSafeZone` -> `onSetSafeZone`
    - `WorldBorder` now extends `SavedData`
        - `getLerpRemainingTime` -> `getLerpTime`
        - `*DamageSafeZone` -> `*SafeZone`
        - `DEFAULT_SETTINGS` -> `$Settings#DEFAULT`
        - `createSettings` has been replaced with the `$Settings` constructor
        - `$BorderExtent#getLerpRemainingTime` -> `getLerpTime`
        - `$Settings` is now a record, meaning all getters now use the record format
- `net.minecraft.world.level.block`
    - `BeehiveBlock#dropHoneycomb(Level, BlockPos)` -> `dropHoneyComb(ServerLevel, ItemStack, BlockState, BlockEntity, Entity, BlockPos)`
    - `CaveVines#use` entity is no longer nullable
    - `ChestBlock` now takes in an open and close sound
    - `ChiseledBookShelfBlock` now implements `SelectableSlotContainer`
        - `BOOKS_PER_ROW` is now private
- `net.minecraft.world.level.block.entity`
    - `ChiseledBookShelfBlockEntity` now implements `ListBackedContainer`
        - `count` -> `ListBackedContainer#count`
    - `ContainerOpenersCounter`
        - `isOwnContainer` is now public
        - `incrementOpeners`, `decrementOpeners` now takes in a `LivingEntity` instead of a `Player`
        - `getPlayersWithContainerOpen` -> `getEntitiesWithContainerOpen`, now public, not one-to-one
- `net.minecraft.world.level.block.state.BlockBehaviour`
    - `getAnalogOutputSignal`, `$BlockStateBase#getAnalogOutputSignal` now takes in the direction the signal is coming from
    - `$Properties#noCollission` -> `noCollision`
- `net.minecraft.world.level.block.state.properties.BlockStateProperties#CHISELED_BOOKSHELF_SLOT_*_OCCUPIED` -> `SLOT_*_OCCUPIED`
- `net.minecraft.world.level.chunk`
    - `GlobalPalette#create` -> `Configuration#createPalette`
    - `HashMapPalette` no longer takes in the `IdMap`
        - `create` no longer takes in the `IdMap` and `PaletteResize`
    - `LevelChunkSection` now takes in a `PalettedContainerFactory` instead of a `Registry<Biome>`
    - `LinearPalette` no longer takes in the `IdMap`
        - `create` no longer takes in the `IdMap` and `PaletteResize`
    - `Palette`
        - `idFor`, `read`, `write`, `getSerializedSize` now takes in an `IdMap`
        - `copy` no longer takes in the `PaletteResize`
        - `$Factory#create` no longer takes in the `IdMap` and `PaletteResize`
    - `PalettedContainer` no longer takes in the `IdMap`
        - `codec*` no longer takes in the `IdMap`
        - `unpack` is now `public`, visible for testing
        - `$Configuration` -> `Configuration$Simple`
        - `$Strategy` -> `Strategy`
            - `getConfiguration` now takes in an `int` instead of an `IdMap`, now protected
    - `PalettedContainerRO`
        - `pack` no longer takes in the `IdMap`
        - `$PackedData` now takes in the bits per entry `int`
        - `$Unpacker#read` no longer takes in the `IdMap`
    - `PaletteResize` is now `public`
    - `ProtoChunk` now takes in a `PalettedContainerFactory` instead of a `Registry<Biome>`
    - `SingleValuePalette` no longer takes in the `IdMap` and `PaletteResize`
        - `create` no longer takes in the `IdMap` and `PaletteResize`
- `net.minecraft.world.level.chunk.storage.SerializableChunkData#containerFactory` now takes in a `PalettedContainerFactory` instead of a `Registry<Biome>`
    - `parse` now takes in a `PalettedContainerFactory` instead of a `RegistryAccess`
- `net.minecraft.world.level.entity.UUIDLookup#getEntity` -> `lookup`
- `net.minecraft.world.level.gameevent`
    - `BlockPositionSource` is now a record
    - `EntityPositionSource#getUuid` is now public
- `net.minecraft.world.level.levelgen`
    - `Beardifier` now takes in lists instead of iterators and a nullable `BoundingBox`
    - `DensityFunctions%Coordinate` now implements `BoundedFloatFunction` instead of `ToFloatFunction`
    - `NoiseRouter#initialDensityWithoutJaggedness` -> `preliminarySurfaceLevel`
- `net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement#addPieces` now takes in a `JigsawStructure$MaxDistance` instead of an integer
- `net.minecraft.world.level.levelgen.structure.structures.JigsawStructure` now takes in a `JigsawStructure$MaxDistance` instead of an integer
- `net.minecraft.world.level.pathfinder.Path` is now final
- `net.minecraft.world.level.portal.TeleportTransition#missingRespawnBlock` no longer takes in the `Entity` to get the respawn data from
- `net.minecraft.world.level.storage`
    - `PrimaryLevelData` now takes in an optional wrapped `WorldBorder$Settings`
    - `ServerLevelData#*WorldBorder` -> `*LegacyWorldBorderSettings`, now dealing with optional wrapped `WorldBorder$Settings`
- `net.minecraft.world.level.storage.loot.functions`
    - `CopyComponentsFunction`
        - `copyComponents` has been split to `copyComponentsFromEntity`, `copyComponentsFromBlockEntity`
        - `$Source` is now an interface
            - Original implementation is in `$BlockEntitySource`
            - `getReferencedContextParams` -> `contextParam`, not one-to-one
    - `CopyNameFunction`
        - `copyName` now takes in a `$Source` instead of a `$NameSource`
        - `$NameSource` -> `$Source`, now a record, not one-to-one
- `net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider`
    - `CODEC` -> `MAP_CODEC`, not one-to-one
    - `$Getter` -> `$Source`
        - `get` now has an overload that takes in the generic
        - `getReferencedContextParams` -> `contextParam`, not one-to-one
- `net.minecraft.world.phys.shapes.EntityCollisionContext` now takes in a `boolean` instead of a `Predicate<FluidState>`
    - `EMPTY` -> `$Empty`, not one-to-one
- `net.minecraft.world.waypoints.TrackedWaypoint`
    - `STREAM_CODEC` is now final
    - `yawAngleToCamera` now takes in a `PartialTickSupplier`
    - `pitchDirectionToCamera` now takes in a `PartialTickSupplier`

### List of Removals

- `com.mojang.blaze3d.audio.Listener#setGain`, `getGain`
- `com.mojang.blaze3d.opengl`
    - `GlShaderModule#compile`
    - `GlStateManager`
        - `_glUniform1`, `_glUniform2`, `_glUniform3`, `_glUniform4`
        - `_glUniformMatrix4`
        - `glActiveTexture`, `_getActiveTexture`
- `com.mojang.blaze3d.pipeline.RenderTarget#viewWidth`, `viewHeight`
- `com.mojang.blaze3d.systems.RenderSystem`
    - `getQuadVertexBuffer`
    - `setModelOffset`, `resetModelOffset`, `getModelOffset`
- `com.mojang.blaze3d.vertex`
    - `DefaultVertexFormat#BLIT_SCREEN`
    - `VertexConsumer#setWhiteAlpha`
- `net.minecraft`
    - `SharedConstants#VERSION_STRING`
    - `Util#getVmArguments`
- `net.miencraft.advancements.critereon.MinMaxBounds$BoundsFactory`, `$BoundsFromReaderFactory`
- `net.minecraft.client`
    - `Camera#FOG_DISTANCE_SCALE`
    - `Minecraft`
        - `getProgressListener`
        - `getProfileKeySignatureValidator`, `canValidateProfileKeys`
    - `Options#RENDER_DISTANCE_TINY`, `RENDER_DISTANCE_NORMAL`
    - `User#getType`, `$Type`
- `net.minecraft.client.gui.components`
    - `AbstractSelectionList`
        - `headerHeight`, associated constructor has been removed
        - `setSelectedIndex`, `getFirstElement`, `getEntry`
        - `isSelectedItem`
        - `renderHeader`, `renderDecorations`
        - `ensureVisible`
        - `remove`
    - `OptionsList#getMouseOver`
    - `StringWidget#alignLeft`, `alignCenter`, alignRight`
- `net.minecraft.client.gui.render.state.GuiRenderState`
    - `down`, `$Node#down`
    - `$LayeredElementConsumer`
- `net.minecraft.client.gui.screens.LevelLoadingScreen#renderChunks`
- `net.minecraft.client.gui.screens.achievement.StatsScreen#setActiveList`
- `net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen`
    - `BUTTON_ROW_WIDTH`, `FOOTER_HEIGHT`
    - `setSelected`
    - `joinSelectedServer`
- `net.minecraft.client.main.GameConfig$UserData#userProperties`, `profileProperties`
- `net.minecraft.client.renderer.chunk.ChunkSectionLayer#outputTarget`
- `net.minecraft.client.resources.SkinManager#getInsecureSkin`
- `net.minecraft.gametest.framework.GameTestTicker#startTicking`
- `net.minecraft.network.syncher.EntityDataSerializers#COMPOUND_TAG`
- `net.minecraft.server.MinecraftServer#getSpawnRadius`
- `net.minecraft.server.dedicated.DedicatedServer#storeUsingWhiteList`
- `net.minecraft.server.level`
    - `ServerChunkCache#getTickingGenerated`
    - `ServerPlayer#loadGameTypes`
- `net.minecraft.server.packs.resources.ResourceMetadata`
    - `copySections`
    - `$Builder`
- `net.minecraft.world.entity.Entity`
    - `spawnAtLocation(ServerLevel, ItemLike, int)`
    - `getServer`
- `net.minecraft.world.entity.decoration.HangingEntity#HANGING_ENTITY`
- `net.minecraft.world.entity.item.ItemEntity#copy`
- `net.minecraft.world.entity.monster`
    - `Creeper#canDropMobsSkull`, `increaseDroppedSkulls`
    - `Zombie#getSkull`
- `net.minecraft.world.entity.player.Player`
    - `getScoreboard`
    - `isModelPartShown`
- `net.minecraft.world.entity.vehicle.MinecartTNT#explode(double)`
- `net.minecraft.world.item.Item#verifyComponentsAfterLoad`
- `net.minecraft.world.level`
    - `BlockGetter#MAX_BLOCK_ITERATIONS_ALONG_TRAVEL`
    - `GameRules#RULE_SPAWN_CHUNK_RADIUS`
- `net.minecraft.world.level.border`
    - `BorderChangeListener$DelegateBorderChangeListener`
    - `WorldBorder`
        - `closestBorder`
        - `$DistancePerDirection`
        - `$Settings#read`, `write`
- `net.minecraft.world.level.block.FletchingTableBlock`
- `net.minecraft.world.level.block.entity.SkullBlockEntity`
    - `CHECKED_MAIN_THREAD_EXECUTOR`
    - `setup`, `clear`
    - `fetchGameProfile`
    - `setOwner`
- `net.minecraft.world.level.portal.TeleportTransition(ServerLevel, Entity, TeleportTransition.PostTeleportTransition)`
- `net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider`
    - `BLOCK_ENTITY`
    - `$Getter#getId`
