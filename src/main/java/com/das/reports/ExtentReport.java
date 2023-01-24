package com.das.reports;

import java.util.Objects;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.das.constants.FrameworkConstants;

public class ExtentReport {
	
	private ExtentReport() {}

	private static ExtentReports extent;
	
	public static void initReport() { 
		if(Objects.isNull(extent)) {
		extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter(FrameworkConstants.getExtentReportFilePath());
		spark.config().setTheme(Theme.DARK);
		spark.config().setDocumentTitle("DAS Extent Report");
		spark.config().setReportName("DAS Automation");
		extent.attachReporter(spark);
		}
	}
	
	public static void tearDownReport() {
		if(Objects.nonNull(extent)) {
		extent.flush();
		ExtentManager.unload();
		}
	}
	
	public static void createExtentTest(String methodname) {
		ExtentTest test = extent.createTest(methodname);
		ExtentManager.setExtentTest(test);
	}

}
