package com.xutao.wowmh.concept;

public enum GameVersion {

	/** 经典旧世 */
	CLASSIC("经典旧世", 0),

	/** 燃烧的远征 */
	THE_BURNING_CRUSADE("燃烧的远征", 1),

	/** 巫妖王之怒 */
	WATH_OF_THE_LICH_KING("巫妖王之怒", 2),

	/** 大地的裂变 */
	CATACLYSM("大地的裂变", 3),

	/** 熊猫人之谜 */
	MISTS_OF_PANDARIA("熊猫人之谜", 4),

	/** 德拉诺之王 */
	WARLORDS_OF_DRAENOR("德拉诺之王", 5),

	/** 军团再临 */
	LEGION("军团再临", 6);

	private final String name;

	private final int code;

	private GameVersion(String name, int code) {
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
