package org.uz.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.uz.model.request.RefreshTokenRequest;
import org.uz.model.request.SignInRequest;
import org.uz.model.request.SignUpRequest;
import org.uz.model.response.BaseResponse;
import org.uz.service.AuthenticateService;

@RestController
@RequestMapping("api/v1/auth")@RequiredArgsConstructor
public class AuthenticationController {

    private  final AuthenticateService authenticateService;

    @PostMapping(path =  "/signUp", consumes= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> signUp(@RequestBody  @Valid SignUpRequest signUpRequest) throws Exception {
        return authenticateService.signUp(signUpRequest);
    }

    @PostMapping("/signIn")
    public ResponseEntity<BaseResponse> signIn(@RequestBody @Valid SignInRequest signInRequest){
        return authenticateService.signIn(signInRequest);
    }

    @PutMapping("/refresh")
    public ResponseEntity<BaseResponse> refreshToken(
            @RequestBody RefreshTokenRequest refreshTokenRequest){
        return authenticateService.refreshToken(refreshTokenRequest);
    }
}
