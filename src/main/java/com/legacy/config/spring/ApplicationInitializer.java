package com.legacy.config.spring;

import org.springframework.mobile.device.DeviceResolverRequestFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * web.xml 을 java 로 구현
 * WebApplicationInitializer
 *
 */
public class ApplicationInitializer implements WebApplicationInitializer {

    /**
     * 서블릿 컨테이너가 실행될 때 onStartup() 메소드가 자동으로 호출됌
     * @param servletContext the {@code ServletContext} to initialize
     * @throws ServletException
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // 컴포넌트 스캔을 위한 spring boot 에서 사용하는 객체 AnnotationConfigWebApplicationContext
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(SecurityConfig.class); // spring security .java 파일 등록 ( @Configuration 항목 등록 )
        rootContext.register(MybatisConfig.class); // ( root-context.xml 구현부 등록 )
        servletContext.addListener(new ContextLoaderListener(rootContext));
        this.addDispatcherServlet(servletContext); // 디스패처 서블릿 설정 추가
        this.encodingFilter(servletContext); // 인코딩 필터 추가
//        this.corsFilter(servletContext); // 나중에 추가
        this.deviceResolverRequestFilter(servletContext); // 디바이스 필터 추가
    }

    /**
     * dispatcherServlet 추가
     * servlet-context.xml 구현부 연결..
     * @param servletContext
     */
    private void addDispatcherServlet(ServletContext servletContext){
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.getEnvironment().addActiveProfile("production");
        applicationContext.register(WebMvcConfig.class); // servlet-context.xml 역할을 하는 class 등록
        // 동적 어노테이션
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("WorkDispatcherServlet", new DispatcherServlet(applicationContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        dispatcher.setInitParameter("dispatchOptionsRequest", "true");
    }

    /**
     * 인코딩 필터
     * @param servletContext
     */
    private void encodingFilter(ServletContext servletContext) {
        FilterRegistration.Dynamic filter = servletContext.addFilter("CHARACTER_ENCODING_FILTER", CharacterEncodingFilter.class);
        filter.setInitParameter("encoding", "UTF-8");
        filter.setInitParameter("forceEncoding", "true");
        filter.addMappingForUrlPatterns(null, false, "/*");
    }

    // CORS ...  요청 방식 등을 설정하는것.. POST 랑 GET 만 들어올 수 있게 설정하는 것들도 포함
//    private void corsFilter(ServletContext servletContext) {
//        FilterRegistration.Dynamic filter = servletContext.addFilter("CORS_FILTER", CORSFilter.class);
//        filter.addMappingForUrlPatterns(null, false, "/*");
//    }

    /**
     * 디바이스 필터
     * @param servletContext
     */
    private void deviceResolverRequestFilter(ServletContext servletContext) {
        FilterRegistration.Dynamic filter = servletContext.addFilter("DEVICE_FILTER", DeviceResolverRequestFilter.class);
        filter.addMappingForUrlPatterns(null, false, "/*");
    }
}
