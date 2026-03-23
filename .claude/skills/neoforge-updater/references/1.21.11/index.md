# Minecraft 1.21.10 -> 1.21.11 Mod Migration Primer

This is a high level, non-exhaustive overview on how to migrate your mod from 1.21.10 to 1.21.11. This does not look at any specific mod loader, just the changes to the vanilla classes. All provided names use the official mojang mappings.

This primer is licensed under the [Creative Commons Attribution 4.0 International](http://creativecommons.org/licenses/by/4.0/), so feel free to use it as a reference and leave a link so that other readers can consume the primer.

If there's any incorrect or missing information, please file an issue on this repository or ping @ChampionAsh5357 in the Neoforged Discord server.

Thank you to:

- @xfacthd for some educated guesses regarding the usage annotations
- @dinnerbone for pointing out gizmos can also be submitted on the server in singleplayer worlds
- @thatgravyboat for pointing out the change in parameter orders for `Mth#clampedLerp`

## Pack Changes

There are a number of user-facing changes that are part of vanilla which are not discussed below that may be relevant to modders. You can find a list of them on [Misode's version changelog](https://misode.github.io/versions/?id=1.22&tab=changelog).

## The Rename Shuffle

Many core classes, method, and parameters have been shuffled around and renamed while still retaining their individual function. The following is a list of the most important changes

### `ResourceLocation` to `Identifier`

All references to `ResourceLocation`, whether in method names, parameters, or other classes, have been replaced with `Identifier`.

### The `util` Package

Most utility classes have been moved to `net.minecraft.util`. These will need to be reimported.

### `critereon` to `criterion`

`net.minecraft.advancements.critereon` has been renamed to `net.minecraft.advancements.criterion`. These will need to be reimported.

### Entity and Object Subpackages

Both `net.minecraft.client.model` and `net.minecraft.world.entity` have been reorganized into additional subpackages based on the type of backing object. These will need to be reimported.

- `net.minecraft`
    - `BlockUtil` -> `.util.BlockUtil`
    - `FileUtil` -> `.util.FileUtil`
    - `ResourceLocationException` -> `IdentifierException`
    - `Util` -> `.util.Util`
- `net.minecraft.advancements.critereon` -> `.advancements.criterion`
- `net.minecraft.client.gui.screens.inventory.JigsawBlockEditScreen#isValidResourceLocation` -> `isValidIdentifier`
- `net.minecraft.client.model`
    - `AbstractBoatModel` -> `.object.boat.AbstractBoatModel`
    - `AbstractEquineModel` -> `.animal.equine.AbstractEquineModel`
    - `AbstractPiglinModel` -> `.monster.piglin.AbstractPiglinModel`
    - `AbstractZombieModel` -> `.monster.zombie.AbstractZombieModel`
    - `AllayModel` -> `.animal.allay.AllayModel`
    - `ArmadilloModel` -> `.animal.armadillo.ArmadilloModel`
    - `ArmorStandArmorModel` -> `.object.armorstand.ArmorStandArmorModel`
    - `ArmorStandModel` -> `.object.armorstand.ArmorStandModel`
    - `ArrowModel` -> `.object.projectile.ArrowModel`
    - `AxolotlModel` -> `.animal.axolotl.AxolotlModel`
    - `BannerFlagModel` -> `.object.banner.BannerFlagModel`
    - `BannerModel` -> `.object.banner.BannerModel`
    - `BatModel` -> `.ambient.BatModel`
    - `BeeModel` -> `.animal.bee.BeeModel`
    - `BeeStingerModel` -> `.animal.bee.BeeStingerModel`
    - `BellModel` -> `.object.bell.BellModel`
    - `BlazeModel` -> `.monster.blaze.BlazeModel`
    - `BoatModel` -> `.object.boat.BoatModel`
    - `BoggedModel` -> `.monster.skeleton.BoggedModel`
    - `BookModel` -> `.object.book.BookModel`
    - `BreezeModel` -> `.monster.breeze.BreezeModel`
    - `CamelModel` -> `.animal.camel.CamelModel`
    - `CamelSaddleModel` -> `.animal.camel.CamelSaddleModel`
    - `CatModel` -> `.animal.feline.CatModel`
    - `ChestModel` -> `.object.chest.ChestModel`
    - `ChickenModel` -> `.animal.chicken.ChickenModel`
    - `CodModel` -> `.animal.fish.CodModel`
    - `ColdChickenModel` -> `.animal.chicken.ColdChickenModel`
    - `ColdCowModel` -> `.animal.cow.ColdCowModel`
    - `ColdPigModel` -> `.animal.pig.ColdPigModel`
    - `CopperGolemModel` -> `.animal.golem.CopperGolemModel`
    - `CopperGolemStatueModel` -> `.object.statue.CopperGolemStatueModel`
    - `CowModel` -> `.animal.cow.CowModel`
    - `CreakingModel` -> `.monster.creaking.CreakingModel`
    - `CreeperModel` -> `.monster.creeper.CreeperModel`
    - `DolphinModel` -> `.animal.dolphin.DolphinModel`
    - `DonkeyModel` -> `.animal.equine.DonkeyModel`
    - `DrownedModel` -> `.monster.zombie.DrownedModel`
    - `ElytraModel` -> `.object.equipment.ElytraModel`
    - `EndCrystalModel` -> `.object.crystal.EndCrystalModel`
    - `EndermanModel` -> `.monster.enderman.EndermanModel`
    - `EndermiteModel` -> `.monster.endermite.EndermiteModel`
    - `EquineSaddleModel` -> `.animal.equine.EquineSaddleModel`
    - `EvokerFangsModel` -> `.effects.EvokerFangsModel`
    - `FelineModel` -> `.animal.feline.FelineModel`
    - `FoxModel` -> `.animal.fox.FoxModel`
    - `FrogModel` -> `.animal.frog.FrogModel`
    - `GhastModel` -> `.monster.ghast.GhastModel`
    - `GiantZombieModel` -> `.monster.zombie.GiantZombieModel`
    - `GoatModel` -> `.animal.goat.GoatModel`
    - `GuardianModel` -> `.monster.guardian.GuardianModel`
    - `GuardianParticleModel` -> `.monster.guardian.GuardianParticleModel`
    - `HappyGhastHarnessModel` -> `.animal.ghast.HappyGhastHarnessModel`
    - `HappyGhastModel` -> `.animal.ghast.HappyGhastModel`
    - `HoglinModel` -> `.monster.hoglin.HoglinModel`
    - `HorseModel` -> `.animal.equine.HorseModel`
    - `IllagerModel` -> `.monster.illager.IllagerModel`
    - `IronGolemModel` -> `.animal.golem.IronGolemModel`
    - `LavaSlimeModel` -> `.monster.slime.MagmaCubeModel`
    - `LeashKnotModel` -> `.object.leash.LeashKnotModel`
    - `LlamaModel` -> `.animal.llama.LlamaModel`
    - `LlamaSpitModel` -> `.animal.llama.LlamaSpitModel`
    - `MinecartModel` -> `.object.cart.MinecartModel`
    - `OcelotModel` -> `.animal.feline.OcelotModel`
    - `PandaModel` -> `.animal.panda.PandaModel`
    - `ParrotModel` -> `.animal.parrot.ParrotModel`
    - `PhantomModel` -> `.monster.phantom.PhantomModel`
    - `PiglinHeadModel` -> `.object.skull.PiglinHeadModel`
    - `PiglinModel` -> `.monster.piglin.PiglinModel`
    - `PigModel` -> `.animal.pig.PigModel`
    - `PlayerCapeModel` -> `.player.PlayerCapeModel`
    - `PlayerEarsModel` -> `.player.PlayerEarsModel`
    - `PlayerModel` -> `.player.PlayerModel`
    - `PolarBearModel` -> `.animal.polarbear.PolarBearModel`
    - `PufferfishBigModel` -> `.animal.fish.PufferfishBigModel`
    - `PufferfishMidModel` -> `.animal.fish.PufferfishMidModel`
    - `PufferfishSmallModel` -> `.animal.fish.PufferfishSmallModel`
    - `RabbitModel` -> `.animal.rabbit.RabbitModel`
    - `RaftModel` -> `.object.boat.RaftModel`
    - `RavagerModel` -> `.monster.ravager.RavagerModel`
    - `SalmonModel` -> `.animal.fish.SalmonModel`
    - `SheepFurModel` -> `.animal.sheep.SheepFurModel`
    - `SheepModel` -> `.animal.sheep.SheepModel`
    - `ShieldModel` -> `.object.equipment.ShieldModel`
    - `ShulkerBulletModel` -> `.object.projectile.ShulkerBulletModel`
    - `ShulkerModel` -> `.monster.shulker.ShulkerModel`
    - `SilverfishModel` -> `.monster.silverfish.SilverfishModel`
    - `SkeletonModel` -> `.monster.skeleton.SkeletonModel`
    - `SkullModel` -> `.object.skull.SkullModel`
    - `SkullModelBase` -> `.object.skull.SkullModelBase`
    - `SlimeModel` -> `.monster.slime.SlimeModel`
    - `SnifferModel` -> `.animal.sniffer.SnifferModel`
    - `SnowGolemModel` -> `.animal.golem.SnowGolemModel`
    - `SpiderModel` -> `.monster.spider.SpiderModel`
    - `SpinAttackEffectModel` -> `.effects.SpinAttackEffectModel`
    - `SquidModel` -> `.animal.squid.SquidModel`
    - `StriderModel` -> `.monster.strider.StriderModel`
    - `TadpoleModel` -> `.animal.frog.TadpoleModel`
    - `TridentModel` -> `.object.projectile.TridentModel`
    - `TropicalFishModelA` -> `.animal.fish.TropicalFishSmallModel`
    - `TropicalFishModelB` -> `.animal.fish.TropicalFishLargeModel`
    - `TurtleModel` -> `.animal.turtle.TurtleModel`
    - `VexModel` -> `.monster.vex.VexModel`
    - `VillagerModel` -> `.npc.VillagerModel`
    - `WardenModel` -> `.monster.warden.WardenModel`
    - `WarmCowModel` -> `.animal.cow.WarmCowModel`
    - `WindChargeModel` -> `.object.projectile.WindChargeModel`
    - `WitchModel` -> `.monster.witch.WitchModel`
    - `WitherBossModel` -> `.monster.wither.WitherBossModel`
    - `WolfModel` -> `.animal.wolf.WolfModel`
    - `ZombieModel` -> `.monster.zombie.ZombieModel`
    - `ZombieVillagerModel` -> `.monster.zombie.ZombieVillagerModel`
    - `ZombifiedPiglinModel` -> `.monster.piglin.ZombifiedPiglinModel`
- `net.minecraft.client.model.dragon`
    - `DragonHeadModel` -> `.model.object.skull.DragonHeadModel`
    - `EnderDragonModel` -> `.model.monster.dragon.EnderDragonModel`
- `net.minecraft.client.resources.sounds`
    - `AbstractSoundInstance#location` -> `identifier`
    - `SoundInstance#getLocation` -> `getIdentifier`
- `net.minecraft.client.searchtree`
    - `IdSearchTree`
        - `resourceLocationSearchTree` -> `identifierSearchTree`
        - `searchResourceLocation` -> `searchIdentifier`
    - `ResourceLocationSearchTree` -> `IdentifierSearchTree`
- `net.minecraft.commands.arguments.ResourceLocationArgument` -> `IdentifierArgument`
- `net.minecraft.network.FriendlyByteBuf#readResourceLocation`, `writeResourceLocation` -> `readIdentifier`, `writeIdentifier`
- `net.minecraft.resources`
    - `ResourceKey#location` -> `identifier`
    - `ResourceLocation` -> `Identifier`
- `net.minecraft.util.ResourceLocationPattern` -> `IdentifierPattern`
- `net.minecraft.util.parsing.packrat.commands.ResourceLocationParseRule` -> `IdentifierParseRule`
- `net.minecraft.world.entity.GlowSquid` -> `.animal.squid.GlowSquid`
- `net.minecraft.world.entity.animal`
    - `AbstractCow` -> `.cow.AbstractCow`
    - `AbstractFish` -> `.fish.AbstractFish`
    - `AbstractGolem` -> `.golem.AbstractGolem`
    - `AbstractSchoolingFish` -> `.fish.AbstractSchoolingFish`
    - `Bee` -> `.bee.Bee`
    - `Cat` -> `.feline.Cat`
    - `CatVariant` -> `.feline.CatVariant`
    - `CatVariants` -> `.feline.CatVariants`
    - `Chicken` -> `.chicken.Chicken`
    - `ChickenVariant` -> `.chicken.ChickenVariant`
    - `ChickenVariants` -> `.chicken.ChickenVariants`
    - `Cod` -> `.fish.Cod`
    - `Cow` -> `.cow.Cow`
    - `CowVariant` -> `.cow.CowVariant`
    - `CowVariants` -> `.cow.CowVariants`
    - `Dolphin` -> `.dolphin.Dolphin`
    - `Fox` -> `.fox.Fox`
    - `HappyGhast` -> `.happyghast.HappyGhast`
    - `HappyGhastAi` -> `.happyghast.HappyGhastAi`
    - `IronGolem` -> `.golem.IronGolem`
    - `MushroomCow` -> `.cow.MushroomCow`
    - `Ocelot` -> `.feline.Ocelot`
    - `Panda` -> `.panda.Panda`
    - `Parrot` -> `.parrot.Parrot`
    - `Pig` -> `.pig.Pig`
    - `PigVariant` -> `.pig.PigVariant`
    - `PigVariants` -> `.pig.PigVariants`
    - `PolarBear` -> `.polarbear.PolarBear`
    - `Pufferfish` -> `.fish.Pufferfish`
    - `Rabbit` -> `.rabbit.Rabbit`
    - `Salmon` -> `.fish.Salmon`
    - `ShoulderRidingEntity` -> `.parrot.ShoulderRidingEntity`
    - `SnowGolem` -> `.golem.SnowGolem`
    - `Squid` -> `.squid.Squid`
    - `TropicalFish` -> `.fish.TropicalFish`
    - `Turtle` -> `.turtle.Turtle`
    - `WaterAnimal` -> `.fish.WaterAnimal`
- `net.minecraft.world.entity.animal.coppergolem.*` -> `.animal.golem.*`
- `net.minecraft.world.entity.animal.horse.*` -> `.animal.equine.*`
- `net.minecraft.world.entity.boss.EnderDragonPart` -> `.enderdragon.EnderDragonPart`
- `net.minecraft.world.entity.decoration`
    - `Painting` -> `.painting.Painting`
    - `PaintingVariant` -> `.painting.PaintingVariant`
    - `PaintingVariants` -> `.painting.PaintingVariants`
- `net.minecraft.world.entity.monster`
    - `AbstractIllager` -> `.illager.AbstractIllager`
    - `AbstractSkeleton` -> `.skeleton.AbstractSkeleton`
    - `Bogged` -> `.skeleton.Bogged`
    - `CaveSpider` -> `.spider.CaveSpider`
    - `Drowned` -> `.zombie.Drowned`
    - `Evoker` -> `.illager.Evoker`
    - `Husk` -> `.zombie.Husk`
    - `Illusioner` -> `.illager.Illusioner`
    - `Parched` -> `.skeleton.Parched`
    - `Pillager` -> `.illager.Pillager`
    - `Skeleton` -> `.skeleton.Skeleton`
    - `SpellcasterIllager` -> `.illager.SpellcasterIllager`
    - `Spider` -> `.spider.Spider`
    - `Stray` -> `.skeleton.Stray`
    - `Vindicator` -> `.illager.Vindicator`
    - `WitherSkeleton` -> `.skeleton.WitherSkeleton`
    - `Zombie` -> `.zombie.Zombie`
    - `ZombieVillager` -> `.zombie.ZombieVillager`
    - `ZombifiedPiglin` -> `.zombie.ZombifiedPiglin`
- `net.minecraft.world.entity.npc`
    - `AbstractVillager` -> `.villager.AbstractVillager`
    - `Villager` -> `.villager.Villager`
    - `VillagerData` -> `.villager.VillagerData`
    - `VillagerDataHolder` -> `.villager.VillagerDataHolder`
    - `VillagerProfession` -> `.villager.VillagerProfession`
    - `VillagerTrades` -> `.villager.VillagerTrades`
    - `VillagerType` -> `.villager.VillagerType`
    - `WanderingTrader` -> `.wanderingtrader.WanderingTrader`
    - `WanderingTraderSpawner` -> `.wanderingtrader.WanderingTraderSpawner`
- `net.minecraft.world.entity.projectile`
    - `AbstractArrow` -> `.arrow.AbstractArrow`
    - `AbstractHurtingProjectile` -> `.hurtingprojectile.AbstractHurtingProjectile`
    - `AbstractThrownPotion` -> `.throwableitemprojectile.AbstractThrownPotion`
    - `Arrow` -> `.arrow.Arrow`
    - `DragonFireball` -> `.hurtingprojectile.DragonFireball`
    - `Fireball` -> `.hurtingprojectile.Fireball`
    - `LargeFireball` -> `.hurtingprojectile.LargeFireball`
    - `SmallFireball` -> `.hurtingprojectile.SmallFireball`
    - `Snowball` -> `.throwableitemprojectile.Snowball`
    - `SpectralArrow` -> `.arrow.SpectralArrow`
    - `ThrowableItemProjectile` -> `.throwableitemprojectile.ThrowableItemProjectile`
    - `ThrownEgg` -> `.throwableitemprojectile.ThrownEgg`
    - `ThrownEnderpearl` -> `.throwableitemprojectile.ThrownEnderpearl`
    - `ThrownExperienceBottle` -> `.throwableitemprojectile.ThrownExperienceBottle`
    - `ThrownLingeringPotion` -> `.throwableitemprojectile.ThrownLingeringPotion`
    - `ThrownSplashPotion` -> `.throwableitemprojectile.ThrownSplashPotion`
    - `ThrownTrident` -> `.arrow.ThrownTrident`
    - `WitherSkull` -> `.hurtingprojectile.WitherSkull`
- `net.minecraft.world.entity.projectile.windcharge.*` -> `.projectile.hurtingprojectile.windcharge.*`
- `net.minecraft.world.entity.vehicle`
    - `AbstractBoat` -> `.boat.AbstractBoat`
    - `AbstractChestBoat` -> `.boat.AbstractChestBoat`
    - `AbstractMinecart` -> `.minecart.AbstractMinecart`
    - `AbstractMinecartContainer` -> `.minecart.AbstractMinecartContainer`
    - `Boat` -> `.boat.Boat`
    - `ChestBoat` -> `.boat.ChestBoat`
    - `ChestRaft` -> `.boat.ChestRaft`
    - `Minecart` -> `.minecart.Minecart`
    - `MinecartBehavior` -> `.minecart.MinecartBehavior`
    - `MinecartChest` -> `.minecart.MinecartChest`
    - `MinecartCommandBlock` -> `.minecart.MinecartCommandBlock`
    - `MinecartFurnace` -> `.minecart.MinecartFurnace`
    - `MinecartHopper` -> `.minecart.MinecartHopper`
    - `MinecartSpawner` -> `.minecart.MinecartSpawner`
    - `MinecartTNT` -> `.minecart.MinecartTNT`
    - `NewMinecartBehavior` -> `.minecart.NewMinecartBehavior`
    - `OldMinecartBehavior` -> `.minecart.OldMinecartBehavior`
    - `Raft` -> `.boat.Raft`
- `net.minecraft.world.level.gamerules.GameRule#getResourceLocation` -> `getIdentifier`

## Oh Hey, Another Rendering Rewrite

More of the rendering pipeline has been rewritten, with the majority focused on samplers, `RenderType` creation, and mipmaps.

### The Separation of Samplers

Blaze3d has separated setting the `AddressMode`s and `FilterMode`s when reading texture data into `GpuSampler`. As the name implies, a `GpuSampler` defines how to sample data from a buffer, such as a texture. `GpuSampler` contains four methods: `getAddressModeU` / `getAddressModeV` for determining how the sampler should behave when reading the UV positions (either repeat or clamp), `getMinFilter` / `getMagFilter` for determining how to minify or magnify the texture respectively (either nearest neighbor or linear), `getMaxAnisotropy` for the largest anisotropic filtering level that can be used, and `getMaxLod` for the maximum level-of-detail on a texture.

Samplers can be created via `GpuDevice#createSampler`, but that is not necessary unless you want to specify a different anisotropic filtering level greater than `1`, or a maximum level-of-detail that is not `0` or `1000`. If the default, as there are only 32 possible combinations, vanilla creates all `GpuSampler`s and stores them in a cache, accessible via `RenderSystem#getSamplerCache`, followed by `SamplerCache#getSampler`:

```java
// Raw call
GpuSampler sampler = RenderSystem.getDevice().createSampler(
    // U address mode
    AddressMode.CLAMP_TO_EDGE,
    // V address mode
    AddressMode.CLAMP_TO_EDGE,
    // Minification filter
    FilterMode.LINEAR,
    // Magnification filter
    FilterMode.NEAREST,
    // The maximum anisotropic filtering level
    // Vanilla uses either 1, 2, 4, or 8 for level rendering
    4f,
    // The maximum level of detail for a texture
    // Vanilla either uses an 0 for the default,
    // or an empty optional for moving objects and
    // uploading to an atlas.
    OptionalDouble.of(0.0)
);

// Sampler cache method
GpuSampler sampler = RenderSystem.getSamplerCache().getSampler(
    // U address mode
    AddressMode.CLAMP_TO_EDGE,
    // V address mode
    AddressMode.CLAMP_TO_EDGE,
    // Minification filter
    FilterMode.LINEAR,
    // Magnification filter
    FilterMode.NEAREST,
    // Whether to use 1000 or 0 for the maximum level-of-detail
    true
);
```

To make user of the sampler for a texture, when binding the texture in a render pass (via `RenderPass#bindTexture`), you must now specify the sampler to use in addition to the texture view:

```java
try (RenderPass pass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(...)) {
    // Set other parameters
    pass.bindTexture(
        // The name of the sampler2D uniform, usually in the fragment shader
        "Sampler0",
        // The texture to sample
        ...,
        // The sampler to use
        sampler
    );

    // Draw buffer
}
```

Setting up the post processor has not changed from the user perspective as only clamp to edge address modes may be selected.

### The `RenderType` Shuffle

Creating a `RenderType` has been reworked to some degree. While most of the features from the previous implementation still exist, they have been changed to match the new rendering system where direct OpenGL is abstracted away and only accessed through their defined pipelines and `RenderSystem`.

#### Existing Types

Existing types have been moved from `RenderType` to `RenderTypes` (e.g., `RenderType#solid` -> `RenderTypes#solid`).

#### Custom Types

Originally, to create a `RenderType`, you would construct a `$CompositeState` using `RenderStateShard`s. Each `RenderStateShard` would define how the pass should be setup and teardown when building some mesh, whether that was setting textures, the render target, model transforms, etc. Then, the `$CompositeState` would be built for use in whatever rendering application was needed.

The new system splits the render definition in two: the `RenderSetup`, and our `RenderType`. The `RenderSetup`, as the name implies, sets up the renderer to be used when drawing to a texture. Teardown is completely removed as it is either handled directly when drawing the `RenderType` or uses newly constructed states that can just be thrown away. `RenderType`, on the other hand, is simply a named `RenderSetup`. It only handles drawing the mesh data and making some fields of the setup public for use in other buffer implementations. Multiple types can have the same `RenderSetup` as a number of existing types dynamically populate the texture used by the sampler and/or the outline of the object.

A `RenderSetup` can be created through its builder via `RenderSetup#Builder`, supply the `RenderPipeline` to use. Once the builder properties are set, the actual setup can be created via `RenderSetup$RenderSetupBuilder#createRenderSetup`:

```java
public static final RenderSetup EXAMPLE_SETUP = RenderSetup.builder(
    // The pipeline to use.
    // This can affect what settings are allowed from the setup.
    RenderPipelines.ITEM_ENTITY_TRANSLUCENT_CULL
)
    // Specifies the texture to bind to the provided sampler.
    // The sampler must be defined by the pipeline via `RenderPipeline$Builder#withSampler`.
    // The texture is represented as an absolute location.
    .withTexture(
        // 'Sampler0' is defined by the pipeline.
        "Sampler0",
        // Points to 'assets/minecraft/entity/wolf/wolf_armor_crackiness_low.png'.
        Identifier.withDefaultNamespace("textures/entity/wolf/wolf_armor_crackiness_low.png"),
        // An optional supplied `GpuSampler` to sample the texture with.
        // The returned value with be cached after first resolution.
        () -> RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST)
    )
    // When set, allows the pipeline to use the light texture.
    // 'Sampler2' must be defined by the pipeline via `RenderPipeline$Builder#withSampler`.
    .useLightmap()
    // When set, allows the pipeline to use the overlay texture.
    // 'Sampler1' must be defined by the pipeline via `RenderPipeline$Builder#withSampler`.
    .useOverlay()
    // When set, uses `RenderTypes#crumbling` to overlay the block destroy stages
    // based on the crumbling progress for an entity model.
    // This is only implemented in `ModelFeatureRenderer`.
    .affectsCrumbling()
    // When set, sorts the quads based on the set `ProjectionType` in
    // `RenderSystem#getProjectionType`.
    // This is only implemented when getting the buffers from `MultiBufferSource$BufferSource`.
    .sortOnUpload()
    // Sets the initial capacity of the used buffer.
    // This is only used when constructing the initial buffers in `RenderBuffers`
    // All custom applications of sources already have some defined buffer with the determined size.
    .bufferSize(786432)
    // An object-wrapped consumer that transforms the model view matrix.
    // Vanilla implementations exist in `LayeringTransform`, applying
    // the transformation through the projection type:
    // - `NO_LAYERING`: Do nothing.
    // - `VIEW_OFFSET_Z_LAYERING`: Offsets the Z by 1 based on its `ProjectionType`
    // - `VIEW_OFFSET_Z_LAYERING_FORWARD`: Offsets the Z by -11 based on its `ProjectionType`
    .setLayeringTransform(
        // We can also construct a new transform
        new LayeringTransform(
            // The name of the transform
            "examplemod:example_layer",
            // The transform should not push or pop to the stack
            // Only translate, scale, or rotate
            stack -> stack.translate(0f, 0.1f, 0f)
        )
    )
    // Sets the output target that this setup should write to,
    // unless overridden by the `RenderSystem#output*Override` textures.
    // This is typically the main target, though it can be other vanilla
    // targets or a custom one if you plan to handle it.
    .setOutputTarget(OutputTarget.MAIN_TARGET)
    // An object-wrapped supplier that provides the texture matrix.
    // This is typically used to modify the texture UV coordinates
    // in the vertex shader before sampling in the fragment shader.
    // Vanilla only uses this for the glint effect and breeze/energy:
    // - `DEFAULT_TEXTURING`: Do nothing.
    // - `GLINT_TEXTURING`: Translates based on the glint speed, rotates pi/18, and scales by 8.
    // - `ENTITY_GLINT_TEXTURING`: Translates based on the glint speed, rotates pi/18, and scales by 0.5.
    // - `ARMOR_ENTITY_GLINT_TEXTURING`: Translates based on the glint speed, rotates pi/18, and scales by 0.16.
    // - `$OffsetTextureTransform`: Translates the texture by the provided XY coordinates.
    .setTextureTransform(
        // We can also construct a new transform
        new TextureTransform(
            // The name of the transform
            "examplemod:example_texture",
            // The transform to apply to the texture
            () -> new Matrix4f().translation(0f, 1f, 0f).scale(1.5f)
        )
    )
    // Sets how an outline of the mesh should be handled:
    // - `NONE`: Do nothing.
    // - `IS_OUTLINE`: This is an outline and should write to the outline buffer source.
    // - `AFFECTS_OUTLINE`: This defines the shape of the outline and should use `RenderTypes#OUTLINE` to draw it.
    // Checked when writing to the outline buffer source or,
    // if the outline color for a feature is not 0
    .setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
    // Builds the setup for use in a render type.
    .createRenderSetup();
```

Then, the `RenderType` can be created via `create`.

```java
public static final RenderType EXAMPLE_TYPE = RenderType.create(
    // The name of the type for debugging
    "examplemod:example_type",
    // The render setup to use
    EXAMPLE_SETUP
);
```

`MeshData` can be written to the output target using `RenderType#draw`.

### Mipmap Strategy Metadata

A texture's `mcmeta` can now specify the `mipmap_strategy` to use in the `textures` section. There are four available strategies, with `auto` defaulting to `mean` if there is no transparency, an `cutout` when there is transparency.

| Strategy        | Description                                                                                                                    |
|:---------------:|:-------------------------------------------------------------------------------------------------------------------------------|
| `mean`          | The default that averages the color between four pixels for the current mipmap level.                                          |
| `cutout`        | `mean`, except that all levels are generated from the original texture, with alpha snapped to 0 or 1 using a threshold of 0.2. |
| `strict_cutout` | `cutout`, except that it sets the alpha snaps using a threshold of `0.6`.                                                      |
| `dark_cutout`   | `mean`, except that the surrounding pixels are only included in the average if their alpha is not `0`.                         |

```json5
// In `assets/examplemod/textures/block/example/example_block.png.mcmeta
{
    "texture": {
        // Uses the chosen strategy
        "mipmap_strategy": "cutout",
        // Determines how much the cutoff should be biased
        // when determining whether a pixel is either fully
        // opaque or transparent.
        // Larger numbers means higher alpha cutoff while
        // lower values use a lower alpha cutoff.
        "alpha_cutoff_bias": 0.2
    }
}
```

### Block and Terrain Split

`RenderPipeline`s that were used by both a standalone block and the terrain has been split into separate pipelines: one with prefix `_BLOCK` and `_TERRAIN`, respectively. This includes the solid, cutout, translucent, and tripwire pipelines. No block variant exists for the translucent pipeline.

### Item Atlases

The block atlas no longer contains textures specifically for items. Those have been moved to their own atlas named `minecraft:items`, with the id stored at `AtlasIds#ITEMS`.

If a given `Material` can use both block and item textures, then it should be supplied with the `ModelManager#BLOCK_OR_ITEM` special case.

- `com.mojang.blaze3d.buffers`
    - `GpuBuffer`, `size` now uses a `long` for the size
        - `slice` now uses `long`s for the length and offset
    - `GpuBufferSlice` now uses `long`s for the length and offset
- `com.mojang.blaze3d.opengl`
    - `BufferStorage`
        - `createBuffer` now uses in a `long` for the size
        - `mapBuffer` now uses `long`s for the length and offset
    - `DirectStateAccess`
        - `bufferSubData` now uses in a `long` for the offset
        - `mapBufferRange`, `flushMappedBufferRange`, `copyBufferSubData` now use `long`s for the length and offset
    - `GlBuffer` now uses a `long` for the size
    - `GlDevice` now takes in a `ShaderSource` instead of a `BiFunction`
        - `getOrCompileShader` now takes in a `ShaderSource` instead of a `BiFunction`
    - `GlRenderPass`
        - `samplers` now is a hash map of strings to `GlRenderPass$TextureViewAndSampler`s
        - `$TextureViewAndSampler` - A record that defines a sampler with its sampled texture.
    - `GlSampler` - The OpenGL implementation of a gpu sampler.
    - `GlStateManager`
        - `_glBufferSubData` now uses in a `long` for the offset
        - `_glMapBufferRange` now uses `long`s for the length and offset
    - `GlTexture#modesDirty`, `flushModeChanges` are removed
    - `GlTextureView#getFbo` - Gets the framebuffer object of a texture, using the cache if present.
- `com.mojang.blaze3d.pipeline.RenderTarget#filterMode`, `setFilterMode` are removed
- `com.mojang.blaze3d.platform.TextureUtil`
    - `solidify` - Modifies the texture by packing and unpacking the pixels to better help with non-darkened interiors within mipmaps.
    - `fillEmptyAreasWithDarkColor` - Sets empty pixels to an empty pixel whose RGB value is the darkest color in the image.
- `com.mojang.blaze3d.shaders.ShaderSource` - A functional interface that gets the shader source from its id and type as a string.
- `com.mojang.blaze3d.systems`
    - `CommandEncoder#copyTextureToBuffer` now uses a `long` for the offset
    - `GpuDevice`
        - `createSampler` - Creates a sampler for some source to destination with the desired address and filter modes.
        - `precompilePipeline` now takes in a `ShaderSource` instead of a `BiFunction`
        - `getMaxSupportedAnisotropy` - The maximum anisotropic filtering level supported by the hardware.
        - `createBuffer` now uses in a `long` for the size
    - `RenderPass#bindTexture` now takes in a `GpuSampler`
    - `RenderSystem`now uses in a `long` for the size
        - `samplerCache` - Returns a cache of samples containing all possible combinations.
        - `TEXTURE_COUNT` is removed
        - `setupOverlayColor`, `teardownOverlayColor` are removed
        - `setShaderTexture`, `getShaderTexture` are removed
        - `setTextureMatrix`, `resetTextureMatrix`, `getTextureMatrix` are removed
        - `lineWidth` -> `VertexConsumer#setLineWidth`
        - `getShaderLineWidth` -> `Window#getAppropriateLineWidth`
        - `initRenderer` now takes in a `ShaderSource` instead of a `BiFunction`
    - `SamplerCache` - A cache of all possible samplers that may be used by the renderer.
- `com.mojang.blaze3d.textures`
    - `GpuSampler` - A buffer sampler with the specified UV address modes and minification and magnification filters.
    - `GpuTexture`
        - `addressModeU` -> `GpuSampler#getAddressModeU`
        - `addressModeV` -> `GpuSampler#getAddressModeV`
        - `minFilter` -> `GpuSampler#getMinFilter`
        - `magFilter` -> `GpuSampler#getMagFilter`
        - `setAddressMode`, `setTextureFilter` have been replaced by `SamplerCache#getSampler`, not one-to-one
        - `useMipmaps`, `setUseMipmaps` are removed
- `net.minecraft.client`
    - `Options#textureFiltering` - The chosen texture sampling method when viewed at an angle or from a distance.
    - `TextureFilteringMethod` - The sampling method when viewing a texture at an angle or from a distance.
- `net.minecraft.client.gui.render.TextureSetup` now takes in the `GpuSampler`s for each of the textures
    - This also includes the static constructors
- `net.minecraft.client.particle.SingleQuadParticle$Layer#ITEMS` - A layer for particles with item textures.
- `net.minecraft.client.renderer`
    - `FaceInfo`
        - `$Constants` -> `$Extent`
        - `$VertexInfo` is now a record
    - `ItemBlockRenderTypes#getRenderType(ItemStack)`
    - `LightTexture#turnOffLightLayer`, `turnOnLightLayer` are removed
    - `LevelRenderer#resetSampler` - Resets the chunk layer sampler.
    - `PostPass`
        - `$Input#bilinear` - Whether to use a bilinear filter.
        - `$TextureInput` now takes in a `boolean` representing whether to use a bilinear filter
    - `RenderPipelines`
        - `SOLID` -> `SOLID_BLOCK`, `SOLID_TERRAIN`
        - `CUTOUT` -> `CUTOUT_BLOCK`, `CUTOUT_TERRAIN`
        - `CUTOUT_MIPPED` is removed
        - `TRANSLUCENT` -> `TRANSLUCENT_TERRAIN`
        - `TRIPWIRE` -> `TRIPWIRE_BLOCK`, `TRIPWIRE_TERRAIN`
        - `ANIMATE_SPRITE_SNIPPET`, `ANIMATE_SPRITE_BLIT`, `ANIMATE_SPRITE_INTERPOLATION` - Pipelines for animated sprites.
    - `RenderStateShard` has been replaced with `RenderSetup`, not one-to-one
        - `$LightmapStateShard` -> `RenderSetup#useLightmap`
        - `$OverlayStateShard` -> `RenderSetup#useOverlay`
        - `$MultiTextureStateShard`, `$TextureStateShard` -> `RenderSetup#textures`
        - `$LayeringStateShard` -> `RenderSetup#layeringTransform`, `LayeringTransform`
        - `$LineStateShard` -> `VertexConsumer#setLineWidth`
        - `$OutputStateShard` -> `RenderSetup#outputTarget`, `OutputTarget`
        - `$TexturingStateShard`, `$OffsetTexturingStateShard` -> `RenderSetup#textureTransform`, `TextureTransform`
    - `RenderType` has been split into two separate concepts, not one-to-one
        - All of the stored `RenderType`s have been moved to `RenderTypes`
        - The actual class usage has moved to `.rendertype.RenderType`, where it does the work of `$CompositeRenderType`
    - `Sheets#translucentBlockItemSheet` - A render type for translucent block items.
- `net.minecraft.client.renderer.block`
    - `BlockRenderDispatcher` no longer takes in the supplied `SpecialBlockModelRenderer`
    - `LiquidBlockRenderer` now takes in a `MaterialSet`
        - `setupSprites` has been moved into the `LiquidBlockRenderer` constructor
- `net.minecraft.client.renderer.block.model`
    - `BakedQuad`
        - `vertices` -> `position*`, `packedUV*`, not one-to-one
        - `position` - Gets the position vector given the index.
        - `packedUV` - Gets the packed UV given the index.
    - `BlockElementRotation` now takes in a `Vector3fc` for the origin and a `Matrix4fc` transform
        - The constructor also takes in a `$RotationValue` instead of a `Direction$Axis` and angle `float`
        - `$EulerXYZRotation` - A XYZ rotation in degrees.
        - `$RotationValue` - An interface that defines the rotation transformation.
        - `$SingleAxisRotation` - A rotation in degrees around a single axis.
    - `FaceBakery`
        - `VERTEX_COUNT` -> `BakedQuad#VERTEX_COUNT`
        - `VERTEX_INT_SIZE`, `COLOR_INDEX`, `UV_INDEX` are removed
        - `bakeQuad` now takes in a `ModelBaker$PartCache`
        - `extractPositions` is removed
    - `SimpleModelWrapper#bake` now returns a `BlockModelPart`
    - `SimpleUnbakedGeometry#bake` now takes in a `ModelBaker` instead of a `SpriteGetter`
    - `TextureSlots$parseTextureMap` no longer takes in an `Identifier`
    - `Variant`
        - `withZRot` - Rotates the model state around the Z axis.
        - `$SimpleModelState` now takes in a Z `Quadrant`
            - `withZ` - Sets the Z quadrant of the model state.
    - `VariantMutator#Z_ROT` - Rotates a model around the Z axis.
- `net.minecraft.client.renderer.chunk`
    - `ChunkSectionLayer` no longer takes in whether to use mipmaps
        - `CUTOUT_MIPPED` is removed
        - `texture` is removed
    - `ChunkSectionsToRender` now takes in the `GpuTextureView`
        - `dynamicTransforms` -> `chunkSectionInfos`
        - `renderGroup` now takes in the `GpuSampler`
- `net.minecraft.client.renderer.item`
    - `BlockModelWrapper` constructor is now package-private
        - `computeExtents` now returns an array of `Vector3fc`s
    - `ItemStackRenderState$LayerRenderState`
        - `NO_EXTENTS_SUPPLIER` is now a supplied array of `Vector3fc`s
        - `setExtents` now takes in a supplied array of `Vector3fc`s
- `net.minecraft.client.renderer.rendertype.RenderTypes`
    - `MOVING_BLOCK_SAMPLER` - A sampler for blocks that are in motion.
    - `solid` -> `solidMovingBlock`
    - `cutout` -> `cutoutMovingBlock`
    - `tripwire` -> `tripwireMovingBlock`
- `net.minecraft.client.renderer.texture`
    - `AbstractTexture#setUseMipmaps` is removed
    - `MipmapGenerator#generateMipLevels` now takes in the name of the texture, a `MipmapStrategy` to determine how a specific texture should be mip mapped, and a `float` for the alpha cutoff bias
    - `MipmapStrategy` - A enum defines the strategies used when constructing a mipmap for a texture.
    - `OverlayTexture#setupOverlayColor`, `teardownOverlayColor` replaced by `getTextureView`, not one-to-one
    - `SpriteContents` now takes in an optional `TextureMetadataSection` to determine the sprite's metadata
        - `UBO_SIZE` - The uniform buffer object size of the sprite contents.
        - `createTicker` -> `createAnimationState`, not one-to-one
        - `uploadFirstFrame` no longer takes in the texture `int`s, instead a mip level `int`
        - `$AnimatedTexture#createTicker`, `uploadFirstFrame` -> `createAnimationState`, not one-to-one
        - `$Ticker` -> `$AnimationState`, not one-to-one
            - `tickAndUpload` -> `tick`, `getDrawUbo`, `needsToDraw`, `drawToAtlas`; not one-to-one
    - `SpriteTicker` interface is removed
    - `Stitcher` now takes in the anisotropic filtering level
        - `$Holder(T, int)` is removed
        - `$Region#walk` now takes in a padding `int`
        - `$SpriteLoader` no longer takes in the minimum width/height
            - `load` now takes in a padding `int`
    - `TextureAtlas` now implements `TickableTexture` instead of `Tickable`
    - `TextureAtlasSprite` now implements `AutoCloseable`
        - The constructor takes in a padding `int`
        - `createTicker` -> `createAnimationState`, not one-to-one
        - `getUOffset`, `getVOffset`, `uvShrinkRatio` are removed
        - `uploadFirstFrame` now takes in the mip level `int`
        - `uploadSpriteUbo` - Uploads the atlas sprite to the to the buffer.
        - `$Ticker` interface is removed
    - `TextureManager` no longer implements `Tickable`
    - `Tickable` -> `TickableTexture`
- `net.minecraft.client.renderer.texture.atlas`
    - `SpriteSource$SpriteSupplier` -> `$DiscardableLoader`
        - `Function` superinterface is now represented as `$Loader`
            - `apply` -> `get`
    - `SpriteSourceList#list` now returns a list of `SpriteSource$Loader`s
- `net.minecraft.client.resources.metadata.texture.TextureMetadataSection` now takes in a `MipmapStrategy` to determine how a specific texture should be mip mapped, and a `float` for the alpha cutoff bias
- `net.minecraft.client.resources.model`
    - `ModelBaker`
        - `missingBlockModelPart` - The missing block model.
        - `parts` - A cache of previously constructed vectors.
        - `$PartCache` - A cache that interns previously constructed vertices in a quad.
    - `ModelBakery`
        - `*_STILL` - Fluid still texture locations.
        - `$MissingModels` now takes in a `BlockModelPart` missing model
    - `ModelManager`
        - `BLOCK_OR_ITEM` - A special case that causes the model manager to check both the item and block atlas.
        - `specialBlockModelRenderer` now returns the raw renderer instead of a supplied value.
- `net.minecraft.data.AtlasIds#ITEMS` - The item atlas identifier.
- `net.minecraft.world.level.block.LeavesBlock#setCutoutLeaves` - Sets whether the leaves is using cutout rendering.

## Gizmos

Gizmos are the newest iteration in the submission and rendering decoupling, this time for debug renderers. However, the underlying structure to submit gizmos for rendering is much more complex since debug renderers can submit objects at nearly any point during the client's process.

### What is a Gizmo?

A `Gizmo` is basically an object that submits some object primitives -- specifically points, lines, triangle fans, quads, and text -- for rendering. Each gizmo essentially strings together these primitives via `emit` into its desired shape. During the render process, these make use of the `RenderPipelines#DEBUG_*` pipelines to render their primitives to the screen. Creating a new gizmo is as simple as extending the interface:

```java
// Store some parameters like a render state to submit the element primitives
public record ExampleGizmo(Vec3 start, Vec3 end) implements Gizmo {

    @Override
    public void emit(GizmoPrimitives gizmos, float alphaMultiplier) {
        // Submit any elements here
        gizmos.addLine(this.start, this.end, ARGB.multiplyAlpha(0, alphaMultiplier), 3f);
    }
}
```

Actually submitting the elements happens through `Gizmos#addGizmo`. This stores the gizmo to be emitted and drawn to the screen, as long as it is called during `Minecraft#tick` or any rendering on the client -- which is how the debug renderers emit theirs, `IntegratedServer#tickServer` in a singleplayer world, or packet processing on either side. All of the methods in `Gizmos` call `addGizmo` internally, which is why the method is typically absent outside its class:

```java
// Somewhere in GameRenderer#render

Gizmos.addGizmo(new ExampleGizmo(Vec3.ZERO, Vec3.X_AXIS));

// Calls addGizmo internally
Gizmos.point(Vec3.ZERO, 0, 5f);
```

Calling `addGizmo` returns a `GizmoProperties`, which sets some properties for when the element is drawn, assuming that `GizmoCollector` is not a `NOOP`. `GizmoProperties` provides three methods:

| Method             | Description                                                                     |
|:------------------:|:--------------------------------------------------------------------------------|
| `setAlwaysOnTop`   | Clears the depth texture before rendering.                                      |
| `persistForMillis` | Keeps the gizmo on screen for the specified amount of time before disappearing. |
| `fadeOut`          | Fades the disappearing when persisted for a certain amount of time.             |

### Putting it Together

With that, how can you submit gizmos for rendering pretty much anywhere in the client pipeline? Well, this all starts from `Gizmos#withCollector` and `SimpleGizmoCollector`.

`SimpleGizmoCollector` is basically just a list that holds the collected gizmos to render. During the rendering process, `SimpleGizmoCollector#drainGizmos` is called, copying the gizmos over to a separate list for the renderer to `Gizmo#emit`, before drawing to the screen through the familiar frame pass and buffer source. `drainGizmos` then clears the internal list based on `GizmoProperties#persistForMillis`, or immediately if not specified, for the next frame.

To actually collect these elements, there is a rather convoluted process. `Minecraft`, `LevelRenderer`, and `IntegratedServer` have their own `SimpleGizmoCollector`. This is done by setting the collector using `Gizmo#withCollector`, which returns a `Gizmos$TemporaryCollection`. The collection is `AutoCloseable` that, when closed, releases the held collector on the local thread. So, the collectors are wrapped in a try-with-resources to facilitate the submission during these periods. Then, during the debug pass, the per tick gizmos are merged with the per frame gizmos and drawn to the screen via `addTemporaryGizmos`, quite literally at the last moment in `LevelRenderer#renderLevel`. The `IntegratedServer` gizmos in a singleplayer world are stored in a volatile field, allowing it to be accessed from the client thread.

- `net.minecraft.client.Minecraft`
    - `collectPerTickGizmos` - Returns a collection of all gizmos to emit.
    - `getPerTickGizmos` - Gets the gizmos to draw to the screen.
- `net.minecraft.client.renderer`
    - `LevelRenderer#collectPerFrameGizmos` - Returns a collection of all gizmos to emit.
    - `OrderedSubmitNodeCollector#submitHitbox` is removed
    - `ShapeRenderer`
        - `renderShape` now takes in a line width `float`
        - `renderLineBox` -> `Gizmos#cuboid`, not one-to-one
        - `addChainedFilledBoxVertices` -> `Gizmos#cuboid`, not one-to-one
        - `renderFace` -> `Gizmos#rect`, not one-to-one
        - `renderVector` -> `Gizmos#line`, not one-to-one
    - `SubmitNodeCollection#getHitboxSubmits` is removed
    - `SubmitNodeStorage$HitboxSubmit` record is removed
- `net.minecraft.client.renderer.debug`
    - `DebugRenderer`
        - `render` -> `emitGizmos`, no longer takes in the `PoseStack`, `BufferSource` or `boolean`, now taking in the partial tick `float`
        - `renderFilledUnitCube` -> `Gizmos#cuboid`, not one-to-one
        - `renderFilledBox` -> `Gizmos#cuboid`, not one-to-one
        - `renderTextOverBlock` -> `Gizmos#billboardTextOverBlock`, not one-to-one
        - `renderTextOverMob` -> `Gizmos#billboardTextOverMob`, not one-to-one
        - `renderFloatingText` -> `Gizmos#billboardText`, not one-to-one
        - `renderVoxelShape` -> `LevelRenderer#renderHitOutline`, now private, not one-to-one
        - `SimpleDebugRenderer$render` -> `emitGizmos`, no longer takes in the `PoseStack`, `BufferSource` or `boolean`, now taking in the partial tick `float`
    - `GameTestBlockHighlightRenderer`
        - `render` -> `emitGizmos`, taking in no parameters
        - `renderMarker` no longer takes in the `PoseStack` or buffer source
        - `$Marker#get*` are removed
    - `LightDebugRenderer` now takes in two flags determining whether to show the block or sky light
    - `PathfindingRenderer#renderPath`, `renderPathLine` no longer take in the `PoseStack` or buffer source
- `net.minecraft.client.renderer.entity.EntityRenderer#extractAdditionalHitboxes` is removed
- `net.minecraft.client.renderer.entity.state`
    - `EntityRenderState#hitboxesRenderState`, `serverHitboxesRenderState` are removed
    - `HitboxesRenderState` class is removed
    - `ServerHitboxesRenderState` class is removed
- `net.minecraft.client.renderer.feature.HitboxFeatureRenderer` -> `EntityHitboxDebugRenderer`, not one-to-one
- `net.minecraft.client.renderer.gizmos.DrawableGizmoPrimitives` - A storage and renderer for primitive shapes. or gizmos.
- `net.minecraft.client.renderer.texture`
    - `AbstractTexture`
        - `sampler`, `getSampler` - Returns the `GpuSampler` used by the texture.
        - `setClamp` -> `GpuSampler#getAddressMode*`, not one-to-one
        - `setFilter` -> `GpuSampler#get*Filter`, not one-to-one
    - `ReloadableTexture` no longer takes in the address mode and filter `boolean`s
- `net.minecraft.client.server.IntegratedServer#getPerTickGizmos` - Gets the gizmos to draw to the screen for the current tick.
- `net.minecraft.gizmos`
    - `ArrowGizmo` - A gizmo that draws an arrow.
    - `CircleGizmo` - A gizmo that draws an approximate circle with twenty vertices.
    - `CuboidGizmo` - A gizmo that draws a rectangular prism.
    - `Gizmo` - An object that can emit simple shape primitives to draw.
    - `GizmoCollector` - An add-only collector.
    - `GizmoPrimitives` - Shape primitives that can be drawn.
    - `GizmoProperties` - Properties that apply to how the gizmo should be drawn.
    - `Gizmos` - A collection of static methods for creating gizmos and collecting them.
    - `GizmoStyle` - A property holder to define how a gizmo should be drawn. These are used by the gizmos themselves and not the actual primitives.
    - `LineGizmo` - A gizmo that draws a line.
    - `PointGizmo` - A gizmo that draws a point.
    - `RectGizmo` - A gizmo that draws a rectangle.
    - `SimpleGizmoCollector` - A collector implementation for adding gizmos before sending them off for rendering.
    - `TextGizmo` - A gizmo that draws text.
- `net.minecraft.server.MinecraftServer#processPacketsAndTick` - Handles server ticking and packet processing.

## Permission Overhaul

The permission level integer has been expanded into an entirely new system that is both simple yet complicated. There are three main parts: `Permission`s, `PermissionSet`s, and `PermissionCheck`s.

### Permissions

`Permission`s are functionally data objects that define some sort of state. Vanilla provides two types of permissions: `Permission$Atom`, which just is a unique unit object; and `Permission$HasCommandLevel`, which holds the desired `PermissionLevel` for a command. Both of the data objects are registered as map codecs for dumping the command report.

```java
// Attempts to query moderator level permissions.
public static final Permission COMMANDS_MODERATOR = new Permission.HasCommandLevel(PermissionLevel.MODERATORS);
```

A custom permission can be created through some class or record that extends `Permission` with an associated `MapCodec` registered to its static registry:

```java
// This does not check whether the user has the given permission
// It is literally just holding data representing the permission state
public record HasExamplePermission(int state) implements Permission {
    public static final MapCodec<HasExamplePermission> MAP_CODEC = Codec.INT.fieldOf("state")
        .xmap(HasExamplePermission::new, HasExamplePermission::state);
    
    @Override
    public MapCodec<HasExamplePermission> codec() {
        return HasExamplePermission.MAP_CODEC;
    }
}

// In some registration handler
Registry.register(
    BuiltInRegistries.PERMISSION_TYPE
    Identifier.withNamespaceAndPath("examplemod", "has_example_permission"),
    HasExamplePermission.MAP_CODEC
);

// Storing the permission for use
public static final Permission HAS_STATE_ONE = new HasExamplePermission(1);
```

### Permission Sets

If `Permission`s define the queryable state, then `PermissionSet`s are the permissions the user actually has. `PermissionSet` is a functional interface that checks whether the user has the desired permission state (via `hasPermission`). Vanilla uses a `LevelBasedPermissionSet` for checking whether the permission queried matches the current `PermissionLevel`. As a permission set is usually checked against some swathe of permissions, multiple permission sets can be combined into one via `PermissionSet#union`. It functionally performs an OR operation, meaning that if a set does not check a permission, it should default to `false`.

```java
public interface ExamplePermissionSet extends PermissionSet {

    // Keep track of the user's state for our permissions
    int state();

    @Override
    default boolean hasPermission(Permission permission) {
        // Check our permission
        if (permission instanceof HasExamplePermission example) {
            return this.state() >= example.state();
        }

        // Otherwise ignore
        return false;
    }
}

// Storing a permission set
// Could also be implemented or stored on the desired target
public static ExamplePermissionSet STATE_ONE = () -> 1;

// Check whether the permission set has the desired permission
STATE_ONE.hasPermission(HAS_STATE_ONE);
```

Currently, there is no simple method to store custom permission sets on the desired user. `CommandSourceStack` does have a method to union other permission sets via `withMaximumPermission`, but that would need to be handled within the associated `createCommandSourceStack` method. The normal `LevelBasedPermissionSet` can typically be queried through a `permissions` method, though there is no common interface across objects, even though `PermissionSetSupplier` seems to exist for that purpose.

### Permission Checks

Now, a `PermissionSet` never directly checks a `Permission` within the codebase. That would require having the object accessible at all times. Instead, a `PermissionCheck` object is created, which takes in the `PermissionSet` and `check`s whether the user has the desired data to continue execution. Vanilla provides two types of checks: `$AlwaysPass`, which means it will always return true; and `$Require`, which requires the set to have the desired `Permission`. The checks also have a map codec for dumping the command report.

```java
// Requires the permission set has acccess to moderator commands
public static final PermissionCheck LEVEL_MODERATORS = new PermissionCheck.Require(COMMANDS_MODERATOR);
```

Custom permission checks can be created through some class or record that implements `check` and registers the map codec to its static registry:

```java
public static record AnyOf(List<Permission> permissions) implements PermissionCheck {
    public static final MapCodec<AnyOf> MAP_CODEC = Permission.CODEC.listOf().fieldOf("permissions")
        .xmap(AnyOf::new, AnyOf::permissions);

    @Override
    public boolean check(PermissionSet permissionSet) {
        return this.permissions.stream().filter(perm -> permissionSet.hasPermission(perm)).findAny().isPresent();
    }

    @Override
    public MapCodec<AnyOf> codec() {
        return MAP_CODEC;
    }
}

// In some registration handler
Registry.register(
    BuiltInRegistries.PERMISSION_CHECK_TYPE
    Identifier.withNamespaceAndPath("examplemod", "any_of"),
    AnyOf.MAP_CODEC
);

// Storing the check for use in a command
public static final PermissionCheck CHECK_STATE_ONE = new AnyOf(List.of(
    HAS_STATE_ONE,
    Permissions.COMMANDS_GAMEMASTER
));

// For some command
Commands.literal("example").requires(Commands.hasPermission(CHECK_STATE_ONE));
```

- `net.minecraft.client.multiplayer.ClientSuggestionProvider` no longer implements `PermissionSource`
    - The constructor now takes in a `PermissionSet` instead of a `boolean`
    - `allowsRestrictedCommands` -> `ClientPacketListener#ALLOW_RESTRICTED_COMMANDS`, now private, not one-to-one
- `net.minecraft.client.player.LocalPlayer#setPermissionLevel` -> `setPermissions`, not one-to-one
- `net.minecraft.commands`
    - `Commands`
        - `LEVEL_*` are now `PermissionCheck`s instead of `int`s
        - `hasPermission` now takes in a `PermissionCheck` instead of an `int`, and returns a `PermissionProviderCheck` instead of a `PermissionCheck`
        - `createCompilationContext` - Creates a source stack with the given permissions.
    - `CommandSourceStack` no longer implements `PermissionSource`
        - The constructor now takes in a `PermissionSet` instead of an `int`
        - The `protected` constructor is now `private`
        - `withPermission` now takes in a `PermissionSet` instead of an `int`
        - `withMaximumPermission` now takes in a `PermissionSet` instead of an `int`
    - `ExecutionCommandSource` now extends `PermissionSetSupplier` instead of `PermissionSource`
    - `PermissionSource` interface is removed
    - `SharedSuggestionProvider` now extends `PermissionSetSupplier`
- `net.minecraft.commands.arguments.selector.EntitySelectorParser#allowSelectors` now has an overload that takes in a `PermissionSetSupplier`
- `net.minecraft.core.registries`
    - `BuiltInRegistries#PERMISSION_TYPE`, `Registries#PERMISSION_TYPE` - An object that defines some data requirement.
    - `BuiltInRegistries#PERMISSION_CHECK_TYPE`, `Registries#PERMISSION_CHECK_TYPE` - A predicate that checks whether the set has the required data.
- `net.minecraft.server`
    - `MinecraftServer`
        - `operatorUserPermissionLevel` -> `operatorUserPermissions`, not one-to-one
        - `getFunctionCompilationLevel` -> `getFunctionCompilationPermissions`, not one-to-one
        - `getProfilePermissions` now returns a `LevelBasedPermissionSet`
    - `ReloadableServerResources#loadResources` now takes in a `PermissionSet` instead of an `int`
    - `ServerFunctionLibrary` now takes in a `PermissionSet` instead of an `int`
    - `WorldLoader$InitConfig` now takes in a `PermissionSet` instead of an `int`
- `net.minecraft.server.commands.PermissionCheck` -> `.server.permissions.PermissionCheck`, not one-to-one
- `net.minecraft.server.dedicated.DedicatedServerProperties`
    - `opPermissionLevel` -> `opPermissions`, not one-to-one
    - `functionPermissionLevel` -> `functionPermissions`, not one-to-one
    - `deserializePermissions`, `serializePermission` - Reads and writes the chosen level permission set. 
- `net.minecraft.server.jsonrpc.internalapi`
    - `MinecraftOperatorListService#op` now takes in an optional `PermissionLevel` instead of an `int`
    - `MinecraftServerSettingsService`
        - `getOperatorUserPermissionLevel` -> `getOperatorUserPermissions`, not one-to-one
        - `setOperatorUserPermissionLevel` -> `setOperatorUserPermissions`, not one-to-one
- `net.minecraft.server.jsonrpc.methods`
    - `OperatorService$OperatorDto#permissionLevel` now takes in an optional `PermissionLevel` instead of an `int`
    - `ServerSettingsService`
        - `operatorUserPermissionLevel` now returns a `PermissionLevel` instead of an `int`
        - `setOperatorUserPermissionLevel` now takes in and returns a `PermissionLevel` instead of an `int`
- `net.minecraft.server.permissions`
    - `LevelBasedPermissionSet` - A set of permissions that checks whether the user has an equal or higher command permission level.
    - `Permission` - Data related to the user's permissions, such as command level.
    - `PermissionCheckTypes` - The types of permission checks vanilla provides.
    - `PermissionLevel` - Defines a level sequence for a permission.
    - `PermissionProviderCheck` - A predicate that checks that tests the supplier's permission set against a check.
    - `Permissions` - The permissions vanilla provides.
    - `PermissionSet` - A set of a permissions to user has, but mostly defines a method to determine whether a user has the desired permission.
    - `PermissionSetSupplier` - An object that supplies a `PermissionSet`.
    - `PermissionSetUnion` - A union of multiple permission sets.
    - `PermissionTypes` - The types of permissions vanilla provides.
- `net.minecraft.server.players`
    - `PlayersList#op` now takes in an optional `LevelBasedPermissionSet` instead of an `int`
    - `ServerOpListEntry` now takes in a `LevelBasedPermissionSet` instead of an `int`
        - `getLevel` -> `permissions`, not one-to-one
- `net.minecraft.world.entity.player.Player#getPermissionLevel`, `hasPermissions` -> `permissions`, not one-to-one
- `net.minecraft.world.entity.projectile.ProjectileUtils`
    - `getHitEntitiesAlong` - Gets the entities hit along the provided path.
    - `getManyEntityHitResult` - Gets all entities hit along the path of the two points within the bounding box.
- `net.minecraft.world.entity.projectile.arrow.AbstractArrow#findHitEntities` - Gets all entities hit by the vector.

## New Data Components

With the addition of the spear, a number of data components have been added to provide the associated functionality. The following is an brief overview of these components.

### Use Effects

`DataComponents#USE_EFFECTS` defines some effects to apply to the player that is using (e.g., right-clicking) an item. Currently, there are only three types of effects: whether the player can sprint when using the item, whether the use interaction causes vibrations, and the scalar that is applied to the player's horizontal movement.

```java
// For some item registration
new Item(new Item.Properties.component(
    DataComponents.USE_EFFECTS,
    new UseEffects(
        // Whether the player can sprint while using the item
        true,
        // Whether on item use that a vibration is sent from the player
        false
        // The scalar applied to the player's horizontal movement
        0.5f
    )
));
```

### Damage Type

`DataComponents#DAMAGE_TYPE` defines the damage type applied to an entity when hit with this item. It takes in either the `ResourceKey` of the damage type or the `DamageType` object itself.

```java
// For some item registration
new Item(new Item.Properties.component(
    DataComponents.DAMAGE_TYPE,
    new EitherHolder<>(
        // The damage type this item applies to the attacked entity
        DamageTypes.FALLING_ANVIL
    )
));
```

### Swing Animation

`DataComponents#SWING_ANIMATION` defines the animation to play when swinging or attacking (e.g., left-clicking) with the item. There are three types of animation to play: `SwingAnimationType#NONE`, which does nothing; `WHACK`, which plays the standard swing animation; and `STAB`, which plays the spear thrust animation. The length of the animation can also be specified.

```java
// For some item registration
new Item(new Item.Properties.component(
    DataComponents.SWING_ANIMATION,
    new SwingAnimation(
        // The animation to play
        SwingAnimationType.NONE,
        // The amount of time to play the animation for, in ticks
        20
    )
));
```

### Minimum Attack Charge

`DataComponents#MINIMUM_ATTACK_CHARGE` determines how long the player must wait before making another attack with the item. The charge is a value between 0 and 1, which determines percentage of the delay to wait before making another attack. The delay is determined by the player's attack speed. This is checked twice if the player's action is a stab.

```java
// For some item registration
new Item(new Item.Properties.component(
    DataComponents.MINIMUM_ATTACK_CHARGE,
    // The percentage of time the player must wait before making another attack with this item
    0.5f
));
```

### Attack Range

`DataComponents#ATTACK_RANGE` determines the range that an entity can attack another entity from when attacking with this item. If not set, it defaults to the entity's interaction range attribute. The range specified is for the player, with mob reach determined by the range times the mob factor. A range can also be specified for the player when in creative mode, overriding the default range.

```java
// For some item registration
new Item(new Item.Properties.component(
    DataComponents.ATTACK_RANGE,
    new AttackRange(
        // The minimum range in blocks for this item to hit the entity.
        // Must be between [0, 64]; defaults to 0.
        0.4f,
        // The maximum range in blocks for this item to hit the entity.
        // Must be between [0,64]; defaults to 3.
        4.5f,
        // The minimum range in blocks for this item to hit the entity,
        // provided the holding entity is a player in creative mode.
        // This supercedes the minimum range.
        // Must be between [0, 64]; defaults to 0.
        0f,
        // The maximum range in blocks for this item to hit the entity,
        // provided the holding entity is a player in creative mode.
        // This supercedes the maximum range.
        // Must be between [0,64]; defaults to 3.
        5f,
        // The margin to inflate the hitbox by in blocks, compensating
        // for potential precision issues.
        // Must be between [0,1]; defaults to 0.3.
        0.25f,
        // A scalar to multiply the minimum and maximum range by to determine
        // a non-player entity's reach.
        // Must be between [0,2]; defaults to 1.
        1.1f
    )
));
```

### Piercing Weapon

`DataComponents#PIERCING_WEAPON` sets the player's attack as not an attack, but as a stab or piercing attack. This is a separate action than swinging, which either attacks the entity or breaks a block. A piercing weapon can attack an entity, but is unable to break blocks. It also applies any enchantment effects for lunging. Piercing weapons are only applied to the player.

The logic pipeline flows like so:

- If `Player#cannotAttackWithItem` returns true, then the pipeline is terminated
- Piercing attack is handled via:
    - Client - `MultiPlayerGameMode#piercingAttack`
    - Server - `PiercingWeapon#attack`
- Server-only:
    - Get all entities that:
        - Are within the entity's attack range `DataComponents#ATTACK_RANGE`
        - Are within the hitbox constructed from the reach starting at the player's eye position
        - If `PiercingWeapon#canHitEntity` returns true:
            - Player is not invulnerable or dead, and
            - Either:
                - Entity is an `Interaction` entity
            - Or:
                - Entity can be hit by a projectile
                - If both players, that this player can harm the other player
                - Is not a passenger of the same vehicle
    - Call `LivingEntity#stabAttack` on each entity
- `LivingEntity#onAttack` is fired
- `LivingEntity#lungeForwardMaybe` is fired
- Server-only:
    - `PiercingWeapon#makeHitSound` is played if at least one entity was hit
    - `PiercingWeapon#makeSound` is played
- `LivingEntity#swing` is fired

```java
// For some item registration
new Item(new Item.Properties.component(
    DataComponents.PIERCING_WEAPON,
    new PiercingWeapon(
        // Whether being hit by this item deals knockback to the entity.
        true,
        // Whether being hit by this item dismounts the entity from its vehicle.
        true,
        // The sound to play when attacking with this item.
        // If the optional is empty, no sound is played.
        Optional.of(SoundEvents.LLAMA_SWAG),
        // The sound to play when this item hits an entity.
        // If the optional is empty, no sound is played.
        Optional.of(SoundEvents.ITEM_BREAK)
    )
));
```

### Kinetic Weapon

`DataComponents#KINETIC_WEAPON` affects an entity's use (e.g., right-click) behavior. On right-click, if an item has the component, then `KineticWeapon#damageEntities` is called every tick instead of `Item#onUseTick`, only on the server. The kinetic weapon also calls `LivingEntity#stabAttack` to damage its entities similar to piercing attack. In fact, the component itself is similar to `PiercingWeapon`, except with a few additional fields to handle the kinetic damage applied and to make it accessible to all living entities instead of only the player.

For the stab attack to occur, one of the conditions (dismount, knockback, damage) must be present and return true. The attack range is obtained from the `DataComponents#ATTACK_RANGE` component. If a stab attack occurs, then the `SPEAR_MOBS_TRIGGER` criteria will be fired on the server.

```java
// For some item registration
new Item(new Item.Properties.component(
    DataComponents.KINETIC_WEAPON,
    new KineticWeapon(
        // The number of ticks to wait before this entity can attempt to contact
        // (e.g., damage) another entity.
        10,
        // The number of ticks to wait before attempting to stab any entities in range.
        20,
        // The condition to check whether an attack from this item will dismount
        // an entity in a vehicle.
        // If the optional is not present, then it will default to false.
        Optional.of(new KineticWeapon.Condition(
            // The maximum number of ticks from first use plus delay that this
            // condition may return true.
            100,
            // The minimum speed the entity must be traveling for this condition
            // to succeed.
            // The speed is calculated as the dot product of the delta movement
            // and the view vector multiplied by 20.
            // Vanilla spears use values from 7-14 for dismount and 5.1 for knockback.
            9f,
            // The minimum speed relative to the attacking entity that this entity
            // must be traveling for this condition to succeed.
            // Vanilla spears use 4.6 for damage.
            5f
        )),
        // The condition to check whether an attack from this item will cause knockback
        // to an entity.
        // If the optional is not present, then it will default to false.
        Optional.of(KineticWeapon.Condition.ofAttackerSpeed(
            // Maximum ticks
            100,
            // Entity traveling speed
            5.1f
        )),
        // The condition to check whether an attack from this item will damage an
        // entity.
        // If the optional is not present, then it will default to false.
        Optional.of(KineticWeapon.Condition.ofRelativeSpeed(
            // Maximum ticks
            100,
            // Relative traveling speed
            4.6f
        )),
        // The movement of the item during the third person attack animation
        // Vanilla spears use 0.38.
        0.38f,
        // A multiplier to apply to the damage of an entity
        // The damage is calculated as the relative traveling speed of this entity
        // to its target.
        4f,
        // The sound to play when first using this item.
        // If the optional is empty, no sound is played.
        Optional.of(SoundEvents.LLAMA_SWAG),
        // The sound to play when this item hits an entity.
        // If the optional is empty, no sound is played.
        Optiona.of(SoundEvents.ITEM_BREAK)
    )
));
```

- `net.minecraft.core.components`
    - `DataComponents`
        - `USE_EFFECTS` - The effects to apply to the entity when using the item.
        - `MINIMUM_ATTACK_CHARGE` - The minimum amount of time to attack with the item.
        - `DAMAGE_TYPE` - The `DamageType` the item deals
        - `PIERCING_WEAPON` - A weapon with some hitbox range that lunges towards the entity.
        - `KINETIC_WEAPON` - A weapon with some hitbox range that requires some amount of forward momentum.
        - `SWING_ANIMATION` - The animation applied when swinging an item.
        - `ATTACK_RANGE` - Sets a custom attack range when using the item, overriding the normal entity interaction range.
- `net.minecraft.core.component.DataComponentType#ignoreSwapAnimation`, `$Builder#ignoreSwapAnimation` - When true, the swap animation does not affect the data component 'usage'.
- `net.minecraft.core.component.predicates`
    - `AnyValue` - A predicate that checks if the component exists on the getter.
    - `DataComponentPredicate`
        - `$Type` is now an interface
            - Its original implementation has been replaced by `$TypeBase`
        - `$AnyValueType` - A type that uses the `AnyValue` predicate.
        - `$ConcreteType` - A type that defines a specific predicate.
- `net.minecraft.network.protocol.game.ServerboundInteractPacket#isWithinRange` - Whether the interaction from the player is within the valid range to execute.
- `net.minecraft.world.entity`
    - `LivingEntity`
        - `SWING_DURATION` -> `SwingAnimation#duration`, not one-to-one
        - `stabbedEntities` - The number of recent entities attacked by a kinetic weapon.
        - `entityAttackRange` - The range that this entity can attack.
        - `getActiveItem` - The currently used item, or the mainhand item.
    - `Mob`
        - `chargeSpeedModifier` - The modifier applied to the movement speed when charging.
        - `canFireProjectileWeapon` -> `canUseNonMeleeWeapon`, now taking in an `ItemStack` instead of a `ProjectileWeaponItem`
        - `getAttackBoundingBox` now takes in a horizontal inflation offset
- `net.minecraft.world.entity.ai.behavior`
    - `ChargeAttack` - Handles a charge attack performed by a mob.
    - `SpearApproach` - Approaches the enemy when holding a kinetic weapon.
    - `SpearAttack` - Attacks the enemy with a kinetic weapon.
    - `SpearRetreat` - Flees from the attacked target after using a kinetic weapon.
- `net.minecraft.world.entity.ai.goal.SpearUseGoal` - Handles a mob using a spear.
- `net.minecraft.world.entity.ai.memory.MemoryModuleType`
    - `SPEAR_FLEEING_TIME` - The number of ticks the entity has been fleeing for after using a kinetic weapon.
    - `SPEAR_FLEEING_POSITION` - The position the entity flees to after using a kinetic weapon.
    - `SPEAR_CHARGE_POSITION` - The position the entity charges to when using a kinetic weapon.
    - `SPEAR_ENGAGE_TIME` - How long this entity has been engaged with its enemy when using a kinetic weapon.
    - `SPEAR_STATUS` - The status of the entity when using a kinetic weapon.
- `net.minecraft.world.entity.player.Player`
    - `hasEnoughFoodToDoExhaustiveManoeuvres` - Returns whether the player can perform an exhaustive manuever.
    - `canInteractWithEntity` -> `isWithinEntityInteractionRange`
    - `isWithinAttackRange` - If the bounding box being targeted is within the player's range.
    - `canInteractWithBlock` -> `isWithinBlockInteractionRange`
    - `CREATIVE_ENTITY_INTERACTION_RANGE_MODIFIER_VALUE` - A modifiers that increases the maximum range of an interaction by the given amount.
- `net.minecraft.world.item`
    - `Item#getDamageSource` -> `getItemDamageSource`, now deprecated
    - `ItemStack`
        - `getSwingAnimation` - Returns the swing animation of the item.
        - `getDamageSource` - Returns the damage source the item provides when hit.
        - `causeUseVibration` - Sends the game event if the item on use can cause vibrations.
    - `SwingAnimationType` - The type of animation played when swinging the item.
- `net.minecraft.world.item.component`
    - `AttackRange` - The hitbox range of this item.
    - `KineticWeapon` - A weapon that requires some amount of forward momentum. 
    - `PiercingWeapon` - A weapon that lunges towards the entity.
    - `SwingAnimation` - The animation applied when swinging an item.
    - `UseEffects` - The effects to apply to the entity when using the item.

## The Timeline of Environment Attributes

Environment attributes, as the name implies, defines a set of properties or modifications ('attributes') for a given dimension and/or biome ('environment'). They are stored directly within the biome or dimension type under the `attributes` field, or as part of a mutable timeline under the `tracks` field. Each attribute can represent anything from the visual settings to gameplay behavior, interpolating between different values as defined. Vanilla provides their available attributes within `EnvironmentAttributes` while the stored attributes are obtained from `Level#environmentAttributes`.

```json5
// For some DimensionType json
// In `data/examplemod/dimension_type/example_dimension.json`
{
    // Defines the attributes to apply within the dimension
    "attributes": {
        // Sets the cloud height
        // More technically, modifies the value by overriding it
        "minecraft:visual/cloud_height": 90
    },
    // ...
}

// For some Biome json
// In `data/examplemod/worldgen/biome/example_biome.json`
{
    // Defines the attributes to apply within the biome
    // Defaults or modifies those in the dimension
    // These attributes must be positional
    "attributes": {
        "minecraft:visual/cloud_height": {
            // Instead of setting the value, apply a modifier
            "modifier": "add",
            // Adds 60 to 90, making this biome have a cloud height of 150
            "argument": 60
        }
    }
    // ...
}

// For some Timeline json
// In `data/examplemod/timeline/example_timeline.json
{
    // The number of ticks this track takes before repeating
    "period_ticks": 24000,
    // Defines the attributes to interpolate between
    // based on the defined keyframes.
    // Defaults or modifies those in the biome, or dimension
    "tracks": {
        "minecraft:visual/cloud_height": {
            // The keyframes that define certain values of the attribute
            "keyframes": [
                {
                    // The tick representing the keyframe
                    "tick": 12000,
                    // The argument that modifies the value
                    // In this case, adds 1 to 60 + 90, making this have a cloud height of 151
                    "value": 1
                },
                {
                    // The tick representing the keyframe
                    "tick": 23999,
                    // The argument that modifies the value
                    // In this case, adds 0 to 60 + 90, making this have a cloud height of 150
                    "value": 0
                }
            ],
            // Instead of setting the value, applies a modifier to the argument
            "modifier": "add",
            // The sampler function to apply when interpolating between ticks
            "ease": "linear"
        }
    }
}
```

When calling `EnvironmentAttributeSystem#getValue`, the attribute value is obtained through the layers defined by the `Level`:

1. Read the default value from the registered attribute (via `EnvironmentAttribute#defaultValue`).
2. Apply the modifier from the dimension, or do nothing if one does not exist for this attribute.
3. Apply the modifier from the biome, or do nothing if one does not exist.
4. Apply the modifiers from all active timelines defined in the `DimensionType`, or do nothing if none exist. Timeline order is not guaranteed.
5. If the dimension can have weather (skylight, no ceiling, and not the end), apply the modifiers from the `WeatherAttributes`.
6. If on the client (i.e. `ClientLevel`), apply the sky flashes modifier.
7. Sanitize the final value to be in the range defined by the `EnvironmentAttribute`.

This is highly oversimplified and introduces many new concepts, so let's break it down further by creating our own environment attribute and timeline.

### Custom Environment Attributes

Environment attributes are created through the `$Builder`, via `EnvironmentAttribute#builder`, taking the type value it represents (e.g., float, integer, object). The builder only requires one value to be set: the `defaultValue`. This is used if the attribute is not overridden by a dimension or biome. If the attribute value should have a valid set of states, then an `AttributeRange` can be set via `valueRange`. The `AttributeRange` is basically a unary operator that transforms the input into its 'valid' state via `sanitize`. It also verifies that the value passed in through the JSON is in a 'valid' state via `validate`.

From there, there are three more methods responsible for determining the logic used to compute the value. `$Builder#syncable` syncs the attribute to the client, which is required for any attribute that causes some sort of change that is not specific to the server (e.g., visuals, audio, or common code). `notPositional` means that the attribute cannot be applied on a biome (still settable in a dimension or timeline), else an exception is thrown. Finally `spatiallyInterpolated` will attempt to interpolate using the attribute type between different biomes to apply a more seamless transition. Vanilla only handles client side attributes for spatial interpolation. Anything on the server must handle their own `SpatialAttributeInterpolator`.

Finally the actual attribute can be obtained via `$Builder#build`. This value must be registered to `BuiltInRegistries#ENVIRONMENT_ATTRIBUTE`:

```java
public static final EnvironmentAttribute<Boolean> EXAMPLE_ATTRIBUTE = Registry.register(
    BuiltInRegistries.ENVIRONMENT_ATTRIBUTE,
    Identifier.withNamespaceAndPath("examplemod", "example_attribute"),
    EnvironmentAttribute.builder(
        // The attribute type
        // Must match the generic for the attribute value
        AttributeTypes.BOOLEAN
    )
        // The value this attribute should have by default
        .defaultValue(false)
        // Syncs this value to the client
        .syncable()
        .build()
);
```

```json5
// For some DimensionType json
// In `data/examplemod/dimension_type/example_dimension.json`
{
    "attributes": {
        "examplemod:example_attribute": true
    },
    // ...
}

// For some Biome json
// In `data/examplemod/worldgen/biome/example_biome.json`
{
    "attributes": {
        "examplemod:example_attribute": {
            "modifier": "xor",
            "argument": true
        }
    }
    // ...
}

// For some Timeline json
// In `data/examplemod/timeline/example_timeline.json
{
    "period_ticks": 24000,
    "tracks": {
        "examplemod:example_attribute": {
            "keyframes": [
                {
                    "tick": 12000,
                    "value": false
                },
                {
                    "tick": 23999,
                    "value": true
                }
            ],
            "modifier": "and",
            "ease": "linear"
        }
    }
}
```

### Custom Attribute Types

Every environment attribute has an associated attribute type that is statically registered to `BuiltInRegistries#ATTRIBUTE_TYPE`. Not only does this define how to serialize the object value, but it also contains the modifications that can be applied to the value along with how to interpolate between spaces and frames. In fact, all of the builder settings, including `syncable` and `spatiallyInterpolated`, rely on the attribute type to determine what does it mean to perform that action. Without it, the attribute couldn't even be read from the dimension or biome JSON, much less the actual logic behind getting the value of the attribute.

As such, the attribute type can be broken into three parts: the serialization codec, the modifier library, and the interpolation functions.

```java
// We will use this example object for explaining the attribute type
public record ExampleObject(int value1, boolean value2) {
    // The default value
    public static final ExampleObject DEFAULT = new ExampleObject(0, false);
}
```

#### Type Serialization

Serialization of the attribute type is handled through a codec of that type, both to disk (biome and dimension JSON) and network (`$Builder#syncable`):

```java
// The codec to serialize the attribute type value
public static final Codec<ExampleObject> CODEC = RecordCodecBuilder.create(
    instance -> instance.group(
        Codec.INT.fieldOf("value1").forGetter(ExampleObject::value1),
        Codec.BOOL.fieldOf("value2").forGetter(ExampleObject::value2)
    ).apply(instance, ExampleObject::new)
);
```

#### Modifier Library

The modifier library is a map of `AttributeModifier$OperationId` to `AttributeModifier`s that determine what operations can be performed on the default value. If the map contains no operations, the the default value cannot be mutated. All of the static constructors for `AttributeType` add the `OVERRIDE` modifier, allowing for the dimension and/or biome to set the value. This map should be thought of as a pseudo-registry (basically keys to unique values), as the default codec used to serialize is for a `BiMap` via an id resolver.

An `AttributeModifier` defines two generics: the first being the environment attribute value type, and the second being an arbitrary object to apply the operation with. The modifier has two methods: `apply`, which takes in the value and the argument to return a new value; and `argumentCodec` to properly serialize the argument. For any operation, all possible mutations must be implemented within a single `AttributeModifier`:

```java
// Modifiers only handling a part of the object
public static final AttributeModifier<ExampleObject, Integer> ADD = new AttributeModifier<>() {

    @Override
    public ExampleObject apply(ExampleObject subject, Integer argument) {
        // Apply the operation to the subject
        return new ExampleObject(subject.value1() + argument, subject.value2());
    }

    @Override
    public Codec<Integer> argumentCodec(EnvironmentAttribute<ExampleObject> attribute) {
        // Construct the codec to deserialize the argument
        return Codec.INT;
    }
};

public static final AttributeModifier<ExampleObject, Boolean> OR = new AttributeModifier<>() {

    @Override
    public ExampleObject apply(ExampleObject subject, Boolean argument) {
        // Apply the operation to the subject
        return new ExampleObject(subject.value1(), subject.value2() || argument);
    }

    @Override
    public Codec<Boolean> argumentCodec(EnvironmentAttribute<ExampleObject> attribute) {
        // Construct the codec to deserialize the argument
        return Codec.BOOL;
    }
};

// A modifier handling all possible object combinations
public static final AttributeModifier<ExampleObject, Either<ExampleObject, Either<Integer, Boolean>>> AND = new AttributeModifier<>() {

    @Override
    public ExampleObject apply(ExampleObject subject, Either<ExampleObject, Either<Integer, Boolean>> argument) {
        return argument.map(
            arg -> new ExampleObject(subject.value1() & arg.value1(), subject.value2() && arg.value2()),
            either -> either.map(
                arg -> new ExampleObject(subject.value1() & arg, subject.value2()),
                arg -> new ExampleObject(subject.value1(), subject.value2() && arg)
            )
        );
    }

    @Override
    public Codec<Either<ExampleObject, Either<Integer, Boolean>>> argumentCodec(EnvironmentAttribute<ExampleObject> attribute) {
        // Construct the codec to deserialize the argument
        // We can use the attribute codec for the value type
        return Codec.either(attribute.valueCodec(), Codec.either(Codec.INT, Codec.BOOL));
    }
};

// Constructing the library
// The argument can be any value as long as it can be serialized and handled
// If using one of the static constructors for the attribute type, the override
// handler is added automatically along with the associated modifier codec for
// the map
public static final Map<AttributeModifier.OperationId, AttributeModifier<ExampleObject, ?>> EXAMPLE_LIBRARY = Map.of(
    AttributeModifier.OperationId.ADD, ADD,
    AttributeModifier.OperationId.OR, OR,
    AttributeModifier.OperationId.AND, AND
);
```

#### Type Interpolation

To support interpolation, whether for client frames (because of `$Builder#syncable`), spatial (`$Builder#spatiallyInterpolated`), states (weather), or keyframes (timelines), there needs to be some function that, given some step between 0 and 1 (either time or position), how are the two values merged. This is handled through a `LerpFunction`, of which the generic is the environment attribute value type. For non-interpolated values, this normally uses `LerpFunction#ofStep`, which acts like a simple threshold between the two values. More specifically, spatial will set the threshold as 0.5 while the partial tick, keyframe, and state change will only consider full steps (meaning always the next value). However, this function can be however you choose to define it:

```java
// Step represents the value between 0 and 1 to interpolation
// Original represents the step at 0
// Next represents the step at 1
public static final LerpFunction<ExampleObject> EXAMPLE_SPATIAL_LERP = (step, original, next) -> {
    return new ExampleObject(
        Mth.lerp(step, original.value1(), next.value1()),
        step >= 0.5f ? next.value2() : original.value2()
    );
}

public static final LerpFunction<ExampleObject> EXAMPLE_PARTIAL_LERP = (step, original, next) -> {
    return new ExampleObject(
        Mth.lerp(step, original.value1(), next.value1()),
        next.value2()
    );
}

// Will always return the first value
public static final LerpFunction<ExampleObject> EXAMPLE_KEYFRAME_LERP = LerpFunction.ofConstant();

// Will change to the next state after 0.1 of the step has past
public static final LerpFunction<ExampleObject> EXAMPLE_STATE_CHANGE_LERP = LerpFunction.ofStep(0.1f);
```

#### Putting it all Together

With each of these parts, an `AttributeType` can now be constructed. This is typically done using one of the static constructors: `ofInterpolated` for values that define their interpolation function, or `ofNotInterpolated`, for values that are okay just snapping between two values. For common use cases, unless your value transitions between something that is inherently obvious to the player (e.g., visuals like fog or sky color), then interpolation is generally unnecessary.

If you decide to use the `AttributeType` instance constructor instead, you will also have to create a codec to serialize the modifier map. See `AttributeType#createModifierCodec` on how to do so.

```java
// The attribute type must be statically registered to be handled correctly
public static final AttributeType<ExampleObject> EXAMPLE_ATTRIBUTE_TYPE = Registry.register(
    BuiltInRegistries.ATTRIBUTE_TYPE,
    Identifier.withNamespaceAndPath("examplemod", "example_attribute_type"),
    new AttributeType<>(
        // The codec for the value
        ExampleObject.CODEC,
        // The map of operations that can be modified
        // `OVERRIDE` is automatically added for serialization
        EXAMPLE_LIBRARY,
        // The codec used to serialize the modifier library
        Util.make(() -> {
            ImmutableBiMap<AttributeModifier.OperationId, AttributeModifier<Value, ?>> map = ImmutableBiMap.builder()
                .put(AttributeModifier.OperationId.OVERRIDE, AttributeModifier.override())
                .putAll(EXAMPLE_LIBRARY)
                .buildOrThrow();
            return ExtraCodecs.idResolverCodec(AttributeModifier.OperationId.CODEC, map::get, map.inverse()::get);
        }),
        // The function interpolating between two keyframes in a timeline
        EXAMPLE_KEYFRAME_LERP,
        // The function interpolating between two states (only used for attributes in the weather maps)
        EXAMPLE_STATE_CHANGE_LERP,
        // The function interpolating between two spatial coordinates
        EXAMPLE_SPATIAL_LERP,
        // The function interpolating between client frames
        EXAMPLE_PARTIAL_LERP
    )
);
```

From there, we can create an `EnvironmentAttribute` that uses said type:

```java
public static final EnvironmentAttribute<ExampleObject> EXAMPLE_OBJECT_ATTRIBUTE = Registry.register(
    BuiltInRegistries.ENVIRONMENT_ATTRIBUTE,
    Identifier.withNamespaceAndPath("examplemod", "example_object_attribute"),
    EnvironmentAttribute.builder(
        EXAMPLE_ATTRIBUTE_TYPE
    )
        .defaultValue(ExampleObject.DEFAULT)
        // Possible because of the codec and partial tick lerp
        .syncable()
        // Possible because of the spatial lerp
        .spatiallyInterpolated()
        .build()
);
```

```json5
// For some DimensionType json
// In `data/examplemod/dimension_type/example_dimension.json`
{
    "attributes": {
        "examplemod:example_object_attribute": {
            "value1": 10,
            "value2": true
        }
    },
    // ...
}

// For some Biome json
// In `data/examplemod/worldgen/biome/example_biome.json`
{
    "attributes": {
        "examplemod:example_object_attribute": {
            // Must use one of the arguments defined in the library
            // In this case either 'add', 'or', or 'and'
            "modifier": "and",
            // This can be either a boolean, integer, or object
            // because of how the serializer was defined
            "argument": false
        }
    }
    // ...
}

// For some Timeline json
// In `data/examplemod/timeline/example_timeline.json
{
    "period_ticks": 24000,
    "tracks": {
        "examplemod:example_object_attribute": {
            "keyframes": [
                {
                    "tick": 12000,
                    // This can be either a boolean, integer, or object
                    // because of how the serializer was defined
                    "value": 1
                },
                {
                    "tick": 23999,
                    // This can be either a boolean, integer, or object
                    // because of how the serializer was defined
                    "value": {
                        "value1": 0,
                        "value2": false
                    }
                }
            ],
            // Must use one of the arguments defined in the library
            // In this case either 'add', 'or', or 'and'
            "modifier": "and",
            "ease": "linear"
        }
    }
}
```

### Timelines

`Timeline`s are method of modifying attributes based on the current game time. More specifically, they define some keyframes that the values are interpolated between, first determining the step using the `EasingType` function, and second using `AttributeType#keyframeLerp` to get the value. This is not only a replacement of `Schedule`s in brains, but also attributes relating to the day/night cycle (e.g., sky color, slime spawn chance, etc.). These function as a layer after the biome modifiers are applied for both positional and non-positional attributes.

Timelines are activated based on the `DimensionType#timelines` tag, which are prefixed with `in_` (e.g. `minecraft:in_overworld` is the timeline tag for the overworld). All dimension tags include the `minecraft:universal` tag, meaning all timelines tagged within will run within all dimensions (provided they add the universal tag).

The vanilla timelines are like so:

- `minecraft:day`: The day/night cycle
- `minecraft:moon`: The moon phase and spawn chance
- `minecraft:villager_schedule`: What `Activity` a villager performs
- `minecraft:early_game`: Stops pillager patrol spawns for the first few days

The associated tags are the following;

- `minecraft:universal`
    - `minecraft:villager_schedule`
- `minecraft:in_overworld` - Overworld dimension
    - `#minecraft:universal`
    - `minecraft:day`
    - `minecraft:moon`
    - `minecraft:early_game`
- `minecraft:in_nether` - Nether dimension
    - `#minecraft:universal`
- `minecraft:in_end` - End dimension
    - `#minecraft:universal`

#### Keyframes

Each `Timeline` is made up of keyframes responsible for determining what the argument to the modifier should be at a given tick. These keyframes are then compiled into a list called a `KeyframeTrack`, baked into a `KeyframeTrackSampler` when constructing the attribute layers. Every two adjacent keyframes (including the first and last) is considered a `KeyframeTrackSampler$Segment`. This is what's used to sample an attribute at a given tick.

Let's say we have the following (keyframe, value) segment (100, 0) -> (200, 1) and we are currently at tick 150. How do we choose what argument to use? Well, this is performed in two operations. First, we calculate the step: a value between `0` and `1` that determines how much to interpolate the value with. The step is calculated first linearly: (current_tick - start_segment_tick) / (end_segment_tick - start_segment_tick). Then, the step is passed into the desired `EasingType`, which is a function that takes in a 0-1 value and returns a 0-1 value, such as `in_out_bounce` or `out_back`. You can also create your own `EasingType` like so:

```java
// `EasingType#registerSimple` must be made public
EasyingType.registerSimple(
    // The name of the function
    "examplemod:ease",
    // The function to apply to the value
    // For smooth transitions, the function should map 0 -> 0 and 1 -> 1
    original -> 0.5f * (float) Mth.sin(Math.PI * (3 * original - 0.5)) + 0.5f
);
```

Then, it passes the step to the `AttributeType#keyframeLerp` function along with the two arguments to get the lerped argument to apply.

#### Attribute Tracks

With the keyframes determining the arguments, we apply the arguments to the attribute through an `AttributeTrack`, baked into an `AttributeTrackSampler` when constructing the attribute layers. An `AttributeTrack` contains the `KeyframeTrack` to get the arguments, and an `ArgumentModifier` to apply the argument to the value. Note that there can only be one modifier for a given track.

These `AttributeTrack`s are then stored in a map of attributes to tracks, which define our `Timeline`. The timeline also contains an optional integer indicating the period of the tracks. The 'period', in this case, acts as one full run of all tracks in the timeline. Values outside of the period are modulo'd. Most timelines use `24000` for the period as that represents one Minecraft day in ticks.

#### Custom Timelines

Custom `Timeline`s are added to the `timeline` datapack registry:

```json5
// For some Timeline json
// In `data/examplemod/timeline/example_timeline.json
{
    // Runs for every 3000 ticks (1/8 of a day)
    "period_ticks": 3000,
    "tracks": {
        // The attribute(s) to modify
        "examplemod:example_object_attribute": {
            // The easing function to determine the step between arguments
            "ease": "examplemod:ease",
            // The list of keyframes defining the arguments at set ticks
            // The arguments are then interpolated using the easing function
            // and keyframe lerp
            "keyframes": [
                {
                    // The tick for which this argument is the given value
                    "tick": 1500,
                    // Adds 10 to the attribute
                    "value": 10
                },
                // In-between, uses the easing function to step down between
                // 10 and 0
                {
                    "tick": 2999,
                    // Adds 0 to the attribute
                    "value": 0
                }
                // In-between, uses the easing function to step up between
                // 0 and 10
            ],
            // The modifier to use when applying the argument
            // to the value
            "modifier": "add"
        }
    }
}
```

- `net.minecraft.client`
    - `Camera#attributeProbe` - Gets the client environment attribute probe for values and interpolation.
    - `Minecraft`
        - `getSituationalMusic` now returns `Music` instead of `MusicInfo`
        - `getMusicVolume` - Gets the volume of the background music, or normal volume if the open screen has background music.
- `net.minecraft.client.multiplayer.ClientLevel`
    - `effects` is removed
    - `getSkyDarken` -> `EnvironmentAttributes#SKY_LIGHT_COLOR`, `SKY_LIGHT_FACTOR`; not one-to-one
    - `getSkyColor` -> `EnvironmentAttributes#SKY_COLOR`, not one-to-one
    - `getCloudColor` -> `EnvironmentAttributes#CLOUD_COLOR`, not one-to-one
    - `getStarBrightness` -> `EnvironmentAttributes#STAR_BRIGHTNESS`, not one-to-one
    - `getSkyFlashTime` is now private
- `net.minecraft.client.renderer`
    - `DimensionSpecialEffects` class is removed, replaced entirely by `EnvironmentAttribute`s
    - `SkyRenderer`
        - `renderSkyDisc` now takes in a single ARGB `int` instead of three RGB `float`s
        - `renderSunMoonAndStars` now take in two additional `float`s for the moon and star rotation
- `net.minecraft.client.renderer.state.SkyRenderState`
    - `skyType` -> `skybox`, not one-to-one
    - `isSunriseOrSunset`, `timeOfDay` are removed
    - `moonAngle`, `starAngle` - The angle of the moon and stars.
- `net.minecraft.client.resources.sounds.BiomeAmbientSoundsHandler` no longer takes in the `BiomeManager`
- `net.minecraft.client.sounds`
    - `MusicInfo` -> `Minecraft#getSituationalMusic`, `getMusicVolume`; not one-to-one
    - `MusicManager#startPlaying` now takes in the `Music` instead of the `MusicInfo`
- `net.minecraft.core.registries`
    - `BuiltInRegistries`, `Registries#ENVIRONMENT_ATTRIBUTE` - The registry for the environment attributes.
    - `BuiltInRegistries`, `Registries#ATTRIBUTE_TYPE` - The registry for the attribute types.
    - `BuiltInRegistires`, `Registries#SCHEDULE` are removed
    - `Registries#TIMELINE` - The registry key for the timeline.
- `net.minecraft.data.tags.TimelineTagsProvider` - The tags provider for the timeline.
- `net.minecraft.server.level.ServerLevel#getMoonBrightness` - Returns the brightness of the moon.
- `net.minecraft.sounds.Music#event` -> `sound`
- `net.minecraft.tags.TimelineTags` - The tags for the timeline.
- `net.minecraft.util`
    - `BinaryAnimator$EasingFunction` -> `EasingType`
    - `CubicSampler` -> `GaussianSampler`, `SpatialAttributeInterpolator`; not one-to-one
    - `KeyframeTrack` - A track of keyframes and the easing performed between them.
    - `KeyframeTrackSampler` - A keyframe track that replays based on the period, lerping between values using the provided function.
- `net.minecraft.world.attribute`
    - `AmbientSounds` - The sounds that ambiently play within an environment.
    - `AttributeRange` - An interface meant to validate inputs and sanitize the corresponding value into an appropriate bound.
    - `AttributeType` - A type definition of the operations and modifications that can be performed by an attribute.
    - `AttributeTypes` - A registry of all vanilla attribute types.
    - `BackgroundMusic` - The background music that plays within an environment.
    - `BedRule` - The rules of how beds function within an environment.
    - `EnvironmentAttribute` - A definition of some attribute within an environment.
    - `EnvironmentAttributeLayer` - A layer that modifies a value.
    - `EnvironmentAttributeMap` - A map of attribute definitions to their argument and modifier. 
    - `EnvironmentAttributeProbe` - The attribute handler for getting and interpolating between values. Used only by the camera.
    - `EnvironmentAttributeReader` - A reader that can lookup the environment attributes either by dimension or position.
    - `EnvironmentAttributes` - A registry of all vanilla environment attributes.
    - `EnvironmentAttributeSystem` - A reader implementation that gets and spatially interpolates environment attributes.
    - `LerpFunction` - A functional interface that takes in some value between 0-1 along with the start and end values to interpolate between.
    - `WeatherAttributes` - Attributes maps for applying weather layers.
- `net.minecraft.world.attribute.holder`
    - `AttributeModifier` - A modifier that takes in the attribute value along with some argument (typically a value of the same type) to produce a modified value.
    - `BooleanModifier` - Modifier for a boolean with a boolean argument.
    - `ColorModifier` - Modifier for an ARGB integer with some argument, typically integers.
    - `FloatModifier` - Modifier for a float with some argument, typical floats or a float with an alpha interpolator.
    - `FloatWithAlpha` - A record containing some value and an alpha typically used for blending.
- `net.minecraft.world.entity.ai.Brain`
    - `getSchedule` is removed
    - `setSchedule` now takes in an `EnvrionmentAttribute<Activity>` instead of a `Schedule`
    - `updateActivityFromSchedule` now takes in the `EnvironmentAttributeSystem` and position instead of the day time
- `net.minecraft.world.entity.animal.bee.Bee#isNightOrRaining` replaced with `EnvironmentAttributes#BEES_STAY_IN_HIVE`
- `net.minecraft.world.entity.player.Player$BedSleepingProblem` is now a record
    - `NOT_POSSIBLE_HERE` -> `BedRule#EXPLODES`
    - `NOT_POSSIBLE_NOW` -> `BedRule#CAN_SLEEP_WHEN_DARK`
- `net.minecraft.world.entity.schedule`
    - `Keyframe` -> `.minecraft.util.Keyframe`, not one-to-one
    - `Schedule` is removed, its logic replaced by `Timeline`, `Timelines`
    - `ScheduleBuilder` is remvoed, its logic replaced by `Timeline$Builder`
    - `Timeline` -> `.world.timeline.Timeline`, not one-to-one
- `net.minecraft.world.entity.variant.SpawnContext` now takes in the `EnvironmentAttributeReader`
- `net.minecraft.world.level`
    - `Level`
        - `isMoonVisible` replaced by `EnvironmentAttributes#MOON_ANGLE`
        - `getSunAngle` replaced by `EnvironmentAttributes#SUN_ANGLE`
        - `canHaveWeather` is now `public`
    - `LevelAccessor` now implements `LevelReader` instead of `LevelTimeAccess`
    - `LevelReader#environmentAttributes` - Returns the manager for get the environment attribute within a dimension and its associated biomes.
    - `LevelTimeAccess` interface is removed
    - `MoonPhase`
        - `CODEC` - The codec for the moon phase.
        - `PHASE_LENGTH` - The number of ticks that a moon phase is present for.
        - `startTick` - The start tick for a particular phase.
- `net.minecraft.world.level.biome`
    - `AmbientAdditionsSettings` -> `.world.attribute.AmbientAdditionsSettings`
    - `AmbientMoodSettings` -> `.world.attribute.AmbientMoodSettings`
    - `AmbientParticleSettings` -> `.world.attribute.AmbientParticle`
    - `Biome` now takes in the `EnvironmentAttributeMap`
        - `getSkyColor` -> `EnvironmentAttributes#SKY_COLOR`
        - `getFogColor` -> `EnvironmentAttributes#FOG_COLOR`
        - `getAttributes` - Gets the attributes for this biome.
        - `getWaterFogColor` -> `EnvironmentAttributes#WATER_FOG_COLOR`
        - `getAmbientParticle` -> `EnvironmentAttributes#AMBIENT_PARTICLES`
        - `getAmbientLoop` -> `AmbientSounds#loop` environment attribute
        - `getAmbientMood` -> `AmbientSounds#mood` environment attribute
        - `getAmbientAdditions` -> `AmbientSounds#additions` environment attribute
        - `getBackgroundMusic` -> `EnvironmentAttributes#BACKGROUND_MUSIC`
        - `getBackgroundMusicVolume` -> `EnvironmentAttributes#MUSIC_VOLUME`
        - `$Builder`
            - `putAttributes` - Puts all attributes from another map.
            - `setAttribute` - Sets an environment attribute.
            - `modifyAttribute` - Modifies an attribute source for the biome.
    - `BiomeSpecialEffects` is now a record
        - `getFogColor` -> `EnvironmentAttributes#FOG_COLOR`
        - `getWaterFogColor` -> `EnvironmentAttributes#WATER_FOG_COLOR`
        - `getSkyColor` -> `EnvironmentAttributes#SKY_COLOR`
        - `getAmbientParticleSettings` -> `EnvironmentAttributes#AMBIENT_PARTICLES`
        - `getAmbientLoopSoundEvent` -> `AmbientSounds#loop` environment attribute
        - `getAmbientMoodSettings` -> `AmbientSounds#mood` environment attribute
        - `getAmbientAdditionsSettings` -> `AmbientSounds#additions` environment attribute
        - `getBackgroundMusic` -> `EnvironmentAttributes#BACKGROUND_MUSIC`
        - `getBackgroundMusicVolume` -> `EnvironmentAttributes#MUSIC_VOLUME`
- `net.minecraft.world.level.block`
    - `BedBlock#canSetSpawn` -> `BedRule#canSetSpawn` environment attribute
    - `CreakingHeartBlock#isNaturalNight` replaced by `EnvironmentAttributes#CREAKING_ACTIVE`
    - `RespawnAnchorBlock#canSetSpawn` now takes in the `ServerLevel` and `BlockPos`
- `net.minecraft.world.level.dimension`
    - `BuiltinDimensionTypes#*_EFFECTS` are removed
    - `DimensionDefaults#OVERWORLD_CLOUD_HEIGHT` is now a `float`
    - `DimensionType`
        - `fixedTime` -> `hasFixedTime`, now a `boolean` instead of a `OptionalLong`
        - `natural`, `effectsLocation` are removed
        - `skybox` - The skybox to display within the dimension.
        - `cardinalLightType` - The type of light permeating through a dimension.
        - `timelines` - A set of timelines that modify the environment attributes of this dimension.
        - `ultraWarm` -> `EnvironmentAttributes#WATER_EVAPORATES`, `FAST_LAVA`, `DEFAULT_DRIPSTONE_PARTICLE`
        - `bedWorks` -> `EnvironmentAttributes#BED_RULE`
        - `respawnAnchorWorks` -> `EnvironmentAttributes#RESPAWN_ANCHOR_WORKS`
        - `cloudHeight` -> `EnvironmentAttributes#CLOUD_HEIGHT`
        - `attribute` - Gets the attributes for this dimension.
        - `piglinSafe`, `$MonsterSettings#piglinSafe` -> `EnvironmentAttributes#PIGLINS_ZOMBIFY`
        - `hasRaids`, `$MonsterSettings#hasRaids` -> `EnvironmentAttributes#CAN_START_RAID`
        - `timeOfDay` is removed
        - `moonPhase` replaced by `EnvironmentAttributes#MOON_PHASE`
        - `hasEndFlashes` - Returns whether the skybox is the end.
        - `$CardinalLightType` - The light permeating through a dimension.
        - `$Skybox` - The skybox of a dimension.
- `net.minecraft.world.level.material.FogType#DIMENSION_OR_BOSS` is removed
- `net.minecraft.world.timeline`
    - `AttributeTrack` - A track that applies the attribute modifier with the argument sampled from the given keyframe track.
    - `AttributeTrackSampler` - A baked attribute track.
    - `Timeline` - A map of attributes to tracks that are applied based on the given time in ticks modulo the period.
    - `Timelines` - All vanilla timelines.

## The Game Rule Shuffle

The gamerule system has been overhauled to a degree, allowing its keys to be stored as proper registry objects while still having its values limited to either integers or booleans. Most of the classes are basically just combinations of others.

### Existing Game Rules

Existing game rules are still in a `GameRules` class, just moved to a different location. Their fields have been renamed and seem to follow some basic rules:

1. Rules no longer have the `RULE_` prefix
1. Rules now have underscores separating words
1. The `DO` prefix is removed from rule names (e.g. `RULE_DOENTITYDROPS` -> `ENTITY_DROPS`)
1. The `SPAWNING` suffix has been replaced with the `SPAWN_` prefix (e.g. `RULE_DOMOBSPAWNING` -> `SPAWN_MOBS`)
1. The `DISABLE` prefix is removed, meaning that their values are inverted (e.g., `RULE_DISABLERAIDS` -> `RAIDS`)

While there are some edge cases, searching for a specific word in the previous game rule name will most likely lead you to the new name (e.g., searching for `ADVANCEMENT` in `RULE_ANNOUNCEADVANCEMENTS` leads to `SHOW_ADVANCEMENT_MESSAGES`).

To actually get a value from the game rules, you would use `GameRules#get` instead of the previous `getBoolean` and `getInteger`. The type is obtained from the generic on the registered `GameRule`.

```java
// With ServerLevel level
boolean fallDamage = level.getGameRules().get(GameRules.FALL_DAMAGE);
```

Additionally, setting the game rule is now simplified to calling `GameRules#set` -- taking in the `GameRule`, value, and the current server if the changes are propogated through `MinecraftServer#onGameRuleChanged`, which it should generally be.

```java
// With ServerLevel level
level.getGameRules().set(GameRules.FALL_DAMAGE, false, level.getServer());
```

### Creating a Game Rule

Game rules are created through the `GameRule` class, which is basically a type definition of how the game rule functions depending on its caller. Its generic represents the type of the value being held. The only hardcoded concepts that separate this from being a general type is that the actual arguments can be limited to a specific range, and that they store the default value. Otherwise, the fields are mostly the same from its previous counterparts in `GameRules$Type` and `GameRules$Key`.

Then, once created, the `GameRule` must be statically registered to `BuiltInRegistries#GAME_RULE`

```java
public static final GameRule<Integer> EXAMPLE_RULE = Registry.register(
    BuiltInRegistries.GAME_RULE
    Identifier.withNamespaceAndPath("examplemod", "example_rule"),
    new GameRule(
        // The category that best represents the game rule.
        // This is only used by the edit game rule screen
        // when first constructing the world.
        // A custom category can be created by calling
        // `GameRuleCategory#register` or just its constructor
        // as the sort order goes unused
        GameRuleCategory.register(
            Identifier.withNamespaceAndPath("examplemod", "example_category")
        ),
        // The type of the game rule, represenative of the
        // JSON schema version of the generic.
        // This is only used by the management system for
        // checking an untyped rule.
        GameRuleType.INT,
        // The argument type used for serializing the value
        // in commands.
        // This can be range-limited based on the constructor.
        IntegerArgumentType.integer(0, 5),
        // A caller that runs typically during the visiting process
        // for each game rule.
        // This caller is only used by the edit game rules screen
        // for adding the correct component that modifies the value.
        // `GameRuleTypeVisitor#visit` should not be used here
        // as the visitor already calls that function.
        GameRuleTypeVisitor::visitInteger,
        // The codec used to serialize the game rules to disk
        // or for the managment service.
        // This can be range-limited based on the constructor.
        Codec.intRange(0, 5),
        // A function that maps the set value to an integer
        // result used when setting or querying the game rule
        // via a command.
        // This is the only case when a result of `0` does not
        // mean the command has failed.
        gameRuleValue -> gameRuleValue,
        // The default value to set for this rule.
        3,
        // A feature flag set that are required for this game rule
        // to be enabled in game.
        // An empty flag set means it should be enabled at all times.
        FeatureFlagSet.of()
    )
);
```

`net.minecraft.client.gui.screens.worldselection`
    - `EditGameRulesScreen`
        - `$BooleanRuleEntry` now takes in a `GameRule<Boolean>` instead of a `GameRules$BooleanValue`
        - `$EntryFactory` no longer bounds its generic
        - `$IntegerRuleEntry` now takes in a `GameRule<Integer>` instead of a `GameRules$IntegerValue`
    - `InitialWorldCreationOptions#disabledGameRules` is now a `GameRuleMap`
