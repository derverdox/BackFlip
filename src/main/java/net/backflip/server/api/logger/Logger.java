package net.backflip.server.api.logger;

import net.backflip.server.api.message.Message;
import net.backflip.server.api.settings.Settings;

import javax.annotation.Nonnull;
import java.util.Calendar;

public abstract class Logger {

    public static void main(String[] args) {
        info("test");
        warn("test");
        debug("test");
        error("test");
    }

    private static void print(@Nonnull String prefix, @Nonnull Object value) {
        String string = value.toString() + "§r";
        if (((prefix.contains("<") && prefix.contains(">")) || prefix.contains("§"))
                || ((string.contains("<") && string.contains(">")) || string.contains("§"))) {
            for (Color color : Color.values()) {
                if (string.contains("<" + color.name().toLowerCase() + ">")) {
                    string = string.replace("<" + color.name().toLowerCase() + ">", color.getAnsi());
                }
                if (string.contains(color.getCode())) {
                    string = string.replace(color.getCode(), color.getAnsi());
                }
                if (prefix.contains("<" + color.name().toLowerCase() + ">")) {
                    prefix = prefix.replace("<" + color.name().toLowerCase() + ">", color.getAnsi());
                }
                if (prefix.contains(color.getCode())) {
                    prefix = prefix.replace(color.getCode(), color.getAnsi());
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
        print(Message.LOG_INFO.getText().replace("%thread%", Thread.currentThread().getName()).replace("%time%", Calendar.getInstance().getTime().getHours() + ":" + Calendar.getInstance().getTime().getMinutes() + ":" + Calendar.getInstance().getTime().getSeconds()), value);
    }

    public static void warn(@Nonnull Object value) {
        print(Message.LOG_WARN.getText().replace("%thread%", Thread.currentThread().getName()).replace("%time%", Calendar.getInstance().getTime().getHours() + ":" + Calendar.getInstance().getTime().getMinutes() + ":" + Calendar.getInstance().getTime().getSeconds()), value);
    }

    public static void debug(@Nonnull Object value) {
        if (Settings.DEBUG.getValue()) {
            print(Message.LOG_DEBUG.getText().replace("%thread%", Thread.currentThread().getName()).replace("%time%", Calendar.getInstance().getTime().getHours() + ":" + Calendar.getInstance().getTime().getMinutes() + ":" + Calendar.getInstance().getTime().getSeconds()), value);
        }
    }

    public static void error(@Nonnull Object value) {
        print(Message.LOG_ERROR.getText().replace("%thread%", Thread.currentThread().getName()).replace("%time%", Calendar.getInstance().getTime().getHours() + ":" + Calendar.getInstance().getTime().getMinutes() + ":" + Calendar.getInstance().getTime().getSeconds()), value);
    }
}
