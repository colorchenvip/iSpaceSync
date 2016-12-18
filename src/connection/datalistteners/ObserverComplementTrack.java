package connection.datalistteners;

import java.util.Observable;
import java.util.Observer;

import draw.PhoneDisplayer;
import draw.PhoneDisplayerImpl;
import spacesync.CoordinateTracker;
import spacesync.SingleSensorData;
import transformation.ComplementaryMatrixTracker;
import transformation.GyroMatrixTracker;
import utils.MatrixUtils;

public class ObserverComplementTrack implements Observer {

	ComplementaryMatrixTracker complementaryMatrixTracker = new ComplementaryMatrixTracker();
	PhoneDisplayer phoneDisplayer = new PhoneDisplayerImpl();
	boolean init = false;

	public ObserverComplementTrack() {
		phoneDisplayer.initView();
	}

	@Override
	public void update(Observable o, Object arg) {
		SingleSensorData singleSensorData = new SingleSensorData((double[]) arg);
		if (!init) {
			double[] gacc = singleSensorData.getGrivity();
			double[] mag = singleSensorData.getMag();
			double[][] mat = CoordinateTracker.getRotationMatrixG2BByMag(gacc, mag);
			complementaryMatrixTracker.setCuMatrixB2G(MatrixUtils.T(mat));
			init = true;
		}
		double[][] cuMatrix_b2g = complementaryMatrixTracker.trackWithCarlibrate(singleSensorData.getGYR(),
				singleSensorData.getGrivity(), singleSensorData.getDT());
		phoneDisplayer.setRotationMatrix_b2g(cuMatrix_b2g);
	}

}
