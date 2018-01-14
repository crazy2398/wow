package com.xutao.wowmh.core;

import java.awt.Point;

public class PixelPoint extends Point {

	private static final long serialVersionUID = -2434986550841604092L;

	public PixelPoint(int arg0, int arg1) {

		this.x = arg0;
		this.y = arg1;

		if (this.x < 0) {
			this.x += java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;

		}
		if (this.y < 0) {
			this.y += java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;

		}

	}

	public PixelPoint offset(int x, int y) {
		return new PixelPoint(this.x + x, this.y + y);
	}

}
