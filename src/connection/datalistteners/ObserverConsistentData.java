package connection.datalistteners;

import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import draw.RealTimeChart;
import draw.RealTimeChartXYPlotImpl;
import spacesync.ConsistentExtraction;
import spacesync.ConsistentExtractionImpl;
import utils.Constants;
import utils.MyDataBuffer;

public class ObserverConsistentData implements Observer {

	MyDataBuffer buffer;

	ConsistentExtraction consistentExtraction;
	RealTimeChart chart = new RealTimeChartXYPlotImpl("Consistent Data", new String[] { "pc1" });

	{
		chart.setRange(-10, 10);
	}

	public ObserverConsistentData(int clientsNum, int dataLen) {
		consistentExtraction = new ConsistentExtractionImpl();
		buffer = new MyDataBuffer(Constants.BUFFER_SIZE, clientsNum);
	}

	@Override
	public void update(Observable o, Object arg) {
		double[] data = (double[]) arg;
		buffer.add(data);
		double[] consistentData = consistentExtraction.extractConsistentData(buffer.getLacc());
		chart.showStaticData(consistentData);
	}

}
