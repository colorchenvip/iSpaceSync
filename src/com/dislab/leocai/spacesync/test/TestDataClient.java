package com.dislab.leocai.spacesync.test;

import java.io.IOException;
import java.net.UnknownHostException;

import com.dislab.leocai.spacesync.connection.DataClient;
import com.dislab.leocai.spacesync.connection.DataClientImpl;


public class TestDataClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		DataClient dataClient = new DataClientImpl();
		dataClient.connect("127.0.0.1", 10007);
		dataClient.sendSample("1,2,3,4,5,6");
		dataClient.disconnect();

	}

}
