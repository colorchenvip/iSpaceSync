package spacesync;

import java.util.List;

import draw.RealTimeChartMultiClientImpl;
import utils.DataUtils;
import utils.MyDataBuffer;
import utils.VectorUtils;

public class SpaceSyncConsistanceImpl implements SpaceSync {

	DirectionEstimator directionEstimator;
	private OreintationTracker oreintationTracker;
	private int clientsNum;

	public SpaceSyncConsistanceImpl(int clientsNum, DirectionEstimator directionEstimator,
			OreintationTracker oreintationTracker) {
		this.directionEstimator = directionEstimator;
		this.oreintationTracker = oreintationTracker;
		this.clientsNum = clientsNum;
	}

	@Override
	public void sync(MyDataBuffer buffer, double[][] data_multi) {
		DirectionEstimateResults directions = null;
		if (isSyncTime(buffer)) {
			System.out.println("Sync Time!");
			directions = directionEstimate(buffer);
		}
		oreintationTracking(data_multi, directions);
	}

	private boolean isSyncTime(MyDataBuffer buffer) {
		for (int i = 0; i < clientsNum; i++) {
			SensorDataList sensorData = buffer.getClientSensorData(i);
			double[][] laccs = sensorData.getLinearAccs();
			double[] rlaccs = DataUtils.resultantData(laccs);
			if (VectorUtils.mean(rlaccs) < 3)
				return false;
		}
		return true;
	}

	@Override
	public DirectionEstimateResults spaceSync(SensorDataSet sensorDataSet) {
		return null;
	}

	@Override
	public double singleSync(SensorDataList sensorData) {
		return 0;
	}

	@Override
	public DirectionEstimateResults directionEstimate(MyDataBuffer dataBuffer) {
		return directionEstimator.estimate(dataBuffer);
	}

	@Override
	public TrackingResults oreintationTracking(double[][] data_multi,
			DirectionEstimateResults directionEstimateResults) {
		return oreintationTracker.track(data_multi, directionEstimateResults);
	}

}
