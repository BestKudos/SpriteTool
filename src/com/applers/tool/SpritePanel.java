package com.applers.tool;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class SpritePanel extends JPanel implements MouseListener,
		MouseMotionListener {

	protected BufferedImage spriteSheet, copySheet;
	private int x, y, initSheetWidth, initSheetHeight, newRectX, newRectY,
			newRectW, newRectH;
	protected int rgb, totalSheetWidth, totalSheetHeight, rX0, rY0, rX1, rY1, rW, rH;
	protected float erMagnification, beforeMag;
	protected boolean cSelectionTF, magTF;
	private int cX0, cY0, cX1, cY1;
	protected Rectangle r;
	protected Integer[] sData;
	protected int index;

	public SpritePanel(BufferedImage ss, int x, int y, int w, int h) {
		spriteSheet = ss;
		copySheet = ss;
		this.x = x;
		this.y = y;
		initSheetWidth = spriteSheet.getWidth();
		initSheetHeight = spriteSheet.getHeight();
		totalSheetWidth = initSheetWidth;
		totalSheetHeight = initSheetHeight;
		setBounds(x, y, w, h);
		beforeMag = 1.0f;
		erMagnification = 1.0f;
		addMouseListener(this);
		addMouseMotionListener(this);
		index = 0;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(spriteSheet, x, y, totalSheetWidth, totalSheetHeight, null);
		if (!magTF) {
			g.drawRect(rX0, rY0, rW, rH);
		} else {
			newRectX = (int) ((rX0 / beforeMag) * erMagnification);
			newRectY = (int) ((rY0 / beforeMag) * erMagnification);
			newRectW = (int) ((rW / beforeMag) * erMagnification);
			newRectH = (int) ((rH / beforeMag) * erMagnification);

			g.drawRect(newRectX, newRectY, newRectW, newRectH);
		}

		r = new Rectangle(x, y, totalSheetWidth, totalSheetHeight);
	}

	public int scanLeft(int x0, int y0, int x1, int y1) {
		int color;

		for (int i = x0; i <= x1; i++) {
			for (int j = y0; j <= y1; j++) {
				color = copySheet.getRGB(i, j);
				if (color != rgb)
					return i;
			}
		}
		return -1;
	}

	public int scanRight(int x0, int y0, int x1, int y1) {
		int color;

		for (int i = x1; i >= x0; i--) {
			for (int j = y0; j <= y1; j++) {
				color = copySheet.getRGB(i, j);
				if (color != rgb)
					return i;
			}
		}
		return -1;
	}

	public int scanUp(int x0, int y0, int x1, int y1) {
		int color;

		for (int j = y0; j <= y1; j++) {
			for (int i = x0; i <= x1; i++) {
				color = copySheet.getRGB(i, j);
				if (color != rgb)
					return j;
			}
		}
		return -1;
	}

	public int scanDown(int x0, int y0, int x1, int y1) {
		int color;

		for (int j = y1; j >= y0; j--) {
			for (int i = x0; i <= x1; i++) {
				color = copySheet.getRGB(i, j);
				if (color != rgb)
					return j;
			}
		}
		return -1;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if (r.contains(e.getPoint())) {
			if (cSelectionTF) {
				rX0 = cX0;
				rY0 = cY0;
				rW = e.getX() - cX0;
				rH = e.getY() - cY0;
				repaint();
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (r.contains(e.getPoint())) {
			if (cSelectionTF) {
				cX0 = e.getX();
				cY0 = e.getY();

				magTF = false;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (r.contains(e.getPoint())) {
			if (cSelectionTF) {
				cX1 = e.getX();
				cY1 = e.getY();

				if (erMagnification == 1.0f) {
					rX0 = scanLeft(cX0, cY0, cX1, cY1);
					rY0 = scanUp(cX0, cY0, cX1, cY1);
					rX1 = scanRight(cX0, cY0, cX1, cY1);
					rY1 = scanDown(cX0, cY0, cX1, cY1);
					
					Integer[] temp = {index, rX0, rY0, rX1, rY1};
					sData = temp;
					
					rW = rX1 - rX0;
					rH = rY1 - rY0;
				} else if(erMagnification > 1.0f || erMagnification < 1.0f){
					cX0 = (int) (cX0 / erMagnification);
					cY0 = (int) (cY0 / erMagnification);
					cX1 = (int) (cX1 / erMagnification);
					cY1 = (int) (cY1 / erMagnification);
					
					rX0 = scanLeft(cX0, cY0, cX1, cY1);
					rY0 = scanUp(cX0, cY0, cX1, cY1);
					rX1 = scanRight(cX0, cY0, cX1, cY1);
					rY1 = scanDown(cX0, cY0, cX1, cY1);

					Integer[] temp = {index, rX0, rY0, rX1, rY1};
					sData = temp;
					
					rX0 = (int) (rX0 * erMagnification);
					rY0 = (int) (rY0 * erMagnification);
					rX1 = (int) (rX1 * erMagnification);
					rY1 = (int) (rY1 * erMagnification);
					rW = rX1 - rX0;
					rH = rY1 - rY0;
				}

				repaint();

				beforeMag = erMagnification;
			}
		}
	}

}
