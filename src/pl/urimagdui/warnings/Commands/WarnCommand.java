package pl.urimagdui.warnings.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.urimagdui.warnings.Data.Config;
import pl.urimagdui.warnings.Data.User;
import pl.urimagdui.warnings.Main;
import pl.urimagdui.warnings.Managers.UserManager;
import pl.urimagdui.warnings.Utils.Logger;
import pl.urimagdui.warnings.Utils.Util;

public class WarnCommand implements CommandExecutor {

    public WarnCommand(Main main) {
        main.getCommand("ostrzez").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission(Config.Config_Permission)) {
            return Util.sendMessage(sender, Config.Config_NoPermission);
        }

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("add")) {
                if (args.length < 3) {
                    return Util.sendMessage(sender, "&cBLAD: &7Podaj nick gracza ktoremu chcesz nadac ostrzezenie lub podaj powod.");
                }

                Player p = Bukkit.getServer().getPlayerExact(args[1]);

                if (p == null) {
                    return Util.sendMessage(sender, "&cBLAD: &7Taki gracz nieistnieje.");
                }

                User u = UserManager.getUser(p);
                if (u == null) {
                    return false;
                }

                StringBuilder sb = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    sb.append(args[i]).append(" ");
                }

                String wiadomosc = sb.toString();
                u.addWarn(wiadomosc);
                return Util.sendMessage(Bukkit.getOnlinePlayers(), Config.Config_Warned.replace("{ADMIN}", sender.getName()).replace("{PLAYER}", p.getName()).replace("{REASON}", wiadomosc));
            } else if (args[0].equalsIgnoreCase("remove")) {
                if (args.length < 3) {
                    return Util.sendMessage(sender, "&cBLAD: &7Podaj nick gracza ktoremu chcesz nadac ostrzezenie lub podaj powod.");
                }

                if (!args[2].matches("[0-9]+")) {
                    return Util.sendMessage(sender, "&cBLAD: &7Liczba ID musi sie skladac tylko z liczb.");
                }

                Player p = Bukkit.getServer().getPlayerExact(args[1]);

                if (p == null) {
                    return Util.sendMessage(sender, "&cBLAD: &7Taki gracz nieistnieje.");
                }

                User u = UserManager.getUser(p);

                if (u == null) {
                    return Util.sendMessage(sender, "&cBLAD: &7Taki gracz nieistnieje w bazie danych.");
                }

                int id = Integer.parseInt(args[2]);

                if (!u.isWarnExist(id)) {
                    return Util.sendMessage(sender, "&cBLAD: &7Ten gracz nie ma ostrzezenia o podanym ID.");
                }

                u.removeWarn(id);
                return Util.sendMessage(sender, Config.Config_WarnRemoved.replace("{PLAYER}", p.getName()).replace("{ID}", Integer.toString(id)));
            } else if (args[0].equalsIgnoreCase("info")) {
                if (args.length < 2) {
                    return Util.sendMessage(sender, "&cBLAD: &7Podaj nick gracza ktorego chcesz sprawdzic.");
                }

                Player p = Bukkit.getServer().getPlayerExact(args[1]);

                if (p == null) {
                    return Util.sendMessage(sender, "&cBLAD: &7Taki gracz nieistnieje.");
                }

                User u = UserManager.getUser(p);

                if (u == null) {
                    return Util.sendMessage(sender, "&cBLAD: &7Taki gracz nieistnieje w bazie danych.");
                }

                for (String s : Config.Config_Warnings) {
                    Util.sendMessage(sender, s.replace("{AMOUNT}", Integer.toString(u.getWarnAmount())).replace("{PLAYER}", p.getName()).replace("{WARNINGS}", u.getMessages()));
                }

            }

        } else {
            return Util.sendMessage(sender, Config.Config_HelpMessage);
        }

        return false;
    }
}
