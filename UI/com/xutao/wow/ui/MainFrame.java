package com.xutao.wow.ui;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

	/**
	 * 自动生成
	 */
	private static final long serialVersionUID = -5668579803046163172L;

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainFrame().setVisible(true);
			}
		});
	}

	/**
	 * 创建一个主窗体
	 */
	public MainFrame() {
		super();
		initFrame();
	}

	/**
	 * 设置窗体相关属性
	 */
	protected void initFrame() {
		super.setResizable(false);
		super.setTitle("WOW工具");
		super.setBounds(100, 100, 370, 525);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 加载标签页
		super.setContentPane(new TabbePanel());
	}

}
