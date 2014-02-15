package com.cllin.matrixcomputation.computation;

import android.content.Context;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.Type;
import android.util.Log;

import com.cllin.matrixcomputation.computation.ScriptC_multiplication;;

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
		
		int rows, cols, size;
		long start, end;
		
		start = System.nanoTime();
		
//		TODO
//		The size of the matrices are assume to be the same
		rows = a.length;
		cols = a[0].length;
		size = rows * cols;
		
		Allocation mInputMatrixA = creatInputAllocation(mRS, a, rows, cols);
		Allocation mInputMatrixB = creatInputAllocation(mRS, b, rows, cols);
		Allocation mOutput = creatOutputAllocation(mRS, mInputMatrixA.getType());
		
		if (mInputMatrixA == null || mInputMatrixB == null) throw new Exception("Error occurs when creating allocation");
		
		mScript.set_rows(a.length);
		mScript.set_cols(b[0].length);
		
		mScript.set_gScript(mScript);
		mScript.set_gInputA(mInputMatrixA);
		mScript.set_gInputB(mInputMatrixB);
		mScript.bind_gOut(mOutput);
		
		end = (System.nanoTime() - start) / 1000000;
		Log.d(MSG_TAG, "It took " + end + " ms to copy the matrices");
		
		mScript.invoke_compute();

//		------------------
//		BOTTLENECK
//		------------------
		start = System.nanoTime();
		
		float[] buf = new float[size];
		mOutput.copyTo(buf);
		
		end = (System.nanoTime() - start) / 1000000;
		Log.d(MSG_TAG, "It took " + end + " ms to copy the matrices back to Java");
//		------------------
		
		float[][] output = new float[rows][cols];
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				try{
					output[i][j] = buf[i * rows + j];
				}catch(Exception e){
					Log.d(MSG_TAG, "i=" + i + ",j=" + j);
					return null;
				}
			}
		}
		
		return output;
	}
	
	private Allocation creatInputAllocation(RenderScript renderscript, 
			float[][] matrix, int rows, int cols) {
		
		float[] array = new float[rows * cols];
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				try {
					array[i * rows + j] = matrix[i][j];
				} catch(Exception e) {
					Log.d(MSG_TAG, "i=" + i + ",j=" + j);
					return null;
				}
			}
		}
		
		Allocation allocation = 
				Allocation.createSized(renderscript, Element.F32(renderscript), array.length, Allocation.USAGE_SCRIPT);
		allocation.copyFrom(array);
		
		return allocation;
	}
	
	private Allocation creatOutputAllocation(RenderScript renderscript, Type type) {
		return Allocation.createTyped(mRS, type);
	}
}
