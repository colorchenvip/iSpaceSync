package connection.datalistteners;

import java.util.Observable;
import java.util.Observer;

import core.model.SingleSensorData;
import draw.PhoneDisplayer;
import draw.PhoneDisplayerPCImpl;
import transformation.ComplementaryMatrixTracker;
import transformation.CoordinateTracker;
import transformation.GyroMatrixTracker;
import utils.MatrixUtils;
import utils.RotationUtils;

public class ObserverComplementTrackSingleClient implements Observer {

	ComplementaryMatrixTracker complementaryMatrixTracker = new ComplementaryMatrixTracker();
	PhoneDisplayer phoneDisplayer = new PhoneDisplayerPCImpl();
	boolean init = false;

	public ObserverComplementTrackSingleClient() {
		phoneDisplayer.initView();
	}

	@Override
	public void update(Observable o, Object arg) {
		SingleSensorData singleSensorData = new SingleSensorData((double[]) arg);
		if (!init) {
			double[] gacc = singleSensorData.getGrivity();
			double[] mag = singleSensorData.getMag();
			double[][] mat = RotationUtils.getRotationMatrixG2BBy2Vectors(gacc, mag);
			complementaryMatrixTracker.setCuMatrixB2G(MatrixUtils.T(mat));
			init = true;
		}
		double[][] cuMatrix_b2g = complementaryMatrixTracker.trackWithCarlibrate(singleSensorData.getGYR(),
				singleSensorData.getGrivity(), singleSensorData.getDT());
		phoneDisplayer.setRotationMatrix_b2g(cuMatrix_b2g);
	}

}
