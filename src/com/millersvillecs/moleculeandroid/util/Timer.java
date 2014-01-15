package com.millersvillecs.moleculeandroid.util;

public class Timer {
   
   public long startTime, stopTime, totalTime;
   public boolean running;
   
   public Timer() {
       startTime = 0;
       stopTime = 0;
       totalTime = 0;
       running = false;
   }
   
   public void start() {
       if(!running) {
           running = true;
           startTime = System.currentTimeMillis();
       }
   }
   
   public void stop() {
       if(running) {
           running = false;
           stopTime = System.currentTimeMillis();
           totalTime += stopTime - startTime;
       }
   }
   
   public void reset() {
       long time = System.currentTimeMillis();
       startTime = time;
       stopTime = time;
       totalTime = 0;
   }
   
   public long getElapsedTimeMS() {
       if(running) {
           return System.currentTimeMillis() - startTime + totalTime;
       }

       return totalTime;
   }
   
   public long getElapsedTimeSec() {
       return (long)Math.floor(getElapsedTimeMS() / 1000);
   }
   
}