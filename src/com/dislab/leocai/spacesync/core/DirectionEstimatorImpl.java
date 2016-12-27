package com.dislab.leocai.spacesync.core;

import java.util.ArrayList;
import java.util.List;

import com.dislab.leocai.spacesync.connection.MultiClientDataBuffer;
import com.dislab.leocai.spacesync.core.model.SensorDataSequnce;
import com.dislab.leocai.spacesync.transformation.GyrGaccMatrixTracker;
import com.dislab.leocai.spacesync.utils.MatrixUtils;
import com.dislab.leocai.spacesync.utils.VectorUtils;

public class DirectionEstimatorImpl implements DirectionEstimator {

	private int clientsNum;
	private ConsistentExtraction consistentExtraction;
	private GyrGaccMatrixTracker matrixTracker;
	private GlobalLinearAccListener globalLinearAccListener;
	private ConsistantAccListener consistantLinearAccListener;

	public DirectionEstimatorImpl(int clientsNum, ConsistentExtraction consistentExtraction,
			GyrGaccMatrixTracker matrixTracker) {
		this.clientsNum = clientsNum;
		this.consistentExtraction = consistentExtraction;
		this.matrixTracker = matrixTracker;
	}

	@Override
	public DirectionEstimateResults estimate(MultiClientDataBuffer buffer) {
		double[][][] tracked_hori_lacc_multi = new double[clientsNum][][];

		for (int clientId = 0; clientId < clientsNum; clientId++) {
			SensorDataSequnce clientSensorDataList = buffer.getClientSensorData(clientId);

			double[][] tracked_lacc = track(clientSensorDataList, new double[] { 0, 1, 0 });
			double[][] tracked_hori_lacc = MatrixUtils.copyAndSetColumn(tracked_lacc, 2, 0);// project
																							// Horizental

			consistantLinearAccListener.dealWithClientGlobalAcc(clientId, tracked_hori_lacc);
			tracked_hori_lacc_multi[clientId] = tracked_hori_lacc;
		}

		double[] Fc = consistentExtraction
				.extractConsistentData(MatrixUtils.combineMultiClientData(tracked_hori_lacc_multi));
		globalLinearAccListener.dealWithConsistant(Fc);
		DirectionEstimateResults syncResult = getEstimateResults(tracked_hori_lacc_multi, Fc);
		return syncResult;
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
			if (fc[i] >= 0.5) {
				selectedRow.add(i);
			}
		}
		return selectedRow;
	}

	private double[][] track(SensorDataSequnce sensorDataList, double[] init_x_axis) {
		double[][] accs = sensorDataList.getLinearAccs();
		double[][] gyrs = sensorDataList.getGyrs();
		double[][] gravitys = sensorDataList.getGravityAccs();
		double[] dt = sensorDataList.getDT();
		double[][] clinetTrackedData = matrixTracker.trackGlobalAcc(accs, gyrs, gravitys, dt, init_x_axis);
		return clinetTrackedData;
	}

	@Override
	public void addGlobalLinearAccListener(GlobalLinearAccListener globalLinearAccListener) {
		this.globalLinearAccListener = globalLinearAccListener;
	}

	@Override
	public void addConsistantAccListener(ConsistantAccListener clobalLinearAccListener) {
		this.consistantLinearAccListener = clobalLinearAccListener;

	}

}
