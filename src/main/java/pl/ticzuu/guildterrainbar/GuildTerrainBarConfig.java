package pl.ticzuu.guildterrainbar;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import pl.ticzuu.guildterrainbar.relation.GuildRelation;

import java.util.HashMap;
import java.util.Map;

@Header(value = {
        "######################",
        "#                    #",
        "#  t-GuildTerrainBar #",
        "#      Config        #",
        "#                    #",
        "######################",
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_UPPER_CASE)
public class GuildTerrainBarConfig extends OkaeriConfig {

    @Comment({
            "Tryb paska",
            "Dostepne tryby:",
            " - BOSS_BAR",
            " - ACTION_BAR",
    })
    public GuildTerrainBarMode mode = GuildTerrainBarMode.BOSS_BAR;
    @Comment("Permisja do dostepu do komendy od przeladowywania configu")
    public String reloadPermission = "tguildterrainbar.reload";
    @Comment("Sekcja konfiguracyjna actionbara")
    public ActionBarSection actionBarSection = new ActionBarSection();
    @Comment("Sekcja konfiguracyjna bossbara")
    public BossBarSection bossBarSection = new BossBarSection();

    @Comment({
            "Co ile ticków ma sie wykonac task od wyswietlania paska",
            "20 ticków = 1 sekunda"
    })
    public int runnableTime = 15;

    static class BossBarSection extends OkaeriConfig {

        @Comment({
                "Wiadomosci na bossbarze zalezne od relacji z gildia",
                "Dostepne relacje:",
                " - MEMBER",
                " - ENEMY",
                " - ALLY",
                "Dostepne zmienne:",
                " - {GUILD-TAG} - Tag gildi",
                " - {GUILD-NAME} - Nazwa gildi",
                " - {DISTANCE} - Dystans do srodka gildi",
                " - {PROTECTION} - Pozostaly czas ochrony gildi",
        })
        public Map<GuildRelation, BossBarWrapper> messages = new HashMap<GuildRelation, BossBarWrapper>() {{
            put(GuildRelation.MEMBER, BossBarWrapper.create("&8** &aZnajdujesz sie na terenie swojej gildi &2{GUILD-TAG} - {GUILD-NAME} &8**", BarColor.GREEN, BarStyle.SOLID));
            put(GuildRelation.ENEMY, BossBarWrapper.create("&8** &cZnajdujesz sie na terenie obcej gildi &4{GUILD-TAG} - {GUILD-NAME} &8**", BarColor.RED, BarStyle.SOLID));
            put(GuildRelation.ALLY, BossBarWrapper.create("&8** &bZnajdujesz sie na terenie sojuszniczej gildi &3{GUILD-TAG} - {GUILD-NAME} &8**", BarColor.BLUE, BarStyle.SOLID));
        }};
        @Comment("Czy progress na bossbarze ma byc zalezny od dystansu do srodka gildi")
        public boolean progressBasedOnDistance = true;
    }

    static class ActionBarSection extends OkaeriConfig {

        @Comment({
                "Wiadomosci na actionbarze zalezne od relacji z gildia",
                "Dostepne relacje:",
                " - MEMBER",
                " - ENEMY",
                " - ALLY",
                "Dostepne zmienne:",
                " - {GUILD-TAG} - Tag gildi",
                " - {GUILD-NAME} - Nazwa gildi",
                " - {DISTANCE} - Dystans do srodka gildi",
                " - {PROTECTION} - Pozostaly czas ochrony gildi",
        })
        public Map<GuildRelation, String> messages = new HashMap<GuildRelation, String>() {{
            put(GuildRelation.MEMBER, "&aZnajdujesz sie na terenie swojej gildi &2{GUILD-TAG} &8(&f{DISTANCE}m&8)");
            put(GuildRelation.ENEMY, "&cZnajdujesz sie na terenie obcej gildi &4{GUILD-TAG} &8(&f{DISTANCE}m&8)");
            put(GuildRelation.ALLY, "&bZnajdujesz sie na terenie sojuszniczej gildi &3{GUILD-TAG} &8(&f{DISTANCE}m&8)");
        }};
    }

    static class BossBarWrapper {

        public String message;
        public BarColor color;
        public BarStyle style;

        public static BossBarWrapper create(String message, BarColor color, BarStyle style) {
            BossBarWrapper wrapper = new BossBarWrapper();
            wrapper.message = message;
            wrapper.color = color;
            wrapper.style = style;
            return wrapper;
        }
    }
    
    static class BossBarWrapperSerializer implements ObjectSerializer<BossBarWrapper> {

        @Override
        public boolean supports(Class<? super BossBarWrapper> type) {
            return type == BossBarWrapper.class;
        }

        @Override
        public void serialize(BossBarWrapper object, SerializationData data, GenericsDeclaration generics) {
            data.add("message", object.message);
            data.add("color", object.color);
            data.add("style", object.style);
        }

        @Override
        public BossBarWrapper deserialize(DeserializationData data, GenericsDeclaration generics) {
            return BossBarWrapper.create(data.get("message", String.class), data.get("color", BarColor.class), data.get("style", BarStyle.class));
        }
    }
}
