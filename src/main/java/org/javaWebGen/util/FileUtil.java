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
package org.javaWebGen.util;

/**
 * <p>Title: FileUtil</p>
 * <p>Description: File handling untility</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author Kevin Scott
 * @version $version: 1.0$
 */

public class FileUtil {
public static final String APPLICATION="application/octlet-stream";
public static final String IMAGE_JPEG="image/jpeg";
public static final String IMAGE_GIF="image/gif";
public static final String IMAGE_BMP="image/bmp";
public static final String IMAGE_PNG="image/png";
public static final String IMAGE_PIC="image/pic";
public static final String BMP="bmp";
public static final String JPEG="jpg";
public static final String GIF="gif";
public static final String PNG="png";
public static final String PIC="pic";

  public FileUtil() {
  }
  /**
   * Get the file extension of of a given file name
   * @param filename
   * @return extension
   */
  public static String getFileExt(String filename){


    int index = filename.indexOf(".");
    String ext = filename;
    while(index>=0){
      if (index >= 0 && index + 1 <= filename.length())
        ext=ext.substring(index + 1);
      index = ext.indexOf(".");
    }
    return ext;
  }
  /**
   * Return the mime type given a filename based on its extension
   * @param filename name
   * @return guessed mime type
   */
  public static String getMimeType(String filename){
    String ext = getFileExt(filename);
    if (ext.equals(BMP) )
      return IMAGE_BMP;
    else if(ext.equals(IMAGE_GIF) )
      return IMAGE_GIF;
    else if(ext.equals(IMAGE_JPEG) )
      return IMAGE_JPEG;
    else if(ext.equals(IMAGE_PNG) )
      return IMAGE_PNG;
    else
      return APPLICATION;
  }

}