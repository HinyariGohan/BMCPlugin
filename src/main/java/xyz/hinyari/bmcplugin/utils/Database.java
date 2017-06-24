package xyz.hinyari.bmcplugin.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import xyz.hinyari.bmcplugin.BMCPlugin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Created by Hinyari_Gohan on 2017/05/06.
 */
public class Database
{

    private HikariDataSource hikari;
    private Connection connection;

    public Map<UUID, Integer> uuidCache = new HashMap<>();
    //public Map<String, Integer> nameCache = new HashMap<>();
    public Map<Integer, String> numberCache = new HashMap<>();

    public Database(String host, int port, String database, String user, String password)
    {
        HikariConfig config = new HikariConfig();
        Properties properties = new Properties();
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;

        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setJdbcUrl(url);

        properties.put("user", user);
        properties.put("password", password);

        config.setAutoCommit(true);
        config.setInitializationFailFast(true);
        config.setDataSourceProperties(properties);
        BMCPlugin.instance.log(Level.INFO, url);
        config.setConnectionInitSql("SELECT 1");

        // 接続する
        hikari = new HikariDataSource(config);

    }

    public Connection getConnection() throws SQLException
    {
        if (hikari != null)
        {
            return hikari.getConnection();
        }
        return null;
    }

    public void close()
    {
        if (hikari != null)
        {
            hikari.close();
        }
    }

}