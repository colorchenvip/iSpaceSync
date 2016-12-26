package draw;

import transformation.TrackingCallBack;

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
