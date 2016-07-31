package org.apache.Jack1201_1230;

import java.awt.Font;
import java.awt.Rectangle;
import java.util.Iterator;

import javax.swing.JFrame;

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
import prefuse.controls.DragControl;
import prefuse.controls.FocusControl;
import prefuse.controls.PanControl;
import prefuse.controls.WheelZoomControl;
import prefuse.controls.ZoomControl;
import prefuse.controls.ZoomToFitControl;
import prefuse.data.Graph;
import prefuse.data.io.GraphMLReader;
import prefuse.render.AxisRenderer;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;
import prefuse.render.LabelRenderer;
import prefuse.render.Renderer;
import prefuse.render.RendererFactory;
import prefuse.render.ShapeRenderer;
import prefuse.util.ColorLib;
import prefuse.visual.VisualGraph;
import prefuse.visual.VisualItem;
/**
 * 导入socialnet.xml数据集按照性别进行划分并展现
 * 在具有点和边的图形中展现出节点上既有文本信息也有图片信息
 * @author Jack
 *
 */
public class Example1215_5 {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		
		//------------   1    ------------
		Graph graph = null;
		try {
			graph = new GraphMLReader().readGraph("src/org/apache/widget/socialnet1.xml");
			graph.addColumn("image", "CONCAT('src/org/apache/widget/images1/',id,'.jpg')");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error loading");
			System.exit(1);
		}
		
		//------------   2    ------------
		Visualization vis = new Visualization();
		

		//------------   3    ------------
		LabelRenderer label = new LabelRenderer("name","image");
		label.setImagePosition(Constants.RIGHT);
        label.setVerticalAlignment(Constants.BOTTOM);
        label.setHorizontalPadding(0);
        label.setVerticalPadding(0);
        label.setMaxImageDimensions(20,20);
        vis.setRendererFactory(new DefaultRendererFactory(label));

		vis.add("graph", graph);
		
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
		Display display = new Display(vis);
		display.setSize(700, 600);
		display.pan(250, 250);
		display.addControlListener(new DragControl());
		display.addControlListener(new PanControl());
		display.addControlListener(new ZoomControl());
		display.addControlListener(new WheelZoomControl());
		display.addControlListener(new FocusControl(1));
		
		display.addControlListener(new ZoomToFitControl());
		//------------   6    ------------
		JFrame jf = new JFrame();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(display);
		jf.pack();
		jf.setVisible(true);
		
		vis.run("color");
		vis.run("layout");
		
//		Thread.sleep(5000);
//		LabelRenderer label1 = new LabelRenderer("name");
//		vis.setRendererFactory(new DefaultRendererFactory(label1));
//		vis.run("color");
//		vis.run("layout");
		
	}

}
