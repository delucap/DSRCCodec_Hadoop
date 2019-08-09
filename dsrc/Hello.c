#include <jni.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
//#include "org_apache_hadoop_io_compress_dsrc_Hello.h"
#include "/Users/pasqualedeluca/Desktop/hadoop_project/hadoop-2.8.5-src/hadoop-common-project/hadoop-common/src/main/java/org_apache_hadoop_io_compress_dsrc_Hello.h"
JNIEXPORT void JNICALL Java_org_apache_hadoop_io_compress_dsrc_Hello_sayHello(JNIEnv *env, jobject obj)
{
	printf("Hello World!");
	return;
}


JNIEXPORT jint JNICALL Java_org_apache_hadoop_io_compress_dsrc_Hello_genRand
  (JNIEnv *env, jobject obj)
{
  int r = 1 + rand() % 100;
  return r;
}


