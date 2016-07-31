package org.apache.Jack1118_1130;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * 窗口之间传值示例
 * @author Jack
 *
 */
public class Example1124 {
    public static void main(String[] args) {
        new FirstFrame().setVisible(true);
    }
}
 
class  FirstFrame extends JFrame{
    JTextField name;
    public FirstFrame() {
        super("窗体之间数据传递");
        this.setSize(330, 200);
        this.setLayout(null);
        this.setLocation(100, 50);
        JLabel a=new JLabel("姓名:");
        name=new JTextField("姓   名",10);
        //按钮
        JButton b=new JButton("传递");
        //添加按钮事件
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                new SecondFrame(FirstFrame.this).setVisible(true);
                FirstFrame.this.setVisible(false);
            }
        });
        JPanel pane=new JPanel();
        pane.add(a);
        pane.add(name);
        pane.add(b);
        setContentPane(pane);
    }
}
class  SecondFrame extends JFrame{
    public SecondFrame(FirstFrame frm) {
        super("显示数据");
        this.setSize(330, 200);
        this.setLayout(null);
        this.setLocation(100, 50);
        JLabel a=new JLabel(frm.name.getText(),10);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel pane=new JPanel();
        pane.add(a);
        setContentPane(pane);
    }
}
