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

  /********************************************
   * cleans up expired objects from the CacheManager
   * may have a threading issue!
   *@see CacheManager#cleanUp
   *********************************************/
 
	@Deprecated
   public final class CacheReaper extends Thread
   {
       private CacheManager manager;
       private long sleepTime;
        private boolean running=true;

       CacheReaper( CacheManager manager, long sleepTime ){
           this.manager = manager;
           this.sleepTime = sleepTime;
       }
       synchronized void reap() throws InterruptedException{
    	   wait( sleepTime );
    	   manager.cleanUp();
       }

       public void run(){
           Util.info("CACHE|Cache Reaper is running every "+sleepTime+" milliseconds");
           while( running ){
               try{
                   reap();
               }
               catch( InterruptedException e ){ 
            	   Util.warn("CACHE|CacheReaper is exiting");
                   return; //exit
               }
           }
       }
   } 
