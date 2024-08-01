package indi.muleisy.ra.pub.jwt;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import indi.muleisy.ra.pub.config.Config;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.bson.Document;

import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class JwtUtil {

    private String secret;
    private long expiration;

    public static final JwtUtil INSTANCE = new JwtUtil(
        MongoClients.create(Config.INSTANCE.getMongoUri())
                ,Config.INSTANCE.getConfigDBName());

    public JwtUtil(MongoClient mongoClient, String databaseName) {
        getConfigFromDatabase(mongoClient, databaseName);
    }

    private void getConfigFromDatabase(MongoClient mongoClient, String databaseName) {
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection("config");

        Document config = collection.find().first();
        if (config != null) {
            Document jwtConfig = config.get("jwt", Document.class);
            if (jwtConfig != null) {
                this.secret = jwtConfig.getString("secret");
                this.expiration = jwtConfig.getLong("expiration");
            }
        }
    }

    public String generateToken(String userId, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)  // 设置签名密钥
                .build()                   // 构建JWS解析器
                .parseClaimsJws(token)     // 解析传入的token
                .getBody();                // 获取Claims体
    }

    public boolean validateToken(String token, String userId) {
        final String subject = getClaimsFromToken(token).getSubject();
        return (subject.equals(userId) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = getClaimsFromToken(token).getExpiration();
        return expiration.before(new Date());
    }

    public boolean isJwtValid(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(this.secret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String decryptJwt(String encryptedJwt, PublicKey publicKey) {
        try {
            // 解析JWT
            Claims claims = Jwts.parser()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(encryptedJwt)
                    .getBody();

            // 返回用户ID或其他信息
            return claims.getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting JWT", e);
        }
    }

    public PublicKey getPublicKey(byte[] keyBytes) {
        return (PublicKey) Keys.hmacShaKeyFor(keyBytes);
    }
}
