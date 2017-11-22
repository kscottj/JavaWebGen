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
package org.javaWebGen.util;

//import org.javaCan.util.DBConst;



import java.net.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

   /********************************
    * WARNING Experimintal code though it will work in theory
    * Listens for CACHE Objects that have been updated in the cluster
    * using a multcast socket
    * calls CacheManager to remove the object
    * @see org.javaWebGen.util.CacheManager#remove(Object,String)
    ******************************/
	@Deprecated
    public final class CacheListener extends Thread{
    	private static final Logger log=LoggerFactory.getLogger(CacheListener.class);
        private boolean running=true;
        private MulticastSocket socket=null;
        private InetAddress broadCastAddress =null;
        //private int broadCastPort =5555;
      
		private CacheManager manager=null;

        /*******************************************
        *@param sock reference to the Multicast Socket to listen on
        *@param address multicast address to listen on
        *@param man CacheManager instance
        *
        *******************************************/
		protected CacheListener(MulticastSocket sock,InetAddress address,CacheManager man){
            socket=sock;
            broadCastAddress=address;
            manager=man;
            //broadCastPort=port;
        }
        public void run(){
           DatagramPacket packet=null;
           try{
               //join socket group
               //endless listen loop
                while (running){
                   //Util.debug("looking for input on "+socket);
                   int buffSize=socket.getReceiveBufferSize() ;
                   byte[] buffer = new byte[buffSize];
                   packet = new DatagramPacket(buffer,buffSize);
                   socket.receive(packet);
                   socket.leaveGroup(broadCastAddress);
                   notifyManager(buffer); 
                   //Util.debug("CACHE|Refreshing object data"+StringUtil.covertBytes(buffer) );
                   yield();
                }
           /*}catch( InterruptedException ie ) {
                running=false;
                Util.info(" CAHCE|Cache Listener is stopping");
                socket.close();
                return;*/
            }catch(Exception e){
                e.printStackTrace();
                log.error("Error receiving packet"+packet,e);
            }
       }
       /******************************************
       *calls the cache manager after converting the
       *to proper object to call the CacheManager.remove(key,class)
       *@see CacheManager
       *@param buffer from socket
       *******************************************/
    
	private void notifyManager(byte[] buffer){
           String str = StringUtil.covertBytes(buffer);
           char delim= CacheManager.DELIM;
           int index = str.lastIndexOf(delim);
           String key = str.substring(0,index);
           String type = str.substring(index+1);
           Util.debug("cache KEY="+key);
           Util.debug("cache type ="+type);
           //ok now somehow call cache manager.refresh
           manager.remove(key,new String(type) ); 
       }
    } 