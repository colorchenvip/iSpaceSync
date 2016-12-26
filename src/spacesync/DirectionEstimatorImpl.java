package spacesync;

import java.util.ArrayList;
import java.util.List;

import draw.RealTimeChart;
import draw.RealTimeChartXYPlotImpl;
import transformation.GyrGaccMatrixTracker;
import utils.ChartsUtils;
import utils.MatrixUtils;
import utils.MyDataBuffer;
import utils.VectorUtils;

public class DirectionEstimatorImpl implements DirectionEstimator {

	private int clientsNum;
	private ConsistentExtraction consistentExtraction;
	private GyrGaccMatrixTracker matrixTracker;
	RealTimeChart charts[];
	RealTimeChart chartFc;

	public DirectionEstimatorImpl(int clientsNum, ConsistentExtraction consistentExtraction,
			GyrGaccMatrixTracker matrixTracker) {
		this.clientsNum = clientsNum;
		this.consistentExtraction = consistentExtraction;
		this.matrixTracker = matrixTracker;
		charts = ChartsUtils.initMultiLacc("Tracked Horizental Linear Acc", clientsNum);
		chartFc = new RealTimeChartXYPlotImpl("FC", new String[] { "PC!" });
	}

	@Override
	public DirectionEstimateResults estimate(MyDataBuffer buffer) {
		double[][][] tracked_hori_lacc_multi = new double[clientsNum][][];

		for (int clientId = 0; clientId < clientsNum; clientId++) {
			SensorDataList clientSensorDataList = buffer.getClientSensorData(clientId);

			double[][] tracked_lacc = track(clientSensorDataList, new double[] { 0, 1, 0 });
			double[][] tracked_hori_lacc = MatrixUtils.copyAndSetColumn(tracked_lacc, 2, 0);// project
																							// Horizental

			plotClinet(clientId, tracked_hori_lacc);

			tracked_hori_lacc_multi[clientId] = tracked_hori_lacc;
		}

		double[] Fc = consistentExtraction
				.extractConsistentData(MatrixUtils.combineMultiClientData(tracked_hori_lacc_multi));
		chartFc.showStaticData(Fc);
		DirectionEstimateResults syncResult = getEstimateResults(tracked_hori_lacc_multi, Fc);
		return syncResult;
	}

	private void plotClinet(int clientId, double[][] linearAccs) {
		charts[clientId].showStaticData(linearAccs);
	}

	private DirectionEstimateResults getEstimateResults(double[][][] tracked_hori_lacc_multi, double[] Fc) {
		List<Integer> selectedRows = selectIndexesByFc(Fc);
		DirectionEstimateResults syncResult = new DirectionEstimateResults(clientsNum);
		for (int clientId = 0; clientId < clientsNum; clientId++) {
			double[][] selected_Fi_multi_data = MatrixUtils.selectRows(tracked_hori_lacc_multi[clientId], selectedRows);
			double syncRs[] = new double[3];
			for (int j = 0; j < 3; j++) {
				double[] fi_i = MatrixUtils.selectColumn(selected_Fi_multi_data, j);
				syncRs[j] = VectorUtils.mean(fi_i);
			}
			syncResult.set(clientId, syncRs);
		}
		return syncResult;
	}

	private List<Integer> selectIndexesByFc(double[] fc) {
		List<Integer> selectedRow = new ArrayList<>(fc.length);
		for (int i = 0; i < fc.length; i++) {
			if (fc[i] >= 0) {
				selectedRow.add(i);
			}
		}
		return selectedRow;
	}

	private double[][] track(SensorDataList sensorDataList, double[] init_x_axis) {
		double[][] accs = sensorDataList.getLinearAccs();
		double[][] gyrs = sensorDataList.getGyrs();
		double[][] gravitys = sensorDataList.getGravityAccs();
		double[] dt = sensorDataList.getDT();
		double[][] clinetTrackedData = matrixTracker.trackGlobalAcc(accs, gyrs, gravitys, dt, init_x_axis);
		return clinetTrackedData;
	}

}
