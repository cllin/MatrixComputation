package com.cllin.matrixcomputation.computation;


public class EigenMatrixComputation extends MatrixComputation {
	private static final String MSG_TAG = "EigenMatrixComputation";
	private static final String LIBRARY = "matrixfromeigen";
	
/*	TODO
 *  Note that since the Eigen part is currently not in progress, lines in Android.mk to build it is currently commented out
 */
	
	private native float[][] matrixComputationByEigen(float[][] floatsA, float[][] floatsB);
	static{
		System.loadLibrary(LIBRARY);
	};
	
	@Override
	public float[][] matrixMultiplication(float[][] a, float[][] b) throws Exception {
		if (!super.isValidMatrix(a, b)) throw new Exception("The matrices are invalid");
		
		matrixComputationByEigen(a, b);
		return null;
	}
}