- `net.minecraft.core.registries.BuiltInRegistries#GAME_RULE`, `Registries#GAME_RULE` - Game rule registry.
- `net.minecraft.gametest.framework.TestEnvironmentDefinition`
    - `$SetGameRules` now takes in a `GameRulesMap` instead of `$Entry`s
        - `entry`, `$Entry` are removed
- `net.minecraft.server.MinecraftServer#onGameRuleChanged` now takes in the `GameRule` and value instead of the string key and `$Value` wrapper
- `net.minecraft.server.jsonrpc.api.Schema`
    - `RULE_TYPE_SCHEMA` is now a `GameRuleType` instead of a `GameRulesService$RuleType`
    - `TYPED_GAME_RULE_SCHEMA` is now a `GameRulesService$GameRuleUpdate` instead of a `GameRulesService$TypedRule`
    - `UNTYPED_GAME_RULE_SCHEMA` is now a `GameRulesService$GameRuleUpdate` instead of a `GameRulesService$UntypedRule`
- `net.minecraft.server.jsonrpc.internalapi`
    - `GameRules` interface is removed
    - `MinecraftGameRuleService#getRule` -> `getRuleValue`
- `net.minecraft.server.jsonrpc.methods.GameRulesService`
    - `$RuleType` is removed
    - `$TypedRule`, `$UntypedRule` -> `$GameRuleUpdate`, not one-to-one
