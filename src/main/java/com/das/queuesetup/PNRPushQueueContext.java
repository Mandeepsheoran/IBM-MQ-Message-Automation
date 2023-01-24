package com.das.queuesetup;

import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.TextMessage;

import org.w3c.dom.Document;

import com.das.requestbuilder.RequestBuilder;
import com.das.utils.FileReaderUtil;
import com.das.utils.PropertyFileUtil;
import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;

public class PNRPushQueueContext {
	
	public static JMSContext context = null;
	public static JMSContext contextout = null;
	public static Destination dasinputqueue = null;
	public static Destination dasoutputqueue = null;
	public static JMSProducer producer = null;
	public static JMSConsumer consumer = null;
	public static Document doc = null;
	public static JmsConnectionFactory confactory = null;
	public static JmsConnectionFactory confactoryout = null;
	
	public static void createPNRInputQueueContext() {
		JmsFactoryFactory jmsin;
		try {
			jmsin = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
			confactory = jmsin.createConnectionFactory();

			confactory.setStringProperty(WMQConstants.WMQ_HOST_NAME, PropertyFileUtil.getValue("HOST"));
			confactory.setIntProperty(WMQConstants.WMQ_PORT, Integer.valueOf(PropertyFileUtil.getValue("PORT")));
			confactory.setStringProperty(WMQConstants.WMQ_CHANNEL, PropertyFileUtil.getValue("CHANNEL"));
			confactory.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
			confactory.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, PropertyFileUtil.getValue("QMGR"));
			confactory.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, "DAS System");
			confactory.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
			confactory.setStringProperty(WMQConstants.USERID, PropertyFileUtil.getValue("APP_USER"));
			confactory.setStringProperty(WMQConstants.PASSWORD, PropertyFileUtil.getValue("APP_PASSWORD"));

			context = confactory.createContext();
			dasinputqueue = context.createQueue("queue:///" + PropertyFileUtil.getValue("DAS_INPUT_QUEUENAME"));
			
			String pnrmsg = FileReaderUtil.readXMLDataFromFile();
			String inputpnr = RequestBuilder.preparePNRRequest(pnrmsg);
			System.out.println(inputpnr);
			TextMessage message = context.createTextMessage(inputpnr);
			producer = context.createProducer();
			producer.send(dasinputqueue, message);
	}  catch (JMSException e) {
		e.printStackTrace();
	}
	}
}
