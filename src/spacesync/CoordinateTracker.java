package spacesync;

import transformation.DealWithRotationMatrix_B2G;
import transformation.MatrixUpdate;
import utils.MatrixUtils;

public class CoordinateTracker {

	MatrixUpdate matrixUpdate = new MatrixUpdate();

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
				globalAccs[i] = getGlobalData(accs[i], cuMatrix_b2g);
			}
		}, gyrs, dt, initMatrix_g2b);
		return globalAccs;
	}

	public static double[] getGlobalData(double[] localData, double[][] rotationMatrix_b2g) {
		double[][] global_acc = MatrixUtils.multiply(rotationMatrix_b2g, MatrixUtils.convertVectorToMatrix(localData));
		return MatrixUtils.convertMatrixToVector(global_acc);
	}

	public void trackByGyro(DealWithRotationMatrix_B2G dealWithRotationMatrix_B2G, double[][] gyrs, double[] dt,
			double[][] initMatrix_g2b) {
		double[][] cuMatrix_b2g = MatrixUtils.T(initMatrix_g2b);
		matrixUpdate.setCuMatrix(cuMatrix_b2g);
		for (int i = 0; i < gyrs.length; i++) {
			cuMatrix_b2g = matrixUpdate.updateMatrixByGYR(gyrs[i], dt[i]);
			dealWithRotationMatrix_B2G.deal(i, cuMatrix_b2g);
		}
	}

}
