package org.apache.test;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class JFrameTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame jf = new JFrame("jack");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		JTextArea textArea = new JTextArea();

		panel.setLayout(new GridLayout());
		textArea.setText("test");
		// 当TextArea里的内容过长时生成滚动条
		panel.add(new JScrollPane(textArea));
		jf.add(panel);
		jf.setSize(500, 500);
		jf.setVisible(true);
		System.out.println("end");

	}

}
