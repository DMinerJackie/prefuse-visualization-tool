package org.apache.demo;

import java.io.File;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.layout.RandomLayout;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.DragControl;
import prefuse.controls.FocusControl;
import prefuse.controls.NeighborHighlightControl;
import prefuse.controls.PanControl;
import prefuse.controls.ToolTipControl;
import prefuse.controls.WheelZoomControl;
import prefuse.controls.ZoomControl;
import prefuse.controls.ZoomToFitControl;
import prefuse.data.Graph;
import prefuse.data.io.DataIOException;
import prefuse.data.io.GraphMLReader;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.LabelRenderer;
import prefuse.render.LabelRenderer.LabelShape;
import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;
/**
 * 工具类
 * @author Jack
 *
 */
public class GraphUtils {
	
	public static final String GroupName = "graph";
	public static final String NODES = "graph.nodes";
	public static final String EDGES = "graph.edges";
	
	private static Visualization visInstance;
	private static JackConfig config = null;
	
	/**
	 * 初始化数据中心，单例模式
	 * @return
	 */
	public static Visualization getVisualization(){
		if(visInstance == null){
			visInstance = new Visualization();
		}
		return visInstance;
	}
	
	/**
	 * 数据加载
	 * @param file
	 * @throws DataIOException
	 */
	public static void loadData(File file, Boolean isDirected) throws DataIOException{
		Graph graph = null;
		graph = new GraphMLReader().readGraph(file);
		graph.setDirected(isDirected);//用于设置面板上选择的方向
		visInstance = new Visualization();
		visInstance.add(GroupName, graph);
	}
	
	/**
	 * 设置渲染器
	 */
	public static void setRenderer(String label){
		visInstance = getVisualization();
		config = JackConfig.getConfig();
		
		LabelRenderer labelR = new LabelRenderer(label, LabelShape.Arc2D);
		config.setProperty("shape.current", LabelShape.Arc2D.toString());
		
//		labelR.setRoundedCorner(10, 10);
		DefaultRendererFactory drf = new DefaultRendererFactory(labelR);
		visInstance.setRendererFactory(drf);
	}
	
	/**
	 * 设置Action
	 */
	public static void setAction(){
		visInstance = getVisualization();
		
//		int[] palette = new int[]{ColorLib.rgb(255, 180, 180),ColorLib.rgb(190, 190, 255)};
//		DataColorAction fill = new DataColorAction(NODES , "gender" , Constants.NOMINAL, VisualItem.FILLCOLOR,palette);
		ColorAction text = new ColorAction(NODES, VisualItem.TEXTCOLOR, ColorLib.gray(0));
		ColorAction fill = new ColorAction(NODES, VisualItem.FILLCOLOR, ColorLib.rgb(190, 190, 255));
		fill.add(VisualItem.FIXED, ColorLib.rgb(255,100,100));
        fill.add(VisualItem.HIGHLIGHT, ColorLib.rgb(255,200,125));
		ColorAction edges = new ColorAction(EDGES, VisualItem.STROKECOLOR, ColorLib.gray(200));
		
		ActionList color = new ActionList();
		color.add(fill);
		color.add(text);
		color.add(edges);
		
		ActionList layout = new ActionList();
		layout.add(color);
		layout.add(new RandomLayout(GroupName));
		layout.add(new RepaintAction());
		
		visInstance.putAction("color", color);
		visInstance.putAction("layout", layout);
	}
	
	/**
	 * 设置Display
	 */
	public static Display setDisplay(){
		visInstance = getVisualization();
		
//		String[] fields = new String[]{"发货日期", "发货人地址", "重量", "收货人手机"};
//		ToolTipControl ttc = new ToolTipControl(fields);
		
		Display display = new Display(visInstance);
		display.setSize(100, 50);
		display.pan(250, 250);
		display.addControlListener(new DragControl());
		display.addControlListener(new PanControl());
		display.addControlListener(new ZoomControl());
		display.addControlListener(new WheelZoomControl());
		display.addControlListener(new FocusControl(1));
		display.addControlListener(new ZoomToFitControl());
		display.addControlListener(new NeighborHighlightControl());
//		display.addControlListener(ttc);
		return display;
	}
	
	public static void startRun(){
		visInstance = getVisualization();
		visInstance.run("color");
		visInstance.run("layout");
	}
	
}
