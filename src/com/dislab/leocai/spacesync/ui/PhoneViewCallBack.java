package com.dislab.leocai.spacesync.ui;

import com.dislab.leocai.spacesync.transformation.TrackingCallBack;
import com.dislab.leocai.spacesync.ui.PhoneDisplayer;

/**
 * 手机显示回调函数
 * @author leocai
 *
 */
public class PhoneViewCallBack implements TrackingCallBack {

	PhoneDisplayer phoneDisplayer;

	public PhoneViewCallBack(PhoneDisplayer phoneDisplayer) {
		this.phoneDisplayer = phoneDisplayer;
	}

	@Override
	public void dealWithRotationMatrix_b2g(double[][] rtm_b2g) {
		phoneDisplayer.setRotationMatrix_b2g(rtm_b2g);
	}

}
