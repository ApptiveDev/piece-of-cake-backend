package apptive.pieceOfCake.users.controller;

import apptive.pieceOfCake.base.BaseController;
import apptive.pieceOfCake.users.model.Member;
import apptive.pieceOfCake.users.model.request.MemberCheckSameEmail;
import apptive.pieceOfCake.users.model.request.MemberRequest;
import apptive.pieceOfCake.users.model.request.MemberUpdateRequest;
import apptive.pieceOfCake.users.model.response.MemberMyPageResponse;
import apptive.pieceOfCake.users.model.response.MemberResponse;
import apptive.pieceOfCake.users.repository.MemberRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface MemberController extends BaseController<Member, MemberResponse, MemberRepository> {

    ResponseEntity<Long> save(@Valid MemberRequest memberRequest); // 회원가입
    ResponseEntity<MemberMyPageResponse> find(@PathVariable Long userId); // 마이페이지
    ResponseEntity<MemberMyPageResponse> update(MemberUpdateRequest memberUpdateRequest, @PathVariable Long userId); // 업데이트
    ResponseEntity<String> checkSameEmail(@RequestBody MemberCheckSameEmail memberCheckSameEmail); // 중복 이메일 확인
}
