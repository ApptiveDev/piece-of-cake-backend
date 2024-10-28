package apptive.pieceOfCake.store.service;

import apptive.pieceOfCake.base.impl.BaseServiceImpl;
import apptive.pieceOfCake.store.exception.StoreException;
import apptive.pieceOfCake.store.model.Store;
import apptive.pieceOfCake.store.model.request.StoreRegistrationRequest;
import apptive.pieceOfCake.store.model.response.StoreResponse;
import apptive.pieceOfCake.store.repository.StoreRepository;
import apptive.pieceOfCake.users.model.request.UserUpdateRequest;
import apptive.pieceOfCake.util.S3Uploader;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static apptive.pieceOfCake.store.exception.StoreExceptionType.NOT_FOUND_STORE;

@Service
public class StoreServiceImpl extends BaseServiceImpl<Store, StoreResponse, StoreRepository> implements StoreService {

    private final StoreRepository storeRepository;
    private final ModelMapper modelMapper;
    private final S3Uploader s3Uploader;

    public StoreServiceImpl(StoreRepository storeRepository, ModelMapper modelMapper, S3Uploader s3Uploader) {
        super(storeRepository);
        this.storeRepository = storeRepository;
        this.modelMapper = modelMapper;
        this.s3Uploader = s3Uploader;
    }

    @Override
    @Transactional
    public StoreResponse save(Long storeId, StoreRegistrationRequest storeRegistrationRequest, MultipartFile multipartFile) throws IOException {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(NOT_FOUND_STORE));

        // 이미지 S3 저장 로직
        String accessUrl = "";

        if (!multipartFile.isEmpty()) {
            accessUrl = s3Uploader.upload(multipartFile, store.getName());
        }

        // 가게 저장 로직
        modelMapper.map(storeRegistrationRequest, store);
        store.setImage(accessUrl);

        storeRepository.save(store);

        return modelMapper.map(store, StoreResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoreResponse> findNearbyStores(double userLat, double userLon) {
        List<Store> storeList = storeRepository.findStoresWithinRadius(userLat, userLon, 0.5);
        List<StoreResponse> storeResponses = new ArrayList<>();

        for (Store store : storeList) {
            storeResponses.add(modelMapper.map(store, StoreResponse.class));
        }

        return storeResponses;
    }

    @Override
    @Transactional
    public StoreResponse update(Long storeId, UserUpdateRequest updateRequest) {
        return null;
    }

    @Override
    @Transactional
    public StoreResponse updateImage(MultipartFile multipartFile) {
        return null;
    }

}
