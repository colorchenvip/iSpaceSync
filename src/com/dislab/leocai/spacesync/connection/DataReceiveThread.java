package com.dislab.leocai.spacesync.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Observable;

import com.dislab.leocai.spacesync.connection.model.ClientData;
import com.dislab.leocai.spacesync.utils.DataUtils;
import com.dislab.leocai.spacesync.utils.SpaceSyncConfig;


/**
 * 数据接收线程
 * 一个客户端对应一个
 * 接收数据后通知观察者
 * @author leocai
 *
 */
public class DataReceiveThread extends Observable implements Runnable {

	private boolean stop;
	private String client;
	private Socket socket;
	private OutputStream out;
	private InputStream in;
	private int clientId;

	public DataReceiveThread(int clientId, String client, Socket socket, OutputStream out, InputStream in) {
		this.clientId = clientId;
		this.client = client;
		this.socket = socket;
		this.out = out;
		this.in = in;
	}

	@Override
	public void run() {
		stop = false;
		while (!stop) {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
			try {
				String newLine = bufferedReader.readLine();
				if (newLine == null)
					continue;
				double data[];
				try {
					data = DataUtils.parseData(newLine, SpaceSyncConfig.RECEIVED_SELECTED_INDEXES);
					setChanged();
					notifyObservers(new ClientData(clientId, client, data));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void stop() {
		stop = true;
	}

	public void close() throws IOException {
		stop();
		in.close();
		out.close();
		socket.close();
	}

}
