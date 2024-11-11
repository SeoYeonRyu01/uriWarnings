package pl.urimagdui.warnings;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pl.urimagdui.warnings.Commands.WarnCommand;
import pl.urimagdui.warnings.Commands.WarningsCommand;
import pl.urimagdui.warnings.Data.Config;
import pl.urimagdui.warnings.Listeners.PlayerJoinListener;
import pl.urimagdui.warnings.Managers.UserManager;
import pl.urimagdui.warnings.MySQL.Modes.StoreMode;
import pl.urimagdui.warnings.MySQL.Modes.StoreMySQL;
import pl.urimagdui.warnings.MySQL.Modes.StoreSQLITE;
import pl.urimagdui.warnings.MySQL.Store;
import pl.urimagdui.warnings.Utils.Logger;

public class Main extends JavaPlugin {

    private static Main plugin;
    public static Store store;

    public static Main getPlugin() {
        return plugin;
    }

    public static Store getStore() {
        return store;
    }

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        new Config();
        setupDatabase();
        UserManager.setup();
        new WarnCommand(this);
        new WarningsCommand(this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Logger.info("Plugin wlaczyl sie poprawnie.");
    }

    @Override
    public void onDisable() {
        for (Player players : Bukkit.getOnlinePlayers()) {
            UserManager.saveUser(players);
        }

        if (Main.store != null && Main.store.isConnected()) {
            Main.store.disconnect();
        }
        Logger.info("Plugin wylaczyl sie poprawnie.");
        plugin = null;
    }

    public static void setupDatabase() {
        switch (StoreMode.getByName(Config.DATABASE_MODE)) {
            case MYSQL:
                Main.store = (Store)new StoreMySQL(Config.DATABASE_MYSQL_HOST, Config.DATABASE_MYSQL_PORT, Config.DATABASE_MYSQL_USER, Config.DATABASE_MYSQL_PASS, Config.DATABASE_MYSQL_NAME, Config.DATABASE_TABLEPREFIX);
                break;
            case SQLITE:
                Main.store = (Store)new StoreSQLITE(Config.DATABASE_SQLITE_NAME, Config.DATABASE_TABLEPREFIX);
                break;
            default:
                Logger.warning(new String[] { "Value of databse mode is not valid! Using SQLITE as database!" });
                Main.store = (Store)new StoreSQLITE(Config.DATABASE_SQLITE_NAME, Config.DATABASE_TABLEPREFIX);
                break;
        }
        boolean conn = Main.store.connect();
        if (conn) {
            Main.store.update(true, "CREATE TABLE IF NOT EXISTS `{P}users` (" + ((Main.store.getStoreMode() == StoreMode.MYSQL) ? "`id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT," : "`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,") +
                    "`name` varchar(255) NOT NULL, " +
                    "`uuid` varchar(255) NOT NULL, " +
                    "`warnings` text NOT NULL); ");
        }
    }
}
