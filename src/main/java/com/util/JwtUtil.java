package com.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

public class JwtUtil {
    private static final String SECRET_KEY = "tuClaveSecreta"; // Debes usar una clave secreta propia y segura
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

    public static DecodedJWT verifyToken(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("auth0") 
                .build(); 
        return verifier.verify(token);
    }
}