package com.dislab.leocai.spacesync.utils;

/**
 * 向量工具类
 * 
 * @author leocai
 *
 */
public class VectorUtils {

	/**
	 * 计算向量夹角，返回度
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double getAngle(double[] v1, double[] v2) {
		double vs = dotMultiply(v1, v2);
		double abs_1 = absVector(v1);
		double abs_2 = absVector(v2);
		return Math.acos(vs / (abs_1 * abs_2)) / Math.PI * 180;
	}

	/**
	 * 计算向量点乘
	 * @param v1
	 * @param v2
	 * @return
	 */
	private static double dotMultiply(double[] v1, double[] v2) {
		double sum = 0;
		for (int i = 0; i < v1.length; i++) {
			sum += v1[i] * v2[i];
		}
		return sum;
	}

	/**
	 * 计算向量数乘
	 * @param vector
	 * @param d
	 * @return
	 */
	public static double[] vectorNumMultiply(double[] vector, double d) {
		double[] newdata = new double[vector.length];
		for (int i = 0; i < vector.length; i++) {
			newdata[i] = vector[i] * d;
		}
		return newdata;
	}

	/**
	 * 计算向量长度
	 * @param gv
	 * @return
	 */
	public static double absVector(double[] gv) {
		double sum = 0;
		for (double d : gv) {
			sum += d * d;
		}
		return Math.sqrt(sum);
	}

	/**
	 * 向量叉乘
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double[] crossProduct(double[] v1, double[] v2) {
		double rsv[] = new double[3];
		rsv[0] = v1[1] * v2[2] - v1[2] * v2[1];
		rsv[1] = v1[2] * v2[0] - v1[0] * v2[2];
		rsv[2] = v1[0] * v2[1] - v1[1] * v2[0];
		return rsv;
	}

	public static double dotProduct(double[] v1, double[] v2) {
		return (v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2]);

	}

	/**
	 * 计算向量投影
	 * @param vector
	 * @param direction
	 * @return
	 */
	public static double project(double[] vector, double[] direction) {
		return dotMultiply(vector, direction)/ absVector(direction);
		
	}

	/**
	 * 计算数据平均值
	 * @param data
	 * @return
	 */
	public static double mean(double[] data) {
		double mean = 0;
		for (double d : data) {
			mean += d;
		}
		return mean / data.length;
	}
}
