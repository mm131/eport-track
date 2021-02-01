package com.njeport.track.common.base;

/**
 * @author limingming
 * @date 2019/12/17
 */
public interface BaseMapper<T, PK> {
    int insert(T object);
    int update(T object);
    T getById(PK id);
    int deleteById(PK id);
}
