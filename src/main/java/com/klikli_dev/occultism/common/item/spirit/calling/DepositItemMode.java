package com.klikli_dev.occultism.common.item.spirit.calling;

import com.klikli_dev.occultism.TranslationKeys;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.util.EntityUtil;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities;

import java.util.Optional;
import java.util.UUID;

public class DepositItemMode extends ItemMode {

    public DepositItemMode() {
        super("set_deposit");
    }

    public boolean setSpiritDepositLocation(Player player, Level world, BlockPos pos, ItemStack stack,
                                            Direction face) {
        UUID boundSpiritId = ItemNBTUtil.getSpiritEntityUUID(stack);
        if (boundSpiritId != null) {
            Optional<SpiritEntity> boundSpirit = EntityUtil.getEntityByUuiDGlobal(world.getServer(), boundSpiritId)
                    .map(e -> (SpiritEntity) e);

            if (boundSpirit.isPresent()) {
                //update properties on entity
                boundSpirit.get().setDepositPosition(pos);
                boundSpirit.get().setDepositFacing(face);
                //also update control item with latest data
                ItemNBTUtil.updateItemNBTFromEntity(stack, boundSpirit.get());

                String blockName = world.getBlockState(pos).getBlock().getDescriptionId();
                player.displayClientMessage(
                        Component.translatable(TranslationKeys.BOOK_OF_CALLING_GENERIC + ".message_set_deposit",
                                TextUtil.formatDemonName(boundSpirit.get().getName().getString()),
                                Component.translatable(blockName), face.getSerializedName()), true);
                return true;
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
     * Handle a deposit action
     *
     * @param blockEntity the block entity
     * @param player      the player
     * @param world       the world
     * @param pos         the position
     * @param stack       the stack
     * @param face        the face
     * @return true if the action was handled
     */
    @Override
    public boolean handle(BlockEntity blockEntity, Player player, Level world, BlockPos pos, ItemStack stack, Direction face) {
        if (blockEntity != null &&
                world.getCapability(Capabilities.ItemHandler.BLOCK, blockEntity.getBlockPos(), blockEntity.getBlockState(), blockEntity, face) != null) {
            return this.setSpiritDepositLocation(player, world, pos, stack, face);
        }
        return true;
    }
}
