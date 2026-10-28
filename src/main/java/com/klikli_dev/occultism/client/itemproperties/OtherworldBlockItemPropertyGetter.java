/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 */

package com.klikli_dev.occultism.client.itemproperties;

import com.klikli_dev.occultism.integration.curios.CuriosUtil;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import com.klikli_dev.occultism.registry.OccultismEffects;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class OtherworldBlockItemPropertyGetter implements ConditionalItemModelProperty {
    public static final MapCodec<OtherworldBlockItemPropertyGetter> MAP_CODEC = MapCodec.unit(new OtherworldBlockItemPropertyGetter());

    @Override
    public boolean get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed, ItemDisplayContext displayContext) {
        if (stack.getOrDefault(OccultismDataComponents.IS_INVENTORY_ITEM, false))
            return true;
        if (entity instanceof Player player)
            return player.hasEffect(OccultismEffects.THIRD_EYE)
                    || CuriosUtil.hasGoggles(player)
                    || CuriosUtil.hasStaff(player);
        return false;
    }

    @Override
    public MapCodec<? extends ConditionalItemModelProperty> type() {
        return MAP_CODEC;
    }
}
