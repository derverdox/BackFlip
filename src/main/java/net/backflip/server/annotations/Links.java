package net.backflip.server.annotations;

import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifier;
import java.lang.annotation.*;

@Documented
@TypeQualifier
@Retention(RetentionPolicy.SOURCE)
public @interface Links {

    /*
    GitHub URL
     */

    @Nonnull URL github() default @URL(url = "");

    /*
    GitLab URL
     */

    @Nonnull URL gitlab() default @URL(url = "");

    /*
    Sourcecode URL
     */

    @Nonnull URL source() default @URL(url = "");

    /*
    Download URL
     */

    @Nonnull URL download() default @URL(url = "");

    /*
    Website URL
     */

    @Nonnull URL website() default @URL(url = "");
}
