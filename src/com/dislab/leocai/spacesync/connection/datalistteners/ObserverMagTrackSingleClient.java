package com.dislab.leocai.spacesync.connection.datalistteners;

import java.util.Observable;
import java.util.Observer;

import com.dislab.leocai.spacesync.core.model.SingleSensorData;
import com.dislab.leocai.spacesync.transformation.GyroMatrixTracker;
import com.dislab.leocai.spacesync.ui.PhoneDisplayer;
import com.dislab.leocai.spacesync.ui.PhoneDisplayerPCImpl;
import com.dislab.leocai.spacesync.utils.MatrixUtils;
import com.dislab.leocai.spacesync.utils.RotationUtils;


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
