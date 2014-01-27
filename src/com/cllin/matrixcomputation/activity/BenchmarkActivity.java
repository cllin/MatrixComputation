package com.cllin.matrixcomputation.activity;

import com.cllin.matrixcomputation.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BenchmarkActivity extends Activity implements OnClickListener {
	private static final String MSG_TAG = "MatrixComputationActivity";
	
//	private static final int FLAG_DEFAULT = 0;
//	private static final int FLAG_FULL_SCRIPT = 1;
//	private static final int FLAG_SCRIPT_LITE = 2;
//	private static final int FLAG_SHOW_OUTPUT = 10; 
//	private static final int FLAG_SHOW_PROGRESS = 20;
	
//	VIEWS
	private ProgressBar mProgressBar = null;
	private TextView mOutputTextView = null;
	private Button mComputeWithFullScriptButton = (Button)findViewById(R.id.button_matrixcomputation_fullscript);
	private Button mComputeWithScriptLiteButton = (Button)findViewById(R.id.button_matrixcomputation_scriptlite);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(MSG_TAG, "onCreate");
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_benchmark);
		setView();
	}
	
	private void setView(){
		mComputeWithFullScriptButton = (Button)findViewById(R.id.button_matrixcomputation_fullscript);
		mComputeWithScriptLiteButton = (Button)findViewById(R.id.button_matrixcomputation_scriptlite);
		mProgressBar = (ProgressBar)findViewById(R.id.progressbar_progress);
		mOutputTextView = (TextView)findViewById(R.id.textview_matrixcomputation_output);
		
		mComputeWithFullScriptButton.setOnClickListener(this);
		mComputeWithScriptLiteButton.setOnClickListener(this);
		
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
//		mProgressBar.setProgress(0);
//		BenchmarkRunnable runnable = null;
//		
//		switch(v.getId()){
//		case R.id.button_matrixcomputation_fullscript:
//			runnable = new BenchmarkRunnable(FLAG_FULL_SCRIPT, mHandler, this.getBaseContext());
//			break;
//		case R.id.button_matrixcomputation_scriptlite:
//			runnable = new BenchmarkRunnable(FLAG_SCRIPT_LITE, mHandler, this.getBaseContext());
//			break;
//		}
//		
//		Thread thread = new Thread(runnable);
//		thread.start();
//		
//		thread = null;
//		
//		v.setPressed(false);
	}
	
//    private Handler mHandler = new Handler(){
//		@Override
//		public void handleMessage(final Message msg) {
//			switch(msg.what){
//				case FLAG_SHOW_OUTPUT:
//					this.post(new Runnable() {
//						@Override
//						public void run() {
//							setTextView(msg.obj.toString());
//						}
//					});
//					break;
//				case FLAG_SHOW_PROGRESS:
//					mProgressBar.post(new Runnable(){
//						@Override
//						public void run() {
//							setProgressBar(msg);
//						}
//					});
//					break;
//			}
//			super.handleMessage(msg);
//		}
//    };
}
