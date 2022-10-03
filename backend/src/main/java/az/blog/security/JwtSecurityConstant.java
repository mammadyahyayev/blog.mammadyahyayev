package az.blog.security;

public class JwtSecurityConstant {
    static final String SECRET_KEY = "client-tracking";
    static final long EXPIRATION_TIME = 5 * 60 * 1000;
    static final String TOKEN_PREFIX = "Bearer ";
    static final String AUTHORIZATION = "Authorization";
    static final String ISSUER = "Mammad Yahyayev";
}
