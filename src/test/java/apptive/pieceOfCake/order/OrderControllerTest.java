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
public class OrderControllerTest extends ControllerTestConfig {

    @Autowired
    ObjectMapper objectMapper;

    private static final String DEFAULT_URL = "/order";

    @MockBean
    private OrderService orderService;

    // TODO: 주문 생성 관련 test 작성
    @DisplayName("주문 생성 API (성공)")
    @Test
    void saveOrder_success() throws Exception {

        // given

        // when // then
    }
}
