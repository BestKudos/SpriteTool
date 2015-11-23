package com.applers.tool;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class Main implements ActionListener, FocusListener, MouseListener,
		MouseMotionListener {

	private final static int TOLERANCE = 50;
	private JFrame frame;
	private String toolName = "SpriteTool";
	private String titleSupport = "    ―    ";
	private boolean newFrameEnable[] = new boolean[3];
	private JTextField invisibleTextField, textField_1, textField_2,
			frameTextField;
	private JTable cPropertiesTable;
	private JTable table_1, table_2;
	private JButton importButton, importCancelButton;
	private JTabbedPane fuctionTab, actionTab;
	private JMenuItem newProject, proSave, open, close, information, aniSave,
			placeSave;
	private JInternalFrame newFrame;
	private JButton newFrameConfirm, newFrameCancel;
	private JFormattedTextField titleTextField, heightTextField,
			widthTextField;
	private String interName, interWidth, interHeight;
	private JList sList, imgAnilist;
	private BufferedImage sheetImg, backImg;
	private JScrollPane aCutScroll, fCutScroll, aAnimationScroll;
	private Boolean invisibleSelectionTF;
	private int initSheetWidth, initSheetHeight;
	private int totalSheetWidth, totalSheetHeight;
	private JButton erConfirmButton;
	private JButton invisibleSelectionButton;
	private JButton cSelectionButton, cRegistrationButton, cInsertionButton,
			cDeletionButton;
	private SpritePanel spritePanel;
	private JTextField erTextField;
	private String erPercent = " %", erMagnification = "100";
	private DefaultTableModel sTableModel1 = new DefaultTableModel();
	private DefaultTableModel sTableModel2 = new DefaultTableModel();
	private Map<Object, ImageIcon> icons = new HashMap<Object, ImageIcon>();
	private ArrayList<ImageIcon> spriteList = new ArrayList<ImageIcon>();
	private ArrayList<String> sNameList = new ArrayList<String>();
	private ArrayList<Image> imgList = new ArrayList<Image>();
	private JPanel aAnimationPanel, aPlacementPanel;
	private AnimationPanel animPanel;
	private JButton aniPlayButton, aniPauseButton, frameConfirmButton;
	private JButton fBackImgImportButton, fBackImgCancelButton,
			fImgImportButton, fImgCancelButton, fAniImportButton,
			fAniCancelButton;
	private Timer timer;
	private int I_SpeedValue, invisibleRGB;
	private ActionListener animate;
	private boolean aniTF = true;
	private PlacementPanel pPanel;
	private int index = -1;
	protected ArrayList<ImageIcon> iconList = new ArrayList<ImageIcon>();
	protected ArrayList<String> nameList = new ArrayList<String>();
	protected ArrayList<ArrayList<BufferedImage>> selectList = new ArrayList<ArrayList<BufferedImage>>();
	private JTable table;
	private String sheetPath;
	private SerializableAnimation loadAni;
	private String lSheetPath;
	private int lInviRGB;
	private ArrayList<Integer[]> lCoordinate = new ArrayList<Integer[]>();
	private ArrayList<Integer> lFrameSpeed = new ArrayList<Integer>();
	private ArrayList<Integer> sFrameSpeed = new ArrayList<Integer>();
	private ArrayList<BufferedImage> sheetList = new ArrayList<BufferedImage>();
	private int aniListIndex = 0;
	private ArrayList<ArrayList<BufferedImage>> animationList = new ArrayList<ArrayList<BufferedImage>>();
	private ArrayList<Integer[]> sIntegerList = new ArrayList<Integer[]>();
	private JButton pAniPlayButton, pAniPauseButton, mRegisterButton,
			mCancelButton;
	private JToggleButton mModeButton;
	private ArrayList<Integer> aniIndexList = new ArrayList<Integer>();
	private ArrayList<Boolean> timerTF = new ArrayList<Boolean>();
	private boolean aniDecision = true, moveDecision;

	public static void main(String[] args) {
		Main window = new Main();
		window.frame.setVisible(true);
	}

	public Main() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle(toolName);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(0, 0, d.width, d.height - 45);
		/*
		 * // ///////////////////// Splash Screen //////////////////// JWindow
		 * window = new JWindow(); window.getContentPane().add( new JLabel("",
		 * new ImageIcon("applers.png"), SwingConstants.CENTER));
		 * window.setBounds(d.width / 2 - 324, d.height / 2 - 109, 648, 218);
		 * window.setVisible(true); try { Thread.sleep(3000); } catch
		 * (InterruptedException e) { e.printStackTrace(); }
		 * window.setVisible(false); window.dispose(); //
		 * ///////////////////////////////////////////////////////
		 */
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu file = new JMenu("파일");
		menuBar.add(file);

		newProject = new JMenuItem("새 프로젝트");
		newProject.addActionListener(this);
		file.add(newProject);

		open = new JMenuItem("프로젝트 열기");
		file.add(open);
		open.addActionListener(this);

		proSave = new JMenuItem("프로젝트 저장");
		file.add(proSave);
		proSave.addActionListener(this);

		aniSave = new JMenuItem("애니메이션 저장");
		file.add(aniSave);
		aniSave.addActionListener(this);

		placeSave = new JMenuItem("배치 저장");
		file.add(placeSave);
		placeSave.addActionListener(this);

		close = new JMenuItem("프로젝트 닫기");
		file.add(close);
		close.addActionListener(this);

		JMenu help = new JMenu("도움말");
		menuBar.add(help);

		information = new JMenuItem("정보");
		help.add(information);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		newFrame = new JInternalFrame("새 프로젝트");
		newFrame.setVisible(false);
		newFrame.setBounds(650, 200, 700, 350);
		frame.getContentPane().add(newFrame);
		newFrame.getContentPane().setLayout(null);

		JLabel newFrameLabel1 = new JLabel("\uD0C0\uC774\uD2C0");
		newFrameLabel1.setFont(new Font("굴림", Font.BOLD, 15));
		newFrameLabel1.setBounds(50, 49, 108, 25);
		newFrame.getContentPane().add(newFrameLabel1);

		JPanel sizeInputPanel = new JPanel();
		sizeInputPanel.setBorder(new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null),
				"\uC791\uC5C5 \uD574\uC0C1\uB3C4", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		sizeInputPanel.setBounds(25, 90, 634, 139);
		newFrame.getContentPane().add(sizeInputPanel);
		sizeInputPanel.setLayout(null);

		JLabel newFrameLabel2 = new JLabel("\uB108\uBE44");
		newFrameLabel2.setBounds(28, 59, 108, 25);
		sizeInputPanel.add(newFrameLabel2);
		newFrameLabel2.setFont(new Font("굴림", Font.BOLD, 15));

		JLabel newFrameLabel3 = new JLabel("\uB192\uC774");
		newFrameLabel3.setBounds(28, 96, 108, 25);
		sizeInputPanel.add(newFrameLabel3);
		newFrameLabel3.setFont(new Font("굴림", Font.BOLD, 15));

		JLabel explain = new JLabel(
				"####  \uC2A4\uB9C8\uD2B8\uD3F0 \uD06C\uAE30\uB97C \uC785\uB825\uD558\uBA74 \uB429\uB2C8\uB2E4.  ####");
		explain.setHorizontalAlignment(SwingConstants.CENTER);
		explain.setBounds(14, 24, 606, 18);
		sizeInputPanel.add(explain);

		widthTextField = new JFormattedTextField();
		widthTextField.setBounds(163, 59, 349, 24);
		sizeInputPanel.add(widthTextField);
		widthTextField.addFocusListener(this);

		heightTextField = new JFormattedTextField();
		heightTextField.setBounds(163, 96, 349, 24);
		sizeInputPanel.add(heightTextField);
		heightTextField.addFocusListener(this);

		titleTextField = new JFormattedTextField();
		titleTextField.setBounds(187, 49, 349, 24);
		newFrame.getContentPane().add(titleTextField);
		titleTextField.addFocusListener(this);

		newFrameConfirm = new JButton("\uD655\uC778");
		newFrameConfirm.setEnabled(false);
		newFrameConfirm.setBounds(170, 258, 105, 27);
		newFrame.getContentPane().add(newFrameConfirm);
		newFrameConfirm.addActionListener(this);

		newFrameCancel = new JButton("\uCDE8\uC18C");
		newFrameCancel.setBounds(431, 258, 105, 27);
		newFrame.getContentPane().add(newFrameCancel);
		newFrameCancel.addActionListener(this);

		JSplitPane splitPane = new JSplitPane();
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));

		sList = new JList();
		sList.setVisibleRowCount(-1);
		sList.setSelectedIndex(0);
		sList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPane = new JScrollPane(sList);
		scrollPane.setPreferredSize(new Dimension(240, 800));
		scrollPane.setMinimumSize(new Dimension(240, 800));
		panel.add(scrollPane, BorderLayout.CENTER);

		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setResizeWeight(0.9);
		splitPane.setRightComponent(splitPane_1);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);

		JPanel panel_1 = new JPanel();
		splitPane_1.setRightComponent(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setPreferredSize(new Dimension(1280, 150));
		panel_1.add(scrollPane_1);

		imgAnilist = new JList();
		imgAnilist.setMinimumSize(new Dimension(1280, 150));
		imgAnilist.setVisibleRowCount(-1);
		imgAnilist.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		imgAnilist.addMouseListener(this);
		imgAnilist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(imgAnilist);

		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setResizeWeight(1.0);
		splitPane_1.setLeftComponent(splitPane_2);

		JPanel panel_3 = new JPanel();
		panel_3.setMinimumSize(new Dimension(new Dimension(420, 700)));
		splitPane_2.setRightComponent(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));

		fuctionTab = new JTabbedPane(SwingConstants.LEFT);
		fuctionTab.setMinimumSize(new Dimension(400, 700));
		fuctionTab.setFont(new Font("굴림", Font.BOLD, 16));
		fuctionTab.setVisible(false);
		panel_3.add(fuctionTab, BorderLayout.CENTER);
		fuctionTab.addMouseListener(this);
		fuctionTab.addMouseMotionListener(this);

		fCutScroll = new JScrollPane();
		fuctionTab.addTab("<html>자<br>르<br>기<br><br>탭", null, fCutScroll, null);
		fuctionTab.setBackgroundAt(0, UIManager.getColor("Button.background"));

		JPanel fCutPanel = new JPanel();
		fCutPanel.setPreferredSize(new Dimension(400, 500));
		fCutScroll.setViewportView(fCutPanel);
		fCutPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("확대 및 축소");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("굴림", Font.BOLD, 15));
		lblNewLabel.setBounds(14, 57, 94, 18);
		fCutPanel.add(lblNewLabel);

		erConfirmButton = new JButton("\uD655\uC778");
		erConfirmButton.setBounds(209, 53, 105, 27);
		fCutPanel.add(erConfirmButton);
		erConfirmButton.addActionListener(this);

		JLabel lblNewLabel_1 = new JLabel("투명 컬러값");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 15));
		lblNewLabel_1.setBounds(14, 101, 94, 26);
		lblNewLabel_1.setBorder(new EtchedBorder(EtchedBorder.RAISED, null,
				null));
		fCutPanel.add(lblNewLabel_1);

		invisibleTextField = new JTextField();
		invisibleTextField.setBackground(Color.WHITE);
		invisibleTextField.setEditable(false);
		invisibleTextField.setBounds(14, 139, 167, 24);
		fCutPanel.add(invisibleTextField);
		invisibleTextField.setColumns(10);

		invisibleSelectionButton = new JButton("\uC120\uD0DD");
		invisibleSelectionButton.setBounds(209, 138, 105, 27);
		fCutPanel.add(invisibleSelectionButton);
		invisibleSelectionButton.addActionListener(this);

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_4.setBounds(14, 214, 343, 310);
		fCutPanel.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));

		JPanel panel_6 = new JPanel();
		panel_4.add(panel_6, BorderLayout.SOUTH);

		cSelectionButton = new JButton("\uC120\uD0DD");
		panel_6.add(cSelectionButton);
		cSelectionButton.addActionListener(this);

		cRegistrationButton = new JButton("\uB4F1\uB85D");
		panel_6.add(cRegistrationButton);
		cRegistrationButton.addActionListener(this);

		cInsertionButton = new JButton("\uC0BD\uC785");
		panel_6.add(cInsertionButton);

		cDeletionButton = new JButton("\uC0AD\uC81C");
		panel_6.add(cDeletionButton);

		JScrollPane scrollPane_8 = new JScrollPane();
		panel_4.add(scrollPane_8, BorderLayout.CENTER);

		sTableModel1.setColumnIdentifiers(new String[] { "순번", "x0", "y0",
				"x1", "y1" });

		cPropertiesTable = new JTable(sTableModel1);
		cPropertiesTable.setCellEditor(null);
		cPropertiesTable.getAutoResizeMode();
		scrollPane_8.setViewportView(cPropertiesTable);

		JLabel lblNewLabel_2 = new JLabel("자른 영역 속성표");
		lblNewLabel_2.setFont(new Font("굴림", Font.BOLD, 15));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBorder(new EtchedBorder(EtchedBorder.RAISED, null,
				null));
		lblNewLabel_2.setBounds(14, 187, 143, 26);
		fCutPanel.add(lblNewLabel_2);

		JLabel label_3 = new JLabel(
				"\uC2A4\uD504\uB77C\uC774\uD2B8 \uC2DC\uD2B8");
		label_3.setFont(new Font("굴림", Font.BOLD, 15));
		label_3.setBounds(14, 16, 116, 18);
		fCutPanel.add(label_3);

		importButton = new JButton("\uBD88\uB7EC\uC624\uAE30");
		importButton.setBounds(133, 12, 105, 27);
		fCutPanel.add(importButton);
		importButton.addActionListener(this);

		importCancelButton = new JButton("\uC0AD\uC81C");
		importCancelButton.setBounds(252, 12, 105, 27);
		fCutPanel.add(importCancelButton);
		importCancelButton.addActionListener(this);

		erTextField = new JTextField();
		erTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		erTextField.setBounds(122, 54, 70, 24);
		erTextField.setColumns(10);
		erTextField.setText(erMagnification + erPercent);
		fCutPanel.add(erTextField);

		JScrollPane fCenterPositiontScroll = new JScrollPane();
		fuctionTab.addTab("<html>중<br>심<br>좌<br>표<br><br>탭", null,
				fCenterPositiontScroll, null);

		JPanel fCenterPositionPanel = new JPanel();
		fCenterPositionPanel.setPreferredSize(new Dimension(400, 500));
		fCenterPositiontScroll.setViewportView(fCenterPositionPanel);
		fCenterPositionPanel.setLayout(null);

		JLabel label = new JLabel("스프라이트 좌표");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("굴림", Font.BOLD, 15));
		label.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		label.setBounds(14, 12, 143, 26);
		fCenterPositionPanel.add(label);

		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_7.setBounds(14, 39, 343, 310);
		fCenterPositionPanel.add(panel_7);
		panel_7.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_9 = new JScrollPane();
		panel_7.add(scrollPane_9, BorderLayout.CENTER);

		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				"\uC21C\uBC88", "x0", "y0", "x1", "y1" }) {
			Class[] columnTypes = new Class[] { Integer.class, Integer.class,
					Integer.class, Integer.class, Integer.class };

			@Override
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		scrollPane_9.setViewportView(table_1);

		JPanel panel_8 = new JPanel();
		panel_7.add(panel_8, BorderLayout.SOUTH);

		JButton btnNewButton_7 = new JButton("확대");
		panel_8.add(btnNewButton_7);

		JButton btnNewButton_8 = new JButton("축소");
		panel_8.add(btnNewButton_8);

		JLabel lblNewLabel_3 = new JLabel(
				"\uC911\uC2EC\uC88C\uD45C \uC124\uC815");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBorder(new EtchedBorder(EtchedBorder.RAISED, null,
				null));
		lblNewLabel_3.setFont(new Font("굴림", Font.BOLD, 15));
		lblNewLabel_3.setBounds(14, 361, 131, 26);
		fCenterPositionPanel.add(lblNewLabel_3);

		JButton btnNewButton_9 = new JButton("중심 좌표");
		btnNewButton_9.setBounds(14, 393, 343, 27);
		fCenterPositionPanel.add(btnNewButton_9);

		textField_1 = new JTextField();
		textField_1.setBackground(Color.WHITE);
		textField_1.setEditable(false);
		textField_1.setBounds(14, 432, 116, 24);
		fCenterPositionPanel.add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setBackground(Color.WHITE);
		textField_2.setEditable(false);
		textField_2.setBounds(144, 432, 116, 24);
		fCenterPositionPanel.add(textField_2);
		textField_2.setColumns(10);

		JButton btnNewButton_10 = new JButton("설정");
		btnNewButton_10.setBounds(274, 432, 83, 27);
		fCenterPositionPanel.add(btnNewButton_10);

		JScrollPane fAnimationScroll = new JScrollPane();
		fuctionTab.addTab("<html>애<br>니<br>메<br>이<br>션<br><br>탭", null,
				fAnimationScroll, null);

		JPanel animationPanel = new JPanel();
		animationPanel.setPreferredSize(new Dimension(400, 650));
		fAnimationScroll.setViewportView(animationPanel);
		animationPanel.setLayout(null);

		JLabel lblNewLabel_4 = new JLabel("애니메이션 플레이어");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setFont(new Font("굴림", Font.BOLD, 15));
		lblNewLabel_4.setBorder(new EtchedBorder(EtchedBorder.RAISED, null,
				null));
		lblNewLabel_4.setBounds(14, 12, 152, 27);
		animationPanel.add(lblNewLabel_4);

		aniPlayButton = new JButton("\u25B6");
		aniPlayButton.setFont(new Font("굴림", Font.PLAIN, 15));
		aniPlayButton.setBounds(14, 47, 105, 27);
		animationPanel.add(aniPlayButton);
		aniPlayButton.addActionListener(this);

		aniPauseButton = new JButton("||");
		aniPauseButton.setFont(new Font("굴림", Font.BOLD, 15));
		aniPauseButton.setBounds(133, 45, 105, 27);
		animationPanel.add(aniPauseButton);
		aniPauseButton.addActionListener(this);

		JLabel lblNewLabel_5 = new JLabel(
				"\uBAA8\uB4E0 \uD504\uB808\uC784 \uC870\uC808");
		lblNewLabel_5.setBounds(14, 86, 115, 18);
		animationPanel.add(lblNewLabel_5);

		frameTextField = new JTextField();
		frameTextField.setHorizontalAlignment(SwingConstants.CENTER);
		frameTextField.setToolTipText("\uAE30\uBCF8 \uD504\uB808\uC784 1000");
		frameTextField.setText("1000");
		frameTextField.setBounds(143, 83, 81, 24);
		animationPanel.add(frameTextField);
		frameTextField.setColumns(10);

		frameConfirmButton = new JButton("\uC124\uC815");
		frameConfirmButton.setBounds(241, 82, 105, 27);
		animationPanel.add(frameConfirmButton);
		frameConfirmButton.addActionListener(this);

		JPanel panel_10 = new JPanel();
		panel_10.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_10.setBounds(14, 150, 343, 310);
		animationPanel.add(panel_10);
		panel_10.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_10 = new JScrollPane();
		panel_10.add(scrollPane_10, BorderLayout.CENTER);

		table_2 = new JTable();
		table_2.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				"\uC21C\uC11C", "\uC560\uB2C8\uBA54\uC774\uC158 \uD0C0\uC784" }) {
			Class[] columnTypes = new Class[] { Integer.class, Long.class };

			@Override
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		scrollPane_10.setViewportView(table_2);

		JLabel label_1 = new JLabel(
				"\uC2A4\uD504\uB77C\uC774\uD2B8 \uC88C\uD45C");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("굴림", Font.BOLD, 15));
		label_1.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		label_1.setBounds(14, 123, 143, 26);
		animationPanel.add(label_1);

		JLabel label_2 = new JLabel("\uD655\uB300 \uBC0F \uCD95\uC18C");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("굴림", Font.BOLD, 15));
		label_2.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		label_2.setBounds(14, 481, 105, 27);
		animationPanel.add(label_2);

		JButton button = new JButton("\uD655\uB300");
		button.setBounds(14, 520, 105, 27);
		animationPanel.add(button);

		JButton button_1 = new JButton("\uC6D0\uBCF8");
		button_1.setBounds(133, 520, 105, 27);
		animationPanel.add(button_1);

		JButton button_2 = new JButton("\uCD95\uC18C");
		button_2.setBounds(252, 520, 105, 27);
		animationPanel.add(button_2);

		JScrollPane fPlacementScroll = new JScrollPane();
		fuctionTab
				.addTab("<html>배<br>치<br><br>탭", null, fPlacementScroll, null);

		JPanel fPlacementPanel = new JPanel();
		fPlacementPanel.setPreferredSize(new Dimension(400, 1000));
		fPlacementScroll.setViewportView(fPlacementPanel);
		fPlacementPanel.setLayout(null);

		JLabel lblNewLabel_6 = new JLabel("\uBC30\uACBD\uD654\uBA74");
		lblNewLabel_6.setFont(new Font("굴림", Font.BOLD, 15));
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setBounds(14, 12, 62, 18);
		fPlacementPanel.add(lblNewLabel_6);

		fBackImgImportButton = new JButton("\uBD88\uB7EC\uC624\uAE30");
		fBackImgImportButton.setBounds(100, 8, 105, 27);
		fPlacementPanel.add(fBackImgImportButton);
		fBackImgImportButton.addActionListener(this);

		fBackImgCancelButton = new JButton("\uCDE8\uC18C");
		fBackImgCancelButton.setBounds(219, 8, 105, 27);
		fPlacementPanel.add(fBackImgCancelButton);
		fBackImgCancelButton.addActionListener(this);

		JLabel lblNewLabel_7 = new JLabel("\uC774\uBBF8\uC9C0");
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_7.setFont(new Font("굴림", Font.BOLD, 15));
		lblNewLabel_7.setBounds(14, 51, 62, 18);
		fPlacementPanel.add(lblNewLabel_7);

		fImgImportButton = new JButton("\uBD88\uB7EC\uC624\uAE30");
		fImgImportButton.setBounds(100, 47, 105, 27);
		fPlacementPanel.add(fImgImportButton);
		fImgImportButton.addActionListener(this);

		fImgCancelButton = new JButton("\uCDE8\uC18C");
		fImgCancelButton.setBounds(219, 47, 105, 27);
		fPlacementPanel.add(fImgCancelButton);
		fImgCancelButton.addActionListener(this);

		JLabel lblNewLabel_8 = new JLabel("\uC560\uB2C8\uBA54\uC774\uC158");
		lblNewLabel_8.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_8.setFont(new Font("굴림", Font.BOLD, 15));
		lblNewLabel_8.setBounds(14, 93, 83, 18);
		fPlacementPanel.add(lblNewLabel_8);

		fAniImportButton = new JButton("\uBD88\uB7EC\uC624\uAE30");
		fAniImportButton.setBounds(100, 89, 105, 27);
		fPlacementPanel.add(fAniImportButton);
		fAniImportButton.addActionListener(this);

		fAniCancelButton = new JButton("\uCDE8\uC18C");
		fAniCancelButton.setBounds(219, 89, 105, 27);
		fPlacementPanel.add(fAniCancelButton);
		fAniCancelButton.addActionListener(this);

		JLabel lblNewLabel_9 = new JLabel(
				"\uC774\uBBF8\uC9C0 & \uC560\uB2C8\uBA54\uC774\uC158 \uB9AC\uC2A4\uD2B8");
		lblNewLabel_9.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_9.setFont(new Font("굴림", Font.BOLD, 15));
		lblNewLabel_9.setBounds(14, 137, 216, 27);
		lblNewLabel_9.setBorder(new EtchedBorder(EtchedBorder.RAISED, null,
				null));
		fPlacementPanel.add(lblNewLabel_9);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(14, 165, 340, 262);
		fPlacementPanel.add(scrollPane_2);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				"\uBC88\uD638", "\uC774\uB984", "x0", "y0",
				"\uC9C0\uC5F0\uC2DC\uAC04" }) {
			Class[] columnTypes = new Class[] { Integer.class, String.class,
					Integer.class, Integer.class, Integer.class };

			@Override
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false,
					false, false };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.setBounds(14, 352, 310, -175);
		scrollPane_2.setViewportView(table);

		JLabel lblNewLabel_10 = new JLabel(
				"\uBAA8\uB4E0 \uC560\uB2C8\uBA54\uC774\uC158 \uD50C\uB808\uC774\uC5B4");
		lblNewLabel_10.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_10.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		lblNewLabel_10.setFont(new Font("굴림", Font.BOLD, 15));
		lblNewLabel_10.setBounds(14, 819, 191, 28);
		fPlacementPanel.add(lblNewLabel_10);

		pAniPlayButton = new JButton("\u25B6");
		pAniPlayButton.setFont(new Font("굴림", Font.BOLD, 15));
		pAniPlayButton.setBounds(14, 859, 105, 27);
		fPlacementPanel.add(pAniPlayButton);
		pAniPlayButton.addActionListener(this);

		pAniPauseButton = new JButton("\u25A0");
		pAniPauseButton.setFont(new Font("굴림", Font.BOLD, 15));
		pAniPauseButton.setBounds(133, 859, 105, 27);
		fPlacementPanel.add(pAniPauseButton);
		pAniPauseButton.addActionListener(this);

		JLabel lblNewLabel_11 = new JLabel("이동 애니메이션");
		lblNewLabel_11.setFont(new Font("굴림", Font.BOLD, 15));
		lblNewLabel_11.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_11.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		lblNewLabel_11.setBounds(14, 439, 133, 27);
		fPlacementPanel.add(lblNewLabel_11);

		mRegisterButton = new JButton("등록");
		mRegisterButton.setBounds(133, 478, 105, 27);
		fPlacementPanel.add(mRegisterButton);
		mRegisterButton.addActionListener(this);

		mCancelButton = new JButton("취소");
		mCancelButton.setBounds(252, 478, 105, 27);
		fPlacementPanel.add(mCancelButton);
		mCancelButton.addActionListener(this);

		mModeButton = new JToggleButton("\uC774\uB3D9 \uBAA8\uB4DC");
		mModeButton.setFont(new Font("굴림", Font.BOLD, 15));
		mModeButton.setBounds(14, 478, 105, 27);
		fPlacementPanel.add(mModeButton);

		JLabel label_4fds = new JLabel(
				"\uC774\uB3D9 \uC560\uB2C8\uBA54\uC774\uC158 \uB9AC\uC2A4\uD2B8");
		label_4fds.setHorizontalAlignment(SwingConstants.CENTER);
		label_4fds.setFont(new Font("굴림", Font.BOLD, 15));
		label_4fds.setBorder(new EtchedBorder(EtchedBorder.RAISED, null,

		null));
		label_4fds.setBounds(14, 517, 216, 27);
		fPlacementPanel.add(label_4fds);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(14, 545, 340, 262);
		fPlacementPanel.add(scrollPane_3);
		mModeButton.addActionListener(this);

		JPanel actionTabPanel = new JPanel();
		actionTabPanel.setMinimumSize(new Dimension(1100, 750));
		actionTabPanel.setMaximumSize(new Dimension(1200, 800));
		actionTabPanel.setLayout(new BorderLayout());
		splitPane_2.setLeftComponent(actionTabPanel);

		actionTab = new JTabbedPane(SwingConstants.TOP);
		actionTab.setMinimumSize(new Dimension(1100, 750));
		actionTab.setMaximumSize(new Dimension(1200, 800));
		actionTab.setVisible(false);
		actionTab.setFont(new Font("굴림", Font.BOLD, 16));
		actionTabPanel.add(actionTab, BorderLayout.CENTER);
		actionTab.addMouseListener(this);
		actionTab.addMouseMotionListener(this);

		aCutScroll = new JScrollPane();
		actionTab.addTab("\uC790\uB974\uAE30 \uC2E4\uD589\uCC3D", null,
				aCutScroll, null);
		aCutScroll.setPreferredSize(new Dimension(1100, 750));

		JScrollPane aCenterPositionScroll = new JScrollPane();
		actionTab.addTab("\uC911\uC2EC\uC88C\uD45C \uC124\uC815\uCC3D", null,
				aCenterPositionScroll, null);

		JPanel aCenterPositionPanel = new JPanel();
		aCenterPositionPanel.setPreferredSize(new Dimension(1100, 750));
		aCenterPositionScroll.setViewportView(aCenterPositionPanel);

		aAnimationScroll = new JScrollPane();
		actionTab.addTab("\uC560\uB2C8\uBA54\uC774\uC158 \uC2E4\uD589\uCC3D",
				null, aAnimationScroll, null);

		aAnimationPanel = new JPanel();
		aAnimationPanel.setPreferredSize(new Dimension(1100, 700));
		aAnimationScroll.setViewportView(aAnimationPanel);
		aAnimationPanel.setLayout(new BorderLayout(0, 0));

		animate = new ActionListener() {
			private int index = 0;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (animPanel.frames != null) {
					if (index < animPanel.frames.size() - 1) {
						index++;
					} else {
						index = 0;
					}
				}
				sList.setSelectedIndex(index);

				sList.scrollRectToVisible( // 자동 스크롤
				sList.getCellBounds(sList.getMinSelectionIndex(),
						sList.getMaxSelectionIndex()));

				animPanel.index = index;
				animPanel.repaint();
			}

		};

		JScrollPane aPlacementScroll = new JScrollPane();
		actionTab.addTab("\uBC30\uCE58 \uC2E4\uD589\uCC3D", null,
				aPlacementScroll, null);

		aPlacementPanel = new JPanel();
		aPlacementPanel.setPreferredSize(new Dimension(1100, 700));
		aPlacementPanel.setLayout(null);
		aPlacementScroll.setViewportView(aPlacementPanel);
	}

	private static BufferedImage imageToBufferedImage(final Image image) {
		final BufferedImage bufferedImage = new BufferedImage(
				image.getWidth(null), image.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2 = bufferedImage.createGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
		return bufferedImage;
	}

	private Image makeColorTransparent(final BufferedImage im, final Color color, int tolerance) {
	    int temp = 0;
	    if (tolerance < 0 || tolerance > 100) {
	        System.err.println("The tolerance is a percentage, so the value has to be between 0 and 100.");
	        temp = 0;
	    } else {
	        temp = tolerance * (0xFF000000 | 0xFF000000) / 100;
	    }
	    final int toleranceRGB = Math.abs(temp);
			
	    final ImageFilter filter = new RGBImageFilter() {
	        // The color we are looking for (white)... Alpha bits are set to opaque
	        public int markerRGBFrom = (color.getRGB() | 0xFF000000) - toleranceRGB;
	        public int markerRGBTo = (color.getRGB() | 0xFF000000) + toleranceRGB;

	        public final int filterRGB(final int x, final int y, final int rgb) {
	            if ((rgb | 0xFF000000) >= markerRGBFrom && (rgb | 0xFF000000) <= markerRGBTo) {
	                // Mark the alpha bits as zero - transparent
	                return 0x00FFFFFF & rgb;
	            } else {
	                // Nothing to do
	                return rgb;
	            }
	        }
	    };

	    final ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
	    return Toolkit.getDefaultToolkit().createImage(ip);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == newProject) {
			newFrame.setVisible(true);
		} else if (e.getSource() == open) {

		} else if (e.getSource() == proSave) {

		} else if (e.getSource() == aniSave) {
			try {
				JFileChooser chooser = new JFileChooser();
				int option = chooser.showSaveDialog(frame);
				if (option == JFileChooser.APPROVE_OPTION) {
					if (chooser.getSelectedFile() != null) {
						FileOutputStream fos = new FileOutputStream(
								chooser.getSelectedFile() + ".ani");
						ObjectOutputStream oos = new ObjectOutputStream(fos);
						oos.writeObject(new SerializableAnimation(sheetPath,
								sIntegerList, I_SpeedValue, invisibleRGB));
						oos.close();
					}
				}
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == close) {

		} else if (e.getSource() == newFrameConfirm) {
			frame.setTitle(toolName + titleSupport + titleTextField.getText());
			interName = titleTextField.getText();
			interWidth = widthTextField.getText();
			interHeight = heightTextField.getText();
			try {
				newFrame.setClosed(true);
			} catch (PropertyVetoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			titleTextField.setText(null);
			heightTextField.setText(null);
			widthTextField.setText(null);
			for (int i = 0; i < 3; i++)
				newFrameEnable[i] = false;
			newFrameConfirm.setEnabled(false);
			try {
				newFrame.setClosed(false);
			} catch (PropertyVetoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			fuctionTab.setVisible(true);
			actionTab.setVisible(true);

			pPanel = new PlacementPanel(Integer.parseInt(interWidth),
					Integer.parseInt(interHeight));
			aPlacementPanel.add(pPanel);
		} else if (e.getSource() == newFrameCancel) {
			try {
				newFrame.setClosed(true);
			} catch (PropertyVetoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				newFrame.setClosed(false);
			} catch (PropertyVetoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == importButton) {
			String[] pics = new String[] { "png", "jpg", "gif" };
			JFileChooser chooser = new JFileChooser();
			chooser.addChoosableFileFilter(new SimpleFileFilter(pics,
					"Images (*.png, *.jpg, *.gif)"));
			int option = chooser.showOpenDialog(frame);
			if (option == JFileChooser.APPROVE_OPTION) {
				if (chooser.getSelectedFile() != null) {
					try {
						sheetImg = ImageIO.read(new File(chooser
								.getSelectedFile().getAbsolutePath()));
						sheetPath = chooser.getSelectedFile().getAbsolutePath();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					initSheetWidth = sheetImg.getWidth();
					initSheetHeight = sheetImg.getHeight();
					totalSheetWidth = initSheetWidth;
					totalSheetHeight = initSheetHeight;

					spritePanel = new SpritePanel(sheetImg, 0, 0,
							totalSheetWidth, totalSheetHeight);
					spritePanel.setPreferredSize(new Dimension(totalSheetWidth,
							totalSheetHeight));
					aCutScroll.setViewportView(spritePanel);
				}
			}

			animPanel = new AnimationPanel(imgList);
			aAnimationPanel.add(animPanel, BorderLayout.CENTER);
		} else if (e.getSource() == importCancelButton) {
			spritePanel.spriteSheet = null;
			spritePanel.rW = 0;
			spritePanel.rH = 0;
			spritePanel.r = null;
			invisibleSelectionTF = false;
			spritePanel.repaint();
			spritePanel.magTF = false;
			invisibleTextField.setText(null);
			for (int i = spriteList.size() - 1; i >= 0; i--)
				sTableModel1.removeRow(i);
			spriteList.clear();
			sNameList.clear();
			sList.setListData(sNameList.toArray());
			sList.setCellRenderer(new IconListRenderer(icons));
		} else if (e.getSource() == erConfirmButton) {
			spritePanel.magTF = true;
			erMagnification = erTextField.getText().split(" %")[0];
			totalSheetWidth = (int) (initSheetWidth * (Float
					.parseFloat(erMagnification) / 100));
			totalSheetHeight = (int) (initSheetHeight * (Float
					.parseFloat(erMagnification) / 100));
			spritePanel.erMagnification = Float.parseFloat(erMagnification) / 100;
			spritePanel.totalSheetWidth = totalSheetWidth;
			spritePanel.totalSheetHeight = totalSheetHeight;
			spritePanel.setBounds(0, 0, totalSheetWidth, totalSheetHeight);
			spritePanel.setPreferredSize(new Dimension(totalSheetWidth,
					totalSheetHeight));
			spritePanel.repaint();
		} else if (e.getSource() == invisibleSelectionButton) {
			invisibleSelectionTF = true;
			spritePanel.addMouseListener(this);
			spritePanel.addMouseMotionListener(this);
		} else if (e.getSource() == cSelectionButton) {
			spritePanel.cSelectionTF = true;
		} else if (e.getSource() == cRegistrationButton) {
			if (spritePanel.cSelectionTF) {
				sTableModel1.addRow(spritePanel.sData);
				sTableModel1.fireTableDataChanged();
				sIntegerList.add(spritePanel.sData);

				int x = (Integer) sTableModel1.getValueAt(spritePanel.index, 1);
				int y = (Integer) sTableModel1.getValueAt(spritePanel.index, 2);
				int w = (Integer) sTableModel1.getValueAt(spritePanel.index, 3)
						- x;
				int h = (Integer) sTableModel1.getValueAt(spritePanel.index, 4)
						- y;

				spriteList.add(new ImageIcon(spritePanel.spriteSheet
						.getSubimage(x, y, w, h)));
				imgList.add(spritePanel.spriteSheet.getSubimage(x, y, w, h));
				sNameList.add(interName
						+ sTableModel1.getValueAt(spritePanel.index, 0));

				if (spritePanel.index > -1)
					icons.put(sNameList.get(spritePanel.index),
							spriteList.get(spritePanel.index));

				sList.setListData(sNameList.toArray());
				sList.setCellRenderer(new IconListRenderer(icons));

				spritePanel.index++;
			}
		} else if (e.getSource() == frameConfirmButton) {
			aniTF = true;
			timer.stop();
		} else if (e.getSource() == aniPlayButton) {
			if (aniTF) {
				try {
					I_SpeedValue = Integer.parseInt(String
							.valueOf(frameTextField.getText()));
				} catch (NumberFormatException n) {
					n.printStackTrace();
				}

				timer = new Timer(I_SpeedValue, animate);

				timer.start();
				aniTF = false;
			}
		} else if (e.getSource() == aniPauseButton) {
			if (!aniTF) {
				timer.stop();
				aniTF = true;
			}
		} else if (e.getSource() == fBackImgImportButton) {
			String[] pics = new String[] { "png", "jpg", "gif" };
			JFileChooser chooser = new JFileChooser();
			chooser.addChoosableFileFilter(new SimpleFileFilter(pics,
					"Images (*.png, *.jpg, *.gif)"));
			int option = chooser.showOpenDialog(frame);
			if (option == JFileChooser.APPROVE_OPTION) {
				if (chooser.getSelectedFile() != null) {
					try {
						backImg = ImageIO.read(new File(chooser
								.getSelectedFile().getAbsolutePath()));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					pPanel.backImg = backImg;
					pPanel.repaint();
				}
			}
		} else if (e.getSource() == fBackImgCancelButton) {
			pPanel.backImg = null;
			pPanel.repaint();
		} else if (e.getSource() == fImgImportButton) {
			String[] pics = new String[] { "png", "jpg", "gif" };
			JFileChooser chooser = new JFileChooser();
			chooser.addChoosableFileFilter(new SimpleFileFilter(pics,
					"Images (*.png, *.jpg, *.gif)"));
			int option = chooser.showOpenDialog(frame);
			if (option == JFileChooser.APPROVE_OPTION) {
				if (chooser.getSelectedFile() != null) {
					try {
						ArrayList<BufferedImage> img = new ArrayList<BufferedImage>();
						img.add(ImageIO.read(new File(chooser.getSelectedFile()
								.getAbsolutePath())));
						animationList.add(img);
						nameList.add(chooser.getSelectedFile().getName());
						iconList.add(new ImageIcon(chooser.getSelectedFile()
								.getAbsolutePath()));
						lFrameSpeed.add(0);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					index++;

					if (index > -1)
						icons.put(nameList.get(index), iconList.get(index));

					imgAnilist.setListData(nameList.toArray());
					imgAnilist.setCellRenderer(new IconListRenderer(icons));
				}
			}
		} else if (e.getSource() == fImgCancelButton) {

		} else if (e.getSource() == fAniImportButton) {
			if (aniDecision == true) {
				FileNameExtensionFilter fnef = new FileNameExtensionFilter(
						"Animation File", "ani");
				JFileChooser chooser = new JFileChooser();
				chooser.addChoosableFileFilter(fnef);
				int option = chooser.showOpenDialog(frame);
				if (option == JFileChooser.APPROVE_OPTION) {
					if (chooser.getSelectedFile() != null) {
						try {
							FileInputStream fis = new FileInputStream(
									chooser.getSelectedFile());
							ObjectInputStream ois = new ObjectInputStream(fis);
							loadAni = (SerializableAnimation) ois.readObject();

							lSheetPath = loadAni.sheetPath;
							lCoordinate = loadAni.coordinate;
							lFrameSpeed.add(loadAni.frameSpeed);
							lInviRGB = loadAni.invisibleRGB;
							ois.close();
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						try {
							BufferedImage source = ImageIO.read(new File(
									lSheetPath));
							final int color = lInviRGB;

							final Image imageWithTransparency = makeColorTransparent(
									source, new Color(color), TOLERANCE);

							final BufferedImage transparentImage = imageToBufferedImage(imageWithTransparency);

							sheetList.add(transparentImage);
							nameList.add(chooser.getSelectedFile().getName());
							ArrayList<BufferedImage> ani = new ArrayList<BufferedImage>();

							for (int i = 0; i < lCoordinate.size(); i++) {
								Integer x = lCoordinate.get(i)[1];
								Integer y = lCoordinate.get(i)[2];
								Integer w = lCoordinate.get(i)[3] - x;
								Integer h = lCoordinate.get(i)[4] - y;
								ani.add(sheetList.get(sheetList.size() - 1)
										.getSubimage(x.intValue(),
												y.intValue(), w.intValue(),
												h.intValue()));

								aniListIndex += 5;
							}
							Integer _x = lCoordinate.get(0)[1];
							Integer _y = lCoordinate.get(0)[2];
							Integer _w = lCoordinate.get(0)[3] - _x;
							Integer _h = lCoordinate.get(0)[4] - _y;
							iconList.add(new ImageIcon(sheetList.get(
									sheetList.size() - 1).getSubimage(
									_x.intValue(), _y.intValue(),
									_w.intValue(), _h.intValue())));

							animationList.add(ani);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (NumberFormatException nfe) {
							nfe.printStackTrace();
						}

						index++;

						if (index > -1)
							icons.put(nameList.get(index), iconList.get(index));

						imgAnilist.setListData(nameList.toArray());
						imgAnilist.setCellRenderer(new IconListRenderer(icons));
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "애니메이션을 끄고 눌러주세요!!");
			}
		} else if (e.getSource() == fAniCancelButton) {

		} else if (e.getSource() == pAniPlayButton) {
			if (aniDecision) {
				for (int i = 0; i <= pPanel.index; i++) {
					pPanel.pTimerList.get(i).start();
					pPanel.pMTimerList.get(i).start();
				}
				aniDecision = false;
			}
		} else if (e.getSource() == pAniPauseButton) {
			if (!aniDecision) {
				for (int i = 0; i < timerTF.size(); i++) {
					pPanel.pTimerList.get(i).stop();
					pPanel.graphicList.get(i).setIndex(0);
					pPanel.pMTimerList.get(i).stop();
					pPanel.imgX.set(i, pPanel.graphicList.get(i).getMoveX()
							.get(0));
					pPanel.imgY.set(i, pPanel.graphicList.get(i).getMoveY()
							.get(0));
					pPanel.graphicList.get(i).setMoveIndex(0);
				}
				aniDecision = true;
				pPanel.repaint();
			}
		} else if (e.getSource() == mRegisterButton) {
			if (moveDecision) {
				mModeButton.setSelected(false);
				pPanel.moveTF = false;
				moveDecision = false;
				for (int i = 0; i <= pPanel.index; i++) {
					if (!pPanel.graphicList.get(i).getMoveX().isEmpty()) {
						pPanel.imgX.set(i, pPanel.graphicList.get(i).getMoveX()
								.get(0));
						pPanel.imgY.set(i, pPanel.graphicList.get(i).getMoveY()
								.get(0));
					}
					pPanel.pMTimerList.add(pPanel.graphicList.get(i).moveTimer(
							i, 10, pPanel.graphicList.get(i).ismTF()));

				}
				pPanel.repaint();
			}
		} else if (e.getSource() == mCancelButton) {
			if (!moveDecision) {
			}
		} else if (e.getSource() == mModeButton) {
			if (mModeButton.isSelected()) {
				pPanel.moveTF = true;
			} else {
				pPanel.moveTF = false;
			}
			moveDecision = pPanel.moveTF;
			pPanel.repaint();
		}
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == titleTextField) {
			newFrameEnable[0] = true;
			if (newFrameEnable[0] == true && newFrameEnable[1] == true
					&& newFrameEnable[2] == true)
				newFrameConfirm.setEnabled(true);
		} else if (arg0.getSource() == widthTextField) {
			newFrameEnable[1] = true;
			if (newFrameEnable[0] == true && newFrameEnable[1] == true
					&& newFrameEnable[2] == true)
				newFrameConfirm.setEnabled(true);
		} else if (arg0.getSource() == heightTextField) {
			newFrameEnable[2] = true;
			if (newFrameEnable[0] == true && newFrameEnable[1] == true
					&& newFrameEnable[2] == true)
				newFrameConfirm.setEnabled(true);
		}
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == titleTextField) {
			interName = titleTextField.getText();
		} else if (arg0.getSource() == widthTextField) {
			interWidth = widthTextField.getText();
		} else if (arg0.getSource() == heightTextField) {
			interHeight = heightTextField.getText();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == spritePanel) {
			invisibleSelectionTF = false;
		} else if (e.getSource() == fuctionTab) {
			actionTab.setSelectedIndex(fuctionTab.getSelectedIndex());
			fuctionTab.setSelectedIndex(actionTab.getSelectedIndex());
		} else if (e.getSource() == actionTab) {
			fuctionTab.setSelectedIndex(actionTab.getSelectedIndex());
			actionTab.setSelectedIndex(fuctionTab.getSelectedIndex());
		} else if (e.getSource() == imgAnilist) {
			if (fuctionTab.getSelectedIndex() == 3) {
				if (e.getClickCount() == 2) {
					if (nameList.size() > 0) {
						selectList.add(animationList.get(imgAnilist
								.getSelectedIndex()));
						sFrameSpeed.add(lFrameSpeed.get(imgAnilist
								.getSelectedIndex()));
						if (!nameList.get(imgAnilist.getSelectedIndex())
								.contains(".ani")) {
							timerTF.add(false);
						} else {
							timerTF.add(true);
						}
						imgAnilist.clearSelection();

						pPanel.pListSet = selectList;
						pPanel.frameSpeedList = sFrameSpeed;
						pPanel.pAniTF = timerTF;
						pPanel.addObjectNBox();

						pPanel.repaint();
					}
				}
			}
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

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == fuctionTab) {
			actionTab.setSelectedIndex(fuctionTab.getSelectedIndex());
			fuctionTab.setSelectedIndex(actionTab.getSelectedIndex());
		} else if (e.getSource() == actionTab) {
			fuctionTab.setSelectedIndex(actionTab.getSelectedIndex());
			actionTab.setSelectedIndex(fuctionTab.getSelectedIndex());
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == spritePanel) {
			if (invisibleSelectionTF) {
				Point point = e.getPoint();
				SwingUtilities.convertPointToScreen(point, spritePanel);

				try {
					Color spriteColor = new Robot().getPixelColor(point.x,
							point.y);
					int red = spriteColor.getRed();
					int green = spriteColor.getGreen();
					int blue = spriteColor.getBlue();
					int rgb = spriteColor.getRGB();
					invisibleRGB = rgb;
					spritePanel.rgb = rgb;
					invisibleTextField.setText(String.valueOf(red + " . "
							+ green + " . " + blue));
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
			}
		}

	}
}
