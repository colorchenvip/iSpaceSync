package connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import connection.datalistteners.SingleMultiClientData;

public class DataServerMultiClient extends Observable implements DataServer, Observer {

	private static final int PORT = 10007;
	private ServerSocket serverSocket;
	private Map<String, DataReceiveThread> threadsMap = new HashMap<>();
	private volatile boolean closed;
	private List<Observer> observerList = new ArrayList<>();

	List<String> clients = new ArrayList<>();
	SingleMultiClientData singleMultiClientData;

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
						DataReceiveThread clientThread = new DataReceiveThread(hostAddress, sockect, out, in);
						threadsMap.put(hostAddress, clientThread);
						clients.add(hostAddress);
						clientThread.addObserver(DataServerMultiClient.this);
						System.out.println(hostAddress + " connected");
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
		for (String key : threadsMap.keySet()) {
			threadsMap.get(key).close();
		}
	}

	@Override
	public String getAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "error ip";
		}
	}

	@Override
	public void receivedData() throws IOException {
		singleMultiClientData = new SingleMultiClientData(clients);
		for (String key : threadsMap.keySet()) {
			new Thread(threadsMap.get(key)).start();
		}
	}

	@Override
	public void stopReceiveData() {
		singleMultiClientData = new SingleMultiClientData(clients);
		for (String key : threadsMap.keySet()) {
			threadsMap.get(key).stop();
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
			double[] multiData = singleMultiClientData.get();
			System.out.println(Arrays.toString(multiData));
			setChanged();
			notifyObservers(multiData);
		}
	}

	public int getClientsNum() {
		return clients.size();
	}

}
