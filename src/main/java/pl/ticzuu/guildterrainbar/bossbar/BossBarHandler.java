package pl.ticzuu.guildterrainbar.bossbar;

import net.dzikoysk.funnyguilds.event.guild.GuildEvent;
import net.dzikoysk.funnyguilds.event.guild.GuildRegionLeaveEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class BossBarHandler implements Listener {

    private final BossBarManager bossBarManager;

    public BossBarHandler(BossBarManager bossBarManager) {
        this.bossBarManager = bossBarManager;
    }

    @EventHandler
    public void handleQuit(PlayerQuitEvent event) {
        this.bossBarManager.remove(event.getPlayer());
    }

    @EventHandler
    public void handleRegionLeave(GuildRegionLeaveEvent event) {
        event.getDoer().map(user -> Bukkit.getPlayer(user.getUUID())).peek(this.bossBarManager::remove);
    }
}
