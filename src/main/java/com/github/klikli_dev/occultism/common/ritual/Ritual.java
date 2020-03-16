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
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.common.ritual.pentacle.Pentacle;
import com.github.klikli_dev.occultism.common.tile.GoldenSacrificialBowlTileEntity;
import com.github.klikli_dev.occultism.common.tile.SacrificialBowlTileEntity;
import com.github.klikli_dev.occultism.registry.OccultismSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeManager;
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
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class Ritual extends ForgeRegistryEntry<Ritual> {

    //region Fields

    /**
     * The default range to look for sacrificial bowls with additional ingredients
     */
    public static final int SACRIFICIAL_BOWL_RANGE = 8;

    /**
     * The default range to listen for sacrifices.
     */
    public static final int SACRIFICE_DETECTION_RANGE = 3;

    /**
     * The default range to listen for sacrifices.
     */
    public static final int ITEM_USE_DETECTION_RANGE = 16;

    /**
     * The pentacle required to perform this ritual.
     */
    public final Pentacle pentacle;

    /**
     * The item required to start the ritual.
     */
    public final Ingredient startingItem;

    /**
     * The SpiritTrade recipe id representing the additional ingredients for this ritual.
     */
    public ResourceLocation additionalIngredientsRecipeId;
    /**
     * The predicate to check sacrifices against.
     */
    public Predicate<LivingEntity> sacrificePredicate;
    /**
     * The predicate to check sacrifices against.
     */
    public Predicate<PlayerInteractEvent.RightClickItem> itemUsePredicate;
    /**
     * The range to look for sacrificial bowls for additional ingredients.
     */
    public int sacrificialBowlRange;
    /**
     * The total time in seconds it takes to finish the ritual.
     */
    public int totalSeconds;
    /**
     * The ritual time to pass per ingredient.
     */
    public float timePerIngredient;
    /**
     * The additional ingredients required to finish the ritual.
     * These ingredients need to be placed within the ritual area.
     */
    protected List<Ingredient> additionalIngredients;
    protected boolean additionalIngredientsLoaded;
    //endregion Fields


    //region Initialization

    /**
     * Constructs a ritual.
     *
     * @param pentacle     the pentacle for the ritual.
     * @param startingItem the item required to start the ritual.
     */
    public Ritual(Pentacle pentacle, Ingredient startingItem) {
        this(pentacle, startingItem, 10);
    }


    /**
     * Constructs a ritual.
     *
     * @param pentacle     the pentacle for the ritual.
     * @param startingItem the item required to start the ritual.
     * @param totalSeconds the total time it takes to finish the ritual.
     */
    public Ritual(Pentacle pentacle, Ingredient startingItem, int totalSeconds) {
        this(pentacle, startingItem, (String) null, totalSeconds);
    }

    /**
     * Constructs a ritual.
     *
     * @param pentacle                        the pentacle for the ritual.
     * @param startingItem                    the item required to start the ritual.
     * @param additionalIngredientsRecipeName the name of the additional ingredients recipe id. Will be prefixed with MODID:ritual_ingredients/
     * @param totalSeconds                    the total time it takes to finish the ritual.
     */
    public Ritual(Pentacle pentacle, Ingredient startingItem, String additionalIngredientsRecipeName,
                  int totalSeconds) {
        this(pentacle, startingItem, additionalIngredientsRecipeName, SACRIFICIAL_BOWL_RANGE, totalSeconds);
    }

    /**
     * Constructs a ritual.
     *
     * @param pentacle                        the pentacle for the ritual.
     * @param startingItem                    the item required to start the ritual.
     * @param additionalIngredientsRecipeName the name of the additional ingredients recipe id. Will be prefixed with MODID:ritual_ingredients/
     * @param sacrificialBowlRange            the range to look for sacrificial bowls for additional ingredients.
     * @param totalSeconds                    the total time it takes to finish the ritual.
     */
    public Ritual(Pentacle pentacle, Ingredient startingItem, String additionalIngredientsRecipeName,
                  int sacrificialBowlRange, int totalSeconds) {
        this.pentacle = pentacle;
        this.startingItem = startingItem;
        if (additionalIngredientsRecipeName != null)
            this.additionalIngredientsRecipeId = new ResourceLocation(Occultism.MODID,
                    "ritual_ingredients/" + additionalIngredientsRecipeName);
        this.additionalIngredients = new ArrayList<>();
        this.sacrificialBowlRange = sacrificialBowlRange;
        this.totalSeconds = totalSeconds;
        this.timePerIngredient = this.totalSeconds;
    }
    //endregion Initialization

    //region Getter / Setter

    /**
     * @return the conditions message translation key for this ritual.
     */
    public String getConditionsMessage() {
        return String.format("ritual.%s.conditions", this.getRegistryName().toString().replace(":", "."));
    }

    /**
     * @return the started message translation key for this ritual.
     */
    public String getStartedMessage() {
        return String.format("ritual.%s.started", this.getRegistryName().toString().replace(":", "."));
    }

    /**
     * @return the interrupted message translation key for this ritual.
     */
    public String getInterruptedMessage() {
        return String.format("ritual.%s.interrupted", this.getRegistryName().toString().replace(":", "."));
    }

    /**
     * @return the finished message translation key for this ritual.
     */
    public String getFinishedMessage() {
        return String.format("ritual.%s.finished", this.getRegistryName().toString().replace(":", "."));
    }
    //endregion Getter / Setter

    //region Methods

    /**
     * The additional ingredients required to finish the ritual.
     * These ingredients need to be placed within the ritual area.
     */
    public List<Ingredient> getAdditionalIngredients(World world) {
        if (!this.additionalIngredientsLoaded) {
            //this is lazily loading the ingredients.
            this.registerAdditionalIngredients(world.getRecipeManager());
        }
        return this.additionalIngredients;
    }

    /**
     * Gets the additional ingredients from the recipe registry.
     */
    public void registerAdditionalIngredients(RecipeManager recipeManager) {
        this.additionalIngredientsLoaded = true;
        if (this.additionalIngredientsRecipeId != null && recipeManager != null) {
            Optional<? extends IRecipe<?>> recipe = recipeManager.getRecipe(this.additionalIngredientsRecipeId);
            if (recipe.isPresent()) {
                this.additionalIngredients = recipe.get().getIngredients();
                //if we have multiple ingredients, make sure
                this.timePerIngredient = this.totalSeconds / (float) (this.additionalIngredients.size() + 1);
            }
            else {
                Occultism.LOGGER.warn("Additional Ingredients Recipe {} not found for Ritual {}",
                        this.additionalIngredientsRecipeId, this.getRegistryName());
            }
        }
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
     * @return
     */
    public boolean isValid(World world, BlockPos goldenBowlPosition, GoldenSacrificialBowlTileEntity tileEntity,
                           PlayerEntity castingPlayer, ItemStack activationItem,
                           List<Ingredient> remainingAdditionalIngredients) {
        return this.startingItem.test(activationItem) &&
               this.areAdditionalIngredientsFulfilled(world, goldenBowlPosition, remainingAdditionalIngredients) &&
               this.pentacle.getBlockMatcher().validate(world, goldenBowlPosition) != null;
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
        castingPlayer.sendStatusMessage(new TranslationTextComponent(this.getStartedMessage()), true);
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
        castingPlayer.sendStatusMessage(new TranslationTextComponent(this.getFinishedMessage()), true);
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
        world.playSound(null, goldenBowlPosition, SoundEvents.ENTITY_CHICKEN_EGG, SoundCategory.BLOCKS, 0.7f, 0.7f);
        castingPlayer.sendStatusMessage(new TranslationTextComponent(this.getInterruptedMessage()), true);
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
        return this.startingItem.test(activationItem) &&
               this.areAdditionalIngredientsFulfilled(world, goldenBowlPosition, this.getAdditionalIngredients(world)) &&
               this.pentacle.getBlockMatcher().validate(world, goldenBowlPosition) != null;
    }

    /**
     * Consumes additional ingredients from sacrificial bowls depending on the time passed.
     *
     * @param world                          the world.
     * @param goldenBowlPosition             the position of the golden bowl.
     * @param remainingAdditionalIngredients the remaining additional ingredients. Will be modified if something was consumed!
     * @param time                           the current ritual time.
     * @return true if ingredients were consumed successfully, or none needed to be consumed.
     */
    public boolean consumeAdditionalIngredients(World world, BlockPos goldenBowlPosition,
                                                List<Ingredient> remainingAdditionalIngredients, int time) {
        if (remainingAdditionalIngredients.isEmpty())
            return true;

        int totalIngredientsToConsume = (int) Math.floor(time / this.timePerIngredient);
        int ingredientsConsumed = this.getAdditionalIngredients(world).size() - remainingAdditionalIngredients.size();

        int ingredientsToConsume = totalIngredientsToConsume - ingredientsConsumed;
        if (ingredientsToConsume == 0)
            return true;

        List<SacrificialBowlTileEntity> sacrificialBowls = this.getSacrificialBowls(world, goldenBowlPosition);
        int consumed = 0;
        for (Iterator<Ingredient> it = remainingAdditionalIngredients.iterator();
             it.hasNext() && consumed < ingredientsToConsume; consumed++) {
            Ingredient ingredient = it.next();
            if (this.consumeAdditionalIngredient(world, goldenBowlPosition, sacrificialBowls, ingredient)) {
                //remove from the remaining required ingredients
                it.remove();
            }
            else {
                //if ingredient not found, return false to enable interrupting the ritual.
                return false;
            }
        }
        return true;
    }

    /**
     * Consumes one ingredient from the first matching sacrificial bowl.
     *
     * @param world              the world.
     * @param goldenBowlPosition the position of the golden bowl.
     * @param sacrificialBowls   the list of sacrificial bowls to check.
     * @param ingredient         the ingredient to consume.
     * @return true if the ingredient was found and consumed.
     */
    public boolean consumeAdditionalIngredient(World world, BlockPos goldenBowlPosition,
                                               List<SacrificialBowlTileEntity> sacrificialBowls,
                                               Ingredient ingredient) {
        for (SacrificialBowlTileEntity sacrificialBowl : sacrificialBowls) {
            //first simulate removal to check the ingredient
            if (sacrificialBowl.itemStackHandler.map(handler -> {
                ItemStack stack = handler.extractItem(0, 1, true);
                if (ingredient.test(stack)) {
                    //now take for real
                    handler.extractItem(0, 1, false);

                    //Show effect in world
                    ((ServerWorld) world)
                            .spawnParticle(ParticleTypes.LARGE_SMOKE, sacrificialBowl.getPos().getX() + 0.5,
                                    sacrificialBowl.getPos().getY() + 1.5, sacrificialBowl.getPos().getZ() + 0.5, 1,
                                    0.0, 0.0, 0.0,
                                    0.0);

                    world.playSound(null, sacrificialBowl.getPos(), OccultismSounds.POOF.get(), SoundCategory.BLOCKS, 0.7f, 0.7f);
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

        if (additionalIngredients.size() != items.size())
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
        return remainingItems.size() == 0;
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
        Iterable<BlockPos> blocksToCheck = BlockPos.getAllInBoxMutable(
                goldenBowlPosition.add(-this.sacrificialBowlRange, 0, -this.sacrificialBowlRange),
                goldenBowlPosition.add(this.sacrificialBowlRange, 0, this.sacrificialBowlRange));
        for (BlockPos blockToCheck : blocksToCheck) {
            TileEntity tileEntity = world.getTileEntity(blockToCheck);
            if (tileEntity instanceof SacrificialBowlTileEntity &&
                !(tileEntity instanceof GoldenSacrificialBowlTileEntity)) {
                result.add((SacrificialBowlTileEntity) tileEntity);
            }
        }
        return result;
    }

    /**
     * Prepares the given spirit for spawning by initializing it, setting the taming player, preparing position and rotation, and setting the custom name.
     *
     * @param spirit             the spirit to prepare.
     * @param world              the world to spawn in.
     * @param goldenBowlPosition the golden bowl position.
     * @param castingPlayer      the ritual casting player.
     * @param spiritName         the spirit name.
     */
    public void prepareSpiritForSpawn(SpiritEntity spirit, World world, BlockPos goldenBowlPosition,
                                      PlayerEntity castingPlayer, String spiritName) {
        spirit.onInitialSpawn(world, world.getDifficultyForLocation(goldenBowlPosition), SpawnReason.MOB_SUMMONED, null,
                null);
        spirit.setTamedBy(castingPlayer);
        spirit.setPositionAndRotation(goldenBowlPosition.getX(), goldenBowlPosition.getY(), goldenBowlPosition.getZ(),
                world.rand.nextInt(360), 0);
        spirit.setCustomName(new StringTextComponent(spiritName));
    }

    /**
     * Checks if the given entity is a valid sacrifice.
     *
     * @param entity the entity to check against.
     * @return true if the entity is a valid sacrifice.
     */
    public boolean isValidSacrifice(LivingEntity entity) {
        if (this.sacrificePredicate == null)
            return false;

        return this.sacrificePredicate.test(entity);
    }

    /**
     * Checks if the given item use event is valid for this ritual.
     *
     * @param event the event to check.
     * @return true if the event represents a valid item use.
     */
    public boolean isValidItemUse(PlayerInteractEvent.RightClickItem event) {
        if (this.itemUsePredicate == null)
            return false;

        return this.itemUsePredicate.test(event);
    }

    /**
     * Gets whether this ritual needs a sacrifice to progress.
     *
     * @return true if a sacrifice is required.
     */
    public boolean requiresSacrifice() {
        return this.sacrificePredicate != null;
    }

    /**
     * Gets whether this ritual needs an item use to progress.
     *
     * @return true if an item use is required.
     */
    public boolean requiresItemUse() {
        return this.itemUsePredicate != null;
    }
    //endregion Methods
}
