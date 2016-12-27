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
import com.dislab.leocai.spacesync.transformation.GyrGaccMatrixTracker;
import com.dislab.leocai.spacesync.transformation.TrackingCallBack;
import com.dislab.leocai.spacesync.ui.PhoneDisplayerPCImpl;
import com.dislab.leocai.spacesync.ui.PhoneViewCallBack;
import com.dislab.leocai.spacesync.ui.SpaceSyncPCFrameDataListener;

/**
 * 程序入口
 * 
 * @author leocai
 *
 */
public class ServerStarter {

	private DataServerMultiClient dataServerMultiClient = new DataServerMultiClient();
	private ConsistentExtraction consistentExtraction = new ConsistentExtractionImpl();
	private GyrGaccMatrixTracker matrixTracker = new GyrGaccMatrixTracker();

	public void run() throws IOException {
		dataServerMultiClient.startServer();
		System.out.println("Wait for client");

		System.out.println("After connected, press Enter to Ready");

		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();

		constructSpaceSyncAlogrithmListener();

		System.out.println("Ready to receive data...");
		dataServerMultiClient.receivedData();
		scanner.close();
	}

	private void constructSpaceSyncAlogrithmListener() {
		int clientsNum = dataServerMultiClient.getClientsNum();
		DirectionEstimator directionEstimator = new DirectionEstimatorImpl(clientsNum, consistentExtraction,
				matrixTracker);
		SpaceSyncPCFrameDataListener frameDataListener = new SpaceSyncPCFrameDataListener("SPACE SYNC PLOT",
				clientsNum);
		directionEstimator.addConsistantAccListener(frameDataListener);
		directionEstimator.addGlobalLinearAccListener(frameDataListener);
		TrackingCallBack[] trackingCallBacks = new TrackingCallBack[clientsNum];
		for (int i = 0; i < clientsNum; i++) {
			PhoneDisplayerPCImpl pcImpl = new PhoneDisplayerPCImpl();
			frameDataListener.addPhoneView(pcImpl.getWorldView().getUniverse().getCanvas());
			trackingCallBacks[i] = new PhoneViewCallBack(pcImpl);
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
	}

	/**
	 * 启动服务器 等待连接 按下回车接收数据 构造算法类 进行算法
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		new ServerStarter().run();
	}

}
