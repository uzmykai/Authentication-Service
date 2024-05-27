package org.uz.service;

import org.springframework.http.ResponseEntity;
import org.uz.model.User;
import org.uz.model.request.RefreshTokenRequest;
import org.uz.model.request.SignInRequest;
import org.uz.model.request.SignUpRequest;
import org.uz.model.response.BaseResponse;
import org.uz.model.response.JWTAuthenticationResponse;

public interface AuthenticateService {
    ResponseEntity<BaseResponse> signUp(SignUpRequest signUpRequest);
    ResponseEntity<BaseResponse> signIn(SignInRequest signInRequest);
    ResponseEntity<BaseResponse> refreshToken(RefreshTokenRequest refreshTokenRequest);
}
