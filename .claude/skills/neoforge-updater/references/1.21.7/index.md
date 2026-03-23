# Minecraft 1.21.6 -> 1.21.7 Mod Migration Primer

This is a high level, non-exhaustive overview on how to migrate your mod from 1.21.6 to 1.21.7. This does not look at any specific mod loader, just the changes to the vanilla classes. All provided names use the official mojang mappings.

This primer is licensed under the [Creative Commons Attribution 4.0 International](http://creativecommons.org/licenses/by/4.0/), so feel free to use it as a reference and leave a link so that other readers can consume the primer.

If there's any incorrect or missing information, please file an issue on this repository or ping @ChampionAsh5357 in the Neoforged Discord server.

## Pack Changes

There are a number of user-facing changes that are part of vanilla which are not discussed below that may be relevant to modders. You can find a list of them on [Misode's version changelog](https://misode.github.io/versions/?id=1.21.7&tab=changelog).

## Minor Migrations

The following is a list of useful or interesting additions, changes, and removals that do not deserve their own section in the primer.

### List of Additions

- `com.mojang.blaze3d.opengl.DirectStateAccess#copyBufferSubData` - Copies all or part of one buffer object's data store to the data store of another buffer object.
- `com.mojang.blaze3d.pipeline.BlendFunction#INVERT` - Inverts the blend factors of the RGB source and destination. Alpha uses the default one from source and zero from destination.
- `com.mojang.blaze3d.systems.CommandEncoder#copyToBuffer` - Copies the data store of one buffer slice to another buffer slice.
- `net.minecraft.Util#isAarch64` - Returns whether the OS architecture uses aarch64.
- `net.minecraft.client.gui.GuiGraphics#textHighlight` - Adds a highlighted box around the provided bounds.
- `net.minecraft.client.renderer.RenderPipelines#GUI_INVERT` - A render pipeline for drawing a gui element with inverted colors.
- `net.minecraft.client.renderer.item.TrackingItemRenderState` - A render state that tracks the model sources being used to render the item stack.

### List of Changes

- `com.mojang.blaze3d.pipeline.RenderPipeline$Builder#withColorLogic` is now deprecated
- `net.minecraft.client.gui.renderer.GuiRenderer#MIN_GUI_Z` is now private
- `net.minecraft.client.gui.render.state.GuiItemRenderState` now takes in a `TrackingItemRenderState` instead of a `ItemStackRenderState`
    - `itemStackRenderState` now returns a `TrackingItemRenderState`
- `net.minecraft.client.renderer.RenderPipelines#GUI_TEXT_HIGHLIGHT` now uses the `ADDITIVE` blend function instead of the `OR_REVERSE` color logic
- `net.minecraft.client.renderer.item.ItemStackRenderState#getModelIdentity` -> `TrackingItemRenderState#getModelIdentity`

### List of Removals

- `net.minecraft.client.renderer.item.ItemStackRenderState#clearModelIdentity`
