package com.apiadminpage.service.jwt;

import com.apiadminpage.environment.Constant;
import com.apiadminpage.exception.ResponseException;
import com.apiadminpage.model.response.jwt.JwtResponse;
import io.jsonwebtoken.*;
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
        currentDate.add(Calendar.HOUR, 1);

        SecretKey key = Keys.hmacShaKeyFor(this.secretKey.getBytes(StandardCharsets.UTF_8));
        logger.info("done generate token");
        return Jwts.builder()
                .claim("accountId", accountId)
                .setIssuedAt(date)
                .setExpiration(currentDate.getTime())
                .signWith(key, SignatureAlgorithm.HS256).compact();
    }

    public JwtResponse checkAccessToken(String token) {
        JwtResponse jwtResponse = new JwtResponse();

        try {
            SecretKey key = Keys.hmacShaKeyFor(this.secretKey.getBytes(StandardCharsets.UTF_8));
            Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            jwtResponse.setId(jws.getBody().get("accountId").toString());
            jwtResponse.setIssueDate(jws.getBody().getIssuedAt());
            jwtResponse.setExpire(jws.getBody().getExpiration());

        } catch (ExpiredJwtException e) {
            logger.info(e.getMessage(), e);
            throw new ResponseException(Constant.CODE_RESPONSE_TOKEN_EXPIRE, Constant.MESSAGE_EXPIRE);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ResponseException(Constant.CODE_RESPONSE_INVALID_AUTH, Constant.MESSAGE_ERROR_TOKEN_INVALID);
        }

        return jwtResponse;
    }
}
