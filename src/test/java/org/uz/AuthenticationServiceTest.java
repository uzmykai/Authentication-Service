package org.uz;

import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.uz.model.User;
import org.uz.model.request.SignUpRequest;
import org.uz.model.response.BaseResponse;
import org.uz.repository.UserRepository;
import org.uz.service.impl.AuthenticateServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    MongoTemplate mongoTemplate;
    @Mock
    BaseResponse response;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    AuthenticateServiceImpl authenticateService;

    SignUpRequest signUpRequest;
    User user;


    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        signUpRequest = new SignUpRequest();
        signUpRequest.setIp("172.16.24.20");
        signUpRequest.setName("Donald James");
        signUpRequest.setPassword("password1234");

        user = new User();
        user.setUsername("Donald James");
        user.setUserId("1234r4e44");
        user.setIp("172.16.24.20");
    }

    @Test
    public void whenSignUpIsCalled_returnOk(){
        ResponseEntity<BaseResponse> res = authenticateService.signUp(signUpRequest);
        assertEquals(res.getStatusCode(), HttpStatus.OK);
    }
}
