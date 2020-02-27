/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.klikli_dev.occultism.proxy;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.api.common.block.IOtherOre;
import com.github.klikli_dev.occultism.client.ClientData;
import com.github.klikli_dev.occultism.client.gui.GuiBookOfCalling;
import com.github.klikli_dev.occultism.client.gui.GuiBookOfCallingManagedMachine;
import com.github.klikli_dev.occultism.client.render.entity.RenderFoliot;
import com.github.klikli_dev.occultism.client.render.tile.TESRSacrificialBowl;
import com.github.klikli_dev.occultism.client.render.tile.TESRStorageController;
import com.github.klikli_dev.occultism.common.block.BlockChalkGlyph;
import com.github.klikli_dev.occultism.common.block.BlockOtherworldLeavesNatural;
import com.github.klikli_dev.occultism.common.entity.spirits.EntityFoliot;
import com.github.klikli_dev.occultism.common.entity.spirits.WorkAreaSize;
import com.github.klikli_dev.occultism.common.item.ItemBookOfCallingActive;
import com.github.klikli_dev.occultism.common.tile.TileEntityGoldenSacrificialBowl;
import com.github.klikli_dev.occultism.common.tile.TileEntitySacrificialBowl;
import com.github.klikli_dev.occultism.common.tile.TileEntityStorageController;
import com.github.klikli_dev.occultism.handler.ClientEventHandler;
import com.github.klikli_dev.occultism.handler.RenderHandler;
import com.github.klikli_dev.occultism.handler.TextureHandler;
import com.github.klikli_dev.occultism.network.MessageBase;
import com.github.klikli_dev.occultism.registry.BlockRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.concurrent.TimeUnit;

public class ClientProxy extends CommonProxy {

    //region Fields
    /**
     * Holds temporary data for client side.
     */
    public ClientData clientData = new ClientData();
    public ClientEventHandler clientEventHandler = new ClientEventHandler();
    //endregion Fields


    //region Overrides
    @Override
    public ClientData getClientData() {
        return this.clientData;
    }

    @Override
    public ClientEventHandler getClientEventHandler() {
        return this.clientEventHandler;
    }

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        MinecraftForge.EVENT_BUS.register(new TextureHandler());
        MinecraftForge.EVENT_BUS.register(new RenderHandler());
        MinecraftForge.EVENT_BUS.register(this.clientEventHandler);
        RenderingRegistry.registerEntityRenderingHandler(EntityFoliot.class, RenderFoliot::new);

        OBJLoader.INSTANCE.addDomain(Occultism.MODID);
    }

    @Override
    public void registerRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStorageController.class, new TESRStorageController());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySacrificialBowl.class, new TESRSacrificialBowl());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGoldenSacrificialBowl.class, new TESRSacrificialBowl());
    }

    @Override
    public void registerModel(Item item, int meta, String variant) {
        ModelLoader
                .setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), variant));
    }

    @Override
    public void registerColorHandlers() {
        //Color the glyphs based on their type.
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(
                (state, world, pos, tintIndex) -> BlockChalkGlyph
                                                          .getColorFromType(state.getValue(BlockChalkGlyph.TYPE)),
                BlockRegistry.CHALK_GLYPH);
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(
                (state, world, pos, tintIndex) -> state.getValue(
                        IOtherOre.UNCOVERED) ? BlockOtherworldLeavesNatural.COLOR : 0x48B518,
                BlockRegistry.OTHERWORLD_LEAVES_NATURAL);
        Minecraft.getMinecraft().getItemColors()
                .registerItemColorHandler((stack, index) -> BlockOtherworldLeavesNatural.COLOR,
                        Item.getItemFromBlock(BlockRegistry.OTHERWORLD_LEAVES_NATURAL));
        Minecraft.getMinecraft().getBlockColors()
                .registerBlockColorHandler((state, world, pos, tintIndex) -> BlockOtherworldLeavesNatural.COLOR,
                        BlockRegistry.OTHERWORLD_LEAVES);
        Minecraft.getMinecraft().getItemColors()
                .registerItemColorHandler((stack, index) -> BlockOtherworldLeavesNatural.COLOR,
                        Item.getItemFromBlock(BlockRegistry.OTHERWORLD_LEAVES));
    }

    @Override
    public <T extends MessageBase<T>> void handleMessage(final T message, final MessageContext messageContext) {
        if (messageContext.side.isServer()) {
            super.handleMessage(message, messageContext);
        }
        else {
            Minecraft minecraft = Minecraft.getMinecraft();
            minecraft.addScheduledTask(
                    () -> message.onClientReceived(minecraft, message, minecraft.player, messageContext));
        }
    }

    @Override
    public void scheduleDelayedTask(World world, Runnable task, int delayMilliseconds) {
        this.scheduler.schedule(() -> Minecraft.getMinecraft().addScheduledTask(task), delayMilliseconds,
                TimeUnit.MILLISECONDS);
    }

    @Override
    public void openBookOfCallingUI(ItemBookOfCallingActive.ItemMode mode, WorkAreaSize workAreaSize) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiBookOfCalling(mode, workAreaSize));
    }

    @Override
    public void openBookOfCallingManageMachineUI(Direction insertFacing, Direction extractFacing, String customName) {
        Minecraft.getMinecraft()
                .displayGuiScreen(new GuiBookOfCallingManagedMachine(insertFacing, extractFacing, customName));
    }

    @Override
    public void spawnParticle(World world, Particle particle) {
        if (particle != null)
            Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }
    //endregion Overrides
}
