package com.hamitmizrak.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

// LOMBOK
@Log4j2

// Json(Javascript Object Notition) Web Token
// JWT 3 ksıımdan oluşmaktadır.
    /*
    1- Header(Başlık)  : alg,type
    2- Payload(yük)    : admin,name
    3- Signature(imza) : header+payload
     */

// Spring IOC konteynarı tarafından yönetilmesine izin vermek
// Diğer sınıflar üzerinden enject(injection) etme özelliğini kazandırır.
@Component
public class _7_JwtUtils {

    // FIELD
    // 1-) HEADER(BAŞLIK)
    // JWT gizli bir anahtar türü : HS512
    private final SecretKey jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    // JWT geçerlilik süresi 1 gün =24*60*60*1000 =86.400.000
    private final long jwtExpirationMs = 86400000L;

    // Kullanıcı bilgisiyle  adı JWT oluştur
    public String generateJwtToken(String username) {
        return Jwts.builder()
                .setSubject(username) // JWT kullanıcı
                .setIssuedAt(new Date()) // Token oluşturma tarihi
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))   // Token geçerlilik süresi
                .signWith(jwtSecret) // Tokeni oluşturmak ve gizli anahtar ile imzalamak
                .compact();        // JWT artık oluştur ve String olarak dönder
    }


    // Kullanıcı adını ayrıştırmak
    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret) // Doğrulama için gizli anahtarı kullan
                .build()
                .parseClaimsJws(token)// Toek ayrıştırı ve doğrular
                .getBody() // Token gövdesini almak
                .getSubject(); // Gövdede ki kullanıcı adını almak

    }

    // Jwt'nin geçerli olup olmadığını kontrol eder.
    public boolean isValidJwtToken(String token) {
        try {
            // Token ayrıştırmak ve imzalamasını doğrulamak
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token);
            return true;// token geçersliysefalse dönder
        } catch (Exception e) {
            System.out.println("Jwt hatası" + e.getMessage());
            System.err.println("Jwt hatası" + e.getMessage());
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return false; // token geçersizse false dönder
    }

} //end _7_JwtUtils
