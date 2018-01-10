package com.xutao.wowmh.op;

import com.xnx3.bean.ActiveBean;
import com.xnx3.microsoft.Press;

public class KeyboardOperation extends Press {

	public KeyboardOperation(ActiveBean activeXComponent) {
		super(activeXComponent);
	}

	public static final int PRINT_SCREEN = 44;

	public void shift(int key) {
		keyDown(SHIFT);
		keyPress(key);
		keyUp(SHIFT);
	}

	public void ctrl(int key) {
		keyDown(CTRL);
		keyPress(key);
		keyUp(CTRL);
	}

	public void alt(int key) {
		keyDown(ALT);
		keyPress(key);
		keyUp(ALT);
	}

	public void tab() {
		keyPress(TAB);
	}

	public void enter() {
		keyPress(ENTER);
	}

	public void printScreen() {
		keyPress(PRINT_SCREEN);
	}
}
