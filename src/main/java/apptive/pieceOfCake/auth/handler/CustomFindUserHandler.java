package apptive.pieceOfCake.auth.handler;

import apptive.pieceOfCake.auth.login.domain.UserInfo;
import apptive.pieceOfCake.store.model.Store;
import apptive.pieceOfCake.store.repository.StoreRepository;
import apptive.pieceOfCake.users.model.Member;
import apptive.pieceOfCake.users.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomFindUserHandler {

    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public UserInfo findById(Long id) {
        // 먼저 memberRepository에서 조회
        Optional<Member> member = memberRepository.findById(id);

        if (member.isPresent()) {
            return UserInfo.of(member.get()); // Member에서 UserInfo 변환
        }

        // memberRepository에서 찾지 못한 경우 storeRepository에서 조회
        Optional<Store> store = storeRepository.findById(id);

        // Store가 없으면 null 반환, 있으면 UserInfo 변환
        return store.map(UserInfo::of).orElse(null); // Store가 없으면 null 반환
    }
}
