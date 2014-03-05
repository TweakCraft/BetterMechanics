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
import net.edoxile.bettermechanics.mechanics.interfaces.BlockMechanicListener;
import net.edoxile.bettermechanics.mechanics.interfaces.IMechanicCommandListener;
import net.edoxile.bettermechanics.mechanics.interfaces.SignMechanicListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Edoxile
 */
public class Debugger implements IMechanicCommandListener{
    @Override
    public String getCommandName() {
        return "bmdebug";
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String[] args) {
        Set<BlockMechanicListener> blockSet = BetterMechanics.getInstance().getMechanicsHandler().getBlockMechanics();
        Set<SignMechanicListener> signSet = BetterMechanics.getInstance().getMechanicsHandler().getSignMechanics();
        Iterator<BlockMechanicListener> blockIterator = blockSet.iterator();
        Iterator<SignMechanicListener> signIterator = signSet.iterator();

        return true;
    }

    @Override
    public String getName() {
        return "BetterMechanics Debugger";
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
