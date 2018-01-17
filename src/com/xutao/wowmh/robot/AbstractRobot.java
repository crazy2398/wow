package com.xutao.wowmh.robot;

import java.awt.Point;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xnx3.bean.ActiveBean;
import com.xnx3.microsoft.File;
import com.xnx3.microsoft.Sleep;
import com.xnx3.microsoft.SystemUtil;
import com.xnx3.microsoft.Tts;
import com.xnx3.microsoft.Window;
import com.xutao.wowmh.core.ComWrapper;
import com.xutao.wowmh.op.ColorOperation;
import com.xutao.wowmh.op.FindPicOperation;
import com.xutao.wowmh.op.KeyboardOperation;
import com.xutao.wowmh.op.MouseOperation;

public class AbstractRobot {
	private static final Logger logger = LogManager.getLogger(AbstractRobot.class);
	private final ComWrapper com;

	private final Sleep sleep = new Sleep(); // 延迟等待类

	public ComWrapper getComWrapper() {
		return com;
	}

	public AbstractRobot(ComWrapper com) {
		this.com = com;
	}

	public void sleep(long millis) {
		if (millis > Integer.MAX_VALUE) {
			throw new RuntimeException("Can not sleep SO LONG[" + millis + "ms]");
		}
		sleep.sleep(Long.valueOf(millis).intValue());
	}

	public void sleep(int time, TimeUnit tu) {
		sleep(tu.toMillis(time));
	}

	/**
	 * 蜂鸣器
	 * 
	 * @param len
	 *            鸣叫次数
	 * @param flag
	 *            标志位，从低位开始，当该位为1时长鸣，否则短鸣
	 */
	public void beep(final int len, final long flag) {
		final int beepLong = 600;
		final int beepShort = 200;

		for (int i = 0; i < len; i++) {
			int time = (new Double(Math.pow(2, i)).longValue() & flag) == 0x00 ? beepShort : beepLong;
			com.getSystemUtilOp().beep(1000, time);
			sleep(200, TimeUnit.MILLISECONDS);
		}
	}

	/**
	 * 蜂鸣器
	 * 
	 * 长鸣一次
	 */
	public void beep() {
		beep(1, 1);
	}

	/**
	 * 蜂鸣器
	 * 
	 * @param code
	 *            如果数组中的某一位为零时短鸣,否则长鸣
	 */
	public void beep(int... code) {
		long flag = 0;
		for (int i = 0; i < code.length; i++) {
			flag += code[i] == 0 ? 0 : new Double(Math.pow(2, i)).longValue();
		}
		beep(code.length, flag);
	}

	public void beep(final String msg, final int... code) {
		new Thread() {
			public void run() {
				beep(code);
				Tts tts = new Tts(com.getActiveXComponent());
				tts.speak(msg);
			}
		}.start();
	}

	public Window getWindowOp() {
		return com.getWindowOp();
	}

	public SystemUtil getSystemUtilOp() {
		return com.getSystemUtilOp();
	}

	public MouseOperation getMouseOp() {
		return com.getMouseOp();
	}

	public KeyboardOperation getKeyboardOp() {
		return com.getKeyboardOp();
	}

	public ColorOperation getColorOp() {
		return com.getColorOp();
	}

	public File getFileOp() {
		return com.getFileOp();
	}

	public FindPicOperation getFindPicOp() {
		return com.getFindPicOp();
	}

	public ActiveBean getActiveXComponent() {
		return com.getActiveXComponent();
	}

	/**
	 * 根据查找图片的结果，如果找到图片则点击
	 * 
	 * @param result
	 *            <li>int[0]:是否找到，没找到返回-1
	 *            <li>int[1]:找到的图像的x坐标
	 *            <li>int[2]:找到的图像的y坐标
	 * 
	 * @return 如果执行了点击动作，返回true,否则是false
	 */
	public boolean clickPictureIfFound(int[] result) {
		if (isPicFound(result) && result.length >= 3) {
			return getMouseOp().mouseClick(result[1] + 1, result[2] + 1, true);
		}
		logger.debug("没有找到图片");
		return false;
	}

	public void clickPoint(Point p, boolean isLeftClick) {
		getMouseOp().mouseClick(p, isLeftClick);
	}

	/** 在一个长方形范围内查找图片 */
	public boolean isPicFound(int[] result) {
		return result != null && result.length >= 1 && result[0] >= 0;
	}

	/** 是否位于等待选择游戏角色的界面状态 */
	public boolean isWaitingRoleSelect() {
		return isPicFound(getFindPicOp().findPicByIndex("选择服务器.bmp", 6, 12, 5));
	}

	/** 是否位于游戏中的界面状态 */
	public boolean isPlaying() {
		return isPicFound(getFindPicOp().findPicByIndex("工具栏装饰.bmp", 3, 12, 33));
	}

	/** 是否正在战斗 */
	public boolean isFighting() {
		return isPicFound(getFindPicOp().findPicByIndex("头像战斗中.bmp", 10, 8, 0));
	}
}
