package net.backflip.server.api.extension;

import javax.annotation.meta.TypeQualifier;
import java.lang.annotation.*;

@Documented
@TypeQualifier
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface Inject {
}
