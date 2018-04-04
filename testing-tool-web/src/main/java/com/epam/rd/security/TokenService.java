package com.epam.rd.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenService {

    private static Map<String, Authentication> cash = new ConcurrentHashMap<>();

    public TokenService() {
    }

    public String generateNewToken() {
        return UUID.randomUUID().toString();
    }

    public void store(String token, Authentication authentication) {
        cash.put(token, authentication);
    }

    public boolean contains(String token) {
        return cash.get(token) != null;
    }

    public Authentication retrieve(String token) {
        return (Authentication) cash.get(token);
    }

    public void deleteToken(String token){
        cash.remove(token);
    }

    public boolean containsAuthority(Authentication authentication){
        return cash.containsValue(authentication);
    }
}
