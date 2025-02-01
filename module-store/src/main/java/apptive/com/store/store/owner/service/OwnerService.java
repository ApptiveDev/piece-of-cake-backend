package apptive.com.store.store.owner.service;

import apptive.com.common.auth.RoleName;
import apptive.com.store.auth.jwt.JwtProvider;
import apptive.com.store.auth.login.domain.request.OwnerLoginRequest;
import apptive.com.store.auth.login.domain.response.OwnerLoginResponse;
import apptive.com.common.store.exception.StoreException;
import apptive.com.store.store.model.Store;
import apptive.com.store.store.model.request.StoreOwnerSignupRequest;
import apptive.com.store.store.model.response.StoreOwnerInfo;
import apptive.com.store.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static apptive.com.common.store.exception.StoreExceptionType.NOT_FOUND_OWNER;
import static apptive.com.common.store.exception.StoreExceptionType.WRONG_PASSWORD;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final StoreRepository storeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public Long save(StoreOwnerSignupRequest storeOwnerSignupRequest) {

        Store store = Store.builder()
                .loginId(storeOwnerSignupRequest.getLoginId())
                .loginPwd(bCryptPasswordEncoder.encode(storeOwnerSignupRequest.getLoginPwd()))
                .roleName(RoleName.ROLE_STORE)
                .ownerName(storeOwnerSignupRequest.getOwnerName())
                .phoneNum(storeOwnerSignupRequest.getPhoneNum())
                .build();

        storeRepository.save(store);

        return store.getId();
    }

    @Transactional
    public OwnerLoginResponse login(OwnerLoginRequest loginRequest) {
        // 사용자 정보 조회
        StoreOwnerInfo userInfo = findByLoginId(loginRequest.getLoginId());

        // password 일치 여부 체크
        if(!bCryptPasswordEncoder.matches(loginRequest.getLoginPwd(), userInfo.loginPwd()))
            throw new StoreException(WRONG_PASSWORD);

        // jwt 토큰 생성
        String accessToken = jwtProvider.generateAccessToken(userInfo.storedId());

        return OwnerLoginResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    public boolean checkLoginIdDuplication(String loginId) {
        return storeRepository.existsByLoginId(loginId);
    }

    // ----------- static method -----------
    public StoreOwnerInfo findByLoginId(String loginId) {
        return storeRepository.findByLoginId(loginId)
                .map(StoreOwnerInfo::of)
                .orElseThrow(() -> new StoreException(NOT_FOUND_OWNER));
    }
}
