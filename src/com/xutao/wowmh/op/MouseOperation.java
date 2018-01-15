package com.xutao.wowmh.op;

import java.awt.Point;

import com.xnx3.microsoft.Mouse;
import com.xnx3.microsoft.Sleep;
import com.xutao.wowmh.core.ComWrapper;

public class MouseOperation extends Mouse {
	private final ComWrapper com;

	/** 两次点击之间的时间间隔（毫秒） */
	private static final int INTERVAL = 5;
	
	public MouseOperation(ComWrapper com) {
		super(com.getActiveXComponent());
		this.com = com;
	}

	public boolean mouseClick(Point point, boolean isLeft) {
		return super.mouseClick(point.x, point.y, isLeft);
	}

	public boolean doubleClick(Point point) {
		return continuousClick(point.x, point.y, 2, true);
	}

	/** 连续点击 */
	protected boolean continuousClick(Integer x, Integer y, int count, boolean isLeft) {
		boolean result = true;
		Sleep s = new Sleep();
		for (int i = 0; i < count; i++) {
			if (null == x || null == y) {
				super.mouseClick(isLeft);
			} else {
				result &= super.mouseClick(x, y, isLeft);
			}

			//s.sleep(INTERVAL);
		}

		return result;
	}

	public boolean leftClick(int count) {
		return continuousClick(null, null, count, true);
	}

	public boolean leftClick(Point point, int count) {
		return continuousClick(point.x, point.y, count, true);
	}

	public boolean rightClick(int count) {
		return continuousClick(null, null, count, false);
	}

	public boolean rightClick(Point point, int count) {
		return continuousClick(point.x, point.y, count, false);
	}
}
