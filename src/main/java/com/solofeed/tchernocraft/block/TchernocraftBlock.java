package com.solofeed.tchernocraft.block;

import com.solofeed.tchernocraft.creativetab.TchernocraftTab;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TchernocraftBlock {
    String name();
    String tab() default TchernocraftTab.NAME;
}