- `net.minecraft.server.notifications.NotificationService#onGameRuleChanged` now takes in the `GameRule` and value instead of the string key and `$Value` wrapper
- `net.minecraft.world.level.GameRules`
    - The static rule keys are now located in `.gamerules.GameRules` without the `RULE_` prefix and underscores in-between words
        - `DO` is removed from the name (e.g. `RULE_DOENTITYDROPS` -> `ENTITY_DROPS`)
        - `SPAWNING` names now start with `SPAWN_` (e.g. `RULE_DOMOBSPAWNING` -> `SPAWN_MOBS`)
    - The map behavior linking the key to its associated value is now handled by `GameRuleMap`
        - `getBoolean`, `getInteger` -> `get`
    - `$Key`, `$Type` -> `GameRule`, not one-to-one
        - `GameRule` implements `FeatureElement`
    - `$Category` -> `GameRuleCategory`, not one-to-one
    - `$Value`, `$BooleanValue`, `$IntegerValue` are removed, replaced with the direct object being wrapped
    - `$GameRuleTypeVisitor` -> `GameRuleTypeVisitor`

## Minor Migrations

The following is a list of useful or interesting additions, changes, and removals that do not deserve their own section in the primer.

### Usage Annotations

Mojang has recently given some integer values and flags an annotation, marking its intended usage. This does not affect modders in any way, as it likely seems to be a way to perform static analysis on the values passed around, probably for some kind of validation.

