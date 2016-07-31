package org.apache.test;

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
import prefuse.action.assignment.ShapeAction;
import prefuse.action.layout.AxisLayout;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.DragControl;
import prefuse.controls.FocusControl;
import prefuse.controls.PanControl;
import prefuse.controls.WheelZoomControl;
import prefuse.controls.ZoomControl;
import prefuse.controls.ZoomToFitControl;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Table;
import prefuse.data.io.GraphMLReader;
import prefuse.render.AxisRenderer;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;
import prefuse.render.LabelRenderer;
import prefuse.render.Renderer;
import prefuse.render.RendererFactory;
import prefuse.render.ShapeRenderer;
import prefuse.util.ColorLib;
import prefuse.util.GraphLib;
import prefuse.visual.VisualItem;
import prefuse.visual.expression.VisiblePredicate;
/**
 * Table示例
 * @author Jack
 *
 */
public class Example1120 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//------------   1    ------------
		Visualization vis = new Visualization();
		Table table = null;
		try {
			table = new Table(4,4);
			table.addRow();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error loading");
			System.exit(1);
		}
		
		//------------   2    ------------
		vis.addTable("graph", table);

		//------------   3    ------------
//		LabelRenderer label = new LabelRenderer("name");
//		label.setRoundedCorner(10, 10);
		
		ShapeRenderer m_shapeR = new ShapeRenderer(8);
		vis.setRendererFactory(new DefaultRendererFactory(m_shapeR));
		
		AxisLayout x_axis = new AxisLayout("graph", "xxx", 
                Constants.X_AXIS, VisiblePredicate.TRUE);
        vis.putAction("x", x_axis);
        
        AxisLayout y_axis = new AxisLayout("graph", "yyy", 
                Constants.Y_AXIS, VisiblePredicate.TRUE);
        vis.putAction("y", y_axis);

        ColorAction strColor = new ColorAction("graph", VisualItem.STROKECOLOR, ColorLib.rgb(100,100,255));
        ColorAction fillColor = new ColorAction("graph", VisualItem.FILLCOLOR, ColorLib.rgb(200,90,10));
        vis.putAction("strColor", strColor);
        vis.putAction("fillColor", fillColor);
        
        
        ActionList draw = new ActionList();
        draw.add(x_axis);
        draw.add(y_axis);
        draw.add(strColor);
        draw.add(fillColor);
        draw.add(new RepaintAction());
        vis.putAction("draw", draw);
		
		//------------   4    ------------
//		int[] palette = new int[]{ColorLib.rgb(255, 180, 180),ColorLib.rgb(190, 190, 255)};
//		DataColorAction fill = new DataColorAction("graph.nodes" , "gender" , Constants.NOMINAL, VisualItem.FILLCOLOR,palette);
//		ColorAction text = new ColorAction("graph.nodes", VisualItem.TEXTCOLOR, ColorLib.gray(0));
//		ColorAction edges = new ColorAction("graph.edges", VisualItem.STROKECOLOR, ColorLib.gray(200));
//		
//		int[] shapes = new int[]{ Constants.SHAPE_RECTANGLE, Constants.SHAPE_DIAMOND };
//		DataShapeAction shape1 = new DataShapeAction("graph.nodes", "gender", shapes);
//		
//		ActionList color = new ActionList();
//		color.add(fill);
//		color.add(text);
//		color.add(edges);
//		color.add(shape1);
		
		
//		vis.putAction("color", color);
//		vis.putAction("layout", layout);
		
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
		vis.run("draw");
		
	}

}
