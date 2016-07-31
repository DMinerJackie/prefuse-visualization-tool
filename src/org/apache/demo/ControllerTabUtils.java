package org.apache.demo;

import java.awt.geom.Rectangle2D;

import prefuse.Display;
import prefuse.Visualization;
import prefuse.util.GraphicsLib;
import prefuse.util.display.DisplayLib;

public class ControllerTabUtils {
	private static Visualization visInstance = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 缩放至适合屏幕显示选项卡
	 */
	public static void zoomToFitAction(){
		visInstance = GraphUtils.getVisualization();
		if(visInstance.getDisplayCount() == 0){
			return;
		}
		Display display = visInstance.getDisplay(0);
		System.out.println("display width:" + visInstance.getDisplay(0).getWidth());
		System.out.println("display height:" + visInstance.getDisplay(0).getHeight());
		Visualization vis = display.getVisualization();
		Rectangle2D bounds = vis.getBounds(Visualization.ALL_ITEMS);
		GraphicsLib.expand(bounds, 50 + (int) (1 / display.getScale()));
		DisplayLib.fitViewToBounds(display, bounds, 2000);
	}

}
