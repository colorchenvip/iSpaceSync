package utils;

import java.util.Arrays;

public class RotationUtils {
	/**
	 * 四元数转旋转矩阵
	 * 
	 * @param angle
	 * @param axis
	 * @return
	 */
	public static double[][] quaternionToMatrix(double angle, double[] axis) {
		double[] u = Arrays.copyOf(axis, axis.length);
		double norm = VectorUtils.absVector(u);
		u[0] = u[0] / norm;
		u[1] = u[1] / norm;
		u[2] = u[2] / norm;
		double[][] rotationMatrix = new double[3][3];

		rotationMatrix[0][0] = Math.cos(angle) + Math.pow(u[0], 2) * (1 - Math.cos(angle));
		rotationMatrix[0][1] = u[0] * u[1] * (1 - Math.cos(angle)) - u[2] * Math.sin(angle);
		rotationMatrix[0][2] = u[1] * Math.sin(angle) + u[0] * u[2] * (1 - Math.cos(angle));

		rotationMatrix[1][0] = u[2] * Math.sin(angle) + u[0] * u[1] * (1 - Math.cos(angle));
		rotationMatrix[1][1] = Math.cos(angle) + Math.pow(u[1], 2) * (1 - Math.cos(angle));
		rotationMatrix[1][2] = -u[0] * Math.sin(angle) + u[1] * u[2] * (1 - Math.cos(angle));

		rotationMatrix[2][0] = -u[1] * Math.sin(angle) + u[0] * u[2] * (1 - Math.cos(angle));
		rotationMatrix[2][1] = u[0] * Math.sin(angle) + u[1] * u[2] * (1 - Math.cos(angle));
		rotationMatrix[2][2] = Math.cos(angle) + Math.pow(u[2], 2) * (1 - Math.cos(angle));

		return (rotationMatrix);
	}
	
	/**
	 * 利用旋转矩阵获取全局数据
	 * @param localData
	 * @param rotationMatrix_b2g
	 * @return
	 */
	public static double[] getGlobalData(double[] localData, double[][] rotationMatrix_b2g) {
		double[][] global_acc = MatrixUtils.multiply(rotationMatrix_b2g, MatrixUtils.convertVectorToMatrix(localData));
		return MatrixUtils.convertMatrixToVector(global_acc);
	}
	
	/**
	 * 根据两个向量获得旋转矩阵
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double[][] getRotationMatrixG2BBy2Vectors(double[] v1, double[] v2) {
		double Ex = v2[0];
		double Ey = v2[1];
		double Ez = v2[2];
		double Ax = v1[0];
		double Ay = v1[1];
		double Az = v1[2];
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
