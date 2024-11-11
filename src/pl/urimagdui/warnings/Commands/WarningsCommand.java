package pl.urimagdui.warnings.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.urimagdui.warnings.Data.Config;
import pl.urimagdui.warnings.Data.User;
import pl.urimagdui.warnings.Main;
import pl.urimagdui.warnings.Managers.UserManager;
import pl.urimagdui.warnings.Utils.Util;

public class WarningsCommand implements CommandExecutor {

    public WarningsCommand(Main main) {
        main.getCommand("ostrzezenia").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return Util.sendMessage(sender, "BLAD: Tylko gracz moze uzyc tej komendy.");
        }

        Player player = (Player)sender;
        User u = UserManager.getUser(player);

        if (u == null) {
            return false;
        }

        for (String s : Config.Config_Warnings) {
            Util.sendMessage(sender, s.replace("{AMOUNT}", Integer.toString(u.getWarnAmount())).replace("{PLAYER}", "OSTRZEZENIA").replace("{WARNINGS}", u.getMessages()));
        }
        return false;
    }
}