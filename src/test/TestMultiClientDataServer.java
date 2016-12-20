package test;

import java.io.IOException;
import java.util.Observer;

import connection.DataServerMultiClient;
import connection.datalistteners.ObserverConsistentData;
import connection.datalistteners.ObserverMultiClientChart;
import connection.datalistteners.ObserverMultiClientData;

public class TestMultiClientDataServer {

	public static void main(String[] args) throws IOException {
		DataServerMultiClient dataServerMultiClient = new DataServerMultiClient();
		Observer dataListener = new ObserverConsistentData();
		dataServerMultiClient.addDataListener(dataListener);
		dataServerMultiClient.addDataListener(new ObserverMultiClientChart());
		dataServerMultiClient.startServer();
	}

}
