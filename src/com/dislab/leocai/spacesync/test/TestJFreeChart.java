package com.dislab.leocai.spacesync.test;

import java.util.Random;

import javax.swing.SwingUtilities;

import com.dislab.leocai.spacesync.ui.RealTimeChart;
import com.dislab.leocai.spacesync.ui.RealTimeChartJFreeChatImpl;
import com.dislab.leocai.spacesync.ui.RealTimeChartXYPlotImpl;


public class TestJFreeChart {

	public static void main(String[] args) {

		testXYChart();
	}

	private static void testXYChart() {
		final RealTimeChart realTimeChart = new RealTimeChartXYPlotImpl("asd", new String[] { "1", "2", "3" });
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < 1000; i++) {
					Random random = new Random();
					final double[] data3 = new double[] { 5+random.nextInt(2), 5+random.nextInt(1), 5+random.nextInt(2) };
					SwingUtilities.invokeLater(new Runnable() {
			            public void run() {
			            	realTimeChart.addData(data3);
			            }
			        });
					
					try {
						Thread.sleep(10);
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		}).start();
		
	}

}
