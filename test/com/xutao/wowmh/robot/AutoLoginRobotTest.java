package com.xutao.wowmh.robot;

import org.junit.Assert;
import org.junit.Test;

import com.xutao.wowmh.core.ComWrapper;

public class AutoLoginRobotTest {
	
	@Test
	public void testLogin() {
		try (ComWrapper com = new ComWrapper()) {

			Assert.assertTrue(com.isCreateSuccess()); 
									
			AutoLoginRobot robot = new AutoLoginRobot(com);
			
			Assert.assertTrue(robot.startGame("43948294@qq.com", "crazy2398", "WoW3"));
			
			robot.exitGame();
			
		}
	}
	
	@Test
	public void testLoginFail() {
		try (ComWrapper com = new ComWrapper()) {

			Assert.assertTrue(com.isCreateSuccess()); 
						
			AutoLoginRobot robot = new AutoLoginRobot(com);
			
			Assert.assertFalse(robot.startGame("43948294@qq.com", "crazy2398w", "WoW2"));

			robot.exitGame();
		}
	}
	
}
