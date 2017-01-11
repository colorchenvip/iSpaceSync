package com.dislab.leocai.spacesync.core;

import com.dislab.leocai.spacesync.core.model.SensorDataSequnce;
import com.dislab.leocai.spacesync.core.model.TrackingResults;
import com.dislab.leocai.spacesync.utils.DataUtils;
import com.dislab.leocai.spacesync.utils.SpaceSyncConfig;

public class SpaceSyncConsistanceImpl implements SpaceSync {

	DirectionEstimator directionEstimator;
	private OreintationTracker oreintationTracker;
	private DirectionListener directionListener;
	private int clientsNum;
	private long sampleCount;
	private long preSampleCount = -SpaceSyncConfig.BUFFER_SIZE;
	private SyncBufferListener syncBufferListener;

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
		if (syncBufferListener != null)
			syncBufferListener.dealWithSyncBuffer(buffer, isSyncTime);
		if (isSyncTime) {
			System.out.println("Sync Time!");
			preSampleCount = sampleCount;
		}
		directions = directionEstimate(buffer);
		if (directionListener != null)
			directionListener.dealWithDirection(directions, isSyncTime);
		oreintationTracking(buffer, directions, isSyncTime);
	}

	private boolean isSyncTime(MultiClientDataBuffer buffer, double threshold) {
		if ((sampleCount - preSampleCount) < SpaceSyncConfig.BUFFER_SIZE)
			return false;
		for (int i = 0; i < clientsNum; i++) {
			SensorDataSequnce sensorData = buffer.getClientSensorData(i);
			double[][] laccs = sensorData.getLinearAccs();
			double[] rlaccs = DataUtils.resultantData(laccs);
			int count = 0;
			for (double rd : rlaccs) {
				if (rd >= threshold)
					count++;
			}
			if (1.0 * count / rlaccs.length < SpaceSyncConfig.SYNC_THRESHOLD_RATE)
				return false;
		}
		return true;
	}

	@Override
	public void setDirectionListener(DirectionListener directionListener) {
		this.directionListener = directionListener;
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

	@Override
	public void setConsistantAccListener(ConsistantAccListener consistantAccLisener) {
		directionEstimator.setConsistantAccListener(consistantAccLisener);

	}

	@Override
	public void setGlobalLinearAccListener(LinearAccListener laccListener) {
		directionEstimator.setGlobalLinearAccListener(laccListener);
	}

	@Override
	public int getClientsNum() {
		return clientsNum;
	}

	@Override
	public void setDataListener(SyncBufferListener syncBufferListener) {
		this.syncBufferListener = syncBufferListener;

	}

}
