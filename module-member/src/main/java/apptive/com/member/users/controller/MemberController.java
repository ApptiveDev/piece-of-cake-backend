package apptive.com.member.users.controller;

import apptive.com.common.base.BaseController;
import apptive.com.member.users.model.Member;
import apptive.com.member.users.model.request.MemberCheckSameEmail;
import apptive.com.member.users.model.response.MemberMyPageResponse;
import apptive.com.member.users.model.response.MemberResponse;
import apptive.com.member.users.repository.MemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface MemberController extends BaseController<Member, MemberResponse, MemberRepository> {

    ResponseEntity<MemberMyPageResponse> mypage(@PathVariable Long userId); // 마이페이지
    ResponseEntity<String> checkSameEmail(@RequestBody MemberCheckSameEmail memberCheckSameEmail); // 중복 이메일 확인
}
