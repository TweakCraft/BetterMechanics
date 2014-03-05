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

package net.edoxile.bettermechanics.handlers;

import net.edoxile.bettermechanics.BetterMechanics;
import net.edoxile.bettermechanics.event.Event;
import net.edoxile.bettermechanics.event.PlayerEvent;
import net.edoxile.bettermechanics.event.RedstoneEvent;
import net.edoxile.bettermechanics.mechanics.interfaces.BlockMechanicListener;
import net.edoxile.bettermechanics.mechanics.interfaces.IMechanicCommandListener;
import net.edoxile.bettermechanics.mechanics.interfaces.IMechanicListener;
import net.edoxile.bettermechanics.mechanics.interfaces.SignMechanicListener;
import net.edoxile.bettermechanics.utils.SignUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Edoxile
 */

public class MechanicsHandler {

    private final HashMap<String, HashSet<SignMechanicListener>> redstoneSignMechanicMap = new HashMap<String, HashSet<SignMechanicListener>>();
    private final HashMap<Material, HashSet<BlockMechanicListener>> redstoneBlockMechanicMap = new HashMap<Material, HashSet<BlockMechanicListener>>();

    private final HashMap<String, HashSet<SignMechanicListener>> signMechanicMap = new HashMap<String, HashSet<SignMechanicListener>>();
    private final HashMap<Material, HashSet<BlockMechanicListener>> blockMechanicMap = new HashMap<Material, HashSet<BlockMechanicListener>>();

    private final HashMap<String, IMechanicCommandListener> commandableMechanicMap = new HashMap<String, IMechanicCommandListener>();

    public void addMechanic(IMechanicListener mechanicListener) {
        if (!mechanicListener.isEnabled())
            return;
        if (mechanicListener instanceof SignMechanicListener) {
            SignMechanicListener signMechanic = (SignMechanicListener) mechanicListener;
            if (signMechanic.isTriggeredByRedstone()) {
                for (String identifier : signMechanic.getIdentifiers()) {
                    HashSet<SignMechanicListener> list = redstoneSignMechanicMap.get(identifier);
                    if (list == null) {
                        list = new HashSet<SignMechanicListener>();
                        list.add(signMechanic);
                        redstoneSignMechanicMap.put(identifier, list);
                    } else {
                        list.add(signMechanic);
                        //TODO: check if this list works as it's supposed to (with passing a reference)
                        //redstoneSignMechanicMap.put(identifier, list);
                    }
                }
            }
            if (signMechanic.isTriggeredByPlayer()) {
                for (String identifier : signMechanic.getIdentifiers()) {
                    HashSet<SignMechanicListener> list = signMechanicMap.get(identifier);
                    if (list == null) {
                        list = new HashSet<SignMechanicListener>();
                        list.add(signMechanic);
                        signMechanicMap.put(identifier, list);
                    } else {
                        list.add(signMechanic);
                        //TODO: check if this list works as it's supposed to (with passing a reference)
                        //signMechanicMap.put(identifier, list);
                    }
                }
            }
        } else if (mechanicListener instanceof BlockMechanicListener) {
            BlockMechanicListener blockMechanicListener = (BlockMechanicListener) mechanicListener;
            if (blockMechanicListener.isTriggeredByRedstone()) {
                for (Material target : blockMechanicListener.getMechanicTargets()) {
                    HashSet<BlockMechanicListener> list = redstoneBlockMechanicMap.get(target);
                    if (list == null) {
                        list = new HashSet<BlockMechanicListener>();
                        list.add(blockMechanicListener);
                        redstoneBlockMechanicMap.put(target, list);
                    } else {
                        list.add(blockMechanicListener);
                        //TODO: check if this list works as it's supposed to (with passing a reference)
                        //redstoneBlockMechanicMap.put(target, list);
                    }
                }
            }
            if (blockMechanicListener.isTriggeredByPlayer()) {
                for (Material target : blockMechanicListener.getMechanicTargets()) {
                    HashSet<BlockMechanicListener> list = blockMechanicMap.get(target);
                    if (list == null) {
                        list = new HashSet<BlockMechanicListener>();
                        list.add(blockMechanicListener);
                        blockMechanicMap.put(target, list);
                    } else {
                        list.add(blockMechanicListener);
                        //TODO: check if this list works as it's supposed to (with passing a reference)
                        //blockMechanicMap.put(target, list);
                    }
                }
            }
        }

        if (mechanicListener instanceof IMechanicCommandListener) {
            IMechanicCommandListener mechanicCommandListener = (IMechanicCommandListener) mechanicListener;
            if (commandableMechanicMap.containsKey(mechanicCommandListener.getCommandName())) {
                BetterMechanics.log("Mechanic: " + mechanicCommandListener.getName() + " tried to register a command that has already been registered!", Level.SEVERE);
            } else {
                commandableMechanicMap.put(mechanicCommandListener.getCommandName(), mechanicCommandListener);
            }
        }
    }

    public boolean callCommandEvent(Command command, CommandSender commandSender, String[] args) {
        IMechanicCommandListener mechanic = commandableMechanicMap.get(command.getName());
        return mechanic != null && mechanic.onCommand(commandSender, command, args);
    }

