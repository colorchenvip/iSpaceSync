package com.dislab.leocai.spacesync.test;

import java.io.IOException;
import java.util.Observer;

import com.dislab.leocai.spacesync.connection.DataServer;
import com.dislab.leocai.spacesync.connection.DataServerSingleClientImpl;
import com.dislab.leocai.spacesync.connection.datalistteners.ObserverChartSingleClient;
import com.dislab.leocai.spacesync.connection.datalistteners.ObserverGyrGaccTrackerSingleClient;
import com.dislab.leocai.spacesync.connection.datalistteners.ObserverGyrTrackSingleClient;
import com.dislab.leocai.spacesync.connection.datalistteners.ObserverMagTrackSingleClient;
import com.dislab.leocai.spacesync.transformation.GyroMatrixTracker;

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
