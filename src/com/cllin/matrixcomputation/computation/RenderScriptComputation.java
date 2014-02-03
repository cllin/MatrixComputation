package com.cllin.matrixcomputation.computation;

import com.cllin.matrixcomputation.matrixcomputation.ScriptC_multiplication;

import android.content.Context;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.util.Log;

public class RenderScriptComputation extends MatrixComputation {
	private static final String MSG_TAG = "RenderScriptMatrixComputation";
	
    private RenderScript mRS;
    private ScriptC_multiplication mScript;
    
    public RenderScriptComputation(Context context){
    	mRS = RenderScript.create(context);
    	mScript = new ScriptC_multiplication(mRS);
    }
	
	@Override
	public float[][] matrixMultiplication(float[][] a, float[][] b)  throws Exception {
		if (!super.isValidMatrix(a, b)) throw new Exception("The matrices are invalid");
		
		long start = System.nanoTime();
		
		int rows, cols, size;
		float[] tmpA, tmpB;
		float[] input;
		
//		Input Vector
		rows = 1;
		cols = 400;
		size = rows * cols;
		input = new float[size];
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				try{
					input[i * rows + j] = a[i][j];
				}catch(Exception e){
					Log.d(MSG_TAG, "i=" + i + ",j=" + j);
					return null;
				}
			}
		}
		
//		Matrices
		rows = a.length;
		cols = a[0].length;
		size = rows * cols;
		
		tmpA = new float[size];
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				try{
					tmpA[i * rows + j] = a[i][j];
				}catch(Exception e){
					Log.d(MSG_TAG, "i=" + i + ",j=" + j);
					return null;
				}
			}
		}
		
		rows = b.length;
		cols = b[0].length;
		size = rows * cols;
		
		tmpB = new float[size];
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				try{
					tmpB[i * rows + j] = b[i][j];
				}catch(Exception e){
					Log.d(MSG_TAG, "i=" + i + ",j=" + j);
					return null;
				}
			}
		}
		
		Allocation mInputVector = Allocation.createSized(mRS, Element.F32(mRS), input.length, Allocation.USAGE_SCRIPT);
		Allocation mInputMatrixA = Allocation.createSized(mRS, Element.F32(mRS), tmpA.length, Allocation.USAGE_SCRIPT);
		Allocation mInputMatrixB = Allocation.createSized(mRS, Element.F32(mRS), tmpB.length, Allocation.USAGE_SCRIPT);
		mInputMatrixA.copyFrom(tmpA);
		mInputMatrixB.copyFrom(tmpB);
		
		mScript.set_rows(a.length);
		mScript.set_cols(b[0].length);
		mScript.set_gInput(mInputVector);
		mScript.set_gInputA(mInputMatrixA);
		mScript.set_gInputB(mInputMatrixB);
		mScript.set_gInputC(mInputMatrixB);
		mScript.set_gInputD(mInputMatrixB);
		mScript.set_gScript(mScript);
		
		long end = (System.nanoTime() - start) / 1000000;
		Log.d(MSG_TAG, "It took " + end + " ms to copy the matrices");
		
		mScript.invoke_compute();
		
//      TODO: COPY OUTPUT FROM ALLOCATION
		return null;
	}
}
