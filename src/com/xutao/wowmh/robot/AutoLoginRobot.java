package com.xutao.wowmh.robot;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xnx3.microsoft.FindPic;
import com.xnx3.microsoft.FindStr;
import com.xnx3.microsoft.SystemUtil;
import com.xnx3.microsoft.Window;
import com.xutao.wowmh.core.ComWrapper;
import com.xutao.wowmh.op.KeyboardOperation;

public class AutoLoginRobot extends AbstractRobot {

	private static final Logger logger = LogManager.getLogger(AutoLoginRobot.class);


	public AutoLoginRobot(ComWrapper com) {
		super(com);
		// 登录时的账号识别字典
		com.setDict(ComWrapper.ACCOUNT_DICT, "wow.txt");
	}
	
	private static final String WINDOW_TITLE = "魔兽世界";

	public boolean login(String email, String password) {
		return login(email, password, "WoW1");
	}

	// TODO 尚未做选服务器处理
	public boolean login(String email, String password, String account) {

		logger.info("游戏启动机器人开始");
		
		new Thread(new Runnable() {
			public void run() {
				logger.info("游戏启动进程开始");
				// 打开记事本,此函数会阻塞当前线程，直到打开的关闭为止。故而须另开辟一个线程执行此函数
				SystemUtil.cmd("startWow.bat"); //PATH + "\\Wow-64.exe\"");
				logger.info("游戏启动进程结束");
			}
		}).start();

		int hwnd = findWindows(4);

		if (hwnd <= 0) {
			String msg = String.format("没有发现[%s]窗口，异常结束", WINDOW_TITLE);
			logger.error(msg);
			beep(1, 0, 0, 1);

			return false;
		}

		Window winOp = getWindowOp();

		KeyboardOperation kbOp = getKeyboardOp();

		// 输入用户名
		winOp.sendString(hwnd, email);
		sleep(500, TimeUnit.MILLISECONDS);

		kbOp.tab();
		sleep(500, TimeUnit.MILLISECONDS);

		// 输入密码
		winOp.sendString(hwnd, password);
		sleep(500, TimeUnit.MILLISECONDS);

		kbOp.enter();

		// 开始找图以判断是否登陆成功
		int centerX = getSystemUtilOp().getScreenWidth() / 2;

		int centerY = getSystemUtilOp().getScreenHeight() / 2;

		FindPic picOp = new FindPic(getActiveXComponent());
		FindStr findStr = new FindStr(getActiveXComponent());

		int retryTimes = 4;

		boolean done = false;

		while (retryTimes-- > 0) {
			sleep(5, TimeUnit.SECONDS);
			done = picOp.findPicExist(centerX * 2 - 300, 0, centerX * 2, 100, "选择服务器.bmp", "000000", 0.9, 1);

			if (done) {
				logger.info("在第" +(4 - retryTimes + 1) + "次尝试时成功登入");
				break;
			}

			if (picOp.findPicExist(centerX - 100, centerY - 100, centerX + 100, centerY + 100, "确定.bmp", "000000", 0.9, 1)) {
				// 发现了有提示就报错，退出
				logger.error("登陆过程中出现服务器报错");
				beep(0, 0, 0, 0, 0);
				break;
			}
			// 查看是否是因为多帐户待选导致的
			int[] result = picOp.findPic(centerX - 205, centerY, centerX + 205, centerY + 225, "同意.bmp", "000000", 0.9, 1);

			if (result[0] >= 0) {
				logger.info("在第" +(4 - retryTimes + 1) + "次尝试时成功发现多账号待选");
				// 选定指定账号
				int accResult[] = findStr.findStrE(centerX - 205, centerY - 225, centerX + 205, centerY + 225, account, "ffc700-000000", 0.9,
								ComWrapper.ACCOUNT_DICT);
				if (accResult[0] >= 0) {
					logger.info("成功选中账号" + account);
					getMouseOp().mouseMoveTo(accResult[1] + 5, accResult[2] + 5);
					getMouseOp().mouseClick(true);
					sleep(500, TimeUnit.MILLISECONDS);
				} else {
					// 没有找到账户则报警后继续执行
					logger.error("没有找到指定账户" + account);
					beep(1, 0, 0);
					break;
				}

				getMouseOp().mouseMoveTo(result[1] + 5, result[2] + 5);
				getMouseOp().mouseClick(true);
			}

		}

		logger.info("游戏启动机器人结束");
		return done;
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

}
