package com.das.poctest;

import java.io.Console;
import java.io.File;
import java.io.IOException;

import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.TextMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.das.requestbuilder.ConvertStringToDocument;
import com.das.requestbuilder.DASResponseParsing;
import com.das.requestbuilder.RequestBuilder;
import com.das.utils.FileReaderUtil;
import com.das.utils.PropertyFileUtil;
import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;

/**
 * This class is having example code for queue connection and send/receive message. 
 * This is not part of the framework and just used for demonstration purpose. 
 * Need to be deleted soon.
 * @author Mandeep.Sheoran
 */
public class QueueConnection {
	
	 static Document doc = null;
	 
	// System exit status value (assume unset value to be 1)
	private static int status = 1;
	private static final String HOST = "57.255.233.10";
	private static final int PORT = 1523;
	private static final String CHANNEL = "AEIN2_PNRDCS.SVRCONN";
	private static final String QMGR = "PNRDCS";
	private static final String APP_USER = "akumar8";
	private static final String APP_PASSWORD = "London@2023";
	private static final String DAS_INPUT_QUEUENAME = "AEIN2_PNRGOV_PNR_PUSH";
	private static final String DAS_OUTPUT_QUEUENAME = "AEIN2_DASTODB";

	public static void main(String[] args) {
		 
		 
		JMSContext context = null;
		Destination dasinputqueue = null;
		Destination dasoutputqueue = null;
		JMSProducer producer = null;
		JMSConsumer consumer = null;

		try {
			JmsFactoryFactory jmsff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
			JmsConnectionFactory confactory = jmsff.createConnectionFactory();

			confactory.setStringProperty(WMQConstants.WMQ_HOST_NAME, HOST);
			confactory.setIntProperty(WMQConstants.WMQ_PORT, PORT);
			confactory.setStringProperty(WMQConstants.WMQ_CHANNEL, CHANNEL);
			confactory.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
			confactory.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, QMGR);
			confactory.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, "DAS System");
			confactory.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
			confactory.setStringProperty(WMQConstants.USERID, APP_USER);
			confactory.setStringProperty(WMQConstants.PASSWORD, APP_PASSWORD);

			// Create JMS objects
			context = confactory.createContext();
			dasinputqueue = context.createQueue("queue:///" + DAS_INPUT_QUEUENAME);
			dasoutputqueue = context.createQueue("queue:///" + DAS_OUTPUT_QUEUENAME);

			String pnr = """
					      UNA:+.?*'
					UNB+IATA:1+BA+UAEPNRGOV+131116:0402+13111522024500+PNRGOV'
					UNG+PNRGOV+BA+UAEPNRGOV+131116:0402+13111522024500+IA+11:1'
					UNH+13111522024500+PNRGOV:11:1:IA+BA3401/060717/1745'
					MSG+:22'
					ORG+BA'
					TVL+160123:1640:160123:1745+DXB+LHR+BA+3401'
					EQN+2'
					SRC'
					RCI+BA:ONE:1:121113:044100'
					DAT+700:151113:2140'
					ORG+BA'
					TIF+KUMAR+LAL MS:A:1:1'
					SSR+DOCS:HK:1:BA:::::/P/PH/XX8898996/PH/12DEC67/F/04JAN15/KUMAR/LAL MS+::1'
					TVL+160123:1640:160123:1745+DXB+LHR+BA+3401'
					RPI+2+HK'
					APD+330'
					SRC'
					RCI+BA:TWO:1:121113:235800'
					DAT+700:151113:2200'
					ORG+BA'
					TIF+SUNDER+SHYAM MR:A:1'
					SSR+DOCS:HK:1:BA:::::/P/PH/EB8091899/PH/09DEC75/M/22FEB15/SUNDER/SHYAM MR+::1'
					TVL+160123:1640:160123:1745+DXB+LHR+BA+3401'
					RPI+3+HK'
					APD+330'
					UNT+824+13111522024500'
					UNE+1+13111522024500'
					UNZ+1+13111522024500'
					  """;
			
			String pnrmsg =FileReaderUtil.readXMLDataFromFile();
			String inputpnr = RequestBuilder.preparePNRRequest(pnrmsg);
			TextMessage message = context.createTextMessage(inputpnr);

			// Producer
			producer = context.createProducer();
			producer.send(dasinputqueue, message);
			System.out.println("Sent message:\n" + message);

