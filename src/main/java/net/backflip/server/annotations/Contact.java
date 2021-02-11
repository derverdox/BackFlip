package net.backflip.server.annotations;

import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifier;
import java.lang.annotation.*;

@Documented
@TypeQualifier
@Retention(RetentionPolicy.SOURCE)
public @interface Contact {

    /*
    E-Mail Address
     */

    @Nonnull String mail() default "";

    /*
    Mobile number
     */

    @Nonnull String mobile() default "";

    /*
    Fax number
     */

    @Nonnull String fax() default "";

    /*
    Google Duo
     */

    @Nonnull String duo() default "";

    /*
    WhatsApp
     */

    @Nonnull String whatsapp() default "";

    /*
    Twitter
     */

    @Nonnull String twitter() default "";

    /*
    Discord
     */

    @Nonnull String discord() default "";

    /*
    Website
     */

    @Nonnull URL website() default @URL(url = "");

    /*
    Residence
     */

    @Nonnull Residence residence() default @Residence;
}
