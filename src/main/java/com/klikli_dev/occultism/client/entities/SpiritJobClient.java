package com.klikli_dev.occultism.client.entities;

import com.klikli_dev.occultism.Occultism;
import net.minecraft.resources.ResourceLocation;


public class SpiritJobClient {
    protected ResourceLocation modelID;

    public SpiritJobClient(ResourceLocation modelID) {
        this.modelID = modelID;
    }

    public ResourceLocation modelID() {
        return modelID;
    }

    public static SpiritJobClient create(ResourceLocation modelID) {
        return new SpiritJobClient(modelID);
    }

    public static SpiritJobClient create(String modelId) {
        return create(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, modelId));
    }

    public static SpiritJobClient create() {
        return create("worker");
    }
}
