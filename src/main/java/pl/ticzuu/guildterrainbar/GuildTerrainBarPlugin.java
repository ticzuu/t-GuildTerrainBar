package pl.ticzuu.guildterrainbar;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.serdes.SerdesRegistry;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import net.dzikoysk.funnyguilds.FunnyGuilds;
import org.bukkit.plugin.java.JavaPlugin;
import pl.ticzuu.guildterrainbar.bossbar.BossBarHandler;
import pl.ticzuu.guildterrainbar.bossbar.BossBarManager;
import pl.ticzuu.guildterrainbar.position.PlayerPositionManager;
import pl.ticzuu.guildterrainbar.position.PlayerPostionHandler;

import java.io.File;
import java.util.Objects;

public class GuildTerrainBarPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        if (!FunnyGuilds.getInstance().getPluginConfiguration().regionsEnabled) {
            this.getLogger().severe("Regiony w FunnyGuilds są wyłączone, plugin zostaje wyłączony ponieważ nie ma co robic plugin");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        GuildTerrainBarConfig config = ConfigManager.create(GuildTerrainBarConfig.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), registry -> registry.register(new GuildTerrainBarConfig.BossBarWrapperSerializer()));
            it.withBindFile(new File(this.getDataFolder(), "config.yml"));
            it.saveDefaults();
            it.load(true);
        });
        Objects.requireNonNull(this.getCommand("tgtb")).setExecutor(new GuildTerrainBarCommand(config));
        PlayerPositionManager playerPositionManager = new PlayerPositionManager();
        BossBarManager bossBarManager = new BossBarManager();
        this.getServer().getScheduler().runTaskTimerAsynchronously(this, new GuildTerrainBarRunnable(config, playerPositionManager, bossBarManager), config.runnableTime, config.runnableTime);
        this.getServer().getPluginManager().registerEvents(new BossBarHandler(bossBarManager), this);
        this.getServer().getPluginManager().registerEvents(new PlayerPostionHandler(playerPositionManager), this);


    }
}