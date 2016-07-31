package org.apache.Jack1216_1230;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import javax.swing.JFrame;
import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.ShapeAction;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.ControlAdapter;
import prefuse.controls.DragControl;
import prefuse.controls.FocusControl;
import prefuse.controls.PanControl;
import prefuse.controls.WheelZoomControl;
import prefuse.controls.ZoomControl;
import prefuse.controls.ZoomToFitControl;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.expression.Predicate;
import prefuse.data.expression.parser.ExpressionParser;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;
import prefuse.render.ShapeRenderer;
import prefuse.util.ColorLib;
import prefuse.visual.VisualGraph;
import prefuse.visual.VisualItem;
import prefuse.visual.expression.InGroupPredicate;
/**
 * 手动创建点、边，并构成一个图形;改变节点默认形状;通过过滤器设定节点度数超过2的不能interactive;当选中某个控件（如点或边）时触发某一事件
 * 创建的是有向图，并添加箭头指示
 * @author Jack
 *
 */
public class Example1216_3{

    
    public static void main(String[] argv) {
    	Visualization vis = new Visualization();
    	Graph g = new Graph(true);
    	for(int i = 0; i<3; i++){
    		Node n1 = g.addNode();
    		Node n2 = g.addNode();
    		Node n3 = g.addNode();
    		g.addEdge(n1, n2);
    		g.addEdge(n2, n3);
    		g.addEdge(n3, n1);
    	}
    	g.addEdge(0, 3);
    	g.addEdge(3, 6);
    	g.addEdge(6, 0);
//    	g.addNode();
    	vis.add("graph", g);

    	ShapeRenderer renderer = new ShapeRenderer(10);
    	EdgeRenderer edgeRenderer = new EdgeRenderer(Constants.EDGE_TYPE_LINE,Constants.EDGE_ARROW_FORWARD);
    	DefaultRendererFactory drf = new DefaultRendererFactory();
    	drf.add(new InGroupPredicate("graph.edges"), edgeRenderer);
    	vis.setRendererFactory(drf);
    	
    	ColorAction nodeFill = new ColorAction("graph.nodes", VisualItem.FILLCOLOR, ColorLib.rgb(10, 150, 220));
		ColorAction edgesStroke = new ColorAction("graph.edges", VisualItem.STROKECOLOR, ColorLib.rgb(100, 80, 180));
		ColorAction nodeHighlight = new ColorAction("graph.nodes", VisualItem.HIGHLIGHT, ColorLib.rgb(10, 150, 220));
		
		ShapeAction  shape = new ShapeAction("graph.nodes",Constants.SHAPE_CROSS); //设置节点形状
		
		
		ActionList color = new ActionList();
		color.add(nodeFill);
		color.add(edgesStroke);
		color.add(shape);
		
		ActionList layout = new ActionList(Activity.INFINITY);
        layout.add(color);
        layout.add(new ForceDirectedLayout("graph"));
        layout.add(new RepaintAction());
		
        Point p = new Point(100,200);
        p.move(300, 400);
        
        
        Predicate pCount =(Predicate)ExpressionParser.parse("degree()>2");
        
        
		Display display = new Display(vis);
		display.setSize(400, 500);
		display.pan(250, 250);
		display.animatePanAbs(230, 220, 2000);
		System.out.println(display.getTransform());
		
		display.rotate(p, 60);
		
		display.addControlListener(new DragControl());
		display.addControlListener(new PanControl());
		display.addControlListener(new ZoomControl());
		display.addControlListener(new WheelZoomControl());
		display.addControlListener(new FocusControl(1));
		display.addControlListener(new ZoomToFitControl());
		
		vis.removeGroup("graph");
        VisualGraph vg = vis.addGraph("graph", g);
        Iterator nodes = vg.nodes();
        display.addControlListener(new ControlAdapter() {
            public void itemEntered(VisualItem item, MouseEvent e) {
                System.out.println("伦家已经是:" + item.getGroup()+"的人了");
            }
            public void itemExited(VisualItem item, MouseEvent e) {
            	System.out.println("哦，那杰哥再找找-_-");
            }
        });//为组件添加监控并作相应的响应
        
        vis.setInteractive("graph.nodes", pCount, false);
//        vis.setValue("graph.nodes", null, VisualItem.SIZE,
//               4);//设置节点形状
		
		vis.putAction("color", color);
		vis.putAction("layout", layout);
		
    	
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 700);
        frame.add(display);
        frame.setVisible(true);
        
        
        vis.run("color");
        vis.run("layout");
    }
    
} // end of class AggregateDemo



class ArrowEdgeRenderer extends EdgeRenderer
{
    public ArrowEdgeRenderer(int edgeType, int arrowType)
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
//            g.setPaint(Color.red);
            super.setArrowHeadSize(10, 10);
            g.fill(m_curArrow);
        }
    }
}

