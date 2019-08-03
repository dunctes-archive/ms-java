package me.duncte123.ms;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ms {
    public static final long SECONDS = 1000L;
    public static final long MINUTES = SECONDS * 60L;
    public static final long HOURS = MINUTES * 60L;
    public static final long DAYS = HOURS * 24L;
    public static final long WEEKS = DAYS * 7L;
    public static final long YEARS = (long) (DAYS * 365.25);

    private static final String TIME_PATTERN = "^(-?(?:\\d+)?\\.?\\d+) *(milliseconds?|msecs?|ms|seconds?|secs?|s|minutes?|mins?|m|hours?|hrs?|h|days?|d|weeks?|w|years?|yrs?|y)?$";
    private static final Pattern TIME_REGEX = Pattern.compile(TIME_PATTERN);

    // Cannot instantiate class
    private Ms() {}

    /**
     * @param time
     *         the format to parse (floating point values will be rounded down)
     *
     * @return the time converted to milliseconds
     *
     * @throws IllegalArgumentException
     *         when the input is incorrect or over 100 characters
     */
    public static long ms(@NotNull String time) {

        // We have to enforce this at runtime
        //noinspection ConstantConditions
        if (time == null) {
            throw new IllegalArgumentException("Time cannot be null");
        }

        if (time.length() > 100) {
            throw new IllegalArgumentException("String length cannot be greater than 100 characters");
        }

        final Matcher matcher = TIME_REGEX.matcher(time);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Time input does not have a valid format");
        }

        final double number = Double.parseDouble(matcher.group(1));
        final String type = matcher.group(2) == null ? "ms" : matcher.group(2);

        double result;

        switch (type) {
            case "years":
            case "year":
            case "yrs":
            case "yr":
            case "y":
                result = number * YEARS;
                break;
            case "weeks":
            case "week":
            case "w":
                result = number * WEEKS;
                break;
            case "days":
            case "day":
            case "d":
                result = number * DAYS;
                break;
            case "hours":
            case "hour":
            case "hrs":
            case "hr":
            case "h":
                result = number * HOURS;
                break;
            case "minutes":
            case "minute":
            case "mins":
            case "min":
            case "m":
                result = number * MINUTES;
                break;
            case "seconds":
            case "second":
            case "secs":
            case "sec":
            case "s":
                result = number * SECONDS;
                break;
            case "milliseconds":
            case "millisecond":
            case "msecs":
            case "msec":
            case "ms":
                result = number;
                break;
            default:
                result = -1L;
        }
        return Math.round(result);
    }

    @NotNull
    public static String ms(long time) {
        return ms(time, false);
    }

    @NotNull
    public static String ms(long time, boolean longTime) {
        if (longTime) {
            return formatLong(time);
        }

        return formatShort(time);
    }

    @NotNull
    private static String formatLong(long time) {
        final long abs = Math.abs(time);

        if (abs >= DAYS) {
            return plural(time, abs, DAYS, "day");
        }

        if (abs >= HOURS) {
            return plural(time, abs, HOURS, "hour");
        }

        if (abs >= MINUTES) {
            return plural(time, abs, MINUTES, "minute");
        }

        if (abs >= SECONDS) {
            return plural(time, abs, SECONDS, "second");
        }

        return time + " ms";
    }

    @NotNull
    private static String formatShort(long time) {
        final long abs = Math.abs(time);

        if (abs >= DAYS) {
            return Math.round(time / DAYS) + "d";
        }

        if (abs >= HOURS) {
            return Math.round(time / HOURS) + "h";
        }

        if (abs >= MINUTES) {
            return Math.round(time / MINUTES) + "m";
        }

        if (abs >= SECONDS) {
            return Math.round(time / SECONDS) + "s";
        }

        return time + "ms";
    }

    @NotNull
    private static String plural(long milis, long absoluteMilis, long unit, String name) {
        final boolean isPlural = absoluteMilis >= unit * 1.5;

        return Math.round(milis / unit) + " " + name + (isPlural ? "s" : "");
    }
}
