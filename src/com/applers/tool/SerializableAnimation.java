package com.applers.tool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

public class SerializableAnimation implements Serializable{
	protected String sheetPath;
	protected ArrayList<Integer[]> coordinate;
	protected int frameSpeed;
	protected int invisibleRGB;

	public SerializableAnimation(String path, ArrayList<Integer[]> coor, int f, int rgb) {
		sheetPath = path;
		coordinate = coor;
		frameSpeed = f;
		invisibleRGB = rgb;
	}
}
