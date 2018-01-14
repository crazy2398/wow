package com.xutao.games.recorder;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 屏幕截图的捉获器
 * 
 * @author xutao
 *
 */
public class ScreenshotCatcher implements Closeable {
	private static final Logger logger = LogManager.getLogger(ScreenshotCatcher.class);

	private static final String FORMAT = "bmp";

	/** 是否会在获得图片后马上写 */
	// private boolean writeImmediately = false;

	/** BlockingQueue的大小 */
	private final int bufferSize;

	/** 文件保存位置 */
	private final String path;

	protected final Robot robot;

	protected final Rectangle screenRectangle;

	private final BlockingQueue<Screenshot> buffer;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");

	private final Thread writeDemon;

	/**
	 * 构造函数
	 * 
	 */
	public ScreenshotCatcher(String path, int bufferSize) {
		this.path = path;
		this.bufferSize = bufferSize;
		try {
			this.robot = new Robot();
		} catch (AWTException e) {
			throw new Error("Robot初始化異常", e);
		}
		this.screenRectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

		this.buffer = new ArrayBlockingQueue<>(this.bufferSize);

		writeDemon = new Thread() {
			public void run() {
				try {
					while (true) {
						Screenshot ss = buffer.take();
						// 如果路径不存在,则创建
						if (!ss.getFile().getParentFile().exists() && !ss.getFile().isDirectory()) {
							ss.getFile().getParentFile().mkdirs();
						}

						if (!ImageIO.write(ss.getImage(), FORMAT, ss.getFile())) {
							logger.error("文件" + ss.getFile().getName() + "没有被写入磁盘!-----------------------------------------------------------------");
						}
					}

				} catch (InterruptedException e) {
					logger.error("写demo被终结");
				} catch (Exception e) {
					logger.error("写demo被终结");
					e.printStackTrace();
				}
			}
		};
		writeDemon.start();
	}

	/**
	 * 捕捉一个全屏截图
	 * 
	 * @return
	 */
	public void capture() {
		capture(screenRectangle);
	}

	public void capture(Rectangle r) {
		saveScreenshot(robot.createScreenCapture(r));
	}

	public void capture(int startX, int startY, int endX, int endY) {

		int minX = Math.min(startX, endX);
		int minY = Math.min(startY, endY);

		Rectangle r = new Rectangle(minX, minY, Math.abs(startX - endX), Math.abs(startY - endY));

		capture(r);
	}

	public void capture(Point p, int w, int h) {
		Rectangle r = new Rectangle(p, new Dimension(w, h));
		capture(r);
	}

	private void saveScreenshot(BufferedImage image) {

		final File screenFile = new File(path + "\\" + sdf.format(new Date()) + "." + FORMAT);

		final Screenshot ss = new Screenshot(image, screenFile);

		new Thread() {
			public void run() {
				try {
					buffer.put(ss);
				} catch (Exception e) {
					logger.error(screenFile.getName() + "的文件没有被输出!-----------------------------------------------------------------", e);
				}
			}
		}.start();

	}

	@Override
	public void close() {
		buffer.clear();
		writeDemon.interrupt();
	}
}
