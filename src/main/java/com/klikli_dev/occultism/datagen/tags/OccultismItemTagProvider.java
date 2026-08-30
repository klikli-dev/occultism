package com.klikli_dev.occultism.datagen.tags;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import com.klikli_dev.occultism.registry.OccultismTags.Blocks;
import com.klikli_dev.occultism.registry.OccultismTags.Items.Miners;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.BlockItemTags;
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

public class OccultismItemTagProvider extends TagsProvider<Item> {

    private final CompletableFuture<TagLookup<Block>> blockTags;
    private final Map<TagKey<Block>, TagKey<Item>> tagsToCopy = new HashMap<>();

    public OccultismItemTagProvider(PackOutput p_275343_, CompletableFuture<Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_) {
        //noinspection deprecation
        super(p_275343_, Registries.ITEM, p_275729_, Occultism.MODID);
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
        this.tag(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath(CuriosResources.MOD_ID, "belt")))
                .add(this.key(OccultismItems.SATCHEL.get()))
                .add(this.key(OccultismItems.ENDER_SATCHEL.get()));
        this.tag(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath(CuriosResources.MOD_ID, "hands")))
                .add(this.key(OccultismItems.STORAGE_REMOTE.get()))
                .add(this.key(OccultismItems.TRUE_SIGHT_STAFF.get()))
                .add(this.key(OccultismItems.FAMILIAR_GLOVE.get()));
        this.tag(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath(CuriosResources.MOD_ID, "head")))
                .add(this.key(OccultismItems.OTHERWORLD_GOGGLES.get()));
        this.tag(OccultismTags.makeItemTag(Identifier.fromNamespaceAndPath(CuriosResources.MOD_ID, "ring")))
                .add(this.key(OccultismItems.FAMILIAR_RING.get()));
    }

    private void addOccultismTags(Provider provider) {
        this.copy(Blocks.OCCULTISM_CANDLES, OccultismTags.Items.OCCULTISM_CANDLES);
        this.copy(BlockTags.CANDLES, ItemTags.CANDLES);

        this.tag(OccultismTags.Items.SKULLS)
                .add(this.key(Items.SKELETON_SKULL))
                .add(this.key(Items.WITHER_SKELETON_SKULL));

        this.tag(OccultismTags.Items.SCUTESHELL)
                .add(this.key(Items.ARMADILLO_SCUTE))
                .add(this.key(Items.TURTLE_SCUTE))
                .add(this.key(Items.SHULKER_SHELL))
                .add(this.key(Items.NAUTILUS_SHELL));

        this.copy(Blocks.PENTACLE_MATERIALS, OccultismTags.Items.PENTACLE_MATERIALS);
        this.tag(OccultismTags.Items.PENTACLE_MATERIALS)
                .addOptionalTag(OccultismTags.Items.TOOLS_CHALK);

        this.tag(OccultismTags.Items.DEMONIC_PARTNER_FOOD)
                .addTag(ItemTags.MEAT);

        this.tag(OccultismTags.Items.BOOK_OF_CALLING_FOLIOT)
                .add(this.key(OccultismItems.BOOK_OF_CALLING_FOLIOT_LUMBERJACK.get()))
                .add(this.key(OccultismItems.BOOK_OF_CALLING_FOLIOT_FARMER.get()))
                .add(this.key(OccultismItems.BOOK_OF_CALLING_FOLIOT_CLEANER.get()))
                .add(this.key(OccultismItems.BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS.get()));
        this.tag(OccultismTags.Items.BOOK_OF_CALLING_DJINNI)
                .add(this.key(OccultismItems.BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE.get()));

        this.tag(OccultismTags.Items.BOOKS_OF_BINDING)
                .add(this.key(OccultismItems.BOOK_OF_BINDING_FOLIOT.get()))
                .add(this.key(OccultismItems.BOOK_OF_BINDING_DJINNI.get()))
                .add(this.key(OccultismItems.BOOK_OF_BINDING_AFRIT.get()))
                .add(this.key(OccultismItems.BOOK_OF_BINDING_MARID.get()));

        this.tag(OccultismTags.Items.BOOKS_FOR_EMPTY)
                .add(this.key(Items.WRITABLE_BOOK))
                .add(this.key(Items.WRITTEN_BOOK))
                .add(this.key(Items.ENCHANTED_BOOK))
                .add(this.key(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .add(this.key(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .add(this.key(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .add(this.key(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .add(this.key(OccultismItems.BOOK_OF_BINDING_FOLIOT.get()))
                .add(this.key(OccultismItems.BOOK_OF_BINDING_DJINNI.get()))
                .add(this.key(OccultismItems.BOOK_OF_BINDING_AFRIT.get()))
                .add(this.key(OccultismItems.BOOK_OF_BINDING_MARID.get()))
                .add(this.key(OccultismItems.BOOK_OF_CALLING_FOLIOT_LUMBERJACK.get()))
                .add(this.key(OccultismItems.BOOK_OF_CALLING_FOLIOT_FARMER.get()))
                .add(this.key(OccultismItems.BOOK_OF_CALLING_FOLIOT_CLEANER.get()))
                .add(this.key(OccultismItems.BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS.get()))
                .add(this.key(OccultismItems.BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE.get()));

        this.tag(Miners.BASIC).add(
                this.key(OccultismItems.MINER_DEBUG_UNSPECIALIZED.get()),
                this.key(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get()),
                this.key(OccultismItems.MINER_DJINNI_ORES.get()),
                this.key(OccultismItems.MINER_AFRIT_DEEPS.get()),
                this.key(OccultismItems.MINER_MARID_MASTER.get()),
                this.key(OccultismItems.MINER_ANCIENT_ELDRITCH.get()));
        this.tag(Miners.IRON).add(
                this.key(OccultismItems.MINER_DEBUG_UNSPECIALIZED.get()),
                this.key(OccultismItems.MINER_DJINNI_ORES.get()),
                this.key(OccultismItems.MINER_AFRIT_DEEPS.get()),
                this.key(OccultismItems.MINER_MARID_MASTER.get()),
                this.key(OccultismItems.MINER_ANCIENT_ELDRITCH.get()));
        this.tag(Miners.DIAMOND).add(
                this.key(OccultismItems.MINER_AFRIT_DEEPS.get()),
                this.key(OccultismItems.MINER_MARID_MASTER.get()),
                this.key(OccultismItems.MINER_ANCIENT_ELDRITCH.get()));
        this.tag(Miners.NETHERITE).add(
                this.key(OccultismItems.MINER_MARID_MASTER.get()),
                this.key(OccultismItems.MINER_ANCIENT_ELDRITCH.get()));
        this.tag(Miners.ELDRITCH).add(
                this.key(OccultismItems.MINER_ANCIENT_ELDRITCH.get()));

        this.tag(Miners.MINERS)
                .addTag(Miners.BASIC)
                .addTag(Miners.IRON)
                .addTag(Miners.DIAMOND)
                .addTag(Miners.NETHERITE)
                .addTag(Miners.ELDRITCH);

        this.tag(OccultismTags.Items.TOOLS_CHALK)
                .add(this.key(OccultismItems.CHALK_YELLOW.get()))
                .add(this.key(OccultismItems.CHALK_WHITE.get()))
                .add(this.key(OccultismItems.CHALK_RED.get()))
                .add(this.key(OccultismItems.CHALK_PURPLE.get()))
                .add(this.key(OccultismItems.CHALK_LIGHT_GRAY.get()))
                .add(this.key(OccultismItems.CHALK_GRAY.get()))
                .add(this.key(OccultismItems.CHALK_BLACK.get()))
                .add(this.key(OccultismItems.CHALK_BROWN.get()))
                .add(this.key(OccultismItems.CHALK_ORANGE.get()))
                .add(this.key(OccultismItems.CHALK_LIME.get()))
                .add(this.key(OccultismItems.CHALK_GREEN.get()))
                .add(this.key(OccultismItems.CHALK_CYAN.get()))
                .add(this.key(OccultismItems.CHALK_LIGHT_BLUE.get()))
                .add(this.key(OccultismItems.CHALK_BLUE.get()))
                .add(this.key(OccultismItems.CHALK_MAGENTA.get()))
                .add(this.key(OccultismItems.CHALK_PINK.get()))
                .add(this.key(OccultismItems.CHALK_RAINBOW.get()))
                .add(this.key(OccultismItems.CHALK_VOID.get()));

        this.tag(OccultismTags.Items.TOOLS_BRUSH)
                .add(this.key(OccultismItems.BRUSH.get()))
                .add(this.key(OccultismItems.CHALK_RAINBOW.get()))
                .add(this.key(OccultismItems.CHALK_VOID.get()));

        this.tag(OccultismTags.Items.START_SPIRIT_FIRE)
                .add(this.key(OccultismItems.DATURA.get()))
                .add(this.key(OccultismItems.PITAYA.get()));
        this.copy(Blocks.OTHERWORLD_SAPLINGS, OccultismTags.Items.OTHERWORLD_SAPLINGS);
        this.copy(Blocks.OTHERWORLD_SAPLINGS_NATURAL, OccultismTags.Items.OTHERWORLD_SAPLINGS_NATURAL);
        this.tag(OccultismTags.Items.TOOLS_KNIFE)
                .add(this.key(OccultismItems.BUTCHER_KNIFE.get()))
                .add(this.key(OccultismItems.IESNIUM_BUTCHER_KNIFE.get()));
        this.tag(OccultismTags.Items.TOOLS_KNIFE_IESNIUM)
                .add(this.key(OccultismItems.IESNIUM_BUTCHER_KNIFE.get()));
        this.tag(Tags.Items.TOOLS).addOptionalTag(OccultismTags.Items.TOOLS_KNIFE); //Don't place chalks
        this.tag(OccultismTags.Items.ELYTRA).add(this.key(Items.ELYTRA));
        this.tag(OccultismTags.Items.OTHERWORLD_GOGGLES).add(this.key(OccultismItems.OTHERWORLD_GOGGLES.get()));
        this.tag(OccultismTags.Items.OTHERSTONE).add(this.key(OccultismBlocks.OTHERSTONE.asItem()));
        this.tag(OccultismTags.Items.OTHERCOBBLESTONE).add(this.key(OccultismBlocks.OTHERCOBBLESTONE.asItem()));
    }

    private void addMinecraftTags(Provider provider) {
        this.tag(ItemTags.BOOKSHELF_BOOKS)
                .add(this.key(OccultismItems.DICTIONARY_OF_SPIRITS.get()))
                .add(this.key(OccultismItems.BOOK_OF_BINDING_AFRIT.get()))
                .add(this.key(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()))
                .add(this.key(OccultismItems.BOOK_OF_BINDING_DJINNI.get()))
                .add(this.key(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()))
                .add(this.key(OccultismItems.BOOK_OF_BINDING_FOLIOT.get()))
                .add(this.key(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()))
                .add(this.key(OccultismItems.BOOK_OF_BINDING_MARID.get()))
                .add(this.key(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()))
                .add(this.key(OccultismItems.BOOK_OF_CALLING_DJINNI_MANAGE_MACHINE.get()))
                .add(this.key(OccultismItems.BOOK_OF_CALLING_FOLIOT_CLEANER.get()))
                .add(this.key(OccultismItems.BOOK_OF_CALLING_FOLIOT_LUMBERJACK.get()))
                .add(this.key(OccultismItems.BOOK_OF_CALLING_FOLIOT_FARMER.get()))
                .add(this.key(OccultismItems.BOOK_OF_CALLING_FOLIOT_TRANSPORT_ITEMS.get()))
                .add(this.key(OccultismItems.BOOK_OF_BINDING_EMPTY.get()))
                .add(this.key(OccultismItems.TABOO_BOOK.get()));
        this.tag(ItemTags.LECTERN_BOOKS).add(this.key(OccultismItems.DICTIONARY_OF_SPIRITS.get()));

        this.tag(ItemTags.MINING_ENCHANTABLE).addTag(Miners.MINERS);
        this.tag(ItemTags.MINING_LOOT_ENCHANTABLE).addTag(Miners.MINERS);
        this.tag(ItemTags.DURABILITY_ENCHANTABLE)
                .addTag(Miners.MINERS)
                .addTag(OccultismTags.Items.TOOLS_CHALK)
                .addTag(OccultismTags.Items.TOOLS_KNIFE)
                .add(this.key(OccultismItems.MINING_DIMENSION_CORE_PIECE.asItem()))
                .add(this.key(OccultismItems.GRAY_PASTE.asItem()))
                .add(this.key(OccultismItems.NATURE_PASTE.asItem()));
        this.tag(ItemTags.MELEE_WEAPON_ENCHANTABLE).addTag(OccultismTags.Items.TOOLS_KNIFE);
        this.tag(ItemTags.SHARP_WEAPON_ENCHANTABLE).addTag(OccultismTags.Items.TOOLS_KNIFE);
        this.tag(ItemTags.WEAPON_ENCHANTABLE).addTag(OccultismTags.Items.TOOLS_KNIFE);

        this.tag(ItemTags.LOGS_THAT_BURN)
                .add(this.key(OccultismBlocks.OTHERWORLD_LOG.asItem()))
                .add(this.key(OccultismBlocks.OTHERWORLD_LOG_NATURAL.asItem()))
                .add(this.key(OccultismBlocks.STRIPPED_OTHERWORLD_LOG_NATURAL.asItem()))
                .add(this.key(OccultismBlocks.OTHERWORLD_WOOD.asItem()))
                .add(this.key(OccultismBlocks.STRIPPED_OTHERWORLD_LOG.asItem()))
                .add(this.key(OccultismBlocks.STRIPPED_OTHERWORLD_WOOD.asItem()));

        this.tag(ItemTags.PLANKS).add(this.key(OccultismBlocks.OTHERPLANKS.asItem()));
        this.tag(ItemTags.WOODEN_STAIRS).add(this.key(OccultismBlocks.OTHERPLANKS_STAIRS.asItem()));
        this.tag(ItemTags.WOODEN_SLABS).add(this.key(OccultismBlocks.OTHERPLANKS_SLAB.asItem()));
        this.tag(ItemTags.WOODEN_FENCES).add(this.key(OccultismBlocks.OTHERPLANKS_FENCE.asItem()));
        this.tag(ItemTags.FENCE_GATES).add(this.key(OccultismBlocks.OTHERPLANKS_FENCE_GATE.asItem()));
        this.tag(ItemTags.WOODEN_DOORS).add(this.key(OccultismBlocks.OTHERPLANKS_DOOR.asItem()));
        this.tag(ItemTags.WOODEN_TRAPDOORS).add(this.key(OccultismBlocks.OTHERPLANKS_TRAPDOOR.asItem()));
        this.tag(ItemTags.WOODEN_PRESSURE_PLATES).add(this.key(OccultismBlocks.OTHERPLANKS_PRESSURE_PLATE.asItem()));
        this.tag(ItemTags.WOODEN_BUTTONS).add(this.key(OccultismBlocks.OTHERPLANKS_BUTTON.asItem()));
        this.tag(ItemTags.SIGNS).add(this.key(OccultismItems.OTHERPLANKS_SIGN.get()));
        this.tag(ItemTags.HANGING_SIGNS).add(this.key(OccultismItems.OTHERPLANKS_HANGING_SIGN.get()));
        this.tag(ItemTags.WOODEN_SHELVES).add(this.key(OccultismBlocks.OTHERPLANKS_SHELF.asItem()));
        this.tag(ItemTags.BOATS).add(this.key(OccultismItems.OTHERPLANKS_BOAT.asItem())).add(this.key(OccultismItems.OTHERPLANKS_BOAT_CHEST.asItem()));
        this.tag(ItemTags.CHEST_BOATS).add(this.key(OccultismItems.OTHERPLANKS_BOAT_CHEST.asItem()));
        /* OTHERSTONE CAN'T HAVE STONE TAG BECAUSE SPIRIT TRADER WILL DUPE
         *this.tag(Tags.Items.STONES)
         *        .add(this.key(OccultismBlocks.OTHERSTONE.asItem()))
         *        .add(this.key(OccultismBlocks.OTHERSTONE_NATURAL.asItem()));
         */
        this.tag(ItemTags.WALLS)
                .add(this.key(OccultismBlocks.OTHERSTONE_WALL.asItem()))
                .add(this.key(OccultismBlocks.OTHERCOBBLESTONE_WALL.asItem()))
                .add(this.key(OccultismBlocks.POLISHED_OTHERSTONE_WALL.asItem()))
                .add(this.key(OccultismBlocks.OTHERSTONE_BRICKS_WALL.asItem()));
        this.tag(BlockItemTags.STONE_BUTTONS.item()).add(this.key(OccultismBlocks.OTHERSTONE_BUTTON.asItem()));

        this.tag(ItemTags.CLUSTER_MAX_HARVESTABLES)
                .add(this.key(OccultismItems.SILVER_PICKAXE.get()))
                .add(this.key(OccultismItems.INFUSED_PICKAXE.get()))
                .add(this.key(OccultismItems.IESNIUM_PICKAXE.get()));

        this.tag(ItemTags.PICKAXES)
                .add(this.key(OccultismItems.SILVER_PICKAXE.get()))
                .add(this.key(OccultismItems.INFUSED_PICKAXE.get()))
                .add(this.key(OccultismItems.IESNIUM_PICKAXE.get()));

        this.tag(ItemTags.SHOVELS)
                .add(this.key(OccultismItems.SILVER_SHOVEL.get()));

        this.tag(ItemTags.AXES)
                .add(this.key(OccultismItems.SILVER_AXE.get()));

        this.tag(ItemTags.HOES)
                .add(this.key(OccultismItems.SILVER_HOE.get()));

        this.tag(ItemTags.SPEARS)
                .add(this.key(OccultismItems.SILVER_SPEAR.get()));

        this.tag(ItemTags.SWORDS)
                .add(this.key(OccultismItems.SILVER_SWORD.get()));

        this.tag(ItemTags.HEAD_ARMOR)
                .add(this.key(OccultismItems.SILVER_HELMET.get()));

        this.tag(ItemTags.CHEST_ARMOR)
                .add(this.key(OccultismItems.SILVER_CHESTPLATE.get()));

        this.tag(ItemTags.LEG_ARMOR)
                .add(this.key(OccultismItems.SILVER_LEGGINGS.get()));

        this.tag(ItemTags.FOOT_ARMOR)
                .add(this.key(OccultismItems.SILVER_BOOTS.get()));

        this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
        this.copy(BlockTags.LOGS, ItemTags.LOGS);
        this.copy(BlockTags.PIGLIN_REPELLENTS, ItemTags.PIGLIN_REPELLENTS);
        this.copy(BlockItemTags.SAPLINGS.block(), BlockItemTags.SAPLINGS.item());
        this.tag(this.cTag("tools/knife")).add(this.key(OccultismItems.BUTCHER_KNIFE.get())).add(this.key(OccultismItems.IESNIUM_BUTCHER_KNIFE.get()));

        this.tag(BlockItemTags.SMALL_FLOWERS.item()).add(this.key(OccultismBlocks.OTHERFLOWER.asItem())).add(this.key(OccultismBlocks.OTHERFLOWER_NATURAL.asItem()));
        this.tag(ItemTags.COMPASSES).add(this.key(OccultismItems.VITALITY_COMPASS.asItem()));

        this.tag(ItemTags.TRIM_MATERIALS)
                .add(this.key(OccultismItems.SPIRIT_ATTUNED_GEM.get()))
                .add(this.key(OccultismItems.SILVER_INGOT.get()))
                .add(this.key(OccultismItems.IESNIUM_INGOT.get()));
    }

    private void addCommonTags(Provider provider) {
        this.tag(Tags.Items.MINING_TOOL_TOOLS)
                .add(this.key(OccultismItems.SILVER_PICKAXE.get()))
                .add(this.key(OccultismItems.INFUSED_PICKAXE.get()))
                .add(this.key(OccultismItems.IESNIUM_PICKAXE.get()));

        this.tag(Tags.Items.MELEE_WEAPON_TOOLS)
                .add(this.key(OccultismItems.BUTCHER_KNIFE.get()))
                .add(this.key(OccultismItems.IESNIUM_BUTCHER_KNIFE.get()))
                .add(this.key(OccultismItems.SILVER_AXE.get()))
                .add(this.key(OccultismItems.SILVER_SPEAR.get()))
                .add(this.key(OccultismItems.SILVER_SWORD.get()));

        this.tag(Tags.Items.ARMORS_HORSE)
                .add(this.key(OccultismItems.SILVER_HORSE_ARMOR.get()));

        this.tag(Tags.Items.ARMORS_NAUTILUS)
                .add(this.key(OccultismItems.SILVER_NAUTILUS_ARMOR.get()));

        this.copy(BlockTags.STAIRS, BlockItemTags.STAIRS.item());
        this.copy(BlockTags.SLABS, BlockItemTags.SLABS.item());
        this.copy(BlockTags.BARS, BlockItemTags.BARS.item());
        this.copy(Tags.Blocks.BARS, Tags.Items.BARS);
        this.copy(BlockTags.CHAINS, BlockItemTags.CHAINS.item());
        this.copy(Tags.Blocks.CHAINS, Tags.Items.CHAINS);
        this.copy(BlockTags.DOORS, BlockItemTags.DOORS.item());
        this.copy(BlockTags.TRAPDOORS, BlockItemTags.TRAPDOORS.item());

        // Ore Blocks
        this.copy(Blocks.IESNIUM_ORE, OccultismTags.Items.IESNIUM_ORE);
        this.copy(Blocks.SILVER_ORE, OccultismTags.Items.SILVER_ORE);
        //noinspection unchecked
        this.tag(Tags.Items.ORES).addTag(OccultismTags.Items.IESNIUM_ORE).addTag(OccultismTags.Items.SILVER_ORE);

        this.copy(Tags.Blocks.ORES_IN_GROUND_STONE, Tags.Items.ORES_IN_GROUND_STONE);
        this.copy(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE, Tags.Items.ORES_IN_GROUND_DEEPSLATE);
        this.copy(Tags.Blocks.ORES_IN_GROUND_NETHERRACK, Tags.Items.ORES_IN_GROUND_NETHERRACK);

        // Raw Materials
        this.tag(OccultismTags.Items.RAW_SILVER).add(this.key(OccultismItems.RAW_SILVER.get()));
        this.tag(OccultismTags.Items.RAW_IESNIUM).add(this.key(OccultismItems.RAW_IESNIUM.get()));
        //noinspection unchecked
        this.tag(Tags.Items.RAW_MATERIALS).addTag(OccultismTags.Items.RAW_IESNIUM).addTag(OccultismTags.Items.RAW_SILVER);

        // Dusts
        this.addDusts(provider);

        // Possessed mobs loots
        this.addMobLoot(provider);

        //Random spawn egg
        this.addRandomEggs(provider);

        // Ingots
        this.tag(OccultismTags.Items.IESNIUM_INGOT).add(this.key(OccultismItems.IESNIUM_INGOT.get()));
        this.tag(OccultismTags.Items.SILVER_INGOT).add(this.key(OccultismItems.SILVER_INGOT.get()));
        //noinspection unchecked
        this.tag(Tags.Items.INGOTS).addTag(OccultismTags.Items.IESNIUM_INGOT).addTag(OccultismTags.Items.SILVER_INGOT);
        this.tag(ItemTags.BEACON_PAYMENT_ITEMS).add(this.key(OccultismItems.SILVER_INGOT.get())).add(this.key(OccultismItems.IESNIUM_INGOT.get()));

        // Nuggets
        this.tag(OccultismTags.Items.IESNIUM_NUGGET).add(this.key(OccultismItems.IESNIUM_NUGGET.get()));
        this.tag(OccultismTags.Items.SILVER_NUGGET).add(this.key(OccultismItems.SILVER_NUGGET.get()));
        //noinspection unchecked
        this.tag(Tags.Items.NUGGETS).addTag(OccultismTags.Items.IESNIUM_NUGGET).addTag(OccultismTags.Items.SILVER_NUGGET);

        // Seeds
        this.tag(OccultismTags.Items.DATURA_SEEDS).add(this.key(OccultismItems.DATURA_SEEDS.get()));
        //noinspection unchecked
        this.tag(Tags.Items.SEEDS).addTag(OccultismTags.Items.DATURA_SEEDS);
        // Crops
        this.tag(OccultismTags.Items.DATURA_CROP).add(this.key(OccultismItems.DATURA.get()));
        //noinspection unchecked
        this.tag(Tags.Items.CROPS).addTag(OccultismTags.Items.DATURA_CROP);
        //Both datura
        // Crops
        this.tag(OccultismTags.Items.DATURA).addTag(OccultismTags.Items.DATURA_SEEDS).addTag(OccultismTags.Items.DATURA_CROP);

        //Foods
        this.tag(Tags.Items.FOODS)
                .add(this.key(OccultismItems.DATURA.get()))
                .add(this.key(OccultismItems.DEMONS_DREAM_ESSENCE.get()))
                .add(this.key(OccultismItems.OTHERWORLD_ESSENCE.get()))
                .add(this.key(OccultismItems.BEAVER_NUGGET.get()))
                .add(this.key(OccultismItems.CURSED_HONEY.get()))
                .add(this.key(OccultismItems.SWEET_HONEY_HEART.get()))
                .add(this.key(OccultismItems.DEMONIC_MEAT.get()))
                .add(this.key(OccultismItems.PITAYA.get()))
                .add(this.key(OccultismItems.PITAYA_GOLDEN.get()))
                .add(this.key(OccultismItems.PITAYA_ENCHANTED.get()));
        this.tag(ItemTags.MEAT)
                .add(this.key(OccultismItems.DEMONIC_MEAT.get()));
        this.tag(Tags.Items.FOODS_FRUIT)
                .add(this.key(OccultismItems.PITAYA.get()))
                .add(this.key(OccultismItems.PITAYA_GOLDEN.get()))
                .add(this.key(OccultismItems.PITAYA_ENCHANTED.get()));
        this.tag(Tags.Items.ANIMAL_FOODS)
                .add(this.key(OccultismItems.PITAYA.get()))
                .add(this.key(OccultismItems.PITAYA_GOLDEN.get()))
                .add(this.key(OccultismItems.PITAYA_ENCHANTED.get()));
        this.tag(ItemTags.HORSE_FOOD)
                .add(this.key(OccultismItems.PITAYA.get()))
                .add(this.key(OccultismItems.PITAYA_GOLDEN.get()))
                .add(this.key(OccultismItems.PITAYA_ENCHANTED.get()));
        this.tag(ItemTags.ZOMBIE_HORSE_FOOD)
                .add(this.key(OccultismItems.PITAYA.get()))
                .add(this.key(OccultismItems.PITAYA_GOLDEN.get()))
                .add(this.key(OccultismItems.PITAYA_ENCHANTED.get()));
        this.tag(ItemTags.HORSE_TEMPT_ITEMS)
                .add(this.key(OccultismItems.PITAYA_GOLDEN.get()))
                .add(this.key(OccultismItems.PITAYA_ENCHANTED.get()));
        this.tag(Tags.Items.FOODS_GOLDEN)
                .add(this.key(OccultismItems.PITAYA_GOLDEN.get()))
                .add(this.key(OccultismItems.PITAYA_ENCHANTED.get()));
        this.tag(ItemTags.PIGLIN_LOVED)
                .add(this.key(OccultismItems.PITAYA_GOLDEN.get()))
                .add(this.key(OccultismItems.PITAYA_ENCHANTED.get()));

        // Storage Blocks
        this.copy(Blocks.STORAGE_BLOCKS_IESNIUM, OccultismTags.Items.STORAGE_BLOCK_IESNIUM);
        this.copy(Blocks.STORAGE_BLOCKS_SILVER, OccultismTags.Items.STORAGE_BLOCK_SILVER);
        this.copy(Blocks.STORAGE_BLOCKS_RAW_IESNIUM, OccultismTags.Items.STORAGE_BLOCK_RAW_IESNIUM);
        this.copy(Blocks.STORAGE_BLOCKS_RAW_SILVER, OccultismTags.Items.STORAGE_BLOCK_RAW_SILVER);
        this.copy(Blocks.STORAGE_BLOCKS_SPIRIT_ATTUNED, OccultismTags.Items.STORAGE_BLOCK_SPIRIT_ATTUNED);
        //noinspection unchecked
        this.tag(Tags.Items.STORAGE_BLOCKS).addTag(OccultismTags.Items.STORAGE_BLOCK_IESNIUM).addTag(OccultismTags.Items.STORAGE_BLOCK_SILVER).addTag(OccultismTags.Items.STORAGE_BLOCK_RAW_IESNIUM).addTag(OccultismTags.Items.STORAGE_BLOCK_RAW_SILVER);
        this.copy(Blocks.MUSHROOM_BLOCKS, OccultismTags.Items.MUSHROOM_BLOCKS);
        this.copy(Blocks.ENCHANTING_TABLES, OccultismTags.Items.ENCHANTING_TABLES);
        this.copy(Blocks.IRON_BARS, OccultismTags.Items.IRON_BARS);
        this.tag(OccultismTags.Items.TUBE_CORALS).add(this.key(Items.TUBE_CORAL)).add(this.key(Items.TUBE_CORAL_FAN));

        // Books
        this.tag(OccultismTags.Items.BOOKS).add(this.key(OccultismItems.DICTIONARY_OF_SPIRITS.get()), this.key(Items.BOOK));

        // Fruits
        this.tag(OccultismTags.Items.FRUITS).add(this.key(Items.APPLE));

        // Gems
        this.tag(Tags.Items.GEMS).add(this.key(OccultismItems.SPIRIT_ATTUNED_GEM.get()));

        // Magma
        this.tag(OccultismTags.Items.MAGMA).add(this.key(Items.MAGMA_BLOCK));

        // Manuals
        this.tag(OccultismTags.Items.MANUALS).add(this.key(OccultismItems.DICTIONARY_OF_SPIRITS.get()));

        // Tallow
        this.tag(OccultismTags.Items.TALLOW).add(this.key(OccultismItems.TALLOW.get()));

        // Wood
        this.copy(Blocks.OTHERWORLD_LOGS, OccultismTags.Items.OTHERWORLD_LOGS);

        // Clay
        this.tag(OccultismTags.Items.CLAY).add(this.key(Items.CLAY_BALL));
    }

    private void addDusts(Provider provider) {
        this.tag(OccultismTags.Items.BLAZE_DUST).add(this.key(Items.BLAZE_POWDER));
        this.tag(OccultismTags.Items.COPPER_DUST).add(this.key(OccultismItems.COPPER_DUST.get()));
        this.tag(OccultismTags.Items.END_STONE_DUST).add(this.key(OccultismItems.CRUSHED_END_STONE.get()));
        this.tag(OccultismTags.Items.GOLD_DUST).add(this.key(OccultismItems.GOLD_DUST.get()));
        this.tag(OccultismTags.Items.IRON_DUST).add(this.key(OccultismItems.IRON_DUST.get()));
        this.tag(OccultismTags.Items.IESNIUM_DUST).add(this.key(OccultismItems.IESNIUM_DUST.get()));
        this.tag(OccultismTags.Items.SILVER_DUST).add(this.key(OccultismItems.SILVER_DUST.get()));
        this.tag(OccultismTags.Items.OBSIDIAN_DUST).add(this.key(OccultismItems.OBSIDIAN_DUST.get()));
        this.tag(OccultismTags.Items.AMETHYST_DUST).add(this.key(OccultismItems.AMETHYST_DUST.get()));
        this.tag(OccultismTags.Items.BLACKSTONE_DUST).add(this.key(OccultismItems.CRUSHED_BLACKSTONE.get()));
        this.tag(OccultismTags.Items.BLUE_ICE_DUST).add(this.key(OccultismItems.CRUSHED_BLUE_ICE.get()));
        this.tag(OccultismTags.Items.CALCITE_DUST).add(this.key(OccultismItems.CRUSHED_CALCITE.get()));
        this.tag(OccultismTags.Items.ICE_DUST).add(this.key(OccultismItems.CRUSHED_ICE.get()));
        this.tag(OccultismTags.Items.PACKED_ICE_DUST).add(this.key(OccultismItems.CRUSHED_PACKED_ICE.get()));
        this.tag(OccultismTags.Items.DRAGONYST_DUST).add(this.key(OccultismItems.DRAGONYST_DUST.get()));
        this.tag(OccultismTags.Items.ECHO_DUST).add(this.key(OccultismItems.ECHO_DUST.get()));
        this.tag(OccultismTags.Items.EMERALD_DUST).add(this.key(OccultismItems.EMERALD_DUST.get()));
        this.tag(OccultismTags.Items.LAPIS_DUST).add(this.key(OccultismItems.LAPIS_DUST.get()));
        this.tag(OccultismTags.Items.NETHERITE_DUST).add(this.key(OccultismItems.NETHERITE_DUST.get()));
        this.tag(OccultismTags.Items.NETHERITE_SCRAP_DUST).add(this.key(OccultismItems.NETHERITE_SCRAP_DUST.get()));
        this.tag(OccultismTags.Items.RESEARCH_DUST).add(this.key(OccultismItems.RESEARCH_FRAGMENT_DUST.get()));
        this.tag(OccultismTags.Items.WITHERITE_DUST).add(this.key(OccultismItems.WITHERITE_DUST.get()));
        this.tag(OccultismTags.Items.OTHERSTONE_DUST).add(this.key(OccultismItems.BURNT_OTHERSTONE.get()));
        this.tag(OccultismTags.Items.OTHERROCK_DUST).add(this.key(OccultismItems.BURNT_OTHERROCK.get()));
        this.tag(OccultismTags.Items.OTHERWORLD_WOOD_DUST).add(this.key(OccultismItems.OTHERWORLD_ASHES.get()));
        //noinspection unchecked
        this.tag(Tags.Items.DUSTS).addTag(OccultismTags.Items.COPPER_DUST).addTag(OccultismTags.Items.END_STONE_DUST).addTag(OccultismTags.Items.GOLD_DUST).addTag(OccultismTags.Items.IRON_DUST).addTag(OccultismTags.Items.IESNIUM_DUST).addTag(OccultismTags.Items.SILVER_DUST).addTag(OccultismTags.Items.OBSIDIAN_DUST).addTag(OccultismTags.Items.AMETHYST_DUST).addTag(OccultismTags.Items.BLACKSTONE_DUST).addTag(OccultismTags.Items.BLUE_ICE_DUST).addTag(OccultismTags.Items.CALCITE_DUST).addTag(OccultismTags.Items.ICE_DUST).addTag(OccultismTags.Items.PACKED_ICE_DUST).addTag(OccultismTags.Items.DRAGONYST_DUST).addTag(OccultismTags.Items.ECHO_DUST).addTag(OccultismTags.Items.EMERALD_DUST).addTag(OccultismTags.Items.LAPIS_DUST).addTag(OccultismTags.Items.NETHERITE_DUST).addTag(OccultismTags.Items.NETHERITE_SCRAP_DUST).addTag(OccultismTags.Items.RESEARCH_DUST).addTag(OccultismTags.Items.WITHERITE_DUST).addTag(OccultismTags.Items.OTHERSTONE_DUST).addTag(OccultismTags.Items.OTHERROCK_DUST).addTag(OccultismTags.Items.OTHERWORLD_WOOD_DUST);
        this.tag(OccultismTags.Items.CHALK_BASE_DUST)
                .addTag(OccultismTags.Items.OTHERSTONE_DUST)
                .addTag(OccultismTags.Items.OTHERROCK_DUST);
    }

    private void addMobLoot(Provider provider) {
        this.tag(OccultismTags.Items.DROPS_POSSESSED_BLAZE)
                .add(this.key(Items.BLAZE_ROD))
                .add(this.key(Items.BLAZE_POWDER))
                .add(this.key(Items.NETHER_WART))
                .add(this.key(Items.CRIMSON_FUNGUS))
                .add(this.key(Items.WARPED_FUNGUS))
                .add(this.key(Items.RED_MUSHROOM))
                .add(this.key(Items.BROWN_MUSHROOM))
                .add(this.key(Items.CRIMSON_ROOTS))
                .add(this.key(Items.WARPED_ROOTS))
                .add(this.key(Items.WEEPING_VINES))
                .add(this.key(Items.TWISTING_VINES))
                .add(this.key(Items.NETHERRACK))
                .add(this.key(Items.NETHER_QUARTZ_ORE))
                .add(this.key(Items.CRIMSON_NYLIUM))
                .add(this.key(Items.WARPED_NYLIUM))
                .add(this.key(Items.NETHER_WART_BLOCK))
                .add(this.key(Items.WARPED_WART_BLOCK))
                .add(this.key(Items.SOUL_SAND))
                .add(this.key(Items.SOUL_SOIL))
                .add(this.key(Items.BASALT))
                .add(this.key(Items.BLACKSTONE))
                .add(this.key(Items.GRAVEL))
                .add(this.key(Items.BONE_BLOCK))
                .add(this.key(Items.GILDED_BLACKSTONE))
                .add(this.key(Items.GLOWSTONE_DUST))
                .add(this.key(Items.MAGMA_BLOCK))
                .add(this.key(Items.GLOWSTONE))
                .add(this.key(Items.SHROOMLIGHT))
                .add(this.key(Items.OBSIDIAN))
                .add(this.key(Items.CRYING_OBSIDIAN))
                .add(this.key(Items.ANCIENT_DEBRIS));

        this.tag(OccultismTags.Items.DROPS_POSSESSED_BREEZE)
                .add(this.key(Items.OMINOUS_TRIAL_KEY))
                .add(this.key(Items.BREEZE_ROD))
                .add(this.key(Items.BOLT_ARMOR_TRIM_SMITHING_TEMPLATE))
                .add(this.key(Items.GUSTER_BANNER_PATTERN))
                .add(this.key(Items.MUSIC_DISC_PRECIPICE));

        this.tag(OccultismTags.Items.DROPS_POSSESSED_ELDER_GUARDIAN)
                .add(this.key(Items.HEART_OF_THE_SEA))
                .add(this.key(Items.NAUTILUS_SHELL))
                .add(this.key(Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE))
                .add(this.key(Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE))
                .add(this.key(Items.WET_SPONGE))
                .add(this.key(Items.TROPICAL_FISH))
                .add(this.key(Items.COD))
                .add(this.key(Items.SALMON))
                .add(this.key(Items.PUFFERFISH))
                .add(this.key(Items.COOKED_COD))
                .add(this.key(Items.COOKED_SALMON))
                .add(this.key(Items.PRISMARINE_SHARD))
                .add(this.key(Items.PRISMARINE_CRYSTALS));

        this.tag(OccultismTags.Items.DROPS_POSSESSED_ENDERMAN)
                .add(this.key(Items.ENDER_PEARL))
                .add(this.key(Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE));

        this.tag(OccultismTags.Items.DROPS_POSSESSED_ENDERMITE)
                .add(this.key(Items.END_STONE))
                .add(this.key(Items.END_STONE_BRICKS))
                .add(this.key(Items.FERMENTED_SPIDER_EYE))
                .add(this.key(Items.SPIDER_EYE))
                .add(this.key(Items.ENDER_EYE));

        this.tag(OccultismTags.Items.DROPS_POSSESSED_EVOKER)
                .add(this.key(Items.TOTEM_OF_UNDYING))
                .add(this.key(Items.OMINOUS_BOTTLE))
                .add(this.key(Items.VEX_ARMOR_TRIM_SMITHING_TEMPLATE))
                .add(this.key(Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE));

        this.tag(OccultismTags.Items.DROPS_POSSESSED_GHAST)
                .add(this.key(Items.GHAST_TEAR))
                .add(this.key(Items.GUNPOWDER))
                .add(this.key(Items.MUSIC_DISC_TEARS))
                .add(this.key(Items.DRIED_GHAST));

        this.tag(OccultismTags.Items.DROPS_POSSESSED_HOGLIN)
                .add(this.key(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE))
                .add(this.key(Items.NETHERITE_SCRAP))
                .add(this.key(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE))
                .add(this.key(Items.PIGLIN_BANNER_PATTERN))
                .add(this.key(Items.NETHER_BRICK));

        this.tag(OccultismTags.Items.DROPS_POSSESSED_PHANTOM)
                .add(this.key(Items.PHANTOM_MEMBRANE))
                .add(this.key(Items.WIND_CHARGE));

        this.tag(OccultismTags.Items.DROPS_POSSESSED_SHULKER)
                .add(this.key(Items.SHULKER_SHELL))
                .add(this.key(Items.CHORUS_FLOWER))
                .add(this.key(Items.CHORUS_FRUIT))
                .add(this.key(Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE));

        this.tag(OccultismTags.Items.DROPS_POSSESSED_SKELETON)
                .add(this.key(Items.SKELETON_SKULL))
                .add(this.key(Items.BONE))
                .add(this.key(Items.ARROW));

        this.tag(OccultismTags.Items.DROPS_POSSESSED_STRONG_BREEZE)
                .add(this.key(Items.HEAVY_CORE))
                .add(this.key(Items.FLOW_BANNER_PATTERN))
                .add(this.key(Items.FLOW_POTTERY_SHERD))
                .add(this.key(Items.FLOW_ARMOR_TRIM_SMITHING_TEMPLATE))
                .add(this.key(Items.MUSIC_DISC_CREATOR));

        this.tag(OccultismTags.Items.DROPS_POSSESSED_WARDEN)
                .add(this.key(Items.ECHO_SHARD))
                .add(this.key(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE))
                .add(this.key(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE))
                .add(this.key(Items.MUSIC_DISC_OTHERSIDE))
                .add(this.key(Items.DISC_FRAGMENT_5))
                .add(this.key(Items.SCULK))
                .add(this.key(Items.SCULK_VEIN))
                .add(this.key(Items.SCULK_CATALYST))
                .add(this.key(Items.SCULK_SHRIEKER))
                .add(this.key(Items.SCULK_SENSOR));

        this.tag(OccultismTags.Items.DROPS_POSSESSED_WEAK_BREEZE)
                .add(this.key(Items.TRIAL_KEY))
                .add(this.key(Items.WIND_CHARGE))
                .add(this.key(Items.OMINOUS_BOTTLE))
                .add(this.key(Items.MUSIC_DISC_CREATOR_MUSIC_BOX))
                .add(this.key(Items.SCRAPE_POTTERY_SHERD))
                .add(this.key(Items.GUSTER_POTTERY_SHERD));

        this.tag(OccultismTags.Items.DROPS_POSSESSED_WEAK_SHULKER)
                .add(this.key(Items.CHORUS_FRUIT))
                .add(this.key(Items.SHULKER_SHELL));

        this.tag(OccultismTags.Items.DROPS_POSSESSED_WITCH)
                .add(this.key(Items.EXPERIENCE_BOTTLE))
                .add(this.key(Items.OMINOUS_BOTTLE))
                .add(this.key(Items.HONEY_BOTTLE));

        this.tag(OccultismTags.Items.DROPS_POSSESSED_ZOMBIFIED_PIGLIN)
                .add(this.key(OccultismItems.DEMONIC_MEAT.get()))
                .add(this.key(OccultismItems.TALLOW.get()))
                .add(this.key(Items.PORKCHOP))
                .add(this.key(Items.ROTTEN_FLESH));

        this.tag(OccultismTags.Items.DROPS_POSSESSED_GUARDIAN)
                .add(this.key(Items.SEA_PICKLE))
                .add(this.key(Items.KELP))
                .add(this.key(Items.TUBE_CORAL))
                .add(this.key(Items.BRAIN_CORAL))
                .add(this.key(Items.BUBBLE_CORAL))
                .add(this.key(Items.FIRE_CORAL))
                .add(this.key(Items.HORN_CORAL))
                .add(this.key(Items.TUBE_CORAL_BLOCK))
                .add(this.key(Items.BRAIN_CORAL_BLOCK))
                .add(this.key(Items.BUBBLE_CORAL_BLOCK))
                .add(this.key(Items.FIRE_CORAL_BLOCK))
                .add(this.key(Items.HORN_CORAL_BLOCK))
                .add(this.key(Items.TUBE_CORAL_FAN))
                .add(this.key(Items.BRAIN_CORAL_FAN))
                .add(this.key(Items.BUBBLE_CORAL_FAN))
                .add(this.key(Items.FIRE_CORAL_FAN))
                .add(this.key(Items.HORN_CORAL_FAN))
                .add(this.key(Items.PRISMARINE_SHARD))
                .add(this.key(Items.PRISMARINE_CRYSTALS));

        this.tag(OccultismTags.Items.DROPS_WILD_HUNT)
                .add(this.key(Items.WITHER_SKELETON_SKULL))
                .add(this.key(Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE))
                .add(this.key(Items.WITHER_ROSE))
                .add(this.key(Items.COAL))
                .add(this.key(Items.BONE))
                .add(this.key(Items.ARROW));

        this.tag(OccultismTags.Items.DROPS_WILD_HORDE_CREEPER)
                .add(this.key(Items.MUSIC_DISC_CAT))
                .add(this.key(Items.MUSIC_DISC_13))
                .add(this.key(Items.MUSIC_DISC_BLOCKS))
                .add(this.key(Items.MUSIC_DISC_CHIRP))
                .add(this.key(Items.MUSIC_DISC_FAR))
                .add(this.key(Items.MUSIC_DISC_MALL))
                .add(this.key(Items.MUSIC_DISC_MELLOHI))
                .add(this.key(Items.MUSIC_DISC_STAL))
                .add(this.key(Items.MUSIC_DISC_STRAD))
                .add(this.key(Items.MUSIC_DISC_WARD))
                .add(this.key(Items.MUSIC_DISC_11))
                .add(this.key(Items.MUSIC_DISC_WAIT));

        this.tag(OccultismTags.Items.DROPS_WILD_HORDE_DROWNED)
                .add(this.key(Items.SNIFFER_EGG))
                .add(this.key(Items.TRIDENT))
                .add(this.key(Items.TURTLE_EGG))
                .add(this.key(Items.SHELTER_POTTERY_SHERD))
                .add(this.key(Items.SNORT_POTTERY_SHERD))
                .add(this.key(Items.ANGLER_POTTERY_SHERD))
                .add(this.key(Items.PLENTY_POTTERY_SHERD))
                .add(this.key(Items.BLADE_POTTERY_SHERD))
                .add(this.key(Items.EXPLORER_POTTERY_SHERD))
                .add(this.key(Items.MOURNER_POTTERY_SHERD))
                .add(this.key(Items.COPPER_INGOT))
                .add(this.key(Items.PRISMARINE_SHARD))
                .add(this.key(Items.PRISMARINE_CRYSTALS));

        this.tag(OccultismTags.Items.DROPS_WILD_HORDE_HUSK)
                .add(this.key(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE))
                .add(this.key(Items.SKULL_POTTERY_SHERD))
                .add(this.key(Items.ARCHER_POTTERY_SHERD))
                .add(this.key(Items.PRIZE_POTTERY_SHERD))
                .add(this.key(Items.MINER_POTTERY_SHERD))
                .add(this.key(Items.BREWER_POTTERY_SHERD))
                .add(this.key(Items.ARMS_UP_POTTERY_SHERD));
        this.tag(OccultismTags.Items.DROPS_WILD_HORDE_PARCHED)
                .add(this.key(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE))
                .add(this.key(Items.SKULL_POTTERY_SHERD))
                .add(this.key(Items.ARCHER_POTTERY_SHERD))
                .add(this.key(Items.PRIZE_POTTERY_SHERD))
                .add(this.key(Items.MINER_POTTERY_SHERD))
                .add(this.key(Items.BREWER_POTTERY_SHERD))
                .add(this.key(Items.ARMS_UP_POTTERY_SHERD));

        this.tag(OccultismTags.Items.DROPS_WILD_HORDE_SILVERFISH)
                .add(this.key(Items.MUSIC_DISC_RELIC))
                .add(this.key(Items.HEART_POTTERY_SHERD))
                .add(this.key(Items.SHEAF_POTTERY_SHERD))
                .add(this.key(Items.DANGER_POTTERY_SHERD))
                .add(this.key(Items.BURN_POTTERY_SHERD))
                .add(this.key(Items.HOWL_POTTERY_SHERD))
                .add(this.key(Items.FRIEND_POTTERY_SHERD))
                .add(this.key(Items.HEARTBREAK_POTTERY_SHERD))
                .add(this.key(Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE))
                .add(this.key(Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE))
                .add(this.key(Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE))
                .add(this.key(Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE));
    }

    private void addRandomEggs(Provider provider) {
        this.tag(OccultismTags.Items.RANDOM_SPAWN_COMMON)
                .add(this.key(Items.CHICKEN_SPAWN_EGG))
                .add(this.key(Items.COW_SPAWN_EGG))
                .add(this.key(Items.PIG_SPAWN_EGG))
                .add(this.key(Items.SHEEP_SPAWN_EGG))
                .add(this.key(Items.SQUID_SPAWN_EGG))
                .add(this.key(Items.WOLF_SPAWN_EGG));
        this.tag(OccultismTags.Items.RANDOM_SPAWN_RIDEABLE)
                .add(this.key(Items.PIG_SPAWN_EGG))
                .add(this.key(Items.CAMEL_SPAWN_EGG))
                .add(this.key(Items.DONKEY_SPAWN_EGG))
                .add(this.key(Items.HORSE_SPAWN_EGG))
                .add(this.key(Items.SKELETON_HORSE_SPAWN_EGG))
                .add(this.key(Items.ZOMBIE_HORSE_SPAWN_EGG))
                .add(this.key(Items.LLAMA_SPAWN_EGG))
                .add(this.key(Items.TRADER_LLAMA_SPAWN_EGG))
                .add(this.key(Items.MULE_SPAWN_EGG))
                .add(this.key(Items.STRIDER_SPAWN_EGG))
                .add(this.key(Items.HAPPY_GHAST_SPAWN_EGG))
                .add(this.key(Items.NAUTILUS_SPAWN_EGG))
                .add(this.key(Items.ZOMBIE_NAUTILUS_SPAWN_EGG))
                .add(this.key(Items.CAMEL_HUSK_SPAWN_EGG));
        this.tag(OccultismTags.Items.RANDOM_SPAWN_SMALL)
                .add(this.key(Items.ALLAY_SPAWN_EGG))
                .add(this.key(Items.BAT_SPAWN_EGG))
                .add(this.key(Items.BEE_SPAWN_EGG))
                .add(this.key(Items.CAT_SPAWN_EGG))
                .add(this.key(Items.FOX_SPAWN_EGG))
                .add(this.key(Items.OCELOT_SPAWN_EGG))
                .add(this.key(Items.PARROT_SPAWN_EGG))
                .add(this.key(Items.RABBIT_SPAWN_EGG));
        this.tag(OccultismTags.Items.RANDOM_SPAWN_SPECIAL)
                .add(this.key(Items.ARMADILLO_SPAWN_EGG))
                .add(this.key(Items.IRON_GOLEM_SPAWN_EGG))
                .add(this.key(Items.MOOSHROOM_SPAWN_EGG))
                .add(this.key(Items.PANDA_SPAWN_EGG))
                .add(this.key(Items.POLAR_BEAR_SPAWN_EGG))
                .add(this.key(Items.GOAT_SPAWN_EGG))
                .add(this.key(Items.SNIFFER_SPAWN_EGG))
                .add(this.key(Items.COPPER_GOLEM_SPAWN_EGG));
        this.tag(OccultismTags.Items.RANDOM_SPAWN_WATER)
                .add(this.key(Items.AXOLOTL_SPAWN_EGG))
                .add(this.key(Items.FROG_SPAWN_EGG))
                .add(this.key(Items.DOLPHIN_SPAWN_EGG))
                .add(this.key(Items.SALMON_SPAWN_EGG))
                .add(this.key(Items.COD_SPAWN_EGG))
                .add(this.key(Items.TROPICAL_FISH_SPAWN_EGG))
                .add(this.key(Items.PUFFERFISH_SPAWN_EGG))
                .add(this.key(Items.SQUID_SPAWN_EGG))
                .add(this.key(Items.SNOW_GOLEM_SPAWN_EGG))
                .add(this.key(Items.GLOW_SQUID_SPAWN_EGG))
                .add(this.key(Items.TADPOLE_SPAWN_EGG))
                .add(this.key(Items.TURTLE_SPAWN_EGG))
                .add(this.key(Items.NAUTILUS_SPAWN_EGG))
                .add(this.key(Items.ZOMBIE_NAUTILUS_SPAWN_EGG));
        this.tag(OccultismTags.Items.RANDOM_SPAWN_VILLAGER)
                .add(this.key(Items.VILLAGER_SPAWN_EGG))
                .add(this.key(Items.WANDERING_TRADER_SPAWN_EGG))
                .add(this.key(OccultismItems.SPAWN_EGG_WONDERING_TRADER.get()));
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

    private ResourceKey<Item> key(Item item) {
        return BuiltInRegistries.ITEM.getResourceKey(item).orElseThrow();
    }

    private ResourceKey<Item> key(Block block) {
        return BuiltInRegistries.ITEM.getResourceKey(block.asItem()).orElseThrow();
    }
}
