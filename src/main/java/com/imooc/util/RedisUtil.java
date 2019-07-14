package com.imooc.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: RedisUtil
 * @date 2019/7/13 16:11
 */
@Slf4j
public class RedisUtil {

    @Autowired
    private static StringRedisTemplate stringRedisTemplate;

    private static final long TIME_OUT=3600;



    //像redis里面写入数据  <String ,String>
    public static boolean set(String key ,String value,long timeOut){
        boolean result=false;
        try{
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            ops.set(key,value,timeOut, TimeUnit.SECONDS);
            result=true;
        }catch (Exception e){
            log.error("数据写入redis失败："+e.getMessage());
        }
        return result;
    }

    //像redis里面写入数据  <String ,String>
    public static boolean set(String key ,String value){
        return set(key,value ,TIME_OUT );
    }


    //从redis里面获取数据
    public static String get(String key){
        String s=null;
        try{
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            s = ops.get(key);
        }catch (Exception e){
            log.error("从redis获取数据失败："+e.getMessage());
        }
        return s;
    }
}
