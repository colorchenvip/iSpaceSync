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
		return selectedColumns(data, SensorDataList.LINEAR_ACC_INDEXES);
	}

	public double[][] getGravity() {
		double[][] data = get();
		return selectedColumns(data, SensorDataList.GRIVITY_INDEXES);
	}

	public double[][] getDT() {
		double[][] data = get();
		return selectedColumns(data, SensorDataList.DT_INDEXES);
	}

	public double[][] getGYR() {
		double[][] data = get();
		return selectedColumns(data, SensorDataList.GYR_INDEXES);
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
