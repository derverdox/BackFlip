package net.backflip.server.annotations;

import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifier;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@TypeQualifier
@Retention(RetentionPolicy.SOURCE)
public @interface Contributor {

    /*
    Firstname
     */

    @Nonnull String firstname() default "";

    /*
    Lastname
     */

    @Nonnull String lastname() default "";

    /*
    Pseudonym
     */

    @Nonnull String pseudonym() default "";

    /*
    Birthday date
     */

    @Nonnull Date birthday() default @Date(day = 0, month = 0, year = 0);

    /*
    Contact
     */

    @Nonnull Contact contact() default @Contact;

    /*
    Age
     */

    int age() default 0;
}