    public void callPlayerEvent(PlayerEvent event) {
        if (SignUtil.isSign(event.getBlock()) && event.getAction() != PlayerEvent.Action.PLACE) {
            Set<SignMechanicListener> listeners = getSignListeners(event);
            main:
            for (SignMechanicListener listener : listeners) {
                if (listener.isTriggeredByPlayer() && listener.isThisMechanic(event.getSign(), event.getPlayer().getItemInHand().getType())) {
                    switch (event.getAction()) {
                        case RIGHT_CLICK:
                            listener.onPlayerRightClickSign(event);
                            break;
                        case LEFT_CLICK:
                            listener.onPlayerLeftClickSign(event);
                            break;
                        case BREAK:
                            listener.onPlayerBreakSign(event);
                        case PLACE:
                            listener.onBlockPlace(event);
                        default:
                            break main;
                    }
                }
            }
        } else {
            Set<BlockMechanicListener> listeners = getBlockListeners(event);
            if (listeners == null)
                return;
            main:
            for (BlockMechanicListener listener : listeners) {
                if (listener.isTriggeredByPlayer() && listener.isThisMechanic(event.getPlayer().getItemInHand().getType(), event.getBlock().getType())) {
                    switch (event.getAction()) {
                        case RIGHT_CLICK:
                            listener.onBlockRightClick(event);
                            break;
                        case LEFT_CLICK:
                            listener.onBlockLeftClick(event);
                            break;
                        case BREAK:
                            listener.onBlockBreak(event);
                            break;
                        case PLACE:
                            listener.onBlockPlace(event);
                        default:
                            break main;
                    }
                }
            }
        }
    }

    public void callRedstoneEvent(RedstoneEvent event) {
        if (SignUtil.isSign(event.getBlock())) {
            Set<SignMechanicListener> listeners = getSignListeners(event);
            main:
            for (SignMechanicListener listener : listeners) {
                if (listener.isTriggeredByRedstone()) {
                    switch (event.getState()) {
                        case ON:
                            listener.onSignPowerOn(event);
                            break;
                        case OFF:
                            listener.onSignPowerOff(event);
                            break;
                        default:
                            break main;
                    }
                }
            }
        } else {
            Set<BlockMechanicListener> listeners = getBlockListeners(event);
            main:
            for (BlockMechanicListener listener : listeners) {
                if (listener.isTriggeredByRedstone()) {
                    switch (event.getState()) {
                        case ON:
                            listener.onBlockPowerOn(event);
                            break;
                        case OFF:
                            listener.onBlockPowerOff(event);
                            break;
                        default:
                            break main;
                    }
                }
            }
        }
    }

    //TODO: check mechanic activators
    private Set<SignMechanicListener> getSignListeners(Event event) {
        if (!SignUtil.isSign(event.getBlock()))
            return null;

        Set<SignMechanicListener> listeners = new HashSet<SignMechanicListener>();
        Sign sign = (Sign) event.getBlock().getState();
        HashSet<SignMechanicListener> data;
        if (event instanceof RedstoneEvent) {
            data = redstoneSignMechanicMap.get(SignUtil.getMechanicsIdentifier(sign));
            if (data != null)
                listeners.addAll(data);
            data = redstoneSignMechanicMap.get("");
            if (data != null)
                listeners.addAll(data);
        } else if (event instanceof PlayerEvent) {
            data = signMechanicMap.get(SignUtil.getMechanicsIdentifier(sign));
            if (data != null)
                listeners.addAll(data);
            data = signMechanicMap.get("");
            if (data != null)
                listeners.addAll(data);
        } else {
            BetterMechanics.log("Something went wrong, unknown type passed to getSignListeners()");
        }

        return listeners;
    }


    //TODO: check mechanic activators
    private Set<BlockMechanicListener> getBlockListeners(Event event) {
        if (SignUtil.isSign(event.getBlock()))
            return null;

        Set<BlockMechanicListener> listeners = new HashSet<BlockMechanicListener>();
        Block block = event.getBlock();
        HashSet<BlockMechanicListener> data;

        if (event instanceof RedstoneEvent) {
            data = redstoneBlockMechanicMap.get(block.getType());
            if (data != null)
                listeners.addAll(data);
            data = redstoneBlockMechanicMap.get(Material.AIR);
            if (data != null)
                listeners.addAll(data);
        } else if (event instanceof PlayerEvent) {
            data = blockMechanicMap.get(block.getType());
            if (data != null)
                listeners.addAll(data);
            data = blockMechanicMap.get(Material.AIR);
            if (data != null)
                listeners.addAll(data);
        } else {
            BetterMechanics.log("Something went wrong, unknown type passed to getBlockListeners()");
        }

        return listeners;
    }

    public Set<SignMechanicListener> getSignMechanics() {
        //Returns a list of sign mechanics. Is needed by BMListener to set correct ID's on each sign.
        Set<SignMechanicListener> set = new HashSet<SignMechanicListener>();
        for (Set<SignMechanicListener> set2 : signMechanicMap.values()) {
            if (set2 != null)
                set.addAll(set2);
        }
        for (Set<SignMechanicListener> set2 : redstoneSignMechanicMap.values()) {
            if (set2 != null)
                set.addAll(set2);
        }
        return set;
    }

    public Set<BlockMechanicListener> getBlockMechanics() {
        Set<BlockMechanicListener> set = new HashSet<BlockMechanicListener>();
        for (Set<BlockMechanicListener> set2 : blockMechanicMap.values()) {
            set.addAll(set2);
        }
        return set;
    }
}
