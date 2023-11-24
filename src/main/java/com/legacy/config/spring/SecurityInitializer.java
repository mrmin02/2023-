package com.legacy.config.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 스프링 시큐리티 기동
 */
@EnableWebMvc
@Configuration
@Import(SecurityConfig.class)
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

}
