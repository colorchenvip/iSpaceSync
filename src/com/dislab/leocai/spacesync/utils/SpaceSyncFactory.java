package com.dislab.leocai.spacesync.utils;

import com.dislab.leocai.spacesync.core.ConsistantAccListener;
import com.dislab.leocai.spacesync.core.ConsistentExtraction;
import com.dislab.leocai.spacesync.core.ConsistentExtractionImpl;
import com.dislab.leocai.spacesync.core.DirectionEstimator;
import com.dislab.leocai.spacesync.core.DirectionEstimatorImpl;
import com.dislab.leocai.spacesync.core.GlobalLinearAccListener;
import com.dislab.leocai.spacesync.core.OreintationTracker;
import com.dislab.leocai.spacesync.core.OreintationTrackerImpl;
import com.dislab.leocai.spacesync.core.SpaceSync;
import com.dislab.leocai.spacesync.core.SpaceSyncConsistanceImpl;
import com.dislab.leocai.spacesync.transformation.GyrGaccMatrixTracker;
import com.dislab.leocai.spacesync.transformation.TrackingCallBack;

public class SpaceSyncFactory {

	/**
	 * 构造默认的spacesync算法
	 * @param clientsNum
	 * @param trackingCallBacks
	 * @param consistantAccListener
	 * @param globalLinearAccListener
	 * @return
	 */
	public static SpaceSync getDefaultSpaceSync(int clientsNum, TrackingCallBack[] trackingCallBacks,
			ConsistantAccListener consistantAccListener, GlobalLinearAccListener globalLinearAccListener) {
		ConsistentExtraction consistentExtraction = new ConsistentExtractionImpl();
		GyrGaccMatrixTracker matrixTracker = new GyrGaccMatrixTracker();
		DirectionEstimator directionEstimator = new DirectionEstimatorImpl(clientsNum, consistentExtraction,
				matrixTracker);
		directionEstimator.addConsistantAccListener(consistantAccListener);
		directionEstimator.addGlobalLinearAccListener(globalLinearAccListener);
		GyrGaccMatrixTracker[] matrixTrackers = new GyrGaccMatrixTracker[clientsNum];
		for (int i = 0; i < clientsNum; i++) {
			matrixTrackers[i] = new GyrGaccMatrixTracker();
		}
		OreintationTracker oreintationTracker = new OreintationTrackerImpl(clientsNum, matrixTrackers,
				trackingCallBacks);
		SpaceSync spaceSync = new SpaceSyncConsistanceImpl(clientsNum, directionEstimator, oreintationTracker);
		return spaceSync;
	}

}
