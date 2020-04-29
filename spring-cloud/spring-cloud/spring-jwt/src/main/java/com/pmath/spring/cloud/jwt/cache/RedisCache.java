package com.pmath.spring.cloud.jwt.cache;

import com.pmath.spring.cloud.jwt.utils.ApplicationContextHolder;
import javafx.application.Application;
import org.apache.catalina.core.ApplicationContext;
import org.apache.ibatis.cache.Cache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.filter.ApplicationContextHeaderFilter;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RedisCache implements Cache {

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final String id;

    private static final long EXPIRE_TIME_IN_MINUTES = 30;

    private RedisTemplate redisTemplate;

    public RedisCache(String id) {
        if(id == null){
            throw  new IllegalArgumentException("Cache instance required an ID");
        }
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        RedisTemplate redisTemplate = getRedisTemplate();
        ValueOperations opsForValue = redisTemplate.opsForValue();
        opsForValue.set(key.toString(),value,EXPIRE_TIME_IN_MINUTES, TimeUnit.MINUTES);
    }

    @Override
    public Object getObject(Object o) {
        RedisTemplate redisTemplate = getRedisTemplate();
        ValueOperations opsForValue = redisTemplate.opsForValue();
        return opsForValue.get(o.toString());
    }

    @Override
    public Object removeObject(Object o) {
        RedisTemplate redisTemplate = getRedisTemplate();
        redisTemplate.delete(o);
        return null;
    }

    @Override
    public void clear() {
        RedisTemplate redisTemplate = getRedisTemplate();
        redisTemplate.execute((RedisCallback) connection -> {
            connection.flushDb();
            return null;
        });
    }

    @Override
    public int getSize() {
        Long size = (Long) redisTemplate.execute((RedisCallback) connection -> connection.dbSize());
        return size.intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    public RedisTemplate getRedisTemplate(){
        if(redisTemplate ==  null){
            redisTemplate = ApplicationContextHolder.getBean("redisTemplate");
        }
        return redisTemplate;
    }
}
