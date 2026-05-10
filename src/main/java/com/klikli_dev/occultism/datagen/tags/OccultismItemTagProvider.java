package com.klikli_dev.occultism.datagen.tags;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import com.klikli_dev.occultism.registry.OccultismTags.Blocks;
import com.klikli_dev.occultism.registry.OccultismTags.Items.Miners;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import org.jspecify.annotations.NonNull;
import top.theillusivec4.curios.api.CuriosResources;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class OccultismItemTagProvider extends IntrinsicHolderTagsProvider<Item> {

    private final CompletableFuture<TagLookup<Block>> blockTags;
    private final Map<TagKey<Block>, TagKey<Item>> tagsToCopy = new HashMap<>();

    public OccultismItemTagProvider(PackOutput p_275343_, CompletableFuture<Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_) {
        //noinspection deprecation
        super(p_275343_, Registries.ITEM, p_275729_, item -> item.builtInRegistryHolder().key(), Occultism.MODID);
        this.blockTags = p_275322_;
    }

    /**
     * Copies entries from a block tag into an item tag (mirrors the removed ItemTagsProvider.copy method).
     */
    protected void copy(TagKey<Block> blockTag, TagKey<Item> itemTag) {
        this.tagsToCopy.put(blockTag, itemTag);
    }

    @Override
    protected @NonNull CompletableFuture<Provider> createContentsProvider() {
        return super.createContentsProvider().thenCombine(this.blockTags, (provider, lookup) -> {
            this.tagsToCopy.forEach((blockTag, itemTag) -> {
                TagBuilder tagBuilder = this.getOrCreateRawBuilder(itemTag);
                Optional<TagBuilder> optional = lookup.apply(blockTag);
                TagBuilder fromBuilder = optional.orElseThrow(() -> new IllegalStateException("Missing block tag " + blockTag.location()));
                fromBuilder.build().forEach(tagBuilder::add);
            });
            return provider;
        });
    }

    @Override
    protected void addTags(@NonNull Provider provider) {
        this.addCommonTags(provider);
        this.addMinecraftTags(provider);
        this.addOccultismTags(provider);
        this.addCuriosTags(provider);
        this.addOptionalOreTagsForMinerCompat(provider);

    }

    private void addOptionalOreTagsForMinerCompat(Provider provider) {
        this.getOrCreateRawBuilder(this.cTag("ores/bone_fragments")).addOptionalElement(this.loc("aoa3:bone_fragments_ore"));
        this.getOrCreateRawBuilder(this.cTag("ores/baronyte")).addOptionalElement(this.loc("aoa3:baronyte_ore"));
        this.getOrCreateRawBuilder(this.cTag("ores/blazium")).addOptionalElement(this.loc("aoa3:blazium_ore"));
        this.getOrCreateRawBuilder(this.cTag("ores/bloodstone")).addOptionalElement(this.loc("aoa3:bloodstone_ore"));
        this.getOrCreateRawBuilder(this.cTag("ores/blue_gemstone")).addOptionalElement(this.loc("aoa3:blue_gemstone_ore"));
        this.getOrCreateRawBuilder(this.cTag("ores/charged_runium")).addOptionalElement(this.loc("aoa3:charged_runium_ore"));
        this.getOrCreateRawBuilder(this.cTag("ores/crystallite")).addOptionalElement(this.loc("aoa3:crystallite_ore"));
        this.getOrCreateRawBuilder(this.cTag("ores/elecanium")).addOptionalElement(this.loc("aoa3:elecanium_ore"));
        this.getOrCreateRawBuilder(this.cTag("ores/emberstone")).addOptionalElement(this.loc("aoa3:emberstone_ore"));
        this.getOrCreateRawBuilder(this.cTag("ores/gemenyte")).addOptionalElement(this.loc("aoa3:gemenyte_ore"));
        this.getOrCreateRawBuilder(this.cTag("ores/ghastly")).addOptionalElement(this.loc("aoa3:ghastly_ore"));
        this.getOrCreateRawBuilder(this.cTag("ores/ghoulish")).addOptionalElement(this.loc("aoa3:ghoulish_ore"));
        this.getOrCreateRawBuilder(this.cTag("ores/green_gemstone")).addOptionalElement(this.loc("aoa3:green_gemstone_ore"));
        this.getOrCreateRawBuilder(this.cTag("ores/jade")).addOptionalElement(this.loc("aoa3:jade_ore"));
        this.getOrCreateRawBuilder(this.cTag("ores/jewelyte")).addOptionalElement(this.loc("aoa3:jewelyte_ore"));
        this.getOrCreateRawBuilder(this.cTag("ores/limonite")).addOptionalElement(this.loc("aoa3:limonite_ore"));
        this.getOrCreateRawBuilder(this.cTag("ores/lyon")).addOptionalElement(this.loc("aoa3:lyon_ore"));
        this.getOrCreateRawBuilder(this.cTag("ores/mystite")).addOptionalElement(this.loc("aoa3:mystite_ore"));
        this.getOrCreateRawBuilder(this.cTag("ores/ornamyte")).addOptionalElement(this.loc("aoa3:ornamyte_ore"));
        this.getOrCreateRawBuilder(this.cTag("ores/purple_gemstone")).addOptionalElement(this.loc("aoa3:purple_gemstone_ore"));
        this.getOrCreateRawBuilder(this.cTag("ores/red_gemstone")).addOptionalElement(this.loc("aoa3:red_gemstone_ore"));
        this.getOrCreateRawBuilder(this.cTag("ores/runium")).addOptionalElement(this.loc("aoa3:runium_ore"));
        this.getOrCreateRawBuilder(this.cTag("ores/shyregem")).addOptionalElement(this.loc("aoa3:shyregem_ore"));
        this.getOrCreateRawBuilder(this.cTag("ores/shyrestone")).addOptionalElement(this.loc("aoa3:shyrestone_ore"));
        this.getOrCreateRawBuilder(this.cTag("ores/varsium")).addOptionalElement(this.loc("aoa3:varsium_ore"));
        this.getOrCreateRawBuilder(this.cTag("ores/white_gemstone")).addOptionalElement(this.loc("aoa3:white_gemstone_ore"));
        this.getOrCreateRawBuilder(this.cTag("ores/yellow_gemstone")).addOptionalElement(this.loc("aoa3:yellow_gemstone_ore"));
        this.getOrCreateRawBuilder(this.cTag("ores/dark_gem"))
                .addOptionalElement(this.loc("evilcraft:dark_ores"))
                .addOptionalTag(this.loc("evilcraft:ores/dark_gem"));
        this.getOrCreateRawBuilder(this.cTag("gems/dark_gem")).addOptionalElement(this.loc("evilcraft:dark_gem"));
        this.getOrCreateRawBuilder(this.cTag("dusts/dark_gem")).addOptionalElement(this.loc("evilcraft:dark_gem_crushed"));
        this.getOrCreateRawBuilder(this.cTag("storage_blocks/dark_gem")).addOptionalElement(this.loc("evilcraft:dark_block"));
        this.getOrCreateRawBuilder(this.cTag("ores/black_quartz")).addOptionalElement(this.loc("actuallyadditions:black_quartz_ore"));
        this.getOrCreateRawBuilder(this.cTag("gems/black_quartz")).addOptionalElement(this.loc("actuallyadditions:black_quartz"));
        this.getOrCreateRawBuilder(this.cTag("dusts/certus_quartz")).addOptionalElement(this.loc("ae2:certus_quartz_dust"));
        this.getOrCreateRawBuilder(this.cTag("dusts/fluix")).addOptionalElement(this.loc("ae2:fluix_dust"));
        this.getOrCreateRawBuilder(this.cTag("dusts/sky_stone")).addOptionalElement(this.loc("ae2:sky_dust"));
        this.getOrCreateRawBuilder(this.cTag("sky_stones"))
                .addOptionalElement(this.loc("ae2:sky_stone_block"))
                .addOptionalElement(this.loc("ae2:smooth_sky_stone_block"))
                .addOptionalElement(this.loc("ae2:sky_stone_brick"))
                .addOptionalElement(this.loc("ae2:sky_stone_small_brick"));
        this.getOrCreateRawBuilder(this.cTag("storage_blocks/rune")).addOptionalElement(this.loc("forbidden_arcanus:rune_block"));
        this.getOrCreateRawBuilder(this.cTag("coal_coke")).addOptionalElement(this.loc("modern_industrialization:coke"));
        this.getOrCreateRawBuilder(this.cTag("dusts/coal_coke")).addOptionalTag(this.cTag("dusts/coke").location());
    }

    private void addCuriosTags(Provider provider) {
        this.tag(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath(CuriosResources.MOD_ID, "belt"))).add(OccultismItems.SATCHEL.get()).add(OccultismItems.ENDER_SATCHEL.get()).replace(false);
        this.tag(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath(CuriosResources.MOD_ID, "hands"))).add(OccultismItems.STORAGE_REMOTE.get()).add(OccultismItems.TRUE_SIGHT_STAFF.get()).replace(false);
        this.tag(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath(CuriosResources.MOD_ID, "head"))).add(OccultismItems.OTHERWORLD_GOGGLES.get()).replace(false);
        this.tag(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath(CuriosResources.MOD_ID, "ring"))).add(OccultismItems.FAMILIAR_RING.get()).replace(false);
    }

    private void addOccultismTags(Provider provider) {
        this.copy(Blocks.OCCULTISM_CANDLES, OccultismTags.Items.OCCULTISM_CANDLES);
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

        this.copy(Blocks.PENTACLE_MATERIALS, OccultismTags.Items.PENTACLE_MATERIALS);
        this.tag(OccultismTags.Items.PENTACLE_MATERIALS)
                .addOptionalTag(OccultismTags.Items.TOOLS_CHALK)
                .replace(false);


        this.tag(OccultismTags.Items.DEMONIC_PARTNER_FOOD)
                .addTag(ItemTags.MEAT);

        this.tag(OccultismTags.Items.BOOK_OF_CALLING_FOLIOT)
                .add(OccultismItems.BOOK_OF_CALLING_FOLIOT_LUMBERJACK.get())
                .add(OccultismItems.BOOK_OF_CALLING_FOLIOT_FARMER.get())
                .add(OccultismItems.BOOK_OF_CALLING_FOLIOT_CLEANER.get())
                .add(OccultismItems.BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS.get()).replace(false);
        this.tag(OccultismTags.Items.BOOK_OF_CALLING_DJINNI)
                .add(OccultismItems.BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE.get()).replace(false);

        this.tag(OccultismTags.Items.BOOKS_OF_BINDING)
                .add(OccultismItems.BOOK_OF_BINDING_FOLIOT.get())
                .add(OccultismItems.BOOK_OF_BINDING_DJINNI.get())
                .add(OccultismItems.BOOK_OF_BINDING_AFRIT.get())
                .add(OccultismItems.BOOK_OF_BINDING_MARID.get());

        this.tag(OccultismTags.Items.BOOKS_FOR_EMPTY)
                .add(Items.WRITABLE_BOOK)
                .add(Items.WRITTEN_BOOK)
                .add(Items.ENCHANTED_BOOK)
                .add(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get())
                .add(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get())
                .add(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get())
                .add(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get())
                .add(OccultismItems.BOOK_OF_BINDING_FOLIOT.get())
                .add(OccultismItems.BOOK_OF_BINDING_DJINNI.get())
                .add(OccultismItems.BOOK_OF_BINDING_AFRIT.get())
                .add(OccultismItems.BOOK_OF_BINDING_MARID.get())
                .add(OccultismItems.BOOK_OF_CALLING_FOLIOT_LUMBERJACK.get())
                .add(OccultismItems.BOOK_OF_CALLING_FOLIOT_FARMER.get())
                .add(OccultismItems.BOOK_OF_CALLING_FOLIOT_CLEANER.get())
                .add(OccultismItems.BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS.get())
                .add(OccultismItems.BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE.get());

        this.tag(Miners.BASIC).add(
                OccultismItems.MINER_DEBUG_UNSPECIALIZED.get(),
                OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get(),
                OccultismItems.MINER_DJINNI_ORES.get(),
                OccultismItems.MINER_AFRIT_DEEPS.get(),
                OccultismItems.MINER_MARID_MASTER.get(),
                OccultismItems.MINER_ANCIENT_ELDRITCH.get());
        this.tag(Miners.IRON).add(
                OccultismItems.MINER_DEBUG_UNSPECIALIZED.get(),
                OccultismItems.MINER_DJINNI_ORES.get(),
                OccultismItems.MINER_AFRIT_DEEPS.get(),
                OccultismItems.MINER_MARID_MASTER.get(),
                OccultismItems.MINER_ANCIENT_ELDRITCH.get());
        this.tag(Miners.DIAMOND).add(
                OccultismItems.MINER_AFRIT_DEEPS.get(),
                OccultismItems.MINER_MARID_MASTER.get(),
                OccultismItems.MINER_ANCIENT_ELDRITCH.get());
        this.tag(Miners.NETHERITE).add(
                OccultismItems.MINER_MARID_MASTER.get(),
                OccultismItems.MINER_ANCIENT_ELDRITCH.get());
        this.tag(Miners.ELDRITCH).add(
                OccultismItems.MINER_ANCIENT_ELDRITCH.get());

        this.tag(Miners.MINERS)
                .addTag(Miners.BASIC)
                .addTag(Miners.IRON)
                .addTag(Miners.DIAMOND)
                .addTag(Miners.NETHERITE)
                .addTag(Miners.ELDRITCH).replace(false);

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

        this.tag(OccultismTags.Items.START_SPIRIT_FIRE)
                .add(OccultismItems.DATURA.get())
                .add(OccultismItems.PITAYA.get());
        this.copy(Blocks.OTHERWORLD_SAPLINGS, OccultismTags.Items.OTHERWORLD_SAPLINGS);
        this.copy(Blocks.OTHERWORLD_SAPLINGS_NATURAL, OccultismTags.Items.OTHERWORLD_SAPLINGS_NATURAL);
        this.tag(OccultismTags.Items.TOOLS_KNIFE)
                .add(OccultismItems.BUTCHER_KNIFE.get())
                .add(OccultismItems.IESNIUM_BUTCHER_KNIFE.get());
        this.tag(OccultismTags.Items.TOOLS_KNIFE_IESNIUM)
                .add(OccultismItems.IESNIUM_BUTCHER_KNIFE.get());
        this.tag(Tags.Items.TOOLS).addOptionalTag(OccultismTags.Items.TOOLS_KNIFE).replace(false); //Don't place chalks
        this.tag(OccultismTags.Items.ELYTRA).add(Items.ELYTRA).replace(false);
        this.tag(OccultismTags.Items.OTHERWORLD_GOGGLES).add(OccultismItems.OTHERWORLD_GOGGLES.get()).replace(false);
        this.tag(OccultismTags.Items.OTHERSTONE).add(OccultismBlocks.OTHERSTONE.asItem());
        this.tag(OccultismTags.Items.OTHERCOBBLESTONE).add(OccultismBlocks.OTHERCOBBLESTONE.asItem());
    }

    private void addMinecraftTags(Provider provider) {
        this.tag(ItemTags.BOOKSHELF_BOOKS)
                .add(OccultismItems.DICTIONARY_OF_SPIRITS.get())
                .add(OccultismItems.BOOK_OF_BINDING_AFRIT.get())
                .add(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get())
                .add(OccultismItems.BOOK_OF_BINDING_DJINNI.get())
                .add(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get())
                .add(OccultismItems.BOOK_OF_BINDING_FOLIOT.get())
                .add(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get())
                .add(OccultismItems.BOOK_OF_BINDING_MARID.get())
                .add(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get())
                .add(OccultismItems.BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE.get())
                .add(OccultismItems.BOOK_OF_CALLING_FOLIOT_CLEANER.get())
                .add(OccultismItems.BOOK_OF_CALLING_FOLIOT_LUMBERJACK.get())
                .add(OccultismItems.BOOK_OF_CALLING_FOLIOT_FARMER.get())
                .add(OccultismItems.BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS.get())
                .add(OccultismItems.BOOK_OF_BINDING_EMPTY.get())
                .add(OccultismItems.TABOO_BOOK.get());

        this.tag(ItemTags.MINING_ENCHANTABLE).addTag(Miners.MINERS);
        this.tag(ItemTags.MINING_LOOT_ENCHANTABLE).addTag(Miners.MINERS);
        this.tag(ItemTags.DURABILITY_ENCHANTABLE)
                .addTag(Miners.MINERS)
                .addTag(OccultismTags.Items.TOOLS_CHALK)
                .addTag(OccultismTags.Items.TOOLS_KNIFE)
                .add(OccultismItems.MINING_DIMENSION_CORE_PIECE.asItem())
                .add(OccultismItems.GRAY_PASTE.asItem())
                .add(OccultismItems.NATURE_PASTE.asItem());
        this.tag(ItemTags.MELEE_WEAPON_ENCHANTABLE).addTag(OccultismTags.Items.TOOLS_KNIFE);
        this.tag(ItemTags.SHARP_WEAPON_ENCHANTABLE).addTag(OccultismTags.Items.TOOLS_KNIFE);
        this.tag(ItemTags.WEAPON_ENCHANTABLE).addTag(OccultismTags.Items.TOOLS_KNIFE);

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
        this.tag(this.cTag("tools/knife")).add(OccultismItems.BUTCHER_KNIFE.get()).add(OccultismItems.IESNIUM_BUTCHER_KNIFE.get());

        this.tag(ItemTags.SMALL_FLOWERS).add(OccultismBlocks.OTHERFLOWER.asItem()).add(OccultismBlocks.OTHERFLOWER_NATURAL.asItem()).replace(false);
        this.tag(ItemTags.COMPASSES).add(OccultismItems.VITALITY_COMPASS.asItem());
    }

    private void addCommonTags(Provider provider) {
        this.tag(Tags.Items.MINING_TOOL_TOOLS)
                .add(OccultismItems.INFUSED_PICKAXE.get())
                .add(OccultismItems.IESNIUM_PICKAXE.get())
                .replace(false);

        this.copy(BlockTags.SLABS, ItemTags.SLABS);

        // Ore Blocks
        this.copy(Blocks.IESNIUM_ORE, OccultismTags.Items.IESNIUM_ORE);
        this.copy(Blocks.SILVER_ORE, OccultismTags.Items.SILVER_ORE);
        //noinspection unchecked
        this.tag(Tags.Items.ORES).addTags(OccultismTags.Items.IESNIUM_ORE, OccultismTags.Items.SILVER_ORE).replace(false);

        this.copy(Tags.Blocks.ORES_IN_GROUND_STONE, Tags.Items.ORES_IN_GROUND_STONE);
        this.copy(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE, Tags.Items.ORES_IN_GROUND_DEEPSLATE);
        this.copy(Tags.Blocks.ORES_IN_GROUND_NETHERRACK, Tags.Items.ORES_IN_GROUND_NETHERRACK);

        // Raw Materials
        this.tag(OccultismTags.Items.RAW_SILVER).add(OccultismItems.RAW_SILVER.get()).replace(false);
        this.tag(OccultismTags.Items.RAW_IESNIUM).add(OccultismItems.RAW_IESNIUM.get()).replace(false);
        //noinspection unchecked
        this.tag(Tags.Items.RAW_MATERIALS).addTags(OccultismTags.Items.RAW_IESNIUM, OccultismTags.Items.RAW_SILVER).replace(false);

        // Dusts
        this.addDusts(provider);

        // Possessed mobs loots
        this.addMobLoot(provider);

        //Random spawn egg
        this.addRandomEggs(provider);

        // Crops
        this.tag(OccultismTags.Items.DATURA_CROP).add(OccultismItems.DATURA.get()).replace(false);
        //noinspection unchecked
        this.tag(Tags.Items.CROPS).addTags(OccultismTags.Items.DATURA_CROP).replace(false);

        // Ingots
        this.tag(OccultismTags.Items.IESNIUM_INGOT).add(OccultismItems.IESNIUM_INGOT.get()).replace(false);
        this.tag(OccultismTags.Items.SILVER_INGOT).add(OccultismItems.SILVER_INGOT.get()).replace(false);
        //noinspection unchecked
        this.tag(Tags.Items.INGOTS).addTags(OccultismTags.Items.IESNIUM_INGOT, OccultismTags.Items.SILVER_INGOT).replace(false);

        // Nuggets
        this.tag(OccultismTags.Items.IESNIUM_NUGGET).add(OccultismItems.IESNIUM_NUGGET.get()).replace(false);
        this.tag(OccultismTags.Items.SILVER_NUGGET).add(OccultismItems.SILVER_NUGGET.get()).replace(false);
        //noinspection unchecked
        this.tag(Tags.Items.NUGGETS).addTags(OccultismTags.Items.IESNIUM_NUGGET, OccultismTags.Items.SILVER_NUGGET).replace(false);

        // Seeds
        this.tag(OccultismTags.Items.DATURA_SEEDS).add(OccultismItems.DATURA_SEEDS.get()).replace(false);
        //noinspection unchecked
        this.tag(Tags.Items.SEEDS).addTags(OccultismTags.Items.DATURA_SEEDS).replace(false);

        //Foods
        this.tag(Tags.Items.FOODS)
                .add(OccultismItems.DATURA.get())
                .add(OccultismItems.DEMONS_DREAM_ESSENCE.get())
                .add(OccultismItems.OTHERWORLD_ESSENCE.get())
                .add(OccultismItems.BEAVER_NUGGET.get())
                .add(OccultismItems.CURSED_HONEY.get())
                .add(OccultismItems.SWEET_HONEY_HEART.get())
                .add(OccultismItems.DEMONIC_MEAT.get())
                .add(OccultismItems.PITAYA.get())
                .add(OccultismItems.PITAYA_GOLDEN.get())
                .add(OccultismItems.PITAYA_ENCHANTED.get())
                .replace(false);
        this.tag(ItemTags.MEAT)
                .add(OccultismItems.DEMONIC_MEAT.get())
                .replace(false);
        this.tag(Tags.Items.FOODS_FRUIT)
                .add(OccultismItems.PITAYA.get())
                .add(OccultismItems.PITAYA_GOLDEN.get())
                .add(OccultismItems.PITAYA_ENCHANTED.get())
                .replace(false);
        this.tag(Tags.Items.ANIMAL_FOODS)
                .add(OccultismItems.PITAYA.get())
                .add(OccultismItems.PITAYA_GOLDEN.get())
                .add(OccultismItems.PITAYA_ENCHANTED.get())
                .replace(false);
        this.tag(ItemTags.HORSE_FOOD)
                .add(OccultismItems.PITAYA.get())
                .add(OccultismItems.PITAYA_GOLDEN.get())
                .add(OccultismItems.PITAYA_ENCHANTED.get())
                .replace(false);
        this.tag(ItemTags.ZOMBIE_HORSE_FOOD)
                .add(OccultismItems.PITAYA.get())
                .add(OccultismItems.PITAYA_GOLDEN.get())
                .add(OccultismItems.PITAYA_ENCHANTED.get())
                .replace(false);
        this.tag(ItemTags.HORSE_TEMPT_ITEMS)
                .add(OccultismItems.PITAYA_GOLDEN.get())
                .add(OccultismItems.PITAYA_ENCHANTED.get())
                .replace(false);
        this.tag(Tags.Items.FOODS_GOLDEN)
                .add(OccultismItems.PITAYA_GOLDEN.get())
                .add(OccultismItems.PITAYA_ENCHANTED.get())
                .replace(false);
        this.tag(ItemTags.PIGLIN_LOVED)
                .add(OccultismItems.PITAYA_GOLDEN.get())
                .add(OccultismItems.PITAYA_ENCHANTED.get())
                .replace(false);

        // Storage Blocks
        this.copy(Blocks.STORAGE_BLOCKS_IESNIUM, OccultismTags.Items.STORAGE_BLOCK_IESNIUM);
        this.copy(Blocks.STORAGE_BLOCKS_SILVER, OccultismTags.Items.STORAGE_BLOCK_SILVER);
        this.copy(Blocks.STORAGE_BLOCKS_RAW_IESNIUM, OccultismTags.Items.STORAGE_BLOCK_RAW_IESNIUM);
        this.copy(Blocks.STORAGE_BLOCKS_RAW_SILVER, OccultismTags.Items.STORAGE_BLOCK_RAW_SILVER);
        //noinspection unchecked
        this.tag(Tags.Items.STORAGE_BLOCKS).addTags(OccultismTags.Items.STORAGE_BLOCK_IESNIUM, OccultismTags.Items.STORAGE_BLOCK_SILVER,
                OccultismTags.Items.STORAGE_BLOCK_RAW_IESNIUM, OccultismTags.Items.STORAGE_BLOCK_RAW_SILVER).replace(false);
        this.copy(Blocks.MUSHROOM_BLOCKS, OccultismTags.Items.MUSHROOM_BLOCKS);
        this.copy(Blocks.ENCHANTING_TABLES, OccultismTags.Items.ENCHANTING_TABLES);
        this.copy(Blocks.IRON_BARS, OccultismTags.Items.IRON_BARS);
        this.tag(OccultismTags.Items.TUBE_CORALS).add(Items.TUBE_CORAL).add(Items.TUBE_CORAL_FAN);

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
        this.copy(Blocks.OTHERWORLD_LOGS, OccultismTags.Items.OTHERWORLD_LOGS);

        // Clay
        this.tag(OccultismTags.Items.CLAY).add(Items.CLAY_BALL).replace(false);
    }

    private void addDusts(Provider provider) {
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
        this.tag(OccultismTags.Items.OTHERROCK_DUST).add(OccultismItems.BURNT_OTHERROCK.get()).replace(false);
        this.tag(OccultismTags.Items.OTHERWORLD_WOOD_DUST).add(OccultismItems.OTHERWORLD_ASHES.get()).replace(false);
        //noinspection unchecked
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
                OccultismTags.Items.OTHERROCK_DUST,
                OccultismTags.Items.OTHERWORLD_WOOD_DUST);
        this.tag(OccultismTags.Items.CHALK_BASE_DUST)
                .addTag(OccultismTags.Items.OTHERSTONE_DUST)
                .addTag(OccultismTags.Items.OTHERROCK_DUST);
    }

    private void addMobLoot(Provider provider) {
        this.tag(OccultismTags.Items.DROPS_POSSESSED_BLAZE)
                .add(Items.BLAZE_ROD)
                .add(Items.BLAZE_POWDER)
                .add(Items.NETHER_WART)
                .add(Items.CRIMSON_FUNGUS)
                .add(Items.WARPED_FUNGUS)
                .add(Items.RED_MUSHROOM)
                .add(Items.BROWN_MUSHROOM)
                .add(Items.CRIMSON_ROOTS)
                .add(Items.WARPED_ROOTS)
                .add(Items.WEEPING_VINES)
                .add(Items.TWISTING_VINES)
                .add(Items.NETHERRACK)
                .add(Items.NETHER_QUARTZ_ORE)
                .add(Items.CRIMSON_NYLIUM)
                .add(Items.WARPED_NYLIUM)
                .add(Items.NETHER_WART_BLOCK)
                .add(Items.WARPED_WART_BLOCK)
                .add(Items.SOUL_SAND)
                .add(Items.SOUL_SOIL)
                .add(Items.BASALT)
                .add(Items.BLACKSTONE)
                .add(Items.GRAVEL)
                .add(Items.BONE_BLOCK)
                .add(Items.GILDED_BLACKSTONE)
                .add(Items.GLOWSTONE_DUST)
                .add(Items.MAGMA_BLOCK)
                .add(Items.GLOWSTONE)
                .add(Items.SHROOMLIGHT)
                .add(Items.OBSIDIAN)
                .add(Items.CRYING_OBSIDIAN)
                .add(Items.ANCIENT_DEBRIS);

        this.tag(OccultismTags.Items.DROPS_POSSESSED_BREEZE)
                .add(Items.OMINOUS_TRIAL_KEY)
                .add(Items.BREEZE_ROD)
                .add(Items.BOLT_ARMOR_TRIM_SMITHING_TEMPLATE)
                .add(Items.GUSTER_BANNER_PATTERN)
                .add(Items.MUSIC_DISC_PRECIPICE);

        this.tag(OccultismTags.Items.DROPS_POSSESSED_ELDER_GUARDIAN)
                .add(Items.HEART_OF_THE_SEA)
                .add(Items.NAUTILUS_SHELL)
                .add(Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE)
                .add(Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE)
                .add(Items.WET_SPONGE)
                .add(Items.TROPICAL_FISH)
                .add(Items.COD)
                .add(Items.SALMON)
                .add(Items.PUFFERFISH)
                .add(Items.COOKED_COD)
                .add(Items.COOKED_SALMON)
                .add(Items.PRISMARINE_SHARD)
                .add(Items.PRISMARINE_CRYSTALS);

        this.tag(OccultismTags.Items.DROPS_POSSESSED_ENDERMAN)
                .add(Items.ENDER_PEARL)
                .add(Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE);

        this.tag(OccultismTags.Items.DROPS_POSSESSED_ENDERMITE)
                .add(Items.END_STONE)
                .add(Items.END_STONE_BRICKS)
                .add(Items.FERMENTED_SPIDER_EYE)
                .add(Items.SPIDER_EYE)
                .add(Items.ENDER_EYE);

        this.tag(OccultismTags.Items.DROPS_POSSESSED_EVOKER)
                .add(Items.TOTEM_OF_UNDYING)
                .add(Items.OMINOUS_BOTTLE)
                .add(Items.VEX_ARMOR_TRIM_SMITHING_TEMPLATE)
                .add(Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE);

        this.tag(OccultismTags.Items.DROPS_POSSESSED_GHAST)
                .add(Items.GHAST_TEAR)
                .add(Items.GUNPOWDER)
                .add(Items.MUSIC_DISC_TEARS)
                .add(Items.DRIED_GHAST);

        this.tag(OccultismTags.Items.DROPS_POSSESSED_HOGLIN)
                .add(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE)
                .add(Items.NETHERITE_SCRAP)
                .add(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE)
                .add(Items.PIGLIN_BANNER_PATTERN)
                .add(Items.NETHER_BRICK);

        this.tag(OccultismTags.Items.DROPS_POSSESSED_PHANTOM)
                .add(Items.PHANTOM_MEMBRANE)
                .add(Items.WIND_CHARGE);

        this.tag(OccultismTags.Items.DROPS_POSSESSED_SHULKER)
                .add(Items.SHULKER_SHELL)
                .add(Items.CHORUS_FLOWER)
                .add(Items.CHORUS_FRUIT)
                .add(Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE);

        this.tag(OccultismTags.Items.DROPS_POSSESSED_SKELETON)
                .add(Items.SKELETON_SKULL)
                .add(Items.BONE)
                .add(Items.ARROW);

        this.tag(OccultismTags.Items.DROPS_POSSESSED_STRONG_BREEZE)
                .add(Items.HEAVY_CORE)
                .add(Items.FLOW_BANNER_PATTERN)
                .add(Items.FLOW_POTTERY_SHERD)
                .add(Items.FLOW_ARMOR_TRIM_SMITHING_TEMPLATE)
                .add(Items.MUSIC_DISC_CREATOR);

        this.tag(OccultismTags.Items.DROPS_POSSESSED_WARDEN)
                .add(Items.ECHO_SHARD)
                .add(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE)
                .add(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE)
                .add(Items.MUSIC_DISC_OTHERSIDE)
                .add(Items.DISC_FRAGMENT_5)
                .add(Items.SCULK)
                .add(Items.SCULK_VEIN)
                .add(Items.SCULK_CATALYST)
                .add(Items.SCULK_SHRIEKER)
                .add(Items.SCULK_SENSOR);

        this.tag(OccultismTags.Items.DROPS_POSSESSED_WEAK_BREEZE)
                .add(Items.TRIAL_KEY)
                .add(Items.WIND_CHARGE)
                .add(Items.OMINOUS_BOTTLE)
                .add(Items.MUSIC_DISC_CREATOR_MUSIC_BOX)
                .add(Items.SCRAPE_POTTERY_SHERD)
                .add(Items.GUSTER_POTTERY_SHERD);

        this.tag(OccultismTags.Items.DROPS_POSSESSED_WEAK_SHULKER)
                .add(Items.CHORUS_FRUIT)
                .add(Items.SHULKER_SHELL);

        this.tag(OccultismTags.Items.DROPS_POSSESSED_WITCH)
                .add(Items.EXPERIENCE_BOTTLE)
                .add(Items.OMINOUS_BOTTLE)
                .add(Items.HONEY_BOTTLE);

        this.tag(OccultismTags.Items.DROPS_POSSESSED_ZOMBIFIED_PIGLIN)
                .add(OccultismItems.DEMONIC_MEAT.get())
                .add(OccultismItems.TALLOW.get())
                .add(Items.PORKCHOP)
                .add(Items.ROTTEN_FLESH);

        this.tag(OccultismTags.Items.DROPS_POSSESSED_GUARDIAN)
                .add(Items.SEA_PICKLE)
                .add(Items.KELP)
                .add(Items.TUBE_CORAL)
                .add(Items.BRAIN_CORAL)
                .add(Items.BUBBLE_CORAL)
                .add(Items.FIRE_CORAL)
                .add(Items.HORN_CORAL)
                .add(Items.TUBE_CORAL_BLOCK)
                .add(Items.BRAIN_CORAL_BLOCK)
                .add(Items.BUBBLE_CORAL_BLOCK)
                .add(Items.FIRE_CORAL_BLOCK)
                .add(Items.HORN_CORAL_BLOCK)
                .add(Items.TUBE_CORAL_FAN)
                .add(Items.BRAIN_CORAL_FAN)
                .add(Items.BUBBLE_CORAL_FAN)
                .add(Items.FIRE_CORAL_FAN)
                .add(Items.HORN_CORAL_FAN)
                .add(Items.PRISMARINE_SHARD)
                .add(Items.PRISMARINE_CRYSTALS);

        this.tag(OccultismTags.Items.DROPS_WILD_HUNT)
                .add(Items.WITHER_SKELETON_SKULL)
                .add(Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE)
                .add(Items.WITHER_ROSE)
                .add(Items.COAL)
                .add(Items.BONE)
                .add(Items.ARROW);

        this.tag(OccultismTags.Items.DROPS_WILD_HORDE_CREEPER)
                .add(Items.MUSIC_DISC_CAT)
                .add(Items.MUSIC_DISC_13)
                .add(Items.MUSIC_DISC_BLOCKS)
                .add(Items.MUSIC_DISC_CHIRP)
                .add(Items.MUSIC_DISC_FAR)
                .add(Items.MUSIC_DISC_MALL)
                .add(Items.MUSIC_DISC_MELLOHI)
                .add(Items.MUSIC_DISC_STAL)
                .add(Items.MUSIC_DISC_STRAD)
                .add(Items.MUSIC_DISC_WARD)
                .add(Items.MUSIC_DISC_11)
                .add(Items.MUSIC_DISC_WAIT);

        this.tag(OccultismTags.Items.DROPS_WILD_HORDE_DROWNED)
                .add(Items.SNIFFER_EGG)
                .add(Items.TRIDENT)
                .add(Items.TURTLE_EGG)
                .add(Items.SHELTER_POTTERY_SHERD)
                .add(Items.SNORT_POTTERY_SHERD)
                .add(Items.ANGLER_POTTERY_SHERD)
                .add(Items.PLENTY_POTTERY_SHERD)
                .add(Items.BLADE_POTTERY_SHERD)
                .add(Items.EXPLORER_POTTERY_SHERD)
                .add(Items.MOURNER_POTTERY_SHERD)
                .add(Items.COPPER_INGOT)
                .add(Items.PRISMARINE_SHARD)
                .add(Items.PRISMARINE_CRYSTALS);

        this.tag(OccultismTags.Items.DROPS_WILD_HORDE_HUSK)
                .add(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE)
                .add(Items.SKULL_POTTERY_SHERD)
                .add(Items.ARCHER_POTTERY_SHERD)
                .add(Items.PRIZE_POTTERY_SHERD)
                .add(Items.MINER_POTTERY_SHERD)
                .add(Items.BREWER_POTTERY_SHERD)
                .add(Items.ARMS_UP_POTTERY_SHERD);
        this.tag(OccultismTags.Items.DROPS_WILD_HORDE_PARCHED)
                .add(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE)
                .add(Items.SKULL_POTTERY_SHERD)
                .add(Items.ARCHER_POTTERY_SHERD)
                .add(Items.PRIZE_POTTERY_SHERD)
                .add(Items.MINER_POTTERY_SHERD)
                .add(Items.BREWER_POTTERY_SHERD)
                .add(Items.ARMS_UP_POTTERY_SHERD);

        this.tag(OccultismTags.Items.DROPS_WILD_HORDE_SILVERFISH)
                .add(Items.MUSIC_DISC_RELIC)
                .add(Items.HEART_POTTERY_SHERD)
                .add(Items.SHEAF_POTTERY_SHERD)
                .add(Items.DANGER_POTTERY_SHERD)
                .add(Items.BURN_POTTERY_SHERD)
                .add(Items.HOWL_POTTERY_SHERD)
                .add(Items.FRIEND_POTTERY_SHERD)
                .add(Items.HEARTBREAK_POTTERY_SHERD)
                .add(Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE)
                .add(Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE)
                .add(Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE)
                .add(Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE);
    }

    private void addRandomEggs(Provider provider) {
        this.tag(OccultismTags.Items.RANDOM_SPAWN_COMMON)
                .add(Items.CHICKEN_SPAWN_EGG)
                .add(Items.COW_SPAWN_EGG)
                .add(Items.PIG_SPAWN_EGG)
                .add(Items.SHEEP_SPAWN_EGG)
                .add(Items.SQUID_SPAWN_EGG)
                .add(Items.WOLF_SPAWN_EGG);
        this.tag(OccultismTags.Items.RANDOM_SPAWN_RIDEABLE)
                .add(Items.PIG_SPAWN_EGG)
                .add(Items.CAMEL_SPAWN_EGG)
                .add(Items.DONKEY_SPAWN_EGG)
                .add(Items.HORSE_SPAWN_EGG)
                .add(Items.SKELETON_HORSE_SPAWN_EGG)
                .add(Items.ZOMBIE_HORSE_SPAWN_EGG)
                .add(Items.LLAMA_SPAWN_EGG)
                .add(Items.TRADER_LLAMA_SPAWN_EGG)
                .add(Items.MULE_SPAWN_EGG)
                .add(Items.STRIDER_SPAWN_EGG)
                .add(Items.HAPPY_GHAST_SPAWN_EGG)
                .add(Items.NAUTILUS_SPAWN_EGG)
                .add(Items.ZOMBIE_NAUTILUS_SPAWN_EGG)
                .add(Items.CAMEL_HUSK_SPAWN_EGG);
        this.tag(OccultismTags.Items.RANDOM_SPAWN_SMALL)
                .add(Items.ALLAY_SPAWN_EGG)
                .add(Items.BAT_SPAWN_EGG)
                .add(Items.BEE_SPAWN_EGG)
                .add(Items.CAT_SPAWN_EGG)
                .add(Items.FOX_SPAWN_EGG)
                .add(Items.OCELOT_SPAWN_EGG)
                .add(Items.PARROT_SPAWN_EGG)
                .add(Items.RABBIT_SPAWN_EGG);
        this.tag(OccultismTags.Items.RANDOM_SPAWN_SPECIAL)
                .add(Items.ARMADILLO_SPAWN_EGG)
                .add(Items.IRON_GOLEM_SPAWN_EGG)
                .add(Items.MOOSHROOM_SPAWN_EGG)
                .add(Items.PANDA_SPAWN_EGG)
                .add(Items.POLAR_BEAR_SPAWN_EGG)
                .add(Items.GOAT_SPAWN_EGG)
                .add(Items.SNIFFER_SPAWN_EGG)
                .add(Items.COPPER_GOLEM_SPAWN_EGG);
        this.tag(OccultismTags.Items.RANDOM_SPAWN_WATER)
                .add(Items.AXOLOTL_SPAWN_EGG)
                .add(Items.FROG_SPAWN_EGG)
                .add(Items.DOLPHIN_SPAWN_EGG)
                .add(Items.SALMON_SPAWN_EGG)
                .add(Items.COD_SPAWN_EGG)
                .add(Items.TROPICAL_FISH_SPAWN_EGG)
                .add(Items.PUFFERFISH_SPAWN_EGG)
                .add(Items.SQUID_SPAWN_EGG)
                .add(Items.SNOW_GOLEM_SPAWN_EGG)
                .add(Items.GLOW_SQUID_SPAWN_EGG)
                .add(Items.TADPOLE_SPAWN_EGG)
                .add(Items.TURTLE_SPAWN_EGG)
                .add(Items.NAUTILUS_SPAWN_EGG)
                .add(Items.ZOMBIE_NAUTILUS_SPAWN_EGG);
        this.tag(OccultismTags.Items.RANDOM_SPAWN_VILLAGER)
                .add(Items.VILLAGER_SPAWN_EGG)
                .add(Items.WANDERING_TRADER_SPAWN_EGG)
                .add(OccultismItems.SPAWN_EGG_WONDERING_TRADER.get());
    }

    private TagKey<Item> cTag(String path) {
        return ItemTags.create(this.cLoc(path));
    }

    private Identifier cLoc(String path) {
        return Identifier.fromNamespaceAndPath("c", path);
    }

    private Identifier loc(String namespaceAndPath) {
        return Identifier.tryParse(namespaceAndPath);
    }

}
