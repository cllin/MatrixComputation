package com.cllin.matrixcomputation.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import android.os.Environment;
import android.text.format.Time;

import com.cllin.matrixcomputation.data.BenchmarkResult;
import com.cllin.matrixcomputation.data.BenchmarkResult.Test;

public class RecordWriter {
	private static final String PATH_FOLDER = "/MatrixComputation";
	private static final String PATH_MATRIXCOMPUTATION = "/MatrixComputation/log_benchmark_";
	
	private static final int FLAG_RECORDING_STRING = 0;
	private static final int FLAG_RECORDING_BENCHMARKRESULT = 1;
	
	private int mFlag = -1;
	private File mFile;
	
    /**
     * Write the record of the tests into a csv file and save to the SD card. 
     * The file will be saved to SDCard//MatrixComputation/log_benchmark_TIME.csv
     * 
     * @param list		the tests to be saved
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void record(ArrayList list) {
		if (list.size() == 0) return;
		
		Class listClass = list.get(0).getClass();
		if (listClass == String.class) mFlag = FLAG_RECORDING_STRING;
		else if(listClass == BenchmarkResult.Test.class) mFlag = FLAG_RECORDING_BENCHMARKRESULT;
		
		createFile();
		
		StringBuffer buffer;
		StringBuilder text = new StringBuilder();
		try {
		    BufferedReader legacy = new BufferedReader(new FileReader(mFile));
		    String line;

		    while ((line = legacy.readLine()) != null) {
		        text.append(line);
		        text.append('\n');
		    }
			
			FileWriter writer = new FileWriter(mFile);
			
			buffer = new StringBuffer(text.toString());
			
			switch(mFlag){
			case FLAG_RECORDING_BENCHMARKRESULT:
				buffer.append("Matrix Size,Initialization,C++,Java,OpenCV,Eigen,RenderScript");
				buffer.append("\r\n");
				for(Test test : (ArrayList<Test>)list){
					
					buffer.append(test.getMatrixSize());
					buffer.append(',');
					buffer.append(test.getInitialization());
					buffer.append(',');
					
					for (int i = Test.CPP_CONSUMPTION; i <= Test.RENDERSCRIPT_CONSUMPTION; i++) {
						buffer.append(test.getValue(i));
						buffer.append(',');
					}
					
					buffer.append("\r\n");
				}
				break;
			}
			
			buffer.append("\r\n\r\n");
			
			writer.write(buffer.toString());
			writer.close();
			legacy.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void createFile() {
		Time time = new Time();
		time.setToNow();
		
		String timeStamp = (Integer.toString(time.year) + Integer.toString(time.month + 1) + Integer.toString(time.monthDay));
		String filePath = new String();
		
		switch (mFlag) {
		case FLAG_RECORDING_BENCHMARKRESULT:
			filePath = PATH_MATRIXCOMPUTATION + timeStamp + ".csv";
			break;
		}
		
		try {
			File folder = new File(Environment.getExternalStorageDirectory() + PATH_FOLDER);
			if(!folder.exists()){
				folder.mkdir();
			}
			
			mFile = new File(Environment.getExternalStorageDirectory() + filePath);
			mFile.createNewFile();
		}catch(IOException e) {
			e.printStackTrace();
		}		
	}
}
