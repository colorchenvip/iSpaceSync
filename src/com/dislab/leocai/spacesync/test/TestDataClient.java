package com.dislab.leocai.spacesync.test;

import java.io.IOException;
import java.net.UnknownHostException;

import com.dislab.leocai.spacesync.connection.DataClient;
import com.dislab.leocai.spacesync.connection.DataClientImpl;


public class TestDataClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		DataClient dataClient = new DataClientImpl();
		dataClient.connect("127.0.0.1", 10007);
		while(true){
			;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
