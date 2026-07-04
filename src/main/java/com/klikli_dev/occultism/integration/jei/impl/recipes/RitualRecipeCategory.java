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

package com.klikli_dev.occultism.integration.jei.impl.recipes;

import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.modonomicon.client.render.MultiblockPreviewRenderer;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.ConditionWrapperFactory;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.OccultismConditionContext;
import com.klikli_dev.occultism.crafting.recipe.conditionextension.RitualRecipeConditionDescriptionVisitor;
import com.klikli_dev.occultism.integration.jei.impl.JeiRecipeTypes;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismItems;
import com.klikli_dev.occultism.registry.OccultismTags;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import com.klikli_dev.occultism.util.StringRenderHelper;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.inputs.IJeiInputHandler;
import mezz.jei.api.gui.inputs.IJeiUserInput;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Util;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.display.SlotDisplay.TagSlotDisplay;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RitualRecipeCategory implements IRecipeCategory<RecipeHolder<RitualRecipe>> {

    private final IDrawable background;
    private final IDrawable arrow;
    private final IDrawable eye;
    private final IDrawable goldenEye;
    private final IDrawable goldenSacrificialBowlDrawable;
    private final IDrawable sacrificialBowlDrawable;
    private final Component localizedName;
    private final String pentacle;
    private final ItemStack goldenSacrificialBowl = new ItemStack(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get());
    private final ItemStack sacrificialBowl = new ItemStack(OccultismBlocks.SACRIFICIAL_BOWL.get());
    private final int iconWidth = 16;
    private final int ritualCenterX;
    private final int ritualCenterY;
    private int recipeOutputOffsetX = 50;

    public RitualRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(168, 120); //64
        this.ritualCenterX = this.background.getWidth() / 2 - this.iconWidth / 2 - 30;
        this.ritualCenterY = this.background.getHeight() / 2 - this.iconWidth / 2 + 20;
        this.localizedName = Component.translatable(Occultism.MODID + ".jei.ritual");
        this.pentacle = I18n.get(Occultism.MODID + ".jei.pentacle");
//        this.goldenSacrificialBowl.getOrCreateTag().putBoolean("RenderFull", true);
//        this.sacrificialBowl.getOrCreateTag().putBoolean("RenderFull", true);
        this.arrow = guiHelper.getRecipeArrow();
        this.eye = new SpriteDrawable(Identifier.fromNamespaceAndPath(Occultism.MODID, "jei/eye"), 16, 16);
        this.goldenEye = new SpriteDrawable(Identifier.fromNamespaceAndPath(Occultism.MODID, "jei/golden_eye"), 16, 16);
        this.goldenSacrificialBowlDrawable = guiHelper.createDrawableItemStack(this.goldenSacrificialBowl);
        this.sacrificialBowlDrawable = guiHelper.createDrawableItemStack(this.sacrificialBowl);
    }

    protected int getStringCenteredMaxX(Font font, Component text, int x, int y) {
        int width = font.width(text);
        int actualX = (int) (x - width / 2.0f);
        return actualX + width;
    }

    protected void drawStringCentered(GuiGraphicsExtractor guiGraphics, Font font, Component text, int x, int y) {
        StringRenderHelper.drawString(guiGraphics, font, text, (x - font.width(text) / 2.0f), y, 0, false);
    }

    protected void drawStringCentered(GuiGraphicsExtractor guiGraphics, Font font, FormattedCharSequence text, int x, int y) {
        StringRenderHelper.drawString(guiGraphics, font, text, (x - font.width(text) / 2.0f), y, 0, false);
    }

    @Override
    public IRecipeType<RecipeHolder<RitualRecipe>> getRecipeType() {
        return JeiRecipeTypes.RITUAL;
    }

    @Override
    public Component getTitle() {
        return this.localizedName;
    }

    @Override
    public IDrawable getIcon() {
        return null;
    }

    @Override
    public int getWidth() {
        return 168;
    }

    @Override
    public int getHeight() {
        return 120;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<RitualRecipe> recipe, IFocusGroup focuses) {
        this.recipeOutputOffsetX = 75;

        //draw activation item on top of bowl
        builder.addSlot(RecipeIngredientRole.INPUT, this.ritualCenterX, this.ritualCenterY - 5)
                .add(recipe.value().getActivationItem());

        int sacrificialCircleRadius = 30;
        int sacricialBowlPaddingVertical = 20;
        int sacricialBowlPaddingHorizontal = 15;
        List<Vec3i> sacrificialBowlPosition = Stream.of(
                //first the 4 centers of each side
                new Vec3i(this.ritualCenterX, this.ritualCenterY - sacrificialCircleRadius, 0),
                new Vec3i(this.ritualCenterX + sacrificialCircleRadius, this.ritualCenterY, 0),
                new Vec3i(this.ritualCenterX, this.ritualCenterY + sacrificialCircleRadius, 0),
                new Vec3i(this.ritualCenterX - sacrificialCircleRadius, this.ritualCenterY, 0),

                //then clockwise of the enter the next 4
                new Vec3i(this.ritualCenterX + sacricialBowlPaddingHorizontal,
                        this.ritualCenterY - sacrificialCircleRadius,
                        0),
                new Vec3i(this.ritualCenterX + sacrificialCircleRadius,
                        this.ritualCenterY - sacricialBowlPaddingVertical, 0),
                new Vec3i(this.ritualCenterX - sacricialBowlPaddingHorizontal,
                        this.ritualCenterY + sacrificialCircleRadius,
                        0),
                new Vec3i(this.ritualCenterX - sacrificialCircleRadius,
                        this.ritualCenterY + sacricialBowlPaddingVertical, 0),

                //then counterclockwise of the center the last 4
                new Vec3i(this.ritualCenterX - sacricialBowlPaddingHorizontal,
                        this.ritualCenterY - sacrificialCircleRadius,
                        0),
                new Vec3i(this.ritualCenterX + sacrificialCircleRadius,
                        this.ritualCenterY + sacricialBowlPaddingVertical, 0),
                new Vec3i(this.ritualCenterX + sacricialBowlPaddingHorizontal,
                        this.ritualCenterY + sacrificialCircleRadius,
                        0),
                new Vec3i(this.ritualCenterX - sacrificialCircleRadius,
                        this.ritualCenterY - sacricialBowlPaddingVertical, 0)
        ).collect(Collectors.toList());


        for (int i = 0; i < recipe.value().getIngredients().size(); i++) {
            Vec3i pos = sacrificialBowlPosition.get(i);

            builder.addSlot(RecipeIngredientRole.INPUT, pos.getX(), pos.getY() - 5)
                    .add(recipe.value().getIngredients().get(i));
        }

        //ingredients: 0: recipe output, 1: ritual dummy item

        //draw recipe output on the left
        if (!recipe.value().getResult().isEmpty() && recipe.value().getResult().getItem() != OccultismItems.JEI_DUMMY_NONE.get()) {
            //if we have an item output -> render it
            builder.addSlot(RecipeIngredientRole.OUTPUT, this.ritualCenterX + this.recipeOutputOffsetX, this.ritualCenterY - 5)
                    .add(recipe.value().getResult());
        } else {
            //if not, we instead render our ritual dummy item, just like in the corner
            builder.addSlot(RecipeIngredientRole.OUTPUT, this.ritualCenterX + this.recipeOutputOffsetX, this.ritualCenterY - 5)
                    .add(recipe.value().getRitualDummy());
        }

        if (recipe.value().getEntityToSummon() != null) {
            recipe.value().getEntityToSummon().getDefaultLootTable()
                    .map(key -> key.identifier().toString()
                            .replace("occultism:entities/", "")
                            .replace("minecraft:entities/", "")
                            .replace("c:entities/", "")
                            .replace(":entities/", "_"))
                    .ifPresent(mob -> builder.addSlot(RecipeIngredientRole.OUTPUT,
                                    this.ritualCenterX + this.recipeOutputOffsetX, this.ritualCenterY - 25)
                            .add(new TagSlotDisplay(
                                    OccultismTags.makeItemTag("occultism:drop_from/" + mob))));
        }
        if (recipe.value().getEntityTagToSummon() != null) {
            var mob = recipe.value().getEntityTagToSummon().location().toString()
                    .replace("random_animals_", "")
                    .replace("occultism:", "")
                    .replace("minecraft:", "")
                    .replace("c:", "")
                    .replace(":", "_");
            builder.addSlot(RecipeIngredientRole.OUTPUT, this.ritualCenterX + this.recipeOutputOffsetX, this.ritualCenterY - 25)
                    .add(new TagSlotDisplay(OccultismTags.makeItemTag("occultism:random_spawn_from/" + mob)));
        }

        //draw ritual dummy item in upper left corner
        builder.addSlot(RecipeIngredientRole.OUTPUT, 0, 0)
                .add(recipe.value().getRitualDummy());

        //draw flame of automation item with NBT outside screen for ae2 compat
        ItemStack flame = new ItemStack(OccultismItems.FLAME_AUTOMATION.get());
        ItemNBTUtil.setBoundSpiritName(flame,
                recipe.value().getRitualDummy().toString().substring(2).replace("occultism:ritual_dummy/", ""));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 10000, 0)
                .add(flame);

        //draw item to use
        if (recipe.value().requiresItemUse()) {

            int infotextY = 0;

            int lineHeight = Minecraft.getInstance().font.lineHeight;
            var pentacle = ModonomiconAPI.get().getMultiblock(recipe.value().getPentacleId());

            //simulate pentacle id rendering, to get the correct Y level to draw at
            if (pentacle != null) {
                var pentacleName = Minecraft.getInstance().font.split(Component.translatable(Util.makeDescriptionId("multiblock", pentacle.getId())), 150);

                for (var line : pentacleName) {
                    //Don't actually draw
                    //this.drawStringCentered(poseStack, Minecraft.getInstance().font,  line , infoTextX, infotextY);
                    infotextY += lineHeight;
                }
            }

            //also simulate the info rendered before the item to use for the y level.
            if (recipe.value().requiresSacrifice()) {
                infotextY += lineHeight;
            }

            int itemToUseY = infotextY - 5;
            int infoTextX = 94;
            int itemToUseX = this.getStringCenteredMaxX(Minecraft.getInstance().font, Component.translatable("jei.occultism.item_to_use"), infoTextX, infotextY);

            builder.addSlot(RecipeIngredientRole.INPUT, itemToUseX, itemToUseY)
                    .add(recipe.value().getItemToUse());
        }
    }

    @Override
    public void draw(RecipeHolder<RitualRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        this.arrow.draw(guiGraphics, this.ritualCenterX + this.recipeOutputOffsetX - 23, this.ritualCenterY - 1);

        this.goldenSacrificialBowlDrawable.draw(guiGraphics, this.ritualCenterX, this.ritualCenterY);
        this.goldenSacrificialBowlDrawable.draw(guiGraphics, this.ritualCenterX + this.recipeOutputOffsetX, this.ritualCenterY);

        int sacrificialCircleRadius = 30;
        int sacricialBowlPaddingVertical = 20;
        int sacricialBowlPaddingHorizontal = 15;
        Stream.of(
                        new Vec3i(this.ritualCenterX, this.ritualCenterY - sacrificialCircleRadius, 0),
                        new Vec3i(this.ritualCenterX + sacrificialCircleRadius, this.ritualCenterY, 0),
                        new Vec3i(this.ritualCenterX, this.ritualCenterY + sacrificialCircleRadius, 0),
                        new Vec3i(this.ritualCenterX - sacrificialCircleRadius, this.ritualCenterY, 0),
                        new Vec3i(this.ritualCenterX + sacricialBowlPaddingHorizontal,
                                this.ritualCenterY - sacrificialCircleRadius, 0),
                        new Vec3i(this.ritualCenterX + sacrificialCircleRadius,
                                this.ritualCenterY - sacricialBowlPaddingVertical, 0),
                        new Vec3i(this.ritualCenterX - sacricialBowlPaddingHorizontal,
                                this.ritualCenterY + sacrificialCircleRadius, 0),
                        new Vec3i(this.ritualCenterX - sacrificialCircleRadius,
                                this.ritualCenterY + sacricialBowlPaddingVertical, 0),
                        new Vec3i(this.ritualCenterX - sacricialBowlPaddingHorizontal,
                                this.ritualCenterY - sacrificialCircleRadius, 0),
                        new Vec3i(this.ritualCenterX + sacrificialCircleRadius,
                                this.ritualCenterY + sacricialBowlPaddingVertical, 0),
                        new Vec3i(this.ritualCenterX + sacricialBowlPaddingHorizontal,
                                this.ritualCenterY + sacrificialCircleRadius, 0),
                        new Vec3i(this.ritualCenterX - sacrificialCircleRadius,
                                this.ritualCenterY - sacricialBowlPaddingVertical, 0)
                ).limit(recipe.value().getIngredients().size())
                .forEach(pos -> this.sacrificialBowlDrawable.draw(guiGraphics, pos.getX(), pos.getY()));

        this.eye.draw(guiGraphics, 2, 120 - 18);

        int infotextY = 0;
        int infoTextX = 94;
        int lineHeight = Minecraft.getInstance().font.lineHeight;
        var pentacle = ModonomiconAPI.get().getMultiblock(recipe.value().getPentacleId());
        if (pentacle != null) {
            var pentacleName = Minecraft.getInstance().font.split(Component.translatable(Util.makeDescriptionId("multiblock", pentacle.getId())), 150);

            for (var line : pentacleName) {
                this.drawStringCentered(guiGraphics, Minecraft.getInstance().font,
                        line, infoTextX, infotextY);
                infotextY += lineHeight;
            }
        } else {
            this.drawStringCentered(guiGraphics, Minecraft.getInstance().font,
                    Component.translatable("jei.occultism.error.pentacle_not_loaded"), infoTextX, 0);
        }

        if (recipe.value().requiresSacrifice()) {
            this.drawStringCentered(guiGraphics, Minecraft.getInstance().font,
                    Component.translatable("jei.occultism.sacrifice", Component.translatable(recipe.value().getEntityToSacrificeDisplayName())), infoTextX, infotextY);
            infotextY += lineHeight;
        }

        if (recipe.value().requiresItemUse()) {
            this.drawStringCentered(guiGraphics, Minecraft.getInstance().font, Component.translatable("jei.occultism.item_to_use"), infoTextX, infotextY);
            infotextY += lineHeight;
        }

        if (recipe.value().getEntityToSummon() != null) {
            this.drawStringCentered(guiGraphics, Minecraft.getInstance().font,
                    Component.translatable("jei.occultism.summon", Component.translatable(recipe.value().getEntityToSummon().getDescriptionId())),
                    infoTextX, infotextY);
            infotextY += lineHeight;
        }

        if (recipe.value().getSpiritJobType() != null) {
            this.drawStringCentered(guiGraphics, Minecraft.getInstance().font,
                    Component.translatable("jei.occultism.job",
                            Component.translatable("job." + recipe.value().getSpiritJobType().toString().replace(":", "."))),
                    infoTextX, infotextY);
        }

        if (recipe.value().getCondition() != null) {
            var visitor = new RitualRecipeConditionDescriptionVisitor();
            var condition = ConditionWrapperFactory.wrap(recipe.value().getCondition());
            if (condition != null) {
                this.drawStringCentered(guiGraphics, Minecraft.getInstance().font,
                        condition.accept(visitor, OccultismConditionContext.EMPTY),
                        infoTextX, infotextY);
            }
        }
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, RecipeHolder<RitualRecipe> recipe, IFocusGroup focuses) {
        builder.addInputHandler(new IJeiInputHandler() {
            @Override
            public ScreenRectangle getArea() {
                return new ScreenRectangle(0, 0, RitualRecipeCategory.this.background.getHeight(), RitualRecipeCategory.this.background.getWidth());
            }

            @Override
            public boolean handleInput(double mouseX, double mouseY, IJeiUserInput input) {
                if (mouseX > 4 && mouseX < 16 && mouseY > RitualRecipeCategory.this.background.getHeight() - 16 && mouseY < RitualRecipeCategory.this.background.getHeight() - 4
                        && recipe.value().getPentacleId() != null) {
                    var pentacle = ModonomiconAPI.get().getMultiblock(recipe.value().getPentacleId());

                    Minecraft.getInstance().setScreen(null);
                    MultiblockPreviewRenderer.setMultiblock(pentacle,
                            Component.translatable(Util.makeDescriptionId("multiblock", pentacle.getId())), true);
                    return true;
                }
                return false;
            }

            @Override
            public void handleMouseMoved(double mouseX, double mouseY) {
                if (mouseX > 4 && mouseX < 16 && mouseY > RitualRecipeCategory.this.background.getHeight() - 16 && mouseY < RitualRecipeCategory.this.background.getHeight() - 4) {
                    builder.addDrawable(RitualRecipeCategory.this.goldenEye, 2, RitualRecipeCategory.this.background.getHeight() - 18);
                } else {
                    builder.addDrawable(RitualRecipeCategory.this.eye, 2, RitualRecipeCategory.this.background.getHeight() - 18);
                }
            }
        });
    }

    private record SpriteDrawable(Identifier sprite, int width, int height) implements IDrawable {
        @Override
        public int getWidth() {
            return this.width;
        }

        @Override
        public int getHeight() {
            return this.height;
        }

        @Override
        public void draw(GuiGraphicsExtractor guiGraphics, int xOffset, int yOffset) {
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.sprite, xOffset, yOffset, this.width, this.height);
        }
    }
}
