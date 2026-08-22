package com.klikli_dev.occultism.util;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.registry.OccultismDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class TeleportUtil {
    // Mirrors RespawnAnchorBlock nearby stand-up search ordering.
    private static final int[][] SAFE_TELEPORT_OFFSETS = new int[][]{
            {0, 0, 0},
            {0, 0, -1},
            {-1, 0, 0},
            {0, 0, 1},
            {1, 0, 0},
            {-1, 0, -1},
            {1, 0, -1},
            {-1, 0, 1},
            {1, 0, 1},
            {0, -1, -1},
            {-1, -1, 0},
            {0, -1, 1},
            {1, -1, 0},
            {-1, -1, -1},
            {1, -1, -1},
            {-1, -1, 1},
            {1, -1, 1},
            {0, 1, -1},
            {-1, 1, 0},
            {0, 1, 1},
            {1, 1, 0},
            {-1, 1, -1},
            {1, 1, -1},
            {-1, 1, 1},
            {1, 1, 1},
            {0, 1, 0}
    };

    public static TeleportDestination findDestination(ServerLevel level, Entity entity, ItemStack compass){
        ResourceKey<Level> resourcekey = null;
        BlockPos targetPos = null;
        Vec3 destination = null;
        if (compass.has(DataComponents.LODESTONE_TRACKER)) {
            var test = compass.get(DataComponents.LODESTONE_TRACKER);
            if (test != null) {
                Optional<GlobalPos> globalPos = test.target();
                if (globalPos.isPresent()) {
                    resourcekey = globalPos.get().dimension();
                    targetPos = globalPos.get().pos().above();
                }
            }
        } else if (compass.is(Items.COMPASS)) {
            resourcekey = level.dimension();
            if (compass.has(DataComponents.CUSTOM_NAME)) {
                var name = compass.get(DataComponents.CUSTOM_NAME);
                if (name != null) {
                    if (name.getString().equals("RTP")) {
                        resourcekey = entity.level().dimension();
                        targetPos = findSafeRTP(level, entity, Occultism.SERVER_CONFIG.itemSettings.maxTryRTP.getAsInt());
                    }
                    if (name.getString().equals("HOME")
                            && entity instanceof ServerPlayer player) {
                        ServerPlayer.RespawnConfig respawnConfig = player.getRespawnConfig();
                        ResourceKey<Level> tempKey = ServerPlayer.RespawnConfig.getDimensionOrDefault(respawnConfig);
                        ServerLevel tempLevel = level.getServer().getLevel(tempKey);
                        BlockPos tempPos = respawnConfig != null ? respawnConfig.respawnData().pos() : null;
                        if (tempLevel != null && tempPos != null) {
                            BlockState blockstate = tempLevel.getBlockState(tempPos);
                            Block block = blockstate.getBlock();
                            if (block instanceof RespawnAnchorBlock && (blockstate.getValue(RespawnAnchorBlock.CHARGE) > 0) && RespawnAnchorBlock.canSetSpawn(tempLevel, tempPos)) {
                                Optional<Vec3> optional = RespawnAnchorBlock.findStandUpPosition(EntityType.PLAYER, tempLevel, tempPos);
                                if (optional.isPresent()) {
                                    destination = optional.get();
                                    resourcekey = tempKey;
                                }
                            } else if (block instanceof BedBlock && tempLevel.environmentAttributes().getValue(EnvironmentAttributes.BED_RULE, tempPos).canSetSpawn(tempLevel)) {
                                float respawnAngle = respawnConfig.respawnData().yaw();
                                Optional<Vec3> optional = BedBlock.findStandUpPosition(EntityType.PLAYER, tempLevel, tempPos, blockstate.getValue(BedBlock.FACING), respawnAngle);
                                if (optional.isPresent()) {
                                    destination = optional.get();
                                    resourcekey = tempKey;
                                }
                            }
                        }
                    }
                }
            }
        } else if (compass.is(Items.RECOVERY_COMPASS)
                && entity instanceof ServerPlayer serverPlayer
                && serverPlayer.getLastDeathLocation().isPresent()) {
            resourcekey = serverPlayer.getLastDeathLocation().get().dimension();
            targetPos = serverPlayer.getLastDeathLocation().get().pos();
        } else if (compass.has(OccultismDataComponents.SPIRIT_ENTITY_UUID)) {
            UUID spirit = ItemNBTUtil.getSpiritEntityUUID(compass);
            if (spirit != null) {
                for (ServerLevel allLvl : level.getServer().getAllLevels()) {
                    Entity targetEntity = allLvl.getEntity(spirit);
                    if (targetEntity != null) {
                        resourcekey = targetEntity.level().dimension();
                        targetPos = targetEntity.blockPosition();
                        break;
                    }
                }
            }
        }
        return new TeleportDestination(resourcekey, targetPos, destination);
    }


    public static Optional<Vec3> findSafeTeleportPosition(EntityType<?> type, ServerLevel level, BlockPos pos, boolean checkDangerous) {
        BlockPos.MutableBlockPos candidatePos = pos.mutable();

        for (int[] offset : SAFE_TELEPORT_OFFSETS) {
            candidatePos.set(pos.getX() + offset[0], pos.getY() + offset[1], pos.getZ() + offset[2]);
            Vec3 safePosition = DismountHelper.findSafeDismountLocation(type, level, candidatePos, checkDangerous);
            if (safePosition != null) {
                return Optional.of(safePosition);
            }
        }

        return Optional.empty();
    }

    @Nullable
    public static Vec3 findSafeTeleportPosition(Entity entity, ServerLevel level, BlockPos pos) {
        // Matches vanilla respawn logic: prefer safe spots, then allow otherwise valid stand-up spaces.
        Optional<Vec3> safePosition = findSafeTeleportPosition(entity.getType(), level, pos, true);
        return safePosition.orElseGet(() -> findSafeTeleportPosition(entity.getType(), level, pos, false).orElse(null));
    }

    public static BlockPos findSafeRTP(Level level, Entity entity, int recursionLeft) {
        if (recursionLeft <= 0)
            return null;

        BlockPos blockpos;
        //Respect word border
        int range = Math.min((int) level.getWorldBorder().getDistanceToBorder(entity), Occultism.SERVER_CONFIG.itemSettings.maxDistanceRTP.getAsInt());
        //Random direction
        blockpos = entity.blockPosition().offset(RandomSource.create().nextInt(-range, range), level.getMaxY(), RandomSource.create().nextInt(-range, range));
        //Find floor
        while (level.getBlockState(blockpos.below()).isAir() && blockpos.getY() > level.getMinY()) {
            blockpos = blockpos.below();
        }
        //Pass nether (or other dimension) roof
        if (blockpos.getY() > 10 && level.getBlockState(blockpos.below()).is(Blocks.BEDROCK)) {
            blockpos = blockpos.below(5);
            while (!level.getBlockState(blockpos.below()).isAir() && blockpos.getY() > level.getMinY()) {
                blockpos = blockpos.below();
            }
            while (level.getBlockState(blockpos.below()).isAir() && blockpos.getY() > level.getMinY()) {
                blockpos = blockpos.below();
            }
        }
        //Return blockPos if safe, or repeat the process
        return blockpos.getY() == level.getMinY()
                || level.getBlockState(blockpos.below()).is(Blocks.WATER)
                || level.getBlockState(blockpos.below()).is(Blocks.LAVA) ?
                findSafeRTP(level, entity, recursionLeft - 1) : blockpos;
    }

    public record TeleportDestination(
            ResourceKey<Level> level,
            BlockPos blockPos,
            Vec3 position
    ) {}
}
