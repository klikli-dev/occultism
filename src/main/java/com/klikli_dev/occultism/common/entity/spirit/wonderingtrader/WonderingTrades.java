package com.klikli_dev.occultism.common.entity.spirit.wonderingtrader;

import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import com.klikli_dev.occultism.util.TextUtil;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.trading.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class WonderingTrades {
    public static final int HINT = 0;
    public static final int BOOK = 1;
    public static final int PARAPHERNALIA = 2;
    public static final int MATERIAL = 3;
    public static final int INVENTORY = 4;
    public static final int STORAGE = 5;
    public static final int UTILITY = 6;
    public static final int FAMILIAR = 7;
    public static final int DYE = 8;

    public static Int2ObjectMap<VillagerTrades.ItemListing[]> WONDERING_TRADES = new Int2ObjectOpenHashMap<>(Map.of(
            HINT, new VillagerTrades.ItemListing[]{
                    new ItemTrade(new ItemStack(Items.WHEAT_SEEDS, 1),
                            new ItemStack(OccultismItems.DATURA.get()), 1, 1)},
            BOOK, new VillagerTrades.ItemListing[]{
                    new ItemTrade(new ItemStack(OccultismItems.OTHERWORLD_ESSENCE.get(), 1),
                            new ItemStack(Items.BOOK), 1, 1),
                    new ItemTrade(new ItemStack(OccultismItems.OTHERWORLD_ESSENCE.get(), 1),
                            new ItemStack(OccultismItems.TABOO_BOOK.get()), 1, 2),
                    new ItemTrade(new ItemStack(OccultismItems.OTHERWORLD_ESSENCE.get(), 2),
                            new ItemStack(Items.WRITABLE_BOOK), 1, 3),
                    new ItemTrade(new ItemStack(OccultismItems.OTHERWORLD_ESSENCE.get(), 2),
                            new ItemStack(OccultismItems.BOOK_OF_BINDING_EMPTY.get()), 1, 4),
                    new ItemTrade(new ItemStack(OccultismItems.OTHERWORLD_ESSENCE.get(), 1),
                            new ItemStack(OccultismItems.BOOK_OF_BINDING_BOUND_FOLIOT.get()), 1, 1),
                    new ItemTrade(new ItemStack(OccultismItems.OTHERWORLD_ESSENCE.get(), 2),
                            new ItemStack(OccultismItems.BOOK_OF_BINDING_BOUND_DJINNI.get()), 1, 2),
                    new ItemTrade(new ItemStack(OccultismItems.OTHERWORLD_ESSENCE.get(), 3),
                            new ItemStack(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()), 1, 3),
                    new ItemTrade(new ItemStack(OccultismItems.OTHERWORLD_ESSENCE.get(), 4),
                            new ItemStack(OccultismItems.BOOK_OF_BINDING_BOUND_MARID.get()), 1, 4)},
            PARAPHERNALIA, new VillagerTrades.ItemListing[]{
                    new ItemTrade(new ItemStack(Items.ROTTEN_FLESH, 10),
                            new ItemStack(OccultismBlocks.LARGE_CANDLE.get()), 8, 4),
                    new ItemTrade(new ItemStack(Items.ROTTEN_FLESH, 10),
                            new ItemStack(OccultismBlocks.LARGE_CANDLE_WHITE.get()), 8, 4),
                    new ItemTrade(new ItemStack(Items.ROTTEN_FLESH, 10),
                            new ItemStack(OccultismBlocks.LARGE_CANDLE_LIGHT_GRAY.get()), 8, 4),
                    new ItemTrade(new ItemStack(Items.ROTTEN_FLESH, 10),
                            new ItemStack(OccultismBlocks.LARGE_CANDLE_GRAY.get()), 8, 4),
                    new ItemTrade(new ItemStack(Items.ROTTEN_FLESH, 10),
                            new ItemStack(OccultismBlocks.LARGE_CANDLE_BLACK.get()), 8, 4),
                    new ItemTrade(new ItemStack(Items.ROTTEN_FLESH, 10),
                            new ItemStack(OccultismBlocks.LARGE_CANDLE_BROWN.get()), 8, 4),
                    new ItemTrade(new ItemStack(Items.ROTTEN_FLESH, 10),
                            new ItemStack(OccultismBlocks.LARGE_CANDLE_RED.get()), 8, 4),
                    new ItemTrade(new ItemStack(Items.ROTTEN_FLESH, 10),
                            new ItemStack(OccultismBlocks.LARGE_CANDLE_ORANGE.get()), 8, 4),
                    new ItemTrade(new ItemStack(Items.ROTTEN_FLESH, 10),
                            new ItemStack(OccultismBlocks.LARGE_CANDLE_YELLOW.get()), 8, 4),
                    new ItemTrade(new ItemStack(Items.ROTTEN_FLESH, 10),
                            new ItemStack(OccultismBlocks.LARGE_CANDLE_LIME.get()), 8, 4),
                    new ItemTrade(new ItemStack(Items.ROTTEN_FLESH, 10),
                            new ItemStack(OccultismBlocks.LARGE_CANDLE_GREEN.get()), 8, 4),
                    new ItemTrade(new ItemStack(Items.ROTTEN_FLESH, 10),
                            new ItemStack(OccultismBlocks.LARGE_CANDLE_CYAN.get()), 8, 4),
                    new ItemTrade(new ItemStack(Items.ROTTEN_FLESH, 10),
                            new ItemStack(OccultismBlocks.LARGE_CANDLE_LIGHT_BLUE.get()), 8, 4),
                    new ItemTrade(new ItemStack(Items.ROTTEN_FLESH, 10),
                            new ItemStack(OccultismBlocks.LARGE_CANDLE_BLUE.get()), 8, 4),
                    new ItemTrade(new ItemStack(Items.ROTTEN_FLESH, 10),
                            new ItemStack(OccultismBlocks.LARGE_CANDLE_PURPLE.get()), 8, 4),
                    new ItemTrade(new ItemStack(Items.ROTTEN_FLESH, 10),
                            new ItemStack(OccultismBlocks.LARGE_CANDLE_MAGENTA.get()), 8, 4),
                    new ItemTrade(new ItemStack(Items.ROTTEN_FLESH, 10),
                            new ItemStack(OccultismBlocks.LARGE_CANDLE_PINK.get()), 8, 4),
                    new ItemTrade(new ItemStack(Items.IRON_BLOCK, 1),
                            new ItemStack(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()), 8, 8),
                    new ItemTrade(new ItemStack(Items.BONE_BLOCK, 4),
                            new ItemStack(Items.SKELETON_SKULL), 4, 8),
                    new ItemTrade(new ItemStack(Items.COAL, 64),
                            new ItemStack(Items.WITHER_SKELETON_SKULL), 2, 16)},
            MATERIAL, new VillagerTrades.ItemListing[]{
                    new ItemTrade(new ItemStack(Items.ROSE_BUSH, 1),
                            new ItemStack(OccultismBlocks.OTHERFLOWER, 15), 15, 15),
                    new ItemTrade(new ItemStack(Items.NETHER_WART, 7),
                            new ItemStack(Items.TORCHFLOWER), 7, 7),
                    new ItemTrade(new ItemStack(OccultismItems.DATURA.get(), 12),
                            new ItemStack(OccultismItems.NATURE_PASTE.get()), 1, 24),
                    new ItemTrade(new ItemStack(Items.TUFF, 4),
                            new ItemStack(OccultismItems.GRAY_PASTE.get()), 1, 24),
                    new ItemTrade(new ItemStack(Items.EMERALD_BLOCK, 1),
                            new ItemStack(OccultismItems.RESEARCH_FRAGMENT_DUST.get()), 1, 32),
                    new ItemTrade(new ItemStack(Items.NETHER_STAR, 1),
                            new ItemStack(OccultismItems.WITHERITE_DUST.get()), 1, 32),
                    new ItemTrade(new ItemStack(Items.DRAGON_HEAD, 1),
                            new ItemStack(OccultismItems.DRAGONYST_DUST.get()), 1, 32),
                    new ItemTrade(new ItemStack(OccultismItems.DEMONIC_MEAT.get(), 2),
                            new ItemStack(OccultismItems.IESNIUM_INGOT.get()), 3, 66),
                    new ItemTrade(new ItemStack(Items.GLOW_INK_SAC, 4),
                            new ItemStack(Items.ECHO_SHARD), 4, 16),
                    new ItemTrade(new ItemStack(Items.SEA_LANTERN, 16),
                            new ItemStack(Items.HEART_OF_THE_SEA), 1, 99),
                    new ItemTrade(new ItemStack(Items.SPORE_BLOSSOM, 1),
                            new ItemStack(Items.DRAGON_BREATH), 1, 33),
                    new ItemTrade(new ItemStack(Items.DARK_PRISMARINE, 1),
                            new ItemStack(Items.TURTLE_SCUTE), 2, 8),
                    new ItemTrade(new ItemStack(Items.BROWN_GLAZED_TERRACOTTA, 1),
                            new ItemStack(Items.ARMADILLO_SCUTE), 2, 8),
                    new ItemTrade(new ItemStack(Items.PURPUR_PILLAR, 1),
                            new ItemStack(Items.SHULKER_SHELL), 2, 8),
                    new ItemTrade(new ItemStack(Items.CHISELED_SANDSTONE, 1),
                            new ItemStack(Items.NAUTILUS_SHELL), 2, 8),
                    new ItemTrade(new ItemStack(Items.AMETHYST_SHARD, 5),
                            new ItemStack(Items.RABBIT_FOOT), 4, 20),
                    new ItemTrade(new ItemStack(Items.LAVA_BUCKET, 1),
                            new ItemStack(Items.BLAZE_ROD, 10), 1, 20),
                    new ItemTrade(new ItemStack(Items.POWDER_SNOW_BUCKET, 1),
                            new ItemStack(Items.BREEZE_ROD, 10), 1, 20)},
            INVENTORY, new VillagerTrades.ItemListing[]{
                    new ItemTrade(new ItemStack(Items.CHEST_MINECART, 1),
                            new ItemStack(OccultismItems.SATCHEL.get()), 1, 27),
                    new ItemTrade(new ItemStack(Items.END_ROD, 4),
                            new ItemStack(OccultismItems.ENDER_SATCHEL.get()), 1, 27),
                    new ItemTrade(new ItemStack(Items.GLOWSTONE, 2),
                            new ItemStack(OccultismItems.RITUAL_SATCHEL_T1.get()), 1, 27),
                    new ItemTrade(new ItemStack(OccultismItems.AFRIT_ESSENCE.get(), 2),
                            new ItemStack(OccultismItems.RITUAL_SATCHEL_T2.get()), 1, 27),
                    new ItemTrade(new ItemStack(Items.SOUL_LANTERN, 1),
                            new ItemStack(OccultismItems.FRAGILE_SOUL_GEM_ITEM.get()), 1, 27),
                    new ItemTrade(new ItemStack(Items.SOUL_LANTERN, 32),
                            new ItemStack(OccultismItems.SOUL_GEM_ITEM.get()), 1, 27),
                    new ItemTrade(new ItemStack(Items.WITHER_SKELETON_SKULL, 1),
                            new ItemStack(OccultismItems.FAMILIAR_RING.get()), 1, 27),
                    new ItemTrade(new ItemStack(OccultismItems.DEMONIC_MEAT.get(), 2),
                            new ItemStack(OccultismItems.STORAGE_REMOTE.get(), 1), 1, 27),
                    new ItemTrade(new ItemStack(Items.SCULK_CATALYST, 1),
                            new ItemStack(OccultismItems.KNOWLEDGE_TABLET.get()), 1, 27)},
            STORAGE, new VillagerTrades.ItemListing[]{
                    new ItemTrade(new ItemStack(Items.QUARTZ, 27),
                            new ItemStack(OccultismItems.DIMENSIONAL_MATRIX.get()), 1, 27),
                    new ItemTrade(new ItemStack(Items.GOLD_INGOT, 3),
                            new ItemStack(OccultismBlocks.STORAGE_CONTROLLER_BASE.get()), 1, 27),
                    new ItemTrade(new ItemStack(Items.GOLD_INGOT, 3),
                            new ItemStack(OccultismBlocks.STORAGE_CONTROLLER_BASE_DARK.get()), 1, 27),
                    new ItemTrade(new ItemStack(Items.ENDER_EYE, 2),
                            new ItemStack(OccultismBlocks.STABLE_WORMHOLE.get()), 3, 27),
                    new ItemTrade(new ItemStack(Items.ENDER_EYE, 2),
                            new ItemStack(OccultismBlocks.STABLE_WORMHOLE_DARK.get()), 3, 27),
                    new ItemTrade(new ItemStack(Items.COPPER_BLOCK, 4),
                            new ItemStack(OccultismBlocks.STORAGE_STABILIZER_TIER1.get()), 3, 27),
                    new ItemTrade(new ItemStack(Items.COPPER_BLOCK, 4),
                            new ItemStack(OccultismBlocks.STORAGE_STABILIZER_TIER1_DARK.get()), 3, 27),
                    new ItemTrade(new ItemStack(OccultismItems.SPIRIT_ATTUNED_GEM.get(), 8),
                            new ItemStack(OccultismBlocks.STORAGE_STABILIZER_TIER2.get()), 3, 27),
                    new ItemTrade(new ItemStack(OccultismItems.SPIRIT_ATTUNED_GEM.get(), 8),
                            new ItemStack(OccultismBlocks.STORAGE_STABILIZER_TIER2_DARK.get()), 3, 27),
                    new ItemTrade(new ItemStack(OccultismItems.AFRIT_ESSENCE.get(), 4),
                            new ItemStack(OccultismBlocks.STORAGE_STABILIZER_TIER3.get()), 1, 27),
                    new ItemTrade(new ItemStack(OccultismItems.AFRIT_ESSENCE.get(), 4),
                            new ItemStack(OccultismBlocks.STORAGE_STABILIZER_TIER3_DARK.get()), 1, 27),
                    new ItemTrade(new ItemStack(OccultismItems.MARID_ESSENCE.get(), 5),
                            new ItemStack(OccultismBlocks.STORAGE_STABILIZER_TIER4.get()), 1, 27),
                    new ItemTrade(new ItemStack(OccultismItems.MARID_ESSENCE.get(), 5),
                            new ItemStack(OccultismBlocks.STORAGE_STABILIZER_TIER4_DARK.get()), 1, 27),

                    new ItemTrade(new ItemStack(Items.ECHO_SHARD, 3),
                            new ItemStack(OccultismBlocks.ENTITY_WORMHOLE.get()), 2, 27),
                    new ItemTrade(new ItemStack(Items.ECHO_SHARD, 3),
                            new ItemStack(OccultismBlocks.ENTITY_WORMHOLE_DARK.get()), 2, 27),
                    new ItemTrade(new ItemStack(OccultismItems.AFRIT_ESSENCE.get(), 8),
                            new ItemStack(OccultismBlocks.IESNIUM_SACRIFICIAL_BOWL.get()), 1, 27),
                    new ItemTrade(new ItemStack(OccultismItems.AFRIT_ESSENCE.get(), 8),
                            new ItemStack(OccultismBlocks.DARK_IESNIUM_SACRIFICIAL_BOWL.get()), 1, 27)},
            UTILITY, new VillagerTrades.ItemListing[]{
                    new ItemTrade(new ItemStack(Items.SPYGLASS, 1),
                            new ItemStack(OccultismItems.OTHERWORLD_GOGGLES.get()), 1, 32),
                    new ItemTrade(new ItemStack(Items.END_CRYSTAL, 1),
                            new ItemStack(OccultismItems.VITALITY_COMPASS.get()), 1, 32),
                    new ItemTrade(new ItemStack(Items.DIAMOND_BLOCK, 2),
                            new ItemStack(OccultismItems.INFUSED_PICKAXE.get()), 1, 32),
                    new ItemTrade(new ItemStack(OccultismItems.DEMONIC_MEAT.get(), 5),
                            new ItemStack(OccultismItems.IESNIUM_PICKAXE.get()), 1, 32),
                    new ItemTrade(new ItemStack(OccultismItems.DEMONIC_MEAT.get(), 8),
                            new ItemStack(OccultismBlocks.DIMENSIONAL_MINESHAFT.get()), 1, 32),
                    new ItemTrade(new ItemStack(OccultismItems.DEMONIC_MEAT.get(), 4),
                            new ItemStack(OccultismBlocks.DIMENSIONAL_BATTLEFIELD.get()), 1, 32),
                    new ItemTrade(new ItemStack(OccultismItems.CRUELTY_ESSENCE.get(), 2),
                            new ItemStack(OccultismBlocks.DIMENSIONAL_EXTRACTOR.get()), 1, 32),
                    new ItemTrade(new ItemStack(OccultismItems.CURSED_HONEY.get(), 8),
                            new ItemStack(OccultismItems.MINER_FOLIOT_UNSPECIALIZED.get()), 1, 32),
                    new ItemTrade(new ItemStack(OccultismItems.DEMONIC_MEAT.get(), 9),
                            new ItemStack(OccultismItems.MINER_DJINNI_ORES.get()), 1, 32),
                    new ItemTrade(new ItemStack(OccultismItems.DEMONIC_MEAT.get(), 64),
                            new ItemStack(OccultismItems.MINER_AFRIT_DEEPS.get()), 1, 32),
                    new ItemTrade(new ItemStack(OccultismItems.DRAGONYST_DUST.get(), 1),
                            new ItemStack(Items.ELYTRA), 1, 32),
                    new ItemTrade(new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 1),
                            new ItemStack(Items.TOTEM_OF_UNDYING), 1, 32),
                    new ItemTrade(new ItemStack(Items.ENDER_EYE, 1),
                            new ItemStack(Items.TURTLE_HELMET), 1, 32)},
            FAMILIAR, new VillagerTrades.ItemListing[]{
                    new ItemTrade(new ItemStack(OccultismBlocks.IESNIUM_BLOCK, 1),
                            new ItemStack(OccultismItems.SPAWN_EGG_BAT_FAMILIAR.get()), 1, 24),
                    new ItemTrade(new ItemStack(OccultismBlocks.IESNIUM_BLOCK, 1),
                            new ItemStack(OccultismItems.SPAWN_EGG_BEAVER_FAMILIAR.get()), 1, 24),
                    new ItemTrade(new ItemStack(OccultismBlocks.IESNIUM_BLOCK, 1),
                            new ItemStack(OccultismItems.SPAWN_EGG_BEHOLDER_FAMILIAR.get()), 1, 24),
                    new ItemTrade(new ItemStack(OccultismBlocks.IESNIUM_BLOCK, 1),
                            new ItemStack(OccultismItems.SPAWN_EGG_BLACKSMITH_FAMILIAR.get()), 1, 24),
                    new ItemTrade(new ItemStack(OccultismBlocks.IESNIUM_BLOCK, 1),
                            new ItemStack(OccultismItems.SPAWN_EGG_CHIMERA_FAMILIAR.get()), 1, 24),
                    new ItemTrade(new ItemStack(OccultismBlocks.IESNIUM_BLOCK, 1),
                            new ItemStack(OccultismItems.SPAWN_EGG_CTHULHU_FAMILIAR.get()), 1, 24),
                    new ItemTrade(new ItemStack(OccultismBlocks.IESNIUM_BLOCK, 1),
                            new ItemStack(OccultismItems.SPAWN_EGG_DEER_FAMILIAR.get()), 1, 24),
                    new ItemTrade(new ItemStack(OccultismBlocks.IESNIUM_BLOCK, 1),
                            new ItemStack(OccultismItems.SPAWN_EGG_DEVIL_FAMILIAR.get()), 1, 24),
                    new ItemTrade(new ItemStack(OccultismBlocks.IESNIUM_BLOCK, 1),
                            new ItemStack(OccultismItems.SPAWN_EGG_DRAGON_FAMILIAR.get()), 1, 24),
                    new ItemTrade(new ItemStack(OccultismBlocks.IESNIUM_BLOCK, 1),
                            new ItemStack(OccultismItems.SPAWN_EGG_FAIRY_FAMILIAR.get()), 1, 24),
                    new ItemTrade(new ItemStack(OccultismBlocks.IESNIUM_BLOCK, 1),
                            new ItemStack(OccultismItems.SPAWN_EGG_GREEDY_FAMILIAR.get()), 1, 24),
                    new ItemTrade(new ItemStack(OccultismBlocks.IESNIUM_BLOCK, 1),
                            new ItemStack(OccultismItems.SPAWN_EGG_HEADLESS_FAMILIAR.get()), 1, 24),
                    new ItemTrade(new ItemStack(OccultismBlocks.IESNIUM_BLOCK, 1),
                            new ItemStack(OccultismItems.SPAWN_EGG_MUMMY_FAMILIAR.get()), 1, 24)},
            DYE, new VillagerTrades.ItemListing[]{
                    new ItemTrade(new ItemStack(Items.WHITE_DYE, 64),
                            new ItemStack(OccultismBlocks.SPIRIT_GRINDSTONE.get()), 1, 1),
                    new ItemTrade(new ItemStack(Items.LIGHT_GRAY_DYE, 64),
                            new ItemStack(Items.HEAVY_CORE), 1, 1),
                    new ItemTrade(new ItemStack(Items.GRAY_DYE, 64),
                            new ItemStack(Items.REINFORCED_DEEPSLATE), 1, 1),
                    new ItemTrade(new ItemStack(Items.BLACK_DYE, 64),
                            new ItemStack(Items.NETHERITE_BLOCK), 1, 1),
                    new ItemTrade(new ItemStack(Items.BROWN_DYE, 64),
                            new ItemStack(Items.ENCHANTED_GOLDEN_APPLE), 1, 1),
                    new ItemTrade(new ItemStack(Items.RED_DYE, 64),
                            new ItemStack(Items.BELL), 1, 1),
                    new ItemTrade(new ItemStack(Items.ORANGE_DYE, 64),
                            new ItemStack(Items.END_STONE_BRICKS), 1, 1),
                    new ItemTrade(new ItemStack(Items.YELLOW_DYE, 64),
                            new ItemStack(Items.SPONGE), 1, 1),
                    new ItemTrade(new ItemStack(Items.LIME_DYE, 64),
                            new ItemStack(Items.EMERALD_BLOCK), 1, 1),
                    new ItemTrade(new ItemStack(Items.GREEN_DYE, 64),
                            new ItemStack(Items.SCULK_SHRIEKER), 1, 1),
                    new ItemTrade(new ItemStack(Items.CYAN_DYE, 64),
                            new ItemStack(Items.SCULK_CATALYST), 1, 1),
                    new ItemTrade(new ItemStack(Items.LIGHT_BLUE_DYE, 64),
                            new ItemStack(OccultismBlocks.IESNIUM_BLOCK.get()), 1, 1),
                    new ItemTrade(new ItemStack(Items.BLUE_DYE, 64),
                            new ItemStack(Items.SOUL_LANTERN), 1, 1),
                    new ItemTrade(new ItemStack(Items.PURPLE_DYE, 64),
                            new ItemStack(Items.RESPAWN_ANCHOR), 1, 1),
                    new ItemTrade(new ItemStack(Items.MAGENTA_DYE, 64),
                            new ItemStack(Items.BUDDING_AMETHYST), 1, 1),
                    new ItemTrade(new ItemStack(Items.PINK_DYE, 64),
                            new ItemStack(Items.CHORUS_FLOWER), 1, 1)}
            ));

    public WonderingTrades(){}

    public static class ItemTrade implements VillagerTrades.ItemListing {
        private final ItemStack input;
        private final int maxUses;
        private final int villagerXp;
        private final ItemStack result;
        private final float priceMultiplier;

        public ItemTrade(ItemStack input, ItemStack result, int maxUses, int villagerXp) {
            this.input = input;
            this.result = result.has(OccultismDataComponents.SPIRIT_NAME) ? this.applyName(result) : result;
            this.maxUses = maxUses;
            this.villagerXp = villagerXp;
            this.priceMultiplier = 0.05F;
        }

        private ItemStack applyName(ItemStack item) {
            ItemStack copy = item.copy();
            ItemNBTUtil.setBoundSpiritName(copy, TextUtil.generateName());
            return copy;
        }

        public MerchantOffer getOffer(@NotNull Entity trader, @NotNull RandomSource random) {
            return new MerchantOffer(new ItemCost(this.input.getItem(), this.input.getCount()), this.result, this.maxUses, this.villagerXp, this.priceMultiplier);
        }
    }
}
