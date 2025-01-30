package apptive.com.member.auth.login.controller;

import apptive.com.member.auth.login.domain.request.MemberLoginRequest;
import apptive.com.member.auth.login.domain.response.MemberLoginResponse;
import apptive.com.member.auth.login.service.LoginService;
import apptive.com.member.users.model.request.MemberRequest;
import apptive.com.member.users.model.request.MemberUpdateRequest;
import apptive.com.member.users.model.response.MemberMyPageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/user/update/{userId}")
    public ResponseEntity<MemberMyPageResponse> update(@RequestBody @Valid MemberUpdateRequest memberUpdateRequest,
                                                       @PathVariable("userId") Long userId) {

        MemberMyPageResponse memberMyPageResponse = loginService.update(userId, memberUpdateRequest);
        return new ResponseEntity<>(memberMyPageResponse, HttpStatus.OK);
    }
}
