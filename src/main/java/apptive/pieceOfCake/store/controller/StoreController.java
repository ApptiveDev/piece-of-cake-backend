package apptive.pieceOfCake.store.controller;

import apptive.pieceOfCake.base.BaseController;
import apptive.pieceOfCake.store.model.Store;
import apptive.pieceOfCake.store.model.request.StoreRegistrationRequest;
import apptive.pieceOfCake.store.model.response.StoreCakeResponse;
import apptive.pieceOfCake.store.model.response.StoreDetailResponse;
import apptive.pieceOfCake.store.model.response.StoreResponse;
import apptive.pieceOfCake.store.repository.StoreRepository;
import apptive.pieceOfCake.users.model.request.MemberUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StoreController extends BaseController<Store, StoreResponse, StoreRepository> {

    ResponseEntity<StoreResponse> save(@PathVariable("storeId") Long storeId,
                                       @RequestPart(value = "store") StoreRegistrationRequest storeRegistrationRequest,
                                       @RequestPart(value = "profileImage", required = false) MultipartFile profileImage,
                                       @RequestPart(value = "logoImage", required = false) MultipartFile logoImage) throws IOException; // 가게 등록
    ResponseEntity<List<StoreResponse>> findNearbyStores(@RequestParam double latitude,
                                                         @RequestParam double longitude); // (위치 기반) 가게 찾기
    ResponseEntity<StoreDetailResponse> findStore(@PathVariable("storeId") Long storeId); // 가게 상세보기
    ResponseEntity<Page<StoreCakeResponse>> findStoreCakes(@PathVariable("storeId") Long storeId,
                                                           @PageableDefault(size = 6) Pageable pageable); // 가게 케이크 목록 보기
    ResponseEntity<StoreResponse> update(Long storeId, MemberUpdateRequest updateRequest); // 정보 업데이트
    ResponseEntity<StoreResponse> updateImage(MultipartFile multipartFile); // 가게 프로필 사진 업데이트

}
