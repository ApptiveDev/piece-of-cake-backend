package apptive.com.member.auth.login.service;

import apptive.com.common.auth.RoleName;
import apptive.com.member.auth.jwt.JwtProvider;
import apptive.com.member.auth.login.domain.request.MemberLoginRequest;
import apptive.com.member.auth.login.domain.response.MemberLoginResponse;
import apptive.com.member.users.exception.MemberException;
import apptive.com.member.users.exception.MemberExceptionType;
import apptive.com.member.users.model.Member;
import apptive.com.member.users.model.request.MemberRequest;
import apptive.com.member.users.model.response.MemberMyPageResponse;
import apptive.com.member.users.repository.MemberRepository;
import apptive.com.member.users.service.MemberService;
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
