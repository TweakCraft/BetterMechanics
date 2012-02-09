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

package old.bettermechanics.mechanics;

import old.bettermechanics.exceptions.InvalidLineLengthException;
import old.bettermechanics.exceptions.OutOfBoundsException;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Edoxile
 */
public class Pen {
    private static HashMap<Player, String[]> dataMap = new HashMap<Player, String[]>();

    public static void setLine(Player player, String[] args) {
        try {
            String[] data = dataMap.get(player);
            if (data == null) {
                player.sendMessage(ChatColor.YELLOW + "You didn't have a message set. Using an empty sign.");
                data = new String[4];
                data[0] = "";
                data[1] = "";
                data[2] = "";
                data[3] = "";
            }
            int line = Integer.parseInt(args[1]);
            String text = mergeString(args, " ", 2);
            data[line] = text;
            dataMap.put(player, data);
            player.sendMessage(ChatColor.GOLD + "New pen text:");
            for (String s : data) {
                player.sendMessage(ChatColor.GOLD + "[" + s + "]");
            }
        } catch (NumberFormatException ex) {
            player.sendMessage(ChatColor.RED + "Invalid number format for line number.");
        } catch (IndexOutOfBoundsException ex) {
            player.sendMessage(ChatColor.RED + "Invalid line number. Line numbers start at 0 and end at 3.");
        }
    }

    public static void clear(Player player) {
        dataMap.put(player, null);
    }

    public static void clearLine(Player player, String[] args) {
        try {
            int line = Integer.parseInt(args[1]);
            String[] data = dataMap.get(player);
            data[line] = "";
            dataMap.put(player, data);
            player.sendMessage(ChatColor.GOLD + "New pen text:");
            for (String s : data) {
                player.sendMessage(ChatColor.GOLD + "[" + s + "]");
            }
        } catch (NumberFormatException ex) {
            player.sendMessage(ChatColor.RED + "Invalid number format for line number.");
        } catch (IndexOutOfBoundsException ex) {
            player.sendMessage(ChatColor.RED + "Invalid line number. Line numbers start at 0 and end at 3.");
        }
    }

    public static void setLines(Player player, String[] args) {
        try {
            String[] data = parseText(args);
            dataMap.put(player, data);
            player.sendMessage(ChatColor.GOLD + "New pen text:");
            for (String s : data) {
                player.sendMessage(ChatColor.GOLD + "[" + s + "]");
            }
        } catch (OutOfBoundsException e) {
            player.sendMessage(ChatColor.DARK_RED + "Your text contains more than 4 lines.");
        } catch (InvalidLineLengthException e) {
            player.sendMessage(ChatColor.DARK_RED + "At least one of your lines has more than 15 chars.");
        }
    }

    public static void setText(Player player, String[] args) {
        dataMap.put(player, args);
        player.sendMessage(ChatColor.GOLD + "New pen text:");
        for (String s : args) {
            player.sendMessage(ChatColor.GOLD + "[" + s + "]");
        }
    }

    public static String[] getLines(Player player) {
        return dataMap.get(player);
    }

    private static String[] parseText(String[] data) throws OutOfBoundsException, InvalidLineLengthException {
        data = mergeString(data, " ", 1).split("\\^");
        if (data.length > 4) {
            throw new OutOfBoundsException();
        }
        String[] lines = {"", "", "", ""};
        for (int i = 0; i < data.length; i++) {
            if (data[i].length() > 15)
                throw new InvalidLineLengthException();
            lines[i] = data[i];
        }
        return lines;
    }

    private static String mergeString(String[] data, String glue, int offset) {
        String str = "";
        for (int i = offset; i < data.length; i++) {
            str += data[i];
            if ((i + 1) != data.length)
                str += glue;
        }
        return str;
    }

    public static void dump(Player player) {
        if (dataMap.get(player) == null) {
            player.sendMessage(ChatColor.GOLD + "Your pen is empty.");
        } else {
            String[] lines = dataMap.get(player);
            player.sendMessage(ChatColor.GOLD + "Pen dump:");
            for (String s : lines) {
                player.sendMessage(ChatColor.GOLD + "[" + s + "]");
            }
        }
    }
}
