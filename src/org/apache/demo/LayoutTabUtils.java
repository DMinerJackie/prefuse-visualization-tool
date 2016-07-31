package org.apache.demo;

import java.awt.geom.Point2D;
import java.util.Iterator;

import prefuse.Constants;
import prefuse.Visualization;
import prefuse.action.Action;
import prefuse.action.ActionList;
import prefuse.action.GroupAction;
import prefuse.action.RepaintAction;
import prefuse.action.animate.ColorAnimator;
import prefuse.action.animate.LocationAnimator;
import prefuse.action.animate.PolarLocationAnimator;
import prefuse.action.animate.QualityControlAnimator;
import prefuse.action.animate.VisibilityAnimator;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.FontAction;
import prefuse.action.filter.FisheyeTreeFilter;
import prefuse.action.layout.CircleLayout;
import prefuse.action.layout.CollapsedSubtreeLayout;
import prefuse.action.layout.GridLayout;
import prefuse.action.layout.RandomLayout;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.action.layout.graph.FruchtermanReingoldLayout;
import prefuse.action.layout.graph.NodeLinkTreeLayout;
import prefuse.action.layout.graph.RadialTreeLayout;
import prefuse.activity.Activity;
import prefuse.activity.SlowInSlowOutPacer;
import prefuse.controls.FocusControl;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.tuple.TupleSet;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;
import prefuse.util.ColorLib;
import prefuse.util.FontLib;
import prefuse.visual.VisualItem;

public class LayoutTabUtils {
	private static Visualization visInstance = null;
	private static MyFocusControl mfc = null;
	private static int m_orientation = Constants.ORIENT_LEFT_RIGHT;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static void removeFocus(){
		if(mfc != null){
			GraphUtils.getVisualization().getDisplay(0).removeControlListener(mfc);
		}
	}
	
	/**
	 * 力导向布局选项卡
	 */
	public static void forceLayoutAction(){
		visInstance = GraphUtils.getVisualization();
		visInstance.setInteractive("graph.edges", null, true);
		removeFocus();
		visInstance.removeAction("layout");
		ActionList layout = new ActionList(Activity.INFINITY);
		layout.add(visInstance.getAction("color"));
		layout.add(new ForceDirectedLayout("graph"));
		layout.add(new RepaintAction());

		visInstance.putAction("layout", layout);
		
		DefaultRendererFactory drf = (DefaultRendererFactory) visInstance.getRendererFactory();
		drf.setDefaultEdgeRenderer(new EdgeRenderer(Constants.EDGE_TYPE_LINE));//设置直线

		visInstance.run("color");
		visInstance.run("layout");
	}
	
	/**
	 * 圆形布局选项卡
	 */
	public static void circleLayoutAction(){
		visInstance = GraphUtils.getVisualization();
		visInstance.setInteractive("graph.edges", null, true);
		removeFocus();
		visInstance.removeAction("layout");
		ActionList layout = new ActionList();
		layout.add(visInstance.getAction("color"));
		layout.add(new CircleLayout("graph"));
		layout.add(new RepaintAction());

		visInstance.putAction("layout", layout);

		visInstance.run("color");
		visInstance.run("layout");
	}
	
	/**
	 * 随机布局选项卡
	 */
	public static void randomLayoutAction(){
		visInstance = GraphUtils.getVisualization();
		removeFocus();
		visInstance.removeAction("layout");
		ActionList layout = new ActionList();
		layout.add(visInstance.getAction("color"));
		layout.add(new RandomLayout("graph"));
		layout.add(new RepaintAction());

		visInstance.putAction("layout", layout);
		
		DefaultRendererFactory drf = (DefaultRendererFactory) visInstance.getRendererFactory();
		drf.setDefaultEdgeRenderer(new EdgeRenderer(Constants.EDGE_TYPE_CURVE));//设置直线

		visInstance.run("color");
		visInstance.run("layout");
	}
	
