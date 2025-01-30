package apptive.com.member.store.service;

import apptive.com.store.cake.exception.CakeException;
import apptive.com.store.cake.model.Cake;
import apptive.com.store.cake.model.response.CakeDetailResponse;
import apptive.com.store.cake.repository.CakeRepository;
import apptive.com.store.store.exception.StoreException;
import apptive.com.store.store.model.Store;
import apptive.com.store.store.model.response.StoreCakeResponse;
import apptive.com.store.store.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static apptive.com.store.cake.exception.CakeExceptionType.NOT_FOUND_CAKE;
import static apptive.com.store.store.exception.StoreExceptionType.NOT_FOUND_STORE;

@Service
@AllArgsConstructor
public class CakeFetchService {

    private final StoreRepository storeRepository;
    private final CakeRepository cakeRepository;

    @Transactional(readOnly = true)
    public List<StoreCakeResponse> findStoreCakes(Long storeId, Pageable pageable) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(NOT_FOUND_STORE));

        Page<Cake> cakeList = cakeRepository.findAllByStoreIdOrderByName(store.getId(), pageable);

        return cakeList.stream()
                .map(StoreCakeResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CakeDetailResponse cakeDetail(Long cakeId) {

        return cakeRepository.findById(cakeId)
                .map(CakeDetailResponse::of)
                .orElseThrow(() -> new CakeException(NOT_FOUND_CAKE));
    }
}
