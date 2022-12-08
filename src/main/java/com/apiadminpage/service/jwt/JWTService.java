package com.apiadminpage.service.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

@Service
public class JWTService {
    private static final Logger logger = Logger.getLogger(JWTService.class);

    @Value("${jwt.secretKey}")
    private String secretKey;

    public String generateToken(Integer accountId) {
        logger.info("start generate token");
        logger.info("generate token account id : " + accountId);
        Calendar currentDate = Calendar.getInstance();
        Date date = currentDate.getTime();

        SecretKey key = Keys.hmacShaKeyFor(this.secretKey.getBytes(StandardCharsets.UTF_8));
        logger.info("done generate token");
        return Jwts.builder()
                .claim("accountId", accountId)
                .setIssuedAt(date)
                .signWith(key, SignatureAlgorithm.HS256).compact();
    }
}
