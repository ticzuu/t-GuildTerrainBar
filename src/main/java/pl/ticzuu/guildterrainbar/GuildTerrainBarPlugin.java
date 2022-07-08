package pl.ticzuu.guildterrainbar;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.serdes.SerdesRegistry;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import net.dzikoysk.funnyguilds.FunnyGuilds;
import net.dzikoysk.funnyguilds.config.MessageConfiguration;
import net.dzikoysk.funnyguilds.config.serdes.DecolorTransformer;
import net.dzikoysk.funnyguilds.config.serdes.SimpleDateFormatTransformer;
import org.bukkit.plugin.java.JavaPlugin;
import pl.ticzuu.guildterrainbar.bossbar.BossBarHandler;
import pl.ticzuu.guildterrainbar.bossbar.BossBarManager;
import pl.ticzuu.guildterrainbar.position.PlayerPositionManager;
import pl.ticzuu.guildterrainbar.position.PlayerPostionHandler;

import java.io.File;

public class GuildTerrainBarPlugin extends JavaPlugin {

    private GuildTerrainBarConfig config;
    private PlayerPositionManager playerPositionManager;
    private BossBarManager bossBarManager;

    @Override
    public void onEnable() {
        if (!FunnyGuilds.getInstance().getPluginConfiguration().regionsEnabled) {
            this.getLogger().severe("Regiony w FunnyGuilds są wyłączone, plugin zostaje wyłączony ponieważ no kurwa nie ma regionów wiec co ma ten plugin robić");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        this.config = ConfigManager.create(GuildTerrainBarConfig.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), registry -> registry.register(new GuildTerrainBarConfig.BossBarWrapperSerializer()));
            it.withBindFile(new File(this.getDataFolder(), "config.yml"));
            it.saveDefaults();
            it.load(true);
        });
        this.getCommand("tgtb").setExecutor(new GuildTerrainBarCommand(this.config));
        this.playerPositionManager = new PlayerPositionManager();
        this.bossBarManager = new BossBarManager();
        this.getServer().getScheduler().runTaskTimerAsynchronously(this, new GuildTerrainBarRunnable(this.config, this.playerPositionManager, this.bossBarManager), this.config.runnableTime, this.config.runnableTime);
        this.getServer().getPluginManager().registerEvents(new BossBarHandler(this.bossBarManager), this);
        this.getServer().getPluginManager().registerEvents(new PlayerPostionHandler(this.playerPositionManager), this);
    }
}