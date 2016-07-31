package org.apache.demo.test;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JToolBar;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Test1 {

	private JFrame jFrame = null;
	private JToolBar jToolBar = null;
	private JSlider jSlider = null;
	private JSlider jSlider1 = null;
	private JSlider jSlider2 = null;
	int rgb[] = new int[] { 0, 0, 0 };

	/**
	 * 初始化JFrame This method initializes jFrame
	 * 
	 * @return javax.swing.JFrame
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setSize(new Dimension(490, 271));
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jFrame.getContentPane().setBackground(new Color(0, 0, 0));
			jFrame.getContentPane().add(getJToolBar(), BorderLayout.NORTH);
			jFrame.setVisible(true);
		}
		return jFrame;
	}

	/**
	 * 往JToolBar添加3个JSider This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setOrientation(0);
			jToolBar.setVisible(true);
			jToolBar.add(getJSlider());
			jToolBar.add(getJSlider1());
			jToolBar.add(getJSlider2());
		}
		return jToolBar;
	}

	// 下面是3个JSlider
	/**
	 * This method initializes jSlider
	 * 
	 * @return javax.swing.JSlider
	 */
	private JSlider getJSlider() {
		if (jSlider == null) {
			jSlider = new JSlider(0, 255, 0);
			jSlider.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					rgb[0] = ((JSlider) e.getSource()).getValue();
					jFrame.getContentPane().setBackground(new Color(rgb[0], rgb[1], rgb[2]));
					jFrame.setTitle(rgb[0] + "," + rgb[1] + "," + rgb[2]);
				}
			});
		}
		return jSlider;
	}

	/**
	 * This method initializes jSlider1
	 * 
	 * @return javax.swing.JSlider
	 */
	private JSlider getJSlider1() {
		if (jSlider1 == null) {
			jSlider1 = new JSlider(0, 255, 0);
			jSlider1.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					rgb[1] = ((JSlider) e.getSource()).getValue();
					jFrame.getContentPane().setBackground(new Color(rgb[0], rgb[1], rgb[2]));
					jFrame.setTitle(rgb[0] + "," + rgb[1] + "," + rgb[2]);
				}
			});
		}
		return jSlider1;
	}

	/**
	 * This method initializes jSlider1
	 * 
	 * @return javax.swing.JSlider
	 */
	private JSlider getJSlider2() {
		if (jSlider2 == null) {
			jSlider2 = new JSlider(0, 255, 0);
			jSlider2.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					rgb[2] = ((JSlider) e.getSource()).getValue();
					jFrame.getContentPane().setBackground(new Color(rgb[0], rgb[1], rgb[2]));
					jFrame.setTitle(rgb[0] + "," + rgb[1] + "," + rgb[2]);
				}
			});
		}
		return jSlider2;
	}

	// 主方法
	public static void main(String[] args) {
		Test1 t1 = new Test1();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t1.getJFrame();
	}
}
