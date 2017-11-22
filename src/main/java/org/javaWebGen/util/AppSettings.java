package org.javaWebGen.util;

import java.util.Properties;

import org.javaWebGen.config.Conf;
import org.javaWebGen.config.ConfigConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @deprecated use @see org.javaWebGen.Conf
 * @author kevin
 *
 */
@Deprecated
public class AppSettings {
	
	private static AppSettings settings=null;
	private PropertiesReader messages;
	private Properties props=null;
	private static final Logger log = LoggerFactory.getLogger(AppSettings.class);
	
	private AppSettings(){
		
	}
	public synchronized static AppSettings getInstance(){
		if(settings==null){
			settings= new AppSettings();
			settings.init();
			
		}
		return settings;
	}
	
	public String getConfig(String key){
		return props.getProperty(key);
	}
	public String getConfig(String key,String defaultStr){
		return props.getProperty(key,defaultStr);
	}
	
	private void init(){
		try {
			if(props==null){
				props = Conf.getConfig();	
			}
			if(messages==null){
				messages = PropertiesReader.getReader(ConfigConst.MESSAGE);
			}		
		} catch (Exception e) {
			log.error("could not open config files", e);
		}
	}
}
