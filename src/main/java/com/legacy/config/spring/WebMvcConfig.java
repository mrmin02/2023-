package com.legacy.config.spring;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

import java.util.concurrent.Executor;

/**
 * servlet-context.xml > java file
 */
@Configuration
@EnableWebMvc  // mvc:annotaion-driven 역할
@EnableAsync    // task:annotation driven 역할
@EnableScheduling
@ComponentScan(basePackages = {"com.legacy.application"})
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 타일즈 설정
     * @return
     */
    @Bean
    public UrlBasedViewResolver tilesResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();

        resolver.setViewClass(TilesView.class);
        /**
         * 아래의 JstlView 의 View Resolver보다 Order을 높게 설정한다.
         */
//        resolver.setOrder(1);
        return resolver;
    }

    /**
     * Tiles 정의파일 설정
     */
    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions("/WEB-INF/config/tiles/tiles.xml");
        tilesConfigurer.setCheckRefresh(true);
        return tilesConfigurer;
    }


    /**
     *  뷰 설정
     * @return
     */
    @Bean
    public UrlBasedViewResolver resolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/views");
        resolver.setSuffix(".jsp");
        resolver.setRedirectHttp10Compatible(false); // https 는 https 로 , http 는 http 로 리다이렉트
        return resolver;
    }

    /**
     * 리소스 경로 설정
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    /**
     * 인터셉터 등록
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new CorsInterceptor());
        registry.addInterceptor(localeChangeInterceptor()); // 다국어 파라메터 관련 인터셉터 등록
    }

    /**
     * 멀티파일 설정
     * @return
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(200000000);
        return commonsMultipartResolver;
    }

    /**
     * ASYNC  관련 Bean 등록
     * @return
     */
    @Bean
    public Executor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

    /**
     * 컨트롤러에서 JSON 데이터 RETURN 위한 Bean 등록
     * @return
     */
    @Bean
    public BeanNameViewResolver beanNameViewResolver() {
        BeanNameViewResolver beanNameViewResolver = new BeanNameViewResolver();
        beanNameViewResolver.setOrder(0);
        return beanNameViewResolver;
    }


    /**
     * --------- 다국어 관련 설정 시작 ---------
     */
    @Bean
    public SessionLocaleResolver sessionLocaleResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        return sessionLocaleResolver;
    }

    /**
     * 요청 param 에 language 포함 될 경우, local을 셋팅해줌
     * @return
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("language");
        return localeChangeInterceptor;
    }

    /**
     * spring boot가 아닐 경우, 다국어 메시지 관련 Bean 등록
     * @return
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("messages");
        return source;
    }
    /**
     * --------- 다국어 관련 설정 끝 ---------
     */
}
