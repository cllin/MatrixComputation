#include <string.h>
#include <jni.h>
#include <time.h>
#include <android/log.h>
#include <Eigen/Dense>
#include <math.h>
#include <android/log.h>

#define MSG_TAG "MatrixComputationFromEigen"
#define LOGD(...)  __android_log_print(ANDROID_LOG_INFO, MSG_TAG, __VA_ARGS__)

using namespace Eigen;

extern "C" {

	JNIEXPORT jobjectArray JNICALL
	Java_com_cllin_matrixcomputation_computation_EigenMatrixComputation_matrixComputationByEigen
	(JNIEnv* env, jobject obj, jobjectArray a, jobjectArray b){
//		Timer
		clock_t t;
		t = clock();

		int row;
		int col;
		int dim;

//		Load float matrices
		int len1 = env->GetArrayLength(a);
		jfloatArray array1D=  (jfloatArray)env->GetObjectArrayElement(a, 0);
		int len2 = env->GetArrayLength(array1D);
		row = len1;

		MatrixXf m1 = MatrixXf::Zero(len1, len2);
		for(int i = 0; i < len1; i++){
			jfloatArray onearray1D = (jfloatArray)env->GetObjectArrayElement(a, i);
			jfloat* element = env->GetFloatArrayElements(onearray1D, 0);
//			localA[i] = new float[len2];
			for(int j = 0; j < len2; j++) {
				m1(i, j) = element[j];
//				m1(i, j) = 1;
//				localA[i][j] = element[j];
			}
			env->ReleaseFloatArrayElements(onearray1D, element, 0);
			env->DeleteLocalRef(onearray1D);
		}

		len1 = env->GetArrayLength(b);
		array1D=  (jfloatArray)env->GetObjectArrayElement(b, 0);
		len2 = env->GetArrayLength(array1D);
		col = len2;
		dim = len1;

		MatrixXf m2 = MatrixXf::Zero(len1, len2);
		for(int i = 0; i < len1; i++){
			jfloatArray onearray1D = (jfloatArray)env->GetObjectArrayElement(b, i);
			jfloat* element = env->GetFloatArrayElements(onearray1D, 0);
			for(int j = 0; j < len2; j++) {
				m2(i, j) = element[j];
			}
			env->ReleaseFloatArrayElements(onearray1D, element, 0);
			env->DeleteLocalRef(onearray1D);
		}

		__android_log_print(ANDROID_LOG_VERBOSE, MSG_TAG, "Load Time= %f (ms)\n", (float)(clock() - t)/CLOCKS_PER_SEC * 1000);
		t = clock();

//		******
		MatrixXf result = m1 * m2;
		__android_log_print(ANDROID_LOG_VERBOSE, MSG_TAG, "Computation Time= %f (ms)\n", (float)(clock() - t)/CLOCKS_PER_SEC * 1000);
		t = clock();
//		******

//		XXX
//		There should be some better way to return the result
		float** tmp = new float*[row];
		jclass floatArrayClass = env->FindClass("[F");
		jobjectArray returnedResult = env->NewObjectArray((jsize)row, floatArrayClass, NULL);
		for(int i = 0; i < row; i++){
			tmp[i] = new float[col];
			for(int j = 0; j < col; j++){
				tmp[i][j] = result(i,j);
			}
			jfloatArray floatArray = env->NewFloatArray(col);
	        env->SetFloatArrayRegion(floatArray, (jsize)0, (jsize)col, (jfloat*)tmp[i]);
	        env->SetObjectArrayElement(returnedResult, (jsize)i, floatArray);
	        env->DeleteLocalRef(floatArray);
		}

		free(tmp);

		__android_log_print(ANDROID_LOG_VERBOSE, MSG_TAG, "Preparation of Returned Value= %f (ms)\n", (float)(clock() - t)/CLOCKS_PER_SEC * 1000);
		return returnedResult;
	}
}
