package connection.datalistteners;

import java.util.Observable;
import java.util.Observer;

import draw.PhoneDisplayer;
import draw.PhoneDisplayerImpl;
import spacesync.CoordinateTracker;
import spacesync.SingleSensorData;
import transformation.GyrGaccMatrixTracker;
import utils.MatrixUtils;

public class ObserverGyrGaccTracker implements Observer {

	GyrGaccMatrixTracker gyrGaccMatrixTracker = new GyrGaccMatrixTracker();
	PhoneDisplayer PhoneDisplayer = new PhoneDisplayerImpl();
	private boolean first = true;

	{
		PhoneDisplayer.initView();
	}

	@Override
	public void update(Observable o, Object arg) {
		SingleSensorData sensorData = new SingleSensorData((double[]) arg);
		if (first) {
			double[][] rtm_g2b = CoordinateTracker.getRotationMatrixG2BByMag(sensorData.getGrivity(),
					sensorData.getMag());
			double[] global_x = MatrixUtils.selectColumn(rtm_g2b, 1);
			gyrGaccMatrixTracker.setInitXAxis(global_x);
			first = false;
		}

		double[][] rtm_b2g = gyrGaccMatrixTracker.track_b2g(sensorData.getGYR(), sensorData.getGrivity(),
				sensorData.getDT());
		PhoneDisplayer.setRotationMatrix_b2g(rtm_b2g);
	}

}
