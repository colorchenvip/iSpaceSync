package com.dislab.leocai.spacesync.connection.datalistteners;

import java.util.Observable;
import java.util.Observer;

import com.dislab.leocai.spacesync.core.model.SingleSensorData;
import com.dislab.leocai.spacesync.transformation.GyrGaccMatrixTracker;
import com.dislab.leocai.spacesync.ui.PhoneDisplayer;
import com.dislab.leocai.spacesync.ui.PhoneDisplayerPCImpl;
import com.dislab.leocai.spacesync.utils.MatrixUtils;
import com.dislab.leocai.spacesync.utils.RotationUtils;

public class ObserverGyrGaccTrackerSingleClient implements Observer {

	GyrGaccMatrixTracker gyrGaccMatrixTracker = new GyrGaccMatrixTracker();
	PhoneDisplayer PhoneDisplayer = new PhoneDisplayerPCImpl();
	private boolean first = true;


	@Override
	public void update(Observable o, Object arg) {
		SingleSensorData sensorData = new SingleSensorData((double[]) arg);
		if (first) {
			double[][] rtm_g2b = RotationUtils.getRotationMatrixG2BBy2Vectors(sensorData.getGrivity(),
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
