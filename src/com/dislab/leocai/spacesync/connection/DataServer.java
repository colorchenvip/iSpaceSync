package com.dislab.leocai.spacesync.connection;

import java.io.IOException;
import java.util.Observer;

/**
 * 服务端接口
 * 
 * @author leocai
 *
 */
public interface DataServer {

	/**
	 * 启动服务器，监听10007端口
	 * @throws IOException
	 */
	void startServer() throws IOException;

	/**
	 * 关闭服务器
	 * @throws IOException
	 */
	void closeServer() throws IOException;

	/**
	 * 获取服务器地址
	 * @return
	 */
	String getAddress();

	/**
	 * 开始接收数据
	 * 
	 * @throws IOException
	 */
	void receivedData() throws IOException;

	void stopReceiveData();

	/**
	 * 添加数据监听者
	 * 
	 * @param dataListener
	 */
	void addDataListener(Observer dataListener);

	/**
	 * 客户端连接事件
	 * @param onConnectedListener
	 */
	void setOnConnectionListener(OnConnectedListener onConnectedListener);
	
	int getClientsNum();

}
