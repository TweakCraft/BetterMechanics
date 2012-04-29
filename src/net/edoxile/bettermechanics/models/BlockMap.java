/*
 * Copyright (c) 2012.
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

package net.edoxile.bettermechanics.models;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Edoxile
 */
public class BlockMap {
    private final List<Block> blockList;
    private final Block startBlock;
    private final Block endBlock;
    private final Material material;
    private final byte materialData;

    public BlockMap(List<Block> blocks, Block start, Block end, Material type, byte data) {
        blockList = blocks;
        startBlock = start;
        endBlock = end;
        material = type;
        materialData = data;
    }

    public List<Block> getList() {
        return blockList;
    }

    public Block getStartBlock() {
        return startBlock;
    }

    public Block getEndBlock() {
        return endBlock;
    }

    public Material getMaterial() {
        return material;
    }

    public byte getMaterialData() {
        return materialData;
    }
}
