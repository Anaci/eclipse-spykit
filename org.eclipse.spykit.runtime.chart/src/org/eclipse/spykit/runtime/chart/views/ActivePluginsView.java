package org.eclipse.spykit.runtime.chart.views;

import java.awt.Font;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.internal.stats.BundleStats;
import org.eclipse.core.runtime.internal.stats.StatsManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.spykit.runtime.chart.Activator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.part.ViewPart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.eclipse.jface.action.Action;


public class ActivePluginsView extends ViewPart {

	public static final String ID = "org.eclipse.spykit.runtime.views.ActivePluginsView"; //$NON-NLS-1$
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	
	private Text txtInstanceLocation;
	private Text txtLogFileLocation;
	private Text txtPlatformInstallOs;
	private Text txtNewText;
	private Table table;
	private ChartComposite chartComposite1;
	private ChartComposite chartComposite2;
	
	public ActivePluginsView() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		ScrolledForm sForm = toolkit.createScrolledForm(parent);
		
		Composite container = sForm.getBody();
		toolkit.paintBordersFor(container);
		sForm.getBody().setLayout(new GridLayout(1, false));
		
		Section sctnNewSection = toolkit.createSection(container, Section.TWISTIE | Section.TITLE_BAR);
		sctnNewSection.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1));
		toolkit.paintBordersFor(sctnNewSection);
		sctnNewSection.setText("Platform");
		sctnNewSection.setExpanded(true);
		
		Composite composite_2 = toolkit.createComposite(sctnNewSection, SWT.NONE);
		toolkit.paintBordersFor(composite_2);
		sctnNewSection.setClient(composite_2);
		{
			TableWrapLayout twl_composite_2 = new TableWrapLayout();
			twl_composite_2.numColumns = 2;
			composite_2.setLayout(twl_composite_2);
		}
		
		Label lblNewLabel = toolkit.createLabel(composite_2, "Installation Location", SWT.NONE);
		lblNewLabel.setLayoutData(new TableWrapData(TableWrapData.RIGHT, TableWrapData.MIDDLE, 1, 1));
		lblNewLabel.setBounds(0, 0, 49, 13);
		
		txtNewText = toolkit.createText(composite_2, "New Text", SWT.NONE);
		txtNewText.setText(Platform.getInstallLocation().getURL().toString());
		txtNewText.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.TOP, 1, 1));
		
		Label lblInstanceLocation = new Label(composite_2, SWT.NONE);
		lblInstanceLocation.setLayoutData(new TableWrapData(TableWrapData.LEFT, TableWrapData.MIDDLE, 1, 1));
		toolkit.adapt(lblInstanceLocation, true, true);
		lblInstanceLocation.setText("Instance Location");
		
		txtInstanceLocation = new Text(composite_2, SWT.BORDER);
		txtInstanceLocation.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.TOP, 1, 1));
		txtInstanceLocation.setText(Platform.getInstanceLocation().getURL().toString());
		toolkit.adapt(txtInstanceLocation, true, true);
		
		Label lblLogFileLocation = new Label(composite_2, SWT.NONE);
		lblLogFileLocation.setLayoutData(new TableWrapData(TableWrapData.LEFT, TableWrapData.MIDDLE, 1, 1));
		toolkit.adapt(lblLogFileLocation, true, true);
		lblLogFileLocation.setText("Log File Location");
		
		txtLogFileLocation = new Text(composite_2, SWT.BORDER);
		txtLogFileLocation.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.TOP, 1, 1));
		txtLogFileLocation.setText(Platform.getLogFileLocation().toOSString());
		toolkit.adapt(txtLogFileLocation, true, true);
		
		Label lblPlatformInstallOs = new Label(composite_2, SWT.NONE);
		lblPlatformInstallOs.setLayoutData(new TableWrapData(TableWrapData.LEFT, TableWrapData.MIDDLE, 1, 1));
		toolkit.adapt(lblPlatformInstallOs, true, true);
		lblPlatformInstallOs.setText("Platform Install OS");
		
		txtPlatformInstallOs = new Text(composite_2, SWT.BORDER);
		txtPlatformInstallOs.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.TOP, 1, 1));
		txtPlatformInstallOs.setText(Platform.getOS());
		toolkit.adapt(txtPlatformInstallOs, true, true);
		
		Section sctnSection_1 = toolkit.createSection(container, Section.TITLE_BAR);
		sctnSection_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		toolkit.paintBordersFor(sctnSection_1);
		sctnSection_1.setText("Startup Analysis");
		sctnSection_1.setExpanded(true);
		
		Composite composite_1 = toolkit.createComposite(sctnSection_1, SWT.NONE);
		toolkit.paintBordersFor(composite_1);
		sctnSection_1.setClient(composite_1);
		composite_1.setLayout(new GridLayout(2, false));
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
				
		JFreeChart chart1 = createStartupAnalysisChart(createStartupAnalysisDataset(), "Startup Analysis I");
		chartComposite1 = new ChartComposite(composite_1, SWT.NONE,
				chart1, true);
		chartComposite1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		toolkit.paintBordersFor(chartComposite1);
		
		JFreeChart chart2 = createStartupAnalysisChart(createActivatedByDataset(), "Startup Analysis II");
		chartComposite2 = new ChartComposite(composite_1, SWT.NONE,
				chart2, true);
		chartComposite2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		toolkit.paintBordersFor(chartComposite2);
		
		createActions();
		initializeToolBar();
		initializeMenu();
		
		Activator.getDefault().getBundle().getBundleContext().addBundleListener(new BundleListener() {
			
			@Override
			public void bundleChanged(BundleEvent event) {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						JFreeChart chart1 = createStartupAnalysisChart(createStartupAnalysisDataset(), "Startup Analysis I");
						chartComposite1.setChart(chart1);
						
						JFreeChart chart2 = createStartupAnalysisChart(createActivatedByDataset(), "Startup Analysis II");
						chartComposite2.setChart(chart2);
					}
				});
				
			}
		});
	}
	
	/*
	 * Creates the Chart based on a dataset */

	private PieDataset createActivatedByDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		BundleStats[] bundles = StatsManager.getDefault().getBundles();
		HashMap<String, Integer> bundleMap = new HashMap<String, Integer>();
		BundleStats parentBundle = null;
		for (BundleStats bundle : bundles) {
			if (bundle.getActivatedBy() != null) {
				parentBundle = fetchParentBundle(bundle);				
			} else {
				parentBundle = bundle;
			}
			
			Integer bundleCount = 1;
			if (parentBundle != null && bundleMap.containsKey(parentBundle.getSymbolicName())) {
				bundleCount = bundleMap.get(parentBundle.getSymbolicName());
				bundleCount += 1;				
			} 			
			bundleMap.put(parentBundle.getSymbolicName(), bundleCount);
			
		}
	
		for (Iterator iterator = bundleMap.keySet().iterator(); iterator.hasNext();) {
			String type = (String) iterator.next();
			Integer count = bundleMap.get(type);
			if (count != 1) {
				dataset.setValue(type, count);
			}
		}
		
		
		return dataset;
	}

	private BundleStats fetchParentBundle(BundleStats bundle) {
		BundleStats parentBundle = bundle.getActivatedBy();
				if (parentBundle != null) {
					parentBundle = fetchParentBundle(parentBundle);
				} else {
					parentBundle = bundle;
				}
		return parentBundle;
	}

	private JFreeChart createStartupAnalysisChart(PieDataset dataset, String chartTitle) {

		JFreeChart chart = ChartFactory.createPieChart(chartTitle, // charttitle
				dataset, // data
				true, // include legend
				true, false);

		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setSectionOutlinesVisible(false);
		plot.setLabelFont(new Font("ARIAL", Font.PLAIN, 12));
		plot.setNoDataMessage("No data available");
		plot.setCircular(false);
		plot.setLabelGap(0.02);
		return chart;

	}

	
	/** * Creates the Dataset for the Pie chart */

	private PieDataset createStartupAnalysisDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		int noOfStartupBundles = 0;
		int noOfActivatedBundles = 0;
		int noOfIndirectActivatedBundles = 0;
		BundleStats[] bundles = StatsManager.getDefault().getBundles();
		for (BundleStats bundle : bundles) {
			if (bundle.isStartupBundle()) {
				noOfStartupBundles +=1;
			} else {
				if (bundle.getActivatedBy() == null) {
					noOfIndirectActivatedBundles += 1;
				} else {
					noOfActivatedBundles += 1;
				}
			}
		}
				
		dataset.setValue("IStartup Contributions", noOfStartupBundles);
		dataset.setValue("Extension Contributions", noOfIndirectActivatedBundles);
		dataset.setValue("Dependant Activation", noOfActivatedBundles);
		

		
		return dataset;
	}


	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
		
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars()
				.getToolBarManager();
		
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

}
