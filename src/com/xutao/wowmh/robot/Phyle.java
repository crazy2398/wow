package com.xutao.wowmh.robot;

/** 各种族 */
public enum Phyle {
	/** 暗夜精灵 */
	NightElf("暗夜精灵"),
	/** 人类 */
	Human("人类"),
	/** 兽人 */
	Orc("兽人"),
	/** 牛头人 */
	Minotaur("牛头人"),
	/** 矮人 */
	Dwarf("矮人"),
	/** 亡灵 */
	Undead("亡灵"),
	/** 巨魔 */
	Trolls("巨魔"),
	/** 侏儒 */
	Dwarfism("侏儒"),
	/** 血精灵 */
	BloodElf("血精灵"),
	/** 德莱尼 */
	Delaney("德莱尼"),
	/** 狼人 */
	Werewolf("狼人"),
	/** 地精 */
	Goblin("地精"),
	/** 熊猫人 */
	Pandaren("熊猫人");

	private final String name;

	public String getName() {
		return name;
	}

	Phyle(String name) {
		this.name = name;
	}

}