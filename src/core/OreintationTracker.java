package core;

import connection.MultiClientDataBuffer;
import core.model.TrackingResults;

/**
 * 姿态跟踪
 * @author leocai
 *
 */
public interface OreintationTracker {

	TrackingResults track(MultiClientDataBuffer buffer,  DirectionEstimateResults directionEstimateResults, boolean isSyncTime);

}
