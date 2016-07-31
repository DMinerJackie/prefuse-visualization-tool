package org.apache.Jack1201_1230;

import javax.swing.JFrame;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.layout.CircleLayout;
import prefuse.activity.Activity;
import prefuse.controls.DragControl;
import prefuse.controls.FocusControl;
import prefuse.controls.PanControl;
import prefuse.controls.WheelZoomControl;
import prefuse.controls.ZoomControl;
import prefuse.controls.ZoomToFitControl;
import prefuse.data.Table;
import prefuse.data.io.DataIOException;
import prefuse.data.io.DelimitedTextTableReader;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.LabelRenderer;
import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;

/**
 * 使用指定图片作为节点的标示
 * 同时显示text属性，即在一个节点上同时显示节点信息以及图片信息,并可以设置文本和图片的相对位置有Constants.LEFT,Constants.RIGHT,Constants.TOP,Constants.BOTTOM
 * @author Jack
 *
 */
public class Example1215_3 {

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
		
		LabelRenderer label = new LabelRenderer("text","image");
//		label.setTextField(null);
		label.setImagePosition(Constants.RIGHT);
        label.setVerticalAlignment(Constants.BOTTOM);
        label.setHorizontalPadding(0);
        label.setVerticalPadding(0);
        label.setMaxImageDimensions(100,100);
        vis.setRendererFactory(new DefaultRendererFactory(label));
        vis.add("data", data);
        
        ColorAction text = new ColorAction("data", VisualItem.TEXTCOLOR, ColorLib.rgb(100, 0, 100));
		ColorAction edges = new ColorAction("data", VisualItem.STROKECOLOR, ColorLib.rgb(0, 100, 10));
		
		ActionList color = new ActionList();
//		color.add(fill);
		color.add(text);
		color.add(edges);
        
        ActionList layout = new ActionList(1000);
        layout.add(new CircleLayout("data"));
        layout.add(new RepaintAction());
        
        vis.putAction("color", color);
        vis.putAction("layout", layout);
        
        Display display = new Display(vis);
        display.setSize(700, 600);
		display.pan(250, 250);
		display.addControlListener(new DragControl());
		display.addControlListener(new PanControl());
		display.addControlListener(new ZoomControl());
		display.addControlListener(new WheelZoomControl());
		display.addControlListener(new FocusControl(1));
		display.addControlListener(new ZoomToFitControl());
		
		
		
		JFrame frame = new JFrame("Image Imagination");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(display);
		frame.pack();
		frame.setVisible(true);
		
		vis.run("color");
		vis.run("layout");

	}

}
