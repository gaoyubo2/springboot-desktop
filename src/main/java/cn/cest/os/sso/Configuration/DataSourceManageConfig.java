//package cn.cest.os.sso.Configuration;
//
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import javax.sql.DataSource;
//
//@Configuration
//@MapperScan(basePackages = "cn.cest.os.sso.mapper.manage",sqlSessionFactoryRef = "manageSqlSessionFactory")
//public class DataSourceManageConfig {
//    @Bean(name = "manageDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.manage")
//    public DataSource testDataSource() {
//        System.out.println("初始化Manage的数据源");
//        return DataSourceBuilder.create().build();
//    }
//    @Bean(name = "manageSqlSessionFactory")
//    public SqlSessionFactory testSqlSessionFactory(@Qualifier("manageDataSource") DataSource dataSource) throws Exception {
//        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/manage/*.xml"));
//        return bean.getObject();
//    }
//
//    @Bean(name = "manageTransactionManager")
//    public DataSourceTransactionManager testTransactionManager(@Qualifier("manageDataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean(name = "manageSqlSessionTemplate")
//    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("manageSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//}
