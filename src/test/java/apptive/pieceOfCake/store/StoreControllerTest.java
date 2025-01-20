package apptive.pieceOfCake.store;

import apptive.pieceOfCake.base.impl.BaseServiceImpl;
import apptive.pieceOfCake.config.ControllerTestConfig;
import apptive.pieceOfCake.store.model.Store;
import apptive.pieceOfCake.store.model.request.StoreOwnerSignupRequest;
import apptive.pieceOfCake.store.model.request.StoreRegistrationRequest;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("dev")
public class StoreControllerTest extends ControllerTestConfig {

    @Autowired
    ObjectMapper objectMapper;
    private static final String DEFAULT_URL = "/store";

    @MockBean
    private BaseServiceImpl<Store, StoreResponse, StoreRepository> baseService;

    @MockBean
    private StoreService storeService;

    @DisplayName("사장 생성 API (성공) - 로그인 적용 전")
    @Test
    void saveOwner_success() throws Exception {
        // given
        StoreOwnerSignupRequest storeOwnerSignupRequest = new StoreOwnerSignupRequest();
        storeOwnerSignupRequest.setLoginId("test123");
        storeOwnerSignupRequest.setLoginPwd("test123!");

        String jsonRq = objectMapper.writeValueAsString(storeOwnerSignupRequest);

        // when // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/owner")
                                .content(jsonRq)
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("createOwner",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Store API")
                                .summary("가게 생성 API")
                                .description("현재 사장님 회원가입 시, 가게 엔티티에 포함되어 생성되도록 설정되어 있스빈다. 즉, 사장님 생성 = 가게 생성이며 가게 내용 업데이트는 PATCH로 진행됩니다.")
                                .requestFields(
                                        fieldWithPath("loginId").type(STRING).description("로그인 아이디"),
                                        fieldWithPath("loginPwd").type(STRING).description("로그인 비밀번호")
                                        )
                                .requestSchema(Schema.schema("StoreOwnerSignupRequest"))
                                .responseSchema(Schema.schema("userId"))
                                .build())));
    }

    @DisplayName("가게 생성 API (성공)")
    @Test
    void saveStore_success() throws Exception {
        // given
        Long storeId = 1L;

        StoreRegistrationRequest storeRegistrationRequest = new StoreRegistrationRequest();
        storeRegistrationRequest.setName("앱티브 케익 가게");
        storeRegistrationRequest.setAddress("부산광역시 금정구 장전동 300-12");
        storeRegistrationRequest.setLongitude(129.087135);
        storeRegistrationRequest.setLatitude(35.229251);
        storeRegistrationRequest.setContact("051-123-4567");
        storeRegistrationRequest.setPhoneNum("010-1234-5678");
        storeRegistrationRequest.setSLink("https://www.instagram.com/apptive_cake/");
        storeRegistrationRequest.setProfileIntroduction("앱티브 수제 케익 가게 입니다. 언제든 문의주세요 :)");

        String jsonRq = objectMapper.writeValueAsString(storeRegistrationRequest);

        MockMultipartFile formFile = new MockMultipartFile("store", "", "application/json", jsonRq.getBytes());
        MockMultipartFile profileImage = new MockMultipartFile("profileImage", "", "image/png", (byte[]) null);
        MockMultipartFile logoImage = new MockMultipartFile("logoImage", "", "image/png", (byte[]) null);

        // when
        StoreResponse storeResponse = new StoreResponse();
        storeResponse.setStoreId(1L);
        storeResponse.setName("앱티브 케익 가게");
        storeResponse.setProfileImage("https://pieceofcake-bucket.s3.ap-northeast-2.amazonaws.com/images/~~");
        storeResponse.setLogoImage("https://pieceofcake-bucket.s3.ap-northeast-2.amazonaws.com/images/~~");
        storeResponse.setDistance(0.0);

        when(storeService.save(any(Long.class), any(StoreRegistrationRequest.class), any(MultipartFile.class), any(MultipartFile.class))).thenReturn(storeResponse);

        // then
        mockMvc.perform(
                        multipart(DEFAULT_URL + "/save/{storeId}", storeId)
                                .file(formFile)
                                .file(profileImage)
                                .file(logoImage)
                                .accept(APPLICATION_JSON, IMAGE_PNG, IMAGE_JPEG)
                                .with(request -> {
                                    request.setMethod("PATCH");
                                    return request;
                                })
                )
                //.andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("patchStore",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Store API")
                                .summary("가게 수정 API")
                                .description("기존 생성된 가게 엔티티 기반으로 가게 내용을 수정(업데이트) 합니다.")
                                .responseFields(
                                        fieldWithPath("storeId").type(NUMBER).description("가게 unique ID (Long)"),
                                        fieldWithPath("name").type(STRING).description("가게명"),
                                        fieldWithPath("profileImage").type(STRING).description("가게 프로필 이미지"),
                                        fieldWithPath("logoImage").type(STRING).description("가게 로 이미지"),
                                        fieldWithPath("distance").type(NUMBER).description("현재 위치에서의 거리 (이 request 에서는 상관 X)")
                                )
                                .requestSchema(Schema.schema("StoreRegistrationRequest"))
                                .responseSchema(Schema.schema("StoreResponse"))
                                .build())));
    }

    @DisplayName("가게 찾기 API (성공)")
    @Test
    void findStore_success() throws Exception {
        // given
        StoreResponse storeResponse1 = new StoreResponse();
        storeResponse1.setStoreId(1L);
        storeResponse1.setName("앱티브 케익 가게");
        storeResponse1.setProfileImage("https://pieceofcake-bucket.s3.ap-northeast-2.amazonaws.com/images/~~");
        storeResponse1.setLogoImage("https://pieceofcake-bucket.s3.ap-northeast-2.amazonaws.com/images/~~");
        storeResponse1.setDistance(0.5);

        StoreResponse storeResponse2 = new StoreResponse();
        storeResponse2.setStoreId(2L);
        storeResponse2.setName("파파라치 케익 가게");
        storeResponse2.setProfileImage("https://pieceofcake-bucket.s3.ap-northeast-2.amazonaws.com/images/~~");
        storeResponse2.setLogoImage("https://pieceofcake-bucket.s3.ap-northeast-2.amazonaws.com/images/~~");
        storeResponse2.setDistance(0.8);

        List<StoreResponse> storeResponseList = new ArrayList<>();
        storeResponseList.add(storeResponse1);
        storeResponseList.add(storeResponse2);

        // when
        when(storeService.findNearbyStores(any(Double.class), any(Double.class))).thenReturn(storeResponseList);

        // then
        mockMvc.perform(
                        get(DEFAULT_URL + "/location")
                                .param("latitude", "12.45")
                                .param("longitude", "184.12")
                )
                //.andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("findStore",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Store API")
                                        .summary("가게 찾기 API")
                                        .description("현재 위치를 기반으로 근처에 위치한 가게를 찾을 수 있습니다.")
                                        .queryParameters(
                                                parameterWithName("latitude").description("현재 위치의 위도"),
                                                parameterWithName("longitude").description("현재 위치의 경도")
                                        )
                                        .responseFields(
                                                fieldWithPath("[].storeId").type(NUMBER).description("가게 unique ID (Long)"),
                                                fieldWithPath("[].name").type(STRING).description("가게명"),
                                                fieldWithPath("[].profileImage").type(STRING).description("가게 프로필 이미지"),
                                                fieldWithPath("[].logoImage").type(STRING).description("가게 로 이미지"),
                                                fieldWithPath("[].distance").type(NUMBER).description("현재 위치에서의 거리")
                                        )
                                        .responseSchema(Schema.schema("StoreResponse"))
                                        .build())));
    }
}
