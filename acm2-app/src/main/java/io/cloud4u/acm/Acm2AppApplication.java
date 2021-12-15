package io.cloud4u.acm;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@ComponentScan(basePackages = {"io.cloud4u.acm", "io.cloud4u.lib.acm", "io.cloud4u.common.spring.util"})
@MapperScan(basePackages = "io.cloud4u.acm.repository")
@SpringBootApplication
public class Acm2AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(Acm2AppApplication.class, args);
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);

		sessionFactory.setTypeAliasesPackage("io.cloud4u.acm.dto");
		
		Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mappers/**/*.xml");
		sessionFactory.setMapperLocations(res);
		
		return sessionFactory.getObject();
	}

}
