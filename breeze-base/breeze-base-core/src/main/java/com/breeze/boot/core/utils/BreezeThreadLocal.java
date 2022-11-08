package com.breeze.boot.core.utils;

/**
 * 微风线程本地
 *
 * @author breeze
 * @date 2022-11-08
 */
public class BreezeThreadLocal {
    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static Long get() {
        try {
            return threadLocal.get();
        } finally {
            threadLocal.remove();
        }
    }

    public static void set(Long value) {
        threadLocal.set(value);
    }

}
