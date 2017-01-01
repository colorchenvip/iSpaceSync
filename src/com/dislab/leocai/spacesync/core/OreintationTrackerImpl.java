package com.dislab.leocai.spacesync.core;

import com.dislab.leocai.spacesync.connection.MultiClientDataBuffer;
import com.dislab.leocai.spacesync.core.model.SingleSensorData;
import com.dislab.leocai.spacesync.core.model.TrackingResults;
import com.dislab.leocai.spacesync.transformation.GyrGaccMatrixTracker;
import com.dislab.leocai.spacesync.transformation.TrackingCallBack;
import com.dislab.leocai.spacesync.utils.MatrixUtils;
import com.dislab.leocai.spacesync.utils.RotationUtils;

public class OreintationTrackerImpl implements OreintationTracker {

	private int clientNum;
	private GyrGaccMatrixTracker[] matrixTrackers;
	private TrackingCallBack[] trackingCallBacks;


	public OreintationTrackerImpl(int clientsNum, GyrGaccMatrixTracker[] matrixTrackers,
			TrackingCallBack[] trackingCallBacks) {
		this.clientNum = clientsNum;
		this.trackingCallBacks = trackingCallBacks;
		this.matrixTrackers = matrixTrackers;
	}

	@Override
	public TrackingResults track(MultiClientDataBuffer buffer, DirectionEstimateResults directionEstimateResults, boolean isSyncTime) {
		double[][] xAxisMulti = null;
		if (isSyncTime)
			xAxisMulti = directionEstimateResults.getClientsInitXAxis();
		for (int i = 0; i < clientNum; i++) {
			SingleSensorData sensorData = new SingleSensorData(buffer.getClientFirstData(i));
			if (isSyncTime){
				matrixTrackers[i].setInitXAxis(xAxisMulti[i]);
				double[][] rtm_b2g = MatrixUtils.T(RotationUtils.getRotationMatrixG2BBy2Vectors(sensorData.getGrivity(), xAxisMulti[i]));
				trackingCallBacks[i].dealWithRotationMatrix_b2g(rtm_b2g);
			}
			matrixTrackers[i].track_b2g(trackingCallBacks[i], sensorData.getGYR(), sensorData.getGrivity(),
					sensorData.getDT());
		}
		return null;
	}

}
