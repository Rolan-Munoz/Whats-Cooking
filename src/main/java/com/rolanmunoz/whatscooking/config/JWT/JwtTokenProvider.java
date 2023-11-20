package com.rolanmunoz.whatscooking.config.JWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtTokenProvider {

    //Generar Token
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date actuallyTime = new Date();
        Date expirationToken = new Date(actuallyTime.getTime() + SecurityConstantJWT.JWT_EXPIRATION_TOKEN);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expirationToken)
                .signWith(SignatureAlgorithm.HS512, SecurityConstantJWT.JWT_FIRMA)
                .compact();
        return token;
    }

    // extraer username a traves de un token

    public String getUsernameByJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstantJWT.JWT_FIRMA)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // Validar el token

    public Boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(SecurityConstantJWT.JWT_FIRMA).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            throw  new AuthenticationCredentialsNotFoundException("JWT expired o incorrect");
        }
    }

}
