package com.dislab.leocai.spacesync.test;

import java.io.IOException;

import com.dislab.leocai.spacesync.core.model.SensorDataSequnce;
import com.dislab.leocai.spacesync.transformation.CoordinateTracker;
import com.dislab.leocai.spacesync.transformation.DealWithRotationMatrix_B2G;
import com.dislab.leocai.spacesync.ui.PhoneDisplayer;
import com.dislab.leocai.spacesync.ui.PhoneDisplayerPCImpl;
import com.dislab.leocai.spacesync.utils.DataLoadUtils;


public class TestPhoneDisplayer {

	public static void main(String args[]) throws IOException {
		final PhoneDisplayer phoneDisplayer = new PhoneDisplayerPCImpl();
		phoneDisplayer.initView();
		SensorDataSequnce sensorData = DataLoadUtils.loadSensorData("./datas/2.csv");
		double[][] gaccs = sensorData.getGravityAccs();
		double[][] mags = sensorData.getMagnet();
		double[][] gyrs = sensorData.getGyrs();
		double[][] initMatrix_g2b = sensorData.getInitGlobalMatrix_G2B();
		double [] dt = sensorData.getDT();

		CoordinateTracker coordinateTracker = new CoordinateTracker();
		coordinateTracker.trackByGyro(new DealWithRotationMatrix_B2G() {
			
			@Override
			public void deal(int index, double[][] cuMatrix_b2g) {
				phoneDisplayer.setRotationMatrix_b2g(cuMatrix_b2g);
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, gyrs, dt, initMatrix_g2b);
//		for (int i = 0; i < gaccs.length; i++) {
//			double[][] rtm_g2b = SensorData.getRotationMatrixG2BByMag(gaccs[i], mags[i]);
//			phoneDisplayer.setRotationMatrix_b2g(MatrixUtils.T(rtm_g2b));
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		

	}

}
