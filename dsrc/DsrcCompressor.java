/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.io.compress.dsrc;

import java.io.IOException;
import java.io.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.compress.Compressor;
import org.apache.hadoop.io.compress.dsrc.Hello;
/**
 * A fake compressor
 * Its input and output is the same.
 */
public class DsrcCompressor implements Compressor {

  private boolean finish;
  private boolean finished;
  private int nread;
  private int nwrite;

  private byte[] userBuf;
  private int userBufOff;
  private int userBufLen;

  //setto la variabile in modo volatile cosìche le diverse cache lavorano senza creare false sharing ovvero un rallentamento dovuto ad intasamenti sulle cache lines
  volatile int countTask;

  private Hello porco;

  public DsrcCompressor(String ext) {

    this.countTask = 0;

    // Controllo dell'estensione del file passato nella fase di reducing poichè sono accettati solo file .fasta
    if(ext.compareTo(".fastq") != 0)
    {
      printf("********** EXETENSION NOT ACCPETED (only fasta) ************");
      finish();
    }
    else {
      printf("*********** EXTENSION ACCEPTED *********");
      this.porco = new Hello();
      printf("NATIVE LIBRARY LOADED!!");
      porco.sayHello();
      printf("NATIVE FUNCTION EXECUTED!!");
    }
  }

  @Override
  public int compress(byte[] b, int off, int len) throws IOException {
   // System.out.println("len in entrata: "+ len);

    porco = new Hello();

    int n = Math.min(len, userBufLen);
    if (userBuf != null && b != null)
      System.arraycopy(userBuf, userBufOff, b, off, n);
    userBufOff += n;
    userBufLen -= n;
    nwrite += n;

    if (finish && userBufLen <= 0)
      finished = true;


    if(countTask<10)
        printf("Random number return for "+countTask+" task is: "+porco.genRand());
      // System.out.printf("task "+countTask+" con len = "+len+" ritorno = "+n+"\t nread local = "+nread+"\n");

    countTask ++;

    //n = this.userBufLen;
    return n;
  }

  @Override
  public void end() {
    // nop
  }

  @Override
  public void finish() {
    finish = true;
  }

  @Override
  public boolean finished() {
    return finished;
  }

  @Override
  public long getBytesRead() {
    return nread;
  }

  @Override
  public long getBytesWritten() {
    return nwrite;
  }

  @Override
  public boolean needsInput() {
    return userBufLen <= 0;
  }

  @Override
  public void reset() {
    finish = false;
    finished = false;
    nread = 0;
    nwrite = 0;
    userBuf = null;
    userBufOff = 0;
    userBufLen = 0;
  }

  @Override
  public void setDictionary(byte[] b, int off, int len) {
    // nop
  }

  @Override
  public void setInput(byte[] b, int off, int len) {

    //printf("Task #"+ countTask+ "input offset: "+off+"\tinput len = "+len);

    nread += len;
    userBuf = b;
    userBufOff = off;
    userBufLen = len;

    if(countTask == 1)
    {
      printf("**** PROVA 1 TASK ****");

      printf("nread = "+nread +"\t userBuf= "+b+ "\t userBuffOff = "+userBufOff+"\t userBufLen = "+userBufLen);
    }
  }

  @Override
  public void reinit(Configuration conf) {
    // nop
  }

  public static String getLibraryName()
  {
    return "diocane";
  }

  private void printf(String s)
  {
    System.out.println(s);
  }
}