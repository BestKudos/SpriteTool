package com.applers.tool;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class PlacementPanel extends JPanel implements MouseListener,
		MouseMotionListener {

	Graphics graphic;
	protected BufferedImage backImg;
	protected ArrayList<ArrayList<BufferedImage>> pListSet = new ArrayList<ArrayList<BufferedImage>>();
	protected int pWidth, pHeight;
	ArrayList<Rectangle> box = new ArrayList<Rectangle>();
	// 마우스 드래그 체크
	int index = -1;
	protected int mIndex = 0;

	ArrayList<Integer> imgX = new ArrayList<Integer>();
	ArrayList<Integer> imgY = new ArrayList<Integer>();
	// 사각형영역
	int lineSize;

	ArrayList<Boolean> isDragged = new ArrayList<Boolean>();
	protected boolean moveTF, moveYN;

	int offX, offY, lNewX, lNewY, lOldX, lOldY;

	protected ArrayList<Boolean> pAniTF = new ArrayList<Boolean>();
	protected ArrayList<javax.swing.Timer> pTimerList = new ArrayList<javax.swing.Timer>();
	protected ArrayList<javax.swing.Timer> pMTimerList = new ArrayList<javax.swing.Timer>();
	protected ArrayList<Integer> frameSpeedList = new ArrayList<Integer>();
	protected ArrayList<Integer> aniIndexList = new ArrayList<Integer>();
	protected ArrayList<GraphicObject> graphicList = new ArrayList<PlacementPanel.GraphicObject>();

	public PlacementPanel(int w, int h) {
		setBounds(0, 0, w, h);
		setBackground(Color.WHITE);
		setBorder(new BevelBorder(BevelBorder.RAISED));
		setVisible(true);
		pWidth = w;
		pHeight = h;
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backImg, 0, 0, pWidth, pHeight, null);

		for (int i = 0; i <= index; i++) {
			graphicList.get(i).drawObject(g, imgX.get(i), imgY.get(i), i);
		}

		if (moveTF == true) {
			g.setColor(Color.RED);
			g.drawLine(
					graphicList.get(mIndex).moveX.get(graphicList.get(mIndex).moveX
							.size() - 1),
					graphicList.get(mIndex).moveY.get(graphicList.get(mIndex).moveY
							.size() - 1), lNewX, lNewY);
			for (int j = 0; j <= index; j++) {
				for (int i = 1; i < graphicList.get(j).moveX.size(); i++) {
					g.drawLine(graphicList.get(j).moveX.get(i - 1),
							graphicList.get(j).moveY.get(i - 1),
							graphicList.get(j).moveX.get(i),
							graphicList.get(j).moveY.get(i));
				}
			}
		}
	}

	public void update(Graphics g) {
		super.update(g); // 상위 update 메소드에서 paint 호출함.
	}

	public void addObjectNBox() {
		index++;
		imgX.add(0);
		imgY.add(0);

		graphicList.add(new GraphicObject());
		graphicList.get(index).setImgs(pListSet.get(index));

		// 사각형영역 정의(100x80 size)
		box.add(new Rectangle(0, 0, pListSet.get(index).get(0).getWidth(),
				pListSet.get(index).get(0).getHeight()));
		pTimerList.add(graphicList.get(index).objectTimer(
				frameSpeedList.get(index), pAniTF.get(index)));
		isDragged.add(false);
	}

	public void moveOver() { // 이미지를 화면 밖으로 나가지 못하게하는 메서드
		if (box.get(mIndex).x < 0) {
			box.get(mIndex).x = 0;
			imgX.set(mIndex, 0);
		}
		if (box.get(mIndex).y < 0) {
			box.get(mIndex).y = 0;
			imgY.set(mIndex, 0);
		}
		if (box.get(mIndex).x + box.get(mIndex).width >= getWidth()) {
			box.get(mIndex).x = getWidth() - box.get(mIndex).width - 1;
			imgX.set(mIndex, getWidth() - box.get(mIndex).width - 1);
		}
		if (box.get(mIndex).y + box.get(mIndex).height >= getHeight()) {
			box.get(mIndex).y = getHeight() - box.get(mIndex).height - 1;
			imgY.set(mIndex, getHeight() - box.get(mIndex).height - 1);
		}
	}

	public void searchBox(MouseEvent e) {
		for (int i = 0; i < box.size(); i++) {
			if (box.get(i).contains(new Point(e.getX(), e.getY())))
				mIndex = i;
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		searchBox(arg0);
		
		lNewX = imgX.get(mIndex);
		lNewY = imgY.get(mIndex);

		if (isDragged.get(mIndex)) {
			box.get(mIndex).x = arg0.getX() - offX;
			box.get(mIndex).y = arg0.getY() - offY;
			imgX.set(mIndex, arg0.getX() - offX);// 마우스가 누른 위치의 x좌료를
													// 넣는다.
			imgY.set(mIndex, arg0.getY() - offY);// 마우스가 누른 위치의 y좌표를
													// 넣는다.
			graphicList.get(mIndex).rectTF = true;
		}
		moveOver();
		repaint();// 그림을 다시 그린다.
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		searchBox(e);
		if (box.get(mIndex).contains(new Point(e.getX(), e.getY()))) {
			for (int i = 0; i < graphicList.size(); i++) {
				if (i != mIndex)
					graphicList.get(i).rectTF = false;
			}
			graphicList.get(mIndex).rectTF = true;
			repaint();
		} else {
			repaint();
		}
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
		searchBox(e);

		lOldX = imgX.get(mIndex);
		lOldY = imgY.get(mIndex);
		if (box.get(mIndex).contains(new Point(e.getX(), e.getY()))) {
			// #1 마우스 버튼 누름
			// 사각형내 마우스 클릭 상대 좌표를 구함
			// 현재 마우스 스크린 좌표에서 사각형 위치 좌표의 차이를 구함
			offX = e.getX() - box.get(mIndex).x;
			offY = e.getY() - box.get(mIndex).y;

			// 드래그 시작을 표시
			isDragged.set(mIndex, true);

			graphicList.get(mIndex).rectTF = true;

			if (moveTF == true) {
				if (graphicList.get(mIndex).moveX.size() == 0) {
					graphicList.get(mIndex).mTF = true;
					graphicList.get(mIndex).moveX.add(lOldX);
					graphicList.get(mIndex).moveY.add(lOldY);
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		// 마우스 버튼이 릴리즈되면 드래그 모드 종료
		isDragged.set(mIndex, false);
		graphicList.get(mIndex).rectTF = false;

		repaint();

		if (moveTF == true) {
			graphicList.get(mIndex).moveX.add(lNewX);
			graphicList.get(mIndex).moveY.add(lNewY);
		}
	}

	class GraphicObject {
		private int index = 0, moveIndex = 0;
		private int x, y;
		private ArrayList<BufferedImage> imgs = new ArrayList<BufferedImage>();
		private boolean rectTF, mTF = false;
		private ArrayList<Integer> moveX = new ArrayList<Integer>();
		private ArrayList<Integer> moveY = new ArrayList<Integer>();

		protected ArrayList<BufferedImage> getImgs() {
			return imgs;
		}

		protected void setImgs(ArrayList<BufferedImage> imgs) {
			this.imgs = imgs;
		}

		protected int getIndex() {
			return index;
		}

		protected void setIndex(int index) {
			this.index = index;
		}

		protected boolean isRectTF() {
			return rectTF;
		}

		protected void setRectTF(boolean rectTF) {
			this.rectTF = rectTF;
		}

		protected ArrayList<Integer> getMoveX() {
			return moveX;
		}

		protected ArrayList<Integer> getMoveY() {
			return moveY;
		}

		protected void setMoveX(ArrayList<Integer> moveX) {
			this.moveX = moveX;
		}

		protected void setMoveY(ArrayList<Integer> moveY) {
			this.moveY = moveY;
		}

		protected boolean ismTF() {
			return mTF;
		}

		protected void setMoveIndex(int moveIndex) {
			this.moveIndex = moveIndex;
		}

		protected double getYPoint(double x, double x1, double y1, double x2,
				double y2) {
			double y;
			y = ((y2 - y1) / (x2 - x1)) * (x - x1) + y1;
			return y;
		}

		protected double getXPoint(double y, double x1, double y1, double x2,
				double y2) {
			double x;
			x = ((x2 - x1) / (y2 - y1)) * (y - y1) + x1;
			return x;
		}

		protected javax.swing.Timer moveTimer(final int id, int delay,
				boolean tf) {
			if (tf == true) {
				ActionListener al = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int difX = moveX.get(moveIndex + 1)
								- moveX.get(moveIndex);
						int difY = moveY.get(moveIndex + 1)
								- moveY.get(moveIndex);
						int x1 = moveX.get(moveIndex);
						int y1 = moveY.get(moveIndex);
						int x2 = moveX.get(moveIndex + 1);
						int y2 = moveY.get(moveIndex + 1);
						int tempX = 0, tempY = 0;

						if (difX > 0) {
							if (difY < 0) {
								if (-difY <= difX) {
									tempX = imgX.get(id) + 1;
									tempY = (int) getYPoint((double) tempX,
											(double) x1, (double) y1,
											(double) x2, (double) y2);
								} else if (-difY > difX) {
									tempY = imgY.get(id) - 1;
									tempX = (int) getXPoint((double) tempY,
											(double) x1, (double) y1,
											(double) x2, (double) y2);
								}
							} else if (difY > 0) {
								if (difY <= difX) {
									tempX = imgX.get(id) + 1;
									tempY = (int) getYPoint((double) tempX,
											(double) x1, (double) y1,
											(double) x2, (double) y2);
								} else if (difY > difX) {
									tempY = imgY.get(id) + 1;
									tempX = (int) getXPoint((double) tempY,
											(double) x1, (double) y1,
											(double) x2, (double) y2);
								}
							}
							imgX.set(id, tempX);
							imgY.set(id, tempY);

							if (moveX.get(moveIndex + 1) == moveX.get(moveX
									.size() - 1)) {
								if (imgX.get(id) >= moveX.get(moveX.size() - 1)) {
									pMTimerList.get(id).stop();
									pTimerList.get(id).stop();
								}
							} else if (imgX.get(id) >= moveX.get(moveIndex + 1)) {
								moveIndex = moveIndex + 1;
							}
						} else if (difX < 0) {
							if (difY < 0) {
								if (-difY <= -difX) {
									tempX = imgX.get(id) - 1;
									tempY = (int) getYPoint((double) tempX,
											(double) x1, (double) y1,
											(double) x2, (double) y2);
								} else if (-difY > -difX) {
									tempY = imgY.get(id) - 1;
									tempX = (int) getXPoint((double) tempY,
											(double) x1, (double) y1,
											(double) x2, (double) y2);
								}
							} else if (difY > 0) {
								if (difY <= -difX) {
									tempX = imgX.get(id) - 1;
									tempY = (int) getYPoint((double) tempX,
											(double) x1, (double) y1,
											(double) x2, (double) y2);
								} else if (difY > -difX) {
									tempY = imgY.get(id) + 1;
									tempX = (int) getXPoint((double) tempY,
											(double) x1, (double) y1,
											(double) x2, (double) y2);
								}
							}
							imgX.set(id, tempX);
							imgY.set(id, tempY);

							if (moveX.get(moveIndex + 1) == moveX.get(moveX
									.size() - 1)) {
								if (imgX.get(id) <= moveX.get(moveX.size() - 1)) {
									pMTimerList.get(id).stop();
									pTimerList.get(id).stop();
								}
							} else if (imgX.get(id) <= moveX.get(moveIndex + 1)) {
								moveIndex = moveIndex + 1;
							}
						} else if (difX == 0) {
							if (difY > 0) {
								imgY.set(id, imgY.get(id) + 1);
								if (moveY.get(moveIndex + 1) == moveY.get(moveY
										.size() - 1)) {
									if (imgY.get(id) >= moveY
											.get(moveY.size() - 1)) {
										pMTimerList.get(id).stop();
										pTimerList.get(id).stop();
									}
								} else if (imgY.get(id) >= moveY
										.get(moveIndex + 1)) {
									moveIndex = moveIndex + 1;
								}
							}
							if (difY < 0) {
								imgY.set(id, imgY.get(id) - 1);
								if (moveY.get(moveIndex + 1) == moveY.get(moveY
										.size() - 1)) {
									if (imgY.get(id) <= moveY
											.get(moveY.size() - 1)) {
										pMTimerList.get(id).stop();
										pTimerList.get(id).stop();
									}
								} else if (imgY.get(id) <= moveY
										.get(moveIndex + 1)) {
									moveIndex = moveIndex + 1;
								}
							}
						} else if (difY == 0) {
							if (difX > 0) {
								imgX.set(id, imgX.get(id) + 1);
								if (moveX.get(moveIndex + 1) == moveX.get(moveX
										.size() - 1)) {
									if (imgX.get(id) >= moveX
											.get(moveX.size() - 1)) {
										pMTimerList.get(id).stop();
										pTimerList.get(id).stop();
									}
								} else if (imgX.get(id) >= moveX
										.get(moveIndex + 1)) {
									moveIndex = moveIndex + 1;
								}
							}
							if (difX < 0) {
								imgX.set(id, imgX.get(id) - 1);
								if (moveX.get(moveIndex + 1) == moveX.get(moveX
										.size() - 1)) {
									if (imgX.get(id) <= moveX
											.get(moveX.size() - 1)) {
										pMTimerList.get(id).stop();
										pTimerList.get(id).stop();
									}
								} else if (imgX.get(id) <= moveX
										.get(moveIndex + 1)) {
									moveIndex = moveIndex + 1;
								}
							}
						}
					}
				};
				javax.swing.Timer timer = new javax.swing.Timer(delay, al);
				return timer;
			} else {
				ActionListener al = null;
				javax.swing.Timer timer = new javax.swing.Timer(delay, al);
				return timer;
			}
		}

		protected javax.swing.Timer objectTimer(int delay, boolean tf) {
			if (tf == true) {
				ActionListener al = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (index < imgs.size()) {
							int _x = x - imgs.get(index).getWidth() / 2;
							int _y = y - imgs.get(index).getHeight() / 2;
							int w = imgs.get(index).getWidth() * 2;
							int h = imgs.get(index).getHeight() * 2;
							repaint(_x, _y, w, h);
							index++;
							if (index == imgs.size())
								index = 0;
						}

					}
				};
				javax.swing.Timer timer = new javax.swing.Timer(delay, al);
				return timer;
			} else {
				ActionListener al = null;
				javax.swing.Timer timer = new javax.swing.Timer(delay, al);
				return timer;
			}
		}

		protected void drawObject(Graphics g, int img_x, int img_y, int id) {
			x = img_x;
			y = img_y;

			g.drawImage(imgs.get(index), x, y, imgs.get(index).getWidth(), imgs
					.get(index).getHeight(), PlacementPanel.this);
			if (rectTF == true) {
				g.setColor(Color.blue);
				g.drawRect(box.get(id).x, box.get(id).y, box.get(id).width,
						box.get(id).height);
			}
		}
	}

}
