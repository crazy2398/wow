package com.xutao.wowmh.concept.role;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** 各职业 */
public enum Classes {
	/** 战士 */
	Brave("战士"),
	/** 圣骑士 */
	Paladin("圣骑士"),
	/** 猎人 */
	Hunter("猎人"),
	/** 潜行者 */
	Rogue("潜行者"),
	/** 牧师 */
	Priest("牧师"),
	/** 萨满祭司 */
	Shaman("萨满祭司"),
	/** 法师 */
	Mage("法师"),
	/** 术士 */
	Warlock("术士"),
	/** 武僧 */
	Monk("武僧"),
	/** 德鲁伊 */
	Druid("德鲁伊"),
	/** 死亡骑士 */
	DeathKnight("死亡骑士"),
	/** 恶魔猎手 */
	DemonHunter("恶魔猎手");

	private final String name;

	public String getName() {
		return name;
	}

	Classes(String name) {
		this.name = name;
	}

	/** 在职业名字中无法通过一个字来确定该职业的 */
	private static final String[] UselessProfessionKeyWords = { "师", "士", "骑", "猎"  };
	private static final Logger logger = LogManager.getLogger(Classes.class);

	public static Classes byAnyWords(String words) {

		String removePattern = "[" + StringUtils.join(UselessProfessionKeyWords, "|") + "]";

		for (Classes p : Classes.values()) {
			String name = StringUtils.removePattern(p.getName(), removePattern);

			if (StringUtils.containsAny(words, name)) {
				logger.debug("根据字符串[" + words + "]选中了[" +name +"], 返回了"+ p.getName());
				return p;
			}
		}

		return null;
	}
}