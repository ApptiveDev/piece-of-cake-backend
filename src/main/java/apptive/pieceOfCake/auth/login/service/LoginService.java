package apptive.pieceOfCake.auth.login.service;

import apptive.pieceOfCake.auth.RoleName;
import apptive.pieceOfCake.auth.jwt.JwtProvider;
import apptive.pieceOfCake.auth.login.domain.request.MemberLoginRequest;
import apptive.pieceOfCake.auth.login.domain.response.MemberLoginResponse;
import apptive.pieceOfCake.users.exception.MemberException;
import apptive.pieceOfCake.users.exception.MemberExceptionType;
import apptive.pieceOfCake.users.model.Member;
import apptive.pieceOfCake.users.model.request.MemberRequest;
import apptive.pieceOfCake.users.model.response.MemberMyPageResponse;
import apptive.pieceOfCake.users.repository.MemberRepository;
import apptive.pieceOfCake.users.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public Long save(MemberRequest memberRequest) {

        Member user = Member.builder()
                .email(memberRequest.getEmail())
                .loginPwd(bCryptPasswordEncoder.encode(memberRequest.getLoginPwd()))
                .name(memberRequest.getName())
                .phoneNum(memberRequest.getPhoneNum())
                .address(memberRequest.getAddress())
                .latitude(memberRequest.getLatitude())
                .longitude(memberRequest.getLongitude())
                .agreementOfMarketing(memberRequest.isAgreementOfMarketing())
                .roleName(RoleName.ROLE_USER)
                .build();

        memberRepository.save(user);

        return user.getId();
    }

    @Transactional
    public MemberLoginResponse memberLogin(MemberLoginRequest loginRequest) {
        // 사용자 정보 조회
        MemberMyPageResponse userInfo = memberService.findByEmail(loginRequest.getLoginId());

        // password 일치 여부 체크
        if(!bCryptPasswordEncoder.matches(loginRequest.getLoginPwd(), userInfo.loginPwd()))
            throw new MemberException(MemberExceptionType.WRONG_PASSWORD);

        // jwt 토큰 생성
        String accessToken = jwtProvider.generateAccessToken(userInfo.userId());

        return MemberLoginResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
