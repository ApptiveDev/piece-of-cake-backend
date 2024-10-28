package apptive.pieceOfCake.users.service;

import apptive.pieceOfCake.base.impl.BaseServiceImpl;
import apptive.pieceOfCake.users.exception.UserException;
import apptive.pieceOfCake.users.model.User;
import apptive.pieceOfCake.users.model.request.UserRequest;
import apptive.pieceOfCake.users.model.request.UserUpdateRequest;
import apptive.pieceOfCake.users.model.response.UserMyPageResponse;
import apptive.pieceOfCake.users.model.response.UserResponse;
import apptive.pieceOfCake.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static apptive.pieceOfCake.users.exception.UserExceptionType.NOT_FOUND_MEMBER;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, UserResponse, UserRepository> implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository repository, UserRepository userRepository) {
        super(repository);
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
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
