package pl.urimagdui.warnings.Data;

import org.bukkit.configuration.file.FileConfiguration;
import pl.urimagdui.warnings.Main;

import java.util.List;

public class Config {

    public static String DATABASE_MODE;
    public static String DATABASE_TABLEPREFIX;
    public static String DATABASE_MYSQL_HOST;
    public static int DATABASE_MYSQL_PORT;
    public static String DATABASE_MYSQL_USER;
    public static String DATABASE_MYSQL_PASS;
    public static String DATABASE_MYSQL_NAME;
    public static String DATABASE_SQLITE_NAME;

    public static String Config_Permission;
    public static String Config_NoPermission;
    public static String Config_HelpMessage;
    public static String Config_Warned;
    public static String Config_WarnLook;
    public static String Config_WarnRemoved;
    public static List<String> Config_Warnings;

    public Config() {
        this.getConfig();
    }

    private void getConfig() {
        FileConfiguration main = Main.getPlugin().getConfig();

        DATABASE_MODE = main.getString("Database.Mode");
        DATABASE_TABLEPREFIX = main.getString("Database.TablePrefix");
        DATABASE_MYSQL_HOST = main.getString("Database.Host");
        DATABASE_MYSQL_PORT = main.getInt("Database.Port");
        DATABASE_MYSQL_USER = main.getString("Database.User");
        DATABASE_MYSQL_PASS = main.getString("Database.Password");
        DATABASE_MYSQL_NAME = main.getString("Database.Name");
        DATABASE_SQLITE_NAME = main.getString("Database.SqLiteName");
        
        Config_Permission = main.getString("Config.Permission");
        Config_NoPermission = main.getString("Config.NoPermission");
        Config_HelpMessage = main.getString("Config.HelpMessage");
        Config_Warned = main.getString("Config.Warned");
        Config_WarnLook = main.getString("Config.WarnLook");
        Config_WarnRemoved = main.getString("Config.WarnRemoved");
        Config_Warnings = main.getStringList("Config.Warnings");
    }
}
