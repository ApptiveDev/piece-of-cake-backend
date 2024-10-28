package apptive.pieceOfCake.store.controller;

import apptive.pieceOfCake.base.impl.BaseControllerImpl;
import apptive.pieceOfCake.base.impl.BaseServiceImpl;
import apptive.pieceOfCake.store.model.Store;
import apptive.pieceOfCake.store.model.request.StoreFindByUserLonLat;
import apptive.pieceOfCake.store.model.request.StoreRegistrationRequest;
import apptive.pieceOfCake.store.model.response.StoreResponse;
import apptive.pieceOfCake.store.repository.StoreRepository;
import apptive.pieceOfCake.store.service.StoreService;
import apptive.pieceOfCake.users.model.request.UserUpdateRequest;
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
                                              @RequestPart(value = "image") MultipartFile multipartFile) throws IOException {

        StoreResponse storeResponse = storeService.save(storeId, storeRegistrationRequest, multipartFile);

        return new ResponseEntity<>(storeResponse, HttpStatus.OK);
    }

    @Override
    @GetMapping("/location")
    public ResponseEntity<List<StoreResponse>> findNearbyStores(@RequestBody StoreFindByUserLonLat storeFindByUserLonLat) {
        List<StoreResponse> nearbyStores = storeService.findNearbyStores(storeFindByUserLonLat.getLatitude(), storeFindByUserLonLat.getLongitude());
        return ResponseEntity.ok(nearbyStores);
    }

    @Override
    public ResponseEntity<StoreResponse> update(Long storeId, UserUpdateRequest updateRequest) {
        return null;
    }

    @Override
    public ResponseEntity<StoreResponse> updateImage(MultipartFile multipartFile) {
        return null;
    }
}
