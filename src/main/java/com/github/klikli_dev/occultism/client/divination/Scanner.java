/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 * Some of the software architecture of the scan system has been based on https://github.com/MightyPirates/Scannable
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

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.function.Consumer;

public class Scanner {
    //region Fields
    protected Block target;

    protected PlayerEntity player;
    protected Vec3d center;
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
    //endregion Fields

    //region Initialization
    public Scanner(Block target) {
        this.target = target;
    }
    //endregion Initialization

    //region Methods
    public void initialize(PlayerEntity player, Vec3d center, float radius, int totalTicks) {
        this.player = player;
        this.center = center;
        this.radius = radius;
        this.radiusSquared = this.radius * this.radius;
        this.min = new BlockPos(center).add(-this.radius, -this.radius, -this.radius);
        this.max = new BlockPos(center).add(this.radius, this.radius, this.radius);
        this.x = this.min.getX();
        this.y = this.min.getY() - 1;//first move next increments this to min.getY();
        this.z = this.min.getZ();

        BlockPos size = this.max.subtract(this.min);
        int blockCount = (size.getX() + 1) * (size.getY() + 1) * (size.getZ() + 1);
        this.blocksPerTick = MathHelper.ceil(blockCount / (float) totalTicks);
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
        World world = this.player.world;
        for (int i = 0; i < this.blocksPerTick; i++) {
            //move to next block
            if (!this.nextBlock(world)) {
                return;
            }

            //check if block is within radius
            if (this.center.squareDistanceTo(this.x + 0.5, this.y + 0.5, this.z + 0.5) > this.radiusSquared) {
                continue;
            }

            BlockPos pos = new BlockPos(this.x, this.y, this.z);
            BlockState state = world.getBlockState(pos);
            state = state.getActualState(world, pos);

            //if this is the block we search for, consume it.
            if (this.isValidBlock(state)) {
                resultConsumer.accept(pos);
            }
        }
    }

    public boolean nextBlock(World world) {
        this.y++;
        if (this.y > this.max.getY() || this.y >= world.getHeight()) {
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

    //endregion Methods
}
