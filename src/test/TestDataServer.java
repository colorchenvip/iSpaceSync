package test;

import java.io.IOException;
import java.util.Observer;

import connection.DataServer;
import connection.DataServerImpl;
import connection.datalistteners.ObserverChart;
import connection.datalistteners.ObserverComplementTrack;
import connection.datalistteners.ObserverGyrGaccTracker;
import connection.datalistteners.ObserverGyrTrack;
import connection.datalistteners.ObserverMagTrack;
import transformation.GyroMatrixTracker;

public class TestDataServer {

	public static void main(String[] args) throws IOException {
		DataServer dataServer = new DataServerImpl();
		dataServer.startServer();
		
		Observer chartListener = new ObserverChart();
		Observer magListener = new ObserverMagTrack();
		Observer gyrListener = new ObserverGyrTrack(GyroMatrixTracker.I);
		dataServer.addDataListener(chartListener);
		dataServer.addDataListener(magListener);
		dataServer.addDataListener(gyrListener);
		dataServer.addDataListener(new ObserverComplementTrack());
		dataServer.addDataListener(new ObserverGyrGaccTracker());
		dataServer.receivedData();
		
	}

}
