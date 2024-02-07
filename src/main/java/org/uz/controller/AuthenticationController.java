package org.uz.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.uz.model.User;
import org.uz.model.request.RefreshTokenRequest;
import org.uz.model.request.SignInRequest;
import org.uz.model.request.SignUpRequest;
import org.uz.model.response.JWTAuthenticationResponse;
import org.uz.service.AuthenticateService;

@RestController
@RequestMapping("api/v1/auth")@RequiredArgsConstructor
public class AuthenticationController {

    private  final AuthenticateService authenticateService;

    @PostMapping("/signUp")
    public ResponseEntity<User> signUp(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticateService.signUp(signUpRequest));
    }

    @PostMapping("/signIn")
    public ResponseEntity<JWTAuthenticationResponse> signIn(@RequestBody SignInRequest signInRequest){
        return ResponseEntity.ok(authenticateService.signIn(signInRequest));
    }

    @PutMapping("/refresh")
    public ResponseEntity<JWTAuthenticationResponse> refreshToken(
            @RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authenticateService.refreshToken(refreshTokenRequest));
    }
}
