package com.solofeed.tchernocraft.tileentity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TchernocraftTileEntity {
    String id();
}
