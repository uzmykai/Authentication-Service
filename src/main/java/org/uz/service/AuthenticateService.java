package org.uz.service;

import org.uz.model.User;
import org.uz.model.request.RefreshTokenRequest;
import org.uz.model.request.SignInRequest;
import org.uz.model.request.SignUpRequest;
import org.uz.model.response.JWTAuthenticationResponse;

public interface AuthenticateService {
    User signUp(SignUpRequest signUpRequest);
    JWTAuthenticationResponse signIn(SignInRequest signInRequest);
    JWTAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
