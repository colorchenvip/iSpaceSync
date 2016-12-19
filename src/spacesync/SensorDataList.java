package spacesync;

import java.util.Arrays;

import utils.MatrixUtils;

public class SensorDataList {

	public static final int LINEAR_X = 0;
	public static final int LINEAR_Y = 1;
	public static final int LINEAR_Z = 2;
	public static final int GRIVATY_X = 3;
	public static final int GRIVATY_Y = 4;
	public static final int GRIVATY_Z = 5;
	public static final int GYR_X = 6;
	public static final int GYR_Y = 7;
	public static final int GYR_Z = 8;
	public static final int MAG_X = 9;
	public static final int MAG_Y = 10;
	public static final int MAG_Z = 11;
	public static final int GLOBAL_MAG_ACC_X = 12;
	public static final int GLOBAL_MAG_ACC_Y = 13;
	public static final int GLOBAL_MAG_ACC_Z = 14;
	public static final int DT_INDEX = 15;

	private double[][] data;

	public SensorDataList(double[][] totalDatas) {
		this.data = totalDatas;
	}

	public double[][] getData() {
		return data;
	}

	public void setData(double[][] data) {
		this.data = data;
	}

	public double[][] getLinearAccs() {
		return MatrixUtils.getColumns(data, new int[] { LINEAR_X, LINEAR_Y, LINEAR_Z });
	}

	public double[][] getGyrs() {
		return MatrixUtils.getColumns(data, new int[] { GYR_X, GYR_Y, GYR_Z });
	}

	public double[] getDT() {
		return MatrixUtils.getColumn(data, DT_INDEX);
	}

	public double[][] getInitMatrix() {
		return new double[][] { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 } };
	}

	public double[][] getGravityAccs() {
		return MatrixUtils.getColumns(data, new int[] { GRIVATY_X, GRIVATY_Y, GRIVATY_Z });
	}

	@Override
	public String toString() {
		return "SensorData [data=" + Arrays.toString(data) + "]";
	}

	public double[][] getMagnet() {
		return MatrixUtils.getColumns(data, new int[] { MAG_X, MAG_Y, MAG_Z });
	}

	public double[][] getGlobalMagAcc() {
		return MatrixUtils.getColumns(data, new int[] { GLOBAL_MAG_ACC_X, GLOBAL_MAG_ACC_Y, GLOBAL_MAG_ACC_Z });
	}

	public double[][] getInitGlobalMatrix_G2B() {
		double[] gravity_acc = getGravityAccs()[0];
		double[] mag_acc = getGlobalMagAcc()[0];
		return CoordinateTracker.getRotationMatrixG2BByMag(gravity_acc, mag_acc);
	}


	public double[][] computeGlobalByMag() {
		double[][] gacc = getGravityAccs();
		double[][] mag = getMagnet();
		double[][] lacc = getLinearAccs();
		double[][] global_acc = new double[lacc.length][lacc[0].length];
		for (int i = 0; i < data.length; i++) {
			double[][] matrix_g2b = CoordinateTracker.getRotationMatrixG2BByMag(gacc[i], mag[i]);
			global_acc[i] = CoordinateTracker.getGlobalData(lacc[i], MatrixUtils.T(matrix_g2b));
		}
		return global_acc;
	}

}
