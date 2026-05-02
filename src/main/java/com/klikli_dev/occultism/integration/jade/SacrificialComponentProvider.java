package com.klikli_dev.occultism.integration.jade;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.TranslationKeys;
import com.klikli_dev.occultism.client.misc.ClientPentacleManager;
import com.klikli_dev.occultism.common.blockentity.GoldenSacrificialBowlBlockEntity;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
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
        if (blockAccessor.getBlockEntity() instanceof GoldenSacrificialBowlBlockEntity goldenSacrificialBowlBlockEntity) {
            if (goldenSacrificialBowlBlockEntity.getCurrentRitualRecipe() != null && goldenSacrificialBowlBlockEntity.getCurrentRitualRecipe().value() instanceof RitualRecipe recipe && goldenSacrificialBowlBlockEntity.ritualActive) {

                String ritualID = I18n.get("item.occultism.ritual_dummy." + goldenSacrificialBowlBlockEntity.getCurrentRitualRecipe().id().identifier().getPath().substring(7));
                String ritualName = Component.translatable(ritualID).getString();
                int i = ritualName.indexOf(":");
                iTooltip.add(Component.translatable("occultism.jade.current_ritual", Component.literal(ritualName.substring(i + 2)).withStyle(ChatFormatting.GREEN)).withStyle(ChatFormatting.WHITE));
                if (!goldenSacrificialBowlBlockEntity.sacrificeFulfilled()) {
                    iTooltip.add(Component.translatable("occultism.jade.no_sacrifice").withStyle(ChatFormatting.RED));
                    iTooltip.add(Component.empty().append("-> ")
                            .append(Component.translatable(recipe.getEntityToSacrificeDisplayName()))
                            .withStyle(ChatFormatting.RED));
                }
                if (!goldenSacrificialBowlBlockEntity.itemUseFulfilled()) {
                    ItemStack[] stacks = goldenSacrificialBowlBlockEntity.currentRitualRecipe.value().getItemToUse().items().map(holder -> new ItemStack(holder.value())).toArray(ItemStack[]::new);
                    int index = stacks.length == 1 ? 0 : (int) (System.currentTimeMillis() / 1000) % stacks.length;
                    iTooltip.add(Component.translatable("occultism.jade.no_item_use").withStyle(ChatFormatting.RED));
                    iTooltip.add(Component.empty().append("-> ")
                            .append(stacks[index].getDisplayName())
                            .withStyle(ChatFormatting.RED));
                }
                return;
            }
            ClientPentacleManager.rebuild(blockAccessor.getPosition());
            if (!ClientPentacleManager.lastPentacles.isEmpty()) {
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
    public Identifier getUid() {
        return Identifier.fromNamespaceAndPath(Occultism.MODID, "sacrificial");
    }
}
