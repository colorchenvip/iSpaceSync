package spacesync;

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
		return new double[] { data[SensorDataList.GYR_X], data[SensorDataList.GYR_Y], data[SensorDataList.GYR_Z] };
	}

	public double getDT() {
		return data[SensorDataList.DT_INDEX];
	}

	public double[] getAcc() {
		return new double[] { data[SensorDataList.LINEAR_X], data[SensorDataList.LINEAR_Y],
				data[SensorDataList.LINEAR_Z] };
	}

	public double[] getGrivity() {
		return new double[] { data[SensorDataList.GRIVATY_X], data[SensorDataList.GRIVATY_Y], data[SensorDataList.GRIVATY_Z] };
	}

	public double[] getMag() {
		return new double[] { data[SensorDataList.MAG_X], data[SensorDataList.MAG_Y], data[SensorDataList.MAG_Z] };
	}

}
