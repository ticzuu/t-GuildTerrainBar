package pl.ticzuu.guildterrainbar.position;

import net.dzikoysk.funnyguilds.guild.Guild;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PlayerPositionManager {

    private final Map<UUID, Guild> positions;

    public PlayerPositionManager() {
        this.positions = new HashMap<>();
    }

    public Guild find(UUID uniqueId) {
        return this.positions.get(uniqueId);
    }

    public void add(UUID uniqueId, Guild guild) {
        this.positions.put(uniqueId, guild);
    }

    public void remove(UUID uniqueId) {
        this.positions.remove(uniqueId);
    }
}
