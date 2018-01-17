package com.xutao.wowmh.robot;

import java.awt.Point;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.xutao.wowmh.core.ComWrapper;
import com.xutao.wowmh.core.PixelPoint;

public class RouteRobotTest {
	@Test
	public void getAngleTest() {
		PixelPoint a = new PixelPoint(0, 0);
		PixelPoint b = new PixelPoint(5, 5);
		System.out.println(RouteRobot.getAngle(a, b)); 
	}
	

	@Test
	public void testMain() {
		try (ComWrapper com = new ComWrapper("H:\\eclipse-workspace\\WoW\\dmres")) {

			Assert.assertTrue(com.isCreateSuccess());

			AutoLoginRobot loginRobot = new AutoLoginRobot(com);

			Assert.assertTrue(loginRobot.startGame("43948294@qq.com", "crazy2398", "WoW3"));

			SelectRoleByLevelRobot roleRobot = new SelectRoleByLevelRobot(com);

			List<Role> allRoles = roleRobot.getRoles(null, null, null, null);

			Assert.assertTrue(allRoles != null && allRoles.size() > 0);
			
			Assert.assertTrue(roleRobot.enterGame( allRoles.get(0)));
	
			RouteRobot routeRobot = new RouteRobot(com);
			
			System.out.println( routeRobot.getCurrentOrientation());
			roleRobot.sleep(2000);
			System.out.println( routeRobot.getCurrentOrientation());
			roleRobot.sleep(2000);
			System.out.println( routeRobot.getCurrentOrientation());
			roleRobot.sleep(2000);
			System.out.println( routeRobot.getCurrentOrientation());
			
			roleRobot.sleep(2000);
			
			loginRobot.exitGame();

		}
	}
}
