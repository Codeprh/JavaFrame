package com.noah.log.config;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.IOException;
import java.io.OutputStream;

public class ThreadLocalOutputStream extends OutputStream {

    public static final int MB = 1024 * 1024;
    public static ThreadLocalOutputStream INSTANCE = new ThreadLocalOutputStream();

    private ThreadLocalOutputStream() {
    }

    private static ThreadLocal<ByteOutputStream> tl = new ThreadLocal<>();

    public static void start() {
        tl.set(new ByteOutputStream());
    }

    public static String stop() {
        ByteOutputStream byteOutputStream = tl.get();
        tl.remove();

        if (byteOutputStream != null) {
            return byteOutputStream.toString();
        }

        return null;
    }

    @Override
    public void write(int b) throws IOException {
        ByteOutputStream byteOutputStream = tl.get();

        if (byteOutputStream == null) {
            return;
        }

        if (byteOutputStream.size() > 4 * MB) {
            System.out.println("长度超过了4M，no save");
            return;
        }

        if (byteOutputStream.size() > MB) {
            System.out.println("长度超过1MB，warn");
        }
        byteOutputStream.write(b);
    }
}
