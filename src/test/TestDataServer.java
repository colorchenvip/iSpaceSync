package test;

import java.io.IOException;
import java.util.Observer;

import connection.DataServer;
import connection.DataServerSingleClientImpl;
import connection.datalistteners.ObserverChartSingleClient;
import connection.datalistteners.ObserverComplementTrackSingleClient;
import connection.datalistteners.ObserverGyrGaccTrackerSingleClient;
import connection.datalistteners.ObserverGyrTrackSingleClient;
import connection.datalistteners.ObserverMagTrackSingleClient;
import transformation.GyroMatrixTracker;

public class TestDataServer {

	public static void main(String[] args) throws IOException {
		DataServer dataServer = new DataServerSingleClientImpl();
		dataServer.startServer();
		
		Observer chartListener = new ObserverChartSingleClient();
		Observer magListener = new ObserverMagTrackSingleClient();
		Observer gyrListener = new ObserverGyrTrackSingleClient(GyroMatrixTracker.I);
		dataServer.addDataListener(chartListener);
		dataServer.addDataListener(magListener);
		dataServer.addDataListener(gyrListener);
//		dataServer.addDataListener(new ObserverComplementTrack());
		dataServer.addDataListener(new ObserverGyrGaccTrackerSingleClient());
		dataServer.receivedData();
		
	}

}
