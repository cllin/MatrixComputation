package com.cllin.matrixcomputation.data;

import java.util.ArrayList;

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
	
	public class Test extends ArrayList<Long> {
		public static final int MATRIX_SIZE = 0;
		public static final int INITIALIZATION_CONSUMPTION = 1;
		public static final int JAVA_CONSUMPTION = 2;
		public static final int CPP_CONSUMPTION = 3;
		public static final int OPENCV_CONSUMPTION = 4;
		public static final int EIGEN_CONSUMPTION = 5;
		public static final int RENDERSCRIPT_CONSUMPTION = 6;
		
		public Test(int size, long init, long java, long cpp, long opencv, long eigen, long rs){
			this.add(Long.valueOf(size));
			this.add(init);
			this.add(java);
			this.add(cpp);
			this.add(opencv);
			this.add(eigen);
			this.add(rs);
		}
		
	    /**
	     * Get the value of matrix size or the time cost of a specific test
	     *
	     * @param key		key of the expected value, Test.matrixSize for the size, etc.
	     * @return			the expected value
	     * @exception		ArrayIndexOutOfBoundsException if the key is out of bound. 
	     * 					Currently the maximum value is 6, stands for renderscriptConsumption
	     */
		public long getValue(int key) throws ArrayIndexOutOfBoundsException {
			if (key > this.size()) throw new ArrayIndexOutOfBoundsException();
			
			return this.get(key);
		}
		
	    /**
	     * Get the matrix size of the test
	     *
	     * @return			the size of the matrix
	     */
		public int getMatrixSize(){
			return this.get(MATRIX_SIZE).intValue();
		}
		
	    /**
	     * Get the initialization time of the test
	     *
	     * @return			the initialization time of the matrix
	     */
		public long getInitialization(){
			return this.get(INITIALIZATION_CONSUMPTION);
		}
	}
}