- `com.mojang.blaze3d.buffers.GpuBuffer$Usage` - An annotation that marks whether a given integer defines the usage flags of the particular buffer.
- `com.mojang.blaze3d.platform.InputConstants$Value` - An annotation that marks whether a given integer defines the input of a device.
- `com.mojang.blaze3d.buffers.GpuTexture$Usage` - An annotation that marks whether a given integer defines the usage flags of the particular texture.
- `net.minecraft.client.input`
    - `InputWithModifiers$Modifiers` - An annotation that marks whether a given integer defines the modifiers of an input.
    - `KeyEvent$Action` - An annotation that marks whether a given integer defines the action being performed by the input (i.e., press, release, repeat).
    - `MouseButtonInfo`
        - `$Action` - An annotation that marks whether a given integer defines the action being performed by the mouse (i.e., press, release, repeat).
        - `$MouseButton` - An annotation that marks whether a given integer defines the input of a mouse.
- `net.minecraft.server.level.TicketType$Flags` - An annotation that marks whether a given integer defines the flags of a ticket type.
- `net.minecraft.world.level.block.Block$UpdateFlags` - An annotation that marks whether a given integer defines the flags for a block update.

### Text Collectors

`ActiveTextCollector` is a method of submitting strings and components to render, meant to provide common utilities for alignment, especially with text that goes off the screen. While this does not necessarily replace `GuiGraphics#drawString`, a few widgets require the use of the `ActiveTextCollector`, such as `AbstractStringWidget#renderLines`.

