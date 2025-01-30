package apptive.com.store.store.controller;

import apptive.com.common.base.impl.BaseControllerImpl;
import apptive.com.common.base.impl.BaseServiceImpl;
import apptive.com.store.store.model.Store;
import apptive.com.store.store.model.request.StoreRegistrationRequest;
import apptive.com.store.store.model.request.StoreUpdateRequest;
import apptive.com.store.store.model.response.StoreCakeResponse;
import apptive.com.store.store.model.response.StoreDetailResponse;
import apptive.com.store.store.model.response.StoreMyPageResponse;
import apptive.com.store.store.model.response.StoreResponse;
import apptive.com.store.store.repository.StoreRepository;
import apptive.com.store.store.service.StoreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreControllerImpl extends BaseControllerImpl<Store, StoreResponse, StoreRepository> implements StoreController {

    private final StoreService storeService;

    public StoreControllerImpl(BaseServiceImpl<Store, StoreResponse, StoreRepository> baseService, StoreService storeService) {
        super(baseService);
        this.storeService = storeService;
    }

    @Override
    @PatchMapping("/save/{storeId}")
    public ResponseEntity<Long> save(@PathVariable("storeId") Long storeId,
                                     @RequestBody StoreRegistrationRequest storeRegistrationRequest) {

        Long storeResponse = storeService.save(storeId, storeRegistrationRequest);

        return new ResponseEntity<>(storeResponse, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreMyPageResponse> myPage(@PathVariable("storeId") Long storeId) {

        StoreMyPageResponse storeMyPageResponse = storeService.myPage(storeId);

        return new ResponseEntity<>(storeMyPageResponse, HttpStatus.OK);
    }

    @Override
    @PatchMapping("/update/{storeId}")
    public ResponseEntity<StoreMyPageResponse> update(@PathVariable("storeId") Long storeId,
                                                @RequestPart(value = "store") StoreUpdateRequest updateRequest,
                                                @RequestPart(value = "logoImage", required = false) MultipartFile logoImage,
                                                @RequestPart(value = "bannerImage", required = false) MultipartFile bannerImage) throws IOException {

        StoreMyPageResponse storeResponse = storeService.update(storeId, updateRequest, logoImage, bannerImage);
        return new ResponseEntity<>(storeResponse, HttpStatus.OK);
    }
}
