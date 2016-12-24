package connection.datalistteners;

import java.util.Observable;
import java.util.Observer;

import spacesync.SpaceSync;
import spacesync.SpaceSyncImpl;
import utils.Constants;
import utils.MyDataBuffer;
import spacesync.DirectionEstimateResults;

public class ObserverSpaceSync implements Observer {

	SpaceSync spacesync;
	private MyDataBuffer buffer;

	public ObserverSpaceSync(int clientNum) {
		spacesync = new SpaceSyncImpl(clientNum);
		buffer = new MyDataBuffer(Constants.BUFFER_SIZE, clientNum);
	}

	@Override
	public void update(Observable o, Object arg) {
		double[] data = (double[]) arg;
		buffer.add(data);
		DirectionEstimateResults directionEstimateResults = spacesync.directionEstimatie(buffer);
		directionEstimateResults.printAngle();
	}

}
