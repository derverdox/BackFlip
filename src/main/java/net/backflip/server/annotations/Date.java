package net.backflip.server.annotations;

import net.backflip.server.enumerations.Month;

import javax.annotation.Nonnull;

public @interface Date {

    /*
    Month
     */

    @Nonnull Month month();

    /*
    Day
     */

    int day();

    /*
    Year
     */

    int year();
}
