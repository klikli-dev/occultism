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

package com.klikli_dev.occultism.client.gui.spirit;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.client.gui.controls.LabelWidget;
import com.klikli_dev.occultism.common.container.spirit.SpiritContainer;
import com.klikli_dev.occultism.common.entity.IFilterConfigurable;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.util.TextUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

public class SpiritGui<T extends SpiritContainer> extends AbstractContainerScreen<T> {

    private static final int ENTITY_RENDER_WIDTH = 70;
    private static final int ENTITY_RENDER_HEIGHT = 70;
    private static final int ENTITY_BASE_SCALE = 30;

    protected static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(Occultism.MODID,
            "textures/gui/inventory_spirit.png");
    protected static final String TRANSLATION_KEY_BASE = "gui." + Occultism.MODID + ".spirit";
    protected IFilterConfigurable spirit;
    protected T container;

    public SpiritGui(T container, Inventory playerInventory, Component titleIn) {
        super(container, playerInventory, titleIn, 175, 165);
        this.container = container;
        this.spirit = this.container.spirit;
    }

    public SpiritGui(T container, Inventory playerInventory, Component titleIn, int imageWidth, int imageHeight) {
        super(container, playerInventory, titleIn, imageWidth, imageHeight);
        this.container = container;
        this.spirit = this.container.spirit;
    }

    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
        this.extractTooltip(guiGraphics, mouseX, mouseY);
    }

    public static void drawEntityToGui(GuiGraphicsExtractor guiGraphics, int posX, int posY, int scale, float mouseX, float mouseY, LivingEntity entity) {
        // Use the vanilla InventoryScreen method with a bounding box centered around posX, posY
        InventoryScreen.extractEntityInInventoryFollowsMouse(guiGraphics, posX - ENTITY_RENDER_WIDTH / 2,
                posY - ENTITY_RENDER_HEIGHT + 10, posX + ENTITY_RENDER_WIDTH / 2, posY + 10, scale, 0.0625F,
                mouseX, mouseY, entity);
    }

    protected static int getEntityScale(LivingEntity entity) {
        float entityScale = Math.max(entity.getScale(), 0.0001F);
        float renderWidth = entity.getBbWidth() / entityScale;
        float renderHeight = entity.getBbHeight() / entityScale;
        if (renderWidth <= 0 || renderHeight <= 0) {
            return ENTITY_BASE_SCALE;
        }

        int maxScaleForWidth = (int) Math.floor(ENTITY_RENDER_WIDTH / renderWidth);
        int maxScaleForHeight = (int) Math.floor(ENTITY_RENDER_HEIGHT / renderHeight);
        return Math.max(1, Math.min(ENTITY_BASE_SCALE, Math.min(maxScaleForWidth, maxScaleForHeight)));
    }

    @Override
    @SuppressWarnings("deprecation")
    public void init() {
        super.init();
        this.clearWidgets();

        int labelHeight = 9;
        LabelWidget nameLabel = new LabelWidget(this.leftPos + 65, this.topPos + 17, false, -1, 2, 0x404040);
        nameLabel.addLine(TextUtil.formatDemonName(this.spirit.getEntity().getName().getString()));
        this.addRenderableWidget(nameLabel);

        if (this.spirit instanceof SpiritEntity spiritEntity && spiritEntity.getSpiritMaxAge() >= 0) {
            int agePercent = (int) Math.floor(spiritEntity.getSpiritAge() / (float) spiritEntity.getSpiritMaxAge() * 100);
            LabelWidget ageLabel = new LabelWidget(this.leftPos + 65, this.topPos + 17 + labelHeight + 5, false, -1, 2, 0x404040);
            ageLabel.addLine(I18n.get(TRANSLATION_KEY_BASE + ".age", agePercent));
            this.addRenderableWidget(ageLabel);
        }

        String jobID = this.spirit instanceof SpiritEntity spiritEntity ? spiritEntity.getJobID() : "";
        if (!StringUtils.isBlank(jobID)) {
            jobID = jobID.replace(":", ".");
            LabelWidget jobLabel = new LabelWidget(this.leftPos + 65,
                    this.topPos + 17 + labelHeight + 5 + labelHeight + 5 + 5, false, -1, 2, 0x404040);

            String jobText = I18n.get(TRANSLATION_KEY_BASE + ".job", I18n.get("job." + jobID));
            String[] lines = WordUtils.wrap(jobText, 15, "\n", true).split("[\\r\\n]+", 2);
            for (String line : lines)
                jobLabel.addLine(ChatFormatting.ITALIC + line + ChatFormatting.RESET);
            this.addRenderableWidget(jobLabel);

        }
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor guiGraphics, int pMouseX, int pMouseY) {
        //prevent default labels being rendered
    }

    @Override
    public void extractContents(GuiGraphicsExtractor guiGraphics, int x, int y, float partialTicks) {
//        this.renderBackground(guiGraphics); //called by super

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, this.getTexture(), this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);

        guiGraphics.pose().pushMatrix();
        int scale = getEntityScale(this.spirit.getEntity());
        drawEntityToGui(guiGraphics, this.leftPos + 35, this.topPos + 65, scale, this.leftPos + 51 - x,
                this.topPos + 75 - 50 - y, this.spirit.getEntity());
        guiGraphics.pose().popMatrix();

        super.extractContents(guiGraphics, x, y, partialTicks);
    }

    protected Identifier getTexture() {
        return TEXTURE;
    }
}
