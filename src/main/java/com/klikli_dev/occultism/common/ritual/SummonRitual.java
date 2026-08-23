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
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.common.item.spirit.BookOfCallingItem;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import com.klikli_dev.occultism.util.ItemTransferUtil;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;

import java.util.stream.StreamSupport;

public class SummonRitual extends Ritual {

    private final boolean tame;

    public SummonRitual(RitualRecipe recipe, boolean tame) {
        super(recipe);
        this.tame = tame;
    }

    /**
     * Check if the result is a book of calling or just a placeholder.
     *
     * @return return the book of calling or an empty stack.
     */
    public ItemStack getBookOfCallingBound() {
        ItemStack result = this.recipe.getResult().copy();
        if (result.getItem() instanceof BookOfCallingItem) {
            //initially to fix https://github.com/klikli-dev/occultism/issues/183
            //currently disabled because Minecraft 26.1 retained the texture and name of the book of binding
            //there's no relevant component to keep, and it's confusing players
            //if (!activationItem.isComponentsPatchEmpty()) {
            //    result.applyComponents(activationItem.getComponents());
            //}
            return result;
        } else {
            return ItemStack.EMPTY;
        }
    }

    /**
     * Links the spirit to the book of calling, and gives the book to the player.
     *
     * @param bookOfCalling the book of calling to link to the spirit and give to the player.
     * @param spirit        the spirit to link to the book.
     * @param player        the player to give the book to.
     * @return ItemStack: configured book of calling. Empty if the player is nonnull (give directly to the player)
     */
    public ItemStack finishBookOfCallingSetup(ItemStack bookOfCalling, SpiritEntity spirit, @Nullable Player player) {
        ItemNBTUtil.setSpiritEntityUUID(bookOfCalling, spirit.getUUID());
        ItemNBTUtil.setBoundSpiritName(bookOfCalling, spirit.getName().getString());

        if (player != null) {
            ItemTransferUtil.giveItemToPlayer(player, bookOfCalling);
            return ItemStack.EMPTY;
        }
        return bookOfCalling;
    }

