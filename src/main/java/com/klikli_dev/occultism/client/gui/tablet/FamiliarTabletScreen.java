package com.klikli_dev.occultism.client.gui.tablet;

import com.google.common.collect.ImmutableList;
import com.klikli_dev.codedefinedgui.api.layout.LayoutResolverRegistry;
import com.klikli_dev.codedefinedgui.api.layout.LayoutScreenView;
import com.klikli_dev.codedefinedgui.api.layout.LayoutSpec;
import com.klikli_dev.codedefinedgui.api.layout.ScreenLayoutController;
import com.klikli_dev.codedefinedgui.api.screen.GuiHost;
import com.klikli_dev.codedefinedgui.api.screen.GuiRootWidget;
import com.klikli_dev.codedefinedgui.api.style.GuiStyleContext;
import com.klikli_dev.codedefinedgui.api.style.GuiStyleRegistry;
import com.klikli_dev.codedefinedgui.api.texture.GuiSprites;
import com.klikli_dev.codedefinedgui.api.widget.GuiBackgroundWidget;
import com.klikli_dev.codedefinedgui.api.widget.IconButtonBackgroundSprites;
import com.klikli_dev.codedefinedgui.api.widget.IconButtonWidget;
import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.client.gui.OccultismGuiParts;
import com.klikli_dev.occultism.client.gui.OccultismGuiStyles;
import com.klikli_dev.occultism.client.gui.widget.*;
import com.klikli_dev.occultism.common.capability.FamiliarSettingsData;
import com.klikli_dev.occultism.common.data.FamiliarEffects;
import com.klikli_dev.occultism.common.entity.familiar.IFamiliar;
import com.klikli_dev.occultism.network.Networking;
import com.klikli_dev.occultism.network.messages.MessageUpdateFamiliarSettings;
import com.klikli_dev.occultism.registry.OccultismDataStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class FamiliarTabletScreen extends Screen implements GuiHost, LayoutScreenView {
    protected static final String GUI_PREFIX = "gui." + Occultism.MODID + ".tablet.familiar";
    protected static final int GUI_WIDTH = 241;
    protected static final int GUI_HEIGHT = 100;
    private static final List<EntityType<? extends IFamiliar>> FAMILIAR_LIST = FamiliarSettingsData.getFamiliars();

    protected final GuiRootWidget root = new GuiRootWidget(this);
    private final ScreenLayoutController layoutController;
    protected int leftPos;
    protected int topPos;
    private boolean closingHandled;
    private final FamiliarSettingsData settingsData;
    private EntityType<?> selectedFamiliar;
    private final Level level;
    private int selectedEffect;
    private Holder<MobEffect> selectedHolder;
    private BookOfCallingSelectionWidget<Holder<MobEffect>> effectSelectionWidget;
    private Checkbox checkbox;
    private FamiliarEffectSlider slider;

    public FamiliarTabletScreen(Player player) {
        super(Component.translatable(GUI_PREFIX));
        this.settingsData = player.getData(OccultismDataStorage.FAMILIAR_SETTINGS);
        this.level = player.level();
        this.layoutController = new ScreenLayoutController(this, this, this.root,
                new GuiStyleContext(GuiStyleRegistry.get(OccultismGuiStyles.FAMILIAR_TABLET)));
        this.selectedEffect = 0;
    }

    @Override
    public LayoutSpec layoutSpec() {
        return TabletLayouts.familiar(GUI_HEIGHT);
    }

    @Override
    protected void init() {
        super.init();
        this.leftPos = (this.width - this.imageWidth()) / 2;
        this.topPos = (this.height - this.imageHeight()) / 2;
        this.clearWidgets();

        this.addRenderableWidget(this.root);
        this.root.clearChildren();
        this.layoutController.init();
        this.root.syncWithHost();
        this.afterLayoutInit();
        this.refreshWidgetState();
    }

    @Override
    public void registerResolvers(LayoutResolverRegistry registry) {
        registry.resolve("frame.panel", ctx -> ctx.addWidget(new GuiBackgroundWidget(
                this,
                ctx.node().x(),
                ctx.node().y(),
                ctx.node().widthOrThrow(),
                ctx.node().heightOrThrow(),
                ctx.style().sprite(OccultismGuiParts.TABLET_PANEL, GuiSprites.GUI_BACKGROUND)
        )));
        registry.resolve("frame.top_bar.background", ctx -> ctx.addWidget(new GuiBackgroundWidget(
                this,
                ctx.node().x(),
                ctx.node().y(),
                ctx.node().widthOrThrow(),
                ctx.node().heightOrThrow(),
                ctx.style().sprite(OccultismGuiParts.TABLET_TOP_BAR, GuiSprites.GUI_BACKGROUND)
        )));
        registry.resolve("frame.top_bar.title", ctx -> {
            int titleX = ctx.node().x() + (ctx.node().widthOrThrow() - this.font.width(this.title)) / 2;
            ctx.addWidget(new VerticallyCenteredTextWidget(
                    titleX,
                    ctx.node().y(),
                    -0.5F,
                    () -> this.title,
                    () -> ctx.style().textColor(OccultismGuiParts.TABLET_TITLE, 0xFF000000),
                    false
            ));
        });
        registry.resolve("content.familiar_list", ctx -> ctx.addWidget(new FamiliarScrollWidget(
                    ctx.node().x(), ctx.node().y(), 24, GUI_HEIGHT - 22, FAMILIAR_LIST, this.selectedFamiliar))
        );
        registry.resolve("content.familiar_preview", ctx -> this.root.addChild(new LivingEntityWidget(
                this,
                ctx.node().x() - this.leftPos(),
                ctx.node().y() - this.topPos(),
                ctx.node().widthOrThrow(),
                ctx.node().heightOrThrow(),
                () -> this.selectedFamiliar == null ? null : (LivingEntity) this.selectedFamiliar.create(this.level, EntitySpawnReason.LOAD),
                this.entityPreviewMouseOffsetX(),
                this.entityPreviewMouseOffsetY()
        )));
        registry.resolve("content.familiar_name", ctx -> {
            int titleX = ctx.node().x();
            ctx.addWidget(new VerticallyCenteredTextWidget(
                    titleX,
                    ctx.node().y(),
                    0.5F,
                    () -> this.selectedFamiliar == null ?
                            Component.translatable(GUI_PREFIX + ".not_selected") :
                            this.selectedFamiliar.getDescription(),
                    () -> ctx.style().textColor(OccultismGuiParts.TABLET_TITLE, 0xFF000000),
                    false
            ));
        });

        registry.resolve("config.mob_effect", ctx -> {
            this.effectSelectionWidget =
                    new BookOfCallingSelectionWidget<>(
                            ctx.node().x(),
                            ctx.node().y(),
                            ctx.node().widthOrThrow(),
                            ctx.node().heightOrThrow(),
                            ctx.style().sprite(OccultismGuiParts.TABLET_SELECTION, GuiSprites.ATTRIBUTE_FILTER_SELECTION),
                            this::availableMobEffects,
                            () -> this.selectedEffect,
                            this::changeMobEffectHolder,
                            effectHolder -> Component.translatable(effectHolder.value().getDescriptionId()),
                            Component.translatable(GUI_PREFIX + ".holder_effect.not_applicable"),
                            Component.translatable(GUI_PREFIX + ".scroll_to_select"))
                            .withTitle(Component.translatable(GUI_PREFIX + ".holder_effect"));
            ctx.addWidget(this.effectSelectionWidget);
        });
        registry.resolve("config.effect_level", ctx -> {
            this.slider = new FamiliarEffectSlider(
                    ctx.node().x(),
                    ctx.node().y(),
                    ctx.node().widthOrThrow(),
                    this.availableLevels(),
                    this.settingsData.getEffectAmplifier(this.selectedFamiliar, this.selectedHolder),
                    value -> {this.settingsData.setEffectAmplifier(this.selectedFamiliar, this.selectedHolder, (byte) value);}
            );
            this.slider.setVisible(this.selectedHolder != null);
            ctx.addWidget(slider);
        });

        registry.resolve("confirm_button", ctx ->
            ctx.addWidget(new IconButtonWidget(
                this.leftPos() + this.imageWidth() - 27,
                this.topPos() + this.imageHeight() - 24,
                GuiSprites.FILTER_ICON_CONFIRM,
                ctx.style().iconButtonBackgroundSprites(OccultismGuiParts.TABLET_CONFIRM_BUTTON, IconButtonBackgroundSprites.DEFAULT),
                Component.translatable(GUI_PREFIX + ".confirm"),
                () -> this.closeScreen(true))
            .withTooltip(Component.translatable(GUI_PREFIX + ".confirm.tooltip")))
        );
    }

    protected void afterLayoutInit() {
    }

    protected void refreshWidgetState() {
        removeGuiWidget(checkbox);
        this.checkbox = Checkbox.builder(
                        Component.translatable(GUI_PREFIX + ".status"), Minecraft.getInstance().font)
                .pos(this.leftPos() + 40, this.topPos() + 32)
                .tooltip(Tooltip.create(Component.translatable(GUI_PREFIX + ".status.tooltip")))
                .selected(this.settingsData.isFamiliarEnabled(this.selectedFamiliar))
                .onValueChange((check, bool) -> this.setFamiliarEnabled(bool))
                .build();
        addGuiWidget(checkbox);
    }

    protected void applyChanges() {
        Networking.sendToServer(new MessageUpdateFamiliarSettings(settingsData.getMap()));
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        this.refreshWidgetState();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        for (var listener : this.children()) {
            if (listener.isMouseOver(mouseX, mouseY) && listener.mouseScrolled(mouseX, mouseY, scrollX, scrollY)) {
                this.setFocused(listener);
                return true;
            }
        }

        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public void onClose() {
        this.closeScreen(false);
    }

    protected final void closeScreen(boolean confirm) {
        if (this.closingHandled) {
            return;
        }

        this.closingHandled = true;
        if (confirm) {
            this.applyChanges();
        }

        super.onClose();
    }

    protected float entityPreviewMouseOffsetX() {
        return 45.0F;
    }

    protected float entityPreviewMouseOffsetY() {
        return -15.0F;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void extractRenderState(@NonNull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public <W extends AbstractWidget> W addGuiWidget(W widget) {
        return this.addRenderableWidget(widget);
    }

    @Override
    public void removeGuiWidget(AbstractWidget widget) {
        this.removeWidget(widget);
    }

    @Override
    public int leftPos() {
        return this.leftPos;
    }

    @Override
    public int topPos() {
        return this.topPos;
    }

    @Override
    public int width() {
        return this.width;
    }

    @Override
    public int height() {
        return this.height;
    }

    @Override
    public int imageWidth() {
        return GUI_WIDTH;
    }

    @Override
    public int imageHeight() {
        return GUI_HEIGHT;
    }

    public void setSelectedFamiliar(EntityType<?> entityType) {
        if (FAMILIAR_LIST.contains(entityType))
            this.selectedFamiliar = entityType;
        this.selectedEffect = 0;
        this.selectedHolder = availableMobEffects().isEmpty() ? null : availableMobEffects().getFirst();
        if (this.effectSelectionWidget != null)
            this.effectSelectionWidget.updateTooltip();
        if (this.slider != null) {
            this.slider.setVisible(this.selectedHolder != null);
            this.slider.setValues(this.availableLevels());
            this.slider.setValue(this.settingsData.getEffectAmplifier(this.selectedFamiliar, this.selectedHolder));
        }
    }

    private List<Holder<MobEffect>> availableMobEffects() {
        ImmutableList<FamiliarEffects.FamiliarEffectDefinition> effectDefinitions = FamiliarEffects.effectMap().get(this.selectedFamiliar);
        List<Holder<MobEffect>> list = new ArrayList<>();
        if (effectDefinitions == null)
            return list;
        for (FamiliarEffects.FamiliarEffectDefinition def : effectDefinitions)
            list.add(def.effect());
        return list;
    }

    private int[] availableLevels() {
        if (this.selectedFamiliar == null || this.selectedHolder == null)
            return new int[]{-1,-1,-1};

        FamiliarEffects.FamiliarEffectDefinition effect = FamiliarEffects.effectMap().get(this.selectedFamiliar).get(this.selectedEffect);
        return new int[]{effect.normalValue(), effect.upgradedValue(), effect.iesniumValue()};
    }

    private void changeMobEffectHolder(int nextIndex) {
        List<Holder<MobEffect>> options = this.availableMobEffects();
        if (options.isEmpty())
            return;
        this.selectedEffect = Math.clamp(options.size() - 1, 0, nextIndex);
        this.selectedHolder = options.get(this.selectedEffect);
        if (this.slider != null && this.selectedFamiliar != null && this.selectedHolder != null) {
            this.slider.setValues(this.availableLevels());
            this.slider.setValue(this.settingsData.getEffectAmplifier(this.selectedFamiliar, this.selectedHolder));
        }
    }

    private void setFamiliarEnabled(boolean bol) {
        if (this.settingsData != null && this.selectedFamiliar != null)
            this.settingsData.setFamiliarEnabled(this.selectedFamiliar, bol);
    }
}
