package apptive.com.store.store.controller;

import apptive.com.common.base.BaseController;
import apptive.com.store.store.model.Store;
import apptive.com.store.store.model.request.StoreRegistrationRequest;
import apptive.com.store.store.model.request.StoreUpdateRequest;
import apptive.com.store.store.model.response.StoreCakeResponse;
import apptive.com.store.store.model.response.StoreDetailResponse;
import apptive.com.store.store.model.response.StoreMyPageResponse;
import apptive.com.store.store.model.response.StoreResponse;
import apptive.com.store.store.repository.StoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StoreController extends BaseController<Store, StoreResponse, StoreRepository> {

    ResponseEntity<Long> save(@PathVariable("storeId") Long storeId,
                              @RequestBody StoreRegistrationRequest storeRegistrationRequest); // 가게 등록
    ResponseEntity<StoreMyPageResponse> myPage(@PathVariable("storeId") Long storeId); // 마이페이지
    ResponseEntity<StoreMyPageResponse> update(@PathVariable("storeId") Long storeId,
                                               @RequestPart(value = "store") StoreUpdateRequest updateRequest,
                                               @RequestPart(value = "logoImage", required = false) MultipartFile logoImage,
                                               @RequestPart(value = "bannerImage", required = false) MultipartFile bannerImage) throws IOException; // 정보 업데이트
}
