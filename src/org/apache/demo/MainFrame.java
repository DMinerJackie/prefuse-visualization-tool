/*
 * MainFrame.java
 *
 * Created on __DATE__, __TIME__
 */

package org.apache.demo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.swing.*;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.layout.graph.NodeLinkTreeLayout;
import prefuse.data.Graph;
import prefuse.data.io.DataIOException;
import prefuse.util.ui.UILib;
import prefuse.visual.VisualGraph;

//-Dfile.encoding=GB18030
/**
 * 
 * @author Jack
 */
public class MainFrame extends javax.swing.JFrame {

	private static MainFrame frame = null;
	private static Visualization visInstance = null;
	private static int width;
	private static int height;
	private static boolean itemIsEnable = false;//用于控制各个菜单项是否可用

	/** Creates new form MainFrame */
	public MainFrame() {
		initComponents();
		saveAsItem.setEnabled(itemIsEnable);
		forceLayoutItem.setEnabled(itemIsEnable);
		CircleLayoutItem.setEnabled(itemIsEnable);
		randomLayoutItem.setEnabled(itemIsEnable);
		gridLayoutItem.setEnabled(itemIsEnable);
		fruchtermanLayoutItem.setEnabled(itemIsEnable);
		radialTreeLayout.setEnabled(itemIsEnable);
		nodeLinkTreeLayoutItem.setEnabled(itemIsEnable);
		noTextItem.setEnabled(itemIsEnable);
		textOnlyItem.setEnabled(itemIsEnable);
		textAndImageItem.setEnabled(itemIsEnable);
		rectangleItem.setEnabled(itemIsEnable);
		roundItem.setEnabled(itemIsEnable);
		roundRectangleItem.setEnabled(itemIsEnable);
		zoomToFitItem.setEnabled(itemIsEnable);
		switchButton.setEnabled(itemIsEnable);
		backgroundButton.setEnabled(itemIsEnable);
	}

	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		parentPanel = new javax.swing.JPanel();
		infoPanel = new javax.swing.JPanel();
		nodeNumLabel = new javax.swing.JLabel();
		edgeNumLabel = new javax.swing.JLabel();
		nodeLabel = new javax.swing.JLabel();
		edgeLabel = new javax.swing.JLabel();
		isdirectLabel = new javax.swing.JLabel();
		switchButton = new javax.swing.JButton();
		backgroundButton = new javax.swing.JButton();
		jSplitPane1 = new javax.swing.JSplitPane();
		paramsPanel = new javax.swing.JPanel();
		displayPanel = new javax.swing.JPanel();
		jMenuBar1 = new javax.swing.JMenuBar();
		fileMenu = new javax.swing.JMenu();
		openItem = new javax.swing.JMenuItem();
		importFromDBItem = new javax.swing.JMenuItem();
		saveAsItem = new javax.swing.JMenuItem();
		quitItem = new javax.swing.JMenuItem();
		layoutAlgMenu = new javax.swing.JMenu();
		forceLayoutItem = new javax.swing.JMenuItem();
		CircleLayoutItem = new javax.swing.JMenuItem();
		randomLayoutItem = new javax.swing.JMenuItem();
		gridLayoutItem = new javax.swing.JMenuItem();
		fruchtermanLayoutItem = new javax.swing.JMenuItem();
		radialTreeLayout = new javax.swing.JMenuItem();
		nodeLinkTreeLayoutItem = new javax.swing.JMenuItem();
		jMenu5 = new javax.swing.JMenu();
		labelMenu = new javax.swing.JMenu();
		noTextItem = new javax.swing.JMenuItem();
		textOnlyItem = new javax.swing.JMenuItem();
		textAndImageItem = new javax.swing.JMenuItem();
		nodeMenu = new javax.swing.JMenu();
		rectangleItem = new javax.swing.JMenuItem();
		roundItem = new javax.swing.JMenuItem();
		roundRectangleItem = new javax.swing.JMenuItem();
		edgeMenu = new javax.swing.JMenu();
		ActionMenu = new javax.swing.JMenu();
		controllerMenu = new javax.swing.JMenu();
		zoomToFitItem = new javax.swing.JMenuItem();
		helpMenu = new javax.swing.JMenu();
		helpItem = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		addWindowStateListener(new java.awt.event.WindowStateListener() {
			public void windowStateChanged(java.awt.event.WindowEvent evt) {
				formWindowStateChanged(evt);
			}
		});

		parentPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentResized(java.awt.event.ComponentEvent evt) {
				parentPanelComponentResized(evt);
			}
		});

		infoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("\u4fe1\u606f"));

		nodeNumLabel.setText("\u8282\u70b9\uff1a");

		edgeNumLabel.setText("\u8fb9\uff1a");

		nodeLabel.setText("NaN");

		edgeLabel.setText("NaN");

		switchButton.setText("\u8fd0\u884c");
		switchButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				switchButtonActionPerformed(evt);
			}
		});

		backgroundButton.setText("\u80cc\u666f\u989c\u8272");
		backgroundButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				backgroundButtonActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout infoPanelLayout = new javax.swing.GroupLayout(infoPanel);
		infoPanel.setLayout(infoPanelLayout);
		infoPanelLayout.setHorizontalGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(infoPanelLayout.createSequentialGroup().addContainerGap().addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(backgroundButton).addGroup(infoPanelLayout.createSequentialGroup().addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(nodeNumLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(edgeNumLabel)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(nodeLabel).addComponent(edgeLabel))).addComponent(isdirectLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(switchButton)).addContainerGap(40, Short.MAX_VALUE)));
		infoPanelLayout.setVerticalGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(infoPanelLayout.createSequentialGroup().addContainerGap().addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(nodeNumLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(nodeLabel)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(edgeNumLabel).addComponent(edgeLabel)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(isdirectLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(64, 64, 64).addComponent(switchButton).addGap(18, 18, 18).addComponent(backgroundButton).addContainerGap(163, Short.MAX_VALUE)));

		jSplitPane1.setBorder(null);
		jSplitPane1.setResizeWeight(1.0);
		jSplitPane1.setContinuousLayout(true);
		jSplitPane1.setLastDividerLocation(5);
		jSplitPane1.setOneTouchExpandable(true);

		javax.swing.GroupLayout paramsPanelLayout = new javax.swing.GroupLayout(paramsPanel);
		paramsPanel.setLayout(paramsPanelLayout);
		paramsPanelLayout.setHorizontalGroup(paramsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
		paramsPanelLayout.setVerticalGroup(paramsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 442, Short.MAX_VALUE));

		jSplitPane1.setRightComponent(paramsPanel);

		javax.swing.GroupLayout displayPanelLayout = new javax.swing.GroupLayout(displayPanel);
		displayPanel.setLayout(displayPanelLayout);
		displayPanelLayout.setHorizontalGroup(displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 595, Short.MAX_VALUE));
		displayPanelLayout.setVerticalGroup(displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 442, Short.MAX_VALUE));

		jSplitPane1.setLeftComponent(displayPanel);

		javax.swing.GroupLayout parentPanelLayout = new javax.swing.GroupLayout(parentPanel);
		parentPanel.setLayout(parentPanelLayout);
		parentPanelLayout.setHorizontalGroup(parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(parentPanelLayout.createSequentialGroup().addComponent(infoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)));
		parentPanelLayout.setVerticalGroup(parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(infoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE));

		fileMenu.setText("\u6587\u4ef6");

		openItem.setText("\u6253\u5f00...");
		openItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				openItemActionPerformed(evt);
			}
		});
		fileMenu.add(openItem);

		importFromDBItem.setText("\u8fde\u63a5\u6570\u636e\u5e93...");
		importFromDBItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				importFromDBItemActionPerformed(evt);
			}
		});
		fileMenu.add(importFromDBItem);

		saveAsItem.setText("\u5b58\u50a8\u4e3a...");
		saveAsItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveAsItemActionPerformed(evt);
			}
		});
		fileMenu.add(saveAsItem);

		quitItem.setText("\u9000\u51fa");
		quitItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				quitItemActionPerformed(evt);
			}
		});
		fileMenu.add(quitItem);

		jMenuBar1.add(fileMenu);

		layoutAlgMenu.setText("\u5e03\u5c40\u7b97\u6cd5");

		forceLayoutItem.setText("\u529b\u5bfc\u5411\u5e03\u5c40");
		forceLayoutItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				forceLayoutItemActionPerformed(evt);
			}
		});
		layoutAlgMenu.add(forceLayoutItem);

		CircleLayoutItem.setText("\u5706\u5f62\u5e03\u5c40");
		CircleLayoutItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				CircleLayoutItemActionPerformed(evt);
			}
		});
		layoutAlgMenu.add(CircleLayoutItem);

		randomLayoutItem.setText("\u968f\u673a\u5e03\u5c40");
		randomLayoutItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				randomLayoutItemActionPerformed(evt);
			}
		});
		layoutAlgMenu.add(randomLayoutItem);

		gridLayoutItem.setText("\u7f51\u72b6\u5e03\u5c40");
		gridLayoutItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				gridLayoutItemActionPerformed(evt);
			}
		});
		layoutAlgMenu.add(gridLayoutItem);

		fruchtermanLayoutItem.setText("FruchtermanReingold\u5e03\u5c40");
		fruchtermanLayoutItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				fruchtermanLayoutItemActionPerformed(evt);
			}
		});
		layoutAlgMenu.add(fruchtermanLayoutItem);

		radialTreeLayout.setText("\u8f90\u5c04\u6811\u72b6\u5e03\u5c40");
		radialTreeLayout.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				radialTreeLayoutActionPerformed(evt);
			}
		});
		layoutAlgMenu.add(radialTreeLayout);

		nodeLinkTreeLayoutItem.setText("\u8282\u70b9\u5206\u7ea7\u6269\u5c55\u5e03\u5c40");
		nodeLinkTreeLayoutItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				nodeLinkTreeLayoutItemActionPerformed(evt);
			}
		});
		layoutAlgMenu.add(nodeLinkTreeLayoutItem);

		jMenuBar1.add(layoutAlgMenu);
		jMenuBar1.add(jMenu5);

		labelMenu.setText("\u6807\u7b7e\u914d\u7f6e");

		noTextItem.setText("\u4e0d\u663e\u793a");
		noTextItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				noTextItemActionPerformed(evt);
			}
		});
		labelMenu.add(noTextItem);

		textOnlyItem.setText("\u663e\u793a\u6587\u672c");
		textOnlyItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				textOnlyItemActionPerformed(evt);
			}
		});
		labelMenu.add(textOnlyItem);

		textAndImageItem.setText("\u663e\u793a\u6587\u672c\u548c\u56fe\u7247");
		labelMenu.add(textAndImageItem);

		jMenuBar1.add(labelMenu);

		nodeMenu.setText("\u8282\u70b9\u914d\u7f6e");

		rectangleItem.setText("\u77e9\u5f62");
		rectangleItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				rectangleItemActionPerformed(evt);
			}
		});
		nodeMenu.add(rectangleItem);

		roundItem.setText("\u5706\u5f62");
		roundItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				roundItemActionPerformed(evt);
			}
		});
		nodeMenu.add(roundItem);

		roundRectangleItem.setText("\u5706\u89d2\u77e9\u5f62");
		roundRectangleItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				roundRectangleItemActionPerformed(evt);
			}
		});
		nodeMenu.add(roundRectangleItem);

		jMenuBar1.add(nodeMenu);

		edgeMenu.setText("\u8fb9\u914d\u7f6e");
		jMenuBar1.add(edgeMenu);

		ActionMenu.setText("Action\u914d\u7f6e");
		jMenuBar1.add(ActionMenu);

		controllerMenu.setText("\u63a7\u5236\u5668");

		zoomToFitItem.setText("\u9002\u5e94\u5c4f\u5e55\u663e\u793a");
		zoomToFitItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				zoomToFitItemActionPerformed(evt);
			}
		});
		controllerMenu.add(zoomToFitItem);

		jMenuBar1.add(controllerMenu);

		helpMenu.setText("\u5e2e\u52a9");

		helpItem.setText("\u5173\u4e8e");
		helpItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				helpItemActionPerformed(evt);
			}
		});
		helpMenu.add(helpItem);

		jMenuBar1.add(helpMenu);

		setJMenuBar(jMenuBar1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(parentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(parentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void backgroundButtonActionPerformed(java.awt.event.ActionEvent evt) {
		JFrame colorFrame = new JFrame();
		JColorChooser colorChooser = new JColorChooser(Color.WHITE);
		colorFrame.setLocationRelativeTo(null);
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
//		helpFrame.setPreferredSize(new Dimension(450, 200));
		visInstance = GraphUtils.getVisualization();
		Color color = new Color(255, 255, 255);
		color = JColorChooser.showDialog(null, "请选择你喜欢的颜色", color);
		Display display = visInstance.getDisplay(0);
		if(display == null)
			return;
		display.setBackground(color);
		visInstance.repaint();
	}

	/**
	 * 控制图形运行/停止
	 */
	private void switchButtonActionPerformed(java.awt.event.ActionEvent evt) {
		visInstance = GraphUtils.getVisualization();
		if (switchButton.getText().equals("运行")) {
			visInstance.run("color");
			visInstance.run("layout");
			switchButton.setText("停止");
		} else {
			visInstance.cancel("color");
			visInstance.cancel("layout");
			switchButton.setText("运行");
		}
	}

	/**
	 * 连接数据库选项卡
	 */
	private void importFromDBItemActionPerformed(java.awt.event.ActionEvent evt) {
		String args[] = null;
		DatabaseConfFrame.main(args);
	}

	/**
	 * 适合屏幕显示选项卡
	 */
	private void zoomToFitItemActionPerformed(java.awt.event.ActionEvent evt) {

		ControllerTabUtils.zoomToFitAction();

	}

	/**
	 * 存储为图片选项卡
	 * 
	 * @param evt
	 */
	private void saveAsItemActionPerformed(java.awt.event.ActionEvent evt) {

		BufferedImage image = new BufferedImage(this.display.getWidth(), this.display.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		jSplitPane1.paint(g2);

		FileTabUtils.saveAsAction(image);

	}

	/**
	 * 显示文本选项卡
	 */
	private void textOnlyItemActionPerformed(java.awt.event.ActionEvent evt) {

		LabelConfTabUtils.textOnlyAction();
	}

	/**
	 * 不显示文本选项卡
	 */
	private void noTextItemActionPerformed(java.awt.event.ActionEvent evt) {

		LabelConfTabUtils.noTextAction();
	}

	/**
	 * 圆角矩形节点选项卡
	 */
	private void roundRectangleItemActionPerformed(java.awt.event.ActionEvent evt) {
		NodeConfTabUtils.roundRectangleAction();
	}

	/**
	 * 圆形节点选项卡
	 */
	void roundItemActionPerformed(java.awt.event.ActionEvent evt) {
		NodeConfTabUtils.roundAction();
	}

	/**
	 * 矩形节点选项卡
	 */
	private void rectangleItemActionPerformed(java.awt.event.ActionEvent evt) {
		NodeConfTabUtils.rectangleAction();
	}

	/**
	 * 节点分级展示选项卡
	 */
	private void nodeLinkTreeLayoutItemActionPerformed(java.awt.event.ActionEvent evt) {
		LayoutTabUtils.nodeLinkTreeLayout();
	}

	/**
	 * 辐射树状布局选项卡
	 * @param evt
	 */
	private void radialTreeLayoutActionPerformed(java.awt.event.ActionEvent evt) {
		LayoutTabUtils.radialTreeLayout();
	}

	/**
	 * Fruchterman Reingold布局选项卡
	 */
	private void fruchtermanLayoutItemActionPerformed(java.awt.event.ActionEvent evt) {

		LayoutTabUtils.fruchtermanLayoutAction();
	}

	/**
	 * 网状布局选项卡 可能涉及到数据结构是否符合网状布局的情况
	 */
	private void gridLayoutItemActionPerformed(java.awt.event.ActionEvent evt) {

		LayoutTabUtils.gridLayoutAction();
	}

	/**
	 * 随机布局选项卡
	 */
	private void randomLayoutItemActionPerformed(java.awt.event.ActionEvent evt) {

		LayoutTabUtils.randomLayoutAction();
	}

	/**
	 * 力导向布局选项卡
	 */
	private void forceLayoutItemActionPerformed(java.awt.event.ActionEvent evt) {

		LayoutTabUtils.forceLayoutAction();
	}

	/**
	 * 圆形布局选项卡
	 */
	private void CircleLayoutItemActionPerformed(java.awt.event.ActionEvent evt) {

		LayoutTabUtils.circleLayoutAction();
	}

	/**
	 * 打开并读取文件选项卡
	 * 
	 * @throws DataIOException
	 */
	private void openItemActionPerformed(java.awt.event.ActionEvent evt) {
		JFileChooser choose = new JFileChooser();
		choose.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("文本文件（*.xml）", "xml"));
		int op = choose.showOpenDialog(this);
		if (op == JFileChooser.CANCEL_OPTION) {
			System.out.println("取消");
		} else {
			String[] args = null;
			// File类表示文件或路径
			File f = choose.getSelectedFile();
			GraphAttriSetFrame.main(args, f, this);
			try {
				System.out.println(new String("文件名为".getBytes("UTF-8")) + ":" + f.getName());// 之所以这么麻烦是因为在run
				// as中的arguments添加了-Dfile...
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}

		}
	}

	/**
	 * 将图形展现到JSplitPane上并在inofPanel中显示图形相关信息
	 */
	public void sendToJSplitPane(String label) {

		this.display = FileTabUtils.preShow(label);

		this.setDisplay(this.display);
		//		this.setParameter(paramsPanel);

		jSplitPane1.setLeftComponent(displayPanel.add(this.display));
		jSplitPane1.setRightComponent(paramsPanel);

		//======布局======
		GridBagLayout gridLayout = new GridBagLayout();
		parentPanel.setLayout(gridLayout);
		parentPanel.add(infoPanel);
		parentPanel.add(jSplitPane1);
		GridBagConstraints s = new GridBagConstraints();
		s.fill = GridBagConstraints.BOTH;
		s.gridwidth = 1;
		s.weightx = 0;
		s.weighty = 1;
		gridLayout.setConstraints(infoPanel, s);//设置组件 
		s.gridwidth = 1;
		s.weightx = 1;
		s.weighty = 1;
		gridLayout.setConstraints(jSplitPane1, s);
		//================

		frame.setContentPane(parentPanel);

		VisualGraph vg = (VisualGraph) GraphUtils.getVisualization().getVisualGroup("graph");
		nodeLabel.setText("" + vg.getNodeCount());
		edgeLabel.setText("" + vg.getEdgeCount());
		isdirectLabel.setText(vg.isDirected() == null ? "混合图" : (vg.isDirected() ? "有向图" : "无向图"));

		this.jSplitPane1.resetToPreferredSizes();
	}

	/**
	 * 部分菜单选项可用
	 * @param itemIsEnable
	 */
	public void showItem(boolean itemIsEnable) {

		saveAsItem.setEnabled(itemIsEnable);
		forceLayoutItem.setEnabled(itemIsEnable);
		CircleLayoutItem.setEnabled(itemIsEnable);
		randomLayoutItem.setEnabled(itemIsEnable);
		gridLayoutItem.setEnabled(itemIsEnable);
		fruchtermanLayoutItem.setEnabled(itemIsEnable);
		radialTreeLayout.setEnabled(itemIsEnable);
		nodeLinkTreeLayoutItem.setEnabled(itemIsEnable);
		noTextItem.setEnabled(itemIsEnable);
		textOnlyItem.setEnabled(itemIsEnable);
		textAndImageItem.setEnabled(itemIsEnable);
		rectangleItem.setEnabled(itemIsEnable);
		roundItem.setEnabled(itemIsEnable);
		roundRectangleItem.setEnabled(itemIsEnable);
		zoomToFitItem.setEnabled(itemIsEnable);
		switchButton.setEnabled(itemIsEnable);
		backgroundButton.setEnabled(itemIsEnable);
		this.repaint();
	}

	/**
	 * 退出选项卡事件
	 */
	private void quitItemActionPerformed(java.awt.event.ActionEvent evt) {
		System.exit(2);
	}

	/**
	 * 关于选项卡事件
	 * 
	 * @param evt
	 */
	private void helpItemActionPerformed(java.awt.event.ActionEvent evt) {
		JFrame helpFrame = new JFrame();
		helpFrame.setLocationRelativeTo(null);
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		helpFrame.setPreferredSize(new Dimension(450, 200));
		JTextArea textArea = new JTextArea();
		textArea.setText("产品版本：BigData-JackieViusalization 1.0.0 201501010230\n专注可视化20年  不变心 看得见");

		helpFrame.add(textArea);
		helpFrame.pack();
		helpFrame.setVisible(true);
	}

	/**
	 * Main函数
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
        //default skin
//		UILib.setPlatformLookAndFeel();

        //mac window style skin, if you want to apply this skin, please add skin.jar under libs into classpath
        try {
            javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				frame = new MainFrame();
				frame.setLocationRelativeTo(null);//设置默认显示位置为居中
				// frame.setSize(700, 600);
				//				frame.addComponentListener(new FrameComponentAdapter(frame));
				frame.setVisible(true);
			}
		});
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JMenu ActionMenu;
	private javax.swing.JMenuItem CircleLayoutItem;
	private javax.swing.JButton backgroundButton;
	private javax.swing.JMenu controllerMenu;
	private javax.swing.JPanel displayPanel;
	private javax.swing.JLabel edgeLabel;
	private javax.swing.JMenu edgeMenu;
	private javax.swing.JLabel edgeNumLabel;
	private javax.swing.JMenu fileMenu;
	private javax.swing.JMenuItem forceLayoutItem;
	private javax.swing.JMenuItem fruchtermanLayoutItem;
	private javax.swing.JMenuItem gridLayoutItem;
	private javax.swing.JMenuItem helpItem;
	private javax.swing.JMenu helpMenu;
	private javax.swing.JMenuItem importFromDBItem;
	private javax.swing.JPanel infoPanel;
	private javax.swing.JLabel isdirectLabel;
	private javax.swing.JMenu jMenu5;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JSplitPane jSplitPane1;
	private javax.swing.JMenu labelMenu;
	private javax.swing.JMenu layoutAlgMenu;
	private javax.swing.JMenuItem noTextItem;
	private javax.swing.JLabel nodeLabel;
	private javax.swing.JMenuItem nodeLinkTreeLayoutItem;
	private javax.swing.JMenu nodeMenu;
	private javax.swing.JLabel nodeNumLabel;
	private javax.swing.JMenuItem openItem;
	private javax.swing.JPanel paramsPanel;
	private javax.swing.JPanel parentPanel;
	private javax.swing.JMenuItem quitItem;
	private javax.swing.JMenuItem radialTreeLayout;
	private javax.swing.JMenuItem randomLayoutItem;
	private javax.swing.JMenuItem rectangleItem;
	private javax.swing.JMenuItem roundItem;
	private javax.swing.JMenuItem roundRectangleItem;
	private javax.swing.JMenuItem saveAsItem;
	private javax.swing.JButton switchButton;
	private javax.swing.JMenuItem textAndImageItem;
	private javax.swing.JMenuItem textOnlyItem;
	private javax.swing.JMenuItem zoomToFitItem;
	// End of variables declaration//GEN-END:variables

	private Display display = null;

	/**
	 * JFrame窗体状态变化
	 */
	private void formWindowStateChanged(java.awt.event.WindowEvent evt) {
		if (evt.getNewState() == 1 || evt.getNewState() == 7) {
			System.out.println("窗口最小化");
		} else if (evt.getNewState() == 0) {
			System.out.println("窗口恢复到初始状态");
		} else if (evt.getNewState() == 6) {
			int width = (int) (this.parentPanel.getSize().getWidth() - this.infoPanel.getSize().getWidth()) - 10;
			int height = (int) this.parentPanel.getSize().getHeight() - 4;

			this.jSplitPane1.setSize(width, height);
			this.repaint();
		}
		if (this.display != null) {
			int width = (int) (this.parentPanel.getSize().getWidth() - this.infoPanel.getSize().getWidth()) - 10;
			int height = (int) this.parentPanel.getSize().getHeight() - 4;
			this.display.setSize(width - this.paramsPanel.getWidth(), height);
		}
	}

	/**
	 * parentPanel窗体变化监听
	 * 
	 * @param evt
	 */
	private void parentPanelComponentResized(java.awt.event.ComponentEvent evt) {

		int width = (int) (this.parentPanel.getSize().getWidth() - this.infoPanel.getSize().getWidth()) - 10;
		int height = (int) this.parentPanel.getSize().getHeight() - 4;

		this.jSplitPane1.setSize(width, height);

		if (this.display != null) {
			this.display.setSize(width - this.paramsPanel.getWidth(), height);
		}

		// this.paramsPanel.setSize(300, height);
		//
		// width = width - this.displayPanel.getWidth();
		//
		// this.displayPanel.setSize(width, height);

	}

	/**
	 * 废弃
	 * 
	 * @author Jack
	 * 
	 */
	public static class FrameComponentAdapter extends ComponentAdapter {
		private MainFrame mainFrame;

		public FrameComponentAdapter(MainFrame mainFrame) {
			this.mainFrame = mainFrame;
		}

		@Override
		public void componentResized(ComponentEvent e) {

			int width = (int) (this.mainFrame.parentPanel.getSize().getWidth() - this.mainFrame.infoPanel.getSize().getWidth()) - 10;
			int height = (int) this.mainFrame.parentPanel.getSize().getHeight() - 4;

			this.mainFrame.jSplitPane1.setSize(width, height);
			// ControllerTabUtils.zoomToFitAction();
		}
	}

	public void setDisplay(JComponent jcomonent) {
		javax.swing.GroupLayout displayPanelLayout = new javax.swing.GroupLayout(jcomonent);
		jcomonent.setLayout(displayPanelLayout);
		displayPanelLayout.setHorizontalGroup(displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 590, Short.MAX_VALUE));
		displayPanelLayout.setVerticalGroup(displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 438, Short.MAX_VALUE));
	}

	public void setParameter(JComponent jcomonent) {
		javax.swing.GroupLayout paramsPanelLayout = new javax.swing.GroupLayout(jcomonent);
		jcomonent.setLayout(paramsPanelLayout);
		paramsPanelLayout.setHorizontalGroup(paramsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
		paramsPanelLayout.setVerticalGroup(paramsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 438, Short.MAX_VALUE));

	}
}