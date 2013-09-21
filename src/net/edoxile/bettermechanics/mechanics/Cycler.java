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

package net.edoxile.bettermechanics.mechanics;

import net.edoxile.bettermechanics.utils.MechanicsConfig;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Edoxile
 */
public class Cycler {
    public static boolean cycle(Player p, Block b, MechanicsConfig c) {
        if (c.getPermissionConfig().checkZonesCreate(p, b)) {
            byte newByte = -1;
            switch(b.getType()) {
                case CHEST:
                    newByte = (byte) (b.getData() + 1);
                    newByte = (newByte == 6) ? 2 : newByte;
                    break;
                case LOG:
                    newByte = (byte) (b.getData() + 4);
                    newByte = (newByte > 15) ? (byte) (newByte - 16) : newByte;
                    break;
                case BIRCH_WOOD_STAIRS:
                case BRICK_STAIRS:
                case COBBLESTONE_STAIRS:
                case JUNGLE_WOOD_STAIRS:
                case NETHER_BRICK_STAIRS:
                case QUARTZ_STAIRS:
                case SANDSTONE_STAIRS:
                case SMOOTH_STAIRS:
                case SPRUCE_WOOD_STAIRS:
                case WOOD_STAIRS:
                    byte data = (byte) (b.getData());
                    byte lastTwo = (byte) ((data & 0b0011) + 1);
                    if (lastTwo == 4){
                        // flip bit 4, clear bits 2 and 1
                        newByte = (byte) (((data ^ (0b1000)) & 0b1100));
                    } else {
                        // put bits 2 and 1 from lastTwo into data
                        newByte = (byte) ((data & 0b1100) | lastTwo);
                    }
                    break;
                case STEP:
                case WOOD_STEP:
                    newByte = (byte) (b.getData() ^ (0b1000));
                    break;                    

            }
            if(newByte != -1) {
                b.setData(newByte);
            }
            return true;
        } else {
            return false;
        }
    }
}
