package utils;

import spacesync.SensorDataList;

public class MyDataBuffer {

	double buffer[][];
	int s;
	int e;
	private int size;
	private int clientsNum;

	private int dataLen;

	public MyDataBuffer(int size, int clientsNum) {
		this.size = size;
		this.clientsNum = clientsNum;
		buffer = new double[size][];
	}

	public void add(double[] data) {
		if (e >= size) {
			s++;
		}
		buffer[e % size] = data;
		e++;

	}

	public double[][] get() {
		int len = e - s;
		double data[][] = new double[len][];
		int k = 0;
		for (int i = s; i < e; i++) {
			data[k++] = buffer[i % size];
		}
		return data;
	}

	public double[][] getLacc() {
		double[][] data = get();
		int[] ids = new int[] { SensorDataList.LINEAR_X, SensorDataList.LINEAR_Y, SensorDataList.LINEAR_Z };
		return selectedColumns(data, ids);
	}

	private double[][] selectedColumns(double[][] data, int[] ids) {
		int[] allIndexes = new int[ids.length * clientsNum];
		for (int i = 0; i < clientsNum; i++) {
			for (int j = 0; j < ids.length; j++) {
				allIndexes[i * ids.length + j] = i * dataLen + j;
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
