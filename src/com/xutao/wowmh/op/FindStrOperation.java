package com.xutao.wowmh.op;

import java.awt.Rectangle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xnx3.bean.ActiveBean;
import com.xnx3.microsoft.FindStr;
import com.xutao.wowmh.core.ComWrapper;
import com.xutao.wowmh.core.PixelPoint;

public class FindStrOperation extends FindStr {
	private static final Logger logger = LogManager.getLogger(FindStrOperation.class);

	/** 字典 */
	private int dict;

	private final ComWrapper com;

	/** 相似度 */
	private double sim = 0.8;

	public FindStrOperation(ComWrapper com, int dict) {
		super(com.getActiveXComponent());
		this.com = com;
		this.dict = dict;
	}


	
	/**
	 * 在屏幕范围(x1,y1,x2,y2)内,查找string(可以是任意个字符串的组合),并返回找到的符合的坐标位置
	 * 		<li>须提前设置好点阵字库 {@link Com#setDict(int, String)}
	 * @param xStart 区域的左上X坐标
	 * @param yStart 区域的左上Y坐标
	 * @param xEnd 区域的右下X坐标
	 * @param yEnd 区域的右下Y坐标
	 * @param findString 待查找的字符串, 可以是字符串组合，比如"长安|洛阳|大雁塔",中间用"|"来分割字符串
	 * @param colorFormat 颜色格式串，如  "FFFFFF-101010|555555-102030"
	 * @param sim 相似度,取值范围0.1-1.0 , 一般情况下0.9即可。越大，查找的越精准，误差越小，速度越快
	 * @param useDict 字库的序号，以此来设定使用哪个字库.   {@link Com#setDict(int, String)} 便是此设置的int序号
	 * @return int[3]   <li>int[0]:是否查找到，若是没有则返回-1  
	 * 					<li>int[1]查找到的文字的X的值，没有返回-1   
	 * 					<li>int[2]查找到的文字的Y值，没有返回-1
	 */
	public int[] findStrE(int xStart, int yStart, int xEnd,int yEnd, String findString ,String colorFormat,double sim,int useDict ) {
		if (logger.isDebugEnabled()) {
			com.getPrintScreen().capture(xStart, yStart, xEnd, yEnd);
		}
		return super.findStrE(xStart, yStart, xEnd, yEnd, findString, colorFormat, sim, useDict);
	}
	
	
	/** 从屏幕中央找图 */
	public int[] findStrCenter(String str, String color, int xOffset, int yOffset) {
		int centerX = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width / 2;
		int centerY = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height / 2;
		return this.findStrE(centerX - xOffset, centerY - yOffset, centerX + xOffset, centerY + yOffset, str, color, sim, dict);
	}

	/**
	 * 将屏幕进行等分，并在指定的一块范围内查找
	 * 
	 * @param str
	 *            要查找的字符串
	 * @param color
	 *            颜色格式
	 * @param xCount
	 *            宽的方向上的等分数，小于像素数的非负数
	 * @param yCount
	 *            高的方向上的等分数，小于像素数的非负数
	 * @param index
	 *            以左上角为开端，从左到右，从上到下的各等分区域的序号。从零开始。
	 * @return
	 */
	public int[] findStrByIndex(String str, String color, int xCount, int yCount, int index) {

		if (index < 0 || index >= xCount * yCount) {
			throw new IllegalArgumentException("index必须是有效地的索引数。 大于等于零，并小于X方向和Y方向切割的总块数");
		}

		int width = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;

		if (xCount < 0 || xCount >= width || yCount < 0 || yCount >= height) {
			throw new IllegalArgumentException("xCount/yCount必须是有效地的等分数。 大于等于零，并小于其方向的像素数");
		}
		int x = index % xCount;
		int y = new Double(Math.floor(index / yCount)).intValue();

		int xOffset = width / xCount;

		int yOffset = height / yCount;

		PixelPoint original = new PixelPoint(x * xOffset, y * yOffset);

		return findStrByOffset(str, color, original, xOffset, yOffset);
	}

	/** 在一个指定起点和长宽的范围内查找文字 */
	public int[] findStrByOffset(String str, String color, PixelPoint original, int areaWidth, int areaHeight) {
		return this.findStrE(original.x, original.y, original.x + areaWidth, original.y + areaHeight, str, color, sim, dict);
	}

	/** 在一个长方形范围内查找文字 */
	public int[] findStr(String str, String color, Rectangle r) {
		return this.findStrE(r.x, r.y, r.x + r.width, r.y + r.height, str, color, sim, dict);
	}

	public String readStr(PixelPoint original, int areaWidth, int areaHeight, String colorFormat) {
		return this.readStr(original.x, original.y, original.x + areaWidth, original.y + areaHeight, colorFormat, "|", sim, dict);
	}

	/**
	 * 指定区域内读取文字
	 * 		<li>须提前设置好点阵字库 {@link Com#setDict(int, String)}
	 * @param xStart 区域的左上X坐标
	 * @param yStart 区域的左上Y坐标
	 * @param xEnd 区域的右下X坐标
	 * @param yEnd 区域的右下Y坐标
	 * @param colorFormat 颜色格式串
	 * 			<li>RGB单色识别:"9f2e3f-000000"
	 * 			<li>RGB单色差色识别:"9f2e3f-030303"
	 * 			<li>RGB多色识别(最多支持10种,每种颜色用"|"分割):"9f2e3f-030303|2d3f2f-000000|3f9e4d-100000"
	 * 			<li>HSV多色识别(最多支持10种,每种颜色用"|"分割):"20.30.40-0.0.0|30.40.50-0.0.0"
	 * 			<li>背景色识别:"b@ffffff-000000"
	 * @param lineBreak 换行符，为空或者null则不使用换行符分割。读取的文字每行换行时会加上此字符串作为换行分割
	 * @param sim 相似度,取值范围0.1-1.0 , 一般情况下0.9即可。越大，查找的越精准，误差越小，速度越快
	 * @param useDict 字库的序号，以此来设定使用哪个字库.   {@link Com#setDict(int, String)} 便是此设置的int序号
	 * @return String 若是没找到，返回null
	 */
	public String readStr(int xStart,int yStart,int xEnd,int yEnd,String colorFormat,String lineBreak, double sim,int useDict) {
		if (logger.isDebugEnabled()) {
			com.getPrintScreen().capture(xStart, yStart, xEnd, yEnd);
		}
		return super.readStr(xStart, yStart, xEnd, yEnd, colorFormat, lineBreak, sim, useDict);
	}
	
	public void setDict(int dict) {
		this.dict = dict;
	}

	public void setSim(double sim) {
		this.sim = sim;
	}

	public int getDict() {
		return dict;
	}

	public double getSim() {
		return sim;
	}
}
