#MatrixComputation

*The project performs a benchmark test for matrices computation in Java, C++, Eigen, OpenCV, RenderScript on Android platform*

*The purpose of this application is not to compare the performance of algorithms performing matrix multiplication. All computations are implemented in naive way*

======
###CONTENT
1. [Application Manifest] (#1)
2. [Java] (#2)
3. [C++] (#3)
4. [OpenCV] (#4)
5. [Eigen] (#5)
6. [RenderScript] (#6)

======
###<a name="1"></a>APPLICATION MANIFEST
- Target SDK Version: 19
- Minimum SDK Version: 16
- Uses Permission
  - android.permission.READ_EXTERNAL_STORAGE
  - android.permission.WRITE_EXTERNAL_STORAGE
  - android.permission.READ_LOGS


###<a name="2"></a>JAVA
This is the simplest way to perform matrices computation on Android devices. Yet, the performance is the worst among all approaches demonstrated in this application.

###<a name="3"></a>C++
This approach is faster than Java. Note that [Java Native Interface](http://docs.oracle.com/javase/7/docs/technotes/guides/jni/) is needed in order to run native code in Android applications.

###<a name="4"></a>OpenCV
[OpenCV](http://opencv.org/) is common library for mathematic computations. Details and tutorial about [OpenCV on Android] (http://opencv.org/platforms/android.html) can be found [here] (http://docs.opencv.org/doc/tutorials/introduction/android_binary_package/O4A_SDK.html).
**NOTE**
*OpenCV is not available in release 1.2*

###<a name="5"></a>Eigen
[Eigen] (http://eigen.tuxfamily.org/index.php?title=Main_Page) is another choice for computation. Eigen usually outperform OpenCV on Android devices.

###<a name="6"></a>RenderScript
[RenderScript] (http://developer.android.com/guide/topics/renderscript/compute.html) is *a framework for running computationally intensive tasks at high performance on Android*, usually used for image processing on Android devices. It accelerates the computation by running on GPUs. Please note that RenderScript is device-dependent. Version problem limits the compatibility of RenderScript.
