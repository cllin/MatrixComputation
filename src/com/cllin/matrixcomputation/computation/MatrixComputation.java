package com.cllin.matrixcomputation.computation;

public class MatrixComputation implements SuperMatrixComputation {
	
	@Override
	public float[][] matrixMultiplication(float[][] a, float[][] b)	throws Exception {
		return null;
	}

	@Override
	public boolean isValidMatrix(float[][] a, float[][] b) {
		if (a.length < 1 || b.length < 1 || a[0].length < 1 || b[0].length < 1) return false;
		return true;
	}

	@Override
	public void finalizeMatrices() throws Throwable {
		finalize();
	}

}
