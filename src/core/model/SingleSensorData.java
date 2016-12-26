package core.model;

import static utils.SpaceSyncConfig.*;

/**
 * 单客户端单个穿管器样本
 * @author leocai
 *
 */
public class SingleSensorData {

	private double[] data;

	public double[] getData() {
		return data;
	}

	public void setData(double[] data) {
		this.data = data;
	}

	public SingleSensorData(double[] data) {
		this.data = data;
	}

	public double[] getGYR() {
		return new double[] { data[GYR_X], data[GYR_Y], data[GYR_Z] };
	}

	public double getDT() {
		return data[DT_INDEX];
	}

	public double[] getAcc() {
		return new double[] { data[LINEAR_ACC_X], data[LINEAR_ACC_Y], data[LINEAR_ACC_Z] };
	}

	public double[] getGrivity() {
		return new double[] { data[GRIVATY_X], data[GRIVATY_Y], data[GRIVATY_Z] };
	}

	public double[] getMag() {
		return new double[] { data[MAG_X], data[MAG_Y], data[MAG_Z] };
	}

}
