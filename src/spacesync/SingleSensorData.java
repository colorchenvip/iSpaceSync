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
		return new double[] { data[SensorData.GYR_X], data[SensorData.GYR_Y], data[SensorData.GYR_Z] };
	}

	public double getDT() {
		return data[SensorData.DT_INDEX];
	}

	public double[] getAcc() {
		return new double[] { data[SensorData.LINEAR_X], data[SensorData.LINEAR_Y],
				data[SensorData.LINEAR_Z] };
	}

	public double[] getGrivity() {
		return new double[] { data[SensorData.GRIVATY_X], data[SensorData.GRIVATY_Y], data[SensorData.GRIVATY_Z] };
	}

	public double[] getMag() {
		return new double[] { data[SensorData.MAG_X], data[SensorData.MAG_Y], data[SensorData.MAG_Z] };
	}

}
