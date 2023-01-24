package com.das.listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.das.reports.ExtentLogger;
import com.das.reports.ExtentReport;

public class TestListener implements ITestListener, ISuiteListener {
	
	@Override
	public void onStart(ISuite suite) {
		ExtentReport.initReport();
	}

	@Override
	public void onFinish(ISuite suite) {
		ExtentReport.tearDownReport();
	}

	@Override
	public  void onTestStart(ITestResult result) {
		ExtentReport.createExtentTest(result.getName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ExtentLogger.pass(result.getName()+ " is passed");
	}
	
	@Override
	public void onTestFailure(ITestResult result) {
		ExtentLogger.fail(String.valueOf(result.getThrowable()));
	}

}
