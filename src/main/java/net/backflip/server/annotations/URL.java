package net.backflip.server.annotations;

import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifier;
import java.lang.annotation.*;

@Documented
@TypeQualifier
@Retention(RetentionPolicy.SOURCE)
public @interface URL {

    /*
    The URL
     */

    @Nonnull String url();

}
