package com.das.requestbuilder;

import static com.das.utils.FakeDataGenerator.*;

public class RequestBuilder {
	
	private RequestBuilder() {}
	
	public static String preparePNRRequest(String inputmessage) {
		String pnrmessage = inputmessage.replace("KUMAR", randomLastName())
		   .replace("LAL", randomFirstName())
		   .replace("ONE", randomBookingRef())
		   .replace("TWO", randomBookingRef())
		   .replace("XX8898996", randomPassport())
		   .replace("PH", randomNationality())
		   .replace("04JAN15", "04JAN25");		
		return pnrmessage;
	}
	
	public static String prepareDCSRequest(String inputmessage) {	
		return null;
	}
	
	public static String prepareAPPRequest(String inputmessage) {
		return null;
	}
	
	public static String prepareAPISRequest(String inputmessage) {
		return null;
	}

}
