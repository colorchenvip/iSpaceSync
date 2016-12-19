package test;

import java.io.IOException;
import java.util.Arrays;

import draw.RealTimeChart;
import draw.RealTimeChartXYPlotImpl;
import spacesync.ConsistentExtraction;
import spacesync.ConsistentExtractionImpl;
import spacesync.SensorDataList;
import utils.DataLoadUtils;

public class TestConsistentExtraction {
	static ConsistentExtraction consistentExtraction = new ConsistentExtractionImpl();
	static RealTimeChart chart = new RealTimeChartXYPlotImpl("Consistent Data", new String[] { "pc1" });

	public static void main(String[] args) throws IOException {
		SensorDataList sensorData = DataLoadUtils.loadSensorData("./datas/run_walk_native_gravity/top/walk1.csv");
	}

	private static void test1() {
		double[] data = new double[] { 1, 2, 3, 4, 5, 6 };
		consistentExtraction.addData(data);
		data = new double[] { 6, 5, 4, 3, 2, 1 };
		consistentExtraction.addData(data);
		data = new double[] { 2, 2, 2, 2, 2, 2 };
		consistentExtraction.addData(data);
		double[] consistentData = consistentExtraction.extractConsistentData();
		System.out.println(Arrays.toString(consistentData));
		chart.showStaticData(consistentData);
	}

}
