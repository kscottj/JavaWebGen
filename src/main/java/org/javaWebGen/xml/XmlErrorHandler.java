package org.javaWebGen.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
/**
 * XML error handler just logs and counts.  
 *  @author kevin
 *
 */
public class XmlErrorHandler implements ErrorHandler {


	private final static Logger log = LoggerFactory.getLogger(XmlErrorHandler.class);
		int errCount=0;
		@Override
		public void error(SAXParseException exception) throws SAXException {
			errCount++;
			 
			log.info("XML format error line#"+exception.getLineNumber()+":"+exception.getMessage() );
		}
		@Override
		public void fatalError(SAXParseException exception) throws SAXException {
			errCount++;
			log.info("XML format fatal line#"+exception.getLineNumber()+":"+exception.getMessage() );
			
		}
		@Override
		public void warning(SAXParseException exception) throws SAXException {
			errCount++;
			log.info("XML format error line#"+exception.getLineNumber()+":"+exception.getMessage() );
		}
		public int getErrorCount(){
			return errCount; 
		}

}
