package org.apache.hadoop.io.compress;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.*;

import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.commons.logging.Log;
//import org.apache.hadoop.commons.logging.LogFactory;
import org.apache.hadoop.io.compress.BlockCompressorStream;
import org.apache.hadoop.io.compress.BlockDecompressorStream;
import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.compress.CompressionInputStream;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.Compressor;
import org.apache.hadoop.io.compress.Decompressor;
import org.apache.hadoop.io.compress.dsrc.DsrcCompressor;
import org.apache.hadoop.io.compress.dsrc.DsrcDecompressor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@InterfaceAudience.Public
@InterfaceStability.Evolving
public class DsrcCodec implements CompressionCodec, Configurable{

    //private static final Log LOG = LogFactory.getLog(DsrcCodec.class);

    public static final String DSRC_EXT = ".dsrc";
    public static String name = "DsrcCodec";
     Configuration config;



    //Carico le classi del compressore e del finto decompressore

    @Override
    public Class<? extends Compressor> getCompressorType()
    {
        //checkNativeCodeLoaded();
        return DsrcCompressor.class;
    }

    @Override
    public Class<? extends Decompressor> getDecompressorType() {
    //checkNativeCodeLoaded();
    return DsrcDecompressor.class;
    }

    //Fine caricamento

    //Creazione compressore e decompressore
    @Override
    public Compressor createCompressor()
    {
        //LOG.info("Creating compressor");
        System.out.println("Compressore caricato correttamente!");
        String ext = ".fastq";
        return new DsrcCompressor(ext);
    }

    @Override
    public Decompressor createDecompressor()
    {
        return new DsrcDecompressor();
    }
    //Fine creazione

    //Definisco inputStream ed outputstream
    @Override
    public  CompressionInputStream createInputStream(InputStream in)
            throws IOException {
        return createInputStream(in);
    }

    @Override
    public CompressionInputStream createInputStream(InputStream in,
                                                    Decompressor decomp) throws IOException {
        //LOG.info("Creating input stream");
        return new BlockDecompressorStream(in, decomp);
    }

    @Override
    public CompressionOutputStream createOutputStream(OutputStream out)
            throws IOException {
        return createOutputStream(out, createCompressor());
    }

    @Override
    public CompressionOutputStream createOutputStream(OutputStream out,
                                                      Compressor comp) throws IOException {
        //LOG.info("Creating output stream");
        System.out.println("Creating output stream");
        return new BlockCompressorStream(out, comp);
    }
    /*public CompressionOutputStream  createOutputStream(OutputStream out, Compressor comp) throws IOException
    {
        return createOutputStream(out, createCompressor());
    }*/

    @Override
    public String getDefaultExtension()
    {
        return this.DSRC_EXT;
    }

    @Override
    public Configuration getConf() {
        return this.config;
    }

    @Override
    public void setConf(Configuration config)
    {
        this.config = config;
    }
}
