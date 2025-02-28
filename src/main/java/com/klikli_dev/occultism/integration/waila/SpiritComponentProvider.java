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
        if (entityAccessor != null && entityAccessor.getEntity() instanceof SpiritEntity spiritEntity) {
            int maxAge = spiritEntity.getSpiritMaxAge();
            int age = spiritEntity.getSpiritAge();

            // Determine spirit type and add appropriate tooltip
            String spiritType = getSpiritType(spiritEntity);
            if (spiritType != null) {
                if (maxAge != -1) {
                    iTooltip.add(Component.translatable("occultism.waila." + spiritType + "_age", maxAge - age));
                } else {
                    iTooltip.add(Component.translatable("occultism.waila." + spiritType));
                }
            }

            // Add job information if available
            if (spiritEntity.getJobID() != null && !spiritEntity.getJobID().isEmpty()) {
                String job = spiritEntity.getJobID().split(":", 2)[1];
                iTooltip.add(Component.translatable("job.occultism." + job));
            }
        }
    }

    private String getSpiritType(SpiritEntity spiritEntity) {
        if (spiritEntity instanceof FoliotEntity) return "foliot";
        if (spiritEntity instanceof DjinniEntity) return "djinni";
        if (spiritEntity instanceof AfritEntity) return "afrit";
        if (spiritEntity instanceof MaridEntity) return "marid";
        return null;
    }

    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "foliot");
    }
}
