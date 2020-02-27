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
import com.github.klikli_dev.occultism.client.ClientData;
import com.github.klikli_dev.occultism.common.entity.spirits.EntityFoliot;
import com.github.klikli_dev.occultism.common.entity.spirits.WorkAreaSize;
import com.github.klikli_dev.occultism.common.item.ItemBookOfCallingActive;
import com.github.klikli_dev.occultism.common.jobs.SpiritJobFactory;
import com.github.klikli_dev.occultism.common.ritual.Ritual;
import com.github.klikli_dev.occultism.common.ritual.pentacle.Pentacle;
import com.github.klikli_dev.occultism.common.tile.TileEntityGoldenSacrificialBowl;
import com.github.klikli_dev.occultism.common.tile.TileEntitySacrificialBowl;
import com.github.klikli_dev.occultism.common.tile.TileEntityStableWormhole;
import com.github.klikli_dev.occultism.common.tile.TileEntityStorageController;
import com.github.klikli_dev.occultism.handler.ClientEventHandler;
import com.github.klikli_dev.occultism.handler.GuiHandler;
import com.github.klikli_dev.occultism.handler.PlayerEventHandler;
import com.github.klikli_dev.occultism.integration.jei.JeiPlugin;
import com.github.klikli_dev.occultism.network.*;
import com.github.klikli_dev.occultism.registry.*;
import com.github.klikli_dev.occultism.util.ModNameUtil;
import net.minecraft.block.Block;
import net.minecraft.client.particle.Particle;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.RegistryBuilder;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Mod.EventBusSubscriber
public class CommonProxy {

    //region Fields
    protected ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    //endregion Fields

    //region Getter / Setter
    public ClientData getClientData() {
        return null;
    }

    public ClientEventHandler getClientEventHandler() {
        return null;
    }
    //endregion Getter / Setter

    //region Static Methods
    @SuppressWarnings("rawtypes")
    @SubscribeEvent
    public static void registerRegistries(RegistryEvent.NewRegistry event) {
        new RegistryBuilder<Pentacle>().setName(new ResourceLocation(Occultism.MODID, "pentacle"))
                .setType(Pentacle.class).create();
        new RegistryBuilder<Ritual>().setName(new ResourceLocation(Occultism.MODID, "ritual")).setType(Ritual.class)
                .create();
        new RegistryBuilder<SpiritJobFactory>().setName(new ResourceLocation(Occultism.MODID, "spiritJobFactory"))
                .setType(SpiritJobFactory.class).create();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        //Get all blocks from fields in the BLockRegistry class
        try {
            for (Field f : BlockRegistry.class.getFields()) {
                Object obj = f.get(null);
                if (obj instanceof Block) {
                    event.getRegistry().register((Block) obj);
                }
            }
        } catch (Exception ignored) {
        }

        //Register tile entities
        GameRegistry.registerTileEntity(TileEntityStorageController.class,
                BlockRegistry.STORAGE_CONTROLLER.getRegistryName());
        GameRegistry
                .registerTileEntity(TileEntitySacrificialBowl.class, BlockRegistry.SACRIFICIAL_BOWL.getRegistryName());
        GameRegistry.registerTileEntity(TileEntityGoldenSacrificialBowl.class,
                BlockRegistry.GOLDEN_SACRIFICIAL_BOWL.getRegistryName());
        GameRegistry
                .registerTileEntity(TileEntityStableWormhole.class, BlockRegistry.STABLE_WORMHOLE.getRegistryName());

        //Set up crops
        BlockRegistry.CROP_DATURA.setSeed(ItemRegistry.DATURA_SEEDS);
        BlockRegistry.CROP_DATURA.setCrop(ItemRegistry.DATURA);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        //Get all items from fields in the ItemRegistry class
        try {
            for (Field f : ItemRegistry.class.getFields()) {
                Object obj = f.get(null);
                if (obj instanceof Item) {
                    Item item = (Item) obj;
                    event.getRegistry().register(item);
                    Occultism.proxy.registerModel(item, 0);
                }
            }
        } catch (Exception ignored) {
        }

        //register the items for blocks
        for (Item item : BlockRegistry.blockItems) {
            event.getRegistry().register(item);
            Occultism.proxy.registerModel(item, 0);
        }

        //register ore dictionary for items
        for (Map.Entry<Item, String[]> entry : ItemRegistry.ORE_DICTIONARY.entrySet()) {
            for (String ore : entry.getValue())
                OreDictionary.registerOre(ore, entry.getKey());
        }

        //register ore dictionary for blocks
        for (Map.Entry<Block, String[]> entry : BlockRegistry.ORE_DICTIONARY.entrySet()) {
            for (String ore : entry.getValue())
                OreDictionary.registerOre(ore, entry.getKey());
        }
    }

