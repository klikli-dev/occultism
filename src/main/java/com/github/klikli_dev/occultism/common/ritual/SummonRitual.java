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

import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.common.tile.GoldenSacrificialBowlTileEntity;
import com.github.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.items.ItemHandlerHelper;

public class SummonRitual extends Ritual {

    private final boolean tame;

    public SummonRitual(RitualRecipe recipe, boolean tame){
        super(recipe);
        this.tame = tame;
    }

    /**
     * Consumes the activation item and copies over the NBT:
     *
     * @param activationItem the activation item.
     * @return return the bound book of calling with the nbt from the activation item.
     */
    public ItemStack getBookOfCallingBound(ItemStack activationItem) {
        ItemStack result = this.recipe.getResultItem().copy();
        if(result.getItem() == OccultismItems.JEI_DUMMY_NONE.get())
            return ItemStack.EMPTY;

        //should never happen, but apparently there is a scenario where it does (item cheated in with non jei?)
        //https://github.com/klikli-dev/occultism/issues/183
        if(activationItem.hasTag())
            result.setTag(activationItem.getTag().copy());
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
        ItemNBTUtil.setSpiritEntityUUID(bookOfCalling, spirit.getUUID());
        ItemHandlerHelper.giveItemToPlayer(player, bookOfCalling);
    }

    /**
     * Spawns the given entity and notifies nearby players.
     *
     * @param entity the entity to spawn
     * @param world  the world to spawn in.
     */
    public void spawnEntity(Entity entity, World world) {
        for (ServerPlayerEntity player : world.getEntitiesOfClass(ServerPlayerEntity.class,
                entity.getBoundingBox().inflate(50)))
            CriteriaTriggers.SUMMONED_ENTITY.trigger(player, entity);
        world.addFreshEntity(entity);
    }

    @Override
    public void finish(World world, BlockPos goldenBowlPosition, GoldenSacrificialBowlTileEntity tileEntity,
                       PlayerEntity castingPlayer, ItemStack activationItem) {
        super.finish(world, goldenBowlPosition, tileEntity, castingPlayer, activationItem);

        ItemStack copy = activationItem.copy();
        //prepare active book of calling
        ItemStack result = this.getBookOfCallingBound(activationItem);
        activationItem.shrink(1); //remove original activation item.

        ((ServerWorld) world).sendParticles(ParticleTypes.LARGE_SMOKE, goldenBowlPosition.getX() + 0.5,
                goldenBowlPosition.getY() + 0.5, goldenBowlPosition.getZ() + 0.5, 1, 0, 0, 0, 0);

        EntityType<?> entityType = this.recipe.getEntityToSummon();
        if(entityType != null){
            Entity entity = this.createSummonedEntity(entityType,  world, goldenBowlPosition, tileEntity, castingPlayer);
            if(entity instanceof LivingEntity){
                LivingEntity living = (LivingEntity) entity;
                this.prepareLivingEntityForSpawn(living, world, goldenBowlPosition, tileEntity, castingPlayer,
                        ItemNBTUtil.getBoundSpiritName(copy), this.tame);

                this.initSummoned(living, world, goldenBowlPosition, tileEntity, castingPlayer);

                //notify players nearby and spawn
                this.spawnEntity(living, world);

                //set up the book of calling
                if(result != ItemStack.EMPTY && living instanceof SpiritEntity)
                    this.finishBookOfCallingSetup(result, (SpiritEntity) living, castingPlayer);
            }
        }
    }

    public Entity createSummonedEntity(EntityType<?> entityType, World world, BlockPos goldenBowlPosition, GoldenSacrificialBowlTileEntity tileEntity,
                                       PlayerEntity castingPlayer){
        return entityType.create(world);
    }

    public void initSummoned(LivingEntity living, World world, BlockPos goldenBowlPosition, GoldenSacrificialBowlTileEntity tileEntity,
                             PlayerEntity castingPlayer){
        if(living instanceof SpiritEntity) {
            SpiritEntity spirit = (SpiritEntity) living;
            spirit.setSpiritMaxAge(this.recipe.getSpiritMaxAge());
        }
    }
}
