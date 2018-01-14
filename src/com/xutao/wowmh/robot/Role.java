package com.xutao.wowmh.robot;

import java.awt.Point;

public class Role {
	private final String name;
	private final Profession profession;
	private final Phyle Phyle;
	private int level;
	private String zone;

	private Point screenPosition;
	
	public Role(String name, Profession profession, Phyle phyle, int level) {
		this.name = name;
		this.profession = profession;
		this.Phyle = phyle;
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

	public Profession getProfession() {
		return profession;
	}

	public Phyle getPhyle() {
		return Phyle;
	}

	public Point getScreenPosition() {
		return screenPosition;
	}

	public void setScreenPosition(Point screenPosition) {
		this.screenPosition = screenPosition;
	}

}