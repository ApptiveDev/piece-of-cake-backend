package apptive.pieceOfCake.users.service;

import apptive.pieceOfCake.base.impl.BaseServiceImpl;
import apptive.pieceOfCake.users.exception.MemberException;
import apptive.pieceOfCake.users.model.Member;
import apptive.pieceOfCake.users.model.request.MemberCheckSameEmail;
import apptive.pieceOfCake.users.model.request.MemberRequest;
import apptive.pieceOfCake.users.model.request.MemberUpdateRequest;
import apptive.pieceOfCake.users.model.response.MemberMyPageResponse;
import apptive.pieceOfCake.users.model.response.MemberResponse;
import apptive.pieceOfCake.users.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static apptive.pieceOfCake.users.exception.MemberExceptionType.*;

@Service
public class MemberServiceImpl extends BaseServiceImpl<Member, MemberResponse, MemberRepository> implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository repository, MemberRepository memberRepository) {
        super(repository);
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public Long save(MemberRequest memberRequest) {

        Member user = Member.builder()
                .email(memberRequest.getEmail())
                .loginPwd(memberRequest.getLoginPwd())
                .name(memberRequest.getName())
                .phoneNum(memberRequest.getPhoneNum())
                .address(memberRequest.getAddress())
                .latitude(memberRequest.getLatitude())
                .longitude(memberRequest.getLongitude())
                .agreementOfMarketing(memberRequest.isAgreementOfMarketing())
                .build();

        memberRepository.save(user);

        return user.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public MemberMyPageResponse find(Long userID) {

        Member member = memberRepository.findById(userID)
                .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));

        return getMemberMyPageResponse(member);
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

        return getMemberMyPageResponse(member);
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

    // ------------ inner method --------------
    private MemberMyPageResponse getMemberMyPageResponse(Member member) {
        MemberMyPageResponse memberMyPageResponse = new MemberMyPageResponse();
        memberMyPageResponse.setUserId(member.getId());
        memberMyPageResponse.setEmail(member.getEmail());
        memberMyPageResponse.setName(member.getName());
        memberMyPageResponse.setPhoneNum(member.getPhoneNum());
        memberMyPageResponse.setAddress(member.getAddress());
        memberMyPageResponse.setLoginPwd(member.getLoginPwd());
        memberMyPageResponse.setAgreementOfMarketing(member.isAgreementOfMarketing());

        return memberMyPageResponse;
    }
}
