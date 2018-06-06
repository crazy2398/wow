package com.xutao.wowmh.concept.zone;

public enum DungeonDifficulty {

	/** 英雄 */
	YX("英雄", "hell"),
	/** 普通 */
	PT("英雄", "normal"),
	/** 普通 */
	M("史诗", "mythic");

	private final String name;

	private final String code;

	private DungeonDifficulty(String name, String code) {
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
