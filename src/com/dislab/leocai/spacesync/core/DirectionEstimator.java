package com.dislab.leocai.spacesync.core;

import com.dislab.leocai.spacesync.connection.MultiClientDataBuffer;

/**
 * 方向估计
 * @author leocai
 *
 */
public interface DirectionEstimator {
	
	DirectionEstimateResults estimate(MultiClientDataBuffer buffer);
	
}
