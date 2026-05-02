package com.klikli_dev.occultism.common.entity.ai.behaviour;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.GateBehavior;

import java.util.*;

public class FirstApplicableBehaviour<E extends LivingEntity> extends GateBehavior<E> {

    @SafeVarargs
    public FirstApplicableBehaviour(BehaviorControl<? super E>... behaviours) {
        this(Arrays.asList(behaviours));
    }

    public FirstApplicableBehaviour(List<? extends BehaviorControl<? super E>> behaviours) {
        super(
                Map.of(),
                Set.of(),
                OrderPolicy.ORDERED,
                RunningPolicy.RUN_ONE,
                weightedBehaviours(behaviours)
        );
    }

    private static <E extends LivingEntity> List<Pair<? extends BehaviorControl<? super E>, Integer>> weightedBehaviours(List<? extends BehaviorControl<? super E>> behaviours) {
        List<Pair<? extends BehaviorControl<? super E>, Integer>> weighted = new ArrayList<>(behaviours.size());

        for (BehaviorControl<? super E> behaviour : behaviours) {
            weighted.add(Pair.of(behaviour, 1));
        }

        return weighted;
    }
}
