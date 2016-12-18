package connection;

import java.util.Arrays;

import draw.PhoneDisplayer;
import draw.PhoneDisplayerImpl;
import draw.RealTimeChart;
import draw.RealTimeChartJFreeChatImpl;
import spacesync.SensorData;
import spacesync.SingleSensorData;
import transformation.MatrixUpdate;
import utils.DataUtils;
import utils.MatrixUtils;

@Deprecated
public class DealWithSampleImpl implements DealWithSample {

	RealTimeChart realTimeChart = new RealTimeChartJFreeChatImpl("11", new String[] { "" });
	MatrixUpdate matrixUpdate = new MatrixUpdate();
	PhoneDisplayer phoneDisplayer = new PhoneDisplayerImpl();

	@Override
	public void init(double[][] cuMatrixB2G) {
		phoneDisplayer.initView();
		matrixUpdate.setCuMatrixB2G(cuMatrixB2G);
	}

	@Override
	public void dealWithSample(double[] data) {
		// System.out.println(Arrays.toString(data));
		SingleSensorData singleSensorData = new SingleSensorData(data);
		double[][] cuMatrix_b2g = matrixUpdate.updateMatrixByGYR(singleSensorData.getGYR(), singleSensorData.getDT());
		phoneDisplayer.setRotationMatrix_b2g(cuMatrix_b2g);
		realTimeChart.addData(singleSensorData.getGYR());
	}

}
