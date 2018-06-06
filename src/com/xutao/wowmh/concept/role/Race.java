package com.xutao.wowmh.concept.role;

/** 各种族 */
public enum Race {
	/** 暗夜精灵 */
	NightElf("暗夜精灵", Camp.Alliance),
	/** 人类 */
	Human("人类", Camp.Alliance),
	/** 矮人 */
	Dwarf("矮人", Camp.Alliance),
	/** 侏儒 */
	Gnome("侏儒", Camp.Alliance),
	/** 德莱尼 */
	Draenei ("德莱尼", Camp.Alliance),
	/** 狼人 */
	Worgen("狼人", Camp.Alliance),
	/** 兽人 */
	Orc("兽人", Camp.Horde),
	/** 牛头人 */
	Tauren("牛头人", Camp.Horde),
	/** 亡灵 */
	Forsaken("亡灵", Camp.Horde),
	/** 巨魔 */
	Troll("巨魔", Camp.Horde),
	/** 血精灵 */
	BloodElf("血精灵", Camp.Horde),
	/** 地精 */
	Goblin("地精", Camp.Horde),
	/** 熊猫人 */
	Pandaren("熊猫人", null);

	private final String name;
	
	private final Camp camp; 

	public String getName() {
		return name;
	}	
	
	public Camp getCamp() {
		return camp;
	}

	Race(String name, Camp camp) {
		this.name = name;
		this.camp = camp;
	}

}