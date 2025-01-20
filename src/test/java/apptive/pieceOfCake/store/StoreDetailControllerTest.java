package apptive.pieceOfCake.store;

import apptive.pieceOfCake.base.impl.BaseServiceImpl;
import apptive.pieceOfCake.config.ControllerTestConfig;
import apptive.pieceOfCake.store.exception.StoreException;
import apptive.pieceOfCake.store.model.Store;
import apptive.pieceOfCake.store.model.response.StoreDetailResponse;
import apptive.pieceOfCake.store.model.response.StoreResponse;
import apptive.pieceOfCake.store.repository.StoreRepository;
import apptive.pieceOfCake.store.service.StoreService;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.ActiveProfiles;

import static apptive.pieceOfCake.store.exception.StoreExceptionType.NOT_FOUND_STORE;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("dev")
public class StoreDetailControllerTest extends ControllerTestConfig {

    @Autowired
    ObjectMapper objectMapper;
    private static final String DEFAULT_URL = "/store";

    @MockBean
    private BaseServiceImpl<Store, StoreResponse, StoreRepository> baseService;

    @MockBean
    private StoreService storeService;

    @DisplayName("가게 상세보기(성공) - 가게 설명")
    @Test
    void findStoreDetail_success() throws Exception {

        // given
        Long storeId = 1L;
        StoreDetailResponse storeDetailResponse = StoreDetailResponse.builder()
                .storeId(1L)
                .name("앱티브 케이크 가게")
                .address("부산대학로 63번길 2")
                .contact("051-123-1234")
                .phoneNum("010-1234-0987")
                .businessHours("화-일 10:00-20:00")
                .closedDays("매주 월 휴무")
                .snsLink("https://example.com/apptive_cake")
                .profileIntroduction("다양한 디자인의 케이크를 준비하고 있습니다. 언제든 방문 해주세요!")
                .profileImage("프로필 이미지 S3 링크")
                .logoImage("로고 이미지 S3 링크")
        .build();

        when(storeService.findStore(any(Long.class))).thenReturn(storeDetailResponse);

        // when // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get(DEFAULT_URL + "/{storeId}/info", storeId))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("find_store_info",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Store API")
                                .summary("가게 상세정보 API: 가게 정보")
                                .description("가게 정보를 전달합니다. 값이 없는 경우, 빈 String (\"\") 혹은 0.0으로 전달됩니다. 케이크 사진은 다른 API 를 통해 전달됩니다.")
                                .pathParameters(
                                        parameterWithName("storeId").description("가게 unique Id")
                                )
                                .responseFields(
                                        fieldWithPath("storeId").type(NUMBER).description("가게 unique Id (Long)"),
                                        fieldWithPath("name").type(STRING).description("가게 이름"),
                                        fieldWithPath("address").type(STRING).description("주소"),
                                        fieldWithPath("contact").type(STRING).description("가게 연락처"),
                                        fieldWithPath("phoneNum").type(STRING).description("대표 연락처"),
                                        fieldWithPath("businessHours").type(STRING).description("영업 시간"),
                                        fieldWithPath("closedDays").type(STRING).description("휴무일"),
                                        fieldWithPath("snsLink").type(STRING).description("SNS 링크"),
                                        fieldWithPath("profileIntroduction").type(STRING).description("프로필 설명"),
                                        fieldWithPath("profileImage").type(STRING).description("프로필 이미지"),
                                        fieldWithPath("logoImage").type(STRING).description("로고 이미지")
                                )
                                .responseSchema(Schema.schema("StoreDetailResponse"))
                                .build())));
    }

    @DisplayName("가게 상세보기 (실패) - 가게 상세정보")
    @Test
    void findStoreDetail_fail_no_store() throws Exception {

        // given
        Long storeId = 1L;

        // when
        when(storeService.findStore(1L)).thenThrow(new StoreException(NOT_FOUND_STORE));

        // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get(DEFAULT_URL + "/{storeId}/info", storeId)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("가게를 찾을 수 없습니다."))
                .andDo(document("find_store_fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Store API")
                                .summary("가게 상세정보 API: 가게 정보")
                                .description("가게 정보를 전달합니다. 값이 없는 경우, 빈 String (\"\") 혹은 0.0으로 전달됩니다. 케이크 사진은 다른 API 를 통해 전달됩니다.")
                                .pathParameters(
                                        parameterWithName("storeId").description("가게 unique Id")
                                )
                                .build())));
    }

