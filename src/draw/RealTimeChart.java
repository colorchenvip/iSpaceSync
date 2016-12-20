package draw;

public interface RealTimeChart {

	void addData(double[] data);
	
	void setRange(int min, int max);

	void showStaticData(double[] consistentData);

	void clearData();

}
