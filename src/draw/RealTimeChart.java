package draw;

/**
 * 实时图表显示
 * @author leocai
 *
 */
public interface RealTimeChart {

	void addData(double[] data);
	
	void setRange(int min, int max);

	void showStaticData(double[] consistentData);

	void clearData();

	void showStaticData(double[][] selectColumns);

}
