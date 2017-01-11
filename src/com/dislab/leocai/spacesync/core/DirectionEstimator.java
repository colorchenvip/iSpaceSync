package com.dislab.leocai.spacesync.core;

/**
 * 方向估计
 * 
 * @author leocai
 *
 */
public interface DirectionEstimator {

	/**
	 * 估计初始方向
	 * @param buffer
	 * @return
	 */
	DirectionEstimateResults estimate(MultiClientDataBuffer buffer);
	


	/**
	 * 添加数据回调函数
	 * @param globalLinearAccListener
	 */
	void setConsistantAccListener(ConsistantAccListener consistantAccListener);

	/**
	 * 添加数据回调函数
	 * @param clobalLinearAccListener
	 */
	void  setGlobalLinearAccListener(LinearAccListener linearAccListener);


}
