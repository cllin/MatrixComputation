package com.cllin.matrixcomputation.benchmark;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Set;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.cllin.matrixcomputation.activity.BenchmarkApplication;
import com.cllin.matrixcomputation.computation.EigenMatrixComputation;
import com.cllin.matrixcomputation.computation.JavaMatrixComputation;
import com.cllin.matrixcomputation.computation.RenderScriptComputation;
import com.cllin.matrixcomputation.data.BenchmarkResult;
import com.cllin.matrixcomputation.data.BenchmarkResult.Test;
import com.cllin.matrixcomputation.data.Script;
import com.cllin.matrixcomputation.utility.MatrixFactory;
import com.cllin.matrixcomputation.utility.RecordWriter;
import com.cllin.matrixcomputation.utility.TimeRecorder;

public class MatrixComputationBenchmark {
	private static final String MSG_TAG = "MatrixComputationBenchmark";
	private static final String LIBRARY = "matrixfromnative";
	private static final String MSG_COMPUTAION_CORRECT = "The result from Native and Java is identical";
	private static final String MSG_COMPUTAION_INCORRECT = "Oops, something went wrong";
	
	private int nTests = 1;
	private Script mScript; 
	
	private TimeRecorder mTimeRecorder;
	
	private Handler mHandler;
	private Context mContext;
	
	public MatrixComputationBenchmark(Handler handler, Context context, Script script){
		mHandler = handler;
		mContext = context;
		mScript = script;
	}

	public void runBenchmarkTest() {
		ArrayList<Integer> matrixSizes = getMatrixSizes(mScript.matrixSize.keySet());
		ArrayList<String> implementations = getImplementations(mScript.computationImplementation.keySet());
		
		BenchmarkResult result = new BenchmarkResult(nTests);
		
		long totalStart = TimeRecorder.getCurrentTime();
		
		int matrixSize;
		int scriptLength = matrixSizes.size();
		for (int i = 0; i < scriptLength; i++) {
			matrixSize = matrixSizes.get(i);
			mTimeRecorder = new TimeRecorder(nTests);
			Log.d(MSG_TAG, "size=" + matrixSize);
			
			long initializationStart = TimeRecorder.getCurrentTime();
			float[][] a = MatrixFactory.getFloatMatrix(matrixSize, matrixSize);
			float[][] b = MatrixFactory.getFloatMatrix(matrixSize, matrixSize);
			long initializationEnd = TimeRecorder.getCurrentTime();
			mTimeRecorder.recordTime(BenchmarkApplication.KEY_INITIALIZE, initializationEnd - initializationStart);
			
			if (implementations.contains(BenchmarkApplication.KEY_JAVA)) benchmarkTestJava(a, b);
			if (implementations.contains(BenchmarkApplication.KEY_CPP)) benchmarkTestNative(a, b);
			if (implementations.contains(BenchmarkApplication.KEY_OPENCV)) benchmarkTestOpenCV(a, b);
			if (implementations.contains(BenchmarkApplication.KEY_EIGEN)) benchmarkTestEigen(a, b);
			if (implementations.contains(BenchmarkApplication.KEY_RENDERSCRIPT)) benchmarkTestRenderScript(a, b);
			
			result.addTest(result.new Test(matrixSize, mTimeRecorder.getAllRecords()));
			
			updateProgressToUIThread(scriptLength, i, matrixSize);
		}
		long totalEnd = TimeRecorder.getCurrentTime();
		
		result.setTotal(totalEnd - totalStart);
		returnFinalResultToUIThread(result);
		
		Log.d(MSG_TAG, "Finished");
	}
	
	private void benchmarkTestNative(float[][] a, float[][] b){
		long start = TimeRecorder.getCurrentTime();
		
		try {
			for (int i = 0; i < nTests; i++) matrixComputationByNative(a, b);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(MSG_TAG, "Error happened when computing in C++");
		}
		
		long end = TimeRecorder.getCurrentTime();
		mTimeRecorder.recordTime(BenchmarkApplication.KEY_CPP, end - start);
	}
	
	private void benchmarkTestJava(float[][] a, float[][] b) {
		long start = TimeRecorder.getCurrentTime();
		
		try {
			JavaMatrixComputation computation = new JavaMatrixComputation();
			for (int i = 0; i < nTests; i++) computation.matrixMultiplication(a, b);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(MSG_TAG, "Error happened when computing in Java");
		}

		long end = TimeRecorder.getCurrentTime();
		mTimeRecorder.recordTime(BenchmarkApplication.KEY_JAVA, end - start);
	}
	
