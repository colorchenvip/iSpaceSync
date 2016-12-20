package connection.datalistteners;

import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import draw.RealTimeChart;
import draw.RealTimeChartXYPlotImpl;
import spacesync.ConsistentExtraction;
import spacesync.ConsistentExtractionImpl;
import utils.MyDataBuffer;

public class ObserverConsistentData implements Observer {

	MyDataBuffer buffer = new MyDataBuffer(100);

	ConsistentExtraction consistentExtraction = new ConsistentExtractionImpl(buffer);
	RealTimeChart chart = new RealTimeChartXYPlotImpl("Consistent Data", new String[] { "pc1" });
	{
		chart.setRange(-10, 10);
	}

	@Override
	public void update(Observable o, Object arg) {
		double[] data = (double[]) arg;
		buffer.add(data);
		double[] consistentData = consistentExtraction.extractConsistentData();
		chart.showStaticData(consistentData);
	}

}
