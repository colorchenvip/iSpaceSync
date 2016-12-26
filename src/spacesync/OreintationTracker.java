package spacesync;

import utils.MyDataBuffer;

public interface OreintationTracker {

	TrackingResults track(double[][] data_multi, DirectionEstimateResults directionEstimateResults);

}
