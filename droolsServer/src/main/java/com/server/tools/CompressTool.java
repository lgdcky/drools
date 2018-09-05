package com.server.tools;

import org.xerial.snappy.Snappy;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/29/18
 * Time: 4:18 PM
 */
public class CompressTool {

    public static byte[] compresss(byte srcBytes[]) throws IOException {
        return  Snappy.compress(srcBytes);
    }
    public static byte[] uncompresss(byte[] bytes) throws IOException {
        return Snappy.uncompress(bytes);
    }

}
