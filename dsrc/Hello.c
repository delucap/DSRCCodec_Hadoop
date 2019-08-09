#include <jni.h>
#include <stdio.h>
#include "Hello.h"

JNIEXPORT void JNICALL Java_Hello_sayHello (JNIEnv *env, jobject obj) 
{
	printf("Hello World!");
	return;
}
