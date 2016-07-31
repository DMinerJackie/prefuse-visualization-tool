package org.apache.Jack1118_1130;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import org.apache.widget.GraphView.GraphMenuAction;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.DragControl;
import prefuse.controls.FocusControl;
import prefuse.controls.PanControl;
import prefuse.controls.WheelZoomControl;
import prefuse.controls.ZoomControl;
import prefuse.controls.ZoomToFitControl;
import prefuse.data.Graph;
import prefuse.data.Table;
import prefuse.data.expression.Predicate;
import prefuse.data.expression.parser.ExpressionParser;
import prefuse.data.io.DataIOException;
import prefuse.data.io.sql.ConnectionFactory;
import prefuse.data.io.sql.DatabaseDataSource;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.LabelRenderer;
import prefuse.util.ColorLib;
import prefuse.util.GraphLib;
import prefuse.visual.VisualItem;
/**
 * 连接数据库，通过填写在配置窗口填写相关参数完成赋值，运行程序生成图形
 * @author Jack
 *
 */
public class Example1124_1 {
	public static Visualization vis = new Visualization();
	public static Example1124_config config = new Example1124_config();//存储、获取参数对象
	public static JFrame jf = new JFrame();
	//标签显示
	public static JLabel nodeLabel = new JLabel("节点查询:");
	public static JLabel edgeLabel = new JLabel("边查询:");
	public static JLabel strConfigLabel = new JLabel("配置字符串:");
	public static JLabel databaseNameLabel = new JLabel("数据库名称:");
	public static JLabel usernameLabel = new JLabel("用户名:");
	public static JLabel passwordLabel = new JLabel("密码:");
	public static JLabel portNumberLabel = new JLabel("端口号:");
	
	//文本框
	public static JTextField nodeText = new JTextField();
	public static JTextField edgeText = new JTextField();
	public static JTextField strConfigText = new JTextField();
	public static JTextField databaseNameText = new JTextField();
	public static JTextField usernameText = new JTextField();
	public static JTextField passwordText = new JTextField();
	public static JTextField portNumberText = new JTextField();
	
