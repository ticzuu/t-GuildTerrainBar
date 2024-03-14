package pl.ticzuu.guildterrainbar.bossbar;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import pl.ticzuu.guildterrainbar.GuildTerrainBarConfig;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BossBarManager {

    private final Map<UUID, BossBar> bossBars;

    public BossBarManager() {
        this.bossBars = new HashMap<>();
    }

    public void remove(Player player) {
        if (player == null) {
            return;
        }
        BossBar bossBar = this.bossBars.remove(player.getUniqueId());
        if (bossBar == null) {
            return;
        }
        bossBar.removeAll();
    }

    public BossBar find(Player player) {
        return bossBars.computeIfAbsent(player.getUniqueId(), (ignored) -> {
            BossBar bossBar = Bukkit.createBossBar("", BarColor.BLUE, BarStyle.SOLID);
            bossBar.addPlayer(player);
            return bossBar;
        });
    }
}
