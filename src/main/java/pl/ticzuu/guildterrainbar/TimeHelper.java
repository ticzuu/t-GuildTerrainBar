package pl.ticzuu.guildterrainbar;

import java.util.concurrent.TimeUnit;

public class TimeHelper {

    public static String parseTime(long time) {
        if (time < 1L) {
            return "< 1s";
        }
        long months = TimeUnit.MILLISECONDS.toDays(time) / 30L;
        long days = TimeUnit.MILLISECONDS.toDays(time) % 30L;
        long hours = TimeUnit.MILLISECONDS.toHours(time) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(time));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time));

        StringBuilder stringBuilder = new StringBuilder();
        if (months > 0L) {
            stringBuilder.append(months).append("msc").append(" ");
        }
        if (days > 0L) {
            stringBuilder.append(days).append("d").append(" ");
        }
        if (hours > 0L) {
            stringBuilder.append(hours).append("h").append(" ");
        }
        if (minutes > 0L) {
            stringBuilder.append(minutes).append("m").append(" ");
        }
        if (seconds > 0L) {
            stringBuilder.append(seconds).append("s");
        }

        return stringBuilder.length() > 0 ? stringBuilder.toString().trim() : time + "ms";
    }
}
