package com.klikli_dev.occultism.common.item.spirit.calling;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.api.common.data.WorkAreaSize;
import com.klikli_dev.occultism.client.gui.spirit.BookOfCallingGui;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.particle.MobAppearanceParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.function.Supplier;

/**
 * Represents the different modes of the calling item
 */
public class ItemMode implements IItemModeSubset<ItemMode>{
    private static final String TRANSLATION_KEY_BASE =
            "enum." + Occultism.MODID + ".book_of_calling.item_mode";


    /**
     * Creates a new item mode
     * @param value the value of the mode
     * @param translationKey the translation key for the mode
     * @param hasSize if the mode has an area size
     */
    public ItemMode(int value, String translationKey, boolean hasSize) {
        this.value = value;
        this.translationKey = translationKey;
        this.hasSize = hasSize;
    }

    /**
     * Creates a new item mode
     * @param value the value of the mode
     * @param translationKey the translation key for the mode
     */
    public ItemMode(int value, String translationKey)  {
        this(value, translationKey, false);
    }

    /**
     * Does the mode need the area size selector
     * @return true if the mode needs the area size selector
     */
    public boolean hasSize() {
        return hasSize;
    }

    /**
     * Sets if the mode needs the area size selector
     * @param hasSize if the mode needs the area size selector
     * @return the item mode
     */
    public ItemMode setHasSize(boolean hasSize) {
        this.hasSize = hasSize;
        return this;
    }

    /**
     * The translation key for the mode. Uses default Occultism base translation key
     * Extend this class to override the base key
     * @return the translation key for the mode
     */
    public String translationKey() {
        return TRANSLATION_KEY_BASE + "." + translationKey;
    }

    /**
     * Sets the translation key for the mode
     * @param translationKey the translation key for the mode
     * @return the item mode
     */
    public ItemMode setTranslationKey(String translationKey) {
        this.translationKey = translationKey;
        return this;
    }

    /**
     * The value of the mode
     * @return the value of the mode
     */
    public int value() {
        return value;
    }

    /**
     * Sets the value of the mode
     * @param value the value of the mode
     * @return the item mode
     */
    public ItemMode setValue(int value) {
        this.value = value;
        return this;
    }

    @Override
    public ItemMode getItemMode() {
        return this;
    }

    /**
     * Gets the next item mode in the list
     * @return the next item mode
     */
    public ItemMode next() {
        return ItemModes.get((this.value + 1) % ItemModes.getSize());
    }

    public Supplier<Screen> getGUI(WorkAreaSize workAreaSize) {
        return ()->new BookOfCallingGui(this, workAreaSize);
    }
    /**
     * Handles the mode's action
     * @param blockEntity the block entity
     * @param player the player
     * @param world the world
     * @param pos the position
     * @param stack the stack
     * @param face the face
     * @return true if the action was handled
     */
    public boolean handle(BlockEntity blockEntity, Player player, Level world, BlockPos pos, ItemStack stack,
                          Direction face)
    {
        return true;
    }

    private  int value;
    private String translationKey;
    private boolean hasSize;

}
