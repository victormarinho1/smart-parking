package com.fatec.smart_parking.core;

import com.fatec.smart_parking.core.authentication.LoginDTO;
import com.fatec.smart_parking.core.authentication.LoginResponseDTO;
import com.fatec.smart_parking.core.authentication.RegisterDTO;
import com.fatec.smart_parking.core.config.TokenService;
import com.fatec.smart_parking.user.User;
import com.fatec.smart_parking.user.UserRepository;
import com.fatec.smart_parking.user.UserValidator;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class ApplicationUserController{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ApplicationUserService applicationUserService;



    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDTO loginDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.email(),loginDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO registerDTO){
        userValidator.checkEmailExists(registerDTO.email());
        userValidator.checkEmailValidity(registerDTO.email());

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        User newUser = new User(registerDTO.email(),registerDTO.name(), encryptedPassword);

        this.userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password/{email}")
    public ResponseEntity resetPassword(@PathVariable @Valid String email){
        this.applicationUserService.resetPassword(email);
    return  ResponseEntity.ok().build();
    }
}