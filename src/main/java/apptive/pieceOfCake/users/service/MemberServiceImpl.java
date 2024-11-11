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
                .loginId(memberRequest.getLoginId())
                .loginPwd(memberRequest.getLoginPwd())
                .name(memberRequest.getName())
                .phoneNum(memberRequest.getPhoneNum())
                .address(memberRequest.getAddress())
                .latitude(memberRequest.getLatitude())
                .longitude(memberRequest.getLongitude())
                .build();

        memberRepository.save(user);

        return user.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public MemberMyPageResponse find(Long userID) {

        Member user = memberRepository.findById(userID)
                .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));

        MemberMyPageResponse memberMyPageResponse = new MemberMyPageResponse();
        memberMyPageResponse.setUserId(user.getId());
        memberMyPageResponse.setName(user.getName());
        memberMyPageResponse.setPhoneNum(user.getPhoneNum());
        memberMyPageResponse.setEmail(user.getLoginId());
        memberMyPageResponse.setAddress(user.getAddress());
        memberMyPageResponse.setLoginPwd(user.getLoginPwd());
        memberMyPageResponse.setPhoneNum(user.getPhoneNum());

        return memberMyPageResponse;
    }

    @Override
    @Transactional
    public MemberMyPageResponse update(Long userID, MemberUpdateRequest updateRequest) {

        Member user = memberRepository.findById(userID)
                .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));

        user.setPhoneNum(updateRequest.getPhoneNum());
        //user.setEmail(updateRequest.getEmail());

        memberRepository.save(user);

        MemberMyPageResponse memberMyPageResponse = new MemberMyPageResponse();
        memberMyPageResponse.setName(user.getName());
        memberMyPageResponse.setPhoneNum(user.getPhoneNum());
        //memberMyPageResponse.setEmail(user.getEmail());

        return memberMyPageResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public String checkSameEmail(MemberCheckSameEmail memberCheckSameEmail) {

        Member user = memberRepository.findByLoginId(memberCheckSameEmail.getLoginId())
                .orElse(null);

        if (user != null) {
            throw new MemberException(ALREADY_EXIST_USERNAME);
        }

        return "사용 가능한 아이디입니다.";
    }
}
