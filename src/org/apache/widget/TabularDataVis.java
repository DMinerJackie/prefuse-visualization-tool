package org.apache.widget;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.GroupAction;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.assignment.DataSizeAction;
import prefuse.action.assignment.ShapeAction;
import prefuse.action.filter.VisibilityFilter;
import prefuse.action.layout.AxisLabelLayout;
import prefuse.action.layout.AxisLayout;
import prefuse.controls.Control;
import prefuse.controls.ControlAdapter;
import prefuse.controls.ToolTipControl;
import prefuse.data.Table;
import prefuse.data.expression.AndPredicate;
import prefuse.data.io.CSVTableReader;
import prefuse.data.query.RangeQueryBinding;
import prefuse.data.query.SearchQueryBinding;
import prefuse.render.AbstractShapeRenderer;
import prefuse.render.AxisRenderer;
import prefuse.render.Renderer;
import prefuse.render.RendererFactory;
import prefuse.render.ShapeRenderer;
import prefuse.util.ColorLib;
import prefuse.util.FontLib;
import prefuse.util.UpdateListener;
import prefuse.util.ui.JFastLabel;
import prefuse.util.ui.JRangeSlider;
import prefuse.util.ui.JSearchPanel;
import prefuse.util.ui.UILib;
import prefuse.visual.VisualItem;
import prefuse.visual.VisualTable;
import prefuse.visual.expression.VisiblePredicate;

public class TabularDataVis extends JPanel {

