package com.xutao.wowmh.robot;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xutao.wowmh.core.ComWrapper;
import com.xutao.wowmh.core.PixelPoint;

public class RouteRobot extends AbstractRobot {
	private static final Logger logger = LogManager.getLogger(RouteRobot.class);

	public RouteRobot(ComWrapper com) {
		super(com);
	}

	private PixelPoint original = null;

	private synchronized PixelPoint getOriginal() {
		PixelPoint expected = new PixelPoint(-97, 77);
		// return getColorOp().findColor(expected, 20, 20, "272842-0f0f0d");

		Collection<PixelPoint> allpoints = getColorOp().findAllPoints(expected, 20, 20, "272842-0f0f0d");
		// Collection<PixelPoint> allpoints = getColorOp().findAllPoints(expected, 20,
		// 20, "1E1E2D-19161A");
		// Collection<PixelPoint> allpoints = getColorOp().findAllPoints(expected, 20,
		// 20, "303751-2E302E");

		// Collection<PixelPoint> allpoints = getColorOp().findAllPoints(expected, 20,
		// 20, "22253F-1F1E1C");
		// Collection<PixelPoint> allpoints = getColorOp().findAllPoints(expected, 20,
		// 20, "191B33-18181A");
		// Collection<PixelPoint> allpoints = getColorOp().findAllPoints(expected, 20,
		// 20, "1E1F31-151317");

		// 去找三乘三方块
		PixelPoint leftTop = getColorOp().findSquareColor(allpoints, 3);
		if (leftTop == null) {
			// 没有三乘三就找二乘二
			return getColorOp().findSquareColor(allpoints, 2);
		}
		// 三乘三方块 取中心点
		return leftTop.offset(1, 1);
	}

	private PixelPoint getOutermostPoint(PixelPoint center, String color) {

		Collection<PixelPoint> allpoints = getColorOp().findAllPoints(center.offset(-12, -12), 24, 24, color);

		if (allpoints.isEmpty()) {
			return null;
		}

		double maxDistance = -1d;
		PixelPoint outermost = null;
		for (PixelPoint p : allpoints) {
			double distance = center.distance(p);
			if (distance > maxDistance) {
				maxDistance = distance;
				outermost = p;
			}
		}

		// logger.debug("最远的点是：" + outermost.toString());

		return outermost;

	}

	/** 计算朝向 */
	public double getCurrentOrientation() {

		if (original == null) {
			original = getOriginal();
			if (original == null) {
				throw new RuntimeException("没有找到小地图原点");
			}
			logger.debug(original);
		}

		PixelPoint outermost = getOutermostPoint(original, "CECBCD-2C2C2E");
		// PixelPoint outermost = getOutermostPoint(original, "C9C7CA-2e3035");
		// PixelPoint outermost = getOutermostPoint(original, "272444-090805");
		return getAngle(original, outermost);
	}

	static double getAngle(PixelPoint a, PixelPoint b) {
		logger.debug("a(" + a.x + "," + a.y + "),b(" + b.x + "," + b.y + ")");

		// double brng = Math.toDegrees(Math.asin((double) (b.y - a.y) /
		// a.distance(b)));

		double brng = Math.toDegrees(Math.atan2((b.y - a.y), (b.x - a.x)));

		return brng < 0 ? brng + 360 : brng;
	}
	
	
	
}
