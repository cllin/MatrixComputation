package com.cllin.matrixcomputation.benchmark;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.cllin.matrixcomputation.data.BenchmarkResult;
import com.cllin.matrixcomputation.data.BenchmarkResult.Test;
import com.cllin.matrixcomputation.utility.MatrixFactory;
import com.cllin.matrixcomputation.utility.Recorder;
import com.cllin.matrixcomputation.utility.TimeManager;

public class MatrixComputationBenchmark {
	private static final String MSG_TAG = "MatrixComputationBenchmark";
	private static final String LIBRARY = "matrixcomputation";
	private static final String MSG_COMPUTAION_CORRECT = "The result from Native and Java is identical";
	private static final String MSG_COMPUTAION_INCORRECT = "Oops, something went wrong";
	
	
	private static final int FLAG_FULL_SCRIPT = 1;
	private static final int FLAG_SCRIPT_LITE = 2;
	private static final int FLAG_SHOW_OUTPUT = 10; 
	private static final int FLAG_SHOW_PROGRESS = 20;
	
	private int nTests = 1;
	private int[] mTestScript;
	
	private TimeManager mTimeManager;
	
	private Handler mHandler;
	private Context mContext;
	
	public MatrixComputationBenchmark(Handler handler, Context context){
		mHandler = handler;
		mContext = context;
	}

