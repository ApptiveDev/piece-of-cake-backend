package apptive.pieceOfCake.users.controller;

import apptive.pieceOfCake.base.BaseController;
import apptive.pieceOfCake.users.model.User;
import apptive.pieceOfCake.users.model.request.UserCheckSameEmail;
import apptive.pieceOfCake.users.model.request.UserRequest;
import apptive.pieceOfCake.users.model.request.UserUpdateRequest;
import apptive.pieceOfCake.users.model.response.UserMyPageResponse;
import apptive.pieceOfCake.users.model.response.UserResponse;
import apptive.pieceOfCake.users.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserController extends BaseController<User, UserResponse, UserRepository> {

    ResponseEntity<Long> save(UserRequest userRequest); // 회원가입
    ResponseEntity<UserMyPageResponse> find(@PathVariable Long userId); // 마이페이지
    ResponseEntity<UserMyPageResponse> update(UserUpdateRequest userUpdateRequest, @PathVariable Long userId); // 업데이트
    ResponseEntity<String> checkSameEmail(@RequestBody UserCheckSameEmail userCheckSameEmail); // 중복 이메일 확인
}
