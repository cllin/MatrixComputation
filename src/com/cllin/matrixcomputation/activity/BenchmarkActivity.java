package com.cllin.matrixcomputation.activity;

import java.util.ArrayList;
import java.util.Hashtable;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cllin.matrixcomputation.R;
import com.cllin.matrixcomputation.data.Script;
import com.cllin.matrixcomputation.runnable.BenchmarkRunnable;

public class BenchmarkActivity extends Activity implements OnClickListener {
	private static final String MSG_TAG = "MatrixComputationActivity";
	
//	VIEWS
	private ProgressBar mProgressBar = null;
	private TextView mOutputTextView = null;
	private Button mRunScriptButton = null;
	private CheckBox mComputeInJavaCheckBox = null;
	private CheckBox mComputeInCppCheckBox = null;
	private CheckBox mComputeInOpenCVCheckBox = null;
	private CheckBox mComputeInEigenCheckBox = null;
	private CheckBox mComputeInRenderScriptCheckBox = null;
	private CheckBox m100SizeCheckBox = null;
	private CheckBox m200SizeCheckBox = null;
	private CheckBox m300SizeCheckBox = null;
	private CheckBox m400SizeCheckBox = null;
	private CheckBox m500SizeCheckBox = null;
	private CheckBox m600SizeCheckBox = null;
	private CheckBox m700SizeCheckBox = null;
	private CheckBox m800SizeCheckBox = null;
	private CheckBox m900SizeCheckBox = null;
	private CheckBox m1000SizeCheckBox = null;
	private CheckBox m1500SizeCheckBox = null;
	private CheckBox m2000SizeCheckBox = null;
	private CheckBox m3000SizeCheckBox = null;
	
