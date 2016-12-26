package utils;

import draw.RealTimeChart;
import draw.RealTimeChartXYPlotImpl;

public class ChartsUtils {

	public static RealTimeChart[] initMultiLacc(String title, int clientsNum) {
		RealTimeChart[] charts = new RealTimeChart[3];
		for (int i = 0; i < clientsNum; i++) {
			charts[i] = new RealTimeChartXYPlotImpl(title + " " + i, new String[] { "x", "y", "z" });
		}
		return charts;
	}

}
