package com.example.mvcbackend.sys.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.server.ServerWebExchange;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class JwtUtils {

    private static String SECRECT;
    private static Long EXPIRE;
    private final static String BEARER = "Bearer ";

    /**
     * 生成jwt token
     *
     * @param subject
     * @param authorities
     * @return
     */
    public static String generateJWT(String subject, Collection<? extends GrantedAuthority> authorities) {
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(subject)
                .issuer("zql")
                .expirationTime(new Date(new Date().getTime() + EXPIRE))
                .claim("roles", authorities.stream().map(GrantedAuthority.class::cast)
                        .map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                .build();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

        try {
            signedJWT.sign(new MACSigner(SECRECT));
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return signedJWT.serialize();
    }

    public static void setSECRECT(String SECRECT) {
        JwtUtils.SECRECT = SECRECT;
    }

    /**
     * 转换成Authentication
     *
     * @param request
     * @return
     */
    public static Authentication extract(HttpServletRequest request) {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = null;
        if (StringUtils.isNotBlank(authHeader)) {
            token = authHeader.substring(BEARER.length());
            try {
                SignedJWT signedJWT = SignedJWT.parse(token);
                if(signedJWT.getJWTClaimsSet().getExpirationTime().after(Date.from(Instant.now()))&&signedJWT.verify(new MACVerifier(SECRECT))){
                    String subject;
                    String auths;
                    List<GrantedAuthority> authorities;
                    subject = signedJWT.getJWTClaimsSet().getSubject();
                    auths = (String) signedJWT.getJWTClaimsSet().getClaim("roles");

                    authorities = Stream.of(auths.split(","))
                            .filter(StringUtils::isNotBlank)
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
                    return new UsernamePasswordAuthenticationToken(subject, null, authorities);
                }

            } catch (ParseException | JOSEException e) {
                e.printStackTrace();
            }
        }
        return  null;
    }

    public static void setEXPIRE(Long expire) {
        EXPIRE = expire;
    }
}
