package apptive.pieceOfCake.store.service;

import apptive.pieceOfCake.base.impl.BaseServiceImpl;
import apptive.pieceOfCake.store.exception.StoreException;
import apptive.pieceOfCake.store.model.Store;
import apptive.pieceOfCake.store.model.request.StoreRegistrationRequest;
import apptive.pieceOfCake.store.model.response.StoreResponse;
import apptive.pieceOfCake.store.repository.StoreRepository;
import apptive.pieceOfCake.users.model.request.MemberUpdateRequest;
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
    public StoreResponse save(Long storeId, StoreRegistrationRequest storeRegistrationRequest, MultipartFile profileImage, MultipartFile logoImage) throws IOException {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(NOT_FOUND_STORE));

        // 이미지 S3 저장 로직
        String profileImageAccessUrl = "";
        String logoImageAccessUrl = "";

        // 가게 프로필 이미지
        if (!profileImage.isEmpty()) {
            profileImageAccessUrl = s3Uploader.upload(profileImage, store.getName());
        }
        // 가게 로고 이미지
        if (!logoImage.isEmpty()) {
            logoImageAccessUrl = s3Uploader.upload(logoImage, store.getName());
        }

        // 가게 저장 로직
        modelMapper.map(storeRegistrationRequest, store);
        store.setProfileImage(profileImageAccessUrl);
        store.setLogoImage(logoImageAccessUrl);

        storeRepository.save(store);

        return modelMapper.map(store, StoreResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoreResponse> findNearbyStores(double userLat, double userLon) {
        List<Store> storeList = storeRepository.findStoresWithinRadius(userLat, userLon, 5.0);
        List<StoreResponse> storeResponses = new ArrayList<>();

        for (Store store : storeList) {
            StoreResponse storeResponse = modelMapper.map(store, StoreResponse.class);

            // 거리 계산
            double distance = calculateDistance(userLat, userLon, store.getLatitude(), store.getLongitude());
            storeResponse.setDistance(distance); // 거리 설정

            storeResponses.add(storeResponse);
        }

        return storeResponses;
    }

    @Override
    @Transactional
    public StoreResponse update(Long storeId, MemberUpdateRequest updateRequest) {
        return null;
    }

    @Override
    @Transactional
    public StoreResponse updateImage(MultipartFile multipartFile) {
        return null;
    }

    // --------------- inner method ---------------
    // 하버사인 공식을 사용한 거리 계산 메서드
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // 지구의 반지름 (킬로미터)
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // 반환값: 거리 (킬로미터)
    }
}
