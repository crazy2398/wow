package com.xutao.wow.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xutao.wowmh.robot.Role;
import com.xutao.wowmh.robot.zone.Zone;

public class T1DungeonPanel extends JPanel {
	private static final Logger logger = LogManager.getLogger(T1DungeonPanel.class);

	private static final long serialVersionUID = 5579024700025055232L;

	private JLabel dungeonsLable = new JLabel("地下城：");
	private JComboBox<Zone> dungeonsCombo = new JComboBox<>();

	private JRadioButton onlyMaxedRedio = new JRadioButton("只选满级角色", true);
	private JRadioButton levelBetweenRedio = new JRadioButton("角色范围：", false);

	private JTextField levelFrom = new JTextField("20", 3);
	private JLabel hyphen = new JLabel("-");
	private JTextField levelTo = new JTextField(String.valueOf(Role.FULL_LEVEL), 3);

	private final Set<Zone> targetDungeons;

	public T1DungeonPanel() {
		super(null);

		TreeSet<Zone> set = new TreeSet<>();
		set.add(Zone.Dungeon.斯坦索姆);
		set.add(Zone.Dungeon.旋云之巅);
		set.add(Zone.Raid.风暴要塞);
		targetDungeons = Collections.unmodifiableSet(set);
		for (Zone dungeon : targetDungeons) {
			dungeonsCombo.addItem(dungeon);
			logger.debug("增加[" + dungeon + "]到界面选项。");
		}

		initLayout();
	}

	private void initLayout() {

		final int lableWidth = 80;
		final int inputHeight = 18;
		final int inputWidth = 200;
		final int initX = 10;
		final int initY = 10;
		final int gap = 5;
		// 初始化地下城下拉选
		dungeonsLable.setBounds(initX, initY, lableWidth, inputHeight);
		dungeonsCombo.setBounds(initX + lableWidth + gap, initY, inputWidth, inputHeight);
		this.add(dungeonsLable);
		this.add(dungeonsCombo);

		// 初始化等级单选按钮
		final int radioWidth = 120;
		int thisY = initY + (inputHeight + gap);
		onlyMaxedRedio.setBounds(initX, thisY, radioWidth, inputHeight);
		levelBetweenRedio.setBounds(initX + radioWidth + gap, thisY, radioWidth, inputHeight);
		ButtonGroup group = new ButtonGroup();
		group.add(onlyMaxedRedio);
		group.add(levelBetweenRedio);
		this.add(onlyMaxedRedio);
		this.add(levelBetweenRedio);
		ActionListener levelInputEnableListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				levelFrom.setEditable(!onlyMaxedRedio.isSelected());
				levelTo.setEditable(!onlyMaxedRedio.isSelected());
				logger.debug("单选：" + event.getActionCommand());
			}
		};
		onlyMaxedRedio.addActionListener(levelInputEnableListener);
		levelBetweenRedio.addActionListener(levelInputEnableListener);

		// 初始化等级范围
		final int levelInputWidth = 30;
		final int hyphenWidth = 10;
		int thisX = initX + (radioWidth + gap) * 2;
		levelFrom.setBounds(thisX, thisY, levelInputWidth, inputHeight);
		hyphen.setBounds(thisX + levelInputWidth + gap, thisY, hyphenWidth, inputHeight);
		levelTo.setBounds(thisX + hyphenWidth + levelInputWidth + gap, thisY, levelInputWidth, inputHeight);
		levelFrom.setEditable(false);
		levelTo.setEditable(false);
		this.add(levelFrom);
		this.add(hyphen);
		this.add(levelTo);
	}
}
