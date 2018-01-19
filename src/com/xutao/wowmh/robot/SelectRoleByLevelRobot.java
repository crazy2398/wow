package com.xutao.wowmh.robot;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xutao.wowmh.core.ComWrapper;
import com.xutao.wowmh.core.PixelPoint;
import com.xutao.wowmh.op.FindStrOperation;

public class SelectRoleByLevelRobot extends AbstractRobot {

	private static final Logger logger = LogManager.getLogger(SelectRoleByLevelRobot.class);

	public SelectRoleByLevelRobot(ComWrapper com) {
		super(com);
	}

	/** 当前的角色最高等级 */
	private static final int FULL_LEVEL = 110;

	public List<Role> getFullLevel(Profession prof) {
		return getRoles(null, prof, null, FULL_LEVEL);
	}

	/**
	 * 根据指定条件选择一个角色 <br/>
	 * <li>下列条件至少一个不是空，如果不为空则必须满足
	 * 
	 * @param name
	 *            指定角色名(尚不支持)
	 * @param prof
	 *            指定职业
	 * @param phyle
	 *            指定种族(尚不支持)，如果不为空返回永远为空列表
	 * @param minLevel
	 *            指定最低等级
	 * @return 返回满足条件的角色
	 */
	public List<Role> getRoles(String name, Profession prof, Phyle phyle, Integer minLevel) {
		List<Role> allRoles = loadAllRoles();
		List<Role> target = new ArrayList<>(allRoles.size());

		for (Role role : allRoles) {
			if (!StringUtils.isEmpty(name) && !StringUtils.equals(name, role.getName())) {
				continue;
			}
			if (prof != null && !prof.equals(role.getProfession())) {
				continue;
			}
			if (phyle != null && !phyle.equals(role.getPhyle())) {
				continue;
			}
			if (minLevel != null && role.getLevel() < minLevel) {
				continue;
			}

			target.add(role);
		}
		return target;
	}

	// 角色栏的左上角
	private static final PixelPoint ORIGINAL = new PixelPoint(-400, 117);
	// 最多11个角色
	private static final int MAX_ROLES_LIMIT = 12;

	private static int ROLE_HEIGHT = 83;
	private static int ROLE_WIDTH = 360;

	/** 把画面上的数据装载到Java */
	protected List<Role> loadAllRoles() {

		FindStrOperation findStr = new FindStrOperation(getComWrapper(), ComWrapper.ROLE_LIST_DICT);

		List<Role> result = new ArrayList<>(MAX_ROLES_LIMIT);

		for (int i = 0; i < MAX_ROLES_LIMIT; i++) {

			Role r = loadRole(findStr, i);

			if (r == null) {
				break;
			}
			result.add(r);
		}

		return result;
	}

	/** 在相应位置扫描字符串并解析角色信息 */
	private Role loadRole(FindStrOperation findStr, int index) {
		// 360*83

		PixelPoint original = ORIGINAL.offset(0, index * ROLE_HEIGHT);

		String scaned = findStr.readStr(original, ROLE_WIDTH, ROLE_HEIGHT, "bababa-454545");
		logger.debug("在角色栏第[" + (index + 1) + "]行发现字符串[" + scaned + "]");

		Role r = getRoleFromText(scaned);

		if (r != null) {

			r.setScreenPosition(original.offset(30, 30));
		}

		return r;

	}

	/** 登入系统 */
	public boolean enterGame(Role role) {
		Point p = role == null ? null : role.getScreenPosition();

		if (null == p) {
			logger.error("该角色没有发现锚点，无法进入游戏。");
			return false;
		}

		getComWrapper().getMouseOp().leftClick(p);
		logger.info("已经选定等级[" + role.getLevel() + "]角色[" + role.getProfession() + "]");
		sleep(500);

		// if (getComWrapper().getMouseOp().doubleClick(p)) {
		if (clickPictureIfFound(getFindPicOp().findPicByIndex("按钮.bmp", 3, 4, 10))) {

			final int tryCount = 120;
			for (int i = 0; i < tryCount && !isPlaying(); i++) {
				logger.info("等待进入游戏[" + i + "/" + tryCount + "]");
				sleep(1, TimeUnit.SECONDS);
			}
		} else {
			logger.error("没有找到游戏按钮，无法进入游戏。");
		}

		return isPlaying();

	}

	/**
	 * 从形如“等级1战士”的字符串分析角色内容
	 * 
	 * @param str
	 *            等级字符串
	 * @return 分析出的角色
	 */
	public static Role getRoleFromText(String str) {

		if (StringUtils.isEmpty(str)) {
			return null;
		}

		Pattern pattern = Pattern.compile("(等级)(\\d+)(\\S+)");
		// TODO 尚不支持识别名称
		String name = "(Not Supporting)";

		int level = 0;

		Profession p = null;

		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			level = Integer.valueOf(matcher.group(2));
			p = Profession.byAnyWords(matcher.group(3));
		}

		return new Role(name, p, null, level);
	}

}
