package com.cllin.matrixcomputation.utility;

import java.util.Hashtable;

public class TimeRecorder {
	private int nTests;
	private Hashtable<String, Long> mTimeRecord;
	
	public TimeRecorder(int tests){
		nTests = tests;
		mTimeRecord = new Hashtable<String, Long>();
	}
	
    /**
     * Set the value of a specific key to a specific value
     * 
     * @param key		one of TimeManager.KEY_INITIALIZATION_START, TimeManager.KEY_INITIALIZATION_END, etc.
     * @param value		the value to set with
     */
	public void recordTime(String key, long value) {
		mTimeRecord.put(key, value);
	}
	
    /**
     * Record current time and set it as the value of a specific key
     * 
     * @param key		one of TimeManager.KEY_INITIALIZATION_START, TimeManager.KEY_INITIALIZATION_END, etc.
     */
	public void recordNow(String key) {
		mTimeRecord.put(key, getCurrentTime());
	}
	
    /**
     * Get the time of all tests
     * 
     * @return			the record of all tests 
     */
	public Hashtable<String, Long> getAllRecords() {
		return mTimeRecord;
	}
	
    /**
     * Get the time to finish a specific test
     * 
     * @param flag		one of TimeManager.FLAG_GET_JAVA, TimeManager.FLAG_GET_NATIVE, TimeManager.FLAG_GET_OPENCV, etc.
     * @return			the time cost in milliseconds
     */
	public long getTime(String key) {
		long time = 0;
		
		time = mTimeRecord.get(key) / nTests;
		return time;
	}

    /**
     * Get current time in milliseconds
     *
     * @return			current time in milliseconds
     */
	public static long getCurrentTime() {
		return System.nanoTime() / 1000000;
	}
}
