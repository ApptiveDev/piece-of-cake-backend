package apptive.com.member.users.controller;

import apptive.com.common.base.impl.BaseControllerImpl;
import apptive.com.common.base.impl.BaseServiceImpl;
import apptive.com.member.users.model.Member;
import apptive.com.member.users.model.request.MemberCheckSameEmail;
import apptive.com.member.users.model.request.MemberUpdateRequest;
import apptive.com.member.users.model.response.MemberMyPageResponse;
import apptive.com.member.users.model.response.MemberResponse;
import apptive.com.member.users.repository.MemberRepository;
import apptive.com.member.users.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class MemberControllerImpl extends BaseControllerImpl<Member, MemberResponse, MemberRepository> implements MemberController {

    private final MemberService memberService;

    public MemberControllerImpl(BaseServiceImpl<Member, MemberResponse, MemberRepository> baseService, MemberService memberService) {
        super(baseService);
        this.memberService = memberService;
    }

    @Override
    @GetMapping("/myPage/{userId}")
    public ResponseEntity<MemberMyPageResponse> mypage(@PathVariable("userId") Long userId) {

        MemberMyPageResponse memberMyPageResponse = memberService.mypage(userId);
        return new ResponseEntity<>(memberMyPageResponse, HttpStatus.OK);
    }

    @Override
    @PostMapping("/checkEmail")
    public ResponseEntity<String> checkSameEmail(@RequestBody @Valid MemberCheckSameEmail memberCheckSameEmail) {

        String msg = memberService.checkSameEmail(memberCheckSameEmail);

        return ResponseEntity.ok(msg);
    }
}