	public void runBenchmarkTest(int flag){
		switch(flag){
		case FLAG_FULL_SCRIPT:
			nTests = 1;
			mTestScript = new int[]{500, 600, 700, 800, 900, 1000, 2000, 3000};
			break;
		case FLAG_SCRIPT_LITE:
			nTests = 1;
			mTestScript = new int[]{100, 200, 300, 400, 500};
			break;
		}
		
		BenchmarkResult result = new BenchmarkResult(nTests);
		mTimeManager = new TimeManager(nTests);
		
		Message msg;
		
		mTimeManager.recordNow(TimeManager.KEY_ENTIRE_START);
		for(int i = 0; i < mTestScript.length; i++){
			Log.d(MSG_TAG, "size=" + mTestScript[i]);
			int size = mTestScript[i];
			mTimeManager.recordNow(TimeManager.KEY_INITIALIZATION_START);
			float[][] a = MatrixFactory.getFloatMatrix(size, size);
			float[][] b = MatrixFactory.getFloatMatrix(size, size);
			mTimeManager.recordNow(TimeManager.KEY_INITIALIZATION_END);
			
//			TODO
//			benchmarkTestNative(a, b);
//			benchmarkTestJava(a, b);
//			benchmarkTestOpenCV(a, b);
//			benchmarkTestEigen(a, b);
//			benchmarkTestRenderScript(a, b);
			
			result.addTest(result.new Test(size, 
					mTimeManager.getTime(TimeManager.FLAG_GET_INITIALIZE), 
					mTimeManager.getTime(TimeManager.FLAG_GET_JAVA), 
					mTimeManager.getTime(TimeManager.FLAG_GET_NATIVE), 
					mTimeManager.getTime(TimeManager.FLAG_GET_OPENCV), 
					mTimeManager.getTime(TimeManager.FLAG_GET_EIGEN),
					mTimeManager.getTime(TimeManager.FLAG_GET_RENDERSCRIPT)));
			
			msg = Message.obtain();
			msg.what = FLAG_SHOW_PROGRESS;
			msg.arg1 = i;
			msg.arg2 = mTestScript.length;
			msg.obj = size;
			mHandler.dispatchMessage(msg);
		}
		mTimeManager.recordNow(TimeManager.KEY_ENTIRE_END);
		result.setTotal(mTimeManager.getTime(TimeManager.FLAG_GET_ENTIRE));
		
		String output = "Nothing returned!";
		output = parseResult(result);
		msg = Message.obtain();
		msg = mHandler.obtainMessage(FLAG_SHOW_OUTPUT, output);
		mHandler.dispatchMessage(msg);
		
		Recorder recorder = new Recorder();
		recorder.record(result);
		
		Log.d(MSG_TAG, "Finished");
	}
	
//	private void benchmarkTestNative(float[][] a, float[][] b){
//		mTimeManager.recordNow(TimeManager.KEY_NATIVE_START);
////		for(int i = 0; i < nTests; i++){
////			matrixComputationByNative(a, b);
////		}
//		mTimeManager.recordNow(TimeManager.KEY_NATIVE_END);
//	}
//	
//	private void benchmarkTestJava(float[][] a, float[][] b){
////		JavaMatrixComputation computation = new JavaMatrixComputation();
//		mTimeManager.recordNow(TimeManager.KEY_JAVA_START);
////		for(int i = 0; i < nTests; i++){
////			computation.matrixMultiplication(a, b);
////		}
//		mTimeManager.recordNow(TimeManager.KEY_JAVA_END);
//	}
//	
//	private void benchmarkTestOpenCV(float[][] a, float[][] b){
////		OpenCVMatrixComputation computation = new OpenCVMatrixComputation();
//		mTimeManager.recordNow(TimeManager.KEY_OPENCV_START);
////		for(int i = 0; i < nTests; i++){
////			computation.matrixMultiplication(a, b);
////		}
//		mTimeManager.recordNow(TimeManager.KEY_OPENCV_END);
//	}
//	
//	private void benchmarkTestEigen(float[][] a, float[][] b){
////		EigenMatrixComputation computation = new EigenMatrixComputation();
//		mTimeManager.recordNow(TimeManager.KEY_EIGEN_START);
////		for(int i = 0; i < nTests; i++){
////			computation.matrixMultiplication(a, b);
////		}
//		mTimeManager.recordNow(TimeManager.KEY_EIGEN_END);	
//	}
//	
//	private void benchmarkTestRenderScript(float[][] a, float[][] b){
//		RenderScriptComputation computation = new RenderScriptComputation(mContext);
//		mTimeManager.recordNow(TimeManager.KEY_RENDERSCRIPT_START);	
//		for(int i = 0; i < nTests; i++){
//			computation.matrixMultiplication(a, b);
//		}
//		mTimeManager.recordNow(TimeManager.KEY_RENDERSCRIPT_END);	
//	}
	
////	The method verifies the correctness of the computation by comparing the result from native and Java.
////	If they are identical, take the result as correct; if not, wrong.	
//	public String runCorrectnessTest(){
//		String output;
//		
//		int x = 2;
//		int y = 2;
//		float[][] a = mMatrixFactory.getFloatMatrix(x, y);
//		float[][] b = mMatrixFactory.getFloatMatrix(x, y);
//
//		JavaMatrixComputation computation = new JavaMatrixComputation();
//		float[][] resultFromJava = computation.matrixMultiplication(a, b);
//		float[][] resultFromNative = matrixComputationByNative(a, b);
//		if(!mMatrixFactory.isIdentical(resultFromNative, resultFromJava)){
//			output = MSG_COMPUTAION_INCORRECT;
//		}else{
//			output = MSG_COMPUTAION_CORRECT;
//			output += ", the result is\n";
//			output += mMatrixFactory.getStringFromFloats(resultFromNative);
//		}
//
//		return output;
//	}
//	
	private String parseResult(BenchmarkResult result){
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(result.getTestNum());
		buffer.append(" times of matrix computation for each test.\n");
		
		for (int i = 0; i < result.size(); i++) {
			Test t = result.get(i);
			
			buffer.append("\nThe initialization of two ");
			buffer.append(t.getMatrixSize()).append(" by ").append(t.getMatrixSize());
			buffer.append(" matrices took ").append(t.getInitialization()).append(" ms");
			
			buffer.append("\nFor the matrix computation, ");
			buffer.append("\nC++ took ").append(t.getValue(Test.CPP_CONSUMPTION));
			buffer.append(" ms, Java took ").append(t.getValue(Test.JAVA_CONSUMPTION));
			buffer.append(" ms, OpenCV took ").append(t.getValue(Test.OPENCV_CONSUMPTION));
			buffer.append(" ms, Eigen took ").append(t.getValue(Test.EIGEN_CONSUMPTION));
			buffer.append(" ms, RenderScript took ").append(t.getValue(Test.RENDERSCRIPT_CONSUMPTION));
			
			buffer.append(" ms\n");
		}
		
		buffer.append("\n\nThe entire test took ").append(result.getTotal()).append(" ms");
		return buffer.toString();
	}
	
	private native float[][] matrixComputationByNative(float[][] floatsA, float[][] floatsB);
	static{
		System.loadLibrary(LIBRARY);
	};
}
