//package apptive.pieceOfCake.cake;
//
//import apptive.pieceOfCake.cake.exception.CakeException;
//import apptive.pieceOfCake.cake.model.request.CakeRequest;
//import apptive.pieceOfCake.cake.model.request.option.*;
//import apptive.pieceOfCake.cake.model.response.CakeResponse;
//import apptive.pieceOfCake.cake.model.response.option.*;
//import apptive.pieceOfCake.cake.service.CakeService;
//import apptive.pieceOfCake.config.ControllerTestConfig;
//import apptive.pieceOfCake.store.exception.StoreException;
//import com.epages.restdocs.apispec.ResourceSnippetParameters;
//import com.epages.restdocs.apispec.Schema;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static apptive.pieceOfCake.cake.exception.CakeExceptionType.NOT_FOUND_CAKE;
//import static apptive.pieceOfCake.store.exception.StoreExceptionType.NOT_FOUND_STORE;
//import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
//import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.http.MediaType.*;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
//import static org.springframework.restdocs.payload.JsonFieldType.*;
//import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@ActiveProfiles("dev")
//public class CakeControllerTest extends ControllerTestConfig {
//
//    @Autowired
//    ObjectMapper objectMapper;
//    private static final String DEFAULT_URL = "/cake";
//
//    @MockBean
//    private CakeService cakeService;
//
//    @DisplayName("케익 생성 API (성공)")
//    @Test
//    void save_cake_success() throws Exception {
//        // given
//        CakeRequest cakeRequest = new CakeRequest();
//        cakeRequest.setStoreId(1L);
//        cakeRequest.setName("앱티브 케익");
//        cakeRequest.setDescription("앱티브만의 특별한 재료로 만든 케이크입니다.");
//        cakeRequest.setPrice(15000);
//
//        String jsonRq = objectMapper.writeValueAsString(cakeRequest);
//
//        MockMultipartFile cake = new MockMultipartFile("cake", "", "application/json", jsonRq.getBytes());
//        MockMultipartFile cakeImage = new MockMultipartFile("cakeImage", "", "image/png", (byte[]) null);
//
//        // when
//        CakeResponse cakeResponse = new CakeResponse();
//        cakeResponse.setCakeId(1L);
//        cakeResponse.setStoreId(1L);
//        cakeResponse.setCakeName("앱티브 케익");
//        cakeResponse.setDescription("앱티브만의 특별한 재료로 만든 케이크입니다.");
//        cakeResponse.setPrice(15000);
//        cakeResponse.setStoreName("앱티브 케익가게");
//        cakeResponse.setCakeImage("https://pieceofcake-bucket.s3.ap-northeast-2.amazonaws.com/images/~~");
//        cakeResponse.setSizeOption(new ArrayList<>());
//        cakeResponse.setTasteOption(new ArrayList<>());
//        cakeResponse.setCreamOption(new ArrayList<>());
//        cakeResponse.setColorOption(new ArrayList<>());
//        cakeResponse.setEtcOption(new ArrayList<>());
//
//        when(cakeService.createCake(any(CakeRequest.class), any(MultipartFile.class))).thenReturn(cakeResponse);
//
//        // then
//        mockMvc.perform(
//                        multipart(DEFAULT_URL + "/create")
//                                .file(cake)
//                                .file(cakeImage)
//                                .accept(APPLICATION_JSON, IMAGE_PNG, IMAGE_JPEG)
//                                .with(request -> {
//                                    request.setMethod("POST");
//                                    return request;
//                                })
//                )
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andDo(document("createCake",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        resource(ResourceSnippetParameters.builder()
//                                .tag("Cake API")
//                                .summary("케이크 생성 API")
//                                .description("옵션 없이 가장 기본 케이크 생성용 API 입니다. 케이크 생성 후에, 각각의 옵션들을 추가하는 형식으로 구현하였습니다.")
//                                .responseFields(
//                                        fieldWithPath("cakeId").type(NUMBER).description("케이크 unique ID (Long)"),
//                                        fieldWithPath("storeId").type(NUMBER).description("가게 unique ID (Long)"),
//                                        fieldWithPath("cakeName").type(STRING).description("케이크 이름"),
//                                        fieldWithPath("description").type(STRING).description("케이크 설명"),
//                                        fieldWithPath("price").type(NUMBER).description("케이크 가격"),
//                                        fieldWithPath("storeName").type(STRING).description("가게 이름"),
//                                        fieldWithPath("cakeImage").type(STRING).description("케이크 이미지"),
//                                        fieldWithPath("sizeOption").type(ARRAY).description("사이즈 옵션 리스트"),
//                                        fieldWithPath("tasteOption").type(ARRAY).description("맛 옵션 리스트"),
//                                        fieldWithPath("creamOption").type(ARRAY).description("크림 옵션 리스트"),
//                                        fieldWithPath("colorOption").type(ARRAY).description("색상 옵션 리스트"),
//                                        fieldWithPath("etcOption").type(ARRAY).description("기타 옵션 리스트")
//                                )
//                                .requestSchema(Schema.schema("CakeRequest"))
//                                .responseSchema(Schema.schema("CakeResponse"))
//                                .build())));
//    }
//
//    @DisplayName("케익 생성 API (실패 - 가게 찾지 못함)")
//    @Test
//    void save_cake_fail_not_found_store() throws Exception {
//
//        // given
//        CakeRequest cakeRequest = new CakeRequest();
//        cakeRequest.setStoreId(1L);
//        cakeRequest.setName("앱티브 케익");
//        cakeRequest.setDescription("앱티브만의 특별한 재료로 만든 케이크입니다.");
//        cakeRequest.setPrice(15000);
//
//        String jsonRq = objectMapper.writeValueAsString(cakeRequest);
//
//        MockMultipartFile cake = new MockMultipartFile("cake", "", "application/json", jsonRq.getBytes());
//        MockMultipartFile cakeImage = new MockMultipartFile("cakeImage", "", "image/png", (byte[]) null);
//
//        // when
//        when(cakeService.createCake(any(CakeRequest.class), any(MultipartFile.class))).thenThrow(new StoreException(NOT_FOUND_STORE));
//
//        // then
//        mockMvc.perform(
//                        multipart(DEFAULT_URL + "/create")
//                                .file(cake)
//                                .file(cakeImage)
//                                .accept(APPLICATION_JSON, IMAGE_PNG, IMAGE_JPEG)
//                                .with(request -> {
//                                    request.setMethod("POST");
//                                    return request;
//                                })
//                )
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andDo(document("createCakeFail",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        resource(ResourceSnippetParameters.builder()
//                                .tag("Cake API")
//                                .summary("케이크 생성 API")
//                                .description("옵션 없이 가장 기본 케이크 생성용 API 입니다. 케이크 생성 후에, 각각의 옵션들을 추가하는 형식으로 구현하였습니다.")
//                                .requestSchema(Schema.schema("CakeRequest"))
//                                .build())));
//
//    }
//
//    @DisplayName("케익 사이즈 옵션 추가 API (성공)")
//    @Test
//    void add_size_option_success() throws Exception {
//
//        // given
//        SizeOptionRequest sizeOptionRequest = new SizeOptionRequest();
//        sizeOptionRequest.setType("도시락 케이크");
//        sizeOptionRequest.setAdditionalPrice(0);
//
//        String jsonRq = objectMapper.writeValueAsString(sizeOptionRequest);
//
//        // when
//        CakeResponse cakeResponse = new CakeResponse();
//        cakeResponse.setCakeId(1L);
//        cakeResponse.setStoreId(1L);
//        cakeResponse.setCakeName("앱티브 케익");
//        cakeResponse.setDescription("앱티브만의 특별한 재료로 만든 케이크입니다.");
//        cakeResponse.setPrice(15000);
//        cakeResponse.setStoreName("앱티브 케익가게");
//        cakeResponse.setCakeImage("https://pieceofcake-bucket.s3.ap-northeast-2.amazonaws.com/images/~~");
//
//        ArrayList<SizeOptionResponse> sizeOptions = new ArrayList<>();
//        SizeOptionResponse sizeOptionResponse = new SizeOptionResponse();
//        sizeOptionResponse.setId(1L);
//        sizeOptionResponse.setType("도시락 케이크");
//        sizeOptionResponse.setAdditionalPrice(0);
//
//        sizeOptions.add(sizeOptionResponse);
//
//        cakeResponse.setSizeOption(sizeOptions);
//        cakeResponse.setTasteOption(new ArrayList<>());
//        cakeResponse.setCreamOption(new ArrayList<>());
//        cakeResponse.setColorOption(new ArrayList<>());
//        cakeResponse.setEtcOption(new ArrayList<>());
//
//        when(cakeService.addSizeOption(any(Long.class), any(SizeOptionRequest.class))).thenReturn(cakeResponse);
//
//        // then
//        mockMvc.perform(
//                        RestDocumentationRequestBuilders.patch(DEFAULT_URL + "/{cakeId}/size", 1L)
//                                .content(jsonRq)
//                                .contentType(APPLICATION_JSON)
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("addSizeOption",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        resource(ResourceSnippetParameters.builder()
//                                .tag("Cake API")
//                                .summary("케이크 크기 옵션 추가 API")
//                                .description("케이크의 크기 옵션을 추가할 수 있습니다. 여러 개의 옵션 추가를 원할 경우 여러 번 추가하면 됩니다.")
//                                .requestFields(
//                                        fieldWithPath("type").type(STRING).description("케이크 사이즈 옵션"),
//                                        fieldWithPath("additionalPrice").type(NUMBER).description("추가 가격")
//                                )
//                                .responseFields(
//                                        fieldWithPath("cakeId").type(NUMBER).description("케이크 unique ID (Long)"),
//                                        fieldWithPath("storeId").type(NUMBER).description("가게 unique ID (Long)"),
//                                        fieldWithPath("cakeName").type(STRING).description("케이크 이름"),
//                                        fieldWithPath("description").type(STRING).description("케이크 설명"),
//                                        fieldWithPath("price").type(NUMBER).description("케이크 가격"),
//                                        fieldWithPath("storeName").type(STRING).description("가게 이름"),
//                                        fieldWithPath("cakeImage").type(STRING).description("케이크 이미지"),
//                                        fieldWithPath("sizeOption[].id").type(NUMBER).description("사이즈 옵션 ID (Long)"),
//                                        fieldWithPath("sizeOption[].type").type(STRING).description("케이크 사이즈 옵션"),
//                                        fieldWithPath("sizeOption[].additionalPrice").type(NUMBER).description("추가 가격"),
//                                        fieldWithPath("tasteOption").type(ARRAY).description("맛 옵션 리스트"),
//                                        fieldWithPath("creamOption").type(ARRAY).description("크림 옵션 리스트"),
//                                        fieldWithPath("colorOption").type(ARRAY).description("색상 옵션 리스트"),
//                                        fieldWithPath("etcOption").type(ARRAY).description("기타 옵션 리스트")
//                                )
//                                .requestSchema(Schema.schema("SizeOptionRequest"))
//                                .responseSchema(Schema.schema("CakeResponse"))
//                                .build())));
//    }
//
//    @DisplayName("케익 사이즈 옵션 추가 API (실패)")
//    @Test
//    void add_size_option_fail() throws Exception {
//
//        // given
//        SizeOptionRequest sizeOptionRequest = new SizeOptionRequest();
//        sizeOptionRequest.setType("도시락 케이크");
//        sizeOptionRequest.setAdditionalPrice(0);
//
//        String jsonRq = objectMapper.writeValueAsString(sizeOptionRequest);
//
//        when(cakeService.addSizeOption(any(Long.class), any(SizeOptionRequest.class))).thenThrow(new CakeException(NOT_FOUND_CAKE));
//
//        // then
//        mockMvc.perform(
//                        RestDocumentationRequestBuilders.patch(DEFAULT_URL + "/{cakeId}/size", 1L)
//                                .content(jsonRq)
//                                .contentType(APPLICATION_JSON)
//                )
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andDo(document("addSizeOptionFail",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        resource(ResourceSnippetParameters.builder()
//                                .tag("Cake API")
//                                .summary("케이크 크기 옵션 추가 API")
//                                .description("케이크의 크기 옵션을 추가할 수 있습니다. 여러 개의 옵션 추가를 원할 경우 여러 번 추가하면 됩니다. 모든 옵션에 대한 에러(404) 동일")
//                                .requestFields(
//                                        fieldWithPath("type").type(STRING).description("케이크 사이즈 옵션"),
//                                        fieldWithPath("additionalPrice").type(NUMBER).description("추가 가격")
//                                )
//                                .requestSchema(Schema.schema("SizeOptionRequest"))
//                                .build()
//                        )
//                ));
//
//    }
//
//    @DisplayName("케익 맛 옵션 추가 API (성공)")
//    @Test
//    void add_taste_option_success() throws Exception {
//
//        // given
//        TasteOptionRequest tasteOptionRequest = new TasteOptionRequest();
//        tasteOptionRequest.setType("초코시트");
//        tasteOptionRequest.setAdditionalPrice(1000);
//
//        String jsonRq = objectMapper.writeValueAsString(tasteOptionRequest);
//
//        // when
//        CakeResponse cakeResponse = new CakeResponse();
//        cakeResponse.setCakeId(1L);
//        cakeResponse.setStoreId(1L);
//        cakeResponse.setCakeName("앱티브 케익");
//        cakeResponse.setDescription("앱티브만의 특별한 재료로 만든 케이크입니다.");
//        cakeResponse.setPrice(15000);
//        cakeResponse.setStoreName("앱티브 케익가게");
//        cakeResponse.setCakeImage("https://pieceofcake-bucket.s3.ap-northeast-2.amazonaws.com/images/~~");
//
//        ArrayList<SizeOptionResponse> sizeOptions = new ArrayList<>();
//        SizeOptionResponse sizeOptionResponse = new SizeOptionResponse();
//        sizeOptionResponse.setId(1L);
//        sizeOptionResponse.setType("도시락 1호 케이크");
//        sizeOptionResponse.setAdditionalPrice(0);
//
//        SizeOptionResponse sizeOptionResponse1 = new SizeOptionResponse();
//        sizeOptionResponse1.setId(2L);
//        sizeOptionResponse1.setType("홀 케이크 1호");
//        sizeOptionResponse1.setAdditionalPrice(3000);
//
//        sizeOptions.add(sizeOptionResponse);
//        sizeOptions.add(sizeOptionResponse1);
//
//        cakeResponse.setSizeOption(sizeOptions);
//
//        ArrayList<TasteOptionResponse> tasteOptionResponses = new ArrayList<>();
//        TasteOptionResponse tasteOptionResponse = new TasteOptionResponse();
//        tasteOptionResponse.setId(1L);
//        tasteOptionResponse.setType("초코시트");
//        tasteOptionRequest.setAdditionalPrice(1000);
//
//        tasteOptionResponses.add(tasteOptionResponse);
//
//        cakeResponse.setTasteOption(tasteOptionResponses);
//        cakeResponse.setCreamOption(new ArrayList<>());
//        cakeResponse.setColorOption(new ArrayList<>());
//        cakeResponse.setEtcOption(new ArrayList<>());
//
//        when(cakeService.addTasteOption(any(Long.class), any(TasteOptionRequest.class))).thenReturn(cakeResponse);
//
//        // then
//        mockMvc.perform(
//                        RestDocumentationRequestBuilders.patch(DEFAULT_URL + "/{cakeId}/taste", 1L)
//                                .content(jsonRq)
//                                .contentType(APPLICATION_JSON)
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("addTasteOption",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        resource(ResourceSnippetParameters.builder()
//                                .tag("Cake API")
//                                .summary("케이크 맛 옵션 추가 API")
//                                .description("케이크의 맛 옵션을 추가할 수 있습니다. 여러 개의 옵션 추가를 원할 경우 여러 번 추가하면 됩니다.")
//                                .requestFields(
//                                        fieldWithPath("type").type(STRING).description("케이크 맛 옵션"),
//                                        fieldWithPath("additionalPrice").type(NUMBER).description("추가 가격")
//                                )
//                                .responseFields(
//                                        fieldWithPath("cakeId").type(NUMBER).description("케이크 unique ID (Long)"),
//                                        fieldWithPath("storeId").type(NUMBER).description("가게 unique ID (Long)"),
//                                        fieldWithPath("cakeName").type(STRING).description("케이크 이름"),
//                                        fieldWithPath("description").type(STRING).description("케이크 설명"),
//                                        fieldWithPath("price").type(NUMBER).description("케이크 가격"),
//                                        fieldWithPath("storeName").type(STRING).description("가게 이름"),
//                                        fieldWithPath("cakeImage").type(STRING).description("케이크 이미지"),
//                                        fieldWithPath("sizeOption[].id").type(NUMBER).description("사이즈 옵션 ID (Long)"),
//                                        fieldWithPath("sizeOption[].type").type(STRING).description("케이크 사이즈 옵션"),
//                                        fieldWithPath("sizeOption[].additionalPrice").type(NUMBER).description("추가 가격"),
//                                        fieldWithPath("tasteOption[].id").type(NUMBER).description("맛 옵션 ID (Long)"),
//                                        fieldWithPath("tasteOption[].type").type(STRING).description("케이크 맛 옵션"),
//                                        fieldWithPath("tasteOption[].additionalPrice").type(NUMBER).description("추가 가격"),
//                                        fieldWithPath("creamOption").type(ARRAY).description("크림 옵션 리스트"),
//                                        fieldWithPath("colorOption").type(ARRAY).description("색상 옵션 리스트"),
//                                        fieldWithPath("etcOption").type(ARRAY).description("기타 옵션 리스트")
//                                )
//                                .requestSchema(Schema.schema("TasteOptionRequest"))
//                                .responseSchema(Schema.schema("CakeResponse"))
//                                .build())));
//    }
//
//    @DisplayName("케익 크림 옵션 추가 API (성공)")
//    @Test
//    void add_cream_option_success() throws Exception {
//
//        // given
//        CreamOptionRequest creamOptionRequest = new CreamOptionRequest();
//        creamOptionRequest.setType("바닐라 크림");
//        creamOptionRequest.setAdditionalPrice(1000);
//
//        String jsonRq = objectMapper.writeValueAsString(creamOptionRequest);
//
//        // when
//        CakeResponse cakeResponse = new CakeResponse();
//        cakeResponse.setCakeId(1L);
//        cakeResponse.setStoreId(1L);
//        cakeResponse.setCakeName("앱티브 케익");
//        cakeResponse.setDescription("앱티브만의 특별한 재료로 만든 케이크입니다.");
//        cakeResponse.setPrice(15000);
//        cakeResponse.setStoreName("앱티브 케익가게");
//        cakeResponse.setCakeImage("https://pieceofcake-bucket.s3.ap-northeast-2.amazonaws.com/images/~~");
//
//        ArrayList<SizeOptionResponse> sizeOptions = new ArrayList<>();
//        SizeOptionResponse sizeOptionResponse = new SizeOptionResponse();
//        sizeOptionResponse.setId(1L);
//        sizeOptionResponse.setType("도시락 1호 케이크");
//        sizeOptionResponse.setAdditionalPrice(0);
//
//        SizeOptionResponse sizeOptionResponse1 = new SizeOptionResponse();
//        sizeOptionResponse1.setId(2L);
//        sizeOptionResponse1.setType("홀 케이크 1호");
//        sizeOptionResponse1.setAdditionalPrice(3000);
//
//        sizeOptions.add(sizeOptionResponse);
//        sizeOptions.add(sizeOptionResponse1);
//
//        cakeResponse.setSizeOption(sizeOptions);
//
//        ArrayList<TasteOptionResponse> tasteOptionResponses = new ArrayList<>();
//        TasteOptionResponse tasteOptionResponse = new TasteOptionResponse();
//        tasteOptionResponse.setId(1L);
//        tasteOptionResponse.setType("초코시트");
//        tasteOptionResponse.setAdditionalPrice(1000);
//
//        TasteOptionResponse tasteOptionResponse1 = new TasteOptionResponse();
//        tasteOptionResponse1.setId(2L);
//        tasteOptionResponse1.setType("딸기시트");
//        tasteOptionResponse1.setAdditionalPrice(1500);
//
//        tasteOptionResponses.add(tasteOptionResponse);
//        tasteOptionResponses.add(tasteOptionResponse1);
//
//        cakeResponse.setTasteOption(tasteOptionResponses);
//
//        ArrayList<CreamOptionResponse> creamOptionResponses = new ArrayList<>();
//        CreamOptionResponse creamOptionResponse = new CreamOptionResponse();
//        creamOptionResponse.setId(1L);
//        creamOptionResponse.setType("바닐라 크림");
//        creamOptionResponse.setAdditionalPrice(1000);
//
//        creamOptionResponses.add(creamOptionResponse);
//
//        cakeResponse.setCreamOption(creamOptionResponses);
//        cakeResponse.setColorOption(new ArrayList<>());
//        cakeResponse.setEtcOption(new ArrayList<>());
//
//        when(cakeService.addCreamOption(any(Long.class), any(CreamOptionRequest.class))).thenReturn(cakeResponse);
//
//        // then
//        mockMvc.perform(
//                        RestDocumentationRequestBuilders.patch(DEFAULT_URL + "/{cakeId}/cream", 1L)
//                                .content(jsonRq)
//                                .contentType(APPLICATION_JSON)
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("addCreamOption",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        resource(ResourceSnippetParameters.builder()
//                                .tag("Cake API")
//                                .summary("케이크 크림 옵션 추가 API")
//                                .description("케이크의 크림 옵션을 추가할 수 있습니다. 여러 개의 옵션 추가를 원할 경우 여러 번 추가하면 됩니다.")
//                                .requestFields(
//                                        fieldWithPath("type").type(STRING).description("케이크 크림 옵션"),
//                                        fieldWithPath("additionalPrice").type(NUMBER).description("추가 가격")
//                                )
//                                .responseFields(
//                                        fieldWithPath("cakeId").type(NUMBER).description("케이크 unique ID (Long)"),
//                                        fieldWithPath("storeId").type(NUMBER).description("가게 unique ID (Long)"),
//                                        fieldWithPath("cakeName").type(STRING).description("케이크 이름"),
//                                        fieldWithPath("description").type(STRING).description("케이크 설명"),
//                                        fieldWithPath("price").type(NUMBER).description("케이크 가격"),
//                                        fieldWithPath("storeName").type(STRING).description("가게 이름"),
//                                        fieldWithPath("cakeImage").type(STRING).description("케이크 이미지"),
//                                        fieldWithPath("sizeOption[].id").type(NUMBER).description("사이즈 옵션 ID (Long)"),
//                                        fieldWithPath("sizeOption[].type").type(STRING).description("케이크 사이즈 옵션"),
//                                        fieldWithPath("sizeOption[].additionalPrice").type(NUMBER).description("추가 가격"),
//                                        fieldWithPath("tasteOption[].id").type(NUMBER).description("맛 옵션 ID (Long)"),
//                                        fieldWithPath("tasteOption[].type").type(STRING).description("케이크 맛 옵션"),
//                                        fieldWithPath("tasteOption[].additionalPrice").type(NUMBER).description("추가 가격"),
//                                        fieldWithPath("creamOption[].id").type(NUMBER).description("크림 옵션 ID (Long)"),
//                                        fieldWithPath("creamOption[].type").type(STRING).description("케이크 크림 옵션"),
//                                        fieldWithPath("creamOption[].additionalPrice").type(NUMBER).description("추가 가격"),
//                                        fieldWithPath("colorOption").type(ARRAY).description("색상 옵션 리스트"),
//                                        fieldWithPath("etcOption").type(ARRAY).description("기타 옵션 리스트")
//                                )
//                                .requestSchema(Schema.schema("CreamOptionRequest"))
//                                .responseSchema(Schema.schema("CakeResponse"))
//                                .build())));
//    }
//
//    @DisplayName("케익 색상 옵션 추가 API (성공)")
//    @Test
//    void add_color_option_success() throws Exception {
//
//        // given
//        ColorOptionRequest colorOptionRequest = new ColorOptionRequest();
//        colorOptionRequest.setType("빨강");
//        colorOptionRequest.setAdditionalPrice(1000);
//
//        String jsonRq = objectMapper.writeValueAsString(colorOptionRequest);
//
//        MockMultipartFile colorOption = new MockMultipartFile("colorOption", "", "application/json", jsonRq.getBytes());
//        MockMultipartFile colorImage = new MockMultipartFile("image", "", "image/png", (byte[]) null);
//
//        // when
//        CakeResponse cakeResponse = new CakeResponse();
//        cakeResponse.setCakeId(1L);
//        cakeResponse.setStoreId(1L);
//        cakeResponse.setCakeName("앱티브 케익");
//        cakeResponse.setDescription("앱티브만의 특별한 재료로 만든 케이크입니다.");
//        cakeResponse.setPrice(15000);
//        cakeResponse.setStoreName("앱티브 케익가게");
//        cakeResponse.setCakeImage("https://pieceofcake-bucket.s3.ap-northeast-2.amazonaws.com/images/~~");
//
//        ArrayList<SizeOptionResponse> sizeOptions = new ArrayList<>();
//        SizeOptionResponse sizeOptionResponse = new SizeOptionResponse();
//        sizeOptionResponse.setId(1L);
//        sizeOptionResponse.setType("도시락 1호 케이크");
//        sizeOptionResponse.setAdditionalPrice(0);
//
//        SizeOptionResponse sizeOptionResponse1 = new SizeOptionResponse();
//        sizeOptionResponse1.setId(2L);
//        sizeOptionResponse1.setType("홀 케이크 1호");
//        sizeOptionResponse1.setAdditionalPrice(3000);
//
//        sizeOptions.add(sizeOptionResponse);
//        sizeOptions.add(sizeOptionResponse1);
//
//        cakeResponse.setSizeOption(sizeOptions);
//
//        ArrayList<TasteOptionResponse> tasteOptionResponses = new ArrayList<>();
//        TasteOptionResponse tasteOptionResponse = new TasteOptionResponse();
//        tasteOptionResponse.setId(1L);
//        tasteOptionResponse.setType("초코시트");
//        tasteOptionResponse.setAdditionalPrice(1000);
//
//        TasteOptionResponse tasteOptionResponse1 = new TasteOptionResponse();
//        tasteOptionResponse1.setId(2L);
//        tasteOptionResponse1.setType("딸기시트");
//        tasteOptionResponse1.setAdditionalPrice(1500);
//
//        tasteOptionResponses.add(tasteOptionResponse);
//        tasteOptionResponses.add(tasteOptionResponse1);
//
//        cakeResponse.setTasteOption(tasteOptionResponses);
//
//        ArrayList<CreamOptionResponse> creamOptionResponses = new ArrayList<>();
//        CreamOptionResponse creamOptionResponse = new CreamOptionResponse();
//        creamOptionResponse.setId(1L);
//        creamOptionResponse.setType("바닐라 크림");
//        creamOptionResponse.setAdditionalPrice(1000);
//
//        creamOptionResponses.add(creamOptionResponse);
//        cakeResponse.setCreamOption(creamOptionResponses);
//
//        ArrayList<ColorOptionResponse> colorOptionResponses = new ArrayList<>();
//        ColorOptionResponse colorOptionResponse = new ColorOptionResponse();
//        colorOptionResponse.setId(1L);
//        colorOptionResponse.setType("빨강");
//        colorOptionResponse.setAdditionalPrice(1000);
//        colorOptionResponse.setColorImage("https://pieceofcake-bucket.s3.ap-northeast-2.amazonaws.com/images/~~");
//
//        colorOptionResponses.add(colorOptionResponse);
//        cakeResponse.setColorOption(colorOptionResponses);
//        cakeResponse.setEtcOption(new ArrayList<>());
//
//        when(cakeService.addColorOption(any(Long.class), any(ColorOptionRequest.class), any(MultipartFile.class))).thenReturn(cakeResponse);
//
//        // then
//        mockMvc.perform(
//                        multipart(DEFAULT_URL + "/{cakeId}/color", 1L)
//                                .file(colorOption)
//                                .file(colorImage)
//                                .accept(APPLICATION_JSON, IMAGE_PNG, IMAGE_JPEG)
//                                .with(request -> {
//                                    request.setMethod("PATCH");
//                                    return request;
//                                })
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("addColorOption",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        resource(ResourceSnippetParameters.builder()
//                                .tag("Cake API")
//                                .summary("케이크 색상 옵션 추가 API")
//                                .description("케이크의 색상 옵션을 추가할 수 있습니다. 여러 개의 옵션 추가를 원할 경우 여러 번 추가하면 됩니다.")
//                                .responseFields(
//                                        fieldWithPath("cakeId").type(NUMBER).description("케이크 unique ID (Long)"),
//                                        fieldWithPath("storeId").type(NUMBER).description("가게 unique ID (Long)"),
//                                        fieldWithPath("cakeName").type(STRING).description("케이크 이름"),
//                                        fieldWithPath("description").type(STRING).description("케이크 설명"),
//                                        fieldWithPath("price").type(NUMBER).description("케이크 가격"),
//                                        fieldWithPath("storeName").type(STRING).description("가게 이름"),
//                                        fieldWithPath("cakeImage").type(STRING).description("케이크 이미지"),
//                                        fieldWithPath("sizeOption[].id").type(NUMBER).description("사이즈 옵션 ID (Long)"),
//                                        fieldWithPath("sizeOption[].type").type(STRING).description("케이크 사이즈 옵션"),
//                                        fieldWithPath("sizeOption[].additionalPrice").type(NUMBER).description("추가 가격"),
//                                        fieldWithPath("tasteOption[].id").type(NUMBER).description("맛 옵션 ID (Long)"),
//                                        fieldWithPath("tasteOption[].type").type(STRING).description("케이크 맛 옵션"),
//                                        fieldWithPath("tasteOption[].additionalPrice").type(NUMBER).description("추가 가격"),
//                                        fieldWithPath("creamOption[].id").type(NUMBER).description("크림 옵션 ID (Long)"),
//                                        fieldWithPath("creamOption[].type").type(STRING).description("케이크 크림 옵션"),
//                                        fieldWithPath("creamOption[].additionalPrice").type(NUMBER).description("추가 가격"),
//                                        fieldWithPath("colorOption[].id").type(NUMBER).description("색상 옵션 ID (Long)"),
//                                        fieldWithPath("colorOption[].type").type(STRING).description("케이크 색상 옵션"),
//                                        fieldWithPath("colorOption[].additionalPrice").type(NUMBER).description("추가 가격"),
//                                        fieldWithPath("colorOption[].colorImage").type(STRING).description("케이크 색상 사진"),
//                                        fieldWithPath("etcOption").type(ARRAY).description("기타 옵션 리스트")
//                                )
//                                .requestSchema(Schema.schema("colorOptionResponse"))
//                                .responseSchema(Schema.schema("CakeResponse"))
//                                .build())));
//    }
//
//    @DisplayName("케익 기타 옵션 추가 API (성공)")
//    @Test
//    void add_etc_option_success() throws Exception {
//
//        // given
//        EtcOptionRequest etcOptionRequest = new EtcOptionRequest();
//        etcOptionRequest.setType("초");
//        etcOptionRequest.setAdditionalPrice(1000);
//
//        String jsonRq = objectMapper.writeValueAsString(etcOptionRequest);
//
//        // when
//        CakeResponse cakeResponse = new CakeResponse();
//        cakeResponse.setCakeId(1L);
//        cakeResponse.setStoreId(1L);
//        cakeResponse.setCakeName("앱티브 케익");
//        cakeResponse.setDescription("앱티브만의 특별한 재료로 만든 케이크입니다.");
//        cakeResponse.setPrice(15000);
//        cakeResponse.setStoreName("앱티브 케익가게");
//        cakeResponse.setCakeImage("https://pieceofcake-bucket.s3.ap-northeast-2.amazonaws.com/images/~~");
//
//        ArrayList<SizeOptionResponse> sizeOptions = new ArrayList<>();
//        SizeOptionResponse sizeOptionResponse = new SizeOptionResponse();
//        sizeOptionResponse.setId(1L);
//        sizeOptionResponse.setType("도시락 1호 케이크");
//        sizeOptionResponse.setAdditionalPrice(0);
//
//        SizeOptionResponse sizeOptionResponse1 = new SizeOptionResponse();
//        sizeOptionResponse1.setId(2L);
//        sizeOptionResponse1.setType("홀 케이크 1호");
//        sizeOptionResponse1.setAdditionalPrice(3000);
//
//        sizeOptions.add(sizeOptionResponse);
//        sizeOptions.add(sizeOptionResponse1);
//
//        cakeResponse.setSizeOption(sizeOptions);
//
//        ArrayList<TasteOptionResponse> tasteOptionResponses = new ArrayList<>();
//        TasteOptionResponse tasteOptionResponse = new TasteOptionResponse();
//        tasteOptionResponse.setId(1L);
//        tasteOptionResponse.setType("초코시트");
//        tasteOptionResponse.setAdditionalPrice(1000);
//
//        TasteOptionResponse tasteOptionResponse1 = new TasteOptionResponse();
//        tasteOptionResponse1.setId(2L);
//        tasteOptionResponse1.setType("딸기시트");
//        tasteOptionResponse1.setAdditionalPrice(1500);
//
//        tasteOptionResponses.add(tasteOptionResponse);
//        tasteOptionResponses.add(tasteOptionResponse1);
//
//        cakeResponse.setTasteOption(tasteOptionResponses);
//
//        ArrayList<CreamOptionResponse> creamOptionResponses = new ArrayList<>();
//        CreamOptionResponse creamOptionResponse = new CreamOptionResponse();
//        creamOptionResponse.setId(1L);
//        creamOptionResponse.setType("바닐라 크림");
//        creamOptionResponse.setAdditionalPrice(1000);
//
//        creamOptionResponses.add(creamOptionResponse);
//        cakeResponse.setCreamOption(creamOptionResponses);
//
//        ArrayList<ColorOptionResponse> colorOptionResponses = new ArrayList<>();
//        ColorOptionResponse colorOptionResponse = new ColorOptionResponse();
//        colorOptionResponse.setId(1L);
//        colorOptionResponse.setType("빨강");
//        colorOptionResponse.setAdditionalPrice(1000);
//        colorOptionResponse.setColorImage("https://pieceofcake-bucket.s3.ap-northeast-2.amazonaws.com/images/~~");
//
//        colorOptionResponses.add(colorOptionResponse);
//        cakeResponse.setColorOption(colorOptionResponses);
//
//        ArrayList<EtcOptionResponse> etcOptionResponses = new ArrayList<>();
//        EtcOptionResponse etcOptionResponse = new EtcOptionResponse();
//        etcOptionResponse.setId(1L);
//        etcOptionResponse.setType("초");
//        etcOptionResponse.setAdditionalPrice(1000);
//
//        etcOptionResponses.add(etcOptionResponse);
//        cakeResponse.setEtcOption(etcOptionResponses);
//
//        when(cakeService.addEtcOption(any(Long.class), any(EtcOptionRequest.class))).thenReturn(cakeResponse);
//
//        // then
//        mockMvc.perform(
//                        RestDocumentationRequestBuilders.patch(DEFAULT_URL + "/{cakeId}/etc", 1L)
//                                .content(jsonRq)
//                                .contentType(APPLICATION_JSON)
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("addEtcOption",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        resource(ResourceSnippetParameters.builder()
//                                .tag("Cake API")
//                                .summary("케이크 기타 옵션 추가 API")
//                                .description("케이크의 기타 옵션을 추가할 수 있습니다. 여러 개의 옵션 추가를 원할 경우 여러 번 추가하면 됩니다.")
//                                .requestFields(
//                                        fieldWithPath("type").type(STRING).description("케이크 기타 옵션"),
//                                        fieldWithPath("additionalPrice").type(NUMBER).description("추가 가격")
//                                )
//                                .responseFields(
//                                        fieldWithPath("cakeId").type(NUMBER).description("케이크 unique ID (Long)"),
//                                        fieldWithPath("storeId").type(NUMBER).description("가게 unique ID (Long)"),
//                                        fieldWithPath("cakeName").type(STRING).description("케이크 이름"),
//                                        fieldWithPath("description").type(STRING).description("케이크 설명"),
//                                        fieldWithPath("price").type(NUMBER).description("케이크 가격"),
//                                        fieldWithPath("storeName").type(STRING).description("가게 이름"),
//                                        fieldWithPath("cakeImage").type(STRING).description("케이크 이미지"),
//                                        fieldWithPath("sizeOption[].id").type(NUMBER).description("사이즈 옵션 ID (Long)"),
//                                        fieldWithPath("sizeOption[].type").type(STRING).description("케이크 사이즈 옵션"),
//                                        fieldWithPath("sizeOption[].additionalPrice").type(NUMBER).description("추가 가격"),
//                                        fieldWithPath("tasteOption[].id").type(NUMBER).description("맛 옵션 ID (Long)"),
//                                        fieldWithPath("tasteOption[].type").type(STRING).description("케이크 맛 옵션"),
//                                        fieldWithPath("tasteOption[].additionalPrice").type(NUMBER).description("추가 가격"),
//                                        fieldWithPath("creamOption[].id").type(NUMBER).description("크림 옵션 ID (Long)"),
//                                        fieldWithPath("creamOption[].type").type(STRING).description("케이크 크림 옵션"),
//                                        fieldWithPath("creamOption[].additionalPrice").type(NUMBER).description("추가 가격"),
//                                        fieldWithPath("colorOption[].id").type(NUMBER).description("색상 옵션 ID (Long)"),
//                                        fieldWithPath("colorOption[].type").type(STRING).description("케이크 색상 옵션"),
//                                        fieldWithPath("colorOption[].additionalPrice").type(NUMBER).description("추가 가격"),
//                                        fieldWithPath("colorOption[].colorImage").type(STRING).description("케이크 색상 사진"),
//                                        fieldWithPath("etcOption[].id").type(NUMBER).description("기타 옵션 ID (Long)"),
//                                        fieldWithPath("etcOption[].type").type(STRING).description("케이크 기타 옵션"),
//                                        fieldWithPath("etcOption[].additionalPrice").type(NUMBER).description("추가 가격")
//                                        )
//                                .requestSchema(Schema.schema("CreamOptionRequest"))
//                                .responseSchema(Schema.schema("CakeResponse"))
//                                .build())));
//    }
//
//    @DisplayName("케익 전체보기 API (성공)")
//    @Test
//    void find_all_cake_success() throws Exception {
//
//        // given
//        List<CakeResponse> cakeResponseList = createCakeResponses();
//
//        // when
//        when(cakeService.findAll(any(Long.class))).thenReturn(cakeResponseList);
//
//        // then
//        mockMvc.perform(
//                        RestDocumentationRequestBuilders.get(DEFAULT_URL + "/{storeId}", 1L)
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("findAllCake",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        resource(ResourceSnippetParameters.builder()
//                                .tag("Cake API")
//                                .summary("케이크 전체보기 API")
//                                .description("가게의 모든 케이크를 볼 수 있는 API 입니다.")
//                                .responseFields(
//                                        fieldWithPath("[].cakeId").type(NUMBER).description("케이크 unique ID (Long)"),
//                                        fieldWithPath("[].storeId").type(NUMBER).description("가게 unique ID (Long)"),
//                                        fieldWithPath("[].cakeName").type(STRING).description("케이크 이름"),
//                                        fieldWithPath("[].description").type(STRING).description("케이크 설명"),
//                                        fieldWithPath("[].price").type(NUMBER).description("케이크 가격"),
//                                        fieldWithPath("[].storeName").type(STRING).description("가게 이름"),
//                                        fieldWithPath("[].cakeImage").type(STRING).description("케이크 이미지"),
//                                        fieldWithPath("[].sizeOption[].id").type(NUMBER).description("사이즈 옵션 ID (Long)"),
//                                        fieldWithPath("[].sizeOption[].type").type(STRING).description("케이크 사이즈 옵션"),
//                                        fieldWithPath("[].sizeOption[].additionalPrice").type(NUMBER).description("추가 가격"),
//                                        fieldWithPath("[].tasteOption[].id").type(NUMBER).description("맛 옵션 ID (Long)"),
//                                        fieldWithPath("[].tasteOption[].type").type(STRING).description("케이크 맛 옵션"),
//                                        fieldWithPath("[].tasteOption[].additionalPrice").type(NUMBER).description("추가 가격"),
//                                        fieldWithPath("[].creamOption[].id").type(NUMBER).description("크림 옵션 ID (Long)"),
//                                        fieldWithPath("[].creamOption[].type").type(STRING).description("케이크 크림 옵션"),
//                                        fieldWithPath("[].creamOption[].additionalPrice").type(NUMBER).description("추가 가격"),
//                                        fieldWithPath("[].colorOption[].id").type(NUMBER).description("색상 옵션 ID (Long)"),
//                                        fieldWithPath("[].colorOption[].type").type(STRING).description("케이크 색상 옵션"),
//                                        fieldWithPath("[].colorOption[].additionalPrice").type(NUMBER).description("추가 가격"),
//                                        fieldWithPath("[].colorOption[].colorImage").type(STRING).description("케이크 색상 사진"),
//                                        fieldWithPath("[].etcOption[].id").type(NUMBER).description("기타 옵션 ID (Long)"),
//                                        fieldWithPath("[].etcOption[].type").type(STRING).description("케이크 기타 옵션"),
//                                        fieldWithPath("[].etcOption[].additionalPrice").type(NUMBER).description("추가 가격")
//                                )
//                                .responseSchema(Schema.schema("CakeResponse"))
//                                .build())));
//    }
//
//    @DisplayName("케익 전체보기 API (성공)")
//    @Test
//    void find_all_cake_success_blank() throws Exception {
//
//        // given
//
//        // when
//        when(cakeService.findAll(any(Long.class))).thenReturn(new ArrayList<>());
//
//        // then
//        mockMvc.perform(
//                        RestDocumentationRequestBuilders.get(DEFAULT_URL + "/{storeId}", 1L)
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("findAllCake",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        resource(ResourceSnippetParameters.builder()
//                                .tag("Cake API")
//                                .summary("케이크 전체보기 API")
//                                .description("가게의 모든 케이크를 볼 수 있는 API 입니다.")
//                                .build())));
//    }
//
//    @DisplayName("케익 디테일 보기 API (성공)")
//    @Test
//    void cake_detail_success() throws Exception {
//
//        // given
//
//        // when
//        CakeResponse cakeResponse = new CakeResponse();
//        cakeResponse.setCakeId(1L);
//        cakeResponse.setStoreId(1L);
//        cakeResponse.setCakeName("앱티브 케익");
//        cakeResponse.setDescription("앱티브만의 특별한 재료로 만든 케이크입니다.");
//        cakeResponse.setPrice(15000);
//        cakeResponse.setStoreName("앱티브 케익가게");
//        cakeResponse.setCakeImage("https://pieceofcake-bucket.s3.ap-northeast-2.amazonaws.com/images/~~");
//
//        ArrayList<SizeOptionResponse> sizeOptions = new ArrayList<>();
//        SizeOptionResponse sizeOptionResponse = new SizeOptionResponse();
//        sizeOptionResponse.setId(1L);
//        sizeOptionResponse.setType("도시락 1호 케이크");
//        sizeOptionResponse.setAdditionalPrice(0);
//
//        SizeOptionResponse sizeOptionResponse1 = new SizeOptionResponse();
//        sizeOptionResponse1.setId(2L);
//        sizeOptionResponse1.setType("홀 케이크 1호");
//        sizeOptionResponse1.setAdditionalPrice(3000);
//
//        sizeOptions.add(sizeOptionResponse);
//        sizeOptions.add(sizeOptionResponse1);
//
//        cakeResponse.setSizeOption(sizeOptions);
//
//        ArrayList<TasteOptionResponse> tasteOptionResponses = new ArrayList<>();
//        TasteOptionResponse tasteOptionResponse = new TasteOptionResponse();
//        tasteOptionResponse.setId(1L);
//        tasteOptionResponse.setType("초코시트");
//        tasteOptionResponse.setAdditionalPrice(1000);
//
//        TasteOptionResponse tasteOptionResponse1 = new TasteOptionResponse();
//        tasteOptionResponse1.setId(2L);
//        tasteOptionResponse1.setType("딸기시트");
//        tasteOptionResponse1.setAdditionalPrice(1500);
//
//        tasteOptionResponses.add(tasteOptionResponse);
//        tasteOptionResponses.add(tasteOptionResponse1);
//
//        cakeResponse.setTasteOption(tasteOptionResponses);
//
//        ArrayList<CreamOptionResponse> creamOptionResponses = new ArrayList<>();
//        CreamOptionResponse creamOptionResponse = new CreamOptionResponse();
//        creamOptionResponse.setId(1L);
//        creamOptionResponse.setType("바닐라 크림");
//        creamOptionResponse.setAdditionalPrice(1000);
//
//        creamOptionResponses.add(creamOptionResponse);
//        cakeResponse.setCreamOption(creamOptionResponses);
//
//        ArrayList<ColorOptionResponse> colorOptionResponses = new ArrayList<>();
//        ColorOptionResponse colorOptionResponse = new ColorOptionResponse();
//        colorOptionResponse.setId(1L);
//        colorOptionResponse.setType("빨강");
//        colorOptionResponse.setAdditionalPrice(1000);
//        colorOptionResponse.setColorImage("https://pieceofcake-bucket.s3.ap-northeast-2.amazonaws.com/images/~~");
//
//        colorOptionResponses.add(colorOptionResponse);
//        cakeResponse.setColorOption(colorOptionResponses);
//
//        ArrayList<EtcOptionResponse> etcOptionResponses = new ArrayList<>();
//        EtcOptionResponse etcOptionResponse = new EtcOptionResponse();
//        etcOptionResponse.setId(1L);
//        etcOptionResponse.setType("초");
//        etcOptionResponse.setAdditionalPrice(1000);
//
//        etcOptionResponses.add(etcOptionResponse);
//        cakeResponse.setEtcOption(etcOptionResponses);
//
//        when(cakeService.cakeDetail(any(Long.class), any(Long.class))).thenReturn(cakeResponse);
//
//        // then
//        mockMvc.perform(
//                        RestDocumentationRequestBuilders.get(DEFAULT_URL + "/{storeId}/{cakeId}", 1L, 1L)
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("cakeDetail",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        resource(ResourceSnippetParameters.builder()
//                                .tag("Cake API")
//                                .summary("케이크 디테일 확인 API")
//                                .description("cakeId에 해당하는 케이크 정보를 볼 수 있습니다.")
//                                .responseFields(
//                                        fieldWithPath("cakeId").type(NUMBER).description("케이크 unique ID (Long)"),
//                                        fieldWithPath("storeId").type(NUMBER).description("가게 unique ID (Long)"),
//                                        fieldWithPath("cakeName").type(STRING).description("케이크 이름"),
//                                        fieldWithPath("description").type(STRING).description("케이크 설명"),
//                                        fieldWithPath("price").type(NUMBER).description("케이크 가격"),
//                                        fieldWithPath("storeName").type(STRING).description("가게 이름"),
//                                        fieldWithPath("cakeImage").type(STRING).description("케이크 이미지"),
//                                        fieldWithPath("sizeOption[].id").type(NUMBER).description("사이즈 옵션 ID (Long)"),
//                                        fieldWithPath("sizeOption[].type").type(STRING).description("케이크 사이즈 옵션"),
//                                        fieldWithPath("sizeOption[].additionalPrice").type(NUMBER).description("추가 가격"),
//                                        fieldWithPath("tasteOption[].id").type(NUMBER).description("맛 옵션 ID (Long)"),
//                                        fieldWithPath("tasteOption[].type").type(STRING).description("케이크 맛 옵션"),
//                                        fieldWithPath("tasteOption[].additionalPrice").type(NUMBER).description("추가 가격"),
//                                        fieldWithPath("creamOption[].id").type(NUMBER).description("크림 옵션 ID (Long)"),
//                                        fieldWithPath("creamOption[].type").type(STRING).description("케이크 크림 옵션"),
//                                        fieldWithPath("creamOption[].additionalPrice").type(NUMBER).description("추가 가격"),
//                                        fieldWithPath("colorOption[].id").type(NUMBER).description("색상 옵션 ID (Long)"),
//                                        fieldWithPath("colorOption[].type").type(STRING).description("케이크 색상 옵션"),
//                                        fieldWithPath("colorOption[].additionalPrice").type(NUMBER).description("추가 가격"),
//                                        fieldWithPath("colorOption[].colorImage").type(STRING).description("케이크 색상 사진"),
//                                        fieldWithPath("etcOption[].id").type(NUMBER).description("기타 옵션 ID (Long)"),
//                                        fieldWithPath("etcOption[].type").type(STRING).description("케이크 기타 옵션"),
//                                        fieldWithPath("etcOption[].additionalPrice").type(NUMBER).description("추가 가격")
//                                )
//                                .responseSchema(Schema.schema("CakeResponse"))
//                                .build())));
//    }
//    private List<CakeResponse> createCakeResponses() {
//
//        List<CakeResponse> cakeResponseList = new ArrayList<>();
//
//        CakeResponse cakeResponse = new CakeResponse();
//        cakeResponse.setCakeId(1L);
//        cakeResponse.setStoreId(1L);
//        cakeResponse.setCakeName("앱티브 케익");
//        cakeResponse.setDescription("앱티브만의 특별한 재료로 만든 케이크입니다.");
//        cakeResponse.setPrice(15000);
//        cakeResponse.setStoreName("앱티브 케익가게");
//        cakeResponse.setCakeImage("https://pieceofcake-bucket.s3.ap-northeast-2.amazonaws.com/images/~~");
//
//        ArrayList<SizeOptionResponse> sizeOptions = new ArrayList<>();
//        SizeOptionResponse sizeOptionResponse = new SizeOptionResponse();
//        sizeOptionResponse.setId(1L);
//        sizeOptionResponse.setType("도시락 1호 케이크");
//        sizeOptionResponse.setAdditionalPrice(0);
//
//        SizeOptionResponse sizeOptionResponse1 = new SizeOptionResponse();
//        sizeOptionResponse1.setId(2L);
//        sizeOptionResponse1.setType("홀 케이크 1호");
//        sizeOptionResponse1.setAdditionalPrice(3000);
//
//        sizeOptions.add(sizeOptionResponse);
//        sizeOptions.add(sizeOptionResponse1);
//
//        cakeResponse.setSizeOption(sizeOptions);
//
//        ArrayList<TasteOptionResponse> tasteOptionResponses = new ArrayList<>();
//        TasteOptionResponse tasteOptionResponse = new TasteOptionResponse();
//        tasteOptionResponse.setId(1L);
//        tasteOptionResponse.setType("초코시트");
//        tasteOptionResponse.setAdditionalPrice(1000);
//
//        TasteOptionResponse tasteOptionResponse1 = new TasteOptionResponse();
//        tasteOptionResponse1.setId(2L);
//        tasteOptionResponse1.setType("딸기시트");
//        tasteOptionResponse1.setAdditionalPrice(1500);
//
//        tasteOptionResponses.add(tasteOptionResponse);
//        tasteOptionResponses.add(tasteOptionResponse1);
//
//        cakeResponse.setTasteOption(tasteOptionResponses);
//
//        ArrayList<CreamOptionResponse> creamOptionResponses = new ArrayList<>();
//        CreamOptionResponse creamOptionResponse = new CreamOptionResponse();
//        creamOptionResponse.setId(1L);
//        creamOptionResponse.setType("바닐라 크림");
//        creamOptionResponse.setAdditionalPrice(1000);
//
//        creamOptionResponses.add(creamOptionResponse);
//        cakeResponse.setCreamOption(creamOptionResponses);
//
//        ArrayList<ColorOptionResponse> colorOptionResponses = new ArrayList<>();
//        ColorOptionResponse colorOptionResponse = new ColorOptionResponse();
//        colorOptionResponse.setId(1L);
//        colorOptionResponse.setType("빨강");
//        colorOptionResponse.setAdditionalPrice(1000);
//        colorOptionResponse.setColorImage("https://pieceofcake-bucket.s3.ap-northeast-2.amazonaws.com/images/~~");
//
//        colorOptionResponses.add(colorOptionResponse);
//        cakeResponse.setColorOption(colorOptionResponses);
//
//        ArrayList<EtcOptionResponse> etcOptionResponses = new ArrayList<>();
//        EtcOptionResponse etcOptionResponse = new EtcOptionResponse();
//        etcOptionResponse.setId(1L);
//        etcOptionResponse.setType("초");
//        etcOptionResponse.setAdditionalPrice(1000);
//
//        etcOptionResponses.add(etcOptionResponse);
//        cakeResponse.setEtcOption(etcOptionResponses);
//
//        cakeResponseList.add(cakeResponse);
//
//        // ----------------------------------------------
//        CakeResponse cakeResponse1 = new CakeResponse();
//        cakeResponse1.setCakeId(2L);
//        cakeResponse1.setStoreId(1L);
//        cakeResponse1.setCakeName("파파라치 케익");
//        cakeResponse1.setDescription("앱티브만의 특별한 재료로 만든 케이크입니다.");
//        cakeResponse1.setPrice(18000);
//        cakeResponse1.setStoreName("앱티브 케익가게");
//        cakeResponse1.setCakeImage("https://pieceofcake-bucket.s3.ap-northeast-2.amazonaws.com/images/~~");
//
//        ArrayList<SizeOptionResponse> sizeOptions1 = new ArrayList<>();
//        SizeOptionResponse sizeOptionResponse2 = new SizeOptionResponse();
//        sizeOptionResponse2.setId(3L);
//        sizeOptionResponse2.setType("도시락 1호 케이크");
//        sizeOptionResponse2.setAdditionalPrice(0);
//
//        SizeOptionResponse sizeOptionResponse3 = new SizeOptionResponse();
//        sizeOptionResponse3.setId(4L);
//        sizeOptionResponse3.setType("홀 케이크 1호");
//        sizeOptionResponse3.setAdditionalPrice(3000);
//
//        sizeOptions1.add(sizeOptionResponse2);
//        sizeOptions1.add(sizeOptionResponse3);
//
//        cakeResponse1.setSizeOption(sizeOptions1);
//
//        ArrayList<TasteOptionResponse> tasteOptionResponses1 = new ArrayList<>();
//        TasteOptionResponse tasteOptionResponse2 = new TasteOptionResponse();
//        tasteOptionResponse2.setId(3L);
//        tasteOptionResponse2.setType("바닐라 시트");
//        tasteOptionResponse2.setAdditionalPrice(0);
//
//        TasteOptionResponse tasteOptionResponse3 = new TasteOptionResponse();
//        tasteOptionResponse3.setId(4L);
//        tasteOptionResponse3.setType("얼그레이 시트");
//        tasteOptionResponse3.setAdditionalPrice(2000);
//
//        tasteOptionResponses1.add(tasteOptionResponse2);
//        tasteOptionResponses1.add(tasteOptionResponse3);
//
//        cakeResponse1.setTasteOption(tasteOptionResponses1);
//
//        ArrayList<CreamOptionResponse> creamOptionResponses1 = new ArrayList<>();
//        CreamOptionResponse creamOptionResponse1 = new CreamOptionResponse();
//        creamOptionResponse1.setId(2L);
//        creamOptionResponse1.setType("초코 크림");
//        creamOptionResponse1.setAdditionalPrice(1500);
//
//        creamOptionResponses1.add(creamOptionResponse1);
//        cakeResponse1.setCreamOption(creamOptionResponses1);
//
//        ArrayList<ColorOptionResponse> colorOptionResponses1 = new ArrayList<>();
//        ColorOptionResponse colorOptionResponse1 = new ColorOptionResponse();
//        colorOptionResponse1.setId(2L);
//        colorOptionResponse1.setType("파랑");
//        colorOptionResponse1.setAdditionalPrice(1000);
//        colorOptionResponse1.setColorImage("https://pieceofcake-bucket.s3.ap-northeast-2.amazonaws.com/images/~~");
//
//        colorOptionResponses1.add(colorOptionResponse);
//        cakeResponse1.setColorOption(colorOptionResponses1);
//
//        ArrayList<EtcOptionResponse> etcOptionResponses1 = new ArrayList<>();
//        EtcOptionResponse etcOptionResponse1 = new EtcOptionResponse();
//        etcOptionResponse1.setId(2L);
//        etcOptionResponse1.setType("초");
//        etcOptionResponse1.setAdditionalPrice(1000);
//
//        etcOptionResponses1.add(etcOptionResponse);
//        cakeResponse1.setEtcOption(etcOptionResponses1);
//
//        cakeResponseList.add(cakeResponse1);
//
//        return cakeResponseList;
//    }
//}
