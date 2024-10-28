package apptive.pieceOfCake.store.controller;

import apptive.pieceOfCake.base.BaseController;
import apptive.pieceOfCake.store.model.Store;
import apptive.pieceOfCake.store.model.request.StoreFindByUserLonLat;
import apptive.pieceOfCake.store.model.request.StoreRegistrationRequest;
import apptive.pieceOfCake.store.model.response.StoreResponse;
import apptive.pieceOfCake.store.repository.StoreRepository;
import apptive.pieceOfCake.users.model.request.UserUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StoreController extends BaseController<Store, StoreResponse, StoreRepository> {

    ResponseEntity<StoreResponse> save(@PathVariable("storeId") Long storeId,
                      @RequestPart(value = "store") StoreRegistrationRequest storeRegistrationRequest,
                      @RequestPart(value = "image", required = false) MultipartFile multipartFile) throws IOException; // 가게 등록
    ResponseEntity<List<StoreResponse>> findNearbyStores(@RequestBody StoreFindByUserLonLat storeFindByUserLonLat); // (위치 기반) 가게 찾기
    ResponseEntity<StoreResponse> update(Long storeId, UserUpdateRequest updateRequest); // 정보 업데이트
    ResponseEntity<StoreResponse> updateImage(MultipartFile multipartFile); // 가게 프로필 사진 업데이트

}
