package apptive.com.store.cake.controller;

import apptive.com.store.cake.model.request.CakeRequest;
import apptive.com.store.cake.model.response.CakeDetailResponse;
import apptive.com.store.cake.model.response.CakeListResponse;
import apptive.com.store.cake.model.response.CakeResponse;
import apptive.com.store.cake.service.CakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cake")
public class CakeControllerImpl implements CakeController{

    private final CakeService cakeService;

    @Override
    @PostMapping("/create/{storeId}")
    public ResponseEntity<CakeDetailResponse> createCake(@PathVariable(value = "storeId") Long storeId,
                                                   @RequestPart(value = "cake") CakeRequest cakeRequest,
                                                   @RequestPart(value = "cakeImage") MultipartFile cakeImage) throws IOException {

        CakeDetailResponse cakeResponse = cakeService.createCake(storeId, cakeRequest, cakeImage);

        return new ResponseEntity<>(cakeResponse, HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/list/{storeId}")
    public ResponseEntity<List<CakeListResponse>> findAll(@PathVariable("storeId") Long storeId, Pageable pageable) {

        List<CakeListResponse> cakeResponseList = cakeService.findAll(storeId, pageable);

        return ResponseEntity.ok(cakeResponseList);
    }

    @Override
    @GetMapping("/detail/{cakeId}")
    public ResponseEntity<CakeDetailResponse> cakeDetail(@PathVariable("cakeId") Long cakeId) {

        CakeDetailResponse cakeResponse = cakeService.cakeDetail(cakeId);

        return ResponseEntity.ok(cakeResponse);
    }
}
