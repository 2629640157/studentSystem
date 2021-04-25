package com.you.system.util;

/**
 * @author 游斌
 * @create 2020-12-14  17:04
 */
public class PageUtil {

    public static long getTotalPage(long total, long size) {
        return total % size == 0 ? total / size : total / size + 1;
    }
}
