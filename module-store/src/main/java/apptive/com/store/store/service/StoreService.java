package apptive.com.store.store.service;

import apptive.com.common.base.BaseService;
import apptive.com.store.store.model.Store;
import apptive.com.store.store.model.request.StoreRegistrationRequest;
import apptive.com.store.store.model.request.StoreUpdateRequest;
import apptive.com.store.store.model.response.*;
import apptive.com.store.store.repository.StoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StoreService extends BaseService<Store, StoreResponse, StoreRepository> {

    Long save(Long storeId, StoreRegistrationRequest storeRegistrationRequest); // 가게 등록
    StoreMyPageResponse myPage(Long storeId); // 가게 마이페이지
    StoreMyPageResponse update(Long storeId, StoreUpdateRequest updateRequest, MultipartFile logoImage, MultipartFile bannerImage) throws IOException; // 정보 업데이트
    StoreOwnerInfo find(Long userId); // 로그인용 찾기
}
