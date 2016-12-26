package connection.datalistteners;

import java.util.Observable;
import java.util.Observer;

import core.model.SingleSensorData;
import draw.RealTimeChart;
import draw.RealTimeChartJFreeChatImpl;
import draw.RealTimeChartXYPlotImpl;

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
