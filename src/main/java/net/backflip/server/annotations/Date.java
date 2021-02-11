package net.backflip.server.annotations;

import net.backflip.server.enumerations.Month;

import javax.annotation.Nonnull;

public @interface Date {

    @Nonnull Month month();

    int day();

    int year();
}
