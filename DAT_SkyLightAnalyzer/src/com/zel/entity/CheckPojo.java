package com.zel.entity;

import java.io.Serializable;

public class CheckPojo implements Serializable{
	private int frontIndex;

	public int getFrontIndex() {
		return frontIndex;
	}

	public void setFrontIndex(int frontIndex) {
		this.frontIndex = frontIndex;
	}

	public CheckPojo(int frontIndex) {
		this.frontIndex = frontIndex;
	}
}
