package com.dislab.leocai.spacesync.draw;



/**
 * 手机的3d显示接口
 * @author leocai
 *
 */
public interface PhoneDisplayer {
	
	void initView();
	void updateView();
	void reset();
	void setRotationMatrix_b2g(double[][] rtm_b2g);

}
