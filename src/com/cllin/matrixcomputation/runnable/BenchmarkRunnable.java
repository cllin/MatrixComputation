package com.cllin.matrixcomputation.runnable;

import com.cllin.matrixcomputation.benchmark.MatrixComputationBenchmark;

import android.content.Context;
import android.os.Handler;

public class BenchmarkRunnable implements Runnable {
	private MatrixComputationBenchmark mBenchmark = null;
	private Handler mHandler = null;
	
	private int mBenchmarkFlag = FLAG_DEFAULT;
	
	private static final int FLAG_DEFAULT = 0;
	
	public BenchmarkRunnable(int flag, Handler handler, Context context, boolean[] script){
		mBenchmarkFlag = flag;
		mHandler = handler;
		mBenchmark = new MatrixComputationBenchmark(mHandler, context, script);
	}
	
	public void run(){
		mBenchmark.runBenchmarkTest(mBenchmarkFlag);
	}
}
