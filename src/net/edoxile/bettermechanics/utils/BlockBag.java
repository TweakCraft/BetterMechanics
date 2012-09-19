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

import net.edoxile.bettermechanics.exceptions.OutOfMaterialException;
import net.edoxile.bettermechanics.exceptions.OutOfSpaceException;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Logger;

/**
 * @author GuntherDW
 */
public abstract class BlockBag {

    protected Block bagBlock = null;
    protected final Logger log = Logger.getLogger("Minecraft");

    public BlockBag(Block bag) {
        this.bagBlock = bag;
    }

    public boolean safeRemoveItems(ItemStack itemStack) throws OutOfMaterialException {
        return false;
    }

    public boolean safeAddItems(ItemStack itemStack) throws OutOfSpaceException {
        return false;
    }

    /**
     * Is this an admin only blockbag?
     *
     * @return true if it is a restricted blockbag
     */
    public boolean isRestricted() {
        return false;
    }

    /**
     * Is this BlockBag a Block Source?
     *
     * @return true if it is a blocksource
     */
    public boolean isBlockSource() {
        return false;
    }

    /**
     * Is this BlockBag a Block Hole?
     *
     * @return true if it is a blockhole
     */
    public boolean isBlockHole() {
        return false;
    }

    /**
     * What kind of material is this BlockBag made of?
     *
     * @return Material the source material of this BlockBag
     */
    public Material getSourceMaterial() {
        return null;
    }

    /**
     * Checks if the mentioned block is an instance of this BlockBag
     *
     * @param block Block to check
     * @return true if it is a suitable instance
     */
    public boolean isBlockBag(BlockState block) {
        return false;
    }

}
