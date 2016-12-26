package com.dislab.leocai.spacesync;


import java.io.IOException;
import java.util.Observer;
import java.util.Scanner;

import com.dislab.leocai.spacesync.connection.DataServerMultiClient;
import com.dislab.leocai.spacesync.connection.datalistteners.ObserverSpaceSyncMultiClient;
import com.dislab.leocai.spacesync.core.ConsistentExtraction;
import com.dislab.leocai.spacesync.core.ConsistentExtractionImpl;
import com.dislab.leocai.spacesync.core.DirectionEstimator;
import com.dislab.leocai.spacesync.core.DirectionEstimatorImpl;
import com.dislab.leocai.spacesync.core.OreintationTracker;
import com.dislab.leocai.spacesync.core.OreintationTrackerImpl;
import com.dislab.leocai.spacesync.core.SpaceSync;
import com.dislab.leocai.spacesync.core.SpaceSyncConsistanceImpl;
import com.dislab.leocai.spacesync.draw.PhoneDisplayerPCImpl;
import com.dislab.leocai.spacesync.draw.PhoneViewCallBack;
import com.dislab.leocai.spacesync.transformation.GyrGaccMatrixTracker;
import com.dislab.leocai.spacesync.transformation.TrackingCallBack;





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
