package transformation;

import utils.MatrixUtils;
import utils.RotationUtils;

@Deprecated
public class CoordinateTracker {

	GyroMatrixTracker matrixUpdate = new GyroMatrixTracker();

	public double[][] trackByGyro(final double[][] accs, double[][] gyrs, double[] dt, double[][] initMatrix_g2b) {
		// double[][] cuMatrix_b2g = MatrixUtils.T(initMatrix_g2b);
		// matrixUpdate.setCuMatrix(cuMatrix_b2g);
		// for (int i = 0; i < gyrs.length; i++) {
		// cuMatrix_b2g = matrixUpdate.updateMatrixByGYR(gyrs[i], dt[i]);
		// }
		final double[][] globalAccs = new double[accs.length][accs[0].length];
		trackByGyro(new DealWithRotationMatrix_B2G() {
			@Override
			public void deal(int i, double[][] cuMatrix_b2g) {
				globalAccs[i] = RotationUtils.getGlobalData(accs[i], cuMatrix_b2g);
			}
		}, gyrs, dt, initMatrix_g2b);
		return globalAccs;
	}

	public void trackByGyro(DealWithRotationMatrix_B2G dealWithRotationMatrix_B2G, double[][] gyrs, double[] dt,
			double[][] initMatrix_g2b) {
		double[][] cuMatrix_b2g = MatrixUtils.T(initMatrix_g2b);
		matrixUpdate.setCuMatrixB2G(cuMatrix_b2g);
		for (int i = 0; i < gyrs.length; i++) {
			cuMatrix_b2g = matrixUpdate.trackByGYR(gyrs[i], dt[i]);
			dealWithRotationMatrix_B2G.deal(i, cuMatrix_b2g);
		}
	}
	

	

}
