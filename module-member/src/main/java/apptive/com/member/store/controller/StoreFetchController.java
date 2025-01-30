package apptive.com.member.store.controller;

import apptive.com.member.store.service.StoreFetchService;
import apptive.com.store.store.model.response.StoreCakeResponse;
import apptive.com.store.store.model.response.StoreDetailResponse;
import apptive.com.store.store.model.response.StoreResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/public/stores")
public class StoreFetchController {

    private final StoreFetchService storeFetchService;

    @GetMapping("/{storeId}/info")
    public ResponseEntity<StoreDetailResponse> findStore(@PathVariable("storeId") Long storeId) {

        StoreDetailResponse storeDetailResponse = storeFetchService.findStore(storeId);

        return new ResponseEntity<>(storeDetailResponse, HttpStatus.OK);
    }

    @GetMapping("/{storeId}/cakes")
    public ResponseEntity<Page<StoreCakeResponse>> findStoreCakes(@PathVariable("storeId") Long storeId, Pageable pageable) {

        Page<StoreCakeResponse> cakeResponses = storeFetchService.findStoreCakes(storeId, pageable);
        return new ResponseEntity<>(cakeResponses, HttpStatus.OK);
    }

    @GetMapping("/location")
    public ResponseEntity<List<StoreResponse>> findNearbyStores(@RequestParam("latitude") double latitude,
                                                                @RequestParam("longitude") double longitude) {
        List<StoreResponse> nearbyStores = storeFetchService.findNearbyStores(latitude, longitude);
        return ResponseEntity.ok(nearbyStores);
    }
}
