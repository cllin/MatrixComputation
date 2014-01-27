LOCAL_PATH := $(call my-dir)

#	Build a shared library from MatrixFromNative.cpp
include $(CLEAR_VARS)
LOCAL_MODULE    := matrixfromnative
LOCAL_SRC_FILES := MatrixFromNative.cpp
LOCAL_LDLIBS    := -L$(SYSROOT)/usr/lib -llog
include $(BUILD_SHARED_LIBRARY)

#	Eigen
include $(CLEAR_VARS)   
LOCAL_MODULE    := matrixfromeigen
LOCAL_SRC_FILES := MatrixFromEigen.cpp
LOCAL_LDLIBS := -L$(SYSROOT)/usr/lib -llog
include $(BUILD_SHARED_LIBRARY)

#	RenderScript
LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := $(call all-java-files-under, src) $(call all-renderscript-files-under, src)
LOCAL_PACKAGE_NAME := MatrixMultiplicationRS
include $(BUILD_PACKAGE)