An `ActiveTextCollector` can be created by calling one of the `GuiRenderer#textRenderer*` methods. They take in a `$HoveredTextEffects`, which handles how to render the component hover and click event, and a `Style` consumer callback for any additional handling. It also stores a set of default parameters, which basically represent the current pose opacity and screen rectangle.

There are two methods two submit a piece of text for rendering: `accept` for standard strings, and `acceptScrolling*` for screens that go out of the rectangle, scrolling back and forth on the screen at a rate of roughly one unit per second (see the accessibility settings for an example). `accept` takes in, at most, five parameters: the alignment of the X position (`TextAlignment#LEFT` is like normal, `CENTER` the center of the text, `RIGHT` the end of the text), the X position for the alignment, Y position, parameters to override, and the text. `acceptScrolling` takes in, at most, seven parameters: the text, the starting X position center aligned, the leftmost X position, the rightmost X position, the topmost Y position, the bottommost Y position, and the parameters to override.

```java
// In some method with GuiGraphics graphics
ActiveTextCollector collector = graphics.textRenderer(
    // Render hover and click events
    HoveredTextEffects.TOOLTIP_AND_CURSOR;
);

collector.accept(
    // Align the text to the center
    TextAlignment.CENTER,
    // Start X (in this case the center position)
    20,
    // Start Y
    0,
    // The parameters to use
    collector.defaultParameters(),
    // The text to display
    Component.literal("Hello world!")
);
```

- `net.minecraft.client.gui`
    - `ActiveTextCollector` - A helper for rendering text with certain parameters and alignments.
    - `GuiGraphics` now takes in the mouse XY
        - `textRenderer*` - Methods for constructing the helper for submit text in their appropriate location.
        - `$HoveredTextEffects` - An enum that defines the text effects to apply when using the text collector.
- `net.minecraft.client.gui.components`
    - `AbstractButton` now extends `AbstractWidget$WithInactiveMessage` instead of `AbstractWidget`
        - `renderWidget` is now final
            - Use `renderContents` instead to submit elements
            - `renderDefaultSprite` should be called in `renderContents` to blit the default sprite
        - `renderString` -> `renderDefaultLabel`, not one-to-one
    - `AbstractSliderButton` now extends `AbstractWidget$WithInactiveMessage` instead of `AbstractWidget`
    - `AbstractStringWidget`
        - `visitLines` - Handles submitting text elements to the screen.
        - `setColor`, `getColor` are removed
            - Use the `ActiveTextCollector` in `visitLines` instead
        - `setComponentClickHandler` - Set the handler for when a component with the provided style is clicked.
    - `AbstractWidget`
        - `renderScrollingString` -> `renderScrollingStringOverContents`, not one-to-one
        - `getAlpha` - Gets the alpha of the widget.
        - `$WithInactiveMessage` - A widget that can change the message to display when inactive.
    - `Button` is now abstract
        - `$Plain` replicates the previous behavior
    - `ChatComponent`
        - `MESSAGE_BOTTOM_TO_MESSAGE_TOP` - The height of a chat component.
        - `render` now takes in a `Font` and a `boolean` for whether to change the curse on insertions
        - `captureClickableText` - Captures the clickable text to submit.
        - `handleChatQueueClicked` replaced by `QUEUE_EXPAND_ID`, not one-to-one
        - `getClickedComponentStyleAt` -> `$ChatGraphicsAccess#handleMessage`, not one-to-one
        - `getMessageTagAt` -> `$ChatGraphicsAccess#handleTag`, `handleTagIcon`; not one-to-one
        - `getWidth`, `getHeight`, `getScale` are now private
        - `$AlphaCalculator` - Calculates the alpha for a given chat line.
        - `$ChatGraphicsAccess` - An interface for handling the submission of the chat input.
        - `$LineConsumer` no longer takes in the first three `int`s
    - `FittingMultilineTextWidget#setColor` is removed
        - Use the `ActiveTextCollector` in `visitLines` instead
    - `MultiLineLabel`
        - `render`, `getStyle` -> `visitLines`, not one-to-one
        - `$Align` -> `TextAlignment`
    - `MultiLineTextWidget#setColor`, `configureStyleHandling` are removed
        - Use the `ActiveTextCollector` in `visitLines` instead
    - `SplashRenderer` now takes in a `Component` instead of a `String`
    - `SpriteIconButton#renderSprite` - Submits the sprite icon.
    - `StringWidget#setColor` is removed
        - Use the `ActiveTextCollector` in `visitLines` instead
    - `TabButton` now extends `AbstractWidget$WithInactiveMessage` instead of `AbstractWidget`
        - `renderString` -> `renderLabel`, now private, not one-to-one
- `net.minecraft.client.gui.screens.inventory.BookViewScreen#getClickedComponentStyleAt` -> `visitText`, now private, not one-to-one

### Shared Text Areas Debugger

A new debug has been added that draws the bounding box each glyph, including the empty glyph. The color shifts slightly between each glyph for ease of differentiation, and changes entirely depending on if there is some combination of a click or hover event.

- `net.minecraft.SharedConstants#DEBUG_ACTIVE_TEXT_AREAS` - A flag for the debugger drawing the bounds and effects of each glyph.
- `net.minecraft.client.gui.Font`
    - `prepareText` now has an overload of whether to render something in the empty areas
    - `$GlyphVisitor`
        - `acceptGlyph` now takes in a `TextRenderable$Styled` instead of a `TextRenderable`
        - `acceptEmptyArea` - Accepts an empty area to draw to the screen.
    - `$PreparedTextBuilder` now takes in whether to include the empty areas for rendering
- `net.minecraft.client.gui.font`
    - `ActiveArea` - Defines the bounds and style of the area to draw.
    - `EmptyArea` - An area with nothing within it.
    - `PlainTextRenderable` now implements `TextRenderable$Styled` instead of `TextRenderable`
        - `width`, `height`, `ascent` - The bounds of the object.
    - `TextRenderable$Styled` - A text renderable that defines some active area for its bounds.
- `net.minecraft.client.gui.font.glyphs.BakedGlyph#createGlyph` now returns a `TextRenderable$Styled`

