package com.dislab.leocai.spacesync.core;

import com.dislab.leocai.spacesync.utils.MatrixUtils;
import com.dislab.leocai.spacesync.utils.RotationUtils;

public class DirectionEstimateResults {
	/**
	 * 估计的Ｘ轴
	 */
	private double[][] clinetsInitXAxis;
	private double[][] clinetsPreInitXAxis;
	private double[][] clientsMagDirections;
	private double[][] firstGravitys;

	public DirectionEstimateResults(int clientsNum) {
		clinetsInitXAxis = new double[clientsNum][3];
		clinetsPreInitXAxis = new double[clientsNum][3];
		clientsMagDirections = new double[clientsNum][3];
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
	
	public double[][] getClientsMagDirections(){
		return this.clientsMagDirections;
	}

	public void setClientsMagDirections(double[][] clientsMagDirections) {
		this.clientsMagDirections = clientsMagDirections;
	}

	public void setFirstGravitys(double[][] firstGravitys) {
		this.firstGravitys = firstGravitys;
	}
	
	/**
	 * 获取ＦＣ在水平面的矢量
	 * @return
	 */
	public double[][] getHoriFcDirection(){
		double fcdirections[][] = new double[clinetsInitXAxis.length][];
		for(int i=0;i< clinetsInitXAxis.length;i++){
			double[][] rtm = RotationUtils.getRotationMatrixG2BBy2Vectors(firstGravitys[i], clinetsInitXAxis[i]);
			fcdirections[i] = MatrixUtils.T(rtm)[1];
		}
		return fcdirections;
	}
	
	

}
