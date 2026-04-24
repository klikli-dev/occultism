package com.klikli_dev.occultism.client.model.entity;

import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.model.DefaultedEntityGeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import com.google.common.reflect.TypeToken;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.entity.job.SpiritJobFactory;
import com.klikli_dev.occultism.client.render.entity.OccultismGeoLivingEntityRenderState;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.registry.OccultismSpiritJobs;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public abstract class DefaultedJobEntityModel<T extends SpiritEntity & GeoAnimatable> extends DefaultedEntityGeoModel<T> {
    private static final DataTicket<Identifier> JOB_MODEL = DataTicket.create("occultism_job_model", new TypeToken<>() {});
    private static final DataTicket<Identifier> JOB_TEXTURE = DataTicket.create("occultism_job_texture", new TypeToken<>() {});
    private static final DataTicket<Identifier> JOB_ANIMATION = DataTicket.create("occultism_job_animation", new TypeToken<>() {});
    private static final DataTicket<Float> HEAD_YAW = DataTicket.create("occultism_head_yaw", new TypeToken<>() {});
    private static final DataTicket<Float> HEAD_PITCH = DataTicket.create("occultism_head_pitch", new TypeToken<>() {});

    private final String entity_subpath;
    protected final Map<String, ModelData> jobModels;
    protected final ModelData worker;
    protected final Map<ModelData, Boolean> resourceCache = new HashMap<>();

    public DefaultedJobEntityModel(Identifier assetSubpath, boolean turnsHead, String entity_subpath) {
        super(assetSubpath);
        this.entity_subpath = entity_subpath;
        this.jobModels = new HashMap<>();
        this.worker = this.buildModelData("worker");
        for (var job : OccultismSpiritJobs.REGISTRY.entrySet()) {
            SpiritJobFactory factory = job.getValue();
            this.jobModels.put(job.getKey().identifier().toString(), this.buildModelData(factory.client().modelID(), "_"));
        }
    }

    public ModelData getModelData(T animatable) {
        var job = animatable.getJobID();
        var model = this.jobModels.getOrDefault(job, this.worker);
        return this.resourceCache.computeIfAbsent(model, this::hasResources) ? model : this.worker;
    }

    protected boolean hasResources(ModelData data) {
        var minecraft = Minecraft.getInstance();

        if (minecraft == null) {
            return true;
        }

        var resourceManager = minecraft.getResourceManager();

        return resourceManager.getResource(data.modelPath()).isPresent()
                && resourceManager.getResource(data.texture()).isPresent()
                && resourceManager.getResource(data.animationPath()).isPresent();
    }

    protected Identifier getModelFilePath(Identifier id) {
        return Identifier.fromNamespaceAndPath(id.getNamespace(), "geckolib/models/" + id.getPath() + ".geo.json");
    }

    protected Identifier getAnimationFilePath(Identifier id) {
        return Identifier.fromNamespaceAndPath(id.getNamespace(), "geckolib/animations/" + id.getPath() + ".animation.json");
    }

    public ModelData buildModelData(String job) {
        return this.buildModelData(job, "_");
    }

    public ModelData buildModelData(Identifier basePath) {
        return new ModelData(
                this.buildFormattedModelPath(basePath),
                this.getModelFilePath(this.buildFormattedModelPath(basePath)),
                this.buildFormattedTexturePath(basePath),
                this.buildFormattedAnimationPath(basePath),
                this.getAnimationFilePath(this.buildFormattedAnimationPath(basePath))
        );
    }

    public ModelData buildModelData(Identifier location, String separator) {
        return this.buildModelData(Identifier.fromNamespaceAndPath(location.getNamespace(), this.entity_subpath + separator + location.getPath()));
    }

    public ModelData buildModelData(String job, String separator) {
        return this.buildModelData(Identifier.fromNamespaceAndPath(Occultism.MODID, job), separator);
    }

    public record ModelData(Identifier model, Identifier modelPath, Identifier texture, Identifier animation, Identifier animationPath) {
    }

    @Override
    public void addAdditionalStateData(T animatable, @Nullable Object relatedObject, GeoRenderState renderState) {
        super.addAdditionalStateData(animatable, relatedObject, renderState);
        ModelData data = this.getModelData(animatable);
        renderState.addGeckolibData(JOB_MODEL, data.model());
        renderState.addGeckolibData(JOB_TEXTURE, data.texture());
        renderState.addGeckolibData(JOB_ANIMATION, data.animation());

        if (renderState instanceof OccultismGeoLivingEntityRenderState livingEntityRenderState) {
            renderState.addGeckolibData(HEAD_YAW, livingEntityRenderState.yRot * Mth.DEG_TO_RAD);
            renderState.addGeckolibData(HEAD_PITCH, -livingEntityRenderState.xRot * Mth.DEG_TO_RAD);
        }
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
