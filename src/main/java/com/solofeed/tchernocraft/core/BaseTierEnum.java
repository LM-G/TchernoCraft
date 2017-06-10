package com.solofeed.tchernocraft.core;

import com.solofeed.tchernocraft.constant.IBlockType;

public enum BaseTierEnum implements IBlockType {
    BASIC(0, "basic"),
    MEDIUM(1, "medium"),
    ADVANCED(2, "advanced"),
    EXPERIMENTAL(3, "experimental");

    private final int meta;
    private final String name;

    BaseTierEnum(int meta, String name) {
        this.meta = meta;
        this.name = name;
    }

    @Override
    public int getMeta() {
        return meta;
    }

    @Override
    public String getName() {
        return name;
    }
}
