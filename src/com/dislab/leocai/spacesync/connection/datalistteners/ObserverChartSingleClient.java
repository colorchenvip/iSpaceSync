package com.dislab.leocai.spacesync.connection.datalistteners;


import java.util.Observable;
import java.util.Observer;

import com.dislab.leocai.spacesync.core.model.SingleSensorData;
import com.dislab.leocai.spacesync.draw.RealTimeChart;
import com.dislab.leocai.spacesync.draw.RealTimeChartXYPlotImpl;


public class ObserverChartSingleClient implements Observer {

	RealTimeChart realTimeChart = new RealTimeChartXYPlotImpl("GYR", new String[] { "GYR X", "GYR Y", "GYR Z" });

	{
		realTimeChart.setRange(-4, 4);
	}

	@Override
	public void update(Observable o, Object arg) {
		SingleSensorData sensorData = new SingleSensorData((double[]) arg);
		realTimeChart.addData(sensorData.getGYR());
	}

}
