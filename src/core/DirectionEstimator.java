package core;

import connection.MultiClientDataBuffer;

/**
 * 方向估计
 * @author leocai
 *
 */
public interface DirectionEstimator {
	
	DirectionEstimateResults estimate(MultiClientDataBuffer buffer);
	
}
