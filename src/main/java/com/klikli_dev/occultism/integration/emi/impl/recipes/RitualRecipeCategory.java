package com.klikli_dev.occultism.integration.emi.impl.recipes;

import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.ConditionWrapperFactory;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.OccultismConditionContext;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.RitualRecipeConditionDescriptionVisitor;
import com.klikli_dev.occultism.integration.emi.impl.OccultismEmiPlugin;
import com.klikli_dev.occultism.integration.emi.impl.render.ItemWidget;
import com.klikli_dev.occultism.registry.*;
import com.mojang.datafixers.util.Pair;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.SlotWidget;
import dev.emi.emi.api.widget.TextWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTextTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RitualRecipeCategory implements EmiRecipe {
    private final RitualRecipe recipe;
    private final ResourceLocation id;
    private final List<Pair<Integer, Integer>> infoTextSlots = new ArrayList<>();

    public RitualRecipeCategory(RecipeHolder<RitualRecipe> recipe) {
        this.recipe = recipe.value();
        this.id=recipe.id();

        this.infoTextSlots.add(new Pair<>(90, 0));
        this.infoTextSlots.add(new Pair<>(90, 18));
        this.infoTextSlots.add(new Pair<>(108, 0));
        this.infoTextSlots.add(new Pair<>(108, 18));
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return OccultismEmiPlugin.RITUAL_CATEGORY;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        List<EmiIngredient> inputs = recipe.getIngredients().stream().map(EmiIngredient::of).collect(Collectors.toCollection(ArrayList::new));
        inputs.add(EmiIngredient.of(recipe.getActivationItem()));
        return inputs;
    }

    @Override
    public List<EmiStack> getOutputs() {
        List<EmiStack> outputs = new ArrayList<>();
        outputs.add(EmiStack.of(recipe.getResultItem(Minecraft.getInstance().level.registryAccess())));
        if(recipe.getEntityToSummon()!=null) {
            for(SpawnEggItem egg:SpawnEggItem.eggs()) {
                if(egg.getType(new ItemStack(egg)).equals(recipe.getEntityToSummon())) {
                    outputs.add(EmiStack.of(egg));
                }
            }
            extraItems(recipe.getEntityToSummon().getDefaultLootTable().toString(), outputs);
        }

        if(recipe.getRitualType().toString().contains("repair")){
            for(ItemStack item:recipe.getActivationItemStack()){
                outputs.add(EmiStack.of(item));
            }
        }

        outputs.add(EmiStack.of(recipe.getRitualDummy()));
        return outputs;
    }

    @Override
    public int getDisplayWidth() {
        return 134;
    }

    @Override
    public int getDisplayHeight() {
        return 100;
    }

    public void extraItems(String mob, List<EmiStack> list){
        if(mob.contains("possessed_breeze")) {
            list.add(EmiStack.of(Items.BREEZE_ROD));
            list.add(EmiStack.of(Items.BOLT_ARMOR_TRIM_SMITHING_TEMPLATE));
            list.add(EmiStack.of(Items.GUSTER_BANNER_PATTERN));
            list.add(EmiStack.of(Items.MUSIC_DISC_PRECIPICE));
        }
        if(mob.contains("possessed_elder_guardian")) {
            list.add(EmiStack.of(Items.NAUTILUS_SHELL));
            list.add(EmiStack.of(Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE));
            list.add(EmiStack.of(Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE));
            list.add(EmiStack.of(Items.WET_SPONGE));
            list.add(EmiStack.of(Items.TROPICAL_FISH));
            list.add(EmiStack.of(Items.COD));
            list.add(EmiStack.of(Items.SALMON));
            list.add(EmiStack.of(Items.PUFFERFISH));
            list.add(EmiStack.of(Items.COOKED_COD));
            list.add(EmiStack.of(Items.COOKED_SALMON));
            list.add(EmiStack.of(Items.PRISMARINE_SHARD));
            list.add(EmiStack.of(Items.PRISMARINE_CRYSTALS));
        }
        if(mob.contains("possessed_enderman")) {
            list.add(EmiStack.of(Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE));
        }
        if(mob.contains("possessed_evoker")) {
            list.add(EmiStack.of(Items.OMINOUS_BOTTLE));
            list.add(EmiStack.of(Items.VEX_ARMOR_TRIM_SMITHING_TEMPLATE));
            list.add(EmiStack.of(Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE));
        }
        if(mob.contains("possessed_ghast")) {
            list.add(EmiStack.of(Items.GUNPOWDER));
        }
        if(mob.contains("possessed_hoglin")) {
            list.add(EmiStack.of(Items.NETHERITE_SCRAP));
            list.add(EmiStack.of(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE));
            list.add(EmiStack.of(Items.PIGLIN_BANNER_PATTERN));
            list.add(EmiStack.of(Items.NETHER_BRICK));
        }
        if(mob.contains("possessed_shulker")) {
            list.add(EmiStack.of(Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE));
        }
        if(mob.contains("possessed_skeleton")) {
            list.add(EmiStack.of(Items.BONE));
            list.add(EmiStack.of(Items.ARROW));
        }
        if(mob.contains("possessed_strong_breeze")) {
            list.add(EmiStack.of(Items.FLOW_BANNER_PATTERN));
            list.add(EmiStack.of(Items.FLOW_POTTERY_SHERD));
            list.add(EmiStack.of(Items.FLOW_ARMOR_TRIM_SMITHING_TEMPLATE));
            list.add(EmiStack.of(Items.MUSIC_DISC_CREATOR));
        }
        if(mob.contains("possessed_warden")) {
            list.add(EmiStack.of(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE));
            list.add(EmiStack.of(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE));
            list.add(EmiStack.of(Items.MUSIC_DISC_OTHERSIDE));
            list.add(EmiStack.of(Items.DISC_FRAGMENT_5));
        }
        if(mob.contains("possessed_weak_breeze")) {
            list.add(EmiStack.of(Items.OMINOUS_BOTTLE));
            list.add(EmiStack.of(Items.MUSIC_DISC_CREATOR_MUSIC_BOX));
            list.add(EmiStack.of(Items.SCRAPE_POTTERY_SHERD));
            list.add(EmiStack.of(Items.GUSTER_POTTERY_SHERD));
        }
        if(mob.contains("possessed_weak_shulker")) {
            list.add(EmiStack.of(Items.SHULKER_SHELL));
        }
        if(mob.contains("possessed_witch")) {
            list.add(EmiStack.of(Items.OMINOUS_BOTTLE));
            list.add(EmiStack.of(Items.HONEY_BOTTLE));
            list.add(EmiStack.of(Items.POTION));
        }
        if(mob.contains("wild_hunt")) {
            list.add(EmiStack.of(Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE));
            list.add(EmiStack.of(Items.COAL));
            list.add(EmiStack.of(Items.BONE));
            list.add(EmiStack.of(Items.ARROW));
        }
        if(mob.contains("horde_creeper")) {
            list.add(EmiStack.of(Items.MUSIC_DISC_13));
            list.add(EmiStack.of(Items.MUSIC_DISC_BLOCKS));
            list.add(EmiStack.of(Items.MUSIC_DISC_CHIRP));
            list.add(EmiStack.of(Items.MUSIC_DISC_FAR));
            list.add(EmiStack.of(Items.MUSIC_DISC_MALL));
            list.add(EmiStack.of(Items.MUSIC_DISC_MELLOHI));
            list.add(EmiStack.of(Items.MUSIC_DISC_STAL));
            list.add(EmiStack.of(Items.MUSIC_DISC_STRAD));
            list.add(EmiStack.of(Items.MUSIC_DISC_WARD));
            list.add(EmiStack.of(Items.MUSIC_DISC_11));
            list.add(EmiStack.of(Items.MUSIC_DISC_WAIT));
        }
        if(mob.contains("horde_drowned")) {
            list.add(EmiStack.of(Items.TRIDENT));
            list.add(EmiStack.of(Items.TURTLE_EGG));
            list.add(EmiStack.of(Items.SHELTER_POTTERY_SHERD));
            list.add(EmiStack.of(Items.SNORT_POTTERY_SHERD));
            list.add(EmiStack.of(Items.ANGLER_POTTERY_SHERD));
            list.add(EmiStack.of(Items.PLENTY_POTTERY_SHERD));
            list.add(EmiStack.of(Items.BLADE_POTTERY_SHERD));
            list.add(EmiStack.of(Items.EXPLORER_POTTERY_SHERD));
            list.add(EmiStack.of(Items.MOURNER_POTTERY_SHERD));
        }
        if(mob.contains("horde_husk")) {
            list.add(EmiStack.of(Items.SKULL_POTTERY_SHERD));
            list.add(EmiStack.of(Items.ARCHER_POTTERY_SHERD));
            list.add(EmiStack.of(Items.PRIZE_POTTERY_SHERD));
            list.add(EmiStack.of(Items.MINER_POTTERY_SHERD));
            list.add(EmiStack.of(Items.BREWER_POTTERY_SHERD));
            list.add(EmiStack.of(Items.ARMS_UP_POTTERY_SHERD));
        }
        if(mob.contains("horde_silverfish")) {
            list.add(EmiStack.of(Items.HEART_POTTERY_SHERD));
            list.add(EmiStack.of(Items.SHEAF_POTTERY_SHERD));
            list.add(EmiStack.of(Items.DANGER_POTTERY_SHERD));
            list.add(EmiStack.of(Items.BURN_POTTERY_SHERD));
            list.add(EmiStack.of(Items.HOWL_POTTERY_SHERD));
            list.add(EmiStack.of(Items.FRIEND_POTTERY_SHERD));
            list.add(EmiStack.of(Items.HEARTBREAK_POTTERY_SHERD));
            list.add(EmiStack.of(Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE));
            list.add(EmiStack.of(Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE));
            list.add(EmiStack.of(Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE));
            list.add(EmiStack.of(Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE));
        }
    }

    @Override
    public void addWidgets(WidgetHolder widgetHolder) {
        int sacrificialCircleRadius = 30;
        int sacricialBowlPaddingVertical = 20;
        int sacricialBowlPaddingHorizontal = 15;
        int ritualCenterX = this.getDisplayWidth() / 2 - 18 / 2 - 30;
        int ritualCenterY = this.getDisplayHeight() / 2 - 18 / 2 + 10;
        List<Vec3i> sacrificialBowlPosition = Stream.of(
                //first the 4 centers of each side
                new Vec3i(ritualCenterX, ritualCenterY - sacrificialCircleRadius, 0),
                new Vec3i(ritualCenterX + sacrificialCircleRadius, ritualCenterY, 0),
                new Vec3i(ritualCenterX, ritualCenterY + sacrificialCircleRadius, 0),
                new Vec3i(ritualCenterX - sacrificialCircleRadius, ritualCenterY, 0),

                //then clockwise of the enter the next 4
                new Vec3i(ritualCenterX + sacricialBowlPaddingHorizontal,
                        ritualCenterY - sacrificialCircleRadius,
                        0),
                new Vec3i(ritualCenterX + sacrificialCircleRadius,
                        ritualCenterY - sacricialBowlPaddingVertical, 0),
                new Vec3i(ritualCenterX - sacricialBowlPaddingHorizontal,
                        ritualCenterY + sacrificialCircleRadius,
                        0),
                new Vec3i(ritualCenterX - sacrificialCircleRadius,
                        ritualCenterY + sacricialBowlPaddingVertical, 0),

                //then counterclockwise of the center the last 4
                new Vec3i(ritualCenterX - sacricialBowlPaddingHorizontal,
                        ritualCenterY - sacrificialCircleRadius,
                        0),
                new Vec3i(ritualCenterX + sacrificialCircleRadius,
                        ritualCenterY + sacricialBowlPaddingVertical, 0),
                new Vec3i(ritualCenterX + sacricialBowlPaddingHorizontal,
                        ritualCenterY + sacrificialCircleRadius,
                        0),
                new Vec3i(ritualCenterX - sacrificialCircleRadius,
                        ritualCenterY - sacricialBowlPaddingVertical, 0)
        ).collect(Collectors.toList());

        //recipe.requiresItemUse()
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            Vec3i pos = sacrificialBowlPosition.get(i);
            SlotWidget slotWidget = new SlotWidget(EmiIngredient.of(recipe.getIngredients().get(i)), pos.getX(), pos.getY() - 5);
            slotWidget.drawBack(false);
            widgetHolder.add(slotWidget);
            ItemWidget bowlWidget = new ItemWidget(EmiStack.of(OccultismBlocks.SACRIFICIAL_BOWL.get()), pos.getX(), pos.getY());
            widgetHolder.add(bowlWidget);
        }

        SlotWidget activationItemSlot = new SlotWidget(EmiIngredient.of(recipe.getActivationItem()), ritualCenterX, ritualCenterY - 5);
        activationItemSlot.drawBack(false);
        widgetHolder.add(activationItemSlot);
        ItemWidget bowlWidget = new ItemWidget(EmiStack.of(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get()), ritualCenterX, ritualCenterY);
        widgetHolder.add(bowlWidget);

        widgetHolder.addTexture(EmiTexture.EMPTY_ARROW, 80, 70);
        if (getOutputs().get(0).getItemStack().getItem() != OccultismItems.JEI_DUMMY_NONE.get()) {
            widgetHolder.addSlot(getOutputs().get(0), 110, 70).recipeContext(this);

        } else {
            widgetHolder.addSlot(EmiIngredient.of(Ingredient.of(recipe.getRitualDummy())), 110, 70);
        }

        widgetHolder.addSlot(EmiIngredient.of(Ingredient.of(recipe.getRitualDummy())), 82, 53).drawBack(false);

        int time = recipe.getDuration();
        widgetHolder.addText(Component.translatable("emi.occultism.ritual_duration", time), 129, 90, -1, true).horizontalAlign(TextWidget.Alignment.END);

        int infotextY = 0;
        int infoTextIndex = 0;
        var pentacle = ModonomiconAPI.get().getMultiblock(recipe.getPentacleId());

        if (pentacle != null) {
            var pentacleName = Minecraft.getInstance().font.split(Component.translatable(Util.makeDescriptionId("multiblock", pentacle.getId())), 150);

            for (var line : pentacleName) {
                widgetHolder.addText(line, getDisplayWidth() / 2, infotextY, -1, true).horizontalAlign(TextWidget.Alignment.CENTER);
                infotextY += Minecraft.getInstance().font.lineHeight;
            }
        } else {
            widgetHolder.addText(Component.translatable("jei.occultism.error.pentacle_not_loaded"), getDisplayWidth() / 2, 0, -1, true).horizontalAlign(TextWidget.Alignment.CENTER);
        }
        if (recipe.requiresSacrifice()) {
            var infoSlot = this.infoTextSlots.get(infoTextIndex++);
            ItemWidget knife = new ItemWidget(EmiStack.of(OccultismItems.BUTCHER_KNIFE.get()), infoSlot.getFirst(), infoSlot.getSecond() + infotextY);

            knife.tooltip((mouseX, mouseY) ->
            {
                List<ClientTooltipComponent> tooltip = new ArrayList<>();
                tooltip.add(new ClientTextTooltip(Component.translatable("jei.occultism.sacrifice", Component.translatable(recipe.getEntityToSacrificeDisplayName())).getVisualOrderText()));
                return tooltip;
            });
            widgetHolder.add(knife);
        }

        if(recipe.requiresItemUse()) {
            var infoSlot = this.infoTextSlots.get(infoTextIndex++);
            ItemWidget itemToUse = new ItemWidget(EmiStack.of(recipe.getItemToUse().getItems()[0]),infoSlot.getFirst(), infoSlot.getSecond() + infotextY);
            itemToUse.tooltip((mouseX, mouseY) ->
            {
                List<ClientTooltipComponent> tooltip = new ArrayList<>();
                tooltip.add(new ClientTextTooltip(Component.translatable("emi.occultism.item_to_use", Component.translatable(recipe.getItemToUse().getItems()[0].getDescriptionId())).getVisualOrderText()));
                return tooltip;
            });

            widgetHolder.add(itemToUse);
        }
        if (recipe.getEntityToSummon() != null) {
            var infoSlot = this.infoTextSlots.get(infoTextIndex++);
            widgetHolder.addTexture(new EmiTexture(OccultismEmiPlugin.EMI_WIDGETS, 16, 16, 16, 16), infoSlot.getFirst(), infoSlot.getSecond() + infotextY).tooltip((mouseX, mouseY) ->
            {
                List<ClientTooltipComponent> tooltip = new ArrayList<>();
                tooltip.add(new ClientTextTooltip(Component.translatable("jei.occultism.summon", Component.translatable(recipe.getEntityToSummon().getDescriptionId())).getVisualOrderText()));
                if(recipe.getSpiritJobType()!=null) {
                    tooltip.add(new ClientTextTooltip(Component.translatable("jei.occultism.job",
                            Component.translatable("job." + recipe.getSpiritJobType().toString().replace(":", "."))).getVisualOrderText()));
                }
                return tooltip;
            });

            List<EmiStack> drops = new ArrayList<>();
            extraItems(recipe.getEntityToSummon().getDefaultLootTable().toString(), drops);
            if(!drops.isEmpty()) {
                widgetHolder.addSlot(EmiIngredient.of(drops), 110, 52);
            }
        }

        if(recipe.getCondition() != null){
            var infoSlot = this.infoTextSlots.get(infoTextIndex++);
            widgetHolder.addTexture(ResourceLocation.fromNamespaceAndPath(Occultism.MODID, "textures/gui/checklist.png"), infoSlot.getFirst(), infoSlot.getSecond() + infotextY, 16, 16, 0, 0, 64, 64, 64, 64).tooltip((mouseX, mouseY) ->
            {
                List<ClientTooltipComponent> tooltip = new ArrayList<>();
                var visitor = new RitualRecipeConditionDescriptionVisitor();
                var condition = ConditionWrapperFactory.wrap(recipe.getCondition());
                if(condition!=null) {
                    tooltip.add(new ClientTextTooltip(condition.accept(visitor, OccultismConditionContext.EMPTY).getVisualOrderText()));
                }
                return tooltip;
            });
        }


//
//        if (recipe.requiresItemUse()) {
//            widgetHolder.addText(Component.translatable("jei.occultism.item_to_use"), infoTextX, infotextY, -1, false);
//            int itemToUseY = infotextY - 5;
//            widgetHolder.addSlot(EmiIngredient.of(Ingredient.of(recipe.getItemToUse().getItems())), infoTextX, itemToUseY).drawBack(false);
//            infotextY += lineHeight;
//        }
    }
}