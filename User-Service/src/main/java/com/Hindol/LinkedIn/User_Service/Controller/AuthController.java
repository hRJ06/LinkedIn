package com.Hindol.LinkedIn.User_Service.Controller;

import com.Hindol.LinkedIn.User_Service.DTO.LoginRequestDTO;
import com.Hindol.LinkedIn.User_Service.DTO.SignUpRequestDTO;
import com.Hindol.LinkedIn.User_Service.DTO.UserDTO;
import com.Hindol.LinkedIn.User_Service.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        UserDTO userDTO = authService.signUp(signUpRequestDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        String token = authService.login(loginRequestDTO);
        return ResponseEntity.ok(token);
    }
}
