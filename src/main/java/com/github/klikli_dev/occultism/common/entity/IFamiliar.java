package com.github.klikli_dev.occultism.common.entity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.klikli_dev.occultism.common.item.tool.FamiliarRingItem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;

/***
 * An interface representing a familiar entity. See {@link OtherworldBirdEntity}
 * for an example of an entity that implements this interface. Used by
 * {@link FamiliarRingItem}.
 *
 */
public interface IFamiliar {

    /***
     * Gets the entity that is the owner of this familiar, i.e. the player that
     * summoned the familiar via a ritual.
     * 
     * @return The owner of this familiar
     */
    @Nullable
    LivingEntity getFamiliarOwner();

    /***
     * Gets the actual familiar as an entity.
     * 
     * @return The familiar.
     */
    @Nonnull
    Entity getEntity();

    /***
     * Gets fresh instances of effects that this familiar should apply to the owner.
     * 
     * @return The effects to apply to the owner.
     */
    @Nonnull
    Iterable<EffectInstance> getFamiliarEffects();
}
