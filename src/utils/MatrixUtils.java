package utils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by leocai on 15-9-29. 矩阵的一些实用函数
 */
public class MatrixUtils {

	public static final double[][] MATRIX_E = new double[][] { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 } };

	/**
	 * 矩阵转置
	 * 
	 * @param mat
	 * @return
	 */
	public static double[][] T(double[][] mat) {
		double[][] matrix = new double[mat[0].length][mat.length];
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				matrix[j][i] = mat[i][j];
			}
		}
		return matrix;
	}

	/**
	 * 将ｆｌｏａｔ数组转换成矩阵
	 * 
	 * @param fv
	 * @return
	 */
	public static double[][] floatArrayToMatrix(float[] fv) {
		double[][] mat = new double[fv.length / 3][3];
		for (int i = 0; i < mat.length; i++) {
			mat[i][0] = fv[i * 3];
			mat[i][1] = fv[i * 3 + 1];
			mat[i][2] = fv[i * 3 + 2];
		}
		return mat;
	}

	/**
	 * 矩阵转换成float数组
	 * 
	 * @param mat
	 * @return
	 */
	public static float[] matrixToFloatArray(double[][] mat) {
		float[] farray = new float[mat.length * 3];
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < 3; j++) {
				farray[i * 3 + j] = (float) mat[i][j];
			}
		}
		return farray;
	}

	/**
	 * 矩阵相乘
	 * 
	 * @param matl
	 * @param matr
	 * @return
	 */
	public static double[][] multiply(double[][] matl, double[][] matr) {
		int row = matl.length;
		int column = matr[0].length;
		double[][] results = new double[row][column];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				double sum = 0;
				for (int k = 0; k < matl[0].length; k++) {
					sum += matl[i][k] * matr[k][j];
				}
				results[i][j] = sum;
			}
		}
		return results;
	}

	public static void main(String args[]) {
		double[][] matl = new double[][] { { 1, 0 }, { 0, 1 } };
		double[][] matr = new double[][] { { 1, 0 }, { 0, 1 } };
		double[][] ret = MatrixUtils.multiply(matl, matr);
		System.out.println(Arrays.toString(ret));

	}

	public static void printMatrix(double[][] mat) {
		String content = "";
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				content += mat[i][j] + " ";
			}
			content += "\n";
		}
		System.out.print(content);
	}

	/**
	 * 矩阵数乘
	 * 
	 * @param num
	 * @param mat
	 * @return
	 */
	public static double[][] numMultiply(double num, double[][] mat) {
		int row = mat.length;
		int column = mat[0].length;
		double[][] results = new double[row][column];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				results[i][j] = mat[i][j] * num;
			}
		}
		return results;
	}

	/**
	 * 矩阵相加
	 * 
	 * @param mat1
	 * @param mat2
	 * @return
	 */
	public static double[][] plus(double[][] mat1, double[][] mat2) {
		int row = mat1.length;
		int column = mat1[0].length;
		double[][] results = new double[row][column];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				results[i][j] = mat1[i][j] + mat2[i][j];
			}
		}
		return results;
	}

	public static double[][] minus(double[][] mat1, double[][] mat2) {
		int row = mat1.length;
		int column = mat1[0].length;
		double[][] results = new double[row][column];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				results[i][j] = mat1[i][j] - mat2[i][j];
			}
		}
		return results;
	}

	// TODO order

	/**
	 * 数组转矩阵
	 * 
	 * @param vector
	 * @return
	 */
	public static double[][] convertVectorToMatrix(double[] vector) {
		return new double[][] { { vector[0] }, { vector[1] }, { vector[2] } };
	}

	// TODO orderTest

	/**
	 * 矩阵转数组
	 * 
	 * @param matrix
	 * @return
	 */
	public static double[] convertMatrixToVector(double[][] matrix) {
		return new double[] { matrix[0][0], matrix[1][0], matrix[2][0] };
	}

	public static double[][] selectColumns(double[][] data, int[] indexes) {
		double[][] newData = new double[data.length][indexes.length];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < indexes.length; j++) {
				newData[i][j] = data[i][indexes[j]];
			}
		}
		return newData;
	}

	public static double[] selectColumn(double[][] data, int index) {
		double[] newData = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			newData[i] = data[i][index];
		}
		return newData;
	}

	/**
	 * 均方
	 * 
	 * @param mat
	 * @return
	 */
	public static double squareMean(double[][] mat) {
		int row = mat.length;
		int column = mat[0].length;
		double sum = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				sum += Math.pow(mat[i][j], 2);
			}
		}
		sum /= (row * column);
		return sum;
	}

	public static double[][] cbind(double[][] m1, double[][] m2) {
		int row = m1.length > m2.length ? m2.length : m1.length;
		int column = m1[0].length + m2[0].length;
		double[][] rs = new double[m1.length][column];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < m1[0].length; j++) {
				rs[i][j] = m1[i][j];
			}
		}
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < m2[0].length; j++) {
				rs[i][j + m1[0].length] = m2[i][j];
			}
		}
		return rs;
	}

	public static double[][] selectRows(double[][] data, List<Integer> selectedRows) {
		double[][] selectedData = new double[selectedRows.size()][];
		for (int i = 0; i < selectedRows.size(); i++) {
			selectedData[i] = data[selectedRows.get(i)];
		}
		return selectedData;
	}

	public static double[] selectRows(double[] data, List<Integer> selectedRows) {
		double[] selectedData = new double[selectedRows.size()];
		for (int i = 0; i < selectedRows.size(); i++) {
			selectedData[i] = data[selectedRows.get(i)];
		}
		return selectedData;
	}

	public static double[][] combineMultiClientData(double[][][] data_multi) {
		int clientNum = data_multi.length;
		int row = data_multi[0].length;
		int singleClientColumns = data_multi[0][0].length;
		double[][] data = new double[row][clientNum * singleClientColumns];
		for (int client = 0; client < clientNum; client++) {
			for (int rowId = 0; rowId < row; rowId++) {
				for (int singleColumnId = 0; singleColumnId < singleClientColumns; singleColumnId++) {
					data[rowId][client * singleClientColumns
							+ singleColumnId] = data_multi[client][rowId][singleColumnId];
				}
			}
		}
		return data;
	}

	public static double[][] copyAndSetColumn(double[][] tracked_lacc, int columnId, int val) {
		int row = tracked_lacc.length, column = tracked_lacc[0].length;
		double[][] new_data = new double[row][column];
		for (int j = 0; j < column; j++) {
			if (j == columnId)
				continue;
			for (int i = 0; i < row; i++) {
				new_data[i][j] = tracked_lacc[i][j];
			}
		}
		return new_data;
	}

}
