package com.xutao.wow.ui;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class T4SettingPanel extends JPanel {
	private static final Logger logger = LogManager.getLogger(T4SettingPanel.class);

	private static final long serialVersionUID = -4369875422624765987L;

	private JLabel emailLable = new JLabel("邮件：");
	private JTextField emailField = new JTextField("43948294@qq.com", 10);
	private JLabel passwordLable = new JLabel("密码：");
	private JPasswordField passwordField = new JPasswordField("crazy2398");
	private JLabel accountLable = new JLabel("账户：");
	private JComboBox<String> accountField = new JComboBox<String>();

	public T4SettingPanel() {
		super(null);
		final int lableWidth = 80;
		final int inputHeight = 18;
		final int inputWidth = 200;
		final int initX = 10;
		final int initY = 10;
		final int gap = 5;
		emailLable.setBounds(initX, initY, lableWidth, inputHeight);
		emailField.setBounds(initX + lableWidth + gap, initY, inputWidth, inputHeight);
		passwordLable.setBounds(initX, initY + inputHeight + gap, lableWidth, inputHeight);
		passwordField.setBounds(initX + lableWidth + gap, initY + inputHeight + gap, inputWidth, inputHeight);
		accountLable.setBounds(initX, initY + (inputHeight + gap) * 2, lableWidth, inputHeight);
		accountField.setBounds(initX + lableWidth + gap, initY + (inputHeight + gap) * 2, inputWidth, inputHeight);

		this.add(emailLable);
		this.add(emailField);
		this.add(passwordLable);
		this.add(passwordField);
		this.add(accountLable);
		this.add(accountField);

		accountField.addItem("WOW1");
		accountField.addItem("WOW2");
		accountField.addItem("WOW3");
		accountField.setSelectedIndex(0);

		logger.debug(getEmail());
		logger.debug(getPassword());
		logger.debug(getAccount());
	}

	public String getAccount() {
		return accountField.getSelectedItem().toString();
	}

	public String getPassword() {
		return new String(passwordField.getPassword());
	}

	public String getEmail() {
		return emailField.getText();
	}
}
