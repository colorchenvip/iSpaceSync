package com.dislab.leocai.spacesync.transformation;

import com.dislab.leocai.spacesync.utils.MatrixUtils;
import com.dislab.leocai.spacesync.utils.RotationUtils;
import com.dislab.leocai.spacesync.utils.VectorUtils;

/**
 * 重力定期补偿姿态跟踪
 * @author leocai
 *
 */
public class ComplementaryMatrixTracker extends GyroMatrixTracker {

	private double[] preGravity;
	private double[][] preMatrixCarlibrated;
	private int sampleIndex = 0;

	public double[][] carlibrate(double[][] rtm_grivity_b2g, double[] gravity, double[][] rtm_gyro_b2g) {
		double mindist = 10000000;
		double[][] bestRm = rtm_gyro_b2g;
		for (int angle = 0; angle < 360; angle++) {
			double[][] rm = RotationUtils.quaternionToMatrix(angle / Math.PI * 180, gravity);
			double[][] minusedRTM = MatrixUtils.minus(MatrixUtils.multiply(rtm_grivity_b2g, rm), rtm_gyro_b2g);
			double dist = MatrixUtils.squareMean(minusedRTM);
			if (dist < mindist) {
				mindist = dist;
				bestRm = MatrixUtils.multiply(rtm_grivity_b2g, rm);
			}
		}
		setCuMatrixB2G(bestRm);
		return bestRm;
	}

	public double[][] trackWithCarlibrate(double[] gyr, double[] gravity, double dt) {
		if (preGravity == null) {
			preGravity = gravity;
			preMatrixCarlibrated = cuMatrix;
		}
		cuMatrix = trackByGYR(gyr, dt);
		double[][] rtm_grivity_update_b2g = getCarlibratedMatirx(gravity, preGravity);
		if (sampleIndex % 10 == 0)
			cuMatrix = carlibrate(MatrixUtils.multiply(preMatrixCarlibrated, rtm_grivity_update_b2g), gravity,
					cuMatrix);
		preGravity = gravity;
		preMatrixCarlibrated = cuMatrix;
		sampleIndex++;
		return cuMatrix;
	}

	private double[][] getCarlibratedMatirx(double[] computedInitGV, double[] initRealGV) {
		double[] vectorBefore = computedInitGV;
		double[] vectorAfter = initRealGV;
		double[] rotationAxis = VectorUtils.crossProduct(vectorBefore, vectorAfter);
		double rotationAngle = Math.acos(VectorUtils.dotProduct(vectorBefore, vectorAfter)
				/ VectorUtils.absVector(vectorBefore) / VectorUtils.absVector(vectorAfter));
		double[][] rotationMatrix = RotationUtils.quaternionToMatrix(rotationAngle, rotationAxis);
		return rotationMatrix;
	}

}
