package draw;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class RealTimeChartJFreeChatImpl extends ApplicationFrame implements RealTimeChart {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static final int MAX_X = 100;

	public RealTimeChartJFreeChatImpl(String title, String[] columnNames) {
		super(title);
		init(title, columnNames);
		pack();
		RefineryUtilities.centerFrameOnScreen(this);
		setVisible(true);
	}

	private DefaultCategoryDataset dataset;
	protected int cuKey;

	public void init(String title, String[] columnNames) {
		 JFreeChart lineChart = ChartFactory.createLineChart(title, "Years", "Number of Schools", createDataset(),
				PlotOrientation.VERTICAL, true, true, false);
		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		setContentPane(chartPanel);
	}

	private CategoryDataset createDataset() {
		dataset = new DefaultCategoryDataset();
		return dataset;
	}

	@Override
	public void addData(double[] data) {
		cuKey++;
		for (int i = 0; i < data.length; i++) {
			dataset.addValue(data[i], i + "", cuKey + "");
		}
		if (cuKey >= MAX_X) {
			for (int i = 0; i < data.length; i++) {
				dataset.removeValue(i + "", (cuKey - MAX_X) + "");
			}
		}
		// dataset.removeValue(rowKey, columnKey);
	}

	@Override
	public void setRange(int min, int max) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showStaticData(double[] ds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		
	}

}
