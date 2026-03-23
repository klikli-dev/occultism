# Minecraft 1.21 -> 1.21.1 Mod Migration Primer

This is a high level, non-exhaustive overview on how to migrate your mod from 1.21 to 1.21.1. This does not look at any specific mod loader, just the changes to the vanilla classes. All provided names use the official mojang mappings.

This primer is licensed under the [Creative Commons Attribution 4.0 International](http://creativecommons.org/licenses/by/4.0/), so feel free to use it as a reference and leave a link so that other readers can consume the primer.

If there's any incorrect or missing information, please file an issue on this repository or ping @ChampionAsh5357 in the Neoforged Discord server.

## Minor Migrations

The following is a list of useful or interesting additions, changes, and removals that do not deserve their own section in the primer.

### Additions

- `net.minecraft.commands.arguments.selector.EntitySelectorParser#allowSelectors` - Returns whether the the available selector providers have at least creative mode permissions.
- `net.minecraft.world.level.block.entity.BlockEntity#isValidBlockState` - Returns whether the block state can have the current block entity.

### Removed

- `net.minecraft.commands.arguments.selector.EntitySelectorParser(StringReader)`
