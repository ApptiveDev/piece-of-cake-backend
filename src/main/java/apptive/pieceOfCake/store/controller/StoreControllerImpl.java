package apptive.pieceOfCake.store.controller;

import apptive.pieceOfCake.base.impl.BaseControllerImpl;
import apptive.pieceOfCake.base.impl.BaseServiceImpl;
import apptive.pieceOfCake.store.model.Store;
import apptive.pieceOfCake.store.model.request.StoreRegistrationRequest;
import apptive.pieceOfCake.store.model.response.StoreCakeResponse;
import apptive.pieceOfCake.store.model.response.StoreDetailResponse;
import apptive.pieceOfCake.store.model.response.StoreResponse;
import apptive.pieceOfCake.store.repository.StoreRepository;
import apptive.pieceOfCake.store.service.StoreService;
import apptive.pieceOfCake.users.model.request.MemberUpdateRequest;
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
    public ResponseEntity<StoreResponse> save(@PathVariable("storeId") Long storeId,
                                              @RequestPart(value = "store") StoreRegistrationRequest storeRegistrationRequest,
                                              @RequestPart(value = "profileImage", required = false) MultipartFile profileImage,
                                              @RequestPart(value = "logoImage", required = false) MultipartFile logoImage) throws IOException {

        StoreResponse storeResponse = storeService.save(storeId, storeRegistrationRequest, profileImage, logoImage);

        return new ResponseEntity<>(storeResponse, HttpStatus.OK);
    }

    @Override
    @GetMapping("/location")
    public ResponseEntity<List<StoreResponse>> findNearbyStores(@RequestParam("latitude") double latitude,
                                                                @RequestParam("longitude") double longitude) {
        List<StoreResponse> nearbyStores = storeService.findNearbyStores(latitude, longitude);
        return ResponseEntity.ok(nearbyStores);
    }

    @Override
    @GetMapping("/{storeId}/info")
    public ResponseEntity<StoreDetailResponse> findStore(@PathVariable("storeId") Long storeId) {

        StoreDetailResponse storeDetailResponse = storeService.findStore(storeId);

        return new ResponseEntity<>(storeDetailResponse, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{storeId}/cakes")
    public ResponseEntity<Page<StoreCakeResponse>> findStoreCakes(@PathVariable("storeId") Long storeId, Pageable pageable) {

        Page<StoreCakeResponse> cakeResponses = storeService.findStoreCakes(storeId, pageable);
        return new ResponseEntity<>(cakeResponses, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<StoreResponse> update(Long storeId, MemberUpdateRequest updateRequest) {
        return null;
    }

    @Override
    public ResponseEntity<StoreResponse> updateImage(MultipartFile multipartFile) {
        return null;
    }
}
