package apptive.pieceOfCake.users;

import apptive.pieceOfCake.auth.RoleName;
import apptive.pieceOfCake.base.impl.BaseServiceImpl;
import apptive.pieceOfCake.config.ControllerTestConfig;
import apptive.pieceOfCake.users.exception.MemberException;
import apptive.pieceOfCake.users.model.Member;
import apptive.pieceOfCake.users.model.request.MemberCheckSameEmail;
import apptive.pieceOfCake.users.model.request.MemberRequest;
import apptive.pieceOfCake.users.model.request.MemberUpdateRequest;
import apptive.pieceOfCake.users.model.response.MemberMyPageResponse;
import apptive.pieceOfCake.users.model.response.MemberResponse;
import apptive.pieceOfCake.users.repository.MemberRepository;
import apptive.pieceOfCake.users.service.MemberService;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.ActiveProfiles;

import static apptive.pieceOfCake.users.exception.MemberExceptionType.ALREADY_EXIST_USERNAME;
import static apptive.pieceOfCake.users.exception.MemberExceptionType.NOT_FOUND_MEMBER;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//@WebMvcTest(MemberController.class)
@ActiveProfiles("dev")
public class MemberControllerTest extends ControllerTestConfig {

    @Autowired
    ObjectMapper objectMapper;
    private static final String DEFAULT_URL = "/user";

    @MockBean
    private BaseServiceImpl<Member, MemberResponse, MemberRepository> baseService;

    @MockBean
    private MemberService memberService;


