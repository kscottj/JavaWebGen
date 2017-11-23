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
package org.javaWebGen.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 
/**
 * Simplistic configuration class.  Read config properties file from classpath.
 * Will read main + specific configuration for environment.   
 * Property conf=dev determines environment properties(conf-dev.properties) to load.
 * <ul>For example:<li>conf.properties</li>
 * <li>config-dev.propertes</li>
 * </ul>

 * @author kevin
 *
 */
public class Conf {
	
	public static final String CONFIG_FILE="config";
	//private long beginTime=0;
	//private long endTime=0;
	
 
	private static final Logger log = LoggerFactory.getLogger(Conf.class);
	/** current env ie PROD dev etc	 */
	public static final String ENV="env";
 
	//private Properties prop=null;
	private static Conf conf=null;
	private static int count=0;

	 
	/**
	 * simple property reader
	 * 
	 * main conf.perperties file tells what env specific Properties file to load from class path
	 * @return joined properties main plus env specific
	 */
	public static Properties getConfig(){
		
		return getConfig(CONFIG_FILE);
	}

	/**
	 * Simple property reader
	 * 
	 * main conf.perperties file tells what env specific Properties file to load from class path
	 * @param confName name of configuration file
	 * @return joined properties main plus env specific
	 */
	public static synchronized Properties getConfig(String confName){
		
		if(conf==null){
			conf=new Conf();
			//conf.beginTime=System.currentTimeMillis();
			//config.prop=config.getProp();
		}
			
		return conf.getProp(confName);
	}
	/**
	 * Get a property from classpath re-reads the property file everytime
	 * @TODO need to only cache this and only re-read every 10 sec 
	 * @param confName name of file name
	 * @return current props from classpath
	 */
	private Properties getProp(String confName){
			InputStream in=null;
			
				Properties mainProp= new Properties(System.getProperties());
				try {
					String confNameClass=confName+".properties";
					in =Conf.class.getClassLoader().getResourceAsStream(confNameClass);
					mainProp.load(in);
					
					log.debug("load "+confName+" from classpath size=" +mainProp.size() );
				} catch (Exception ge) {
					log.error("mainConfig not found in classPath="+ge.getMessage() );
				}
 				mainProp=new Properties(mainProp);
				String envProp=mainProp.getProperty(ENV );
				//endTime=System.currentTimeMillis();
				try{

					if(envProp!=null){
						String envConfName=confName+"-"+envProp+".properties";
					
						in=Conf.class.getClassLoader().getResourceAsStream(envConfName);
						
						
						mainProp.load(in );
						count=mainProp.size();
						 
						log.debug("load "+envConfName+" from classpath size=" +count );
					}	
				} catch (IOException e) {
					log.error("env Config not found in classPath" +e.getMessage() );
					return mainProp;
				}
		 
		
			return mainProp;
	}
	/**
	 * How many current configuration properties
	 * @return number of configuration properties
	 */
	public  synchronized int size() {
		return count;
	}
	
	

}
