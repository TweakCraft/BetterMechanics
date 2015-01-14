/*
 * Copyright (c) 2012 Edoxile
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package net.edoxile.bettermechanics.utils;

import net.edoxile.bettermechanics.MechanicsType;
import net.edoxile.bettermechanics.exceptions.BlockNotFoundException;
import net.edoxile.bettermechanics.exceptions.InvalidDirectionException;
import net.edoxile.bettermechanics.exceptions.OutOfBoundsException;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Sign;

import java.util.HashSet;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Edoxile
 */
public class BlockMapper {
    private static final Logger log = Logger.getLogger("Minecraft");
    private static HashSet<Block> recursiveSet = new HashSet<Block>();
    private static int maxTraverse = 128;

    public static HashSet<Block> mapHorizontal(BlockFace direction, Block start, Block end, boolean small) throws InvalidDirectionException {
        HashSet<Block> blockSet = new HashSet<Block>();
        int traversed = 0;

        switch (direction) {
            case SOUTH: {
                Location startLoc = start.getLocation();
                Location endLoc = end.getLocation();
                if (startLoc.getBlockX() != endLoc.getBlockX() || startLoc.getBlockY() != endLoc.getBlockY() || startLoc.getBlockZ() > endLoc.getBlockZ()) {
                    throw new InvalidDirectionException();
                } else {
                    Block tempBlock = start;
                    while (!tempBlock.getLocation().equals(end.getLocation()) && traversed < maxTraverse) {
                        blockSet.add(tempBlock);
                        if (!small) {
                            blockSet.add(tempBlock.getRelative(BlockFace.WEST));
                            blockSet.add(tempBlock.getRelative(BlockFace.EAST));
                        }
                        tempBlock = tempBlock.getRelative(direction);
                        traversed++;
                    }
                    if (traversed >= maxTraverse) {
                        log.severe("[BetterMechanics] MaxTraverse hit on " + start.getLocation());
                    }
                }
            }
            break;
            case NORTH: {
                Location startLoc = start.getLocation();
                Location endLoc = end.getLocation();
                if (startLoc.getBlockX() != endLoc.getBlockX() || startLoc.getBlockY() != endLoc.getBlockY() || endLoc.getBlockZ() > startLoc.getBlockZ()) {
                    throw new InvalidDirectionException();
                } else {
                    Block tempBlock = start;
                    while (!tempBlock.getLocation().equals(end.getLocation()) && traversed < maxTraverse) {
                        blockSet.add(tempBlock);
                        if (!small) {
                            blockSet.add(tempBlock.getRelative(BlockFace.WEST));
                            blockSet.add(tempBlock.getRelative(BlockFace.EAST));
                        }
                        tempBlock = tempBlock.getRelative(direction);
                        traversed++;
                    }
                    if (traversed >= maxTraverse) {
                        log.severe("[BetterMechanics] MaxTraverse hit on " + start.getLocation());
                    }
                }
            }
            break;
            case EAST: {
                Location startLoc = start.getLocation();
                Location endLoc = end.getLocation();
                if (startLoc.getBlockZ() != endLoc.getBlockZ() || startLoc.getBlockY() != endLoc.getBlockY() || startLoc.getBlockX() > endLoc.getBlockX()) {
                    throw new InvalidDirectionException();
                } else {
                    Block tempBlock = start;
                    while (!tempBlock.getLocation().equals(end.getLocation()) && traversed < maxTraverse) {
                        blockSet.add(tempBlock);
                        if (!small) {
                            blockSet.add(tempBlock.getRelative(BlockFace.NORTH));
                            blockSet.add(tempBlock.getRelative(BlockFace.SOUTH));
                        }
                        tempBlock = tempBlock.getRelative(direction);
                        traversed++;
                    }
                    if (traversed >= maxTraverse) {
                        log.severe("[BetterMechanics] MaxTraverse hit on " + start.getLocation());
                    }
                }
            }
            break;
            case WEST: {
                Location startLoc = start.getLocation();
                Location endLoc = end.getLocation();
                if (startLoc.getBlockZ() != endLoc.getBlockZ() || startLoc.getBlockY() != endLoc.getBlockY() || endLoc.getBlockX() > startLoc.getBlockX()) {
                    throw new InvalidDirectionException();
                } else {
                    Block tempBlock = start;
                    while (!tempBlock.getLocation().equals(end.getLocation()) && traversed < maxTraverse) {
                        blockSet.add(tempBlock);
                        if (!small) {
                            blockSet.add(tempBlock.getRelative(BlockFace.NORTH));
                            blockSet.add(tempBlock.getRelative(BlockFace.SOUTH));
                        }
                        tempBlock = tempBlock.getRelative(direction);
                        traversed++;
                    }
                    if (traversed >= maxTraverse) {
                        log.severe("[BetterMechanics] MaxTraverse hit on " + start.getLocation());
                    }
                }
            }
            break;
            default:
                throw new InvalidDirectionException();
        }
        return blockSet;
    }

