package com.noah.log.config;

import ch.qos.logback.core.OutputStreamAppender;

public class ThreadLocalOutputStreamAppender<E> extends OutputStreamAppender<E> {

    public ThreadLocalOutputStreamAppender() {
        setOutputStream(ThreadLocalOutputStream.INSTANCE);
    }
}
