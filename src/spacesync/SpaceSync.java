package spacesync;
/**
 * 
 */

/**
 * @author leocai
 *
 */
public interface SpaceSync {
	
	SyncResult spaceSync(SensorDataSet sensorDataSet);
	
	double singleSync(SensorDataList sensorData);

}
