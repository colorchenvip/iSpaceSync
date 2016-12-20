package connection;

public class ClientData {

	private String key;
	private double[] data;

	public ClientData(String key, double[] data) {
		this.key = key;
		this.data = data;
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

}
