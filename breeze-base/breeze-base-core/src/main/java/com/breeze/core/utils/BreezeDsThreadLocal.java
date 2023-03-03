package com.breeze.core.utils;

/**
 * 线程本地
 *
 * @author gaoweixuan
 * @date 2022-11-08
 */
public class BreezeDsThreadLocal {

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static String get() {
        try {
            return threadLocal.get();
        } finally {
            threadLocal.remove();
        }
    }

    public static void set(String value) {
        threadLocal.set(value);
    }

}
