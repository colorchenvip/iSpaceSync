package test;

import java.io.IOException;
import java.util.Arrays;

import draw.PhoneDisplayer;
import draw.PhoneDisplayerImpl;
import spacesync.CoordinateTracker;
import spacesync.SensorData;
import transformation.GyroMatrixTracker;
import utils.DataLoadUtils;
import utils.MatrixUtils;
import utils.PlotUtils;

public class TestTrackGyro {

	public static void main(String[] args) throws IOException {

		testAssumedData();

		// test2();
		// test3();

		// PlotUtils.plotCompareData(computed_gacc, g_acc);
		// PlotUtils.plotCompareData(g_acc, sensorData.getLinearAccs());

		// testSimple();

	}

	private static void testAssumedData() {
		PhoneDisplayer phoneDisplayer = new PhoneDisplayerImpl();
		phoneDisplayer.initView();
		GyroMatrixTracker matrixUpdate = new GyroMatrixTracker();
//		for (int i = 0; i < 1000; i++) {
//			double[] gyr = new double[] { 0, 0, 0.1 };
//			double[][] cumatrix = matrixUpdate.updateMatrixByGYR(gyr, 0.1);
//			phoneDisplayer.setRotationMatrix_b2g(MatrixUtils.T(cumatrix));
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		for (int i = 0; i < 1000; i++) {
//			double[] gyr = new double[] { 0, 0.1, 0.1 };
//			double[][] cumatrix = matrixUpdate.updateMatrixByGYR(gyr, 0.1);
//			phoneDisplayer.setRotationMatrix_b2g(MatrixUtils.T(cumatrix));
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
		for (int i = 0; i < 1000; i++) {
			double[] gyr = new double[] { 0.1, 0.1, 0.1 };
			double[][] cumatrix = matrixUpdate.trackByGYR(gyr, 0.1);
			phoneDisplayer.setRotationMatrix_b2g(MatrixUtils.T(cumatrix));
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private static void test3() throws IOException {
		SensorData sensorData = DataLoadUtils.loadSensorData("./datas/1.csv");
		double[][] computed_gacc = sensorData.computeGlobalByMag();
		CoordinateTracker coordinateTracker = new CoordinateTracker();
		double[][] g_acc = coordinateTracker.trackByGyro(sensorData.getLinearAccs(), sensorData.getGyrs(),
				sensorData.getDT(), sensorData.getInitGlobalMatrix_G2B());
	}

	private static void test2() throws IOException {
		SensorData sensorData = DataLoadUtils.loadSensorData("./datas/1.csv");
		double[][] local_acc = sensorData.getLinearAccs();
		CoordinateTracker coordinateTracker = new CoordinateTracker();
		double[][] g_acc = coordinateTracker.trackByGyro(sensorData.getLinearAccs(), sensorData.getGyrs(),
				sensorData.getDT(), MatrixUtils.MATRIX_E);
		PlotUtils.plotCompareData(local_acc, g_acc);
	}

	private static void testSimple() {
		CoordinateTracker coordinateTracker = new CoordinateTracker();
		double[][] accs = new double[][] { { 1, 0, 0 }, { 0, 2, 0 }, { 0, 3, 0 }, };
		double[][] gyrs = new double[][] { { -Math.PI / 2, 0, 0 }, { -Math.PI / 2, 0, 0 }, { 0, 0, 0 }, };
		double[] dt = new double[] { 1, 1, 1 };
		double[][] initMatrix_g2b = MatrixUtils.MATRIX_E;
		double[][] gacc = coordinateTracker.trackByGyro(accs, gyrs, dt, initMatrix_g2b);
		for (int i = 0; i < gacc.length; i++)
			System.out.println(Arrays.toString(gacc[i]));
	}

}
