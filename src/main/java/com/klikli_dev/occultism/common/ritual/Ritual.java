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

import com.google.common.base.Suppliers;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.blockentity.GoldenSacrificialBowlBlockEntity;
import com.klikli_dev.occultism.common.blockentity.SacrificialBowlBlockEntity;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.ConditionWrapperFactory;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.RitualRecipeConditionContext;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.RitualRecipeConditionFailureInformationVisitor;
import com.klikli_dev.occultism.registry.OccultismAdvancements;
import com.klikli_dev.occultism.registry.OccultismParticles;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import com.klikli_dev.occultism.registry.OccultismSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class Ritual {

    /**
     * The default range to look for sacrificial bowls with additional ingredients
     */
    public static final int SACRIFICIAL_BOWL_RANGE = 8;

    /**
     * The default range to listen for sacrifices.
     */
    public static final int SACRIFICE_DETECTION_RANGE = 8;
    public static final int SACRIFICE_DETECTION_RANGE_SQUARE = SACRIFICE_DETECTION_RANGE * SACRIFICE_DETECTION_RANGE;

    /**
     * The default range to listen for sacrifices.
     */
    public static final int ITEM_USE_DETECTION_RANGE = 16;
    public static final int ITEM_USE_DETECTION_RANGE_SQUARE = ITEM_USE_DETECTION_RANGE * ITEM_USE_DETECTION_RANGE;

    public RitualRecipe recipe;

    public ResourceLocation factoryId;
    Supplier<RecipeHolder<RitualRecipe>> recipeHolderSupplier;

    //region Getter / Setter

    /**
     * Constructs a ritual.
     */
    public Ritual(RitualRecipe recipe) {
        this.recipe = recipe;
    }

    /**
     * Removes all matching consumed already consumed ingredients from the remaining additional ingredients.
     *
     * @param additionalIngredients the total additional ingredients required.
     * @param consumedIngredients   the already consumed ingredients.
     * @return the remaining additional ingredients that still need to be consumed.
     */
    public static List<Ingredient> getRemainingAdditionalIngredients(List<Ingredient> additionalIngredients, List<ItemStack> consumedIngredients) {
        //copy the consumed ingredients to not modify the input
        List<ItemStack> consumedIngredientsCopy = new ArrayList<>(consumedIngredients);
        List<Ingredient> remainingAdditionalIngredients = new ArrayList<>();
        for (Ingredient ingredient : additionalIngredients) {
            Optional<ItemStack> matchedStack = consumedIngredientsCopy.stream().filter(ingredient::test).findFirst();
            if (matchedStack.isPresent()) {
                //if it is in the consumed ingredients, we do not need to add it to the remaining required ones
                //but we remove it from our consumed ingredients copy so each provided ingredient an only be simulated consumed once
                consumedIngredientsCopy.remove(matchedStack.get());
            } else {
                //if it is not already consumed, we add it to the remaining additional ingredients.
                remainingAdditionalIngredients.add(ingredient);
            }
        }
        return remainingAdditionalIngredients;
    }

    public ResourceLocation getFactoryID() {
        return this.factoryId;
    }

    public void setFactoryId(ResourceLocation factoryId) {
        this.factoryId = factoryId;
    }

    public RitualRecipe getRecipe() {
        return this.recipe;
    }

    public RecipeHolder<RitualRecipe> getRecipeHolder(Level level) {
        if (this.recipeHolderSupplier == null) {
            this.recipeHolderSupplier =
                    Suppliers.memoize(() -> level.getRecipeManager().getAllRecipesFor(OccultismRecipes.RITUAL_TYPE.get()).stream().filter(
                    r -> r.value() == this.getRecipe()
            ).findFirst().orElse(null));
        }
        return this.recipeHolderSupplier.get();
    }

    public String getRitualID(ServerPlayer player) {
        var holder = this.getRecipeHolder(player.level());
        if(holder == null)
            return "unknown";
        ResourceLocation recipeId = holder.id();
        String path = recipeId.getPath();
        if (path.contains("/"))
            path = path.substring(path.indexOf("/") + 1);

        return recipeId.getNamespace() + "." + path;
    }

    /**
     * @return the conditions message translation key for this ritual.
     */
    public String getConditionsMessage(ServerPlayer player) {
        return String.format("ritual.%s.conditions", this.getRitualID(player));
    }

    /**
     * @return the started message translation key for this ritual.
     */
    public String getStartedMessage(ServerPlayer player) {
        return String.format("ritual.%s.started", this.getRitualID(player));
    }

    /**
     * @return the interrupted message translation key for this ritual.
     */
    public String getInterruptedMessage(ServerPlayer player) {
        return String.format("ritual.%s.interrupted", this.getRitualID(player));
    }

    /**
     * @return the interrupted message translation key for this ritual.
     */
    public MutableComponent getConditionNotMetMessage(GoldenSacrificialBowlBlockEntity bowl, ServerPlayer player) {
        var visitor = new RitualRecipeConditionFailureInformationVisitor();
        var condition = ConditionWrapperFactory.wrap(this.recipe.getCondition());
        if(condition == null)
            return Component.literal("Unknown condition");

        return condition.accept(visitor, RitualRecipeConditionContext.of(bowl));
    }

    /**
     * @return the finished message translation key for this ritual.
     */
    public String getFinishedMessage(ServerPlayer player) {
        return String.format("ritual.%s.finished", this.getRitualID(player));
    }

    /**
     * Checks whether the ritual is valid. Validity is required to start and continue a ritual.
     *
     * @param level              the level.
     * @param goldenBowlPosition the position of the golden bowl.
     * @param blockEntity        the block entity controlling the ritual.
     * @param castingPlayer      the player starting the ritual.
     * @param activationItem     the item used to start the ritual.
     * @return true if a valid ritual is found.
     */
    public boolean isValid(Level level, BlockPos goldenBowlPosition, GoldenSacrificialBowlBlockEntity blockEntity,
                           @Nullable Player castingPlayer, ItemStack activationItem,
                           List<Ingredient> remainingAdditionalIngredients) {
        return this.recipe.getPentacle() != null && this.recipe.getActivationItem().test(activationItem) &&
                this.areAdditionalIngredientsFulfilled(level, goldenBowlPosition, remainingAdditionalIngredients) &&
                this.recipe.getPentacle().validate(level, goldenBowlPosition) != null;
    }

    /**
     * Called when starting the ritual.
     *
     * @param level              the level.
     * @param goldenBowlPosition the position of the golden bowl.
     * @param blockEntity        the block entity controlling the ritual.
     * @param castingPlayer      the player starting the ritual.
     * @param activationItem     the item used to start the ritual.
     */
    public boolean start(Level level, BlockPos goldenBowlPosition, GoldenSacrificialBowlBlockEntity blockEntity,
                      @Nullable ServerPlayer castingPlayer, ItemStack activationItem) {
        level.playSound(null, goldenBowlPosition, OccultismSounds.START_RITUAL.get(), SoundSource.BLOCKS, 1, 1);

        if(this.recipe.getCondition() != null){
            var context = RitualRecipeConditionContext.of(blockEntity);
            if(!this.recipe.getCondition().test(context)){
                if (castingPlayer != null)
                    castingPlayer.displayClientMessage(this.getConditionNotMetMessage(blockEntity, castingPlayer), false);
                return false;
            }
        }

        if (castingPlayer != null)
            castingPlayer.displayClientMessage(Component.translatable(this.getStartedMessage(castingPlayer)), true);

        return true;
    }

    /**
     * Called when finishing the ritual.
     *
     * @param level              the level.
     * @param goldenBowlPosition the position of the golden bowl.
     * @param blockEntity        the block entity controlling the ritual.
     * @param castingPlayer      the player starting the ritual.
     * @param activationItem     the item used to start the ritual.
     */
    public void finish(Level level, BlockPos goldenBowlPosition, GoldenSacrificialBowlBlockEntity blockEntity,
                       @Nullable ServerPlayer castingPlayer, ItemStack activationItem) {
        level.playSound(null, goldenBowlPosition, OccultismSounds.POOF.get(), SoundSource.BLOCKS, 0.7f,
                0.7f);

        if (castingPlayer != null){
            castingPlayer.displayClientMessage(Component.translatable(this.getFinishedMessage(castingPlayer)), true);
            OccultismAdvancements.RITUAL.get().trigger(castingPlayer, this);
        }
    }

    /**
     * Called when interrupting the ritual.
     *
     * @param level              the level.
     * @param goldenBowlPosition the position of the golden bowl.
     * @param blockEntity        the block entity controlling the ritual.
     * @param castingPlayer      the player starting the ritual.
     * @param activationItem     the item used to start the ritual.
     */
    public void interrupt(Level level, BlockPos goldenBowlPosition, GoldenSacrificialBowlBlockEntity blockEntity,
                          @Nullable ServerPlayer castingPlayer, ItemStack activationItem, boolean showMessage) {
        level.playSound(null, goldenBowlPosition, SoundEvents.CHICKEN_EGG, SoundSource.BLOCKS, 0.7f, 0.7f);
        if (castingPlayer != null && showMessage)
            castingPlayer.displayClientMessage(Component.translatable(this.getInterruptedMessage(castingPlayer)), true);
    }

    /**
     * Called when updating the ritual.
     *
     * @param level                          the level.
     * @param goldenBowlPosition             the position of the golden bowl.
     * @param blockEntity                    the block entity controlling the ritual.
     * @param castingPlayer                  the player starting the ritual.
     * @param activationItem                 the item used to start the ritual.
     * @param remainingAdditionalIngredients the additional ingredients not yet fulfilled.
     * @param time                           the current ritual time.
     */
    public void update(Level level, BlockPos goldenBowlPosition, GoldenSacrificialBowlBlockEntity blockEntity,
                       @Nullable Player castingPlayer, ItemStack activationItem,
                       List<Ingredient> remainingAdditionalIngredients, int time) {
    }

    /**
     * Called when updating the ritual.
     *
     * @param level              the level.
     * @param goldenBowlPosition the position of the golden bowl.
     * @param blockEntity        the block entity controlling the ritual.
     * @param castingPlayer      the player starting the ritual.
     * @param activationItem     the item used to start the ritual.
     * @param time               the current ritual time.
     */
    public void update(Level level, BlockPos goldenBowlPosition, GoldenSacrificialBowlBlockEntity blockEntity,
                       @Nullable Player castingPlayer, ItemStack activationItem, int time) {
        this.update(level, goldenBowlPosition, blockEntity, castingPlayer, activationItem, new ArrayList<Ingredient>(),
                time);
    }

    /**
     * Identifies the ritual by it's activation item, pentacle shape and ingredients.
     *
     * @param level              the level.
     * @param goldenBowlPosition the position of the golden bowl.
     * @param activationItem     the item used to start the ritual.
     * @return true if the ritual matches, false otherwise.
     */
    public boolean identify(Level level, BlockPos goldenBowlPosition, ItemStack activationItem) {
        return this.recipe.getPentacle() != null && this.recipe.getActivationItem().test(activationItem) &&
                this.areAdditionalIngredientsFulfilled(level, goldenBowlPosition, this.recipe.getIngredients()) &&
                this.recipe.getPentacle().validate(level, goldenBowlPosition) != null;
    }

    /**
     * Consumes additional ingredients from sacrificial bowls depending on the time passed.
     *
     * @param level                          the level.
     * @param goldenBowlPosition             the position of the golden bowl.
     * @param remainingAdditionalIngredients the remaining additional ingredients. Will be modified if something was
     *                                       consumed!
     * @param time                           the current ritual time.
     * @param consumedIngredients            the list of already consumed ingredients, newly consumd ingredients will be
     *                                       appended
     * @return true if ingredients were consumed successfully, or none needed to be consumed.
     */
    public boolean consumeAdditionalIngredients(Level level, BlockPos goldenBowlPosition,
                                                List<Ingredient> remainingAdditionalIngredients, int time,
                                                List<ItemStack> consumedIngredients) {
        if (remainingAdditionalIngredients.isEmpty())
            return true;

        int totalIngredientsToConsume = (int) Math.floor(time / this.recipe.getDurationPerIngredient());
        int ingredientsConsumed = consumedIngredients.size();

        int ingredientsToConsume = totalIngredientsToConsume - ingredientsConsumed;
        if (ingredientsToConsume == 0)
            return true;

        List<SacrificialBowlBlockEntity> sacrificialBowls = this.getSacrificialBowls(level, goldenBowlPosition);
        int consumed = 0;
        for (Iterator<Ingredient> it = remainingAdditionalIngredients.iterator();
             it.hasNext() && consumed < ingredientsToConsume; consumed++) {
            Ingredient ingredient = it.next();
            if (this.consumeAdditionalIngredient(level, goldenBowlPosition, sacrificialBowls, ingredient,
                    consumedIngredients)) {
                //remove from the remaining required ingredients
                it.remove();
            } else {
                //if ingredient not found, return false to enable interrupting the ritual.
                return false;
            }
        }
        return true;
    }

    /**
     * Consumes one ingredient from the first matching sacrificial bowl.
     *
     * @param level               the level.
     * @param goldenBowlPosition  the position of the golden bowl.
     * @param sacrificialBowls    the list of sacrificial bowls to check.
     * @param ingredient          the ingredient to consume.
     * @param consumedIngredients the list of already consumed ingredients, newly consumd ingredients will be appended
     * @return true if the ingredient was found and consumed.
     */
    public boolean consumeAdditionalIngredient(Level level, BlockPos goldenBowlPosition,
                                               List<SacrificialBowlBlockEntity> sacrificialBowls,
                                               Ingredient ingredient, List<ItemStack> consumedIngredients) {
        for (SacrificialBowlBlockEntity sacrificialBowl : sacrificialBowls) {
            //first simulate removal to check the ingredient
            ItemStack stack = sacrificialBowl.itemStackHandler.extractItem(0, 1, true);
            if (ingredient.test(stack)) {
                //now take for real
                ItemStack extracted = sacrificialBowl.itemStackHandler.extractItem(0, 1, false);
                consumedIngredients.add(extracted);
                //Show effect in level
                ((ServerLevel) level)
                        .sendParticles(ParticleTypes.LARGE_SMOKE, sacrificialBowl.getBlockPos().getX() + 0.5,
                                sacrificialBowl.getBlockPos().getY() + 1.5, sacrificialBowl.getBlockPos().getZ() + 0.5, 1,
                                0.0, 0.0, 0.0,
                                0.0);

                level.playSound(null, sacrificialBowl.getBlockPos(), OccultismSounds.POOF.get(), SoundSource.BLOCKS,
                        0.7f, 0.7f);
                return true;
            }
        }
        return false;
    }

    /**
     * Mark the next ingredient to consume.
     *
     * @param level               the level.
     * @param goldenBowlPosition  the position of the golden bowl.
     * @param ingredient          the ingredient to consume.
     * @param tier                the tier of central bowl
     */
    public void markNextIngredient(Level level,
                                   BlockPos goldenBowlPosition,
                                   Ingredient ingredient, int tier) {
        List<SacrificialBowlBlockEntity> sacrificialBowls = this.getSacrificialBowls(level, goldenBowlPosition);
        for (SacrificialBowlBlockEntity sacrificialBowl : sacrificialBowls) {
            //first simulate removal to check the ingredient
            ItemStack stack = sacrificialBowl.itemStackHandler.extractItem(0, 1, true);
            if (ingredient.test(stack)) {
                double gameTime = level.getGameTime() * 0.05;
                //Show effect in level
                double sin = Math.sin(gameTime) * 0.3;
                double cos = Math.cos(gameTime) * 0.3;
                Vec3 center = sacrificialBowl.getBlockPos().getCenter();
                ((ServerLevel) level)
                        .sendParticles(OccultismParticles.SPIRIT_FIRE_FLAME.get(), center.x + cos, center.y + 0.2 + cos, center.z + sin,
                                1, 0.0, 0.0, 0.0, 0.003);
                if (tier == 2){
                    double sin2 = Math.sin(gameTime + (Math.PI * 0.5)) * 0.3;
                    double cos2 = Math.cos(gameTime + (Math.PI * 0.5)) * 0.3;
                    ((ServerLevel) level)
                            .sendParticles(OccultismParticles.SPIRIT_FIRE_FLAME.get(), center.x + cos2, center.y + 0.2 + sin2, center.z + sin2,
                                    1, 0.0, 0.0, 0.0, 0.003);
                    ((ServerLevel) level)
                            .sendParticles(OccultismParticles.SPIRIT_FIRE_FLAME.get(), center.x - cos2, center.y + 0.2 + cos2, center.z - sin2,
                                    1, 0.0, 0.0, 0.0, 0.003);
                    ((ServerLevel) level)
                            .sendParticles(OccultismParticles.SPIRIT_FIRE_FLAME.get(), center.x - cos, center.y + 0.2 + sin, center.z - sin,
                                    1, 0.0, 0.0, 0.0, 0.003);
                }
                return;
            }
        }
    }

    /**
     * Compares the items on sacrificial bowls in range to the additional ingredients.
     *
     * @param level              the level.
     * @param goldenBowlPosition the position of the golden bowl.
     * @return true if additional ingredients are fulfilled.
     */
    public boolean areAdditionalIngredientsFulfilled(Level level, BlockPos goldenBowlPosition,
                                                     List<Ingredient> additionalIngredients) {
        return this.matchesAdditionalIngredients(additionalIngredients,
                this.getItemsOnSacrificialBowls(level, goldenBowlPosition));
    }

    /**
     * Checks if the given stack of items matches the additional ingredients for this ritual.
     *
     * @param items the item stacks to check
     * @return true if the additional ingredients are fulfilled.
     */
    public boolean matchesAdditionalIngredients(List<Ingredient> additionalIngredients, List<ItemStack> items) {

        //optional performance improvement to speed up matching at the cost of convenience
        if (Occultism.SERVER_CONFIG.rituals.enableRemainingIngredientCountMatching.get() &&
                additionalIngredients.size() != items.size())
            return false; //if we have different sizes, it cannot be right

        if (additionalIngredients.isEmpty())
            return true; //implies both are empty, so nothing to check.

        //create a copy to avoid modifying the original
        List<ItemStack> remainingItems = new ArrayList<>(items);

        for (Ingredient ingredient : additionalIngredients) {
            boolean isMatched = false;
            //go through the remaining items
            for (int i = 0; i < remainingItems.size(); i++) {
                ItemStack stack = remainingItems.get(i);
                //check if ingredient matches
                if (ingredient.test(stack)) {
                    isMatched = true;
                    remainingItems.remove(i); //prevent double dipping :)
                    break;
                }
            }
            if (!isMatched)
                return false;
        }

        //more items need to cause failure, otherwise we cannot properly identify the type of ritual.
        //return remainingItems.size() == 0;
        return true;
    }

    /**
     * Gets all items on sacrificial bowls in range of the golden bowl.
     *
     * @param level              the level.
     * @param goldenBowlPosition the ritual golden bowl.
     * @return a list of items on sacrificial bowls in range.
     */
    public List<ItemStack> getItemsOnSacrificialBowls(Level level, BlockPos goldenBowlPosition) {
        List<ItemStack> result = new ArrayList<>();

        List<SacrificialBowlBlockEntity> sacrificialBowls = this.getSacrificialBowls(level, goldenBowlPosition);
        for (SacrificialBowlBlockEntity sacrificialBowl : sacrificialBowls) {
            ItemStack stack = sacrificialBowl.itemStackHandler.getStackInSlot(0);
            if (!stack.isEmpty()) {
                result.add(stack);
            }
        }

        return result;
    }

    /**
     * Gets all sacrificial bowls in range of this ritual's golden bowl.
     *
     * @param level              the level.
     * @param goldenBowlPosition the block position of the golden bowl.
     * @return a list of sacrificial bowls.
     */
    public List<SacrificialBowlBlockEntity> getSacrificialBowls(Level level, BlockPos goldenBowlPosition) {

        var pentacle = this.recipe.getPentacle();
        var offset = pentacle.getOffset();
        var size = pentacle.getSize();

        //get offsets for the top and bottom layer
        var yBowlRangeTop = size.getY() - offset.getY() - 1;
        var yBowlRangeBottom = offset.getY();

        //add one to go beyond that layer by one
        yBowlRangeTop++;
        yBowlRangeBottom++;

        List<SacrificialBowlBlockEntity> result = new ArrayList<>();
        Iterable<BlockPos> blocksToCheck = BlockPos.betweenClosed(
                goldenBowlPosition.offset(-SACRIFICIAL_BOWL_RANGE, -yBowlRangeBottom, -SACRIFICIAL_BOWL_RANGE),
                goldenBowlPosition.offset(SACRIFICIAL_BOWL_RANGE, yBowlRangeTop, SACRIFICIAL_BOWL_RANGE));
        for (BlockPos blockToCheck : blocksToCheck) {
            BlockEntity blockEntity = level.getBlockEntity(blockToCheck);
            if (blockEntity instanceof SacrificialBowlBlockEntity &&
                    !(blockEntity instanceof GoldenSacrificialBowlBlockEntity)) {
                result.add((SacrificialBowlBlockEntity) blockEntity);
            }
        }
        return result;
    }

    /**
     * Checks if the given entity is a valid sacrifice.
     *
     * @param entity the entity to check against.
     * @return true if the entity is a valid sacrifice.
     */
    public boolean isValidSacrifice(LivingEntity entity) {
        return entity != null && this.recipe.requiresSacrifice() && entity.getType().is(this.recipe.getEntityToSacrifice());
    }

    /**
     * Checks if the given item use event is valid for this ritual.
     *
     * @param event the event to check.
     * @return true if the event represents a valid item use.
     */
    public boolean isValidItemUse(PlayerInteractEvent.RightClickItem event) {
        return this.recipe.requiresItemUse() && this.recipe.getItemToUse().test(event.getItemStack());
    }

    /**
     * Gets whether this ritual needs a sacrifice to progress.
     *
     * @return true if a sacrifice is required.
     */
    public boolean requiresSacrifice() {
        return this.recipe.requiresSacrifice();
    }

    /**
     * Gets whether this ritual needs an item use to progress.
     *
     * @return true if an item use is required.
     */
    public boolean require() {
        return this.recipe.requiresItemUse();
    }

    /**
     * Drops the given result stack near the golden bowl.
     *
     * @param level              the level.
     * @param goldenBowlPosition the position of the golden bowl.
     * @param blockEntity        the block entity controlling the ritual.
     * @param castingPlayer      the player starting the ritual.
     * @param stack              the result stack to drop.
     */
    public void dropResult(Level level, BlockPos goldenBowlPosition, GoldenSacrificialBowlBlockEntity blockEntity,
                           @Nullable Player castingPlayer, ItemStack stack) {
        double angle = level.random.nextDouble() * Math.PI * 2;
        ItemEntity entity = new ItemEntity(level, goldenBowlPosition.getX() + 0.5, goldenBowlPosition.getY() + 0.75,
                goldenBowlPosition.getZ() + 0.5, stack);
        entity.setDeltaMovement(Math.sin(angle) * 0.125, 0.25, Math.cos(angle) * 0.125);
        entity.setPickUpDelay(10);
        level.addFreshEntity(entity);
    }

}
