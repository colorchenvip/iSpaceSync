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

	@Deprecated
	DirectionEstimateResults spaceSync(SensorDataSet sensorDataSet);

	@Deprecated
	double singleSync(SensorDataList sensorData);

	DirectionEstimateResults directionEstimate(MyDataBuffer dataBuffer);

	TrackingResults oreintationTracking(double[][] data_multi, DirectionEstimateResults directionEstimateResults);

	void sync(MyDataBuffer buffer, double[][] data_multi);

}
