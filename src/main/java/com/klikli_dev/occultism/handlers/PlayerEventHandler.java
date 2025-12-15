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

package com.klikli_dev.occultism.handlers;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.klikli_dev.occultism.common.entity.familiar.IFamiliar;
import com.klikli_dev.occultism.common.item.spirit.BookOfBindingItem;
import com.klikli_dev.occultism.common.item.tool.SoulGemItem;
import com.klikli_dev.occultism.crafting.recipe.BoundBookOfBindingRecipe;
import com.klikli_dev.occultism.registry.*;
import com.klikli_dev.occultism.util.Math3DUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.List;
import java.util.Optional;

@EventBusSubscriber(modid = Occultism.MODID)
public class PlayerEventHandler {

    private static final ItemAbility LIGHT_FIRE = ItemAbility.get("light_fire");
    private static final ItemAbility LIGHT_CAMPFIRE = ItemAbility.get("light_campfire");

    //region Static Methods
    @SubscribeEvent
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        spiritFire(event);
        dancingFamiliars(event);
        bookshelfBinding(event);
    }

    private static void spiritFire(PlayerInteractEvent.RightClickBlock event) {
        boolean isFlintAndSteel = event.getItemStack().getItem() == Items.FLINT_AND_STEEL;
        boolean isFireCharge = event.getItemStack().getItem() == Items.FIRE_CHARGE;
        boolean canLightFire = event.getItemStack().canPerformAction(LIGHT_FIRE) || event.getItemStack().canPerformAction(LIGHT_CAMPFIRE);

        if (isFlintAndSteel || isFireCharge || canLightFire) {
            //find if there is any datura
            AABB box = new AABB(-1, -1, -1, 1, 1, 1)
                    .move(Math3DUtil.center(event.getPos()));
            List<ItemEntity> list = event.getLevel().getEntitiesOfClass(ItemEntity.class, box,
                    item -> item.getItem().is(OccultismTags.Items.START_SPIRIT_FIRE));
            if (!list.isEmpty()) {
                //if there is datura, check if we can edit the target face
                Level level = event.getLevel();
                BlockPos pos = level.getBlockState(event.getPos()).canBeReplaced() ? event.getPos(): event.getPos().relative(event.getFace());
                if (!event.getEntity().mayUseItemAt(pos, event.getFace(), event.getItemStack())) {
                    return;
                }

                //consume all datura
                list.forEach(e -> e.remove(Entity.RemovalReason.DISCARDED));

                //if there is air, place block and play sound
                if (level.getBlockState(pos).canBeReplaced()) {
                    //sound based on the item used
                    SoundEvent soundEvent =
                            isFlintAndSteel ? SoundEvents.FLINTANDSTEEL_USE : SoundEvents.FIRECHARGE_USE;
                    level.playSound(event.getEntity(), pos, soundEvent,
                            SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.4F + 0.8F);

                    level.setBlock(pos, OccultismBlocks.SPIRIT_FIRE.get().defaultBlockState(), 11);

                    //now handle used item
                    if (isFlintAndSteel) {
                        event.getItemStack().hurtAndBreak(1, event.getEntity(), LivingEntity.getSlotForHand(event.getHand()));
                    } else if (isFireCharge) {
                        event.getItemStack().shrink(1);
                    }
                }
                //finally, cancel original event to prevent real action and show use animation
                event.setCanceled(true);
                event.getEntity().swing(InteractionHand.MAIN_HAND);
            }
        }
    }

    private static void dancingFamiliars(PlayerInteractEvent.RightClickBlock event) {
        BlockState state = event.getLevel().getBlockState(event.getPos());
        if (!state.hasProperty(JukeboxBlock.HAS_RECORD) || state.getValue(JukeboxBlock.HAS_RECORD)
                || !(event.getItemStack().has(DataComponents.JUKEBOX_PLAYABLE)))
            return;
        if (event.getLevel()
                .getEntitiesOfClass(Entity.class, new AABB(event.getPos()).inflate(3),
                        e -> e instanceof IFamiliar && ((IFamiliar) e).getFamiliarOwner() == event.getEntity())
                .isEmpty())
            return;
        OccultismAdvancements.FAMILIAR.get().trigger(event.getEntity(), FamiliarTrigger.Type.PARTY);
    }

    private static void bookshelfBinding(PlayerInteractEvent.RightClickBlock event) {
        if (!(event.getHand() == InteractionHand.MAIN_HAND)
                || !(event.getItemStack().getItem() == OccultismItems.DICTIONARY_OF_SPIRITS.get())
                || !(event.getEntity().isCrouching()))
            return;

        BlockEntity blockEntity = event.getLevel().getBlockEntity(event.getPos());
        if (!(blockEntity instanceof ChiseledBookShelfBlockEntity bookShelf))
            return;

        for (int i = 0; i < 6; i++) {
            if (bookShelf.getItem(i).getItem() instanceof BookOfBindingItem book) {
                if (book.equals(OccultismItems.BOOK_OF_BINDING_EMPTY.get())) {
                    ItemStack dye = event.getEntity().getOffhandItem();
                    if (dye.getCount() > 3) {
                        List<ItemStack> ingredients = List.of(ItemStack.EMPTY, dye, ItemStack.EMPTY, dye, bookShelf.getItem(i), dye,ItemStack.EMPTY, dye, ItemStack.EMPTY);
                        CraftingInput input = CraftingInput.of(3, 3, ingredients);
                        Optional<RecipeHolder<CraftingRecipe>> optional = event.getLevel().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, event.getLevel());
                        if (optional.isPresent()){
                            bookShelf.setItem(i, BoundBookOfBindingRecipe.bookshelfCraft(
                                    optional.get().value().getResultItem(event.getLevel().registryAccess()).copy(), event.getItemStack()));
                            if (!event.getEntity().isCreative())
                                dye.shrink(4);
                        }
                    }
                } else {
                    bookShelf.setItem(i, BoundBookOfBindingRecipe.bookshelfCraft(book.getDefaultInstance(), event.getItemStack()));
                }
            }
        }

        //finally, cancel original event to prevent real action and show use animation
        event.setCancellationResult(InteractionResult.FAIL);
        event.setCanceled(true);
        event.getEntity().swing(InteractionHand.MAIN_HAND);
    }

    @SubscribeEvent
    public static void onPlayerRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        ItemStack stack = event.getItemStack();
        if (stack.getItem() instanceof SoulGemItem soulGemItem) {
            //called from here to bypass sitting entity's sit command.
            if (event.getTarget() instanceof LivingEntity livingEntity
                    && soulGemItem.interactLivingEntity(stack, event.getEntity(),
                        livingEntity, event.getHand()) == InteractionResult.SUCCESS) {
                event.setCanceled(true);
            }
            //force for multipart entities
            if (event.getTarget() instanceof PartEntity<?> partEntity
                    && partEntity.getParent() instanceof LivingEntity livingEntity
                    && soulGemItem.interactLivingEntity(stack, event.getEntity(),
                        livingEntity, event.getHand()) == InteractionResult.SUCCESS) {
                event.setCanceled(true);
            }
        }
    }
    //endregion Static Methods
}
