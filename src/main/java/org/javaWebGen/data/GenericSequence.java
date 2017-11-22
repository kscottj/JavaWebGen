
//Copyright (c) 2003-2006 Kevin Scott

//Permission is hereby granted, free of charge, to any person obtaining a copy of 
//this software and associated documentation files (the "Software"), to deal in 
//the Software without restriction, including without limitation the rights to 
//use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
//of the Software, and to permit persons to whom the Software is furnished to do 
//so, subject to the following conditions:

//The above copyright notice and this permission notice shall be included in all 
//copies or substantial portions of the Software.

//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
//SOFTWARE.
package org.javaWebGen.data;

import java.sql.*;
import java.util.*;

import org.javaWebGen.config.DBConst;
import org.javaWebGen.data.DataManager;
import org.javaWebGen.data.Sequence;
import org.javaWebGen.exception.DBException;
import org.javaWebGen.exception.SqlAppException;


/********************************************
 * used to find next unique value(sequence) of a primary key
 *for a database using a special table called org_kev_index
 *The table will use the following DDL
 *
 *<PRE>
 *CREATE TABLE id_gen (
 * gen_key VARCHAR(20) NOT NULL,
 * gen_value INTEGER NOT NULL,
 * primary key (gen_key)
 *);
 *<PRE>
 **********************************************/
@SuppressWarnings("deprecation")
public final class GenericSequence extends JdbcDao implements Sequence{
	//private DataManager _manager = null;
	//private static Sequence _gen = null;

	public static final String GEN_KEY="gen_key";
	public static final String GEN_VALUE="gen_value";
	public static final String sql="select nextval from "+DBConst.NEXT_VALUE_TABLE +
	" where "+GEN_KEY+"=?";

	public static final String UPDATE_SQL="update "+DBConst.NEXT_VALUE_TABLE+
	" set "+GEN_VALUE+"=? where "+GEN_KEY+"=?";

	protected GenericSequence(DataManager dmanager)
	{


		super(dmanager);
		//Util.enter("GenericSequence("+dmanager+")");
		//Util.leave("GenericSequence("+dmanager+")");
	}
	/**
	 * 
	 */
	@Override
	public synchronized int getNextValue(String tableName) throws DBException{
		int value = 0;
		Connection con = null;
		try{
			con =getConnection();
			getDataManager().startTransaction(con);
			value=getNextValue(con,tableName);
			getDataManager().endTransaction(con);
		}catch(SQLException e){
			getDataManager().rollbackTransaction(con);
			throw new DBException(DBException.SEQUENCE_ERROR,this.toString(),e );
		}finally{
			getDataManager().close(con);
		}
		return value;
	}

	@Override
	/**
	 * 
	 */
	public synchronized int getNextValue(Connection con,String tableName) throws SQLException{

		int value =-1;

		Object[] parms={tableName};

		ArrayList <Object[]> list = query(con, sql,parms );
		if(list.size() != 1){
			throw new SqlAppException(SqlAppException.WRONG_NUMBER_OF_ROWS,"|SQL=|"+sql );
		}
		Object[] o= (Object[]) list.get(0);
		Integer index = (Integer) o[0];
		value = index.intValue();
		value++;

		parms=new Object[2];
		parms[0]=value;
		parms[1]=tableName;
		update(con,sql,parms);
		return value;

	}

}
