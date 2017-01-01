package com.dislab.leocai.spacesync.core;

import java.util.ArrayList;
import java.util.List;

import com.dislab.leocai.spacesync.connection.MultiClientDataBuffer;
import com.dislab.leocai.spacesync.core.model.SensorDataSequnce;
import com.dislab.leocai.spacesync.transformation.GyrGaccMatrixTracker;
import com.dislab.leocai.spacesync.utils.MatrixUtils;
import com.dislab.leocai.spacesync.utils.RotationUtils;
import com.dislab.leocai.spacesync.utils.SpaceSyncConfig;
import com.dislab.leocai.spacesync.utils.VectorUtils;

public class DirectionEstimatorImpl implements DirectionEstimator {

	private int clientsNum;
	private ConsistentExtraction consistentExtraction;
	private GyrGaccMatrixTracker matrixTracker;

	private ConsistantAccListener consistantAccListener;
	private LinearAccListener linearAccListener;

	public DirectionEstimatorImpl(int clientsNum, ConsistentExtraction consistentExtraction,
			GyrGaccMatrixTracker matrixTracker) {
		this.clientsNum = clientsNum;
		this.consistentExtraction = consistentExtraction;
		this.matrixTracker = matrixTracker;
	}

	@Override
	public DirectionEstimateResults estimate(MultiClientDataBuffer buffer) {
		double[][][] tracked_hori_lacc_multi = new double[clientsNum][][];

		double[][][] rtm_g2bs = new double[clientsNum][][];

		for (int clientId = 0; clientId < clientsNum; clientId++) {
			SensorDataSequnce clientSensorDataList = buffer.getClientSensorData(clientId);
			double[] initXAxis = new double[] { 0, 1, 0 };
			adjustInitXAxis(initXAxis, clientSensorDataList.getGravityAccs()[0][0]);
			double[][] tracked_lacc = track(clientSensorDataList, initXAxis);
			double[][] tracked_hori_lacc = MatrixUtils.copyAndSetColumn(tracked_lacc, 2, 0);// project
																							// Horizental
			if (linearAccListener != null)
				linearAccListener.dealWithClientGlobalAcc(clientId, tracked_hori_lacc);
			rtm_g2bs[clientId] = RotationUtils.getRotationMatrixG2BBy2Vectors(clientSensorDataList.getGravityAccs()[0],
					initXAxis);
			tracked_hori_lacc_multi[clientId] = tracked_hori_lacc;
		}

		double[] Fc = consistentExtraction
				.extractConsistentData(MatrixUtils.combineMultiClientData(tracked_hori_lacc_multi));
		if (consistantAccListener != null)
			consistantAccListener.dealWithConsistant(Fc);
		DirectionEstimateResults syncResult = getEstimateResults(tracked_hori_lacc_multi, Fc, rtm_g2bs);
		return syncResult;
	}
	
	private void adjustInitXAxis(double[] initXAxis, double d) {
		if(Math.abs(d)>=7){
//			System.out.println("Y");
			initXAxis[0] = 0;
			initXAxis[1] = 1;
		}else{
//			System.out.println("X");
			initXAxis[0] = 1;
			initXAxis[1] = 0;
		}
		
	}

	/**
	 * 获得估计结果
	 * 
	 * @param tracked_hori_lacc_multi
	 * @param Fc
	 * @param rtm_g2b
	 * @return
	 */
	private DirectionEstimateResults getEstimateResults(double[][][] tracked_hori_lacc_multi, double[] Fc,
			double[][][] rtm_g2b) {
		List<Integer> selectedRows = selectIndexesByFc(Fc);
		DirectionEstimateResults syncResult = new DirectionEstimateResults(clientsNum);
		for (int clientId = 0; clientId < clientsNum; clientId++) {
			double[][] selected_Fi_multi_data = MatrixUtils.selectRows(tracked_hori_lacc_multi[clientId], selectedRows);
			double syncRs[] = new double[3];

			for (int j = 0; j < 3; j++) {
				double[] fi_i = MatrixUtils.selectColumn(selected_Fi_multi_data, j);
				syncRs[j] = VectorUtils.mean(fi_i);
			}
			syncResult.setPre(clientId, syncRs);
			syncRs = RotationUtils.getGlobalData(syncRs, rtm_g2b[clientId]);
			syncResult.set(clientId, syncRs);
		}
		return syncResult;
	}

	private List<Integer> selectIndexesByFc(double[] fc) {
		List<Integer> selectedRow = new ArrayList<>(fc.length);
		for (int i = 0; i < fc.length; i++) {
			if (fc[i] >= SpaceSyncConfig.SELECTED_FC_THRESHOLD) {
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
	public void addGlobalLinearAccListener(ConsistantAccListener globalLinearAccListener) {
		this.consistantAccListener = globalLinearAccListener;
	}

	@Override
	public void addConsistantAccListener(LinearAccListener clobalLinearAccListener) {
		this.linearAccListener = clobalLinearAccListener;

	}

}
