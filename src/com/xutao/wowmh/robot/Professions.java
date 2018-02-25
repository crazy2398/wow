package com.xutao.wowmh.robot;

public enum Professions {
	/** 锻造 */
	BLACKSMITHING(false),
	/** 炼金 */
	ALCHEMY(false),
	/** 铭文 */
	INSCRIPTION(false),
	/** 附魔 */
	ENCHANTING(false),
	/** 珠宝 */
	JEWELCRAFTING(false),
	/** 制皮 */
	LEATHERWORKING(false),
	/** 裁缝 */
	TAILORING(false),
	/** 工程 */
	ENGINEERING(false),
	/** 烹饪 */
	COOKING(false),
	/** 钓鱼 */
	FISHING(true),
	/** 急救 */
	FIRST_AID(false),
	/** 剥皮 */
	SKINNING(true),
	/** 挖矿 */
	MINING(true),
	/** 采药 */
	HERBALISM(true);
	/** 是否为采集 */
	private final boolean collecting;

	private Professions(boolean collecting) {
		this.collecting = collecting;
	}

	/** 是否为采集 */
	public boolean isCollecting() {
		return collecting;
	}
}
