package apptive.pieceOfCake.cake.controller;

import apptive.pieceOfCake.cake.model.request.CakeRequest;
import apptive.pieceOfCake.cake.model.request.option.SizeOptionRequest;
import apptive.pieceOfCake.cake.model.response.CakeResponse;
import apptive.pieceOfCake.cake.service.CakeService;
import lombok.RequiredArgsConstructor;
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
    @PostMapping("/create")
    public ResponseEntity<CakeResponse> createCake(@RequestPart(value = "cake") CakeRequest cakeRequest,
                                                   @RequestPart(value = "cakeImage") MultipartFile cakeImage) throws IOException {

        CakeResponse cakeResponse = cakeService.createCake(cakeRequest, cakeImage);

        return new ResponseEntity<>(cakeResponse, HttpStatus.CREATED);
    }

    @Override
    @PatchMapping("/{cakeId}/size")
    public ResponseEntity<CakeResponse> addSizeOption(@PathVariable("cakeId") Long cakeId,
                                                      @RequestBody SizeOptionRequest sizeOptionRequest) {

        CakeResponse cakeResponse = cakeService.addSizeOption(cakeId, sizeOptionRequest);
        return ResponseEntity.ok(cakeResponse);
    }

    @Override
    @GetMapping("/{storeId}")
    public ResponseEntity<List<CakeResponse>> findAll(@PathVariable("storeId") Long storeId) {

        List<CakeResponse> cakeResponseList = cakeService.findAll(storeId);

        return ResponseEntity.ok(cakeResponseList);
    }

    @Override
    @GetMapping("/{storeId}/{cakeId}")
    public ResponseEntity<CakeResponse> cakeDetail(@PathVariable("storeId") Long storeId,
                                                   @PathVariable("cakeId") Long cakeId) {

        CakeResponse cakeResponse = cakeService.cakeDetail(storeId, cakeId);

        return ResponseEntity.ok(cakeResponse);
    }
}
