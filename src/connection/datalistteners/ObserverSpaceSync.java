package connection.datalistteners;

import java.util.Observable;
import java.util.Observer;

import spacesync.SpaceSync;
import spacesync.SpaceSyncImpl;
import spacesync.DirectionEstimateResults;

public class ObserverSpaceSync implements Observer {

	SpaceSync spacesync;

	public ObserverSpaceSync(int clientNum) {
		spacesync = new SpaceSyncImpl(clientNum);
	}

	@Override
	public void update(Observable o, Object arg) {
		double[] data = (double[]) arg;
		DirectionEstimateResults syncResult = spacesync.directionEstimatie(data);
		System.out.println(syncResult.getAngle());
	}

}
