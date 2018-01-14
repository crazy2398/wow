package com.xutao.wowmh.robot;

import java.awt.Point;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.xutao.wowmh.core.ComWrapper;

public class SelectRoleByLevelRobotTest {
	@Test
	public void testLogin() {
		try (ComWrapper com = new ComWrapper()) {

			Assert.assertTrue(com.isCreateSuccess());

			AutoLoginRobot loginRobot = new AutoLoginRobot(com);

			Assert.assertTrue(loginRobot.startGame("43948294@qq.com", "crazy2398", "WoW3"));

			SelectRoleByLevelRobot roleRobot = new SelectRoleByLevelRobot(com);

			List<Role> allRoles = roleRobot.getRoles(null, null, null, null);

			for (Role r : allRoles) {
				Point p = r.getScreenPosition();
				com.getMouseOp().mouseClick(p.x, p.y, true);
				roleRobot.sleep(1000);
			}

			loginRobot.exitGame();

		}
	}

	@Test
	public void getRoleFromTextTest() {
		final String[] target = {
						//
						"",
						//
						"等级1战士",
						//
						"等级12武1僧",
						//
						"等级13潜行者",
						//
						"等级14牧师",
						//
						"等级15猎人",
						//
						"等级16德鲁伊",
						//
						"等级110萨满祭司1",
						//
						"等级103法师",
						//
						"等级15术士",
						//
						"等级16圣骑士",
						//
						"等级圣骑士" };

		final Role[] expected = { 
						// 
						null,
						//
						new Role(null, Profession.Brave, null, 1),
						//
						new Role(null, Profession.Monk, null, 12),
						//
						new Role(null, Profession.Bandit, null, 13),
						//
						new Role(null, Profession.Priest, null, 14),
						//
						new Role(null, Profession.Hunter, null, 15),
						//
						new Role(null, Profession.Druid, null, 16),
						//
						new Role(null, Profession.Shaman, null, 110),
						//
						new Role(null, Profession.Mage, null, 103),
						//
						new Role(null, Profession.Warlock, null, 15),
						//
						new Role(null, Profession.Paladin, null, 16),
						//
						null };

		for (int i = 0; i < target.length; i++) {
			Role r = SelectRoleByLevelRobot.getRoleFromText(target[i]);
			if (expected[i] == null) {
				Assert.assertNull(r);
			} else {
				Assert.assertEquals(expected[i].getProfession(), r.getProfession());
				Assert.assertEquals(expected[i].getLevel(), r.getLevel());
			}
		}

	}
}
