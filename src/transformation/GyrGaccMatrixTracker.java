package transformation;

import utils.MatrixUtils;
import utils.RotationUtils;
import utils.VectorUtils;

/**
 * 陀螺仪跟踪x轴旋转,结合重力构造旋转矩阵
 * @author leocai
 *
 */
public class GyrGaccMatrixTracker {

	private double[] global_x = new double[] { 0, 1, 0 };
	boolean first = true;

	public double[][] track_b2g(double[] gyr, double[] grivity, double dt) {
		if (first) {
			double[][] rmt_g2b = RotationUtils.getRotationMatrixG2BBy2Vectors(grivity, global_x);
			global_x = MatrixUtils.selectColumn(rmt_g2b, 1);
			first = false;
		}
		double w_grivity = VectorUtils.project(gyr, grivity);
		double[][] rtm_b2g = RotationUtils.quaternionToMatrix(w_grivity * dt, grivity);
		double[][] next_global_mat_x = MatrixUtils.multiply(MatrixUtils.T(rtm_b2g),
				MatrixUtils.convertVectorToMatrix(global_x));
		global_x = MatrixUtils.convertMatrixToVector(next_global_mat_x);
		double[][] rmt_g2b = RotationUtils.getRotationMatrixG2BBy2Vectors(grivity, global_x);
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
			newAccs[i] = RotationUtils.getGlobalData(acc, rtm_b2g);
		}
		return newAccs;
	}

	public void track_b2g(TrackingCallBack trackingCallBack, double[][] gyrs, double[][] gravitys, double[] dts) {
		for (int i = 0; i < gyrs.length; i++) {
			double[] gyr = gyrs[i];
			double[] gravity = gravitys[i];
			double dt = dts[i];
			double[][] rtm_b2g = track_b2g(gyr, gravity, dt);
			trackingCallBack.dealWithRotationMatrix_b2g(rtm_b2g);
		}
	}

	public void track_b2g(TrackingCallBack trackingCallBack, double[] gyr, double[] grivity, double dt) {
		double[][] rtm_b2g = track_b2g(gyr, grivity, dt);
		trackingCallBack.dealWithRotationMatrix_b2g(rtm_b2g);
	}

}
