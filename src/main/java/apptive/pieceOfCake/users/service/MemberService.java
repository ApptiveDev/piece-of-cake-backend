package apptive.pieceOfCake.users.service;

import apptive.pieceOfCake.base.BaseService;
import apptive.pieceOfCake.users.model.Member;
import apptive.pieceOfCake.users.model.request.MemberCheckSameEmail;
import apptive.pieceOfCake.users.model.request.MemberRequest;
import apptive.pieceOfCake.users.model.request.MemberUpdateRequest;
import apptive.pieceOfCake.users.model.response.MemberMyPageResponse;
import apptive.pieceOfCake.users.model.response.MemberResponse;
import apptive.pieceOfCake.users.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public interface MemberService extends BaseService<Member, MemberResponse, MemberRepository> {

    Long save(MemberRequest memberRequest); // 회원가입
    MemberMyPageResponse find(Long userID); // 유저 찾기
    MemberMyPageResponse update(Long userID, MemberUpdateRequest updateRequest); // 정보 업데이트
    String checkSameEmail(MemberCheckSameEmail memberCheckSameEmail);
}
