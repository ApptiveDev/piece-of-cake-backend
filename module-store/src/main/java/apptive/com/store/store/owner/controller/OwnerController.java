package apptive.com.store.store.owner.controller;

import apptive.com.store.auth.login.domain.request.OwnerLoginRequest;
import apptive.com.store.auth.login.domain.response.OwnerLoginResponse;
import apptive.com.store.store.model.request.StoreOwnerCheckLoginId;
import apptive.com.store.store.model.request.StoreOwnerSignupRequest;
import apptive.com.store.store.owner.service.OwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping("/save")
    public ResponseEntity<Long> save(@Valid @RequestBody StoreOwnerSignupRequest storeOwnerSignupRequest) {

        Long storeId = ownerService.save(storeOwnerSignupRequest);

        return new ResponseEntity<>(storeId, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<OwnerLoginResponse> login(@RequestBody @Valid OwnerLoginRequest loginRequest) {
        // login 체크 후 token 생성
        var loginInfo = ownerService.login(loginRequest);

        return new ResponseEntity<>(loginInfo, HttpStatus.OK);
    }

    @GetMapping("/check")
    public ResponseEntity checkLoginId(@RequestBody @Valid StoreOwnerCheckLoginId storeOwnerCheckLoginId) {

        // 로그인 아이디 중복 여부 확인
        boolean isDuplicate = ownerService.checkLoginIdDuplication(storeOwnerCheckLoginId.getLoginId());

        // 응답을 Map에 담아서 반환
        Map<String, Object> response = new HashMap<>();
        response.put("isDuplicate", isDuplicate);

        return ResponseEntity.ok(response);
    }
}
