package com.das.queuesetup;

import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.TextMessage;

import org.testng.annotations.BeforeSuite;
import org.w3c.dom.Document;

import com.das.requestbuilder.ConvertStringToDocument;
import com.das.requestbuilder.RequestBuilder;
import com.das.utils.FileReaderUtil;
import com.das.utils.PropertyFileUtil;
import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;

public class ConnectionFactory {

	public static JMSContext context = null;
	public static Destination dasinputqueue = null;
	public static Destination dasoutputqueue = null;
	public static JMSProducer producer = null;
	public static JMSConsumer consumer = null;
	public static Document doc = null;
	public static JmsConnectionFactory confactory = null;
	public static String receivedMessage;
	
	private static final String HOST = "57.255.233.10";
	private static final int PORT = 1523;
	private static final String CHANNEL = "AEIN2_PNRDCS.SVRCONN";
	private static final String QMGR = "PNRDCS";
	private static final String APP_USER = "akumar8";
	private static final String APP_PASSWORD = "London@2023";
	private static final String DAS_INPUT_QUEUENAME = "AEIN2_PNRGOV_PNR_PUSH";
	private static final String DAS_OUTPUT_QUEUENAME = "AEIN2_DASTODB";

	public static void setUpQueueManager() {
		JmsFactoryFactory jmsff;
		try {
			jmsff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
			confactory = jmsff.createConnectionFactory();

			confactory.setStringProperty(WMQConstants.WMQ_HOST_NAME, HOST);
			confactory.setIntProperty(WMQConstants.WMQ_PORT, PORT);
			confactory.setStringProperty(WMQConstants.WMQ_CHANNEL, CHANNEL);
			confactory.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
			confactory.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, QMGR);
			confactory.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, "DAS System");
			confactory.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
			confactory.setStringProperty(WMQConstants.USERID, APP_USER);
			confactory.setStringProperty(WMQConstants.PASSWORD, APP_PASSWORD);

			context = confactory.createContext();
			
			dasinputqueue = context.createQueue("queue:///" + DAS_INPUT_QUEUENAME);
			
			String pnrmsg =FileReaderUtil.readXMLDataFromFile();
			String inputpnr = RequestBuilder.preparePNRRequest(pnrmsg);
			TextMessage message = context.createTextMessage(inputpnr);
			
			producer = context.createProducer();	
			producer.send(dasinputqueue, message);
			
		//	consumer = context.createConsumer(dasoutputqueue);
		//	String receivedMessage = consumer.receiveBody(String.class, 15000);
			
