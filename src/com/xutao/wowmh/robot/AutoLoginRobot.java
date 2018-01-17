package com.xutao.wowmh.robot;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xnx3.microsoft.SystemUtil;
import com.xnx3.microsoft.Window;
import com.xutao.wowmh.core.ComWrapper;
import com.xutao.wowmh.op.FindStrOperation;
import com.xutao.wowmh.op.KeyboardOperation;

public class AutoLoginRobot extends AbstractRobot {

	private static final String DEFAULT_ACCOUNT = "WoW1";
	private static final Logger logger = LogManager.getLogger(AutoLoginRobot.class);

	public AutoLoginRobot(ComWrapper com) {
		super(com);
	}

	private static final String WINDOW_TITLE = "魔兽世界";

	public boolean login(int hwnd, String email, String password) {
		return login(hwnd, email, password, DEFAULT_ACCOUNT);
	}

	// TODO 尚未做选服务器处理
	public boolean login(int hwnd, String email, String password, String account) {
		logger.info("开始登入系统");

		Window winOp = getWindowOp();

		KeyboardOperation kbOp = getKeyboardOp();

		// 输入用户名
		winOp.sendString(hwnd, email);
		logger.info("正在输入用户名");
		sleep(500, TimeUnit.MILLISECONDS);

		kbOp.tab();
		sleep(500, TimeUnit.MILLISECONDS);

		// 输入密码
		winOp.sendString(hwnd, password);
		logger.info("正在输入密码");
		sleep(500, TimeUnit.MILLISECONDS);

		kbOp.enter();

		// 将鼠标移开屏幕中央，防止鼠标附近的颜色影响选择
		getMouseOp().mouseMoveTo((int)(100*Math.random()), (int)(100*Math.random()));
		
		FindStrOperation findStr = new FindStrOperation(getComWrapper(), ComWrapper.ROLE_LIST_DICT);

		final int RERTY_LIMIT = 30;

		int retryTimes = RERTY_LIMIT;

		boolean done = false;

		while (retryTimes-- > 0) {
			sleep(1, TimeUnit.SECONDS);
			done = isWaitingRoleSelect();

			if (done) {
				beep("成功登入", 1);
				logger.info("在第" + (RERTY_LIMIT - retryTimes) + "次识别时成功登入");
				break;
			}

			if (clickPictureIfFound(getFindPicOp().findPicCenter("确定.bmp", 100, 100))) {
				// 发现了有提示就报错，退出
				logger.error("登陆过程中出现服务器报错");
				beep("登陆过程中出现服务器报错", 0, 0, 0, 0, 0);
				break;
			}

			// 查看是否是因为多帐户待选导致的
			int[] result = getFindPicOp().findPicCenter("同意.bmp", 225, 225);

			if (isPicFound(result)) {
				logger.info("在第" + (RERTY_LIMIT - retryTimes) + "次识别时时成功发现多账号待选");
				// 选定指定账号
				int accResult[] = findStr.findStrCenter(account, "DCAF12-233213", 205, 225);
				if (clickPictureIfFound(accResult)) {
					logger.info("成功选中账号" + account);
					sleep(500, TimeUnit.MILLISECONDS);
				} else {
					// 没有找到账户则报警后继续执行
					logger.error("没有找到指定账户" + account);
					beep("没有找到指定账户", 1, 0, 0);
				}
				clickPictureIfFound(result);
			}

			if (!done) {
				logger.info("在第" + (RERTY_LIMIT - retryTimes) + "次识别时没有登录成功");
			}

		}

		return done;
	}

	public boolean startGame(String email, String password) {
		return startGame(email, password, DEFAULT_ACCOUNT);
	}

	public boolean startGame(String email, String password, String account) {
		logger.info("开始启动游戏");

		new Thread(new Runnable() {
			public void run() {
				logger.info("游戏启动进程开始");
				// 打开记事本,此函数会阻塞当前线程，直到打开的关闭为止。故而须另开辟一个线程执行此函数
				SystemUtil.cmd("startWow.bat"); // PATH + "\\Wow-64.exe\"");
				logger.info("游戏启动进程结束");
			}
		}).start();

		int hwnd = findWindows(8);

		if (hwnd <= 0) {
			String msg = String.format("没有发现[%s]窗口，异常结束", WINDOW_TITLE);
			logger.error(msg);
			beep(1, 0, 0, 1);

			return false;
		}

		return login(hwnd, email, password, account);
	}

	/**
	 * 寻找魔兽世界窗口
	 * 
	 * @param window
	 * @param window
	 * @return
	 */
	private int findWindows(int retryTimes) {
		int hwnd = 0;
		int lodingRetry = 0;
		Window winOp = getWindowOp();
		while (hwnd <= 0 && lodingRetry < retryTimes) {
			sleep(5, TimeUnit.SECONDS);
			hwnd = winOp.findWindow(0, null, WINDOW_TITLE);
			lodingRetry++;
		}
		return hwnd;
	}

	public boolean returnRoleList() {

		boolean clicked = false;

		if (isWaitingRoleSelect()) {
			clicked = true;
		} else if (isPlaying()) {

			int retryCount = 5;
			while (!(clicked = clickPictureIfFound(getFindPicOp().findPicCenter("返回角色选择.bmp", 100, 160))) && retryCount-- > 0) {
				getKeyboardOp().escape();
				sleep(500, TimeUnit.MILLISECONDS);
			}

			// 返回角色选择画面是有可能要等待20秒
			int waitTime = 24;
			while (clicked && !isWaitingRoleSelect() && waitTime-- > 0) {
				sleep(1, TimeUnit.SECONDS);
			}

		}

		return clicked;
	}

	public boolean exitGame() {
		boolean clicked = false;
		if (isPlaying()) {
			int retryCount = 5;
			while (!(clicked = clickPictureIfFound(getFindPicOp().findPicCenter("退出游戏.bmp", 100, 160))) && retryCount-- > 0) {
				getKeyboardOp().escape();
				sleep(500, TimeUnit.MILLISECONDS);
			}

			sleep(500, TimeUnit.MILLISECONDS);
			clickPictureIfFound(getFindPicOp().findPicByIndex("立刻退出.bmp", 3, 2, 1));

		} else {
			clicked = true;
			// 退出到登录画面,再退出程序
			for (int i = 0; i < 5; i++) {
				getKeyboardOp().escape();
				sleep(500, TimeUnit.MILLISECONDS);
			}
		}
		return clicked;
	}

}
