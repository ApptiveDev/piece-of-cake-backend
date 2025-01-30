package apptive.com.member.users.service;

import apptive.com.common.base.BaseService;
import apptive.com.member.auth.login.domain.response.MemberInfoResponse;
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

    MemberMyPageResponse mypage(Long userId); // 마이페이지
    MemberInfoResponse find(Long userID); // 유저 찾기 (로그인 용)
    MemberInfoResponse findByEmail(String email); // 유저 찾기 (로그인 용)
    String checkSameEmail(MemberCheckSameEmail memberCheckSameEmail);
}
