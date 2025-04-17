package com.proyectointegrado.skillswap.conf;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "4050c1d41ca1b74c9302e5453d70b7a1206b53772652ee031b266a132dd9ee4aaa6cff4e4ad30aea7d549c9348226db7c13042aed51b09a3f7529016b16c857ddbeefa374ad05d281b58b0bfbb5ea2f710d50067538d03599ee899c41a21e3d126e0363aa73375e64b640fad88fb473042bb9adb5a63f101f9d34677efcd409bbe9a5a90afce5ee043ed715ab0552c95862aab7499697efd22c675d79f199437db81712a9af6f442967226168a2c6f227a874795650f3d2de1f19e375f31d7562a9ba13e5e6e907b5304c8d77fa29e22c39832929a9573d4e0e6d19a229324f46317f49ca3ed201daff4c9ad18e1e579d9398dfc3bbe5ac26ceca4fe913cd967";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24 ))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Algoritmo de firma
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts // Clase principal de jjwt (entrada de la librería)
                .parserBuilder() // Crea un parser para el token
                .setSigningKey(getSigningKey()) // Establece la clave de firma para validar el token
                .build() // Construye el parser
                .parseClaimsJws(token) // Lee y valida el token JWT, si es válido lo decodifica devolviendo el objeto Jws<Claims>
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY); // Decodifica la clave secreta en Base64
        return Keys.hmacShaKeyFor(keyBytes); // Crea una clave secreta para firmar el token
    }

}
