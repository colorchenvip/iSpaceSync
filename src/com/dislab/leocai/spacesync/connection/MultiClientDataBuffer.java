package com.dislab.leocai.spacesync.connection;

import java.util.Arrays;

import com.dislab.leocai.spacesync.core.model.SensorDataSequnce;


/**
 * 
 * @author leocai
 * 多客户端Sensor的数据缓冲区
 *
 */
public class MultiClientDataBuffer {

	/**
	 * 三个维度分别代表：客户端　行　列
	 */
	double buffer[][][]; // client row column
	int start;
	int end;
	private int bufferSize;
	private int clientsNum;

	public MultiClientDataBuffer(int size, int clientsNum) {
		this.bufferSize = size;
		this.clientsNum = clientsNum;
		buffer = new double[clientsNum][size][];
	}

	public void add(double[][] data_multiClient) {
		if (end >= bufferSize) {
			start++;
		}
		for (int client = 0; client < clientsNum; client++) {
			buffer[client][end % bufferSize] = data_multiClient[client];
		}
		end++;
	}

	public SensorDataSequnce getClientSensorData(int clientId) {
		double[][][] data = get();
		SensorDataSequnce sensorDataList = new SensorDataSequnce(data[clientId]);
		return sensorDataList;
	}

	public double[][][] get() {
		int len = end - start;
		double data[][][] = new double[clientsNum][len][];
		for (int client = 0; client < clientsNum; client++) {
			int k = 0;
			for (int row = start; row < end; row++) {
				data[client][k++] = buffer[client][row % bufferSize];
			}
		}
		return data;
	}

	public int getClientsNum() {
		return clientsNum;
	}

	public void setClientsNum(int clientsNum) {
		this.clientsNum = clientsNum;
	}

	public double[] getClientFirstData(int clientId) {
		double[] d = buffer[clientId][start%bufferSize];
		return Arrays.copyOf(d, d.length);
	}

}
