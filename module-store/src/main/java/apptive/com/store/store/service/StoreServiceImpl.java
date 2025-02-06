package apptive.com.store.store.service;

import apptive.com.common.base.impl.BaseServiceImpl;
import apptive.com.common.store.exception.StoreException;
import apptive.com.common.util.S3Uploader;
import apptive.com.store.store.model.Store;
import apptive.com.store.store.model.request.StoreRegistrationRequest;
import apptive.com.store.store.model.request.StoreUpdateRequest;
import apptive.com.store.store.model.response.StoreMyPageResponse;
import apptive.com.store.store.model.response.StoreOwnerInfo;
import apptive.com.store.store.model.response.StoreResponse;
import apptive.com.store.store.model.value.BankInfo;
import apptive.com.store.store.model.value.StoreInfo;
import apptive.com.store.store.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static apptive.com.common.store.exception.StoreExceptionType.NOT_FOUND_OWNER;
import static apptive.com.common.store.exception.StoreExceptionType.NOT_FOUND_STORE;

@Service
public class StoreServiceImpl extends BaseServiceImpl<Store, StoreResponse, StoreRepository> implements StoreService {

    private final StoreRepository storeRepository;
    private final S3Uploader s3Uploader;

    public StoreServiceImpl(StoreRepository storeRepository, S3Uploader s3Uploader) {
        super(storeRepository);
        this.storeRepository = storeRepository;
        this.s3Uploader = s3Uploader;
    }

    @Override
    @Transactional
    public Long save(Long storeId, StoreRegistrationRequest storeRegistrationRequest) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(NOT_FOUND_STORE));

        storeRegistrationRequest.initialize(); // 기본값 설정

        // 가게 등록 여부 변경
        store.setStoreApplied(true);

        // storeInfo가 null이면 새로 생성하여 설정
        if (store.getStoreInfo() == null) {
            store.setStoreInfo(new StoreInfo());
        }
        store.getStoreInfo().saveFrom(storeRegistrationRequest);

        // bankInfo가 null이면 새로 생성하여 설정
        if (store.getBankInfo() == null) {
            store.setBankInfo(new BankInfo());
        }
        store.getBankInfo().saveFrom(storeRegistrationRequest);

        // 값 나중에 받는 내용 빈 문자열로 입력
        store.setBannerImage("");
        store.setLogoImage("");

        storeRepository.save(store);

        return storeId;
    }

    @Override
    @Transactional(readOnly = true)
    public StoreMyPageResponse myPage(Long storeId) {
        return storeRepository.findById(storeId)
                .map(StoreMyPageResponse::of)
                .orElseThrow(() -> new StoreException(NOT_FOUND_STORE));
    }

    @Override
    @Transactional
    public StoreMyPageResponse update(Long storeId, StoreUpdateRequest updateRequest,
                                      MultipartFile logoImage, MultipartFile bannerImage) throws IOException {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(NOT_FOUND_STORE));

        // 가게 로고 이미지
        if (logoImage!= null && !logoImage.isEmpty()) {
            String logoImageAccessUrl = s3Uploader.upload(logoImage, store.getStoreInfo().getStoreName());
            store.setLogoImage(logoImageAccessUrl);
        }

        // 가게 프로필 이미지
        if (bannerImage != null && !bannerImage.isEmpty()) {
            String bannerImageAccessUrl = s3Uploader.upload(bannerImage, store.getStoreInfo().getStoreName());
            store.setBannerImage(bannerImageAccessUrl);
        }

        updateRequest.initialize();
        store.getStoreInfo().updateForm(updateRequest);

        storeRepository.save(store);

        return StoreMyPageResponse.of(store);
    }

    /**
     * 사용자 idx 를 이용해 사용자 정보 조회
     *
     * @param userId 사용자 idx
     * @return 사용자 정보 MemberMyPageResponse
     */
    @Override
    @Transactional(readOnly = true)
    public StoreOwnerInfo find(Long userId) {

        return storeRepository.findById(userId)
                .map(StoreOwnerInfo::of)
                .orElseThrow(() -> new StoreException(NOT_FOUND_OWNER));
    }
}
