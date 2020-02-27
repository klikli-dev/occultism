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

package com.github.klikli_dev.occultism.network;

import com.github.klikli_dev.occultism.api.client.gui.IStorageControllerGui;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * This message sends the stacks in the currently opened storage controller.
 */
public class MessageUpdateStacks extends MessageBase<MessageUpdateStacks> {

    //region Fields
    private static final int DEFAULT_BUFFER_SIZE = 2 * 1024;

    private List<ItemStack> stacks;
    private int usedSlots;
    private ByteBuf payload;

    //endregion Fields

    //region Initialization
    public MessageUpdateStacks() {

    }

    public MessageUpdateStacks(List<ItemStack> stacks, int usedSlots) {
        this.stacks = stacks;
        this.usedSlots = usedSlots;
        this.compress();
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void onClientReceived(Minecraft minecraft, MessageUpdateStacks message, PlayerEntity player,
                                 MessageContext context) {
        message.uncompress();
        IStorageControllerGui gui = (IStorageControllerGui) Minecraft.getMinecraft().currentScreen;
        if (gui != null) {
            gui.setStacks(message.stacks);
            gui.setUsedSlots(message.usedSlots);
        }
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, MessageUpdateStacks message, ServerPlayerEntity player,
                                 MessageContext context) {

    }


    @Override
    public void fromBytes(ByteBuf byteBuf) {
        this.usedSlots = byteBuf.readInt();
        //read compressed size, then compressed data.
        int compressedSize = byteBuf.readInt();
        this.payload = Unpooled.buffer(compressedSize);
        byteBuf.readBytes(this.payload, 0, compressedSize);
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(this.usedSlots);

        //write compressed size, then compressed data
        byteBuf.writeInt(this.payload.readableBytes());
        byteBuf.writeBytes(this.payload, 0, this.payload.readableBytes());
    }
    //endregion Overrides

    //region Methods
    public void uncompress() {
        Inflater decompressor = new Inflater();
        decompressor.setInput(this.payload.array());

        // Create an expandable byte array to hold the decompressed data
        ByteBuf uncompressed = Unpooled.buffer(this.payload.readableBytes() * 4);

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
            ItemStack stack = new ItemStack(ByteBufUtils.readTag(uncompressed));
            stack.setCount(uncompressed.readInt());
            this.stacks.add(stack);
        }
    }

    public void compress() {
        Deflater compressor = new Deflater();
        compressor.setLevel(Deflater.BEST_SPEED);

        // Give the compressor the data to compress
        //create buffer with reasonable size (will increase automatically as needed
        ByteBuf uncompressed = Unpooled.buffer(DEFAULT_BUFFER_SIZE * this.stacks.size());
        uncompressed.writeInt(this.stacks.size());

        for (ItemStack stack : this.stacks) {
            ByteBufUtils.writeTag(uncompressed, stack.serializeNBT());
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
    //endregion Methods
}
