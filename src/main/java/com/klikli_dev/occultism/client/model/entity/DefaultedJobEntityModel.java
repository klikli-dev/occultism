package com.klikli_dev.occultism.client.model.entity;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.entity.job.SpiritJobFactory;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.registry.OccultismSpiritJobs;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.animation.AnimationState;
import com.geckolib.cache.GeckoLibCache;
import com.geckolib.cache.object.GeoBone;
import com.geckolib.constant.DataTickets;
import com.geckolib.model.DefaultedEntityGeoModel;
import com.geckolib.model.data.EntityModelData;

import java.util.HashMap;
import java.util.Map;

public abstract class DefaultedJobEntityModel<T extends SpiritEntity & GeoAnimatable> extends DefaultedEntityGeoModel<T> {
    private final String entity_subpath;
    protected final Map<String, ModelData> jobModels;
    protected final ModelData worker;

    public DefaultedJobEntityModel(Identifier assetSubpath, boolean turnsHead, String entity_subpath) {
        super(assetSubpath, turnsHead);
        this.entity_subpath = entity_subpath;
        jobModels = new HashMap<>();
        this.worker = this.buildModelData("worker");
        for(var job: OccultismSpiritJobs.REGISTRY.entrySet()) {
            SpiritJobFactory factory = job.getValue();
            jobModels.put(job.getKey().location().toString(), this.buildModelData(factory.client().modelID(),"_"));
        }
    }

    public ModelData getModelData(T animatable) {
        var job = animatable.getJobID();
        var model = jobModels.getOrDefault(job, this.worker);
        if(!GeckoLibCache.getBakedModels().containsKey(model.model()))
            model=this.worker;
        return model;
    }
    public ModelData buildModelData(String job) {
        return this.buildModelData(job, "_");
    }

    public ModelData buildModelData(Identifier basePath) {
        return new ModelData(
                this.buildFormattedModelPath(basePath),
                this.buildFormattedTexturePath(basePath),
                this.buildFormattedAnimationPath(basePath)
        );
    }
    public ModelData buildModelData(Identifier location, String separator) {
        return this.buildModelData(Identifier.fromNamespaceAndPath(location.getNamespace(), entity_subpath + separator + location.getPath()));
    }
    public ModelData buildModelData(String job, String separator) {
        return this.buildModelData(Identifier.fromNamespaceAndPath(Occultism.MODID, job),separator);
    }

    public record ModelData(Identifier model, Identifier texture, Identifier animation) {
    }

    @Override
    public RenderType getRenderType(T animatable, Identifier texture) {
        return RenderType.entityTranslucent(this.getTextureResource(animatable));
    }

    @Override
    public Identifier getModelResource(T animatable) {
        return this.getModelData(animatable).model();
    }

    @Override
    public Identifier getTextureResource(T animatable) {
        return this.getModelData(animatable).texture();
    }

    @Override
    public Identifier getAnimationResource(T animatable) {
        return this.getModelData(animatable).animation();
    }

    @Override
    public void setCustomAnimations(T entity, long instanceId, AnimationState<T> animationState) {
        super.setCustomAnimations(entity, instanceId, animationState);

        GeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
        }
        GeoBone Head = getAnimationProcessor().getBone("Head");
        if (Head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            Head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
            Head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
        }
    }
}
