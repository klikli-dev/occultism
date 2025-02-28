package com.klikli_dev.occultism.integration.waila;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.common.entity.spirit.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class SpiritComponentProvider implements IEntityComponentProvider {
    public static final SpiritComponentProvider INSTANCE = new SpiritComponentProvider();

    @Override
    public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {

        if(entityAccessor!=null && entityAccessor.getEntity() instanceof SpiritEntity spiritEntity) {
            int maxAge = spiritEntity.getSpiritMaxAge();
            int age = spiritEntity.getSpiritAge();
            if (entityAccessor.getEntity() instanceof FoliotEntity ) {
                if (maxAge != -1) {
                    iTooltip.add(Component.translatable("occultism.waila.foliot_age", maxAge - age));
                } else {
                    iTooltip.add(Component.translatable("occultism.waila.foliot"));
                }
            }
            if (entityAccessor.getEntity() instanceof DjinniEntity) {
                if (maxAge != -1) {
                    iTooltip.add(Component.translatable("occultism.waila.djinni_age", maxAge - age));
                } else {
                    iTooltip.add(Component.translatable("occultism.waila.djinni"));
                }
            }
            if (entityAccessor.getEntity() instanceof AfritEntity) {
                if (maxAge != -1) {
                    iTooltip.add(Component.translatable("occultism.waila.afrit_age", maxAge - age));
                } else {
                    iTooltip.add(Component.translatable("occultism.waila.afrit"));
                }
            }
            if (entityAccessor.getEntity() instanceof MaridEntity ) {
                if (maxAge != -1) {
                    iTooltip.add(Component.translatable("occultism.waila.marid_age", maxAge - age));
                } else {
                    iTooltip.add(Component.translatable("occultism.waila.marid"));
                }
            }

            if (spiritEntity.getJobID() != null && !spiritEntity.getJobID().isEmpty()) {
                String job = spiritEntity.getJobID().split(":", 2)[1];
                iTooltip.add(Component.translatable("job.occultism." + job));
            }

        }
    }

    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "foliot");
    }
}
