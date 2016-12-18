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
}
