package com.cllin.matrixcomputation.computation;

public class JavaMatrixComputation extends MatrixComputation {

	@Override
	public float[][] matrixMultiplication(float[][] a, float[][] b) throws Exception {
		if (!super.isValidMatrix(a, b)) throw new Exception("The matrices are invalid");
		
		int row = a.length;
		int col = b[0].length;
		int p = b.length;
		float[][] result = new float[row][col];
		
		for (int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++){
				for(int k = 0; k < p; k++){
					result[i][j] += a[i][k] * b [k][j];
				}
			}
		}
		
		return result;
	}
}
