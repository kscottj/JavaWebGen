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
package org.javaWebGen;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;

import org.javaWebGen.util.FileUtil;

/**
 * <p>Title: </p>
 * <p>Description: class that displays binary files setting mine type etc</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author Kevin Scott
 * @version 1.0
 */

public class ViewFile extends HttpServlet {


  /**
	 * 
	 */
	private static final long serialVersionUID = 4209910720020299750L;

public ViewFile() {
  }

  public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
   //String mimeType="application/octlet-stream";
   String filename=("test.doc");
   //String oid = req.getParameter("oid");
   res.setContentType(FileUtil.getMimeType(filename) );
   res.setHeader("Content-Disposition", "attachment;filename="+filename+";");

   OutputStream os=res.getOutputStream();
   InputStream is = new FileInputStream("/"+filename);
   int size = is.available();
   res.setContentLength(size);
   java.io.BufferedOutputStream out = new BufferedOutputStream(os);
   java.io.BufferedInputStream in = new BufferedInputStream(is);
   int temp = in.read();
   while(temp >=0){
     out.write(temp);
     temp=in.read();
   }
   in.close();
   out.flush();
   out.close();

   }
}
