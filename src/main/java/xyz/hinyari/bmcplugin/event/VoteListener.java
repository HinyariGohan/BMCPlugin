package xyz.hinyari.bmcplugin.event;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
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
        if (bmcPlayer == null) { plugin.getLogger().severe("投票プレイヤー読み込み不可能"); return; }
        bmcPlayer.barmsg("投票ありがとうございます！");
        plugin.broadcast("&6&l" + bmcPlayer.getName() + " &rさんが当サーバーに&e投票&rしました！ありがとうございます！");
        plugin.broadcast(plugin.config.getVoteURL() + " にて投票可能です。&n（登録が必要です）");
        List<String> commands = plugin.config.getVoteCommands();
        if (commands == null || commands.isEmpty()) { return; }
        for (String cmd : commands) {
            Bukkit.dispatchCommand(bmcPlayer.getPlayer(), cmd.replace("%player", bmcPlayer.getName()) + " " + plugin.config.getAdmin_pass()); // コマンド実行
        }
        for (BMCPlayer p : plugin.getBMCPlayers()) {
            p.playSound(Sound.ENTITY_PLAYER_LEVELUP, 0.5F, 0.6F);
        }
    }

}
