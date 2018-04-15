/*
 * =================================================================== *
 * Copyright (c) 2017 Kevin Scott All rights  reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in
 * the documentation and/or other materials provided with the
 * distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 * if any, must include the following acknowledgment:
 * "This product includes software developed by "Kevin Scott"
 * Alternately, this acknowledgment may appear in the software itself,
 * if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The name "Kevin Scott must not be used to endorse or promote products
 * derived from this software without prior written permission. For
 * written permission, please contact kevscott_tx@yahoo.com
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL KEVIN SCOTT BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */
package org.javaWebGen.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
 
import org.xml.sax.SAXException;

/**
 * Collection of handy XML DOM methods that need a home somewhere
 * @author kevin
 *
 */
public final class DOMHelper {
	/**valid string for a null to seperate from "null"*/
	public static String NULL= "[null]";
	private final static Logger log = LoggerFactory.getLogger(DOMHelper.class);

	/**
	 * Get an element from the document. If more than one is found only returns the first one
	 * @param doc XML document
	 * @param tagName name of tag you are searching for
	 * @return only one element
	 */
	public static Element getElement(Document doc,String tagName){
		if(doc==null||tagName==null){
			return null;
		}
		Element element=null;
		 
		NodeList nodeList=doc.getElementsByTagName(tagName);
		if( nodeList.getLength()==1){
			element= (Element) nodeList.item(0);
		}else if(nodeList.getLength()>1){
			element= (Element) nodeList.item(0);
			log.warn("got more than Elements this what you want?");
		 
		}
		//log.debug("getElmentfromDoc="+XmlHelper.makeXml(element) );
		return element;
		
		
	}
	/**
	 * Get an element from the document. If more than one is found only returns the first one.
	 * only returns Element objects. Ignores any non elements
	 * @param sourceElement DOM Element to search in
	 * @param tagName name of tag you are searching for
	 * @return only one element
	 */
	public static Element getElement(Element sourceElement,String tagName){
		if(sourceElement==null||tagName==null){
			log.info("returning null element="+sourceElement+" tag="+tagName);
			return null;
		}
		Element element=null;
		//log.debug("getElmentfromElement="+tagName );
		NodeList nodeList=sourceElement.getElementsByTagName(tagName);
		int count =0;
		if( nodeList.getLength()==1){
			Node node=nodeList.item(0);
			if(node.getNodeType()==Node.ELEMENT_NODE){
				element= (Element) node;
				count++;
			}
		}
		if(count>1){
			log.info("found "+count+" elements only returned the first one.  count="+count);
		}
		 
		//log.debug("getElment="+element );
		return element;
		
		
	}
	/**
	 * Get a List of elements from the element. Ignores any non elements
	 * @param sourceElement DOM Element to search in
	 * @param tagName name of tag you are searching for
	 * @return elements
	 */
	public static ArrayList<Element> getElements(Element sourceElement,String tagName){
		if(sourceElement==null||tagName==null){
			return null;
		}
		ArrayList<Element> elements=new ArrayList<Element>();
		NodeList nodeList=sourceElement.getElementsByTagName(tagName);
		if( nodeList!=null){
			
			for(int i=0;i<nodeList.getLength();i++){
				Node node= nodeList.item(i);
				if(node.getNodeType()==Node.ELEMENT_NODE){
					elements.add( (Element) nodeList.item(i) );
				}
			}
		}else{ //not found
			log.info("Element not found="+tagName);
			return null;
			
		}
		return elements;
	}
	
