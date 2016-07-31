package org.apache.demo.test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class JColorChooserTest extends JFrame {
	Container c;
	JButton btn = new JButton("选背景色");
	Color color = new Color(200, 200, 200);

	public JColorChooserTest() {

		c = getContentPane();
		c.setLayout(new FlowLayout());
		c.add(btn);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = JColorChooser.showDialog(null, "请选择你喜欢的颜色", color);
				if (color == null)
					color = Color.lightGray;
				c.setBackground(color);
				c.repaint();
			}
		});

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(new Dimension(400, 300));
		show();
	}

	public static void main(String[] args) {
		JColorChooserTest ge = new JColorChooserTest();
	}
}