	/**
	 * 网状布局选项卡     可能涉及到数据结构是否符合网状布局的情况
	 */
	public static void gridLayoutAction(){
		visInstance = GraphUtils.getVisualization();
		visInstance.setInteractive("graph.edges", null, true);
		removeFocus();
		visInstance.removeAction("layout");
		ActionList layout = new ActionList(Activity.INFINITY);
		layout.add(visInstance.getAction("color"));
		layout.add(new GridLayout("graph"));
		layout.add(new RepaintAction());

		visInstance.putAction("layout", layout);

		visInstance.run("color");
		visInstance.run("layout");
	}
	
	
	
	/**
	 * Fruchterman Reingold布局选项卡
	 */
	public static void fruchtermanLayoutAction(){
		visInstance = GraphUtils.getVisualization();
		visInstance.setInteractive("graph.edges", null, true);
		removeFocus();
		visInstance.removeAction("layout");
		ActionList layout = new ActionList();
		layout.add(visInstance.getAction("color"));
		layout.add(new FruchtermanReingoldLayout("graph"));
		layout.add(new RepaintAction());

		visInstance.putAction("layout", layout);
		
		DefaultRendererFactory drf = (DefaultRendererFactory) visInstance.getRendererFactory();
		drf.setDefaultEdgeRenderer(new EdgeRenderer(Constants.EDGE_TYPE_CURVE));//设置直线

		visInstance.run("color");
		visInstance.run("layout");
	}
	
	/**
	 * 辐射树状布局选项卡
	 */
	public static void radialTreeLayout(){
		visInstance = GraphUtils.getVisualization();
		visInstance.setInteractive("graph.edges", null, false);
		visInstance.removeAction("layout");
		ActionList layout = new ActionList();
		layout.add(new TreeRootAction("graph"));
		layout.add(visInstance.getAction("color"));
		layout.add(new RadialTreeLayout("graph"));
		layout.add(new CollapsedSubtreeLayout("graph"));
		layout.add(new RepaintAction());
		visInstance.putAction("layout", layout);

		ActionList animate = new ActionList(1250);
		animate.setPacingFunction(new SlowInSlowOutPacer());
		animate.add(new QualityControlAnimator());
		animate.add(new VisibilityAnimator("graph"));
		animate.add(new PolarLocationAnimator("graph.nodes", "linear"));
		animate.add(new ColorAnimator("graph.nodes"));
		animate.add(new RepaintAction());
		visInstance.putAction("animate", animate);
		visInstance.alwaysRunAfter("layout", "animate");
		MyFocusControl mfc = new MyFocusControl(1, "layout");
		visInstance.getDisplay(0).addControlListener(mfc);
		
		visInstance.run("color");
		visInstance.run("layout");
	}
	
	/**
	 * 节点分级展示选项卡
	 */
	public static void nodeLinkTreeLayout(){
		visInstance = GraphUtils.getVisualization();
		visInstance.setInteractive("graph.edges", null, true);
		removeFocus();
		visInstance.removeAction("layout");
		ActionList layout = new ActionList();
		layout.add(visInstance.getAction("color"));
		layout.add(new FisheyeTreeFilter("graph", 30));//添加一个鱼眼树形过滤器    设置初始界面显示几层节点
		layout.add(new FontAction("graph.nodes", FontLib.getFont("微软雅黑", 16)));
		layout.add(new NodeLinkTreeLayout("graph", Constants.ORIENT_LEFT_RIGHT, 50, 20, 58));
		layout.add(new CollapsedSubtreeLayout("graph", Constants.ORIENT_LEFT_RIGHT));
		layout.add(new RepaintAction());
		visInstance.putAction("layout", layout);
		
		AutoPanAction autoPan = new AutoPanAction();
		ActionList animate = new ActionList(1000);
        animate.setPacingFunction(new SlowInSlowOutPacer());
        animate.add(autoPan);
        animate.add(new QualityControlAnimator());
        animate.add(new VisibilityAnimator("graph.nodes"));
        animate.add(new LocationAnimator("graph.nodes"));
        animate.add(new ColorAnimator("graph.nodes"));
        animate.add(new RepaintAction());
        visInstance.putAction("animate", animate);
        visInstance.alwaysRunAfter("filter", "animate");
        MyFocusControl mfc = new MyFocusControl(1, "layout");
		visInstance.getDisplay(0).addControlListener(mfc);
		
		DefaultRendererFactory drf = (DefaultRendererFactory) visInstance.getRendererFactory();
		drf.setDefaultEdgeRenderer(new EdgeRenderer(Constants.EDGE_TYPE_CURVE));//设置曲线

		visInstance.run("color");
		visInstance.run("layout");
	}
	
