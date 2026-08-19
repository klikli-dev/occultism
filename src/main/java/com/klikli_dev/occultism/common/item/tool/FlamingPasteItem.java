package com.klikli_dev.occultism.common.item.tool;

//import com.klikli_dev.occultism.common.entity.familiar.DrikwingEntity;
import com.klikli_dev.occultism.registry.OccultismItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import net.minecraft.world.entity.projectile.hurtingprojectile.SmallFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class FlamingPasteItem extends DamageInCraftingItem implements ProjectileItem {
    private final Fluid lava = Fluids.LAVA;

    public FlamingPasteItem(Item.Properties properties) {
        super(properties);
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

        target.setRemainingFireTicks(30 * 20);
        player.level().playSound(player, player.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.3F, player.level().getRandom().nextFloat() * 0.4F + 0.8F);
        stack.hurtAndBreak(1 , player, hand.asEquipmentSlot());
        player.awardStat(Stats.ITEM_USED.get(this));
        player.getCooldowns().addCooldown(stack, 20);
        //if (target instanceof DrikwingEntity bird) {
        //    bird.setFlaming(true);
        //    stack.hurtAndBreak(128 , player, hand.asEquipmentSlot());
        //}
        return InteractionResult.SUCCESS;
    }

    @Override
    public void postHurtEnemy(ItemStack itemStack, LivingEntity mob, LivingEntity attacker) {
        itemStack.hurtAndBreak(1 , attacker, attacker.getUsedItemHand().asEquipmentSlot());
        mob.setRemainingFireTicks(30 * 20);
        super.postHurtEnemy(itemStack, mob, attacker);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        InteractionHand offHand = hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack offStack = player.getItemInHand(offHand);
        if (offStack.is(OccultismItems.GRAY_PASTE)) {
            if (level instanceof ServerLevel serverLevel) {
                Projectile.spawnProjectileFromRotation((source, l, itemStack) ->
                        new SmallFireball(level,
                                player.position().x(), player.getEyePosition().y(), player.position().z(),
                                player.getLookAngle()), serverLevel, stack, player, 0.0F, 1.5F, 1.0F);
            }

            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.PLAYERS, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            player.awardStat(Stats.ITEM_USED.get(this));
            stack.hurtAndBreak(1, player, hand);
            offStack.hurtAndBreak(1, player, offHand);
            player.getCooldowns().addCooldown(stack, 10);
            return InteractionResult.SUCCESS;
        }
        return super.use(level, player, hand);
    }

    public @NonNull InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        BlockState blockstate2 = state.getToolModifiedState(context, ItemAbilities.FIRESTARTER_LIGHT, false);
        ItemStack itemStack = context.getItemInHand();
        BlockPos relativePos = pos.relative(context.getClickedFace());
        if (blockstate2 == null && player != null) {
            if (player.isShiftKeyDown()) {
                SoundEvent soundEvent = SoundEvents.BUCKET_EMPTY_LAVA;
                ResourceHandler<FluidResource> handleFluid = level.getCapability(Capabilities.Fluid.BLOCK, pos, context.getClickedFace());
                if (handleFluid != null) {
                    try (var tx = Transaction.openRoot()) {
                        int inserted = handleFluid.insert(FluidResource.of(Fluids.LAVA), Integer.MAX_VALUE , tx);
                        tx.commit();
                        if (inserted > 0) {
                            itemStack.hurtAndBreak((int)(inserted*0.008) , player, context.getHand().asEquipmentSlot());
                            level.playSound(player, pos, soundEvent, SoundSource.BLOCKS, 0.8F, 1.0F);
                            level.gameEvent(player, GameEvent.FLUID_PLACE, pos);
                        }
                        player.awardStat(Stats.ITEM_USED.get(this));
                        return InteractionResult.SUCCESS;
                    }
                }

                BlockPos placePos = canBlockContainFluid(player, level, pos, state) ? pos : relativePos;
                if (this.placeLava(player, level, placePos, context.getHitResult(), itemStack)) {
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, placePos, itemStack);
                        itemStack.hurtAndBreak(8, player, context.getHand().asEquipmentSlot());
                    }
                    level.playSound(player, pos, soundEvent, SoundSource.BLOCKS, 0.8F, 1.0F);
                    level.gameEvent(player, GameEvent.FLUID_PLACE, pos);
                    player.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResult.SUCCESS;

                } else {
                    return InteractionResult.FAIL;
                }

            } else if (BaseFireBlock.canBePlacedAt(level, relativePos, context.getHorizontalDirection())) {
                level.playSound(player, relativePos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 0.3F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                BlockState fireState = BaseFireBlock.getState(level, relativePos);
                level.setBlock(relativePos, fireState, 11);
                level.gameEvent(player, GameEvent.BLOCK_PLACE, pos);
                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, relativePos, itemStack);
                    itemStack.hurtAndBreak(1, player, context.getHand().asEquipmentSlot());
                }

                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.FAIL;
            }
        } else  {
            level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 0.8F, level.getRandom().nextFloat() * 0.4F + 0.8F);
            level.setBlock(pos, blockstate2, 11);
            level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            if (player != null) {
                context.getItemInHand().hurtAndBreak(1, player, context.getHand().asEquipmentSlot());
            }

            return InteractionResult.SUCCESS;
        }
    }

    @Override
    public boolean canPerformAction(ItemInstance stack, ItemAbility itemAbility) {
        return ItemAbilities.DEFAULT_FLINT_ACTIONS.contains(itemAbility);
    }

    protected boolean canBlockContainFluid(@Nullable Player player, Level worldIn, BlockPos posIn, BlockState blockstate) {
        return blockstate.getBlock() instanceof LiquidBlockContainer
                && ((LiquidBlockContainer)blockstate.getBlock()).canPlaceLiquid(player, worldIn, posIn, blockstate, this.lava);
    }

    public boolean placeLava(@Nullable LivingEntity user, Level level, BlockPos pos, @Nullable BlockHitResult hitResult, @Nullable ItemStack containerItem) {
        if (!(this.lava instanceof FlowingFluid flowingFluid)) {
            return false;
        } else {
            BlockState blockState = level.getBlockState(pos);
            Block block = blockState.getBlock();
            boolean mayReplace = blockState.canBeReplaced(this.lava);
            boolean placeLiquid = mayReplace
                    || block instanceof LiquidBlockContainer container
                    && container.canPlaceLiquid(user, level, pos, blockState, this.lava);
            var containedFluidStack = containerItem != null ? net.neoforged.neoforge.transfer.fluid.FluidUtil.getFirstStackContained(containerItem) : net.neoforged.neoforge.fluids.FluidStack.EMPTY;
            boolean canPlaceFluidInsideBlock = blockState.isAir() || placeLiquid && (user != null || hitResult == null);
            if (!canPlaceFluidInsideBlock) {
                return hitResult != null && this.placeLava(user, level, hitResult.getBlockPos().relative(hitResult.getDirection()), null, containerItem);
            } else if (!containedFluidStack.isEmpty() && this.lava.getFluidType().isVaporizedOnPlacement(level, pos, containedFluidStack)) {
                this.lava.getFluidType().onVaporize(user, level, pos, containedFluidStack);
                return true;
            } else if (block instanceof LiquidBlockContainer container && container.canPlaceLiquid(user, level, pos, blockState, this.lava)) {
                container.placeLiquid(level, pos, blockState, flowingFluid.getSource(false));
                return true;
            } else {
                if (!level.isClientSide() && mayReplace && !blockState.liquid()) {
                    level.destroyBlock(pos, true);
                }

                if (!level.setBlock(pos, this.lava.defaultFluidState().createLegacyBlock(), 11) && !blockState.getFluidState().isSource()) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    @Override
    public Projectile asProjectile(Level level,  Position position, ItemStack itemStack, Direction direction) {
        RandomSource random = level.getRandom();
        double dirX = random.triangle(direction.getStepX(), 0.11485000000000001);
        double dirY = random.triangle(direction.getStepY(), 0.11485000000000001);
        double dirZ = random.triangle(direction.getStepZ(), 0.11485000000000001);
        Vec3 dir = new Vec3(dirX, dirY, dirZ);
        SmallFireball fireball = new SmallFireball(level, position.x(), position.y(), position.z(), dir.normalize());
        fireball.setItem(itemStack);
        return fireball;
    }

    public Projectile asBigProjectile(Level level, LivingEntity entity, ItemStack itemStack, Direction direction, int power) {
        RandomSource random = level.getRandom();
        double dirX = random.triangle(direction.getStepX(), 0.11485000000000001);
        double dirY = random.triangle(direction.getStepY(), 0.11485000000000001);
        double dirZ = random.triangle(direction.getStepZ(), 0.11485000000000001);
        Vec3 dir = new Vec3(dirX, dirY, dirZ);
        LargeFireball fireball = new LargeFireball(level, entity, dir, power);
        fireball.setItem(itemStack);
        return fireball;
    }

    @Override
    public void shoot(Projectile projectile, double xd, double yd, double zd, float pow, float uncertainty) {
    }

    @Override
    public ProjectileItem.DispenseConfig createDispenseConfig() {
        return ProjectileItem.DispenseConfig.builder()
                .positionFunction((source, direction) -> DispenserBlock.getDispensePosition(source, 1.0, Vec3.ZERO))
                .uncertainty(0.5F)
                .power(1.0F)
                .overrideDispenseEvent(1018)
                .build();
    }
}
