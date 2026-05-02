package com.klikli_dev.occultism.client.entities;

import com.klikli_dev.occultism.Occultism;
import net.minecraft.resources.Identifier;


public class SpiritJobClient {
    protected Identifier modelID;

    public SpiritJobClient(Identifier modelID) {
        this.modelID = modelID;
    }

    public static SpiritJobClient create(Identifier modelID) {
        return new SpiritJobClient(modelID);
    }

    public static SpiritJobClient create(String modelId) {
        return create(Identifier.fromNamespaceAndPath(Occultism.MODID, modelId));
    }

    public static SpiritJobClient create() {
        return create("worker");
    }

    public Identifier modelID() {
        return this.modelID;
    }
}
