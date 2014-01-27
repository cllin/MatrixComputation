package com.cllin.matrixcomputation.computation;

public interface SuperMatrixComputation {
	public float[][] matrixMultiplication(float[][] a, float[][] b) throws Exception;
	abstract boolean isValidMatrix(float[][] a, float[][] b);
	abstract void finalizeMatrices() throws Throwable;
}
