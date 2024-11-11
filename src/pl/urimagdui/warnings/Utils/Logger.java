package pl.urimagdui.warnings.Utils;

import java.util.logging.Level;
import pl.urimagdui.warnings.Main;

public class Logger {
    public static void info(String... logs) {
        for (String s : logs)
            log(Level.INFO, s);
    }

    public static void warning(String... logs) {
        for (String s : logs)
            log(Level.WARNING, s);
    }

    public static void severe(String... logs) {
        for (String s : logs)
            log(Level.SEVERE, s);
    }

    public static void log(Level level, String log) {
        Main.getPlugin().getLogger().log(level, log);
    }


}
