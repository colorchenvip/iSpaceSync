package com.dislab.leocai.spacesync.core;


/**
 * 抽取傳感器的相关性主成分
 * @author leocai
 *
 */
public interface ConsistentExtraction {

	double[] extractConsistentData(double[][] ds);

}
