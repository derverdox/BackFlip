package net.backflip.server.api.logger;

import javax.annotation.Nonnull;

public enum Color {
    RESET("\033[0m"),
    BLACK("\033[0;30m"),
    RED("\033[0;31m"),
    GREEN("\033[0;32m"),
    GOLD("\033[0;33m"),
    AQUA("\033[0;34m"),
    DARK_PURPLE("\033[0;35m"),
    DARK_CYAN("\033[0;36m"),
    GRAY("\033[0;90m"),
    DARK_RED("\033[0;91m"),
    LIME("\033[0;92m"),
    YELLOW("\033[0;93m"),
    BLUE("\033[0;94m"),
    PURPLE("\033[0;95m"),
    CYAN("\033[0;96m"),
    WHITE("\033[0;97m"),

    BOLD_BLACK("\033[1;30m"),
    BOLD_RED("\033[1;31m"),
    BOLD_DARK_GREEN("\033[1;32m"),
    BOLD_GOLD("\033[1;33m"),
    BOLD_AQUA("\033[1;34m"),
    BOLD_DARK_PURPLE("\033[1;35m"),
    BOLD_DARK_CYAN("\033[1;36m"),
    BOLD_GRAY("\033[1;90m"),
    BOLD_DARK_RED("\033[1;91m"),
    BOLD_LIME("\033[1;92m"),
    BOLD_YELLOW("\033[1;93m"),
    BOLD_BLUE("\033[1;94m"),
    BOLD_PURPLE("\033[1;95m"),
    BOLD_CYAN("\033[1;96m"),
    BOLD_WHITE("\033[1;97m"),

    UNDERLINED_BLACK("\033[4;30m"),
    UNDERLINED_RED("\033[4;31m"),
    UNDERLINED_DARK_GREEN("\033[4;32m"),
    UNDERLINED_GOLD("\033[4;33m"),
    UNDERLINED_AQUA("\033[4;34m"),
    UNDERLINED_DARK_PURPLE("\033[4;35m"),
    UNDERLINED_DARK_CYAN("\033[4;36m"),

    BACKGROUND_BLACK("\033[40m"),
    BACKGROUND_RED("\033[41m"),
    BACKGROUND_DARK_GREEN("\033[42m"),
    BACKGROUND_GOLD("\033[43m"),
    BACKGROUND_AQUA("\033[44m"),
    BACKGROUND_DARK_PURPLE("\033[45m"),
    BACKGROUND_DARK_CYAN("\033[46m"),
    BACKGROUND_GRAY("\033[0;100m"),
    BACKGROUND_DARK_RED("\033[0;101m"),
    BACKGROUND_LIME("\033[0;102m"),
    BACKGROUND_YELLOW("\033[0;103m"),
    BACKGROUND_BLUE("\033[0;104m"),
    BACKGROUND_PURPLE("\033[0;105m"),
    BACKGROUND_CYAN("\033[0;106m"),
    BACKGROUND_WHITE("\033[0;107m"),
    ;

    @Nonnull private final String ansi;

    Color(@Nonnull String ansi) {
        this.ansi = ansi;
    }

    @Nonnull
    public String getAnsi() {
        return ansi;
    }

    @Override
    public String toString() {
        return getAnsi();
    }
}
