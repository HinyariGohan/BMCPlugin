package xyz.hinyari.bmcplugin;

import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.hinyari.bmcplugin.utils.BMCUtils;
import xyz.hinyari.bmcplugin.utils.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.hinyari.bmcplugin.utils.Database;

import java.sql.*;
import java.util.UUID;

public class BMCPlayer
{
    private final BMCPlugin plugin;
    private final Player player;
    private final BMCScoreBoard bmcScoreBoard;
    private final Inventory privateGUI;

    private int id = 0;
    private String number = "000000";

    public BMCPlayer(Player player, BMCPlugin bmcPlugin)
    {
        this.player = player;
        this.plugin = bmcPlugin;
        this.bmcScoreBoard = new BMCScoreBoard(this);
        this.privateGUI = Bukkit.createInventory(player, 54, player.getName() + "'s GUI");
        player.setCollidable(false);
        setupAccount();
    }


    //checkPermというメソッドがあってもいいかもしれない。（そのままエラー処理可能）
    public Player getPlayer()
    {
        return this.player;
    }

    public BMCPlugin getPlugin()
    {
        return this.plugin;
    }

    public BMCScoreBoard getScoreboard()
    {
        return this.bmcScoreBoard;
    }

    public void msg(String message)
    {
        player.sendMessage(BMCUtils.convert((plugin.config.getPrefix() + message)));
    }

    public void msg(String message, String permission)
    {
        if (hasPermission(permission))
            msg(message);
    }

    public void noprefix(String message)
    {
        player.sendMessage(BMCUtils.convert(message));
    }

    public void broadcast(String message)
    {
        Bukkit.broadcastMessage(message);
    }

    public String getName()
    {
        return player.getName();
    }

    public UUID getUUID()
    {
        return player.getUniqueId();
    }

    public ItemStack getItemInMainHand()
    {
        return player.getInventory().getItemInMainHand();
    }

    public boolean hasPermission(String permission)
    {
        return player.hasPermission(permission);
    }

    public boolean errmsg(String message)
    {
        player.sendMessage(BMCUtils.convert(plugin.config.getErrorPrefix() + message));
        return true;
    }

    public boolean errbar(String message)
    {
        new ActionBar(BMCUtils.convert(plugin.config.getErrorPrefix() + message)).sendToPlayer(player);
        return true;
    }

    public void barmsg(String message)
    {
        new ActionBar(BMCUtils.convert(plugin.config.getPrefix() + message)).sendToPlayer(player);
    }

    public boolean noperm()
    {
        errmsg("権限がありません。");
        return true;
    }

    public void openRankmenu()
    {
        player.openInventory(plugin.rankGUIMenu.getMainMenu(this));
    }

    public Inventory getPrivateGUI()
    {
        return this.privateGUI;
    }

    public void playErrSound()
    {
        playSound(Sound.BLOCK_NOTE_BASS, 0.3F, 0.6F);
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                playSound(Sound.BLOCK_NOTE_BASS, 0.3F, 0.6F);
            }
        }.runTaskLater(plugin, 2L);
    }

    /**
     * サウンドを再生します。
     *
     * @param sound  サウンド名(Sound.example)
     * @param volume float ボリューム
     * @param pitch  float ピッチ
     */
    public void playSound(Sound sound, float volume, float pitch)
    {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    private void setupAccount()
    {
        BMCPlugin plugin = getPlugin();
        Database db = plugin.getBMCDatabase();

        if (db.uuidCache.get(this.getUUID()) != null)
        {
            id = db.uuidCache.get(this.getUUID());
            number = db.numberCache.get(id);
            return;
        }

        try (Connection con = plugin.getBMCDatabase().getConnection();
             PreparedStatement prestat = con.prepareStatement("SELECT * FROM account"))
        {
            try (ResultSet rs = prestat.executeQuery())
            {
                while (rs.next())
                {
                    // UUIDが一致するものを探す
                    if (this.getUUID().toString().equals(rs.getString("uuid")))
                    {
                        // 見つかった
                        id = rs.getInt("id");
                        number = rs.getString("number");
                        db.uuidCache.put(this.getUUID(), id);
                        db.numberCache.put(id, number);
                        //PreparedStatement updatename = con.prepareStatement("UPDATE account SET name = " +
                         //       getName() + " WHERE id = " + this.getID());
                        //updatename.executeUpdate();
                        prestat.close();
                        con.close();
                        return;
                    }
                }

                PreparedStatement stmt = con.prepareStatement("INSERT INTO account VALUES (NULL, ?, ?, ?)");
                stmt.setString(1, this.getUUID().toString());
                stmt.setString(2, genAccountNumber());
                stmt.setString(3, this.getName());
                stmt.executeUpdate();
                db.uuidCache.put(this.getUUID(), id);
                db.numberCache.put(id, number);

                stmt.close();
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public int getID()
    {
        return this.id;
    }



    private String genAccountNumber()
    {
        int raw = (int) (Math.random() * 1000000);
        return String.format("%06d", raw);
    }

    public String getAccountNumber()
    {
        return this.number;
    }
}