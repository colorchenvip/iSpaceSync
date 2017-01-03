package com.dislab.leocai.spacesync.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class DataClientImpl implements DataClient {

	private OutputStream out;
	private InputStream in;
	private Socket socket;
	private PrintWriter bufferedWriter;

	@Override
	public void connect(String address, int port) throws UnknownHostException, IOException {
		socket = new Socket(address, port);
		out = socket.getOutputStream();
		in = socket.getInputStream();
		bufferedWriter = new PrintWriter(new OutputStreamWriter(out));
	}

	@Override
	public void disconnect() throws IOException {
		if (in != null)
			in.close();
		if (out != null)
			out.close();
		if (socket != null)
			socket.close();
	}

	@Override
	public void sendSample(String data) throws IOException {
		bufferedWriter.println(data);
		bufferedWriter.flush();
	}

}
