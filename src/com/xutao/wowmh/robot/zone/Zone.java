package com.xutao.wowmh.robot.zone;

import com.xutao.wowmh.concept.zone.Landmass;
import com.xutao.wowmh.concept.zone.ZoneType;

public final class Zone implements Comparable<Zone> {

	/** 区域ID */
	private final int zoneId;
	private final String name;
	/** 允许进入的最低等级 */
	private final int admittanceLevel;
	/** 在该区域有经验的最高等级 */
	private final int beneficialLevel;
	/** 区域类型 */
	private final ZoneType zoneType;

	private final Landmass landmass;

	private Zone(int zoneId, String name, int admittanceLevel, int beneficialLevel, ZoneType zoneType,
			Landmass landmass) {
		this.zoneId = zoneId;
		this.name = name;
		this.admittanceLevel = admittanceLevel;
		this.beneficialLevel = beneficialLevel;
		this.zoneType = zoneType;
		this.landmass = landmass;
	}

	public static final class Dungeon {
		private Dungeon() {
		}

		public static Zone 乌特加德城堡 = new Zone(206, "乌特加德城堡", 68, 78, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 影牙城堡 = new Zone(209, "影牙城堡", 16, 26, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 剃刀沼泽 = new Zone(491, "剃刀沼泽", 30, 40, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 监狱 = new Zone(717, "监狱", 20, 30, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 哀嚎洞穴 = new Zone(718, "哀嚎洞穴", 15, 25, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 黑暗深渊 = new Zone(719, "黑暗深渊", 20, 30, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 诺莫瑞根 = new Zone(721, "诺莫瑞根", 24, 34, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 剃刀高地 = new Zone(722, "剃刀高地", 40, 50, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 祖尔法拉克 = new Zone(1176, "祖尔法拉克", 44, 54, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 乌特加德之巅 = new Zone(1196, "乌特加德之巅", 77, 80, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 奥达曼 = new Zone(1337, "奥达曼", 35, 45, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 阿塔哈卡神庙 = new Zone(1477, "阿塔哈卡神庙", 50, 60, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 死亡矿井 = new Zone(1581, "死亡矿井", 15, 21, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 黑石塔 = new Zone(1583, "黑石塔", 55, 65, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 黑石深渊 = new Zone(1584, "黑石深渊", 51, 61, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 祖尔格拉布 = new Zone(1977, "祖尔格拉布", 85, 87, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 斯坦索姆 = new Zone(2017, "斯坦索姆", 46, 56, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 玛拉顿 = new Zone(2100, "玛拉顿", 34, 44, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 黑色沼泽 = new Zone(2366, "黑色沼泽", 68, 75, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 旧希尔斯布莱德丘陵 = new Zone(2367, "旧希尔斯布莱德丘陵", 64, 73, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 怒焰裂谷 = new Zone(2437, "怒焰裂谷", 15, 21, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 厄运之槌 = new Zone(2557, "厄运之槌", 42, 52, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 地狱火城墙 = new Zone(3562, "地狱火城墙", 58, 67, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 鲜血熔炉 = new Zone(3713, "鲜血熔炉", 59, 68, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 破碎大厅 = new Zone(3714, "破碎大厅", 67, 75, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 蒸汽地窟 = new Zone(3715, "蒸汽地窟", 67, 75, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 幽暗沼泽 = new Zone(3716, "幽暗沼泽", 61, 70, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 奴隶围栏 = new Zone(3717, "奴隶围栏", 60, 69, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 暗影迷宫 = new Zone(3789, "暗影迷宫", 67, 75, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 奥金尼地穴 = new Zone(3790, "奥金尼地穴", 63, 72, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 塞泰克大厅 = new Zone(3791, "塞泰克大厅", 65, 73, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 法力陵墓 = new Zone(3792, "法力陵墓", 62, 71, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 祖阿曼 = new Zone(3805, "祖阿曼", 85, 87, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 生态船 = new Zone(3847, "生态船", 67, 75, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 禁魔监狱 = new Zone(3848, "禁魔监狱", 68, 75, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 能源舰 = new Zone(3849, "能源舰", 67, 75, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 净化斯坦索姆 = new Zone(4100, "净化斯坦索姆", 78, 80, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 魔导师平台 = new Zone(4131, "魔导师平台", 68, 75, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 达克萨隆要塞 = new Zone(4196, "达克萨隆要塞", 72, 80, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 魔环 = new Zone(4228, "魔环", 77, 80, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 岩石大厅 = new Zone(4264, "岩石大厅", 75, 80, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 魔枢 = new Zone(4265, "魔枢", 69, 79, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 闪电大厅 = new Zone(4272, "闪电大厅", 77, 80, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 艾卓_尼鲁布 = new Zone(4277, "艾卓-尼鲁布", 70, 80, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 紫罗兰监狱_巫妖王 = new Zone(4415, "紫罗兰监狱", 73, 80, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 古达克 = new Zone(4416, "古达克", 74, 80, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 安卡赫特_古代王国 = new Zone(4494, "安卡赫特：古代王国", 71, 80, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 冠军的试炼 = new Zone(4723, "冠军的试炼", 78, 80, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 灵魂洪炉 = new Zone(4809, "灵魂洪炉", 80, 80, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 萨隆矿坑 = new Zone(4813, "萨隆矿坑", 80, 80, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 映像大厅 = new Zone(4820, "映像大厅", 80, 80, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 黑石岩窟 = new Zone(4926, "黑石岩窟", 80, 85, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 起源大厅 = new Zone(4945, "起源大厅", 84, 85, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 格瑞姆巴托 = new Zone(4950, "格瑞姆巴托", 84, 85, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 潮汐王座 = new Zone(5004, "潮汐王座", 80, 85, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 旋云之巅 = new Zone(5035, "旋云之巅", 81, 85, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 巨石之核 = new Zone(5088, "巨石之核", 81, 85, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 托维尔失落之城 = new Zone(5396, "托维尔失落之城", 84, 85, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 永恒之井 = new Zone(5788, "永恒之井", 85, 85, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 时光之末 = new Zone(5789, "时光之末", 85, 87, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 暮光审判 = new Zone(5844, "暮光审判", 85, 85, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 影踪禅院 = new Zone(5918, "影踪禅院", 87, 90, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 青龙寺 = new Zone(5956, "青龙寺", 85, 90, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 风暴烈酒酿造厂 = new Zone(5963, "风暴烈酒酿造厂", 85, 90, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 残阳关 = new Zone(5976, "残阳关", 88, 90, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 血色大厅 = new Zone(6052, "血色大厅", 26, 36, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 通灵学院 = new Zone(6066, "通灵学院", 38, 48, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 血色修道院 = new Zone(6109, "血色修道院", 28, 38, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 魔古山宫殿 = new Zone(6182, "魔古山宫殿", 87, 90, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 围攻砮皂寺 = new Zone(6214, "围攻砮皂寺", 88, 90, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 血槌炉渣矿井 = new Zone(6874, "血槌炉渣矿井", 90, 95, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 奥金顿 = new Zone(6912, "奥金顿", 94, 99, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 影月墓地 = new Zone(6932, "影月墓地", 100, 100, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 钢铁码头 = new Zone(6951, "钢铁码头", 92, 96, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 恐轨车站 = new Zone(6984, "恐轨车站", 100, 100, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 通天峰 = new Zone(6988, "通天峰", 97, 100, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 永茂林地 = new Zone(7109, "永茂林地", 100, 100, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 黑石塔上层 = new Zone(7307, "黑石塔上层", 100, 100, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 奈萨里奥的巢穴 = new Zone(7546, "奈萨里奥的巢穴", 98, 110, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 英灵殿 = new Zone(7672, "英灵殿", 98, 110, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 黑心林地 = new Zone(7673, "黑心林地", 98, 110, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 守望者地窟 = new Zone(7787, "守望者地窟", 110, 110, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 黑鸦堡垒 = new Zone(7805, "黑鸦堡垒", 110, 110, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 纳格法尔号 = new Zone(7811, "纳格法尔号", 110, 110, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 冥口峭壁 = new Zone(7812, "冥口峭壁", 110, 110, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 魔法回廊 = new Zone(7855, "魔法回廊", 110, 110, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 紫罗兰监狱_军团 = new Zone(7996, "紫罗兰监狱", 105, 110, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 艾萨拉之眼 = new Zone(8040, "艾萨拉之眼", 98, 110, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 群星庭院 = new Zone(8079, "群星庭院", 110, 110, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 卡拉赞 = new Zone(8443, "卡拉赞", 110, 110, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 永夜大教堂 = new Zone(8527, "永夜大教堂", 110, 110, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 执政团之座 = new Zone(8910, "执政团之座", 110, 110, ZoneType.Dungeon, Landmass.EasternKingdoms);

	}

	public static final class Raid {
		private Raid() {
		}

		public static Zone 暗夜要塞 = new Zone(8025, "暗夜要塞", 110, 110, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 翡翠梦魇 = new Zone(8026, "翡翠梦魇", 110, 110, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 勇气试炼 = new Zone(8440, "勇气试炼", 110, 110, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 萨格拉斯之墓 = new Zone(8524, "萨格拉斯之墓", 110, 110, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 安托鲁斯_燃烧王座 = new Zone(8638, "安托鲁斯，燃烧王座", 110, 110, ZoneType.Dungeon,
				Landmass.EasternKingdoms);
		public static Zone 黑石铸造厂 = new Zone(6967, "黑石铸造厂", 100, 100, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 悬槌堡 = new Zone(6996, "悬槌堡", 100, 100, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 地狱火堡垒 = new Zone(7545, "地狱火堡垒", 100, 100, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 永春台 = new Zone(6067, "永春台", 90, 90, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 魔古山宝库 = new Zone(6125, "魔古山宝库", 90, 90, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 恐惧之心 = new Zone(6297, "恐惧之心", 90, 90, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 雷电王座 = new Zone(6622, "雷电王座", 90, 90, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 决战奥格瑞玛 = new Zone(6738, "决战奥格瑞玛", 90, 90, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 黑翼血环 = new Zone(5094, "黑翼血环", 85, 85, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 暮光堡垒 = new Zone(5334, "暮光堡垒", 85, 85, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 巴拉丁监狱 = new Zone(5600, "巴拉丁监狱", 85, 85, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 风神王座 = new Zone(5638, "风神王座", 85, 85, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 火焰之地 = new Zone(5723, "火焰之地", 85, 85, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 巨龙之魂 = new Zone(5892, "巨龙之魂", 85, 85, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 奥妮克希亚的巢穴 = new Zone(2159, "奥妮克希亚的巢穴", 80, 83, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 纳克萨玛斯 = new Zone(3456, "纳克萨玛斯", 80, 83, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 奥杜尔 = new Zone(4273, "奥杜尔", 80, 83, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 黑曜石圣殿 = new Zone(4493, "黑曜石圣殿", 80, 83, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 永恒之眼 = new Zone(4500, "永恒之眼", 80, 83, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 阿尔卡冯的宝库 = new Zone(4603, "阿尔卡冯的宝库", 80, 83, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 十字军的试炼 = new Zone(4722, "十字军的试炼", 80, 83, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 冰冠堡垒 = new Zone(4812, "冰冠堡垒", 80, 83, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 红玉圣殿 = new Zone(4987, "红玉圣殿", 80, 83, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 卡拉赞 = new Zone(3457, "卡拉赞", 70, 70, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 海加尔峰 = new Zone(3606, "海加尔峰", 70, 70, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 毒蛇神殿 = new Zone(3607, "毒蛇神殿", 70, 70, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 玛瑟里顿的巢穴 = new Zone(3836, "玛瑟里顿的巢穴", 70, 70, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 风暴要塞 = new Zone(3845, "风暴要塞", 70, 70, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 格鲁尔的巢穴 = new Zone(3923, "格鲁尔的巢穴", 70, 70, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 太阳之井高地 = new Zone(4075, "太阳之井高地", 70, 70, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 黑翼之巢 = new Zone(2677, "黑翼之巢", 60, 60, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 熔火之心 = new Zone(2717, "熔火之心", 60, 60, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 安其拉 = new Zone(3428, "安其拉", 60, 60, ZoneType.Dungeon, Landmass.EasternKingdoms);
		public static Zone 安其拉废墟 = new Zone(3429, "安其拉废墟", 60, 60, ZoneType.Dungeon, Landmass.EasternKingdoms);
	}

	public int getZoneId() {
		return zoneId;
	}

	public String getName() {
		return name;
	}

	public int getAdmittanceLevel() {
		return admittanceLevel;
	}

	public int getBeneficialLevel() {
		return beneficialLevel;
	}

	public ZoneType getZoneType() {
		return zoneType;
	}

	public Landmass getLandmass() {
		return landmass;
	}

	@Override
	public int compareTo(Zone arg0) {
		int x = ((Zone) arg0).zoneId;
		return Integer.valueOf(zoneId).compareTo(x);
	}

	@Override
	public String toString() {
		return name + "#" + zoneId;
	}

}