### JSpecify Annotations

Mojang has moved from using a mix of their own annotations to those available in JSpecify when required. As such, instead of all fields, methods, and parameters being marked as nonnull by default, it is replaced by `NullMarked`, which considers a type usage non-null unless explictly annotated as `Nullable`, barring some special cases.

- `com.mojang.blaze3d.FieldsAreNonnullByDefault`, `MethodsReturnNonnullByDefault` are removed
- `com.mojang.math.FieldsAreNonnullByDefault`, `MethodsReturnNonnullByDefault` are removed
- `net.minecraft.FieldsAreNonnullByDefault`, `MethodsReturnNonnullByDefault` are removed

### Slot Sources

Slot sources are an expansion upon the previous contents drop system in shulker boxes allowing any loot table to pulls its entries from some container slots. This can be used in any location where a `LootContext` is enabled, though it is currently only implemented as a loot pool entry.

In vanilla, a slot source works by having some `LootContextArg`, which points to some loot context param value, return an object that implements `SlotProvider`. Currently, this refers to any `Container` or `Entity` implementation. The `SlotProvider` is then used by `SlotSource#provide` to construct a `SlotCollection`: a stream of deep copied `ItemStack`s. The stacks stored in the collection are then passed to the output of the pool. As this is all done in one of the `SlotSource#provide` implementations, it can reference anything (not just `SlotProvider`) as long as it can transform that data into the `SlotCollection`.

```java
// A slot source whose 'slots' are the elements
// within an item tag.
public record TagSlotSource(TagKey<Item> tag) implements SlotSource {

    public static final MapCodec<TagSlotSource> MAP_CODEC = TagKey.codec(Registries.ITEM)
        .fieldOf("tag").xmap(TagSlotSource::new, TagSlotSource::tag);
    
    @Override
    public SlotCollection provide(LootContext ctx) {
        // Get the holder set for the tag
        Optional<HolderSet.Named<Item>> holderSetOpt = ctx.getResolver()
            .lookup(Registries.ITEM).flatMap(getter -> getter.get(this.tag));
        
        // Stream the elements and map to a SlotCollection
        return holderSetOpt.map(holderSet ->
            // `Item#getDefaultInstance` returns a new copy, so it can be used.
            // If the ItemStack already exists, then `ItemStack#copy` should be
            // called on each.
            (SlotCollection) () -> holderSet.stream().map(holder -> holder.value().getDefaultInstance())
        ).orElse(SlotCollection.EMPTY);
    }

    @Override
    public MapCodec<? extends SlotSource> codec() {
        // The codec used to serialize the slot source
        return MAP_CODEC;
    }
}

// The map codec needs to be registered to the slot source type registry
Registry.register(
    BuiltInRegistries.SLOT_SOURCE_TYPE
    Identifier.withNamespaceAndPath("examplemod", "tag"),
    TagSlotSource.MAP_CODEC
);
```

```json5
// An example loot table
{
    // ...
    "pools": [
        {
            "rolls": 1.0,
            "bonus_rolls": 0.0,
            "entries": [
                {
                    // Use the slot source loot pool
                    "type": "minecraft:slots",
                    "slot_source": {
                        // Our slot source
                        "type": "examplemod:tag",
                        "tag": "minecraft:planks"
                    }
                }
            ]
        }
        // ...
    ]
}
```

- `net.minecraft.advancements.criterion.SlotsPredicate#matches` now takes in a `SlotProvider` instead of an `Entity`
- `net.minecraft.core.registries.BuiltInRegistries#SLOT_SOURCE_TYPE`, `Registries#SLOT_SOURCE_TYPE` - Slot source type registry.
- `net.minecraft.world.Container` now extends `SlotProvider`
    - `getSlot` - Gets an access for a single item.
- `net.minecraft.world.entity`
    - `Entity` now implements `SlotProvider`
    - `SlotAccess`
        - `NULL` is removed
        - `forContainer` -> `forListElement`, not one-to-one
    - `SlotProvider` - An object that provides some access to its internal storage via slots.
- `net.minecraft.world.item.slot`
    - `CompositeSlotSource` - A composite of multiple slot sources.
    - `ContentsSlotSource` - Gets the slot contents.
    - `EmptySlotSource` - An empty slot source.
    - `FilteredSlotSource` - Filters the provided slot source pased on the item predicate.
    - `GroupSlotSource` - Groups multiple slot sources together into one concatenated collection.
    - `LimitSlotSource` - Limits the provided slot source to a maximum size.
    - `RangeSlotSource` - Gets the desired range of slots.
    - `SlotCollection` - A collection of slots to grab the item copies from.
    - `SlotSource` - Given a loot context, returns a collection of slots to provide.
    - `SlotSources` - The slot sources provided by vanilla.
    - `TransformedSlotSource` - Transforms the provided slot source.
- `net.minecraft.world.level.storage.loot.ContainerComponentManipulator#getSlots` - Gets the slots of a data component on the stack.
- `net.minecraft.world.level.storage.loot.entries`
    - `LootPoolEntries#SLOTS` - A pool that uses slots from a source.
    - `SlotLoot` - A pool that gets its items from some slot source.

### Zombie Nautilus Variant

Zombie nautilus are the newest addition to the variant datapack registry objects, taking in the familiar model and texture override along with the spawn conditions:

```json5
// A file located at:
// - `data/examplemod/zombie_nautilus_variant/example_zombie_nautilus.json`
{
    // Points to a texture at `assets/examplemod/textures/entity/nautilus/example_zombie_nautilus.png`
    "asset_id": "examplemod:entity/nautilus/example_zombie_nautilus",
    // Defines the `ZombieNautilusVariant$ModelType` that's used to select what entity model to render the zombie nautilus variant with
    "model": "warm",
    "spawn_conditions": [
        // The conditions for this variant to spawn
        {
            "priority": 0
        }
    ]
}
```

- `net.minecraft.core.component.DataComponents#ZOMBIE_NAUTILUS_VARIANT` - The variant of the zombie nautilus.
- `net.minecraft.core.registries.Registries#ZOMBIE_NAUTILUS_VARIANT` - The registry key for the zombie nautilus variant.
- `net.minecraft.network.syncher.EntityDataSerializers#ZOMBIE_NAUTILUS_VARIANT` - The variant of the zombie nautilus.
- `net.minecraft.world.entity.animal.nautilus`
    - `ZombieNautilusVariant` - A variant of a zombie nautilus.
    - `ZombieNautilusVariants` - All vanilla zombie nautilus variants.

### `OptionEnum` Removal

`OptionEnum` has been removed in favor of simply calling the `OptionInstance$Enum` constructor with the desired values and codec. As such, most `byId` methods have been replaced with some codec and the translatable entry is now stored as a `Component` than the translation key string.

- `net.minecraft.client`
    - `AttackIndicatorStatus` no longer implements `OptionEnum`
        - `byId` -> `LEGACY_CODEC`, not one-to-one
        - `getKey` -> `caption`, not one-to-one
    - `CloudStatus` no longer implements `OptionEnum`
        - `getKey` -> `caption`, not one-to-one
    - `InactivityFpsLimit` no longer implements `OptionEnum`
        - `getKey` -> `caption`, not one-to-one
    - `OptionInstance#forOptionEnum` is removed
    - `PrioritizeChunkUpdate` no longer implements `OptionEnum`
        - `getKey` -> `caption`, not one-to-one
        - `byId` -> `LEGACY_CODEC`, not one-to-one
- `net.minecraft.client.sounds.MusicManager$MusicFrequency` no longer implements `OptionEnum`
    - `getKey` -> `caption`, not one-to-one
- `net.minecraft.server.level.ParticleStatus` no longer implements `OptionEnum`
    - `getKey` -> `caption`, not one-to-one
    - `byId` -> `LEGACY_CODEC`, not one-to-one
- `net.minecraft.util.OptionEnum` is removed
- `net.minecraft.world.entity.HumanoidArm` no longer implements `OptionEnum`
    - `BY_ID` is now private
    - `getKey` -> `caption`, not one-to-one
- `net.minecraft.world.entity.player.ChatVisbility` no longer implements `OptionEnum`
    - `byId` -> `LEGACY_CODEC`, not one-to-one
    - `getKey` -> `caption`, not one-to-one

### Specific Logic Changes

- `net.minecraft.client.renderer.entity.EntityRenderState#lightCoords` now defaults to 0xF000F0.
- `net.minecraft.client.gui.screens.inventory.AbstractContainerScreen#keyPressed` no longer returns `true` if the key is not handled by the screen, instead returning `false`.
- `net.minecraft.util.Mth#clampedLerp` parameters have been reordered for both overloads. The methods now take in the step, the original value, and the next value; instead of the original value, next value, and the step value.

### Tag Changes

- `minecraft:biome`
    - `plays_underwater_music` is removed
        - Replaced by `BackgroundMusic#underwaterMusic` environment attribute
    - `has_closer_water_fog` is removed
        - Replaced by `EnvironmentAttributes#WATER_FOG_END_DISTANCE`
    - `increased_fire_burnout` is removed
        - Replaced by `EnvironmentAttributes#INCREASED_FIRE_BURNOUT`
    - `snow_golem_melts` is removed
        - Replaced by `EnvironmentAttributes#SNOW_GOLEM_MELTS`
    - `without_patrol_spawns` is removed
        - Replaced by `EnvironmentAttributes#CAN_PILLAGER_PATROL_SPAWN`
    - `spawns_coral_variant_zombie_nautilus`
- `minecraft:block`
    - `can_glide_through`
- `minecraft:entity_type`
    - `burn_in_daylight`
    - `can_float_while_ridden`
    - `can_wear_nautilus_armor`
    - `nautilus_hostiles`
- `minecraft:item`
    - `camel_husk_food`
    - `zombie_horse_food`
    - `nautilus_bucket_food`
    - `nautilus_food`
    - `nautilus_taming_items`
    - `spears`
    - `enchantable/lunge`
    - `enchantable/sword` -> `enchantable/melee_weapon`, `enchantable/sweeping`
- `minecraft:timeline`
    - `universal`
    - `in_overworld`
    - `in_nether`
    - `in_end`

### List of Additions

- `com.mojang.blaze3d.GraphicsWorkarounds#isAmd` - Whether the GPU's vendor is AMD.
- `com.mojang.blaze3d.opengl`
    - `GlConst#GL_POINTS` - Defines the points primitive as the type to render.
    - `GlTimerQuery` - The OpenGL implementation of querying an object, typically the time elapsed.
- `com.mojang.blaze3d.platform.InputConstants#MOUSE_BUTTON_*` - The inputs of a mouse click, represented by numbers as they may have different intended purposes.
- `com.mojang.blaze3d.systems`
    - `CommandEncoder#timerQueryBegin`, `timerQueryEnd` - Handlers for keeping track of the time elapsed.
    - `GpuQuery` - A query for an arbitrary object, such as the time elapsed.
- `com.mojang.blaze3d.vertex`
    - `DefaultVertexFormat`
        - `POSITION_COLOR_LINE_WIDTH` - A vertex format that specifies the position, color, and line width.
        - `POSITION_COLOR_NORMAL_LINE_WIDTH` - A vertex format that specifies the position, color, normal, and line width.
    - `VertexFormat$Mode#POINTS` - A vertex mode that draws points.
    - `VertexFormatElement#LINE_WIDTH` - A vertex element that takes in one float representing the width.
- `com.mojang.math`
    - `OctahedralGroup`
        - `BLOCK_ROT_*` - Constants representing the block rotations.
        - `permutation` - Returns the symmetric group.
    - `Quadrant#fromXYZAngles` - Gets the octahedral group that represents the three quadrant rotations.
    - `SymmetricGroup3#inverse` - Returns the inverse group.
- `net.minecraft`
    - `SharedConstants`
        - `MAX_CLOUD_DISTANCE` - The maximum cloud range to be rendered by the player.
        - `DEFAULT_RANDOM_TICK_SPEED` - The default random tick speed.
    - `Util#localizedDateFormatter` - Returns the localized `DateTimeFormatter` for the given style.
- `net.minecraft.advancements.criterion`
    - `DataComponentMatchers$Builder#any` - Matches whether there exists some data for the component.
    - `SpearMobsTrigger` - A trigger that checks the number of entities the player has speared with a kinetic weapon.
- `net.minecraft.client`
    - `GuiMessage`
        - `splitLines` - Splits the component into lines with the desired width.
        - `getTagIconLeft` - Gets the width of the content with an additional four pixel padding.
    - `KeyMapping$Category#DEBUG` - The debug keyboard category.
    - `MusicToastDisplayState` - An enum representing how the toast for music should be displayed.
    - `NarratorStatus#LEGACY_CODEC` - A codec to deserialize the enum narrator status.
    - `OptionInstance`
        - `$IntRangeBase`
            - `next` - Gets the next value.
            - `previous` - Gets the previous value.
        - `$SliderableEnum` - A slider that selects between enum options.
        - `$SliderableValueSet`
            - `next` - Gets the next value.
            - `previous` - Gets the previous value.
    - `Options`
        - `keyToggleGui` - A key mapping that toggles the in-game gui.
        - `keyToggleSpectatorShaderEffects` - A key mapping that toggles the shader effects tied to a camera entity.
        - `keyDebug*`, `debugKeys` - Key mappings for the debug renderers.
        - `weatherRadius` - Returns the radius of the weather particles to render in an area.
        - `cutoutLeaves` - Whether leaves should render in cutout or solid.
        - `vignette` - Whether a vignette should be applied to the screen.
        - `improvedTransparency` - Whether to use the transparency post processor.
        - `chunkSectionFadeInTime` - The amount of second that should be taken for a chunk to fade in when first rendered.
        - `maxAnisotropyBit` - The bit value of the anisotrophic filtering level.
        - `maxAnisotropyValue` - The ansiotrophic filtering level.
- `net.minecraft.client.animation.definitions.NautilusAnimation` - The animation definitions for the nautilus.
- `net.minecraft.client.data.models.ItemModelGenerators`
    - `generateSpear` - Generates the spear item model.
    - `generateItemWithTintedBaseLayer` - Generates a two layered item model whose base layer is tinted.
- `net.minecraft.client.data.models.model.ModelTemplates#SPEAR_IN_HAND` - A template for the spear in hand model.
- `net.minecraft.client.gui.components`
    - `AbstractButton#setOverrideRenderHighlightedSprite` - Overrides whether to use the focused enabled/disabled sprite.
    - `Checkbox#adjustWidth` - Sets the width of the widget using the message, font, and its initial X position.
    - `CycleButton`
        - `$Builder#withSprite` - Sets the supplier used to get the sprite based on the current button state.
        - `$DisplayState` - How the button shoud be displayed.
        - `$SpriteSupplier` - Gets the sprite location given the current button state.
    - `EditBox#setInvertHighlightedTextColor` - Sets whether to invert the highlighted text color.
    - `FocusableTextWidget`
        - `getPadding` - Returns the text padding.
        - `updateWidth` - Updates the width the text can take up.
        - `updateHeight` - Update the height the text can take up.
        - `$Builder` - Builds the component.
    - `MultiLineTextWidget#getTextX`, `getTextY` - Gets the text position.
    - `OptionsList`
        - `addHeader` - Adds a header entry.
        - `resetOption` - Resets the option value.
        - `$AbstractEntry` - Defines the element within the selection list.
        - `$HeaderEntry` - An entry that represents the header of a section.
        - `$OptionInstanceWidget` - A record containing the widget and optionally the option instance.
    - `ResettableOptionWidget` - A widget that can reset its value to a default.
    - `SelectableEntry` - A utility for checking whether the mouse is in a specific region.
- `net.minecraft.client.gui.layouts.HeaderAndFooterLayout#MAGIC_PADDING` - A common padding between the elements.
- `net.minecraft.client.gui.screens.advancements`
    - `AdvancementTab#canScrollHorizontally`, `canScrollVertically` - Checks whether the tab data can be scrolled in a given direction.
    - `AdvancementTabType#getWidth`, `getHeight` - Gets the width / height of the tab.
- `net.minecraft.client.gui.screens.debug.DebugOptionsScreen#getOptionList` - Returns the list of options for the debug screen.
- `net.minecraft.client.gui.screens.inventory`
    - `AbstractMountInventoryScreen` - A screen representing a mount's inventory.
    - `EffectsInInventory`
        - `SPACING` - The spacing between effects.
        - `SPRITE_SQUARE_SIZE` - The size of the effect icon.
    - `NautilusInventoryScreen` - The screen for the nautilus inventory.
- `net.minecraft.client.gui.screens.options`
    - `OptionsSubScreen#resetOption` - Resets the option value to its default.
    - `VideoSettingsScreen#updateTransparencyButton` - Sets the transparency button to the current option value.
- `net.minecraft.client.gui.screens.packs.TransferableSelectionList$PackEntry#ICON_SIZE` - The size of the pack icon.
- `net.minecraft.client.gui.screens.recipebook.RecipeBookTabButton#select`, `unselect` - Handles tab display selection.
- `net.minecraft.client.input.InputQuirks#EDIT_SHORTCUT_KEY_LEFT`, `EDIT_SHORTCUT_KEY_RIGHT` -> `InputWithModifiers#hasControlDownWithQuirk`, not one-to-one
- `net.minecraft.client.model.HumanoidModel$ArmPose`
    - `SPEAR` - The spear third person arm pose.
    - `animateUseItem` - Modifies the `PoseStack` given the entity state, use time, arm, and stack.
    - `affectsOffhandPose` - Whether the arm animation will affect the offhand pose.
- `net.minecraft.client.model.animal.nautilus`
    - `NautilusArmorModel` - The armor model for a nautilus.
    - `NautilusModel` - The model for a nautilus.
    - `NautilusSaddleModel` - The saddle model for a nautilus.
- `net.minecraft.client.model.effects.SpearAnimations` - The animations performed when using a spear.
- `net.minecraft.client.model.geom`
    - `ModelLayers`
        - `*NAUTILUS*` - The model layers for the nautilus.
        - `UNDEAD_HORSE*_ARMOR` - The armor model layers for the undead horse.
    - `PartName`
        - `INNER_MOUTH`, `LOWER_MOUTH` - Part names for a mouth.
        - `SHELL` - Part name for a shell.
        - `*_CORAL*` - Part names for the corals on a zombie nautilus.
- `net.minecraft.client.model.geom.builders.UVPair#pack`, `unpack*` - Handles packing/unpacking of a UV into a `long`.
- `net.minecraft.client.model.monster.nautilus.ZombieNautilusCoralModel` - The model for the warm variant of a zombie nautilus.
- `net.minecraft.client.model.monster.skeleton.SkeletonModel#createSingleModelDualBodyLayer` - Creates a parched layer definition.
- `net.minecraft.client.multiplayer`
    - `ClientPacketListener#hasClientLoaded` - Whether the client is loaded.
    - `MultiPlayerGameMode#piercingAttack` - Initiates a lunging attack.
- `net.minecraft.client.player.LocalPlayer#raycastHitResult` - Gets the hit result for the camera entity for the given partial tick.
- `net.minecraft.client.renderer`
    - `DynamicUniforms`
        - `CHUNK_SECTION_UBO_SIZE` - The uniform buffer object size for the chunk section.
        - `writeChunkSections` - Writes a varargs of chunk sections to the uniform storage.
        - `$ChunkSectionInfo` - The dynamic uniform for the chunk section.
    - `GameRenderer`
        - `updateCamera` - Calls the setup function for the camera.
        - `getPanoramicScreenshotParameters` - Get the screenshot parameters for panoramic mode.
    - `PanoramicScreenshotParameters` - The screenshot parameters for panoramic mode.
    - `Sheets#CELESTIAL_SHEET` - The atlas for the celestial textures.
- `net.minecraft.client.renderer.blockentity.BlockEntityWithBoundingBoxRenderer#STRUCTURE_VOIDS_COLOR` - The void color for a structure.
- `net.minecraft.client.renderer.chunk.SectionRenderDispatcher$RenderSection`
    - `getVisibility` - Returns the current alpha of the chunk.
    - `setFadeDuration` - Sets the amount of time it should take for a chunk to fade in.
    - `setWasPreviouslyEmpty`, `wasPreviouslyEmpty` - Handles whether the section did not previously exist. 
- `net.minecraft.client.renderer.entity`
    - `CamelHuskRenderer` - The entity renderer for a camel husk.
    - `CamelRenderer#createCamelSaddleLayer` - Creates the saddle layer for the camel.
    - `NautilusRenderer` - The entity renderer for a nautilus.
    - `ParchedRenderer` - The entity renderer for a parched.
    - `ZombieNautilusRenderer` - The entity renderer for a zombie nautilus.
- `net.minecraft.client.renderer.entity.state`
    - `ArmedEntityRenderState`
        - `swingAnimationType` - The animation to play when swinging their hand.
        - `ticksUsingItem` - How many ticks the item has been used for.
        - `getUseItemStackForArm` - Returns the held item stack based on the arm.
    - `LivingEntityRenderState#ticksSinceKineticHitFeedback` - The amount of ticks since this entity was hit with a kinetic weapon.
    - `NautilusRenderState` - The entity render state of a nautilus.
    - `UndeadRenderState` - The entity render state for an undead humanoid.
- `net.minecraft.client.renderer.item.ItemModelResolver#swapAnimationScale` - Gets the scale of the swap animation for the stack.
- `net.minecraft.client.renderer.state.LevelRenderState#gameTime` - The current game time.
- `net.minecraft.client.resources.SplashManager` component fields - The components for the special messages.
- `net.minecraft.client.resources.model`
    - `BlockModelRotation#IDENTITY` - The identity rotation.
    - `EquipmentClientInfo#NAUTILUS_*` - The layers for the nautilus.
- `net.minecraft.core.Vec3i`
    - `multiply` - Multiplies each component with a provided scalar.
    - `toMutable` - Returns a mutable `Vector3i`.
- `net.minecraft.data.AtlasIds#CELESTIAL_SHEET` - The atlas for the celestial textures.
- `net.minecraft.data.recipes.RecipeProvider#waxedChiseled` - The recipe for a waxed chiseled block.
- `net.minecraft.gametest.framework.GameTestHelper#getAbsoluteDirection` - Returns the absolute direction from the test relative direction.
- `net.minecraft.nbt.NbtAccounter`
    - `defaultQuota` - An accounter with a maximum of 2 MiB allocated.
    - `uncompressedQuota` - An accounter with a maximum of 100 MiB allocated.
- `net.minecraft.network.chat.MutableComponent#withoutShadow`, `Style#withoutShadow` - Removes the drop shadow from the text.
- `net.minecraft.network.protocol.game.ServerboundPlayerActionPacket$Action#STAB` - The player performed the stab action.
- `net.minecraft.network.syncher.EntityDataSerializers#HUMANOID_ARM` - The main hand of the humanoid.
- `net.minecraft.resources.Identifier#toShortString` - Returns the string of the location. Namespace is omitted if `minecraft`.
- `net.minecraft.server`
    - `MinecraftServer`
        - `getServerActivityMonitor` - Returns the monitor that sends the server activity notification.
        - `getStopwatches` - Returns a map of ids to timers.
    - `ServerScoreboard#storeToSaveDataIfDirty` - Writes the data if dirty.
- `net.minecraft.server.commands.StopwatchCommand` - A command that starts or stops a stopwatch.
- `net.minecraft.server.dedicated.DedicatedServerProperties#managementServerAllowedOrigins` - The origins a request from the management server can come from.
- `net.minecraft.server.jsonrpc.OutgoingRpcMethods#SERVER_ACTIVITY_OCCURRED` - A request made from the minecraft server about server activity occurring.
- `net.minecraft.server.jsonrpc.api.Schema`
    - `BOOL_OR_INT_SCHEMA` - A schema for a field that can be either a boolean or integer.
    - `typedCodec` - Returns the codec for the schema.
    - `info` - Returns a copy of the schema.
- `net.minecraft.server.level`
    - `ChunkMap#getChunkDataFixContextTag` - Returns the datafix tag for the chunk data.
    - `ServerLevel`
        - `getDayCount` - Gets the number of days that has passed.
        - `canSpreadFireAround` - Whether fire can spread at the given block position.
