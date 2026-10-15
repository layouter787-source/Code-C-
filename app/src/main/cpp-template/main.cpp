#include <android/log.h>
#include <jni.h>

#define LOG_TAG "CodeCppSample"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

extern "C"
JNIEXPORT void JNICALL
Java_com_example_nativeapp_MainActivity_nativeStart(JNIEnv*, jobject) {
    LOGI("Hello from a C++ Android native library.");
}