    public static HashSet<Block> mapHiddenSwitch(Block start) {
        // if(!(start.getState() instanceof Sign)) return null;

        HashSet<Block> blockSet = new HashSet<Block>();
        HashSet<Block> tempBlocks = new HashSet<Block>();

        if (start.getType() != org.bukkit.Material.WALL_SIGN) return blockSet;

        BlockFace signface = ((Sign) start.getState().getData()).getAttachedFace();

        tempBlocks.add(start.getRelative(BlockFace.UP));
        tempBlocks.add(start.getRelative(BlockFace.DOWN));

        switch (signface) {
            case NORTH:
            case SOUTH:
                tempBlocks.add(start.getRelative(BlockFace.WEST));
                tempBlocks.add(start.getRelative(BlockFace.EAST));
                break;
            case EAST:
            case WEST:
                tempBlocks.add(start.getRelative(BlockFace.NORTH));
                tempBlocks.add(start.getRelative(BlockFace.SOUTH));
                break;
        }
        for (Block b : tempBlocks) {
            if (b.getType() == Material.LEVER)
                blockSet.add(b);
        }
        return blockSet;
    }

    public static HashSet<Block> mapVertical(BlockFace direction, BlockFace orientation, Block start, Block end, boolean small) throws InvalidDirectionException {
        HashSet<Block> blockSet = new HashSet<Block>();
        int traversed = 0;
        switch (direction) {
            case UP: {
                Location startLoc = start.getLocation();
                Location endLoc = end.getLocation();
                if (startLoc.getBlockX() != endLoc.getBlockX() || startLoc.getBlockZ() != endLoc.getBlockZ()) {
                    throw new InvalidDirectionException();
                } else {
                    Block tempBlock = start;

                    while (!tempBlock.getLocation().equals(end.getLocation()) && traversed < maxTraverse) {
                        blockSet.add(tempBlock);
                        if (!small) {
                            switch (orientation) {
                                case NORTH:
                                case SOUTH: {
                                    blockSet.add(tempBlock.getRelative(BlockFace.WEST));
                                    blockSet.add(tempBlock.getRelative(BlockFace.EAST));
                                }
                                break;
                                case EAST:
                                case WEST: {
                                    blockSet.add(tempBlock.getRelative(BlockFace.NORTH));
                                    blockSet.add(tempBlock.getRelative(BlockFace.SOUTH));
                                }
                                break;
                            }
                        }
                        tempBlock = tempBlock.getRelative(direction);
                        traversed++;
                    }
                    if (traversed >= maxTraverse) {
                        log.severe("[BetterMechanics] MaxTraverse hit on " + start.getLocation());
                    }
                }
            }
            break;
            case DOWN: {
                Location startLoc = start.getLocation();
                Location endLoc = end.getLocation();
                if (startLoc.getBlockZ() != endLoc.getBlockZ() || startLoc.getBlockX() != endLoc.getBlockX()) {
                    throw new InvalidDirectionException();
                } else {
                    Block tempBlock = start;
                    while (!tempBlock.getLocation().equals(end.getLocation()) && traversed < maxTraverse) {
                        blockSet.add(tempBlock);
                        switch (orientation) {
                            case NORTH:
                            case SOUTH: {
                                blockSet.add(tempBlock.getRelative(BlockFace.WEST));
                                blockSet.add(tempBlock.getRelative(BlockFace.EAST));
                            }
                            break;
                            case EAST:
                            case WEST: {
                                blockSet.add(tempBlock.getRelative(BlockFace.NORTH));
                                blockSet.add(tempBlock.getRelative(BlockFace.SOUTH));
                            }
                            break;
                        }
                        tempBlock = tempBlock.getRelative(direction);
                        traversed++;
                    }
                    if (traversed >= maxTraverse) {
                        log.severe("[BetterMechanics] MaxTraverse hit on " + start.getLocation());
                    }
                }
            }
            break;
            default:
                throw new InvalidDirectionException();
        }
        return blockSet;
    }

    public static Block mapColumn(Block start, int sw, int h, Material m) {
        Block tempBlock;

        int nsw = ~sw + 1; // Negative search Width
        int nh = ~h + 1;
        for (int dy = nh; dy <= h; dy++) {
            for (int dx = nsw; dx <= sw; dx++) {
                tempBlock = start.getRelative(dx, dy, 0);
                // System.out.println(dx+" "+dy+" 0 tempBlock -> "+tempBlock);
                if (tempBlock.getType() == m) {
                    return getUpperBlock(tempBlock);
                }
            }
            for (int dz = nsw; dz <= sw; dz++) {
                tempBlock = start.getRelative(0, dy, dz);
                // System.out.println("0 "+dy+" "+dz+" tempBlock -> "+tempBlock);
                if (tempBlock.getType() == m) {
                    return getUpperBlock(tempBlock);
                }
            }
        }
        return null;
    }

