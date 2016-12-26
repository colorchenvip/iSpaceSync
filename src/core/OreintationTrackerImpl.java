package core;

import connection.MultiClientDataBuffer;
import core.model.SingleSensorData;
import core.model.TrackingResults;
import draw.RealTimeChart;
import transformation.GyrGaccMatrixTracker;
import transformation.TrackingCallBack;
import utils.ChartsUtils;

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
			if (isSyncTime)
				matrixTrackers[i].setInitXAxis(xAxisMulti[i]);
			matrixTrackers[i].track_b2g(trackingCallBacks[i], sensorData.getGYR(), sensorData.getGrivity(),
					sensorData.getDT());
		}
		return null;
	}

}
