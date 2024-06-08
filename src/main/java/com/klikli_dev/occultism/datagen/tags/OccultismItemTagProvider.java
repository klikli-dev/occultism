package com.klikli_dev.occultism.datagen.tags;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
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
        this.addForgeTags(provider);
        this.addMinecraftTags(provider);
        this.addOccultismTags(provider);
        this.addCuriosTags(provider);

    }


    private void addCuriosTags(HolderLookup.Provider provider) {
        this.tag(OccultismTags.makeItemTag(new ResourceLocation(CuriosApi.MODID, "belt"))).add(OccultismItems.SATCHEL.get()).replace(false);
        this.tag(OccultismTags.makeItemTag(new ResourceLocation(CuriosApi.MODID, "hands"))).add(OccultismItems.STORAGE_REMOTE.get()).replace(false);
        this.tag(OccultismTags.makeItemTag(new ResourceLocation(CuriosApi.MODID, "head"))).add(OccultismItems.OTHERWORLD_GOGGLES.get()).replace(false);
        this.tag(OccultismTags.makeItemTag(new ResourceLocation(CuriosApi.MODID, "ring"))).add(OccultismItems.FAMILIAR_RING.get()).replace(false);
    }

    private void addOccultismTags(HolderLookup.Provider provider) {
        this.tag(OccultismTags.Items.DEMONIC_PARTNER_FOOD)
                .addTag(ItemTags.MEAT);

        this.tag(OccultismTags.Items.BOOK_OF_CALLING_FOLIOT)
                .add(OccultismItems.BOOK_OF_CALLING_FOLIOT_LUMBERJACK.get())
                .add(OccultismItems.BOOK_OF_CALLING_FOLIOT_CLEANER.get())
                .add(OccultismItems.BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS.get()).replace(false);
        this.tag(OccultismTags.Items.BOOK_OF_CALLING_DJINNI)
                .add(OccultismItems.BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE.get()).replace(false);
        this.tag(OccultismTags.Items.Miners.BASIC_RESOURCES).add(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get(),
                OccultismItems.MINER_DEBUG_UNSPECIALIZED.get()).replace(false);
        this.tag(OccultismTags.Items.Miners.DEEPS).add(OccultismItems.MINER_AFRIT_DEEPS.get(), OccultismItems.MINER_MARID_MASTER.get()).replace(false);
        this.tag(OccultismTags.Items.Miners.MASTER).add(OccultismItems.MINER_MARID_MASTER.get()).replace(false);
        this.tag(OccultismTags.Items.Miners.ORES).add(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get(),
                OccultismItems.MINER_DEBUG_UNSPECIALIZED.get(), OccultismItems.MINER_AFRIT_DEEPS.get(), OccultismItems.MINER_MARID_MASTER.get(), OccultismItems.MINER_DJINNI_ORES.get()).replace(false);
        this.copy(OccultismTags.Blocks.OTHERWORLD_SAPLINGS, OccultismTags.Items.OTHERWORLD_SAPLINGS);
        this.tag(OccultismTags.Items.TOOL_KNIVES).add(OccultismItems.BUTCHER_KNIFE.get()).replace(false);
        this.tag(OccultismTags.Items.ELYTRA).add(Items.ELYTRA).addOptional(new ResourceLocation("mana-and-artifice", "spectral_elytra")).replace(false);
        this.tag(OccultismTags.Items.OTHERWORLD_GOGGLES).add(OccultismItems.OTHERWORLD_GOGGLES.get()).replace(false);
    }

    private void addMinecraftTags(HolderLookup.Provider provider) {
        this.tag(ItemTags.CLUSTER_MAX_HARVESTABLES).add(OccultismItems.INFUSED_PICKAXE.get(), OccultismItems.IESNIUM_PICKAXE.get()).replace(false);
        this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
        this.copy(BlockTags.LOGS, ItemTags.LOGS);
        this.copy(BlockTags.PIGLIN_REPELLENTS, ItemTags.PIGLIN_REPELLENTS);
        this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        this.tag(ItemTags.PICKAXES).add(OccultismItems.INFUSED_PICKAXE.get(), OccultismItems.IESNIUM_PICKAXE.get()).replace(false);
        this.tag(ItemTags.SWORDS).add(OccultismItems.BUTCHER_KNIFE.get()).replace(false);

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

    }

    private void addForgeTags(HolderLookup.Provider provider) {
        // Ore Blocks
        this.copy(OccultismTags.Blocks.IESNIUM_ORE, OccultismTags.Items.IESNIUM_ORE);
        this.copy(OccultismTags.Blocks.SILVER_ORE, OccultismTags.Items.SILVER_ORE);
        this.tag(Tags.Items.ORES).addTags(OccultismTags.Items.IESNIUM_ORE, OccultismTags.Items.SILVER_ORE).replace(false);

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

        // Storage Blocks
        this.copy(OccultismTags.Blocks.STORAGE_BLOCKS_IESNIUM, OccultismTags.Items.STORAGE_BLOCK_IESNIUM);
        this.copy(OccultismTags.Blocks.STORAGE_BLOCKS_SILVER, OccultismTags.Items.STORAGE_BLOCK_SILVER);
        this.copy(OccultismTags.Blocks.STORAGE_BLOCKS_RAW_IESNIUM, OccultismTags.Items.STORAGE_BLOCK_RAW_IESNIUM);
        this.copy(OccultismTags.Blocks.STORAGE_BLOCKS_RAW_SILVER, OccultismTags.Items.STORAGE_BLOCK_RAW_SILVER);
        this.tag(Tags.Items.STORAGE_BLOCKS).addTags(OccultismTags.Items.STORAGE_BLOCK_IESNIUM, OccultismTags.Items.STORAGE_BLOCK_SILVER,
                OccultismTags.Items.STORAGE_BLOCK_RAW_IESNIUM, OccultismTags.Items.STORAGE_BLOCK_RAW_SILVER).replace(false);

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
    }

    private void addDusts(HolderLookup.Provider provider) {
        this.tag(OccultismTags.Items.COPPER_DUST).add(OccultismItems.COPPER_DUST.get()).replace(false);
        this.tag(OccultismTags.Items.END_STONE_DUST).add(OccultismItems.CRUSHED_END_STONE.get()).replace(false);
        this.tag(OccultismTags.Items.GOLD_DUST).add(OccultismItems.GOLD_DUST.get()).replace(false);
        this.tag(OccultismTags.Items.IRON_DUST).add(OccultismItems.IRON_DUST.get()).replace(false);
        this.tag(OccultismTags.Items.IESNIUM_DUST).add(OccultismItems.IESNIUM_DUST.get()).replace(false);
        this.tag(OccultismTags.Items.SILVER_DUST).add(OccultismItems.SILVER_DUST.get()).replace(false);
        this.tag(OccultismTags.Items.OBSIDIAN_DUST).add(OccultismItems.OBSIDIAN_DUST.get()).replace(false);
        this.tag(Tags.Items.DUSTS)
                .addTags(OccultismTags.Items.COPPER_DUST, OccultismTags.Items.END_STONE_DUST,
                        OccultismTags.Items.GOLD_DUST, OccultismTags.Items.IRON_DUST, OccultismTags.Items.IESNIUM_DUST,
                        OccultismTags.Items.SILVER_DUST, OccultismTags.Items.OBSIDIAN_DUST)
                .add(OccultismItems.BURNT_OTHERSTONE.get(), OccultismItems.OTHERWORLD_ASHES.get()).replace(false);

    }
}
