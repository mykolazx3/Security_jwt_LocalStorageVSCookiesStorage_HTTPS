package com.mykola.crm.controller.authentication;

import com.mykola.crm.dto.authentication.LoginRequest;
import com.mykola.crm.dto.authentication.RegistrationRequest;
import com.mykola.crm.dto.authentication.TokenResponse;
import com.mykola.crm.model.User;
import com.mykola.crm.service.authentication.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody @Valid RegistrationRequest registrationRequest) {
        TokenResponse jwtToken = authenticationService.register(registrationRequest);
        return ResponseEntity.ok(jwtToken);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        TokenResponse jwtToken = authenticationService.authenticate(loginRequest);
        return ResponseEntity.ok(jwtToken);
    }


    @GetMapping("refresh-token")
    public ResponseEntity<TokenResponse> refreshToken(@AuthenticationPrincipal User currentUser) {
        TokenResponse jwtToken = authenticationService.refreshToken(currentUser);
        return ResponseEntity.ok(jwtToken);
    }
}
