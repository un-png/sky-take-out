package com.sky.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Slf4j
public class RedisConfiguration {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        //设置redis连接工厂对象
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //使用redis key的序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //使用redis value的序列化器
        //redisTemplate.setValueSerializer(new StringRedisSerializer());
        //支持set多种类型的值的序列化
        //redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        return redisTemplate;

    }
}
