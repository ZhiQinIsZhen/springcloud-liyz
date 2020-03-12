package com.liyz.cloud.common.redisson.service;

import com.liyz.cloud.common.redisson.constant.RedissonConstant;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RList;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/7/22 10:47
 */
@Slf4j
@ConditionalOnClass({RedissonClient.class})
@Service
public class RedissonService {

    @Autowired(required = false)
    RedissonClient redissonClient;

    private static final Codec STRING_CODE = new StringCodec();

    public <T> RList<T> getList(String key) {
        return redissonClient.getList(key, STRING_CODE);
    }

    public <T> boolean setListValue(String key, T value) {
        return redissonClient.getList(key, STRING_CODE).add(value);
    }

    public <T> boolean removeListValue(String key, T value) {
        return redissonClient.getList(key, STRING_CODE).remove(value);
    }

    public <T> boolean isExistsForList(String key, T value) {
        return redissonClient.getList(key, STRING_CODE).contains(value);
    }

    public <T> RSet<T> getSet(String key) {
        return redissonClient.getSet(key, STRING_CODE);
    }

    public <T> boolean setSetValue(String key, T value) {
        return redissonClient.getSet(key, STRING_CODE).add(value);
    }

    public <T> boolean removeSetValue(String key, T value) {
        return redissonClient.getSet(key, STRING_CODE).remove(value);
    }

    public <T> boolean setSetList(String key, List<T> list) {
        return redissonClient.getSet(key, STRING_CODE).addAll(list);
    }

    public <T> boolean isExistsForSet(String key, T value) {
        return redissonClient.getSet(key, STRING_CODE).contains(value);
    }

    public String getValue(String key) {
        Object result = redissonClient.getBucket(key, STRING_CODE).get();
        return result == null ? null : result.toString();
    }

    public <T> void setValue(String key, T value) {
        redissonClient.getBucket(key, STRING_CODE).set(value);
    }

    public <T> void setValueExpire(String key, T value) {
        setValueExpire(key, value, RedissonConstant.DEFAULT_EXPIRE_TIME_DAY, TimeUnit.DAYS);
    }

    public <T> void setValueExpire(String key, T value, long time, TimeUnit unit) {
        redissonClient.getBucket(key, STRING_CODE).set(value, time, unit);
    }

    public boolean isExists(String key) {
        return redissonClient.getBucket(key, STRING_CODE).isExists();
    }

    public <T> boolean compareAndSet(String key, T target, T source) {
        return redissonClient.getBucket(key, STRING_CODE).compareAndSet(target, source);
    }

    public boolean expire(String key) {
        return expire(key, RedissonConstant.DEFAULT_EXPIRE_TIME_DAY, TimeUnit.DAYS);
    }

    public boolean expire(String key, long time, TimeUnit unit) {
        return redissonClient.getBucket(key, STRING_CODE).expire(time, unit);
    }

    public long getAndIncrement(String key) {
        return redissonClient.getAtomicLong(key).getAndIncrement();
    }

    public long incrementAndGet(String key) {
        return redissonClient.getAtomicLong(key).incrementAndGet();
    }
}