	private Hashtable<Integer, CheckBox> mSizeCheckBoxes;
	private Hashtable<String, CheckBox> mImplementationCheckBoxes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(MSG_TAG, "onCreate");
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_benchmark);
		setView();
	}
	
	private void setView(){
		mRunScriptButton = (Button)findViewById(R.id.button_matrixcomputation_run);
		mProgressBar = (ProgressBar)findViewById(R.id.progressbar_progress);
		mOutputTextView = (TextView)findViewById(R.id.textview_matrixcomputation_output);
		mComputeInJavaCheckBox = (CheckBox)findViewById(R.id.checkbox_matrixcomputation_java);
		mComputeInCppCheckBox = (CheckBox)findViewById(R.id.checkbox_matrixcomputation_cpp);
		mComputeInOpenCVCheckBox = (CheckBox)findViewById(R.id.checkbox_matrixcomputation_opencv);
		mComputeInEigenCheckBox = (CheckBox)findViewById(R.id.checkbox_matrixcomputation_eigen);
		mComputeInRenderScriptCheckBox = (CheckBox)findViewById(R.id.checkbox_matrixcomputation_renderscript);
		m100SizeCheckBox = (CheckBox)findViewById(R.id.checkbox_matrixcomputation_100);
		m200SizeCheckBox = (CheckBox)findViewById(R.id.checkbox_matrixcomputation_200);
		m300SizeCheckBox = (CheckBox)findViewById(R.id.checkbox_matrixcomputation_300);
		m400SizeCheckBox = (CheckBox)findViewById(R.id.checkbox_matrixcomputation_400);
		m500SizeCheckBox = (CheckBox)findViewById(R.id.checkbox_matrixcomputation_500);
		m600SizeCheckBox = (CheckBox)findViewById(R.id.checkbox_matrixcomputation_600);
		m700SizeCheckBox = (CheckBox)findViewById(R.id.checkbox_matrixcomputation_700);
		m800SizeCheckBox = (CheckBox)findViewById(R.id.checkbox_matrixcomputation_800);
		m900SizeCheckBox = (CheckBox)findViewById(R.id.checkbox_matrixcomputation_900);
		m1000SizeCheckBox = (CheckBox)findViewById(R.id.checkbox_matrixcomputation_1000);
		m1500SizeCheckBox = (CheckBox)findViewById(R.id.checkbox_matrixcomputation_1500);
		m2000SizeCheckBox = (CheckBox)findViewById(R.id.checkbox_matrixcomputation_2000);
		m3000SizeCheckBox = (CheckBox)findViewById(R.id.checkbox_matrixcomputation_3000);
		
		mRunScriptButton.setOnClickListener(this);
		
		mSizeCheckBoxes = new Hashtable<Integer, CheckBox>();
		mImplementationCheckBoxes = new Hashtable<String, CheckBox>();
		
		mImplementationCheckBoxes.put(BenchmarkApplication.KEY_JAVA, mComputeInJavaCheckBox);
		mImplementationCheckBoxes.put(BenchmarkApplication.KEY_CPP, mComputeInCppCheckBox);
		mImplementationCheckBoxes.put(BenchmarkApplication.KEY_OPENCV, mComputeInOpenCVCheckBox);
		mImplementationCheckBoxes.put(BenchmarkApplication.KEY_EIGEN, mComputeInEigenCheckBox);
		mImplementationCheckBoxes.put(BenchmarkApplication.KEY_RENDERSCRIPT, mComputeInRenderScriptCheckBox);
		
		mSizeCheckBoxes.put(100, m100SizeCheckBox);
		mSizeCheckBoxes.put(200, m200SizeCheckBox);
		mSizeCheckBoxes.put(300, m300SizeCheckBox);
		mSizeCheckBoxes.put(400, m400SizeCheckBox);
		mSizeCheckBoxes.put(500, m500SizeCheckBox);
		mSizeCheckBoxes.put(600, m600SizeCheckBox);
		mSizeCheckBoxes.put(700, m700SizeCheckBox);
		mSizeCheckBoxes.put(800, m800SizeCheckBox);
		mSizeCheckBoxes.put(900, m900SizeCheckBox);
		mSizeCheckBoxes.put(1000, m1000SizeCheckBox);
		mSizeCheckBoxes.put(1500, m1500SizeCheckBox);
		mSizeCheckBoxes.put(2000, m2000SizeCheckBox);
		mSizeCheckBoxes.put(3000, m3000SizeCheckBox);

		for (String key : mImplementationCheckBoxes.keySet()) {
			mImplementationCheckBoxes.get(key).setOnClickListener(this);
		}
		
		for (int key : mSizeCheckBoxes.keySet()) {
			mSizeCheckBoxes.get(key).setOnClickListener(this);
		}
		
		mProgressBar.setMax(100);
	}
	
	private void setTextView(String str){
		mOutputTextView.setText(str);
	}
	
	private void setProgressBar(Message msg){
		int order = msg.arg1 + 1;
		int total = msg.arg2;
		int size = (Integer) msg.obj;
		mProgressBar.setProgress((100 * order/total));
		setTextView(size + "*" + size + " matrix multiplication, " + order + " of " + total + " tests");
	}
	
	@Override
	public void onClick(View v) {
		mProgressBar.setProgress(0);
		BenchmarkRunnable runnable = null;
		
		switch(v.getId()){
		case R.id.button_matrixcomputation_run:
			runnable = new BenchmarkRunnable(mHandler, this.getBaseContext(), createScript());
			v.setPressed(false);
			runThread(runnable);
			break;
			default:
				break;
		}
	}
	
	private Script createScript() {
		ArrayList<Integer> sizes = new ArrayList<Integer>();
		ArrayList<String> implementations = new ArrayList<String>();
		
		for (int key : mSizeCheckBoxes.keySet()) {
			if (mSizeCheckBoxes.get(key).isChecked()) sizes.add(key);
		}
		
		for (String key : mImplementationCheckBoxes.keySet()) {
			if (mImplementationCheckBoxes.get(key).isChecked()) implementations.add(key);
		}
		
		return new Script(sizes, implementations);
	}
	
	private void runThread(BenchmarkRunnable runnable) {
		Thread thread = new Thread(runnable);
		thread.start();
		
		thread = null;
	}
	
    private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(final Message msg) {
			switch(msg.what){
				case BenchmarkApplication.FLAG_SHOW_OUTPUT:
					this.post(new Runnable() {
						@Override
						public void run() {
							setTextView(msg.obj.toString());
						}
					});
					break;
				case BenchmarkApplication.FLAG_SHOW_PROGRESS:
					mProgressBar.post(new Runnable(){
						@Override
						public void run() {
							setProgressBar(msg);
						}
					});
					break;
			}
			super.handleMessage(msg);
		}
    };
}
