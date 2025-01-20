package apptive.pieceOfCake.auth.login.controller;

import apptive.pieceOfCake.auth.login.domain.request.MemberLoginRequest;
import apptive.pieceOfCake.auth.login.domain.response.MemberLoginResponse;
import apptive.pieceOfCake.auth.login.service.LoginService;
import apptive.pieceOfCake.users.model.request.MemberRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/user/save")
    public ResponseEntity<Long> save(@Valid @RequestBody MemberRequest memberRequest) {

        Long userId = loginService.save(memberRequest);
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    @PostMapping("/user/login")
    public ResponseEntity<MemberLoginResponse> login(@RequestBody @Valid MemberLoginRequest loginRequest) {
        // login 체크 후 token 생성
        var loginInfo = loginService.memberLogin(loginRequest);

        return new ResponseEntity<>(loginInfo, HttpStatus.OK);
    }
}
