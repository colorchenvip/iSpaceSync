package connection.datalistteners;

import java.util.Arrays;
import java.util.List;

import connection.ClientData;
import spacesync.SingleSensorData;

public class SingleMultiClientData {

	private List<String> clients;
	private double[][] data;
	private int mask;
	private final int MASK_FULL;

	public SingleMultiClientData(List<String> clients) {
		this.clients = clients;

		int tmpMask = 0;
		for (int i = 0; i < clients.size(); i++) {
			tmpMask |= 1 << i;
		}
		data = new double[clients.size()][];
		MASK_FULL = tmpMask;
	}

	public boolean add(ClientData clientData) {
		int id = clientData.getId();
		double[] cdata = clientData.getData();
		data[id] = cdata;
		setMask(id);
		return isFull();
	}

	private boolean isFull() {
		return mask == MASK_FULL;
	}

	private void setMask(int id) {
		mask |= 1 << id;
	}

	public double[][] get() {
		double[][] newData = Arrays.copyOf(data, data.length);
		mask = 0;
		return newData;
	}

}
