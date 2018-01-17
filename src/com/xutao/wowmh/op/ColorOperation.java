package com.xutao.wowmh.op;

import java.awt.Point;
import java.awt.Rectangle;

import com.xnx3.microsoft.Color;
import com.xutao.wowmh.core.ComWrapper;
import com.xutao.wowmh.core.PixelPoint;

/**
 * @author Xutao
 *
 */
public class ColorOperation extends Color {

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

	public ColorOperation(ComWrapper com) {
		super(com.getActiveXComponent());
		this.com = com;
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
	 * 范围区域内查询某颜色是否存在
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
	 * @return array
	 *         <li>若是成功返回坐在坐标int[0]：X坐标，int[1]：Y坐标
	 *         <li>若是失败，则都是-1
	 */
	public int[] findColor(PixelPoint original, int areaWidth, int areaHeight, String color) {
		return this.findColor(original.x, original.y, original.x + areaWidth, original.y + areaHeight, color,
				similarity, direction.ordinal());

	}

	/**
	 * 范围区域内查询某颜色是否存在
	 * 
	 * @param r
	 *            区域
	 * @param color
	 *            颜色 格式为"RRGGBB-DRDGDB",比如"123456-000000|aabbcc-202020"
	 * 
	 * @return array
	 *         <li>若是成功返回坐在坐标int[0]：X坐标，int[1]：Y坐标
	 *         <li>若是失败，则都是-1
	 */
	public int[] findColor(Rectangle r, String color) {
		return this.findColor(r.x, r.y, r.x + r.width, r.y + r.height, color, similarity, direction.ordinal());

	}

}