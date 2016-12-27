package com.dislab.leocai.spacesync.ui;

import java.awt.GridLayout;
import java.awt.Panel;

import javax.media.j3d.Canvas3D;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import com.dislab.leocai.spacesync.core.ConsistantAccListener;
import com.dislab.leocai.spacesync.core.GlobalLinearAccListener;

public class SpaceSyncPCFrameDataListener extends ApplicationFrame
		implements ConsistantAccListener, GlobalLinearAccListener {

	Panel panelPhoneView;
	Panel panelRightCharts;
	Panel panelDownCharts;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1403264489283928697L;
	private int clientsNum;
	private Panel panelTop;

	public SpaceSyncPCFrameDataListener(String title, int clientsNum) {
		super(title);
		this.clientsNum = clientsNum;
		initPanel(clientsNum);
		pack();
		RefineryUtilities.centerFrameOnScreen(this);
		setVisible(true);
		setSize(1024, 768);

	}

	private void initPanel(int clientsNum) {
		setLayout(new GridLayout(2, 1));
		panelTop = new Panel(new GridLayout(1, 2));
		panelPhoneView = new Panel(new GridLayout(clientsNum, 1));
		panelRightCharts = new Panel(new GridLayout(clientsNum, 1));

		panelTop.add(panelPhoneView);
		panelTop.add(panelRightCharts);
		add(panelTop);
		panelDownCharts = new Panel(new GridLayout(1, 1));
		panelDownCharts.setSize(800, 50);
		add(panelDownCharts);

		chartFc = new RealTimeChartXYPlotImpl("Consistant Acc", new String[] { "PC1" }, false);
		JFreeChart chart = chartFc.getLineChart();
		panelDownCharts.add(new ChartPanel(chart));

		chartGlobalAcc = new RealTimeChartXYPlotImpl[3];
		for (int i = 0; i < clientsNum; i++) {
			chartGlobalAcc[i] = new RealTimeChartXYPlotImpl("", new String[] { "X", "Y", "Z" }, false);
			JFreeChart lchart = chartGlobalAcc[i].getLineChart();
			panelRightCharts.add(new ChartPanel(lchart));
		}
	}

	RealTimeChartXYPlotImpl chartFc;
	RealTimeChartXYPlotImpl[] chartGlobalAcc;

	@Override
	public void dealWithClientGlobalAcc(int clientId, double[][] tracked_hori_lacc) {
		chartGlobalAcc[clientId].showStaticData(tracked_hori_lacc);
	}

	@Override
	public void dealWithConsistant(double[] fc) {
		chartFc.showStaticData(fc);
	}

	public void addPhoneView(Canvas3D canvas) {
		panelPhoneView.add(canvas);
	}

}