    public static Block mapCuboidRegion(Block start, int sw, Material m) {
        for (int dy = 0; dy <= sw; dy++) {
            for (int dx = 0; dx <= sw; dx++) {
                for (int dz = 0; dz <= sw; dz++) {
                    HashSet<Block> blockSet = new HashSet<Block>();
                    blockSet.add(start.getRelative(dx, dy, dz));
                    blockSet.add(start.getRelative(-dx, dy, dz));
                    blockSet.add(start.getRelative(dx, dy, -dz));
                    blockSet.add(start.getRelative(-dx, dy, -dz));
                    blockSet.add(start.getRelative(dx, -dy, dz));
                    blockSet.add(start.getRelative(-dx, -dy, dz));
                    blockSet.add(start.getRelative(dx, -dy, -dz));
                    blockSet.add(start.getRelative(-dx, -dy, -dz));
                    for (Block b : blockSet) {
                        if (b.getType() == m) {
                            return b;
                        }
                    }
                }
            }
        }
        return null;
    }

    public static HashSet<Block> mapAllInCuboidRegion(Block start, int sw, Material m) {
        Block tempBlock;
        HashSet<Block> blockSet = new HashSet<Block>();
        int nsw = ~sw + 1;
        for (int dx = nsw; dx <= sw; dx++) {
            for (int dy = nsw; dy <= sw; dy++) {
                for (int dz = nsw; dz <= sw; dz++) {
                    tempBlock = start.getRelative(dx, dy, dz);
                    if (tempBlock.getType() == m) {
                        blockSet.add(tempBlock);
                    }
                }
            }
        }
        return blockSet;
    }

    private static Block getUpperBlock(Block block) {
        while (block.getRelative(BlockFace.UP).getType() == block.getType()) {
            block = block.getRelative(BlockFace.UP);
        }
        return block;
    }

    public static HashSet<Block> mapFlatRegion(Block start, Material m, int w, int l) throws OutOfBoundsException {
        Block tempBlock = start;
        // System.out.println("Starting block : "+tempBlock);
        int west = 0, east = 0, south = 0, north = 0, width, length;
        while (checkInColumn(tempBlock.getRelative(BlockFace.WEST), m, 1) != null) {
            // System.out.println("Adding WEST");
            tempBlock = tempBlock.getRelative(BlockFace.WEST);
            west++;
        }
        tempBlock = start;
        while (checkInColumn(tempBlock.getRelative(BlockFace.EAST), m, 1) != null) {
            tempBlock = tempBlock.getRelative(BlockFace.EAST);
            // System.out.println("Adding EAST");
            east++;
        }
        tempBlock = start;
        while (checkInColumn(tempBlock.getRelative(BlockFace.NORTH), m, 1) != null) {
            tempBlock = tempBlock.getRelative(BlockFace.NORTH);
            // System.out.println("Adding NORTH");
            north++;
        }
        tempBlock = start;
        while (checkInColumn(tempBlock.getRelative(BlockFace.SOUTH), m, 1) != null) {
            tempBlock = tempBlock.getRelative(BlockFace.SOUTH);
            // System.out.println("Adding SOUTH");
            south++;
        }
        if ((north + south) > (east + west)) {
            length = (north + south);
            width = (east + west);
        } else {
            width = (north + south);
            length = (east + west);
        }
        if (width > w || length > l) {
            /* System.out.println("w:"+w+" width : "+width);
            System.out.println("l:"+l+" length : "+length); */
            throw new OutOfBoundsException();
        }
        start = start.getRelative((~west + 1), 0, (~north + 1));
        HashSet<Block> blockSet = new HashSet<Block>();
        for (int dx = 0; dx <= (east + west); dx++) {
            for (int dz = 0; dz <= (north + south); dz++) {
                Block b = start.getRelative(dx, 0, dz);
                // Location ll = b.getLocation();
                tempBlock = checkInColumn(b, m, 1);
                // System.out.println("Fetching ("+ll.getBlockX()+","+ll.getBlockY()+","+ll.getBlockZ()+")"+tempBlock);
                if (tempBlock != null) {
                    blockSet.add(getUpperBlock(tempBlock));
                }
            }
        }
        return blockSet;
    }

    private static Block checkInColumn(Block start, Material m, int h) {
        int nh = ~h + 1;
        for (int dy = nh; dy <= h; dy++) {
            if (start.getRelative(0, dy, 0).getType() == m) {
                return start.getRelative(0, dy, 0);
            }
        }
        return null;
    }

    public static org.bukkit.block.Sign findMechanicsSign(Block block, BlockFace direction, MechanicsType type, int maxBlockDistance) throws BlockNotFoundException {
        for (int d = 0; d < maxBlockDistance; d++) {
            block = block.getRelative(direction);
            if (SignUtil.isSign(block)) {
                org.bukkit.block.Sign s = SignUtil.getSign(block);
                if (s != null) {
                    if (SignUtil.getMechanicsType(s) == type) {
                        return s;
                    }
                }
            }
        }
        throw new BlockNotFoundException();
    }
}