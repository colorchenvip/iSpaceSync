package draw;

public interface RealTimeChart {

	void init(String title, String[] columnNames);

	void addData(double[] data);
	
	void setRange(int min, int max);

}
