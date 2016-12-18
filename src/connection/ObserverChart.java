package connection;

import java.util.Observable;
import java.util.Observer;

import draw.RealTimeChart;
import draw.RealTimeChartJFreeChatImpl;
import spacesync.SingleSensorData;

public class ObserverChart implements Observer {

	RealTimeChart realTimeChart = new RealTimeChartJFreeChatImpl("11", new String[] { "" });

	@Override
	public void update(Observable o, Object arg) {
		SingleSensorData sensorData = new SingleSensorData((double[]) arg);
		realTimeChart.addData(sensorData.getGYR());
	}

}
