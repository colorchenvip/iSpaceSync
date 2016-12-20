package connection.datalistteners;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import connection.ClientData;
import draw.RealTimeChart;
import draw.RealTimeChartXYPlotImpl;
import spacesync.SensorDataList;
import spacesync.SingleSensorData;

@Deprecated
public class ObserverMultiClientData implements Observer {

	Map<String, RealTimeChart> chartMap = new HashMap<>();
	List<String> clients;
	SingleMultiClientData singleMultiClientData;

	public ObserverMultiClientData(List<String> clients) {
		singleMultiClientData = new SingleMultiClientData(clients);
	}

	@Override
	public void update(Observable o, Object arg) {
		ClientData clientData = (ClientData) arg;
		if (!chartMap.containsKey(clientData.getKey())) {
			RealTimeChart newChart = new RealTimeChartXYPlotImpl(clientData.getKey(), new String[] { "X", "Y", "Z" });
			chartMap.put(clientData.getKey(), newChart);
		}
		RealTimeChart chart = chartMap.get(clientData.getKey());
		SingleSensorData sensorDataList = new SingleSensorData(clientData.getData());
		chart.addData(sensorDataList.getAcc());
		if (singleMultiClientData.add(clientData)) {
			double[] multiData = singleMultiClientData.get();
		}
	}

}
