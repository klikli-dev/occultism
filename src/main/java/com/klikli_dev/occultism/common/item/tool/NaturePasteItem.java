package com.klikli_dev.occultism.common.item.tool;

import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.*;

public class NaturePasteItem extends DamageInCraftingItem {

    public NaturePasteItem(Properties properties) {
        super(properties);
    }

    public static boolean applyBonemeal(ItemStack naturePaste, Level level, BlockPos blockPos, @Nullable Player player) {
        BlockState blockstate = level.getBlockState(blockPos);
        var event = EventHooks.fireBonemealEvent(player, level, blockPos, blockstate, naturePaste);
        if (event.isCanceled()) return event.isSuccessful();
        if (blockstate.getBlock() instanceof BonemealableBlock bonemealableblock && bonemealableblock.isValidBonemealTarget(level, blockPos, blockstate)) {
            if (level instanceof ServerLevel) {
                if (bonemealableblock.isBonemealSuccess(level, level.getRandom(), blockPos, blockstate)) {
                    bonemealableblock.performBonemeal((ServerLevel) level, level.getRandom(), blockPos, blockstate);
                }
            }
            return true;
        }
        return false;
    }

    public static boolean growWaterPlant(ItemStack stack, Level level, BlockPos pos, @Nullable Direction clickedSide) {
        if (level.getBlockState(pos).is(Blocks.WATER) && level.getFluidState(pos).getAmount() == 8 && !stack.isEmpty()) {
            ParticleUtils.spawnParticles(level, pos, 15 * 3, 3.0, 1.0, true, ParticleTypes.HAPPY_VILLAGER);
            if (level instanceof ServerLevel) {
                RandomSource randomsource = level.getRandom();
                label78:
                for (int i = 0; i < 128; i++) {
                    BlockPos blockpos = pos;
                    BlockState blockstate = Blocks.SEAGRASS.defaultBlockState();
                    for (int j = 0; j < i / 16; j++) {
                        blockpos = blockpos.offset(randomsource.nextInt(3) - 1, (randomsource.nextInt(3) - 1) * randomsource.nextInt(3) / 2, randomsource.nextInt(3) - 1);
                        if (level.getBlockState(blockpos).isCollisionShapeFullBlock(level, blockpos)) {
                            continue label78;
                        }
                    }
                    Holder<Biome> holder = level.getBiome(blockpos);
                    if (holder.is(BiomeTags.PRODUCES_CORALS_FROM_BONEMEAL)) {
                        if (i == 0 && clickedSide != null && clickedSide.getAxis().isHorizontal()) {
                            blockstate = BuiltInRegistries.BLOCK
                                    .getRandomElementOf(BlockTags.WALL_CORALS, level.getRandom())
                                    .map(p_204100_ -> p_204100_.value().defaultBlockState())
                                    .orElse(blockstate);
                            if (blockstate.hasProperty(BaseCoralWallFanBlock.FACING)) {
                                blockstate = blockstate.setValue(BaseCoralWallFanBlock.FACING, clickedSide);
                            }
                        } else if (randomsource.nextInt(4) == 0) {
                            blockstate = BuiltInRegistries.BLOCK
                                    .getRandomElementOf(BlockTags.UNDERWATER_BONEMEALS, level.getRandom())
                                    .map(p_204095_ -> p_204095_.value().defaultBlockState())
                                    .orElse(blockstate);
                        }
                    }
                    if (blockstate.is(BlockTags.WALL_CORALS, p_204093_ -> p_204093_.hasProperty(BaseCoralWallFanBlock.FACING))) {
                        for (int k = 0; !blockstate.canSurvive(level, blockpos) && k < 4; k++) {
                            blockstate = blockstate.setValue(BaseCoralWallFanBlock.FACING, Plane.HORIZONTAL.getRandomDirection(randomsource));
                        }
                    }
                    if (blockstate.canSurvive(level, blockpos)) {
                        BlockState blockState1 = level.getBlockState(blockpos);
                        if (blockState1.is(Blocks.WATER) && level.getFluidState(blockpos).getAmount() == 8) {
                            level.setBlock(blockpos, blockstate, 3);
                        } else if (blockState1.is(Blocks.SEAGRASS) && randomsource.nextInt(10) == 0) {
                            ((BonemealableBlock) Blocks.SEAGRASS).performBonemeal((ServerLevel) level, randomsource, blockpos, blockState1);
                        }
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean growCustom(ItemStack item, Level level, BlockPos blockpos) {
        BlockState blockState = level.getBlockState(blockpos);
        if (blockState.is(BlockTags.DIRT)) {
            int r = level.getRandom().nextInt(100);
            if (r < 5) {
                level.setBlock(blockpos, Blocks.ROOTED_DIRT.defaultBlockState(), 2);
            } else if (r < 80) {
                level.setBlock(blockpos, Blocks.GRASS_BLOCK.defaultBlockState(), 2);
            } else if (r < 99) {
                level.setBlock(blockpos, Blocks.PODZOL.defaultBlockState(), 2);
            } else {
                level.setBlock(blockpos, Blocks.MYCELIUM.defaultBlockState(), 2);
            }
            return true;
        }

        if (blockState.getBlock() instanceof CropBlock crop && !crop.isMaxAge(blockState)) {
            level.setBlock(blockpos, crop.getStateForAge(crop.getMaxAge()), 2);
            return true;
        }

        if (blockState.getBlock() instanceof StemBlock) {
            if (blockState.getValue(AGE_7) < 7) {
                level.setBlock(blockpos, blockState.setValue(AGE_7, 7), 2);
            } else if (!level.isClientSide()) {
                ServerLevel serverLevel = (ServerLevel) level;
                //Force fruit growth
                for (int test = 0; test < 100; test++) {
                    blockState.randomTick(serverLevel, blockpos, RandomSource.create(test));
                    if (level.getBlockState(blockpos).getBlock() instanceof AttachedStemBlock) {
                        test += 100;
                    }
                }
            }
            return true;
        }

        if (blockState.getBlock() instanceof SweetBerryBushBlock) {
            level.setBlock(blockpos, blockState.setValue(AGE_3, 3), 2);
            return true;
        }

        if (blockState.getBlock() instanceof MangrovePropaguleBlock) {
            if (blockState.getValue(AGE_4) != 4) {
                level.setBlockAndUpdate(blockpos, blockState.setValue(AGE_4, 4));
                return true;
            }
            if (blockState.getValue(HANGING))
                return true;
        }

        if (blockState.getBlock() instanceof SaplingBlock sapling && !level.isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) level;
            BlockState blockState1 = blockState.setValue(STAGE, 1);
            sapling.advanceTree(serverLevel, blockpos, blockState1, RandomSource.create());
            return true;
        }

        if (blockState.getBlock() instanceof NetherFungusBlock
                || blockState.getBlock() instanceof MushroomBlock
                || blockState.getBlock() instanceof AzaleaBlock) {
            //Force growth
            for (int test = 0; test < 100; test++) {
                if (!(applyBonemeal(item, level, blockpos, null))) {
                    return true;
                }
            }
        }

        if (blockState.getBlock() instanceof NetherWartBlock && blockState.getValue(AGE_3) != 3) {
            level.setBlockAndUpdate(blockpos, blockState.setValue(AGE_3, 3));
            ParticleUtils.spawnParticleInBlock(level, blockpos, 15, ParticleTypes.HAPPY_VILLAGER);
            return true;
        }

        if (blockState.getBlock() instanceof VineBlock) {
            ParticleUtils.spawnParticles(level, blockpos, 15 * 3, 0.3, 1.0, true, ParticleTypes.HAPPY_VILLAGER);
            if (!level.isClientSide()) {
                ServerLevel serverLevel = (ServerLevel) level;
                for (int test = 0; test < 100; test++) {
                    blockState.randomTick(serverLevel, blockpos, RandomSource.create(test));
                }
            }
            return true;
        }

        if (blockState.getBlock() instanceof SugarCaneBlock
                || blockState.getBlock() instanceof CactusBlock) {
            for (int i = 1; i < (level.getMinY() + level.getHeight() - blockpos.getY()); i++) {
                if (level.getBlockState(blockpos.above(i)).canBeReplaced()) {
                    level.setBlockAndUpdate(blockpos.above(i), blockState.getBlock().defaultBlockState());
                    ParticleUtils.spawnParticles(level, blockpos, 15 * 3, 0.6, 1.0, true, ParticleTypes.HAPPY_VILLAGER);
                    if (!blockState.canSurvive(level, blockpos.above(i))) {
                        level.destroyBlock(blockpos.above(i), true);
                    }
                    break;
                } else if (!level.getBlockState(blockpos.above(i)).getBlock().equals(blockState.getBlock())) {
                    break;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer() == null)
            return InteractionResult.FAIL;

        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockState = level.getBlockState(blockpos);
        Player player = context.getPlayer();
        ItemStack item = player.getItemInHand(context.getHand());

        if (blockState.is(Tags.Blocks.ORES) && !blockState.is(OccultismTags.Blocks.BLOCKED_PASTE)
                && player.getItemInHand(InteractionHand.OFF_HAND).getItem() == OccultismItems.GRAY_PASTE.asItem()) {
            level.destroyBlock(blockpos, true);
            level.setBlock(blockpos, blockState, 4);
            item.hurtAndBreak(1, player, player.getEquipmentSlotForItem(item));
            player.getItemInHand(InteractionHand.OFF_HAND)
                    .hurtAndBreak(8, player, player.getEquipmentSlotForItem(player.getItemInHand(InteractionHand.OFF_HAND)));
            ParticleUtils.spawnParticles(level, blockpos, 15 * 5, 0.9, 1.0, true, ParticleTypes.HAPPY_VILLAGER);
            level.levelEvent(1505, blockpos, 15);
            player.getCooldowns().addCooldown(item, 50);
            return InteractionResult.SUCCESS;
        }

        if (growCustom(item, level, blockpos)) {
            if (!level.isClientSide()) {
                item.hurtAndBreak(1, player, player.getEquipmentSlotForItem(item));
                player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
                level.levelEvent(1505, blockpos, 15);
            }
            return InteractionResult.SUCCESS;
        } else if (applyBonemeal(item, level, blockpos, player)) {
            if (!level.isClientSide()) {
                item.hurtAndBreak(1, player, player.getEquipmentSlotForItem(item));
                player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
                level.levelEvent(1505, blockpos, 15);
            }
            return InteractionResult.SUCCESS;
        } else {
            BlockState blockstate = level.getBlockState(blockpos);
            boolean flag = blockstate.isFaceSturdy(level, blockpos, context.getClickedFace());
            if (flag && growWaterPlant(item, level, blockpos.relative(context.getClickedFace()), context.getClickedFace())) {
                if (!level.isClientSide()) {
                    player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
                    level.levelEvent(1505, blockpos, 15);
                    item.hurtAndBreak(1, player, player.getEquipmentSlotForItem(item));
                }
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (!target.isAlive())
            return InteractionResult.PASS;

        //This is called from PlayerEventHandler#onPlayerRightClickEntity, because we need to bypass sitting entities processInteraction
        if (target.level().isClientSide())
            return InteractionResult.PASS;

        if (player.getCooldowns().isOnCooldown(stack))
            return InteractionResult.PASS;

        RandomSource randomSource = target.level().getRandom();
        if (randomSource.nextFloat() < 0.33F)
            target.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 33 * 20, 0, true, true), player);
        if (randomSource.nextFloat() < 0.33F)
            target.addEffect(new MobEffectInstance(MobEffects.OOZING, 33 * 20, 0, true, true), player);
        if (randomSource.nextFloat() < 0.33F)
            target.addEffect(new MobEffectInstance(MobEffects.POISON, 33 * 20, 0, true, true), player);
        player.level().playSound(player, player.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.3F, player.level().getRandom().nextFloat() * 0.4F + 0.8F);
        stack.hurtAndBreak(1 , player, hand.asEquipmentSlot());
        player.awardStat(Stats.ITEM_USED.get(this));
        player.getCooldowns().addCooldown(stack, 20);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void postHurtEnemy(ItemStack itemStack, LivingEntity mob, LivingEntity attacker) {
        itemStack.hurtAndBreak(1 , attacker, attacker.getUsedItemHand().asEquipmentSlot());
        RandomSource randomSource = mob.level().getRandom();
        if (randomSource.nextFloat() < 0.33F)
            mob.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 33 * 20, 0, true, true), attacker);
        if (randomSource.nextFloat() < 0.33F)
            mob.addEffect(new MobEffectInstance(MobEffects.OOZING, 33 * 20, 0, true, true), attacker);
        if (randomSource.nextFloat() < 0.33F)
            mob.addEffect(new MobEffectInstance(MobEffects.POISON, 33 * 20, 0, true, true), attacker);
        super.postHurtEnemy(itemStack, mob, attacker);
    }
}
