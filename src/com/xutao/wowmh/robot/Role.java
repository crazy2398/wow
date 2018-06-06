package com.xutao.wowmh.robot;

import java.awt.Point;

import com.xutao.wowmh.concept.role.Classes;
import com.xutao.wowmh.concept.role.Race;

public class Role {
	private final String name;
	private final Classes wowClass;
	private final Race race;
	private int level;
	private String zone;

	private Point screenPosition;
	
	public Role(String name, Classes profession, Race race, int level) {
		this.name = name;
		this.wowClass = profession;
		this.race = race;
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getName() {
		return name;
	}

	public Classes getWowClass() {
		return wowClass;
	}

	public Race getRace() {
		return race;
	}

	public Point getScreenPosition() {
		return screenPosition;
	}

	public void setScreenPosition(Point screenPosition) {
		this.screenPosition = screenPosition;
	}

}