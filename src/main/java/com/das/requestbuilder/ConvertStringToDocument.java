package com.das.requestbuilder;

import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;


public class ConvertStringToDocument {

	static Document doc;

	private ConvertStringToDocument() {
	}

	public static Document convertToDocument(String dasresponse) {
		DocumentBuilderFactory dbfctry = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder bldr = dbfctry.newDocumentBuilder();																													
			doc = bldr.parse(new InputSource(new StringReader(dasresponse)));
			doc.getDocumentElement().getNodeName();
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
