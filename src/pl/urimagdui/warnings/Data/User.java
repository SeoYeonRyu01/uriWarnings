package pl.urimagdui.warnings.Data;

import org.bukkit.entity.Player;
import pl.urimagdui.warnings.Main;
import pl.urimagdui.warnings.MySQL.Entry;
import pl.urimagdui.warnings.Utils.Util;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User implements Entry {

    private final String nick;
    private final UUID uuid;
    public Map<Integer, String> warn;

    public User(Player player) {
        super();
        nick = player.getName();
        uuid = player.getUniqueId();
        warn = new HashMap<Integer, String>();
        insert();
    }

    public User(ResultSet rs) throws SQLException {
        nick = rs.getString("name");
        uuid = UUID.fromString(rs.getString("uuid"));
        warn = Util.deserializeMap(rs.getString("warnings"));
    }

    public String getName() {
        return this.nick;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public Map<Integer, String> getWarnings() {
        return this.warn;
    }

    public void addWarn(String warning) {
        getWarnings().put(getWarnings().size() + 1, warning);
    }

    public void removeWarn(int id) {
        warn.remove(id);

        Map<Integer, String> reorderedWarnings = new HashMap<>();
        int newId = 1;

        for (String warning : warn.values()) {
            reorderedWarnings.put(newId, warning);
            newId++;
        }

        warn = reorderedWarnings;
    }

    public boolean isWarnExist(int id) {
        return getWarnings().get(id) != null;
    }

    public int getWarnAmount() {
        return getWarnings().size();
    }

    public String getMessages() {
        StringBuilder warnings = new StringBuilder();
        int i = 1;

        for (String s1 : getWarnings().values()) {
            warnings.append(Config.Config_WarnLook.replace("{ID}", Integer.toString(i)).replace("{WARN}", s1)).append("\n");
            i++;
        }

        return warnings.toString();
    }

    @Override
    public void insert() {
        String serializedWarnings = Util.serializeMap(getWarnings());
        Main.getStore().update(false, "INSERT INTO `{P}users`(`id`, `name`, `uuid`, `warnings`) VALUES (NULL, '" + getName() + "', '" + getUuid() + "', '" + serializedWarnings + "')");
    }

    @Override
    public void update(boolean now) {
        String serializedWarnings = Util.serializeMap(getWarnings());
        Main.getStore().update(now, "UPDATE `{P}users` SET `uuid`='" + getUuid() + "', `warnings`='" + serializedWarnings + "' WHERE `name`='" + getName() + "'");
    }

    @Override
    public void delete() {
        Main.getStore().update(false, "DELETE FROM `{P}users` WHERE `name` = '" + getName() + "'");
    }
}