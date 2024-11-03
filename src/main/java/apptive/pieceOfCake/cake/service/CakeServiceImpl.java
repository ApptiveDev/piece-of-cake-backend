package apptive.pieceOfCake.cake.service;

import apptive.pieceOfCake.cake.exception.CakeException;
import apptive.pieceOfCake.cake.model.Cake;
import apptive.pieceOfCake.cake.model.option.*;
import apptive.pieceOfCake.cake.model.request.CakeRequest;
import apptive.pieceOfCake.cake.model.request.option.*;
import apptive.pieceOfCake.cake.model.response.CakeResponse;
import apptive.pieceOfCake.cake.model.response.option.*;
import apptive.pieceOfCake.cake.repository.CakeRepository;
import apptive.pieceOfCake.cake.repository.option.*;
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
import java.util.stream.Collectors;

import static apptive.pieceOfCake.cake.exception.CakeExceptionType.NOT_FOUND_CAKE;
import static apptive.pieceOfCake.store.exception.StoreExceptionType.NOT_FOUND_STORE;

@Service
@RequiredArgsConstructor
public class CakeServiceImpl implements CakeService{

    private final CakeRepository cakeRepository;
    private final StoreRepository storeRepository;

    // option repository
    private final SizeOptionRepository sizeOptionRepository;
    private final TasteOptionRepository tasteOptionRepository;
    private final CreamOptionRepository creamOptionRepository;
    private final ColorOptionRepository colorOptionRepository;
    private final EtcOptionRepository etcOptionRepository;

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
    @Transactional
    public CakeResponse addTasteOption(Long cakeId, TasteOptionRequest tasteOptionRequest) {
        Cake cake = cakeRepository.findById(cakeId)
                .orElseThrow(() -> new CakeException(NOT_FOUND_CAKE));

        TasteOption tasteOption = new TasteOption();
        tasteOption.setCake(cake);
        tasteOption.setType(tasteOptionRequest.getType());
        tasteOption.setAdditionalPrice(tasteOptionRequest.getAdditionalPrice());

        tasteOptionRepository.save(tasteOption);

        List<TasteOption> tasteOptions = cake.getTasteOptions();
        tasteOptions.add(tasteOption);

        return createCakeResponse(cake);
    }

    @Override
    @Transactional
    public CakeResponse addCreamOption(Long cakeId, CreamOptionRequest creamOptionRequest) {
        Cake cake = cakeRepository.findById(cakeId)
                .orElseThrow(() -> new CakeException(NOT_FOUND_CAKE));

        CreamOption creamOption = new CreamOption();
        creamOption.setCake(cake);
        creamOption.setType(creamOptionRequest.getType());
        creamOption.setAdditionalPrice(creamOptionRequest.getAdditionalPrice());

        creamOptionRepository.save(creamOption);

        List<CreamOption> creamOptions = cake.getCreamOptions();
        creamOptions.add(creamOption);

        return createCakeResponse(cake);
    }

    @Override
    @Transactional
    public CakeResponse addColorOption(Long cakeId, ColorOptionRequest colorOptionRequest, MultipartFile colorImage) throws IOException {
        Cake cake = cakeRepository.findById(cakeId)
                .orElseThrow(() -> new CakeException(NOT_FOUND_CAKE));

        ColorOption colorOption = new ColorOption();
        colorOption.setCake(cake);
        colorOption.setType(colorOptionRequest.getType());
        colorOption.setAdditionalPrice(colorOptionRequest.getAdditionalPrice());

        // 이미지 S3 저장 로직
        String colorImageAccessUrl = "";

        // 가게 프로필 이미지
        if (!colorImage.isEmpty()) {
            colorImageAccessUrl = s3Uploader.upload(colorImage, cake.getName());
        }

        colorOption.setColorImage(colorImageAccessUrl);

        colorOptionRepository.save(colorOption);

        List<ColorOption> colorOptions = cake.getColorOptions();
        colorOptions.add(colorOption);

        return createCakeResponse(cake);
    }

    @Override
    @Transactional
    public CakeResponse addEtcOption(Long cakeId, EtcOptionRequest etcOptionRequest) {
        Cake cake = cakeRepository.findById(cakeId)
                .orElseThrow(() -> new CakeException(NOT_FOUND_CAKE));

        EtcOption etcOption = new EtcOption();
        etcOption.setCake(cake);
        etcOption.setType(etcOptionRequest.getType());
        etcOption.setAdditionalPrice(etcOptionRequest.getAdditionalPrice());

        etcOptionRepository.save(etcOption);

        List<EtcOption> etcOptions = cake.getEtcOptions();
        etcOptions.add(etcOption);

        return createCakeResponse(cake);
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
