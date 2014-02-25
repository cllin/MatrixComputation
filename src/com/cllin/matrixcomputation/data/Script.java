package com.cllin.matrixcomputation.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import android.annotation.SuppressLint;

@SuppressLint("UseSparseArrays")
public class Script {
	public HashMap<Integer, Boolean> matrixSize;
	public HashMap<String, Boolean> computationImplementation;
	
    /**
     * Generate a script for the computation
     *
     * @param sizes 			the size of the matrices that will be computed
     * @param implementations	the implementations that will be executed. 
     * 							Note that content should only be {JAVA, CPP, OPENCV, EIGEN, RENDERSCRIPT}
     */
	public Script(ArrayList<Integer> sizes, ArrayList<String> implementations) {
		if (sizes == null || implementations == null) return; 

		matrixSize = new HashMap<Integer, Boolean>();
		computationImplementation = new HashMap<String, Boolean>();
		
		Collections.sort(sizes);
		
		for (int size : sizes) matrixSize.put(size, true);
		for (String implementation : implementations) computationImplementation.put(implementation, true);
	}
}
