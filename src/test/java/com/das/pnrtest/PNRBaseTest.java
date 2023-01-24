package com.das.pnrtest;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.das.queuesetup.PNRDASTODBQueueContext;
import com.das.queuesetup.PNRPushQueueContext;

public class PNRBaseTest extends PNRDASTODBQueueContext {

	@BeforeSuite
	public static void setUpConnection() {
		PNRPushQueueContext.createPNRInputQueueContext();
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		PNRDASTODBQueueContext.createPNROutputQueueContext();
	}

	@AfterSuite
	public static void tearDownConnection() {
		context.close();
		contextout.close();
	}

}
