package org.uz.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.uz.model.User;
import org.uz.model.request.RefreshTokenRequest;
import org.uz.model.request.SignInRequest;
import org.uz.model.request.SignUpRequest;
import org.uz.model.response.BaseResponse;
import org.uz.model.response.JWTAuthenticationResponse;
import org.uz.repository.UserRepository;
import org.uz.service.AuthenticateService;
import org.uz.security.JWTService;
import org.uz.utility.Constant;
import java.util.HashMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticateServiceImpl implements AuthenticateService {

    private  final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private  final JWTService jwtService;
    @Override
    public ResponseEntity<BaseResponse> signUp(SignUpRequest signUpRequest){
        userRepository.save(createUserObject(signUpRequest));
        return response(Constant.SUCCESS_CODE,Constant.SUCCESS_DESCRIPTION,
                null, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<BaseResponse> signIn(SignInRequest signInRequest){
        authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(signInRequest.getUsername(),
                signInRequest.getPassword()));
        var  user = userRepository.findUserByUserName(signInRequest.getUsername());
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JWTAuthenticationResponse jwtAuthenticationResponse
                =new JWTAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);

        return response(Constant.SUCCESS_CODE,Constant.SUCCESS_DESCRIPTION
                ,jwtAuthenticationResponse, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<BaseResponse> refreshToken(RefreshTokenRequest refreshTokenRequest){

        String username = jwtService.extractUsername(
                refreshTokenRequest.getToken());
        User user = userRepository.findUserByUserName(username);
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user)){
            var jwt = jwtService.generateToken(user);
            JWTAuthenticationResponse jwtAuthenticationResponse
                    =new JWTAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());

            return response(Constant.SUCCESS_CODE,Constant.SUCCESS_DESCRIPTION,
                    jwtAuthenticationResponse, HttpStatus.OK);
        }
        return null;
    }
    private User createUserObject(SignUpRequest signUpRequest){
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setIp(signUpRequest.getIp());
        user.setUsername(signUpRequest.getName());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        return user;
    }
    private ResponseEntity<BaseResponse> response(String code, String description,
                                                 Object data, HttpStatus httpStatus){
        BaseResponse response = new BaseResponse();
        response.setCode(code);
        response.setDescription(description);
        response.setData(data);
        return new ResponseEntity<>(response, httpStatus);
    }

}
