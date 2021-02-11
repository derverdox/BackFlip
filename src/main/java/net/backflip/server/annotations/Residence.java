package net.backflip.server.annotations;

import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifier;
import java.lang.annotation.*;

@Documented
@TypeQualifier
@Retention(RetentionPolicy.SOURCE)
public @interface Residence {

    /*
    State
     */

    @Nonnull String state() default "";

    /*
    Country
     */

    @Nonnull String country() default "";

    /*
    City
     */

    @Nonnull String city() default "";

    /*
    Street
     */

    @Nonnull String street() default "";

    /*
    House Number
     */

    @Nonnull String houseNumber() default "";

    /*
    Postal Code
     */

    int postalCode() default 0;
}
