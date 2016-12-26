package connection.datalistteners;

import java.util.Observable;
import java.util.Observer;

import core.model.SingleSensorData;
import draw.PhoneDisplayer;
import draw.PhoneDisplayerPCImpl;
import transformation.GyroMatrixTracker;
import utils.MatrixUtils;
import utils.RotationUtils;

public class ObserverMagTrackSingleClient implements Observer {
	
	GyroMatrixTracker matrixUpdate = new GyroMatrixTracker();
	PhoneDisplayer phoneDisplayer = new PhoneDisplayerPCImpl();
	
	public ObserverMagTrackSingleClient(){
		phoneDisplayer.initView();
		matrixUpdate.setCuMatrixB2G(MatrixUtils.MATRIX_E);
	}

	@Override
	public void update(Observable o, Object arg) {
		double[] data = (double[]) arg;
		SingleSensorData sensorData = new SingleSensorData(data);
		double[] grivity = sensorData.getGrivity();
		double[] mag = sensorData.getMag();
		double[][] rtm_g2b = RotationUtils.getRotationMatrixG2BBy2Vectors(grivity, mag);
		phoneDisplayer.setRotationMatrix_b2g(MatrixUtils.T(rtm_g2b));

	}

}
