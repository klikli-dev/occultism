/*
 * MIT License
 *
 * Copyright 2021 vemerion
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

package com.klikli_dev.occultism.common.entity.familiar;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.advancement.FamiliarTrigger.Type;
import com.klikli_dev.occultism.config.OccultismServerConfig;
import com.klikli_dev.occultism.registry.OccultismAdvancements;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.FollowMobGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.neoforge.common.Tags.Items;
import org.jspecify.annotations.NonNull;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class BlacksmithFamiliarEntity extends FamiliarEntity {

    private static final ConfigValue<Integer> UPGRADE_COST = Occultism.SERVER_CONFIG.familiar.blacksmithFamiliarUpgradeCost;
    private static final EntityDataAccessor<Byte> BARS = SynchedEntityData.defineId(BlacksmithFamiliarEntity.class,
            EntityDataSerializers.BYTE);
    private int ironCount;
    private static final ConfigValue<Integer> IESNIUM_COST = Occultism.SERVER_CONFIG.familiar.blacksmithFamiliarIesniumUpgradeCost;
    private static final EntityDataAccessor<Byte> IESNIUM_BARS = SynchedEntityData.defineId(BlacksmithFamiliarEntity.class,
            EntityDataSerializers.BYTE);
    private int iesniumCount;

    public BlacksmithFamiliarEntity(EntityType<? extends BlacksmithFamiliarEntity> type, Level level) {
        super(type, level);
    }

    private static int getMaxIron() {
        return UPGRADE_COST.get() * 10;
    }

    private static int getMaxIesnium() {
        return IESNIUM_COST.get() * 10;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, EntitySpawnReason pReason, @Nullable SpawnGroupData pSpawnData) {
        this.setEarring(this.getRandom().nextDouble() < 0.1);
        this.setMarioMoustache(this.getRandom().nextDouble() < 0.5);
        this.setSquareHair(this.getRandom().nextDouble() < 0.5);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FamiliarPanicGoal(this, 1.25));
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new UpgradeGoal(this));
        this.goalSelector.addGoal(2, new IesniumUpgradeGoal(this));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8));
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new FollowMobGoal(this, 1, 3, 7));
    }

    @Override
    protected InteractionResult mobInteract(Player playerIn, InteractionHand hand) {
        ItemStack stack = playerIn.getItemInHand(hand);
        if (playerIn == this.getFamiliarOwner() && this.ironCount < getMaxIron()
                && (stack.is(Items.INGOTS_IRON) || stack.is(Items.STORAGE_BLOCKS_IRON))) {
            if (!this.level().isClientSide()) {
                stack.shrink(1);
                this.changeIronCount(stack.is(Items.INGOTS_IRON) ? 1 : 9);
            }
            return !this.level().isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
        }
        if (this.hasIesniumUpgrade() && playerIn == this.getFamiliarOwner() && this.iesniumCount < getMaxIesnium()
                && (stack.is(OccultismTags.Items.IESNIUM_INGOT) || stack.is(OccultismTags.Items.STORAGE_BLOCK_IESNIUM))) {
            if (!this.level().isClientSide()) {
                stack.shrink(1);
                this.changeIesniumCount(stack.is(OccultismTags.Items.IESNIUM_INGOT) ? 1 : 9);
            }
            return !this.level().isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
        }
        if (this.hasBlacksmithUpgrade() && !this.hasIesniumUpgrade()
                && playerIn == this.getFamiliarOwner()
                && stack.is(OccultismBlocks.IESNIUM_ANVIL.asItem())) {
            if (!this.level().isClientSide()) {
                stack.shrink(1);
                this.iesniumUpgrade();
            }
            return !this.level().isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
        }

        return super.mobInteract(playerIn, hand);
    }

    @Override
    public boolean canIesniumUpgrade() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.hasBlacksmithUpgrade() && this.getOwner() instanceof Player player
                && this.isEffectEnabled(player) && player.level() instanceof ServerLevel serverLevel
                && serverLevel.getGameTime() % Occultism.SERVER_CONFIG.familiar.blacksmithFamiliarUpgradeCost.getAsInt() == 0) {
            repairEquipment(player, serverLevel);
        }
    }

    @Override
    public void curioTick(LivingEntity wearer) {
        if (this.hasBlacksmithUpgrade() && wearer instanceof Player player
                && this.isEffectEnabled(player) && player.level() instanceof ServerLevel serverLevel
                && serverLevel.getGameTime() % Occultism.SERVER_CONFIG.familiar.blacksmithFamiliarUpgradeCost.getAsInt() == 0) {
            repairEquipment(player, serverLevel);
        }
    }

    private void repairEquipment(Player player, ServerLevel serverLevel) {
        boolean onlyOne = !this.hasIesniumUpgrade();
        for (int i = 0 ; i < player.getInventory().getContainerSize() ; i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.isDamaged()) {
                stack.hurtAndBreak(-1, serverLevel, null, (item) -> {});
                if (onlyOne)
                    return;
            }
        }
    }

    @Override
    public boolean hasRareVariant() {
        return this.hasEarring();
    }

    @Override
    protected void defineSynchedData(Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BARS, (byte) 0);
        builder.define(IESNIUM_BARS, (byte) 0);
    }

    public boolean hasEarring() {
        return this.hasVariant(0);
    }

    public boolean hasMarioMoustache() {
        return this.hasVariant(1);
    }

    public boolean hasSquareHair() {
        return this.hasVariant(2);
    }

    private void setEarring(boolean b) {
        this.setVariant(0, b);
    }

    private void setMarioMoustache(boolean b) {
        this.setVariant(1, b);
    }

    private void setSquareHair(boolean b) {
        this.setVariant(2, b);
    }

    private void setIronCount(int count) {
        this.ironCount = count;
        this.entityData.set(BARS, (byte) Math.min(10, (this.ironCount / UPGRADE_COST.get())));
    }

    private void changeIronCount(int delta) {
        this.setIronCount(this.ironCount + delta);
    }

    public byte getBars() {
        return this.entityData.get(BARS);
    }

    private void setIesniumCount(int count) {
        this.iesniumCount = count;
        this.entityData.set(IESNIUM_BARS, (byte) Math.min(10, (this.iesniumCount / IESNIUM_COST.get())));
    }

    private void changeIesniumCount(int delta) {
        this.setIesniumCount(this.iesniumCount + delta);
    }

    public byte getIesniumBars() {
        return this.entityData.get(IESNIUM_BARS);
    }

    protected void dropMetal(@NonNull ServerLevel level) {
        int blockCount = this.ironCount / 9;
        int barCount = this.ironCount % 9;
        this.spawnAtLocation(level, new ItemStack(net.minecraft.world.item.Items.IRON_INGOT, barCount));
        this.spawnAtLocation(level, new ItemStack(net.minecraft.world.item.Items.IRON_BLOCK, blockCount));
        int blockIesniumCount = this.iesniumCount / 9;
        int barIesniumCount = this.iesniumCount % 9;
        this.spawnAtLocation(level, new ItemStack(OccultismItems.IESNIUM_INGOT.get(), barIesniumCount));
        this.spawnAtLocation(level, new ItemStack(OccultismBlocks.IESNIUM_BLOCK.get(), blockIesniumCount));
    }

    @Override
    protected void resetCustomFamiliarData() {
        if (this.level() instanceof ServerLevel level)
            this.dropMetal(level);
        super.resetCustomFamiliarData();
        this.setIronCount(0);
        this.setIesniumCount(0);
    }

    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("ironCount", this.ironCount);
        output.putInt("iesniumCount", this.iesniumCount);
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.setIronCount(input.getIntOr("ironCount", 0));
        this.setIesniumCount(input.getIntOr("iesniumCount", 0));
    }

    private static class UpgradeGoal extends Goal {

        private static final ConfigValue<Integer> MAX_COOLDOWN = Occultism.SERVER_CONFIG.familiar.blacksmithFamiliarUpgradeCooldown;

        private final BlacksmithFamiliarEntity blacksmith;
        private IFamiliar target;
        private int cooldown = MAX_COOLDOWN.get();

        public UpgradeGoal(BlacksmithFamiliarEntity blacksmith) {
            this.blacksmith = blacksmith;
            this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            this.target = this.findTarget();
            return this.blacksmith.ironCount >= UPGRADE_COST.get() && this.target != null && this.cooldown-- < 0;
        }

        @Override
        public boolean canContinueToUse() {
            return this.target != null;
        }

        public void start() {
            this.blacksmith.getNavigation().moveTo(this.target.getFamiliarEntity(), 0.7);
        }

        public void stop() {
            this.blacksmith.getNavigation().stop();
            this.cooldown = MAX_COOLDOWN.get();
            this.target = null;
        }

        @Override
        public void tick() {
            if (this.target == null)
                return;

            if (!this.blacksmith.isPathFinding())
                this.blacksmith.getNavigation().moveTo(this.target.getFamiliarEntity(), 0.7);

            if (this.blacksmith.distanceToSqr(this.target.getFamiliarEntity()) < 3) {
                if (this.target.canBlacksmithUpgrade()) {
                    this.target.blacksmithUpgrade();
                    this.blacksmith.changeIronCount(-UPGRADE_COST.get());
                    this.blacksmith.level().playSound(this.blacksmith, this.blacksmith.getOnPos(),
                            SoundEvents.ANVIL_USE, SoundSource.NEUTRAL, 0.5F, 1F);
                    OccultismAdvancements.FAMILIAR.get().trigger(this.blacksmith.getFamiliarOwner(),
                            Type.BLACKSMITH_UPGRADE);
                }
                this.target = null;
            }
        }

        private IFamiliar findTarget() {
            for (LivingEntity e : this.blacksmith.level().getEntitiesOfClass(LivingEntity.class,
                    this.blacksmith.getBoundingBox().inflate(4), this::familiarPred)) {
                return (IFamiliar) e;
            }
            return null;
        }

        private boolean familiarPred(Entity e) {
            if (!(e instanceof IFamiliar familiar))
                return false;
            LivingEntity owner = familiar.getFamiliarOwner();
            return familiar.canBlacksmithUpgrade() && owner != null && owner == this.blacksmith.getFamiliarOwner();
        }
    }

    private static class IesniumUpgradeGoal extends Goal {

        private static final ConfigValue<Integer> MAX_COOLDOWN = Occultism.SERVER_CONFIG.familiar.blacksmithFamiliarUpgradeCooldown;

        private final BlacksmithFamiliarEntity blacksmith;
        private IFamiliar target;
        private int cooldown = MAX_COOLDOWN.get();

        public IesniumUpgradeGoal(BlacksmithFamiliarEntity blacksmith) {
            this.blacksmith = blacksmith;
            this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            this.target = this.findTarget();
            return this.blacksmith.hasIesniumUpgrade() && this.blacksmith.iesniumCount >= IESNIUM_COST.get() && this.target != null && this.cooldown-- < 0;
        }

        @Override
        public boolean canContinueToUse() {
            return this.target != null;
        }

        public void start() {
            this.blacksmith.getNavigation().moveTo(this.target.getFamiliarEntity(), 0.7);
        }

        public void stop() {
            this.blacksmith.getNavigation().stop();
            this.cooldown = MAX_COOLDOWN.get();
            this.target = null;
        }

        @Override
        public void tick() {
            if (this.target == null)
                return;

            if (!this.blacksmith.isPathFinding())
                this.blacksmith.getNavigation().moveTo(this.target.getFamiliarEntity(), 0.7);

            if (this.blacksmith.distanceToSqr(this.target.getFamiliarEntity()) < 3) {
                if (this.target.canIesniumUpgrade()) {
                    this.target.iesniumUpgrade();
                    this.blacksmith.changeIesniumCount(-IESNIUM_COST.get());
                    this.blacksmith.level().playSound(this.blacksmith, this.blacksmith.getOnPos(),
                            SoundEvents.ANVIL_USE, SoundSource.NEUTRAL, 0.5F, 1F);
                }
                this.target = null;
            }
        }

        private IFamiliar findTarget() {
            for (LivingEntity e : this.blacksmith.level().getEntitiesOfClass(LivingEntity.class,
                    this.blacksmith.getBoundingBox().inflate(4), this::familiarPred)) {
                return (IFamiliar) e;
            }
            return null;
        }

        private boolean familiarPred(Entity e) {
            if (!(e instanceof IFamiliar familiar))
                return false;
            LivingEntity owner = familiar.getFamiliarOwner();
            return familiar.canIesniumUpgrade() && owner != null && owner == this.blacksmith.getFamiliarOwner();
        }
    }
}
