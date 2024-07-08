package com.dwarfeng.familyhelper.webapi.node.webmvc;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Base64 参数。
 *
 * @author DwArFeng
 * @since 1.3.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Base64RequestParam {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    boolean required() default true;
}
