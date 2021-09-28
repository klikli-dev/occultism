/*
 * MIT License
 *
 * Copyright 2021 vemerion
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

package com.github.klikli_dev.occultism.common.entity;

import com.github.klikli_dev.occultism.common.item.tool.FamiliarRingItem;
import com.github.klikli_dev.occultism.registry.OccultismCapabilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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

    /**
     * Sets the owner entity of this familiar.
     * 
     * @param owner the new owner of this familiar
     */
    void setFamiliarOwner(LivingEntity owner);

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

    /***
     * This method is called every tick when this familiar is captured in a
     * {@link FamiliarRingItem}. <br>
     * <br>
     * BEWARE: Extra caution has to be taken when using instance variables from the
     * {@link IFamiliar#getEntity} in this method (such as {@link Entity#world}),
     * since their values are no longer updated when the familiar is inside the ring
     * and might be outdated. The same caution should be taken when implementing
     * {@link IFamiliar#getFamiliarEffects} or any other method that is called while
     * the familiar is inside the {@link FamiliarRingItem}.
     *
     * @param wearer The wearer of the curio
     */
    default void curioTick(LivingEntity wearer) {

    }

    /***
     * This method determines based on the familiar settings, if the familiar effect
     * should be enabled.
     *
     * @return True of the familiar effect is enabled, false otherwise.
     */
    default boolean isEffectEnabled() {
        LivingEntity owner = this.getFamiliarOwner();
        if (owner == null)
            return false;
        return owner.getCapability(OccultismCapabilities.FAMILIAR_SETTINGS)
                .map(cap -> cap.isFamiliarEnabled(this.getEntity().getType())).orElse(false);
    }

    /***
     * This method determines if the familiar can be upgraded by the blacksmith familiar.
     * 
     * @return True if the familiar can be upgraded, false otherwise.
     */
    default boolean canBlacksmithUpgrade() {
        return false;
    }
    
    /***
     * Upgrade the familiar by a blacksmith familiar.
     */
    default void blacksmithUpgrade() {

    }
}