	/**
	 * Get a List of elements from the document. Ignores any non elements
	 * @param doc DOM to search in
	 * @param tagName name of tag you are searching for
	 * @return list of DOM elements
	 */
	public static ArrayList<Element> getElements(Document doc,String tagName){
		if(doc==null||tagName==null){
			log.debug("return null due [NULL] doc="+doc+"tag="+tagName);
			return null;
		}
		ArrayList<Element> elements=new ArrayList<Element>();
		NodeList nodeList=doc.getElementsByTagName(tagName);
		if( nodeList!=null){
			
			for(int i=0;i<nodeList.getLength();i++){
				Node node= nodeList.item(i);
				if(node.getNodeType()==Node.ELEMENT_NODE){
					elements.add( (Element) nodeList.item(i) );
				}
			}
		}else{ //not found
			log.info("element not found="+tagName);
			return null;
			
		}
		return elements;
		
	}
	/**
	 * Get attribute from DOM element
	 * @param element to search in
	 * @param name tag name to look for
	 * @return value
	 */
	public static String getAttribute(Element element,String name){
		return getAttributeAsString(element,name);
	
	}
	/**
	 * Get the attribute value handle null and not found gracefully
	 * @param element to search in
	 * @param name tag name to look for
	 * @return value
	 */
	public static String getAttributeAsString(Element element,String name){
		if (element==null||name==null){
			 
			return null;
		}
		//log.debug("getAttr:"+name);
		String value =element.getAttribute(name.trim()  );  //@TODO should I do the trim?
		if(value==null||value.isEmpty() ){
			log.debug("attribute not found:"+name);
		}
		//log.debug("value:"+value+":");
		
		return value;
	}

	/**
	 * Get the attribute value handle null and not found gracefully
	 * @param element to search in
	 * @param name tag name to look for
	 * @return element 0 if not found or not an int
	 */
	public static int getAttributeAsInt(Element element,String name){
		int value=0;
		if (element==null||name==null){
			return 0;
		}
		
		String valueStr =element.getAttribute(name);
		if(valueStr==null){
			log.info("attribute not found="+name );
			return 0;
		}
		
		try{
			
			value=Integer.parseInt(valueStr);
		}catch(NumberFormatException ne){
			log.warn("not a number="+ne.getMessage() );
			return 0;
		}
		return value;
 
	}
	/**
	 * Set the attribute value handle null and not found gracefully
	 * does not set empty attributes
	 * @param element DOM element
	 * @param attributeName DOM attribute to set
	 * @param attributeValue value to set
	 */
	public static void setAttribute(Element element, String attributeName, String attributeValue) {
		if(element!=null&&attributeName!=null&&attributeValue!=null&&!attributeValue.isEmpty()){
				element.setAttribute(attributeName, attributeValue.trim()); //@TODO should I do the trim?
		} 
	}
	/**
	 * Write to binary this can be used after a doc is singed. No transformation allowed
	 * @param doc DOM
	 * @return XML
	 */
	public static byte[] toBinaryXml(Document doc){
		if (doc==null){
			return null;
		}
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		
		try {
			transformer = tf.newTransformer();
			/*DocumentType doctype = doc.getDoctype();
	         if(doctype != null) {
	        	if (doctype.getPublicId()!=null){
	        		transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, doctype.getPublicId());
	        	}
	        	if (doctype.getSystemId()!=null){
	        		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
	        	}
	        }*/
			transformer.transform(new DOMSource(doc), new StreamResult(bo));
		} catch (TransformerException te) {
			log.warn(te.getMessage() );
			return null;
		}
		 
		return bo.toByteArray();
	}
 

