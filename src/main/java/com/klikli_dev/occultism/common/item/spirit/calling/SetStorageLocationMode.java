package com.klikli_dev.occultism.common.item.spirit.calling;

import com.klikli_dev.occultism.TranslationKeys;
import com.klikli_dev.occultism.api.common.blockentity.IStorageController;
import com.klikli_dev.occultism.api.common.data.GlobalBlockPos;
import com.klikli_dev.occultism.common.entity.job.ManageMachineJob;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.util.EntityUtil;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Optional;
import java.util.UUID;

public class SetStorageLocationMode extends ItemMode {

    public SetStorageLocationMode() {
        super(0, "set_storage_controller");
    }

    public boolean setSpiritStorageController(Player player, Level world, BlockPos pos, ItemStack stack,
                                              Direction face) {
        UUID boundSpiritId = ItemNBTUtil.getSpiritEntityUUID(stack);
        if (boundSpiritId != null) {
            Optional<SpiritEntity> boundSpirit = EntityUtil.getEntityByUuiDGlobal(world.getServer(), boundSpiritId)
                    .map(e -> (SpiritEntity) e);

            if (boundSpirit.isPresent() && boundSpirit.get().getJob().isPresent()) {
                if (boundSpirit.get().getJob().get() instanceof ManageMachineJob job) {
                    job.setStorageControllerPosition(new GlobalBlockPos(pos, world));
                    //write data into item nbt for client side usage
                    ItemNBTUtil.updateItemNBTFromEntity(stack, boundSpirit.get());

                    String blockName = world.getBlockState(pos).getBlock().getDescriptionId();
                    player.displayClientMessage(Component.translatable(
                            TranslationKeys.BOOK_OF_CALLING_GENERIC + ".message_set_storage_controller",
                            TextUtil.formatDemonName(boundSpirit.get().getName().getString()),
                            Component.translatable(blockName)), true);
                    return true;
                }
            } else {
                player.displayClientMessage(
                        Component.translatable(
                                TranslationKeys.BOOK_OF_CALLING_GENERIC + ".message_spirit_not_found"),
                        true);
            }
        }
        return false;
    }

    /**
     * Handle setting storage controller location
     * @param blockEntity the block entity
     * @param player the player
     * @param world the world
     * @param pos the position
     * @param stack the stack
     * @param face the face
     * @return true if handled, false otherwise
     */
    @Override
    public boolean handle(BlockEntity blockEntity, Player player, Level world, BlockPos pos, ItemStack stack, Direction face) {
        if (blockEntity instanceof IStorageController) {
            return this.setSpiritStorageController(player, world, pos, stack,
                    face);
        }
        return true;
    }
}
