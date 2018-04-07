package org.javaWebGen.xml;

 
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 * return DTD or schema from JAVA class path
 * @author kevin
 *
 */
public class ClasspathEntityResolver implements EntityResolver  {
 
   
    private Logger log = LoggerFactory.getLogger(ClasspathEntityResolver.class);
    /**
     * return a resource from the classpath
     */
    @Override
	public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
		 
		       //log.debug("resolveEntity: publicId=" + publicId + ", systemId=" + systemId);
			   InputSource inputSource = null;
			 
		        try {
		            if (systemId==null|| systemId.isEmpty() ){
		            	log.warn("systemId="+systemId);
		                return null; // use the default behaviour
		            }
		            File localFile=new File(systemId);
		            String entityName=localFile.getName();
		            log.debug("find:"+entityName);
		            InputStream stream = getClass().getClassLoader().getResourceAsStream(entityName);
		         
		             if (stream == null) {
		            	 log.warn("not found:"+entityName);
		             } else {
		                 return new InputSource(stream);
		             }
		        } catch (Exception e) {
		            log.error("getting "+systemId ,e);
		        }
		        // If nothing found, null is returned, for normal processing
		        return inputSource;
    }
}
