package apptive.pieceOfCake.auth.controller;

import apptive.pieceOfCake.auth.model.TokenDto;
import apptive.pieceOfCake.auth.model.TokenRequestDto;
import apptive.pieceOfCake.auth.model.request.JoinRequest;
import apptive.pieceOfCake.auth.model.request.LoginRequest;
import apptive.pieceOfCake.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody JoinRequest joinRequest) {
        return ResponseEntity.ok(authService.signup(joinRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }
}
