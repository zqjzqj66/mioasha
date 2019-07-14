package com.imooc.util.threadlocal;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: ThreadLocalnterface
 * @date 2019/7/13 21:34
 */
public interface ThreadLocalnterface<T> {

    void set(T obj);

    T get();

    void remove();
}