			// Consumer
	//	consumer = context.createConsumer(dasoutputqueue);
	//		String receivedMessage = consumer.receiveBody(String.class, 15000);

			String receivedMessage = 
				""" 
						<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><DbData createdDateTime=\"2023-01-18T07:12:57\" sequenceNumber=\"0\"><JourneySegment routeOfInterest=\"ID0\" journeyReference=\"18650309\" segmentOrder=\"1\" noOfSegments=\"1\" journeyRouteIdentifier=\"DXB-LHR\" carrierType=\"Air\" carrierCode=\"BA\" serviceNumber=\"3401\" originPortCode=\"DXB\"><JourneyRoute ref=\"ID0\" reference=\"AIR-BA3401-DXB-20230116\" carrierCode=\"BA\" departurePortCode=\"DXB\" arrivalPortCode=\"LHR\" direction=\"Out\" status=\"Unknown\"><scheduled departureDateTime=\"2023-01-16T16:40:00\" arrivalDateTime=\"2023-01-16T17:45:00\" departureDateTimeWithTimeZone=\"2023-01-16T12:40:00+00:00\" arrivalDateTimeWithTimeZone=\"2023-01-16T17:45:00+00:00\"/></JourneyRoute><JourneyCodeShare carrierCode=\"BA\" serviceNumber=\"3401\" serviceType=\"Operating\"/></JourneySegment><RuleInfo ruleId=\"2\" bcsUserName=\"bcsUsername\"/><PNR pnrSequenceNumber=\"0\" bookingReferenceNumber=\"ONE\" airlineCode=\"BA\" pnrCancelledFlag=\"false\" pnrCreationDate=\"2013-11-12T00:00:00\" id=\"7200268\"><MasterTraveller ref=\"ID1\" pnrNameNumber=\"1\" pnrNameCount=\"1\" staffFlag=\"false\" surname=\"KUMAR\" forename=\"LAL\" infantFlag=\"false\" groupFlag=\"false\" vipFlag=\"false\"><Type type=\"Passenger\" passengerSubType=\"Adult\"/></MasterTraveller><MasterSegment xsi:type=\"ns3:AirSegment\" carrierCode=\"BA\" flightNumber=\"3401\" departureCity=\"DXB\" departureTime=\"16:40:00\" departureDate=\"2023-01-16\" arrivalCity=\"LHR\" arrivalTime=\"17:45:00\" arrivalDate=\"2023-01-16\" dateChangeFlag=\"false\" etktFlag=\"false\" openFlag=\"false\" aircraftType=\"330\" seatRequestAvailable=\"false\" nativeSegmentFlag=\"false\" airPartyCount=\"2\" ref=\"ID2\" segmentNo=\"1\" segmentUsed=\"false\" statusCode=\"HK - Confirmed\" rawStatusCode=\"HK\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:ns3=\"dbpnr\"/><Traveller>ID1</Traveller><Segment>ID2</Segment><PnrStatus type=\"Active\"/><TravelDocument><Traveller>ID1</Traveller><Document type=\"Passport\" number=\"XX8898996\" title=\"MS\" forename=\"LAL\" surname=\"KUMAR\" issueCountry=\"PHL\" nationality=\"PHL\" gender=\"Female\" expiryDate=\"2015-01-04\" dateOfBirth=\"1967-12-12\" holder=\"false\" infant=\"false\"/></TravelDocument><SsrDetail code=\"DOCS\" airline=\"BA\" status=\"HK - Confirmed\" noInParty=\"1\" text=\"/P/PH/XX8898996/PH/12DEC67/F/04JAN15/KUMAR/LAL MS\"><Traveller>ID1</Traveller><SsrInfo><TravelDocument><Document type=\"Passport\" number=\"XX8898996\" title=\"MS\" forename=\"LAL\" surname=\"KUMAR\" issueCountry=\"PHL\" nationality=\"PHL\" gender=\"Female\" expiryDate=\"2015-01-04\" dateOfBirth=\"1967-12-12\" holder=\"false\" infant=\"false\"/></TravelDocument></SsrInfo></SsrDetail><Responsibility><PointOfSale crsCode=\"BA\"/></Responsibility></PNR><PNR pnrSequenceNumber=\"0\" bookingReferenceNumber=\"TWO\" airlineCode=\"BA\" pnrCancelledFlag=\"false\" pnrCreationDate=\"2013-11-12T00:00:00\" id=\"7200269\"><MasterTraveller ref=\"ID3\" pnrNameNumber=\"1\" pnrNameCount=\"1\" staffFlag=\"false\" surname=\"SUNDER\" forename=\"SHYAM\" infantFlag=\"false\" groupFlag=\"false\" vipFlag=\"false\"><Type type=\"Passenger\" passengerSubType=\"Adult\"/></MasterTraveller><MasterSegment xsi:type=\"ns3:AirSegment\" carrierCode=\"BA\" flightNumber=\"3401\" departureCity=\"DXB\" departureTime=\"16:40:00\" departureDate=\"2023-01-16\" arrivalCity=\"LHR\" arrivalTime=\"17:45:00\" arrivalDate=\"2023-01-16\" dateChangeFlag=\"false\" etktFlag=\"false\" openFlag=\"false\" aircraftType=\"330\" seatRequestAvailable=\"false\" nativeSegmentFlag=\"false\" airPartyCount=\"3\" ref=\"ID4\" segmentNo=\"1\" segmentUsed=\"false\" statusCode=\"HK - Confirmed\" rawStatusCode=\"HK\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:ns3=\"dbpnr\"/><Traveller>ID3</Traveller><Segment>ID4</Segment><PnrStatus type=\"Active\"/><TravelDocument><Traveller>ID3</Traveller><Document type=\"Passport\" number=\"EB8091899\" title=\"MR\" forename=\"SHYAM\" surname=\"SUNDER\" issueCountry=\"PHL\" nationality=\"PHL\" gender=\"Male\" expiryDate=\"2015-02-22\" dateOfBirth=\"1975-12-09\" holder=\"false\" infant=\"false\"/></TravelDocument><SsrDetail code=\"DOCS\" airline=\"BA\" status=\"HK - Confirmed\" noInParty=\"1\" text=\"/P/PH/EB8091899/PH/09DEC75/M/22FEB15/SUNDER/SHYAM MR\"><Traveller>ID3</Traveller><SsrInfo><TravelDocument><Document type=\"Passport\" number=\"EB8091899\" title=\"MR\" forename=\"SHYAM\" surname=\"SUNDER\" issueCountry=\"PHL\" nationality=\"PHL\" gender=\"Male\" expiryDate=\"2015-02-22\" dateOfBirth=\"1975-12-09\" holder=\"false\" infant=\"false\"/></TravelDocument></SsrInfo></SsrDetail><Responsibility><PointOfSale crsCode=\"BA\"/></Responsibility></PNR><travellerCount type=\"Passenger\" count=\"2\"/><travellerCount type=\"Crew\" count=\"0\"/><travellerDataCount dataType=\"PNR\" type=\"Passenger\" count=\"2\"/></DbData>""";

