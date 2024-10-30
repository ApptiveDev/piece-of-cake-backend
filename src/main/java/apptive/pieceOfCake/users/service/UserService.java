package apptive.pieceOfCake.users.service;

import apptive.pieceOfCake.base.BaseService;
import apptive.pieceOfCake.users.model.User;
import apptive.pieceOfCake.users.model.request.UserCheckSameEmail;
import apptive.pieceOfCake.users.model.request.UserRequest;
import apptive.pieceOfCake.users.model.request.UserUpdateRequest;
import apptive.pieceOfCake.users.model.response.UserMyPageResponse;
import apptive.pieceOfCake.users.model.response.UserResponse;
import apptive.pieceOfCake.users.repository.UserRepository;

public interface UserService extends BaseService<User, UserResponse, UserRepository> {

    Long save(UserRequest userRequest); // 회원가입
    UserMyPageResponse find(Long userID); // 유저 찾기
    UserMyPageResponse update(Long userID, UserUpdateRequest updateRequest); // 정보 업데이트
    String checkSameEmail(UserCheckSameEmail userCheckSameEmail);
}
