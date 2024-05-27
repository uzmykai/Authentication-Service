package org.uz.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.uz.model.User;

import java.util.HashMap;

public interface JWTService {
    String extractUsername(String token);

    String generateToken(UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);

    String generateRefreshToken(HashMap<String, Object> map , UserDetails userDetails);
}
