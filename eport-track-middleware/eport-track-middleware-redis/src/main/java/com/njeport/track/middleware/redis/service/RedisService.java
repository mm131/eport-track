package com.njeport.track.middleware.redis.service;

import java.util.concurrent.TimeUnit;

/**
 * @author kongming
 * @date 2019/12/18
 */
public interface RedisService {

    /**
     * 获取完整的 redis key
     *
     * @param key 关键字
     * @return 完整的key
     */
    String getRealKey(String key);

    /**
     * 根据key查询value
     *
     * @param key 缓存的key
     * @return 该key保存的字符串
     */
    String readString(String key);

    /**
     * 写入某个key对应的value (使用默认超时时间： 1小时)
     *
     * @param key   缓存的key
     * @param value 该key将要设置的字符串
     */
    void writeString(String key, String value);

    /**
     * 写入某个key对应的value (使用默认超时时间： 1小时)
     *
     * @param key    缓存的key
     * @param value  该key将要设置的字符串
     * @param expire 过期时间
     */
    void writeString(String key, String value, long expire);

    /**
     * 根据key获取对应的缓存
     *
     * @param key   查询key
     * @param clazz 泛型类
     * @return 缓存内容
     */
    <T> T readObject(String key, Class<T> clazz);

    /**
     * 写缓存 (使用默认超时时间： 1小时)
     *
     * @param key    缓存的key
     * @param object 写入的对象
     */
    void writeObject(String key, Object object);

    /**
     * 写缓存
     *
     * @param key    缓存的key
     * @param object 写入的对象
     * @param expire 超时时间，单位为秒
     */
    void writeObject(String key, Object object, long expire);

    /**
     * 删除缓存
     *
     * @param key 缓存的key
     */
    void del(String... key);

    /**
     * 当key不存在的时候才去将值设为指定字符串
     *
     * @param key   缓存的key
     * @param value 写入的字符串
     */
    boolean setNX(String key, String value);

    /**
     * 获取分布式锁
     *
     * @param key            输入key
     * @param expirationTime 超时时间
     * @param timeUnit       时间单位
     */
    String tryLock(String key, long expirationTime, TimeUnit timeUnit);

    /**
     * 释放分布式锁
     *
     * @param key   输入key
     * @param value tryLock返回值
     */
    boolean unLock(final String key, String value);
}
