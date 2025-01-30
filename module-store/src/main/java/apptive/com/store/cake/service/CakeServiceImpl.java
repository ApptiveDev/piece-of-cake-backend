package apptive.com.store.cake.service;

import apptive.com.common.util.S3Uploader;
import apptive.com.store.cake.exception.CakeException;
import apptive.com.store.cake.model.Cake;
import apptive.com.store.cake.model.option.CakeOption;
import apptive.com.store.cake.model.option.dto.CakeOptionDto;
import apptive.com.store.cake.model.request.CakeRequest;
import apptive.com.store.cake.model.response.CakeDetailResponse;
import apptive.com.store.cake.model.response.CakeListResponse;
import apptive.com.store.cake.repository.CakeRepository;
import apptive.com.store.store.exception.StoreException;
import apptive.com.store.store.model.Store;
import apptive.com.store.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static apptive.com.store.cake.exception.CakeExceptionType.NOT_FOUND_CAKE;
import static apptive.com.store.store.exception.StoreExceptionType.NOT_FOUND_STORE;

@Service
@RequiredArgsConstructor
public class CakeServiceImpl implements CakeService{

    private final CakeRepository cakeRepository;
    private final StoreRepository storeRepository;
    private final S3Uploader s3Uploader;

    @Override
    @Transactional
    public CakeDetailResponse createCake(Long storeId, CakeRequest cakeRequest, MultipartFile cakeImage) throws IOException {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(NOT_FOUND_STORE));

        // 이미지 S3 저장 로직
        String cakeImageAccessUrl = "";

        // 가게 프로필 이미지
        if (!cakeImage.isEmpty()) {
            cakeImageAccessUrl = s3Uploader.upload(cakeImage, store.getStoreInfo().getStoreName());
        }

        Cake cake = Cake.builder()
                .store(store)
                .name(cakeRequest.getName())
                .description(cakeRequest.getDescription())
                .price(cakeRequest.getPrice())
                .cakeImage(cakeImageAccessUrl)
                .options(new ArrayList<>()) // 옵션 초기화
                .build();

        // 옵션 추가
        for (CakeOptionDto optionDto : cakeRequest.getOptions()) {
            CakeOption option = CakeOption.builder()
                    .cake(cake)
                    .type(optionDto.getType())
                    .value(optionDto.getValue())
                    .price(optionDto.getPrice())
                    .build();
            cake.getOptions().add(option);
        }

        cakeRepository.save(cake);

        return CakeDetailResponse.of(cake);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CakeListResponse> findAll(Long storeId, Pageable pageable) {
        Page<Cake> cakes = cakeRepository.findAllByStoreId(storeId, pageable);
        return cakes.stream()
                .map(CakeListResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CakeDetailResponse cakeDetail(Long cakeId) {
        return cakeRepository.findById(cakeId)
                .map(CakeDetailResponse::of)
                .orElseThrow(() -> new CakeException(NOT_FOUND_CAKE));
    }

    @Override
    public Long deleteCake(Long cakeId) {
        return null;
    }

    // --------------- inner method ---------------
}
