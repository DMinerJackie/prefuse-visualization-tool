package org.apache.demo;

import prefuse.Constants;
import prefuse.Visualization;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;

/**
 * Created by Jackie on 2016/8/5 0005.
 */
public class EdgeConfTabUtils {

    private static Visualization visInstance = null;

    /**
     * 曲线边选项卡
     */
    public static void curveEdgeAction(){
        visInstance = GraphUtils.getVisualization();
        DefaultRendererFactory drf = (DefaultRendererFactory) visInstance.getRendererFactory();
        drf.setDefaultEdgeRenderer(new EdgeRenderer(Constants.EDGE_TYPE_CURVE));//设置曲线
        visInstance.setRendererFactory(drf);
    }

    /**
     * 直线边选项卡
     */
    public static void straightEdgeAction(){
        visInstance = GraphUtils.getVisualization();
        DefaultRendererFactory drf = (DefaultRendererFactory) visInstance.getRendererFactory();
        drf.setDefaultEdgeRenderer(new EdgeRenderer(Constants.EDGE_TYPE_LINE));//设置直线
        visInstance.setRendererFactory(drf);
    }
}
