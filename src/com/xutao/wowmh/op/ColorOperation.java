package com.xutao.wowmh.op;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;
import com.xnx3.microsoft.Color;
import com.xutao.wowmh.core.ComWrapper;
import com.xutao.wowmh.core.PixelPoint;

/**
 * @author Xutao
 *
 */
public class ColorOperation extends Color {

	private static final Logger logger = LogManager.getLogger(ColorOperation.class);

	public enum Direction {
		/** 0: 从左到右,从上到下 */
		L2R_U2D,
		/** 1: 从左到右,从下到上 */
		L2R_D2U,
		/** 2: 从右到左,从上到下 */
		R2L_U2D,
		/** 3: 从右到左,从下到上 */
		R2L_D2U,
		/** 4：从中心往外查找 */
		C2S,
		/** 5: 从上到下,从左到右 */
		U2D_L2R,
		/** 6: 从上到下,从右到左 */
		U2D_R2L,
		/** 7: 从下到上,从左到右 */
		D2U_L2R,
		/** 8: 从下到上,从右到左 */
		D2U_R2L

	}

	private final ComWrapper com;

	private final ActiveXComponent active;

	public ColorOperation(ComWrapper com) {
		super(com.getActiveXComponent());
		this.com = com;
		this.active = com.getActiveXComponent().getDm();
	}

	public float getSimilarity() {
		return similarity;
	}

