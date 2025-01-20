package apptive.pieceOfCake.order;

import apptive.pieceOfCake.config.ControllerTestConfig;
import apptive.pieceOfCake.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
public class OrderListControllerTest extends ControllerTestConfig {

    @Autowired
    ObjectMapper objectMapper;

    private static final String DEFAULT_URL = "/order";

    @MockBean
    private OrderService orderService;

    // TODO: 주문/장바구니 관련 test 작성

    @DisplayName("주문 보기 API - 값 없음")
    @Test
    void findAll_withoutOrder() throws Exception {

        // given
        Long memberId = 1L;

        // when

        // then

    }

    @DisplayName("주문 보기 API - 값 있음")
    @Test
    void findAll_withOrder() throws Exception {

        // given

        // when

        // then
    }
}
