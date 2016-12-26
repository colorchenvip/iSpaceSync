package utils;

public class SpaceSyncConfig {

	/**
	 * 接收后单个样本的列长度
	 */
	public static final int DATA_LEN = 16;
	/**
	 * 缓冲区长度
	 */
	public static final int BUFFER_SIZE = 100;
	/**
	 * 判断同步的阈值
	 */
	public static final double SYNC_THRESHOLD = 4;
	
	/**
	 * 接收数据后需要筛选的id值
	 */
	public static final int[] RECEIVED_SELECTED_INDEXES =  new int[] { 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 20 };
	
	//以下是接收并选择后对应的传感器数据id
	public static final int LINEAR_ACC_X = 0;
	public static final int LINEAR_ACC_Y = 1;
	public static final int LINEAR_ACC_Z = 2;
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
	
	public static final int[] GRIVITY_INDEXES = new int[] { GRIVATY_X, GRIVATY_Y, GRIVATY_Z };
	public static final int[] LINEAR_ACC_INDEXES = new int[] { LINEAR_ACC_X, LINEAR_ACC_Y, LINEAR_ACC_Z };
	public static final int[] DT_INDEXES = new int[] { DT_INDEX };
	public static final int[] GYR_INDEXES = new int[]{GYR_X, GYR_Y, GYR_Z};

}
