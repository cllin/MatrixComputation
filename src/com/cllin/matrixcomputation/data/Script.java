package com.cllin.matrixcomputation.data;

import java.util.ArrayList;
import java.util.Arrays;

public class Script {
	private Script script = null;
	
	public ArrayList<Integer> matrixSize;
	public ArrayList<Boolean> implementation;
	
	private Script(int[] size, boolean[] implement) {
		if (size.length < 1 || implement.length != 5) return; 
		
		int length;
		
		Arrays.sort(size);
		matrixSize = new ArrayList<Integer>();
		implementation = new ArrayList<Boolean>();
		
		length = size.length;
		for (int i = 0; i < length; i++) matrixSize.add(size[i]);
		
		length = implement.length;
		for (int i = 0; i < length; i++) implementation.add(implement[i]);
	}
	
    /**
     * Generate a script for the computation
     *
     * @param size 		matrix size
     * @param implement	whether an implementation will be executed or not. 
     * 					Note that the array should be in order of {JAVA, C++, OPENCV, EIGEN, RENDERSCRIPT}
     */
	public Script getScript(int[] size, boolean[] implement) {
		script = new Script(size, implement);
		return script;
	}
}
