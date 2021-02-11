package net.backflip.server.api.logger;

import javax.annotation.Nonnull;

public abstract class Logger {

    public static void main(String[] args) {
        info("test");
        warn("test");
        error("test");
    }

    private static void print(@Nonnull String prefix, @Nonnull Object value) {
        String string = value.toString();
        if ((prefix.contains("<") && prefix.contains(">")) || (string.contains("<") && string.contains(">"))) {
            for (Color color : Color.values()) {
                if (string.contains("<" + color.name().toLowerCase() + ">")) {
                    string = string.replace("<" + color.name().toLowerCase() + ">", color.getAnsi());
                }
                if (prefix.contains("<" + color.name().toLowerCase() + ">")) {
                    prefix = prefix.replace("<" + color.name().toLowerCase() + ">", color.getAnsi());
                }
            }
        }
        if (prefix.isEmpty()) {
            System.out.println(string);
        } else {
            System.out.println(prefix + " " + string);
        }
    }

    public static void info(@Nonnull Object value) {
        print("<gray>[<blue>Info<gray>]<reset>", value);
    }

    public static void warn(@Nonnull Object value) {
        print("<gray>[<yellow>Warning<gray>]<reset>", value);
    }

    public static void error(@Nonnull Object value) {
        print("<gray>[<dark_red>Error<gray>]<reset>", value);
    }
}
