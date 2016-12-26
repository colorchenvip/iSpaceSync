package spacesync;

import utils.MyDataBuffer;

public interface DirectionEstimator {
	
	DirectionEstimateResults estimate(MyDataBuffer buffer);
	
}
