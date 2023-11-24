package com.legacy.config.spring;

import com.legacy.application.common.config.security.LoginFailureHandler;
import com.legacy.application.common.config.security.LoginSuccessHandler;
import com.legacy.application.common.config.security.LogoutSuccessHandler;
import com.legacy.application.common.config.security.SecurityAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = {"com.legacy.application.common.config.security","com.legacy.application.common.system.login.**"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Autowired
    LoginFailureHandler loginFailureHandler;

    @Autowired
    LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    SecurityAuthenticationProvider securityAuthenticationProvider;

    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers("/resources/**", "/**.jsp", "/images/**", "/css/**", "/cmmn/**", "/js/**", "/file/ckeditorFileupload");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests() // 요청에 대한 보안검사 실행
                .antMatchers("/mng/**").hasRole("ADMIN")// 관리자 페이지는 ADMIN 권한만 접근 가능
                .anyRequest().permitAll()// 다른 요청은 인증 없이 가능
                .and()
            .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/proc")
                .usernameParameter("user_id")
                .passwordParameter("user_pwd")
                .successHandler(loginSuccessHandler) // 로그인 성공 핸들러
                .failureHandler(loginFailureHandler) // 로그인 실패 핸들러
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
            .authenticationProvider(securityAuthenticationProvider) // 로그인 인증 처리
            .csrf() // csrf 공격 방지 토큰 사용 설정
                .disable()
                .httpBasic() // 사용자 인증방법으로 HTTP Basic Authentication 사용
                .and()
            .portMapper()
                .http(80)
                .mapsTo(443)
                .and()
            .portMapper()
                .http(8080)
                .mapsTo(443)
                .and()
            .sessionManagement()
                .maximumSessions(1);
    }
}
