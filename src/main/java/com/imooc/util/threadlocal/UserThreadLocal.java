package com.imooc.util.threadlocal;

import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: UserThreadLocal
 * @date 2019/7/13 21:04
 */
//定义一个面向接口编程的规范
public class  UserThreadLocal{

    private static final ThreadLocal<String> LOCAL=new ThreadLocal();

    public static void set(String obj) {
        LOCAL.set(obj);
    }

    public static String get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }

}
