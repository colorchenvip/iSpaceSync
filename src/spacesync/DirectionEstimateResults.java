package spacesync;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DirectionEstimateResults {
	private List<double[]> clientsRs;

	public List<double[]> getClientsRs() {
		return clientsRs;
	}

	public void setClientsRs(List<double[]> clientsRs) {
		this.clientsRs = clientsRs;
	}

	public DirectionEstimateResults(int clientsNum) {
		clientsRs = new ArrayList<>(clientsNum);
	}

	public void add(double[] syncRs) {
		clientsRs.add(syncRs);
	}

	public void printAngle() {
		for (double[] rs : clientsRs) {
			System.out.println(Math.atan2(rs[0], rs[1]) / Math.PI * 180);
		}

	}

}
