package com.solofeed.tchernocraft.block;

import com.google.auto.service.AutoService;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Solofeed on 07/05/2017.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModBlock {
    AutoService autoService() default @AutoService(ITchernocraftBlock.class);
}
