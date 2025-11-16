package com.klikli_dev.occultism.integration.waila;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.TranslationKeys;
import com.klikli_dev.occultism.client.misc.ClientPentacleManager;
import com.klikli_dev.occultism.common.blockentity.GoldenSacrificialBowlBlockEntity;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class SacrificialComponentProvider implements IBlockComponentProvider {
    public static SacrificialComponentProvider INSTANCE;
    static {
        INSTANCE = new SacrificialComponentProvider();
    }
    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if(blockAccessor.getBlockEntity() instanceof GoldenSacrificialBowlBlockEntity goldenSacrificialBowlBlockEntity) {
            if(goldenSacrificialBowlBlockEntity.getCurrentRitualRecipe()!=null && goldenSacrificialBowlBlockEntity.getCurrentRitualRecipe().value() instanceof RitualRecipe recipe && goldenSacrificialBowlBlockEntity.ritualActive) {

                String ritualID = I18n.get("item.occultism.ritual_dummy." + goldenSacrificialBowlBlockEntity.getCurrentRitualRecipe().id().getPath().substring(7));
                String ritualName = Component.translatable(ritualID).getString();
                int i = ritualName.indexOf(":");
                iTooltip.add(Component.translatable("occultism.waila.current_ritual", Component.literal(ritualName.substring(i+2)).withStyle(ChatFormatting.GREEN)).withStyle(ChatFormatting.WHITE));
                if (!goldenSacrificialBowlBlockEntity.sacrificeFulfilled()) {
                    iTooltip.add(Component.translatable("occultism.waila.no_sacrifice").withStyle(ChatFormatting.RED));
                    iTooltip.add(Component.empty().append("-> ")
                            .append(Component.translatable(recipe.getEntityToSacrificeDisplayName()))
                            .withStyle(ChatFormatting.RED));
                }
                if (!goldenSacrificialBowlBlockEntity.itemUseFulfilled()) {
                    iTooltip.add(Component.translatable("occultism.waila.no_item_use").withStyle(ChatFormatting.RED));
                    iTooltip.add(Component.empty().append("-> ")
                            .append(Component.translatable(recipe.getItemToUse().getItems()[0].getDisplayName().getString()))
                            .withStyle(ChatFormatting.RED));
                }
                return;
            }
            ClientPentacleManager.rebuild(blockAccessor.getPosition());
            if(!ClientPentacleManager.lastPentacles.isEmpty()){
                iTooltip.add(Component.translatable(TranslationKeys.HUD_PENTACLE_FOUND));
                for (var text : ClientPentacleManager.lastPentacles) {
                    iTooltip.add(text);
                }
            } else {
                iTooltip.add(ClientPentacleManager.noPentacleFound.withStyle(ChatFormatting.YELLOW));
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "sacrificial");
    }
}
