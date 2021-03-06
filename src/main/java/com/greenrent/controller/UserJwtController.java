package com.greenrent.controller;

import com.greenrent.dto.request.LoginRequest;
import com.greenrent.dto.response.GRResponse;
import com.greenrent.dto.request.RegisterRequest;
import com.greenrent.dto.response.LoginResponse;
import com.greenrent.dto.response.ResponseMessage;
import com.greenrent.security.jwt.JwtUtils;
import com.greenrent.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
@AllArgsConstructor
public class UserJwtController {

    private UserService userService; // @AllArg ekledigimiz icin Autowired e gerek yok.
    private AuthenticationManager authManager;
    private JwtUtils jwtUtils;


//  @PostMapping("/register")
//  public ResponseEntity<Map<String,String>> createRegister(@RequestBody RegisterRequest registerRequest){
//      userService.register(registerRequest);
//      Map<String,String> map=new HashMap<>();
//      map.put("message","User Successfully created");
//      map.put("status", "true");
//      return new ResponseEntity<>(map, HttpStatus.CREATED);
//  }


    // 1- Register User
    @PostMapping("/register")
    public ResponseEntity<GRResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        userService.register(registerRequest);
        GRResponse response = new GRResponse();
        response.setMessage(ResponseMessage.REGISTER_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginRequest loginRequest) {


        Authentication authentication =  authManager.authenticate
                (new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));

       String token = jwtUtils.generateJwtToken(authentication);
       LoginResponse response = new LoginResponse();
       response.setToken(token);

       return new ResponseEntity<>(response, HttpStatus.OK);
    }




}
