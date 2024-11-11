package pl.urimagdui.warnings.Managers;

import org.bukkit.entity.Player;
import pl.urimagdui.warnings.Data.User;
import pl.urimagdui.warnings.Main;
import pl.urimagdui.warnings.Utils.Logger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class UserManager {

    private static final HashMap<UUID, User> users = new HashMap<>();

    public static void setup() {
        ResultSet rs = Main.getStore().query("SELECT * FROM `{P}users`");
        try {
            while (rs.next()) {
                User u = new User(rs);
                users.put(u.getUuid(), u);
            }
            Logger.info(new String[] { "Zaladowano " + users.size() + " graczy!" });
        } catch (SQLException e) {
            Logger.warning(new String[] { "An error occurred while loading users!", "Error: " + e.getMessage() });
        }
    }

    public static void createUser(final Player p) {
        User u = new User(p);
        users.put(p.getUniqueId(), u);
    }

    public static User getUser(Player player) {
        return users.get(player.getUniqueId());
    }

    public static void saveUser(Player player) {
        User u = getUser(player);
        if (u != null) {
            u.update(true);
        }
    }

    public static HashMap<UUID, User> getUsers() {
        return users;
    }
}