			String receivedMessage = 
					""" 
							<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><DbData createdDateTime=\"2023-01-18T07:12:57\" sequenceNumber=\"0\"><JourneySegment routeOfInterest=\"ID0\" journeyReference=\"18650309\" segmentOrder=\"1\" noOfSegments=\"1\" journeyRouteIdentifier=\"DXB-LHR\" carrierType=\"Air\" carrierCode=\"BA\" serviceNumber=\"3401\" originPortCode=\"DXB\"><JourneyRoute ref=\"ID0\" reference=\"AIR-BA3401-DXB-20230116\" carrierCode=\"BA\" departurePortCode=\"DXB\" arrivalPortCode=\"LHR\" direction=\"Out\" status=\"Unknown\"><scheduled departureDateTime=\"2023-01-16T16:40:00\" arrivalDateTime=\"2023-01-16T17:45:00\" departureDateTimeWithTimeZone=\"2023-01-16T12:40:00+00:00\" arrivalDateTimeWithTimeZone=\"2023-01-16T17:45:00+00:00\"/></JourneyRoute><JourneyCodeShare carrierCode=\"BA\" serviceNumber=\"3401\" serviceType=\"Operating\"/></JourneySegment><RuleInfo ruleId=\"2\" bcsUserName=\"bcsUsername\"/><PNR pnrSequenceNumber=\"0\" bookingReferenceNumber=\"ONE\" airlineCode=\"BA\" pnrCancelledFlag=\"false\" pnrCreationDate=\"2013-11-12T00:00:00\" id=\"7200268\"><MasterTraveller ref=\"ID1\" pnrNameNumber=\"1\" pnrNameCount=\"1\" staffFlag=\"false\" surname=\"KUMAR\" forename=\"LAL\" infantFlag=\"false\" groupFlag=\"false\" vipFlag=\"false\"><Type type=\"Passenger\" passengerSubType=\"Adult\"/></MasterTraveller><MasterSegment xsi:type=\"ns3:AirSegment\" carrierCode=\"BA\" flightNumber=\"3401\" departureCity=\"DXB\" departureTime=\"16:40:00\" departureDate=\"2023-01-16\" arrivalCity=\"LHR\" arrivalTime=\"17:45:00\" arrivalDate=\"2023-01-16\" dateChangeFlag=\"false\" etktFlag=\"false\" openFlag=\"false\" aircraftType=\"330\" seatRequestAvailable=\"false\" nativeSegmentFlag=\"false\" airPartyCount=\"2\" ref=\"ID2\" segmentNo=\"1\" segmentUsed=\"false\" statusCode=\"HK - Confirmed\" rawStatusCode=\"HK\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:ns3=\"dbpnr\"/><Traveller>ID1</Traveller><Segment>ID2</Segment><PnrStatus type=\"Active\"/><TravelDocument><Traveller>ID1</Traveller><Document type=\"Passport\" number=\"XX8898996\" title=\"MS\" forename=\"LAL\" surname=\"KUMAR\" issueCountry=\"PHL\" nationality=\"PHL\" gender=\"Female\" expiryDate=\"2015-01-04\" dateOfBirth=\"1967-12-12\" holder=\"false\" infant=\"false\"/></TravelDocument><SsrDetail code=\"DOCS\" airline=\"BA\" status=\"HK - Confirmed\" noInParty=\"1\" text=\"/P/PH/XX8898996/PH/12DEC67/F/04JAN15/KUMAR/LAL MS\"><Traveller>ID1</Traveller><SsrInfo><TravelDocument><Document type=\"Passport\" number=\"XX8898996\" title=\"MS\" forename=\"LAL\" surname=\"KUMAR\" issueCountry=\"PHL\" nationality=\"PHL\" gender=\"Female\" expiryDate=\"2015-01-04\" dateOfBirth=\"1967-12-12\" holder=\"false\" infant=\"false\"/></TravelDocument></SsrInfo></SsrDetail><Responsibility><PointOfSale crsCode=\"BA\"/></Responsibility></PNR><PNR pnrSequenceNumber=\"0\" bookingReferenceNumber=\"TWO\" airlineCode=\"BA\" pnrCancelledFlag=\"false\" pnrCreationDate=\"2013-11-12T00:00:00\" id=\"7200269\"><MasterTraveller ref=\"ID3\" pnrNameNumber=\"1\" pnrNameCount=\"1\" staffFlag=\"false\" surname=\"SUNDER\" forename=\"SHYAM\" infantFlag=\"false\" groupFlag=\"false\" vipFlag=\"false\"><Type type=\"Passenger\" passengerSubType=\"Adult\"/></MasterTraveller><MasterSegment xsi:type=\"ns3:AirSegment\" carrierCode=\"BA\" flightNumber=\"3401\" departureCity=\"DXB\" departureTime=\"16:40:00\" departureDate=\"2023-01-16\" arrivalCity=\"LHR\" arrivalTime=\"17:45:00\" arrivalDate=\"2023-01-16\" dateChangeFlag=\"false\" etktFlag=\"false\" openFlag=\"false\" aircraftType=\"330\" seatRequestAvailable=\"false\" nativeSegmentFlag=\"false\" airPartyCount=\"3\" ref=\"ID4\" segmentNo=\"1\" segmentUsed=\"false\" statusCode=\"HK - Confirmed\" rawStatusCode=\"HK\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:ns3=\"dbpnr\"/><Traveller>ID3</Traveller><Segment>ID4</Segment><PnrStatus type=\"Active\"/><TravelDocument><Traveller>ID3</Traveller><Document type=\"Passport\" number=\"EB8091899\" title=\"MR\" forename=\"SHYAM\" surname=\"SUNDER\" issueCountry=\"PHL\" nationality=\"PHL\" gender=\"Male\" expiryDate=\"2015-02-22\" dateOfBirth=\"1975-12-09\" holder=\"false\" infant=\"false\"/></TravelDocument><SsrDetail code=\"DOCS\" airline=\"BA\" status=\"HK - Confirmed\" noInParty=\"1\" text=\"/P/PH/EB8091899/PH/09DEC75/M/22FEB15/SUNDER/SHYAM MR\"><Traveller>ID3</Traveller><SsrInfo><TravelDocument><Document type=\"Passport\" number=\"EB8091899\" title=\"MR\" forename=\"SHYAM\" surname=\"SUNDER\" issueCountry=\"PHL\" nationality=\"PHL\" gender=\"Male\" expiryDate=\"2015-02-22\" dateOfBirth=\"1975-12-09\" holder=\"false\" infant=\"false\"/></TravelDocument></SsrInfo></SsrDetail><Responsibility><PointOfSale crsCode=\"BA\"/></Responsibility></PNR><travellerCount type=\"Passenger\" count=\"2\"/><travellerCount type=\"Crew\" count=\"0\"/><travellerDataCount dataType=\"PNR\" type=\"Passenger\" count=\"2\"/></DbData>""";

			
	//		doc = ConvertStringToDocument.convertToDocument(receivedMessage);
			
		} catch (JMSException e) {
			recordFailure(e);
		}
	}
	
	private static void recordFailure(Exception ex) {
		if (ex != null) {
			if (ex instanceof JMSException) {
				processJMSException((JMSException) ex);
			} else {
				System.out.println(ex);
			}
		}
		System.out.println("FAILURE");
		return;
	}
	
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