	/*
	 * TODO
	 * Note that the shared objects of OpenCV is stored under /tmp
	 * Since every build will delete these files, OpenCV is currently disabled 
	 */
	private void benchmarkTestOpenCV(float[][] a, float[][] b) {
		long start = TimeRecorder.getCurrentTime();
//		try {
//			OpenCVMatrixComputation computation = new OpenCVMatrixComputation();
//			for (int i = 0; i < nTests; i++) computation.matrixMultiplication(a, b);
//		} catch (Exception e) {
//			e.printStackTrace();
//			Log.e(MSG_TAG, "Error happened when computing with OpenCV");
//		}
		
		long end = TimeRecorder.getCurrentTime();
		mTimeRecorder.recordTime(BenchmarkApplication.KEY_OPENCV, end - start);
	}
	
	private void benchmarkTestEigen(float[][] a, float[][] b){
		long start = TimeRecorder.getCurrentTime();
		
		try {
			EigenMatrixComputation computation = new EigenMatrixComputation();
			for (int i = 0; i < nTests; i++) computation.matrixMultiplication(a, b);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(MSG_TAG, "Error happened when computing with Eigen");
		}
		
		long end = TimeRecorder.getCurrentTime();
		mTimeRecorder.recordTime(BenchmarkApplication.KEY_EIGEN, end - start);	
	}
	
	private void benchmarkTestRenderScript(float[][] a, float[][] b){
		long start = TimeRecorder.getCurrentTime();
		
		try {
			RenderScriptComputation computation = new RenderScriptComputation(mContext);
			for (int i = 0; i < nTests; i++) computation.matrixMultiplication(a, b);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(MSG_TAG, "Error happened when computing with RenderScript");
		}
		
		long end = TimeRecorder.getCurrentTime();
		mTimeRecorder.recordTime(BenchmarkApplication.KEY_RENDERSCRIPT, end - start);
	}
	
	private ArrayList<Integer> getMatrixSizes(Set<Integer> rawScript) {
		ArrayList<Integer> matrixSizes = new ArrayList<Integer>();
		for (Integer size : rawScript) matrixSizes.add(size);
		
		Collections.sort(matrixSizes);
		
		return matrixSizes;
	}
	
	private ArrayList<String> getImplementations(Set<String> rawScript) {
		ArrayList<String> implementations = new ArrayList<String>();
		for (String implementation : rawScript) implementations.add(implementation);
		
		Collections.sort(implementations);
		
		return implementations;
	}
	
	private boolean correctnessCheck(float[][] java, float[][] result) {
		if (result == null) {
			Log.e(MSG_TAG, "Oops, the result is null!");
			return false;
		}
		
		if (result.length == 0) {
			Log.e(MSG_TAG, "Oops, the length of the matrix is 0!");
			return false;
		}
		
		int cols = result.length;
		if (cols != result.length || cols == 0) {
			Log.e(MSG_TAG, "Oops, the number of columns of the matrix is incorrect!");
			return false;
		}
		
		int rows = result[0].length;
		if (rows != result[0].length || rows == 0) {
			Log.e(MSG_TAG, "Oops, the number of rows of the matrix is incorrect!");
			return false;
		}
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (result[i][j] != result[i][j]) {
					Log.e(MSG_TAG, "Oops, the result is incorrect!");
					return false;
				}
			}
		}
		
		Log.e(MSG_TAG, "The result is correct, great!");
		
		return true;
	}
	
	private String parseResult(BenchmarkResult result){
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(result.getTestNum());
		buffer.append(" times of matrix computation for each test.\n");
		
		for (int i = 0; i < result.size(); i++) {
			Test test = result.get(i);
			
			buffer.append("\n\nThe initialization of two ");
			buffer.append(test.getMatrixSize()).append(" by ").append(test.getMatrixSize());
			buffer.append(" matrices took ").append(test.getInitialization()).append(" ms");
			
			buffer.append("\nFor the matrix computation,");
			
			try {
				Hashtable<String, Long> table = test.getAllValues();
				for (String key : table.keySet()) {
					buffer.append("\n      " + key + " took ").append(table.get(key)).append(" ms");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		buffer.append("\n\nThe entire test took ").append(result.getTotal()).append(" ms");
		return buffer.toString();
	}
	
	private void updateProgressToUIThread(int numTests, int currentTest, int matrixSize) {
		Message msg = Message.obtain();
		msg.what = BenchmarkApplication.FLAG_SHOW_PROGRESS;
		msg.arg1 = currentTest;
		msg.arg2 = numTests;
		msg.obj = matrixSize;
		mHandler.dispatchMessage(msg);
	}
	
	private void returnFinalResultToUIThread(BenchmarkResult result) {
		String output = "Nothing returned!";
		Message msg = Message.obtain();
		
		output = parseResult(result);
		msg = mHandler.obtainMessage(BenchmarkApplication.FLAG_SHOW_OUTPUT, output);
		mHandler.dispatchMessage(msg);
	}
	
	private void writeRecordToCSV(BenchmarkResult result){
		RecordWriter recorder = new RecordWriter();
		recorder.record(result);
	}
	
	private native float[][] matrixComputationByNative(float[][] floatsA, float[][] floatsB);
	static{
		System.loadLibrary(LIBRARY);
	};
}
