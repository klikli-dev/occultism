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
import snownee.jade.api.ui.IElement;

public class SacrificialComponentProvider implements IBlockComponentProvider {
public static SacrificialComponentProvider INSTANCE;
    static {
        INSTANCE = new SacrificialComponentProvider();
    }
    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if(blockAccessor.getBlockEntity() instanceof GoldenSacrificialBowlBlockEntity goldenSacrificialBowlBlockEntity) {
            if(goldenSacrificialBowlBlockEntity.getCurrentRitualRecipe()!=null && goldenSacrificialBowlBlockEntity.getCurrentRitualRecipe().value() instanceof RitualRecipe recipe && goldenSacrificialBowlBlockEntity.ritualActive) {

                String ritualName = I18n.get("item.occultism.ritual_dummy." + goldenSacrificialBowlBlockEntity.getCurrentRitualRecipe().id().getPath().substring(7));

                    iTooltip.add(Component.translatable("occultism.waila.current_ritual", Component.translatable(ritualName).withStyle(ChatFormatting.GREEN)).withStyle(ChatFormatting.WHITE));
                    if(!goldenSacrificialBowlBlockEntity.sacrificeFulfilled()) {
                        iTooltip.add(Component.translatable("occultism.waila.no_sacrifice").withStyle(ChatFormatting.RED));
                    }
                    if(!goldenSacrificialBowlBlockEntity.itemUseFulfilled()) {
                        iTooltip.add(Component.translatable("occultism.waila.no_item_use").withStyle(ChatFormatting.RED));
                    }
                    return;
            }
            ClientPentacleManager.rebuild(blockAccessor.getPosition());
            if(!ClientPentacleManager.lastPentacles.isEmpty()){
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
