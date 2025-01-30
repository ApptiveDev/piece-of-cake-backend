package apptive.com.store.cake.service;

import apptive.com.store.cake.model.request.CakeRequest;
import apptive.com.store.cake.model.response.CakeDetailResponse;
import apptive.com.store.cake.model.response.CakeListResponse;
import apptive.com.store.cake.model.response.CakeResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CakeService {

    CakeDetailResponse createCake(Long storeId, CakeRequest cakeRequest, MultipartFile cakeImage) throws IOException;// 케이크 생성
    List<CakeListResponse> findAll(Long storeId, Pageable pageable); // 케이크 전체보기
    CakeDetailResponse cakeDetail(Long cakeId); // 케이크 디테일보기
    Long deleteCake(Long cakeId);// 케이크 삭제
    // 케이크 수정
}
