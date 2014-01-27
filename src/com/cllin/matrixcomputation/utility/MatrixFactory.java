package com.cllin.matrixcomputation.utility;

import java.util.Random;

public class MatrixFactory {
    /**
     * Creates a matrix that contains random floats
     *
     * @param rows 		number of rows
     * @param cols 		number of columns
     * @return      	float[][] floats, while floats.length = rows, floats[0].length = cols 
     */
	public static float[][] getFloatMatrix(int rows, int cols) {
		float[][] floats = new float[rows][cols];

		float min = 0.0f;
		float max = 10.0f;
		Random rand = new Random();
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				floats[i][j] = rand.nextFloat() * max + min;
			}
		}
		
		return floats;
	}
	
    /**
     * Creates a vector that contains random floats
     *
     * @param size		size of the vector
     * @return			float[] floats, while floats.length = size
     */
	public static float[] getFloatVector(int size) {
		float[] floats = new float[size];
		
		float min = 0.0f;
		float max = 10.0f;
		Random rand = new Random();
		
		for (int i = 0; i < size; i++) floats[i] = rand.nextFloat() * max + min;
		
		return floats;
	}
	
    /**
     * Translate the float array into a string
     *
     * @param floats	the array to be translated
     * @return      	a string with elements from floats in the format of [1.0F 2.0F 3.0F 4.0F]
     */
	public static String getStringFromFloats(float[] floats) {
		int length = floats.length;
		if (length < 1) return new String();
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("[\n");
		for (int i = 0; i < length; i++) {
			buffer.append(Float.toString(floats[i]));
			buffer.append(" ");
		}
		
		buffer.append("]");
		
		return buffer.toString();
	}
	
    /**
     * Translate the float matrix into a string
     *
     * @param floats	the matrix to be translated
     * @return      	a string with elements from floats in the format of [1.0F 2.0F %n 3.0F 4.0F]
     */
	public static String getStringFromFloats(float[][] floats){
		int rows = floats.length;
		if (rows < 1) return new String();
		
		int cols = floats[0].length;
		StringBuffer buffer = new StringBuffer();

		buffer.append("[\n");
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				buffer.append(Float.toString(floats[i][j]));
				buffer.append(" ");
			}
			buffer.append("\n");
		}
		buffer.append("]");
		
		return buffer.toString();
	}
	
    /**
     * Check if two float matrices are identical
     *
     * @param a			the matrix to be checked
     * @param b			the matrix to be checked
     * @return      	true if the matrices are identical
     */
	public static boolean isIdentical(float[][] a, float[][] b){
		if (a.length == 0 || b.length == 0) return false;
		
		if (a.length != b.length) return false;
		if (a[0].length != b[0].length) return false;

		int rows = a.length;
		int cols = a[0].length;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (a[i][j] != b[i][j]) return false;
			}
		}
		return true;
	}
}
