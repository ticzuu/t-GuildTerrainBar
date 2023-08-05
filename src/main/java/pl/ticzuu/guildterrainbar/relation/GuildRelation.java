package pl.ticzuu.guildterrainbar.relation;

import net.dzikoysk.funnyguilds.guild.Guild;

public enum GuildRelation {

    ENEMY, MEMBER, ALLY;

    public static GuildRelation match(Guild guild, Guild targetGuild) {
        if (guild == null || targetGuild == null) {
            return ENEMY;
        }
        if (guild.equals(targetGuild)) {
            return MEMBER;
        }
        if (guild.isAlly(targetGuild)) {
            return ALLY;
        }
        return ENEMY;
    }
}
