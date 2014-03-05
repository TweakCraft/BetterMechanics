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

package net.edoxile.bettermechanics.mechanics;

import net.edoxile.bettermechanics.BetterMechanics;
import net.edoxile.bettermechanics.event.PlayerEvent;
import net.edoxile.bettermechanics.handlers.ConfigHandler;
import net.edoxile.bettermechanics.mechanics.interfaces.BlockMechanicListener;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Edoxile
 */
public class Ammeter extends BlockMechanicListener {

    private ConfigHandler.AmmeterConfig config = BetterMechanics.getInstance().getConfigHandler().getAmmeterConfig();
    private List<Material> tool = Arrays.asList(config.getTool());
    private List<Material> target = Arrays.asList(Material.REDSTONE_WIRE);

    @Override
    public boolean isTriggeredByRedstone() {
        return false;
    }

    @Override
    public boolean isTriggeredByPlayer() {
        return true;
    }

    @Override
    public List<Material> getMechanicActivators() {
        return tool;
    }

    @Override
    public List<Material> getMechanicTargets() {
        return target;
    }

    @Override
    public String getName() {
        return "Ammeter";
    }

    @Override
    public boolean isEnabled() {
        return config.isEnabled();
    }

    @Override
    public void onBlockRightClick(PlayerEvent event) {
        String msg = "The current is: [" + ChatColor.GREEN;
        for (byte i = 0; i < event.getBlock().getData(); i++) {
            msg += "|";
        }
        msg += ChatColor.DARK_RED;
        for (byte i = event.getBlock().getData(); i < 15; i++) {
            msg += "|";
        }
        msg += ChatColor.WHITE + "] " + ChatColor.RED + Byte.toString(event.getBlock().getData()) + ChatColor.WHITE + " A.";
        event.getPlayer().sendMessage(msg);
    }
}
