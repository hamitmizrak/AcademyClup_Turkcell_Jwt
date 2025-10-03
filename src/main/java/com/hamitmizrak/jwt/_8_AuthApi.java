package com.hamitmizrak.jwt;

import com.hamitmizrak.bean.PasswordEncoderBean;
import com.hamitmizrak.error.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

// LOMBOK
@RequiredArgsConstructor
@Log4j2

// http://localhost:4444/api/auth

// REST
@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class _8_AuthApi {

    // Field
    private final _5_IUserRepository iUserRepository;
    private final _7_JwtUtils jwtUtils;
    private final PasswordEncoderBean passwordEncoderBean;
    private final AuthenticationManager authenticationManager;

    // Field
    private ApiResult apiResult;


    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<ApiResult> userRegister(@RequestBody _2_RegisterRequest registerRequest) {

        // UserEntity Instance
        _4_UserEntity userEntity; userEntity = new _4_UserEntity();

        // SET
        userEntity.setUsername(registerRequest.getUsername());
        userEntity.setPassword(passwordEncoderBean.getPasswordEncoderBeanMethod()
                        .encode(registerRequest.getPassword())
                );
        userEntity.setRole(_3_Role.USER);
        // Kayıt
        iUserRepository.save(userEntity);

        // ApiResult
        ApiResult apiResult = ApiResult.builder()
                .path("/api/auth/register")
                .status(201)
                .message("Register successfully!")
                .createdDate(new Date())
                .build();
        return ResponseEntity.ok().body(apiResult);
    }


    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<ApiResult> userRegister(@RequestBody _1_LoginRequest loginRequest) {

        // Kullanıcı Bilgilerini almak
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),loginRequest.getPassword()
                )
        ); // end authentication

        // TOKEN
        String jwtToken = jwtUtils.generateJwtToken(authentication.getName());
        System.out.println("JWT Token: " + jwtToken);
        System.out.printf("JWT Token:%s " , jwtToken);
        //log.info("JWT Token: " + jwtToken);

        StringBuilder stringBuilder= new StringBuilder();
        stringBuilder.append("Login successfully!").append("jwt ==> ").append(jwtToken);

        // ApiResult
        ApiResult apiResult = ApiResult.builder()
                .path("/api/auth/login")
                .status(201)
                .message(stringBuilder.toString())
                .createdDate(new Date())
                .build();
        return ResponseEntity.ok().body(apiResult);
    }

} // end _8_AuthApi
