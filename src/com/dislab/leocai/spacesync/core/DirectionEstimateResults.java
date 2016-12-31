package com.dislab.leocai.spacesync.core;


public class DirectionEstimateResults {
	private double[][] clinetsInitXAxis;
	private double[][] clinetsPreInitXAxis;

	public DirectionEstimateResults(int clientsNum) {
		clinetsInitXAxis = new double[clientsNum][3];
		clinetsPreInitXAxis = new double[clientsNum][3];
	}

	public double[][] getClientsInitXAxis() {
		return clinetsInitXAxis;
	}
	
	public double[][] getClientsPreInitXAxis() {
		return clinetsPreInitXAxis;
	}

	public void setClientsRs(double[][] clientsRs) {
		this.clinetsInitXAxis = clientsRs;
	}

	public void set(int clientId, double[] syncRs) {
		clinetsInitXAxis[clientId] = syncRs;
	}

	public void printAngle() {
		for (double[] rs : clinetsInitXAxis) {
			System.out.println(Math.atan2(rs[0], rs[1]) / Math.PI * 180);
		}
	}

	public void setPre(int clientId, double[] syncRs) {
		clinetsPreInitXAxis[clientId] = syncRs;
	}

}
