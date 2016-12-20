package connection.datalistteners;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import draw.RealTimeChart;
import draw.RealTimeChartXYPlotImpl;
import utils.DataUtils;

public class ObserverMultiClientChart implements Observer {

	List<RealTimeChart> charts = new ArrayList<>();

	private int clientColumnNum = 3;

	@Override
	public void update(Observable o, Object arg) {
		double[] data = (double[]) arg;
		int clientsNum = data.length / clientColumnNum;
		if (charts.size() < clientsNum) {
			charts.clear();
			for (int i = 0; i < clientsNum; i++)
				charts.add(new RealTimeChartXYPlotImpl("Client" + i,
						new String[] { "linear acc0", "linear acc1", "linear acc2" }));
		}
		for (int i = 0; i < clientsNum; i++) {
			charts.get(i).addData(Arrays.copyOfRange(data, i * 3, (i + 1) * 3-1) );
		}

	}

}
