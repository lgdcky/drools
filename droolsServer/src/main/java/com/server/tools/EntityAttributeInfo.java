package com.server.tools;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/9/18
 * Time: 8:41 PM
 */
public class EntityAttributeInfo {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface ClassAnnotation {
        String name() default "";
        String desc() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface FieldAnnotation {
        String name() default "";
        String type() default "";
        String desc() default "";
    }


}

