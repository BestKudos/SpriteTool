package com.applers.tool;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JPanel;

public class AnimationPanel extends JPanel {

	protected ArrayList<Image> frames;
	protected int index;
	private int imgWidth, imgHeight;

	public AnimationPanel(ArrayList<Image> imgs) {
		frames = imgs;
		index = 0;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		imgWidth = frames.get(index).getWidth(null);
		imgHeight = frames.get(index).getHeight(null);

		g.drawImage(frames.get(index), 10, 10, imgWidth, imgHeight, null);
	}

}
