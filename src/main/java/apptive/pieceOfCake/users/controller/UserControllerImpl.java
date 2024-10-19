package apptive.pieceOfCake.users.controller;

import apptive.pieceOfCake.users.model.request.UserRequest;
import apptive.pieceOfCake.users.model.request.UserUpdateRequest;
import apptive.pieceOfCake.users.model.response.UserMyPageResponse;
import apptive.pieceOfCake.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserControllerImpl implements UserController{

    private final UserService userService;

    @Override
    @PostMapping("/save")
    public ResponseEntity<Long> save(@Valid @RequestBody UserRequest userRequest) {

        Long userId = userService.save(userRequest);
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/myPage/{userId}")
    public ResponseEntity<UserMyPageResponse> find(@PathVariable("userId") Long userId) {

        UserMyPageResponse userMyPageResponse = userService.find(userId);
        return new ResponseEntity<>(userMyPageResponse, HttpStatus.OK);
    }

    @Override
    @PatchMapping("/update/{userId}")
    public ResponseEntity<UserMyPageResponse> update(@RequestBody UserUpdateRequest userUpdateRequest,
                                                     @PathVariable("userId") Long userId) {

        UserMyPageResponse userMyPageResponse = userService.update(userId, userUpdateRequest);
        return new ResponseEntity<>(userMyPageResponse, HttpStatus.OK);
    }
}
