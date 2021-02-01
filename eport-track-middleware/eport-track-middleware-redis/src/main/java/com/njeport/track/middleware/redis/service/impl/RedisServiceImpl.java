package com.njeport.track.middleware.redis.service.impl;

import com.njeport.track.common.constants.EportConstants;
import com.njeport.track.middleware.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author kongming
 * @date 2019/12/18
 */
@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

    private static final StringRedisSerializer SERIALIZER = new StringRedisSerializer();

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${spring.redis.expire:60}")
    private long expireTime;

    /**
     * 获取真实的redis key
     *
     * @param key 输入key
     * @return 真实key
     */
    @Override
    public String getRealKey(String key) {
        return EportConstants.REDIS_KEY_PREFIX + key;
    }

    /**
     * 根据key查询value
     *
     * @param key 缓存的key
     * @return 该key保存的字符串
     */
    @Override
    public String readString(String key) {
        return stringRedisTemplate.boundValueOps(getRealKey(key)).get();
    }

    /**
     * 写入某个key对应的value (使用默认超时时间： 1小时)
     *
     * @param key   缓存的key
     * @param value 该key将要设置的字符串
     */
    @Override
    public void writeString(String key, String value) {
        stringRedisTemplate.opsForValue().set(getRealKey(key), value);
    }

    @Override
    public void writeString(String key, String value, long expire) {
        stringRedisTemplate.opsForValue().set(getRealKey(key), value);
        if (expire > 0) {
            stringRedisTemplate.expire(getRealKey(key), expire, TimeUnit.SECONDS);
        }
    }

    /**
     * 根据key获取对应的缓存
     *
     * @param key   查询key
     * @param clazz 泛型类
     * @return 缓存内容
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T readObject(String key, Class<T> clazz) {
        return (T) redisTemplate.boundValueOps(getRealKey(key)).get();
    }

    /**
     * 写缓存 (使用默认超时时间： 1小时)
     *
     * @param key    缓存的key
     * @param object 写入的对象
     */
    @Override
    public void writeObject(String key, Object object) {
        writeObject(getRealKey(key), object, expireTime);
    }

    /**
     * 写缓存
     *
     * @param key    缓存的key
     * @param object 写入的对象
     * @param expire 超时时间，单位为秒
     */
    @Override
    public void writeObject(String key, Object object, long expire) {
        redisTemplate.opsForValue().set(getRealKey(key), object);
        if (expire > 0) {
            redisTemplate.expire(getRealKey(key), expire, TimeUnit.SECONDS);
        }
    }

    /**
     * 删除缓存
     *
     * @param key 缓存的key
     */
    @Override
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 当key不存在的时候才去将值设为指定字符串
     *
     * @param key   缓存的key
     * @param value 写入的字符串
     */
    @Override
    @SuppressWarnings("all")
    public boolean setNX(String key, String value) {
        return redisTemplate.getConnectionFactory().getConnection().setNX((getRealKey(key)).getBytes(), value.getBytes());
    }

    /**
     * 获取分布式锁
     *
     * @param key            输入key
     * @param expirationTime 超时时间
     * @param timeUnit       时间单位
     */
    @Override
    public String tryLock(String key, long expirationTime, TimeUnit timeUnit) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        try {
            final String realKey = this.getRealKey(key);
            final StringBuffer sb = new StringBuffer();
            sb.append(System.currentTimeMillis());
            sb.append("_");
            sb.append(Thread.currentThread().getId());
            sb.append("_");
            sb.append(UUID.randomUUID().toString());
            final Expiration expiration = Expiration.from(expirationTime, timeUnit);
            redisTemplate.execute(
                    (RedisCallback<Boolean>) connection -> {
                        connection.set(SERIALIZER.serialize(realKey), SERIALIZER.serialize(sb.toString()), expiration,
                                RedisStringCommands.SetOption.SET_IF_ABSENT);
                        return true;
                    });
            String temp = this.redisTemplate.execute(
                    (RedisCallback<String>) connection -> {
                        byte[] ret = connection.get(SERIALIZER.serialize(realKey));
                        return SERIALIZER.deserialize(ret);
                    });
            if (sb.toString().equals(temp)) {
                return sb.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("Redis 分布式锁 lock 失败", e);
            return null;
        }
    }

    /**
     * 释放分布式锁
     *
     * @param key   输入key
     * @param value tryLock返回值
     */
    @Override
    public boolean unLock(String key, String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return false;
        }
        try {
            final String realKey = this.getRealKey(key);
            String temp = this.redisTemplate.execute(
                    (RedisCallback<String>) connection -> {
                        byte[] ret = connection.get(SERIALIZER.serialize(realKey));
                        return SERIALIZER.deserialize(ret);
                    });
            if (Objects.equals(temp, value)) {
                this.redisTemplate.execute(
                        (RedisCallback<String>) connection -> {
                            connection.del(SERIALIZER.serialize(realKey));
                            return null;
                        });
            }
            return true;
        } catch (Exception e) {
            log.error("Redis 分布式锁 unlock 失败", e);
            return false;
        }
    }
}