    @SubscribeEvent
    public static void registerRituals(RegistryEvent.Register<Ritual> event) {
        try {
            for (Field f : RitualRegistry.class.getFields()) {
                Object obj = f.get(null);
                if (obj instanceof Ritual) {
                    Ritual ritual = (Ritual) obj;
                    event.getRegistry().register(ritual);
                }
            }
        } catch (Exception ignored) {
        }
    }

    @SubscribeEvent
    public static void registerPentacles(RegistryEvent.Register<Pentacle> event) {
        try {
            for (Field f : RitualRegistry.class.getFields()) {
                Object obj = f.get(null);
                if (obj instanceof Pentacle) {
                    Pentacle pentacle = (Pentacle) obj;
                    event.getRegistry().register(pentacle);
                }
            }
        } catch (Exception ignored) {
        }
    }

    @SubscribeEvent
    public static void registerSpiritJobFactories(RegistryEvent.Register<SpiritJobFactory> event) {
        try {
            for (Field f : SpiritJobFactoryRegistry.class.getFields()) {
                Object obj = f.get(null);
                if (obj instanceof SpiritJobFactory) {
                    SpiritJobFactory spiritJobFactory = (SpiritJobFactory) obj;
                    event.getRegistry().register(spiritJobFactory);
                }
            }
        } catch (Exception ignored) {
        }
    }

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Effect> event) {
        try {
            for (Field f : PotionRegistry.class.getFields()) {
                Object obj = f.get(null);
                if (obj instanceof Effect) {
                    Effect potion = (Effect) obj;
                    event.getRegistry().register(potion);
                }
            }
        } catch (Exception ignored) {
        }
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
        int entityId = 0;
        EntityRegistry.registerEntity(event, EntityFoliot.class, "foliot", entityId++, 0xaa728d, 0x37222c);
    }


    //endregion Static Methods

    //region Methods
    public void registerNetworkMessages() {
        int id = 0;
        Occultism.network.registerMessage(MessageParticle.class, MessageParticle.class, id++, Side.CLIENT);
        Occultism.network.registerMessage(MessageModParticle.class, MessageModParticle.class, id++, Side.CLIENT);
        Occultism.network.registerMessage(MessageSetItemMode.class, MessageSetItemMode.class, id++, Side.SERVER);
        Occultism.network
                .registerMessage(MessageSetWorkAreaSize.class, MessageSetWorkAreaSize.class, id++, Side.SERVER);
        Occultism.network.registerMessage(MessageRequestStacks.class, MessageRequestStacks.class, id++, Side.SERVER);
        Occultism.network.registerMessage(MessageUpdateStacks.class, MessageUpdateStacks.class, id++, Side.CLIENT);
        Occultism.network
                .registerMessage(MessageUpdateMouseHeldItem.class, MessageUpdateMouseHeldItem.class, id++, Side.CLIENT);
        Occultism.network.registerMessage(MessageTakeItem.class, MessageTakeItem.class, id++, Side.SERVER);
        Occultism.network.registerMessage(MessageSortItems.class, MessageSortItems.class, id++, Side.SERVER);
        Occultism.network
                .registerMessage(MessageClearCraftingMatrix.class, MessageClearCraftingMatrix.class, id++, Side.SERVER);
        Occultism.network
                .registerMessage(MessageInsertMouseHeldItem.class, MessageInsertMouseHeldItem.class, id++, Side.SERVER);
        Occultism.network.registerMessage(MessageSetRecipe.class, MessageSetRecipe.class, id++, Side.SERVER);
        Occultism.network
                .registerMessage(MessageRequestTileEntityUpdate.class, MessageRequestTileEntityUpdate.class, id++,
                        Side.SERVER);
        Occultism.network.registerMessage(MessageRequestOrder.class, MessageRequestOrder.class, id++, Side.SERVER);
        Occultism.network.registerMessage(MessageClearOrderSlot.class, MessageClearOrderSlot.class, id++, Side.SERVER);
        Occultism.network
                .registerMessage(MessageSetManagedMachine.class, MessageSetManagedMachine.class, id++, Side.SERVER);
        Occultism.network.registerMessage(MessageUpdateLinkedMachines.class, MessageUpdateLinkedMachines.class, id++,
                Side.CLIENT);
        Occultism.network
                .registerMessage(MessageSetDivinationResult.class, MessageSetDivinationResult.class, id++, Side.SERVER);
    }

    public void preInitPentacleMultiblocks() {
        try {
            for (Field f : RitualRegistry.class.getFields()) {
                Object obj = f.get(null);
                if (obj instanceof Pentacle) {
                    Pentacle pentacle = (Pentacle) obj;
                    pentacle.registerMultiblock();
                }
            }
        } catch (Exception ignored) {
        }
    }

    public void preInit(FMLPreInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
        this.preInitPentacleMultiblocks();
        this.registerRenderers();

        JeiPlugin.preInit();
    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(Occultism.instance, new GuiHandler());
        this.registerColorHandlers();
        this.registerNetworkMessages();
        this.registerSmeltingRecipes();
        this.registerCustomRecipes();
        this.registerRitualAdditionalIngredients();
    }

    public void postInit(FMLPostInitializationEvent e) {
        ModNameUtil.postInit();
    }

    public void registerRenderers() {

    }

    /**
     * Registers the item texture for the given item
     *
     * @param item    the item to register the texture for.
     * @param meta    the item metadata.
     * @param variant the variant name, usually "normal".
     */
    public void registerModel(Item item, int meta, String variant) {
        //Implemented only in client
    }

    /**
     * Registers the item texture for the given item
     *
     * @param item the item to register the texture for.
     * @param meta the item metadata.
     */
    public void registerModel(Item item, int meta) {
        this.registerModel(item, meta, "inventory");
    }

    /**
     * Registers the block and item color handlers for objects with tintIndex
     */
    public void registerColorHandlers() {

    }

    public void registerSmeltingRecipes() {
        GameRegistry.addSmelting(BlockRegistry.OTHERWORLD_LOG, new ItemStack(ItemRegistry.OTHERWORLD_ASHES), 1);
        GameRegistry.addSmelting(BlockRegistry.OTHERSTONE, new ItemStack(ItemRegistry.BURNT_OTHERSTONE), 1);
    }

    public void registerCustomRecipes() {
        BlockRegistry.SPIRIT_FIRE.registerRecipes();
    }

    public void registerRitualAdditionalIngredients() {
        GameRegistry.findRegistry(Ritual.class).getValuesCollection().forEach(Ritual::registerAdditionalIngredients);
    }

    /**
     * Handles incoming messages
     *
     * @param message        the message
     * @param messageContext thhe context
     * @param <T>            the message type
     */
    public <T extends MessageBase<T>> void handleMessage(final T message, final MessageContext messageContext) {
        ServerWorld world = (ServerWorld) messageContext.getServerHandler().player.world;
        world.addScheduledTask(
                () -> message.onServerReceived(FMLCommonHandler.instance().getMinecraftServerInstance(), message,
                        messageContext.getServerHandler().player, messageContext));
    }

    /**
     * Schedules the given task on the MC main thread after the given delay in milliseconds.
     *
     * @param world             the world to run the task on.
     * @param task              the task to run.
     * @param delayMilliseconds the delay in milliseconds.
     */
    public void scheduleDelayedTask(World world, Runnable task, int delayMilliseconds) {
        ServerWorld worldServer = (ServerWorld) world;
        this.scheduler.schedule(() -> worldServer.addScheduledTask(task), delayMilliseconds, TimeUnit.MILLISECONDS);
    }

    public void openBookOfCallingUI(ItemBookOfCallingActive.ItemMode mode, WorkAreaSize workAreaSize) {

    }

    public void openBookOfCallingManageMachineUI(Direction insertFacing, Direction extractFacing, String customName) {

    }

    public void spawnParticle(World world, Particle particle) {
    }
    //endregion Methods
}
//endregion Subtypes