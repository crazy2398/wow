package com.xutao.wow.ui;

import java.awt.GridLayout;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** 标签页布局 */
public class TabbePanel extends JPanel {
	private static final Logger logger = LogManager.getLogger(TabbePanel.class);

	private static final long serialVersionUID = -343367257978865334L;
	/** 存放选项卡的组件 */
	private JTabbedPane jTabbedpane = new JTabbedPane();

	private ImageIcon icon = createImageIcon("images/middle.gif");

	private T4SettingPanel settingPanel = new T4SettingPanel();
	
	private T1DungeonPanel dungeonPanel= new T1DungeonPanel();
	
	/** 存放卡片和布局的数据 */
	private final Map<String, JPanel> tabContents;

	public TabbePanel() {
		TreeMap<String, JPanel> setting = new TreeMap<>();
		setting.put("副本", dungeonPanel);
		setting.put("任务", new JPanel());
		setting.put("技能", new JPanel());
		setting.put("设置", settingPanel);
		tabContents = Collections.unmodifiableMap(setting);

		layoutComponents(tabContents);
		jTabbedpane.setSelectedIndex(3);
	}

	private void layoutComponents(Map<String, JPanel> setting) {
		for (Map.Entry<String, JPanel> e : setting.entrySet()) {
			jTabbedpane.addTab(e.getKey(), icon, e.getValue());
		}
		setLayout(new GridLayout(1, 1));
		add(jTabbedpane);
	}

	/** 装载图标 */
	private ImageIcon createImageIcon(String path) {
		URL url = TabbePanel.class.getResource(path);
		if (url == null) {
			logger.error("the image " + path + " is not exist!");
			return null;
		}
		return new ImageIcon(url);
	}

}
