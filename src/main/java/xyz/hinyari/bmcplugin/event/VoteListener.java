package xyz.hinyari.bmcplugin.event;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import xyz.hinyari.bmcplugin.BMCPlayer;
import xyz.hinyari.bmcplugin.BMCPlugin;

import java.util.List;

/**
 * Created by Hinyari_Gohan on 2016/10/22.
 */
public class VoteListener implements Listener {

    private BMCPlugin plugin;

    public VoteListener(BMCPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * 投票された際のリスナーイベント
     * @param event VotifierEvent
     */
    @EventHandler
    public void onVote(VotifierEvent event) {
        Vote vote = event.getVote();
        BMCPlayer bmcPlayer = plugin.getBMCPlayer(vote.getUsername());
        bmcPlayer.barmsg("当サーバーへの投票ありがとうございます！");
        List<String> commands = plugin.config.getConfig().getStringList("vote.commands");
        if (commands == null || commands.isEmpty()) { return; }
        for (String cmd : commands) {
            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd.replace("%player", bmcPlayer.getName())); // コマンド実行
        }
    }

}
