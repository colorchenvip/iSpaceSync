package connection.datalistteners;

import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import draw.RealTimeChart;
import draw.RealTimeChartXYPlotImpl;
import spacesync.ConsistentExtraction;
import spacesync.ConsistentExtractionImpl;

public class ObserverConsistentData implements Observer {

	ConsistentExtraction consistentExtraction = new ConsistentExtractionImpl();
	RealTimeChart chart = new RealTimeChartXYPlotImpl("Consistent Data", new String[] { "pc1" });

	@Override
	public void update(Observable o, Object arg) {
		double[] data = (double[]) arg;
		consistentExtraction.addData(data);
		double[] consistentData = consistentExtraction.extractConsistentData();
		chart.showStaticData(consistentData);
	}

}
