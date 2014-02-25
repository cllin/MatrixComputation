package com.cllin.matrixcomputation.data;

import java.util.ArrayList;
import java.util.Hashtable;

import com.cllin.matrixcomputation.activity.BenchmarkApplication;
import com.cllin.matrixcomputation.data.BenchmarkResult.Test;

@SuppressWarnings("serial")
public class BenchmarkResult extends ArrayList<Test>{
	private int nTests = 0;
	private long totalTime = 0;
	
	public BenchmarkResult(int tests) {
		nTests = tests;
	}
	
    /**
     * Get the number of tests
     * 
     * @return			number of performed tests
     */
	public int getTestNum() {
		return nTests;
	}
	
    /**
     * Get a specific test
     * 
     * @param index		index of the test
     * @return			the record of the test
     */
	public Test getTest(int index) {
		return this.get(index);
	}
	
    /**
     * Add a specific test to the list
     * 
     * @param test		the test to be added
     */
	public void addTest(Test test){
		this.add(test);
	}
	
    /**
     * Set the total time consumption
     * 
     * @param total		the total time consumption of all tests
     */
	public void setTotal(long total){
		totalTime = total;
	}
	
    /**
     * Get the total time consumption
     * 
     * @return			total time of all tests in milliseconds
     */
	public long getTotal(){
		return totalTime;
	}
	
	public class Test {
		private int matrixSize;
		private long initialization;
		private Hashtable<String, Long> mTimeRecord;
		
		public Test(int size, Hashtable<String, Long> record){
			this.matrixSize = size;
			this.initialization = record.get(BenchmarkApplication.KEY_INITIALIZE);
			
			record.remove(BenchmarkApplication.KEY_INITIALIZE);
			this.mTimeRecord = record;
		}
		
	    /**
	     * Get the value of the time cost of a specific test
	     *
	     * @param key		key of the expected value
	     * @return			the expected value
	     * @exception		Exception if the table does not contain a value for key
	     */
		public long getValue(String key) throws Exception {
			if (!mTimeRecord.containsKey(key)) throw new Exception("The record does not contain value relating to key: " + key);
			
			return mTimeRecord.get(key);
		}
		
	    /**
	     * Get the value of the time cost of all tests
	     *
	     * @return			a Hashtable that contains time cost of all tests
	     */
		public Hashtable<String, Long> getAllValues() {
			return mTimeRecord;
		}
		
	    /**
	     * Get the matrix size of the test
	     *
	     * @return			the size of the matrix
	     */
		public int getMatrixSize(){
			return this.matrixSize;
		}
		
	    /**
	     * Get the time cost of initialization
	     *
	     * @return			time cost of initialization
	     */
		public long getInitialization(){
			return this.initialization;
		}
	}
}
