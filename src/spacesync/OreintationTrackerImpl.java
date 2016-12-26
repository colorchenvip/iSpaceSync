package spacesync;

import draw.RealTimeChart;
import transformation.GyrGaccMatrixTracker;
import utils.ChartsUtils;
import utils.MyDataBuffer;

public class OreintationTrackerImpl implements OreintationTracker {

	private int clientNum;
	private GyrGaccMatrixTracker[] matrixTrackers;
	private TrackingCallBack[] trackingCallBacks;

	private RealTimeChart chartsGyrs[];
	private RealTimeChart chartsGrivitys[];
	private RealTimeChart chartsDt[];

	public OreintationTrackerImpl(int clientsNum, GyrGaccMatrixTracker[] matrixTrackers,
			TrackingCallBack[] trackingCallBacks) {
		this.clientNum = clientsNum;
		this.trackingCallBacks = trackingCallBacks;
		this.matrixTrackers = matrixTrackers;
		this.chartsGyrs = ChartsUtils.initMultiLacc("gyr", clientsNum);
		this.chartsGrivitys = ChartsUtils.initMultiLacc("gravitys", clientsNum);
		this.chartsDt = ChartsUtils.initMultiLacc("dt", clientsNum);

	}

	@Override
	public TrackingResults track(double[][] data_multi, DirectionEstimateResults directionEstimateResults) {
		double[][] xAxisMulti = null;
		if (directionEstimateResults != null)
			xAxisMulti = directionEstimateResults.getClientsInitXAxis();
		for (int i = 0; i < clientNum; i++) {
			SingleSensorData sensorData = new SingleSensorData(data_multi[i]);
			if (directionEstimateResults != null)
				matrixTrackers[i].setInitXAxis(xAxisMulti[i]);
			matrixTrackers[i].track_b2g(trackingCallBacks[i], sensorData.getGYR(), sensorData.getGrivity(), sensorData.getDT());
		}
		return null;
	}

}
