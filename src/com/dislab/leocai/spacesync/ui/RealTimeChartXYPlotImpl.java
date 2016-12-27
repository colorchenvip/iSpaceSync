package com.dislab.leocai.spacesync.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class RealTimeChartXYPlotImpl extends ApplicationFrame implements RealTimeChart {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9127338033397910052L;
	List<XYSeries> seriesList;
	private JFreeChart lineChart;

	protected long cuKey;
	protected static final int MAX_X = 100;

	public RealTimeChartXYPlotImpl(String title, String[] columnNames, boolean visible) {
		super(title);
		init(title, columnNames);
		pack();
		RefineryUtilities.centerFrameOnScreen(this);
		setVisible(visible);
	}

	public RealTimeChartXYPlotImpl(String title, String[] columnNames) {
		super(title);
		init(title, columnNames);
		pack();
		RefineryUtilities.centerFrameOnScreen(this);
		setVisible(true);
	}

	public void init(String title, String[] columnNames) {
		seriesList = new ArrayList<>();
		lineChart = ChartFactory.createXYLineChart(title, "index", "readings", createDataset(columnNames),
				PlotOrientation.VERTICAL, true, true, false);
		ChartPanel chartPanel = new ChartPanel(lineChart);
		XYPlot plot = lineChart.getXYPlot();
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setRange(-10, 10);
		// chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
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

	@Override
	public void showStaticData(final double[] data) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				clearData();
				for (double d : data) {
					addData(new double[] { d });
				}
			}
		});
	}

	@Override
	public void showStaticData(final double[][] data) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				clearData();
				for (double[] d : data) {
					addData(d);
				}
			}
		});
	}

	@Override
	public void clearData() {
		synchronized (this) {
			cuKey = 0;
			for (XYSeries series : seriesList) {
				series.clear();
			}
		}
	}

	public JFreeChart getLineChart() {
		return lineChart;
	}

	public void setLineChart(JFreeChart lineChart) {
		this.lineChart = lineChart;
	}

}
