package org.javaWebGen.data;


public abstract class JdoDao  implements DaoAware{
	

/*	*//**
	 * set by Spring IOC container
	 * @param pmf
	 *//*
	public void setPersistenceManagerFactory(PersistenceManagerFactory pmf){
		//log.info("JdoDao.setPersistenceManagerFactory()========"+pmf+" only called once by Spring!");
		this.pmf=pmf;
	}
	public PersistenceManagerFactory getPersistenceManagerFactory( ){
		return this.pmf;
	}
	
	public PersistenceManager getPersistenceManager(){
		if(this.pmf!=null){
			return this.pmf.getPersistenceManager();
		}else{
			log.error("!!!!!!!!!DAO object did not get inited by Container!!!!!!!"+this.pmf);
			
			return null;
		}
	}
	*//**
	 * shorthand for  getPersistenceManager
	 * @return
	 *//*
	public PersistenceManager getPM(){
		return getPersistenceManager();
	}*/
}
