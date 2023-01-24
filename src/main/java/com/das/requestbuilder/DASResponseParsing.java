package com.das.requestbuilder;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DASResponseParsing {

	String attrbutevalue;

	private DASResponseParsing() {
	} 

	public static String parseXMLResponseWithAttribute(Document document, String tagname, String attributename) {
		NodeList nlist = document.getElementsByTagName(tagname);
		List<Object> list = new ArrayList();
		for (int i = 0; i < nlist.getLength(); i++) {
			Node nnode = nlist.item(i);
			if (nnode.getNodeType() == Node.ELEMENT_NODE) {
				Element ele = (Element) nnode;
				String attrbutevalue = ele.getAttribute(attributename);
				list.add(attrbutevalue);
				return attrbutevalue;
			}
		}
		return null;
	}
	
	public static String parseXMLResponseWithoutAttribute(Document document, String tagname, String elenode) {
		NodeList nlist = document.getElementsByTagName(tagname);
		for (int i = 0; i < nlist.getLength(); i++) {
			Node nnode = nlist.item(i);
			if (nnode.getNodeType() == Node.ELEMENT_NODE) {
				Element ele = (Element) nnode;
				String attrbutevalue = ele.getElementsByTagName(elenode).item(0).getTextContent();
				return attrbutevalue;
			}
		}
		return null;
	}
	
	public static String parseXMLResponseWithIndex(Document document, String tagname, String attributename, int index) {
		Node node = document.getElementsByTagName(tagname).item(index);
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element ele = (Element) node;
			String attrbutevalue = ele.getAttribute(attributename);
			return attrbutevalue;
		}
		
		return null;
	}
}
