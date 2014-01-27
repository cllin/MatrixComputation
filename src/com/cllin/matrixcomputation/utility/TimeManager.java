package com.cllin.matrixcomputation.utility;

import java.util.Hashtable;

public class TimeManager {
	public static final String KEY_INITIALIZATION_START = "KEY_INITIALIZATION_START";
	public static final String KEY_INITIALIZATION_END = "KEY_INITIALIZATION_END";
	public static final String KEY_NATIVE_START = "KEY_NATIVE_START";
	public static final String KEY_NATIVE_END = "KEY_NATIVE_END";
	public static final String KEY_JAVA_START = "KEY_JAVA_START";
	public static final String KEY_JAVA_END = "KEY_JAVA_END";
	public static final String KEY_OPENCV_START = "KEY_OPENCV_START";
	public static final String KEY_OPENCV_END = "KEY_OPENCV_END";
	public static final String KEY_EIGEN_START = "KEY_EIGEN_START";
	public static final String KEY_EIGEN_END = "KEY_EIGEN_END";
	public static final String KEY_RENDERSCRIPT_START = "KEY_RENDERSCRIPT_START";
	public static final String KEY_RENDERSCRIPT_END = "KEY_RENDERSCRIPT_END";
	public static final String KEY_ENTIRE_START = "KEY_ENTIRE_START";
	public static final String KEY_ENTIRE_END = "KEY_ENTIRE_END";
	
	public static final int FLAG_GET_JAVA = 0;
	public static final int FLAG_GET_NATIVE = 1;
	public static final int FLAG_GET_OPENCV = 2;
	public static final int FLAG_GET_EIGEN = 3;
	public static final int FLAG_GET_RENDERSCRIPT = 4;
	public static final int FLAG_GET_INITIALIZE = 5;
	public static final int FLAG_GET_ENTIRE = 6;
	
	private int nTests;
	private Hashtable<String, Long> mTimeRecord;
	
	public TimeManager(int tests){
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
     * Get the time of a specific record
     * 
     * @param key		one of TimeManager.KEY_INITIALIZATION_START, TimeManager.KEY_INITIALIZATION_END, etc.
     * @return			currect time in milliseconds
     */
	public long getRecord(String key) {
		return mTimeRecord.get(key);
	}
	
    /**
     * Get the time to finish a specific test
     * 
     * @param flag		one of TimeManager.FLAG_GET_JAVA, TimeManager.FLAG_GET_NATIVE, TimeManager.FLAG_GET_OPENCV, etc.
     * @return			the time cost in milliseconds
     */
	public long getTime(int flag) {
		long time = 0;
		
		switch (flag) {
		case FLAG_GET_JAVA:
			time = (mTimeRecord.get(KEY_JAVA_END) - mTimeRecord.get(KEY_JAVA_START)) / nTests;  
			break;
		case FLAG_GET_NATIVE:
			time = (mTimeRecord.get(KEY_NATIVE_END) - mTimeRecord.get(KEY_NATIVE_START)) / nTests;
			break;
		case FLAG_GET_OPENCV:
			time = (mTimeRecord.get(KEY_OPENCV_END) - mTimeRecord.get(KEY_OPENCV_START)) / nTests;
			break;
		case FLAG_GET_EIGEN:
			time = (mTimeRecord.get(KEY_EIGEN_END) - mTimeRecord.get(KEY_EIGEN_START)) / nTests;
			break;
		case FLAG_GET_RENDERSCRIPT:
			time = (mTimeRecord.get(KEY_RENDERSCRIPT_END) - mTimeRecord.get(KEY_RENDERSCRIPT_START)) / nTests;
			break;
		case FLAG_GET_INITIALIZE:
			time = mTimeRecord.get(KEY_INITIALIZATION_END) - mTimeRecord.get(KEY_INITIALIZATION_START);
			break;
		case FLAG_GET_ENTIRE:
			time = mTimeRecord.get(KEY_ENTIRE_END) - mTimeRecord.get(KEY_ENTIRE_START);
			break;
		}
		return time;
	}

    /**
     * Get currect time in milliseconds
     *
     * @return			currect time in milliseconds
     */
	public long getCurrentTime() {
		return System.nanoTime() / 1000000;
	}
}
