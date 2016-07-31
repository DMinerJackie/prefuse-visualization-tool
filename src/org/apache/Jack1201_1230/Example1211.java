package org.apache.Jack1201_1230;

import java.awt.Point;
import java.awt.event.MouseEvent;

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
import prefuse.controls.Control;
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
import prefuse.render.ShapeRenderer;
import prefuse.util.ColorLib;
import prefuse.visual.VisualGraph;
import prefuse.visual.VisualItem;
/**
 * 手动创建点、边，并构成一个图形;改变节点默认形状;通过过滤器设定节点度数超过2的不能interactive
 * 将Example1211添加当鼠标经过和离开某点时改变其填充颜色
 * @author Jack
 *
 */
public class Example1211{

    
    public static void main(String[] argv) {
    	Visualization vis = new Visualization();
    	Graph g = new Graph();
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

    	vis.setRendererFactory(new DefaultRendererFactory(renderer));
    	
    	final ColorAction nodeFill = new ColorAction("graph.nodes", VisualItem.FILLCOLOR, ColorLib.rgb(10, 150, 220));
		ColorAction edgesStroke = new ColorAction("graph.edges", VisualItem.STROKECOLOR, ColorLib.rgb(100, 80, 180));
		ColorAction nodeHighlight = new ColorAction("graph.nodes", VisualItem.HIGHLIGHT, ColorLib.rgb(10, 150, 220));
		
		ShapeAction  shape = new ShapeAction("graph.nodes",Constants.SHAPE_CROSS); //设置节点形状
		
		
		final ActionList color = new ActionList();
		color.add(nodeFill);
		color.add(edgesStroke);
		color.add(shape);
		
		ActionList layout = new ActionList(Activity.INFINITY);
        layout.add(color);
        layout.add(new ForceDirectedLayout("graph"));
        layout.add(new RepaintAction());
        
        
        Predicate pCount =(Predicate)ExpressionParser.parse("degree()>2");
        vis.setInteractive("graph.nodes", pCount, false);
        vis.setValue("graph.nodes", null, VisualItem.SIZE,
               3);//设置节点形状
        
        Control hoverc = new ControlAdapter(){
        	public void itemEntered(VisualItem item, MouseEvent evt) {
                if ( item.isInGroup("graph") ) {
                  color.remove(nodeFill);//注意，这里如果没有这行代码则该部分没有效果，因为fillcolor的效果在前面已经被注册了，后面再注册无用
                  item.setFillColor(ColorLib.rgb(255, 255, 255));
                  item.setStrokeColor(ColorLib.rgb(0,0,0));
                  item.getVisualization().repaint();
                }
            }
            public void itemExited(VisualItem item, MouseEvent evt) {
                if ( item.isInGroup("graph") ) {
                  color.remove(nodeFill);
                  item.setFillColor(ColorLib.rgb(250, 10, 10));
                  item.setStrokeColor(item.getEndStrokeColor());
                  item.getVisualization().repaint();
                }
            }
        };
        
		Display display = new Display(vis);
		display.setSize(400, 500);
		display.pan(250, 250);
		display.animatePanAbs(230, 220, 2000);
		System.out.println(display.getTransform());
		
		
		display.addControlListener(new DragControl());
		display.addControlListener(new PanControl());
		display.addControlListener(new ZoomControl());
		display.addControlListener(new WheelZoomControl());
		display.addControlListener(new FocusControl(1));
		display.addControlListener(new ZoomToFitControl());
		display.addControlListener(hoverc);
		
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





