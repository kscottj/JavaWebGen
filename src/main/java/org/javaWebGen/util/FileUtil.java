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