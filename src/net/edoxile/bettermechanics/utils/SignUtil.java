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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package net.edoxile.bettermechanics.utils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Edoxile
 */
public class SignUtil {

    //TODO: lolwhut, this has to be simplified
    public static BlockFace getFacing(Sign sign) {
        if(sign.getType() == Material.WALL_SIGN){
            switch(sign.getData().getData()){
                case 0x2:
                    return BlockFace.NORTH;
                case 0x3:
                    return BlockFace.SOUTH;
                case 0x4:
                    return BlockFace.WEST;
                case 0x5:
                    return BlockFace.EAST;
                default:
                    return BlockFace.SELF;
            }
        } else {
            switch (sign.getData().getData()){
                case 0x0:
                    return BlockFace.SOUTH;
                case 0x1:
                    return BlockFace.SOUTH_SOUTH_WEST;
                case 0x2:
                    return BlockFace.SOUTH_WEST;
                case 0x3:
                    return BlockFace.WEST_SOUTH_WEST;
                case 0x4:
                    return BlockFace.WEST;
                case 0x5:
                    return BlockFace.WEST_NORTH_WEST;
                case 0x6:
                    return BlockFace.NORTH_WEST;
                case 0x7:
                    return BlockFace.NORTH_NORTH_WEST;
                case 0x8:
                    return BlockFace.NORTH;
                case 0x9:
                    return BlockFace.NORTH_NORTH_EAST;
                case 0xa:
                    return BlockFace.NORTH_EAST;
                case 0xb:
                    return BlockFace.EAST_NORTH_EAST;
                case 0xc:
                    return BlockFace.EAST;
                case 0xd:
                    return BlockFace.EAST_SOUTH_EAST;
                case 0xe:
                    return BlockFace.SOUTH_EAST;
                case 0xf:
                    return BlockFace.SOUTH_SOUTH_EAST;
                default:
                    return BlockFace.SELF;
            }
        }
    }

    public static BlockFace getAttachedFace(Sign sign) {
        BlockState state = sign.getBlock().getState();
        if (state instanceof org.bukkit.material.Sign) {
            org.bukkit.material.Sign s = (org.bukkit.material.Sign) state;
            return s.getAttachedFace();
        } else {
            return BlockFace.SELF;
        }
    }

    public static BlockFace getOrdinalFacing(Sign sign) {
        BlockFace blockFace = getFacing(sign);
        switch (blockFace) {
            case NORTH:
            case SOUTH:
            case WEST:
            case EAST:
                return blockFace;
            default:
                return null;
        }
    }

    public static BlockFace getOrdinalAttachedFace(Sign sign) {
        BlockFace blockFace = getAttachedFace(sign);
        switch (blockFace) {
            case NORTH:
            case SOUTH:
            case WEST:
            case EAST:
                return blockFace;
            default:
                return null;
        }
    }

    public static boolean isSign(Block block) {
        return block.getTypeId() == Material.WALL_SIGN.getId() || block.getTypeId() == Material.SIGN_POST.getId();
    }

    public static String getMechanicsIdentifier(Sign sign) {
        String id = sign.getLine(1);
        if (id.charAt(0) == '[' && id.charAt(id.length() - 1) == ']')
            id = id.substring(1, id.length() - 1);
        return id;
    }

    public static void setMechanicsIdentifier(Sign sign, String identifier) {
        sign.setLine(1, identifier);
    }

    public static Sign getSign(Block block) {
        if (block.getState() instanceof Sign) {
            return (Sign) block.getState();
        } else {
            return null;
        }
    }

    public static boolean isOrdinal(Sign sign) {
        return isOrdinal(getFacing(sign));
    }

    public static boolean isOrdinal(BlockFace direction) {
        switch (direction) {
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
                return true;
            default:
                return false;
        }
    }

    public static boolean isWallSign(Block b) {
        return b.getType() == Material.WALL_SIGN;
    }

    public static boolean isWallSign(Sign s) {
        return isWallSign(s.getBlock());
    }
}
