package com.solofeed.tchernocraft.item;

import com.solofeed.tchernocraft.creativetab.TchernocraftTab;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TchernocraftItem {
    String value() default StringUtils.EMPTY;
    String name();
    String tab() default TchernocraftTab.NAME;
}
