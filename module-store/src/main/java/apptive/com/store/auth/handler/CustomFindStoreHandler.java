package apptive.com.store.auth.handler;

import apptive.com.store.auth.login.domain.StoreInfo;
import apptive.com.store.store.exception.StoreException;
import apptive.com.store.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static apptive.com.store.store.exception.StoreExceptionType.NOT_FOUND_STORE;

@Service
@RequiredArgsConstructor
public class CustomFindStoreHandler {

    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public StoreInfo findById(Long id) {
        return storeRepository.findById(id)
                .map(StoreInfo::of)
                .orElseThrow(() -> new StoreException(NOT_FOUND_STORE));
    }
}
