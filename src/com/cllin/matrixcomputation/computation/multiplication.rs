#pragma version(1)
#pragma rs java_package_name(com.cllin.matrixcomputation.matrixcomputation)

#define MSG_TAG "MatrixComputationFromRenderScript"

rs_allocation gInput;
rs_allocation gInputA;
rs_allocation gInputB;
rs_allocation gInputC;
rs_allocation gInputD;
rs_allocation gOut;
rs_script gScript;

int rows = 0;
int cols = 0;

void root(const float *in, float *out) {
 	int size = rows * cols;
 	int dim = rows;
 	float output[rows * cols];
 	
	for(int i = 0; i < rows; i++){
 		for(int j = 0; j < cols; j++){
 			output[i * cols + j] = 0;
 			for(int k = 0; k < dim; k++){
 				output[i * cols + j] += in[i * cols + k] * out[k * cols + j];
 			}
 		}
 	}
  }

void init(){
	rsDebug("Called init", rsUptimeMillis());
}

void compute(){
 	int start = rsUptimeNanos();
 	
	rsForEach(gScript, gInputB, gInputC);
	rsForEach(gScript, gInputA, gInputB);
	rsForEach(gScript, gOut, gInputD);
	rsForEach(gScript, gOut, gInput);
	
	int end = rsUptimeNanos();
 	int cost = end - start;
 	rsDebug("size=", rows);
 	rsDebug("time cost(ns)=", cost);
}