package connection;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import utils.DataUtils;
import utils.SpaceSyncConfig;


/**
 * 单个客户端接听服务器实现
 * @author leocai
 *
 */
public class DataServerSingleClientImpl extends Observable implements DataServer {

	private static final int PORT = 10007;
	private ServerSocket serverSocket;
	private Socket sockect;
	private OutputStream out;
	private InputStream in;
	private boolean stop;

	@Override
	public void startServer() throws IOException {
		serverSocket = new ServerSocket(PORT);
		serverSocket.setReuseAddress(true);
		System.out.println(getAddress() + ":" + PORT);
		sockect = serverSocket.accept();
		out = sockect.getOutputStream();
		in = sockect.getInputStream();
		System.out.println("connected");
	}

	@Override
	public void closeServer() throws IOException {
		in.close();
		out.close();
		sockect.close();
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
	public void stopReceiveData() {
		stop = true;
	}

	@Override
	public void receivedData() throws IOException {
		stop = false;
		while (!stop) {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
			String newLine = bufferedReader.readLine();
			if (newLine == null)
				continue;
			double data[];
			try {
				data = DataUtils.parseData(newLine, SpaceSyncConfig.RECEIVED_SELECTED_INDEXES);
				setChanged();
				notifyObservers(data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void addDataListener(Observer dataListener) {
		addObserver(dataListener);
	}

}
