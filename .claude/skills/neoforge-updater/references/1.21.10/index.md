# Minecraft 1.21.9 -> 1.21.10 Mod Migration Primer

This is a high level, non-exhaustive overview on how to migrate your mod from 1.21.9 to 1.21.10. This does not look at any specific mod loader, just the changes to the vanilla classes. All provided names use the official mojang mappings.

This primer is licensed under the [Creative Commons Attribution 4.0 International](http://creativecommons.org/licenses/by/4.0/), so feel free to use it as a reference and leave a link so that other readers can consume the primer.

If there's any incorrect or missing information, please file an issue on this repository or ping @ChampionAsh5357 in the Neoforged Discord server.

Thank you to:

- @melanx for a typo

## Pack Changes

There are a number of user-facing changes that are part of vanilla which are not discussed below that may be relevant to modders. You can find a list of them on [Misode's version changelog](https://misode.github.io/versions/?id=1.21.10&tab=changelog).

## Minor Migrations

The following is a list of useful or interesting additions, changes, and removals that do not deserve their own section in the primer.

### List of Additions

- `net.minecraft.world.entity.decoration.HangingEntity#getPopBox` - Returns the bounding box indicating where the entity will pop off if colliding.

### List of Changes

`net.minecraft.world.level.block.state.BlockBehaviour#entityInside` now takes in a `boolean` indicating whether the entity is intersecting or inside the block
