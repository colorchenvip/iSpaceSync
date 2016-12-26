package core.model;

import java.util.Arrays;

import transformation.CoordinateTracker;
import utils.MatrixUtils;
import utils.RotationUtils;
import static utils.SpaceSyncConfig.*;

/**
 * 单客户端传感器数据序列
 * @author leocai
 *
 */
public class SensorDataSequnce {

	

	private double[][] data;

	public SensorDataSequnce(double[][] totalDatas) {
		this.data = totalDatas;
	}

	public double[][] getData() {
		return data;
	}

	public void setData(double[][] data) {
		this.data = data;
	}

	public double[][] getLinearAccs() {
		return MatrixUtils.selectColumns(data, new int[] { LINEAR_ACC_X, LINEAR_ACC_Y, LINEAR_ACC_Z });
	}

	public double[][] getGyrs() {
		return MatrixUtils.selectColumns(data, new int[] { GYR_X, GYR_Y, GYR_Z });
	}

	public double[] getDT() {
		return MatrixUtils.selectColumn(data, DT_INDEX);
	}

	public double[][] getInitMatrix() {
		return new double[][] { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 } };
	}

	public double[][] getGravityAccs() {
		return MatrixUtils.selectColumns(data, new int[] { GRIVATY_X, GRIVATY_Y, GRIVATY_Z });
	}

	@Override
	public String toString() {
		return "SensorData [data=" + Arrays.toString(data) + "]";
	}

	public double[][] getMagnet() {
		return MatrixUtils.selectColumns(data, new int[] { MAG_X, MAG_Y, MAG_Z });
	}

	public double[][] getGlobalMagAcc() {
		return MatrixUtils.selectColumns(data, new int[] { GLOBAL_MAG_ACC_X, GLOBAL_MAG_ACC_Y, GLOBAL_MAG_ACC_Z });
	}

	public double[][] getInitGlobalMatrix_G2B() {
		double[] gravity_acc = getGravityAccs()[0];
		double[] mag_acc = getGlobalMagAcc()[0];
		return RotationUtils.getRotationMatrixG2BBy2Vectors(gravity_acc, mag_acc);
	}

	public double[][] computeGlobalByMag() {
		double[][] gacc = getGravityAccs();
		double[][] mag = getMagnet();
		double[][] lacc = getLinearAccs();
		double[][] global_acc = new double[lacc.length][lacc[0].length];
		for (int i = 0; i < data.length; i++) {
			double[][] matrix_g2b = RotationUtils.getRotationMatrixG2BBy2Vectors(gacc[i], mag[i]);
			global_acc[i] = RotationUtils.getGlobalData(lacc[i], MatrixUtils.T(matrix_g2b));
		}
		return global_acc;
	}

}
