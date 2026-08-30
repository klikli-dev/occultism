package com.klikli_dev.occultism.client.gui.widget;

import com.klikli_dev.occultism.client.gui.tablet.FamiliarTabletScreen;
import com.klikli_dev.occultism.common.entity.familiar.IFamiliar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractScrollArea;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;

public class FamiliarScrollWidget extends AbstractScrollArea {

    private final List<EntityType<?>> entities;
    private EntityType<?> selectedEntity;

    public FamiliarScrollWidget(int x, int y, int width, int height, List<EntityType<? extends IFamiliar>> entities, EntityType<?> selectedEntity) {
        super(x, y, width, height, Component.empty(), AbstractScrollArea.defaultSettings(8));
        this.entities = List.copyOf(entities);
        this.selectedEntity = selectedEntity;
    }

    @Override
    protected int contentHeight() {
        return this.entities.size() * 18;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        int entryHeight = 18;
        int entryWidth = 18;
        int padding = 1;

        graphics.fill(
                getX(),
                getY(),
                getX() + entryWidth,
                getY() + height,
                0x80000000
        );

        graphics.enableScissor(
                getX(),
                getY(),
                getX() + entryWidth,
                getY() + height
        );

        int contentY = getY() - (int) scrollAmount();

        for (int index = 0; index < entities.size(); index++) {
            EntityType<?> entityType = entities.get(index);

            int entryY = contentY + index * entryHeight;
            if (entryY + entryHeight < getY() || entryY > getY() + height) {
                continue;
            }

            boolean selected = entityType == selectedEntity;
            if (selected) {
                int left = getX();
                int right = getRight() - scrollbarWidth();
                int bottom = entryY + entryHeight;
                graphics.fill(left, entryY, right, entryY + 1, 0xFFFFFFFF);
                graphics.fill(left, bottom - 1, right, bottom, 0xFFFFFFFF);
                graphics.fill(left, entryY, left + 1, bottom, 0xFFFFFFFF);
                graphics.fill(right - 1, entryY, right, bottom, 0xFFFFFFFF);
            }

            boolean hovered = mouseX >= getX() && mouseX < getX() + entryWidth
                                && mouseY >= entryY && mouseY < entryY + entryHeight;
            if (hovered) {
                graphics.fill(getX(), entryY, getX() + entryWidth, entryY + entryHeight, 0x40FFFFFF);
            }

            ItemStack icon = getEntityIcon(entityType);
            graphics.item(
                    icon,
                    getX() + padding,
                    entryY + padding
            );
        }

        graphics.disableScissor();

        extractScrollbar(graphics, mouseX, mouseY);
    }

    @Override
    protected void updateWidgetNarration(@NonNull NarrationElementOutput narrationElementOutput) {

    }

    @Override
    public boolean mouseClicked(@NonNull MouseButtonEvent event, boolean doubleClick) {
        if (this.updateScrolling(event)) {
            return true;
        }

        if (!isMouseOver(event.x(), event.y())) {
            return false;
        }

        int entryHeight = 18;
        int contentY = (int) (event.y() - getY() + scrollAmount());
        int index = contentY / entryHeight;
        if (index >= 0 && index < entities.size()) {
            selectedEntity = entities.get(index);
            if (Minecraft.getInstance().screen instanceof FamiliarTabletScreen tabletScreen) {
                tabletScreen.setSelectedFamiliar(selectedEntity);
            }
        }

        return super.mouseClicked(event, doubleClick);
    }

    @Override
    public boolean mouseReleased(@NonNull MouseButtonEvent event) {
        this.onRelease(event);
        return super.mouseReleased(event);
    }

    private static ItemStack getEntityIcon(EntityType<?> entityType) {
        Optional<Holder<Item>> spawnEgg = SpawnEggItem.byId(entityType);
        return spawnEgg.map(ItemStack::new).orElseGet(() -> new ItemStack(Items.EGG));
    }
}
