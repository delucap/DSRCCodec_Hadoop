// Hello.java
// test con file .c

package org.apache.hadoop.io.compress.dsrc;

class Hello {

	// metodo nativo sayHello che non è implementato qui, ma in C in un altro file
	public native void sayHello();

	// richiama la libreria creata
	static { System.loadLibrary("HelloImpl"); }

	// main

}
