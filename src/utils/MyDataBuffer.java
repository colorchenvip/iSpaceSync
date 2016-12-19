package utils;

public class MyDataBuffer {

	double buffer[][];
	int s;
	int e;
	private int size;

	public MyDataBuffer(int size) {
		this.size = size;
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

}
