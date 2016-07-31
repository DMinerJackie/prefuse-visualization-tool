package org.apache.demo;

import prefuse.Visualization;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.LabelRenderer;
import prefuse.render.LabelRenderer.LabelShape;

public class LabelConfTabUtils {

	private static Visualization visInstance = null;
	private static JackConfig config = null;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 不显示文本选项卡
	 */
	public static void noTextAction(){
		visInstance = GraphUtils.getVisualization();
		LabelRenderer label = new LabelRenderer("", LabelShape.Arc2D);
		visInstance.setRendererFactory(new DefaultRendererFactory(label));
	}
	
	/**
	 * 显示文本选项卡
	 */
	public static void textOnlyAction(){
		visInstance = GraphUtils.getVisualization();
		config = JackConfig.getConfig();
		LabelRenderer label = new LabelRenderer(config.getProperty("field.selected"), config.getProperty("shape.current"));
		label.setRoundedCorner(15, 15);
		visInstance.setRendererFactory(new DefaultRendererFactory(label));
	}
	

}
