package az.blog.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;

@Component
public class TokenProvider {

    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    public static String generateToken(String email) {
        return JWT.create()
                .withSubject(email)
                .withIssuer(JwtSecurityConstant.ISSUER)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtSecurityConstant.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(JwtSecurityConstant.SECRET_KEY.getBytes()));
    }

    public Authentication getAuthentication(String token) {
        User principal = new User(getSubject(token), "", Collections.emptyList());
        return new UsernamePasswordAuthenticationToken(principal, token, null);
    }

    public static String getSubject(String token) {
        return JWT
                .require(Algorithm.HMAC512(JwtSecurityConstant.SECRET_KEY.getBytes()))
                .build()
                .verify(token.replace(JwtSecurityConstant.TOKEN_PREFIX, ""))
                .getSubject();
    }

}
