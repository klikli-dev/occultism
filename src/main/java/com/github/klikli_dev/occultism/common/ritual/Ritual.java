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

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.tile.GoldenSacrificialBowlTileEntity;
import com.github.klikli_dev.occultism.common.tile.SacrificialBowlTileEntity;
import com.github.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.github.klikli_dev.occultism.registry.OccultismAdvancements;
import com.github.klikli_dev.occultism.registry.OccultismSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public abstract class Ritual {

    //region Fields

    /**
     * The default range to look for sacrificial bowls with additional ingredients
     */
    public static final int SACRIFICIAL_BOWL_RANGE = 8;

    /**
     * The default range to listen for sacrifices.
     */
    public static final int SACRIFICE_DETECTION_RANGE = 8;

    /**
     * The default range to listen for sacrifices.
     */
    public static final int ITEM_USE_DETECTION_RANGE = 16;

    public RitualRecipe recipe;

    public ResourceLocation factoryId;

    //endregion Fields


    //region Initialization

    /**
     * Constructs a ritual.
     */
    public Ritual(RitualRecipe recipe) {
        this.recipe = recipe;
    }
    //endregion Initialization

    //region Getter / Setter

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

    public String getRitualID() {
        ResourceLocation recipeId = this.getRecipe().getId();
        String path = recipeId.getPath();
        if (path.contains("/"))
            path = path.substring(path.indexOf("/") + 1);

        return recipeId.getNamespace() + "." + path;
    }

    /**
     * @return the conditions message translation key for this ritual.
     */
    public String getConditionsMessage() {
        return String.format("ritual.%s.conditions", this.getRitualID());
    }

    /**
     * @return the started message translation key for this ritual.
     */
    public String getStartedMessage() {
        return String.format("ritual.%s.started", this.getRitualID());
    }

    /**
     * @return the interrupted message translation key for this ritual.
     */
    public String getInterruptedMessage() {
        return String.format("ritual.%s.interrupted", this.getRitualID());
    }
    //endregion Getter / Setter

    //region Methods

    /**
     * @return the finished message translation key for this ritual.
     */
    public String getFinishedMessage() {
        return String.format("ritual.%s.finished", this.getRitualID());
    }

    /**
     * Checks whether the ritual is valid.
     * Validity is required to start and continue a ritual.
     *
     * @param world              the world.
     * @param goldenBowlPosition the position of the golden bowl.
     * @param tileEntity         the tile entity controlling the ritual.
     * @param castingPlayer      the player starting the ritual.
     * @param activationItem     the item used to start the ritual.
     * @return true if a valid ritual is found.
     */
    public boolean isValid(World world, BlockPos goldenBowlPosition, GoldenSacrificialBowlTileEntity tileEntity,
                           PlayerEntity castingPlayer, ItemStack activationItem,
                           List<Ingredient> remainingAdditionalIngredients) {
        return this.recipe.getPentacle() != null && this.recipe.getActivationItem().test(activationItem) &&
                this.areAdditionalIngredientsFulfilled(world, goldenBowlPosition, remainingAdditionalIngredients) &&
                this.recipe.getPentacle().validate(world, goldenBowlPosition);
    }

    /**
     * Called when starting the ritual.
     *
     * @param world              the world.
     * @param goldenBowlPosition the position of the golden bowl.
     * @param tileEntity         the tile entity controlling the ritual.
     * @param castingPlayer      the player starting the ritual.
     * @param activationItem     the item used to start the ritual.
     */
    public void start(World world, BlockPos goldenBowlPosition, GoldenSacrificialBowlTileEntity tileEntity,
                      PlayerEntity castingPlayer, ItemStack activationItem) {
        world.playSound(null, goldenBowlPosition, OccultismSounds.START_RITUAL.get(), SoundCategory.BLOCKS, 1, 1);
        castingPlayer.displayClientMessage(new TranslationTextComponent(this.getStartedMessage()), true);
    }

    /**
     * Called when finishing the ritual.
     *
     * @param world              the world.
     * @param goldenBowlPosition the position of the golden bowl.
     * @param tileEntity         the tile entity controlling the ritual.
     * @param castingPlayer      the player starting the ritual.
     * @param activationItem     the item used to start the ritual.
     */
    public void finish(World world, BlockPos goldenBowlPosition, GoldenSacrificialBowlTileEntity tileEntity,
                       PlayerEntity castingPlayer, ItemStack activationItem) {
        world.playSound(null, goldenBowlPosition, OccultismSounds.POOF.get(), SoundCategory.BLOCKS, 0.7f,
                0.7f);
        castingPlayer.displayClientMessage(new TranslationTextComponent(this.getFinishedMessage()), true);
        OccultismAdvancements.RITUAL.trigger((ServerPlayerEntity) castingPlayer, this);
    }

    /**
     * Called when interrupting the ritual.
     *
     * @param world              the world.
     * @param goldenBowlPosition the position of the golden bowl.
     * @param tileEntity         the tile entity controlling the ritual.
     * @param castingPlayer      the player starting the ritual.
     * @param activationItem     the item used to start the ritual.
     */
    public void interrupt(World world, BlockPos goldenBowlPosition, GoldenSacrificialBowlTileEntity tileEntity,
                          PlayerEntity castingPlayer, ItemStack activationItem) {
        world.playSound(null, goldenBowlPosition, SoundEvents.CHICKEN_EGG, SoundCategory.BLOCKS, 0.7f, 0.7f);
        castingPlayer.displayClientMessage(new TranslationTextComponent(this.getInterruptedMessage()), true);
    }

    /**
     * Called when interrupting the ritual.
     *
     * @param world                          the world.
     * @param goldenBowlPosition             the position of the golden bowl.
     * @param tileEntity                     the tile entity controlling the ritual.
     * @param castingPlayer                  the player starting the ritual.
     * @param activationItem                 the item used to start the ritual.
     * @param remainingAdditionalIngredients the additional ingredients not yet fulfilled.
     * @param time                           the current ritual time.
     */
    public void update(World world, BlockPos goldenBowlPosition, GoldenSacrificialBowlTileEntity tileEntity,
                       PlayerEntity castingPlayer, ItemStack activationItem,
                       List<Ingredient> remainingAdditionalIngredients, int time) {
    }

    /**
     * Called when interrupting the ritual.
     *
     * @param world              the world.
     * @param goldenBowlPosition the position of the golden bowl.
     * @param tileEntity         the tile entity controlling the ritual.
     * @param castingPlayer      the player starting the ritual.
     * @param activationItem     the item used to start the ritual.
     * @param time               the current ritual time.
     */
    public void update(World world, BlockPos goldenBowlPosition, GoldenSacrificialBowlTileEntity tileEntity,
                       PlayerEntity castingPlayer, ItemStack activationItem, int time) {
        this.update(world, goldenBowlPosition, tileEntity, castingPlayer, activationItem, new ArrayList<Ingredient>(),
                time);
    }

    /**
     * Identifies the ritual by it's activation item and pentacle world shape.
     *
     * @param world              the world.
     * @param goldenBowlPosition the position of the golden bowl.
     * @param activationItem     the item used to start the ritual.
     * @return true if the ritual matches, false otherwise.
     */
    public boolean identify(World world, BlockPos goldenBowlPosition, ItemStack activationItem) {
        return this.recipe.getPentacle() != null && this.recipe.getActivationItem().test(activationItem) &&
                this.areAdditionalIngredientsFulfilled(world, goldenBowlPosition, this.recipe.getIngredients()) &&
                this.recipe.getPentacle().validate(world, goldenBowlPosition);
    }

    /**
     * Consumes additional ingredients from sacrificial bowls depending on the time passed.
     *
     * @param world                          the world.
     * @param goldenBowlPosition             the position of the golden bowl.
     * @param remainingAdditionalIngredients the remaining additional ingredients. Will be modified if something was consumed!
     * @param time                           the current ritual time.
     * @param consumedIngredients            the list of already consumed ingredients, newly consumd ingredients will be appended
     * @return true if ingredients were consumed successfully, or none needed to be consumed.
     */
    public boolean consumeAdditionalIngredients(World world, BlockPos goldenBowlPosition,
                                                List<Ingredient> remainingAdditionalIngredients, int time,
                                                List<ItemStack> consumedIngredients) {
        if (remainingAdditionalIngredients.isEmpty())
            return true;

        int totalIngredientsToConsume = (int) Math.floor(time / this.recipe.getDurationPerIngredient());
        int ingredientsConsumed = consumedIngredients.size();

        int ingredientsToConsume = totalIngredientsToConsume - ingredientsConsumed;
        if (ingredientsToConsume == 0)
            return true;

        List<SacrificialBowlTileEntity> sacrificialBowls = this.getSacrificialBowls(world, goldenBowlPosition);
        int consumed = 0;
        for (Iterator<Ingredient> it = remainingAdditionalIngredients.iterator();
             it.hasNext() && consumed < ingredientsToConsume; consumed++) {
            Ingredient ingredient = it.next();
            if (this.consumeAdditionalIngredient(world, goldenBowlPosition, sacrificialBowls, ingredient,
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
     * @param world               the world.
     * @param goldenBowlPosition  the position of the golden bowl.
     * @param sacrificialBowls    the list of sacrificial bowls to check.
     * @param ingredient          the ingredient to consume.
     * @param consumedIngredients the list of already consumed ingredients, newly consumd ingredients will be appended
     * @return true if the ingredient was found and consumed.
     */
    public boolean consumeAdditionalIngredient(World world, BlockPos goldenBowlPosition,
                                               List<SacrificialBowlTileEntity> sacrificialBowls,
                                               Ingredient ingredient, List<ItemStack> consumedIngredients) {
        for (SacrificialBowlTileEntity sacrificialBowl : sacrificialBowls) {
            //first simulate removal to check the ingredient
            if (sacrificialBowl.itemStackHandler.map(handler -> {
                ItemStack stack = handler.extractItem(0, 1, true);
                if (ingredient.test(stack)) {
                    //now take for real
                    ItemStack extracted = handler.extractItem(0, 1, false);
                    consumedIngredients.add(extracted);
                    //Show effect in world
                    ((ServerWorld) world)
                            .sendParticles(ParticleTypes.LARGE_SMOKE, sacrificialBowl.getBlockPos().getX() + 0.5,
                                    sacrificialBowl.getBlockPos().getY() + 1.5, sacrificialBowl.getBlockPos().getZ() + 0.5, 1,
                                    0.0, 0.0, 0.0,
                                    0.0);

                    world.playSound(null, sacrificialBowl.getBlockPos(), OccultismSounds.POOF.get(), SoundCategory.BLOCKS,
                            0.7f, 0.7f);
                    return true;
                }
                return false;
            }).orElse(false))
                return true;

        }
        return false;
    }

    /**
     * Compares the items on sacrificial bowls in range to the additional ingredients.
     *
     * @param world              the world.
     * @param goldenBowlPosition the position of the golden bowl.
     * @return true if additional ingredients are fulfilled.
     */
    public boolean areAdditionalIngredientsFulfilled(World world, BlockPos goldenBowlPosition,
                                                     List<Ingredient> additionalIngredients) {
        return this.matchesAdditionalIngredients(additionalIngredients,
                this.getItemsOnSacrificialBowls(world, goldenBowlPosition));
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
     * @param world              the world.
     * @param goldenBowlPosition the ritual golden bowl.
     * @return a list of items on sacrificial bowls in range.
     */
    public List<ItemStack> getItemsOnSacrificialBowls(World world, BlockPos goldenBowlPosition) {
        List<ItemStack> result = new ArrayList<>();

        List<SacrificialBowlTileEntity> sacrificialBowls = this.getSacrificialBowls(world, goldenBowlPosition);
        for (SacrificialBowlTileEntity sacrificialBowl : sacrificialBowls) {
            sacrificialBowl.itemStackHandler.ifPresent(handler -> {
                ItemStack stack = handler.getStackInSlot(0);
                if (!stack.isEmpty()) {
                    result.add(stack);
                }
            });
        }

        return result;
    }

    /**
     * Gets all sacrificial bowls in range of this ritual's golden bowl.
     *
     * @param world              the world.
     * @param goldenBowlPosition the block position of the golden bowl.
     * @return a list of sacrificial bowls.
     */
    public List<SacrificialBowlTileEntity> getSacrificialBowls(World world, BlockPos goldenBowlPosition) {
        List<SacrificialBowlTileEntity> result = new ArrayList<>();
        Iterable<BlockPos> blocksToCheck = BlockPos.betweenClosed(
                goldenBowlPosition.offset(-SACRIFICIAL_BOWL_RANGE, 0, -SACRIFICIAL_BOWL_RANGE),
                goldenBowlPosition.offset(SACRIFICIAL_BOWL_RANGE, 0, SACRIFICIAL_BOWL_RANGE));
        for (BlockPos blockToCheck : blocksToCheck) {
            TileEntity tileEntity = world.getBlockEntity(blockToCheck);
            if (tileEntity instanceof SacrificialBowlTileEntity &&
                    !(tileEntity instanceof GoldenSacrificialBowlTileEntity)) {
                result.add((SacrificialBowlTileEntity) tileEntity);
            }
        }
        return result;
    }

    /**
     * Prepares the given living entity for spawning by
     * - initializing it
     * - setting the taming player
     * - preparing position and rotation
     * - setting the custom name.
     *
     * @param livingEntity       the living entity to prepare.
     * @param world              the world to spawn in.
     * @param goldenBowlPosition the golden bowl position.
     * @param castingPlayer      the ritual casting player.
     * @param spiritName         the spirit name.
     */
    public void prepareLivingEntityForSpawn(LivingEntity livingEntity, World world, BlockPos goldenBowlPosition,
                                            GoldenSacrificialBowlTileEntity tileEntity, PlayerEntity castingPlayer, String spiritName) {
        this.prepareLivingEntityForSpawn(livingEntity, world, goldenBowlPosition, tileEntity, castingPlayer, spiritName, true);
    }

    /**
     * Prepares the given living entity for spawning by
     * - initializing it
     * - optionally setting the taming player
     * - preparing position and rotation
     * - setting the custom name.
     *
     * @param livingEntity       the living entity to prepare.
     * @param world              the world to spawn in.
     * @param goldenBowlPosition the golden bowl position.
     * @param castingPlayer      the ritual casting player.
     * @param spiritName         the spirit name.
     * @param setTamed           true to tame the spirit
     */
    public void prepareLivingEntityForSpawn(LivingEntity livingEntity, World world, BlockPos goldenBowlPosition, GoldenSacrificialBowlTileEntity tileEntity,
                                            PlayerEntity castingPlayer, String spiritName, boolean setTamed) {
        if (setTamed && livingEntity instanceof TameableEntity) {

            ((TameableEntity) livingEntity).tame(castingPlayer);
        }
        livingEntity.absMoveTo(goldenBowlPosition.getX(), goldenBowlPosition.getY(), goldenBowlPosition.getZ(),
                world.random.nextInt(360), 0);
        if (spiritName.length() > 0)
            livingEntity.setCustomName(new StringTextComponent(spiritName));
        if (livingEntity instanceof MobEntity)
            ((MobEntity) livingEntity).finalizeSpawn((ServerWorld) world, world.getCurrentDifficultyAt(goldenBowlPosition),
                    SpawnReason.MOB_SUMMONED, null,
                    null);
    }

    /**
     * Checks if the given entity is a valid sacrifice.
     *
     * @param entity the entity to check against.
     * @return true if the entity is a valid sacrifice.
     */
    public boolean isValidSacrifice(LivingEntity entity) {
        return this.recipe.getEntityToSacrifice().contains(entity.getType());
    }

    /**
     * Checks if the given item use event is valid for this ritual.
     *
     * @param event the event to check.
     * @return true if the event represents a valid item use.
     */
    public boolean isValidItemUse(PlayerInteractEvent.RightClickItem event) {
        return this.recipe.getItemToUse().test(event.getItemStack());
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
     * @param world              the world.
     * @param goldenBowlPosition the position of the golden bowl.
     * @param tileEntity         the tile entity controlling the ritual.
     * @param castingPlayer      the player starting the ritual.
     * @param stack              the result stack to drop.
     */
    public void dropResult(World world, BlockPos goldenBowlPosition, GoldenSacrificialBowlTileEntity tileEntity,
                           PlayerEntity castingPlayer, ItemStack stack) {
        double angle = world.random.nextDouble() * Math.PI * 2;
        ItemEntity entity = new ItemEntity(world, goldenBowlPosition.getX() + 0.5, goldenBowlPosition.getY() + 0.75,
                goldenBowlPosition.getZ() + 0.5, stack);
        entity.setDeltaMovement(Math.sin(angle) * 0.125, 0.25, Math.cos(angle) * 0.125);
        entity.setPickUpDelay(10);
        world.addFreshEntity(entity);
    }
    //endregion Methods
}
