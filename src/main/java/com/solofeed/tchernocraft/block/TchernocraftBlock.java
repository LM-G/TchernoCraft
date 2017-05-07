package com.solofeed.tchernocraft.block;

import com.solofeed.tchernocraft.creativetab.TchernocraftTab;

/**
 * Interface for Block Element
 */
public interface TchernocraftBlock {

	/**
	 * Retrun the name of the Block
	 * @return
	 */
	String getName();
	
	/**
	 * Retrun the tab of the block
	 * @return
	 */
	default String getTab(){
		return TchernocraftTab.NAME;
	}
	
}
