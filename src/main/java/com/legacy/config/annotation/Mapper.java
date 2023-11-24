package com.legacy.config.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 2023-10-10 의존성 주입을 위한 mapper 어노테이션
 * mybatis-spring:scan 에 명시된 패키지 이외에 추가적인 mapper 지정을 위한 mybatis mapper 어노테이션과 다르게
 * service 에서 사용할 목적의 의존성 주입을 위한 어노테이션
 *
 *  -- 참고 --
 * 전자정부 프레임워크의 어노테이션 사용으로 해당 Mapper 파일 사용 X
 *
 *     수정일     /   수정자     / 수정내용
 * ------------------------------
 * 2023-10-10   /   최민석	 / 최초 생성
 *
 * @author mrmin
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Mapper {
	String value() default "";
}
