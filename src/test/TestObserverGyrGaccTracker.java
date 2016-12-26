package test;

import connection.datalistteners.ObserverGyrGaccTrackerSingleClient;
import draw.PhoneDisplayerPCImpl;
import transformation.GyrGaccMatrixTracker;

public class TestObserverGyrGaccTracker {

	public static void main(String[] args) {
		PhoneDisplayerPCImpl phoneDisplayerImpl = new PhoneDisplayerPCImpl();
		phoneDisplayerImpl.initView();
		GyrGaccMatrixTracker gaccMatrixTracker = new GyrGaccMatrixTracker();
		gaccMatrixTracker.setInitXAxis(new double[] { 1, 0, 0 });

		for (int i = 0; i < 1000; i++) {
			double[] gyr = new double[] { 0, 0, -Math.PI / 500 };
			double[] grivity = new double[] { 0, 0, 9 };
			double[][] rtm = gaccMatrixTracker.track_b2g(gyr, grivity, 0.2);
			phoneDisplayerImpl.setRotationMatrix_b2g(rtm);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
