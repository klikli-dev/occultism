package com.klikli_dev.occultism.common.item.familiar;

import com.klikli_dev.occultism.client.gui.GuiHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public class FamiliarTabletItem extends Item {

    public FamiliarTabletItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NonNull InteractionResult use(Level level, @NonNull Player player, @NonNull InteractionHand hand) {
        if (level.isClientSide()) {
            GuiHelper.openFamiliarTabletGui(player);
            return InteractionResult.SUCCESS;
        }
        return super.use(level, player, hand);
    }
}
