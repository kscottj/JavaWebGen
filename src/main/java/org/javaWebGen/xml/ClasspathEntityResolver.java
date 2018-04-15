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
