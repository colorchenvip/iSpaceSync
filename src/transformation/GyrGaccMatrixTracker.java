package transformation;

import java.util.Arrays;

import spacesync.CoordinateTracker;
import utils.MatrixUtils;
import utils.RotationUtils;
import utils.VectorUtils;

public class GyrGaccMatrixTracker {

	private double[] global_x = new double[] { 0, 1, 0 };
	boolean first = true;

	public double[][] track_b2g(double[] gyr, double[] grivity, double dt) {
		if (first) {
			double[][] rmt_g2b = CoordinateTracker.getRotationMatrixG2BByMag(grivity, global_x);
			global_x = MatrixUtils.selectColumn(rmt_g2b, 1);
			first = false;
		}
		double w_grivity = VectorUtils.project(gyr, grivity);
		double[][] rtm_b2g = RotationUtils.quaternionToMatrix(w_grivity * dt, grivity);
		double[][] next_global_mat_x = MatrixUtils.multiply(MatrixUtils.T(rtm_b2g),
				MatrixUtils.convertVectorToMatrix(global_x));
		global_x = MatrixUtils.convertMatrixToVector(next_global_mat_x);
		double[][] rmt_g2b = CoordinateTracker.getRotationMatrixG2BByMag(grivity, global_x);
		global_x = MatrixUtils.selectColumn(rmt_g2b, 1);
		// System.out.println(Arrays.toString(global_x));
		return MatrixUtils.T(rmt_g2b);
	}

	public void setInitXAxis(double[] global_x) {
		this.global_x = global_x;
	}

	public double[][] trackGlobalAcc(double[][] accs, double[][] gyrs, double[][] gravitys, double[] dts,
			double[] init_x_axis) {
		setInitXAxis(init_x_axis);
		double[][] newAccs = new double[accs.length][];
		for (int i = 0; i < accs.length; i++) {
			double[] gyr = gyrs[i];
			double[] acc = accs[i];
			double[] gravity = gravitys[i];
			double dt = dts[i];
			double[][] rtm_b2g = track_b2g(gyr, gravity, dt);
			newAccs[i] = CoordinateTracker.getGlobalData(acc, rtm_b2g);
		}
		return newAccs;
	}

}
