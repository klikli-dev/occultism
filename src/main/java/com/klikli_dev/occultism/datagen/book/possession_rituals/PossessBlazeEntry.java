package com.klikli_dev.occultism.datagen.book.possession_rituals;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.minecraft.world.item.Items;

public class PossessBlazeEntry extends AbstractPossessionEntry {

    public static final String ENTRY_ID = "possess_blaze";

    public PossessBlazeEntry(CategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.entityPage("entity", "occultism:possessed_blaze", 1f);
        this.pageText("""
                **Drops**: 2-6x [](item://minecraft:blaze_rod), 0-13x [](item://minecraft:blaze_powder)
                and nether-related items (check next page);
                """);

        this.ritualPage("ritual", "ritual/possess_blaze");

        this.textPageNoTitle("description");
        this.pageText("""
                In this ritual a [#]({0})Blaze[#]() is spawned with energy of [#]({0})The Nether[#]() and immediately 
                possessed by the summoned [#]({0})Djinni[#](). The [#]({0})Possessed Blaze[#]() is immune to water and snowball! 
                \\                        
                Extra Drops:
                \\
                Always one of [](item://minecraft:nether_wart), [](item://minecraft:crimson_fungus), [](item://minecraft:warped_fungus), 
                [](item://minecraft:red_mushroom), [](item://minecraft:brown_mushroom), [](item://minecraft:crimson_roots), 
                [](item://minecraft:warped_roots), [](item://minecraft:weeping_vines), [](item://minecraft:twisting_vines);                         
                """, COLOR_PURPLE);

        this.textPageNoTitle("description2");
        this.pageText("""
                Usually one of [](item://minecraft:netherrack), [](item://minecraft:nether_quartz_ore), [](item://minecraft:crimson_nylium),
                [](item://minecraft:warped_nylium), [](item://minecraft:nether_wart_block), [](item://minecraft:warped_wart_block);
                \\
                Generally one of [](item://minecraft:soul_sand), [](item://minecraft:soul_soil), [](item://minecraft:basalt), 
                [](item://minecraft:blackstone), [](item://minecraft:gravel), [](item://minecraft:bone_block), [](item://minecraft:gilded_blackstone);
                Sometimes one of [](item://minecraft:glowstone_dust), [](item://minecraft:magma_block), [](item://minecraft:glowstone), [](item://minecraft:shroomlight);
                \\
                Occasionally one of [](item://minecraft:obsidian), [](item://minecraft:crying_obsidian), [](item://minecraft:ancient_debris);
                """);
    }

    @Override
    protected String entryName() {
        return "Possessed Blaze";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.BLAZE_ROD);
    }

    @Override
    protected String entryId() {
        return ENTRY_ID;
    }
}
