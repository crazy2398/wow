package com.xutao.wowmh.robot.role;

import com.xutao.wowmh.core.ComWrapper;
import com.xutao.wowmh.robot.AbstractRobot;
/**
 * 
 * 快捷键1：坐骑
 * 快捷键2：攻击宏
 * 快捷键3：
 * 快捷键4：
 * 快捷键5：
 * 快捷键6：
 * 快捷键7：
 * 快捷键8：
 * 快捷键9：
 * 快捷键0：炉石
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */
public class AbastractRole extends AbstractRobot {

	public AbastractRole(ComWrapper com) {
		super(com);
	}

	/** 是否处在交战状态 */ 
	public boolean isUnderAttack(){
		return false;
	}

	/** 是否已经死亡（包含鬼魂状态） */ 
	public boolean isDead(){
		return false;
	}

	/** 是否已经释放灵魂，处于鬼魂状态 */ 
	public boolean isGhost(){
		return false;
		
	}
	
	/** 是否在飞 */ 
	public boolean isFlying(){
		return false;
	}

	/** 是否骑在坐骑上，或者处于旅行状态 */ 
	public boolean isRiding(){
		return false;
	}

	/** 是否在乘坐旅行服务 */ 
	public boolean isOnTaxi() {
		return false;
	}

	/** 获取包中的可用栏位数量 */ 
	public int getSpareBagCount() {
		return 0;
	}
	
	/** 开始攻击 */ 
	public void target(){
		
	}
	/** 开始攻击 */ 
	public void startAttack(){
		
	}
	/** 停止攻击 */ 
	public void stopAttack(){
		
	}
	
	/** 强制复活 */ 
	public void forceRevive(){
		
	}

	/** 使用炉石 */ 
	public void useHearthstone(){
		
	}
}
