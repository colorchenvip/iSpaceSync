package com.dislab.leocai.spacesync.core;

import com.dislab.leocai.spacesync.connection.MultiClientDataBuffer;
import com.dislab.leocai.spacesync.core.model.SensorDataSequnce;
import com.dislab.leocai.spacesync.core.model.TrackingResults;
import com.dislab.leocai.spacesync.utils.DataUtils;
import com.dislab.leocai.spacesync.utils.SpaceSyncConfig;
import com.dislab.leocai.spacesync.utils.VectorUtils;

public class SpaceSyncConsistanceImpl implements SpaceSync {

	DirectionEstimator directionEstimator;
	private OreintationTracker oreintationTracker;
	private int clientsNum;
	private long sampleCount;
	private long preSampleCount = -SpaceSyncConfig.BUFFER_SIZE;

	public SpaceSyncConsistanceImpl(int clientsNum, DirectionEstimator directionEstimator,
			OreintationTracker oreintationTracker) {
		this.directionEstimator = directionEstimator;
		this.oreintationTracker = oreintationTracker;
		this.clientsNum = clientsNum;
	}

	@Override
	public void sync(MultiClientDataBuffer buffer) {
		sampleCount++;
		DirectionEstimateResults directions = null;
		boolean isSyncTime = isSyncTime(buffer, SpaceSyncConfig.SYNC_THRESHOLD);
		if (isSyncTime) {
			System.out.println("Sync Time!");
			preSampleCount = sampleCount;
		}
		directions = directionEstimate(buffer);
		oreintationTracking(buffer, directions, isSyncTime);
	}

	private boolean isSyncTime(MultiClientDataBuffer buffer, double threshold) {
		if ((sampleCount - preSampleCount) < SpaceSyncConfig.BUFFER_SIZE)
			return false;
		for (int i = 0; i < clientsNum; i++) {
			SensorDataSequnce sensorData = buffer.getClientSensorData(i);
			double[][] laccs = sensorData.getLinearAccs();
			double[] rlaccs = DataUtils.resultantData(laccs);
			if (VectorUtils.mean(rlaccs) < threshold)
				return false;
		}
		return true;
	}

	@Override
	public double singleSync(SensorDataSequnce sensorData) {
		return 0;
	}

	@Override
	public DirectionEstimateResults directionEstimate(MultiClientDataBuffer dataBuffer) {
		return directionEstimator.estimate(dataBuffer);
	}

	@Override
	public TrackingResults oreintationTracking(MultiClientDataBuffer buffer,
			DirectionEstimateResults directionEstimateResults, boolean isSyncTime) {
		return oreintationTracker.track(buffer, directionEstimateResults, isSyncTime);
	}

}
