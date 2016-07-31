package org.apache.Jack1201_1230;

import javax.swing.JFrame;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.layout.CircleLayout;
import prefuse.activity.Activity;
import prefuse.controls.DragControl;
import prefuse.controls.ZoomToFitControl;
import prefuse.data.Table;
import prefuse.data.io.DataIOException;
import prefuse.data.io.DelimitedTextTableReader;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.LabelRenderer;

/**
 * 使用指定图片作为节点的标示
 * @author Jack
 *
 */
public class Example1215 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String datafile = "E:\\workspace0428\\MySwingTest\\src\\org\\apache\\widget\\amazon1.txt";
		Table data = null;
		try {
			data = (new DelimitedTextTableReader()).readTable(datafile);
			data.addColumn("image", "CONCAT('src/org/apache/widget/images1/',id,'.jpg')");
		} catch (DataIOException e) {
			e.printStackTrace();
			System.exit(2);
		}
		
		Visualization vis = new Visualization();
		
		LabelRenderer label = new LabelRenderer(null, "image");
		label.setTextField("text");
        label.setVerticalAlignment(Constants.BOTTOM);
        label.setHorizontalPadding(0);
        label.setVerticalPadding(0);
        label.setMaxImageDimensions(100,100);
        vis.setRendererFactory(new DefaultRendererFactory(label));
        vis.add("data", data);
        
        ActionList layout = new ActionList(1000);
        layout.add(new CircleLayout("data"));
        layout.add(new RepaintAction());
        
        vis.putAction("layout", layout);
        
        Display display = new Display(vis);
        display.setSize(700, 600);
		display.pan(250, 250);
		display.addControlListener(new ZoomToFitControl());
		display.addControlListener(new DragControl());
		
		
		
		JFrame frame = new JFrame("Image Imagination");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(display);
		frame.pack();
		frame.setVisible(true);
		
		vis.run("layout");

	}

}
