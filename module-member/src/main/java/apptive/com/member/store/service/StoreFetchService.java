package apptive.com.member.store.service;

import apptive.com.store.cake.model.Cake;
import apptive.com.store.cake.repository.CakeRepository;
import apptive.com.store.store.exception.StoreException;
import apptive.com.store.store.model.Store;
import apptive.com.store.store.model.response.StoreCakeResponse;
import apptive.com.store.store.model.response.StoreDetailResponse;
import apptive.com.store.store.model.response.StoreResponse;
import apptive.com.store.store.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static apptive.com.store.store.exception.StoreExceptionType.NOT_FOUND_STORE;

@Service
@AllArgsConstructor
public class StoreFetchService {

    private final StoreRepository storeRepository;
    private final CakeRepository cakeRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<StoreResponse> findNearbyStores(double userLat, double userLon) {
        List<Store> storeList = storeRepository.findStoresWithinRadius(userLat, userLon, 5.0);
        List<StoreResponse> storeResponses = new ArrayList<>();

        for (Store store : storeList) {
            StoreResponse storeResponse = modelMapper.map(store, StoreResponse.class);

            // 거리 계산
            double distance = calculateDistance(userLat, userLon,
                    store.getStoreInfo().getLatitude(), store.getStoreInfo().getLongitude());
            storeResponse.setDistance(distance); // 거리 설정

            storeResponses.add(storeResponse);
        }

        return storeResponses;
    }

    @Transactional(readOnly = true)
    public StoreDetailResponse findStore(Long storeId) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(NOT_FOUND_STORE));

        StoreDetailResponse storeDetailResponse = new StoreDetailResponse();
        modelMapper.map(store, storeDetailResponse);
        storeDetailResponse.initialize();

        return storeDetailResponse;
    }

    @Transactional(readOnly = true)
    public Page<StoreCakeResponse> findStoreCakes(Long storeId, Pageable pageable) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(NOT_FOUND_STORE));

        Page<Cake> cakeList = cakeRepository.findAllByStoreIdOrderByName(storeId, pageable);

        return cakeList.map(cake -> new StoreCakeResponse(
                cake.getId(),
                store.getId(),
                cake.getName(),
                cake.getDescription(),
                cake.getCakeImage()
        ));
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
