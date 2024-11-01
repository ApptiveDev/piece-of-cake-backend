package apptive.pieceOfCake.store.service;

import apptive.pieceOfCake.base.BaseService;
import apptive.pieceOfCake.store.model.Store;
import apptive.pieceOfCake.store.model.request.StoreRegistrationRequest;
import apptive.pieceOfCake.store.model.response.StoreResponse;
import apptive.pieceOfCake.store.repository.StoreRepository;
import apptive.pieceOfCake.users.model.request.UserUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StoreService extends BaseService<Store, StoreResponse, StoreRepository> {

    StoreResponse save(Long storeId, StoreRegistrationRequest storeRegistrationRequest, MultipartFile multipartFile) throws IOException; // 가게 등록
    List<StoreResponse> findNearbyStores(double userLat, double userLon); // (위치 기반) 가게 찾기
    StoreResponse update(Long storeId, UserUpdateRequest updateRequest); // 정보 업데이트
    StoreResponse updateImage(MultipartFile multipartFile); // 가게 프로필 사진 업데이트
}
