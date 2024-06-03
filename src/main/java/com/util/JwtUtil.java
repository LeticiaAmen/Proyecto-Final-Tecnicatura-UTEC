package com.util;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.algorithms.Algorithm;
public class JwtUtil {
   private static final String SECRET_KEY = "tuClaveSecreta";
   public static String generateToken(Long userId, String username, String role) {
       Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
       return JWT.create()
               .withIssuer("auth0")
               .withClaim("usuarioId", userId.toString())
               .withClaim("username", username)
               .withClaim("role", role)
               .sign(algorithm);
   }
   public static DecodedJWT verifyToken(String token) throws JWTVerificationException {
       Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
       JWTVerifier verifier = JWT.require(algorithm)
                                 .withIssuer("auth0")
                                 .build();
       return verifier.verify(token);
   }
}

