package com.dislab.leocai.spacesync;


import java.io.IOException;
import java.util.Observer;
import java.util.Scanner;

import connection.DataServerMultiClient;
import connection.datalistteners.ObserverSpaceSyncMultiClient;
import core.ConsistentExtraction;
import core.ConsistentExtractionImpl;
import core.DirectionEstimator;
import core.DirectionEstimatorImpl;
import core.OreintationTracker;
import core.OreintationTrackerImpl;
import core.SpaceSync;
import core.SpaceSyncConsistanceImpl;
import draw.PhoneDisplayer;
import draw.PhoneDisplayerPCImpl;
import draw.PhoneViewCallBack;
import transformation.GyrGaccMatrixTracker;
import transformation.TrackingCallBack;
import utils.SpaceSyncConfig;

/**
 * 程序入口
 * @author leocai
 *
 */
public class ServerStarter {

	public static void main(String[] args) throws IOException {
		DataServerMultiClient dataServerMultiClient = new DataServerMultiClient();
		dataServerMultiClient.startServer();
		System.out.println("After connected, press Enter to Ready");

		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		
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
		Observer spaceSyncOb = new ObserverSpaceSyncMultiClient(clientsNum, spaceSync);
		dataServerMultiClient.addDataListener(spaceSyncOb);
		scanner.close();
		System.out.println("ready to receive data...");

		dataServerMultiClient.receivedData();

	}

}
