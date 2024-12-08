package com.klikli_dev.occultism.datagen.tags;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.concurrent.CompletableFuture;

public class OccultismItemTagProvider extends ItemTagsProvider {
    public OccultismItemTagProvider(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, Occultism.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.addCommonTags(provider);
        this.addMinecraftTags(provider);
        this.addOccultismTags(provider);
        this.addCuriosTags(provider);
        this.addOptionalOreTagsForMinerCompat(provider);

    }

    private void addOptionalOreTagsForMinerCompat(HolderLookup.Provider provider) {
        this.tag(this.cTag("ores/bone_fragments")).addOptional(this.loc("aoa3:bone_fragments_ore"));
        this.tag(this.cTag("ores/baronyte")).addOptional(this.loc("aoa3:baronyte_ore"));
        this.tag(this.cTag("ores/blazium")).addOptional(this.loc("aoa3:blazium_ore"));
        this.tag(this.cTag("ores/bloodstone")).addOptional(this.loc("aoa3:bloodstone_ore"));
        this.tag(this.cTag("ores/blue_gemstone")).addOptional(this.loc("aoa3:blue_gemstone_ore"));
        this.tag(this.cTag("ores/charged_runium")).addOptional(this.loc("aoa3:charged_runium_ore"));
        this.tag(this.cTag("ores/crystallite")).addOptional(this.loc("aoa3:crystallite_ore"));
        this.tag(this.cTag("ores/elecanium")).addOptional(this.loc("aoa3:elecanium_ore"));
        this.tag(this.cTag("ores/emberstone")).addOptional(this.loc("aoa3:emberstone_ore"));
        this.tag(this.cTag("ores/gemenyte")).addOptional(this.loc("aoa3:gemenyte_ore"));
        this.tag(this.cTag("ores/ghastly")).addOptional(this.loc("aoa3:ghastly_ore"));
        this.tag(this.cTag("ores/ghoulish")).addOptional(this.loc("aoa3:ghoulish_ore"));
        this.tag(this.cTag("ores/green_gemstone")).addOptional(this.loc("aoa3:green_gemstone_ore"));
        this.tag(this.cTag("ores/jade")).addOptional(this.loc("aoa3:jade_ore"));
        this.tag(this.cTag("ores/jewelyte")).addOptional(this.loc("aoa3:jewelyte_ore"));
        this.tag(this.cTag("ores/limonite")).addOptional(this.loc("aoa3:limonite_ore"));
        this.tag(this.cTag("ores/lyon")).addOptional(this.loc("aoa3:lyon_ore"));
        this.tag(this.cTag("ores/mystite")).addOptional(this.loc("aoa3:mystite_ore"));
        this.tag(this.cTag("ores/ornamyte")).addOptional(this.loc("aoa3:ornamyte_ore"));
        this.tag(this.cTag("ores/purple_gemstone")).addOptional(this.loc("aoa3:purple_gemstone_ore"));
        this.tag(this.cTag("ores/red_gemstone")).addOptional(this.loc("aoa3:red_gemstone_ore"));
        this.tag(this.cTag("ores/runium")).addOptional(this.loc("aoa3:runium_ore"));
        this.tag(this.cTag("ores/shyregem")).addOptional(this.loc("aoa3:shyregem_ore"));
        this.tag(this.cTag("ores/shyrestone")).addOptional(this.loc("aoa3:shyrestone_ore"));
        this.tag(this.cTag("ores/varsium")).addOptional(this.loc("aoa3:varsium_ore"));
        this.tag(this.cTag("ores/white_gemstone")).addOptional(this.loc("aoa3:white_gemstone_ore"));
        this.tag(this.cTag("ores/yellow_gemstone")).addOptional(this.loc("aoa3:yellow_gemstone_ore"));
        this.tag(this.cTag("ores/dark_gem"))
                .addOptional(this.loc("evilcraft:dark_ores"))
                .addOptionalTag(this.loc("evilcraft:ores/dark_gem")); //does not exist as of 1.21, but if they unify the pattern it will

        this.tag(this.cTag("dusts/dark_gem"))
                .addOptional(this.loc("evilcraft:dark_gem_crushed"));
        this.tag(this.cTag("ores/black_quartz")).addOptional(this.loc("actuallyadditions:black_quartz_ore"));
        this.tag(this.cTag("gems/black_quartz")).addOptional(this.loc("actuallyadditions:black_quartz"));
        this.tag(this.cTag("dusts/certus_quartz")).addOptional(this.loc("ae2:certus_quartz_dust"));
        this.tag(this.cTag("dusts/fluix")).addOptional(this.loc("ae2:fluix_dust"));
        this.tag(this.cTag("gems/fluix")).addOptional(this.loc("ae2:fluix_crystal"));
    }

