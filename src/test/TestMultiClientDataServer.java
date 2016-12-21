package test;

import java.io.IOException;
import java.util.Observer;
import java.util.Scanner;

import connection.DataServerMultiClient;
import connection.datalistteners.ObserverConsistentData;
import connection.datalistteners.ObserverMultiClientChart;
import connection.datalistteners.ObserverMultiClientData;
import connection.datalistteners.ObserverSpaceSync;
import utils.Constants;

public class TestMultiClientDataServer {

	public static void main(String[] args) throws IOException {
		DataServerMultiClient dataServerMultiClient = new DataServerMultiClient();
		dataServerMultiClient.startServer();
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		
//		dataServerMultiClient.addDataListener(new ObserverMultiClientChart());
		int clientsNum = dataServerMultiClient.getClientsNum();
		// Observer dataListener = new ObserverConsistentData(clientsNum,
		// Constants.DATA_LEN);
		// dataServerMultiClient.addDataListener(dataListener);
		Observer spaceSyncOb = new ObserverSpaceSync(clientsNum);
		dataServerMultiClient.addDataListener(spaceSyncOb);
		scanner.close();
		System.out.println("ready to receive data...");
		
		dataServerMultiClient.receivedData();

	}

}
