package pl.ticzuu.guildterrainbar;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildTerrainBarCommand implements CommandExecutor {

    private final GuildTerrainBarConfig config;

    public GuildTerrainBarCommand(GuildTerrainBarConfig config) {
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        if (!player.hasPermission(config.reloadPermission)) {
            return false;
        }
        config.load();
        player.sendMessage(ChatHelper.colored("&7Przeładowano config &et-GuildTerrainBar"));
        return true;
    }
}
