package com.xutao.wowmh.op;

import java.awt.Point;
import java.awt.Rectangle;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jacob.com.Variant;
import com.xutao.wowmh.core.ComWrapper;

public class MouseOperation {
	private final ComWrapper com;
	private static final Logger logger = LogManager.getLogger(MouseOperation.class);

	public MouseOperation(ComWrapper com) {
		this.com = com;
	}

	/** 调用DM插件的鼠标方法，该方法返回值必须是整形(0:失败,1:成功) */
	protected boolean invokeMouseAction(String method, Point point, Object... params) {
		Variant result = null;
		try {
			if (point != null) {
				mouseMoveTo(point);
			}

			if (params != null && params.length > 0) {
				Variant[] var = new Variant[params.length];
				for (int i = 0; i < params.length; i++) {
					var[i] = new Variant(params[i]);
				}
				result = com.getActiveXComponent().getDm().invoke(method, var);
			} else {
				result = com.getActiveXComponent().getDm().invoke(method);
			}
			//logger.debug("调用[" + method + "]的结果是:" + String.valueOf(result));

		} catch (Exception e) {
			logger.error("调用[" + method + "]失败", e);
			throw new RuntimeException(e);
		}
		return result != null && result.getInt() > 0;
	}

	/**
	 * 鼠标相对于上次的位置移动
	 * 
	 * @param xOffset
	 *            相对于上次的X偏移
	 * @param yOffset
	 *            相对于上次的Y偏移
	 * @return
	 */
	public boolean mouseMoveRelative(int xOffset, int yOffset) {
		return invokeMouseAction("MoveR", null, xOffset, yOffset);
	}

	/**
	 * 把鼠标移动到目的范围内的任意一点
	 * 
	 * @param x
	 *            X坐标
	 * @param y
	 *            Y坐标
	 * @param w
	 *            宽度(从x计算起)
	 * @param h
	 *            高度(从y计算起)
	 * @return
	 */
	public Point mouseMoveToArea(int x, int y, int w, int h) {

		try {
			Variant vx = new Variant(x);
			Variant vy = new Variant(y);
			Variant vw = new Variant(w);
			Variant vh = new Variant(h);
			String result = com.getActiveXComponent().getDm().invoke("MoveToEx", vx, vy, vw, vh).getString();

			String[] coord = StringUtils.split(result, ",");

			return new Point(Integer.valueOf(coord[0]), Integer.valueOf(coord[1]));
		} catch (Exception e) {
			logger.error("调用[MoveToEx]失败", e);
			throw new RuntimeException(e);
		}
	}

	/** 把鼠标移动到目的点(x,y) */
	public Point mouseMoveToArea(Point point, int w, int h) {
		return mouseMoveToArea(point.x, point.y, w, h);
	}

	/** 把鼠标移动到目的点(x,y) */
	public Point mouseMoveToArea(Rectangle r) {
		return mouseMoveToArea(r.x, r.y, r.width, r.height);
	}

	/** 把鼠标移动到目的点(x,y) */
	public boolean mouseMoveTo(int x, int y) {
		return invokeMouseAction("MoveTo", null, x, y);
	}

	/** 把鼠标移动到目的点(x,y) */
	public boolean mouseMoveTo(Point point) {
		return mouseMoveTo(point.x, point.y);
	}

	/** 按下鼠标左键 */
	public boolean leftClick(Point point) {
		return invokeMouseAction("LeftClick", point);
	}

	/** 按下鼠标左键 */
	public boolean leftClick() {
		return leftClick(null);
	}

	/** 按下鼠标右键 */
	public boolean rightClick(Point point) {
		return invokeMouseAction("RightClick", point);
	}

	/** 按下鼠标右键 */
	public boolean rightClick() {
		return rightClick(null);
	}

	/** 双击鼠标左键 */
	public boolean leftDoubleClick(Point point) {
		return invokeMouseAction("LeftDoubleClick", point);
	}

	/** 双击鼠标左键 */
	public boolean leftDoubleClick() {
		return leftDoubleClick(null);
	}

	/** 按住鼠标左键 */
	public boolean leftDown(Point point) {
		return invokeMouseAction("LeftDown", point);
	}

	/** 按住鼠标左键 */
	public boolean leftDown() {
		return leftDown(null);
	}

	/** 按住鼠标左键 */
	public boolean leftUp(Point point) {
		return invokeMouseAction("LeftUp", point);
	}

	/** 弹起鼠标左键 */
	public boolean leftUp() {
		return leftUp(null);
	}

	/** 按住鼠标右键 */
	public boolean rightDown(Point point) {
		return invokeMouseAction("RightDown", point);
	}

	/** 按住鼠标右键 */
	public boolean rightDown() {
		return rightDown(null);
	}

	/** 双击鼠标左键 */
	public boolean rightUp(Point point) {
		return invokeMouseAction("RightUp", point);
	}

	/** 弹起鼠标左键 */
	public boolean rightUp() {
		return rightUp(null);
	}

}
