/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.klikli_dev.occultism.common.ritual;

import com.klikli_dev.occultism.common.blockentity.GoldenSacrificialBowlBlockEntity;
import com.klikli_dev.occultism.common.entity.familiar.FamiliarEntity;
import com.klikli_dev.occultism.common.entity.spirit.demonicpartner.DemonicPartner;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.klikli_dev.occultism.registry.OccultismAdvancements;
import com.klikli_dev.occultism.registry.OccultismSounds;
import com.klikli_dev.occultism.util.EntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ResurrectFamiliarRitual extends SummonRitual {

    public ResurrectFamiliarRitual(RitualRecipe recipe) {
        super(recipe, true);
    }

    @Override
    public void finish(Level level, BlockPos goldenBowlPosition, GoldenSacrificialBowlBlockEntity blockEntity,
                       @Nullable ServerPlayer castingPlayer, ItemStack activationItem) {
        //manually call content of Ritual.finish(), because we cannot access it via super
        level.playSound(null, goldenBowlPosition, OccultismSounds.POOF.get(), SoundSource.BLOCKS, 0.7f,
                0.7f);
        if(castingPlayer != null){
            castingPlayer.displayClientMessage(Component.translatable(this.getFinishedMessage(castingPlayer)), true);
            OccultismAdvancements.RITUAL.get().trigger( castingPlayer, this);
        }

        var shard = activationItem.copy();
        activationItem.shrink(1); //remove original activation item.

        ((ServerLevel) level).sendParticles(ParticleTypes.LARGE_SMOKE, goldenBowlPosition.getX() + 0.5,
                goldenBowlPosition.getY() + 0.5, goldenBowlPosition.getZ() + 0.5, 1, 0, 0, 0, 0);

        //copied and modified from soul gem item
        if (shard.has(DataComponents.ENTITY_DATA)) {
            //whenever we have an entity stored we can do nothing but release it
            var entityData = shard.get(DataComponents.ENTITY_DATA).getUnsafe();
            var type = EntityUtil.entityTypeFromNbt(entityData);

            BlockPos spawnPos = goldenBowlPosition;

            //remove position from tag to allow the entity to spawn where it should be
            entityData.remove("Pos");

            //type.spawn uses the sub-tag EntityTag
            CompoundTag wrapper = new CompoundTag();
            wrapper.put("EntityTag", entityData);

            Entity entity = type.create(level);
            entity.load(entityData);
            entity.absMoveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, 0, 0);
            level.addFreshEntity(entity);

            if (entity instanceof FamiliarEntity familiar && castingPlayer != null)
                familiar.setFamiliarOwner(castingPlayer);

            if (entity instanceof DemonicPartner partner && castingPlayer != null)
                partner.setOwnerUUID(castingPlayer.getUUID());
        }
    }
}
