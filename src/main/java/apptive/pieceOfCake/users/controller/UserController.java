package apptive.pieceOfCake.users.controller;

import apptive.pieceOfCake.users.model.request.UserRequest;
import apptive.pieceOfCake.users.model.request.UserUpdateRequest;
import apptive.pieceOfCake.users.model.response.UserMyPageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface UserController {

    ResponseEntity<Long> save(UserRequest userRequest); // 회원가입
    ResponseEntity<UserMyPageResponse> find(@PathVariable Long userId); // 마이페이지
    ResponseEntity<UserMyPageResponse> update(UserUpdateRequest userUpdateRequest, @PathVariable Long userId); // 업데이트
}
