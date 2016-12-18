package test;

import java.util.Random;

import draw.RealTimeChart;
import draw.RealTimeChartJFreeChatImpl;
import draw.RealTimeChartXYPlotImpl;

public class TestJFreeChart {

	public static void main(String[] args) {

		testXYChart();
	}

	private static void testXYChart() {
		RealTimeChart realTimeChart = new RealTimeChartXYPlotImpl("asd", new String[] { "1", "2", "3" });
		for (int i = 0; i < 1000; i++) {
			Random random = new Random();
			double[] data3 = new double[] { 5+random.nextInt(2), 5+random.nextInt(1), 5+random.nextInt(2) };
			realTimeChart.addData(data3);
			try {
				Thread.sleep(100);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void testLineChart() {
		RealTimeChart realTimeChart = new RealTimeChartJFreeChatImpl("asd", new String[] { "1", "2", "3" });
		for (int i = 0; i < 1000; i++) {
			Random random = new Random();
			double[] data3 = new double[] { 10+random.nextInt(2), 12+random.nextInt(1), 16+random.nextInt(2) };
			realTimeChart.addData(data3);
			try {
				Thread.sleep(100);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
