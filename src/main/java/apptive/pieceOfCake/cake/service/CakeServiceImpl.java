package apptive.pieceOfCake.cake.service;

import apptive.pieceOfCake.cake.exception.CakeException;
import apptive.pieceOfCake.cake.model.Cake;
import apptive.pieceOfCake.cake.model.option.SizeOption;
import apptive.pieceOfCake.cake.model.request.CakeRequest;
import apptive.pieceOfCake.cake.model.request.option.*;
import apptive.pieceOfCake.cake.model.response.CakeResponse;
import apptive.pieceOfCake.cake.model.response.option.*;
import apptive.pieceOfCake.cake.repository.CakeRepository;
import apptive.pieceOfCake.cake.repository.option.SizeOptionRepository;
import apptive.pieceOfCake.store.exception.StoreException;
import apptive.pieceOfCake.store.model.Store;
import apptive.pieceOfCake.store.repository.StoreRepository;
import apptive.pieceOfCake.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static apptive.pieceOfCake.cake.exception.CakeExceptionType.NOT_FOUND_CAKE;
import static apptive.pieceOfCake.cake.exception.CakeExceptionType.WRONG_INPUT;
import static apptive.pieceOfCake.store.exception.StoreExceptionType.NOT_FOUND_STORE;

@Service
@RequiredArgsConstructor
public class CakeServiceImpl implements CakeService{

    private final CakeRepository cakeRepository;
    private final StoreRepository storeRepository;

    // option repository
    private final SizeOptionRepository sizeOptionRepository;

    private final ModelMapper modelMapper;
    private final S3Uploader s3Uploader;

    @Override
    @Transactional
    public CakeResponse createCake(CakeRequest cakeRequest, MultipartFile cakeImage) throws IOException {

        Store store = storeRepository.findById(cakeRequest.getStoreId())
                .orElseThrow(() -> new StoreException(NOT_FOUND_STORE));

        // 이미지 S3 저장 로직
        String cakeImageAccessUrl = "";

        // 가게 프로필 이미지
        if (!cakeImage.isEmpty()) {
            cakeImageAccessUrl = s3Uploader.upload(cakeImage, store.getName());
        }

        Cake cake = Cake.builder()
                .storeId(cakeRequest.getStoreId())
                .name(cakeRequest.getName())
                .description(cakeRequest.getDescription())
                .price(cakeRequest.getPrice())
                .cakeImage(cakeImageAccessUrl)
                .build();

        cakeRepository.save(cake);

        return createCakeResponse(cake);
    }

    @Override
    @Transactional
    public CakeResponse addSizeOption(Long cakeId, SizeOptionRequest sizeOptionRequest) {

        Cake cake = cakeRepository.findById(cakeId)
                .orElseThrow(() -> new CakeException(NOT_FOUND_CAKE));

        SizeOption sizeOption = new SizeOption();
        sizeOption.setCake(cake);
        sizeOption.setType(sizeOptionRequest.getType());
        sizeOption.setAdditionalPrice(sizeOptionRequest.getAdditionalPrice());

        sizeOptionRepository.save(sizeOption);

        List<SizeOption> sizeOptions = cake.getSizeOptions();
        sizeOptions.add(sizeOption);

        return createCakeResponse(cake);
    }

    @Override
    public CakeResponse addTasteOption(TasteOptionRequest tasteOptionRequest) {
        return null;
    }

    @Override
    public CakeResponse addCreamOption(CreamOptionRequest creamOptionRequest) {
        return null;
    }

    @Override
    public CakeResponse addColorOption(ColorOptionRequest colorOptionRequest) {
        return null;
    }

    @Override
    public CakeResponse addEtcOption(EtcOptionRequest etcOptionRequest) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CakeResponse> findAll(Long storeId) {

        List<Cake> cakeList = cakeRepository.findAllByStoreId(storeId);
        List<CakeResponse> cakeResponseList = new ArrayList<>();

        for (Cake cake : cakeList) {

            cakeResponseList.add(createCakeResponse(cake));
        }

        return cakeResponseList;
    }

    @Override
    @Transactional(readOnly = true)
    public CakeResponse cakeDetail(Long storeId, Long cakeId) {

        Cake cake = cakeRepository.findById(cakeId)
                .orElseThrow(() -> new CakeException(NOT_FOUND_CAKE));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(NOT_FOUND_STORE));

        CakeResponse cakeResponse = createCakeResponse(cake);
        cakeResponse.setStoreName(store.getName());

        return cakeResponse;
    }

    @Override
    public Long deleteCake(Long cakeId) {
        return null;
    }

    // --------------- inner method ---------------
    // response 생성
    private CakeResponse createCakeResponse(Cake cake) {

        CakeResponse response = modelMapper.map(cake, CakeResponse.class);

        response.setSizeOption(cake.getSizeOptions() != null ? cake.getSizeOptions().stream()
                .map(size -> modelMapper.map(size, SizeOptionResponse.class))
                .collect(Collectors.toList()) : new ArrayList<>()); // null 체크

        response.setTasteOption(cake.getTasteOptions() != null ? cake.getTasteOptions().stream()
                .map(taste -> modelMapper.map(taste, TasteOptionResponse.class))
                .collect(Collectors.toList()) : new ArrayList<>()); // null 체크

        response.setCreamOption(cake.getCreamOptions() != null ? cake.getCreamOptions().stream()
                .map(cream -> modelMapper.map(cream, CreamOptionResponse.class))
                .collect(Collectors.toList()) : new ArrayList<>()); // null 체크

        response.setColorOption(cake.getColorOptions() != null ? cake.getColorOptions().stream()
                .map(color -> modelMapper.map(color, ColorOptionResponse.class))
                .collect(Collectors.toList()) : new ArrayList<>()); // null 체크

        response.setEtcOption(cake.getEtcOptions() != null ? cake.getEtcOptions().stream()
                .map(etc -> modelMapper.map(etc, EtcOptionResponse.class))
                .collect(Collectors.toList()) : new ArrayList<>()); // null 체크

        return response;
    }

}
