package test;

import java.io.IOException;

import connection.DataServer;
import connection.DataServerImpl;

public class TestDataServer {

	public static void main(String[] args) throws IOException {
		
		DataServer dataServer = new DataServerImpl();
		dataServer.startServer();
		dataServer.receivedData();

	}

}
