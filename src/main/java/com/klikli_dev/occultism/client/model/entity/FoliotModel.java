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

package com.klikli_dev.occultism.client.model.entity;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.entity.spirit.FoliotEntity;
import com.klikli_dev.occultism.registry.OccultismSpiritJobs;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

import java.util.Objects;


public class FoliotModel extends DefaultedEntityGeoModel<FoliotEntity> {

    public final static String ASSET_SUBPATH = "foliot";

    public final ModelData crusher;
    public final ModelData transporter;
    public final ModelData janitor;
    public final ModelData lumberjack;
    public final ModelData saplingTrader;
    public final ModelData otherstoneTrader;

    public FoliotModel() {
        super(new ResourceLocation(Occultism.MODID, ASSET_SUBPATH), false);

        this.crusher = this.buildModelData("crusher");
        this.transporter = this.buildModelData("transporter");
        this.janitor = this.buildModelData("janitor");
        this.lumberjack = this.buildModelData("lumberjack");
        this.otherstoneTrader = this.buildModelData("otherstone_trader");
        this.saplingTrader = this.buildModelData("sapling_trader");
    }

    public ModelData getModelData(FoliotEntity animatable) {
        var job = animatable.getJobID();

        if (Objects.equals(job, OccultismSpiritJobs.TRANSPORT_ITEMS.getId().toString())) {
            return this.transporter;
        }

        if (Objects.equals(job, OccultismSpiritJobs.CLEANER.getId().toString())) {
            return this.janitor;
        }

        if (Objects.equals(job, OccultismSpiritJobs.LUMBERJACK.getId().toString())) {
            return this.lumberjack;
        }

        if (Objects.equals(job, OccultismSpiritJobs.TRADE_OTHERSTONE_T1.getId().toString())) {
            return this.otherstoneTrader;
        }

        if (Objects.equals(job, OccultismSpiritJobs.TRADE_OTHERWORLD_SAPLINGS_T2.getId().toString())) {
            return this.saplingTrader;
        }

        return this.crusher;
    }

    @Override
    public ResourceLocation getModelResource(FoliotEntity animatable) {
        return this.getModelData(animatable).model();
    }

    @Override
    public ResourceLocation getTextureResource(FoliotEntity animatable) {
        return this.getModelData(animatable).texture();
    }

    @Override
    public ResourceLocation getAnimationResource(FoliotEntity animatable) {
        return this.getModelData(animatable).animation();
    }

    public ModelData buildModelData(String job) {
        return this.buildModelData(job, "_");
    }

    public ModelData buildModelData(String job, String separator) {
        return new ModelData(
                this.buildFormattedModelPath(new ResourceLocation(Occultism.MODID, ASSET_SUBPATH + separator + job)),
                this.buildFormattedTexturePath(new ResourceLocation(Occultism.MODID, ASSET_SUBPATH + separator + job)),
                this.buildFormattedAnimationPath(new ResourceLocation(Occultism.MODID, ASSET_SUBPATH + separator + job))
        );
    }

    public record ModelData(ResourceLocation model, ResourceLocation texture, ResourceLocation animation) {
    }
}

