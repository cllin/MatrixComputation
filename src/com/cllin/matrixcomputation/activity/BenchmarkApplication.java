package com.cllin.matrixcomputation.activity;

import android.app.Application;

public class BenchmarkApplication extends Application {
	
//	FLAGS
	public static final int FLAG_SHOW_OUTPUT = 10; 
	public static final int FLAG_SHOW_PROGRESS = 20;
	
//	KEYS
	public static final String KEY_INITIALIZE = "INITIALIZE";
	public static final String KEY_TOTAL = "TOTAL";
	public static final String KEY_JAVA = "JAVA";
	public static final String KEY_CPP = "CPP";
	public static final String KEY_EIGEN = "EIGEN";
	public static final String KEY_OPENCV = "OPENCV";
	public static final String KEY_RENDERSCRIPT = "RENDERSCRIPT";
}
