package com.xutao.wowmh.robot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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

		//Collection<PixelPoint> allpoints = getColorOp().findAllPoints(expected, 20, 20, "272842-0f0f0d");
		// Collection<PixelPoint> allpoints = getColorOp().findAllPoints(expected, 20,
		// 20, "1E1E2D-19161A");
		// Collection<PixelPoint> allpoints = getColorOp().findAllPoints(expected, 20,
		// 20, "303751-2E302E");

		Collection<PixelPoint> allpoints = getColorOp().findAllPoints(expected, 20, 20, "22253F-1F1E1C");

		Map<Integer, Map<Integer, PixelPoint>> xMap = new HashMap<>();
		Map<Integer, Map<Integer, PixelPoint>> yMap = new HashMap<>();

		for (PixelPoint p : allpoints) {

			Map<Integer, PixelPoint> map = xMap.get(p.x);

			if (map == null) {
				map = new HashMap<>();
				xMap.put(p.x, map);
			}
			map.put(p.y, p);

			map = yMap.get(p.y);
			if (map == null) {
				map = new HashMap<>();
				yMap.put(p.y, map);
			}
			map.put(p.x, p);
		}

		PixelPoint center = null;

		printPointMatrix(allpoints);

		for (PixelPoint p : allpoints) {
			Map<Integer, PixelPoint> xPlusOne = xMap.get(p.x + 1);
			Map<Integer, PixelPoint> yPlusOne = yMap.get(p.y + 1);
			Map<Integer, PixelPoint> xMinusOne = xMap.get(p.x - 1);
			Map<Integer, PixelPoint> yMinusOne = yMap.get(p.y - 1);

			if (xPlusOne == null || yPlusOne == null || xMinusOne == null || yMinusOne == null) {
				continue;
			}

			// logger.debug("center=" + p);
			//
			// logger.debug("(x-1,y-1)=" + yMinusOne.containsKey(p.x - 1) );
			// logger.debug("(x,y-1)=" + yMinusOne.containsKey(p.x) );
			// logger.debug("(x+1,y-1)=" + yMinusOne.containsKey(p.x +1) );
			// 第一层
			boolean found = yMinusOne.containsKey(p.x - 1) && yMinusOne.containsKey(p.x) && yMinusOne.containsKey(p.x + 1);
			// logger.debug("(x-1,y)=" + xPlusOne.containsKey(p.y) );
			// logger.debug("(x+1,y)=" + xMinusOne.containsKey(p.y));
			// 第二层
			found = found && xPlusOne.containsKey(p.y) && xMinusOne.containsKey(p.y);
			// 第三层
			found = found && yPlusOne.containsKey(p.x - 1) && yPlusOne.containsKey(p.x) && yPlusOne.containsKey(p.x + 1);
			// logger.debug("(x-1,y+1)=" + yPlusOne.containsKey(p.x - 1) );
			// logger.debug("(x,y+1)=" + yPlusOne.containsKey(p.x) );
			// logger.debug("(x+1,y+1)=" + yPlusOne.containsKey(p.x +1) );

			if (found) {
				center = p;
				break;
			}
		}

		return center;
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

	private static final void printPointMatrix(Collection<PixelPoint> c) {

		Map<Integer, List<PixelPoint>> matrix = new HashMap<>();
		// Map<Integer, Map<Integer, PixelPoint>> yMap = new HashMap<>();

		int minX = Integer.MAX_VALUE;

		for (PixelPoint p : c) {

			List<PixelPoint> row = matrix.get(p.y);

			if (row == null) {
				row = new ArrayList<>();
				matrix.put(p.y, row);
			}
			row.add(p);

			minX = Math.min(minX, p.x);
		}
		logger.debug("minX=" + minX);

		@SuppressWarnings({ "rawtypes", "unchecked" })
		Comparator<PixelPoint> comp = new Comparator() {

			@Override
			public int compare(Object arg0, Object arg1) {
				if (arg0 instanceof PixelPoint && arg1 instanceof PixelPoint) {
					PixelPoint a = (PixelPoint) arg0;
					PixelPoint b = (PixelPoint) arg1;
					return a.x == a.y ? ObjectUtils.compare(a.y, b.y) : ObjectUtils.compare(a.x, b.x);
				}
				return 0;
			}

		};

		int maxX = -1;

		for (List<PixelPoint> row : matrix.values()) {
			Collections.sort(row, comp);
			maxX = Math.max(maxX, row.get(row.size() - 1).x);
		}

		List<Integer> rows = new ArrayList<>(matrix.keySet().size());
		rows.addAll(matrix.keySet());
		Collections.sort(rows);

		final String BLANK = "☒";
		final String POINT = "■";

		Integer lastRowIdx = null;

		int lineSize = maxX - minX + 1;

		for (Integer rowIdx : rows) {

			// 打印空行
			for (int i = 0; lastRowIdx != null && i < rowIdx - lastRowIdx - 1; i++) {
				logger.debug(StringUtils.repeat(BLANK, lineSize));
			}
			lastRowIdx = rowIdx;

			PixelPoint lastPoint = new PixelPoint(minX, rowIdx);
			StringBuilder str = new StringBuilder();
			StringBuilder pstr = new StringBuilder();
			for (PixelPoint p : matrix.get(rowIdx)) {
				str.append(StringUtils.repeat(BLANK, p.x - lastPoint.x - 1)).append(POINT);
				pstr.append("(").append(p.x).append(",").append(p.y).append(")");
				lastPoint = p;
			}

			logger.debug(StringUtils.rightPad(str.toString(), lineSize, BLANK) + pstr.toString());
		}

	}
}
