package com.dislab.leocai.spacesync.connection.datalistteners;

import java.util.Observable;
import java.util.Observer;

import com.dislab.leocai.spacesync.core.model.SingleSensorData;
import com.dislab.leocai.spacesync.transformation.GyroMatrixTracker;
import com.dislab.leocai.spacesync.ui.PhoneDisplayer;
import com.dislab.leocai.spacesync.ui.PhoneDisplayerPCImpl;
import com.dislab.leocai.spacesync.utils.MatrixUtils;
import com.dislab.leocai.spacesync.utils.RotationUtils;


public class ObserverGyrTrackSingleClient implements Observer {

	GyroMatrixTracker matrixUpdate = new GyroMatrixTracker();
	PhoneDisplayer phoneDisplayer = new PhoneDisplayerPCImpl();
	boolean init = false;

	public ObserverGyrTrackSingleClient(double[][] cuMatrixB2G) {
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
			double[][] mat = RotationUtils.getRotationMatrixG2BBy2Vectors(gacc, mag);
			matrixUpdate.setCuMatrixB2G(MatrixUtils.T(mat));
			init = true;
		}
		double[][] cuMatrix_b2g = matrixUpdate.trackByGYR(k, singleSensorData.getDT());
		phoneDisplayer.setRotationMatrix_b2g(cuMatrix_b2g);

	}

}
