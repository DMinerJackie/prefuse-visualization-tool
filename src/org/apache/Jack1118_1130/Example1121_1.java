package org.apache.Jack1118_1130;

import java.sql.SQLException;

import javax.swing.JFrame;

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
import prefuse.data.Table;
import prefuse.data.expression.Predicate;
import prefuse.data.expression.parser.ExpressionParser;
import prefuse.data.io.DataIOException;
import prefuse.data.io.sql.ConnectionFactory;
import prefuse.data.io.sql.DatabaseDataSource;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.LabelRenderer;
import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;
/**
 * 连接数据库，读取数据库中点和边信息所在的表，并根据这些信息绘制图形
 * @author Jack
 *
 */
/**
 * @author Jack
 *
 */
public class Example1121_1 {

	/**
	 * @param args
	 * @throws DataIOException 
	 */
	public static void main(String[] args) throws DataIOException {
		
		//------------   1    ------------
		
		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url = "jdbc:sqlserver://localhost:1433;DatabaseName=miningdb";
		String username = "sa";
		String password = "sa";
		DatabaseDataSource dbds = null;
		try {
			dbds = ConnectionFactory.getDatabaseConnection(driver, url, username, password);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		Table nodes = dbds.getData("select * from nodes");
		Table edges = dbds.getData("select * from edges");
		Visualization vis = new Visualization();
		Graph graph = new Graph(nodes, edges, false, "id", "sid", "tid");
//		Predicate p = (Predicate) ExpressionParser.parse("[id]>2");
		
		//------------   2    ------------
		vis.add("graph", graph);

		//------------   3    ------------
		LabelRenderer label = new LabelRenderer("name");
		label.setRoundedCorner(10, 10);
		
		 
		vis.setRendererFactory(new DefaultRendererFactory(label));
		
		//------------   4    ------------
		int[] palette = new int[]{ColorLib.rgb(255, 180, 180),ColorLib.rgb(190, 190, 255)};
		DataColorAction fill = new DataColorAction("graph.nodes" , "gender" , Constants.NOMINAL, VisualItem.FILLCOLOR,palette);
		ColorAction text = new ColorAction("graph.nodes", VisualItem.TEXTCOLOR, ColorLib.gray(0));
		ColorAction edges1 = new ColorAction("graph.edges", VisualItem.STROKECOLOR, ColorLib.gray(200));
		
		
		ActionList color = new ActionList();
		color.add(fill);
		color.add(text);
		color.add(edges1);
		
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
