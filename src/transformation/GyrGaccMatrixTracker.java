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
			global_x = MatrixUtils.getColumn(rmt_g2b, 1);
			first = false;
		}
		double w_grivity = VectorUtils.project(gyr, grivity);
		double[][] rtm_b2g = RotationUtils.quaternionToMatrix(w_grivity * dt, grivity);
		double[][] next_global_mat_x = MatrixUtils.multiply(MatrixUtils.T(rtm_b2g),
				MatrixUtils.convertVectorToMatrix(global_x));
		global_x = MatrixUtils.convertMatrixToVector(next_global_mat_x);
		double[][] rmt_g2b = CoordinateTracker.getRotationMatrixG2BByMag(grivity, global_x);
		global_x = MatrixUtils.getColumn(rmt_g2b, 1);
		// System.out.println(Arrays.toString(global_x));
		return MatrixUtils.T(rmt_g2b);
	}

	public void setInitXAxis(double[] global_x) {
		this.global_x = global_x;
	}

}
