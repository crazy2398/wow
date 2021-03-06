package com.xutao.wowmh.core;

import java.io.Closeable;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xnx3.microsoft.Com;
import com.xnx3.microsoft.File;
import com.xnx3.microsoft.SystemUtil;
import com.xnx3.microsoft.Window;
import com.xutao.games.recorder.ScreenshotCatcher;
import com.xutao.wowmh.op.ColorOperation;
import com.xutao.wowmh.op.FindPicOperation;
import com.xutao.wowmh.op.KeyboardOperation;
import com.xutao.wowmh.op.MouseOperation;

public class ComWrapper extends Com implements Closeable {
	private static final Logger logger = LogManager.getLogger(ComWrapper.class);

	public static final int ACCOUNT_DICT = 0;
	public static final int ROLE_LIST_DICT = 1;

	/** 窗口操作类 */
	private final Window windowOp = new Window(this.getActiveXComponent());

	/** 系统操作类 */
	private final SystemUtil systemUtilOp = new SystemUtil(this.getActiveXComponent());

	/** 鼠标模拟操作类 */
	private final MouseOperation mouseOp = new MouseOperation(this);

	/** 键盘模拟操作类 */
	private final KeyboardOperation keyboardOp = new KeyboardOperation(this.getActiveXComponent());

	/** 颜色相关的取色、判断类 */
	private final ColorOperation colorOp = new ColorOperation(this);

	/** 文件相关的操作类，如截图等 */
	private final File fileOp = new File(this.getActiveXComponent());

	/** 文件相关的操作类，如截图等 */
	private final FindPicOperation findPicOp = new FindPicOperation(this);

	/** 实时截图的工具，用来调试 */
	private final ScreenshotCatcher printScreen = new ScreenshotCatcher("D:\\test", 256);

	public ComWrapper() {
		this(null);
	}

	public ComWrapper(String resourceDir) {
		super();
		if (!StringUtils.isEmpty(resourceDir)) {
			this.setResourcePath(resourceDir);
		}
		// 登录时的账号识别字典
		this.setDict(ComWrapper.ACCOUNT_DICT, "wow.txt");
		this.setDict(ComWrapper.ROLE_LIST_DICT, "roleList.txt");
	}

	@Override
	public void close() {
		try {
			this.unbind();
			logger.info("资源已释放");
		} catch (Exception e) {
			logger.error("资源释放失败", e);
			// e.printStackTrace();
		}
	}

	public Window getWindowOp() {
		return windowOp;
	}

	public SystemUtil getSystemUtilOp() {
		return systemUtilOp;
	}

	public MouseOperation getMouseOp() {
		return mouseOp;
	}

	public KeyboardOperation getKeyboardOp() {
		return keyboardOp;
	}

	public ColorOperation getColorOp() {
		return colorOp;
	}

	public File getFileOp() {
		return fileOp;
	}

	public FindPicOperation getFindPicOp() {
		return findPicOp;
	}

	public ScreenshotCatcher getPrintScreen() {
		return printScreen;
	}
}
