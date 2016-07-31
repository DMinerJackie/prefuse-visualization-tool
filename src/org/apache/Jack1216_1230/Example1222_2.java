package org.apache.Jack1216_1230;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.ItemAction;
import prefuse.action.RepaintAction;
import prefuse.action.animate.QualityControlAnimator;
import prefuse.action.assignment.ColorAction;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.activity.SlowInSlowOutPacer;
import prefuse.controls.DragControl;
import prefuse.controls.FocusControl;
import prefuse.controls.PanControl;
import prefuse.controls.WheelZoomControl;
import prefuse.controls.ZoomControl;
import prefuse.controls.ZoomToFitControl;
import prefuse.data.Graph;
import prefuse.data.Tuple;
import prefuse.data.event.TupleSetListener;
import prefuse.data.io.GraphMLReader;
import prefuse.data.query.SearchQueryBinding;
import prefuse.data.search.PrefixSearchTupleSet;
import prefuse.data.search.SearchTupleSet;
import prefuse.data.tuple.TupleSet;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.LabelRenderer;
import prefuse.util.ColorLib;
import prefuse.util.ui.JSearchPanel;
import prefuse.visual.VisualItem;
/**
 * 导入socialnet.xml数据集按照性别进行划分并展现
 * 将RadialGraphView中的文本搜索功能迁移到
 * @author Jack
 *
 */
public class Example1222_2 {

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
		final Visualization vis = new Visualization();
		vis.add("graph", graph);

		//------------   3    ------------
		LabelRenderer label = new LabelRenderer("name");
		label.setRoundedCorner(10, 10);

		vis.setRendererFactory(new DefaultRendererFactory(label));
		
		//------------   4    ------------
//		int[] palette = new int[]{ColorLib.rgb(255, 180, 180),ColorLib.rgb(190, 190, 255)};
//		DataColorAction fill = new DataColorAction("graph.nodes" , "gender" , Constants.NOMINAL, VisualItem.FILLCOLOR,palette);
//		ColorAction text = new ColorAction("graph.nodes", VisualItem.TEXTCOLOR, ColorLib.gray(0));
//		ColorAction edges = new ColorAction("graph.edges", VisualItem.STROKECOLOR, ColorLib.gray(200));
//		
//		ActionList color = new ActionList();
//		color.add(fill);
//		color.add(text);
//		color.add(edges);
		
		
		ItemAction nodeColor = new NodeColorAction("graph.nodes");
        ItemAction textColor = new TextColorAction("graph.nodes");
        vis.putAction("textColor", textColor);
        
        ItemAction edgeColor = new ColorAction("graph.edges",VisualItem.STROKECOLOR, ColorLib.rgb(200,200,200));//新建边颜色Action
            
        
        // recolor   新建重新着色ActionList，并添加上面的节点和边Action
        ActionList recolor = new ActionList();
        recolor.add(nodeColor);
        recolor.add(textColor);
        vis.putAction("recolor", recolor);
        
        // repaint   新建重绘ActionList，并加入recolor以及RepaintAction
        ActionList repaint = new ActionList();
        repaint.add(recolor);
        repaint.add(new RepaintAction());
        vis.putAction("repaint", repaint);
        
        // animate paint change  
        ActionList animatePaint = new ActionList(400);
        animatePaint.add(new RepaintAction());
        vis.putAction("animatePaint", animatePaint);
        
        
        // create the filtering and layout   创建过滤器和布局     在图形呈现之前，做好过滤filter工作
        ActionList filter = new ActionList(Activity.INFINITY);
        filter.add(new ForceDirectedLayout("graph"));
        filter.add(textColor);
        filter.add(nodeColor);
        filter.add(edgeColor);
        vis.putAction("filter", filter);
        
        // animated transition  动画过渡
        ActionList animate = new ActionList(Activity.INFINITY);
        animate.setPacingFunction(new SlowInSlowOutPacer());//起搏功能，提供渐入渐出效果
        animate.add(new QualityControlAnimator());//可以平滑切换动画的动画控制器
        animate.add(new RepaintAction());
        vis.putAction("animate", animate);
        vis.alwaysRunAfter("filter", "animate");
		
		
		
		
//		ActionList layout = new ActionList(Activity.INFINITY);
//		layout.add(new ForceDirectedLayout("graph"));
//		layout.add(new RepaintAction());
//		
//		vis.putAction("layout", layout);
		
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
		
		
		JPanel panel = new JPanel(new BorderLayout());
		
		SearchTupleSet searcht = new PrefixSearchTupleSet();//监听搜索栏数据
		vis.addFocusGroup(Visualization.SEARCH_ITEMS, searcht);
		searcht.addTupleSetListener(new TupleSetListener() {
			public void tupleSetChanged(TupleSet t, Tuple[] add, Tuple[] rem) {
				System.out.println("hello");
				vis.cancel("animatePaint");
				vis.run("recolor");
				vis.run("animatePaint");
			}
		});	        
		
		SearchQueryBinding sq = new SearchQueryBinding(vis.getGroup("graph.nodes"), "name",(SearchTupleSet)vis.getGroup(Visualization.SEARCH_ITEMS));
	        JSearchPanel search = sq.createSearchPanel();
	        search.setShowResultCount(true);
	        search.setBorder(BorderFactory.createEmptyBorder(5,5,4,0));
	        
	        
	        
	        
//	    AndPredicate filter = new AndPredicate(sq.getPredicate());
	    
//	    ActionList update = new ActionList();
//        update.add(new VisibilityFilter("graph", filter));
//        update.add(new RepaintAction());
//        vis.putAction("update", update);
//        
//        UpdateListener lstnr = new UpdateListener() {
//            public void update(Object src) {
//                vis.run("update");
//            }
//        };
//        filter.addExpressionListener(lstnr);
	    
		Box box = new Box(BoxLayout.X_AXIS);//创建盒子容器，装在上面的搜索面板和搜索文本框（安装水平方向布局）
        box.add(Box.createHorizontalStrut(10));
        box.add(search);
        box.add(Box.createHorizontalGlue());
        
        panel.add(display, BorderLayout.CENTER);
        panel.add(box, BorderLayout.SOUTH);
		
		
		//------------   6    ------------
		JFrame jf = new JFrame();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setContentPane(panel);
		jf.pack();
		jf.setVisible(true);
		
		vis.run("filter");
		vis.run("animate");
//		vis.run("layout");
		
	}

}

/**
 * Set node fill colors    设置点的填充色
 */
 class NodeColorAction extends ColorAction {
    public NodeColorAction(String group) {
        super(group, VisualItem.FILLCOLOR, ColorLib.rgba(255,255,255,0));
        add("_hover", ColorLib.gray(220,230));
        add("ingroup('_search_')", ColorLib.rgb(255,190,190));
        add("ingroup('_focus_')", ColorLib.rgb(198,229,229));
    }
            
} // end of inner class NodeColorAction
 
  class TextColorAction extends ColorAction {
     public TextColorAction(String group) {
         super(group, VisualItem.TEXTCOLOR, ColorLib.gray(0));
         add("_hover", ColorLib.rgb(255,0,0));
     }
 } // end of inner class TextColorAction
