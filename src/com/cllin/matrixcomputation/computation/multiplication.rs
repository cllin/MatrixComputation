#pragma version(1)
#pragma rs java_package_name(com.cllin.matrixcomputation.computation)

#define MSG_TAG "MatrixComputationFromRenderScript"

rs_script gScript;
rs_allocation gInputA;
rs_allocation gInputB;
float* gOut;

int rows = 0;
int cols = 0;
int count = 0;

void root(const float *a, float *b) {
	if (count != 0) return;
 	count++;
	
 	int dim = rows;
	for(int i = 0; i < rows; i++){
 		for(int j = 0; j < cols; j++){
 			gOut[i * cols + j] = 0;
 			for(int k = 0; k < dim; k++){
 				gOut[i * cols + j] += (a[i * cols + k] * b[k * cols + j]);
 			}
 		}
 	}
}

void init(){
	rsDebug("Called init", rsUptimeMillis());
}

void compute(){
 	int start = rsUptimeNanos();
 	
 	rsForEach(gScript, gInputA, gInputB);
 	
	int end = rsUptimeNanos();
 	int cost = end - start;
 	rsDebug("time cost(ns)=", cost);
}