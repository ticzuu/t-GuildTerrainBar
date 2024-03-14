package pl.ticzuu.guildterrainbar;

import net.dzikoysk.funnyguilds.FunnyGuilds;
import net.dzikoysk.funnyguilds.guild.Guild;
import net.dzikoysk.funnyguilds.shared.TimeUtils;
import net.dzikoysk.funnyguilds.shared.bukkit.ChatUtils;
import net.dzikoysk.funnyguilds.shared.bukkit.LocationUtils;
import net.dzikoysk.funnyguilds.user.User;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import pl.ticzuu.guildterrainbar.bossbar.BossBarManager;
import pl.ticzuu.guildterrainbar.position.PlayerPositionManager;
import pl.ticzuu.guildterrainbar.relation.GuildRelation;

public class GuildTerrainBarRunnable implements Runnable {

    private final GuildTerrainBarConfig config;
    private final FunnyGuilds funnyGuilds;
    private final PlayerPositionManager playerPositionManager;
    private final BossBarManager bossBarManager;

    public GuildTerrainBarRunnable(GuildTerrainBarConfig config, PlayerPositionManager playerPositionManager, BossBarManager bossBarManager) {
        this.config = config;
        this.playerPositionManager = playerPositionManager;
        this.bossBarManager = bossBarManager;
        this.funnyGuilds = FunnyGuilds.getInstance();
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {

            Guild guild = this.playerPositionManager.find(player.getUniqueId());
            if (guild == null) {
                return;
            }

            User user = this.funnyGuilds.getUserManager().findByPlayer(player).orNull();
            if (user == null) {
                return;
            }

            GuildRelation relation = GuildRelation.match(user.getGuild().orNull(), guild);
            double distance = LocationUtils.flatDistance(player.getLocation(), guild.getCenter().get());
            switch (config.mode) {
                case BOSS_BAR: {
                    BossBar bossBar = this.bossBarManager.find(player);
                    GuildTerrainBarConfig.BossBarWrapper bossBarWrapper = this.config.bossBarSection.messages.get(relation);

                    String message = bossBarWrapper.message;
                    message = message.replace("{GUILD-TAG}", guild.getTag());
                    message = message.replace("{GUILD-NAME}", guild.getName());
                    message = message.replace("{DISTANCE}", String.format("%.2f", distance));
                    message = message.replace("{PROTECTION}", TimeHelper.parseTime(guild.getProtection().toEpochMilli()));

                    bossBar.setTitle(ChatUtils.colored(message));
                    bossBar.setColor(bossBarWrapper.color);
                    bossBar.setStyle(bossBarWrapper.style);
                    if (this.config.bossBarSection.progressBasedOnDistance) {
                        bossBar.setProgress(Math.max(1 - (distance / guild.getRegion().get().getSize()), 0));
                    }
                    break;
                }
                case ACTION_BAR: {

                    String message = config.actionBarSection.messages.get(relation);
                    message = message.replace("{GUILD-TAG}", guild.getTag());
                    message = message.replace("{GUILD-NAME}", guild.getName());
                    message = message.replace("{DISTANCE}", String.format("%.2f", distance));
                    message = message.replace("{PROTECTION}", TimeHelper.parseTime(guild.getProtection().toEpochMilli()));

                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatUtils.colored(message)));
                    break;
                }
            }
        });
    }
}
