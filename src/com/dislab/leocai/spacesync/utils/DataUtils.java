package com.dislab.leocai.spacesync.utils;

import java.util.Arrays;

public class DataUtils {

	public static double[] resultantData(double[][] data) {
		double resultant_data[] = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			double sum = 0;
			for (int j = 0; j < data[0].length; j++) {
				sum += Math.pow(data[i][j], 2);
			}
			resultant_data[i] = Math.sqrt(sum);
		}
		return resultant_data;
	}

	public static double[][] selectRows(double[][] data, int start, int end) {
		int len = end - start + 1;
		double[][] newData = new double[len][data[0].length];
		for (int i = start; i <= end; i++) {
			for (int j = 0; j < data[0].length; j++) {
				newData[i - start][j] = data[i][j];
			}
		}
		return newData;
	}

	public static double[][] smooth(double[][] data, int smoothSize) {
		return RollApply.rollApply(data, smoothSize, new RollApplyFunMean());
	}

	public static double[] parseData(String newLine, int[] ids) {
		String[] str_line = newLine.split(",");
		double[] singleData = new double[ids.length];
		for (int i = 0; i < ids.length; i++) {
			singleData[i] = Double.parseDouble(str_line[ids[i]]);
		}
		return singleData;
	}

	/**
	 * 抽取某个客户端的特定传感器的索引
	 * 
	 * @param totalLen
	 * 总数据长度
	 * @param ids
	 * @param clientIndex
	 * @param clientsNum
	 * @return
	 */
	public static int[] getClientTargetIndexes(int totalLen, int[] ids, int clientIndex, int clientsNum) {
		int dataLen = totalLen / clientsNum;
		for (int i = 0; i < ids.length; i++) {
			ids[i] += dataLen * clientIndex;
		}
		return ids;
	}

	public static void main(String args[]) {
		System.out.println(Arrays.toString(DataUtils.getClientTargetIndexes(10, new int[]{0,1}, 1, 2)));
	}

}
