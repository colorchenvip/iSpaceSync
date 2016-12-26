package com.dislab.leocai.spacesync.connection;


import java.io.IOException;
import java.util.Observer;

/**
 * 服务端
 * @author leocai
 *
 */
public interface DataServer {
	
	
	void startServer() throws IOException;
	void closeServer() throws IOException;
	String getAddress();
	/**
	 * 开始接收数据
	 * @throws IOException
	 */
	void receivedData() throws IOException;
	void stopReceiveData();
	/**
	 * 添加数据监听者
	 * @param dataListener
	 */
	void addDataListener(Observer dataListener);

}
