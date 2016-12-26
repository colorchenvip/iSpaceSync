package com.dislab.leocai.spacesync.connection.model;


/**
 * 单客户端传感器数据
 * @author leocai
 *
 */
public class ClientData {

	private String key;
	private double[] data;
	private int id;

	public ClientData(int id, String key, double[] data) {
		this.key = key;
		this.data = data;
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public double[] getData() {
		return data;
	}

	public void setData(double[] data) {
		this.data = data;
	}

	public int getId() {
		return id;
	}

}
