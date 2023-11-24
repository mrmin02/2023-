package com.legacy.config.spring;

//import com.legacy.config.annotation.Mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import net.sf.log4jdbc.tools.Log4JdbcCustomFormatter;
import net.sf.log4jdbc.tools.LoggingType;

import javax.sql.DataSource;

/**
 * root-context.xml 역할, RootConfig.java 대신..
 */
@Configuration
@EnableTransactionManagement // 트랜잭션 처리
public class MybatisConfig {

    /**
     * DataSource 등록
     * @return
     */
    @Bean
    public DataSource dataSourceWEB(){
        JndiDataSourceLookup jndiDataSourceLookup = new JndiDataSourceLookup();
        jndiDataSourceLookup.setResourceRef(true);
        DataSource dataSource = jndiDataSourceLookup.getDataSource("java:/comp/env/jdbc/minseok");

        // 쿼리 로그를 보기 위한 Log4j2 설정
        Log4jdbcProxyDataSource log4jdbcProxyDataSource = new Log4jdbcProxyDataSource(dataSource);
        Log4JdbcCustomFormatter log4JdbcCustomFormatter = new Log4JdbcCustomFormatter();
        log4JdbcCustomFormatter.setLoggingType(LoggingType.MULTI_LINE);
        log4JdbcCustomFormatter.setSqlPrefix("\n=============== SQL ===============\n");
        log4jdbcProxyDataSource.setLogFormatter(log4JdbcCustomFormatter);
        return log4jdbcProxyDataSource;
    }

    /**
     * 트렌젝션 메니저 설정
     * @return
     */
    @Bean
    public DataSourceTransactionManager transactionManagerWEB(){
        return new DataSourceTransactionManager(dataSourceWEB());
    }

    /**
     * SqlSessionFactory 설정
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactoryBeanWEB() throws Exception{
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSourceWEB());
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/com/legacy/application/**/**/**/sqlmap/*_ORACLE_SQL.xml"));
        SqlSessionFactory sqlSessionFactory = sessionFactoryBean.getObject();
        sqlSessionFactory.getConfiguration().setCacheEnabled(false);
        sqlSessionFactory.getConfiguration().setCallSettersOnNulls(true);
        sqlSessionFactory.getConfiguration().setJdbcTypeForNull(JdbcType.NULL);
        return sqlSessionFactory;
    }

    /**
     * mapper scanner 설정
     * 전자정부프레임워크 형식으로 @Mapper("xxxMapper") 형식으로 작성하기 위해서
     * @Mapper 어노테이션을 새로 만든 Mapper.class를 지정
     * -> FIXME 임의의 Mapper 지정 시, mapper scan 안되서 전자정부 프레임워크의 Mapper.class 사용
     * ( root-context.xml 에서 설정할 경우, interface 는 지정하지 못하여, implements 한 전자정부프레임워크의 class 사용 )
     *
     * @return
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurerWEB(){
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage("com.legacy.application.**.**.**.mapper");
        configurer.setAnnotationClass(Mapper.class);
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBeanWEB");
        return configurer;
    }
}
