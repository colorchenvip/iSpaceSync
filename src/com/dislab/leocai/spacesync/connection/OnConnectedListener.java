package com.dislab.leocai.spacesync.connection;

/**
 * 客户端连接监听者
 * @author leocai
 *
 */
public interface OnConnectedListener {

	void newClientConnected(String hostAddress);

}
