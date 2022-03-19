package me.flab.loginjoinAPI.security;


import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {
    private static String secretKey = Base64.getEncoder().encodeToString("010AERC4395WERCCXZ71DFGCB30djhaasdfh231234dsajklfkjghjxdsada".getBytes());

    public String createToken(String userId,String password){
        Claims claims = Jwts.claims(); // 클레임을 만든다.
        claims.put("userId",userId);  // 로그인이 성공한 사용자의 id
        claims.put("password",password); // 로그인에 성공한 사용자의 비밀번호
        Date now = new Date(); // 생성날짜를 기준으로 유효날짜를 정하기 위해 생성된 일시를 저장한다.



        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; // token 암호화에 필요한 알고리즘을 선택하여 객체에 저장한다.
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(secretKey); // 암호화에 필요한 key를 byte 배열로 만든다
        Key signingKey = new SecretKeySpec(secretKeyBytes,signatureAlgorithm.getJcaName()); // 바이트로 변환된 시크릿 키와 사용할 알고리즘을 Key객체에 담는다.

        return Jwts.builder()
                .setHeaderParam(Header.JWT_TYPE,Header.TYPE)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + 1000L*60 * 30))
                .signWith(signingKey)
                .compact();
    }


    public boolean validateToken(String jwtToken){
        try{
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                    .build().parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        }catch(Exception e){
            return false;
        }
    }

    public Claims getInformation(String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .build().parseClaimsJws(token).getBody();
        return claims;
    }
}