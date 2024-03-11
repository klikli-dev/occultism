package com.klikli_dev.occultism.client.model.entity;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.entity.job.SpiritJobFactory;
import com.klikli_dev.occultism.common.entity.spirit.DjinniEntity;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.registry.OccultismSpiritJobs;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

import java.util.HashMap;
import java.util.Map;

public abstract class DefaultedJobEntityModel<T extends SpiritEntity & GeoAnimatable> extends DefaultedEntityGeoModel<T> {
    private final String entity_subpath;
    protected final Map<String, ModelData> jobModels;
    protected final ModelData worker;

    public DefaultedJobEntityModel(ResourceLocation assetSubpath, boolean turnsHead, String entity_subpath) {
        super(assetSubpath, turnsHead);
        this.entity_subpath = entity_subpath;
        jobModels = new HashMap<>();
        this.worker = this.buildModelData("worker");
        for(var job: OccultismSpiritJobs.JOBS.getEntries()) {
            SpiritJobFactory factory = job.get();
            jobModels.put(job.getId().toString(), this.buildModelData(factory.client().modelID(),"_"));
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

    public ModelData buildModelData(ResourceLocation basePath) {
        return new ModelData(
                this.buildFormattedModelPath(basePath),
                this.buildFormattedTexturePath(basePath),
                this.buildFormattedAnimationPath(basePath)
        );
    }
    public ModelData buildModelData(ResourceLocation location, String separator) {
        return this.buildModelData(new ResourceLocation(location.getNamespace(), entity_subpath + separator + location.getPath()));
    }
    public ModelData buildModelData(String job, String separator) {
        return this.buildModelData(new ResourceLocation(Occultism.MODID, job),separator);
    }

    public record ModelData(ResourceLocation model, ResourceLocation texture, ResourceLocation animation) {
    }

    @Override
    public RenderType getRenderType(T animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(this.getTextureResource(animatable));
    }

    @Override
    public ResourceLocation getModelResource(T animatable) {
        return this.getModelData(animatable).model();
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return this.getModelData(animatable).texture();
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return this.getModelData(animatable).animation();
    }
}
