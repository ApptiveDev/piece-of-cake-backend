package apptive.com.member.users.service;

import apptive.com.common.base.BaseService;
import apptive.com.member.users.model.Member;
import apptive.com.member.users.model.request.MemberCheckSameEmail;
import apptive.com.member.users.model.request.MemberRequest;
import apptive.com.member.users.model.request.MemberUpdateRequest;
import apptive.com.member.users.model.response.MemberMyPageResponse;
import apptive.com.member.users.model.response.MemberResponse;
import apptive.com.member.users.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public interface MemberService extends BaseService<Member, MemberResponse, MemberRepository> {

    Long save(MemberRequest memberRequest); // 회원가입
    MemberMyPageResponse find(Long userID); // 유저 찾기
    MemberMyPageResponse findByEmail(String email); // 유저 찾기 (로그인 용)
    MemberMyPageResponse update(Long userID, MemberUpdateRequest updateRequest); // 정보 업데이트
    String checkSameEmail(MemberCheckSameEmail memberCheckSameEmail);
}
