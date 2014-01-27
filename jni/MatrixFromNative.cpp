#include <string.h>
#include <jni.h>
#include <time.h>
#include <android/log.h>

#define MSG_TAG "MatrixComputationInNative"

extern "C" {
	JNIEXPORT jobjectArray JNICALL
	Java_com_cllin_matrixcomputation_benchmark_MatrixComputationBenchmark_matrixComputationByNative
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


		float** localA;
		localA = new float*[len1];
		for(int i = 0; i < len1; i++){
			jfloatArray onearray1D = (jfloatArray)env->GetObjectArrayElement(a, i);
			jfloat* element = env->GetFloatArrayElements(onearray1D, 0);
			localA[i] = new float[len2];
			for(int j = 0; j < len2; j++) {
				localA[i][j] = element[j];
			}
			env->ReleaseFloatArrayElements(onearray1D, element, 0);
			env->DeleteLocalRef(onearray1D);
		}

		len1 = env->GetArrayLength(b);
		array1D=  (jfloatArray)env->GetObjectArrayElement(b, 0);
		len2 = env->GetArrayLength(array1D);
		col = len2;
		dim = len1;

		float** localB;
		localB = new float*[len1];
		for(int i = 0; i < len1; i++){
			jfloatArray onearray1D = (jfloatArray)env->GetObjectArrayElement(b, i);
			jfloat* element = env->GetFloatArrayElements(onearray1D, 0);
			localB[i] = new float[len2];
			for(int j = 0; j < len2; j++) {
				localB[i][j] = element[j];
			}
			env->ReleaseFloatArrayElements(onearray1D, element, 0);
			env->DeleteLocalRef(onearray1D);
		}

//		__android_log_print(ANDROID_LOG_VERBOSE, MSG_TAG, "Load Time= %f (ms)\n", (float)(clock() - t)/CLOCKS_PER_SEC * 1000);
		t = clock();

//		Computation
		float** result;

		result = new float*[row];
		for(int i = 0; i < row; i++){
			result[i] = new float[col];
			for(int j = 0; j < col; j++){
				result[i][j] = 0;
				for(int k = 0; k < dim; k++){
					result[i][j] += localA[i][k] * localB[k][j];
				}
			}
		}

		jclass floatArrayClass = env->FindClass("[F");
		jobjectArray returnedResult = env->NewObjectArray((jsize)row, floatArrayClass, NULL);
		for(int i = 0; i < row; i++){
			jfloatArray floatArray = env->NewFloatArray(col);
	        env->SetFloatArrayRegion(floatArray, (jsize)0, (jsize)col, (jfloat*)result[i]);
	        env->SetObjectArrayElement(returnedResult, (jsize)i, floatArray);
	        env->DeleteLocalRef(floatArray);
		}

		env->DeleteLocalRef(array1D);

		free(localA);
		free(localB);
		free(result);

//		__android_log_print(ANDROID_LOG_VERBOSE, MSG_TAG, "Computation Time= %f (ms)\n", (float)(clock() - t)/CLOCKS_PER_SEC * 1000);

		return returnedResult;
	}
}