- `net.minecraft.server.network`
    - `EventLoopGroupHolder` - A holder for managing the event loop and channels for communicating with some end, whether local or socket-based.
    - `ServerGamePacketListenerImpl#resetFlyingTicks` - Resets how long the player has been flying.
- `net.minecraft.server.notifications`
    - `NotificationService#serverActivityOccurred` - Notifies the management server that activity has occurred.
    - `ServerActivityMonitor` - The monitor that sends the server activity notification
- `net.minecraft.util`
    - `ARGB`
        - `srgbToLinearChannel` - Converts the sRGB value into a linear color space.
        - `linearToSrgbChannel` - Converts the linear value into a sRGB color space.
        - `meanLinear` - Computes the mean using the linear color space for four values, then converting it back into sRGB.
        - `addRgb` - Adds the RGB channels, using the alpha from the first value.
        - `subtractRgb` - Subtracts the RGB channels, using the alpha from the first value.
        - `multiplyAlpha` - Multiplies the alpha value into the provided ARGB value.
        - `linearLerp` - Linearly interpolates the color by converting into the linear color space.
        - `white`, `black` - Colors with the provided alpha.
        - `alphaBlend` - Blends two colors along with their alpha value.
        - `vector4fFromARGB32` - Converts an ARGB value to four floats.
    - `Ease` - A utility full of easing functions.
    - `ExtraCodecs`
        - `NON_NEGATIVE_LONG`, `POSITIVE_LONG` - Longs with the listed constraints.
        - `longRange` - A long codec that validates whether it is between the provided range.
        - `STRING_RGB_COLOR`, `STRING_ARGB_COLOR` - A codec allowing for an (A)RGB value expressed in hex form as a string.
        - `MAX_PROPERTY_NAME_LENGTH`, `MAX_PROPERTY_VALUE_LENGTH`, `MAX_PROPERTY_SIGNATURE_LENGTH`, `MAX_PROPERTIES` - Constants related to serializing the property map.
    - `Mth`
        - `cube` - Cubes a number.
        - `chessboardDistance` - Computes the absolute maximum difference between two pairs of coordinates; the larger axis difference is returned.
    - `SpecialDates` - A utility containing the dates that Mojang changes some behavior or rendering for.
    - `TriState`
        - `CODEC` - The codec for the tristate.
        - `from` - Turns a boolean into a tristate.
- `net.minecraft.util.profiling.jfr.JvmProfiler#onClientTick` - Runs on client tick, taking in the current FPS.
- `net.minecraft.util.profiling.jfr.event.ClientFpsEvent` - An event that keeps track of the client FPS.
- `net.minecraft.util.profiling.jfr.stats.FpsStat` - A record containing the client FPS.
- `net.minecraft.world`
    - `LockCode#canUnlock` - Whether the given player can unlock this code.
    - `Stopwatch` - A record that holds the creation time and amount of time that has elapsed.
    - `Stopwatches` - A tracker for starting, managing, and stopping stopwatches.
- `net.minecraft.world.effect`
    - `MobEffects#BREATH_OF_THE_NAUTILUS` - Prevents the user from losing air underwater.
    - `MobEffectUtil#shouldEffectsRefillAirsupply` - Whether the entity has an effect that refills the air supply while under a liquid.
- `net.minecraft.world.entity`
    - `Entity`
        - `getHeadLookAngle` - Calculates the view vector of the head rotation.
        - `updateDataBeforeSync` - Updates the data stored in the entity before syncing to the client.
        - `computeSpeed` - Computes last known speed and position of the entity.
        - `getKnownSpeed` - Gets the last known speed of the entity.
        - `hasMovedHorizontallyRecently` - If the last known speed's horizontal distance is larger than 0, more specifically the margin of error.
    - `EntityProcessor` - A post processor for an entity when loading.
    - `EntityEvent#KINETIC_HIT` - An event fired when an entity is hit with a kinetic weapon.
    - `HumanoidArm#STREAM_CODEC` - The network codec for the arm enum.
    - `LivingEntity`
        - `DEFAULT_KNOCKBACK` - The default knockback applied to an entity on hit.
        - `itemSwapTicker` - The amount of time taken when swapping items.
        - `recentKineticEnemies` - The attackers that have recently attacked with a kinetic weapon.
        - `lungeForwardMaybe` - Apply the lunge effects.
        - `causeExtraKnockback` - Applies an multiplicative force to the knockback.
        - `wasRecentlyStabbed`, `rememberStabbedEntity` - Handles enemies that were stabbed with a kinetic weapon.
        - `stabAttack` - Handles when a mob is stabbed by this entity.
        - `onAttack` - Handles when this entity has attacked another entity.
        - `getTicksUsingItem` - Returns the number of ticks this item has been used for.
        - `getTicksSinceLastKineticHitFeedback` - The number of ticks that has passed since this entity was last hit with a kinetic weapon.
        - `shouldTravelInFluid` - If this entity should travel in the given fluid.
        - `travelInWater` - Moves an entity as if they were in water.
    - `Mob#sunProtectionSlot` - The equipment slot that protects the entity from the sun.
    - `NeutralMob#level` - Returns the level the entity is in.
    - `PlayerRideableJumping#getPlayerJumpPendingScale` - Returns the scalar to apply to the entity on player jump.
- `net.minecraft.world.entity.ai.attributes.Attributes#DEFAULT_ATTACK_SPEED` - The default attack speed.
- `net.minecraft.world.entity.ai.memory.MemoryModuleType`
    - `CHARGE_COOLDOWN_TICKS` - The number of cooldown ticks after a charge attack.
    - `ATTACK_TARGET_COOLDOWN` - The number of cooldown ticks before attacking a target.
- `net.minecraft.world.entity.ai.sensing.TemptingSensor#forAnimal` - A sensor that special cases animal entities for check if the desired item is food.
- `net.minecraft.world.entity.animal.camel`
    - `Camel`
        - `getDashingSound`, `getDashReadySound` - Camel dashing sounds.
        - `getStandUpSound`, `getSitDownSound` - Camel sit/stand sounds.
        - `getSaddleSound` - Camel saddle sound.
    - `CamelHusk` - The camel husk entity.
- `net.minecraft.world.entity.animal.equine.AbstractHorse#isMobControlled` - Whether a mob can control this horse.
- `net.minecraft.world.entity.animal.nautilus`
    - `AbstractNautilus` - The core of the nautilus entity.
    - `Nautilus` - The nautilus entity.
    - `NautilusAi` - The brain of a nautilus.
    - `ZombieNautilus` - The zombie nautilus entity.
    - `ZombieNautilusAi` - The brain of a zombie nautilus.
- `net.minecraft.world.entity.decoration.HangingEntity#hasLevelCollision` - Whether this entity is colliding with a block or the border in a given bounds.
- `net.minecraft.world.entity.monster.skeleton.Parched` - The parched entity.
- `net.minecraft.world.entity.monster.zombie.Husk$HuskGroupData` - The group data for the husk.
- `net.minecraft.world.entity.player.Player`
    - `cannotAttackWithItem` - Checks whether the player cannot attack with the item.
    - `getItemSwapScale` - Returns the scalar to use for the item swap animation.
    - `resetOnlyAttackStrengthTicker` - Resets the attack strength ticker.
    - `openNautilusInventory` - Opens the inventory of the interacted nautilus.
    - `applyPostImpulseGraceTime`, `isInPostImpulseGraceTime` - Handles the grace time between impulses.
- `net.minecraft.world.food.FoodData#hasEnoughFood` - Whether the current food level is greater than 6 hunger (or three full hunger bars).
- `net.minecraft.world.inventory`
    - `AbstractMountInventoryMenu` - The inventory menu for a mount.
    - `NautilusInventoryMenu` - The inventory menu of a nautilus.
- `net.minecraft.world.item`
    - `HoneycombItem#WAXED_RECIPES` - A map of waxed block to their recipe categories and name.
    - `Item$Properties`
        - `spear` - Adds the spear components.
        - `nautilusArmor` - Adds the nautilus armor components.
    - `ItemStack#matchesIgnoringComponents` - Whether the stack matches ignoring all components that match the predicate.
    - `ItemUseAnimation`
        - `TRIDENT` - The trident use animation.
        - `hasCustomArmTransform` - Whether the animation provides a custom transform to the arm.
- `net.minecraft.world.item.enchantment`
    - `Enchantment#doLunge` - Applies the post piercing attack effect.
    - `EnchantmentEffectComponents#POST_PIERCING_ATTACK` - The effect to apply after a piercing attack.
    - `EnchantmentHelper#doLungeEffects` - Applies the effect on lunge.
    - `LevelBasedValue$Exponent` - Applies an exponent given the base and power.
- `net.minecraft.world.item.enchantment.effects`
    - `ApplyEntityImpulse` - An entity effect that adds an impulse in the direction of the look angle.
    - `ApplyExhaustion` - An entity effect that applies food exhaustion to the player if they are using the enchanted item.
    - `ScaleExponentially` - A value effect that multiplies the value by a number raised to some exponent.
- `net.minecraft.world.level`
    - `Chunk#isValid` - Whether the chunk pos is within the maximum allowed coordinate world (within the 30 million block radius).
    - `CollisionGetter`
        - `noEntityCollision` - Whether the entity is not colliding with another entity in the given bounds.
        - `noBorderCollision` - Whether the entity is not colliding with the world border in the given bounds.
    - `Level#isInValidBounds` - Whether the block position is not outside the maximum allowed coordinate world (build height for Y axis, 30 million block radius for XZ axis).
    - `MoonPhase` - An enum representing the phases of the moon.
- `net.minecraft.world.level.border.WorldBorder$MovingBorderExtent#getPreviousSize` - Gets the previous size of the border.
- `net.minecraft.world.level.chunk.storage`
    - `IOWorker#STORE_EMPTY` - A supplied `null` tag.
    - `LegacyTagFixer` - An interface that handles how to upgrade a tag, like for the chunk.
    - `SimpleRegionStorage`
        - `isOldChunkAround` - Whether the chunk from a previous version still exists in this version.
        - `injectDatafixingContext` - When the context is not `null`, adds it to the given tag.
        - `markChunkDone` - Marks a chunk as finished for upgrading to the current version.
        - `chunkScanner` - Gets the access used to scan chunks.
- `net.minecraft.world.level.levelgen.structure.LegacyStructureDataHandler#LAST_MONOLYTH_STRUCTURE_DATA_VERSION` - Returns the last data version containing glitched monolyths.
- `net.minecraft.world.level.storage.loot.LootContextArg` - An argument for a loot context to query.
- `net.minecraft.world.level.storage.loot.functions.DiscardItem` - A loot function that discards the loot, returning an empty stack.
- `net.minecraft.world.phys.Vec3`
    - `offsetRandomXZ` - Offsets the point by a random amount in the XZ direction.
    - `rotation` - Computes the rotation of the vector.
    - `applyLocalCoordinatesToRotation` - Adds the components relative to the current rotation of the vector.
    - `isFinite` - Returns whether all components of the vector are finite (not NaN or infinity) values.
- `net.minecraft.world.scores`
    - `Scoreboard`
        - `packPlayerTeams` - Packs the player teams into a serializable format.
        - `packObjectives` - Packs the objectives into a serializable format.
        - `packDisplaySlots` - Packs the display slots into a serializable format.
    - `ScoreboardSaveData`
        - `getData`, `setData` - Handles the packed scoreboard.
        - `Packed$EMPTY` - Represents an empty scoreboard.
- `net.minecraft.world.waypoints.Waypoint$Icon#copyFrom` - Copies the icon color and style from another icon.

### List of Changes

- `com.mojang.blaze3d.platform.Lighting#updateLevel` now takes in a `DimensionType$CardinalLightType` instead of a boolean for whether the level is the nether or not
- `com.mojang.blaze3d.systems.GpuDevice#createTexture` now has an overload that takes in a supplied label instead of the raw string
- `com.mojang.blaze3d.vertex.VertexConsumer`
    - `addVertex`, `addVertexWith2DPose` now take in the interface, 'read only' variants of its arguments (e.g., `Vector3f` -> `Vector3fc`)
    - `putBulkData` no longer takes the final `boolean` to read the buffer data to determine the initial color
- `com.mojang.math`
    - `OctahedralGroup`
        - `fromXYAngles` -> `Quadrant#fromXYAngles`
        - `permute` -> `SymmetricGroup3#permuteAxis`
    - `SymmetricGroup3`
        - `permutation` -> `permute`
        - `permuteVector` -> `OctahedralGroup#rotate`
    - `Transformation` now takes in the interface, 'read only' variants of its arguments (e.g., `Vector3f` -> `Vector3fc`)
        - This also applies to the argument getter methods
- `net.minecraft`
    - `FileUtil#isValidStrictPathSegment` -> `containsAllowedCharactersOnly`, now private
        - Replaced by `isValidPathSegment`
    - `Minecraft`
        - `disconnectWithProgressScreen` now takes in a `boolean` of whether to stop the sound engine
        - `disconnect` now takes in a `boolean` of whether to stop the sound engine
    - `SharedConstants`
        - `DEBUG_WATER` -> `DebugScreenEntries#VISUALIZE_WATER_LEVELS`, not one-to-one
        - `DEBUG_HEIGHTMAP` -> `DebugScreenEntries#VISUALIZE_HEIGHTMAP`, not one-to-one
        - `DEBUG_COLLISION` -> `DebugScreenEntries#VISUALIZE_COLLISION_BOXES`, not one-to-one
        - `DEBUG_SUPPORT_BLOCKS` -> `DebugScreenEntries#VISUALIZE_ENTITY_SUPPORTING_BLOCKS`, not one-to-one
        - `DEBUG_LIGHT` -> `DebugScreenEntries#VISUALIZE_BLOCK_LIGHT_LEVELS`, `VISUALIZE_SKY_LIGHT_LEVELS`; not one-to-one
        - `DEBUG_SKY_LIGHT_SECTIONS` -> `DebugScreenEntries#VISUALIZE_SKY_LIGHT_SECTIONS`, not one-to-one
        - `DEBUG_SOLID_FACE` -> `DebugScreenEntries#VISUALIZE_SOLID_FACES`, not one-to-one
        - `DEBUG_CHUNKS` -> `DebugScreenEntries#VISUALIZE_CHUNKS_ON_SERVER`, not one-to-one
- `net.minecraft.advancements.criterion.EntityFlagsPredicate` now takes in optional booleans for if the entity is in water or fall flying
    - The associated `$Builder` methods have also been added
- `net.minecraft.client`
    - `Camera`
        - `setup` now takes in a `Level` instead of a `BlockGetter`
        - `get*` has been replaced by their record alternatives (e.g. `getEntity` -> `entity`)
        - `Vector3f` return values are replaced with `Vector3fc`
    - `GraphicsStatus` -> `GraphicsPreset`, not one-to-one
    - `KeyMapping` now has an overload that takes in the sort order
    - `MouseHandler#lastClickTime` -> `lastClick`, now private, not one-to-one
    - `OptionInstance$OptionInstanceSliderButton` now implements `ResettableOptionWidget`
    - `Options`
        - `graphicsMode` -> `graphicsPreset`, `applyGraphicsPreset`
        - `showNowPlayingToast` -> `musicToast`, not one-to-one
- `net.minecraft.client.data.models`
    - `EquipmentAssetProvider#humanoidAndHorse` -> `humanoidAndMountArmor`
    - `ItemModelGenerators`
        - `getSpans` -> `getSideFaces`, not one-to-one
        - `$SpanFacing` -> `$SideDirection`, not one-to-one
        - `$Span` -> `$SideFace`, not one-to-one
    - `ItemModelOutput#accept` now has an overload that takes in the `ClientItem$Properties`
- `net.minecraft.client.gui`
    - `Font#NO_SHADOW` -> `Style#NO_SHADOW`
    - `GuiGraphics`
        - `textHighlight` now takes in a `boolean` of whether to render the background rectangle
        - `submitOutline` -> `renderOutline`
- `net.minecraft.client.gui.components`
    - `AbstractButton#handleCursor` -> `handleCursor`, now protected
    - `AbstractSliderButton`
        - `HANDLE_WIDTH` is now protected
        - `canChangeValue`, `setValue` are now protected
    - `AbstractWidget#message` is now protected
    - `CycleButton` now implements `ResettableOptionWidget`
        - `builder` now has an overload to take in a supplied default value
        - `booleanBuilder` now takes in a boolean to choose which component to default to
        - `$Builder` now takes in a supplied default value
            - `displayOnlyValue(boolean)` -> `displayState`, not one-to-one
    - `FocusableTextWidget` constructor is now package private, use `builder` instead
    - `OptionsList` now passes in an `$AbstractEntry` to the generic rather than an `$Entry`
        - `addSmall` now has an overload that takes in an `OptionInstance`
        - `$Entry` now extends `$AbstractEntry`
        - `$OptionEntry` class is removed
            - `big` -> `$Entry#big`
            - `small` -> `$Entry#small`
    - `StringWidget#clipText` is now public static, taking in the `Font`
- `net.minecraft.client.gui.components.debug`
    - `DebugScreenEntryList`
        - `toggleF3Visible` -> `toggleDebugOverlay`
        - `setF3Visible` -> `setOverlayVisible`
        - `isF3Visible` -> `isOverlayVisible`
    - `DebugScreenEntryStatus#IN_F3` -> `IN_OVERLAY`
- `net.minecraft.client.gui.components.debugchart.AbstractDebugChart#COLOR_GREY` -> `CommonColors#TEXT_GRAY`
- `net.minecraft.client.gui.components.toasts.ToastManager`
    - `createNowPlayingToast` -> `initializeMusicToast`, now private, not one-to-one
    - `removeNowPlayingToast` -> `setMusicToastDisplayState`, not one-to-one
- `net.minecraft.client.gui.navigation.ScreenRectangle#transform*` methods now take in the interface, 'read only' variants of its arguments (e.g., `Vector3f` -> `Vector3fc`)
- `net.minecraft.client.gui.render.state.*` now take in the interface, 'read only' variants for its `pose` (e.g., `Vector3f` -> `Vector3fc`)
    - `GuiTextRenderState` now takes in whether to draw the empty space around each glyph
- `net.minecraft.client.gui.screens`
    - `DeathScreen` now takes in the `LocalPlayer`
    - `Screen` now has an overload that takes in the `Minecraft` instance and `Font` to use
        - `minecraft` is now final
        - `font` is now final
        - `init(Minecraft, int, int)` -> `init(int, int)`
        - `resize(Minecraft, int, int)` -> `init(int, int)`
        - `handleComponentClicked` -> `ChatScreen#handleComponentClicked`, now private
        - `handleClickEvent` has been moved to their associated classes instead of one super interface (e.g., `BookViewScreen#handleClickEvent`)
- `net.minecraft.client.gui.screens.advancements`
    - `AdvancementsScreen#renderWindow` now takes in the mouse XY `int`s
    - `AdvancementTab#drawTab` now takes in the mouse XY `int`s
- `net.minecraft.client.gui.screens.debug.DebugOptionsScreen$OptionList` is now public
- `net.minecraft.client.gui.screens.inventory`
    - `AbstractCommandBlockEditScreen#populateAndSendPacket` no longer takes in the `BaseCommandBlock`
    - `AbstractContainerScreen#renderSlots`, `renderSlot` now take in the mouse XY `int`s
    - `CreativeModeInventoryScreen#renderTabButton` now takes in the mouse XY `int`s
    - `EffectsInInventory#renderEffects` -> `render`
    - `HorseInventoryScreen` now extends `AbstractMountInventoryScreen`
    - `MinecartCommandBlockEditScreen` now takes in a `MinecartCommandBlock` instead of a `BaseCommandBlock`
- `net.minecraft.client.gui.screens.multiplayer.ServerSelectionList$OnlineServerEntry` now implements `SelectableEntry`
- `net.minecraft.client.gui.screens.packs.TransferableSelectionList$PackEntry` now implements `SelectableEntry`
- `net.minecraft.client.gui.screens.recipebook`
    - `RecipeBookComponent#initFilterButtonTextures` -> `getFilterButtonTextures`, not one-to-one
    - `RecipeBookTabButton` now implements `ImageButton` instead of `StateSwitchingButton`
        - The constructor now takes in the XY position along with the `Button$OnPress` consumer
- `net.minecraft.client.gui.screens.worldselection.WorldSelectionList$WorldListEntry` is no longer static, now implements `SelectableEntry`
- `net.minecraft.client.model`
    - `AnimationUtils`
        - `animateCrossbowCharge` now takes in a `float` instead of an `int`
        - `animateZombieArms` now takes in an `UndeadRenderState` instead of two `float`s
    - `HumanoidModel`
        - `setupAttackAnimation` no longer takes in a `float`
        - `getArm` is now public from protected
- `net.minecraft.client.model.geom.ModelPart#getExtentsForGui` now takes in a `Consumer<Vector3fc>` instead of a set
- `net.minecraft.client.model.geom.builders.UVPair` is now a record
- `net.minecraft.client.multiplayer`
    - `MultiPlayerGameMode#isAlwaysFlying` -> `isSpectator`
    - `ServerStatusPinger#pingServer` now takes in an `EventLoopGroupHolder`
- `net.minecraft.client.renderer`
    - `CloudRenderer#render` now takes in the game time `long`
    - `DynamicUniforms#writeTransform`, `$Transform` no longer take in the line width `float`
    - `GameRenderer#setPanoramicMode` -> `setPanoramicScreenshotParameters`, not one-to-one
    - `GlobalSettingsUniform#update` now takes in the `Camera` and whether to use Rotated Grid Super Sampling (RGSS)
    - `ItemBlockRenderTypes#setFancy` -> `setCutoutLeaves`
    - `ItemInHandRenderer` no longer takes in the `ItemRenderer`
    - `LevelRenderer#isSectionCompiled` -> `isSectionCompiledAndVisible`
    - `RenderPipelines`
        - `LINE_STRIP` -> `LINES` or `LINES_TRANSLUCENT`, not one-to-one
        - `DEBUG_LINE_STRIP` -> `DEBUG_POINTS`, not one-to-one
    - `RenderType`
        - `LINE_STRIP`, `lineStrip` -> `RenderTypes#LINES`, `LINES_TRANSLUCENT`, `linesTranslucent`; not one-to-one
        - `debugLineStrip` -> `debugPoint`, not one-to-one
    - `SkyRenderer` now takes in the `TextureManager` and `AtlasManager`
        - `extractRenderState` now takes in a `Camera` instead of the camera position
        - `renderSunMoonAndStars` now takes in a `MoonPhase` instead of an `int`
    - `UniformValue`
        - `$IVec3Uniform` now takes in a `Vector3ic` instead of a `Vector3i`
        - `$Vec2Uniform` now takes in a `Vector2fc` instead of a `Vector2f`
        - `$Vec3Uniform` now takes in a `Vector3fc` instead of a `Vector3f`
        - `$Vec4Uniform` now takes in a `Vector4fc` instead of a `Vector4f`
    - `WeatherEffectRenderer#tickRainParticles` now takes in an `int` for the weather radius
    - `WorldBorderRenderer#extract` now takes in a `float` for the partial tick
- `net.minecraft.client.renderer.blockentity`
    - `BannerRenderer#getExtents` now takes in a `Consumer<Vector3fc>` instead of a set
    - `BedRenderer#getExtents` now takes in a `Consumer<Vector3fc>` instead of a set
    - `BellRenderer#BELL_RESOURCE_LOCATION` -> `BELL_TEXTURE`
    - `DecoratedPotRenderer#getExtents` now takes in a `Consumer<Vector3fc>` instead of a set
    - `EnchantTableRenderer#BOOK_LOCATION` -> `BOOK_TEXTURE`
    - `ShulkerBoxRenderer#getExtents` now takes in a `Consumer<Vector3fc>` instead of a set
    - `TestInstanceRenderer` no longer takes in the `BlockEntityRendererProvider$Context`
