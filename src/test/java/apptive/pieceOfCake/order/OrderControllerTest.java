package apptive.pieceOfCake.order;

import apptive.pieceOfCake.config.ControllerTestConfig;
import apptive.pieceOfCake.order.model.response.OrderResponse;
import apptive.pieceOfCake.order.service.OrderService;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("dev")
public class OrderControllerTest extends ControllerTestConfig {

    @Autowired
    ObjectMapper objectMapper;

    private static final String DEFAULT_URL = "/order";

    @MockBean
    private OrderService orderService;

    @DisplayName("주문 생성 API (성공)")
    @Test
    void saveOrder_success() throws Exception {

        // given

        // when // then
    }

    @DisplayName("주문 보기 API - 값 없음")
    @Test
    void findAll_withoutOrder() throws Exception {

        // given
        Long memberId = 1L;

        // when
        when(orderService.findAll(any(Long.class))).thenReturn(new ArrayList<>());

        // then
        mockMvc.perform(
                        get(DEFAULT_URL + "/{memberId}", memberId)
                )
                //.andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("findOrder",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Order API")
                                        .summary("주문 찾기 API")
                                        .description("자신이 주문한 케이크들을 볼 수 있습니다.")
                                        .pathParameters(
                                                parameterWithName("memberId").description("회원 unique ID")
                                        )
                                        .build())));
    }

    @DisplayName("주문 보기 API - 값 있음")
    @Test
    void findAll_withOrder() throws Exception {

        // given
        Long memberId = 1L;

        List<OrderResponse> orderInfos = new ArrayList<>();
        OrderResponse orderRes1 = new OrderResponse();
        orderRes1.setOrderId(1L);
        // TODO: response 값 설정 및 테스트 진행

        // when
        when(orderService.findAll(any(Long.class))).thenReturn(orderInfos);

        // then
        mockMvc.perform(
                        get(DEFAULT_URL + "/{memberId}", memberId)
                )
                //.andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("findOrder",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("Order API")
                                        .summary("주문 찾기 API")
                                        .description("자신이 주문한 케이크들을 볼 수 있습니다.")
                                        .pathParameters(
                                                parameterWithName("memberId").description("회원 unique ID")
                                        )
                                        .build())));
    }
}