	/**
	 * @param args
	 * @throws DataIOException 
	 */
	public static void main(String[] args) throws DataIOException {
		//1.构建显示画面   2.填写参数配置    3.配置传值到主界面    4.主界面图形展示
		
		
		//-----------1、构建主界面-----------
		 JMenu dataMenu = new JMenu("数据导入");//添加菜单按钮
		 final JMenuItem dataItem = new JMenuItem("连接数据库");
		 dataItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final JFrame second = new JFrame("second");
                second.setSize(400,400);
                second.setLayout(null);
                strConfigLabel.setBounds(50, 20, 80, 30);
                strConfigText.setBounds(130, 20, 150, 30);
                second.add(strConfigLabel);
                second.add(strConfigText);
                
                databaseNameLabel.setBounds(50, 60, 80, 30);
                databaseNameText.setBounds(130, 60, 150, 30);
                second.add(databaseNameLabel);
                second.add(databaseNameText);
                
                usernameLabel.setBounds(50, 100, 80, 30);
                usernameText.setBounds(130, 100, 150, 30);
                second.add(usernameLabel);
                second.add(usernameText);
                
                passwordLabel.setBounds(50, 140, 80, 30);
                passwordText.setBounds(130, 140, 150, 30);
                second.add(passwordLabel);
                second.add(passwordText);
                
                portNumberLabel.setBounds(50, 180, 80, 30);
                portNumberText.setBounds(130, 180, 150, 30);
                second.add(portNumberLabel);
                second.add(portNumberText);
                
                nodeLabel.setBounds(50, 220, 80, 30);
                nodeText.setBounds(130, 220, 150, 30);
                second.add(nodeLabel);
                second.add(nodeText);
                
                edgeLabel.setBounds(50, 260, 80, 30);
                edgeText.setBounds(130, 260, 150, 30);
                second.add(edgeLabel);
                second.add(edgeText);
                
                JButton ok = new JButton("完成配置");
                ok.setBounds(70, 300, 100, 30);
        		second.add(ok);
        		second.setVisible(true);
        		ok.addActionListener(new ActionListener() {
        			
        			@Override
        			public void actionPerformed(ActionEvent e) {
        				if(nodeText.getText()!=null && edgeText.getText()!=null){
        					configPass();
        					System.out.println("node:"+ config.getNodeSql());
        					try {
								actionEvent();
//								second.dispose();这种关闭方式也可以
								second.dispatchEvent(new WindowEvent(second,WindowEvent.WINDOW_CLOSING));//关闭子窗口
								visual();
							} catch (DataIOException e1) { 
								e1.printStackTrace();
							}
        				}
        			}
        		});
                
            }
            
         });
		 JMenuBar bar = new JMenuBar();
		 bar.add(dataMenu);
		 dataMenu.add(dataItem);
		
		jf.setSize(new Dimension(800, 600));
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setJMenuBar(bar);
//		jf.pack();
		jf.setVisible(true);
		
	}
	
	
	/**
	 * 存储配置界面用户输入的值
	 */
	private static void configPass() {
		config.setNodeSql(nodeText.getText());
		config.setEdgeSql(edgeText.getText());
		config.setStrConfig(strConfigText.getText());
		config.setDatabaseName(databaseNameText.getText());
		config.setUsername(usernameText.getText());
		config.setPassword(passwordText.getText());
		config.setPortNumber(portNumberText.getText());
	}
	
	
	
	/**
	 * 连接数据库并添加相应效果渲染和动作
	 * @throws DataIOException
	 */
	public static void actionEvent() throws DataIOException{
		
		String driver = config.getStrConfig().toString();
		String url = "jdbc:sqlserver://localhost:"+config.getPortNumber().toString()+";DatabaseName="+config.getDatabaseName().toString()+"";
		String username = config.getUsername().toString();
		String password = config.getPassword().toString();
		DatabaseDataSource dbds = null;
		try {
			dbds = ConnectionFactory.getDatabaseConnection(driver, url, username, password);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		System.out.println(config.getEdgeSql());
		Table nodes = dbds.getData(config.getNodeSql().toString());
		Table edges = dbds.getData(config.getEdgeSql().toString());
		
		Graph graph = new Graph(nodes, edges, false, "id", "sid", "tid");
		
		vis.add("graph", graph);

		LabelRenderer label = new LabelRenderer("name");
		label.setRoundedCorner(10, 10);
		
		 
		vis.setRendererFactory(new DefaultRendererFactory(label));
		
		int[] palette = new int[]{ColorLib.rgb(255, 180, 180),ColorLib.rgb(190, 190, 255)};
		DataColorAction fill = new DataColorAction("graph.nodes" , "gender" , Constants.NOMINAL, VisualItem.FILLCOLOR,palette);
		ColorAction text = new ColorAction("graph.nodes", VisualItem.TEXTCOLOR, ColorLib.gray(0));
		ColorAction edges1 = new ColorAction("graph.edges", VisualItem.STROKECOLOR, ColorLib.gray(200));
		
		ActionList color = new ActionList();
		color.add(fill);
		color.add(text);
		color.add(edges1);
		
		ActionList layout = new ActionList(Activity.INFINITY);
		layout.add(new ForceDirectedLayout("graph"));
		layout.add(new RepaintAction());
		
		vis.putAction("color", color);
		vis.putAction("layout", layout);
		
	}
	
	
	/**
	 * 添加控制器，显示图形
	 */
	public static void visual(){
		
		Display display = new Display(vis);
		display.setSize(700, 600);
		display.pan(250, 250);
		display.addControlListener(new DragControl());
		display.addControlListener(new PanControl());
		display.addControlListener(new ZoomControl());
		display.addControlListener(new WheelZoomControl());
		display.addControlListener(new FocusControl(1));
		display.addControlListener(new ZoomToFitControl());
		jf.add(display);
		vis.run("color");
		vis.run("layout");
	}
	
}
