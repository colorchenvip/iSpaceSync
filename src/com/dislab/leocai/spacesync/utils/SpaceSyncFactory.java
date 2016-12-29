package com.dislab.leocai.spacesync.utils;

import com.dislab.leocai.spacesync.core.LinearAccListener;
import com.dislab.leocai.spacesync.core.ConsistentExtraction;
import com.dislab.leocai.spacesync.core.ConsistentExtractionImpl;
import com.dislab.leocai.spacesync.core.DirectionEstimator;
import com.dislab.leocai.spacesync.core.DirectionEstimatorImpl;
import com.dislab.leocai.spacesync.core.ConsistantAccListener;
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
	 * 客户段数量
	 * @param trackingCallBacks
	 * 每个客户端对应的姿态跟踪回调函数
	 * @param consistantAccListener
	 * 一致力数据监听器
	 * @param globalLinearAccListener
	 * 全局线性加速度监听器
	 * @return
	 */
	public static SpaceSync getDefaultSpaceSync(int clientsNum, TrackingCallBack[] trackingCallBacks,
			LinearAccListener consistantAccListener, ConsistantAccListener globalLinearAccListener) {
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