    /**
     * Spawns the given entity and notifies nearby players.
     *
     * @param entity the entity to spawn
     * @param level  the level to spawn in.
     */
    public void spawnEntity(Entity entity, Level level) {
        for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class,
                entity.getBoundingBox().inflate(50)))
            CriteriaTriggers.SUMMONED_ENTITY.trigger(player, entity);
        level.addFreshEntity(entity);
    }

    /**
     * Needs to be called after finalizeSpawn to avoid finalizeSpawn overwriting things like RabbitType with random init values.
     */
    public void applyEntityNbt(Entity entity) {
        if (this.recipe.getEntityNbt() != null) {
            var output = TagValueOutput.createWithoutContext(ProblemReporter.DISCARDING);
            entity.saveWithoutId(output);
            var tag = output.buildResult();
            tag.merge(this.recipe.getEntityNbt());
            entity.load(TagValueInput.create(ProblemReporter.DISCARDING, entity.registryAccess(), tag));
        }
    }

    @Override
    public void finish(Level level, BlockPos goldenBowlPosition, GoldenSacrificialBowlBlockEntity blockEntity,
                       @Nullable ServerPlayer castingPlayer, ItemStack activationItem) {
        super.finish(level, goldenBowlPosition, blockEntity, castingPlayer, activationItem);

        ItemStack copy = activationItem.copy();
        //prepare active book of calling
        ItemStack result = this.getBookOfCallingBound();
        activationItem.shrink(1); //remove original activation item.

        ((ServerLevel) level).sendParticles(ParticleTypes.LARGE_SMOKE, goldenBowlPosition.getX() + 0.5,
                goldenBowlPosition.getY() + 0.5, goldenBowlPosition.getZ() + 0.5, 1, 0, 0, 0, 0);

        EntityType<?> entityType = this.getEntityToSummon(level);
        if (entityType != null) {
            Entity entity = this.createSummonedEntity(entityType, level, goldenBowlPosition, blockEntity, castingPlayer);
            if (entity instanceof LivingEntity living) {
                this.prepareLivingEntityForSpawn(living, level, goldenBowlPosition, blockEntity, castingPlayer,
                        ItemNBTUtil.getBoundSpiritName(copy), this.tame);

                this.applyEntityNbt(living);

                this.initSummoned(living, level, goldenBowlPosition, blockEntity, castingPlayer);

                //notify players nearby and spawn
                this.spawnEntity(living, level);

                //set up the book of calling
                if (result != ItemStack.EMPTY && living instanceof SpiritEntity)
                    result = this.finishBookOfCallingSetup(result, (SpiritEntity) living, castingPlayer);
            }
        }
        this.dropResultAndFlame(level, goldenBowlPosition, blockEntity, castingPlayer, result);
    }

    protected EntityType<?> getEntityToSummon(Level level) {
        if (this.recipe.getEntityTagToSummon() != null) {
            var options = StreamSupport.stream(BuiltInRegistries.ENTITY_TYPE.getTagOrEmpty(this.recipe.getEntityTagToSummon()).spliterator(), false).toList();

            if (!options.isEmpty()) {
                int index = level.getRandom().nextInt(options.size());
                return options.get(index).value();
            }
        }

        return this.recipe.getEntityToSummon();
    }

    public Entity createSummonedEntity(EntityType<?> entityType, Level level, BlockPos goldenBowlPosition, GoldenSacrificialBowlBlockEntity blockEntity,
                                       @Nullable Player castingPlayer) {
        return entityType.create(level, EntitySpawnReason.MOB_SUMMONED);
    }

    public void initSummoned(LivingEntity living, Level level, BlockPos goldenBowlPosition, GoldenSacrificialBowlBlockEntity blockEntity, @Nullable Player castingPlayer) {
        if (living instanceof SpiritEntity spirit) {
            spirit.setSpiritMaxAge(this.recipe.getSpiritMaxAge());
        }
    }

    /**
     * Prepares the given living entity for spawning by - initializing it - optionally setting the taming player -
     * preparing position and rotation - setting the custom name.
     *
     * @param livingEntity       the living entity to prepare.
     * @param level              the level to spawn in.
     * @param goldenBowlPosition the golden bowl position.
     * @param castingPlayer      the ritual casting player.
     * @param spiritName         the spirit name.
     * @param setTamed           true to tame the spirit
     */
    public void prepareLivingEntityForSpawn(LivingEntity livingEntity, Level level, BlockPos goldenBowlPosition, GoldenSacrificialBowlBlockEntity blockEntity,
                                            @Nullable Player castingPlayer, String spiritName, boolean setTamed) {
        if (setTamed && livingEntity instanceof TamableAnimal tamableAnimal && castingPlayer != null) {
            tamableAnimal.tame(castingPlayer);
        }
        if (setTamed && livingEntity instanceof FamiliarEntity familiar && castingPlayer != null) {
            familiar.setFamiliarOwner(castingPlayer);
        }
        if (level.getBlockState(goldenBowlPosition).getBlock().equals(OccultismBlocks.ELDRITCH_CHALICE.get())
                || level.getBlockState(goldenBowlPosition).getBlock().equals(OccultismBlocks.CELESTIAL_CHALICE.get())) {
            livingEntity.snapTo(goldenBowlPosition.getX() + 0.5, goldenBowlPosition.getY() + 1, goldenBowlPosition.getZ() + 0.5,
                    level.getRandom().nextInt(360), 0);
        } else {
            livingEntity.snapTo(goldenBowlPosition.getX() + 0.5, goldenBowlPosition.getY() + 0.5, goldenBowlPosition.getZ() + 0.5,
                    level.getRandom().nextInt(360), 0);
        }
        if (!spiritName.isEmpty())
            livingEntity.setCustomName(Component.literal(spiritName));
        if (livingEntity instanceof Mob mob) {
            EventHooks.finalizeMobSpawn(mob, (ServerLevelAccessor) level, ((ServerLevel) level).getCurrentDifficultyAt(goldenBowlPosition), EntitySpawnReason.MOB_SUMMONED, null);
        }
    }
}