	public void setSimilarity(float similarity) {
		this.similarity = similarity;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	private float similarity = 1.0f;

	private Direction direction = Direction.C2S;

	/**
	 * 获取指定点的颜色
	 * 
	 * @param p
	 *            点的位置
	 * @return 字符串形式16禁制，如 FFFFFF
	 */
	public String getColor(Point p) {
		return super.getColor(p.x, p.y);
	}

	/**
	 * 判断某点颜色是否跟指定的相同或相似
	 * 
	 * @param p
	 *            点的位置
	 * @param color
	 *            颜色字符串,可以支持偏色,多色,例如 "ffffff-202020|000000-000000"
	 *            这个表示白色偏色为202020,和黑色偏色为000000.颜色最多支持10种颜色组合.
	 * @param similarity
	 *            相似度(0.1-1.0)
	 * @return 若颜色匹配，此点是这颜色，则返回true
	 */
	public boolean findColor(Point p, String color) {
		return super.findColor(p.x, p.y, color, similarity);
	}

	/**
	 * 范围区域内查询某颜色的位置
	 * 
	 * @param original
	 *            起始点坐标，区域的左点
	 * @param areaWidth
	 *            区域的宽度
	 * @param areaHeight
	 *            区域的高度
	 * @param color
	 *            颜色 格式为"RRGGBB-DRDGDB",比如"123456-000000|aabbcc-202020"
	 * 
	 * @return PixelPoint
	 *         <li>若是成功返回找到的第一个点的坐标
	 *         <li>若是失败，返回null
	 */
	public PixelPoint findColor(PixelPoint original, int areaWidth, int areaHeight, String color) {
		int[] a = this.findColor(original.x, original.y, original.x + areaWidth, original.y + areaHeight, color, similarity, direction.ordinal());
		return a != null && a.length > 1 && a[0] > 0 ? new PixelPoint(a[0], a[1]) : null;

	}

	/**
	 * 范围区域内查询某颜色是否存在
	 * 
	 * @param xStart
	 *            起始点x坐标，区域的左上X坐标
	 * @param yStart
	 *            起始点y坐标，区域的左上Y坐标
	 * @param xEnd
	 *            结束点x坐标，区域的右下X坐标
	 * @param yEnd
	 *            结束点y坐标，区域的右下Y坐标
	 * @param color
	 *            颜色 格式为"RRGGBB-DRDGDB",比如"123456-000000|aabbcc-202020"
	 * @param sim
	 *            相似度,取值范围0.1-1.0
	 * @param dir
	 *            查找方向
	 *            <li>0: 从左到右,从上到下
	 *            <li>1: 从左到右,从下到上
	 *            <li>2: 从右到左,从上到下
	 *            <li>3: 从右到左,从下到上
	 *            <li>4：从中心往外查找
	 *            <li>5: 从上到下,从左到右
	 *            <li>6: 从上到下,从右到左
	 *            <li>7: 从下到上,从左到右
	 *            <li>8: 从下到上,从右到左
	 * @return array
	 *         <li>若是成功返回坐在坐标int[0]：X坐标，int[1]：Y坐标
	 *         <li>若是失败，则都是-1
	 */
	public int[] findColor(int xStart, int yStart, int xEnd, int yEnd, String color, double sim, int dir) {
		if (logger.isDebugEnabled()) {
			com.getPrintScreen().capture("findColor_" + color, xStart, yStart, xEnd, yEnd);
		}

		return super.findColor(xStart, yStart, xEnd, yEnd, color, sim, dir);
	}

	/**
	 * 范围区域内查询某颜色的位置
	 * 
	 * @param r
	 *            区域
	 * @param color
	 *            颜色 格式为"RRGGBB-DRDGDB",比如"123456-000000|aabbcc-202020"
	 * 
	 * @return PixelPoint
	 *         <li>若是成功返回找到的第一个点的坐标
	 *         <li>若是失败，返回null
	 */
	public PixelPoint findColor(Rectangle r, String color) {
		int[] a = this.findColor(r.x, r.y, r.x + r.width, r.y + r.height, color, similarity, direction.ordinal());
		return a != null && a.length > 1 && a[0] > 0 ? new PixelPoint(a[0], a[1]) : null;

	}

	/**
	 * 范围区域内查询某颜色的全部位置
	 * 
	 * @param original
	 *            起始点坐标，区域的左点
	 * @param areaWidth
	 *            区域的宽度
	 * @param areaHeight
	 *            区域的高度
	 * @param color
	 *            颜色 格式为"RRGGBB-DRDGDB",比如"123456-000000|aabbcc-202020"
	 * 
	 * @return Collection &lt;PixelPoint&gt;
	 *         <li>若是成功返回找到的所有点
	 *         <li>若是失败，返回空的Collection
	 */
	public Collection<PixelPoint> findAllPoints(PixelPoint original, int areaWidth, int areaHeight, String color) {
		return this.findAllPoints(original.x, original.y, original.x + areaWidth, original.y + areaHeight, color, similarity, direction.ordinal());

	}

	/**
	 * 范围区域内查询某颜色是否存在
	 * 
	 * @param r
	 *            区域
	 * @param color
	 *            颜色 格式为"RRGGBB-DRDGDB",比如"123456-000000|aabbcc-202020"
	 * 
	 * @return Collection &lt;PixelPoint&gt;
	 *         <li>若是成功返回找到的所有点
	 *         <li>若是失败，返回空的Collection
	 */
	public Collection<PixelPoint> findAllPoints(Rectangle r, String color) {
		return this.findAllPoints(r.x, r.y, r.x + r.width, r.y + r.height, color, similarity, direction.ordinal());
	}

	/**
	 * 范围区域内查询某颜色是否存在
	 * 
	 * @param xStart
	 *            起始点x坐标，区域的左上X坐标
	 * @param yStart
	 *            起始点y坐标，区域的左上Y坐标
	 * @param xEnd
	 *            结束点x坐标，区域的右下X坐标
	 * @param yEnd
	 *            结束点y坐标，区域的右下Y坐标
	 * @param color
	 *            颜色 格式为"RRGGBB-DRDGDB",比如"123456-000000|aabbcc-202020"
	 * @param sim
	 *            相似度,取值范围0.1-1.0
	 * @param dir
	 *            查找方向
	 *            <li>0: 从左到右,从上到下
	 *            <li>1: 从左到右,从下到上
	 *            <li>2: 从右到左,从上到下
	 *            <li>3: 从右到左,从下到上
	 *            <li>4：从中心往外查找
	 *            <li>5: 从上到下,从左到右
	 *            <li>6: 从上到下,从右到左
	 *            <li>7: 从下到上,从左到右
	 *            <li>8: 从下到上,从右到左
	 * 
	 * @return Collection &lt;PixelPoint&gt;
	 *         <li>若是成功返回找到的所有点
	 *         <li>若是失败，返回空的Collection
	 */
	protected Collection<PixelPoint> findAllPoints(int xStart, int yStart, int xEnd, int yEnd, String color, double sim, int dir) {
		if (logger.isDebugEnabled()) {
			com.getPrintScreen().capture("findAllPoints" + color, xStart, yStart, xEnd, yEnd);
		}

		try {
			Variant[] var = new Variant[7];
			var[0] = new Variant(xStart);
			var[1] = new Variant(yStart);
			var[2] = new Variant(xEnd);
			var[3] = new Variant(yEnd);
			var[4] = new Variant(color);
			var[5] = new Variant(sim);
			// FindColorEx不支持中间查找
			var[6] = new Variant(dir == 4 ? 0 : dir);

			// logger.error(this.active.invoke("Ver").getString());

			String findColor = this.active.invoke("FindColorEx", var).getString();
			logger.debug("FindColorEx:" + findColor);
			if (StringUtils.isEmpty(findColor)) {
				return Collections.emptyList();
			}

			String[] colors = StringUtils.split(findColor, "|");// findColor.split("\\|");
			String[] x = StringUtils.split(colors[0], ",");
			String[] y = StringUtils.split(colors[1], ",");

			ArrayList<PixelPoint> points = new ArrayList<>(x.length);

			for (int i = 0; i < x.length; i++) {
				points.add(new PixelPoint(Integer.parseInt(x[i]), Integer.parseInt(y[i])));
			}

			findColor = null;

			return points;

		} catch (Exception e) {
			logger.error("查找颜色时发生异常", e);
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static Comparator<PixelPoint> comp = new Comparator() {

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

	// private static final void printPointMatrix(Collection<PixelPoint> c) {
	//
	// Map<Integer, List<PixelPoint>> matrix = new HashMap<>();
	// // Map<Integer, Map<Integer, PixelPoint>> yMap = new HashMap<>();
	//
	// int minX = Integer.MAX_VALUE;
	//
	// for (PixelPoint p : c) {
	//
	// List<PixelPoint> row = matrix.get(p.y);
	//
	// if (row == null) {
	// row = new ArrayList<>();
	// matrix.put(p.y, row);
	// }
	// row.add(p);
	//
	// minX = Math.min(minX, p.x);
	// }
	// logger.debug("minX=" + minX);
	//
	// int maxX = -1;
	//
	// for (List<PixelPoint> row : matrix.values()) {
	// Collections.sort(row, comp);
	// maxX = Math.max(maxX, row.get(row.size() - 1).x);
	// }
	//
	// List<Integer> rows = new ArrayList<>(matrix.keySet().size());
	// rows.addAll(matrix.keySet());
	// Collections.sort(rows);
	//
	// final String BLANK = "☒";
	// final String POINT = "■";
	//
	// Integer lastRowIdx = null;
	//
	// int lineSize = maxX - minX + 1;
	//
	// for (Integer rowIdx : rows) {
	//
	// // 打印空行
	// for (int i = 0; lastRowIdx != null && i < rowIdx - lastRowIdx - 1; i++) {
	// logger.debug(StringUtils.repeat(BLANK, lineSize));
	// }
	// lastRowIdx = rowIdx;
	//
	// PixelPoint lastPoint = new PixelPoint(minX, rowIdx);
	// StringBuilder str = new StringBuilder();
	// StringBuilder pstr = new StringBuilder();
	// for (PixelPoint p : matrix.get(rowIdx)) {
	// str.append(StringUtils.repeat(BLANK, p.x - lastPoint.x - 1)).append(POINT);
	// pstr.append("(").append(p.x).append(",").append(p.y).append(")");
	// lastPoint = p;
	// }
	//
	// logger.debug(StringUtils.rightPad(str.toString(), lineSize, BLANK) +
	// pstr.toString());
	// }
	//
	// }

	/** 一个点的矩阵结构 */
	private static class PointsArray {
		private PixelPoint[][] array = null;
		private int minX = Integer.MAX_VALUE;
		private int minY = Integer.MAX_VALUE;
		private int maxX = -1;
		private int maxY = -1;

		public PointsArray(Collection<PixelPoint> c) {
			Map<Integer, List<PixelPoint>> matrix = new HashMap<>();

			for (PixelPoint p : c) {
				List<PixelPoint> row = matrix.get(p.x);

				if (row == null) {
					row = new ArrayList<>();
					matrix.put(p.x, row);
				}
				row.add(p);

				minX = Math.min(minX, p.x);
				minY = Math.min(minY, p.y);
				maxX = Math.max(maxX, p.x);
				maxY = Math.max(maxY, p.y);
			}

			array = new PixelPoint[maxX - minX + 1][maxY - minY + 1];

			for (Entry<Integer, List<PixelPoint>> e : matrix.entrySet()) {
				for (PixelPoint p : e.getValue()) {
					array[e.getKey() - minX][p.y - minY] = p;
				}
			}

			if (logger.isDebugEnabled()) {
				logger.debug(this);
			}
		}

		private int getX(PixelPoint p) {
			return p.x - minX;
		}

		private int getY(PixelPoint p) {
			return p.y - minY;
		}

		private int getRows() {
			return maxX - minX + 1;
		}

		private int getCols() {
			return maxY - minY + 1;
		}

		/**
		 * 查找能否构成一个被填充好的正方形
		 * 
		 * @param size
		 *            正方形边的像素数
		 * @return 找到的左上角点，没找到是null
		 */
		public PixelPoint findSquare(final int size) {
			for (PixelPoint[] row : array) {
				for (PixelPoint p : row) {
					if (p != null && findSquare(p, size)) {
						return p;
					}
				}
			}

			return null;
		}

		/**
		 * 以迭代的方式查找从某个点开始的正方形
		 * 
		 * @param leftTop
		 *            左上角
		 * @param size
		 *            正方形边的像素数
		 * @return 是否找到
		 */
		private boolean findSquare(final PixelPoint leftTop, final int size) {
			if (leftTop != null && size > 1) {

				int x = getX(leftTop);
				int y = getY(leftTop);

				// 如果到边界了还放不下就肯定没戏
				if (x + size > getRows() || y + size > getCols()) {
					logger.debug("findSquare(" + leftTop.toString() + "," + size + ")的返回值是false,超越边界");
					return false;
				}

				// 寻找最上边和最左边是否满足条件
				for (int i = 1; i < size; i++) {
					if (array[x + i][y] == null || array[x][y + i] == null) {
						logger.debug("findSquare(" + leftTop.toString() + "," + size + ")的返回值是false,上边线和左边线失败");
						return false;
					}
				}
				// 向右下角递进一个像素，看是否是一个小了一像素的正方形
				logger.debug("findSquare(" + leftTop.toString() + "," + size + ")的返回值是右下方向迭代值");
				return findSquare(array[x + 1][y + 1], size - 1);
			} else {
				// 迭代结束条件
				logger.debug("findSquare(" + (leftTop == null ? null : leftTop.toString()) + "," + size + ")的返回值是" + (leftTop != null) + ",迭代条件结束");
				return leftTop != null;
			}
		}

		public String toString() {
			final String NEW_LINE = System.getProperty("line.separator");
			final int devider = 50;
			StringBuilder str = new StringBuilder(StringUtils.repeat("=", devider)).append(NEW_LINE);
			for (PixelPoint[] row : array) {
				StringBuilder ps = new StringBuilder();
				for (PixelPoint p : row) {
					str.append(p != null ? "■" : "☒");
					if (p != null) {
						ps.append("(").append(p.x).append(",").append(p.y).append(")");
					}
				}
				str.append(ps.toString());
				str.append(NEW_LINE);
			}
			str.append(StringUtils.repeat("=", devider)).append(NEW_LINE);
			return str.toString();
		}
	}

	/**
	 * @param allpoints
	 * @return
	 */
	public PixelPoint findSquareColor(Collection<PixelPoint> allpoints, int sqSize) {

		PointsArray pa = new PointsArray(allpoints);

		return pa.findSquare(sqSize);
	}

}