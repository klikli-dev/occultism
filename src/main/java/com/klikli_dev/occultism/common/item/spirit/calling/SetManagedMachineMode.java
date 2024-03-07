package com.klikli_dev.occultism.common.item.spirit.calling;

import com.klikli_dev.occultism.TranslationKeys;
import com.klikli_dev.occultism.api.common.data.MachineReference;
import com.klikli_dev.occultism.common.entity.job.ManageMachineJob;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.util.BlockEntityUtil;
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
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import java.util.Optional;
import java.util.UUID;

public class SetManagedMachineMode extends ItemMode {
    public SetManagedMachineMode() {
        super( "set_managed_machine");
    }

    public boolean setSpiritManagedMachine(Player player, Level world, BlockPos pos, ItemStack stack,
                                           Direction face) {
        UUID boundSpiritId = ItemNBTUtil.getSpiritEntityUUID(stack);
        if (boundSpiritId != null) {
            Optional<SpiritEntity> boundSpirit = EntityUtil.getEntityByUuiDGlobal(world.getServer(), boundSpiritId)
                    .map(e -> (SpiritEntity) e);
            BlockEntity managedMachineBlockEntity = world.getBlockEntity(pos);
            if (boundSpirit.isPresent() && managedMachineBlockEntity != null) {

                if (boundSpirit.get().getJob().isPresent() &&
                        boundSpirit.get().getJob().get() instanceof ManageMachineJob manageMachine) {

                    //Old code:
                    //  //get the existing extract reference here, or if null reuse insert
                    //  var oldExtractBlockEntity = manageMachine.getExtractBlockEntity();
                    //now we just use the new machine as extract target, because otherwise we would insert into the new machine, but extract from the old
                    MachineReference newReference = MachineReference.from(managedMachineBlockEntity, managedMachineBlockEntity);
                    if (manageMachine.getManagedMachine() == null ||
                            !manageMachine.getManagedMachine().insertGlobalPos.equals(newReference.insertGlobalPos)) {
                        //if we are setting a completely new machine, just overwrite the reference.
                        manageMachine.setManagedMachine(newReference);
                    } else {
                        //otherwise just update the registry name in case the block type was switched out
                        manageMachine.getManagedMachine().insertRegistryName = newReference.insertRegistryName;
                    }

                    //write data into item nbt for client side usage
                    ItemNBTUtil.updateItemNBTFromEntity(stack, boundSpirit.get());

                    player.displayClientMessage(
                            Component.translatable(
                                    TranslationKeys.BOOK_OF_CALLING_GENERIC + ".message_set_managed_machine",
                                    TextUtil.formatDemonName(boundSpirit.get().getName().getString())), true);
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

    @Override
    public boolean handle(BlockEntity blockEntity, Player player, Level world, BlockPos pos, ItemStack stack, Direction face) {
        if (blockEntity != null && BlockEntityUtil.hasCapabilityOnAnySide(blockEntity,
                ForgeCapabilities.ITEM_HANDLER)) {
            this.setSpiritManagedMachine(player, world, pos, stack, face);
            return true;
        }
        return true;
    }
}
