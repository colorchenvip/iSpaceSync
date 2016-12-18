package connection.datalistteners;

import java.util.Observable;
import java.util.Observer;

import draw.PhoneDisplayer;
import draw.PhoneDisplayerImpl;
import spacesync.CoordinateTracker;
import spacesync.SingleSensorData;
import transformation.GyroMatrixTracker;
import utils.MatrixUtils;

public class ObserverGyrTrack implements Observer {

	GyroMatrixTracker matrixUpdate = new GyroMatrixTracker();
	PhoneDisplayer phoneDisplayer = new PhoneDisplayerImpl();
	boolean init = false;

	public ObserverGyrTrack(double[][] cuMatrixB2G) {
		phoneDisplayer.initView();
		matrixUpdate.setCuMatrixB2G(cuMatrixB2G);
	}

	@Override
	public void update(Observable o, Object arg) {
		SingleSensorData singleSensorData = new SingleSensorData((double[]) arg);
		double[] k = singleSensorData.getGYR();
		if (!init) {
			double[] gacc = singleSensorData.getGrivity();
			double[] mag = singleSensorData.getMag();
			double[][] mat = CoordinateTracker.getRotationMatrixG2BByMag(gacc, mag);
			matrixUpdate.setCuMatrixB2G(MatrixUtils.T(mat));
			init = true;
		}
		double[][] cuMatrix_b2g = matrixUpdate.trackByGYR(k, singleSensorData.getDT());
		phoneDisplayer.setRotationMatrix_b2g(cuMatrix_b2g);

	}

}
