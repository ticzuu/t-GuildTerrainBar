package pl.ticzuu.guildterrainbar.relation;

import net.dzikoysk.funnyguilds.guild.Guild;
import net.dzikoysk.funnyguilds.user.User;

public class GuildRelationHelper {

    public static GuildRelation getRelation(User user, Guild targetGuild) {
        Guild guild = user.getGuild().orNull();
        if (guild == null) {
            return GuildRelation.ENEMY;
        }
        if (guild.equals(targetGuild)) {
            return GuildRelation.MEMBER;
        }
        if (guild.isAlly(targetGuild)) {
            return GuildRelation.ALLY;
        }
        return GuildRelation.NONE;
    }
}
