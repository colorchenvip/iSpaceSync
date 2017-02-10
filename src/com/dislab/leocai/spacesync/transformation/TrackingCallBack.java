package com.dislab.leocai.spacesync.transformation;


public interface TrackingCallBack {

	void dealWithRotationMatrix_b2g(double[][] rtm_b2g);
	
	void dealWithMagRotationMatrix_b2g(double[][] rtm_mag_b2g);

}
