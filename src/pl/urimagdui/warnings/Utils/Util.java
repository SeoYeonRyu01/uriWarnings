package pl.urimagdui.warnings.Utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Util {

    public static String fixColor(final String s) {
        if (s == null) {
            return "";
        }
        return ChatColor.translateAlternateColorCodes('&', s.replace("{.}", "•").replace("{>}", "»").replace("{<3}", "♥").replace("{X}", "✖").replace("{Y}", "✔").replace("{:>}", "►").replace("{:<}", "◄").replace("{::>}", "⊳"));
    }

    public static boolean sendMessage(final CommandSender sender, final String message, final String permission) {
        if (sender instanceof ConsoleCommandSender) {
            sendMessage(sender, message);
        }
        return permission != null && permission != "" && sender.hasPermission(permission) && sendMessage(sender, message);
    }

    public static boolean sendMessage(final CommandSender sender, final String message) {
        if (sender instanceof Player) {
            if (message != null || message != "") {
                sender.sendMessage(fixColor(message));
            }
        } else {
            sender.sendMessage(ChatColor.stripColor(fixColor(message)));
        }
        return true;
    }

    public static boolean sendMessage(final Collection<? extends CommandSender> collection, final String message) {
        for (final CommandSender cs : collection) {
            sendMessage(cs, message);
        }
        return true;
    }

    public static Map<Integer, String> deserializeMap(String serializedMap) {
        Map<Integer, String> map = new HashMap<>();
        if (serializedMap != null && !serializedMap.isEmpty()) {
            String[] entries = serializedMap.split(";%@%;");
            for (String entry : entries) {
                String[] keyValue = entry.split("=");
                if (keyValue.length == 2) {
                    try {
                        Integer key = Integer.parseInt(keyValue[0]);
                        String value = keyValue[1];
                        map.put(key, value);
                    } catch (NumberFormatException e) {
                        System.out.println("Niepoprawny format klucza: " + keyValue[0]);
                    }
                }
            }
        }
        return map;
    }

    public static String serializeMap(Map<Integer, String> map) {
        return map.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(";%@%;"));
    }

}
