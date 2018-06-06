package com.xutao.wowmh.concept.zone;

public enum ZoneType {

	/** 战场 */
	BattleGround("战场", "Battleground"),

	/** 地下城 */
	Dungeon("地下城", "Dungeon"),

	/** 团队本 */
	Raid("团队本", "Raid"),

	/** 场景战役 */
	Scenario("场景战役", "Scenario"),

	/** 普通区域 */
	Normal("普通区域", ""),;

	private final String name;

	private final String code;

	private ZoneType(String name, String code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}
}
