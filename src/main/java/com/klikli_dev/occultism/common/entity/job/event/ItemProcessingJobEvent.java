package com.klikli_dev.occultism.common.entity.job.event;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.event.entity.EntityEvent;

public class ItemProcessingJobEvent extends EntityEvent {
    private ItemStack input;
    private ItemStack result;
    public ItemProcessingJobEvent(Entity entity, ItemStack input, ItemStack result) {
        super(entity);
        this.input = input;
        this.result = result;
    }

    public ItemStack getInput() {
        return input;
    }

    public void setInput(ItemStack input) {
        this.input = input;
    }

    public ItemStack getResult() {
        return result;
    }

    public void setResult(ItemStack result) {
        this.result = result;
    }
}