package apptive.pieceOfCake.users.controller;

import apptive.pieceOfCake.base.impl.BaseControllerImpl;
import apptive.pieceOfCake.base.impl.BaseServiceImpl;
import apptive.pieceOfCake.users.model.User;
import apptive.pieceOfCake.users.model.request.UserCheckSameEmail;
import apptive.pieceOfCake.users.model.request.UserRequest;
import apptive.pieceOfCake.users.model.request.UserUpdateRequest;
import apptive.pieceOfCake.users.model.response.UserMyPageResponse;
import apptive.pieceOfCake.users.model.response.UserResponse;
import apptive.pieceOfCake.users.repository.UserRepository;
import apptive.pieceOfCake.users.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserControllerImpl extends BaseControllerImpl<User, UserResponse, UserRepository> implements UserController{

    private final UserService userService;

    public UserControllerImpl(BaseServiceImpl<User, UserResponse, UserRepository> baseService, UserService userService) {
        super(baseService);
        this.userService = userService;
    }

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

    @Override
    @GetMapping("/checkEmail")
    public ResponseEntity<String> checkSameEmail(UserCheckSameEmail userCheckSameEmail) {

        String msg = userService.checkSameEmail(userCheckSameEmail);

        return ResponseEntity.ok(msg);
    }
}

