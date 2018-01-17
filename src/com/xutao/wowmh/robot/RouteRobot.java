package com.xutao.wowmh.robot;

import com.xutao.wowmh.core.ComWrapper;
import com.xutao.wowmh.core.PixelPoint;

public class RouteRobot extends AbstractRobot {

	public RouteRobot(ComWrapper com) {
		super(com);
	}

	private PixelPoint original = null;
	
	private PixelPoint getOriginal() {

		PixelPoint expected = new PixelPoint(87, 87);

		int[] resutl = getColorOp().findColor(expected, 10, 10, "272444-090805");

		if (resutl[0] >= 0 && resutl[1] >= 0) {
			return new PixelPoint(resutl[0], resutl[1]);
		}

		return null;
	}
	
	private PixelPoint getOutermostPoint(PixelPoint center, String color){
		
		return null;
		
	}
	
	/** 计算朝向 */
	private double getCurrentOrientation(){
		
		if(original == null){
			original = getOriginal();
			if(original == null){
				throw new RuntimeException("没有找到小地图原点");
			}
		}
		
		PixelPoint outermost = getOutermostPoint(original, "C9C7CA-2e3035");
		
		// TODO
		return 0;
	}

}
