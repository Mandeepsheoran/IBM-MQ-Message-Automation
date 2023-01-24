package com.das.reports;

import com.aventstack.extentreports.ExtentTest;

public class ExtentManager {
	
private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	
	protected static ExtentTest getExtentTest() {
		return test.get();
	}
	
	public static void setExtentTest(ExtentTest etest) {
		test.set(etest);
	}
	
	public static void unload() {
		test.remove();
	}


}
