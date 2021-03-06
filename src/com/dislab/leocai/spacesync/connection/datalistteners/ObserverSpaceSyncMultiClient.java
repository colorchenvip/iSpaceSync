package com.dislab.leocai.spacesync.connection.datalistteners;


import java.util.Observable;
import java.util.Observer;

import com.dislab.leocai.spacesync.core.MultiClientDataBuffer;
import com.dislab.leocai.spacesync.core.SpaceSync;
import com.dislab.leocai.spacesync.utils.SpaceSyncConfig;


/**
 * 用于监听多客户端数据并发送给算法处理
 * @author leocai
 *
 */
public class ObserverSpaceSyncMultiClient implements Observer {

	private SpaceSync spacesync;
	private MultiClientDataBuffer buffer;

	public ObserverSpaceSyncMultiClient(int clientNum, SpaceSync spacesync) {
		this.spacesync = spacesync;
		buffer = new MultiClientDataBuffer(SpaceSyncConfig.BUFFER_SIZE, clientNum);
	}

	@Override
	public void update(Observable o, Object arg) {
		double[][] data_multiClient = (double[][]) arg;
		buffer.add(data_multiClient);
		spacesync.sync(buffer);
		// DirectionEstimateResults directionEstimateResults =
		// spacesync.directionEstimate(buffer);
		// directionEstimateResults.printAngle();
	}

}
