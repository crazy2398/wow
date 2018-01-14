package com.xutao.games.recorder;

import java.awt.AWTException;
import java.io.File;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ScreenshotCatcherTest {
	private String savePath = "H:\\test\\" + RandomStringUtils.randomAlphanumeric(5);

	@Test
	public void test() throws AWTException, InterruptedException {

		final int LIMIT = 10;

		File f = new File(savePath);

		try (ScreenshotCatcher catcher = new ScreenshotCatcher(savePath, 128)) {

			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					catcher.capture();
				}
			};

			ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
			// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
			service.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
			Thread.sleep(LIMIT * 1000);
			service.shutdown();
			service.awaitTermination(2, TimeUnit.SECONDS);

			for (File ss : f.listFiles()) {
				System.out.println(ss.getName());
			}

			Assert.assertEquals(LIMIT, f.listFiles().length);
		}
	}

	@After
	@Before
	public void deleteFile() {
		File f = new File(savePath);
		if (f.exists()) {
			Assert.assertTrue(f.delete());
		}
	}
}
