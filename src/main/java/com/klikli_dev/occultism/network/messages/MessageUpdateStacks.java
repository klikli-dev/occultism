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

package com.klikli_dev.occultism.network.messages;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.api.client.gui.IStorageControllerGui;
import com.klikli_dev.occultism.network.IMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * This message sends the stacks in the currently opened storage controller.
 */
public class MessageUpdateStacks implements IMessage {

    public static final ResourceLocation ID = new ResourceLocation(Occultism.MODID, "update_stacks");

    private static final int DEFAULT_BUFFER_SIZE = 2 * 1024;

    private List<ItemStack> stacks;
    private int maxItemTypes;
    private int usedItemTypes;
    private long maxTotalItemCount;
    private long usedTotalItemCount;
    private ByteBuf payload;

    public MessageUpdateStacks(FriendlyByteBuf buf) {
        this.decode(buf);
    }

    public MessageUpdateStacks(List<ItemStack> stacks, int maxItemTypes, int usedItemTypes, long maxTotalItemCount, long usedTotalItemCount) {
        this.stacks = stacks;
        this.maxItemTypes = maxItemTypes;
        this.usedItemTypes = usedItemTypes;
        this.maxTotalItemCount = maxTotalItemCount;
        this.usedTotalItemCount = usedTotalItemCount;
        this.compress();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void onClientReceived(Minecraft minecraft, Player player) {
        this.uncompress();
        if (minecraft.screen instanceof IStorageControllerGui gui) {
            if (gui != null) {
                gui.setStacks(this.stacks);
                gui.setUsedStorageSize(this.usedItemTypes, this.usedTotalItemCount);
                gui.setMaxStorageSize(this.maxItemTypes, this.maxTotalItemCount);
                gui.markDirty();
            }
        }
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeVarInt(this.usedItemTypes);
        buf.writeVarLong(this.usedTotalItemCount);
        buf.writeVarInt(this.maxItemTypes);
        buf.writeVarLong(this.maxTotalItemCount);

        //write compressed size, then compressed data
        buf.writeVarInt(this.payload.readableBytes());
        buf.writeBytes(this.payload, 0, this.payload.readableBytes());
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        this.usedItemTypes = buf.readVarInt();
        this.usedTotalItemCount = buf.readVarLong();
        this.maxItemTypes = buf.readVarInt();
        this.maxTotalItemCount = buf.readVarLong();

        //read compressed size, then compressed data.
        int compressedSize = buf.readVarInt();
        this.payload = Unpooled.buffer(compressedSize);
        buf.readBytes(this.payload, 0, compressedSize);
    }

    public void uncompress() {
        Inflater decompressor = new Inflater();
        decompressor.setInput(this.payload.array());

        // Create an expandable packet buffer to hold the decompressed data
        FriendlyByteBuf uncompressed = new FriendlyByteBuf(Unpooled.buffer(this.payload.readableBytes() * 4));

        // Decompress the data
        byte[] buf = new byte[1024];
        while (!decompressor.finished()) {
            try {
                int count = decompressor.inflate(buf);
                uncompressed.writeBytes(buf, 0, count);
            } catch (Exception e) {
            }
        }

        int stacksSize = uncompressed.readInt();
        this.stacks = new ArrayList<>(stacksSize);
        for (int i = 0; i < stacksSize; i++) {
            ItemStack stack = uncompressed.readItem();
            stack.setCount(uncompressed.readInt());
            this.stacks.add(stack);
        }
    }

    public void compress() {
        Deflater compressor = new Deflater();
        compressor.setLevel(Deflater.BEST_SPEED);

        // Give the compressor the data to compress
        //create buffer with reasonable size (will increase automatically as needed
        FriendlyByteBuf uncompressed = new FriendlyByteBuf(Unpooled.buffer(DEFAULT_BUFFER_SIZE * this.stacks.size()));
        uncompressed.writeInt(this.stacks.size());

        for (ItemStack stack : this.stacks) {
            uncompressed.writeItem(stack);
            uncompressed.writeInt(stack.getCount());
        }

        compressor.setInput(uncompressed.array(), 0, uncompressed.readableBytes());
        compressor.finish();


        this.payload = Unpooled.buffer(DEFAULT_BUFFER_SIZE);
        // Compress the data
        byte[] buf = new byte[1024];
        while (!compressor.finished()) {
            int count = compressor.deflate(buf);
            this.payload.writeBytes(buf, 0, count);
        }
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
