package apptive.com.store.cake.controller;

import apptive.com.store.cake.model.request.CakeRequest;
import apptive.com.store.cake.model.response.CakeDetailResponse;
import apptive.com.store.cake.model.response.CakeListResponse;
import apptive.com.store.cake.model.response.CakeResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CakeController {

    ResponseEntity<CakeDetailResponse> createCake(@PathVariable(value = "storeId") Long storeId,
                                                  @RequestPart(value = "cake") CakeRequest cakeRequest,
                                                  @RequestPart(value = "cakeImage") MultipartFile cakeImage) throws IOException; // 케이크 생성
    ResponseEntity<List<CakeListResponse>> findAll(@PathVariable("storeId") Long storeId,
                                                   @PageableDefault(size = 6, sort = "id", direction = Sort.Direction.DESC) Pageable pageable); // 케이크 전체보기
    ResponseEntity<CakeDetailResponse> cakeDetail(@PathVariable("cakeId") Long cakeId); // 케이크 디테일보기
}