    /*
     * main execution class (for running as an applet)
     */
    public static void main(String[] args) {

        JFrame f = buildFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    /*
     * load the data and generate the frame that contains the visualization
     */
    public static JFrame buildFrame() {
        // load the data
        Table t = null;
        try {
            // data in CSV format, so use CSVTableReader
            t = new CSVTableReader().readTable("E:\\workspace0428\\MySwingTest\\src\\org\\apache\\widget\\population.csv");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        // set the title on the frame
        JFrame frame = new JFrame("Canadian Urban Population");

        // add in the visualization contents (calls the constructor for this class)
        frame.setContentPane(new TabularDataVis(t));

        // pack the elements in the frame and return
        frame.pack();
        return frame;
    }

    /*
     * Global variables and configuration parameters
     */

    // summary information needs to be set and reloaded in support classes
    private String g_totalStr;
    private JFastLabel g_total = new JFastLabel("");
    // access to the visualization elements are needed within the support classes
    private Visualization g_vis;
    private Display g_display;
    // containers for the data, x-axis labels, and y-axis labels
    private Rectangle2D g_dataB = new Rectangle2D.Double();
    private Rectangle2D g_xlabB = new Rectangle2D.Double();
    private Rectangle2D g_ylabB = new Rectangle2D.Double();

    /*
     * Constructor for the class 
     * This is where all the important stuff happens
     */
    public TabularDataVis(Table t) {
        super(new BorderLayout());

        /*
         * Step 1: Setup the Visualization
         */

        // create a new visualization object, and assign it to the global variable
        final Visualization vis = new Visualization();
        g_vis = vis;

        // create a visual abstraction of the table data (loaded in the buildFrame method)
        // call our data "canUrban"
        VisualTable vt = vis.addTable("canUrban", t);

        // add a new column containing a label string showing
        // the Geographic name and population
        // note: uses the prefuse expression language
        vt.addColumn("label", "CONCAT([Geographic name], ' (Population: ', FORMAT([2006 Population],0), ')')");

        // add a new column that divides the provinces by their geographic location (derived values)
        // note: uses the prefuse expression language
        vt.addColumn("geographic location", "IF ([Province]='BC') THEN 1 ELSE " +
                "(IF ([Province] = 'AB' OR [Province] = 'SK' OR [Province] = 'MB') THEN 2 ELSE " +
                "(IF ([Province] = 'ON' OR [Province] = 'QC') THEN 3 ELSE" +
                "(IF ([Province] = 'NS' OR [Province] = 'NB' OR [Province] = 'PE' OR [Province] = 'NL') THEN 4 ELSE 5)))");

        // add a new column that converts the population data to ordinal data (derived values)
        // note: uses the prefuse expression language
        vt.addColumn("population ordinal", "IF ([2006 Population] > 5000000) THEN 7 ELSE " +
                "(IF ([2006 Population] > 1000000) THEN 6 ELSE " +
                "(IF ([2006 Population] > 250000) THEN 5 ELSE " +
                "(IF ([2006 Population] > 100000) THEN 4 ELSE " +
                "(IF ([2006 Population] > 50000) THEN 3 ELSE " +
                "(IF ([2006 Population] > 20000) THEN 2 ELSE 1)))))");

        // create a new renderer factory for drawing the visual items
        vis.setRendererFactory(new RendererFactory() {

            // specify the default shape renderer (the actions will decide how to actually render the visual elements)
            AbstractShapeRenderer sr = new ShapeRenderer();
            // renderers for the axes
            Renderer arY = new AxisRenderer(Constants.RIGHT, Constants.TOP);
            Renderer arX = new AxisRenderer(Constants.CENTER, Constants.FAR_BOTTOM);

            // return the appropriate renderer for a given visual item
            public Renderer getRenderer(VisualItem item) {
                return item.isInGroup("ylab") ? arY : item.isInGroup("xlab") ? arX : sr;
            }
        });

        /*
         * Step 2: Add X-Axis
         */

        // add the x-axis
        AxisLayout xaxis = new AxisLayout("canUrban", "Province", Constants.X_AXIS, VisiblePredicate.TRUE);

        // ensure the axis spans the width of the data container
        xaxis.setLayoutBounds(g_dataB);

        // add the labels to the x-axis
        AxisLabelLayout xlabels = new AxisLabelLayout("xlab", xaxis, g_xlabB, 15);

        /*
         * Step 3: Add the Y-Axis and its dynamic query feature
         */

        // dynamic query based on population data
        RangeQueryBinding populationQ = new RangeQueryBinding(vt, "2006 Population");
        AndPredicate filter = new AndPredicate(populationQ.getPredicate());

        // add the y-axis
        AxisLayout yaxis = new AxisLayout("canUrban", "2006 Population", Constants.Y_AXIS, VisiblePredicate.TRUE);

        // set the range controls on the y-axis
        yaxis.setRangeModel(populationQ.getModel());
        populationQ.getNumberModel().setValueRange(0, 6000000, 0, 6000000);

        // ensure the y-axis spans the height of the data container
        yaxis.setLayoutBounds(g_dataB);

        // add the labels to the y-axis
        AxisLabelLayout ylabels = new AxisLabelLayout("ylab", yaxis, g_ylabB);
        NumberFormat nf = NumberFormat.getIntegerInstance();
        nf.setMaximumFractionDigits(0);
        ylabels.setNumberFormat(nf);

        /* 
         * Step 4: Add the search box
         */

        // dynamic query based on Geographic name data
        SearchQueryBinding searchQ = new SearchQueryBinding(vt, "Geographic name");
        filter.add(searchQ.getPredicate());		// reuse the same filter as the population query

        /*
         * Step 5: Colours and Shapes
         */

        // assign a set of five perceptually distinct colours to assign to the provinces
        // chosen from ColorBrewer (5-class qualitative Set1)
        int[] palette = new int[]{
            ColorLib.rgb(77, 175, 74),
            ColorLib.rgb(55, 126, 184),
            ColorLib.rgb(228, 26, 28),
            ColorLib.rgb(152, 78, 163),
            ColorLib.rgb(255, 127, 0)
        };

        // specify the stroke (exterior line) based on the ordinal data
        DataColorAction color = new DataColorAction("canUrban", "geographic location",
                Constants.ORDINAL, VisualItem.STROKECOLOR, palette);

        // specify the fill (interior) as a static colour (white)
        ColorAction fill = new ColorAction("canUrban", VisualItem.FILLCOLOR, 0);

        // represent all the data points with rectangles
        ShapeAction shape = new ShapeAction("canUrban", Constants.SHAPE_RECTANGLE);

        // assign the size of the visual element based on the population data (we
        // converted the 2006 Population data to ordinal values)
        DataSizeAction size = new DataSizeAction("canUrban", "population ordinal");

        // setup a counter to keep track of which data points are currently being viewed
        Counter cntr = new Counter("canUrban");

        /*
         * Step 6: Create the action list for drawing the visual elements
         */

        ActionList draw = new ActionList();
        draw.add(cntr);
        draw.add(color);
        draw.add(fill);
        draw.add(shape);
        draw.add(size);
        draw.add(xaxis);
        draw.add(yaxis);
        draw.add(ylabels);
        draw.add(new RepaintAction());
        vis.putAction("draw", draw);
        vis.putAction("xlabels", xlabels);

        /*
         * create the action list for updating the visual elements 
         * (during interactive operations and re-sizing of the window)
         */
        ActionList update = new ActionList();
        update.add(new VisibilityFilter("canUrban", filter));	// filter performs the size/name filtering
        update.add(cntr);
        update.add(xaxis);
        update.add(yaxis);
        update.add(ylabels);
        update.add(new RepaintAction());
        vis.putAction("update", update);

        // create an update listener that will update the visualization when fired
        UpdateListener lstnr = new UpdateListener() {

            public void update(Object src) {
                vis.run("update");
            }
        };

        // add this update listener to the filter, so that when the filter changes (i.e.,
        // the user adjusts the axis parameters, or enters a name for filtering), the 
        // visualization is updated
        filter.addExpressionListener(lstnr);

        /*
         * Step 7: Setup the Display and the other Interface components 
         * (scroll bar, query box, tool tips)
         */

        // create the display
        g_display = new Display(vis);

        // set the display properties
        g_display.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        g_display.setSize(700, 450);
        g_display.setHighQuality(true);

        // call the function that sets the sizes of the containers that contain
        // the data and the axes
        displayLayout();

        // whenever the window is re-sized, update the layout of the axes
        g_display.addComponentListener(new ComponentAdapter() {

            public void componentResized(ComponentEvent e) {
                displayLayout();
            }
        });

        // title label (top left)
        JFastLabel g_details = new JFastLabel("Canadian Urban Population");
        g_details.setPreferredSize(new Dimension(350, 20));
        g_details.setVerticalAlignment(SwingConstants.BOTTOM);

        // total label (top right)
        g_total.setPreferredSize(new Dimension(350, 20));
        g_total.setHorizontalAlignment(SwingConstants.RIGHT);
        g_total.setVerticalAlignment(SwingConstants.BOTTOM);

        // tool tips
        ToolTipControl ttc = new ToolTipControl("label");
        Control hoverc = new ControlAdapter() {

            public void itemEntered(VisualItem item, MouseEvent evt) {
                if (item.isInGroup("canUrban")) {
                    g_total.setText(item.getString("label"));
                    item.setFillColor(item.getStrokeColor());
                    item.setStrokeColor(ColorLib.rgb(0, 0, 0));
                    item.getVisualization().repaint();
                }
            }

            public void itemExited(VisualItem item, MouseEvent evt) {
                if (item.isInGroup("canUrban")) {
                    g_total.setText(g_totalStr);
                    item.setFillColor(item.getEndFillColor());
                    item.setStrokeColor(item.getEndStrokeColor());
                    item.getVisualization().repaint();
                }
            }
        };
        g_display.addControlListener(ttc);
        g_display.addControlListener(hoverc);

        // vertical slider for adjusting the population filter
        JRangeSlider slider = populationQ.createVerticalRangeSlider();
        slider.setThumbColor(null);
        slider.setToolTipText("drag the arrows to filter the data");
        // smallest window: 200,000
        slider.setMinExtent(200000);
        slider.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                g_display.setHighQuality(false);
            }

            public void mouseReleased(MouseEvent e) {
                g_display.setHighQuality(true);
                g_display.repaint();
            }
        });

