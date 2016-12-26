package com.dislab.leocai.spacesync.connection;


import java.io.IOException;
import java.net.UnknownHostException;

/**
 * 传感器数据发送客户端
 * @author leocai
 *
 */
public interface DataClient {

	void connect(String address, int port) throws UnknownHostException, IOException;

	void disconnect() throws IOException;

	void sendSample(String data) throws IOException;

}
