package connection.datalistteners;

import java.util.Observable;
import java.util.Observer;

import draw.PhoneDisplayer;
import draw.PhoneDisplayerImpl;
import spacesync.CoordinateTracker;
import spacesync.SensorDataList;
import spacesync.SingleSensorData;
import transformation.GyroMatrixTracker;
import utils.MatrixUtils;

public class ObserverMagTrack implements Observer {
	
	GyroMatrixTracker matrixUpdate = new GyroMatrixTracker();
	PhoneDisplayer phoneDisplayer = new PhoneDisplayerImpl();
	
	public ObserverMagTrack(){
		phoneDisplayer.initView();
		matrixUpdate.setCuMatrixB2G(MatrixUtils.MATRIX_E);
	}

	@Override
	public void update(Observable o, Object arg) {
		double[] data = (double[]) arg;
		SingleSensorData sensorData = new SingleSensorData(data);
		double[] grivity = sensorData.getGrivity();
		double[] mag = sensorData.getMag();
		double[][] rtm_g2b = CoordinateTracker.getRotationMatrixG2BByMag(grivity, mag);
		phoneDisplayer.setRotationMatrix_b2g(MatrixUtils.T(rtm_g2b));

	}

}
