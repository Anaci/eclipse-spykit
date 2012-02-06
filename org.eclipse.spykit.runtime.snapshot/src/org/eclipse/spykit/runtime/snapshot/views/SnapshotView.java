package org.eclipse.spykit.runtime.snapshot.views;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.internal.stats.BundleStats;
import org.eclipse.core.runtime.internal.stats.StatsManager;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.event.PlotChangeEvent;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.experimental.chart.swt.ChartComposite;

public class SnapshotView extends ViewPart {

	public static final String ID = "org.eclipse.spykit.runtime.snapshot.views.SnapshotView"; //$NON-NLS-1$
	private Action collectAction;
	private Action clearAction;
	private XYSeries series1;
	private List<Double> countOfPlugins = new ArrayList<Double>();
	
	private JFreeChart chart1;
	private ChartComposite chartComposite1;

	public SnapshotView() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		countOfPlugins.add(0, new Double(StatsManager.getDefault().getBundles().length));	
		parent.setLayout(new FillLayout());
		JFreeChart chart1 = createChart(createDataset());
		chartComposite1 = new ChartComposite(parent, SWT.NONE,
				chart1, true);
//		toolkit.paintBordersFor(chartComposite1);

		createActions();
		initializeToolBar();
		initializeMenu();
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
		{
			collectAction = new Action("Collect") {
				@Override
				public void run() {
					BundleStats[] bundles = StatsManager.getDefault().getBundles();
					double noOfBundles = bundles.length;
					countOfPlugins.add(noOfBundles);
					redrawChart();
				}
			};
		}
		{
			clearAction = new Action("Clear") {
				@Override
				public void run() {
					countOfPlugins.clear();
					countOfPlugins.add((double)StatsManager.getDefault().getBundles().length);
					redrawChart();
				}
			};
		}
	}

	protected void redrawChart() {
		JFreeChart chart1 = createChart(createDataset());
		chartComposite1.setChart(chart1);
		chartComposite1.forceRedraw();
		
    
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars()
				.getToolBarManager();
		toolbarManager.add(collectAction);
		toolbarManager.add(clearAction);
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars()
				.getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}
	
	/**
     * Creates a sample dataset.
     * 
     * @return a sample dataset.
     */
    private XYDataset createDataset() {
        
        series1 = new XYSeries("Plugin Set");
        
        int counter = countOfPlugins.size();
        for (Double count : countOfPlugins) {
        	series1.add(counter++, count);
		}
   
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
  
        return dataset;
        
    }
    
    /**
     * Creates a chart.
     * 
     * @param dataset  the data for the chart.
     * 
     * @return a chart.
     */
    private JFreeChart createChart(final XYDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "Plugin Load Snapshot",      // chart title
            "X",                      // x axis label
            "Y",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);
 
//        final StandardLegend legend = (StandardLegend) chart.getLegend();
  //      legend.setDisplaySeriesShapes(true);
        
        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
    //    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
       
        
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.
        
                
        return chart;
        
    }
}
