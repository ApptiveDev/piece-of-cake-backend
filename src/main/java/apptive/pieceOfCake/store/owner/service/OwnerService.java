package apptive.pieceOfCake.store.owner.service;

import apptive.pieceOfCake.store.model.Store;
import apptive.pieceOfCake.store.model.request.StoreOwnerSignupRequest;
import apptive.pieceOfCake.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final StoreRepository storeRepository;

    @Transactional
    public Long save(StoreOwnerSignupRequest storeOwnerSignupRequest) {

        Store store = Store.builder()
                .loginId(storeOwnerSignupRequest.getLoginId())
                .loginPwd(storeOwnerSignupRequest.getLoginPwd())
                .build();

        storeRepository.save(store);

        return store.getId();
    }
}
