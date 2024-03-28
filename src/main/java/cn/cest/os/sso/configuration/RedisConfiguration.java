package cn.cest.os.sso.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @ClassName RedisConfiguration
 * @Author cylin
 * @Date 2024/3/27 10:13
 * @Version 1.0
 * @Description redis配置类
 **/
@Configuration
@Slf4j
public class RedisConfiguration {

    //创建RedisTemplate对象
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConfiguration){
        RedisTemplate redisTemplate = new RedisTemplate();
        //设置redis的连接工厂对象
        redisTemplate.setConnectionFactory(redisConfiguration);
        //设置redis key的序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
