package com.xutao.wowmh.concept.zone;

public enum RaidDifficulty {

	/** 10人 */
	TEN("10人", "ten"),
	/** 25人 */
	TWENTY_FIVE("25人", "twenty_five"),
	/** 40人 */
	FORTY("40人", "forty"),
	/** 弹性 */
	ELASTIC("弹性", "elastic");

	private final String name;

	private final String code;

	private RaidDifficulty(String name, String code) {
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
