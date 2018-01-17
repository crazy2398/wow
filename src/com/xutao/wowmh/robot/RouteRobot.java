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

	private PixelPoint getOriginal() {
		PixelPoint expected = new PixelPoint(-97, 77);
		return getColorOp().findColor(expected, 20, 20, "272444-090805");
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
		}

		logger.debug(original);

		PixelPoint outermost = getOutermostPoint(original, "C9C7CA-2e3035");
		// PixelPoint outermost = getOutermostPoint(original, "272444-090805");
		return getAngle(original, outermost);
	}

	static double getAngle(PixelPoint a, PixelPoint b) {
		// double y = Math.sin(b.x - a.x) * Math.cos(b.y);
		// double x = Math.cos(a.y) * Math.sin(b.y) - Math.sin(a.y) * Math.cos(b.y) *
		// Math.cos(b.x - a.x);
		// double brng = Math.atan2(y, x);
		//
		// brng = Math.toDegrees(brng);
		// double brng = ((double)(b.y - a.y) / (b.x - a.x)) / Math.PI * 180d;
		logger.debug("a(" + a.x + "," + a.y + "),b(" + b.x + "," + b.y + ")");
		// double brng= Math.toDegrees(Math.atan ((double)(b.y - a.y) / (b.x - a.x)));

		double brng = Math.toDegrees(Math.asin((double) (b.y - a.y) / a.distance(b)));
		return brng < 0 ? brng + 360 : brng;
	}

}
