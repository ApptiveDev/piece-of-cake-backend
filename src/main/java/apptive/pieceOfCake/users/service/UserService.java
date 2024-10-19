package apptive.pieceOfCake.users.service;

import apptive.pieceOfCake.users.model.request.UserRequest;
import apptive.pieceOfCake.users.model.request.UserUpdateRequest;
import apptive.pieceOfCake.users.model.response.UserMyPageResponse;

public interface UserService {

    Long save(UserRequest userRequest); // 회원가입
    UserMyPageResponse find(Long userID); // 유저 찾기
    UserMyPageResponse update(Long userID, UserUpdateRequest updateRequest); // 정보 업데이트
}