	/**
	 * Read file parse to document
	 * @param fileName
	 * @return DOM 
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Document  readFromFile(String fileName) throws SAXException, IOException{
		log.debug("load file:"+fileName);
		if(fileName==null){
			return null;
		}
		File file = new File(fileName);
		return readFromFile(file);
 
	}
	/**
	 * Read file parse to document
	 * @param file to read into XML doc
	 * @return XML DOM document
	 * @throws SAXException parsing error
	 * @throws IOException IO error
	 */
	public static Document  readFromFile(File file) throws SAXException, IOException{
		log.debug("load file:"+file);
		if(file==null){
			return null;
		}
	 
		FileInputStream in= new FileInputStream(file);
	 

		return readFromInput(in); 
	}
	/**
	 * parse XML
	 * @param xml 
	 * @return DOM document
	 * @throws SAXException parse error
	 * @throws IOException IO error
	 */
	public static Document readFromString(String xml) throws SAXException, IOException
	{
		if(xml==null){
			log.warn("empty string passed");
			return null;
		}
		InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
		return readFromInput(is); 
		
		 
	}
	/**
	 * All this to validate a doc
	 * @param document DOM
	 * @throws IOException io error 
	 * @throws SAXException parse error
	 * @return true if it validates against DTD or XSD
	 */
	public static boolean validate(Document document,XmlErrorHandler errHandler) throws SAXException, IOException{
		if(document==null){
			return false;
		}
		String xml=makeString(document);
		ByteArrayInputStream in = new ByteArrayInputStream(xml.getBytes("UTF-8"));
	   try{
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setNamespaceAware(true);
			documentBuilderFactory.setValidating(true);
			documentBuilderFactory.setXIncludeAware(true);
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            documentBuilder.setEntityResolver( new ClasspathEntityResolver() );
            documentBuilder.setErrorHandler(errHandler) ;
            documentBuilder.parse(in);	
         	if(errHandler.getErrorCount()==0){
					return true;
				}else{
					
					log.warn("XML has errors count="+errHandler.getErrorCount());
					return false;
			}
        } catch (ParserConfigurationException pe) {
			log.error(pe.getMessage() );
		}finally{
			in.close();
		}
        return false;
	}
	/**
	 * parse input into document expanding and DTDs
	 * @param in
	 * @return DOM document
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Document readFromInput(InputStream in) throws SAXException, IOException
	{
		log.debug("load file:"+in);
		if(in==null){
			return null;
		}
		Document doc=null;
	 
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
		documentBuilderFactory.setValidating(true);
		documentBuilderFactory.setXIncludeAware(true);
		DocumentBuilder documentBuilder=null;

        try{
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            documentBuilder.setEntityResolver( new ClasspathEntityResolver() );
            documentBuilder.setErrorHandler(new XmlErrorHandler() );
            doc=documentBuilder.parse(in);
		} catch (ParserConfigurationException pe) {
			log.error(pe.getMessage() );
		}finally{
			in.close();
		}
		return doc; 
	}
	/**
	 * Create a blank document
	 * @return DOM document
	 */
	public static Document newDoc(){
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
		documentBuilderFactory.setXIncludeAware(true);
		//documentBuilderFactory.isValidating();
		
		DocumentBuilder documentBuilder=null;
		Document doc=null;
		try {
			documentBuilder=documentBuilderFactory.newDocumentBuilder();
			doc=documentBuilder.newDocument();
			
		} catch (ParserConfigurationException pe) {
			log.warn(pe.getMessage() );
		}
		return doc;
	}
	/**
	 * create a blank document with a DTD/schema assigned in a default namespace
	 * @param groupName name of root element
	 * @param dtdname DTD or XSD name 
	 * @return DOM document
	 */
	public static Document newDoc(String groupName,String dtdname){
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
		documentBuilderFactory.setXIncludeAware(true);
		Document doc=null;
		DocumentBuilder documentBuilder=null;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			 documentBuilder.setEntityResolver( new ClasspathEntityResolver() );
			 DocumentType doctype =	documentBuilder.getDOMImplementation().createDocumentType(groupName,"SYSTEM",dtdname) ;
			 doc= documentBuilder.getDOMImplementation().createDocument(null, groupName, doctype);
		} catch (ParserConfigurationException pe) {
			log.warn(pe.getMessage() );
		}
		return doc;
	}

	/*public static Document copyDocument(Document document,String name, String publicId, String systemId) {
		log.debug(">copyDoc n="+name+" p="+publicId+" s="+systemId);
		Document copiedDoc=null;
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
		documentBuilderFactory.setXIncludeAware(true);
		//documentBuilderFactory.setValidating(true);
		DocumentBuilder documentBuilder=null;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			 documentBuilder.setEntityResolver( new ClasspathEntityResolver() );
			//documentBuilder ;
 		    DocumentType origDoctype = document.getDoctype();
		    DocumentType doctype = documentBuilder.getDOMImplementation().createDocumentType(
		    		origDoctype.getName(), 
		    		origDoctype.getPublicId(),
		    		origDoctype.getSystemId());                        
		                                                    
		    copiedDoc = documentBuilder.getDOMImplementation().createDocument(null, origDoctype.getName(), doctype);
		    // so we already have the top element, and we have to handle the kids.
		    Element newDocElement = copiedDoc.getDocumentElement();
		    Element oldDocElement = document.getDocumentElement();
		    for (Node n = oldDocElement.getFirstChild(); n != null; n = n.getNextSibling()) {
		        Node newNode = copiedDoc.importNode(n, true);
		        newDocElement.appendChild(newNode);
		    }
		} catch (ParserConfigurationException pe) {
			log.warn(pe.getMessage() );
		}
		log.debug("<copyDoc c="+copiedDoc);
	    return copiedDoc;
	}*/
	/**
	 * turn an element into text
	 * @param element to convert to XML string
	 * @return XML text
	 */
	public static String toXml(Element element){
		StringWriter buffer = new StringWriter();
		if(element==null){
			return null;
		}
		try{
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.transform(new DOMSource(element),
			      new StreamResult(buffer));
		} catch (TransformerException te) {
			return null;
		}
		return buffer.toString();
	}

	/**
	 * Turn the DOM node into XML text
	 * <b>WARN</b> do not use this after a document is signed
	 * @param node to turn into sting
	 * @return XML text
	 */
	public static String toXml(Node node){
		StringWriter buffer = new StringWriter();
		if(node==null){
			return null;
		}
		try{
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.transform(new DOMSource(node),
			      new StreamResult(buffer));
		} catch (TransformerException te) {
			log.info(te.getMessage());
		}
		return buffer.toString();
	}

	/**
	 * Turn a doc into a string expanded DTD and enforces UTF-8.  This can be used with a signed DOM.
	 * However, it is to use be with the @see SignedDocument instead of a DOM class after signing. 
	 * @param doc DOM
	 * @return string version of the doc
	 * @throws UnsupportedEncodingException
	 */
	public static String makeString(Document doc) throws UnsupportedEncodingException{
		if(doc==null){
			log.warn("null document passed");
			return null;
		}
		ByteArrayOutputStream outputByteStream = new ByteArrayOutputStream();
		String xml=null;
		try {
			
	        TransformerFactory tf = TransformerFactory.newInstance();
	        Transformer transformer = tf.newTransformer();
	        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			DocumentType doctype = doc.getDoctype();
	         if(doctype != null) {
	        	if (doctype.getSystemId()!=null){
	        		//log.info("system doctype="+doctype.getSystemId());
	        		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
	        	}else if(doctype.getPublicId()!=null){
	        		transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, doctype.getPublicId());
	        	}

	        }

	        transformer.transform(new DOMSource(doc), new StreamResult(outputByteStream) );
	        xml=outputByteStream.toString("UTF-8");
	    } catch (Exception ex) {
	        throw new RuntimeException("Error converting to String", ex);
	    }
		 return xml;
	}
	/**
	 * Just turn the document into compact XML not CR LF or DTD
	 * <b>WARN</b> do not use this after a document is signed
	 * @param doc root Doc
	 * @return XML text
	 */
	public static String toXml(Document doc){
		if (doc==null){
			return null;
		}
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		StringWriter writer = new StringWriter();
		try {
			transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
		} catch (TransformerException te) {
			log.info(te.getMessage());
			return null;
		}
		String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
		return output;
	}
	/**
	 * Just turn the document into pretty XML(readable)
	 * <b>WARN</b> do not use this after a document is signed removes DTD
	 * @param doc DOM
	 * @return XML text
	 */
	public static String toPrettyXml(Document doc){
		if (doc==null){
			return NULL;
		}
		log.debug("makePrettyXml"+doc);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		StringWriter writer = new StringWriter();
		try {
			transformer = tf.newTransformer();
		 
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			//transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	        DocumentType doctype = doc.getDoctype();
	        if(doctype != null) {
	        	if (doctype.getPublicId()!=null){
	        		transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, doctype.getPublicId());
	        	}
	        	if (doctype.getSystemId()!=null){
	        		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
	        	}
	        }
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
		} catch (TransformerException te) {
			log.info(te.getMessage());
			return null;
		}
	 
		return writer.getBuffer().toString();
	}
 
 
	/**
	 * Write to binary this can be used after a doc is singed. No transformation allowed
	 * @param fileName File name
	 * @param doc DOM
	 * @throws IOException 
	 */
	public static void writeToFile(String fileName,Document doc ) throws IOException{
		if (fileName==null||doc==null){
			return ;
		}
		//FileOutputStream fo = new FileOutputStream( new File (fileName) );
		File file = new File(fileName);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		 
		try {
			transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.transform(new DOMSource(doc), new StreamResult(file));
		} catch (TransformerException te) {
			log.info(te.getMessage());
			 
		}
	}
	/**
	 * Writes pretty XML to a file. <b>WARNING</b> Do not use this with signed doc.  Just used to make output human readable
	 * @param fileName File name
	 * @param doc DOM
	 * @throws IOException IO error
	 */
	public static void writePrettyToFile(String fileName,Document doc ) throws IOException{
		if (fileName==null||doc==null){
			return ;
		}

		FileOutputStream fo=null;
		try{
			String xml =DOMHelper.toPrettyXml(doc);
			File file = new File(fileName);
			fo = new FileOutputStream(file);
			fo.write(xml.getBytes("UTF-8") );
		}finally{
			if(fo!=null){
				fo.flush();
				fo.close();
			}
		}
		 
		 
	}
	/**
	 * Find first child element.  Will only return elements and ignore any other types of nodes.99% of the time 
	 * this is the this is what you want.
	 * @param parent element
	 * @return single element. returns a null if nothing found
	 */
	public static Element findChild(Element parent){
		 
		if(parent==null){
			return null;
		}
		ArrayList<Element> list = new ArrayList<Element>();
		NodeList nodes= parent.getChildNodes();
		//log.debug("node="+nodes.getLength());
		for(int i=0;i<nodes.getLength();i++){
			Node node=nodes.item(i);
			if (node.getNodeType()==Node.ELEMENT_NODE ){
				list.add( (Element) node);
			}
			 
		}
		return list.get(0); //only return the first one
	}
	/**
	 * Find all child elements.  Will only return elements and ignore any other types of nodes.  99% of the time 
	 * this is the this is what you want.
	 * @param parent element
	 * @return list of elements. Returns an empty list if not found
	 */
	public static ArrayList<Element> findChildren(Element parent){
		if(parent==null){
			return null;
		}
		ArrayList<Element> list = new ArrayList<Element>();
		NodeList nodes= parent.getChildNodes();
		log.debug("node="+nodes.getLength());
		for(int i=0;i<nodes.getLength();i++){
			Node node=nodes.item(i);
			if (node.getNodeType()==Node.ELEMENT_NODE ){
				list.add( (Element) node);
			}
			 
		}
		return list;
	}
	/**
	 * write to file. <b>WARN</b> Do not use this with a signed document.
	 * @param fileName file name
	 * @param xml text
	 * @throws IOException
	 */
	public static void writeToFile(String fileName, String xml) throws IOException {
		FileOutputStream fo=null;
		try{
			 
			File file = new File(fileName);
			fo = new FileOutputStream(file);
			fo.write(xml.getBytes("UTF-8") );
		}finally{
			if(fo!=null){
				fo.flush();
				fo.close();
			}
		}
		
	}
 
}
