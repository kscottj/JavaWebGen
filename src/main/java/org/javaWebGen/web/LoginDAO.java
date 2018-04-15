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
package org.javaWebGen.web;

import java.sql.SQLException;

import org.javaWebGen.data.DbResult;
import org.javaWebGen.data.JdbcDao;
import org.javaWebGen.exception.DBException;

/**
 * TEST class.  Generic basic role based login.   Allows Role based security .
 * Expects the following tables to exist!  you can add extra columns but the following
 * DDL must exist
 * 
 * <PRE>
 * CREATE TABLE users(
 * 		user_name VARCHAR(15), 
 * 		user_pass VARCHAR(32),
 *);
 * 
 * CREATE TABLE user_roles(
 * 		user_name  VARCHAR(15),
 * 		role_name VARCHAR(15) 
 * );
 * 
 * </PRE>
 * 
 * @author kevin scott
 *
 */
 
@Deprecated
public class LoginDAO extends JdbcDao{

	public static final String USER_SQL=
		"select user_id, password, role_id from users where user_id =? AND password=?";
	public static final String ROLES_SQL=
		"select users.user_id, users.user_role  from users, user_role where user_role.user_id =? ";

	/**
	 * find login information about a user if it exist
	 * 
	 * @param userId
	 * @param pass
	 * @return LoginBean 
	 * @throws DBException could not find user id and password
	 */
	public LoginBean findLogin(String userId,String pass) throws DBException{

		Object parms[] = new Object[2];
		parms[0]=userId;
		parms[1]=pass;
		try{
			DbResult result=runQuery(USER_SQL,parms);

			LoginBean login= new LoginBean();
			if(result.size() >1 ){
				throw new DBException(DBException.WRONG_NUMBER_OF_ROWS);
			}else if(result.size()==0){
				login.setLogInStatus(false);
				throw new DBException(DBException.NOT_FOUND_ERROR);
			}
			//login.setUserId( (String) result.getByName(0, "user_id") );
			login.setPasswd( (String) result.getByName(0, "password") );
			//login.setUserRole(findRoles(login.getUserId() ) );
			login.setLogInStatus(true);  //found it in the DB
			//note I do not pass the password back to the caller
			//this is to resist the temptation of putting the password on the session

		}catch(SQLException se){
			throw new DBException(DBException.SQL_ERROR,"Could not find user_id and pass",se);
		}catch(Exception e){
			throw new DBException(DBException.GENERIC,e);
		}
		return new LoginBean();
	}
	/**
	 * finds all roles used by this user
	 * 
	 * @param userId
	 * @return list of roles
	 * @throws DBException
	 */
	public String[] findRoles(String userId ) throws DBException{
		Object parms[] = new Object[1];
		parms[0]=userId;
		try{
			DbResult result=runQuery(USER_SQL,parms);
			String[] roles= new String[result.size()];
			for(int i=0;i<roles.length ;i++){
				roles[i]=(String) result.getByName(i, "user_role");
			}
			return roles;
		}catch(SQLException se){
			throw new DBException(DBException.NOT_FOUND_ERROR,se);
		}

	}

}
