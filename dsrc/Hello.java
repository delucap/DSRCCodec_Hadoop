// Hello.java
// test con file .c

package org.apache.hadoop.io.compress.dsrc;

import java.util.Random;

class Hello {

	// metodo nativo sayHello che non è implementato qui, ma in C in un altro file
	public native void sayHello();

	public native int genRand();

	// richiama la libreria creata
	static { System.loadLibrary("HelloImpl"); }

	public int randomNumber()
	{
		Random rand = new Random();

		int r = rand.nextInt(50);

		return r;
	}
	// main
/*	public static void main (String[] args) {
		Hello h = new Hello();

		h.sayHello();	// riferimento al metodo C esterno al file Java
		System.out.println("funzione eseguita...");
	}*/
}
