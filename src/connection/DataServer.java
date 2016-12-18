package connection;


import java.io.IOException;
import java.util.Observer;

public interface DataServer {
	
	void startServer() throws IOException;
	void closeServer() throws IOException;
	String getAddress();
	void receivedData() throws IOException;
	void stopReceiveData();
	void addDataListener(Observer dataListener);

}
