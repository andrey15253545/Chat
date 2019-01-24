package server.annotation;

import server.user.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * key for dynamic class loading, and adding to factory
 *    that doesn't work :\
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Key {
    String url();
    Role[] role();
}
