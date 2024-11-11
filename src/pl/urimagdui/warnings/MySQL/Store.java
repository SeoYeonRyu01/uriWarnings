package pl.urimagdui.warnings.MySQL;

import pl.urimagdui.warnings.MySQL.Modes.StoreMode;
import java.sql.*;

public interface Store {
    Connection getConnection();
    boolean connect();
    void disconnect();
    void reconnect();
    boolean isConnected();
    ResultSet query(String p0);
    void query(String p0, Callback<ResultSet> p1);
    void update(boolean p0, String p1);
    ResultSet update(String p0);
    StoreMode getStoreMode();
}
