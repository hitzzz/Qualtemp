//
// Created by hitesh on 3/18/16.
//

#include"com_example_hitesh_qualtemp_MyNDK.h"
#include <jni.h>
#include <android/log.h>


//function which takes in the Cel and returns Far
JNIEXPORT jint JNICALL Java_com_example_hitesh_qualtemp_MyNDK_getMyString(JNIEnv * env, jobject obj, jint a)
{
    return ((a * 1.8) + 32);
}

