package com.das.queuesetup;

import com.das.utils.PropertyFileUtil;

public class CreateQueue extends ConnectionFactory{
	
	public static void createPNRPushQueue() {
		dasinputqueue = context.createQueue("queue:///" + PropertyFileUtil.getValue("DAS_INPUT_QUEUENAME"));
	}	
	public static void createPNRDasToDBQueue() {
		dasoutputqueue = context.createQueue("queue:///" + PropertyFileUtil.getValue("DAS_OUTPUT_QUEUENAME"));
	}
}
