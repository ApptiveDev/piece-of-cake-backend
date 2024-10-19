package apptive.pieceOfCake.users.service;

import apptive.pieceOfCake.users.exception.UserException;
import apptive.pieceOfCake.users.model.User;
import apptive.pieceOfCake.users.model.request.UserRequest;
import apptive.pieceOfCake.users.model.request.UserUpdateRequest;
import apptive.pieceOfCake.users.model.response.UserMyPageResponse;
import apptive.pieceOfCake.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static apptive.pieceOfCake.users.exception.UserExceptionType.NOT_FOUND_MEMBER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Long save(UserRequest userRequest) {

        User user = User.builder()
                .loginId(userRequest.getLoginId())
                .loginPwd(userRequest.getLoginPwd())
                .name(userRequest.getName())
                .phoneNum(userRequest.getPhoneNum())
                .email(userRequest.getEmail())
                .build();

        userRepository.save(user);

        return user.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public UserMyPageResponse find(Long userID) {

        User user = userRepository.findById(userID)
                .orElseThrow(() -> new UserException(NOT_FOUND_MEMBER));

        UserMyPageResponse userMyPageResponse = new UserMyPageResponse();
        userMyPageResponse.setName(user.getName());
        userMyPageResponse.setPhoneNum(user.getPhoneNum());
        userMyPageResponse.setEmail(user.getEmail());

        return userMyPageResponse;
    }

    @Override
    @Transactional
    public UserMyPageResponse update(Long userID, UserUpdateRequest updateRequest) {

        User user = userRepository.findById(userID)
                .orElseThrow(() -> new UserException(NOT_FOUND_MEMBER));

        user.setPhoneNum(updateRequest.getPhoneNum());
        user.setEmail(updateRequest.getEmail());

        userRepository.save(user);

        UserMyPageResponse userMyPageResponse = new UserMyPageResponse();
        userMyPageResponse.setName(user.getName());
        userMyPageResponse.setPhoneNum(user.getPhoneNum());
        userMyPageResponse.setEmail(user.getEmail());

        return userMyPageResponse;
    }
}