- `net.minecraft.client.renderer.blockentity.state.BlockEntityWithBoundingBoxRenderState$InvisibleBlockType$STRUCUTRE_VOID` -> `STRUCTURE_VOID`
- `net.minecraft.client.renderer.chunk.ChunkSectionLayer#textureView` -> `texture`, not one-to-one
- `net.minecraft.client.renderer.entity.EntityRenderDispatcher` no longer takes in the `ItemRenderer`
- `net.minecraft.client.renderer.entity.layers`
    - `CarriedBLockLayer` no longer takes in the `BlockRenderDispatcher`
    - `IronGolemFlowerLayer` no longer takes in the `BlockRenderDispatcher`
    - `ItemInHandLayer#submitArmWithItem` now takes in the held `ItemStack`
- `net.minecraft.client.renderer.entity.state`
    - `ArmedEntityRenderState`
        - `*HandItem` -> `*HandItemState`, `*HandItemStack`; not one-to-one
        - `extractArmedRenderState` now takes in the partial tick `float`
    - `HorseRenderState#bodyArmorItem` -> `EquineRenderState#bodyArmorItem`
    - `HumanoidRenderState`
        - `attackTime` -> `ArmedEntityRenderState#attackTime`
        - `ticksUsingItem` is now a float
    - `IllagerRenderState` now extends `UndeadRenderState`
        - `ticksUsingItem` is now a float
    - `ZombieRenderState` now extends `UndeadRenderState`
    - `ZombifiedPiglinRenderState` now extends `UndeadRenderState`
- `net.minecraft.client.renderer.fog.FogRenderer#setupFog` no longer takes in the `boolean`
- `net.minecraft.client.renderer.fog.environment`
    - `AtmosphericFogEnvironment` now extends `FogEnvironment` instead of `AirBasedFogEnvironment`
    - `FogEnvironment#setupFog` no longer takes in the `Entity` and `BlockPos`, instead the `Camera`
- `net.minecraft.client.renderer.item.ClientItem$Properties` now takes in a float for changing the scale of the swap animation
- `net.minecraft.client.renderer.special.SpecialModelRenderer#getExtents` now takes in a `Consumer<Vector3fc>` instead of a set
- `net.minecraft.client.renderer.state.SkyRenderState#moonPhase` is now a `MoonPhase` instead of an `int`
- `net.minecraft.client.resources.SplashManager`
    - `prepare` now returns a list of `Component`s instead of strings
    - `apply` now takes in a list of `Component`s instead of strings
- `net.minecraft.client.resources.model.BlockModelRotation` is now a class
    - `by` -> `get`, not one-to-one
- `net.minecraft.client.resources.sounds`
    - `RidingHappyGhastSoundInstance` -> `RidingEntitySoundInstance`, not one-to-one
    - `RidingMinecartSoundInstance` now extends `RidingEntitySoundInstance` instead of `AbstractTickableSoundInstance`
        - The constructor now takes in the `SoundEvent`, volume min and max, and amplifier
    - `SimpleSoundInstance#forMusic` no longer takes in the volume
- `net.minecraft.client.sounds` 
    - `SoundEngine` no longer takes in the `MusicManager`
        - `updateCategoryVolume` -> `refreshCategoryVolume`
        - `setVolume` -> `updateCategoryVolume`, not one-to-one
    - `SoundManager` no longer takes in the `MusicManager`
        - `updateSourceVolume` -> `refreshCategoryVolume`
        - `setVolume` -> `updateCategoryVolume`, not one-to-one
- `net.minecraft.gametest.framework.GameTestHelper`
    - `spawn` now has an overload that takes in the `EntitySpawnReason` or three `int`s for the position
    - `assetTrue`, `assetFalse`, `assertValueEqual` now has an overload that takes in a `String` instead of a `Component`
    - `assertEntityData` now has an overload that takes in the `AABB` bounding box
    - `getRelativeBounds` is now public
    - `assertEntityPosition` -> `assertEntityPresent`, not one-to-one
- `net.minecraft.nbt`
    - `CompoundTag#remove` now returns the removed tag
    - `NbtUtils#getDataVersion` now has an overload that only takes in the `CompoundTag`
- `net.minecraft.network`
    - `Connection`
        - `NETWORK_WORKER_GROUP` -> `EventLoopGroupHolder#NIO`, not one-to-one
        - `NETWORK_EPOLL_WORKER_GROUP` -> `EventLoopGroupHolder#EPOLL`, not one-to-one
        - `LOCAL_WORKER_GROUP` -> `EventLoopGroupHolder#LOCAL`, not one-to-one
        - `connectToServer`, `connect` now take in an `EventLoopGroupHolder` instead of a `boolean`
    - `FriendlyByteBuf`
        - `writeVector3f` now takes in a `Vector3fc` instead of a `Vector3f`
        - `writeQuaternion` now takes in a `Quaternionfc` instead of a `Quaternionf`
        - `DEFAULT_NBT_QUOTA` -> `NbtAccounter#DEFAULT_NBT_QUOTA`
- `net.minecraft.network.codec`
    - `ByteBufCodecs`
        - `VECTOR3F` now uses a `Vector3fc` instead of a `Vector3f`
        - `QUATERNIONF` now uses a `Quaternionfc` instead of a `Quaternionf`
    - `StreamCodec#composite` now has ten and twelve parameter variants
- `net.minecraft.network.chat`
    - `ComponentUtils#mergeStyles` now has an overload that takes in and returns a `Component`
    - `MutableComponent` is now final
- `net.minecraft.network.protocol.game`
    - `ClientboundHorseScreenOpenPacket` -> `ClientboundMountScreenOpenPacket`
    - `ClientGamePacketListener#handleHorseScreenOpen` -> `handleMountScreenOpen`
    - `GamePacketTypes#CLIENTBOUND_HORSE_SCREEN_OPEN` -> `CLIENTBOUND_MOUNT_SCREEN_OPEN`
- `net.minecraft.network.numbers`
    - `FixedFormat` is now a record
    - `StyledFormat` is now a record
- `net.minecraft.network.syncher.EntityDataSerializers`
    - `VECTOR3` now uses a `Vector3fc` instead of a `Vector3f`
    - `QUATERNION` now uses a `Quaternionfc` instead of a `Quaternionf`
- `net.minecraft.server`
    - `MinecraftServer`
        - `isAllowedToEnterPortal` -> `ServerLevel#isAllowedToEnterPortal`
        - `isSpawningMonsters` -> `ServerLevel#isSpawningMonsters`
        - `isPvpAllowed` -> `ServerLevel#isPvpAllowed`
        - `isCommandBlockEnabled` -> `ServerLevel#isCommandBlockEnabled`
        - `isSpawnerBlockEnabled` -> `ServerLevel#isSpawnerBlockEnabled`
        - `getGameRules` -> `ServerLevel#getGameRules`
        - `isEpollEnabled` -> `useNativeTransport`
    - `ServerScoreboard` no longer implements its own saved data type, instead using the packed `ScoreboardSaveData`
        - `TYPE` -> `ScoreboardSavedData#TYPE`
- `net.minecraft.server.jsonrpc`
    - `IncomingRpcMethod` now takes in two generics for the parameters to the request and the result response
        - `$Builder` now has constructors for parameterless and parameter functions, replacing `$Factory`
            - `response`, `param` now take in their `Schema`s
    - `OutgoingRpcMethod$Factory` now takes in the generic params and result
- `net.minecraft.server.jsonrpc.api`
    - `MethodInfo`, `$Named` now takes in two generics for the parameters to the request and the result response
        - `PARAMS_CODEC` -> `paramsTypedCodec`, now private, not one-to-one
        - `MAP_CODEC` -> `typedCodec`, now package-private, not one-to-one
    - `ParamInfo` now takes in a generic for the parameter
        - `CODEC` -> `typedCodec`, not one-to-one
    - `ResultInfo` now takes in a generic for the result response
        - `CODEC` -> `typedCodec`, not one-to-one
    - `Schema` now takes in a generic for the type it represents
        - The constructor now takes in a list of types instead of an optional, an non-optional property map, non-oprional enuma values, and the codec to serialize the type
        - `ofTypes` now has an overload that takes in a list of types
    - `SchemaComponent` now takes in a generic for the type it represents
- `net.minecraft.server.jsonrpc.security.AuthenticationHandler` now implements `ChannelDuplexHandler` instead of `ChannelInboundHandlerAdapter`
    - The constructor now takes in a string set of allowed origins
    - `$SecurityCheckResult#allowed` now has an overload that specifies whether the token was sent through the websocket protocol
- `net.minecraft.server.level`
    - `ChunkMap` now extends `SimpleRegionStorage` instead of `ChunkStorage`
    - `ServerLevel#drop` no longer returns a `boolean`
- `net.minecraft.server.network.ServerConnectionListener`
    - `SERVER_EVENT_GROUP` -> `EventLoopGroupHolder#NIO`, not one-to-one
    - `SERVER_EPOLL_EVENT_GROUP` -> `EventLoopGroupHolder#EPOLL`, not one-to-one
- `net.minecraft.stats.ServerStatsCounter` now takes in a `Path` instead of a `File`
    - `parseLocal` -> `parse`, not one-to-one
    - `toJson` now returns a `JsonElement` instead of a `String`
- `net.minecraft.util`
    - `ARGB#lerp` -> `srgbLerp`
    - `ExtraCodecs` now use the interface, 'read only' variants for its generic (e.g., `Vector3f` -> `Vector3fc`)
    - `Mth`
        - `easeInOutSine` -> `Ease#inOutSine`
        - `sin`, `cos` now takes in a `double` instead of a `float`
        - `absMax` now has overloads that uses `int`s or `float`s
    - `TriState` now implements `StringRepresentable`
- `net.minecraft.util.profiling.jfr.Percentiles#evaluate` now has an overload that takes in an `int[]`
- `net.minecraft.util.profiling.jfr.parse.JfrStatsResult` now takes in an FPS stat
    - `tickTimes` -> `serverTickTimes`
- `net.minecraft.util.profiling.jfr.stats.TimedStatSummary#summary` now returns an optional of the `TimeStatSummary`
- `net.minecraft.util.worldupdate.WorldUpgrader`
    - `$AbstractUpgrader` no longer takes in a generic
        - `createStorage` now returns a `SimpleRegionStorage` instead of the generic
        - `tryProcessOnePosition` now takes in a `SimpleRegionStorage` instead of the generic
    - `$DimensionToUpgrade` no longer takes in a generic, instead using `SimpleRegionStorage`
- `net.minecraft.world.RandomSequences` no longer takes in the world seed
    - `codec` -> `CODEC`
    - `get`, `reset` now takes in the world seed
- `net.minecraft.world.entity`
    - `Avatar#DATA_PLAYER_MAIN_HAND` now uses a `HumanoidArm` generic instead of a byte
    - `Entity#hasImpulse` -> `needsSync`
    - `EntityType#loadEntityRecursive` now takes in an `EntityProcessor` instead of a `Function`
    - `LivingEntity#invulnerableDuration` -> `INVULNERABLE_DURATION`
    - `Mob#playAttackSound` -> `LivingEntity#playAttackSound`
    - `NeutralMob`
        - `TAG_ANGER_TIME` -> `TAG_ANGER_END_TIME`, not one-to-one
        - `getRemainingPersistentAngerTime` -> `getPersistentAngerEndTime`, not one-to-one
        - `setRemainingPersistentAngerTime` -> `setTimeToRemainAngry`, `setPersistentAngerEndTime`; second is not one-to-one
        - `getPersistentAngerTarget`, `setPersistentAngerTarget` now deal with `EntityReference`s
- `net.minecraft.world.entity.ai.sensing.SensorType#*_TEMPTATIONS` -> `FOOD_TEMPTATIONS`, not one-to-one
- `net.minecraft.world.entity.ai.util`
    - `GoalUtils`
        - `mobRestricted` now takes in a `double` instead of an `int`
        - `isRestricted` now has an overload that takes in a `Vec3`
    - `LandRandomPos`
        - `getPosAway` now has an overload that takes in an additional `double` for the start/end radians
        - `generateRandomPosTowardDirection` now takes in a `double` instead of an `int`
    - `RandomPos`
        - `generateRandomDirectionWithinRadians` now takes in `double`s for the start/end radians
        - `generateRandomPosTowardDirection` now takes in a `double` instead of an `int`
- `net.minecraft.world.entity.animal.equine.AbstractHorse#getInventorySize` -> `AbstractMountInventoryMenu#getInventorySize`
- `net.minecraft.world.entity.monster.Monster#checkMonsterSpawnRules` now expanded its type generic to extends `Mob` instead of `Monster`
- `net.minecraft.world.entity.monster.skeleton.Bogged#*_ATTACK_INTERVAL` -> `AbstractSkeleton#INCREASED_*_ATTACK_INTERVAL`
- `net.minecraft.world.entity.monster.zombie`
    - `Husk#checkHuskSpawnRules` -> `Monster#checkSurfaceMonsterSpawnRules`, not one-to-one
    - `Zombie`
        - `doUnderWaterConversion` now takes in the `ServerLevel`
        - `convertToZombieType` now takes in the `ServerLevel`
- `net.minecraft.world.entity.npc.villager`
    - `AbstractVillager`
        - `updateTrades` now takes in the `ServerLevel`
        - `addOffersFromItemListings` now takes in the `ServerLevel`
    - `Villager#shouldRestock` now takes in the `ServerLevel`
    - `VillagerTrades$ItemListing#getOffer` now takes in the `ServerLevel`
- `net.minecraft.world.entity.player.Player`
    - `openMinecartCommandBlock` now takes in a `MinecartCommandBlock` instead of a `BaseCommandBlock`
    - `sweepAttack` -> `doSweepAttack`, now private, not one-to-one
    - `respawn` -> `LocalPlayer#respawn`
    - `CLIENT_LOADED_TIMEOUT_TIME` -> `ServerGamePacketListenerImpl#CLIENT_LOADED_TIMEOUT_TIME`
    - `clientLoadedTimeoutTimer`, `tickClientLoadTimeout` -> `ServerGamePacketListenerImpl#tickClientLoadTimeout`
    - `hasClientLoaded` -> `ServerGamePacketListenerImpl#hasClientLoaded`
    - `setClientLoaded` -> `ServerGamePacketListenerImpl#markClientLoaded`, `markClientUnloadedAfterDeath`, `restartClientLoadTimerAfterRespawn`; not one-to-one
- `net.minecraft.world.entity.projectile.Projectile` constructor is now `protected` instead of package private
- `net.minecraft.world.entity.vehicle.VehicleEntity#shouldSourceDestroy` is now `protected` instead of package private
- `net.minecraft.world.entity.vehicle.minecart`
    - `AbstractMinecart` now takes in the `ServerLevel`
    - `MinecartCommandBlock$MinecartCommandBase` is now package-private
- `net.minecraft.world.inventory.HorseInventoryMenu` now extends `AbstractMountInventoryMenu`
- `net.minecraft.world.item.component.ItemAttributeModifiers#compute` now takes in the `Attribute` holder
- `net.minecraft.world.item.enchantment.effects.PlaySoundEffect` now takes in a list of sound events instead of a single
- `net.minecraft.world.level`
    - `BaseCommandBlock`
        - `performCommand` now takes in a `ServerLevel` instead of a `Level`
        - `onUpdated` now takes in a `ServerLevel`
        - `createCommandSourceStack` now takes in a `ServerLevel`
        - `$CloseableCommandBlockSource` now takes in a `ServerLevel`, with its constructor protected
    - `CollisionGetter#noBlockCollision` now has an overload that takes in an additional `boolean` of whether to check liquid collisions.
    - `Level#getGameTime` -> `LevelAccessor#getGameTime`
    - `LevelAccessor#getCurrentDifficultyAt` -> `ServerLevelAccessor#getCurrentDifficultyAt`
    - `LevelTimeAccess#getMoonPhase` now returns a `MoonPhase` instead of an `int`
- `net.minecraft.world.level.biome`
    - `AmbientAdditionsSettings` is now a record
    - `AmbientMoodSettings` is now a record
    - `AmbientParticleSettings` is now a record
- `net.minecraft.world.level.block.entity.BaseContainerBlockEntity#canUnlock` -> `sendChestLockedNotifications`, not one-to-one
- `net.minecraft.world.level.border`
    - `BorderChangeListener#onLerpSize` now takes in an additional `long` for the game time
    - `WorldBorder` can now take in the `WorldBorder$Settings`
        - `getMin*`, `getMax*` now have an overload that takes in the partial tick `float`
        - `lerpSizeBetween` now takes in an additional `long` for the game time
        - `applySettings` -> `applyInitialSettings`, not one-to-one
            - The original behavior can be replicated by passing the settings into the constructor
        - `$BorderExtent#getMin*`, `getMax*` now takes in the partial tick `float`
- `net.minecraft.world.level.chunk.storage`
    - `RecreatingSimpleRegionStorage` now takes in a supplied `LegacyTagFixer`
    - `SimpleRegionStorage` now takes in a supplied `LegacyTagFixer`
        - `write` now has an overload that takes in a supplied `CompoundTag`
        - `upgradeChunkTag` now has an overload that takes in a a nullable tag comntext
- `net.minecraft.world.level.dimension.DimensionType`
    - `MOON_PHASES` is now an array of `MoonPhase`s and private
    - `moonPhase` now returns a `MoonPhase` instead of an `int`
- `net.minecraft.world.level.levelgen.structure.LegacyStructureDataHandler` now implements `LegacyTagFixer`
    - The constructor now takes in the `DataFixer`
    - `removeIndex` -> `LegacyTagFixer#markChunkDone`
    - `updateFromLegacy` now private
    - `getLegacyStructureHandler` now takes in the `DataFixer`, a supplied `DimensionDataStorage`, and returns a supplied `LegacyTagFixer`
- `net.minecraft.world.level.levelgen.structure.structures.NetherFortressPieces$StartPiece` fields are now package-private
- `net.minecraft.world.level.saveddata.SavedDataType` no longer takes in a `SavedData$Context`, removing the function argument constructor
- `net.minecraft.world.level.storage`
    - `DimensionDataStorage` no longer takes in a `SavedData$Context`
    - `FileNameDateFormatter#create` -> `FORMATTER`
    - `LevelStorageSource`
        - `UNCOMPRESSED_NBT_QUOTA` -> `NbtAccounter#UNCOMPRESSED_NBT_QUOTA`, now `public`
        - `$LevelDirectory#corruptedDataFile`, `rawDataFile` now take in a `ZonedDateTime` instead of a `LocalDateTime`
- `net.minecraft.world.level.storage.loot.LootContext`
    - `$BlockEntityTarget` now implements `LootContextArg$SimpleGetter`
        - `getParam` -> `contextParam`
    - `$EntityTarget` now implements `LootContextArg$SimpleGetter`
        - `getParam` -> `contextParam`
    - `$ItemStackTarget` now implements `LootContextArg$SimpleGetter`
        - `getParam` -> `contextParam`
- `net.minecraft.world.level.storage.loot.functions`
    - `CopyComponentsFunction`
        - `$*Source` -> `$DirectSource`, not one-to-one
        - `$Source` -> `LootContextArg$Getter`, not one-to-one
    - `CopyNameFunction#copyName` now takes in a `LootContextArg` instead of a `$Source`
        - `$Source` -> `LootContextArg`, not one-to-one
    - `FilteredFunction` now takes in an `Optional` pass and fail `LootItemFunction` instead of just a modifier
        - The function can now be builder through a `$Builder` via `filtered`
- `net.minecraft.world.phys.Vec3` now takes in a `Vector3fc` instead of a `Vector3f`
- `net.minecraft.world.phys.shapes.Shapes#rotateHorizontal`, `rotateAll`, `rotateAttachFace` now have overloads to take in the `OctahedralGroup`
- `net.minecraft.world.scores`
    - `Score` now has a public constructor for the `$Packed` value
        - `MAP_CODEC` -> `Score$Packed` ands its `$Packed#MAP_CODEC`
    - `Scoreboard$PackedScore#score` now takes in a `Score$Packed` instead of a `Score`
    - `ScoreboardSavedData` now takes in a `ScoreboardSaveData$Packed` instead of a `Scoreboard`
        - `FILE_ID` merged into type
        - `loadFrom` -> `ServerScoreboard#load`
        - `pack` -> `ServerScoreboard#store`, now private, not one-to-one

### List of Removals

- `com.mojang.blaze3d.vertex.VertexFormat$Mode#LINE_STRIP`
- `net.minecraft.Util#lastOf`
- `net.minecraft.client`
    - `Minecraft#useFancyGraphics`
    - `GuiMessage#icon`
    - `StringSplitter`
        - `formattedIndexByWidth`, `componentStyleAtWidth`
        - `splitLines(FormattedText, int, Style, FormattedText)`
- `net.minecraft.client.gui.Font#wordWrapHeight(String, int)`
- `net.minecraft.client.gui.components`
    - `CycleButton`
        - `onOffBuilder()`
        - `$Builder#withInitialValue`
    - `StateSwitchingButton`
- `net.minecraft.client.gui.screens.inventory`
    - `EffectsInInventory#renderTooltip`
    - `InventoryScreen#renderEntityInInventory`
- `net.minecraft.client.gui.screens.packs.PackSelectionScreen#clearSelected`
- `net.minecraft.client.player.LocalPlayer#USING_ITEM_SPEED_FACTOR`
- `net.minecraft.client.renderer`
    - `ItemModelGenerator#createOrExpandSpan`
    - `GpuWarnlistManager#dismissWarningAndSkipFabulous`, `isSkippingFabulous`
    - `RenderPipelines`
        - `DEBUG_STRUCTURE_QUADS`, `DEBUG_SECTION_QUADS`
    - `SkyRenderer#initTextures`
- `net.minecraft.client.renderer.fog.environment`
    - `AirBasedFogEnvironment`
    - `DimensionOrBossFogEnvironment`
    - `FogEnvironment#onNotApplicable`
- `net.minecraft.client.resources.model.BlockModelRotation#actualRotation`
- `net.minecraft.gametest.framework.GameTestHelper#setNight`, `setDayTime`
- `net.minecraft.network.FriendlyByteBuf#readDate`, `writeDate`
- `net.minecraft.server`
    - `MinecraftServer#hasGui`
    - `ServerScoreboard#createData`, `addDirtyListener`
- `net.minecraft.server.jsonrpc.IncomingRpcMethod$Factory`
- `net.minecraft.server.jsonrpc.methods.IllegalMethodDefinitionException`
- `net.minecraft.server.jsonrpc.security.AuthenticationHandler#AUTH_HEADER`
- `net.minecraft.util`
    - `DebugBuffer`
    - `LazyLoadedValue`
- `net.minecraft.util.thread.NamedThreadFactory`
- `net.minecraft.world.entity.Mob#isSunBurnTick`
- `net.minecraft.world.entity.animal.armadillo.ArmadilloAi#getTemptations`
- `net.minecraft.world.entity.animal.axolotl.AxolotlAi#getTemptations`
- `net.minecraft.world.entity.animal.camel.CamelAi#getTemptations`
- `net.minecraft.world.entity.animal.equine.ZombieHorse#checkZombieHorseSpawnRules`
    - Use `Monster#checkMonsterSpawnRules` instead
- `net.minecraft.world.entity.animal.goat.GoatAi#getTemptations`
- `net.minecraft.world.entity.animal.sniffer.SnifferAi#getTemptations`
- `net.minecraft.world.entity.player.Player#playNotifySound`
- `net.minecraft.world.entity.raid.Raid#TICKS_PER_DAY`
- `net.minecraft.world.level`
    - `BaseCommandBlock`
        - `getLevel`
        - `getUsedBy`, `getPosition`
    - `Level#TICKS_PER_DAY`
- `net.minecraft.world.level.border.WorldBorder$Settings#toWorldBorder`
    - Use the `WorldBorder` constructor instead
- `net.minecraft.world.level.chunk.storage`
    - `ChunkStorage`
    - `RecreatingChunkStorage`
- `net.minecraft.world.level.saveddata.SavedData$Context`
- `net.minecraft.world.phys.Vec3#fromRGB24`
