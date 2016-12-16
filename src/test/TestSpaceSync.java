package test;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;

import spacesync.SensorData;
import spacesync.SpaceSync;
import spacesync.SpaceSyncImpl;
import utils.DataLoadUtils;
import utils.MatrixUtils;
import utils.PlotUtils;

public class TestSpaceSync {

	public static void main(String[] args) throws IOException {
		SpaceSync spaceSync = new SpaceSyncImpl();
		SensorData sensorData;
		sensorData = DataLoadUtils.loadSensorData("datas/run_walk_native_gravity/top/walk" + 1 + ".csv");
//		double rs = spaceSync.singleSync(sensorData);
//		System.out.println(rs);
		for (int i = 1; i < 5; i++) {
			sensorData = DataLoadUtils.loadSensorData("datas/run_walk_native_gravity/top/walk" + i + ".csv");
			double rs = spaceSync.singleSync(sensorData);
			System.out.println(rs);
		}
		for (int i = 1; i < 5; i++) {
			sensorData = DataLoadUtils.loadSensorData("datas/run_walk_native_gravity/rightpants/walk" + i + ".csv");
			double rs = spaceSync.singleSync(sensorData);
			System.out.println(rs);
		}
		for (int i = 1; i < 5; i++) {
			sensorData = DataLoadUtils.loadSensorData("datas/run_walk_native_gravity/leftpants/walk" + i + ".csv");
			double rs = spaceSync.singleSync(sensorData);
			System.out.println(rs);
		}
		System.out.println("run");
		for (int i = 1; i < 5; i++) {
			sensorData = DataLoadUtils.loadSensorData("datas/run_walk_native_gravity/top/run" + i + ".csv");
			double rs = spaceSync.singleSync(sensorData);
			System.out.println(rs);
		}
		for (int i = 1; i < 5; i++) {
			sensorData = DataLoadUtils.loadSensorData("datas/run_walk_native_gravity/rightpants/run" + i + ".csv");
			double rs = spaceSync.singleSync(sensorData);
			System.out.println(rs);
		}
		for (int i = 1; i < 5; i++) {
			sensorData = DataLoadUtils.loadSensorData("datas/run_walk_native_gravity/leftpants/run" + i + ".csv");
			double rs = spaceSync.singleSync(sensorData);
			System.out.println(rs);
		}
		// PlotUtils.plotData(sensorData.getLinearAccs());
		// System.out.println(sensorData);
		// double sd[][] = new double[5][10];
		// for (int i = 0; i < 5; i++) {
		// for (int j = 0; j < 10; j++) {
		// sd[i][j] = new Random().nextDouble()*5;
		// }
		// }
		// sensorData.setData(sd);
		// double rs = spaceSync.singleSync(sensorData);
		// System.out.println(rs);
	}

}
