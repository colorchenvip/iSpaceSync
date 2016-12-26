package com.dislab.leocai.spacesync.connection.model;


import java.util.Arrays;
import java.util.List;


/**
 * 多客户端单次传感器样本
 * @author leocai
 *
 */
public class SingleMultiClientData {

	private double[][] data;
	private int mask;
	private final int MASK_FULL;

	public SingleMultiClientData(int clientsNum) {
		int tmpMask = 0;
		for (int i = 0; i < clientsNum; i++) {
			tmpMask |= 1 << i;
		}
		data = new double[clientsNum][];
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
