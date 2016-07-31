package org.apache.Jack1201_1230;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.assignment.DataShapeAction;
import prefuse.action.assignment.FontAction;
import prefuse.action.assignment.ShapeAction;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.Control;
import prefuse.controls.ControlAdapter;
import prefuse.controls.DragControl;
import prefuse.controls.FocusControl;
import prefuse.controls.PanControl;
import prefuse.controls.WheelZoomControl;
import prefuse.controls.ZoomControl;
import prefuse.controls.ZoomToFitControl;
import prefuse.data.Graph;
import prefuse.data.io.GraphMLReader;
import prefuse.data.query.SearchQueryBinding;
import prefuse.render.AxisRenderer;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;
import prefuse.render.LabelRenderer;
import prefuse.render.Renderer;
import prefuse.render.RendererFactory;
import prefuse.render.ShapeRenderer;
import prefuse.util.ColorLib;
import prefuse.util.FontLib;
import prefuse.util.ui.JFastLabel;
import prefuse.util.ui.JSearchPanel;
import prefuse.visual.VisualGraph;
import prefuse.visual.VisualItem;
/**
 * 导入socialnet.xml数据集按照性别进行划分并展现
 * 在panel中如何添加box和display，当鼠标经过点时会显示该节点某个属性的信息在JFastLabel上
 * 针对Example1211_1.java运行会在控制台报错进行改进，原因在于当鼠标经过边时并没有文本要显示
 * @author Jack
 *
 */
public class Example1212_1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//------------   1    ------------
		Graph graph = null;
		try {
			graph = new GraphMLReader().readGraph("src/org/apache/Jack1118_1130/socialnet.xml");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error loading");
			System.exit(1);
		}
		
		//------------   2    ------------
		Visualization vis = new Visualization();
		vis.add("graph", graph);

		//------------   3    ------------
		LabelRenderer label = new LabelRenderer("name");
		label.setRoundedCorner(10, 10);

		vis.setRendererFactory(new DefaultRendererFactory(label));
		
		//------------   4    ------------
		int[] palette = new int[]{ColorLib.rgb(255, 180, 180),ColorLib.rgb(190, 190, 255)};
		DataColorAction fill = new DataColorAction("graph.nodes" , "gender" , Constants.NOMINAL, VisualItem.FILLCOLOR,palette);
		ColorAction text = new ColorAction("graph.nodes", VisualItem.TEXTCOLOR, ColorLib.gray(0));
		ColorAction edges = new ColorAction("graph.edges", VisualItem.STROKECOLOR, ColorLib.gray(200));
		
		ActionList color = new ActionList();
		color.add(fill);
		color.add(text);
		color.add(edges);
		
		ActionList layout = new ActionList(Activity.INFINITY);
		layout.add(new ForceDirectedLayout("graph"));
		layout.add(new RepaintAction());
		
		vis.putAction("color", color);
		vis.putAction("layout", layout);
		
		//------------   5    ------------
		final Display display = new Display(vis);
//		display.setSize(700, 600);
		display.pan(250, 250);
		display.addControlListener(new DragControl());
		display.addControlListener(new PanControl());
		display.addControlListener(new ZoomControl());
		display.addControlListener(new WheelZoomControl());
		display.addControlListener(new FocusControl(1));
		
		display.addControlListener(new ZoomToFitControl());
		//------------   6    ------------
		
		
		JPanel panel = new JPanel();
		JTextArea textField = new JTextArea("甲：我有钱，我就是任性\n乙：那你为什么一百块钱都不给我\n甲：。。。。");
//		JSearchPanel searcher = searchQ.createSearchPanel();//创建搜索面板模块
//        searcher.setLabelText("Candidate: ");
//        searcher.setBorder(BorderFactory.createEmptyBorder(5,5,5,0));
        
        final JFastLabel fast = new JFastLabel("fast label");
        fast.setFont(FontLib.getFont("Times New Roman", 16));
        fast.setPreferredSize(new Dimension(75,20));
        Control hoverc = new ControlAdapter() {
            public void itemEntered(VisualItem item, MouseEvent evt) {
                if ( item.isInGroup("graph.nodes") ) {
                	System.out.println("hello");
                  fast.setText(item.getString("name"));
                  item.setFillColor(item.getStrokeColor());
                  item.setStrokeColor(ColorLib.rgb(0,0,0));
                  display.setToolTipText(item.getString("name"));//添加文本提示功能
                  item.getVisualization().repaint();
                }
            }
            public void itemExited(VisualItem item, MouseEvent evt) {
                if ( item.isInGroup("graph") ) {
                  fast.setText("fast label");
                  item.setFillColor(item.getEndFillColor());
                  item.setStrokeColor(item.getEndStrokeColor());
                  item.getVisualization().repaint();
                }
            }
        };
        display.setSize(500, 400);
        display.addControlListener(hoverc);
		
        Box info = new Box(BoxLayout.X_AXIS);
        info.add(Box.createHorizontalStrut(5));
        info.add(fast);
        
        Box search = new Box(BoxLayout.X_AXIS);
        search.add(Box.createHorizontalStrut(5));
        search.add(textField);
        
        panel.add(info, BorderLayout.NORTH);//将上面的infoBox、m_display、slider、radioBox分别布局到JPanel中
        panel.add(display, BorderLayout.CENTER);
        panel.add(search, BorderLayout.SOUTH);
        
        
		JFrame jf = new JFrame();
		jf.setSize(800, 600);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setContentPane(panel);
		jf.pack();
		jf.setVisible(true);
		
		vis.run("color");
		vis.run("layout");
		
	}

}
