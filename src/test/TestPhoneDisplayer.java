package test;

import java.io.IOException;

import draw.PhoneDisplayer;
import draw.PhoneDisplayerImpl;
import spacesync.CoordinateTracker;
import spacesync.SensorData;
import transformation.DealWithRotationMatrix_B2G;
import utils.DataLoadUtils;
import utils.MatrixUtils;

public class TestPhoneDisplayer {

	public static void main(String args[]) throws IOException {
		final PhoneDisplayer phoneDisplayer = new PhoneDisplayerImpl();
		phoneDisplayer.initView();
		SensorData sensorData = DataLoadUtils.loadSensorData("./datas/2.csv");
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
