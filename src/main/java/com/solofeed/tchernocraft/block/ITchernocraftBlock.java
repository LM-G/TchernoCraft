package com.solofeed.tchernocraft.block;

import com.solofeed.tchernocraft.creativetab.TchernocraftTab;

/**
 * Interface for ModBlock Element
 */
public interface ITchernocraftBlock {

	/**
	 * Retrun the name of the ModBlock
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
