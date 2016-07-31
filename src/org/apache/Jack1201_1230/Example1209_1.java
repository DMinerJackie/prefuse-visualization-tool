package org.apache.Jack1201_1230;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import prefuse.Visualization;
import prefuse.util.FontLib;
import prefuse.util.ui.JFastLabel;
/**
 * 手动创建点、边，并构成一个图形;FocusControl单击ViusalItem后执行某一action
 * 当鼠标经过点或边时将大小设置为8，鼠标移出后大小设为1
 * @author Jack
 *
 */
public class Example1209_1{
	public static Visualization vis = new Visualization();
    
    public static void main(String[] argv) {
    	
		final JFastLabel fast = new JFastLabel("*************RRRRRGGGGGBBBBB**************");//为树状图添加一个搜索面板
		fast.setPreferredSize(new Dimension(350, 20));//长350 宽20
        fast.setVerticalAlignment(SwingConstants.BOTTOM);
        fast.setBorder(BorderFactory.createEmptyBorder(3,0,0,0));
        fast.setFont(FontLib.getFont("Tahoma", Font.PLAIN, 16));
        fast.setBackground(Color.WHITE);
        fast.setForeground(Color.BLACK);
		
				
		Box box = new Box(BoxLayout.X_AXIS);
        box.add(Box.createHorizontalStrut(10));
        box.add(fast);
        box.add(Box.createHorizontalGlue());
        box.add(Box.createHorizontalStrut(3));
        box.setBackground(Color.WHITE);
    	
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 700);
//        frame.setContentPane(panel);
        frame.setContentPane(box);
        frame.setVisible(true);
    }
    
} // end of class AggregateDemo