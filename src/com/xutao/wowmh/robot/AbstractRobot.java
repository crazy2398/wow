package com.xutao.wowmh.robot;

import java.util.concurrent.TimeUnit;

import com.xnx3.bean.ActiveBean;
import com.xnx3.microsoft.Color;
import com.xnx3.microsoft.File;
import com.xnx3.microsoft.Mouse;
import com.xnx3.microsoft.Sleep;
import com.xnx3.microsoft.SystemUtil;
import com.xnx3.microsoft.Window;
import com.xutao.wowmh.core.ComWrapper;
import com.xutao.wowmh.op.KeyboardOperation;

public class AbstractRobot {
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
	
	public Window getWindowOp() {
		return com.getWindowOp();
	}

	public SystemUtil getSystemUtilOp() {
		return com.getSystemUtilOp();
	}

	public Mouse getMouseOp() {
		return com.getMouseOp();
	}

	public KeyboardOperation getKeyboardOp() {
		return com.getKeyboardOp();
	}

	public Color getColorOp() {
		return com.getColorOp();
	}

	public File getFileOp() {
		return com.getFileOp();
	}
	
	public ActiveBean getActiveXComponent() {
		return com.getActiveXComponent();
	}
}
