/*
Copyright (c) 2003-2006 Kevin Scott

Permission is hereby granted, free of charge, to any person obtaining a copy of 
this software and associated documentation files (the "Software"), to deal in 
the Software without restriction, including without limitation the rights to 
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
of the Software, and to permit persons to whom the Software is furnished to do 
so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all 
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
SOFTWARE.
*/
/*
Copyright (c) 2003-2006 Kevin Scott

Permission is hereby granted, free of charge, to any person obtaining a copy of 
this software and associated documentation files (the "Software"), to deal in 
the Software without restriction, including without limitation the rights to 
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
of the Software, and to permit persons to whom the Software is furnished to do 
so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all 
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
SOFTWARE.
*/
package org.javaWebGen.data;

import org.javaWebGen.data.DbResult;
import org.javaWebGen.util.Util;

import java.sql.*;
/**
 * WARNING this class can be Dangerous.  It runs any SQL statements that are passed in
 * Be careful and think about security before using this class
 * If you allow users to enter dynamic SQL.  This class should be allowed on 
 * a production server that can be accessed from the Internet.   
 *
 */
@SuppressWarnings("deprecation")
public class ReportDAO extends JdbcDao{
	@Deprecated
public ReportDAO(){

}

	/******************************************************
	*returns an DBResult based on a database query
	*@param sql command
	*@return results of query including metadata
	*******************************************************/
	public DbResult runSQL(String sql) throws SQLException{
		Util.enter(this+"runSQL="+sql);
		DbResult result=runQuery(sql);

		Util.leave(this+"runSQL="+result);
		return result;
	}

	/******************************************************
	 * RUNS A sql update statement
	 * 
	*@param sql command
	*@return results of query including metadata
	*******************************************************/
	public long updateSQL(String sql) throws SQLException{
		return update(sql);

	}

}