package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Observable;

import utils.DataUtils;

public class DataReceiveThread extends Observable implements Runnable {

	private boolean stop;
	private String client;
	private Socket socket;
	private OutputStream out;
	private InputStream in;
	private int[] selectedIds = new int[] { 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 20 };

	public DataReceiveThread(String client, Socket socket, OutputStream out, InputStream in) {
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
					data = DataUtils.parseData(newLine, selectedIds);
					setChanged();
					notifyObservers(new ClientData(client, data));
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
