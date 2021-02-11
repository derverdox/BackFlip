package net.backflip.server.api.plugin;

import net.backflip.server.annotations.*;

import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifier;
import java.lang.annotation.*;

@Documented
@TypeQualifier
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Extension {

    /*
    The name of the extension.
     */

    @Nonnull String name();

    /*
    The version of the extension.
     */

    @Nonnull String version() default "";

    /*
    The description of the extension.
     */

    @Nonnull String description() default "";

    /*

     */

    @Nonnull Links links() default @Links;

    /*
    The authors of the extension.
     */

    @Nonnull Contributors contributors() default @Contributors;

    /*
    Some credit information
     */

    @Nonnull Links credits() default @Links;
}