    @DisplayName("회원 생성 API (성공) - 로그인 적용 전")
    @Test
    void saveMember_success() throws Exception {
        // given
        MemberRequest memberRequest = new MemberRequest();
        memberRequest.setName("홍길동");
        memberRequest.setEmail("apptive1234@naver.com");
        memberRequest.setLoginPwd("pwd1234");
        memberRequest.setAddress("부산시 금정구 부산대학로63번길 2");
        memberRequest.setPhoneNum("010-1234-5678");
        memberRequest.setLatitude(35.1355);
        memberRequest.setLongitude(129.5);

        String jsonRq = objectMapper.writeValueAsString(memberRequest);

        // when // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.post(DEFAULT_URL + "/save")
                                .content(jsonRq)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("createUser",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("User API")
                                .summary("회원 가입 API")
                                .requestFields(
                                        fieldWithPath("email").type(STRING).description("로그인 아이디"),
                                        fieldWithPath("loginPwd").type(STRING).description("로그인 비밀번호"),
                                        fieldWithPath("name").type(STRING).description("이름"),
                                        fieldWithPath("phoneNum").type(STRING).description("이름"),
                                        fieldWithPath("address").type(STRING).description("이름"),
                                        fieldWithPath("latitude").type(NUMBER).description("위도 (double)"),
                                        fieldWithPath("longitude").type(NUMBER).description("경도 (double)")
                                )
                                .requestSchema(Schema.schema("MemberRequest"))
                                .responseSchema(Schema.schema("userId"))
                                .build())));
    }

    @DisplayName("마이페이지 API (성공)")
    @Test
    void findMember_success() throws Exception {
        // given
        Long userId = 1L;
        Member member = new Member();
        member.setId(userId);
        member.setName("홍길동");
        member.setEmail("apptive1234@naver.com");
        member.setLoginPwd("pwd1234");
        member.setAddress("부산시 금정구 부산대학로63번길 2");
        member.setPhoneNum("010-1234-5678");
        member.setAgreementOfMarketing(true);
        member.setRoleName(RoleName.ROLE_USER);

        MemberMyPageResponse memberMyPageResponse = MemberMyPageResponse.of(member);

        when(memberService.find(userId)).thenReturn(memberMyPageResponse);

        // when // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get(DEFAULT_URL + "/myPage/{userId}", userId)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("findUser(myPage)",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("User API")
                                .summary("myPage API (성공)")
                                .pathParameters(
                                        parameterWithName("userId").description("회원 unique Id")
                                )
                                .responseFields(
                                        fieldWithPath("userId").type(NUMBER).description("회원 unique Id (Long)"),
                                        fieldWithPath("email").type(STRING).description("로그인 아이디"),
                                        fieldWithPath("loginPwd").type(STRING).description("로그인 비밀번호"),
                                        fieldWithPath("name").type(STRING).description("이름"),
                                        fieldWithPath("phoneNum").type(STRING).description("이름"),
                                        fieldWithPath("address").type(STRING).description("이름"),
                                        fieldWithPath("agreementOfMarketing").type(BOOLEAN).description("마케팅 동의 여부")
                                )
                                .requestSchema(Schema.schema("userId"))
                                .responseSchema(Schema.schema("MemberMyPageResponse"))
                                .build())));
    }

    @DisplayName("마이페이지 API (실패)")
    @Test
    void findMember_fail_no_member() throws Exception {

        // given
        Long userId = 1L;

        // when
        when(memberService.find(userId)).thenThrow(new MemberException(NOT_FOUND_MEMBER));

        // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get(DEFAULT_URL + "/myPage/{userId}", userId)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("회원을 찾을 수 없습니다."))
                .andDo(document("findUser(myPage)-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("User API")
                                .summary("myPage API (실패)")
                                .pathParameters(
                                        parameterWithName("userId").description("회원 unique Id")
                                )
                                .responseFields()
                                .requestSchema(Schema.schema("userId"))
                                .build())));
    }

    @Test
    @DisplayName("업데이트 (성공)")
    public void update_member_success() throws Exception {

        // given
        Long userId = 1L;
        MemberUpdateRequest memberUpdateRequest = new MemberUpdateRequest();
        memberUpdateRequest.setName("홍길동");
        memberUpdateRequest.setEmail("apptive1234@naver.com");
        memberUpdateRequest.setLoginPwd("pwd1234");
        memberUpdateRequest.setAddress("부산시 금정구 부산대학로63번길 2");
        memberUpdateRequest.setPhoneNum("010-1234-5678");
        memberUpdateRequest.setLongitude(129.0500);
        memberUpdateRequest.setLatitude(35.1355);
        memberUpdateRequest.setAgreementOfMarketing(true);

        String jsonRq = objectMapper.writeValueAsString(memberUpdateRequest);

        Member member = new Member();
        member.setId(userId);
        member.setName("홍길동");
        member.setEmail("apptive1234@naver.com");
        member.setLoginPwd("pwd1234");
        member.setAddress("부산시 금정구 부산대학로63번길 2");
        member.setPhoneNum("010-1234-5678");
        member.setAgreementOfMarketing(true);
        member.setRoleName(RoleName.ROLE_USER);

        MemberMyPageResponse memberMyPageResponse = MemberMyPageResponse.of(member);

        //when
        when(memberService.update(any(Long.class), any(MemberUpdateRequest.class))).thenReturn(memberMyPageResponse);

        // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.patch(DEFAULT_URL + "/update/{userId}", userId)
                                .content(jsonRq)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("updateUser(myPage)",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("User API")
                                .summary("Member Update API (성공)")
                                .pathParameters(
                                        parameterWithName("userId").description("회원 unique Id")
                                )
                                .requestFields(
                                        fieldWithPath("email").type(STRING).description("로그인 아이디"),
                                        fieldWithPath("loginPwd").type(STRING).description("로그인 비밀번호"),
                                        fieldWithPath("name").type(STRING).description("이름"),
                                        fieldWithPath("phoneNum").type(STRING).description("이름"),
                                        fieldWithPath("address").type(STRING).description("이름"),
                                        fieldWithPath("latitude").type(NUMBER).description("위도 (double)"),
                                        fieldWithPath("longitude").type(NUMBER).description("경도 (double)"),
                                        fieldWithPath("agreementOfMarketing").type(BOOLEAN).description("마케팅 동의 여부")
                                )
                                .responseFields(
                                        fieldWithPath("userId").type(NUMBER).description("회원 unique Id (Long)"),
                                        fieldWithPath("email").type(STRING).description("로그인 아이디"),
                                        fieldWithPath("loginPwd").type(STRING).description("로그인 비밀번호"),
                                        fieldWithPath("name").type(STRING).description("이름"),
                                        fieldWithPath("phoneNum").type(STRING).description("이름"),
                                        fieldWithPath("address").type(STRING).description("이름"),
                                        fieldWithPath("agreementOfMarketing").type(BOOLEAN).description("마케팅 동의 여부")
                                )
                                .requestSchema(Schema.schema("MemberUpdateRequest"))
                                .responseSchema(Schema.schema("MemberMyPageResponse"))
                                .build())));
    }

    @Test
    @DisplayName("업데이트 (실패)")
    public void update_member_fail() throws Exception {

        // given
        Long userId = 1L;
        MemberUpdateRequest memberUpdateRequest = new MemberUpdateRequest();
        memberUpdateRequest.setName("홍길동");
        memberUpdateRequest.setEmail("apptive1234@naver.com");
        memberUpdateRequest.setLoginPwd("pwd1234");
        memberUpdateRequest.setAddress("부산시 금정구 부산대학로63번길 2");
        memberUpdateRequest.setPhoneNum("010-1234-5678");
        memberUpdateRequest.setLongitude(129.0500);
        memberUpdateRequest.setLatitude(35.1355);
        memberUpdateRequest.setAgreementOfMarketing(true);

        String jsonRq = objectMapper.writeValueAsString(memberUpdateRequest);

        //when
        when(memberService.update(any(Long.class), any(MemberUpdateRequest.class))).thenThrow(new MemberException(NOT_FOUND_MEMBER));

        // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.patch(DEFAULT_URL + "/update/{userId}", userId)
                                .content(jsonRq)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(document("updateUser(myPage) - fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("User API")
                                .summary("Member Update API (실패)")
                                .pathParameters(
                                        parameterWithName("userId").description("회원 unique Id")
                                )
                                .requestFields(
                                        fieldWithPath("email").type(STRING).description("로그인 아이디"),
                                        fieldWithPath("loginPwd").type(STRING).description("로그인 비밀번호"),
                                        fieldWithPath("name").type(STRING).description("이름"),
                                        fieldWithPath("phoneNum").type(STRING).description("이름"),
                                        fieldWithPath("address").type(STRING).description("이름"),
                                        fieldWithPath("latitude").type(NUMBER).description("위도 (double)"),
                                        fieldWithPath("longitude").type(NUMBER).description("경도 (double)"),
                                        fieldWithPath("agreementOfMarketing").type(BOOLEAN).description("마케팅 동의 여부")
                                )
                                .responseFields()
                                .requestSchema(Schema.schema("MemberUpdateRequest"))
                                .build())));
    }

    @Test
    @DisplayName("이메일 체크 (성공)")
    public void check_email_success() throws Exception {

        // given
        Long userId = 1L;
        MemberCheckSameEmail memberCheckSameEmail = new MemberCheckSameEmail();
        memberCheckSameEmail.setLoginId("test123@apptive.com");

        String jsonRq = objectMapper.writeValueAsString(memberCheckSameEmail);

        //when
        when(memberService.checkSameEmail(any(MemberCheckSameEmail.class))).thenReturn("사용 가능한 아이디입니다.");

        // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get(DEFAULT_URL + "/checkEmail")
                                .content(jsonRq)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("checkEmail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("User API")
                                .summary("이메일 중복 확인 (중복 없음/사용가능)")
                                .requestFields(
                                        fieldWithPath("loginId").type(STRING).description("로그인 아이디 (이메일)")
                                )
                                .responseFields()
                                .requestSchema(Schema.schema("MemberCheckSameEmail"))
                                .build())));
    }

    @Test
    @DisplayName("이메일 체크 (실패)")
    public void check_email_fail() throws Exception {

        // given
        MemberCheckSameEmail memberCheckSameEmail = new MemberCheckSameEmail();
        memberCheckSameEmail.setLoginId("test123@apptive.com");

        String jsonRq = objectMapper.writeValueAsString(memberCheckSameEmail);

        //when
        when(memberService.checkSameEmail(any(MemberCheckSameEmail.class))).thenThrow(new MemberException(ALREADY_EXIST_USERNAME));

        // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get(DEFAULT_URL + "/checkEmail")
                                .content(jsonRq)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("checkEmail - fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("User API")
                                .summary("이메일 중복 확인 (중복 있음/사용불가능)")
                                .requestFields(
                                        fieldWithPath("loginId").type(STRING).description("로그인 아이디 (이메일)")
                                )
                                .responseFields()
                                .requestSchema(Schema.schema("MemberCheckSameEmail"))
                                .build())));
    }
}
