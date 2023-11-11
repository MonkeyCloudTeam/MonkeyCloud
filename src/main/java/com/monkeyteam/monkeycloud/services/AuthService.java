package com.monkeyteam.monkeycloud.services;

import com.monkeyteam.monkeycloud.dtos.JwtRequest;
import com.monkeyteam.monkeycloud.dtos.JwtResponse;
import com.monkeyteam.monkeycloud.dtos.RegistrationUserDto;
import com.monkeyteam.monkeycloud.dtos.UserDto;
import com.monkeyteam.monkeycloud.entities.User;
import com.monkeyteam.monkeycloud.exeptions.AppError;
import com.monkeyteam.monkeycloud.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    /*public void userValidation (@RequestBody JwtRequest authRequest) throws AppError {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new AppError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль");
        }
    }

    public ResponseEntity<?> createAuthToken(@RequestBody String username) {
        UserDetails userDetails = userService.loadUserByUsername(username);
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    public ResponseEntity<?> userAuthentification(@RequestBody JwtRequest authRequest){
        try {
            userValidation(authRequest);
        } catch ( AppError error){
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }
        return createAuthToken(authRequest.getUsername());
    }*/

    public ResponseEntity<?> userAuthentification(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        System.out.println("Пользователь зашел");
        return ResponseEntity.ok(new JwtResponse(token));
    }

    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {

        if (userService.findByUsername(registrationUserDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пользователь с указанным именем уже существует"), HttpStatus.BAD_REQUEST);
        }
        User user = userService.createNewUser(registrationUserDto);
        return ResponseEntity.ok(new UserDto(user.getUser_id(), user.getUsername()));
    }
}
