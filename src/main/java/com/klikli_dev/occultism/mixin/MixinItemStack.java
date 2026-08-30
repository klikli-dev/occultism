package com.klikli_dev.occultism.mixin;

import com.klikli_dev.occultism.registry.OccultismDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.loading.FMLEnvironment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemStack.class, priority = 10, remap = false)
public class MixinItemStack {

    @Inject(method = "getHoverName", at = @At("RETURN"), cancellable = true)
    public void bedrockItemName(CallbackInfoReturnable<Component> cir) {
        if (FMLEnvironment.getDist().isClient()) {
            if (Minecraft.getInstance().gui.screen() instanceof AnvilScreen) {
                return;
            }
        }

        ItemStack stack = (ItemStack) (Object) this;
        if (stack.getOrDefault(OccultismDataComponents.UNBREAKABLE, false)) {
            MutableComponent name = cir.getReturnValue().copy();
            MutableComponent glitch = Component.empty().append(ChatFormatting.OBFUSCATED + "nice" + ChatFormatting.RESET);
            MutableComponent space = Component.literal("   ");
            MutableComponent custom = Component.empty().append(glitch).append(space).append(name).append(space).append(glitch);
            cir.setReturnValue(custom);
        }
    }

}
