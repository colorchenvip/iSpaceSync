package draw;

import java.util.Arrays;

import utils.MatrixUtils;

@Deprecated
public class RealTimeChartMultiClientImpl implements RealTimeChartMultiClient {
	RealTimeChart[] charts;
	private int clientsNum;

	public RealTimeChartMultiClientImpl(int clientsNum) {
		this.clientsNum = clientsNum;
		charts = new RealTimeChart[clientsNum];
		for (int i = 0; i < clientsNum; i++) {
			RealTimeChart chart_multi_data = new RealTimeChartXYPlotImpl("linear acc " + i,
					new String[] { "x", "y", "z" });
			charts[i] = chart_multi_data;
			chart_multi_data.setRange(-10, 10);
		}
	}

	@Override
	public void plotMuti(double[][] tracked_hori_lacc) {
		for (int i = 0; i < clientsNum; i++) {
			charts[i].showStaticData(
					MatrixUtils.selectColumns(tracked_hori_lacc, new int[] { i * 3, i * 3 + 1, i * 3 + 2 }));
		}
	}

}
