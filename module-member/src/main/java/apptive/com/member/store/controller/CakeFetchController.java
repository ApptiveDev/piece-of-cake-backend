package apptive.com.member.store.controller;

import apptive.com.member.store.service.CakeFetchService;
import apptive.com.store.cake.model.response.CakeDetailResponse;
import apptive.com.store.store.model.response.StoreCakeResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/public/cakes")
public class CakeFetchController {

    private final CakeFetchService cakeFetchService;

    @GetMapping("/{storeId}")
    public ResponseEntity<List<StoreCakeResponse>> findStoreCakes(@PathVariable("storeId") Long storeId,
                                                                  @PageableDefault(size = 6, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        List<StoreCakeResponse> cakeResponses = cakeFetchService.findStoreCakes(storeId, pageable);
        return new ResponseEntity<>(cakeResponses, HttpStatus.OK);
    }

    @GetMapping("/detail/{cakeId}")
    public ResponseEntity<CakeDetailResponse> cakeDetail(@PathVariable("cakeId") Long cakeId) {

        CakeDetailResponse cakeDetailResponse = cakeFetchService.cakeDetail(cakeId);
        return new ResponseEntity<>(cakeDetailResponse, HttpStatus.OK);
    }
}
