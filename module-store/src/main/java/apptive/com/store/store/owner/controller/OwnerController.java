package apptive.com.store.store.owner.controller;

import apptive.com.store.store.model.request.StoreOwnerSignupRequest;
import apptive.com.store.store.owner.service.OwnerService;
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

    @PostMapping("")
    public ResponseEntity<Long> save(@RequestBody StoreOwnerSignupRequest storeOwnerSignupRequest) {

        Long storeId = ownerService.save(storeOwnerSignupRequest);

        return new ResponseEntity<>(storeId, HttpStatus.CREATED);
    }
}
