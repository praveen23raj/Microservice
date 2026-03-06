package com.example.User.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.User.entity.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class JwtService {


    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiration.time}")
    private long expirationTime;

    private Algorithm algorithm;

    private static final String USER_NAME = "username"; // Use consistent naming convention


    @PostConstruct
    public void postConstruct() throws UnsupportedEncodingException {
        algorithm=algorithm.HMAC256(algorithmKey);
    }



    public String generateToken(User user) {
        return JWT.create()
                .withClaim(USER_NAME, user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .withIssuer(issuer)
                .sign(algorithm);
    }


    public String getUsername(String token){
        DecodedJWT decodedJwt=JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return decodedJwt.getClaim(USER_NAME).asString();
    }
    

}