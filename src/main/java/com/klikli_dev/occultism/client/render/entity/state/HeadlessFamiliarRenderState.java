/*
 * MIT License
 *
 * Copyright 2026 klikli-dev
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

package com.klikli_dev.occultism.client.render.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

public class HeadlessFamiliarRenderState extends LivingEntityRenderState {
    public boolean isSitting;
    public boolean isHeadlessDead;
    public boolean isPartying;
    public boolean hasBlacksmithUpgrade;
    public boolean isHairy;
    public boolean rebuiltRightLeg;
    public boolean rebuiltLeftLeg;
    public boolean rebuiltBody;
    public boolean rebuiltRightArm;
    public boolean rebuiltLeftArm;
    public boolean rebuiltHead;
    public boolean hasHead;
    public boolean hasGlasses;
    public EntityType<?> headType;
    public ItemStack weaponItem = ItemStack.EMPTY;
    public float yHeadRot;
    public float limbSwing;
    public float limbSwingAmount;
    public float attackTime;
}
