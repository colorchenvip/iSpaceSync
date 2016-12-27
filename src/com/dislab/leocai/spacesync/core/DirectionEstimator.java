package com.dislab.leocai.spacesync.core;

import com.dislab.leocai.spacesync.connection.MultiClientDataBuffer;

/**
 * 方向估计
 * 
 * @author leocai
 *
 */
public interface DirectionEstimator {

	DirectionEstimateResults estimate(MultiClientDataBuffer buffer);

	/**
	 * 添加数据回调函数
	 * @param globalLinearAccListener
	 */
	void addGlobalLinearAccListener(GlobalLinearAccListener globalLinearAccListener);

	/**
	 * 添加数据回调函数
	 * @param clobalLinearAccListener
	 */
	void addConsistantAccListener(ConsistantAccListener consistantAccListener);

}
