package com.xutao.wowmh.op;

import java.awt.Rectangle;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xnx3.microsoft.FindPic;
import com.xutao.wowmh.core.ComWrapper;
import com.xutao.wowmh.core.PixelPoint;

public class FindPicOperation extends FindPic {
	private static final double SIMILARITY = 0.8;

	private static final Logger logger = LogManager.getLogger(FindPicOperation.class);

	private final ComWrapper com;

	public FindPicOperation(ComWrapper com) {
		super(com.getActiveXComponent());
		this.com = com;
	}

	private boolean debug = false;
	
	/**
	 * 查找图片，返回找到的第一个图片的坐标
	 * 
	 * @param xStart
	 *            区域的左上X坐标
	 * @param yStart
	 *            区域的左上Y坐标
	 * @param xEnd
	 *            区域的右下X坐标
	 * @param yEnd
	 *            区域的右下Y坐标
	 * @param pic
	 *            图像集，多个用|分割，24位色bmp格式,且边框为同一种颜色,比如"test.bmp|test2.bmp|test3.bmp"
	 * @param deviationColor
	 *            颜色偏差，比如"203040" 表示RGB的色偏分别是20 30 40 (这里是16进制表示)
	 * @param sim
	 *            相似度,取值范围0.1-1.0 , 一般情况下0.9即可
	 *            <li>越大，查找的越精准，误差越小，速度越快
	 * @param order
	 *            <li>0: 从左到右,从上到下
	 *            <li>1: 从左到右,从下到上
	 *            <li>2: 从右到左,从上到下
	 *            <li>3: 从右到左, 从下到上
	 * @return 返回找到的图片，返回int[]
	 *         <li>int[0]:是否找到，没找到返回-1
	 *         <li>int[1]:找到的图像的x坐标
	 *         <li>int[2]:找到的图像的y坐标
	 */
	public int[] findPic(int xStart, int yStart, int xEnd, int yEnd, String pic, String deviationColor, double sim, int order) {

		if (logger.isDebugEnabled() && debug) {
			String name = StringUtils.split(pic, ".")[0];
			com.getPrintScreen().capture(name, xStart, yStart, xEnd, yEnd);
		}
		return super.findPic(xStart, yStart, xEnd, yEnd, pic, deviationColor, sim, order);
	}

	/** 从屏幕中央找图 */
	public int[] findPicCenter(String bmp, int xOffset, int yOffset, int mode) {
		int centerX = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width / 2;
		int centerY = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height / 2;
		return this.findPic(centerX - xOffset, centerY - yOffset, centerX + xOffset, centerY + yOffset, bmp, "000000", SIMILARITY, mode);
	}

	/** 已默认方式从屏幕中央找图 */
	public int[] findPicCenter(String bmp, int xOffset, int yOffset) {
		// 默认的搜索方式是[从左到右,从下到上]
		return findPicCenter(bmp, xOffset, yOffset, 1);
	}

	/**
	 * 将屏幕进行等分，并在指定的一块范围内查找
	 * 
	 * @param bmp
	 *            要查找的图片
	 * @param xCount
	 *            宽的方向上的等分数，小于像素数的非负数
	 * @param yCount
	 *            高的方向上的等分数，小于像素数的非负数
	 * @param index
	 *            以左上角为开端，从左到右，从上到下的各等分区域的序号。从零开始。
	 * @return
	 */
	public int[] findPicByIndex(String bmp, int xCount, int yCount, int index) {

		if (index < 0 || index >= xCount * yCount) {
			throw new IllegalArgumentException("index必须是有效地的索引数。 大于等于零，并小于X方向和Y方向切割的总块数");
		}

		int width = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;

		if (xCount < 0 || xCount >= width || yCount < 0 || yCount >= height) {
			throw new IllegalArgumentException("xCount/yCount必须是有效地的等分数。 大于等于零，并小于其方向的像素数");
		}
		int x = index % xCount;
		int y = (index - x) / xCount; // new Double(Math.floor(index / yCount)).intValue();

		int xOffset = width / xCount;

		int yOffset = height / yCount;

		PixelPoint original = new PixelPoint(x * xOffset, y * yOffset);

		return findPicByOffset(bmp, original, xOffset, yOffset);
	}

	/** 在一个指定起点和长宽的范围内查找图片 */
	public int[] findPicByOffset(String bmp, PixelPoint original, int areaWidth, int areaHeight) {
		return this.findPic(original.x, original.y, original.x + areaWidth, original.y + areaHeight, bmp, "000000", SIMILARITY, 0);
	}

	/** 在一个长方形范围内查找图片 */
	public int[] findPicByOffset(String bmp, Rectangle r) {
		return this.findPic(r.x, r.y, r.x + r.width, r.y + r.height, bmp, "000000", SIMILARITY, 0);
	}
}
