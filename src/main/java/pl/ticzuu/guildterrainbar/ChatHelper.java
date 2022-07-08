package pl.ticzuu.guildterrainbar;

import org.bukkit.ChatColor;

public class ChatHelper {

    public static String colored(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}
