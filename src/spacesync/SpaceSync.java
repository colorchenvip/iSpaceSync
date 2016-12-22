package spacesync;
/**
 * 
 */

import utils.MyDataBuffer;

/**
 * @author leocai
 *
 */
public interface SpaceSync {
	
	DirectionEstimateResults spaceSync(SensorDataSet sensorDataSet);
	
	double singleSync(SensorDataList sensorData);

	DirectionEstimateResults directionEstimatie(MyDataBuffer dataBuffer);
	
	TrackingResults oreintationTracking(TrackingCallBack trackingCallBack, double[] data, DirectionEstimateResults directionEstimateResults);

	TrackingResults oreintationTracking(TrackingCallBack trackingCallBack, MyDataBuffer dataBuffer,
			DirectionEstimateResults directionEstimateResults);

}
