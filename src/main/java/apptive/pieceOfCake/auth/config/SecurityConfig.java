package apptive.pieceOfCake.auth.config;

import apptive.pieceOfCake.auth.RoleName;
import apptive.pieceOfCake.auth.handler.CustomAccessDeniedHandler;
import apptive.pieceOfCake.auth.handler.CustomAuthenticationEntryPointHandler;
import apptive.pieceOfCake.auth.jwt.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    private final CustomAuthenticationEntryPointHandler customAuthenticationEntryPointHandler;

    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public SecurityFilterChain config(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);

        // white list (Spring Security 체크 제외 목록)
        MvcRequestMatcher[] permitAllWhiteList = {
                mvc.pattern("/"),
                mvc.pattern("/user/login"), // 유저 로그인
                mvc.pattern("/user/save"), // 유저 회원가입
                mvc.pattern("/owner/login"), // 사장님 로그인
                mvc.pattern("/owner/save"), // 사장님 회원가입
                mvc.pattern("/store/**"),   // 가게 관련
                mvc.pattern("/favicon.ico"),
                mvc.pattern("/resources/**"),
                mvc.pattern("/js/**")
        };

        // http request 인증 설정
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(permitAllWhiteList).permitAll()
                // 가게 관리는 사장님 권한만 가능
                .requestMatchers("/cake/**").hasRole(RoleName.ROLE_STORE.getRole())
                // 그 외 요청 체크
                .anyRequest().authenticated()
        );

        // form login disable
        http.formLogin(AbstractHttpConfigurer::disable);

        // logout disable
        http.logout(AbstractHttpConfigurer::disable);

        // csrf disable
        http.csrf(AbstractHttpConfigurer::disable);

        // session management
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 미사용
        );

        // before filter
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        // exception handler
        http.exceptionHandling(conf -> conf
                .authenticationEntryPoint(customAuthenticationEntryPointHandler)
                .accessDeniedHandler(customAccessDeniedHandler)
        );

        // build
        return http.build();
    }
}
