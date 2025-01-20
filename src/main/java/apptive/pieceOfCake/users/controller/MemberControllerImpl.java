package apptive.pieceOfCake.users.controller;

import apptive.pieceOfCake.base.impl.BaseControllerImpl;
import apptive.pieceOfCake.base.impl.BaseServiceImpl;
import apptive.pieceOfCake.users.model.Member;
import apptive.pieceOfCake.users.model.request.MemberCheckSameEmail;
import apptive.pieceOfCake.users.model.request.MemberRequest;
import apptive.pieceOfCake.users.model.request.MemberUpdateRequest;
import apptive.pieceOfCake.users.model.response.MemberMyPageResponse;
import apptive.pieceOfCake.users.model.response.MemberResponse;
import apptive.pieceOfCake.users.repository.MemberRepository;
import apptive.pieceOfCake.users.service.MemberService;
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
    @PostMapping("/save")
    public ResponseEntity<Long> save(@Valid @RequestBody MemberRequest memberRequest) {

        Long userId = memberService.save(memberRequest);
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/myPage/{userId}")
    public ResponseEntity<MemberMyPageResponse> find(@PathVariable("userId") Long userId) {

        MemberMyPageResponse memberMyPageResponse = memberService.find(userId);
        return new ResponseEntity<>(memberMyPageResponse, HttpStatus.OK);
    }

    @Override
    @PatchMapping("/update/{userId}")
    public ResponseEntity<MemberMyPageResponse> update(@RequestBody MemberUpdateRequest memberUpdateRequest,
                                                       @PathVariable("userId") Long userId) {

        MemberMyPageResponse memberMyPageResponse = memberService.update(userId, memberUpdateRequest);
        return new ResponseEntity<>(memberMyPageResponse, HttpStatus.OK);
    }

    @Override
    @GetMapping("/checkEmail")
    public ResponseEntity<String> checkSameEmail(MemberCheckSameEmail memberCheckSameEmail) {

        String msg = memberService.checkSameEmail(memberCheckSameEmail);

        return ResponseEntity.ok(msg);
    }
}

