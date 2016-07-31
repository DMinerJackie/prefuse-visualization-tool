package org.apache.Jack1201_1230;

import javax.swing.JFrame;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.assignment.DataShapeAction;
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
import prefuse.visual.VisualItem;
import prefuse.visual.expression.InGroupPredicate;
/**
 * 导入socialnet.xml数据集按照性别赋予不同的图形表示
 * 根据图形中的name属性对其进行不同形状的赋值，如果超过形状库的个数则会有些是重复的形状。
 * @author Jack
 *
 */
public class Example1215_2 {

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
		
		
		//------------   4    ------------
		int[] palette = new int[]{ColorLib.rgb(255, 180, 180),ColorLib.rgb(190, 190, 255)};
		DataColorAction fill = new DataColorAction("graph.nodes" , "gender" , Constants.NOMINAL, VisualItem.FILLCOLOR,palette);
		ColorAction text = new ColorAction("graph.nodes", VisualItem.TEXTCOLOR, ColorLib.gray(0));
		ColorAction edges = new ColorAction("graph.edges", VisualItem.STROKECOLOR, ColorLib.gray(200));
//		
		int[] shapes = new int[]{ Constants.SHAPE_RECTANGLE, Constants.SHAPE_DIAMOND};
		DataShapeAction shape = new DataShapeAction("graph.nodes", "name");
		ShapeRenderer shapeR = new ShapeRenderer(30);
		shapeR.star(10, 10, 10);
		shapeR.setRenderType(3);
		
		ActionList color = new ActionList();
		color.add(fill);
		color.add(text);
		color.add(edges);
		color.add(shape);
		
//		label.setTextField("name");
		DefaultRendererFactory drf = new DefaultRendererFactory();
		drf.setDefaultRenderer(label);
		drf.add(new InGroupPredicate("graph.nodes"), shapeR);
//		drf.add(new InGroupPredicate("graph.nodes"), label);
		 
		vis.setRendererFactory(drf);
		
		ActionList layout = new ActionList(Activity.INFINITY);
		layout.add(new ForceDirectedLayout("graph"));
		layout.add(new RepaintAction());
		
		vis.putAction("color", color);
		vis.putAction("layout", layout);
		
		//------------   5    ------------
		Display display = new Display(vis);
		display.setSize(750, 700);
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
		
	}

}
