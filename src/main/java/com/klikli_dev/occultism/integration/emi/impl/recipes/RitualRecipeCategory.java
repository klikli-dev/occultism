package com.klikli_dev.occultism.integration.emi.impl.recipes;

import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.modonomicon.client.render.MultiblockPreviewRenderer;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.ConditionWrapperFactory;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.OccultismConditionContext;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.RitualRecipeConditionDescriptionVisitor;
import com.klikli_dev.occultism.integration.emi.impl.OccultismEmiPlugin;
import com.klikli_dev.occultism.integration.emi.impl.render.ItemWidget;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import com.mojang.datafixers.util.Pair;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.SlotWidget;
import dev.emi.emi.api.widget.TextWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTextTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
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
    private final Identifier id;
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
    public @Nullable Identifier getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        List<EmiIngredient> inputs = recipe.getIngredients().stream().map(EmiIngredient::of).collect(Collectors.toCollection(ArrayList::new));
        inputs.add(EmiIngredient.of(recipe.getActivationItem()));
        return inputs;
    }

    @Override
    public List<EmiIngredient> getCatalysts() {
        return List.of(EmiIngredient.of(recipe.getItemToUse()));
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
            extraItems(recipe.getEntityToSummon().getDefaultLootTable().location().toString()
                    .replace("occultism:entities/","")
                    .replace("minecraft:entities/","")
                    .replace("c:entities","")
                    .replace(":entities/","_"), outputs);
        }

        if(recipe.getEntityTagToSummon()!=null){
            extraSummons(recipe.getEntityTagToSummon().location().toString()
                    .replace("random_animals_","")
                    .replace("occultism:","")
                    .replace("minecraft:","")
                    .replace("c:","")
                    .replace(":","_"), outputs);
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
        if (!EmiIngredient.of(OccultismTags.makeItemTag("occultism:drop_from/" + mob)).getEmiStacks().isEmpty())
            list.addAll(EmiIngredient.of(OccultismTags.makeItemTag("occultism:drop_from/" + mob)).getEmiStacks());
    }

    public void extraSummons(String mobList, List<EmiStack> listStack){
        if (!EmiIngredient.of(OccultismTags.makeItemTag("occultism:random_spawn_from/" + mobList)).getEmiStacks().isEmpty())
            listStack.addAll(EmiIngredient.of(OccultismTags.makeItemTag("occultism:random_spawn_from/" + mobList)).getEmiStacks());
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
            widgetHolder.addButton(this.getDisplayWidth() + 5, 2, 12, 12, 244, 0,
                    OccultismEmiPlugin.EMI_WIDGETS, () -> true,
                    (mouseX, mouseY, button) -> {
                        Minecraft.getInstance().setScreen(null);
                        MultiblockPreviewRenderer.setMultiblock(pentacle,
                                Component.translatable(Util.makeDescriptionId("multiblock", pentacle.getId())), true);
                    }
            );
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
            SlotWidget itemToUse = new SlotWidget(EmiIngredient.of(Ingredient.of(recipe.getItemToUse().getItems())),infoSlot.getFirst(), infoSlot.getSecond() + infotextY);
            itemToUse.drawBack(false);
            itemToUse.appendTooltip(t ->
            {
                List<ClientTooltipComponent> tooltip = new ArrayList<>();
                tooltip.add(new ClientTextTooltip(Component.translatable("emi.occultism.item_to_use").getVisualOrderText()));
                return tooltip.getFirst();
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
            extraItems(recipe.getEntityToSummon().getDefaultLootTable().location().toString()
                    .replace("occultism:entities/","")
                    .replace("minecraft:entities/","")
                    .replace("c:entities/","")
                    .replace(":entities/","_"), drops);
            if(!drops.getFirst().isEmpty()) {
                widgetHolder.addSlot(EmiIngredient.of(drops), 110, 52);
            }
        }

        if(recipe.getEntityTagToSummon()!=null){
            List<EmiStack> eggs = new ArrayList<>();
            extraSummons(recipe.getEntityTagToSummon().location().toString()
                    .replace("random_animals_","")
                    .replace("occultism:","")
                    .replace("minecraft:","")
                    .replace("c:","")
                    .replace(":","_"), eggs);
            if(!eggs.getFirst().isEmpty()) {
                widgetHolder.addSlot(EmiIngredient.of(eggs), 110, 52);
            }
        }

        if(recipe.getCondition() != null){
            var infoSlot = this.infoTextSlots.get(infoTextIndex++);
            widgetHolder.addTexture(Identifier.fromNamespaceAndPath(Occultism.MODID, "textures/gui/checklist.png"), infoSlot.getFirst(), infoSlot.getSecond() + infotextY, 16, 16, 0, 0, 64, 64, 64, 64).tooltip((mouseX, mouseY) ->
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