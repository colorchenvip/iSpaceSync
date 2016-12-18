package test;

import java.io.IOException;
import java.util.Observer;

import connection.DataServer;
import connection.DataServerImpl;
import connection.ObserverChart;
import connection.ObserverGyrTrack;
import connection.ObserverMagTrack;
import transformation.MatrixUpdate;

public class TestDataServer {

	public static void main(String[] args) throws IOException {
		DataServer dataServer = new DataServerImpl();
		dataServer.startServer();
		
		Observer chartListener = new ObserverChart();
		Observer magListener = new ObserverMagTrack();
		Observer gyrListener = new ObserverGyrTrack(MatrixUpdate.I);
		dataServer.addDataListener(chartListener);
		dataServer.addDataListener(magListener);
		dataServer.addDataListener(gyrListener);
		dataServer.receivedData();
		
	}

}
