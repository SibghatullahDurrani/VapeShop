package com.sibghat.vape_shop.config;


import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class CustomeJwtAuthenticationTokenConverter implements Converter<Jwt, JwtAuthenticationToken> {


    @Override
    public JwtAuthenticationToken convert(Jwt source) {
        @SuppressWarnings(value = "unchecked")
        List<String> authorities = (List<String>) source.getClaims().get("roles");


        return new JwtAuthenticationToken(source, authorities.stream().map(SimpleGrantedAuthority::new).toList());
    }
}
