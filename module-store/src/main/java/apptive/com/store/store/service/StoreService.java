package apptive.com.store.store.service;

import apptive.com.common.base.BaseService;
import apptive.com.store.store.model.Store;
import apptive.com.store.store.model.request.StoreRegistrationRequest;
import apptive.com.store.store.model.request.StoreUpdateRequest;
import apptive.com.store.store.model.response.StoreCakeResponse;
import apptive.com.store.store.model.response.StoreDetailResponse;
import apptive.com.store.store.model.response.StoreOwnerInfo;
import apptive.com.store.store.model.response.StoreResponse;
import apptive.com.store.store.repository.StoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StoreService extends BaseService<Store, StoreResponse, StoreRepository> {

    StoreResponse save(Long storeId, StoreRegistrationRequest storeRegistrationRequest, MultipartFile profileImage, MultipartFile logoImage) throws IOException; // 가게 등록
    List<StoreResponse> findNearbyStores(double userLat, double userLon); // (위치 기반) 가게 찾기
    StoreOwnerInfo find(Long userId); // 로그인용 찾기
    StoreDetailResponse findStore(Long storeId); // 가게 상세보기
    Page<StoreCakeResponse> findStoreCakes(Long storeId, Pageable pageable); // 가게 케이크 목록보기
    StoreResponse update(Long storeId, StoreUpdateRequest updateRequest); // 정보 업데이트
    StoreResponse updateImage(MultipartFile multipartFile); // 가게 프로필 사진 업데이트
}
