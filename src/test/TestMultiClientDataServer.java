package test;

import java.io.IOException;
import java.util.Observer;
import java.util.Scanner;

import connection.DataServerMultiClient;
import connection.datalistteners.ObserverConsistentData;
import connection.datalistteners.ObserverMultiClientChart;
import connection.datalistteners.ObserverMultiClientData;
import connection.datalistteners.ObserverSpaceSync;
import draw.PhoneDisplayer;
import draw.PhoneDisplayerPCImpl;
import draw.PhoneViewCallBack;
import spacesync.ConsistentExtraction;
import spacesync.ConsistentExtractionImpl;
import spacesync.DirectionEstimator;
import spacesync.DirectionEstimatorImpl;
import spacesync.OreintationTracker;
import spacesync.OreintationTrackerImpl;
import spacesync.SpaceSync;
import spacesync.SpaceSyncConsistanceImpl;
import spacesync.TrackingCallBack;
import transformation.GyrGaccMatrixTracker;
import utils.Constants;

public class TestMultiClientDataServer {

	public static void main(String[] args) throws IOException {
		DataServerMultiClient dataServerMultiClient = new DataServerMultiClient();
		dataServerMultiClient.startServer();
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();

		// dataServerMultiClient.addDataListener(new
		// ObserverMultiClientChart());
		int clientsNum = dataServerMultiClient.getClientsNum();
		ConsistentExtraction consistentExtraction = new ConsistentExtractionImpl();
		GyrGaccMatrixTracker matrixTracker = new GyrGaccMatrixTracker();
		DirectionEstimator directionEstimator = new DirectionEstimatorImpl(clientsNum, consistentExtraction,
				matrixTracker);
		TrackingCallBack[] trackingCallBacks = new TrackingCallBack[clientsNum];
		for (int i = 0; i < clientsNum; i++) {
			trackingCallBacks[i] = new PhoneViewCallBack(new PhoneDisplayerPCImpl());
		}
		GyrGaccMatrixTracker[] matrixTrackers = new GyrGaccMatrixTracker[clientsNum];
		for (int i = 0; i < clientsNum; i++) {
			matrixTrackers[i] = new GyrGaccMatrixTracker();
		}
		OreintationTracker oreintationTracker = new OreintationTrackerImpl(clientsNum, matrixTrackers,
				trackingCallBacks);
		SpaceSync spaceSync = new SpaceSyncConsistanceImpl(clientsNum, directionEstimator, oreintationTracker);
		// Observer dataListener = new ObserverConsistentData(clientsNum,
		// Constants.DATA_LEN);
		// dataServerMultiClient.addDataListener(dataListener);
		Observer spaceSyncOb = new ObserverSpaceSync(clientsNum, spaceSync);
		dataServerMultiClient.addDataListener(spaceSyncOb);
		scanner.close();
		System.out.println("ready to receive data...");

		dataServerMultiClient.receivedData();

	}

}
