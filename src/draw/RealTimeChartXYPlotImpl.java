package draw;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class RealTimeChartXYPlotImpl extends RealTimeChartJFreeChatImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9127338033397910052L;
	List<XYSeries> seriesList;
	private JFreeChart lineChart;

	public RealTimeChartXYPlotImpl(String title, String[] columnNames) {
		super(title, columnNames);
	}

	@Override
	public void init(String title, String[] columnNames) {
		seriesList = new ArrayList<>();
		lineChart = ChartFactory.createXYLineChart(title, "index", "readings", createDataset(columnNames),
				PlotOrientation.VERTICAL, true, true, false);
		ChartPanel chartPanel = new ChartPanel(lineChart);
		XYPlot plot = lineChart.getXYPlot();
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setRange(-10, 10);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		setContentPane(chartPanel);
	}

	/**
	 * Creates a sample dataset.
	 * 
	 * @return a sample dataset.
	 */
	private XYDataset createDataset(String[] columnNames) {
		XYSeriesCollection dataset = new XYSeriesCollection();
		for (String col : columnNames) {
			XYSeries series1 = new XYSeries(col);
			seriesList.add(series1);
			//			Random rdm = new Random();
//			series1.add(1,rdm.nextInt(10));
//			series1.add(2,rdm.nextInt(10));
//			series1.add(3,rdm.nextInt(10));
//			series1.add(4,rdm.nextInt(10));
			dataset.addSeries(series1);
		}
		return dataset;

	}

	@Override
	public void addData(double[] data) {
		cuKey++;
		for (int i = 0; i < data.length; i++) {
			seriesList.get(i).add(cuKey, data[i]);
		}
		if (cuKey >= MAX_X) {
			for (int i = 0; i < data.length; i++) {
				seriesList.get(i).remove(0);
			}
		}
	}

	@Override
	public void setRange(int min, int max) {
		XYPlot plot = lineChart.getXYPlot();
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setRange(min, max);
	}
	
	

}
