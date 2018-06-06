package com.xutao.wowmh.concept.zone;

public enum Landmass {

	/** 东部王国 */
	EasternKingdoms("东部王国", 0),

	/** 卡利姆多 */
	Kalimdor("卡利姆多", 1),

	/** 地下城 */
	Dungeon("地下城", 2),

	/** 团队副本 */
	Raid("地下城", 3),

	/** 战场 */
	BattleGround("战场", 6),

	/** 外域 */
	Outland("外域", 8),

	/** 特殊镜像位面 */
	SpecialImagePlanes("特殊镜像位面", 9),

	/** 诺森德 */
	Northrend("诺森德", 10),

	/** 大漩涡 */
	TheMaelstrom("大漩涡", 11),

	/** 潘达利亚 */
	Pandaria("潘达利亚", 12),

	/** 场景战役 */
	Scenario("场景战役", 13),

	/** 德拉诺 */
	Draenor("德拉诺", 14),

	/** 破碎群岛 */
	BrokenIsles("破碎群岛", 15),

	/** 时光漫游地下城 */
	TimeOdyssey("时光漫游地下城", 24);

	private final String name;

	private final int code;

	private Landmass(String name, int code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public int getCode() {
		return code;
	}
}
