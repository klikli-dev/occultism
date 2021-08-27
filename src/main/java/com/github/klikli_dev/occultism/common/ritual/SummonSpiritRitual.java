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

package com.github.klikli_dev.occultism.common.ritual;

import java.util.function.Supplier;

import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.common.ritual.pentacle.Pentacle;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

public class SummonSpiritRitual extends Ritual {

    //region Fields
    public Item bookOfCalling;
    //endregion Fields

    //region Initialization

    public SummonSpiritRitual(Item bookOfCalling, Supplier<Pentacle> pentacle, Ingredient startingItem,
                              int totalTime) {
        super(pentacle, startingItem, totalTime);
        this.bookOfCalling = bookOfCalling;
    }

    public SummonSpiritRitual(Item bookOfCalling, Supplier<Pentacle> pentacle, Ingredient startingItem,
                              String additionalIngredientsRecipeName,
                              int totalTime) {
        super(pentacle, startingItem, additionalIngredientsRecipeName, totalTime);
        this.bookOfCalling = bookOfCalling;
    }

    public SummonSpiritRitual(Item bookOfCalling, Supplier<Pentacle> pentacle, Ingredient startingItem,
                              String additionalIngredientsRecipeName, int sacrificialBowlRange, int totalTime) {
        super(pentacle, startingItem, additionalIngredientsRecipeName, sacrificialBowlRange,
                totalTime);
        this.bookOfCalling = bookOfCalling;
    }

    //endregion Initialization

    //region Overrides
    //endregion Overrides

    //region Methods

    /**
     * Consumes the activation item and copies over the NBT:
     *
     * @param activationItem the activation item.
     * @return return the bound book of calling with the nbt from the activation item.
     */
    public ItemStack getBookOfCallingBound(ItemStack activationItem) {
        ItemStack result = new ItemStack(this.bookOfCalling);
        //should never happen, but apparently there is a scenario where it does (item cheated in with non jei?)
        //https://github.com/klikli-dev/occultism/issues/183
        if(activationItem.hasTag())
            result.setTag(activationItem.getTag().copy());
        activationItem.shrink(1); //remove original activation item.
        return result;
    }

    /**
     * Links the spirit to the book of calling, and gives the book to the player.
     *
     * @param bookOfCalling the book of calling to link to the spirit and give to the player.
     * @param spirit        the spirit to link to the book.
     * @param player        the player to give the book to.
     */
    public void finishBookOfCallingSetup(ItemStack bookOfCalling, SpiritEntity spirit, PlayerEntity player) {
        ItemNBTUtil.setSpiritEntityUUID(bookOfCalling, spirit.getUniqueID());
        ItemHandlerHelper.giveItemToPlayer(player, bookOfCalling);
    }

    /**
     * Spawns the given entity and notifies nearby players.
     *
     * @param entity the entity to spawn
     * @param world  the world to spawn in.
     */
    public void spawnEntity(Entity entity, World world) {
        for (ServerPlayerEntity player : world.getEntitiesWithinAABB(ServerPlayerEntity.class,
                entity.getBoundingBox().grow(50)))
            CriteriaTriggers.SUMMONED_ENTITY.trigger(player, entity);
        world.addEntity(entity);
    }
    //endregion Methods
}
