/*
 * MIT License
 *
 * Copyright 2020 klikli-dev, Florian "Sangar" Nücke
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

package com.github.klikli_dev.occultism.client.divination;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.client.render.SelectedBlockRenderer;
import com.github.klikli_dev.occultism.network.MessageSetDivinationResult;
import com.github.klikli_dev.occultism.network.OccultismPackets;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.function.Consumer;

/**
 * Based on https://github.com/MightyPirates/Scannable
 */
public class Scanner {
    protected Block target;

    protected Player player;
    protected Vec3 center;
    protected float radius;
    //radius squared for faster comparison of distance
    protected float radiusSquared;

    //the max extent of the scan
    protected BlockPos min;
    protected BlockPos max;

    //the current scanned block
    protected int x;
    protected int y;
    protected int z;

    private int blocksPerTick;

    protected boolean highlightAllResults;

    public Scanner(Block target) {
        this.target = target;
    }

    public void setHighlightAllResults(boolean highlightAllResults) {
        this.highlightAllResults = highlightAllResults;
    }

    public void initialize(Player player, Vec3 center, float radius, int totalTicks) {
        this.player = player;
        this.center = center;
        this.radius = radius;
        this.radiusSquared = this.radius * this.radius;
        this.min = new BlockPos(center).offset(-this.radius, -this.radius, -this.radius);
        this.max = new BlockPos(center).offset(this.radius, this.radius, this.radius);
        this.x = this.min.getX();
        this.y = this.min.getY() - 1;//first move next increments this to min.getY();
        this.z = this.min.getZ();

        BlockPos size = this.max.subtract(this.min);
        int blockCount = (size.getX() + 1) * (size.getY() + 1) * (size.getZ() + 1);
        this.blocksPerTick = Mth.ceil(blockCount / (float) totalTicks);
    }

    public void reset() {
        this.player = null;
        this.center = null;
        this.radius = 0;
        this.radiusSquared = 0;
        this.min = null;
        this.max = null;
    }

    public void scan(Consumer<BlockPos> resultConsumer) {
        Level level = this.player.level;
        for (int i = 0; i < this.blocksPerTick; i++) {
            //move to next block
            if (!this.nextBlock(level)) {
                return;
            }

            //check if block is within radius
            if (this.center.distanceToSqr(this.x + 0.5, this.y + 0.5, this.z + 0.5) > this.radiusSquared) {
                continue;
            }

            BlockPos pos = new BlockPos(this.x, this.y, this.z);
            BlockState state = level.getBlockState(pos);

            //if this is the block we search for, consume it.
            if (this.isValidBlock(state)) {
                if(this.highlightAllResults){
                    Occultism.SELECTED_BLOCK_RENDERER.selectBlock(pos, System.currentTimeMillis() + 10000);
                }
                resultConsumer.accept(pos);
            }
        }
    }

    public boolean nextBlock(Level level) {
        this.y++;
        if (this.y > this.max.getY() || this.y >= level.getHeight()) {
            this.y = this.min.getY();
            this.x++;
            if (this.x > this.max.getX()) {
                this.x = this.min.getX();
                this.z++;
                if (this.z > this.max.getZ()) {
                    this.blocksPerTick = 0;
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isValidBlock(BlockState state) {
        return state.getBlock() == this.target;
    }
}
