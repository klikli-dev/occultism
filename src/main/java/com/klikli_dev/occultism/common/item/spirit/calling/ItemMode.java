package com.klikli_dev.occultism.common.item.spirit.calling;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.api.common.data.WorkAreaSize;
import com.klikli_dev.occultism.client.gui.GuiHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents the different modes of the calling item
 */
public class ItemMode {
    private static final String TRANSLATION_KEY_BASE =
            "enum." + Occultism.MODID + ".book_of_calling.item_mode";
    private int value = -1;
    private String translationKey;
    private boolean hasSize;

    /**
     * Creates a new item mode
     *
     * @param translationKey the translation key for the mode
     * @param hasSize        if the mode has an area size
     */
    public ItemMode(String translationKey, boolean hasSize) {
        this.translationKey = translationKey;
        this.hasSize = hasSize;
    }

    /**
     * Creates a new item mode
     *
     * @param translationKey the translation key for the mode
     */
    public ItemMode(String translationKey) {
        this(translationKey, false);
    }

    /**
     * Does the mode need the area size selector
     *
     * @return true if the mode needs the area size selector
     */
    public boolean hasSize() {
        return this.hasSize;
    }

    /**
     * Sets if the mode needs the area size selector
     *
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
     *
     * @return the translation key for the mode
     */
    public String translationKey() {
        return TRANSLATION_KEY_BASE + "." + this.translationKey;
    }

    /**
     * Sets the translation key for the mode
     *
     * @param translationKey the translation key for the mode
     * @return the item mode
     */
    public ItemMode setTranslationKey(String translationKey) {
        this.translationKey = translationKey;
        return this;
    }

    /**
     * The value of the mode
     *
     * @return the value of the mode
     */
    private int value() {
        return this.value;
    }

    /**
     * Sets the value of the mode
     *
     * @param value the value of the mode
     * @return the item mode
     */
    public ItemMode setValue(int value) {
        this.value = value;
        return this;
    }

    public void openGUI(WorkAreaSize workAreaSize) {
        GuiHelper.openBookOfCallingGui_internal(this, workAreaSize);
    }

    /**
     * Handles the mode's action
     *
     * @param blockEntity the block entity
     * @param player      the player
     * @param world       the world
     * @param pos         the position
     * @param stack       the stack
     * @param face        the face
     * @return true if the action was handled
     */
    public boolean handle(BlockEntity blockEntity, Player player, Level world, BlockPos pos, ItemStack stack,
                          Direction face) {
        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", this.value)
                .append("translationKey", this.translationKey)
                .append("hasSize", this.hasSize)
                .toString();
    }
}
