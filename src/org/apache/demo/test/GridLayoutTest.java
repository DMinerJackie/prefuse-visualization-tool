package org.apache.demo.test;

import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GridLayoutTest implements ActionListener {
	JPanel p1, p2, p3, p4;
	int i = 1;
	JFrame f;

	public GridLayoutTest() {
		f = new JFrame();// 当做top-level组件
		Container contentPane = f.getContentPane();
		contentPane.setLayout(new GridLayout(2, 1));

		p1 = new JPanel();
		Button b = new Button("Change Card");
		b.addActionListener(this);// 当按下"Change Card"时，进行事件监听，将会有系统操作产生。
		p1.add(b); // 处理操作在52-64行.
		contentPane.add(p1);

		p2 = new JPanel();
		p2.setLayout(new FlowLayout());
		p2.add(new JButton("first"));
		p2.add(new JButton("second"));
		p2.add(new JButton("third"));

		p3 = new JPanel();
		p3.setLayout(new GridLayout(3, 1));
		p3.add(new JButton("fourth"));
		p3.add(new JButton("fifth"));
		p3.add(new JButton("This is the last button"));

		p4 = new JPanel();
		p4.setLayout(new CardLayout());
		p4.add("one", p2);
		p4.add("two", p3);
		/*
		 * 要显示CardLayout的卡片，除了用show(Container parent,String name)这个方法外
		 * ，也可试试first(
		 * Container),next(Container),previous(Container),last(Container)这
		 * 四个方法，一样可以达到显示效果。
		 */
		((CardLayout) p4.getLayout()).show(p4, "one");

		contentPane.add(p4);

		f.setTitle("CardLayout");
		f.pack();
		f.setVisible(true);

		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

	}

	public void actionPerformed(ActionEvent event) {
		switch (i) {
		case 1:
			((CardLayout) p4.getLayout()).show(p4, "two");
			break;
		case 2:
			((CardLayout) p4.getLayout()).show(p4, "one");
			break;
		}
		i++;
		if (i == 3)
			i = 1;
		f.validate();
	}

	public static void main(String[] args) {
		new GridLayoutTest();
	}
}
