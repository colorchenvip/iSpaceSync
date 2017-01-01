package com.dislab.leocai.spacesync.core;
/**
 * 
 */

import com.dislab.leocai.spacesync.core.model.SensorDataSequnce;
import com.dislab.leocai.spacesync.core.model.TrackingResults;

/**
 * @author leocai
 * 算法核心接口，包括方向估计和姿态跟踪
 * 主入口　sync
 *
 */
public interface SpaceSync {


	@Deprecated
	double singleSync(SensorDataSequnce sensorData);

	/**
	 * 方向估计
	 * @param dataBuffer
	 * @return
	 */
	DirectionEstimateResults directionEstimate(MultiClientDataBuffer dataBuffer);
	

	/**
	 * 姿态跟着跟踪
	 * @param buffer
	 * @param directionEstimateResults
	 * 方向估计的返回值
	 * @param isSyncTime
	 * 是否根据direction results进行同步
	 * @return
	 */
	TrackingResults oreintationTracking(MultiClientDataBuffer buffer,  DirectionEstimateResults directionEstimateResults, boolean isSyncTime);

	/**
	 * 同步主入口
	 * @param buffer
	 */
	void sync(MultiClientDataBuffer buffer);

	void setDirectionListener(DirectionListener directionListener);
	
}
