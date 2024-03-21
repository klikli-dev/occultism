package com.klikli_dev.occultism.common.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DirectionalBlockShape {

    protected final float length;
    protected final float width;
    protected final float height;
    protected final float center;
    protected final VoxelShape up;
    protected final VoxelShape down;
    protected final VoxelShape west;
    protected final VoxelShape east;
    protected final VoxelShape north;
    protected final VoxelShape south;


    public DirectionalBlockShape(float length, float width, float height) {
        this(length, width, height, 8.0F);
    }

    public DirectionalBlockShape(float length, float width, float height, float center) {
        this.length = length;
        this.width = width;
        this.height = height;
        this.center = center;

        this.up = Block.box(center - width / 2, 0.0F, center - length / 2, center + width / 2, height, center + length / 2);
        this.down = Block.box(center - width / 2, 16.0F - height, center - length / 2, center + width / 2, 16.0F, center + length / 2);
        this.west = Block.box(16.0F - height, center - width / 2, center - length / 2, 16.0F, center + width / 2, center + length / 2);
        this.east = Block.box(0.0F, center - width / 2, center - length / 2, height, center + width / 2, center + length / 2);
        this.north = Block.box(center - width / 2, center - length / 2, 16.0F - height, center + width / 2, center + length / 2, 16.0F);
        this.south = Block.box(center - width / 2, center - length / 2, 0.0F, center + width / 2, center + length / 2, height);
    }

    public VoxelShape getShape(Direction direction) {
        return switch (direction) {
            case UP -> this.up;
            case DOWN -> this.down;
            case WEST -> this.west;
            case EAST -> this.east;
            case NORTH -> this.north;
            case SOUTH -> this.south;
        };
    }

    public VoxelShape up() {
        return this.up;
    }

    public VoxelShape down() {
        return this.down;
    }

    public VoxelShape west() {
        return this.west;
    }

    public VoxelShape east() {
        return this.east;
    }

    public VoxelShape north() {
        return this.north;
    }

    public VoxelShape south() {
        return this.south;
    }

}
