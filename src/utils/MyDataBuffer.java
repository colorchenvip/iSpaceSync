package utils;

import spacesync.SensorDataList;

public class MyDataBuffer {

	double buffer[][][]; // client row column
	int s;
	int e;
	private int size;
	private int clientsNum;

	public MyDataBuffer(int size, int clientsNum) {
		this.size = size;
		this.clientsNum = clientsNum;
		buffer = new double[clientsNum][size][];
	}

	public void add(double[][] data_multiClient) {
		if (e >= size) {
			s++;
		}
		for (int client = 0; client < clientsNum; client++) {
			buffer[client][e % size] = data_multiClient[client];
		}
		e++;
	}

	public SensorDataList getClientSensorData(int clientId) {
		double[][][] data = get();
		SensorDataList sensorDataList = new SensorDataList(data[clientId]);
		return sensorDataList;
	}

	public double[][][] get() {
		int len = e - s;
		double data[][][] = new double[clientsNum][len][];
		for (int client = 0; client < clientsNum; client++) {
			int k = 0;
			for (int row = s; row < e; row++) {
				data[client][k++] = buffer[client][row % size];
			}
		}
		return data;
	}

	private double[][] selectedColumns(double[][] data, int[] ids) {
		int[] allIndexes = new int[ids.length * clientsNum];
		for (int i = 0; i < clientsNum; i++) {
			for (int j = 0; j < ids.length; j++) {
				allIndexes[i * ids.length + j] = i * Constants.DATA_LEN + ids[j];
			}
		}
		return MatrixUtils.selectColumns(data, allIndexes);
	}

	public int getClientsNum() {
		return clientsNum;
	}

	public void setClientsNum(int clientsNum) {
		this.clientsNum = clientsNum;
	}

}