		//	System.out.println("\nReceived message:\n" + receivedMessage.stripLeading().stripTrailing());
			Document doc = ConvertStringToDocument.convertToDocument(receivedMessage);

			String value = DASResponseParsing.parseXMLResponseWithAttribute(doc, "JourneySegment", "journeyReference");
			System.out.println("\nRoute of Interst value :\n" + value);
			context.close();

			recordSuccess();
		} catch (JMSException jmsex) {
			recordFailure(jmsex);
		}

		System.exit(status);
		
	} // end main()

	/**
	 * Record this run as successful.
	 */
	private static void recordSuccess() {
		System.out.println("Message is posted in Queue");
		status = 0;
		return;
	}

	/**
	 * Record this run as failure.
	 *
	 * @param ex
	 */
	private static void recordFailure(Exception ex) {
		if (ex != null) {
			if (ex instanceof JMSException) {
				processJMSException((JMSException) ex);
			} else {
				System.out.println(ex);
			}
		}
		System.out.println("FAILURE");
		status = -1;
		return;
	}

	/**
	 * Process a JMSException and any associated inner exceptions.
	 *
	 * @param jmsex
	 */
	private static void processJMSException(JMSException jmsex) {
		System.out.println(jmsex);
		Throwable innerException = jmsex.getLinkedException();
		if (innerException != null) {
			System.out.println("Inner exception(s):");
		}
		while (innerException != null) {
			System.out.println(innerException);
			innerException = innerException.getCause();
		}
		return;
	}

}
