package com.dislab.leocai.spacesync.connection.datalistteners;

import java.util.Observable;
import java.util.Observer;

import com.dislab.leocai.spacesync.core.model.SingleSensorData;
import com.dislab.leocai.spacesync.draw.PhoneDisplayer;
import com.dislab.leocai.spacesync.draw.PhoneDisplayerPCImpl;
import com.dislab.leocai.spacesync.transformation.ComplementaryMatrixTracker;
import com.dislab.leocai.spacesync.utils.MatrixUtils;
import com.dislab.leocai.spacesync.utils.RotationUtils;

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