	/**
	 * FocusControl的实现类(for radial)
	 * @author Jack
	 *
	 */
	 public static class MyFocusControl extends FocusControl {
	        public MyFocusControl(int clicks, String act) {
	        	ccount = clicks;
	            activity = act;
	        }
	    } 
	 
	/**
	 * 选择根 (for radial)
	 * @author Jack
	 *
	 */
	public static class TreeRootAction extends GroupAction {
        public TreeRootAction(String graphGroup) {
            super(graphGroup);
        }
        public void run(double frac) {
            TupleSet focus = m_vis.getGroup(Visualization.FOCUS_ITEMS);//如果没有点被选中或选中为空则跳出run函数
            if ( focus==null || focus.getTupleCount() == 0 ) return;
            
            Graph g = (Graph)m_vis.getGroup(m_group);
            Node f = null;
            Iterator tuples = focus.tuples();
            while (tuples.hasNext() && !g.containsTuple(f=(Node)tuples.next()))//迭代选中的焦点，如果选中焦点不是上次的焦点，则置f为空，并跳出run函数
            {
                f = null;
            }
            if ( f == null ) return;
            g.getSpanningTree(f);//返回一个生成树
        }
    }
	
	/**
	 * 自动平移 (for nodelink)
	 * @author Jack
	 *
	 */
	public static  class AutoPanAction extends Action {
        private Point2D m_start = new Point2D.Double();
        private Point2D m_end   = new Point2D.Double();
        private Point2D m_cur   = new Point2D.Double();
        private int     m_bias  = 150;
        
        public void run(double frac) {
            TupleSet ts = m_vis.getFocusGroup(Visualization.FOCUS_ITEMS);
            if ( ts.getTupleCount() == 0 )
                return;
            
            if ( frac == 0.0 ) {
                int xbias=0, ybias=0;
                switch ( m_orientation ) {
                case Constants.ORIENT_LEFT_RIGHT:
                    xbias = m_bias;
                    break;
                case Constants.ORIENT_RIGHT_LEFT:
                    xbias = -m_bias;
                    break;
                case Constants.ORIENT_TOP_BOTTOM:
                    ybias = m_bias;
                    break;
                case Constants.ORIENT_BOTTOM_TOP:
                    ybias = -m_bias;
                    break;
                }

                VisualItem vi = (VisualItem)ts.tuples().next();
                m_cur.setLocation(GraphUtils.getVisualization().getDisplay(0).getWidth()/2, GraphUtils.getVisualization().getDisplay(0).getHeight()/2);
                GraphUtils.getVisualization().getDisplay(0).getAbsoluteCoordinate(m_cur, m_start);
                m_end.setLocation(vi.getX()+xbias, vi.getY()+ybias);
            } else {
                m_cur.setLocation(m_start.getX() + frac*(m_end.getX()-m_start.getX()),
                                  m_start.getY() + frac*(m_end.getY()-m_start.getY()));
                GraphUtils.getVisualization().getDisplay(0).panToAbs(m_cur);
            }
        }
    }

}