        // search box
        JSearchPanel searcher = searchQ.createSearchPanel();
        searcher.setLabelText("Urban Centre: ");
        searcher.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 0));

        /*
         * Step 8: Create Containers for the Interface Elements
         */

        // add the listener to this component
        this.addComponentListener(lstnr);

        // container for elements at the top of the screen
        Box topContainer = new Box(BoxLayout.X_AXIS);
        topContainer.add(Box.createHorizontalStrut(5));
        topContainer.add(g_details);
        topContainer.add(Box.createHorizontalGlue());
        topContainer.add(Box.createHorizontalStrut(5));
        topContainer.add(g_total);
        topContainer.add(Box.createHorizontalStrut(5));

        // container for elements at the bottom of the screen
        Box bottomContainer = new Box(BoxLayout.X_AXIS);
        bottomContainer.add(Box.createHorizontalStrut(5));
        bottomContainer.add(searcher);
        bottomContainer.add(Box.createHorizontalGlue());
        bottomContainer.add(Box.createHorizontalStrut(5));
        bottomContainer.add(Box.createHorizontalStrut(16));

        // fonts, colours, etc.
        UILib.setColor(this, ColorLib.getColor(255, 255, 255), Color.GRAY);
        slider.setForeground(Color.LIGHT_GRAY);
        UILib.setFont(bottomContainer, FontLib.getFont("Tahoma", 15));
        g_details.setFont(FontLib.getFont("Tahoma", 18));
        g_total.setFont(FontLib.getFont("Tahoma", 16));

        // add the containers to the JPanel
        add(topContainer, BorderLayout.NORTH);
        add(g_display, BorderLayout.CENTER);
        add(slider, BorderLayout.EAST);
        add(bottomContainer, BorderLayout.SOUTH);

        /*
         * Step 9: Start the Visualization
         */

        vis.run("draw");
        vis.run("xlabels");

    }

    /*
     * calculate the sizes of the data and axes containers based on the
     * display size, and then tell the visualization to update itself and
     * re-draw the x-axis labels
     */
    public void displayLayout() {
        Insets i = g_display.getInsets();
        int w = g_display.getWidth();
        int h = g_display.getHeight();
        int iw = i.left + i.right;
        int ih = i.top + i.bottom;
        int aw = 85;
        int ah = 15;

        g_dataB.setRect(i.left, i.top, w - iw - aw, h - ih - ah);
        g_xlabB.setRect(i.left, h - ah - i.bottom, w - iw - aw, ah - 10);
        g_ylabB.setRect(i.left, i.top, w - iw, h - ih - ah);

        g_vis.run("update");
        g_vis.run("xlabels");
    }

    /*
     * internal class that handles counting the number of elements that are
     * visible in the current view
     */
    private class Counter extends GroupAction {

        public Counter(String group) {
            super(group);
        }

        public void run(double frac) {
            // counters for population and urban centres
            double totalPopulation = 0;
            int urbanCentreCount = 0;

            // iterate through all the visual items that are visible
            VisualItem item = null;
            Iterator items = g_vis.visibleItems("canUrban");
            while (items.hasNext()) {
                item = (VisualItem) items.next();
                // add the population data
                totalPopulation += item.getDouble("2006 Population");
                // increment the counter
                urbanCentreCount++;
            }

            // if there is only one urban centre being displayed, show its information
            // in the counter display; otherwise show the number of urban centres and
            // the total population
            if (urbanCentreCount == 1) {
                g_totalStr = item.getString("label");
            } else {

                g_totalStr = NumberFormat.getIntegerInstance().format(urbanCentreCount) +
                        " Cities, Total Population: " +
                        NumberFormat.getIntegerInstance().format(totalPopulation);

            }
            // set the text in the interface element
            g_total.setText(g_totalStr);

        }
    }
}
