package pl.ticzuu.guildterrainbar.position;

import net.dzikoysk.funnyguilds.event.guild.GuildDeleteEvent;
import net.dzikoysk.funnyguilds.event.guild.GuildRegionEnterEvent;
import net.dzikoysk.funnyguilds.event.guild.GuildRegionLeaveEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerPostionHandler implements Listener {

    private final PlayerPositionManager playerPositionManager;

    public PlayerPostionHandler(PlayerPositionManager playerPositionManager) {
        this.playerPositionManager = playerPositionManager;
    }

    @EventHandler
    public void handleRegionEnter(GuildRegionEnterEvent event) {
        event.getDoer().peek(user -> {
           this.playerPositionManager.add(user.getUUID(), event.getGuild());
        });
    }

    @EventHandler
    public void handleRegionLeave(GuildRegionLeaveEvent event) {
        event.getDoer().peek(user -> {
            this.playerPositionManager.remove(user.getUUID());
        });
    }

    @EventHandler
    public void handleQuit(PlayerQuitEvent event) {
        this.playerPositionManager.remove(event.getPlayer().getUniqueId());
    }
}
