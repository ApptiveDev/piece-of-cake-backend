package apptive.com.member.users.service;

import apptive.com.common.base.impl.BaseServiceImpl;
import apptive.com.member.users.exception.MemberException;
import apptive.com.member.users.model.Member;
import apptive.com.member.users.model.request.MemberCheckSameEmail;
import apptive.com.member.users.model.request.MemberRequest;
import apptive.com.member.users.model.request.MemberUpdateRequest;
import apptive.com.member.users.model.response.MemberMyPageResponse;
import apptive.com.member.users.model.response.MemberResponse;
import apptive.com.member.users.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static apptive.com.member.users.exception.MemberExceptionType.ALREADY_EXIST_USERNAME;
import static apptive.com.member.users.exception.MemberExceptionType.NOT_FOUND_MEMBER;

@Service
public class MemberServiceImpl extends BaseServiceImpl<Member, MemberResponse, MemberRepository> implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository repository, MemberRepository memberRepository) {
        super(repository);
        this.memberRepository = memberRepository;
    }

    // 사용 X
    @Override
    public Long save(MemberRequest memberRequest) {
        return null;
    }

    /**
     * 사용자 idx 를 이용해 사용자 정보 조회
     *
     * @param userId 사용자 idx
     * @return 사용자 정보 MemberMyPageResponse
     */
    @Override
    @Transactional(readOnly = true)
    public MemberMyPageResponse find(Long userId) {

        return memberRepository.findById(userId)
                .map(MemberMyPageResponse::of)
                .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));
    }

    @Override
    public MemberMyPageResponse findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .map(MemberMyPageResponse::of)
                .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));
    }

    @Override
    @Transactional
    public MemberMyPageResponse update(Long userID, MemberUpdateRequest updateRequest) {

        Member member = memberRepository.findById(userID)
                .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));

        member.setEmail(updateRequest.getEmail());
        member.setName(updateRequest.getName());
        member.setPhoneNum(updateRequest.getPhoneNum());
        member.setAddress(updateRequest.getAddress());
        member.setLoginPwd(updateRequest.getLoginPwd());
        member.setAgreementOfMarketing(updateRequest.isAgreementOfMarketing());

        memberRepository.save(member);

        return MemberMyPageResponse.of(member);
    }

    @Override
    @Transactional(readOnly = true)
    public String checkSameEmail(MemberCheckSameEmail memberCheckSameEmail) {

        Member user = memberRepository.findByEmail(memberCheckSameEmail.getLoginId())
                .orElse(null);

        if (user != null) {
            throw new MemberException(ALREADY_EXIST_USERNAME);
        }

        return "사용 가능한 아이디입니다.";
    }
}
