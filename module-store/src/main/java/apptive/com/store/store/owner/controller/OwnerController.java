package apptive.com.store.store.owner.controller;

import apptive.com.store.auth.login.domain.request.OwnerLoginRequest;
import apptive.com.store.auth.login.domain.response.OwnerLoginResponse;
import apptive.com.store.store.model.request.StoreOwnerSignupRequest;
import apptive.com.store.store.owner.service.OwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
