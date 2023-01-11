package com.apiadminpage.service.redis;

import com.apiadminpage.environment.Constant;
import com.apiadminpage.service.jwt.JWTService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    private static final Logger logger = Logger.getLogger(RedisService.class);

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private JWTService jwtService;

    public String getValueFromRedis(String keyName, String value, String expire, String typeTime, String process) {
        String value_redis = null;
        long expireTime = 0;

        // get key redis
        logger.info("Query key: " + keyName);
        value_redis = redisTemplate.opsForValue().get(keyName);
        logger.info("Get Redis value from =====> Redis server : " + value_redis);

        if (value_redis == null) {
            logger.warn("Redis key " + keyName + " not found, refreshing token");
            try {
                //refreshToken
                switch (process){
                    case Constant.REDIS_PROCESS_LOGIN :
                        value_redis = refreshTokenLogin(Integer.parseInt(value));
                        break;
                    default:
                        logger.info("type process invalid!");
                }

                //Save New Redis Token
                expireTime = Long.parseLong(expire);
                redisTemplate.opsForValue().set(keyName, value_redis, expireTime, TimeUnit.valueOf(typeTime));
            } catch (Exception ex) {
                logger.error(String.format(Constant.THROW_EXCEPTION, ex.getMessage()));
            }
        }

        return value_redis;
    }

    public String refreshTokenLogin(Integer accountId){
        return jwtService.generateToken(accountId);
    }
}
