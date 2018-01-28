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
		Object[][] target = {
						//
						{ new PixelPoint(100, 100), new PixelPoint(105, 100), 0.0d },
						//
						{ new PixelPoint(100, 100), new PixelPoint(105, 105), 45.0d },
						//
						{ new PixelPoint(100, 100), new PixelPoint(100, 105), 90.0d },
						//
						{ new PixelPoint(100, 100), new PixelPoint(95, 105), 135.0d },
						//
						{ new PixelPoint(100, 100), new PixelPoint(95, 100), 180.0d },
						//
						{ new PixelPoint(100, 100), new PixelPoint(95, 95), 225.0d },
						//
						{ new PixelPoint(100, 100), new PixelPoint(100, 95), 270.0d },
						//
						{ new PixelPoint(100, 100), new PixelPoint(105, 95), 315.0d }

		};

		int i = 0;

		for (Object[] data : target) {
			double degree = RouteRobot.getAngle((PixelPoint) data[0], (PixelPoint) data[1]);
			System.out.println("degee[" + (i++) + "]=" + degree);

			Assert.assertEquals((double) data[2], degree, 0.0001);
		}

	}

	@Test
	public void testMain() {
		try (ComWrapper com = new ComWrapper("H:\\eclipse-workspace\\WoW\\dmres")) {

			Assert.assertTrue(com.isCreateSuccess());

			AutoLoginRobot loginRobot = new AutoLoginRobot(com);

			Assert.assertTrue(loginRobot.startGame("43948294@qq.com", "crazy2398", "WoW1"));

			SelectRoleByLevelRobot roleRobot = new SelectRoleByLevelRobot(com);

			List<Role> allRoles = roleRobot.getRoles(null, null, null, null);

			Assert.assertTrue(allRoles != null && allRoles.size() > 0);

			Assert.assertTrue(roleRobot.enterGame(allRoles.get(0)));

			RouteRobot routeRobot = new RouteRobot(com);

			double degree = routeRobot.getCurrentOrientation();
			// 重置鼠标位置
			routeRobot.getMouseOp().mouseMoveToArea(200, 200, 100, 100);

			for (int i = 0; i < 20; i++) {
				routeRobot.getMouseOp().rightDown();
				routeRobot.sleep(50);
				routeRobot.getMouseOp().mouseMoveRelative(i * 10, 0);
				routeRobot.sleep(50);
				routeRobot.getMouseOp().rightUp();
				routeRobot.sleep(400);
				double newDegree = routeRobot.getCurrentOrientation();

				System.err.println("横移" + (i * 10) + "像素时角度差为:" + newDegree + "-" + degree + "=" + (newDegree - degree));
				degree = newDegree;
			}
			loginRobot.exitGame();

		}
	}

	@Test
	/** 尝试计算平移鼠标多少像素可以使角色旋转一周 */
	public void testToCompute360() {
		try (ComWrapper com = new ComWrapper("H:\\eclipse-workspace\\WoW\\dmres")) {

			Assert.assertTrue(com.isCreateSuccess());

			AutoLoginRobot loginRobot = new AutoLoginRobot(com);

			Assert.assertTrue(loginRobot.startGame("43948294@qq.com", "crazy2398", "WoW1"));

			SelectRoleByLevelRobot roleRobot = new SelectRoleByLevelRobot(com);

			List<Role> allRoles = roleRobot.getRoles(null, null, null, null);

			Assert.assertTrue(allRoles != null && allRoles.size() > 0);

			Assert.assertTrue(roleRobot.enterGame(allRoles.get(0)));

			RouteRobot routeRobot = new RouteRobot(com);

			// 重置鼠标位置
			routeRobot.getMouseOp().mouseMoveToArea(200, 200, 100, 100);

			for (int i = 0; i < 	
							10; i++) {
				testAction(com, routeRobot);
			}

			loginRobot.exitGame();

		}
	}

	public void testAction(ComWrapper com, RouteRobot routeRobot) {
		int width = com.getSystemUtilOp().getScreenWidth();

		double base = routeRobot.getCurrentOrientation();
		for (int i = 200; i < width; i++) {
			routeRobot.getMouseOp().rightDown();
			routeRobot.sleep(50);
			routeRobot.getMouseOp().mouseMoveRelative(i, 0);
			routeRobot.sleep(50);
			routeRobot.getMouseOp().rightUp();
			routeRobot.sleep(400);
			double end = routeRobot.getCurrentOrientation();

			if (Math.abs(end - base) < 0.1) {
				System.err.println("在i=" + i + "时，发现了角度一致");
			}
			base = end;
		}
	}
}
