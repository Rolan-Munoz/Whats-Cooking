package com.rolanmunoz.whatscooking.config.JWT;
import com.rolanmunoz.whatscooking.config.security.CustomUsersDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    private CustomUsersDetailsService customUsersDetailsService;

    @Autowired
    public JwtTokenProvider(CustomUsersDetailsService customUsersDetailsService) {
        this.customUsersDetailsService = customUsersDetailsService;
    }

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Long userId = customUsersDetailsService.getId(username);
        List<String> userRoles = customUsersDetailsService.getRoles(username);
        Date actuallyTime = new Date();
        Date expirationToken = new Date(actuallyTime.getTime() + SecurityConstantJWT.JWT_EXPIRATION_TOKEN);

        String token = Jwts.builder()
                .setSubject(username)
                .claim("id", userId)
                .claim("role", userRoles)
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
