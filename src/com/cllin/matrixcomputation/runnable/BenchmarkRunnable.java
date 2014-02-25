package com.cllin.matrixcomputation.runnable;

import android.content.Context;
import android.os.Handler;

import com.cllin.matrixcomputation.benchmark.MatrixComputationBenchmark;
import com.cllin.matrixcomputation.data.Script;

public class BenchmarkRunnable implements Runnable {
	private MatrixComputationBenchmark mBenchmark = null;
	private Handler mHandler = null;
	
	public BenchmarkRunnable(Handler handler, Context context, Script script){
		mHandler = handler;
		mBenchmark = new MatrixComputationBenchmark(mHandler, context, script);
	}
	
	public void run(){
		mBenchmark.runBenchmarkTest();
	}
}
