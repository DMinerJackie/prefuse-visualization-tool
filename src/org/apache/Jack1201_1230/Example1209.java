package org.apache.Jack1201_1230;

import javax.swing.JFrame;
/**
 * @author Jack
 *在界面中现有具有html效果的页面
 */
import javax.swing.*;

public class Example1209 extends JPanel {

  public static final String markup = "<html>line 1<p><font color=blue size=+2>"
                                    + "big blue</font> line 2<p>line 3</html>";

  public static void main(String argv[]) {
    JPanel p = new JPanel(new java.awt.GridLayout(0, 1));
    p.add(new JLabel(markup));
    p.add(new java.awt.Label(markup));

    JFrame f = new JFrame("HtmlLabel");
    f.setContentPane(p);
    f.setSize(600, 200);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setVisible(true);
  }  
}