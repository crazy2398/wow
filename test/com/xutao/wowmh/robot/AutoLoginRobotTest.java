package com.xutao.wowmh.robot;

import org.junit.Assert;
import org.junit.Test;

import com.xutao.wowmh.core.ComWrapper;

public class AutoLoginRobotTest {
	
	@Test
	public void testLogin() {
		try (ComWrapper com = new ComWrapper()) {

			Assert.assertTrue(com.isCreateSuccess()); 
			
			com.setResourcePath("H:\\eclipse-workspace\\WoWMaphack\\dmres");
						
			AutoLoginRobot robot = new AutoLoginRobot(com);
			
			Assert.assertTrue(robot.login("43948294@qq.com", "2319668love", "WoW3"));
			
		}
	}
	
	@Test
	public void testLoginFail() {
		try (ComWrapper com = new ComWrapper()) {

			Assert.assertTrue(com.isCreateSuccess()); 
			
			com.setResourcePath("H:\\eclipse-workspace\\WoWMaphack\\dmres");
						
			AutoLoginRobot robot = new AutoLoginRobot(com);
			
			Assert.assertFalse(robot.login("43948294@qq.com", "2319668lovw", "WoW2"));
			
		}
	}
	
}
