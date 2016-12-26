package com.dislab.leocai.spacesync.transformation;

import java.util.Arrays;

import com.dislab.leocai.spacesync.utils.MatrixUtils;


/**
 * Created by leocai on 15-10-10.
 * 仅用陀螺仪跟踪
 */
public class GyroMatrixTracker {
	protected double[][] cuMatrix = new double[][] { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 } };
	public static double[][] I = new double[][] { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 } };

	public double[][] updateMatrix(double[][] matUpdate) {
		cuMatrix = MatrixUtils.multiply(cuMatrix, matUpdate);
		return cuMatrix;
	}

	public double[][] trackByGYR(double[] gyr, double dt) {
		double delta = Math.sqrt(Math.pow(gyr[0] * dt, 2) + Math.pow(gyr[1] * dt, 2) + Math.pow(gyr[2] * dt, 2));
		if (delta == 0)
			return updateMatrix(MatrixUtils.MATRIX_E);
		double[][] B = new double[][] { 
			{ 0, -gyr[2] * dt, gyr[1] * dt }, 
			{ gyr[2] * dt, 0, -gyr[0] * dt },
			{ -gyr[1] * dt, gyr[0] * dt, 0 } };
		double[][] B1 = MatrixUtils.numMultiply(Math.sin(delta) / delta, B);
		double[][] B2 = MatrixUtils.numMultiply((1 - Math.cos(delta)) / Math.pow(delta, 2), MatrixUtils.multiply(B, B));
		double[][] updateMatrix = MatrixUtils.plus(MatrixUtils.plus(I, B1), B2);
		return updateMatrix(updateMatrix);
	}

	public double[][] getCuMatrix() {
		return cuMatrix;
	}

	public void setCuMatrixB2G(double[][] cuMatrixB2G) {
		this.cuMatrix = cuMatrixB2G;
	}

	public void resetCuMatrix() {
		cuMatrix = new double[][] { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 } };
	}
}
