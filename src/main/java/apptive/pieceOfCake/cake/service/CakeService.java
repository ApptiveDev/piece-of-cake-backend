package apptive.pieceOfCake.cake.service;

import apptive.pieceOfCake.cake.model.request.CakeRequest;
import apptive.pieceOfCake.cake.model.request.option.*;
import apptive.pieceOfCake.cake.model.response.CakeResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CakeService {

    CakeResponse createCake(CakeRequest cakeRequest, MultipartFile cakeImage) throws IOException;// 케이크 생성
    CakeResponse addSizeOption(Long cakeId, SizeOptionRequest sizeOptionRequest);// 케이크 크기 옵션 추가
    CakeResponse addTasteOption(Long cakeId, TasteOptionRequest tasteOptionRequest);// 케이크 맛 옵션 추가
    CakeResponse addCreamOption(Long cakeId, CreamOptionRequest creamOptionRequest);// 케이크 크림 옵션 추가
    CakeResponse addColorOption(Long cakeId, ColorOptionRequest colorOptionRequest, MultipartFile colorImage) throws IOException;// 케이크 색상 옵션 추가
    CakeResponse addEtcOption(Long cakeId, EtcOptionRequest etcOptionRequest);// 케이크 기타 옵션 추가
    List<CakeResponse> findAll(Long storeId); // 케이크 전체보기
    CakeResponse cakeDetail(Long storeId, Long cakeId); // 케이크 디테일보기
    Long deleteCake(Long cakeId);// 케이크 삭제
    // 케이크 수정
}
