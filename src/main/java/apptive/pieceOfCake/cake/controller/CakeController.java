package apptive.pieceOfCake.cake.controller;

import apptive.pieceOfCake.cake.model.request.CakeRequest;
import apptive.pieceOfCake.cake.model.request.option.SizeOptionRequest;
import apptive.pieceOfCake.cake.model.response.CakeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CakeController {

    ResponseEntity<CakeResponse> createCake(@RequestPart(value = "cake") CakeRequest cakeRequest,
                                            @RequestPart(value = "cakeImage") MultipartFile cakeImage) throws IOException; // 케이크 생성

    ResponseEntity<CakeResponse> addSizeOption(@PathVariable("cakeId") Long cakeId,
                                               @RequestBody SizeOptionRequest sizeOptionRequest); // 케이크 크기 옵션 추가

    ResponseEntity<List<CakeResponse>> findAll(@PathVariable("storeId") Long storeId); // 케이크 전체보기
    ResponseEntity<CakeResponse> cakeDetail(@PathVariable("storeId") Long storeId,
                                            @PathVariable("cakeId") Long cakeId); // 케이크 디테일보기
}
