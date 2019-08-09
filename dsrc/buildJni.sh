#!/bin/bash

echo '*** Building Java class file and generating header for C file ***'
cd /Users/pasqualedeluca/Desktop/hadoop_project/hadoop-2.8.5-src/hadoop-common-project/hadoop-common/src/main/java
javac org/apache/hadoop/io/compress/dsrc/Hello.java
javah -jni org.apache.hadoop.io.compress.dsrc.Hello

echo '*** Access to HADOOP Home and generating object file of C ***'

cd /Users/pasqualedeluca/Desktop/hadoop_project/hadoop-2.8.5-src/hadoop-common-project/hadoop-common/src/main/java/org/apache/hadoop/io/compress/dsrc
gcc -o libHelloImpl.jnilib -lc -shared -I/Library/Java/JavaVirtualMachines/jdk1.8.0_221.jdk/Contents/Home/include -I/Library/Java/JavaVirtualMachines/jdk1.8.0_221.jdk/Contents/Home/include/darwin/ Hello.c

echo '*** Copying object file to Hadoop native library directory ***'
mv libHelloImpl.jnilib /usr/local/Cellar/hadoop/lib/native/

echo '*** END SCRIPT ***'
