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
