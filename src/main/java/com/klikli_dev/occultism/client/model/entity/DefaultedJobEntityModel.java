package com.klikli_dev.occultism.client.model.entity;

import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.model.DefaultedEntityGeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import com.google.common.reflect.TypeToken;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.entity.job.SpiritJobFactory;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.registry.OccultismSpiritJobs;
import net.minecraft.resources.Identifier;

import java.util.HashMap;
import java.util.Map;

public abstract class DefaultedJobEntityModel<T extends SpiritEntity & GeoAnimatable> extends DefaultedEntityGeoModel<T> {
    private static final DataTicket<Identifier> JOB_MODEL = DataTicket.create("occultism_job_model", new TypeToken<>() {});
    private static final DataTicket<Identifier> JOB_TEXTURE = DataTicket.create("occultism_job_texture", new TypeToken<>() {});
    private static final DataTicket<Identifier> JOB_ANIMATION = DataTicket.create("occultism_job_animation", new TypeToken<>() {});

    private final String entity_subpath;
    protected final Map<String, ModelData> jobModels;
    protected final ModelData worker;

    public DefaultedJobEntityModel(Identifier assetSubpath, boolean turnsHead, String entity_subpath) {
        super(assetSubpath);
        this.entity_subpath = entity_subpath;
        jobModels = new HashMap<>();
        this.worker = this.buildModelData("worker");
        for (var job : OccultismSpiritJobs.REGISTRY.entrySet()) {
            SpiritJobFactory factory = job.getValue();
            jobModels.put(job.getKey().identifier().toString(), this.buildModelData(factory.client().modelID(), "_"));
        }
    }

    public ModelData getModelData(T animatable) {
        var job = animatable.getJobID();
        return jobModels.getOrDefault(job, this.worker);
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
        return this.buildModelData(Identifier.fromNamespaceAndPath(Occultism.MODID, job), separator);
    }

    public record ModelData(Identifier model, Identifier texture, Identifier animation) {
    }

    @Override
    public void addAdditionalStateData(T animatable, @org.jetbrains.annotations.Nullable Object relatedObject, GeoRenderState renderState) {
        super.addAdditionalStateData(animatable, relatedObject, renderState);
        ModelData data = this.getModelData(animatable);
        renderState.addGeckolibData(JOB_MODEL, data.model());
        renderState.addGeckolibData(JOB_TEXTURE, data.texture());
        renderState.addGeckolibData(JOB_ANIMATION, data.animation());
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        Identifier id = renderState.getGeckolibData(JOB_MODEL);
        return id != null ? id : this.worker.model();
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        Identifier id = renderState.getGeckolibData(JOB_TEXTURE);
        return id != null ? id : this.worker.texture();
    }

    @Override
    public Identifier getAnimationResource(T animatable) {
        return this.getModelData(animatable).animation();
    }
}