    private void addCuriosTags(HolderLookup.Provider provider) {
        this.tag(OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath(CuriosApi.MODID, "belt"))).add(OccultismItems.SATCHEL.get()).replace(false);
        this.tag(OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath(CuriosApi.MODID, "hands"))).add(OccultismItems.STORAGE_REMOTE.get()).replace(false);
        this.tag(OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath(CuriosApi.MODID, "head"))).add(OccultismItems.OTHERWORLD_GOGGLES.get()).replace(false);
        this.tag(OccultismTags.makeItemTag(ResourceLocation.fromNamespaceAndPath(CuriosApi.MODID, "ring"))).add(OccultismItems.FAMILIAR_RING.get()).replace(false);
    }

    private void addOccultismTags(HolderLookup.Provider provider) {
        this.copy(OccultismTags.Blocks.OCCULTISM_CANDLES, OccultismTags.Items.OCCULTISM_CANDLES);
        this.copy(BlockTags.CANDLES, ItemTags.CANDLES);

        this.tag(OccultismTags.Items.SKULLS)
                .add(Items.SKELETON_SKULL)
                .add(Items.WITHER_SKELETON_SKULL)
                .replace(false);

        this.tag(OccultismTags.Items.SCUTESHELL)
                .add(Items.ARMADILLO_SCUTE)
                .add(Items.TURTLE_SCUTE)
                .add(Items.SHULKER_SHELL)
                .add(Items.NAUTILUS_SHELL)
                .replace(false);

        this.copy(OccultismTags.Blocks.PENTACLE_MATERIALS, OccultismTags.Items.PENTACLE_MATERIALS);
        this.tag(OccultismTags.Items.PENTACLE_MATERIALS)
                .addOptionalTag(OccultismTags.Items.TOOLS_CHALK)
                .replace(false);


        this.tag(OccultismTags.Items.DEMONIC_PARTNER_FOOD)
                .addTag(ItemTags.MEAT);

        this.tag(OccultismTags.Items.BOOK_OF_CALLING_FOLIOT)
                .add(OccultismItems.BOOK_OF_CALLING_FOLIOT_LUMBERJACK.get())
                .add(OccultismItems.BOOK_OF_CALLING_FOLIOT_CLEANER.get())
                .add(OccultismItems.BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS.get()).replace(false);
        this.tag(OccultismTags.Items.BOOK_OF_CALLING_DJINNI)
                .add(OccultismItems.BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE.get()).replace(false);

        this.tag(OccultismTags.Items.BOOKS_OF_BINDING)
                .add(OccultismItems.BOOK_OF_BINDING_FOLIOT.get())
                .add(OccultismItems.BOOK_OF_BINDING_DJINNI.get())
                .add(OccultismItems.BOOK_OF_BINDING_AFRIT.get())
                .add(OccultismItems.BOOK_OF_BINDING_MARID.get());

        this.tag(OccultismTags.Items.Miners.BASIC_RESOURCES).add(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get(),
                OccultismItems.MINER_DEBUG_UNSPECIALIZED.get()).replace(false);
        this.tag(OccultismTags.Items.Miners.DEEPS).add(OccultismItems.MINER_AFRIT_DEEPS.get(), OccultismItems.MINER_MARID_MASTER.get()).replace(false);
        this.tag(OccultismTags.Items.Miners.MASTER).add(OccultismItems.MINER_MARID_MASTER.get()).replace(false);
        this.tag(OccultismTags.Items.Miners.ORES).add(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get(),
                OccultismItems.MINER_DEBUG_UNSPECIALIZED.get(), OccultismItems.MINER_AFRIT_DEEPS.get(), OccultismItems.MINER_MARID_MASTER.get(), OccultismItems.MINER_DJINNI_ORES.get()).replace(false);
        this.tag(OccultismTags.Items.Miners.ELDRITCH).add(OccultismItems.MINER_ANCIENT_ELDRITCH.get()).replace(false);

        this.tag(OccultismTags.Items.Miners.MINERS)
                .addTag(OccultismTags.Items.Miners.BASIC_RESOURCES)
                .addTag(OccultismTags.Items.Miners.DEEPS)
                .addTag(OccultismTags.Items.Miners.MASTER)
                .addTag(OccultismTags.Items.Miners.ORES)
                .addTag(OccultismTags.Items.Miners.ELDRITCH).replace(false);

        this.tag(OccultismTags.Items.TOOLS_CHALK)
                .add(OccultismItems.CHALK_YELLOW.get())
                .add(OccultismItems.CHALK_WHITE.get())
                .add(OccultismItems.CHALK_RED.get())
                .add(OccultismItems.CHALK_PURPLE.get())
                .add(OccultismItems.CHALK_LIGHT_GRAY.get())
                .add(OccultismItems.CHALK_GRAY.get())
                .add(OccultismItems.CHALK_BLACK.get())
                .add(OccultismItems.CHALK_BROWN.get())
                .add(OccultismItems.CHALK_ORANGE.get())
                .add(OccultismItems.CHALK_LIME.get())
                .add(OccultismItems.CHALK_GREEN.get())
                .add(OccultismItems.CHALK_CYAN.get())
                .add(OccultismItems.CHALK_LIGHT_BLUE.get())
                .add(OccultismItems.CHALK_BLUE.get())
                .add(OccultismItems.CHALK_MAGENTA.get())
                .add(OccultismItems.CHALK_PINK.get())
                .add(OccultismItems.CHALK_RAINBOW.get())
                .add(OccultismItems.CHALK_VOID.get())
                .replace(false);

        this.copy(OccultismTags.Blocks.OTHERWORLD_SAPLINGS, OccultismTags.Items.OTHERWORLD_SAPLINGS);
        this.copy(OccultismTags.Blocks.OTHERWORLD_SAPLINGS_NATURAL, OccultismTags.Items.OTHERWORLD_SAPLINGS_NATURAL);
        this.tag(OccultismTags.Items.TOOLS_KNIFE).add(OccultismItems.BUTCHER_KNIFE.get()).replace(false);
        this.tag(Tags.Items.TOOLS).addOptionalTag(OccultismTags.Items.TOOLS_KNIFE).replace(false); //Don't place chalks
        this.tag(OccultismTags.Items.ELYTRA).add(Items.ELYTRA).addOptional(ResourceLocation.fromNamespaceAndPath("mana-and-artifice", "spectral_elytra")).replace(false);
        this.tag(OccultismTags.Items.OTHERWORLD_GOGGLES).add(OccultismItems.OTHERWORLD_GOGGLES.get()).replace(false);
        this.tag(OccultismTags.Items.OTHERSTONE).add(OccultismBlocks.OTHERSTONE.asItem());
        this.tag(OccultismTags.Items.OTHERCOBBLESTONE).add(OccultismBlocks.OTHERCOBBLESTONE.asItem());
    }

    private void addMinecraftTags(HolderLookup.Provider provider) {
        this.tag(ItemTags.BOOKSHELF_BOOKS).add(OccultismItems.DICTIONARY_OF_SPIRITS.get());
        this.tag(ItemTags.BOOKSHELF_BOOKS).add(OccultismItems.BOOK_OF_BINDING_AFRIT.get());
        this.tag(ItemTags.BOOKSHELF_BOOKS).add(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get());
        this.tag(ItemTags.BOOKSHELF_BOOKS).add(OccultismItems.BOOK_OF_BINDING_DJINNI.get());
        this.tag(ItemTags.BOOKSHELF_BOOKS).add(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get());
        this.tag(ItemTags.BOOKSHELF_BOOKS).add(OccultismItems.BOOK_OF_BINDING_FOLIOT.get());
        this.tag(ItemTags.BOOKSHELF_BOOKS).add(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get());
        this.tag(ItemTags.BOOKSHELF_BOOKS).add(OccultismItems.BOOK_OF_BINDING_MARID.get());
        this.tag(ItemTags.BOOKSHELF_BOOKS).add(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get());
        this.tag(ItemTags.BOOKSHELF_BOOKS).add(OccultismItems.BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE.get());
        this.tag(ItemTags.BOOKSHELF_BOOKS).add(OccultismItems.BOOK_OF_CALLING_FOLIOT_CLEANER.get());
        this.tag(ItemTags.BOOKSHELF_BOOKS).add(OccultismItems.BOOK_OF_CALLING_FOLIOT_LUMBERJACK.get());
        this.tag(ItemTags.BOOKSHELF_BOOKS).add(OccultismItems.BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS.get());
        this.tag(ItemTags.BOOKSHELF_BOOKS).add(OccultismItems.BOOK_OF_BINDING_EMPTY.get());

        this.tag(ItemTags.DURABILITY_ENCHANTABLE).addTag(OccultismTags.Items.Miners.MINERS);
        this.tag(ItemTags.DURABILITY_ENCHANTABLE).addTag(OccultismTags.Items.TOOLS_CHALK);
        this.tag(ItemTags.DURABILITY_ENCHANTABLE).addTag(OccultismTags.Items.TOOLS_KNIFE);
        this.tag(ItemTags.SHARP_WEAPON_ENCHANTABLE).addTag(OccultismTags.Items.TOOLS_KNIFE);
        this.tag(ItemTags.SWORD_ENCHANTABLE).addTag(OccultismTags.Items.TOOLS_KNIFE);

        this.tag(ItemTags.LOGS_THAT_BURN)
                .add(OccultismBlocks.OTHERWORLD_LOG.asItem())
                .add(OccultismBlocks.OTHERWORLD_LOG_NATURAL.asItem())
                .add(OccultismBlocks.STRIPPED_OTHERWORLD_LOG_NATURAL.asItem())
                .add(OccultismBlocks.OTHERWORLD_WOOD.asItem())
                .add(OccultismBlocks.STRIPPED_OTHERWORLD_LOG.asItem())
                .add(OccultismBlocks.STRIPPED_OTHERWORLD_WOOD.asItem());

        this.tag(ItemTags.PLANKS).add(OccultismBlocks.OTHERPLANKS.asItem());
        this.tag(ItemTags.WOODEN_STAIRS).add(OccultismBlocks.OTHERPLANKS_STAIRS.asItem());
        this.tag(ItemTags.WOODEN_SLABS).add(OccultismBlocks.OTHERPLANKS_SLAB.asItem());
        this.tag(ItemTags.WOODEN_FENCES).add(OccultismBlocks.OTHERPLANKS_FENCE.asItem()).replace(false);
        this.tag(ItemTags.FENCE_GATES).add(OccultismBlocks.OTHERPLANKS_FENCE_GATE.asItem()).replace(false);
        this.tag(ItemTags.WOODEN_DOORS).add(OccultismBlocks.OTHERPLANKS_DOOR.asItem()).replace(false);
        this.tag(ItemTags.WOODEN_TRAPDOORS).add(OccultismBlocks.OTHERPLANKS_TRAPDOOR.asItem()).replace(false);
        this.tag(ItemTags.WOODEN_PRESSURE_PLATES).add(OccultismBlocks.OTHERPLANKS_PRESSURE_PLATE.asItem()).replace(false);
        this.tag(ItemTags.WOODEN_BUTTONS).add(OccultismBlocks.OTHERPLANKS_BUTTON.asItem()).replace(false);
        this.tag(ItemTags.SIGNS).add(OccultismItems.OTHERPLANKS_SIGN.get()).replace(false);
        this.tag(ItemTags.HANGING_SIGNS).add(OccultismItems.OTHERPLANKS_HANGING_SIGN.get()).replace(false);
        /* OTHERSTONE CAN'T HAVE STONE TAG BECAUSE SPIRIT TRADER WILL DUPE
        *this.tag(Tags.Items.STONES)
        *        .add(OccultismBlocks.OTHERSTONE.asItem())
        *        .add(OccultismBlocks.OTHERSTONE_NATURAL.asItem());
         */
        this.tag(ItemTags.STAIRS)
                .add(OccultismBlocks.OTHERSTONE_STAIRS.asItem())
                .add(OccultismBlocks.OTHERCOBBLESTONE_STAIRS.asItem())
                .add(OccultismBlocks.POLISHED_OTHERSTONE_STAIRS.asItem())
                .add(OccultismBlocks.OTHERSTONE_BRICKS_STAIRS.asItem());
        this.tag(ItemTags.WALLS)
                .add(OccultismBlocks.OTHERSTONE_WALL.asItem())
                .add(OccultismBlocks.OTHERCOBBLESTONE_WALL.asItem())
                .add(OccultismBlocks.POLISHED_OTHERSTONE_WALL.asItem())
                .add(OccultismBlocks.OTHERSTONE_BRICKS_WALL.asItem());
        this.tag(ItemTags.STONE_BUTTONS).add(OccultismBlocks.OTHERSTONE_BUTTON.asItem());

        this.tag(ItemTags.CLUSTER_MAX_HARVESTABLES).add(OccultismItems.INFUSED_PICKAXE.get(), OccultismItems.IESNIUM_PICKAXE.get()).replace(false);
        this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
        this.copy(BlockTags.LOGS, ItemTags.LOGS);
        this.copy(BlockTags.PIGLIN_REPELLENTS, ItemTags.PIGLIN_REPELLENTS);
        this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        this.tag(ItemTags.PICKAXES).add(OccultismItems.INFUSED_PICKAXE.get(), OccultismItems.IESNIUM_PICKAXE.get()).replace(false);
        this.tag(this.cTag("tools/knife")).add(OccultismItems.BUTCHER_KNIFE.get()).replace(false);

        this.tag(ItemTags.BOOKSHELF_BOOKS)
                .replace(false)
                .add(OccultismItems.DICTIONARY_OF_SPIRITS.get())
                .add(OccultismItems.BOOK_OF_BINDING_FOLIOT.get())
                .add(OccultismItems.BOOK_OF_BINDING_DJINNI.get())
                .add(OccultismItems.BOOK_OF_BINDING_AFRIT.get())
                .add(OccultismItems.BOOK_OF_BINDING_MARID.get())
                .add(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get())
                .add(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get())
                .add(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get())
                .add(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get())
                .add(OccultismItems.BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE.get())
                .add(OccultismItems.BOOK_OF_CALLING_FOLIOT_CLEANER.get())
                .add(OccultismItems.BOOK_OF_CALLING_FOLIOT_LUMBERJACK.get())
                .add(OccultismItems.BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS.get());

        this.tag(ItemTags.SMALL_FLOWERS).add(OccultismBlocks.OTHERFLOWER.asItem()).add(OccultismBlocks.OTHERFLOWER_NATURAL.asItem()).replace(false);
    }

    private void addCommonTags(HolderLookup.Provider provider) {
        this.tag(Tags.Items.MINING_TOOL_TOOLS)
                .add(OccultismItems.INFUSED_PICKAXE.get())
                .add(OccultismItems.IESNIUM_PICKAXE.get())
                .replace(false);

        this.copy(BlockTags.SLABS, ItemTags.SLABS);

        // Ore Blocks
        this.copy(OccultismTags.Blocks.IESNIUM_ORE, OccultismTags.Items.IESNIUM_ORE);
        this.copy(OccultismTags.Blocks.SILVER_ORE, OccultismTags.Items.SILVER_ORE);
        this.tag(Tags.Items.ORES).addTags(OccultismTags.Items.IESNIUM_ORE, OccultismTags.Items.SILVER_ORE).replace(false);

        this.copy(Tags.Blocks.ORES_IN_GROUND_STONE, Tags.Items.ORES_IN_GROUND_STONE);
        this.copy(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE, Tags.Items.ORES_IN_GROUND_DEEPSLATE);
        this.copy(Tags.Blocks.ORES_IN_GROUND_NETHERRACK, Tags.Items.ORES_IN_GROUND_NETHERRACK);

        // Raw Materials
        this.tag(OccultismTags.Items.RAW_SILVER).add(OccultismItems.RAW_SILVER.get()).replace(false);
        this.tag(OccultismTags.Items.RAW_IESNIUM).add(OccultismItems.RAW_IESNIUM.get()).replace(false);
        this.tag(Tags.Items.RAW_MATERIALS).addTags(OccultismTags.Items.RAW_IESNIUM, OccultismTags.Items.RAW_SILVER).replace(false);

        // Dusts
        this.addDusts(provider);

        // Crops
        this.tag(OccultismTags.Items.DATURA_CROP).add(OccultismItems.DATURA.get()).replace(false);
        this.tag(Tags.Items.CROPS).addTags(OccultismTags.Items.DATURA_CROP).replace(false);

        // Ingots
        this.tag(OccultismTags.Items.IESNIUM_INGOT).add(OccultismItems.IESNIUM_INGOT.get()).replace(false);
        this.tag(OccultismTags.Items.SILVER_INGOT).add(OccultismItems.SILVER_INGOT.get()).replace(false);
        this.tag(Tags.Items.INGOTS).addTags(OccultismTags.Items.IESNIUM_INGOT, OccultismTags.Items.SILVER_INGOT).replace(false);

        // Nuggets
        this.tag(OccultismTags.Items.IESNIUM_NUGGET).add(OccultismItems.IESNIUM_NUGGET.get()).replace(false);
        this.tag(OccultismTags.Items.SILVER_NUGGET).add(OccultismItems.SILVER_NUGGET.get()).replace(false);
        this.tag(Tags.Items.NUGGETS).addTags(OccultismTags.Items.IESNIUM_NUGGET, OccultismTags.Items.SILVER_NUGGET).replace(false);

        // Seeds
        this.tag(OccultismTags.Items.DATURA_SEEDS).add(OccultismItems.DATURA_SEEDS.get()).replace(false);
        this.tag(Tags.Items.SEEDS).addTags(OccultismTags.Items.DATURA_SEEDS).replace(false);

        //Foods
        this.tag(Tags.Items.FOODS)
                .add(OccultismItems.DATURA.get())
                .add(OccultismItems.DEMONS_DREAM_ESSENCE.get())
                .add(OccultismItems.OTHERWORLD_ESSENCE.get())
                .add(OccultismItems.BEAVER_NUGGET.get())
                .add(OccultismItems.CURSED_HONEY.get())
                .add(OccultismItems.DEMONIC_MEAT.get())
                .replace(false);
        this.tag(ItemTags.MEAT)
                .add(OccultismItems.DEMONIC_MEAT.get())
                .replace(false);

        // Storage Blocks
        this.copy(OccultismTags.Blocks.STORAGE_BLOCKS_IESNIUM, OccultismTags.Items.STORAGE_BLOCK_IESNIUM);
        this.copy(OccultismTags.Blocks.STORAGE_BLOCKS_SILVER, OccultismTags.Items.STORAGE_BLOCK_SILVER);
        this.copy(OccultismTags.Blocks.STORAGE_BLOCKS_RAW_IESNIUM, OccultismTags.Items.STORAGE_BLOCK_RAW_IESNIUM);
        this.copy(OccultismTags.Blocks.STORAGE_BLOCKS_RAW_SILVER, OccultismTags.Items.STORAGE_BLOCK_RAW_SILVER);
        this.tag(Tags.Items.STORAGE_BLOCKS).addTags(OccultismTags.Items.STORAGE_BLOCK_IESNIUM, OccultismTags.Items.STORAGE_BLOCK_SILVER,
                OccultismTags.Items.STORAGE_BLOCK_RAW_IESNIUM, OccultismTags.Items.STORAGE_BLOCK_RAW_SILVER).replace(false);
        this.copy(OccultismTags.Blocks.MUSHROOM_BLOCKS, OccultismTags.Items.MUSHROOM_BLOCKS);

        // Metal Axes Tag
        this.tag(OccultismTags.Items.METAL_AXES).add(Items.IRON_AXE, Items.GOLDEN_AXE, Items.DIAMOND_AXE, Items.NETHERITE_AXE).replace(false);

        // Books
        this.tag(OccultismTags.Items.BOOKS).add(OccultismItems.DICTIONARY_OF_SPIRITS.get(), Items.BOOK).replace(false);

        // Fruits
        this.tag(OccultismTags.Items.FRUITS).add(Items.APPLE).replace(false);

        // Gems
        this.tag(Tags.Items.GEMS).add(OccultismItems.SPIRIT_ATTUNED_GEM.get()).replace(false);

        // Magma
        this.tag(OccultismTags.Items.MAGMA).add(Items.MAGMA_BLOCK).replace(false);

        // Manuals
        this.tag(OccultismTags.Items.MANUALS).add(OccultismItems.DICTIONARY_OF_SPIRITS.get()).replace(false);

        // Tallow
        this.tag(OccultismTags.Items.TALLOW).add(OccultismItems.TALLOW.get()).replace(false);

        // Wood
        this.copy(OccultismTags.Blocks.OTHERWORLD_LOGS, OccultismTags.Items.OTHERWORLD_LOGS);
    }

    private void addDusts(HolderLookup.Provider provider) {
        this.tag(OccultismTags.Items.BLAZE_DUST).add(Items.BLAZE_POWDER).replace(false);
        this.tag(OccultismTags.Items.COPPER_DUST).add(OccultismItems.COPPER_DUST.get()).replace(false);
        this.tag(OccultismTags.Items.END_STONE_DUST).add(OccultismItems.CRUSHED_END_STONE.get()).replace(false);
        this.tag(OccultismTags.Items.GOLD_DUST).add(OccultismItems.GOLD_DUST.get()).replace(false);
        this.tag(OccultismTags.Items.IRON_DUST).add(OccultismItems.IRON_DUST.get()).replace(false);
        this.tag(OccultismTags.Items.IESNIUM_DUST).add(OccultismItems.IESNIUM_DUST.get()).replace(false);
        this.tag(OccultismTags.Items.SILVER_DUST).add(OccultismItems.SILVER_DUST.get()).replace(false);
        this.tag(OccultismTags.Items.OBSIDIAN_DUST).add(OccultismItems.OBSIDIAN_DUST.get()).replace(false);
        this.tag(OccultismTags.Items.AMETHYST_DUST).add(OccultismItems.AMETHYST_DUST.get()).replace(false);
        this.tag(OccultismTags.Items.BLACKSTONE_DUST).add(OccultismItems.CRUSHED_BLACKSTONE.get()).replace(false);
        this.tag(OccultismTags.Items.BLUE_ICE_DUST).add(OccultismItems.CRUSHED_BLUE_ICE.get()).replace(false);
        this.tag(OccultismTags.Items.CALCITE_DUST).add(OccultismItems.CRUSHED_CALCITE.get()).replace(false);
        this.tag(OccultismTags.Items.ICE_DUST).add(OccultismItems.CRUSHED_ICE.get()).replace(false);
        this.tag(OccultismTags.Items.PACKED_ICE_DUST).add(OccultismItems.CRUSHED_PACKED_ICE.get()).replace(false);
        this.tag(OccultismTags.Items.DRAGONYST_DUST).add(OccultismItems.DRAGONYST_DUST.get()).replace(false);
        this.tag(OccultismTags.Items.ECHO_DUST).add(OccultismItems.ECHO_DUST.get()).replace(false);
        this.tag(OccultismTags.Items.EMERALD_DUST).add(OccultismItems.EMERALD_DUST.get()).replace(false);
        this.tag(OccultismTags.Items.LAPIS_DUST).add(OccultismItems.LAPIS_DUST.get()).replace(false);
        this.tag(OccultismTags.Items.NETHERITE_DUST).add(OccultismItems.NETHERITE_DUST.get()).replace(false);
        this.tag(OccultismTags.Items.NETHERITE_SCRAP_DUST).add(OccultismItems.NETHERITE_SCRAP_DUST.get()).replace(false);
        this.tag(OccultismTags.Items.RESEARCH_DUST).add(OccultismItems.RESEARCH_FRAGMENT_DUST.get()).replace(false);
        this.tag(OccultismTags.Items.WITHERITE_DUST).add(OccultismItems.WITHERITE_DUST.get()).replace(false);
        this.tag(OccultismTags.Items.OTHERSTONE_DUST).add(OccultismItems.BURNT_OTHERSTONE.get()).replace(false);
        this.tag(OccultismTags.Items.OTHERWORLD_WOOD_DUST).add(OccultismItems.OTHERWORLD_ASHES.get()).replace(false);
        this.tag(Tags.Items.DUSTS).addTags(
                        OccultismTags.Items.COPPER_DUST,
                        OccultismTags.Items.END_STONE_DUST,
                        OccultismTags.Items.GOLD_DUST,
                        OccultismTags.Items.IRON_DUST,
                        OccultismTags.Items.IESNIUM_DUST,
                        OccultismTags.Items.SILVER_DUST,
                        OccultismTags.Items.OBSIDIAN_DUST,
                        OccultismTags.Items.AMETHYST_DUST,
                        OccultismTags.Items.BLACKSTONE_DUST,
                        OccultismTags.Items.BLUE_ICE_DUST,
                        OccultismTags.Items.CALCITE_DUST,
                        OccultismTags.Items.ICE_DUST,
                        OccultismTags.Items.PACKED_ICE_DUST,
                        OccultismTags.Items.DRAGONYST_DUST,
                        OccultismTags.Items.ECHO_DUST,
                        OccultismTags.Items.EMERALD_DUST,
                        OccultismTags.Items.LAPIS_DUST,
                        OccultismTags.Items.NETHERITE_DUST,
                        OccultismTags.Items.NETHERITE_SCRAP_DUST,
                        OccultismTags.Items.RESEARCH_DUST,
                        OccultismTags.Items.WITHERITE_DUST,
                        OccultismTags.Items.OTHERSTONE_DUST,
                        OccultismTags.Items.OTHERWORLD_WOOD_DUST);
    }

    private TagKey<Item> cTag(String path) {
        return ItemTags.create(this.cLoc(path));
    }

    private ResourceLocation cLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath("c", path);
    }

    private ResourceLocation loc(String namespaceAndPath) {
        return ResourceLocation.tryParse(namespaceAndPath);
    }

}