//    @DisplayName("가게 상세보기 (성공) - 가게 케이크 정보")
//    @Test
//    void findStoreDetailWithCake_success() throws Exception {
//
//        // given
//        Long storeId = 1L;
//        int page = 0; // 첫 번째 페이지
//        int size = 6; // 페이지 크기
//        Pageable pageable = PageRequest.of(page, size); // Pageable 초기화
//
//        List<StoreCakeResponse> exampleCakes = Arrays.asList(
//                new StoreCakeResponse(1L, storeId, "love 케이크", "곰돌이가 그려진 케이크입니다.", "https://..."),
//                new StoreCakeResponse(2L, storeId, "choco 케이크", "달콤한 초코 케이크입니다.", "https://..."),
//                new StoreCakeResponse(3L, storeId, "strawberry 케이크", "신선한 딸기가 올라간 케이크입니다.", "https://..."),
//                new StoreCakeResponse(4L, storeId, "vanilla 케이크", "부드러운 바닐라 케이크입니다.", "https://..."),
//                new StoreCakeResponse(5L, storeId, "cheesecake", "크리미한 치즈 케이크입니다.", "https://..."),
//                new StoreCakeResponse(6L, storeId, "red velvet 케이크", "부드러운 레드벨벳 케이크입니다.", "https://...")
//        );
//
//        // Page 객체 생성
//        Page<StoreCakeResponse> pageResponse = new PageImpl<>(exampleCakes, PageRequest.of(page, size), exampleCakes.size());
//
//        // when
//        when(storeService.findStoreCakes(1L, pageable)).thenReturn(pageResponse);
//
//        // then
//        mockMvc.perform(
//                        RestDocumentationRequestBuilders.get(DEFAULT_URL + "/{storeId}/cakes", storeId)
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("find_store_cake_success",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        resource(ResourceSnippetParameters.builder()
//                                .tag("Store API")
//                                .summary("가게 상세정보 API: 가게 케이크 정보")
//                                .description("케이크 정보를 전달합니다. 가게 상세 정보는 다른 API 를 통해 전달됩니다.")
//                                .pathParameters(
//                                        parameterWithName("storeId").description("가게 unique Id")
//                                )
//                                .responseFields(
//                                        fieldWithPath("content[]").description("케이크 목록"),
//                                        fieldWithPath("content[].cakeId").type(NUMBER).description("케이크 unique Id (Long)"),
//                                        fieldWithPath("content[].storeId").type(NUMBER).description("가게 unique Id (Long)"),
//                                        fieldWithPath("content[].name").type(STRING).description("케이크 이름 (이름 순 정렬)"),
//                                        fieldWithPath("content[].description").type(STRING).description("케이크 설명"),
//                                        fieldWithPath("content[].cakeImage").type(STRING).description("케이크 이미지 URL"),
//
//                                        fieldWithPath("pageable").description("페이징 정보"),
//                                        fieldWithPath("pageable.pageNumber").type(NUMBER).description("현재 페이지 번호"),
//                                        fieldWithPath("pageable.pageSize").type(NUMBER).description("페이지 크기"),
//                                        fieldWithPath("pageable.sort").description("정렬 정보"),
//                                        fieldWithPath("pageable.sort.empty").type(BOOLEAN).description("정렬이 비어 있는지 여부"),
//                                        fieldWithPath("pageable.sort.sorted").type(BOOLEAN).description("정렬 여부"),
//                                        fieldWithPath("pageable.sort.unsorted").type(BOOLEAN).description("정렬되지 않은 여부"),
//                                        fieldWithPath("pageable.offset").type(NUMBER).description("현재 페이지의 시작 오프셋"),
//                                        fieldWithPath("pageable.paged").type(BOOLEAN).description("페이지 가능 여부"),
//                                        fieldWithPath("pageable.unpaged").type(BOOLEAN).description("페이지 불가능 여부"),
//
//                                        fieldWithPath("sort.empty").type(BOOLEAN).description("정렬이 비어 있는지 여부"),
//                                        fieldWithPath("sort.sorted").type(BOOLEAN).description("정렬 여부"),
//                                        fieldWithPath("sort.unsorted").type(BOOLEAN).description("정렬되지 않은 여부"),
//
//                                        fieldWithPath("last").type(BOOLEAN).description("마지막 페이지 여부"),
//                                        fieldWithPath("totalPages").type(NUMBER).description("총 페이지 수"),
//                                        fieldWithPath("totalElements").type(NUMBER).description("총 요소 수"),
//                                        fieldWithPath("first").type(BOOLEAN).description("첫 페이지 여부"),
//                                        fieldWithPath("size").type(NUMBER).description("페이지 크기"),
//                                        fieldWithPath("number").type(NUMBER).description("현재 페이지 번호"),
//                                        fieldWithPath("numberOfElements").type(NUMBER).description("현재 페이지의 요소 수"),
//                                        fieldWithPath("empty").type(BOOLEAN).description("현재 페이지가 비어 있는지 여부")
//                                )
//
//                                .build())));
//    }

    @DisplayName("가게 상세보기 (실패) - 가게 케이크 정보")
    @Test
    void findStoreDetailWithCake_fail_no_store() throws Exception {

        // given
        Long storeId = 1L;
        int page = 0; // 첫 번째 페이지
        int size = 6; // 페이지 크기
        Pageable pageable = PageRequest.of(page, size); // Pageable 초기화

        // when
        when(storeService.findStoreCakes(1L, pageable)).thenThrow(new StoreException(NOT_FOUND_STORE));

        // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get(DEFAULT_URL + "/{storeId}/cakes", storeId)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("가게를 찾을 수 없습니다."))
                .andDo(document("find_store_cake_fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Store API")
                                .summary("가게 상세정보 API: 가게 케이크 정보")
                                .description("케이크 정보를 전달합니다. 가게 상세 정보는 다른 API 를 통해 전달됩니다.")
                                .pathParameters(
                                        parameterWithName("storeId").description("가게 unique Id")
                                )
                                .build())));
    }
}
