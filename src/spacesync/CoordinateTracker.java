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
		matrixUpdate.setCuMatrixB2G(cuMatrix_b2g);
		for (int i = 0; i < gyrs.length; i++) {
			cuMatrix_b2g = matrixUpdate.updateMatrixByGYR(gyrs[i], dt[i]);
			dealWithRotationMatrix_B2G.deal(i, cuMatrix_b2g);
		}
	}
	

	public static double[][] getRotationMatrixG2BByMag(double[] gravity_acc, double[] mag) {
		double Ex = mag[0];
		double Ey = mag[1];
		double Ez = mag[2];
		double Ax = gravity_acc[0];
		double Ay = gravity_acc[1];
		double Az = gravity_acc[2];
		double Hx = Ey * Az - Ez * Ay;
		double Hy = Ez * Ax - Ex * Az;
		double Hz = Ex * Ay - Ey * Ax;
		double normH = Math.sqrt(Hx * Hx + Hy * Hy + Hz * Hz);
		double invH = 1.0 / normH;
		Hx = Hx * invH;
		Hy = Hy * invH;
		Hz = Hz * invH;
		double invA = 1.0 / Math.sqrt(Ax * Ax + Ay * Ay + Az * Az);
		Ax = Ax * invA;
		Ay = Ay * invA;
		Az = Az * invA;
		double Mx = Ay * Hz - Az * Hy;
		double My = Az * Hx - Ax * Hz;
		double Mz = Ax * Hy - Ay * Hx;
		return new double[][] { 
			{ Hx, Mx, Ax }, 
			{ Hy, My, Ay }, 
			{ Hz, Mz, Az } };
	}

}
