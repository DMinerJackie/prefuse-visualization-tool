package org.apache.demo;

import prefuse.Visualization;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.LabelRenderer;
import prefuse.render.LabelRenderer.LabelShape;

public class NodeConfTabUtils {
	private static Visualization visInstance = null;
	private static JackConfig config = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
	
	/**
	 * 矩形节点选项卡
	 */
	public static void rectangleAction(){
		visInstance = GraphUtils.getVisualization();
		config = JackConfig.getConfig();
		LabelRenderer label = new LabelRenderer(config.getProperty("field.selected"), LabelShape.Rectangle2D);
		visInstance.setRendererFactory(new DefaultRendererFactory(label));
		visInstance.run("layout");
	}

	/**
	 * 圆形节点选项卡
	 */
	public static void roundAction(){
		visInstance = GraphUtils.getVisualization();
		config = JackConfig.getConfig();
		LabelRenderer label = new LabelRenderer(config.getProperty("field.selected"), LabelShape.Arc2D);
		visInstance.setRendererFactory(new DefaultRendererFactory(label));
		visInstance.run("layout");
	}
	
	/**
	 * 圆角矩形节点选项卡
	 */
	public static void roundRectangleAction(){
		visInstance = GraphUtils.getVisualization();
		config = JackConfig.getConfig();
		LabelRenderer label = new LabelRenderer(config.getProperty("field.selected"), LabelShape.Rectangle2D);
		label.setRoundedCorner(15, 15);
		visInstance.setRendererFactory(new DefaultRendererFactory(label));
		visInstance.run("layout");
	}

}
