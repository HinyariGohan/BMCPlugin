package xyz.hinyari.bmcplugin.utils;

import net.minecraft.server.v1_10_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_10_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBar
{

    private PacketPlayOutChat packet;

    public ActionBar(String text)
    {
        this.packet = new PacketPlayOutChat(ChatSerializer.a("{\"text\":\"" + text + "\"}"), (byte) 2);
    }

    public void sendToPlayer(Player p)
    {
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

    public void sendToAll()
    {
        for (Player p : Bukkit.getServer().getOnlinePlayers())
        {
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
            ;
        }
    }

}
