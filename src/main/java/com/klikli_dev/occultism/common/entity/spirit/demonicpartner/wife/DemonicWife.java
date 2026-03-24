package com.klikli_dev.occultism.common.entity.spirit.demonicpartner.wife;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.entity.spirit.demonicpartner.DemonicPartner;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.Lazy;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.*;
import com.geckolib.animation.object.PlayState;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.util.GeckoLibUtil;

public class DemonicWife extends DemonicPartner implements GeoEntity {

    public static final Identifier ID = Identifier.fromNamespaceAndPath(Occultism.MODID, "demonic_wife");
    public static final Lazy<EntityType<DemonicWife>> ENTITY_TYPE =
            Lazy.of(() -> EntityType.Builder.of(DemonicWife::new, MobCategory.CREATURE)
                    .sized(0.6F, 2)
                    .fireImmune()
                    .clientTrackingRange(8)
                    .build(ID.toString()));
    AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);

    protected DemonicWife(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public int getCurrentSwingDuration() {
        return 11; //to match our attack animation speed + 1 tick
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        var mainController = new AnimationController<>(this, "mainController", 0, this::animPredicate);
        controllers.add(mainController);
    }

    private <T extends GeoAnimatable> PlayState animPredicate(AnimationTest<T> tAnimationState) {

        if (this.swinging) {
            return tAnimationState.setAndContinue(RawAnimation.begin().thenPlay("attack"));
        }

        if (this.isInSittingPose())
            return tAnimationState.setAndContinue(RawAnimation.begin().thenPlay("sit"));

        if (this.isLying())
            return tAnimationState.setAndContinue(RawAnimation.begin().thenPlay("lies"));

        return tAnimationState.setAndContinue(tAnimationState.isMoving() ? RawAnimation.begin().thenPlay("walk") : RawAnimation.begin().thenPlay("idle"));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.animatableInstanceCache;
    }
}
