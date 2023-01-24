package com.das.pnrtest;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import com.das.requestbuilder.DASResponseParsing;

public class DASOutputPNRTest extends PNRBaseTest{
	
	@Test
	public static void validateBookingRef() {
		Assertions.assertThat(DASResponseParsing.parseXMLResponseWithIndex(doc, "PNR", "bookingReferenceNumber", 1))
		.isNotBlank()
		.isNotEmpty()
		.isEqualTo("TW");	          
	}
	@Test
	public static void validateJourneySegment() {
		Assertions.assertThat(DASResponseParsing.parseXMLResponseWithAttribute(doc, "JourneySegment", "journeyReference"))
		.isNotBlank()
		.isNotEmpty()
		.isEqualTo("18650309");		          
	}
	@Test
	public static void checkTravellerID() {
		Assertions.assertThat(DASResponseParsing.parseXMLResponseWithoutAttribute(doc, "TravelDocument", "Traveller"))
		.isNotBlank()
		.isNotEmpty()
		.isEqualTo("ID1");		          
	}

}
