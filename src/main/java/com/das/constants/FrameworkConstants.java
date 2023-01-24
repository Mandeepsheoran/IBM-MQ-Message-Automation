package com.das.constants;

import java.util.Objects;


public class FrameworkConstants {

private static FrameworkConstants frameworkconstant;
	
	public static FrameworkConstants getInstance() {
		if(Objects.isNull(frameworkconstant)) {
			frameworkconstant = new FrameworkConstants();
		}
		return frameworkconstant;
	}
	
	private final static String pnrfilepath = System.getProperty("user.dir") + "/src/test/resources/inputmessage/file.txt";
	
	public static String getPnrFilePath() {
		return pnrfilepath;
	}

	private final static String configfilepath = System.getProperty("user.dir")+"/src/test/resources/config/config.properties";

	public static String getConfigfilepath() {
		return configfilepath;
	}
		
    private final static String extentreportfilepath = System.getProperty("user.dir") + "/src/test/resources/reports/DASTestResults.html";
	
	public static String getExtentReportFilePath() {
		return extentreportfilepath;
	}

}
