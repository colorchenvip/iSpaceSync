package test;

import java.io.IOException;
import java.util.Arrays;

import connection.MultiClientDataBuffer;
import core.ConsistentExtraction;
import core.ConsistentExtractionImpl;
import core.model.SensorDataSequnce;
import draw.RealTimeChart;
import draw.RealTimeChartXYPlotImpl;
import utils.DataLoadUtils;
import utils.MatrixUtils;
import utils.PlotUtils;

public class TestConsistentExtraction {
	static RealTimeChart chart = new RealTimeChartXYPlotImpl("Consistent Data", new String[] { "pc1" });
	private static double[][] data;

	public static void main(String[] args) throws IOException {
//
//		double[][] m1 = new double[][] { { 0, 0, 1 }, { 1, 1, 1 }, { 2, 2, 2 } };
//		double[][] m2 = new double[][] { { 3, 2, 1 }, { 7, 5, 1 }, { 2, 0, 9 } };
//		double[][] rs = MatrixUtils.cbind(m1, m2);
//		MatrixUtils.printMatrix(rs);
//
//		SensorDataList top = DataLoadUtils.loadSensorData("./datas/run_walk_native_gravity/top/walk1.csv");
//		SensorDataList left = DataLoadUtils.loadSensorData("./datas/run_walk_native_gravity/leftpants/walk1.csv");
//		SensorDataList right = DataLoadUtils.loadSensorData("./datas/run_walk_native_gravity/rightpants/walk1.csv");
//		double[][] bindedData = MatrixUtils.cbind(top.getLinearAccs(), left.getLinearAccs());
//		bindedData = MatrixUtils.cbind(bindedData, left.getLinearAccs());
//		double[] consistData = consistentExtraction.extractConsistentData(data);
//		PlotUtils.plotData(MatrixUtils.selectColumn(top.getLinearAccs(), 2));
//		PlotUtils.plotData(consistData);
	}


}
