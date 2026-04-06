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

package com.klikli_dev.occultism.common.entity.spirit.wonderingtrader;

import com.klikli_dev.occultism.registry.OccultismEffects;
import com.klikli_dev.occultism.registry.OccultismParticles;
import com.klikli_dev.occultism.util.CuriosUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.equine.TraderLlama;
import net.minecraft.world.entity.npc.wanderingtrader.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.*;
import com.geckolib.animation.object.PlayState;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class WonderingTraderEntity extends WanderingTrader implements GeoEntity {
    AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);

    @Nullable
    protected MerchantOffers otherOffers;
    @Nullable
    protected MerchantOffers commonOffers;

    protected WanderingTrader replacedTrader = null;

    public WonderingTraderEntity(EntityType<? extends WonderingTraderEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (player.hasEffect(OccultismEffects.THIRD_EYE) || CuriosUtil.hasGoggles(player) || CuriosUtil.hasStaff(player)) {
            this.offers = this.otherOffers;
        } else {
            this.offers = this.commonOffers;
        }
        if (!itemstack.is(Items.VILLAGER_SPAWN_EGG) && this.isAlive() && !this.isTrading() && !this.isBaby()) {
            if (hand == InteractionHand.MAIN_HAND) {
                player.awardStat(Stats.TALKED_TO_VILLAGER);
            }

            if (!this.level().isClientSide()) {
                if (this.getOffers().isEmpty()) {
                    return InteractionResult.CONSUME;
                }

                this.setTradingPlayer(player);
                Component name = this.getDisplayName() == null ? this.getName() : this.getDisplayName();
                this.openTradingScreen(player, name, 1);
            }

            return InteractionResult.SUCCESS;
        } else {
            return super.mobInteract(player, hand);
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty,
                                        @NotNull EntitySpawnReason spawnType, @Nullable SpawnGroupData spawnGroupData) {
        if (spawnType == EntitySpawnReason.EVENT) {
            for (int t = 0; t < 2; t++) {
                BlockPos blockpos = null;
                SpawnPlacementType spawnplacementtype = SpawnPlacements.getPlacementType(EntityType.WANDERING_TRADER);
                for (int i = 0; i < 10; i++) {
                    int j = this.blockPosition().getX() + level.getRandom().nextInt(8) - 4;
                    int k = this.blockPosition().getZ() + level.getRandom().nextInt(8) - 4;
                    int l = level.getHeight(Heightmap.Types.WORLD_SURFACE, j, k);
                    BlockPos blockpos1 = new BlockPos(j, l, k);
                    if (spawnplacementtype.isSpawnPositionOk(level, blockpos1, EntityType.WANDERING_TRADER)) {
                        blockpos = blockpos1;
                        break;
                    }
                }
                if (blockpos != null) {
                    TraderLlama traderllama = EntityType.TRADER_LLAMA.spawn((ServerLevel) level, blockpos, EntitySpawnReason.EVENT);
                    if (traderllama != null) {
                        traderllama.setLeashedTo(this, true);
                        traderllama.setPersistenceRequired();
                    }
                }
            }
        }
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            if (this.replacedTrader != null) {
                WanderingTrader wanderingTrader = this.replacedTrader;
                for (TraderLlama llama : this.level().getEntitiesOfClass(TraderLlama.class,
                        wanderingTrader.getBoundingBox().inflate(8), Entity::isAlive)) {
                    if (llama.getLeashHolder() != null && llama.getLeashHolder().is(wanderingTrader))
                        llama.remove(Entity.RemovalReason.DISCARDED);
                }
                wanderingTrader.discard();
                this.replacedTrader = null;
            }
            if (this.level().getGameTime() % 20 == 0) {
                Vec3 pos = this.position();
                ((ServerLevel) this.level())
                        .sendParticles(ParticleTypes.ENCHANT, pos.x + this.level().getRandom().nextGaussian() / 3,
                                pos.y + 0.5, pos.z + this.level().getRandom().nextGaussian() / 3,
                                this.level().getRandom().nextInt(4), 0.0, 0.0, 0.0, 0.0);
            }
        }
    }

    public void setReplacedTrader(WanderingTrader wanderingTrader){
        this.replacedTrader = wanderingTrader;
    }

    @Override
    public void die(@NotNull DamageSource damageSource) {
        super.die(damageSource);
        if (!this.level().isClientSide() && !this.isAlive()) {
            Vec3 pos = this.position();
            for (int i = 0; i < 30; i++)
                ((ServerLevel) this.level())
                    .sendParticles(OccultismParticles.RITUAL_WAITING.get(), pos.x + this.level().getRandom().nextGaussian() / 3,
                            pos.y + 0.2, pos.z + this.level().getRandom().nextGaussian() / 3,
                            1, 0.0, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public @NotNull MerchantOffers getOffers() {
        if (this.level().isClientSide()) {
            throw new IllegalStateException("Cannot load Villager offers on the client");
        } else {
            if (this.offers == null) {
                this.offers = new MerchantOffers();
                this.updateTrades((ServerLevel) this.level());
                if (this.commonOffers == null) {
                    this.commonOffers = this.offers;
                } else if (this.otherOffers == null) {
                    this.otherOffers = this.offers;
                }
            }
            if (this.otherOffers == null) {
                this.otherOffers = new MerchantOffers();
                this.updateOtherTrades();
            }

            return this.offers;
        }
    }

    public MerchantOffers getCommonOffers() {
        if (this.level().isClientSide()) {
            throw new IllegalStateException("Cannot load Villager offers on the client");
        } else {
            if (this.commonOffers == null) {
                if (this.offers == null) {
                    this.offers = new MerchantOffers();
                    this.updateTrades((ServerLevel) this.level());
                }
                this.commonOffers = this.offers;
            }

            return this.commonOffers;
        }
    }

    public MerchantOffers getOtherOffers() {
        if (this.level().isClientSide()) {
            throw new IllegalStateException("Cannot load Villager offers on the client");
        } else {
            if (this.otherOffers == null) {
                this.otherOffers = new MerchantOffers();
                this.updateOtherTrades();
            }

            return this.otherOffers;
        }
    }

    @Override
    protected void updateTrades(ServerLevel level) {
        if (this.level().enabledFeatures().contains(FeatureFlags.TRADE_REBALANCE)) {
            super.updateTrades(level);
        } else {
            WonderingTrades.ItemListing[] hint = WonderingTrades.WONDERING_TRADES.get(WonderingTrades.HINT);
            // Vanilla wandering trader trades are now data-driven and no longer accessible via code.
            // We only add our own custom hint trades here.
            if (hint != null) {
                MerchantOffers merchantoffers = this.getOffers();
                this.addOffersFromItemListings(merchantoffers, hint, 1);
            }
        }
    }

    protected void updateOtherTrades() {
            WonderingTrades.ItemListing[] list1 = WonderingTrades.WONDERING_TRADES.get(WonderingTrades.BOOK);
            WonderingTrades.ItemListing[] list2 = WonderingTrades.WONDERING_TRADES.get(WonderingTrades.PARAPHERNALIA);
            WonderingTrades.ItemListing[] list3 = WonderingTrades.WONDERING_TRADES.get(WonderingTrades.MATERIAL);
            WonderingTrades.ItemListing[] list4 = WonderingTrades.WONDERING_TRADES.get(WonderingTrades.INVENTORY);
            WonderingTrades.ItemListing[] list5 = WonderingTrades.WONDERING_TRADES.get(WonderingTrades.STORAGE);
            WonderingTrades.ItemListing[] list6 = WonderingTrades.WONDERING_TRADES.get(WonderingTrades.UTILITY);
            WonderingTrades.ItemListing[] list7 = WonderingTrades.WONDERING_TRADES.get(WonderingTrades.FAMILIAR);
            WonderingTrades.ItemListing[] list8 = WonderingTrades.WONDERING_TRADES.get(WonderingTrades.DYE);
            if (list1 != null && list2 != null && list3 != null && list4 != null
                    && list5 != null && list6 != null && list7 != null && list8 != null) {
                MerchantOffers merchantoffers = this.getOtherOffers();
                this.addOffersFromItemListings(merchantoffers, list1, 1);
                this.addOffersFromItemListings(merchantoffers, list2, this.random.nextIntBetweenInclusive(1,3));
                this.addOffersFromItemListings(merchantoffers, list3, this.random.nextIntBetweenInclusive(1,2));
                this.addOffersFromItemListings(merchantoffers, list4, 1);
                this.addOffersFromItemListings(merchantoffers, list5, this.random.nextIntBetweenInclusive(1,3));
                this.addOffersFromItemListings(merchantoffers, list6, 1);
                if (this.random.nextBoolean()) {
                    this.addOffersFromItemListings(merchantoffers, list7, 1);
                } else if (this.random.nextBoolean()) {
                    this.addOffersFromItemListings(merchantoffers, list8, 1);
                }
            }
    }

    private void addOffersFromItemListings(MerchantOffers offers, WonderingTrades.ItemListing[] listings, int count) {
        ArrayList<WonderingTrades.ItemListing> list = new ArrayList<>(Arrays.asList(listings));
        Collections.shuffle(list);
        for (int i = 0; i < Math.min(count, list.size()); i++) {
            MerchantOffer offer = list.get(i).getOffer(this, this.random);
            if (offer != null) offers.add(offer);
        }
    }

    @Override
    public int getCurrentSwingDuration() {
        return 11; //to match our attack animation speed + 1 tick
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        var mainController = new AnimationController<>("mainController", 5, this::animPredicate);
        controllers.add(mainController);
    }

    private <T extends GeoAnimatable> PlayState animPredicate(AnimationTest<T> tAnimationState) {

        if (this.swinging) {
            return tAnimationState.setAndContinue(RawAnimation.begin().thenPlay("attack"));
        }

        if (tAnimationState.isMoving()) {
            return tAnimationState.setAndContinue(
                    RawAnimation.begin().thenLoop("walk")
            );
        }

        return tAnimationState.setAndContinue(
                RawAnimation.begin().thenLoop("idle")
        );
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.animatableInstanceCache;
    }

    @Override
    public void addAdditionalSaveData(@NotNull ValueOutput output) {
        super.addAdditionalSaveData(output);
        if (!this.level().isClientSide()) {
            if (this.commonOffers != null && !this.commonOffers.isEmpty()) {
                output.storeNullable("CommonOffers", MerchantOffers.CODEC, this.commonOffers);
            }
            if (this.otherOffers != null && !this.otherOffers.isEmpty()) {
                output.storeNullable("OtherOffers", MerchantOffers.CODEC, this.otherOffers);
            }
        }
        this.writeInventoryToTag(output);
    }

    @Override
    public void readAdditionalSaveData(@NotNull ValueInput input) {
        super.readAdditionalSaveData(input);
        this.commonOffers = input.<MerchantOffers>read("CommonOffers", MerchantOffers.CODEC).orElse(null);
        this.otherOffers = input.<MerchantOffers>read("OtherOffers", MerchantOffers.CODEC).orElse(null);
        this.readInventoryFromTag(input);
    }
}
