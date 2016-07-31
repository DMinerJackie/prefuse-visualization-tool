package org.apache.Jack1216_1230;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import org.apache.widget.TreeMap.NodeRenderer;

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
import prefuse.data.io.GraphMLReader;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;
import prefuse.render.LabelRenderer;
import prefuse.render.PolygonRenderer;
import prefuse.render.RendererFactory;
import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;
import prefuse.visual.expression.InGroupPredicate;
/**
 * 导入socialnet.xml数据集按照性别进行划分并展现;曲线展示
 * @author Jack
 *
 */
public class Example1216_2 {

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
//		label.setHorizontalPadding(5);
//		label.setVerticalPadding(5);

		DefaultRendererFactory rf = new DefaultRendererFactory(label);
		MyEdgeRenderer edgeRenderer = new MyEdgeRenderer(Constants.EDGE_TYPE_CURVE, Constants.EDGE_ARROW_FORWARD);
		rf.add(new InGroupPredicate("graph.edges"), edgeRenderer);
		
//		PolygonRenderer pr = new PolygonRenderer(Constants.POLY_TYPE_CURVE);
//		rf.add(new InGroupPredicate("graph.nodes"), pr);
		vis.setRendererFactory(rf);
		
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
		
	}

}



class MyEdgeRenderer extends EdgeRenderer
{
    public MyEdgeRenderer(int edgeType, int arrowType)
    {
        super(edgeType,arrowType);
    }
    public void render(Graphics2D g, VisualItem item)
    {
        super.render(g, item);
        // render the edge arrow head, if appropriate
        if ( m_curArrow != null )
        {
            g.setPaint(ColorLib.getColor(item.getStrokeColor()));
            super.setArrowHeadSize(10, 10);
            g.fill(m_curArrow);
        }
    }
}