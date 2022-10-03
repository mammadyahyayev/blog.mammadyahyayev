package az.blog.security;

public class JwtSecurityConstant {
    public static final String SECRET_KEY = "client-tracking";
    public static final long EXPIRATION_TIME = 5 * 60 * 1000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";
    public static final String ISSUER = "Mammad Yahyayev";
}
