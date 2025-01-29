package apptive.com.member.auth.handler;

import apptive.com.member.auth.login.domain.UserInfo;
import apptive.com.member.users.exception.MemberException;
import apptive.com.member.users.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static apptive.com.member.users.exception.MemberExceptionType.NOT_FOUND_MEMBER;

@Service
@RequiredArgsConstructor
public class CustomFindUserHandler {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public UserInfo findById(Long id) {
        return memberRepository.findById(id)
                .map(UserInfo::of)
                .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));
    }
}
