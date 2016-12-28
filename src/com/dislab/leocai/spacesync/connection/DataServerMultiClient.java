package com.dislab.leocai.spacesync.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import com.dislab.leocai.spacesync.connection.model.ClientData;
import com.dislab.leocai.spacesync.connection.model.SingleMultiClientData;
import com.dislab.leocai.spacesync.utils.NetworkUtils;


/**
 * 多客户端的服务端
 * 
 * @author leocai
 *
 */
public class DataServerMultiClient extends Observable implements DataServer, Observer {

	private static final int PORT = 10007;
	private ServerSocket serverSocket;
	private List<String> clients = new ArrayList<>();
	/**
	 * 记录客户端id
	 */
	private int cuClientId = 0;

	/**
	 * 一个客户端对应一个接收线程，接收到数据后通知服务中心线程收集
	 */
	private Map<String, DataReceiveThread> receiveThreadMap = new HashMap<>();
	private List<Observer> observerList = new ArrayList<>();

	
	private volatile boolean closed;
	/**
	 * 收集多客户端的数据
	 */
	SingleMultiClientData singleMultiClientData;
	private OnConnectedListener connectedListener;

	@Override
	public void startServer() throws IOException {
		serverSocket = new ServerSocket(PORT);
		serverSocket.setReuseAddress(true);
		System.out.println(getAddress() + ":" + PORT);
		closed = false;
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (!closed) {
					Socket sockect;
					try {
						sockect = serverSocket.accept();
						OutputStream out = sockect.getOutputStream();
						InputStream in = sockect.getInputStream();
						String hostAddress = sockect.getInetAddress().getHostAddress();
						DataReceiveThread clientThread = new DataReceiveThread(cuClientId++, hostAddress, sockect, out,
								in);
						receiveThreadMap.put(hostAddress, clientThread);
						clients.add(hostAddress);
						clientThread.addObserver(DataServerMultiClient.this);
//						System.out.println(hostAddress + " connected");
						connectedListener.newClientConnected(hostAddress);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

	}

	@Override
	public void closeServer() throws IOException {
		closed = true;
		for (String key : receiveThreadMap.keySet()) {
			receiveThreadMap.get(key).close();
		}
	}

	@Override
	public String getAddress() {
		try {
			return NetworkUtils.getLocalHostLANAddress().getHostAddress();
		} catch (UnknownHostException e) {
			return "error ip";
		}
	}

	@Override
	public void receivedData() throws IOException {
		singleMultiClientData = new SingleMultiClientData(clients.size());
		for (String key : receiveThreadMap.keySet()) {
			new Thread(receiveThreadMap.get(key)).start();
		}
	}

	@Override
	public void stopReceiveData() {
		for (String key : receiveThreadMap.keySet()) {
			receiveThreadMap.get(key).stop();
		}
	}

	@Override
	public void addDataListener(Observer dataListener) {
		observerList.add(dataListener);
		addObserver(dataListener);
	}

	@Override
	public void update(Observable o, Object arg) {
		ClientData clientData = (ClientData) arg;
		if (singleMultiClientData.add(clientData)) {
			double[][] multiData = singleMultiClientData.get();
			setChanged();
			notifyObservers(multiData);
		}
	}

	public int getClientsNum() {
		return clients.size();
	}

	@Override
	public void setOnConnectionListener(OnConnectedListener onConnectedListener) {
		this.connectedListener = onConnectedListener;
		
	}

}
