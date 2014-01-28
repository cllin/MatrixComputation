package com.cllin.matrixcomputation.benchmark;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.cllin.matrixcomputation.computation.RenderScriptComputation;
import com.cllin.matrixcomputation.data.BenchmarkResult;
import com.cllin.matrixcomputation.data.BenchmarkResult.Test;
import com.cllin.matrixcomputation.utility.MatrixFactory;
import com.cllin.matrixcomputation.utility.RecordWriter;
import com.cllin.matrixcomputation.utility.TimeRecorder;

public class MatrixComputationBenchmark {
	private static final String MSG_TAG = "MatrixComputationBenchmark";
	private static final String LIBRARY = "matrixfromnative";
	private static final String MSG_COMPUTAION_CORRECT = "The result from Native and Java is identical";
	private static final String MSG_COMPUTAION_INCORRECT = "Oops, something went wrong";
	
	
	private static final int FLAG_FULL_SCRIPT = 1;
	private static final int FLAG_SCRIPT_LITE = 2;
	private static final int FLAG_SHOW_OUTPUT = 10; 
	private static final int FLAG_SHOW_PROGRESS = 20;
	
	private int nTests = 1;
	private int[] mTestScript;
	private boolean[] mScript;
	
	private TimeRecorder mTimeRecorder;
	
	private Handler mHandler;
	private Context mContext;
	
	public MatrixComputationBenchmark(Handler handler, Context context, boolean[] script){
		mHandler = handler;
		mContext = context;
		mScript = script;
	}

	public void runBenchmarkTest(int flag) { 
		switch (flag) {
		case FLAG_FULL_SCRIPT:
			nTests = 1;
//			mTestScript = new int[]{500, 600, 700, 800, 900, 1000, 2000, 3000};
			mTestScript = new int[]{500, 600, 700, 800, 900, 1000, 2000};
			break;
		case FLAG_SCRIPT_LITE:
			nTests = 1;
			mTestScript = new int[]{100, 200, 300, 400, 500};
			break;
		}
		
		BenchmarkResult result = new BenchmarkResult(nTests);
		mTimeRecorder = new TimeRecorder(nTests);
		
		Message msg;
		
		mTimeRecorder.recordNow(TimeRecorder.KEY_ENTIRE_START);
		for (int i = 0; i < mTestScript.length; i++) {
			Log.d(MSG_TAG, "size=" + mTestScript[i]);

			int size = mTestScript[i];
			
			mTimeRecorder.recordNow(TimeRecorder.KEY_INITIALIZATION_START);
			float[][] a = MatrixFactory.getFloatMatrix(size, size);
			float[][] b = MatrixFactory.getFloatMatrix(size, size);
			mTimeRecorder.recordNow(TimeRecorder.KEY_INITIALIZATION_END);
			
			benchmarkTestNative(a, b);
			benchmarkTestJava(a, b);
			benchmarkTestOpenCV(a, b);
			benchmarkTestEigen(a, b);
			benchmarkTestRenderScript(a, b);
			
			result.addTest(result.new Test(size, 
					mTimeRecorder.getTime(TimeRecorder.FLAG_GET_INITIALIZE), 
					mTimeRecorder.getTime(TimeRecorder.FLAG_GET_JAVA), 
					mTimeRecorder.getTime(TimeRecorder.FLAG_GET_NATIVE), 
					mTimeRecorder.getTime(TimeRecorder.FLAG_GET_OPENCV), 
					mTimeRecorder.getTime(TimeRecorder.FLAG_GET_EIGEN),
					mTimeRecorder.getTime(TimeRecorder.FLAG_GET_RENDERSCRIPT)));
			
			msg = Message.obtain();
			msg.what = FLAG_SHOW_PROGRESS;
			msg.arg1 = i;
			msg.arg2 = mTestScript.length;
			msg.obj = size;
			mHandler.dispatchMessage(msg);
		}
		mTimeRecorder.recordNow(TimeRecorder.KEY_ENTIRE_END);
		result.setTotal(mTimeRecorder.getTime(TimeRecorder.FLAG_GET_ENTIRE));
		
		String output = "Nothing returned!";
		output = parseResult(result);
		msg = Message.obtain();
		msg = mHandler.obtainMessage(FLAG_SHOW_OUTPUT, output);
		mHandler.dispatchMessage(msg);
		
		RecordWriter recorder = new RecordWriter();
		recorder.record(result);
		
		Log.d(MSG_TAG, "Finished");
	}
	
	private void benchmarkTestNative(float[][] a, float[][] b){
		mTimeRecorder.recordNow(TimeRecorder.KEY_NATIVE_START);
//		TODO
//		try {
//			for (int i = 0; i < nTests; i++) matrixComputationByNative(a, b);
//		} catch (Exception e) {
//			e.printStackTrace();
//			Log.e(MSG_TAG, "Error happened when computing in C++");
//		}
		mTimeRecorder.recordNow(TimeRecorder.KEY_NATIVE_END);
	}
	
	private void benchmarkTestJava(float[][] a, float[][] b) {
		mTimeRecorder.recordNow(TimeRecorder.KEY_JAVA_START);
//		TODO
//		try {
//			JavaMatrixComputation computation = new JavaMatrixComputation();
//			for (int i = 0; i < nTests; i++) computation.matrixMultiplication(a, b);
//		} catch (Exception e) {
//			e.printStackTrace();
//			Log.e(MSG_TAG, "Error happened when computing in Java");
//		}
		mTimeRecorder.recordNow(TimeRecorder.KEY_JAVA_END);
	}
	
	/*
	 * TODO
	 * Note that the shared objects of OpenCV is stored under /tmp
	 * Since every build will delete these files, OpenCV is currently disabled 
	 */
	private void benchmarkTestOpenCV(float[][] a, float[][] b) {
		mTimeRecorder.recordNow(TimeRecorder.KEY_OPENCV_START);
		
//		try {
//			OpenCVMatrixComputation computation = new OpenCVMatrixComputation();
//			for (int i = 0; i < nTests; i++) computation.matrixMultiplication(a, b);
//		} catch (Exception e) {
//			e.printStackTrace();
//			Log.e(MSG_TAG, "Error happened when computing with OpenCV");
//		}
		
		mTimeRecorder.recordNow(TimeRecorder.KEY_OPENCV_END);
	}
	
	private void benchmarkTestEigen(float[][] a, float[][] b){
		mTimeRecorder.recordNow(TimeRecorder.KEY_EIGEN_START);
//		TODO
//		try {
//			EigenMatrixComputation computation = new EigenMatrixComputation();
//			for (int i = 0; i < nTests; i++) computation.matrixMultiplication(a, b);
//		} catch (Exception e) {
//			e.printStackTrace();
//			Log.e(MSG_TAG, "Error happened when computing with Eigen");
//		}
		
		mTimeRecorder.recordNow(TimeRecorder.KEY_EIGEN_END);	
	}
	
	private void benchmarkTestRenderScript(float[][] a, float[][] b){
		mTimeRecorder.recordNow(TimeRecorder.KEY_RENDERSCRIPT_START);	
		
		try {
			RenderScriptComputation computation = new RenderScriptComputation(mContext);
			for (int i = 0; i < nTests; i++) computation.matrixMultiplication(a, b);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(MSG_TAG, "Error happened when computing with RenderScript");
		}
		
		mTimeRecorder.recordNow(TimeRecorder.KEY_RENDERSCRIPT_END);	
	}
	
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
