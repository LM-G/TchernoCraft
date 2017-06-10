package com.solofeed.tchernocraft.core;

import com.solofeed.tchernocraft.constant.IBlockType;

public enum RecipeTypeEnum implements IBlockType {
    PURIFYING(0, "purifying");

    private final int meta;
    private final String name;

    RecipeTypeEnum(int meta, String name) {
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
